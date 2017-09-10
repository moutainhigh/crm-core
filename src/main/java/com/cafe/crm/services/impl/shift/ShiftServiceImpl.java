package com.cafe.crm.services.impl.shift;

import com.cafe.crm.dto.PositionDTO;
import com.cafe.crm.dto.UserDTO;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.client.Debt;
import com.cafe.crm.models.client.LayerProduct;
import com.cafe.crm.models.cost.Cost;
import com.cafe.crm.models.cost.CostCategory;
import com.cafe.crm.models.menu.Product;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.dto.ShiftView;
import com.cafe.crm.models.user.Position;
import com.cafe.crm.models.user.User;
import com.cafe.crm.repositories.shift.ShiftRepository;
import com.cafe.crm.services.interfaces.calculate.CalculateService;
import com.cafe.crm.services.interfaces.cost.CostCategoryService;
import com.cafe.crm.services.interfaces.cost.CostService;
import com.cafe.crm.services.interfaces.menu.ProductService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.services.interfaces.user.UserService;
import com.cafe.crm.utils.DozerUtil;
import com.cafe.crm.utils.TimeManager;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Transactional
@Service
public class ShiftServiceImpl implements ShiftService {

	private final ShiftRepository shiftRepository;
	private final UserService userService;
	private final CostCategoryService costCategoryService;
	private final CalculateService calculateService;
	private final TimeManager timeManager;
	private final SessionRegistry sessionRegistry;
	private CostService costService;
	private final ProductService productService;

	@Value("${cost-category-name.salary-for-shift}")
	private String categoryNameSalaryForShift;

	@Autowired
	public ShiftServiceImpl(CalculateService calculateService, TimeManager timeManager, CostCategoryService costCategoryService, ShiftRepository shiftRepository, UserService userService, SessionRegistry sessionRegistry, ProductService productService) {
		this.calculateService = calculateService;
		this.timeManager = timeManager;
		this.costCategoryService = costCategoryService;
		this.shiftRepository = shiftRepository;
		this.userService = userService;
		this.sessionRegistry = sessionRegistry;
		this.productService = productService;
	}

	@Autowired
	public void setCostService(CostService costService) {
		this.costService = costService;
	}

	@Override
	public void saveAndFlush(Shift shift) {
		shiftRepository.saveAndFlush(shift);
	}

	@Override
	public Shift crateNewShift(Double cashBox, Double bankCashBox, long... usersIdsOnShift) {
		List<User> users = userService.findByIdIn(usersIdsOnShift);
		Shift shift = new Shift(timeManager.getDate(), users, bankCashBox);
		shift.setOpen(true);
		for (User user: users) {
			user.getShifts().add(shift);
		}
		shift.setCashBox(cashBox);
		shift.setBankCashBox(bankCashBox);
		shiftRepository.saveAndFlush(shift);
		return shift;
	}

	@Transactional(readOnly = true)
	@Override
	public Shift findOne(Long id) {
		return shiftRepository.findOne(id);
	}

	@Transactional(readOnly = true)
	@Override
	public List<User> getUsersNotOnShift() {
		List<User> users = userService.findAll();
		Shift lastShift = shiftRepository.getLast();
		if (lastShift != null) {
			users.removeAll(lastShift.getUsers());
		}
		return users;
	}

	@Transactional(readOnly = true)
	@Override
	public List<User> getUsersOnShift() {
		Shift lastShift = shiftRepository.getLast();
		if (lastShift != null) {
			return lastShift.getUsers();
		}
		return new ArrayList<>();
	}

	@Override
	public void deleteUserFromShift(Long userId) {
		Shift shift = shiftRepository.getLast();
		User user = userService.findById(userId);
		shift.getUsers().remove(user);
		user.getShifts().remove(shift);
		shiftRepository.saveAndFlush(shift);
	}

	@Override
	public void addUserToShift(Long userId) {
		Shift shift = shiftRepository.getLast();
		User user = userService.findById(userId);
		shift.getUsers().add(user);
		user.getShifts().add(shift);
		shiftRepository.saveAndFlush(shift);
	}

	@Transactional(readOnly = true)
	@Override
	public Shift getLast() {
		return shiftRepository.getLast();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Shift> findAll() {
		return shiftRepository.findAll();
	}

	@Transactional
	@Override
	public Shift closeShift(Map<Long, Integer> mapOfUsersIdsAndBonuses, Double allPrice, Double cashBox, Double bankCashBox, String comment) {
		CostCategory costCategory = costCategoryService.find(categoryNameSalaryForShift);
		Shift shift = shiftRepository.getLast();
		for (Map.Entry<Long, Integer> entry: mapOfUsersIdsAndBonuses.entrySet()) {
			User user = userService.findById(entry.getKey());
			user.setSalary(user.getSalary() + user.getShiftSalary());
			user.setBonus(user.getBonus() + entry.getValue());
			int salaryCost = user.getShiftSalary() + entry.getValue();
			Cost costSalaryUser = new Cost(user.getFirstName() + " " + user.getLastName(),
					salaryCost, 1,
					costCategory, LocalDate.now());
			costService.save(costSalaryUser);
			userService.save(user);
		}
		shift.setBankCashBox(bankCashBox);
		shift.setCashBox(cashBox);
		shift.setProfit(allPrice);
		shift.setComment(comment);
		shift.setOpen(false);
		shiftRepository.saveAndFlush(shift);
		closeAllUserSessions();
		return shift;
	}

	private void closeAllUserSessions() {
		for (Object principal: sessionRegistry.getAllPrincipals()) {
			sessionRegistry.getAllSessions(principal, false).forEach(SessionInformation::expireNow);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Set<Shift> findByDates(LocalDate start, LocalDate end) {
		return shiftRepository.findByDates(start, end);
	}

	@Override
	public ShiftView createShiftView(Shift shift) {

		List<UserDTO> usersOnShift = DozerUtil.map(shift.getUsers(),UserDTO.class);
		Set<Client> clients = findOne(shift.getId()).getClients();
		List<Calculate> activeCalculate = calculateService.getAllOpen();
		Set<Calculate> allCalculate = shift.getCalculates();
		double cashBox = shift.getCashBox();
		double bankCashBox = shift.getBankCashBox();
		Double totalCashBox;
		int usersTotalShiftSalary = 0;
		Double card = 0D;
		Double allPrice = 0D;

		Set<LayerProduct> layerProducts = new HashSet<>();
		for (Client client: clients) {
			layerProducts.addAll(client.getLayerProducts());
			card += client.getPayWithCard();
			allPrice += client.getPriceTime();
		}

		Map<Long, Integer> staffPercentBonusesMap = calcStaffPercentBonusesAndGetMap(layerProducts, usersOnShift);

		for (UserDTO user: usersOnShift) {
			usersTotalShiftSalary += user.getShiftSalary();
		}

		for (LayerProduct layerProduct: layerProducts) {
			if (layerProduct.isDirtyProfit()) {
				allPrice += layerProduct.getCost();
			}
		}

		for (Debt debt: shift.getRepaidDebts()) {
			allPrice += debt.getDebtAmount();
		}

		for (Debt debt: shift.getGivenDebts()) {
			allPrice -= debt.getDebtAmount();
		}


		LocalDate shiftDate = shift.getShiftDate();
		List<Cost> costWithoutUsersSalaries = costService.findByShiftIdAndCategoryNameNot(shift.getId(), categoryNameSalaryForShift);
		double otherCosts = 0d;
		for (Cost cost: costWithoutUsersSalaries) {
			otherCosts += (cost.getPrice() * cost.getQuantity());
		}
		allPrice -= card;
		totalCashBox = (cashBox + bankCashBox + allPrice) - (usersTotalShiftSalary + otherCosts);
		return new ShiftView(usersOnShift, clients, activeCalculate, allCalculate,
				cashBox, totalCashBox, usersTotalShiftSalary, card, allPrice, shiftDate, otherCosts, bankCashBox,staffPercentBonusesMap);
	}

	@Override
	public Shift findByDateShift(LocalDate date) {
		return shiftRepository.findByShiftDate(date);
	}

	@Override
	public void transferFromBankToCashBox(Double transfer) {
		Shift lastShift = getLast();
		Double bankCashBox = lastShift.getBankCashBox() + transfer;
		Double cashBox = lastShift.getCashBox() - transfer;

		lastShift.setCashBox(cashBox);
		lastShift.setBankCashBox(bankCashBox);
		saveAndFlush(lastShift);
	}

	@Override
	public void transferFromCashBoxToBank(Double transfer) {
		Shift lastShift = getLast();
		Double bankCashBox = lastShift.getBankCashBox() - transfer;
		Double cashBox = lastShift.getCashBox() + transfer;

		lastShift.setCashBox(cashBox);
		lastShift.setBankCashBox(bankCashBox);
		saveAndFlush(lastShift);
	}

	private Map<Long,Integer> calcStaffPercentBonusesAndGetMap(Set<LayerProduct> layerProducts, List<UserDTO> staff) {
		Map<Long,Integer> staffPercentBonusesMap = new HashMap<>();
		Mapper mapper = new DozerBeanMapper();

		for (LayerProduct layerProduct: layerProducts) {

			Long productId = layerProduct.getProductId();
			Product product = productService.findOne(productId);
			Map<Position, Integer> staffPercent = product.getStaffPercent();

			for (UserDTO user: staff) {

				List<PositionDTO> userPositions = DozerUtil.map(mapper,user.getPositions(),PositionDTO.class);
				for (PositionDTO positionDTO: userPositions) {

					Integer percent = staffPercent.get(DozerUtil.map(mapper,positionDTO,Position.class));
					if (percent != null) {

						int bonus = (int) (layerProduct.getCost() * percent / 100);
						user.setShiftSalary(bonus + user.getShiftSalary());

						Integer saveBonus = staffPercentBonusesMap.get(user.getId());
						staffPercentBonusesMap.put(user.getId(), bonus + (saveBonus != null?saveBonus:0));
					}
				}
			}
		}
		return staffPercentBonusesMap;
	}
}

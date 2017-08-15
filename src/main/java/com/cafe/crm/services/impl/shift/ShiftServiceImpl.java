package com.cafe.crm.services.impl.shift;

import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.client.Debt;
import com.cafe.crm.models.client.LayerProduct;
import com.cafe.crm.models.goods.Goods;
import com.cafe.crm.models.goods.GoodsCategory;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.dto.ShiftView;
import com.cafe.crm.models.user.User;
import com.cafe.crm.repositories.shift.ShiftRepository;
import com.cafe.crm.services.interfaces.calculate.CalculateService;
import com.cafe.crm.services.interfaces.goods.GoodsCategoryService;
import com.cafe.crm.services.interfaces.goods.GoodsService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.services.interfaces.user.UserService;
import com.cafe.crm.utils.TimeManager;
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
	private final GoodsCategoryService goodsCategoryService;
	private final CalculateService calculateService;
	private final TimeManager timeManager;
	private final SessionRegistry sessionRegistry;
	private GoodsService goodsService;

	@Value("${cost-category-name.salary-for-shift}")
	private String categoryNameSalaryForShift;

	@Autowired
	public ShiftServiceImpl(CalculateService calculateService, TimeManager timeManager, GoodsCategoryService goodsCategoryService, ShiftRepository shiftRepository, UserService userService, SessionRegistry sessionRegistry) {
		this.calculateService = calculateService;
		this.timeManager = timeManager;
		this.goodsCategoryService = goodsCategoryService;
		this.shiftRepository = shiftRepository;
		this.userService = userService;
		this.sessionRegistry = sessionRegistry;
	}

	@Autowired
	public void setGoodsService(GoodsService goodsService) {
		this.goodsService = goodsService;
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
		for (User user : users) {
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
		GoodsCategory goodsCategory = goodsCategoryService.findByNameIgnoreCase(categoryNameSalaryForShift);
		Shift shift = shiftRepository.getLast();
		for (Map.Entry<Long, Integer> entry : mapOfUsersIdsAndBonuses.entrySet()) {
			User user = userService.findById(entry.getKey());
			user.setSalary(user.getSalary() + user.getShiftSalary());
			user.setBonus(user.getBonus() + entry.getValue());
			int salaryCost = user.getShiftSalary() + entry.getValue();
			Goods goodsSalaryWorker = new Goods(user.getFirstName() + " " + user.getLastName(),
					salaryCost, 1,
					goodsCategory, LocalDate.now());
			goodsService.save(goodsSalaryWorker);
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
		for (Object principal : sessionRegistry.getAllPrincipals()) {
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
		List<User> usersOnShift = getUsersOnShift();
		Set<Client> clients = findOne(shift.getId()).getClients();
		List<Calculate> activeCalculate = calculateService.getAllOpen();
		Set<Calculate> allCalculate = shift.getCalculates();
		double cashBox = shift.getCashBox();
		double bankCashBox = shift.getBankCashBox();
		Double totalCashBox;
		int usersTotalShiftSalary = 0;
		Double card = 0D;
		Double allPrice = 0D;
		for (User user : usersOnShift) {
			usersTotalShiftSalary += user.getShiftSalary();
		}
		Set<LayerProduct> layerProducts = new HashSet<>();
		for (Client client : clients) {
			layerProducts.addAll(client.getLayerProducts());
			card = card + client.getPayWithCard();
			allPrice += client.getPriceTime();
		}
		for (LayerProduct layerProduct : layerProducts) {
			if (layerProduct.isDirtyProfit()) {
				allPrice += layerProduct.getCost();
			}
		}
		for (Debt debt : shift.getDebts()) {
			allPrice += debt.getDebtAmount();
		}
		LocalDate shiftDate = shift.getShiftDate();
		List<Goods> goodsWithoutUsersSalaries = goodsService.findByShiftIdAndCategoryNameNot(shift.getId(), categoryNameSalaryForShift);
		double otherCosts = 0d;
		for (Goods goods : goodsWithoutUsersSalaries) {
			otherCosts += (goods.getPrice() * goods.getQuantity());
		}
		totalCashBox = (cashBox + bankCashBox + allPrice) - (usersTotalShiftSalary + otherCosts);
		return new ShiftView(usersOnShift, clients, activeCalculate, allCalculate,
				cashBox, totalCashBox, usersTotalShiftSalary, card, allPrice, shiftDate, otherCosts, bankCashBox);
	}

	@Override
	public Shift findByDateShift(LocalDate date) {
		return shiftRepository.findByShiftDate(date);
	}

}

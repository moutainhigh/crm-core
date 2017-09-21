package com.cafe.crm.services.impl.shift;

import com.cafe.crm.dto.PositionDTO;
import com.cafe.crm.dto.ShiftView;
import com.cafe.crm.dto.UserDTO;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.client.Debt;
import com.cafe.crm.models.client.LayerProduct;
import com.cafe.crm.models.cost.Cost;
import com.cafe.crm.models.menu.Product;
import com.cafe.crm.models.note.Note;
import com.cafe.crm.models.note.NoteRecord;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.user.Position;
import com.cafe.crm.models.user.User;
import com.cafe.crm.repositories.shift.ShiftRepository;
import com.cafe.crm.services.interfaces.calculate.CalculateService;
import com.cafe.crm.services.interfaces.cost.CostCategoryService;
import com.cafe.crm.services.interfaces.cost.CostService;
import com.cafe.crm.services.interfaces.menu.ProductService;
import com.cafe.crm.services.interfaces.note.NoteRecordService;
import com.cafe.crm.services.interfaces.note.NoteService;
import com.cafe.crm.services.interfaces.position.PositionService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.services.interfaces.user.UserService;
import com.cafe.crm.utils.converters.user.UserConverter;
import com.cafe.crm.utils.DozerUtil;
import com.cafe.crm.utils.TimeManager;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
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
	private final CalculateService calculateService;
	private final TimeManager timeManager;
	private final SessionRegistry sessionRegistry;
	private final NoteService noteService;
	private final NoteRecordService noteRecordService;
	private CostService costService;
	private final ProductService productService;
	private final PositionService positionService;

	@Autowired
	public ShiftServiceImpl(CalculateService calculateService, TimeManager timeManager, CostCategoryService costCategoryService, ShiftRepository shiftRepository, UserService userService, SessionRegistry sessionRegistry, NoteService noteService, NoteRecordService noteRecordService, ProductService productService, PositionService positionService) {
		this.calculateService = calculateService;
		this.timeManager = timeManager;
		this.shiftRepository = shiftRepository;
		this.userService = userService;
		this.sessionRegistry = sessionRegistry;
		this.noteService = noteService;
		this.noteRecordService = noteRecordService;
		this.productService = productService;
		this.positionService = positionService;
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
	public Shift createNewShift(Double cashBox, Double bankCashBox, long... usersIdsOnShift) {
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
	public Shift closeShift(Map<Long, Integer> mapOfUsersIdsAndBonuses, Double allPrice, Double cashBox, Double bankCashBox, String comment, Map<String, String> mapOfNoteNameAndValue) {
		Shift shift = shiftRepository.getLast();
		List<User> usersOnShift = shift.getUsers();
		for (Map.Entry<Long, Integer> entry : mapOfUsersIdsAndBonuses.entrySet()) {
			User user = userService.findById(entry.getKey());
			user.setSalary(user.getSalary() + user.getShiftSalary());
			user.setBonus(user.getBonus() + entry.getValue());
			userService.save(user);
		}
		for (User user : usersOnShift) {
			int amountOfPositionsPercent = user.getPositions().stream().filter(Position::isPositionUsePercentOfSales).mapToInt(Position::getPercentageOfSales).sum();
			user.setShiftSalary((int) (user.getShiftSalary() + (allPrice * amountOfPositionsPercent) / 100));
			userService.save(user);
		}
		List<NoteRecord> noteRecords = saveAndGetNoteRecords(mapOfNoteNameAndValue, shift);
		shift.setBankCashBox(bankCashBox);
		shift.setCashBox(cashBox);
		shift.setProfit(allPrice);
		shift.setComment(comment);
		shift.setNoteRecords(noteRecords);
		shift.setOpen(false);
		shiftRepository.saveAndFlush(shift);
		closeAllUserSessions();
		return shift;
	}

	private List<NoteRecord> saveAndGetNoteRecords(Map<String, String> mapOfNoteNameAndValue, Shift shift) {
		List<NoteRecord> noteRecords = new ArrayList<>();
		if (mapOfNoteNameAndValue != null) {
			for (Map.Entry<String, String> noteNameAndValue : mapOfNoteNameAndValue.entrySet()) {
				NoteRecord noteRecord = new NoteRecord();
				noteRecord.setName(noteNameAndValue.getKey());
				noteRecord.setValue(noteNameAndValue.getValue());
				noteRecord.setShift(shift);
				NoteRecord savedNoteRecord = noteRecordService.save(noteRecord);
				noteRecords.add(savedNoteRecord);
			}
		}
		return noteRecords;
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
		List<UserDTO> usersOnShift = UserConverter.convertListUsersToDTO(shift.getUsers());
		Set<Client> clients = findOne(shift.getId()).getClients();
		List<Calculate> activeCalculate = calculateService.getAllOpen();
		Set<Calculate> allCalculate = shift.getCalculates();
		List<Note> enabledNotes = noteService.findAllByEnableIsTrue();
		Double cashBox = shift.getCashBox();
		Double bankCashBox = shift.getBankCashBox();
		Double totalCashBox;
		int usersTotalShiftSalary = 0;
		Double card = 0D;
		Double allPrice = 0D;

		Set<LayerProduct> layerProducts = new HashSet<>();
		for (Client client : clients) {
			layerProducts.addAll(client.getLayerProducts());
			card += client.getPayWithCard();
			allPrice += client.getPriceTime();
		}

		Map<Long, Integer> staffPercentBonusesMap = calcStaffPercentBonusesAndGetMap(layerProducts, usersOnShift);

		for (LayerProduct layerProduct : layerProducts) {
			if (layerProduct.isDirtyProfit()) {
				allPrice += layerProduct.getCost();
			}
		}

		for (Debt debt : shift.getRepaidDebts()) {
			allPrice += debt.getDebtAmount();
		}

		for (Debt debt : shift.getGivenDebts()) {
			allPrice -= debt.getDebtAmount();
		}

		LocalDate shiftDate = shift.getShiftDate();
		List<Cost> costWithoutUsersSalaries = costService.findByShiftId(shift.getId());
		double otherCosts = 0d;

		for (Cost cost : costWithoutUsersSalaries) {
			otherCosts += (cost.getPrice() * cost.getQuantity());
		}

		allPrice -= card;

		for (UserDTO user : usersOnShift) {
			int amountOfPositionsPercent = user.getPositions().stream().filter(PositionDTO::isPositionUsePercentOfSales).mapToInt(PositionDTO::getPercentageOfSales).sum();
			user.setShiftSalary((int) (user.getShiftSalary() + (allPrice * amountOfPositionsPercent) / 100));
			usersTotalShiftSalary += user.getShiftSalary();
		}

		totalCashBox = (cashBox + bankCashBox + allPrice) - (usersTotalShiftSalary + otherCosts);

		return new ShiftView(usersOnShift, clients, activeCalculate, allCalculate,
				cashBox, totalCashBox, usersTotalShiftSalary, card, allPrice, shiftDate, otherCosts, bankCashBox, enabledNotes, staffPercentBonusesMap);
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

	private Map<Long, Integer> calcStaffPercentBonusesAndGetMap(Set<LayerProduct> layerProducts, List<UserDTO> staff) {
		Mapper mapper = new DozerBeanMapper();
		Map<Long, Integer> staffPercentBonusesMap = new HashMap<>();

		for (LayerProduct layerProduct : layerProducts) {

			Long productId = layerProduct.getProductId();
			Product product = productService.findOne(productId);
			Map<Position, Integer> staffPercent = product.getStaffPercent();

			for (UserDTO user : staff) {
				List<PositionDTO> userPositions = user.getPositions();

				for (PositionDTO positionDTO : userPositions) {

					Integer percent = staffPercent.get(DozerUtil.map(mapper,positionDTO,Position.class));
					if (percent != null) {

						int bonus = (int) (layerProduct.getCost() * percent / 100);
						user.setShiftSalary(bonus + user.getShiftSalary());

						Integer saveBonus = staffPercentBonusesMap.get(user.getId());
						if (saveBonus == null) {
							staffPercentBonusesMap.put(user.getId(), bonus);
						} else {
							staffPercentBonusesMap.put(user.getId(), bonus + saveBonus);
						}

					}
				}
			}
		}
		return staffPercentBonusesMap;
	}
}

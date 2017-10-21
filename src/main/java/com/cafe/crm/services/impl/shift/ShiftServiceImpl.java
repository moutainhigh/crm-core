package com.cafe.crm.services.impl.shift;

import com.cafe.crm.dto.PositionDTO;
import com.cafe.crm.dto.ShiftView;
import com.cafe.crm.dto.UserDTO;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.client.Debt;
import com.cafe.crm.models.client.LayerProduct;
import com.cafe.crm.models.company.Company;
import com.cafe.crm.models.cost.Cost;
import com.cafe.crm.models.menu.Product;
import com.cafe.crm.models.note.Note;
import com.cafe.crm.models.note.NoteRecord;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.models.user.Position;
import com.cafe.crm.models.user.Receipt;
import com.cafe.crm.models.user.User;
import com.cafe.crm.repositories.shift.ShiftRepository;
import com.cafe.crm.services.interfaces.company.CompanyService;
import com.cafe.crm.services.interfaces.cost.CostService;
import com.cafe.crm.services.interfaces.menu.ProductService;
import com.cafe.crm.services.interfaces.note.NoteRecordService;
import com.cafe.crm.services.interfaces.note.NoteService;
import com.cafe.crm.services.interfaces.receipt.ReceiptService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.services.interfaces.user.UserService;
import com.cafe.crm.utils.CompanyIdCache;
import com.cafe.crm.utils.RoundUpper;
import com.cafe.crm.utils.TimeManager;
import com.yc.easytransformer.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Transactional
@Service
public class ShiftServiceImpl implements ShiftService {

	private final ShiftRepository shiftRepository;
	private final UserService userService;
	private final TimeManager timeManager;
	private final NoteRecordService noteRecordService;

    private final CompanyService companyService;
    private final CompanyIdCache companyIdCache;


	@Autowired
	public ShiftServiceImpl(TimeManager timeManager, ShiftRepository shiftRepository,
                            UserService userService,
                            NoteRecordService noteRecordService,
                            CompanyIdCache companyIdCache,
                            CompanyService companyService) {
		this.timeManager = timeManager;
		this.shiftRepository = shiftRepository;
		this.userService = userService;
		this.noteRecordService = noteRecordService;
        this.companyService = companyService;
        this.companyIdCache = companyIdCache;
	}

	private void setCompany(Shift shift) {
		Long companyId = companyIdCache.getCompanyId();
		Company company = companyService.findOne(companyId);
		shift.setCompany(company);
	}

	@Override
	public void saveAndFlush(Shift shift) {
		setCompany(shift);
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
        setCompany(shift);
        shiftRepository.saveAndFlush(shift);
        return shift;
    }

    @Transactional(readOnly = true)
    @Override
    public Shift findOne(Long id) {
        return shiftRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsersNotOnShift() {
        List<User> users = userService.findAll();
        Shift lastShift = shiftRepository.getLastAndCompanyId(companyIdCache.getCompanyId());
        if (lastShift != null) {
            users.removeAll(lastShift.getUsers());
        }
        return users;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsersOnShift() {
        Shift lastShift = shiftRepository.getLastAndCompanyId(companyIdCache.getCompanyId());
        if (lastShift != null) {
            return lastShift.getUsers();
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteUserFromShift(Long userId) {
        Shift shift = shiftRepository.getLastAndCompanyId(companyIdCache.getCompanyId());
        User user = userService.findById(userId);
        shift.getUsers().remove(user);
        user.getShifts().remove(shift);
        shiftRepository.saveAndFlush(shift);
    }

    @Override
    public void addUserToShift(Long userId) {
        Shift shift = shiftRepository.getLastAndCompanyId(companyIdCache.getCompanyId());
        User user = userService.findById(userId);
        shift.getUsers().add(user);
        user.getShifts().add(shift);
        shiftRepository.saveAndFlush(shift);
    }

    @Transactional(readOnly = true)
    @Override
    public Shift getLast() {
        return shiftRepository.getLastAndCompanyId(companyIdCache.getCompanyId());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Shift> findAll() {
        return shiftRepository.findByCompanyId(companyIdCache.getCompanyId());
    }

	@Transactional
	@Override
	public Shift closeShift(Map<Long, Integer> mapOfUsersIdsAndBonuses, Double allPrice, Double cashBox, Double bankCashBox, String comment, Map<String, String> mapOfNoteNameAndValue) {
		Shift shift = shiftRepository.getLastAndCompanyId(companyIdCache.getCompanyId());
		List<User> usersOnShift = shift.getUsers();
		for (Map.Entry<Long, Integer> entry : mapOfUsersIdsAndBonuses.entrySet()) {
			User user = userService.findById(entry.getKey());
			user.setSalary(user.getSalary() + user.getShiftSalary());
			user.setBonus(user.getBonus() + entry.getValue());
			userService.save(user);
		}
		for (User user : usersOnShift) {
			int amountOfPositionsPercent = user.getPositions().stream().filter(Position::isPositionUsePercentOfSales).mapToInt(Position::getPercentageOfSales).sum();
			user.setSalary((int) (user.getShiftSalary() + (allPrice * amountOfPositionsPercent) / 100));
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


    @Transactional(readOnly = true)
    @Override
    public Set<Shift> findByDates(LocalDate start, LocalDate end) {
        return shiftRepository.findByDatesAndCompanyId(start, end, companyIdCache.getCompanyId());
    }

	@Override
	public Shift findByDateShift(LocalDate date) {
		return shiftRepository.findByShiftDateAndCompanyId(date, companyIdCache.getCompanyId());
	}

    @Override
    public LocalDate getLastShiftDate() {
        Shift lastShift = shiftRepository.getLastAndCompanyId(companyIdCache.getCompanyId());
        if (lastShift != null) {
            return lastShift.getShiftDate();
        } else {
            return timeManager.getDate();
        }
    }
}

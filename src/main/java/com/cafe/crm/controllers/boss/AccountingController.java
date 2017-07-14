package com.cafe.crm.controllers.boss;
import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.models.worker.Manager;
import com.cafe.crm.models.worker.Position;
import com.cafe.crm.models.worker.Worker;
import com.cafe.crm.services.interfaces.position.PositionService;
import com.cafe.crm.services.interfaces.worker.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class AccountingController {

	@Autowired
	private WorkerService workerService;
	@Autowired
	private PositionService positionService;

	@ModelAttribute(value = "worker")
	public Worker newEntityWorker() {
		return new Worker("worker");
	}

	@ModelAttribute(value = "manager")
	public Manager newEntityManager() {
		return new Manager("admin");
	}

	@ModelAttribute(value = "boss")
	public Boss newEntityBoss() {
		return new Boss("boss");
	}

	@ModelAttribute(value = "position")
	public Position newEntityPosition() {
		return new Position();
	}

	private final Pattern VALID_EMAIL_ADDRESS_REGEX =
			Pattern.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");

	private final Pattern VALID_PNONE_REGEX =
			Pattern.compile("^(8|\\+7)?[\\d]{10}$");

	private final Pattern VALID_SHIFT_SALARY_REGEX =
			Pattern.compile("\\d+");

	@RequestMapping(value = {"/worker"}, method = RequestMethod.GET)
	public ModelAndView getAllWorker(ModelAndView model) {
		List<Worker> workerList = workerService.listAllWorker();
		List<Manager> managerList = workerService.listAllManager();
		List<Boss> bossList = workerService.listAllBoss();
		List<Position> positionList = positionService.listAllPosition();
		List<Position> allPosManager = positionService.listAllPosition();
		for (Manager manager : managerList) {
			allPosManager.removeAll(manager.getAllPosition());
		}
		List<Position> allPosBoss = positionService.listAllPosition();
		for (Boss boss : bossList) {
			allPosBoss.removeAll(boss.getAllPosition());
		}
		List<Position> allPosWorker = positionService.listAllPosition();
		for (Worker worker : workerList) {
			if (worker.getActionForm().equalsIgnoreCase("worker")) {
				allPosWorker.removeAll(worker.getAllPosition());
			}
		}
		workerList.sort(Comparator.comparing(o -> (o.getLastName())));
		managerList.sort(Comparator.comparing(o -> (o.getLastName())));
		bossList.sort(Comparator.comparing(o -> (o.getLastName())));
		positionList.sort(Comparator.comparing(o -> (o.getJobName())));
		model.addObject("allPosWorker", allPosWorker);
		model.addObject("allPosManager", allPosManager);
		model.addObject("allPosBoss", allPosBoss);
		model.addObject("managerList", managerList);
		model.addObject("workerList", workerList);
		model.addObject("bossList", bossList);
		model.addObject("positionList", positionList);
		model.setViewName("pages/accountingPage");
		return model;
	}

	@RequestMapping(value = {"worker/addWorker"}, method = RequestMethod.POST)
	public String addWorker(Worker worker,
							@RequestParam(name = "passwordWorker", required = false) String passwordWorker,
							@RequestParam(name = "submitNewPasswordWorker", required = false) String submitNewPasswordWorker,
							@RequestParam(name = "adminPositionId", required = false) Long adminPositionId,
							@RequestParam(name = "bossPositionId", required = false) Long bossPositionId) throws IOException, URISyntaxException {

		Matcher matcherEmail = VALID_EMAIL_ADDRESS_REGEX.matcher(worker.getEmail());
		Matcher matcherPhone = VALID_PNONE_REGEX.matcher(worker.getPhone());
		Matcher matcherShiftSalary = VALID_SHIFT_SALARY_REGEX.matcher(String.valueOf(worker.getShiftSalary()));
		if (matcherEmail.find() && matcherShiftSalary.find() && matcherPhone.find()) {
			worker.setPhone(workerService.parsePhoneNumber(worker.getPhone()));
			if (adminPositionId == null && bossPositionId == null) {
				workerService.addWorker(worker);
			} else if (adminPositionId != null && bossPositionId != null &&
					(passwordWorker.equals(submitNewPasswordWorker))) {
				workerService.castWorkerToBoss(worker, passwordWorker, bossPositionId);
			} else if ((adminPositionId != null) && (passwordWorker.equals(submitNewPasswordWorker))) {
				workerService.castWorkerToManager(worker, passwordWorker, adminPositionId);
			} else if (bossPositionId != null && passwordWorker.equals(submitNewPasswordWorker)) {
				workerService.castWorkerToBoss(worker, passwordWorker, bossPositionId);
			}
			return "redirect:/worker";
		}
		return "redirect:/worker";
	}

	@RequestMapping(value = {"worker/addManager"}, method = RequestMethod.POST)
	public String addManager(Manager manager,
							 @RequestParam(name = "bossPositionId", required = false) Long bossPositionId,
							 @RequestParam(name = "submitPassword", required = false) String submitAdminPassword) throws IOException {

		Matcher matcherEmail = VALID_EMAIL_ADDRESS_REGEX.matcher(manager.getEmail());
		Matcher matcherPhone = VALID_PNONE_REGEX.matcher(manager.getPhone());
		Matcher matcherShiftSalary = VALID_SHIFT_SALARY_REGEX.matcher(String.valueOf(manager.getShiftSalary()));
		if (matcherEmail.find() && matcherShiftSalary.find() && matcherPhone.find()) {
			manager.setPhone(workerService.parsePhoneNumber(manager.getPhone()));
			if (!manager.getPassword().equals(submitAdminPassword)) {
				return "redirect:/worker";
			} else if (bossPositionId == null) {
				workerService.addManager(manager);
			} else {
				workerService.castManagerToBoss(manager, bossPositionId);
			}
		}
		return "redirect:/worker";
	}

	@RequestMapping(value = {"worker/addBoss"}, method = RequestMethod.POST)
	public String addBoss(Boss boss,
						  @RequestParam(name = "submitPassword", required = false) String submitBossPassword) throws IOException {

		Matcher matcherEmail = VALID_EMAIL_ADDRESS_REGEX.matcher(boss.getEmail());
		Matcher matcherPhone = VALID_PNONE_REGEX.matcher(boss.getPhone());
		Matcher matcherShiftSalary = VALID_SHIFT_SALARY_REGEX.matcher(String.valueOf(boss.getShiftSalary()));
		if (matcherEmail.find() && matcherShiftSalary.find() && matcherPhone.find()) {
			boss.setPhone(workerService.parsePhoneNumber(boss.getPhone()));
			if (!boss.getPassword().equals(submitBossPassword)) {
				return "redirect:/worker";
			} else {
				workerService.addBoss(boss);
			}
			return "redirect:/worker";
		}
		return "redirect:/worker";
	}

	@RequestMapping(value = {"worker/editWorker"}, method = RequestMethod.POST)
	public String editWorker(Worker worker,
							 @RequestParam(name = "adminPositionId", required = false) Long adminPositionId,
							 @RequestParam(name = "bossPositionId", required = false) Long bossPositionId,
							 @RequestParam(name = "passwordEditWorkerToAdmin", required = false) String password,
							 @RequestParam(name = "submitPasswordEditWorkerToAdmin", required = false) String submitPassword) throws IOException {

		Matcher matcherEmail = VALID_EMAIL_ADDRESS_REGEX.matcher(worker.getEmail());
		Matcher matcherPhone = VALID_PNONE_REGEX.matcher(worker.getPhone());
		Matcher matcherShiftSalary = VALID_SHIFT_SALARY_REGEX.matcher(String.valueOf(worker.getShiftSalary()));
		if (matcherEmail.find() && matcherShiftSalary.find() && matcherPhone.find()) {
			worker.setPhone(workerService.parsePhoneNumber(worker.getPhone()));
			workerService.editWorker(worker, adminPositionId, bossPositionId, password, submitPassword);
		}
		return "redirect:/worker";
	}


	@RequestMapping(value = {"worker/editManager"}, method = RequestMethod.POST)
	public String editManager(Manager manager,
							  @RequestParam(name = "adminPositionId", required = false) Long adminPositionId,
							  @RequestParam(name = "bossPositionId", required = false) Long bossPositionId) throws IOException {

		Matcher matcherEmail = VALID_EMAIL_ADDRESS_REGEX.matcher(manager.getEmail());
		Matcher matcherPhone = VALID_PNONE_REGEX.matcher(manager.getPhone());
		Matcher matcherShiftSalary = VALID_SHIFT_SALARY_REGEX.matcher(String.valueOf(manager.getShiftSalary()));
		if (matcherEmail.find() && matcherShiftSalary.find() && matcherPhone.find()) {
			manager.setPhone(workerService.parsePhoneNumber(manager.getPhone()));
			workerService.editManager(manager, adminPositionId, bossPositionId);
			return "redirect:/worker";
		}
		return "redirect:/worker";
	}

	@RequestMapping(value = {"worker/editBoss"}, method = RequestMethod.POST)
	public String editBoss(Boss boss,
						   @RequestParam(name = "bossPositionId", required = false) Long bossPositionId,
						   @RequestParam(name = "adminPositionId", required = false) Long adminPositionId,
						   @RequestParam(name = "shiftSalary", required = false) Long shiftSalary) throws IOException {

		Matcher matcherEmail = VALID_EMAIL_ADDRESS_REGEX.matcher(boss.getEmail());
		Matcher matcherPhone = VALID_PNONE_REGEX.matcher(boss.getPhone());
		if (matcherEmail.find() && matcherPhone.find()) {
			boss.setPhone(workerService.parsePhoneNumber(boss.getPhone()));
			workerService.editBoss(boss, bossPositionId, adminPositionId, shiftSalary);
			return "redirect:/worker";
		}
		return "redirect:/worker";
	}

	@RequestMapping(value = {"worker/deleteWorker/{id}"}, method = RequestMethod.GET)
	public String deleteWorker(@PathVariable("id") Long id) throws IOException {
		Worker worker = workerService.findWorkerById(id);
		workerService.deleteWorker(worker);
		return "redirect:/worker";
	}

	@RequestMapping(value = {"worker/addPosition"}, method = RequestMethod.POST)
	public String addPosition(Position position) throws IOException {
		positionService.addPosition(position);
		return "redirect:/worker";
	}

	@RequestMapping(value = {"worker/editPosition"}, method = RequestMethod.POST)
	public String editPosition(Position position) throws IOException {
		positionService.updatePosition(position);
		return "redirect:/worker";
	}

	@RequestMapping(value = {"worker/deletePosition/{id}"}, method = RequestMethod.GET)
	public String deletePosition(@PathVariable("id") Long id) throws IOException {
		positionService.deletePosition(id);
		return "redirect:/worker";
	}
}

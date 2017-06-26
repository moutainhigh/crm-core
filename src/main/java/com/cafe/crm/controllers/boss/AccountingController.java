package com.cafe.crm.controllers.boss;

import com.cafe.crm.models.property.PropertyWrapper;
import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.models.worker.Manager;
import com.cafe.crm.models.worker.Position;
import com.cafe.crm.models.worker.Worker;
import com.cafe.crm.service_abstract.position.PositionService;
import com.cafe.crm.service_abstract.property.PropertyService;
import com.cafe.crm.service_abstract.worker.WorkerService;
import com.cafe.crm.service_impl.position.PositionServiceImpl;
import com.cafe.crm.service_impl.worker.WorkerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.List;

@Controller
public class AccountingController {

	@ModelAttribute(value = "wrapper")
	public PropertyWrapper addClass() {
		PropertyWrapper PropertyWrapper = new PropertyWrapper();
		PropertyWrapper.setProperties(propertyService.findAll());
		return PropertyWrapper;
	}

	@ModelAttribute(value = "worker")
	public Worker newEntityWorker() {
		return new Worker("Worker");
	}

	@ModelAttribute(value = "manager")
	public Manager newEntityManager() {
		return new Manager("Admin");
	}

	@ModelAttribute(value = "boss")
	public Boss newEntityBoss() {
		return new Boss("Boss");
	}

	@ModelAttribute(value = "position")
	public Position newEntityPosition() {
		return new Position();
	}


	@Autowired
	private PropertyService propertyService;

	@Autowired
	private WorkerService workerService;

	@Autowired
	private PositionService positionService;

	@RequestMapping(value = {"/worker"}, method = RequestMethod.GET)
	public ModelAndView getAllWorker(ModelAndView model) {
		List<Worker> workerList = workerService.listAllWorker();
		List<Manager> managerList = workerService.listAllManager();
		List<Boss> bossList = workerService.listAllBoss();
		List<Position> positionList = positionService.listAllPosition();
		List<Position> allPosManager = positionService.listAllPosition();
		for (Manager m : managerList) {
			allPosManager.removeAll(m.getAllPosition());
		}
		List<Position> allPosBoss = positionService.listAllPosition();
		for (Boss b : bossList) {
			allPosBoss.removeAll(b.getAllPosition());
		}
		List<Position> allPosWorker = positionService.listAllPosition();
		for (Worker w : workerList) {
			if (w.getActionForm().equalsIgnoreCase("Worker")) {
				allPosWorker.removeAll(w.getAllPosition());
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
	public void addWorker(HttpServletResponse response, Worker worker,
						  @RequestParam(name = "passwordWorkerToAdmin", required = false) String passwordWorkerToAdmin,
						  @RequestParam(name = "passwordWorkerToBoss", required = false) String passwordWorkerToBoss,
						  @RequestParam(name = "submitNewPasswordAdmin", required = false) String submitNewPasswordAdmin,
						  @RequestParam(name = "adminPositionId", required = false) Long adminPositionId,
						  @RequestParam(name = "bossPositionId", required = false) Long bossPositionId) throws IOException, URISyntaxException {
		if (adminPositionId == null && bossPositionId == null) {
			workerService.addWorker(worker);
		} else if (!passwordWorkerToAdmin.equals(submitNewPasswordAdmin)) {
			response.sendRedirect("/worker");
		} else if (adminPositionId != null && bossPositionId == null) {
			workerService.castWorkerToManager(worker, passwordWorkerToAdmin, adminPositionId);
		} else if (adminPositionId == null) {
			workerService.castWorkerToBoss(worker, passwordWorkerToBoss, bossPositionId);
		}
		response.sendRedirect("/worker");
	}

	@RequestMapping(value = {"worker/addManager"}, method = RequestMethod.POST)
	public void addManager(HttpServletResponse response, Manager manager,
						   @RequestParam(name = "bossPositionId", required = false) Long bossPositionId,
						   @RequestParam(name = "submitAdminPassword", required = false) String submitAdminPassword) throws IOException {
		if (!manager.getPassword().equals(submitAdminPassword)) {
			response.sendRedirect("/worker");
		} else if (bossPositionId == null) {
			workerService.addManager(manager);
		} else {
			workerService.castManagerToBoss(manager, bossPositionId);
		}
		response.sendRedirect("/worker");
	}

	@RequestMapping(value = {"worker/addBoss"}, method = RequestMethod.POST)
	public void addBoss(HttpServletResponse response, Boss boss,
						@RequestParam(name = "submitBossPassword", required = false) String submitBossPassword) throws IOException {
		if (!boss.getPassword().equals(submitBossPassword)) {
			response.sendRedirect("/worker");
		} else {
			workerService.addBoss(boss);
		}
		response.sendRedirect("/worker");
	}

	@RequestMapping(value = {"worker/editWorker"}, method = RequestMethod.POST)
	public void editWorker(HttpServletResponse response, Worker worker,
						   @RequestParam(name = "adminPositionId", required = false) Long adminPositionId,
						   @RequestParam(name = "bossPositionId", required = false) Long bossPositionId,
						   @RequestParam(name = "passwordEditWorkerToAdmin", required = false) String password) throws IOException {
		workerService.editWorker(worker, adminPositionId, bossPositionId, password);
		response.sendRedirect("/worker");
	}

	@RequestMapping(value = {"worker/editManager"}, method = RequestMethod.POST)
	public void editManager(HttpServletResponse response, Manager manager,
							@RequestParam(name = "adminPositionId", required = false) Long adminPositionId,
							@RequestParam(name = "bossPositionId", required = false) Long bossPositionId) throws IOException {
		workerService.editManager(manager, adminPositionId, bossPositionId);
		response.sendRedirect("/worker");
	}

	@RequestMapping(value = {"worker/editBoss"}, method = RequestMethod.POST)
	public void editBoss(HttpServletResponse response, Boss boss,
						 @RequestParam(name = "bossPositionId", required = false) Long bossPositionId,
						 @RequestParam(name = "adminPositionId", required = false) Long adminPositionId) throws IOException {
		workerService.editBoss(boss, bossPositionId, adminPositionId);
		response.sendRedirect("/worker");
	}

	@RequestMapping(value = {"worker/deleteWorker/{id}"}, method = RequestMethod.GET)
	public void deleteWorker(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
		Worker worker = workerService.findWorkerById(id);
		workerService.deleteWorker(worker);
		response.sendRedirect("/worker");
	}

	@RequestMapping(value = {"worker/addPosition"}, method = RequestMethod.POST)
	public void addPosition(HttpServletResponse response, Position position) throws IOException {
		positionService.addPosition(position);
		response.sendRedirect("/worker");
	}

	@RequestMapping(value = {"worker/editPosition"}, method = RequestMethod.POST)
	public void editPosition(HttpServletResponse response, Position position) throws IOException {
		positionService.updatePosition(position);
		response.sendRedirect("/worker");
	}

	@RequestMapping(value = {"worker/deletePosition/{id}"}, method = RequestMethod.GET)
	public void deletePosition(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
		positionService.deletePosition(id);
		response.sendRedirect("/worker");
	}
}

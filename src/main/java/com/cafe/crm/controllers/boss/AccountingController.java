package com.cafe.crm.controllers.boss;

import com.cafe.crm.models.property.PropertyWrapper;
import com.cafe.crm.models.worker.Boss;
import com.cafe.crm.models.worker.Manager;
import com.cafe.crm.models.worker.Worker;
import com.cafe.crm.service_abstract.property.PropertyService;
import com.cafe.crm.service_impl.worker.WorkerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@Controller
public class AccountingController {

	@ModelAttribute(value = "worker")
	public Worker newEntityWorker() {
		return new Worker();
	}

	@ModelAttribute(value = "manager")
	public Manager newEntityManager() {
		return new Manager();
	}

	@ModelAttribute(value = "boss")
	public Boss newEntityBoss() {
		return new Boss();
	}

	@Autowired
	private WorkerServiceImpl workerService;

	@Autowired
	private PropertyService propertyService;

	@ModelAttribute(value = "wrapper")
	public PropertyWrapper addClass() {
		PropertyWrapper PropertyWrapper = new PropertyWrapper();
		PropertyWrapper.setProperties(propertyService.findAll());
		return PropertyWrapper;
	}

	@RequestMapping(value = {"/worker"}, method = RequestMethod.GET)
	public ModelAndView getAllWorker(ModelAndView model) {
		List<Worker> workerList = workerService.listAllWorker();
		List<Manager> managerList = workerService.listAllManager();
		List<Boss> bossList = workerService.listAllBoss();
		bossList.sort(Comparator.comparing(o -> (o.getLastName())));
		workerList.sort(Comparator.comparing(o -> (o.getLastName())));
		managerList.sort(Comparator.comparing(o -> (o.getLastName())));
		model.addObject("managerList", managerList);
		model.addObject("workerList", workerList);
		model.addObject("bossList", bossList);
		model.setViewName("pages/accountingPage");
		return model;
	}

	@RequestMapping(value = {"worker/addWorker"}, method = RequestMethod.POST)
	public void addWorker(HttpServletResponse response, Worker worker) throws IOException {
		workerService.saveWorker(worker);
		response.sendRedirect("/worker");
	}

	@RequestMapping(value = {"worker/addManager"}, method = RequestMethod.POST)
	public void addManager(HttpServletResponse response, Manager manager) throws IOException {
		workerService.saveManager(manager);
		response.sendRedirect("/worker");
	}

	@RequestMapping(value = {"worker/addBoss"}, method = RequestMethod.POST)
	public void addBoss(HttpServletResponse response, Boss boss) throws IOException {
		workerService.saveBoss(boss);
		response.sendRedirect("/worker");
	}

	@RequestMapping(value = {"worker/editWorker"}, method = RequestMethod.POST)
	public void editWorker(HttpServletResponse response, Worker worker, Manager manager, Boss boss) throws IOException {
		if (worker.getPosition().equals("Управляющий")) {
			workerService.saveManager(manager);
		} else if (worker.getPosition().equals("Владелец")) {
			workerService.saveBoss(boss);
		} else {
			workerService.saveWorker(worker);
		}
		response.sendRedirect("/worker");
	}

	@RequestMapping(value = {"worker/deleteWorker/{id}"}, method = RequestMethod.GET)
	public void deleteWorker(HttpServletResponse response, @PathVariable("id") Long id) throws IOException {
		workerService.delete(id);
		response.sendRedirect("/worker");
	}
}

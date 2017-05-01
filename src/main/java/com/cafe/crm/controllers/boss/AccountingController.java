package com.cafe.crm.controllers.boss;


import com.cafe.crm.dao.ManagerRepository;
import com.cafe.crm.dao.WorkerRepository;
import com.cafe.crm.models.worker.Manager;
import com.cafe.crm.models.worker.Worker;
import com.cafe.crm.service_impl.counting.CountingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class AccountingController {


    @Autowired
    private CountingServiceImpl countingService;
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private ManagerRepository managerRepository;


    @RequestMapping(value = {"/accounting"}, method = RequestMethod.GET)
    public ModelAndView getAllWorker(ModelMap modelMap) {
        countingService.countShift();
        countingService.totalSalary();
        List<Worker> workerList = workerRepository.findAll();
        List<Manager> managerList = managerRepository.findAll();
        modelMap.addAttribute("managerList", managerList);
        modelMap.addAttribute("workerList", workerList);
        return new ModelAndView("pages/accountingPage");
    }

    @RequestMapping(value = {"/accounting"}, method = RequestMethod.POST)
    public ModelAndView getAllWorkerPost(ModelMap modelMap) {
        return new ModelAndView("pages/bossPage");
    }


    @RequestMapping(value = {"accounting/addWorker"}, method = RequestMethod.POST)
    public void addWorkerPost(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String position = request.getParameter("position");
        Long shiftSalary = Long.parseLong(request.getParameter("shiftSalary"));
        Worker newWorker = new Worker(firstName, lastName, position, shiftSalary);
        workerRepository.saveAndFlush(newWorker);
        response.sendRedirect("/accounting");
    }

    @RequestMapping(value = {"accounting/addManager"}, method = RequestMethod.POST)
    public void addManagerPost(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String position = "Управляющий";
        Long shiftSalary = Long.parseLong(request.getParameter("shiftSalary"));
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Manager newManager = new Manager(firstName, lastName, position, shiftSalary, login, password);
        managerRepository.saveAndFlush(newManager);
        response.sendRedirect("/accounting");
    }

    @RequestMapping(value = {"accounting/addWorker"}, method = RequestMethod.GET)
    public void addWorkerGet(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/accounting");
    }


    @RequestMapping(value = {"accounting/editWorker"}, method = RequestMethod.POST)
    public void editWorkerPost(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String position = request.getParameter("position");
        Long shiftSalary = Long.parseLong(request.getParameter("shiftSalary"));
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (position.equals("Управляющий")) {
            Manager newManager = new Manager(id, firstName, lastName, login, password, position, shiftSalary);
            managerRepository.saveAndFlush(newManager);
            System.out.println(newManager);
        } else {
            Worker newWorker = new Worker(id, firstName, lastName, position, shiftSalary);
            workerRepository.saveAndFlush(newWorker);
            System.out.println(newWorker);
        }
        response.sendRedirect("/accounting");
    }

    @RequestMapping(value = {"accounting/editWorker"}, method = RequestMethod.GET)
    public void editWorkerGet(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("/accounting");
    }


    @RequestMapping(value = {"accounting/deleteWorker"}, method = RequestMethod.POST)
    public ModelAndView deleteWorkerPost(HttpServletRequest request) throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        workerRepository.delete(id);
        return new ModelAndView("redirect:/accounting");
    }
}

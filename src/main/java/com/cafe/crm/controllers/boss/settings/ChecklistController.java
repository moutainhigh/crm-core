package com.cafe.crm.controllers.boss.settings;

import com.cafe.crm.models.checklist.Checklist;
import com.cafe.crm.services.interfaces.checklist.ChecklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/boss/settings/checklists")
public class ChecklistController {

	private final ChecklistService checklistService;

	@Autowired
	public ChecklistController(ChecklistService checklistService) {
		this.checklistService = checklistService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showChecklistSettingsPage(Model model) {
		model.addAttribute("openChecklist", checklistService.getAllForOpenShift());
		model.addAttribute("closeChecklist", checklistService.getAllForCloseShift());
		model.addAttribute("checklistForm", new Checklist());
		return "settingPages/checklistSettingPage";
	}

	@RequestMapping(value = "/addOpen", method = RequestMethod.POST)
	public String addToOpenChecklist(@RequestParam(name = "text") String text) {
		checklistService.saveChecklistOnOpenShift(text);
		return "redirect:/boss/settings/checklists";
	}

	@RequestMapping(value = "/addClose", method = RequestMethod.POST)
	public String addToCloseChecklist(@RequestParam(name = "text") String text) {
		checklistService.saveChecklistOnCloseShift(text);
		return "redirect:/boss/settings/checklists";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateChecklist(Checklist checklist) {
		checklistService.update(checklist);

		return "redirect:/boss/settings/checklists";
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	public String deleteChecklist(@PathVariable("id") Long id) {
		checklistService.delete(id);
		return "redirect:/boss/settings/checklists";
	}
}

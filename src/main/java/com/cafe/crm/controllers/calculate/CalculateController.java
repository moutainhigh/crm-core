package com.cafe.crm.controllers.calculate;

import com.cafe.crm.exceptions.debt.DebtDataException;
import com.cafe.crm.models.client.Calculate;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.client.TimerOfPause;
import com.cafe.crm.models.discount.Discount;
import com.cafe.crm.models.shift.Shift;
import com.cafe.crm.services.interfaces.board.BoardService;
import com.cafe.crm.services.interfaces.calculate.CalculateControllerService;
import com.cafe.crm.services.interfaces.calculate.CalculateService;
import com.cafe.crm.services.interfaces.calculate.TimerOfPauseService;
import com.cafe.crm.services.interfaces.checklist.ChecklistService;
import com.cafe.crm.services.interfaces.client.ClientService;
import com.cafe.crm.services.interfaces.discount.DiscountService;
import com.cafe.crm.services.interfaces.menu.CategoriesService;
import com.cafe.crm.services.interfaces.menu.ProductService;
import com.cafe.crm.services.interfaces.shift.ShiftService;
import com.cafe.crm.utils.TimeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.temporal.ChronoUnit;
import java.util.List;


@Controller
@RequestMapping("/manager")
public class CalculateController {

	private final ClientService clientService;
	private final CalculateControllerService calculateControllerService;
	private final CalculateService calculateService;
	private final BoardService boardService;
	private final ProductService productService;
	private final ShiftService shiftService;
	private final DiscountService discountService;
	private final CategoriesService categoriesService;
	private final TimeManager timeManager;
	private final TimerOfPauseService timerOfPauseService;
	private final ChecklistService checklistService;

	@Autowired
	public CalculateController(TimeManager timeManager, ProductService productService, ClientService clientService, CategoriesService categoriesService, CalculateService calculateService, ShiftService shiftService, BoardService boardService, DiscountService discountService, CalculateControllerService calculateControllerService, TimerOfPauseService timerOfPauseService, ChecklistService checklistService) {
		this.timeManager = timeManager;
		this.productService = productService;
		this.clientService = clientService;
		this.categoriesService = categoriesService;
		this.calculateService = calculateService;
		this.shiftService = shiftService;
		this.boardService = boardService;
		this.discountService = discountService;
		this.calculateControllerService = calculateControllerService;
		this.timerOfPauseService = timerOfPauseService;
		this.checklistService = checklistService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView manager() {
		Shift shift = shiftService.getLast(); //текущая смена
		ModelAndView modelAndView = new ModelAndView("client/clients");
		modelAndView.addObject("listMenu", categoriesService.sortProductListAndGetAllCategories());
		modelAndView.addObject("listProduct", productService.findAllOrderByRatingDesc());
		modelAndView.addObject("listCalculate", calculateService.getAllOpen());
		modelAndView.addObject("listBoard", boardService.getAllOpen());
		modelAndView.addObject("listDiscounts", discountService.getAllOpen());
		modelAndView.addObject("closeChecklist", checklistService.getAllForCloseShift());
		return modelAndView;
	}

	@RequestMapping(value = {"/pause"}, method = RequestMethod.POST)
	public String pause(@RequestParam("id") Long idCalculate) {
		TimerOfPause timer;
		Calculate calculate = calculateService.getOne(idCalculate);
		List<Client> clients;
		if (calculate != null) {
			clients = calculate.getClient();
			if (!clients.isEmpty()) {
				for (Client client : clients) {
					timer = timerOfPauseService.findTimerOfPauseByIdOfClient(client.getId());
					if (client.isPause()) {                                   //unset pause
						Long timeOfPastPauses = timer.getWholeTimePause();
						timer.setEndTime(timeManager.getDateTime().withSecond(0).withNano(0));
						long fullPauseTime = ChronoUnit.MINUTES.between(timer.getStartTime(), timer.getEndTime());
						if (timeOfPastPauses != null) {
							fullPauseTime += timeOfPastPauses;
						}
						timer.setWholeTimePause(fullPauseTime);
						client.setPause(false);
						calculate.setPause(false);
					} else {                                                     //set pause
						if (timer == null) {                                    // if this first pause on this calc
							timer = new TimerOfPause();
							timer.setIdOfClient(client.getId());
							timer.setStartTime(timeManager.getDateTime().withSecond(0).withNano(0));
							client.setPause(true);
							client.setPausedIndex(true);
							calculate.setPause(true);
						} else {
							timer.setStartTime(timeManager.getDateTime().withSecond(0).withNano(0));      // if this second or more pause on this calc
							client.setPause(true);
							calculate.setPause(true);
						}
					}
					timerOfPauseService.save(timer);
					clientService.save(client);
				}
			}
		}
		calculateService.save(calculate);

		return "redirect:/manager";
	}

	@RequestMapping(value = "/edit-client-time-start", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> editClientTimeStart(@RequestParam("clientId") Long clientId,
												 @RequestParam("hours") int hours,
												 @RequestParam("minutes") int minutes) {
		boolean successfully = clientService.updateClientTime(clientId, hours, minutes);

		return successfully ? ResponseEntity.ok("ok") : ResponseEntity.badRequest().body("bad");
	}

	@RequestMapping(value = {"/add-calculate"}, method = RequestMethod.POST)
	public String createCalculate(@RequestParam("boardId") Long id,
								  @RequestParam("number") Double number,
								  @RequestParam("description") String description) {
		calculateControllerService.createCalculate(id, number.longValue(), description);
		return "redirect:/manager";
	}

	@RequestMapping(value = {"/card/add-card-on-client"}, method = RequestMethod.POST)
	@ResponseBody
	public Long addCardOnClient(@RequestParam("calculateId") Long calculateId,
								@RequestParam("clientId") Long clientId,
								@RequestParam("cardId") Long cardId) {
		return calculateControllerService.addCardOnClient(calculateId, clientId, cardId);
	}

	@RequestMapping(value = {"/refresh-board"}, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void refreshBoard(@RequestParam("boardId") Long idB,
							 @RequestParam("calculateId") Long idC) {
		calculateControllerService.refreshBoard(idC, idB);
	}

	@RequestMapping(value = {"/add-client"}, method = RequestMethod.POST)
	public String addClient(@RequestParam("calculateId") Long id,
							@RequestParam("number") Double number,
							@RequestParam("description") String description) {
		calculateControllerService.addClient(id, number.longValue(), description);
		return "redirect:/manager";
	}

	@RequestMapping(value = {"/update-fields-client"}, method = RequestMethod.POST)
	@ResponseBody
	public String updateClientFields(@RequestParam("clientId") Long clientId,
									 @RequestParam("discountId") Long discountId,
									 @RequestParam(name = "payWithCard", required = false) Double payWithCard,
									 @RequestParam("description") String description) {
		Client client = clientService.getOne(clientId);
		if (discountId == -1) {
			client.setDiscount(0L);
			client.setDiscountObj(null);
		} else {
			Discount discount = discountService.getOne(discountId);
			client.setDiscount(discount.getDiscount());
			client.setDiscountObj(discount);
		}
		Double newPayWithCard = (payWithCard != null) ? payWithCard : client.getPayWithCard();
		client.setPayWithCard(newPayWithCard);
		client.setDescription(description);
		clientService.save(client);
		return description;
	}

	@RequestMapping(value = {"/calculate-price"}, method = RequestMethod.POST)
	@ResponseBody
	public List<Client> calculatePrice() {
		return calculateControllerService.calculatePrice();
	}

	@RequestMapping(value = {"/calculate-price-on-calculate"}, method = RequestMethod.POST)
	@ResponseBody
	public List<Client> calculatePriceOnCalculate(@RequestParam("calculateId") Long calculateId) {
		return calculateControllerService.calculatePrice(calculateId);
	}

	@RequestMapping(value = {"/delete-clients"}, method = RequestMethod.POST)
	public String deleteClients(@RequestParam(name = "clientsId", required = false) long[] clientsId,
								@RequestParam("calculateId") Long calculateId) {
		calculateControllerService.deleteClients(clientsId, calculateId);
		return "redirect:/manager";
	}

	@RequestMapping(value = {"/output-clients"}, method = RequestMethod.POST)
	@ResponseBody
	public List<Client> outputClients(@RequestParam(name = "clientsId", required = false) long[] clientsId) {
		return calculateControllerService.outputClients(clientsId);
	}

	@RequestMapping(value = {"/close-client"}, method = RequestMethod.POST)
	public String closeClient(@RequestParam(name = "clientsId", required = false) long[] clientsId,
							  @RequestParam("calculateId") Long calculateId) {
		calculateControllerService.closeClient(clientsId, calculateId);
		return "redirect:/manager";
	}

	@RequestMapping(value = {"/close-client-debt"}, method = RequestMethod.POST)
	public ResponseEntity closeClientDebt(@RequestParam(name = "clientsId[]") long[] clientsId,
							  @RequestParam("calculateId") Long calculateId,
								  @RequestParam("debtorName") String debtorName,
	                              @RequestParam(value = "paidAmount", required = false) Double paidAmount) {
		calculateControllerService.closeClientDebt(debtorName, clientsId, calculateId, paidAmount);
		return ResponseEntity.ok("Долг добавлен!");
	}

	@RequestMapping(value = {"/change-round-state"}, method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changeRoundState(@RequestParam("calculateId") Long calculateId) {
		Calculate calculate = calculateService.getAllOpenOnCalculate(calculateId);
		if (calculate.isRoundState()) {
			calculate.setRoundState(false);
		} else {
			calculate.setRoundState(true);
		}
		calculateService.save(calculate);
	}

	@ExceptionHandler(value = DebtDataException.class)
	public ResponseEntity<?> handleUserUpdateException(DebtDataException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}



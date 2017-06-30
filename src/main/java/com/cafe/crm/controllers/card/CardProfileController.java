package com.cafe.crm.controllers.card;

import com.cafe.crm.models.card.Card;
import com.cafe.crm.service_abstract.boardService.BoardService;
import com.cafe.crm.service_abstract.calculateService.CalculateControllerService;
import com.cafe.crm.service_abstract.calculateService.CalculateService;
import com.cafe.crm.service_abstract.cardService.CardControllerService;
import com.cafe.crm.service_abstract.cardService.CardService;
import com.cafe.crm.service_abstract.property.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Controller
@RequestMapping("/manager")
public class CardProfileController {

	@Autowired
	private CardService cardService;

	@Autowired
	private CalculateService calculateService;

	@Autowired
	private CardControllerService cardControllerService;

	@Autowired
	private BoardService boardService;

	@Autowired
	private CalculateControllerService calculateControllerService;

	@Autowired
	private PropertyService propertyService;

	@RequestMapping(value = {"/card/{id}"}, method = RequestMethod.GET)
	public ModelAndView getCard(@PathVariable Long id) {

		ModelAndView modelAndView = new ModelAndView("card");
		modelAndView.addObject("card", cardService.getOne(id));
		modelAndView.addObject("listCalculate", calculateService.getAllOpen());
		modelAndView.addObject("boards", boardService.getAll());
		return modelAndView;
	}

	@RequestMapping(value = {"/add-card-to-calculate"}, method = RequestMethod.POST)
	public String addCardToCalculate(@RequestParam("idCard") Long idCard,
									 @RequestParam("idCalculate") Long idCalculate) {
		cardControllerService.addCardToCalculate(idCard, idCalculate);
		return "redirect:/manager/card/" + idCard;
	}

	@RequestMapping(value = {"/card/edit"}, method = RequestMethod.POST)
	public String editCard(@RequestParam("idCard") Long idCard,
						   @RequestParam("name") String name,
						   @RequestParam("phone") String phone,
						   @RequestParam("email") String email,
						   HttpServletRequest request) {
		Card card = cardService.getOne(idCard);
		if (card != null) {
			card.setName(name);
			card.setPhoneNumber(phone);
			card.setEmail(email);
			cardService.save(card);
		}
		String referrer = request.getHeader("Referer");
		return "redirect:" + referrer;
	}

	@RequestMapping(value = {"/card/addMoney"}, method = RequestMethod.POST)
	public String addMoneyToBalance(@RequestParam("id") Long idCard,
									@RequestParam("money") Long money,
									HttpServletRequest request) {
		Card card = cardService.getOne(idCard);
		if (card != null) {
			Long balance = card.getBalance();
			card.setBalance(balance + money);
			cardService.save(card);
		}
		String referrer = request.getHeader("Referer");
		return "redirect:" + referrer;
	}

	@RequestMapping(value = {"/add-calculate-with-card"}, method = RequestMethod.POST)
	public String createCalculate(@RequestParam("boardId") Long id,
								  @RequestParam("number") Double number,
								  @RequestParam("description") String description,
								  @RequestParam("idCard") Long idCard,
								  HttpServletRequest request) {
		calculateControllerService.createCalculateWithCard(id, number.longValue(), description, idCard);
		String referrer = request.getHeader("Referer");
		return "redirect:" + referrer;
	}

	@RequestMapping(value = {"/uploadPhoto"}, method = RequestMethod.POST)
	public String uploadPhoto(@RequestParam("file") MultipartFile file,
							  @RequestParam("id") Long idCard,
							  HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		Card card = cardService.getOne(idCard);
		try {
			byte[] arra = file.getBytes();
			card.setPhoto(arra);

		} catch (IOException e) {
			return "redirect:" + referer;
		}
		cardService.save(card);
		return "redirect:" + referer;
	}

	// controller for card's avatar
	@RequestMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) throws IOException {

		byte[] imageContent = cardService.getOne(id).getPhoto();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
	}

	@RequestMapping(value = {"/card/registration"}, method = RequestMethod.POST)
	public String registrationNewUser(@RequestParam("idCard") Long idCard,
									  @RequestParam("name") String name,
									  @RequestParam("phone") String phone,
									  @RequestParam("email") String email,
									  @RequestParam(value = "invited", required = false) Long invited) {
		Card card = cardService.getOne(idCard);
		if (card != null) {
			card.setName(name);
			card.setPhoneNumber(phone);
			card.setEmail(email);
			card.setActivatedCard(true);
			if (invited != null) {
				Card ourInvited = cardService.getOne(invited);
				card.setWhoInvitedMe(invited);
				card.setBalance(propertyService.getOne(3L).getValue());                    // referral bonus
				ourInvited.getIdMyInvitedUsers().add(idCard);
				cardService.save(ourInvited);
			}
			cardService.save(card);
		}
		return "redirect:/manager/card/" + idCard;
	}

	@RequestMapping(value = {"/card/checkUser"}, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> checkInvited(@RequestParam("searchParam") String searchParam, @RequestParam("ourId") Long ourId) {
		Card card = cardService.checkWhoInvitedMe(searchParam);
		if (card != null && card.getId() != ourId) {

			return ResponseEntity.status(HttpStatus.OK).body(card);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
}

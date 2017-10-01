package com.cafe.crm.utils.converters.client;

import com.cafe.crm.dto.ClientDTO;
import com.cafe.crm.models.client.Client;
import com.cafe.crm.models.client.LayerProduct;
import com.cafe.crm.utils.RoundUpper;

import java.util.ArrayList;
import java.util.List;

public class ClientConverter {
	public static ClientDTO convertClientToClientDTOWithoutRound(Client client) {
		ClientDTO clientDTO = getFieldsWithMenu(client);
		clientDTO.setAllDirtyPrice(recalculationAllPriceWithoutRound(clientDTO));
		return clientDTO;
	}

	public static ClientDTO convertClientToClientDTOWithRound(Client client) {
		ClientDTO clientDTO = getFieldsWithMenu(client);
		clientDTO.setAllDirtyPrice(recalculationAllPriceWithRound(clientDTO));
		return clientDTO;
	}

	private static ClientDTO getFieldsWithMenu(Client client) {
		ClientDTO clientDTO = getFields(client);
		clientDTO.setOtherPriceMenu(getOtherMenuPrice(client));
		clientDTO.setDirtyPriceMenu(getDirtyMenuPrice(client));
		return clientDTO;
	}


	private static double getDirtyMenuPrice(Client client) {
		double dirtyPriceMenu = 0D;
		for (LayerProduct product : client.getLayerProducts()) {
			if (product.isDirtyProfit())
				dirtyPriceMenu += product.getCost();
		}
		return dirtyPriceMenu;
	}

	private static double getOtherMenuPrice(Client client) {
		double otherPriceMenu = 0D;
		for (LayerProduct product : client.getLayerProducts()) {
			if (!product.isDirtyProfit())
				otherPriceMenu += product.getCost();
		}
		return otherPriceMenu;
	}

	private static ClientDTO getFields(Client client) {
		ClientDTO clientDTO = new ClientDTO();

		clientDTO.setId(client.getId());
		clientDTO.setDescription(client.getDescription());
		clientDTO.setPause(client.isPause());
		clientDTO.setTimeStart(client.getTimeStart());
		clientDTO.setAllPrice(client.getAllPrice());
		clientDTO.setDiscount(client.getDiscount());
		clientDTO.setDiscount(client.getDiscount());
		clientDTO.setDiscountWithCard(client.getDiscountWithCard());
		clientDTO.setCache(client.getCache());
		clientDTO.setPassedTime(client.getPassedTime());
		clientDTO.setPriceMenu(client.getPriceMenu());
		clientDTO.setPriceTime(client.getPriceTime());
		clientDTO.setPayWithCard(client.getPayWithCard());
		clientDTO.setLayerProducts(client.getLayerProducts());
		clientDTO.setCard(client.getCard());
		clientDTO.setDiscountObj(client.getDiscountObj());
		return clientDTO;
	}

	public static List<ClientDTO> convertListClientsToDTOWithoutRound(List<Client> clients) {
		List<ClientDTO> result = new ArrayList<>();
		for (Client client : clients) {
			result.add(convertClientToClientDTOWithoutRound(client));
		}
		return result;
	}

	public static List<ClientDTO> convertListClientsToDTOWithRound(List<Client> clients) {
		List<ClientDTO> result = new ArrayList<>();
		for (Client client : clients) {
			result.add(convertClientToClientDTOWithRound(client));
		}
		return result;
	}

	private static double recalculationAllPriceWithoutRound(ClientDTO clientDTO) {
		return clientDTO.getPriceTime() + clientDTO.getDirtyPriceMenu() - clientDTO.getPayWithCard();
	}

	private static double recalculationAllPriceWithRound(ClientDTO clientDTO) {
		double allDirtyPrice = clientDTO.getPriceTime() + clientDTO.getDirtyPriceMenu() - clientDTO.getPayWithCard();
		return RoundUpper.roundDouble(allDirtyPrice);
	}
}

package com.cafe.crm.utils.converters.position;


import com.cafe.crm.dto.PositionDTO;
import com.cafe.crm.models.user.Position;

import java.util.ArrayList;
import java.util.List;

public class PositionConverter {

	public static List<PositionDTO> convertToListPositionDTO(List<Position> positions) {
		List<PositionDTO> result = new ArrayList<>();

		for (Position position : positions) {
			result.add(convertToPositionDTO(position));
		}
		return result;
	}

	public static PositionDTO convertToPositionDTO(Position position) {
		PositionDTO positionDTO = new PositionDTO();

		positionDTO.setId(position.getId());
		positionDTO.setName(position.getName());
		positionDTO.setPercentageOfSales(position.getPercentageOfSales());
		positionDTO.setIsPositionUsePercentOfSales(position.isPositionUsePercentOfSales());

		return positionDTO;
	}
}

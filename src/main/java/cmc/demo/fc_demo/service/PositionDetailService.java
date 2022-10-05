package cmc.demo.fc_demo.service;

import cmc.demo.fc_demo.dto.response.PositionDetailResponse;
import cmc.demo.fc_demo.dto.response.PositionResponse;

import java.util.List;

public interface PositionDetailService {
	List<PositionDetailResponse> getAllActivePositionDetail();

	List<PositionDetailResponse> getAllActivePositionDetailByPositionId(Long positionId);

	PositionDetailResponse findById(Long id);
}

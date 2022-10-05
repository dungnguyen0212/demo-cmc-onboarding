package cmc.demo.fc_demo.service;

import cmc.demo.fc_demo.dto.response.PositionResponse;

import java.util.List;

public interface PositionService {
	List<PositionResponse> getAllActivePosition();

	PositionResponse findById(Long id);
}

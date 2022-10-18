package cmc.demo.fc_demo.service;

import cmc.demo.fc_demo.dto.response.ConditionTypeResponse;
import cmc.demo.fc_demo.dto.response.CountryResponse;

import java.util.List;

public interface ConditionTypeService {
	List<ConditionTypeResponse> getAllConditions();

	ConditionTypeResponse findById(Long id);
}

package cmc.demo.fc_demo.service.impl;

import cmc.demo.fc_demo.dto.response.ConditionTypeResponse;
import cmc.demo.fc_demo.dto.response.CountryResponse;
import cmc.demo.fc_demo.model.ConditionType;
import cmc.demo.fc_demo.model.Country;
import cmc.demo.fc_demo.repository.ConditionTypeRepository;
import cmc.demo.fc_demo.service.ConditionTypeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConditionTypeServiceImpl implements ConditionTypeService {
	@Autowired
	private ConditionTypeRepository conditionTypeRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<ConditionTypeResponse> getAllConditions() {
		return conditionTypeRepository.findAll().stream().map(
				c -> modelMapper.map(c, ConditionTypeResponse.class)).collect(Collectors.toList());
	}

	@Override
	public ConditionTypeResponse findById(Long id) {
		if (Objects.isNull(id)) {
			return null;
		}

		ConditionType conditionType = conditionTypeRepository.findById(id)
				.orElse(null);
		if (Objects.nonNull(conditionType)) {
			return modelMapper.map(conditionType, ConditionTypeResponse.class);
		} else {
			return null;
		}
	}
}

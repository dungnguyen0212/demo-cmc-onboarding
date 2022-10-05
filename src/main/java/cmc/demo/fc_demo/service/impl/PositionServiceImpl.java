package cmc.demo.fc_demo.service.impl;

import cmc.demo.fc_demo.dto.response.PositionDetailResponse;
import cmc.demo.fc_demo.dto.response.PositionResponse;
import cmc.demo.fc_demo.model.Position;
import cmc.demo.fc_demo.model.PositionDetail;
import cmc.demo.fc_demo.repository.PositionRepository;
import cmc.demo.fc_demo.service.PositionService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PositionServiceImpl implements PositionService {
	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<PositionResponse> getAllActivePosition() {
		return positionRepository.findAllByIsActive(true).stream().map(
				p -> modelMapper.map(p, PositionResponse.class)).collect(Collectors.toList());
	}

	@Override
	public PositionResponse findById(Long id) {
		if (Objects.isNull(id)) {
			return null;
		}

		Position position = positionRepository.findById(id)
				.orElse(null);
		if (Objects.nonNull(position)) {
			return modelMapper.map(position, PositionResponse.class);
		} else {
			return null;
		}
	}
}

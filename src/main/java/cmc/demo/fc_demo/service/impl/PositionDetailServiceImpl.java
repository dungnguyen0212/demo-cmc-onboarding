package cmc.demo.fc_demo.service.impl;

import cmc.demo.fc_demo.dto.response.PositionDetailResponse;
import cmc.demo.fc_demo.dto.response.PositionResponse;
import cmc.demo.fc_demo.model.Position;
import cmc.demo.fc_demo.model.PositionDetail;
import cmc.demo.fc_demo.repository.PositionDetailRepository;
import cmc.demo.fc_demo.repository.PositionRepository;
import cmc.demo.fc_demo.service.PositionDetailService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PositionDetailServiceImpl implements PositionDetailService {
	@Autowired
	private PositionDetailRepository positionDetailRepository;

	@Autowired
	private PositionRepository positionRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<PositionDetailResponse> getAllActivePositionDetail() {
		return positionDetailRepository.findAllByIsActive(true).stream()
				.map(transformToPositionDetailResponse()).collect(Collectors.toList());
	}

	@Override
	public List<PositionDetailResponse> getAllActivePositionDetailByPositionId(Long positionId) {
		return positionDetailRepository.findAllByPositionIdAndIsActive(positionId, true).stream()
				.map(transformToPositionDetailResponse()).collect(Collectors.toList());
	}

	@Override
	public PositionDetailResponse findById(Long id) {
		if (Objects.isNull(id)) {
			return null;
		}

		PositionDetail positionDetail = positionDetailRepository.findById(id)
				.orElse(null);
		if (Objects.nonNull(positionDetail)) {
			return transformToPositionDetailResponse().apply(positionDetail);
		} else {
			return null;
		}
	}

	private Function<PositionDetail, PositionDetailResponse> transformToPositionDetailResponse() {
		return positionDetail -> {
			Position position = null;
			if (Objects.nonNull(positionDetail.getPositionId()))
				position = positionRepository.findById(positionDetail.getPositionId()).orElse(null);
			return PositionDetailResponse.builder()
					.id(positionDetail.getId())
					.code(positionDetail.getCode())
					.name(positionDetail.getName())
					.description(positionDetail.getDescription())
					.isActive(positionDetail.getIsActive())
					.positionId(Objects.nonNull(position) ? position.getId() : null)
					.positionName(Objects.nonNull(position) ? position.getName() : null)
					.positionDescription(Objects.nonNull(position) ? position.getDescription() : null)
					.build();
		};
	}
}

package cmc.demo.fc_demo.service.impl;

import cmc.demo.fc_demo.dto.response.PlayerResponse;
import cmc.demo.fc_demo.dto.response.TransferHistoryResponse;
import cmc.demo.fc_demo.model.ChangeCategory;
import cmc.demo.fc_demo.model.TransferHistory;
import cmc.demo.fc_demo.repository.ChangeCategoryRepository;
import cmc.demo.fc_demo.repository.TransferHistoryRepository;
import cmc.demo.fc_demo.service.PlayerService;
import cmc.demo.fc_demo.service.TransferHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransferHistoryServiceImpl implements TransferHistoryService {
	@Autowired
	private TransferHistoryRepository transferHistoryRepository;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private ChangeCategoryRepository changeCategoryRepository;

	@Override
	public List<TransferHistoryResponse> getAllTransferHistory() {
		return transferHistoryRepository.findAllOrderByCreatedAtDesc()
				.stream().map(transformToTransferHistoryResponse()).collect(Collectors.toList());
	}

	private Function<TransferHistory, TransferHistoryResponse> transformToTransferHistoryResponse() {
		return transfer -> {
			PlayerResponse playerResponse = null;
			ChangeCategory changeCategory = null;
			if (Objects.nonNull(transfer.getChangeCategoryId())) {
				changeCategory = changeCategoryRepository.findById(transfer.getChangeCategoryId()).orElse(null);
			}
			if (Objects.nonNull(transfer.getObjectId()))
				playerResponse = playerService.findById(transfer.getObjectId());
			return TransferHistoryResponse.builder()
					.id(transfer.getId())
					.objectId(playerResponse.getId())
					.objectName(playerResponse.getFirstName() + " " + playerResponse.getLastName())
					.marketValue(playerResponse.getMarketValue())
					.changeCategoryId(Objects.nonNull(changeCategory) ? changeCategory.getId() : null)
					.changeCategoryName(Objects.nonNull(changeCategory) ? changeCategory.getName() : null)
					.createdAt(transfer.getCreatedAt())
					.build();
		};
	}

	;

}

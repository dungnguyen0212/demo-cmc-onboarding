package cmc.demo.fc_demo.service;

import cmc.demo.fc_demo.dto.response.TransferHistoryResponse;

import java.util.List;

public interface TransferHistoryService {
	List<TransferHistoryResponse> getAllTransferHistory();
}

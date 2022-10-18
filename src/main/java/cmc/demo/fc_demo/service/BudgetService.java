package cmc.demo.fc_demo.service;

import cmc.demo.fc_demo.dto.response.BudgetResponse;

import java.util.List;

public interface BudgetService {
	List<BudgetResponse> getAllInformationBudget();

	BudgetResponse findById(Long id);

	void changeMoney(Long id, Long changeMoney);
}

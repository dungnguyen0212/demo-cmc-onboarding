package cmc.demo.fc_demo.service;

import cmc.demo.fc_demo.dto.response.BudgetResponse;
import cmc.demo.fc_demo.dto.response.CountryResponse;

import java.util.List;

public interface BudgetService {
	List<BudgetResponse> getAllInformationBudget();

	BudgetResponse findById(Long id);

	BudgetResponse changeMoney(Long id, Long changeMoney);
}

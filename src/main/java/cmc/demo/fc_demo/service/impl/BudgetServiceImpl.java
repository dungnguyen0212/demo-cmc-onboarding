package cmc.demo.fc_demo.service.impl;

import cmc.demo.fc_demo.dto.response.BudgetResponse;
import cmc.demo.fc_demo.model.Budget;
import cmc.demo.fc_demo.repository.BudgetRepository;
import cmc.demo.fc_demo.service.BudgetService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BudgetServiceImpl implements BudgetService {
	@Autowired
	private BudgetRepository budgetRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<BudgetResponse> getAllInformationBudget() {
		return budgetRepository.findAll().stream().map(
				c -> modelMapper.map(c, BudgetResponse.class)).collect(Collectors.toList());
	}

	@Override
	public BudgetResponse findById(Long id) {
		if (Objects.isNull(id)) {
			return null;
		}

		Budget budget = budgetRepository.findById(id)
				.orElse(null);
		if (Objects.nonNull(budget)) {
			return modelMapper.map(budget, BudgetResponse.class);
		} else {
			return null;
		}
	}

	@Override
	public void changeMoney(Long id, Long changeMoney) {
		Budget budget = budgetRepository.findById(id)
				.orElse(null);
		if (Objects.nonNull(budget) && changeMoney <= budget.getValue()) {
			budgetRepository.updateBudget(id, changeMoney);
		}
	}
}

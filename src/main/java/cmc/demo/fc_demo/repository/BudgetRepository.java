package cmc.demo.fc_demo.repository;

import cmc.demo.fc_demo.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
	@Procedure(name = "updateBudget")
	String updateBudget(@Param("p_budget_id") Long p_player_id, @Param("p_change_money") Long p_change_money);
}

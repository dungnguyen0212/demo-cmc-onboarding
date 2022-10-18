package cmc.demo.fc_demo.repository;

import cmc.demo.fc_demo.model.TransferHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {
	@Query(value = "SELECT * FROM transfer_history ORDER BY created_at DESC FETCH NEXT 5 ROWS ONLY",
			nativeQuery = true)
	List<TransferHistory> findAllOrderByCreatedAtDesc();

	@Procedure(name = "addTransferHistory")
	String addTransferHistory(@Param("p_change_category_id") Long changeCategoryId,
							  @Param("p_object_id") Long objectId,
							  @Param("p_market_value") Long marketValue);
}

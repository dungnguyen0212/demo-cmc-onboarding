package cmc.demo.fc_demo.repository;

import cmc.demo.fc_demo.model.PositionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionDetailRepository extends JpaRepository<PositionDetail, Long> {
	List<PositionDetail> findAllByPositionIdAndIsActive(Long positionId, Boolean isActive);

	List<PositionDetail> findAllByIsActive(Boolean isActive);
}

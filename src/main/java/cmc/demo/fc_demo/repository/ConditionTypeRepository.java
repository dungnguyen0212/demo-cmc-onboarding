package cmc.demo.fc_demo.repository;

import cmc.demo.fc_demo.model.ConditionType;
import cmc.demo.fc_demo.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionTypeRepository extends JpaRepository<ConditionType, Long> {
}

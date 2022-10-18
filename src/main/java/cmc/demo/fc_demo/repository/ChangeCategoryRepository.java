package cmc.demo.fc_demo.repository;

import cmc.demo.fc_demo.model.ChangeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeCategoryRepository extends JpaRepository<ChangeCategory, Long> {
}

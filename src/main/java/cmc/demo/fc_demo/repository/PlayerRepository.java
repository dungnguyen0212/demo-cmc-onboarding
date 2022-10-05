package cmc.demo.fc_demo.repository;

import cmc.demo.fc_demo.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
	@Query(value = "SELECT * FROM player " +
			"WHERE (:nameLike IS NULL OR CONCAT(first_name, ' ', last_name) LIKE CONCAT('%', :nameLike, '%')) " +
			"AND (:positionDetailId IS NULL OR position_detail_id = :positionDetailId) " +
			"AND (market_value BETWEEN :marketValueGte AND :marketValueLte) " +
			"AND (annually_salary BETWEEN :annuallySalaryGte AND :annuallySalaryLte) " +
			"AND (:countryId IS NULL OR country_id = :countryId) " +
			"AND (:isInjury IS NULL OR is_injury = :isInjury) " +
			"AND (:clothesNumber IS NULL OR clothes_number = :clothesNumber) " +
			"AND (created_at BETWEEN :createdAtFrom AND :createdAtTo) " +
			"AND is_active = true; "
			, nativeQuery = true)
	List<Player> findAllByFilter(String nameLike, Long positionDetailId, Integer marketValueGte, Integer marketValueLte,
								 Integer annuallySalaryGte, Integer annuallySalaryLte, Long countryId, Boolean isInjury,
								 Integer clothesNumber, Timestamp createdAtFrom, Timestamp createdAtTo);

	List<Player> findAllByClothesNumberAndIsActive(Integer clothesNumber, Boolean isActive);

	List<Player> findAllByIsActive(Boolean isActive);

	@Query(value = "select distinct clothes_number from player where is_active = 1",
	nativeQuery = true)
	List<Integer> getAllExistedClothesNumber();
}

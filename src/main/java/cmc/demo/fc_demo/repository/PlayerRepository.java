package cmc.demo.fc_demo.repository;

import cmc.demo.fc_demo.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
	@Query(value = "SELECT * FROM player " +
			"WHERE (:nameLike IS NULL OR CONCAT(CONCAT(first_name, ' '), last_name) LIKE CONCAT(CONCAT('%', :nameLike), '%')) " +
			"AND (:positionDetailId IS NULL OR position_detail_id = TO_NUMBER(:positionDetailId)) " +
			"AND (market_value BETWEEN :marketValueGte AND :marketValueLte) " +
			"AND (annually_salary BETWEEN :annuallySalaryGte AND :annuallySalaryLte) " +
			"AND (:countryId IS NULL OR country_id = TO_NUMBER(:countryId)) " +
			"AND (:isInjury IS NULL OR is_injury = TO_NUMBER(:isInjury)) " +
			"AND (:clothesNumber IS NULL OR clothes_number = TO_NUMBER(:clothesNumber)) " +
			"AND (:conditionId IS NULL OR condition_id = TO_NUMBER(:conditionId)) " +
			"AND (created_at BETWEEN :createdAtFrom AND :createdAtTo) " +
			"AND is_active = 1 ORDER BY updated_at DESC"
			, nativeQuery = true)
	List<Player> findAllByFilter(String nameLike, Long positionDetailId, Integer marketValueGte, Integer marketValueLte,
								 Integer annuallySalaryGte, Integer annuallySalaryLte, Long countryId, Boolean isInjury,
								 Integer clothesNumber, Timestamp createdAtFrom, Timestamp createdAtTo, Long conditionId);

	List<Player> findAllByClothesNumberAndIsActive(Integer clothesNumber, Boolean isActive);

	Page<Player> findAllByIsActive(Boolean isActive, Pageable pageable);

	List<Player> findAllByIsActive(Boolean isActive);

	@Query(value = "select distinct clothes_number from player where is_active = 1",
			nativeQuery = true)
	List<Integer> getAllExistedClothesNumber();

//	@Query(value = "pkg_player.del_player(:p_player_id)", nativeQuery = true)
//	String deletePlayerDirectly(@Param("p_player_id") Long p_player_id);

	@Procedure(name = "deletePlayer")
	String deletePlayer(@Param("p_player_id") Long p_player_id);

	@Procedure(name = "buyPlayer")
	Map<String, Object> buyPlayer(@Param("p_first_name") String firstName,
								  @Param("p_last_name") String lastName,
								  @Param("p_position_detail_id") Long positionDetailId,
								  @Param("p_annually_salary") Long annuallySalary,
								  @Param("p_market_value") Long marketValue,
								  @Param("p_country_id") Long countryId,
								  @Param("p_date_of_birth") Date dateOfBirth,
								  @Param("p_skill") String skill,
								  @Param("p_clothes_number") Integer clothesNumber,
								  @Param("p_is_injury") Boolean isInjury,
								  @Param("p_is_active") Boolean isActive,
								  @Param("p_avatar") String avatar,
								  @Param("p_condition_id") Long conditionId);

	@Procedure(name = "updatePlayer")
	String updatePlayer(@Param("p_player_id") Long playerId,
						@Param("p_first_name") String firstName,
						@Param("p_last_name") String lastName,
						@Param("p_position_detail_id") Long positionDetailId,
						@Param("p_annually_salary") Long annuallySalary,
						@Param("p_market_value") Long marketValue,
						@Param("p_country_id") Long countryId,
						@Param("p_date_of_birth") Date dateOfBirth,
						@Param("p_skill") String skill,
						@Param("p_clothes_number") Integer clothesNumber,
						@Param("p_is_injury") Boolean isInjury,
						@Param("p_is_active") Boolean isActive,
						@Param("p_avatar") String avatar,
						@Param("p_condition_id") Long conditionId);
}

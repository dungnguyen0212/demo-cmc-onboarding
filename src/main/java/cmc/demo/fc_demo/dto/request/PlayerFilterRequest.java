package cmc.demo.fc_demo.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerFilterRequest {
	@JsonProperty("name_like")
	private String nameLike;

	@JsonProperty("position_detail_id")
	private Long positionDetailId;

	@JsonProperty("market_value_gte")
	private Integer marketValueGte;

	@JsonProperty("market_value_lte")
	private Integer marketValueLte;

	@JsonProperty("annually_salary_gte")
	private Integer annuallySalaryGte;

	@JsonProperty("annually_salary_lte")
	private Integer annuallySalaryLte;

	@JsonProperty("country_id")
	private Long countryId;

	@JsonProperty("is_injury")
	private Boolean isInjury;

	@JsonProperty("clothes_number")
	private Integer clothesNumber;

	@JsonProperty("created_at_from")
	private String createdAtFrom;

	@JsonProperty("created_at_to")
	private String createdAtTo;
}

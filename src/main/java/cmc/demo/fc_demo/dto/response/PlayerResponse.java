package cmc.demo.fc_demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("first_name")
	private String firstName;

	@JsonProperty("last_name")
	private String lastName;

	@JsonProperty("full_name")
	private String fullName;

	@JsonProperty("position_detail_id")
	private Long positionDetailId;

	@JsonProperty("position_detail_code")
	private String positionDetailCode;

	@JsonProperty("position_detail_name")
	private String positionDetailName;

	@JsonProperty("position_detail_description")
	private String positionDetailDescription;

	@JsonProperty("position_id")
	private Long positionId;

	@JsonProperty("position_name")
	private String positionName;

	@JsonProperty("position_description")
	private String positionDescription;

	@JsonProperty("annually_salary")
	private Long annuallySalary;

	@JsonProperty("market_value")
	private Long marketValue;

	@JsonProperty("country_id")
	private Long countryId;

	@JsonProperty("country_name")
	private String countryName;

	@JsonProperty("country_code")
	private String countryCode;

	@JsonProperty("date_of_birth")
	private Date dateOfBirth;

	@JsonProperty("skill")
	private String skill;

	@JsonProperty("clothes_number")
	private Integer clothesNumber;

	@JsonProperty("created_at")
	private Timestamp createdAt;

	@JsonProperty("is_injury")
	private Boolean isInjury;

	@JsonProperty("is_active")
	private Boolean isActive;

	@JsonProperty("avatar")
	private String avatar;

}

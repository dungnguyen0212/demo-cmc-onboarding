package cmc.demo.fc_demo.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerRequest {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("first_name")
	@NotBlank(message = "Không được để trống")
	private String firstName;

	@JsonProperty("last_name")
	@NotBlank(message = "Không được để trống")
	private String lastName;

	@JsonProperty("position_detail_id")
	private Long positionDetailId;

	@JsonProperty("annually_salary")
	@NotNull(message = "Không được để trống")
	@Min(value = 1, message = "Phải nhập số dương")
	@Max(value = 999999999, message = "Quá giới hạn số tiền cho phép")
	private Long annuallySalary;

	@JsonProperty("market_value")
	@NotNull(message = "Không được để trống")
	@Min(value = 1, message = "Phải nhập số dương")
	@Max(value = 999999999, message = "Quá giới hạn số tiền cho phép")
	private Long marketValue;

	@JsonProperty("country_id")
	private Long countryId;

	@JsonProperty("date_of_birth")
	private String dateOfBirth;

	@JsonProperty("skill")
	private String skill;

	@JsonProperty("clothes_number")
	@NotNull(message = "Không được để trống")
	@Min(value = 1, message = "Phải nhập số dương")
	private Integer clothesNumber;

	@JsonProperty("is_injury")
	private Boolean isInjury;

	@JsonProperty("is_active")
	private Boolean isActive;

	@JsonProperty("avatar")
	private String avatar;
}

package cmc.demo.fc_demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionDetailResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("code")
	private String code;

	@JsonProperty("name")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("position_id")
	private Long positionId;

	@JsonProperty("position_name")
	private String positionName;

	@JsonProperty("position_description")
	private String positionDescription;

	@JsonProperty("is_active")
	private Boolean isActive;
}

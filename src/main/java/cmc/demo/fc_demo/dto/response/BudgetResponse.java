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
public class BudgetResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("attribute_name")
	private String attributeName;

	@JsonProperty("value")
	private Long value;
}

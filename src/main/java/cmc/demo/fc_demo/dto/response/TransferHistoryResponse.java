package cmc.demo.fc_demo.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferHistoryResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("change_category_id")
	private Long changeCategoryId;

	@JsonProperty("change_category_name")
	private String changeCategoryName;

	@JsonProperty("object_id")
	private Long objectId;

	@JsonProperty("object_name")
	private String objectName;

	@Column(name = "market_value")
	private Long marketValue;

	@Column(name = "created_at")
	private Timestamp createdAt;
}

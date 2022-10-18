package cmc.demo.fc_demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "transfer_history")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "addTransferHistory",
				procedureName = "pkg_transfer_history.add_transfer_history",
				parameters = {
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_change_category_id", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_object_id", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_market_value", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.OUT, name = "v_result", type = String.class),
				})
}
)
public class TransferHistory extends BaseModel {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "change_category_id")
	private Long changeCategoryId;

	@Column(name = "object_id")
	private Long objectId;

	@Column(name = "market_value")
	private Long marketValue;

	@Column(name = "created_by")
	private String createdBy;
}

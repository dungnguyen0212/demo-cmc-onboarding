package cmc.demo.fc_demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "budget")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "updateBudget",
				procedureName = "pkg_budget.update_budget",
				parameters = {
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_budget_id", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_change_money", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.OUT, name = "v_result", type = String.class)
				})
})
public class Budget {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "attribute_name")
	private String attributeName;

	@Column(name = "value")
	private Long value;
}

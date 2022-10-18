package cmc.demo.fc_demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Table(name = "player")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "deletePlayer",
				procedureName = "pkg_player.del_player",
				parameters = {
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_player_id", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.OUT, name = "v_result", type = String.class)
				}),
		@NamedStoredProcedureQuery(name = "buyPlayer",
				procedureName = "pkg_player.buy_player",
				parameters = {
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_first_name", type = String.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_last_name", type = String.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_position_detail_id", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_annually_salary", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_market_value", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_country_id", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_date_of_birth", type = Date.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_skill", type = String.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_clothes_number", type = Integer.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_is_injury", type = Boolean.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_is_active", type = Boolean.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_avatar", type = String.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_condition_id", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.OUT, name = "v_result", type = String.class),
						@StoredProcedureParameter(mode = ParameterMode.OUT, name = "v_player_id", type = Long.class)
				},
				hints = {
						@QueryHint(name = "hibernate.proc.param_null_passing.p_is_injury", value = "true"),
						@QueryHint(name = "hibernate.proc.param_null_passing.p_is_active", value = "true"),
						@QueryHint(name = "hibernate.proc.param_null_passing.p_date_of_birth", value = "true"),
						@QueryHint(name = "hibernate.proc.param_null_passing.p_skill", value = "true"),
						@QueryHint(name = "hibernate.proc.param_null_passing.p_country_id", value = "true"),
						@QueryHint(name = "hibernate.proc.param_null_passing.p_avatar", value = "true")
				}),
		@NamedStoredProcedureQuery(name = "updatePlayer",
				procedureName = "pkg_player.update_player",
				parameters = {
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_player_id", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_first_name", type = String.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_last_name", type = String.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_position_detail_id", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_annually_salary", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_market_value", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_country_id", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_date_of_birth", type = Date.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_skill", type = String.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_clothes_number", type = Integer.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_is_injury", type = Boolean.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_is_active", type = Boolean.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_avatar", type = String.class),
						@StoredProcedureParameter(mode = ParameterMode.IN, name = "p_condition_id", type = Long.class),
						@StoredProcedureParameter(mode = ParameterMode.OUT, name = "v_result", type = String.class)
				},
				hints = {
						@QueryHint(name = "hibernate.proc.param_null_passing.p_is_injury", value = "true"),
						@QueryHint(name = "hibernate.proc.param_null_passing.p_is_active", value = "true"),
						@QueryHint(name = "hibernate.proc.param_null_passing.p_date_of_birth", value = "true"),
						@QueryHint(name = "hibernate.proc.param_null_passing.p_skill", value = "true"),
						@QueryHint(name = "hibernate.proc.param_null_passing.p_country_id", value = "true"),
						@QueryHint(name = "hibernate.proc.param_null_passing.p_avatar", value = "true")
				})
})
public class Player extends BaseModel {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "position_detail_id")
	private Long positionDetailId;

	@Column(name = "annually_salary")
	private Long annuallySalary;

	@Column(name = "market_value")
	private Long marketValue;

	@Column(name = "country_id")
	private Long countryId;

	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	@Column(name = "skill")
	private String skill;

	@Column(name = "clothes_number")
	private Integer clothesNumber;

	@Column(name = "is_injury")
	private Boolean isInjury;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "condition_id")
	private Long conditionId;
}

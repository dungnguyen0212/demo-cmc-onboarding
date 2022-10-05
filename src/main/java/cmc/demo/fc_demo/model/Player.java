package cmc.demo.fc_demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Table(name = "player")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

	@JsonProperty("avatar")
	private String avatar;
}

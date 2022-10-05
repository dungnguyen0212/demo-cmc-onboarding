package cmc.demo.fc_demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "position_detail")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionDetail extends BaseModel {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "position_id")
	private Long positionId;

	@Column(name = "is_active")
	private Boolean isActive;
}

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

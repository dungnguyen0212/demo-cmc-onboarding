package cmc.demo.fc_demo.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ConditionEnum {
	TOP("Top"),
	GOOD("Good"),
	NORMAL("Normal"),
	POOR("Poor"),
	TERRIBLE("Terrible");
	private String name;
}

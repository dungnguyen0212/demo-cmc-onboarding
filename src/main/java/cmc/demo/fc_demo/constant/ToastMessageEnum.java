package cmc.demo.fc_demo.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ToastMessageEnum {
	CREATE("Create", "Khởi tạo thành công"),
	UPDATE("Update", "Cập nhật thông tin thành công"),
	DELETE("Delete", "Sa thải thành công");
	private String name;
	private String description;
}

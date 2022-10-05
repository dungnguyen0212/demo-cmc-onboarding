package cmc.demo.fc_demo.constant;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ValidMessageEnum {
	SALARY("validAnnuallySalary", "Quỹ lương không đủ, sa thải bớt đi"),
	TOTAL_MONEY("validMarketValue","Tổng ngân sách chuyển nhượng không đủ, đừng mua mấy thằng giá ảo"),
	CLOTHES_NUMBER("validClothesNumber", "Số áo đã được đăng ký, vui lòng lựa chọn số khác"),
	TYPE_UPLOAD("validAvatar", "Vui lòng upload ảnh, không up linh tinh cẩn thận virus");
	private String name;
	private String message;
}

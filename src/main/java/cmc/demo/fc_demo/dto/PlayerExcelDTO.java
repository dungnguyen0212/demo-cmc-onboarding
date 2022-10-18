package cmc.demo.fc_demo.dto;

import cmc.demo.fc_demo.util.anotation.ExcelField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerExcelDTO {
	@ExcelField("Tên cầu thủ")
	private String fullName;

	@ExcelField("Vị trí thi đấu")
	private String positionDetailCode;

	@ExcelField("Lương")
	private Long annuallySalary;

	@ExcelField("Giá trị chuyển nhượng")
	private Long marketValue;

	@ExcelField("Quê hương")
	private String countryName;

	@ExcelField("Kỹ năng đặc biệt")
	private String skill;

	@ExcelField("Số áo")
	private Integer clothesNumber;

//	@ExcelField("Ngày sinh")
//	private Date dateOfBirth;
//
//	@ExcelField("Ngày gia nhập")
//	private Date createdAt;
}

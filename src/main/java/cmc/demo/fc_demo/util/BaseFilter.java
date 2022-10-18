package cmc.demo.fc_demo.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@SuperBuilder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class BaseFilter {

	public BaseFilter() {
		page = 1;
		size = 20;
		sortBy = "id";
		order = "DESC";
	}

	@NotNull
	@Positive
	@JsonProperty("page")
	@ApiModelProperty(example = "1")
	private int page;

	@NotNull
	@Positive
	@JsonProperty("size")
	@ApiModelProperty(example = "20")
	private int size;

	@ApiParam(name = "sort_by", value="DESC")
	@JsonProperty("sort_by")
	private String sortBy;

	@JsonProperty("order")
	private String order;

}

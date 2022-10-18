package cmc.demo.fc_demo.util;

import cmc.demo.fc_demo.util.anotation.ExcelField;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.*;

@Slf4j
public class ExcelUtils {

	private static final String CSV_DATE_FORMAT = "MM/dd/yyyy";

	private static final String CSV_DATETIME_FORMAT = "MM/dd/yyyy HH:mm:ss";

	private static void fitColumnWidth(Sheet sheet) {
		// fit column width
		try {
			int numOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
			for (int i = 0; i < numOfColumn; i++) {
				sheet.autoSizeColumn(i);
			}
		} catch (Exception ex) {
			log.error("Error when fit column width", ex);
		}

	}

	private static CellStyle createStyle(XSSFWorkbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setBorderBottom(BorderStyle.NONE);
		cellStyle.setBorderTop(BorderStyle.NONE);
		cellStyle.setBorderRight(BorderStyle.NONE);
		cellStyle.setBorderLeft(BorderStyle.NONE);
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 14);
		cellStyle.setFont(font);
		return cellStyle;
	}

	private static void createTitle(List<String> titles, XSSFWorkbook workbook) {
		CellStyle styleForTitle = createStyle(workbook);
		Sheet sheet = workbook.getSheetAt(0);
		Row row = sheet.createRow(0);
		Cell cell;
		for (int i = 0; i < titles.size(); i++) {
			cell = row.createCell(i, CellType.STRING);
			cell.setCellValue(titles.get(i));
			cell.setCellStyle(styleForTitle);
		}
	}

	private static void assignRowData(Object obj, Row row, CellStyle style) throws IllegalAccessException {
		int cellIndex = 0;
		Class<?> cls = obj.getClass();
		for (Field field : cls.getDeclaredFields()) {
			if (field.isAnnotationPresent(ExcelField.class)) {
				Cell cell = row.createCell(cellIndex);
				String value = getFieldValue(field, obj);
				cell.setCellValue(value);
				cell.setCellStyle(style);
				cellIndex++;
			}
		}
	}

	private static String getFieldValue(Field field, Object obj) throws IllegalAccessException {
		if (Objects.isNull(obj))
			return null;
		field.setAccessible(true);
		Object raw = field.get(obj);
		if (Objects.isNull(raw) || StringUtils.isEmpty(raw.toString())) {
			return "";
		}
		String value;
		if (raw instanceof Timestamp) {
			value = DateUtils.format((Timestamp) raw, CSV_DATETIME_FORMAT);
		} else if (raw instanceof Date) {
			value = DateUtils.format((Date) raw, CSV_DATE_FORMAT);
		} else {
			value = raw.toString();
		}
		return value;
	}

	public static ByteArrayOutputStream exportExcel(List<?> objects, String fileName) throws IOException {
		if (CollectionUtils.isEmpty(objects))
			return null;
		Object firstMessage = objects.get(0);
		Class<?> cls = firstMessage.getClass();
		List<String> titles = new ArrayList<>();
		Arrays.stream(cls.getDeclaredFields()).forEach(field -> {
			if (field.isAnnotationPresent(ExcelField.class)) {
				field.setAccessible(true);
				ExcelField annotation = field.getAnnotation(ExcelField.class);
				String fieldTitle = annotation.value();
				if (StringUtils.isEmpty(fieldTitle)) {
					fieldTitle = field.getName();
				}
				titles.add(fieldTitle);
			}
		});

		XSSFWorkbook workbook = null;

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet(fileName);

			int rowIndex = 1;
			createTitle(titles, workbook);

			CellStyle styleOfValue = createStyle(workbook);

			for (Object obj : objects) {
				Row row = sheet.createRow(rowIndex);
				assignRowData(obj, row, styleOfValue);
				rowIndex++;
			}
			fitColumnWidth(sheet);
			workbook.write(outputStream);
		} catch (IOException | IllegalAccessException e) {
			log.error(e.getMessage(), e);
		} finally {
			assert workbook != null;
			workbook.close();
		}

		return outputStream;
	}
}


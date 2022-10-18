package cmc.demo.fc_demo.service.excel;

import cmc.demo.fc_demo.util.DateUtils;
import cmc.demo.fc_demo.util.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class ExportService {

	private static String CSV_DATETIME_FORMAT = "dd_MM_yyyy_HH_mm_ss";

//	public static void exportCsv(HttpServletResponse response, List<?> list) throws IOException {
//
//		response.setContentType("application/csv");
//		response.setHeader("Content-Disposition", "attachment; filename=" + getFileNameCsv());
//		CsvUtils.printToCsv(response, list);
//	}

	public static void exportExcel(HttpServletResponse response, List<?> list) throws IOException {
		String id = UUID.randomUUID().toString();
		log.info("Start export excel with: id: {}, time: {}", id, new Timestamp(System.currentTimeMillis()));
		OutputStream outputStream = null;
		try {
			String fileName = getFileNameXlsx();
			outputStream = response.getOutputStream();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			ByteArrayOutputStream byteArrayOutputStream = ExcelUtils.exportExcel(list, fileName);
			if (Objects.isNull(byteArrayOutputStream))
				return;
			outputStream.write(byteArrayOutputStream.toByteArray());
			outputStream.flush();
			log.info("End export excel with: id: {}, time: {}", id, new Timestamp(System.currentTimeMillis()));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			assert outputStream != null;
			outputStream.close();
		}
		;
	}

//	public static void exportExcelAiModel(HttpServletResponse response, List<?> list, String type) throws IOException {
//		String id = UUID.randomUUID().toString();
//		log.info("Start export excel with: id: {}, time: {}", id, new Timestamp(System.currentTimeMillis()));
//		OutputStream outputStream = null;
//		try {
//			String fileName = type + DateUtils.format(new Timestamp(System.currentTimeMillis()), CSV_DATETIME_FORMAT) + ".xlsx";
//			outputStream = response.getOutputStream();
//			response.setContentType("application/octet-stream");
//			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//			ByteArrayOutputStream byteArrayOutputStream = ExcelUtils.exportExcel(list, fileName);
//			if (Objects.isNull(byteArrayOutputStream))
//				return;
//			outputStream.write(byteArrayOutputStream.toByteArray());
//			outputStream.flush();
//			log.info("End export excel with: id: {}, time: {}", id, new Timestamp(System.currentTimeMillis()));
//		} catch (IOException e) {
//			log.error(e.getMessage(), e);
//		} finally {
//			assert outputStream != null;
//			outputStream.close();
//		}
//		;
//	}
//
//	private static String getFileNameCsv() {
//		return getFileName() + ".csv";
//	}

	private static String getFileNameXlsx() {
		return getFileName() + ".xlsx";
	}

	private static String getFileName() {
		return "EXPORTED_" + DateUtils.format(new Timestamp(System.currentTimeMillis()), CSV_DATETIME_FORMAT);
	}
}

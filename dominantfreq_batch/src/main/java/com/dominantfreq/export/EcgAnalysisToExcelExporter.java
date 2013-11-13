package com.dominantfreq.export;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.dominantfreq.model.data.EcgAnalysis;
import com.dominantfreq.model.data.SpectrumAnalysis;

public class EcgAnalysisToExcelExporter {

	public static void export(List<EcgAnalysis> toSave) {
		if (!toSave.isEmpty()) {
			exportToXsl(toSave);
		}
	}

	private static void exportToXsl(List<EcgAnalysis> toSave) {
		Calendar calendar = GregorianCalendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		String pathString = "./ECG_Analysis_Results/" + year + "-" + month + "-" + day + "/";
		File path = new File(pathString);
		path.mkdirs();

		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		int miliSecs = calendar.get(Calendar.MILLISECOND);
		String fileName = hours + "h-" + minutes + "m-" + seconds + "s-" + miliSecs + "ms.xls";

		File file = new File(path, fileName);
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(file);
			Workbook workbook = new HSSFWorkbook();
			Sheet sheet = workbook.createSheet("Dominant Frequencies");
			for (int i = 0; i < toSave.size(); i++) {
				Row row = sheet.createRow(i);
				EcgAnalysis ecgAnalysis = toSave.get(i);
				row.createCell(0).setCellValue(ecgAnalysis.getName());
				int j = 1;
				for (SpectrumAnalysis analysis : ecgAnalysis.getAnalysises()) {
					row.createCell(j).setCellValue(analysis.getDominantFrequency());
					j++;
				}
			}
			workbook.write(output);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
			}
		}

	}
}

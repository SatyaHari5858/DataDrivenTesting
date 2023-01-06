package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtility {

	public FileInputStream fis;
	public FileOutputStream fos;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public CellStyle style;
	String path = null;

	public XLUtility(String path) {
		this.path = path;
	}

	/*
	 * Reading data from excel sheet
	 */

	/*
	 * Getting count of a ROW
	 */

	public int getRowCount(String sheetName) throws IOException {

		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		int rowcount = sheet.getLastRowNum();
		workbook.close();
		fis.close();
		return rowcount;
	}

	/*
	 * Getting count of a cell
	 */

	public int getCellCount(String sheetName, int rownum) throws IOException {
		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		int cellCount = row.getLastCellNum();
		workbook.close();
		fis.close();
		return cellCount;
	}

	/*
	 * Getting data of a Cell
	 */

	public String getCellData(String sheetName, int rownum, int col) throws IOException {
		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rownum);
		cell = row.getCell(col);

		DataFormatter formatter = new DataFormatter();// get the all type data from sheet and stored it as string value
		String data ;
		try {
			data = formatter.formatCellValue(cell);

		} catch (Exception e) {
			data = "";
		}
		workbook.close();
		fis.close();
		return data;
	}

	/*
	 * Writing data into excel sheet
	 */

	public void setCellData(String sheetName, int rownum, int col, String data) throws IOException {
		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);

		sheet.getRow(rownum);
		cell = row.createCell(col);
		cell.setCellValue(data);

		fos = new FileOutputStream(path);
		workbook.write(fos);
		workbook.close();
		fos.close();
	}

	/*
	 * Apply GREEN color
	 */
	public void fillGreenColor(String sheetName, int rownum, int col) throws IOException {
		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);

		row = sheet.getRow(rownum);
		cell = row.getCell(col);

		style = workbook.createCellStyle();

		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		workbook.write(fos);
		workbook.close();
		fis.close();
		fos.close();
	}

	/*
	 * Apply RED color
	 */
	public void fillRedColor(String sheetName, int rownum, int col) throws IOException {
		fis = new FileInputStream(path);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);

		row = sheet.getRow(rownum);
		cell = row.getCell(col);

		style = workbook.createCellStyle();

		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		workbook.write(fos);
		workbook.close();
		fis.close();
		fos.close();
	}
}

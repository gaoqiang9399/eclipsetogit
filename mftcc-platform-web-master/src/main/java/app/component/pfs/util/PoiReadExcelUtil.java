package app.component.pfs.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 功能描述：Poi读取Excel工具类
 * @author zhaomin(mailto:zhaomin@dhcc.com.cn)
 * @date 2011-07-22
 *
 */
public class PoiReadExcelUtil {
	
	private FileInputStream file = null;
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;
	
	public PoiReadExcelUtil(){
		
	}
	
	/**
	 * 功能描述：初始化工作区、sheet脚本，默认为第一个sheet
	 * @param path 文件路径
	 */
	public PoiReadExcelUtil(String path){
		try {
			file = new FileInputStream(path);
			workbook = new HSSFWorkbook(file);
			sheet = workbook.getSheetAt(0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能描述：初始化工作区
	 * @param path 文件路径
	 * @param sheetName sheet名称
	 */
	public PoiReadExcelUtil(String path,String sheetName){
		try {
			file = new FileInputStream(path);
			workbook = new HSSFWorkbook(file);
			sheet = workbook.getSheet(sheetName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 功能描述：初始化工作区
	 * @param path 文件路径
	 * @param sheetIndex sheet序号
	 */
	public PoiReadExcelUtil(String path,int sheetIndex){
		try {
			file = new FileInputStream(path);
			workbook = new HSSFWorkbook(file);
			sheet = workbook.getSheetAt(sheetIndex);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 功能描述：获得总行数
	 * @return
	 */
	public int getRows(){
		return sheet.getLastRowNum();
	}
	
	/**
	 * 功能描述：获得纯数字列值
	 * @param colNum  列数
	 * @param rowNum  行数
	 * @date 2011-08-02
	 * @return double类型
	 */
	public double getDoubleValue(int colNum,int rowNum){
		HSSFRow row = sheet.getRow(rowNum);
		HSSFCell cell = row.getCell(colNum);
		if(cell==null){
			return 0.00;
		}
		return cell.getNumericCellValue();
	}
	
	/**
	 * 功能描述：获得非纯数字列值
	 * @param colNum 列数
	 * @param rowNum 行数
	 * @return String类型
	 */
	public String getStringValue(int colNum,int rowNum){
		HSSFRow row = sheet.getRow(rowNum);
		HSSFCell cell = row.getCell(colNum);
		return cell.getStringCellValue();
	}
	
	/**
	 * 功能描述：兼容旧的取值方法，返回均为String类型
	 * @param colNum 列数
	 * @param rowNum 行数
	 * @date 2011-08-02
	 * @return String类型
	 */
	public String getValue(int colNum,int rowNum){
		HSSFRow row = sheet.getRow(rowNum);
		HSSFCell cell = row.getCell(colNum);
		if(cell==null){
			return "";
		}
		if(cell.getCellType() == 0){
			return String.format("%.0f",cell.getNumericCellValue());
		}else{
			return cell.getStringCellValue();
		}
	}
	/**
	 * 获取日期类型的值
	 * */
	public String getDateValue(int colNum,int rowNum){
		HSSFRow row = sheet.getRow(rowNum);
		HSSFCell cell = row.getCell(colNum);
		if(cell.getCellType() == 0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(cell.getDateCellValue());
		}else{
			return cell.getStringCellValue().replaceAll("/", "-");
		}
	}
	
	/**
	 * 功能描述：兼容旧的按sheet索引取值方法，返回均为String类型，sheet默认索引0
	 * @param shee sheet索引
	 * @param colNum 列数
	 * @param rowNum 行数
	 * @date 2011-08-02
	 * @return String类型
	 */
	public String getAnyValue(int shee,int colNum,int rowNum){
		HSSFRow row = sheet.getRow(rowNum);
		HSSFCell cell = row.getCell(colNum);
		if(cell.getCellType() == 0){
			return String.valueOf(cell.getNumericCellValue());
		}else{
			return cell.getStringCellValue();
		}
	}
	
	public void close(){
		try {
			file.close();
			workbook = null;
			sheet = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setFile(FileInputStream file) {
		this.file = file;
	}
	
	public void setFile(String path){
		try {
			this.file = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void setWorkbook() {
		if(file != null){
			try {
				this.workbook = new HSSFWorkbook(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setSheetBySheetName(String sheetName) {
		if(workbook != null){
			this.sheet = workbook.getSheet(sheetName);
		}
	}
	
	public void setSheetBySheetIndex(int index){
		if(workbook != null){
			this.sheet = workbook.getSheetAt(index);
		}
	}

}

package app.component.cus.whitename.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import app.component.cus.whitename.entity.MfCusWhitename;
import app.component.pfs.entity.CusFinParm;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * 功能描述：Poi导出excel，路径是从upload.properties中读取
 * 
 * @author czk
 * @date 2016-11-15
 *
 */
public class WhitenameExcelUtil {

	public FileInputStream file = null;
	public HSSFWorkbook workbook = null;

	public HSSFSheet sheet = null;
	public List<CusFinParm> cusFinParmList = null;

	public WhitenameExcelUtil() {

	}

	/**
	 * 功能描述：初始化工作区、sheet脚本，默认为第一个sheet
	 * 
	 * @param cusFinParmList
	 *            文件路径
	 */
	public WhitenameExcelUtil(List<CusFinParm> cusFinParmList) {
		try {
			this.cusFinParmList = cusFinParmList;
			if (this.cusFinParmList == null) {
				cusFinParmList = new ArrayList();
			}
			workbook = new HSSFWorkbook();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getFilePath(String fileName) throws Exception {
		String CurrentClassFilePath = this.getClass().getResource("").getPath();
		int lastpath = CurrentClassFilePath.lastIndexOf("util/");
		String web_rootPath = CurrentClassFilePath.substring(0, lastpath);
		String path = web_rootPath + "template";
		try {
			if (StringUtil.isEmpty(path)) {
				return null;
			}
			String destFile = path + File.separator + fileName;
			return destFile;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values) {
		// 第一步，创建一个webbook，对应一个Excel文件
		if (workbook == null) {
			workbook = new HSSFWorkbook();
		}
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = workbook.createSheet(sheetName);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow(0);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFCell cell = null;
		// 创建标题
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}
		// 创建内容
		for (int i = 0; i < values.length; i++) {
			row = sheet.createRow(i + 1);
			for (int j = 0; j < values[i].length; j++) {
				row.createCell(j).setCellValue(values[i][j]);
			}
		}
		return workbook;
	}

	public List<MfCusWhitename> resolveExcel(String filePath) throws Exception {
		List<MfCusWhitename> mfCusWhitenameList = new ArrayList<>();
		FileInputStream is = null;
		Workbook hwb = null;
		try {
			is = new FileInputStream(filePath);
			hwb = WorkbookFactory.create(is);//创建excel对象
			Sheet childSheet = hwb.getSheet("白名单管理模板");//读取指定名称的Excel表
			if (childSheet.getLastRowNum() < 0) {//如果指定模板读取不到则读取第一个
				childSheet = hwb.getSheetAt(0);
			}
			MfCusWhitename mfCusWhitename;
			for (int j = 0; j < childSheet.getLastRowNum(); j++) {
				Row row = childSheet.getRow(j + 1);//从第一行开始
				if (row != null) {
					mfCusWhitename = new MfCusWhitename();
					row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
					mfCusWhitename.setCusName(row.getCell(0).getStringCellValue());
					row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
					if ("企业客户".equals(row.getCell(1).getStringCellValue())) {
						mfCusWhitename.setCusType("101");
					} else if("个人客户".equals(row.getCell(1).getStringCellValue())) {
						mfCusWhitename.setCusType("202");
					}
					row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
					mfCusWhitename.setIdNum(row.getCell(2).getStringCellValue());
					row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
					mfCusWhitename.setTel(row.getCell(3).getStringCellValue());
					row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
					mfCusWhitename.setRemark(row.getCell(4).getStringCellValue());
					mfCusWhitename.setId(WaterIdUtil.getWaterId("wn"));
					mfCusWhitename.setRegTime(DateUtil.getDateTime());
					mfCusWhitename.setLstModTime(DateUtil.getDateTime());
					mfCusWhitename.setOpNo(mfCusWhitename.getCurrentSessionRegNo());
					mfCusWhitename.setOpName(mfCusWhitename.getCurrentSessionRegName());
					mfCusWhitename.setBrNo(mfCusWhitename.getCurrentSessionOrgNo());
					mfCusWhitename.setBrName(mfCusWhitename.getCurrentSessionOrgName());
					if (StringUtil.isNotEmpty(mfCusWhitename.getCusName()) && StringUtil.isNotEmpty(mfCusWhitename.getId())) {
						mfCusWhitenameList.add(mfCusWhitename);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (hwb != null) {
					hwb.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return mfCusWhitenameList;
	}
}

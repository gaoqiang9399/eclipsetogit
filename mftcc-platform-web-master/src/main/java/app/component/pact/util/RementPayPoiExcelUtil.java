package app.component.pact.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import app.component.calc.core.entity.MfRepayPlan;
import app.component.pfs.entity.CusFinParm;
import cn.mftcc.util.DateUtil;
import cn.mftcc.util.MathExtend;
import cn.mftcc.util.StringUtil;
import cn.mftcc.util.WaterIdUtil;

/**
 * 功能描述：Poi导出excel，路径是从upload.properties中读取
 * 
 * @author czk
 * @date 2016-11-15
 *
 */
public class RementPayPoiExcelUtil {

	private FileInputStream file = null;
	private HSSFWorkbook workbook = null;

	private HSSFSheet sheet = null;
	private List<CusFinParm> cusFinParmList = null;

	public RementPayPoiExcelUtil() {

	}

	/**
	 * 功能描述：初始化工作区、sheet脚本，默认为第一个sheet
	 * 
	 * @param path
	 *            文件路径
	 */
	public RementPayPoiExcelUtil(List<CusFinParm> cusFinParmList) {
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

	public List<MfRepayPlan> resolveExcel(String filePath) throws Exception {
		FileInputStream is =null;
		HSSFWorkbook hwb =null;
		ArrayList<MfRepayPlan> mfRepayPlans = new ArrayList<>();
		try{
			is = new FileInputStream(filePath);
			hwb = new HSSFWorkbook(is);
			HSSFSheet childSheet = hwb.getSheetAt(0);
			MfRepayPlan mfRepayPlan = new MfRepayPlan();
			Double feesum = 0.0;
			for (int j = 7; j < childSheet.getLastRowNum(); j++) {
				HSSFRow row = childSheet.getRow(j);
				if (row != null) {
					int cellType = row.getCell(0).getCellType();
					if (cellType == 0) {
						row.getCell(0).setCellType(Cell.CELL_TYPE_NUMERIC);
						double termNumDouble = row.getCell(0).getNumericCellValue();
						int termNum = (int) termNumDouble;
						mfRepayPlan = new MfRepayPlan();
						mfRepayPlan.setTermNum(termNum);
						row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
						mfRepayPlan.setPlanBeginDate(row.getCell(1).getStringCellValue());
						row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
						mfRepayPlan.setPlanEndDate(row.getCell(2).getStringCellValue());
						row.getCell(3).setCellType(Cell.CELL_TYPE_NUMERIC);
						mfRepayPlan.setRepayPrcp(row.getCell(3).getNumericCellValue());
						row.getCell(4).setCellType(Cell.CELL_TYPE_NUMERIC);
						mfRepayPlan.setRepayIntst(row.getCell(4).getNumericCellValue());
						row.getCell(5).setCellType(Cell.CELL_TYPE_NUMERIC);
						mfRepayPlan.setRepayPrcpIntstSum(row.getCell(5).getNumericCellValue());
						row.getCell(6).setCellType(Cell.CELL_TYPE_NUMERIC);
						mfRepayPlan.setRepayPrcpBalAfter(row.getCell(6).getNumericCellValue());
						mfRepayPlan.setFeeSum(feesum);
						mfRepayPlan.setPlanBeginDateFormat(DateUtil.getShowDateTime(mfRepayPlan.getPlanBeginDate()));//本期开始日期
						mfRepayPlan.setPlanEndDateFormat(DateUtil.getShowDateTime(mfRepayPlan.getPlanEndDate()));//本期结束日期
						mfRepayPlan.setRepayIntstFormat(MathExtend.moneyStr(mfRepayPlan.getRepayIntst()));//本期计划应还利息
						mfRepayPlan.setRepayPrcpFormat(MathExtend.moneyStr(mfRepayPlan.getRepayPrcp()));//本期计划应还本金
						mfRepayPlan.setFeeSumFormat(MathExtend.moneyStr(mfRepayPlan.getFeeSum()));//费用总额
						mfRepayPlan.setRepayPrcpIntstSumFormat(MathExtend.moneyStr(mfRepayPlan.getRepayPrcpIntstSum()));//应还总额
						mfRepayPlan.setRepayPrcpBalAfterFormat(MathExtend.moneyStr(mfRepayPlan.getRepayPrcpBalAfter()));//期末本金余额（减本期应还本金
						mfRepayPlan.setPlanId(WaterIdUtil.getWaterId("plan"));
						mfRepayPlans.add(mfRepayPlan);
					} else {
						return mfRepayPlans;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
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
		return mfRepayPlans;
	}
}

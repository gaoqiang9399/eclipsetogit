package app.component.pss.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import app.component.pss.utils.PssPublicUtil;
import cn.mftcc.util.WaterIdUtil;


public class ExcelUtil {
	
	private static String locations = ExcelUtil.class.getResource("/").getPath()+"app/component/pss/template/pss-excel-config.xml";
	private static String pathFolder = ExcelUtil.class.getResource("/").getPath()+"app/component/pss/template";

	/**
	 * 判断指定的单元格是否是合并单元格
	 * @param sheet 
	 * @param row 行下标
	 * @param column 列下标
	 * @return
	 */
	public static boolean isMergedRegion(Sheet sheet,int row ,int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if(row >= firstRow && row <= lastRow){
				if(column >= firstColumn && column <= lastColumn){
					return true;
				}
			}
		}
		return false;
	}

	/** 
	 * 获取合并单元格的值 
	 * @param sheet 
	 * @param row 
	 * @param column 
	 * @return 
	 */  
	public static String getMergedRegionValue(Sheet sheet ,int row , int column){
		int sheetMergeCount = sheet.getNumMergedRegions();  

		for(int i = 0 ; i < sheetMergeCount ; i++){  
			CellRangeAddress ca = sheet.getMergedRegion(i);  
			int firstColumn = ca.getFirstColumn();  
			int lastColumn = ca.getLastColumn();  
			int firstRow = ca.getFirstRow();  
			int lastRow = ca.getLastRow();  

			if(row >= firstRow && row <= lastRow){  

				if(column >= firstColumn && column <= lastColumn){  
					Row fRow = sheet.getRow(firstRow);  
					Cell fCell = fRow.getCell(firstColumn);  
					return getCellValue(fCell) ;  
				}  
			}  
		}  

		return null ;  
	}

	/** 
	 * 获取单元格的值 
	 * @param cell 
	 * @return 
	 */  
	private static String getCellValue(Cell cell){

		if(cell == null) {
			return "";
		}

		if(cell.getCellType() == Cell.CELL_TYPE_STRING){  

			return cell.getStringCellValue();  

		}else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){  

			return String.valueOf(cell.getBooleanCellValue());  

		}else if(cell.getCellType() == Cell.CELL_TYPE_FORMULA){  

			return cell.getCellFormula() ;  

		}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){  

			return String.valueOf(cell.getNumericCellValue());  

		}else {
		}
		return "";  
	}
	
	/** 
	 * 获取EXCEL模板
	 * @param cell 
	 * @return 
	 * @throws Exception 
	 */  
	public static HSSFWorkbook getWorkbook(String type) throws Exception{
		XMLExcelDefinitionReader xmlExcelDefinitionReader = XMLExcelDefinitionReader.getInstance(locations);
		ExcelDefinition excelDefinition = xmlExcelDefinitionReader.getRegistry().get(type);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();//生成一个表格
		workbook.setSheetName(0, excelDefinition.getSheetname());//设置表格标题及编码格式
		sheet.setColumnWidth(0, 256*10+184);
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 550);
		sheet.setDefaultColumnWidth(15);
		/*HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //创建一个居中格式
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFillBackgroundColor(HSSFColor.LIGHT_GREEN.index);
		
		//背景色
		HSSFCellStyle styleChild = workbook.createCellStyle();
		styleChild.setAlignment(HSSFCellStyle.ALIGN_CENTER); //创建一个居中格式
		styleChild.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleChild.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleChild.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleChild.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleChild.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		styleChild.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		styleChild.setFillBackgroundColor(HSSFColor.LIGHT_YELLOW.index);
		
		
		//字体颜色
		Font fontDefault = workbook.createFont();
		fontDefault.setColor(HSSFColor.BLACK.index);
		
		Font fontMust = workbook.createFont();
		fontMust.setColor(HSSFColor.RED.index);*/
		
		List<FieldValue> fieldValues = excelDefinition.getFieldValues();
		FieldValue fieldValue = null;
//		HSSFCell cell = null;
		List<FieldChildValue> fieldChildValues = null;
		int childStartLine = 0;
		for(int i = 0; i < fieldValues.size(); i++){
			fieldValue = fieldValues.get(i);
			if(fieldValue.getFieldChildValues() != null && fieldValue.getFieldChildValues().size() > 0){
				fieldChildValues = fieldValue.getFieldChildValues();
				continue;
			}
			Cell cell = row.createCell(i);
			cell.setCellValue(fieldValue.getTitle());
			//表格样式处理
			HSSFCellStyle style = workbook.createCellStyle();
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //创建一个居中格式
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setFillBackgroundColor(HSSFColor.LIGHT_GREEN.index);
			if(fieldValue.isNull()){
			}else{
				Font fontMust = workbook.createFont();
				fontMust.setColor(HSSFColor.RED.index);
				style.setFont(fontMust);
			}
			cell.setCellStyle(style);
			//sheet.setColumnWidth(i, 256*30+184);//设置列宽
			childStartLine++;
		}
		FieldChildValue fieldChildValue = null;
		for(int c = 0; c < fieldChildValues.size(); c++){
			fieldChildValue = fieldChildValues.get(c);
			Cell cell = row.createCell((childStartLine + c));
			cell.setCellValue(fieldChildValue.getTitle());
			//表格样式处理
			HSSFCellStyle styleChild = workbook.createCellStyle();
			styleChild.setAlignment(HSSFCellStyle.ALIGN_CENTER); //创建一个居中格式
			styleChild.setBorderTop(HSSFCellStyle.BORDER_THIN);
			styleChild.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			styleChild.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			styleChild.setBorderRight(HSSFCellStyle.BORDER_THIN);
			styleChild.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			styleChild.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
			styleChild.setFillBackgroundColor(HSSFColor.LIGHT_YELLOW.index);
			if(fieldChildValue.isNull()){
			}else{
				Font fontMust = workbook.createFont();
				fontMust.setColor(HSSFColor.RED.index);
				styleChild.setFont(fontMust);
			}
			cell.setCellStyle(styleChild);
		}
		
		return workbook;
	}
	
	/** 
	 * 获取EXCEL模板（带下载数据）
	 * @param <T>
	 * @param <E>
	 * @param type 
	 * @return 
	 * @throws Exception 
	 */
	public static <T, E> HSSFWorkbook getWorkbook(String type, HSSFWorkbook workbook, T t, List<E> list) throws Exception{
		XMLExcelDefinitionReader xmlExcelDefinitionReader = XMLExcelDefinitionReader.getInstance(locations);
		ExcelDefinition excelDefinition = xmlExcelDefinitionReader.getRegistry().get(type);
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		int lastrow = sheet.getLastRowNum();
		
//		HSSFSheet sheet = workbook.createSheet();//生成一个表格
//		workbook.setSheetName(0, excelDefinition.getSheetname());//设置表格标题及编码格式
//		sheet.setColumnWidth(0, 256*10+184);
//		sheet.setDefaultColumnWidth((short) 15);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //创建一个居中格式
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		
		List<FieldValue> fieldValues = excelDefinition.getFieldValues();
		FieldValue fieldValue = null;
		HSSFCell cell = null;
		List<FieldChildValue> fieldChildValues = null;
		for(int d = 0; d < list.size(); d++){
			HSSFRow row = sheet.createRow(lastrow + 1 + d);
			row.setHeight((short) 550);
			
			int childStartLine = 0;
			for(int i = 0; i < fieldValues.size(); i++){
				fieldValue = fieldValues.get(i);
				if(fieldValue.getFieldChildValues() != null && fieldValue.getFieldChildValues().size() > 0){
					fieldChildValues = fieldValue.getFieldChildValues();
					continue;
				}
				cell = row.createCell(i);
				cell.setCellValue(String.valueOf(PssPublicUtil.getValueByKey(t, fieldValue.getName())));
				cell.setCellStyle(style);
				//sheet.setColumnWidth(i, 256*30+184);//设置列宽
				childStartLine++;
			}
			FieldChildValue fieldChildValue = null;
			for(int c = 0; c < fieldChildValues.size(); c++){
				fieldChildValue = fieldChildValues.get(c);
				cell = row.createCell((childStartLine + c));
				cell.setCellValue(String.valueOf(PssPublicUtil.getValueByKey(list.get(d), fieldChildValue.getName())));
				cell.setCellStyle(style);
			}
			
			//合并单元格 						
			if(d != 0 && d == list.size() - 1){
				for(int i = 0; i < fieldValues.size() - 1; i++){
					//参数1：起始行号，参数2：起始列号，参数3：行号 参数，4：终止列号
					CellRangeAddress cellRangeAddress= new CellRangeAddress(lastrow + 1, (short) i, lastrow + 1 + d, (short) i);
					sheet.addMergedRegion(cellRangeAddress);
				}
			}
		}
		
		return workbook;
	}
	
	/** 
	 * 处理EXCEL文件
	 * 
	 * @throws Exception 
	 */
	public static List<ExcelDefinitionBean> getExcelDefinitionBeanListFromExcel(File file, String type) throws Exception{
		XMLExcelDefinitionReader xmlExcelDefinitionReader = XMLExcelDefinitionReader.getInstance(locations);
		ExcelDefinition excelDefinition = xmlExcelDefinitionReader.getRegistry().get(type);
		List<FieldValue> fieldValues = excelDefinition.getFieldValues();
		
		ExcelDefinitionBean excelDefinitionBean = null;
		FieldValue fieldValue = null;
		FieldChildValue fieldChildValue = null;
		FieldValueBean fieldValueBean = null;
		FieldChildValueBean fieldChildValueBean = null;
		HSSFWorkbook  wb = null;
		List<ExcelDefinitionBean> list = null;
		try{
			wb = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(file.getAbsolutePath())));
			HSSFSheet sheet = wb.getSheetAt(0);

//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		DecimalFormat dfm = new DecimalFormat("##0.00");
			DecimalFormat df = new DecimalFormat("##0");

			HSSFRow row = null;
			HSSFCell cell = null;
			int lastrow = sheet.getLastRowNum();
//		int lastcell;
			int fieldValuesSize = fieldValues.size();
			int fieldChildValuesSize = fieldValues.get(fieldValuesSize - 1).getFieldChildValues().size();
//		List<Map<String, ExcelDefinitionBean>> list = new ArrayList<Map<String, ExcelDefinitionBean>>();
			 list = new ArrayList<ExcelDefinitionBean>();
//		Map<String, ExcelDefinitionBean> map = new HashMap<String, ExcelDefinitionBean>();
			for (int i = 0; i <= lastrow; i++) {
				row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				if(i == 0){
					//TODO 导入excel头部与系统模板比对校验
					continue;
				}

				excelDefinitionBean = new ExcelDefinitionBean();

//			lastcell = row.getLastCellNum();
				String cellValue = "";
				for (int j = 0; j < fieldValuesSize - 1 + fieldChildValuesSize; j++) {
					cell = row.getCell(j);
					if(cell != null){
						//判断是否具有合并单元格
						boolean isMerge = ExcelUtil.isMergedRegion(sheet, i, cell.getColumnIndex());
						if(isMerge){
							cellValue = ExcelUtil.getMergedRegionValue(sheet, row.getRowNum(), cell.getColumnIndex());
						}else{
							switch (cell.getCellType()) {
								case HSSFCell.CELL_TYPE_NUMERIC:
									cellValue = df.format(cell.getNumericCellValue());
									break;
								case HSSFCell.CELL_TYPE_STRING:
									cellValue = cell.getStringCellValue();
									break;
								case HSSFCell.CELL_TYPE_BLANK:
									cellValue = "";
									break;
								default:
									break;
							}
						}
					}else{
						cellValue = "";
					}

					//cellValue放入excelDefinition中
					if(j < fieldValuesSize - 1){
						fieldValueBean = new FieldValueBean();
						fieldValue = new FieldValue();
						fieldValue = fieldValues.get(j);
						fieldValueBean.setName(fieldValue.getName());
						fieldValueBean.setTitle(fieldValue.getTitle());
						fieldValueBean.setNull(fieldValue.isNull());
						fieldValueBean.setValue(cellValue);
						excelDefinitionBean.getFieldValues().add(fieldValueBean);
					}else{
						if(j == fieldValuesSize - 1){
							fieldValueBean = new FieldValueBean();
						}
						fieldValue = new FieldValue();
						fieldValue = fieldValues.get(fieldValuesSize - 1);
						if(null != fieldValue.getFieldChildValues() && fieldValue.getFieldChildValues().size() > 0){
//						for(int d = 0; d < fieldValue.getFieldChildValues().size(); d++){
							fieldChildValue = new FieldChildValue();
							fieldChildValue = fieldValue.getFieldChildValues().get(j - (fieldValuesSize - 1));
							fieldChildValueBean = new FieldChildValueBean();
							fieldChildValueBean.setName(fieldChildValue.getName());
							fieldChildValueBean.setTitle(fieldChildValue.getTitle());
							fieldChildValueBean.setNull(fieldChildValue.isNull());
							fieldChildValueBean.setValue(cellValue);
							fieldValueBean.getFieldChildValues().add(fieldChildValueBean);
//						}
						}
						if(j == fieldValuesSize - 1 + fieldChildValuesSize - 1){
							excelDefinitionBean.getFieldValues().add(fieldValueBean);
						}
					}
				}
//			map.put(String.valueOf(i), excelDefinitionBean);
				list.add(excelDefinitionBean);
			}
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}finally {
			if (wb !=null){
				wb.close();
			}
		}

		
		return list;
	}
	
	/** 
	 * 处理EXCEL文件
	 * @param <T>
	 * @param <E>
	 * 
	 * @throws Exception 
	 */
	public static <T, E> List<Map<List<T>, List<E>>> getMapFromExcel(Class<T> t, Class<E> e, File file, String type) throws Exception{
		List<Map<List<T>, List<E>>> list = new ArrayList<Map<List<T>, List<E>>>();
//		Map<List<T>, List<E>> map = null;
		
//		List<T> listt = null;
//		List<E> liste = null;
		
		List<ExcelDefinitionBean> excelDefinitionBeanList = getExcelDefinitionBeanListFromExcel(file, type);
		
		List<FieldValueBean> fieldValues = null;
		List<FieldChildValueBean> fieldChildValues = null;
		
		Map<String, String> pssBuyBillMap = new HashMap<String, String>();
		Map<String, String> pssBuyBillDetailMap = new HashMap<String, String>();
		
		for(ExcelDefinitionBean excelDefinitionBean:excelDefinitionBeanList){
			//初始化
			T t1 = t.newInstance();
			E e1 = e.newInstance();
			List<T> listt = new ArrayList<T>();
			List<E> liste = new ArrayList<E>();
			Map<List<T>, List<E>> map = new HashMap<List<T>, List<E>>();
			fieldValues = excelDefinitionBean.getFieldValues();
			for(FieldValueBean fieldValueBean:fieldValues){
				if(null != fieldValueBean.getFieldChildValues() && fieldValueBean.getFieldChildValues().size() > 1){
					fieldChildValues = fieldValueBean.getFieldChildValues();
					for(FieldChildValueBean fieldChildValueBean:fieldChildValues){
						pssBuyBillDetailMap.put(fieldChildValueBean.getName(), fieldChildValueBean.getValue());
					}
					PssPublicUtil.setFieldValue(e1, pssBuyBillDetailMap);
					liste.add(e1);
					continue;
				}
				pssBuyBillMap.put(fieldValueBean.getName(), fieldValueBean.getValue());
			}
			PssPublicUtil.setFieldValue(t1, pssBuyBillMap);
			listt.add(t1);
			map.put(listt, liste);
			list.add(map);
		}
		
		return list;
	}
	
	/** 
	 * 处理EXCEL文件（过滤重复主信息）
	 * @param <T>
	 * @param <E>
	 * 
	 * @throws Exception 
	 */
	public static <T, E> Map<T, List<E>> getMapFromExcel(Class<T> t, Class<E> e, File file, String type, String uniqueColumn) throws Exception{
//		Map<List<T>, List<E>> mapFromExcel = getMapFromExcel(t, e, file, type);
		List<Map<List<T>, List<E>>> listFromExcel = getMapFromExcel(t, e, file, type);
		Map<String, Map<T, List<E>>> mapResult = new HashMap<String, Map<T, List<E>>>();
		
		Map<T, List<E>> mapTemp = null;
		
		Object value = null;
		
		for(Map<List<T>, List<E>> mapFromExcel:listFromExcel){
			for(Entry<List<T>, List<E>> map:mapFromExcel.entrySet()){
				mapTemp = new HashMap<T, List<E>>();
				mapTemp.put(map.getKey().get(0), map.getValue());
				
				value = PssPublicUtil.getValueByKey(map.getKey().get(0), uniqueColumn);
				
				String uniqueColumnValue = String.valueOf(value == null?"":value);
				if(null == uniqueColumnValue || "".equals(uniqueColumnValue)){
//					continue;
				}
				if(null != mapResult.get(uniqueColumnValue)){
//					mapResult.get(uniqueColumnValue).values().add(map.getValue());
					for(Entry<T, List<E>> mapResultValue:mapResult.get(uniqueColumnValue).entrySet()){
						mapResultValue.getValue().addAll((map.getValue()));
					}
				}else{
					mapResult.put(uniqueColumnValue, mapTemp);
				}
			}
		}
		
		mapTemp = new HashMap<T, List<E>>();
		for(Entry<String, Map<T, List<E>>> map:mapResult.entrySet()){
			mapTemp.putAll(map.getValue());
		}
		
		return mapTemp;
	}
	
	/** 
	 * 处理EXCEL文件（头 + 体）
	 * @param <T>
	 * @param <E>
	 * @throws Exception 
	 */
	public static <T, E> List<PssExcelResultBean<T, E>> getListResultFromExcel(Class<T> t, Class<E> e, File file, String type, String uniqueColumn) throws Exception{
		List<PssExcelResultBean<T, E>> listHead = getListResultFromExcel(file, type);
		
		List<PssExcelResultBean<T, E>> listBody = getListResultFromExcel(t, e, file, type);
		
		listHead.addAll(listBody);
		
		return listHead;
	}
	
	/** 
	 * 处理EXCEL文件（头部信息）
	 * @param <T>
	 * @param <E>
	 * @throws Exception 
	 */
	private static <T, E> List<PssExcelResultBean<T, E>> getListResultFromExcel(File file, String type) throws Exception{
		XMLExcelDefinitionReader xmlExcelDefinitionReader = XMLExcelDefinitionReader.getInstance(locations);
		ExcelDefinition excelDefinition = xmlExcelDefinitionReader.getRegistry().get(type);
		List<FieldValue> fieldValues = excelDefinition.getFieldValues();

		List<PssExcelResultBean<T, E>> listPssExcelResultBean = new ArrayList<PssExcelResultBean<T, E>>();
		PssExcelResultBean<T, E> pssExcelResultBean = null;
		List<PssExcelImportMsg> listPssExcelImportMsg = null;
		PssExcelImportMsg pssExcelImportMsg = null;
		StringBuilder stringBuilder = null;

		FieldValue fieldValue = null;
		FieldChildValue fieldChildValue = null;
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(file.getAbsolutePath())));
			HSSFSheet sheet = wb.getSheetAt(0);

			DecimalFormat df = new DecimalFormat("##0");

			HSSFRow row = null;
			HSSFCell cell = null;
			int lastcell;
			int fieldValuesSize = fieldValues.size();
			pssExcelResultBean = new PssExcelResultBean<T, E>();
			listPssExcelImportMsg = new ArrayList<PssExcelImportMsg>();
			int i = 0;
			row = sheet.getRow(i);
			if (row == null) {
				pssExcelImportMsg = new PssExcelImportMsg();
				pssExcelImportMsg.setRow(i + 1);
				pssExcelImportMsg.setColumn(0);
				pssExcelImportMsg.setColumnShow(0);
				stringBuilder = new StringBuilder();
				stringBuilder.append("导入模板内容为空请重新下载模板导入！");
				pssExcelImportMsg.setSb(stringBuilder);
				listPssExcelImportMsg.add(pssExcelImportMsg);
				pssExcelResultBean.setListMsg(listPssExcelImportMsg);
				pssExcelResultBean.setResult("0001");
				listPssExcelResultBean.add(pssExcelResultBean);
				//列名不匹配直接返回
				return listPssExcelResultBean;
			}
			String cellValue = "";
			//TODO 导入excel头部与系统模板比对校验
			lastcell = row.getLastCellNum();
			//首先判断导入EXCEL列数与模板列数是否一致
			int modelLength = fieldValuesSize - 1 + fieldValues.get(fieldValuesSize - 1).getFieldChildValues().size();
			if (modelLength != lastcell) {
				pssExcelImportMsg = new PssExcelImportMsg();
				pssExcelImportMsg.setRow(i + 1);
				pssExcelImportMsg.setColumn(lastcell);
				pssExcelImportMsg.setColumnShow(lastcell);
				stringBuilder = new StringBuilder();
				stringBuilder.append("模板列数与导入模板列数不一致请重新下载模板导入！");
				pssExcelImportMsg.setSb(stringBuilder);
				listPssExcelImportMsg.add(pssExcelImportMsg);
				pssExcelResultBean.setListMsg(listPssExcelImportMsg);
				pssExcelResultBean.setResult("0001");
				listPssExcelResultBean.add(pssExcelResultBean);
				//列名不匹配直接返回
				return listPssExcelResultBean;
			}
			for (int j = 0; j < lastcell; j++) {
				cell = row.getCell(j);
				switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC:
						cellValue = df.format(cell.getNumericCellValue());
						break;
					case HSSFCell.CELL_TYPE_STRING:
						cellValue = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_BLANK:
						cellValue = "";
						break;
					default:
						break;
				}

				if (j < fieldValuesSize - 1) {
					fieldValue = new FieldValue();
					fieldValue = fieldValues.get(j);
					//导入EXCEl头部名称与模板不一致
					if (!fieldValue.getTitle().equals(cellValue)) {
						pssExcelImportMsg = new PssExcelImportMsg();
						pssExcelImportMsg.setRow(i + 1);
						pssExcelImportMsg.setColumn(j + 1);
						pssExcelImportMsg.setColumnShow(lastcell);
						stringBuilder = new StringBuilder();
						stringBuilder.append("模板中第" + pssExcelImportMsg.getColumn() + "列列名不匹配，正确列名：" + fieldValue.getTitle() + ",不匹配的列名：" + cellValue + "，请设置与导入文件匹配的导入模板参数或者重新下载模板导入！");
						pssExcelImportMsg.setSb(stringBuilder);
						listPssExcelImportMsg.add(pssExcelImportMsg);
						pssExcelResultBean.setListMsg(listPssExcelImportMsg);
						pssExcelResultBean.setResult("0001");
						listPssExcelResultBean.add(pssExcelResultBean);
						//列名不匹配直接返回
						return listPssExcelResultBean;
					}
				} else {
					if (j == fieldValuesSize - 1) {
						fieldValue = new FieldValue();
						fieldValue = fieldValues.get(fieldValuesSize - 1);
					}
					if (null != fieldValue.getFieldChildValues() && fieldValue.getFieldChildValues().size() > 0) {
//					for(int d = 0; d < fieldValue.getFieldChildValues().size(); d++){
						fieldChildValue = new FieldChildValue();
						fieldChildValue = fieldValue.getFieldChildValues().get(j - (fieldValuesSize - 1));
						//导入EXCEl头部名称与模板不一致
						if (!fieldChildValue.getTitle().equals(cellValue)) {
							pssExcelImportMsg = new PssExcelImportMsg();
							pssExcelImportMsg.setRow(i + 1);
							pssExcelImportMsg.setColumn(j + 1);
							pssExcelImportMsg.setColumnShow(lastcell);
							stringBuilder = new StringBuilder();
							stringBuilder.append("模板中第" + pssExcelImportMsg.getColumn() + "列列名不匹配，正确列名：" + fieldValue.getTitle() + ",不匹配的列名：" + cellValue + "，请设置与导入文件匹配的导入模板参数或者重新下载模板导入！");
							pssExcelImportMsg.setSb(stringBuilder);
							listPssExcelImportMsg.add(pssExcelImportMsg);
							pssExcelResultBean.setListMsg(listPssExcelImportMsg);
							pssExcelResultBean.setResult("0001");
							listPssExcelResultBean.add(pssExcelResultBean);
							//列名不匹配直接返回
							return listPssExcelResultBean;
						}
//					}
					}
				}
			}
			pssExcelResultBean.setListMsg(listPssExcelImportMsg);
			listPssExcelResultBean.add(pssExcelResultBean);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			if (wb != null) {
				wb.close();
			}
		}
		return listPssExcelResultBean;
	}
	
	/** 
	 * 处理EXCEL文件（体部信息）
	 * @param <T>
	 * @param <E>
	 * 
	 * @throws Exception 
	 */
	public static <T, E> List<PssExcelResultBean<T, E>> getListResultFromExcel(Class<T> t, Class<E> e, File file, String type) throws Exception{
		
		List<ExcelDefinitionBean> excelDefinitionBeanList = getExcelDefinitionBeanListFromExcel(file, type);
		ExcelDefinitionBean excelDefinitionBean = null;
		
		List<FieldValueBean> fieldValueBeans = null;
		List<FieldChildValueBean> fieldChildValueBeans = null;
		FieldValueBean fieldValueBean = null;
		FieldChildValueBean fieldChildValueBean = null;
		
		List<PssExcelResultBean<T, E>> listPssExcelResultBean = new ArrayList<PssExcelResultBean<T, E>>();
		PssExcelResultBean<T, E> pssExcelResultBean = null;
		List<PssExcelImportMsg> listPssExcelImportMsg = null;
		PssExcelImportMsg pssExcelImportMsg = null;
		StringBuilder stringBuilder = null;
		
		for(int i = 0; i < excelDefinitionBeanList.size(); i++){
			excelDefinitionBean = excelDefinitionBeanList.get(i);
			
			pssExcelResultBean = new PssExcelResultBean<T, E>();
			listPssExcelImportMsg = new ArrayList<PssExcelImportMsg>();
			
			//初始化
			E e1 = e.newInstance();
			T t1 = t.newInstance();
			Map<String, String> EClassMap = new HashMap<String, String>();
			Map<String, String> TClassMap = new HashMap<String, String>();
			List<E> EList = new ArrayList<E>();
			
			fieldValueBeans = excelDefinitionBean.getFieldValues();
			int fieldValueBeansSize = fieldValueBeans.size();
			int fieldChildValuesSize = fieldValueBeans.get(fieldValueBeansSize - 1).getFieldChildValues().size();
			for(int j = 0; j < fieldValueBeansSize; j++){
				fieldValueBean = fieldValueBeans.get(j);
				
				if(null != fieldValueBean.getFieldChildValues() && fieldValueBean.getFieldChildValues().size() > 0){
					fieldChildValueBeans = fieldValueBean.getFieldChildValues();
					for(int k = 0; k < fieldChildValueBeans.size(); k++){
						fieldChildValueBean = fieldChildValueBeans.get(k);
						
						if(!fieldChildValueBean.isNull()){//必输项
							if(fieldChildValueBean.getValue() == null || "".equals(fieldChildValueBean.getValue())){
								pssExcelImportMsg =  new PssExcelImportMsg();
								pssExcelImportMsg.setRow(i + 2);
								pssExcelImportMsg.setColumn(j + k + 1);
								pssExcelImportMsg.setColumnShow(fieldValueBeansSize - 1 + fieldChildValuesSize);
								stringBuilder = new StringBuilder();
//								stringBuilder.append("模板中第" + pssExcelImportMsg.getRow() + "行、" + pssExcelImportMsg.getColumn() + "列值不能为空，请补全模板内容！");
								stringBuilder.append("第" + pssExcelImportMsg.getColumn() + "列，" + fieldChildValueBean.getTitle() + "不能为空！");
								pssExcelImportMsg.setSb(stringBuilder);
								listPssExcelImportMsg.add(pssExcelImportMsg);
							}
						}
						EClassMap.put(fieldChildValueBean.getName(), fieldChildValueBean.getValue());
					}
					PssPublicUtil.setFieldValue(e1, EClassMap);
					EList.add(e1);
					continue;
				}
				
				if(!fieldValueBean.isNull()){//必输项
					if(fieldValueBean.getValue() == null || "".equals(fieldValueBean.getValue())){
						pssExcelImportMsg =  new PssExcelImportMsg();
						pssExcelImportMsg.setRow(i + 2);
						pssExcelImportMsg.setColumn(j + 1);
						pssExcelImportMsg.setColumnShow(fieldValueBeansSize - 1 + fieldChildValuesSize);
						stringBuilder = new StringBuilder();
//						stringBuilder.append("模板中第" + pssExcelImportMsg.getRow() + "行、" + pssExcelImportMsg.getColumn() + "列值不能为空，请补全模板内容！");
						stringBuilder.append("第" + pssExcelImportMsg.getColumn() + "列，" + fieldValueBean.getTitle() + "不能为空！");
						pssExcelImportMsg.setSb(stringBuilder);
						listPssExcelImportMsg.add(pssExcelImportMsg);
					}
				}
				
				TClassMap.put(fieldValueBean.getName(), fieldValueBean.getValue());
			}
			PssPublicUtil.setFieldValue(t1, TClassMap);
			
			if(listPssExcelImportMsg.size() > 0){
				pssExcelResultBean.setResult("0002");
			}
			pssExcelResultBean.setListMsg(listPssExcelImportMsg);
			pssExcelResultBean.setT(t1);
			pssExcelResultBean.setListDetail(EList);
			pssExcelResultBean.setTotalColumnNum(fieldValueBeansSize - 1 + fieldChildValuesSize);
			listPssExcelResultBean.add(pssExcelResultBean);
		}
		
		return listPssExcelResultBean;
	}
	
	/**
	 * 生成校验EXCEL（导入文件头）
	 */
	public static HSSFWorkbook generateValidateExcelHead(File file, PssExcelResultBean<?, ?> pssExcelResultBean) throws Exception{
		HSSFWorkbook wb = null;
		HSSFWorkbook workbookNew = new HSSFWorkbook();
		try {
			wb = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(file.getAbsolutePath())));
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFSheet sheetNew = workbookNew.createSheet();//生成一个表格
//		workbook.setSheetName(0, excelDefinition.getSheetname());//设置表格标题及编码格式
//		sheet.setColumnWidth(0, 256*10+184);
			HSSFRow rowNew = sheetNew.createRow(0);
//		row.setHeight((short) 550);
//		rowNew = sheet.getRow(0);

			copyRow(workbookNew, sheet.getRow(0), rowNew, true);
			if (null != pssExcelResultBean.getListMsg() && pssExcelResultBean.getListMsg().size() > 0) {
				generateValidateExcel(workbookNew, pssExcelResultBean.getListMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			if (wb != null) {
				wb.close();
			}
			if (workbookNew != null) {
				workbookNew.close();
			}
		}
		return workbookNew;
	}
	
	/**
	 * 生成校验EXCEL（导入文件体）
	 */
	public static HSSFWorkbook generateValidateExcelBody(HSSFWorkbook workbook, PssExcelResultBean<?, ?> pssExcelResultBean) throws Exception{
		if(null != pssExcelResultBean.getListMsg() && pssExcelResultBean.getListMsg().size() > 0){
			workbook = generateValidateExcel(workbook, pssExcelResultBean.getListMsg());
		}
		
		return workbook;
	}
	
	/**
	 * 生成校验EXCEL（带错误信息）
	 */
	public static HSSFWorkbook generateValidateExcel(HSSFWorkbook workbook, List<PssExcelImportMsg> listMsg) throws Exception{
		HSSFSheet sheet = workbook.getSheetAt(0);
		for(PssExcelImportMsg pssExcelImportMsg:listMsg){
			HSSFRow row = sheet.getRow(pssExcelImportMsg.getRow() - 1);
			if(row == null){
				row = sheet.createRow(pssExcelImportMsg.getRow() - 1);
			}
			int cellNum = pssExcelImportMsg.getColumnShow();
			HSSFCell cell = row.getCell(cellNum);
			if(cell == null){
				cell = row.createCell(cellNum);
				cell.setCellValue(pssExcelImportMsg.getSb().toString());
			}else{
				cell.setCellValue(cell.getStringCellValue() + pssExcelImportMsg.getSb().toString());
			}
			//设置成红色
			HSSFCellStyle style = workbook.createCellStyle();
			Font fontRed = workbook.createFont();
			fontRed.setColor(HSSFColor.RED.index);
			style.setFont(fontRed);
			cell.setCellStyle(style);
		}
		
		return workbook;
	}
	
	/**
	 * 校验EXCEL（系统）
	 * @param <T>
	 * @param <E>
	 */
	public static <T, E> Map<Boolean, HSSFWorkbook> validateExcelByConfig(List<PssExcelResultBean<T, E>> listPssExcelResultBean, File file) throws Exception{
		Map<Boolean, HSSFWorkbook> map = new HashMap<Boolean, HSSFWorkbook>();
		Boolean validateResult = false;
		HSSFWorkbook workbook = null;
		PssExcelResultBean<T, E> pssExcelResultBean = null;
		for (int i = 0; i < listPssExcelResultBean.size(); i++) {
			pssExcelResultBean = new PssExcelResultBean<T, E>();
			if(i == 0){
				pssExcelResultBean = listPssExcelResultBean.get(i);
				workbook = ExcelUtil.generateValidateExcelHead(file, pssExcelResultBean);
				if(!"0000".equals(pssExcelResultBean.getResult())){
					validateResult = true;
					break;
				}
			}else{
				pssExcelResultBean = listPssExcelResultBean.get(i);
				if(!"0000".equals(pssExcelResultBean.getResult())){
					workbook = ExcelUtil.generateValidateExcelBody(workbook, pssExcelResultBean);
					validateResult = true;
				}
			}
		}
		//TODO HSSFWorkbook类型传递回去null
		map.put(validateResult, workbook);
		return map;
	}
	
	/**
	 * 校验EXCEL（系统）
	 * @param <T>
	 * @param <E>
	 */
	public static <T, E> Map<Boolean, HSSFWorkbook> validateExcelByConfig(List<PssExcelResultBean<T, E>> listPssExcelResultBean, HSSFWorkbook workbook) throws Exception{
		Map<Boolean, HSSFWorkbook> map = new HashMap<Boolean, HSSFWorkbook>();
		Boolean validateResult = false;
		PssExcelResultBean<T, E> pssExcelResultBean = null;
		for (int i = 1; i < listPssExcelResultBean.size(); i++) {//去掉头部
			pssExcelResultBean = listPssExcelResultBean.get(i);
			if(!"0000".equals(pssExcelResultBean.getResult())){
				workbook = ExcelUtil.generateValidateExcelBody(workbook, pssExcelResultBean);
				validateResult = true;
			}
		}
		map.put(validateResult, workbook);
		return map;
	}
	
	/**
	 * 生成校验EXCEL（保存文件到服务器）
	 */
	public static String saveValidateExcel(HSSFWorkbook workbook) throws Exception{
		String path = pathFolder + File.separator +"导入校验_";
		File file = null;
		while(true){
			String waterId = WaterIdUtil.getWaterId();
			file = new File(path + waterId + ".xls");
			if(!file.exists()){
				path += waterId + ".xls";
				break;
			}
		}
		
		FileOutputStream fileOutputStream = new FileOutputStream(path);
		workbook.write(fileOutputStream);
		fileOutputStream.close();
		return path;
	}
	
	/**
     * 行复制功能
     *
     * @param fromRow
     * @param toRow
     */
	public static void copyRow(HSSFWorkbook wb, Row fromRow, Row toRow, boolean copyValueFlag) {
		if(fromRow == null){
			return;
		}
		toRow.setHeight(fromRow.getHeight());
		for(Iterator<Cell> cellIt = fromRow.cellIterator(); cellIt.hasNext(); ) {
			Cell tmpCell = (Cell) cellIt.next();
			Cell newCell = toRow.createCell(tmpCell.getColumnIndex());
			copyCell(wb, tmpCell, newCell, copyValueFlag);
		}
		Sheet worksheet = fromRow.getSheet();
		for(int i = 0; i < worksheet.getNumMergedRegions(); i++) {
			CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
			if(cellRangeAddress.getFirstRow() == fromRow.getRowNum()) {
				CellRangeAddress newCellRangeAddress = new CellRangeAddress(toRow.getRowNum(), (toRow.getRowNum() +
						(cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())), cellRangeAddress
						.getFirstColumn(), cellRangeAddress.getLastColumn());
				worksheet.addMergedRegion(newCellRangeAddress);
			}
		}
	}

    /**
     * 复制单元格
     *
     * @param srcCell
     * @param distCell
     * @param copyValueFlag true则连同cell的内容一起复制
     */
    public static void copyCell(HSSFWorkbook wb, Cell srcCell, Cell distCell, boolean copyValueFlag) {
    	CellStyle newStyle = wb.createCellStyle();
    	CellStyle srcStyle = srcCell.getCellStyle();
    	newStyle.cloneStyleFrom(srcStyle);
//    	newStyle.setFont(wb.getFontAt(srcStyle.getFontIndex()));
    	//样式
    	distCell.setCellStyle(newStyle);
    	//评论
    	if(srcCell.getCellComment() != null) {
    		distCell.setCellComment(srcCell.getCellComment());
    	}
    	// 不同数据类型处理
    	distCell.setCellType(srcCell.getCellType());
    	if(copyValueFlag) {
    		DecimalFormat df = new DecimalFormat("##0");
    		/*if(srcCellType == CellType.NUMERIC) {
    			if(DateUtil.isCellDateFormatted(srcCell)) {
    				distCell.setCellValue(srcCell.getDateCellValue());
    			} else {
    				distCell.setCellValue(srcCell.getNumericCellValue());
    			}
    		} else if(srcCellType == CellType.STRING) {
    			distCell.setCellValue(srcCell.getRichStringCellValue());
    		} else if(srcCellType == CellType.BLANK) {

    		} else if(srcCellType == CellType.BOOLEAN) {
    			distCell.setCellValue(srcCell.getBooleanCellValue());
    		} else if(srcCellType == CellType.ERROR) {
    			distCell.setCellErrorValue(srcCell.getErrorCellValue());
    		} else if(srcCellType == CellType.FORMULA) {
    			distCell.setCellFormula(srcCell.getCellFormula());
    		} else {
    		}*/
    		switch (srcCell.getCellType()) {
    		case HSSFCell.CELL_TYPE_NUMERIC:
    			distCell.setCellValue(df.format(srcCell.getNumericCellValue()));
    			break;
    		case HSSFCell.CELL_TYPE_STRING:
    			distCell.setCellValue(srcCell.getStringCellValue());
    			break;
    		case HSSFCell.CELL_TYPE_BLANK:
    			distCell.setCellValue("");
    			break;
    		default:
    			break;
    		}
    	}
    }
    
    /** 
	 * 处理EXCEL文件（过滤重复主信息）
	 * @param <T>
	 * @param <E>
	 * 
	 * @throws Exception 
	 */
	public static <T, E> List<PssExcelResultBean<T, E>> getListResultFromExcel(List<PssExcelResultBean<T, E>> listPssExcelResultBean, String uniqueColumn) throws Exception{
		List<PssExcelResultBean<T, E>> listPssExcelResultBeanSimple = new ArrayList<PssExcelResultBean<T, E>>();
		Map<String, Map<T, List<E>>> mapResults = new HashMap<String, Map<T, List<E>>>();
		Map<T, List<E>> mapTemp = null;
		Object value = null;
		for(PssExcelResultBean<T, E> pssExcelResultBean:listPssExcelResultBean){
			value = PssPublicUtil.getValueByKey(pssExcelResultBean.getT(), uniqueColumn);
			String uniqueColumnValue = String.valueOf(value == null?"":value);
			if(null == uniqueColumnValue || "".equals(uniqueColumnValue)){
				continue;
			}
			if(null != mapResults.get(uniqueColumnValue)){
				for(Entry<T, List<E>> mapResultValue:mapResults.get(uniqueColumnValue).entrySet()){
					mapResultValue.getValue().addAll(pssExcelResultBean.getListDetail());
				}
			}else{
				mapTemp.put(pssExcelResultBean.getT(), pssExcelResultBean.getListDetail());
				mapResults.put(uniqueColumnValue, mapTemp);
			}
		}
		
		PssExcelResultBean<T, E> pssExcelResultBean = null;
		for(Map<T, List<E>> mapResult:mapResults.values()){
			for(Entry<T, List<E>> mapPssExcelResultBean:mapResult.entrySet()){
				pssExcelResultBean = new PssExcelResultBean<T, E>();
				pssExcelResultBean.setT(mapPssExcelResultBean.getKey());
				pssExcelResultBean.setListDetail(mapPssExcelResultBean.getValue());
				listPssExcelResultBeanSimple.add(pssExcelResultBean);
			}
		}
		return listPssExcelResultBeanSimple;
	}
	
}

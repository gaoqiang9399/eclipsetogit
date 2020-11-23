package app.component.pfs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.component.pfs.entity.CusFinSubjectData;
import app.component.sys.entity.SysUser;
import cn.mftcc.util.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import app.component.pfs.entity.CusFinMain;
import app.component.pfs.entity.CusFinParm;
import app.tech.upload.UploadUtil;
import cn.mftcc.util.PropertiesUtil;
import cn.mftcc.util.StringUtil;
import org.apache.poi.ss.usermodel.Cell;

/**
 * 功能描述：Poi导出excel，路径是从upload.properties中读取
 * @author czk
 * @date 2016-11-15
 *
 */
public class PoiExportExcelUtil {
	
	private FileInputStream file = null;
	private HSSFWorkbook workbook = null;
	
	private HSSFSheet sheet = null;
	private List<CusFinParm> cusFinParmList = null;
	public PoiExportExcelUtil(){
		
	}
	
	/**
	 * 功能描述：初始化工作区、sheet脚本，默认为第一个sheet
	 * @param path 文件路径
	 */
	public PoiExportExcelUtil(List<CusFinParm> cusFinParmList) {
		try {
			this.cusFinParmList = cusFinParmList;
			if(this.cusFinParmList == null){
				cusFinParmList = new ArrayList();
			}
			workbook = new HSSFWorkbook();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author LJW
	 * @Description: 获取财务报表excel/xml模板路径
	 * @param 1:资产负债表模板 2:利润分配表模板  3:现金流量表模板   4:资产负债表、利润分配表和现金流量表的总模板
	 * date 2017-4-13
	 */
	public String getFileModel(String downType,String fileType) throws Exception{
		String path = PropertiesUtil.getUploadProperty("uploadFinModelFilePath");
//		String CurrentClassFilePath =this.getClass().getResource("").getPath(); 
//		int lastpath=CurrentClassFilePath.lastIndexOf("util/");
//		String web_rootPath=CurrentClassFilePath.substring(0,lastpath);
//		String path = web_rootPath + "template";
		try {
			if(StringUtil.isEmpty(path)){
				return null;
			}
			File file = new File(path);
			String fileName = ""; 
			if ("1".equals(downType)) {
				fileName = "finCapTemplate.xls";
				if ("xml".equals(fileType)) {
					fileName = "finCapTemplate.xml";
				}
			}
			if ("2".equals(downType)) {
				fileName = "finProTemplate.xls";
				if ("xml".equals(fileType)) {
					fileName = "finProTemplate.xml";
				}
			}
			if ("3".equals(downType)) {
				fileName = "finCashTemplate.xls";
				if ("xml".equals(fileType)) {
					fileName = "finCashTemplate.xml";
				}
			}
			if ("4".equals(downType)) {
				fileName = "finTemplate.xls";
				if ("xml".equals(fileType)) {
					fileName = "finTemplate.xml";
				}
			}
			// 判断文件是否存在
			String destFile = path + File.separator + fileName;
			file = new File(destFile);
			if (!file.exists()) {
				System.out.println("PoiExportExcelUtil:"+destFile);
				return null;
			}
			return destFile;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * @author LJW
	 * @Description: 获取个人名下企业财务报表excel/xml模板路径
	 * @param 1:资产负债表模板 2:利润分配表模板  3:现金流量表模板   4:资产负债表、利润分配表和现金流量表的总模板
	 * date 2017-4-13
	 */
	public String getPersonFileModel(String downType,String fileType) throws Exception{
		String path = PropertiesUtil.getUploadProperty("uploadFinModelFilePath");
//		String CurrentClassFilePath =this.getClass().getResource("").getPath(); 
//		int lastpath=CurrentClassFilePath.lastIndexOf("util/");
//		String web_rootPath=CurrentClassFilePath.substring(0,lastpath);
//		String path = web_rootPath + "template";
		try {
			if(StringUtil.isEmpty(path)){
				return null;
			}
			File file = new File(path);
			String fileName = ""; 
			if ("1".equals(downType)) {
				fileName = "finPersonCapTemplate.xls";
				if ("xml".equals(fileType)) {
					fileName = "finPersonCapTemplate.xml";
				}
			}
			if ("2".equals(downType)) {
				fileName = "finPersonProTemplate.xls";
				if ("xml".equals(fileType)) {
					fileName = "finPersonProTemplate.xml";
				}
			}
			if ("3".equals(downType)) {
				fileName = "finPersonCashTemplate.xls";
				if ("xml".equals(fileType)) {
					fileName = "finPersonCashTemplate.xml";
				}
			}
			if ("4".equals(downType)) {
				fileName = "finPersonTemplate.xls";
				if ("xml".equals(fileType)) {
					fileName = "finPersonTemplate.xml";
				}
			}

			if ("5".equals(downType)) {
				fileName = "personSubjectBalTemplate.xls";
			}
			// 判断文件是否存在
			String destFile = path + File.separator + fileName;
			file = new File(destFile);
			if (!file.exists()) {
				System.out.println("PoiExportExcelUtil:"+destFile);
				return null;
			}
			return destFile;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	public void initFileModel(String filePath,CusFinMain cusFinMain) throws Exception{
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		workbook = new HSSFWorkbook(new FileInputStream(filePath));
	    for(int i = 0; i < workbook.getNumberOfSheets(); i++) {//获取每个Sheet表
			HSSFSheet sheet=workbook.getSheetAt(i);
			HSSFRow row=sheet.getRow(2);
			HSSFCell cell=row.getCell(1);
			cell.setCellValue(cusFinMain.getCusName());
	    }
		FileOutputStream os;
		os = new FileOutputStream(filePath);
		workbook.write(os);
		os.close();
	}
	public void initPersonFileModel(String filePath,CusFinMain cusFinMain,List<Map<String, String>> corpList) throws Exception{
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		workbook = new HSSFWorkbook(new FileInputStream(filePath));
		for(int i = 0; i < workbook.getNumberOfSheets(); i++) {//获取每个Sheet表
			HSSFSheet sheet=workbook.getSheetAt(i);
			if ("Sheet2".equals(sheet.getSheetName())) {
				for (int j = 0; j < 100; j++) { //清理历史数据
					HSSFRow row=sheet.getRow(1+j)!=null?sheet.getRow(1+j):sheet.createRow(1+j);
					HSSFCell cell=row.getCell(0)!=null?row.getCell(0):row.createCell(0);
					HSSFCell cell2 = row.getCell(1)!=null?row.getCell(1):row.createCell(1);
					if(StringUtil.isBlank(cell.getStringCellValue())&&StringUtil.isBlank(cell2.getStringCellValue())){
						break;
					}else{
						cell.setCellValue("");
						cell2.setCellValue("");
					}
				}
				for (int j = 0; j < corpList.size(); j++) {
					HSSFRow row=sheet.getRow(1+j)!=null?sheet.getRow(1+j):sheet.createRow(1+j);
					Map<String, String> map = corpList.get(j);
					HSSFCell cell=row.getCell(0)!=null?row.getCell(0):row.createCell(0);
					HSSFCell cell2 = row.getCell(1)!=null?row.getCell(1):row.createCell(1);
					cell.setCellValue(map.get("corpName"));
					cell2.setCellValue(map.get("corpNo"));
				}
			}
			
		}
		FileOutputStream os;
		os = new FileOutputStream(filePath);
		workbook.write(os);
		os.close();
	}


	public String getFilePath(String fileName) throws Exception{
		String CurrentClassFilePath =this.getClass().getResource("").getPath(); 
		int lastpath=CurrentClassFilePath.lastIndexOf("util/");
		String web_rootPath=CurrentClassFilePath.substring(0,lastpath);
		String path = web_rootPath + "template";
		try {
			if(StringUtil.isEmpty(path)){
				return null;
			}
			String destFile = path + File.separator + fileName;
			return destFile;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	 public HSSFWorkbook getHSSFWorkbook(String sheetName,String []title,String [][]values){
         // 第一步，创建一个webbook，对应一个Excel文件  
        if(workbook == null){
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
        //创建标题
        for(int i=0;i<title.length;i++){
            cell = row.createCell(i);  
            cell.setCellValue(title[i]);  
            cell.setCellStyle(style);  
        }
        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1); 
            for(int j=0;j<values[i].length;j++){
                 row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return workbook;
    }

	public Map<String,Object> resolveSubjectBalModel(String filePath,String cusNo,String cusType) throws Exception {
		Map<String,Object> resMap = new HashMap<String,Object>();
		HSSFWorkbook hwb =null;
		try{
			resMap.put("flag","success");
			StringBuffer msg = new StringBuffer();

			FileInputStream is = new FileInputStream(filePath);
			hwb = new HSSFWorkbook(is);
			HSSFSheet childSheet = hwb.getSheetAt(0);
			//解析报表主信息表
			CusFinMain cusFinMain = new CusFinMain();
			cusFinMain.setCusNo(cusNo);
			HSSFRow row = childSheet.getRow(2);
			//客户名称
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String cusName =row.getCell(1).getStringCellValue();
			if("2".equals(cusType)){
				String corpNo=row.getCell(5).getStringCellValue();
				cusFinMain.setRelationCorpNo(corpNo);
				cusFinMain.setRelationCorpName(cusName);
			}else{
				cusFinMain.setCusName(cusName);
			}
			//报表类型
			row = childSheet.getRow(3);
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String finRptType =row.getCell(1).getStringCellValue();
			if("月报".equals(finRptType)){
				cusFinMain.setFinRptType("1");
			}else if("季报".equals(finRptType)){
				cusFinMain.setFinRptType("2");
			}else if("年报".equals(finRptType)){
				cusFinMain.setFinRptType("3");
			}else {
			}
			//报表日期
			row = childSheet.getRow(4);
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String finRptDate =row.getCell(1).getStringCellValue();
			if(StringUtil.isEmpty(finRptDate)) {
				msg.append("报表日期为空<br>");
				resMap.put("msg",msg.toString());
				resMap.put("flag","error");
				return resMap;
			}else {
				if(("月报".equals(finRptType) && finRptDate.length() != 6) || ("年报".equals(finRptType) && finRptDate.length() != 4)){
					msg.append("报表日期与报表类型不符<br>");
					resMap.put("msg",msg.toString());
					resMap.put("flag","error");
					return resMap;
				}
			}
			cusFinMain.setFinRptDate(finRptDate);
			//是否审计
			row = childSheet.getRow(5);
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			String ifAud =row.getCell(1).getStringCellValue();
			if("未审计".equals(ifAud)){
				cusFinMain.setIfAud("0");
			}
			if("已审计".equals(ifAud)){
				cusFinMain.setIfAud("1");
				//审计单位
				row = childSheet.getRow(6);
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				String audOrg =row.getCell(1).getStringCellValue();
				cusFinMain.setAudOrg(audOrg);
				//审计意见
				row = childSheet.getRow(7);
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				String audIdea =row.getCell(1).getStringCellValue();
				cusFinMain.setAudIdea(audIdea);
				//审计意见
				row = childSheet.getRow(8);
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				String audDate =row.getCell(1).getStringCellValue();
				cusFinMain.setAudDate(audDate);
			}
			resMap.put("cusFinMain",cusFinMain);

			List<CusFinSubjectData>  list = new ArrayList<CusFinSubjectData>();
			CusFinSubjectData cusFinSubjectData= null;
			for (int j = 11; j < childSheet.getLastRowNum(); j++) {
				row = childSheet.getRow(j);
				if (row != null) {
					Cell cell= row.getCell(0);
					if(cell!=null){
						//科目编号
						row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
						String subjectNo = row.getCell(0).getStringCellValue();
						if (StringUtil.isNotEmpty(subjectNo)) {
							cusFinSubjectData = new CusFinSubjectData();
//						//科目编号
//						row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
//						String subjectNo = row.getCell(0).getStringCellValue();
							//科目名称
							row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
							String subjectName = row.getCell(1).getStringCellValue();
							//款项性质
							row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
							String amtProperties = row.getCell(2).getStringCellValue();
							//金额
							row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
							String subjectAmt = row.getCell(3).getStringCellValue();
							if(StringUtil.isEmpty(subjectAmt)){
								subjectAmt = "0.00";
							}
							cusFinSubjectData.setCusNo(cusNo);
							cusFinSubjectData.setCusName(cusName);
							cusFinSubjectData.setFinRptType(cusFinMain.getFinRptType());
							cusFinSubjectData.setFinRptYear(finRptDate.substring(0, 4));
							cusFinSubjectData.setFinRptDate(finRptDate);
							cusFinSubjectData.setSubjectNo(subjectNo);
							cusFinSubjectData.setSubjectName(subjectName);
							String[] subjectNames = subjectName.split("_");
							int len = subjectNames.length;
							cusFinSubjectData.setSubjectCusName(subjectNames[len-1]);

							//处理科目编号（因为科目编号可能以不同的分割符分割，4-2-2规则）
							subjectNo = subjectNo.replaceAll("\\.","");
							subjectNo = subjectNo.replaceAll("-","");
							subjectNo = subjectNo.replaceAll("_","");
							subjectNo = subjectNo.replaceAll("/","");
							//计算科目级别
							int subjectNoLen = subjectNo.length();
							int subLev = (subjectNoLen-4)/2+1;
							String lev = subLev+"";
							cusFinSubjectData.setSubjectLev(lev);//科目级别
							if(!"1".equals(lev)){
								//一级科目是4位
								String subjectFirstNo =subjectNo.substring(0,4);
								cusFinSubjectData.setSubjectFirstNo(subjectFirstNo);
							}

							cusFinSubjectData.setAmtProperties(amtProperties);
							cusFinSubjectData.setSubjectAmt(Double.valueOf(subjectAmt));
							list.add(cusFinSubjectData);
						}
					}
				}
			}
			resMap.put("cusFinSubjectDataList", list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} finally {
			try {
				if (hwb != null) {
					hwb.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return resMap;
	}
    
}

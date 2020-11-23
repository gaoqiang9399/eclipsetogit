package app.component.finance.paramset.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.jexl2.UnifiedJEXL;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import app.base.ServiceException;
import app.component.finance.account.entity.CwComItem;
import app.component.finance.cwtools.feign.CwToolsFeign;
import app.component.finance.paramset.feign.CwInitBalFeign;
import app.component.finance.util.CWEnumBean;
import cn.mftcc.util.StringUtil;
import org.apache.poi.ss.util.CellRangeAddress;

public class InitExcleUtil {

	
	public static String[] getreaderInitExcleResult(InputStream in,CwInitBalFeign cwInitBalFeign, String finBooks,String regNo,String regName) throws ServiceException {
		String[] flagValue = new String[2];// 错误信息
		flagValue[0] = "0000";
		POIFSFileSystem fs = null;
		HSSFWorkbook wb = null;
		try {
			Map<String, String[]> map = new HashMap<String, String[]>();
			// 检查是否可以执行科目余额导入
			// 已经执行过结账不允许再进行余额初始化操作
			fs = new POIFSFileSystem(in);
			wb = new HSSFWorkbook(fs);
			DecimalFormat myFormat2 = new DecimalFormat("##0.00");
			DecimalFormat myF = new DecimalFormat("##0");
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = null;
			HSSFCell cell = null;
			int rowNum, cellNum;
			int i;
			rowNum = sheet.getLastRowNum();
			// 科目列表数据列表
			ArrayList<String> listParm = null;
			boolean flag = true;
			for (i = 2; i <= rowNum; i++) {
				if (flag == false) {
					break;
				}
				row = sheet.getRow(i);
				cellNum = row.getLastCellNum();
				listParm = new ArrayList<String>();
				for (int j = 0; j <= cellNum; j++) {
					String temp = "";
					cell = row.getCell(j);
					if (j != 0 && null == cell) {// 如果第一列不为空跳过
						listParm.add("0");
						continue;
					}
					if (j == 0 && null == cell) {// 如果第一列为空
						flagValue[1] = "第 " + (i + 1) + " 行 " + (j + 1) + " 列为空，请修改模版再重试！";
						flag = false;
						break;
					}
					int cellType = cell.getCellType();
					if (j == 0 || j == 5 || j == 10) {// 验证科目号
						// *************************************要修改************************************************
						if (cellType == 0) {// 数字类型
							temp = (cell.getNumericCellValue() + "").trim();
						} else {
							temp = cell.getStringCellValue().trim(); // 获取no
						}
						// if
						// (StringUtil.isNotEmpty(temp)&&!testParttern(temp.trim()))
						// {
						// flagValue[1] = "第 " + (i + 1) + " 行 " + (j + 1) + "
						// 列为非数字，请修改模版再重试！";
						// flag = false;
						// break;
						// }
						// if(StringUtil.isNotEmpty(temp)){
						// temp = myF.format(Double.valueOf(temp));
						// if (temp.length() != 4 && temp.length() != 6
						// && temp.length() != 8) {
						// flagValue[1] = "第 " + (i + 1)+ " 行 "+ (j + 1)+ "
						// 列科目号不符合科目规则，请修改模版再重试！";
						// flag = false;
						// break;
						// }
						// }
					}
					if (j == 1 || j == 6 || j == 11) {// 验证科目名称
						temp = cell.getStringCellValue().trim().toLowerCase();
						if (StringUtil.isNotEmpty(temp)) {
							if ("".equals(temp) && null == temp) {
								flagValue[1] = "第 " + (i + 1) + " 行 " + (j + 1) + " 列科目名称不规则，请修改模版再重试！";
								flag = false;
								break;
							}
						}
					}
					if (j == 2 || j == 7 || j == 12) {// 验证科目余额方向
						temp = cell.getStringCellValue().trim();
						if (StringUtil.isNotEmpty(temp)) {
							if (!"借".equals(temp) && !"贷".equals(temp)) {
								flagValue[0] = "0006";
								flagValue[1] = (i + 1) + ", " + (j + 1);
								// flagValue[1] = "第 " + (i + 1) + " 行 " + (j + 1)
								// + " 列科目余额方向不规则，请修改模版再重试。";
								flag = false;
								break;
							}
						}
					}
					if (j == 3 || j == 8 || j == 13) {
						// getStringCellValue(cell);
						if (cellType == 0) {// 数字类型
							flagValue[1] = "第 " + (i + 1) + " 行 " + (j + 1) + " 列为非为文本格式，请修改模版再重试！";
							flag = false;
							break;
						} else {
							// KANG
							temp = cell.getStringCellValue().trim();
							if (StringUtil.isNotEmpty(temp) && !doTestParttern(temp.trim())) {// 验证当前借、贷余额
								flagValue[1] = "第 " + (i + 1) + " 行 " + (j + 1) + " 列为非数字金额类型，请修改模版再重试";
								flag = false;
								break;
							}
						}
						if (StringUtil.isNotEmpty(temp)) {
							temp = myFormat2.format(Double.valueOf(temp.trim()));
							if (temp.length() > 17) {
								flagValue[1] = "第 " + (i + 1) + " 行 " + (j + 1) + " 列模版金额过大，请修改模版再重试";
								flag = false;
								break;
							}
						}
					}
					listParm.add(temp);// 添加此行此列的数据
				}
				if (!flag) {
					break;
				}
				if (map.containsKey(listParm.get(0)) || map.containsKey(listParm.get(5)) || map.containsKey(listParm.get(10))) {// 检查是否存在相同数据
					flagValue[1] = "第" + (i + 1) + "行科目号为" + StringUtil.KillNull(listParm.get(0)) + StringUtil.KillNull(listParm.get(5)) + StringUtil.KillNull(listParm.get(10)) + "的科目存在重复数据，请修改模版再重试!";
					flag = false;
					break;
				}
				if (StringUtil.isNotEmpty(listParm.get(0))) {
                    map.put(listParm.get(0), new String[] { listParm.get(0), listParm.get(1), listParm.get(2), listParm.get(3) });
                }
				if (StringUtil.isNotEmpty(listParm.get(5))) {
                    map.put(listParm.get(5), new String[] { listParm.get(5), listParm.get(6), listParm.get(7), listParm.get(8) });
                }
				if (StringUtil.isNotEmpty(listParm.get(10))) {
                    map.put(listParm.get(10), new String[] { listParm.get(10), listParm.get(11), listParm.get(12), listParm.get(13) });
                }
				listParm = null;
			} // 循环读取表格中一列数据到map中
			if (flag) {// 如果数据符合规则
				String[] rsult = cwInitBalFeign.doDealMapData(map, finBooks, regNo,regName);// 处理结果
				if ("1111".equals(rsult[0])) {// 有错误
					flagValue[0] = "0003";
					flagValue[1] = rsult[1];
				} else if ("2222".equals(rsult[0])) {// 借贷平衡的校验有误
					flagValue[0] = "0005";
					flagValue[1] = rsult[1];
				}else {
				}
			} else {
				if (!"0006".equals(flagValue[0])) {// 除0006以外的错误
					flagValue[0] = "0003";
				}
			}
		} catch (Exception e) {
			flagValue[0] = "1111";// 系统异常
			flagValue[1] = "系统异常!";
			throw new ServiceException(e);
		}
		finally {
			try{
				if(wb != null) {
					wb.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
				flagValue[0] = "1111";// 系统异常
				flagValue[1] = e.getMessage();
			}
		}
		return flagValue;
	}
	public static boolean doTestParttern(String val) {
		// 表达式的功能：验证必须为数字（整数或小数）
		String pattern = "[-]?+[0-9]+(.[0-9]+)?";
		// 对()的用法总结：将()中的表达式作为一个整体进行处理，必须满足他的整体结构才可以。
		// (.[0-9]+)? ：表示()中的整体出现一次或一次也不出现
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(val);
		boolean b = m.matches();
		return b;
	}
	
	public static HSSFWorkbook getHSSFWorkbook(CwToolsFeign cwToolsFeign,String finBooks) throws ServiceException {
		HSSFWorkbook demoWorkBook= new HSSFWorkbook();//此对象可以生成表格
		try {
			CwComItem cwComItem=new CwComItem();
			cwComItem.setSubAccYn("N");//没有下级科目
			cwComItem.setAccType("1");
			List<CwComItem> list1=cwToolsFeign.getCwComListByProperty(cwComItem,finBooks);//资产类明细科目
			cwComItem.setAccType("2");
			List<CwComItem> list2=cwToolsFeign.getCwComListByProperty(cwComItem,finBooks);//负债类明细科目
			cwComItem.setAccType("4");
			List<CwComItem> list3=cwToolsFeign.getCwComListByProperty(cwComItem,finBooks);//所有者权益类明细科目
			HSSFSheet sheet = demoWorkBook.createSheet();// 生成一个表格
			sheet.protectSheet("dxhm");
			demoWorkBook.setSheetName(0, "财务余额初始化");// 设置表格标题及编码格式
			HSSFRow row = sheet.createRow(0);
			sheet.setDefaultColumnWidth(15);
			HSSFCell cell = null;
			//查出三类科目的List<Map<String,String>>
			int len1=0,len2=0,len3=0,tmp=0;
			List<List<String>> list=null;
			//资产类
///////////////////////////辅助核算项处理————————————————————————
			if(null!=list1&&list1.size()>0){
				len1=list1.size();
			}
			//负债类
/////////////////////////////辅助核算项处理————————————————————————
			if(null!=list2&&list2.size()>0){
			len2=list2.size();
			}
			//所有者权益类
///////////////////////////辅助核算项处理————————————————————————
			if(null!=list3&&list3.size()>0){
				len3=list3.size();
			}
			if(len1==0&&len2==0&&len3==0){
				throw new NullPointerException("没有查询到任何可以进行余额初始化的科目。");
			}
			//取得几个list中的最大的条数
			if(len1>len2){
				if(len1>len3){
					tmp=len1;
				}else{
					tmp=len3;
				}
			}else{
				if(len2>len3){
					tmp=len2;
				}else{
					tmp=len3;
				}
			}
			list = new ArrayList<List<String>>(tmp);
			List<String> lts=null;
			for (int i = 0; i < tmp; i++) {
				lts=new ArrayList<String>(12);
				// 资产类
				if(i<len1){
					lts.add(list1.get(i).getAccNo());
					lts.add(list1.get(i).getAccName());
					lts.add(CWEnumBean.JIEDAITYPE.getTypeDesc(list1.get(i).getDcInd()));
					lts.add("0");
				}else{
					lts.add("");
					lts.add("");
					lts.add("");
					lts.add("");
				}
				lts.add("");
				// 负债类
				if(i<len2){
					lts.add(list2.get(i).getAccNo());
					lts.add(list2.get(i).getAccName());
					lts.add(CWEnumBean.JIEDAITYPE.getTypeDesc(list2.get(i).getDcInd()));
					lts.add("0");
				}else{
					lts.add("");
					lts.add("");
					lts.add("");
					lts.add("");
				}
				lts.add("");
				// 所有者权益类
				if(i<len3){
					lts.add(list3.get(i).getAccNo());
					lts.add(list3.get(i).getAccName());
					lts.add(CWEnumBean.JIEDAITYPE.getTypeDesc(list3.get(i).getDcInd()));
					lts.add("0");
				}else{
					lts.add("");
					lts.add("");
					lts.add("");
					lts.add("");
				}
				list.add(lts);
			}
			if (null == list) {
				throw new Exception("数据为空");
			}
			int len = list.size();
			ArrayList<String> listParm = new ArrayList<String>();
			listParm.add("科目代码");
			listParm.add("科目名称");
			listParm.add("科目方向");
			listParm.add("期初余额");
			listParm.add("");
			listParm.add("科目代码");
			listParm.add("科目名称");
			listParm.add("科目方向");
			listParm.add("期初余额");
			listParm.add("");
			listParm.add("科目代码");
			listParm.add("科目名称");
			listParm.add("科目方向");
			listParm.add("期初余额");
			String[] arrys1 = new String[listParm.size()];// 表格题头
			listParm.toArray(arrys1);
			// 定义单元格格式
			HSSFCellStyle cellStyleContents = demoWorkBook.createCellStyle();
			cellStyleContents.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 表格细边框
			cellStyleContents.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyleContents.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyleContents.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			// 定义数据格式
			// HSSFDataFormat df = demoWorkBook.createDataFormat();
			cellStyleContents.setDataFormat(HSSFDataFormat
					.getBuiltinFormat("text"));
			// cellStyleContents.setFillForegroundColor((short) 13);// 设置背景色
			// cellStyleContents.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			// 设置单元格高度
			///////////////
			HSSFFont font2 = demoWorkBook.createFont();
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
			font2.setFontHeightInPoints((short) 14);
			// 定义单元格格式
			HSSFCellStyle headStyle = demoWorkBook.createCellStyle();
			headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 表格细边框
			headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			// 定义数据格式
			// HSSFDataFormat df = demoWorkBook.createDataFormat();
			headStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
			// 设定上下居中，垂直居中
			headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			headStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
			headStyle.setFont(font2);
			headStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);// 设置背景色
			headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			// 分类信息
			row = sheet.createRow(0);
			row.setHeight((short) 450);
			
			for (int i = 0; i < listParm.size(); i++) {
				cell = row.createCell(i);
				if(i!=4&&i!=9){
					cell.setCellStyle(headStyle);
				}
			}
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));//锁定列
			sheet.addMergedRegion(new CellRangeAddress(0,0,5,8));
			sheet.addMergedRegion(new CellRangeAddress(0,0,10,13));
			sheet.createFreezePane(0,2,0,2);//冻结前两行行
			String[] title_type=new String[]{"资产类","负债类","所有者权益类"};
			row.getCell(0).setCellValue(title_type[0]);
			row.getCell(5).setCellValue(title_type[1]);
			row.getCell(10).setCellValue(title_type[2]);
			// 表头信息
			row = sheet.createRow(1);
			row.setHeight((short) 400);
			for (int i = 0; i < listParm.size(); i++) {
				cell = row.createCell(i);
				cell.setCellValue(arrys1[i]);
				if(null!=arrys1[i]&&!"".equals(arrys1[i])){
					cell.setCellStyle(headStyle);
				}
			}
			// 定义锁定单元格格式
			HSSFCellStyle cellStyleContents1 = demoWorkBook.createCellStyle();
			cellStyleContents1.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 表格细边框
			cellStyleContents1.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyleContents1.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyleContents1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyleContents1.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
			cellStyleContents1.setLocked(false);
			int klen = 0,kleng = 0;
			List<String> lt = null;
			int yz = 0;
			for (int i = 0; i < len; i++) {
				row = sheet.createRow(i + 2);
				row.setHeight((short) 350);
				lt = list.get(i);
				int lens = lt.size();
				for (int j = 0; j < lens; j++) {
					cell = row.createCell(j);
					cell.setCellType(1);
					if(null!=lt.get(j)&&!"".equals(lt.get(j))){
						if(j==3||j==8||j==13){
							cell.setCellStyle(cellStyleContents1); // 不锁定
						}else{
							cell.setCellStyle(cellStyleContents); // 锁定
						}
					}
					cell.setCellValue(lt.get(j));
					if (j == 0) {//第一列
						int lensss = lt.get(j).length();
						if (kleng < lensss) {
							kleng = lensss;
						}
					}
					if (j == 1) {//第二列的长度
						int lenss = lt.get(j).length();
						if (klen < lenss) {
							klen = lenss;
						}
					}
				}
				if (yz == 0) {
					cell = row.createCell(14);
					cell.setCellType(1);
					cell.setCellValue("如：10000000,不需要金额分隔符。");
					yz++;
				}
			}
			//设置变长单元格
			sheet.setColumnWidth(0, (kleng * 400));
			sheet.setColumnWidth( 5,(kleng * 400));
			sheet.setColumnWidth(10,(kleng * 400));
			sheet.setColumnWidth(1, (klen * 512));
			sheet.setColumnWidth(6, (klen * 512));
			sheet.setColumnWidth(11,(klen * 512));
			sheet.setColumnWidth(4,  400);
			sheet.setColumnWidth( 9, 400);
		} catch (Exception e) {
			throw new ServiceException("getHSSFWorkbook方法出错，执行bo层失败",e);
		}
		finally {
			try{
				if(demoWorkBook!=null)
				{
					demoWorkBook.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return demoWorkBook;
	}
	
}

package app.component.finance.voucher.util;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import app.component.finance.account.entity.CwAssistData;
import app.component.finance.account.entity.CwComItem;
import app.component.finance.account.entity.CwFication;
import app.component.finance.account.entity.CwRelation;
import app.component.finance.cwtools.feign.CwToolsFeign;
import app.component.finance.paramset.entity.CwProofWords;
import app.component.finance.util.CWEnumBean;
import app.component.finance.util.CustomException;
import app.component.finance.util.R;
import cn.mftcc.common.MessageEnum;
import cn.mftcc.util.StringUtil;

public class VoucherXslUtil {

	public static R importVchPlate(CwToolsFeign cwToolsFeign,InputStream in,String finBooks,String regNo, String regName) throws Exception {

		HSSFWorkbook  wb = null;
		try {
        	wb = new HSSFWorkbook(new POIFSFileSystem(in));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			DecimalFormat dfm = new DecimalFormat("##0.00");
			DecimalFormat df = new DecimalFormat("##0");
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row = null;
			HSSFCell cell = null;
			int lastrow = sheet.getLastRowNum();
			int lastcell;
			List<String[]> dataList = new ArrayList<String[]>();
			for (int i = 1; i <= lastrow; i++) {
				row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				lastcell = row.getLastCellNum();
				String[] temp = new String[lastcell];
				for (int j = 0; j < lastcell; j++) {
					//如果分录序号与科目号同时为空时  自动结束读取
					if (row.getCell(4) == null && (row.getCell(6) == null || row.getCell(6).getCellType() == HSSFCell.CELL_TYPE_BLANK)) {
						break;
					}
					cell = row.getCell(j);
					if (cell == null) {
						temp[j] = "";
						continue;
					}
					if (lastcell < 9) {
//						throw new CustomExp("第" + (i + 1) + "行凭证信息不完整，请仔细检查模版！");
						throw new CustomException(MessageEnum.FAILED_OPERATION.getMessage("第" + (i + 1) + "行凭证信息不完整，请仔细检查模版！"));
					}
					switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_NUMERIC:
							if (j == 8 || j == 9) {
								temp[j] = dfm.format(cell.getNumericCellValue());  
				            }else if(HSSFDateUtil.isCellDateFormatted(cell) || "m/d/yy".equals(cell.getCellStyle().getDataFormatString())){  
				            	temp[j] = sdf.format(cell.getDateCellValue());  
//				            }else if("General".equals(cell.getCellStyle().getDataFormatString())){  
				            }else{  
				            	temp[j] = df.format(cell.getNumericCellValue());  
				            } 
							break;
						case HSSFCell.CELL_TYPE_STRING:
							temp[j] = cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							temp[j] = "";
							break;
						default:
							break;
					}
				}
				dataList.add(temp);
			}
			//处理凭证导入数据，适用其他

			String res = cwToolsFeign.dodealImportVchData(dataList,finBooks);
			return R.ok(res);
		} catch (Exception e) {
			if (e instanceof CustomException) {
				//throw new CustomExp(e.getMessage());
				return R.error(e.getMessage());
			}else {
				//logger.error("CwVchPlateMstBoImpl.importVchPlate凭证模版导入失败");
				throw e;
			}
		} finally {
			if (wb != null) {
				wb.close();
			}
		}
	}
	
	
	public static HSSFWorkbook getVchTemplateWB(CwToolsFeign cwToolsFeign,String finBooks) throws Exception {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet0 = wb.createSheet();// 生成一个表格
		HSSFSheet sheet1 = wb.createSheet();// 生成一个表格
		HSSFSheet sheet2 = wb.createSheet();// 生成一个表格
		wb.setSheetName(0, "凭证模版");// 设置表格标题及编码格式
		wb.setSheetName(1, "科目数据");// 设置表格标题及编码格式
		wb.setSheetName(2, "辅助核算项数据");// 设置表格标题及编码格式
		HSSFRow row = null;
		HSSFCell cell = null;
		row = sheet0.createRow(0);
		sheet0.setDefaultColumnWidth(12);
		String[] pztitle = new String[]{"日期","凭证字","凭证号","附件数","分录序号","摘要","科目代码","科目名称","借方金额","贷方金额","职员","部门","客户","自定义辅助核算类别","自定义辅助核算编码"};
		String[] tt_val = new String[]{
				"日期格式为200x-x-x，其他格式或为空值将无法引入凭证；日期应在帐套启用日期之后，否则凭证无法引入。",
				"需与系统设置的凭证字名称一致,否则凭证无法引入；只需在每张凭证的第一行分录输入,其他行可以为空。",
				"每张凭证的第一行分录必须输入,其他行可以为空",
				"可以为空",
				"每张凭证都从1开始顺序流水编号。",
				"每张凭证的第一行分录摘要不能为空，否则凭证无法引入。",
				"需要与系统设置的科目代码一致。",
				"可以为空，系统自动根据科目代码显示对应的科目名称。",
				"借贷方金额为本位币的金额；借方金额合计要与贷方金额合计相等。",
				"借贷方金额为本位币的金额；借方金额合计要与贷方金额合计相等。",
				"如果科目核算职员，则必须填入职员编码。",
				"如果科目核算部门，则必须填入部门编码。",
				"如果科目核算客户，则必须填入客户编码。",
				"如果科目下挂核算项目，需要录入核算项目类别名称",
				"自定义类别下的编码"};
		// 定义单元格格式（加粗、红色）
		HSSFCellStyle cellStyle = wb.createCellStyle();
		// 定义数据格式
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		// 设定上下居中，垂直居中
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//设置字体颜色
		HSSFFont font = wb.createFont();
		font.setColor(HSSFColor.RED.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		cellStyle.setFont(font);
		
		// 定义单元格格式只加粗
		HSSFCellStyle boldStyle = wb.createCellStyle();
		// 定义数据格式
		boldStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
		// 设定上下居中，垂直居中
		boldStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		boldStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont font1 = wb.createFont();
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		boldStyle.setFont(font1);
		//添加批注
		HSSFPatriarch patr = sheet0.createDrawingPatriarch();
		HSSFComment comment = null;
		for (int i = 0; i < pztitle.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(pztitle[i]);
			//添加批注的问题及编辑的大小
			comment = patr.createCellComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 8));
			//容器写人批注内容
			comment.setString(new HSSFRichTextString(tt_val[i]));
			cell.setCellComment(comment);
			if ((i >= 0 && i < 3) || (i > 3 && i < 7)) {
				cell.setCellStyle(cellStyle);
			}else {
				cell.setCellStyle(boldStyle);
			}
		}
//		//设置CELL格式为文本格式  
//        HSSFCellStyle cellStyle2 = wb.createCellStyle();  
//        HSSFDataFormat format = wb.createDataFormat();  
//        cellStyle2.setDataFormat(format.getFormat("@"));  
//		for (int i = 1; i < 500; i++) {
//			row = sheet0.createRow(i);
//			for (int j = 0; j < pztitle.length; j++) {
//				cell = row.createCell(j);
//				if (j == 6) {
//					cell.setCellStyle(cellStyle2);
//				}
//			}
//		}
		
	    try {
			/*********** 辅助项  科目   凭证字  ***********/
			List<CwProofWords> proofs = cwToolsFeign.getCwProofWordsList(new CwProofWords(),finBooks);
			String[] pzzi = new String[proofs.size()];
			for (int i = 0; i < proofs.size(); i++) {
				pzzi[i] = proofs.get(i).getPzPrefix();
			}
			//生成下拉列表
			//对第1行到500行的第一列单元格区域有效
			CellRangeAddressList region = new CellRangeAddressList((short)(1), 500, (short)(1), (short)1);
			//生成下拉框内容
			DVConstraint constraint = DVConstraint.createExplicitListConstraint(pzzi);
			//绑定下拉框和作用区域
			HSSFDataValidation data_validation = new HSSFDataValidation(region,constraint);
			//对sheet页生效
			sheet0.addValidationData(data_validation);
			
			//第二个工作表(科目数据)
			List<CwComItem> comItems = cwToolsFeign.getCwComItemCache(finBooks);
			CwComItem cwComItem = null;
			List<CwRelation> relations = cwToolsFeign.getCwRelationList(new CwRelation(),finBooks);
			row = sheet1.createRow(0);
			sheet1.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)4));
			cell = row.createCell(0);
			cell.setCellValue("科目备查数据,不会被导入。");
			cell.setCellStyle(cellStyle);
			//标题
			String[] kmtitle = new String[]{"科目代码", "科目名称", "方向", "是否核算", "核算类别"};
			row = sheet1.createRow(1);
			for (int i = 0; i < 5; i++) {
				cell = row.createCell(i);
				cell.setCellValue(kmtitle[i]);
				cell.setCellStyle(boldStyle);
			}
			//科目数据
			for (int i = 0; i < comItems.size(); i++) {
				row = sheet1.createRow(i + 2);
				cwComItem = comItems.get(i);
				String accNo = cwComItem.getAccNo();
				//编号
				cell = row.createCell(0);
				cell.setCellValue(accNo);
				//名称
				cell = row.createCell(1);
				cell.setCellValue(cwComItem.getAccName());
				//方向
				cell = row.createCell(2);
				cell.setCellValue(CWEnumBean.JIEDAITYPE.getTypeDesc(cwComItem.getDcInd()));
				//核算
				cell = row.createCell(3);
				cell.setCellValue(CWEnumBean.YES_OR_NO.getTypeDesc(cwComItem.getSeqCtl()));
				//类别
				cell = row.createCell(4);
				String items = "";
				if (CWEnumBean.YES_OR_NO.SHI.getNum().equals(cwComItem.getSeqCtl())) {
					for (CwRelation relation : relations) {
						if (relation.getAccNo().equals(accNo)) {
							items += "/" + relation.getTxName();
						}
					}
				}
				cell.setCellValue(StringUtil.isEmpty(items) ? "" : items.substring(1));
			}
			
			//第三个工作表(辅助核算)
			row = sheet2.createRow(0);
			sheet2.addMergedRegion(new CellRangeAddress(0,(short)0,0,(short)2));
			cell = row.createCell(0);
			cell.setCellValue("辅助核算项目备查数据,不会被导入。");
			cell.setCellStyle(cellStyle);
			
			String[] fzx = new String[]{"类别","编码","名称"};
			row = sheet2.createRow(1);
			for (int i = 0; i < 3; i++) {
				cell = row.createCell(i);
				cell.setCellValue(fzx[i]);
				cell.setCellStyle(boldStyle);
			}
			
			List<CwFication> fications = cwToolsFeign.getCwFicationList(new  CwFication(),finBooks);
			int r = 2;
			Map<String, String> formMap = new HashMap<String, String>();
			for (CwFication fication : fications) {
				formMap.put("txType", fication.getTxType());
				List<CwAssistData> assists = cwToolsFeign.getAssistAccountData(formMap,finBooks);
				if (null != assists && assists.size() > 0) {
					for (CwAssistData ass : assists) {
						row = sheet2.createRow(r);
						cell = row.createCell(0);
						cell.setCellValue(fication.getTypeName());//辅助项名称
						cell = row.createCell(1);
						cell.setCellValue(ass.getTxCode());//辅助项值编号
						cell = row.createCell(2);
						cell.setCellValue(ass.getTxName());//辅助项值名称
						r ++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (wb != null) {
				wb.close();
			}
		}
		return wb;
	}
}

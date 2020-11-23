package app.component.model.entity;

import cn.mftcc.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zhuozhengsoft.pageoffice.excelwriter.Sheet;
import com.zhuozhengsoft.pageoffice.excelwriter.Workbook;
import com.zhuozhengsoft.pageoffice.excelwriter.XlBorderType;
import com.zhuozhengsoft.pageoffice.excelwriter.XlHAlign;
import com.zhuozhengsoft.pageoffice.wordwriter.WordDocument;
import com.zhuozhengsoft.pageoffice.wordwriter.Table;
import com.zhuozhengsoft.pageoffice.wordwriter.DataRegion;
import com.zhuozhengsoft.pageoffice.wordwriter.WdCellVerticalAlignment;
import com.zhuozhengsoft.pageoffice.wordwriter.WdAutoFitBehavior;

import java.awt.Color;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * @Description:页面输出内容   
 * @author pgq
 * @date 2015-11-11
 */
public class PageContent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1823806817753823073L;
	//打开文件名包括路径
	private String pathFileName;
	//保存文件路径
	private String savePath;
	//保存文件名
	private String saveFileName;
	//jsp页面的标题
	private String title;
	//office窗口标题
	private String caption;
	//打开属性 1 doc 2excel 3ppt
	private String fileType;
	//只读 1只读 0非只读
	private String readOnly="0";
	//菜单栏  0无1有
	private String menuBar="0";
	//工具栏 0无1有
	private String toolBar="0";
	//自定义栏 0无1有
	private String customBar="1";
	//保存按钮 0无1有
	private String saveBtn="0";
	//关闭按钮 0无1有
	private String closeBtn="1";
	//打印按钮 0无1有
	private String printBtn="1";
	//全屏按钮 0无1有
	private String zoomBtn="1";
	//默认全屏 全屏按钮 1是 0否
	private String autoFullScreen="1";
	//返回网址
	private String returnUrl;
	//保存记录调用的url
	private String saveUrl;
	//保存记录调用的方法名
	private String saveFunc;
	//书签方法名及参数
	private String markUrl;
	//替换excel字段方法串
	private String replaceExcelFunc;
	//文档打开初始化运行宏
	private String initMacro;
	//word替换方式 0是用api提供的方法,1是用js替换
	//对于word出现拼写错误的问题要设置为1
	private String jsReplaceWord="0";
	//字段类
	private Map<String,PageField> pageFieldMap;
	//表格类
	private List<Map<String,Object>> pageTableMapList;
	
	public PageContent(String fileName, String caption,String fileType) {
		super();
		this.setPathFileName(fileName);//打开全路径文件名
		this.setFileType(fileType);//1 doc 2excel 3ppt
		this.setCaption(caption);//标题内容
		this.setMenuBar("0");//菜单栏
		this.setCustomBar("1");//自定义栏
		this.setCloseBtn("1");//关闭按钮
		this.setPrintBtn("1");// 打印按钮
		this.setToolBar("0");//默认无工具栏
		if ("0".equals(fileType)) {// 自动匹配
			String fname = fileName.toLowerCase();
			if (fname.endsWith(".xls") || fname.endsWith(".xlsx")) {
				this.setFileType("2");// 2excel
			} else if (fname.endsWith(".ppt") || fname.endsWith(".pptx")) {
				this.setFileType("3");// 3ppt
			} else {
				this.setFileType("1");// 默认是doc
			}
		}
		this.pageFieldMap=new LinkedHashMap<String, PageField>();
		this.pageTableMapList=new ArrayList<Map<String,Object>>();
	}
	
	public PageContent(){
		super();
		this.setMenuBar("0");//菜单栏
		this.setCustomBar("1");//自定义栏
		this.setToolBar("1");//工具栏
		this.setPrintBtn("1");// 打印按钮
		this.setCloseBtn("1");//关闭按钮
		this.setSaveBtn("1");//保存按钮
		this.setZoomBtn("1");//全屏按钮
		this.pageFieldMap=new LinkedHashMap<String, PageField>();
		this.pageTableMapList=new ArrayList<Map<String,Object>>();
	};
	
	/**
	 * 
	 * @Description: 调用方创建详情画面
	 * @param fileName 文件名
	 * @param caption 标题
	 * @param modeType 1为word 2为excel 3为ppt
	 * @return PageContent  
	 * @throws
	 * @author pgq
	 * @date 2015-11-13
	 */
	public static PageContent getDetailPc(String fileName, String caption,String modeType) {
		// 有自定义 文件(页面设置 打印 打印预览) 关闭 无菜单栏 有工具栏置灰
		PageContent poCnt = new PageContent(fileName,caption,modeType);
		poCnt.setReadOnly("1");//默认只读
		poCnt.setSaveBtn("0");// 无保存按钮
		poCnt.setToolBar("0");// 无工具栏
		return poCnt;
	}
	
	/**
	 * 
	 * @Description: 调用方创建可编辑画面
	 * @param fileName 文件名
	 * @param caption 标题
	 * @param modeType 1为word 2为excel 3为ppt
	 * @return PageContent  
	 * @throws
	 * @author pgq
	 * @date 2015-11-13
	 */
	public static PageContent getEditPc(String fileName, String caption,String modeType) {
		// 有自定义 文件(页面设置 打印 打印预览) 关闭 无菜单栏 有工具栏置灰
		PageContent poCnt = new PageContent(fileName,caption,modeType);
		poCnt.setReadOnly("0");//默认编辑
		poCnt.setSaveBtn("1");// 有保存按钮
		return poCnt;
	}
	
	/**
	 * 
	 * @Description: 添加字符串类字段
	 * @param key $标识$
	 * @param value   
	 * @return void  
	 * @throws
	 * @author pgq
	 * @date 2015-11-10
	 */
	public void addField(String key,String value) {
		PageField pageField=new PageField(key, value);
		this.pageFieldMap.put(key, pageField);
	}
	
	/**
	 * 
	 * @Description: 添加表格
	 * @param key 书签
	 * @param table   
	 * @return void  
	 * @throws
	 * @author pgq
	 * @date 2015-11-11
	 */
	public void addTable(String key,PageTable table) {
		table.setBookMark(key);
		Map<String,Object> pageTableMap = new HashMap<String,Object>();
		pageTableMap.put("key",key);
		pageTableMap.put("table",table);
		this.pageTableMapList.add(pageTableMap);
	}
	
	/**
	 * 
	 * @Description: 将内容转换成doc对象
	 * @return   
	 * @return WordDocument  
	 * @throws UnsupportedEncodingException 
	 * @throws
	 * @author pgq
	 * @date 2015-11-14
	 */
	public WordDocument getWordDoc() throws UnsupportedEncodingException {
		WordDocument doc = new WordDocument();
		if("1".equals(jsReplaceWord)) {
			//---------------WORD的js替换避开语法报错------------------
			List<PageField> pfList=new ArrayList<PageField>(); 
			PageField fileType=new PageField();
			fileType.setKey("fileType");
			fileType.setValue("1");//word
			pfList.add(fileType);
			for(String key:this.pageFieldMap.keySet()) {
				pfList.add(this.pageFieldMap.get(key));
			}
			//将替换excel字段转换成json抛到前台*/
			//如果放开以下语句,则需要给一个文件类型的判断
			this.replaceExcelFunc=URLEncoder.encode(new Gson().toJson(pfList), "UTF-8");
			//--------------------js替换代码结束----------------------------------------------
		} else {
			//api替换方式
			StringBuffer sbf=new StringBuffer();
			for (String key : this.pageFieldMap.keySet()) {
				if(key==null) {
                    continue;
                }
				PageField field = this.pageFieldMap.get(key);
				String value=field.getValue();
				//插入excel表格
				if(value==null) {
					doc.openDataTag(key).setValue("");
				}else if(value.startsWith("[excel]")) {
					String fileName=value.substring(7);
					sbf.append("insertExcel('"+key+"','"+fileName+"');");
				}else {
					doc.openDataTag(key).setValue(value);
				}
			}
			this.initMacro=sbf.toString();
		}
		Table docTable = null;
		int rowNum = 0;
		int colNum = 0;
		int startRowNum = 0;
		Color red = new Color(255,0,0);
		DataRegion afterDataRegion =null;
		String key = "";
		String vertical = "";
		PageTable pageTable = null;
		for (Map<String,Object> pageTableMapTmp :this.pageTableMapList){
			key = (String) pageTableMapTmp.get("key");
			pageTable = (PageTable)JSONObject.toJavaObject((JSON) pageTableMapTmp.get("table"),PageTable.class);
			rowNum = pageTable.rowNum();
			colNum = pageTable.getMaxCol();
			startRowNum = pageTable.getStartRow();
			if("PO_asymmetry".equals(key)){
				docTable = doc.openDataRegion(key).openTable(1);
				for (int m = 0; m < rowNum; m++) {
					List<String> colorRow = pageTable.getRows().get(m);
					docTable.openCellRC(Integer.parseInt(colorRow.get(0)), Integer.parseInt(colorRow.get(1))).getFont().setColor(red);
				}
				continue;
			}
			//表格中有动态列
			if(key.indexOf("PO_afterIn")>-1){
				docTable = doc.openDataRegion(key).openTable(1);
				// 扩充表格
				for (int k = 1; k < rowNum; k++){
					docTable.insertRowAfter(docTable.openCellRC(startRowNum, 1));  //在第startRowNum行的最后一个单元格下插入新行
				}
				startRowNum = startRowNum-1;
			}else if(key.indexOf("image")>-1){//图片书签区域
				afterDataRegion = doc.openDataRegion(key);
				afterDataRegion.setValue("[image]"+pageTable.getBookMark()+"[/image]");
			}else{// 创建新表
				docTable = doc.openDataRegion(key).createTable(rowNum,colNum, WdAutoFitBehavior.wdAutoFitFixed);
			}
			//合并单元格
			for(int[] rcA:pageTable.getMergeList()) {
				docTable.openCellRC(rcA[0],rcA[1]).mergeTo(rcA[2],rcA[3]); //合并两个单元格
			}
			vertical = pageTable.getVertical();
			String value = "";
			//单元格值@颜色
			String [] cellArry = new String[2];
			int rowIndex = 0;
			int colIndex = 0;
			for (int i = 0; i < rowNum; i++) {
				List<String> row = pageTable.getRows().get(i);
				for (int j = 0; j < colNum && j < row.size(); j++) {
					value=StringUtil.KillNull(row.get(j));
					if(value!=null){
						cellArry = value.split("@");
						if("1".equals(vertical)){
							rowIndex = startRowNum + i + 1;
							colIndex = j + 1;
						}else{
							rowIndex = startRowNum + j + 1;
							colIndex = i + 1;
						}
						docTable.openCellRC(rowIndex, colIndex).setValue(cellArry.length>0?cellArry[0]:"");
						docTable.openCellRC(rowIndex, colIndex).setVerticalAlignment(WdCellVerticalAlignment.wdCellAlignVerticalCenter);
						if(value.indexOf("@")>-1 && cellArry.length == 2){
							if("bold".equals(cellArry[1])){
								docTable.openCellRC(rowIndex, colIndex).getFont().setBold(true);
							}
						}
					}
				}
			}
		}
		return doc;
	}
	
	/**
	 * 
	 * @Description: 得到表格
	 * @return   
	 * @return WordDocument  
	 * @throws UnsupportedEncodingException 
	 * @throws
	 * @author pgq
	 * @date 2015-11-16
	 */
	public Workbook getExcel() throws UnsupportedEncodingException {
		//定义Workbook对象
		Workbook workBook = new Workbook();
		List<PageField> pfList=new ArrayList<PageField>();
		PageField fileType=new PageField();
		fileType.setKey("fileType");
		fileType.setValue("2");//excel
		pfList.add(fileType);
		for(String key:this.pageFieldMap.keySet()) {
			pfList.add(this.pageFieldMap.get(key));
		}
		//将替换excel字段转换成json抛到前台*/
		this.replaceExcelFunc=URLEncoder.encode(new Gson().toJson(pfList), "UTF-8");
		Sheet sheet = null;
		int rowNum = 0;
		int colNmu = 0;
		int startRow = 0;
		int startCol = 0;
		String startAd = "";
		String toAd = "";
		String tableStd = "";
		String tableEnd = "";
		String key = "";
		//单元格值@颜色@边框@对齐方式
		String [] cellArry = new String[3];
		PageTable pageTable = null;
        boolean vertical = true;
		for (Map<String,Object> pageTableMapTmp :this.pageTableMapList){
			key = (String) pageTableMapTmp.get("key");
            pageTable = (PageTable)JSONObject.toJavaObject((JSON) pageTableMapTmp.get("table"),PageTable.class);
            if(null != pageTable.getVertical() && "0".equals(pageTable.getVertical())){
                vertical = false;
            }else{
                vertical = true;
            }
            if(pageTable.getSheet()==null) {
                pageTable.setSheet("Sheet1");
            }
			//定义Sheet对象，"Sheet1"是打开的Excel表单的名称
			sheet = workBook.openSheet(pageTable.getSheet());
			rowNum = pageTable.rowNum();
			colNmu = pageTable.getMaxCol();
			startRow=Math.max(1,pageTable.getStartRow());//不设置是为第一行
			startCol=Math.max(1,pageTable.getStartCol());//不设置则为第一列
			//定义Table对象.插入行
			tableStd = cellRcToAddress(startRow,startCol);
			tableEnd = cellRcToAddress(startRow,colNmu);
			com.zhuozhengsoft.pageoffice.excelwriter.Table table = sheet.openTable(tableStd+":"+tableEnd);
			table.getBorder().setBorderType(XlBorderType.xlAllEdges);

            for (int i = 0; i < rowNum; i++) {
                List<String> row = pageTable.getRows().get(i);
                for (int j = 0; j < colNmu && j < row.size(); j++) {
                    String value=row.get(j);
                    if(value!=null){
						cellArry = value.split("@");
                        if(vertical){
                            sheet.openCellRC(startRow+i, startCol+j).setValue(cellArry.length>0?cellArry[0]:"");
                            sheet.openCellRC(startRow+i, startCol+j).getBorder().setBorderType(XlBorderType.xlAllEdges);
                            if(value.indexOf("@")>-1 && cellArry.length == 4){
                                if("bold".equals(cellArry[1])){
                                    sheet.openCellRC(startRow+i, startCol+j).getFont().setBold(true);
                                }

                                if("center".equals(cellArry[3])){
                                    sheet.openCellRC(startRow+i, startCol+j).setHorizontalAlignment(XlHAlign.xlHAlignCenter);
                                }else if ("center".equals(cellArry[3])){
                                    sheet.openCellRC(startRow+i, startCol+j).setHorizontalAlignment(XlHAlign.xlHAlignLeft);
                                }else if ("right".equals(cellArry[3])){
                                    sheet.openCellRC(startRow+i, startCol+j).setHorizontalAlignment(XlHAlign.xlHAlignRight);
                                }else {
								}
                            }
                        }else{
                            sheet.openCellRC(startRow+j,startCol+i).setValue(cellArry[0]);
                        }
                    }
                }
                table.nextRow();
            }
            table.close();
            //合并单元格
            for(int[] rcA:pageTable.getMergeList()) {
                //行列转换成字符串地址如C5
                startAd=cellRcToAddress(startRow+rcA[0]-1,startCol+rcA[1]-1);
                toAd=cellRcToAddress(startRow+rcA[2]-1,startCol+rcA[3]-1);
                sheet.openTable(startAd+":"+toAd).merge();
            }
		}
		return workBook;
	}
	/**
	 * 
	 * @Description: 单元格行列转换到地址
	 * @param col
	 * @param row
	 * @return   
	 * @return String  
	 * @throws
	 * @author pgq
	 * @date 2016-1-25
	 */
	private String cellRcToAddress(int row,int col) {
		if(col<1) {
            return null;
        }
		int leftChar=(col-1)/26;
		int rightChar=(col%26==0)?26:(col%26);
		
		String rowStr=String.valueOf((char)(rightChar+'A'-1));
		if(leftChar>0) {//有左列
			rowStr=String.valueOf((char)(leftChar+'A'-1))+rowStr;
		}
		return rowStr+row;
	}
	
	public String getPathFileName() {
		return pathFileName;
	}

	public void setPathFileName(String pathFileName) {
		this.pathFileName = pathFileName;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
/**打开属性 1 doc 2excel 3ppt
 */
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public String getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(String menuBar) {
		this.menuBar = menuBar;
	}

	public String getToolBar() {
		return toolBar;
	}

	public void setToolBar(String toolBar) {
		this.toolBar = toolBar;
	}

	public String getCustomBar() {
		return customBar;
	}

	public void setCustomBar(String customBar) {
		this.customBar = customBar;
	}

	public String getSaveBtn() {
		return saveBtn;
	}

	public void setSaveBtn(String saveBtn) {
		this.saveBtn = saveBtn;
	}

	public String getCloseBtn() {
		return closeBtn;
	}

	public void setCloseBtn(String closeBtn) {
		this.closeBtn = closeBtn;
	}

	public String getPrintBtn() {
		return printBtn;
	}

	public void setPrintBtn(String printBtn) {
		this.printBtn = printBtn;
	}

	public String getZoomBtn() {
		return zoomBtn;
	}

	public void setZoomBtn(String zoomBtn) {
		this.zoomBtn = zoomBtn;
	}
	
	public String getAutoFullScreen() {
		return autoFullScreen;
	}

	public void setAutoFullScreen(String autoFullScreen) {
		this.autoFullScreen = autoFullScreen;
	}

	public Map<String, PageField> getPageFieldMap() {
		return pageFieldMap;
	}

	public void setPageFieldMap(Map<String, PageField> pageFieldMap) {
		this.pageFieldMap = pageFieldMap;
	}

	public List<Map<String, Object>> getPageTableMapList() {
		return pageTableMapList;
	}

	public void setPageTableMapList(List<Map<String, Object>> pageTableMapList) {
		this.pageTableMapList = pageTableMapList;
	}

	public String getMarkUrl() {
		return markUrl;
	}

	public void setMarkUrl(String markUrl) {
		this.markUrl = markUrl;
	}
	
	public String getSaveUrl() {
		return saveUrl;
	}

	public void setSaveUrl(String saveUrl) {
		this.saveUrl = saveUrl;
	}
	
	public String getSaveFunc() {
		return saveFunc;
	}

	public void setSaveFunc(String saveFunc) {
		this.saveFunc = saveFunc;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getReplaceExcelFunc() {
		return replaceExcelFunc;
	}

	public void setReplaceExcelFunc(String replaceExcelFunc) {
		this.replaceExcelFunc = replaceExcelFunc;
	}

	public String getInitMacro() {
		return initMacro;
	}

	public void setInitMacro(String initMacro) {
		this.initMacro = initMacro;
	}

	public String getJsReplaceWord() {
		return jsReplaceWord;
	}

	public void setJsReplaceWord(String jsReplaceWord) {
		this.jsReplaceWord = jsReplaceWord;
	}
}
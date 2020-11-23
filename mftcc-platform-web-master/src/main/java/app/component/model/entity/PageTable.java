package app.component.model.entity;

import java.util.ArrayList;
import java.util.List;

//表行类
public class PageTable {
	//行列表
	private List<List<String>> rows;
	//表格对应书签
	private String bookMark;
	//最大列数
	private int maxCol;
	//excel的sheet名
	private String sheet;
	//起始行
	private int startRow;
	//起始列
	private int startCol;
	//要插入表格起始位置
	private String tableStd;
	//要插入表格结束位置
	private String tableEd;
	//合并设置
	private List<int[]> mergeList;
	//是否横着替换值，1-是 0-否.默认横着
	private String vertical;
	public PageTable() {
		super();
		this.maxCol=0;
		this.vertical="1";
		this.rows=new ArrayList<List<String>>();
		this.mergeList=new ArrayList<int[]>();
	}
	
	/**
	 * 
	 * @Description: 初始化word表格
	 * @param list
	 * @return   
	 * @return PageTable  
	 * @throws
	 * @author pgq
	 * @date 2015-11-25
	 */

	public static PageTable getWordTable(List<List<String>> list) {
		PageTable pageTable=new PageTable();
		//将list中内容放到table中
		for(List<String> row:list)  {//行
			pageTable.maxCol=Math.max(pageTable.maxCol,row.size());//取最大列数
			List<String> cols=new ArrayList<String>();
			for(String col:row) {
				cols.add(col);
			}
			pageTable.rows.add(cols);
		}
		return pageTable;
	}
	
	/**
	 * 
	 * @Description: 将表list放入table对象中
	 * @param list   
	 * @return void  
	 * @throws
	 * @author pgq
	 * @date 2015-12-9
	 */
	public void setWordTable(List<List<String>> list) {
		PageTable pageTable=this;
		//将list中内容放到table中
		for(List<String> row:list)  {//行
			pageTable.maxCol=Math.max(pageTable.maxCol,row.size());//取最大列数
			List<String> cols=new ArrayList<String>();
			for(String col:row) {
				cols.add(col);
			}
			pageTable.rows.add(cols);
		}
	}
	
	/**
	 * 
	 * @Description: 合并单元格方法
	 * @param fr
	 * @param rc
	 * @param tr
	 * @param tc   
	 * @return void  
	 * @throws
	 * @author pgq
	 * @date 2015-12-9
	 */
	public void setMerge(int fr,int rc,int tr,int tc) {
		int[] rcA=new int[4];
		rcA[0]=fr;
		rcA[1]=rc;
		rcA[2]=tr;
		rcA[3]=tc;
		this.mergeList.add(rcA);
	}
	
	/**
	 * 
	 * @Description: 初始化excel表
	 * @param sheet
	 * @param startRow
	 * @param startCol
	 * @param excellist
	 * @return   
	 * @return PageTable  
	 * @throws
	 * @author pgq
	 * @date 2015-11-25
	 */
	public static PageTable getExcelTable(String sheet,int startRow,int startCol,List<List<String>> excellist) {
		PageTable pageTable=new PageTable();
		pageTable.setSheet(sheet);
		pageTable.setStartRow(startRow);
		pageTable.setStartCol(startCol);
		//将list中内容放到table中
		for(List<String> row:excellist)  {//行
			pageTable.maxCol=Math.max(pageTable.maxCol,row.size());//取最大列数
			List<String> cols=new ArrayList<String>();
			for(String col:row) {
				cols.add(col);
			}
			pageTable.rows.add(cols);
		}
		return pageTable;
	}

	public void addRow(String... fields) {
		this.maxCol=Math.max(this.maxCol,fields.length);//取最大列数
		List<String> cols=new ArrayList<String>();
		for(String f:fields) {
			cols.add(f);
		}
		this.rows.add(cols);
	}
	
	public List<List<String>> getRows() {
		return rows;
	}

	public void setRows(List<List<String>> rows) {
		this.rows = rows;
	}

	public String getBookMark() {
		return bookMark;
	}

	public void setBookMark(String bookMark) {
		this.bookMark = bookMark;
	}

	public int rowNum() {
		return this.rows.size();
	}
	
	public int getMaxCol() {
		if(maxCol==0) {
			for(List<String> r:this.rows){
				maxCol=Math.max(maxCol,r.size());
			}
		}
		return maxCol;
	}

	public void setMaxCol(int maxCol) {
		this.maxCol = maxCol;
	}

	public String getSheet() {
		return sheet;
	}

	public void setSheet(String sheet) {
		this.sheet = sheet;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}

	public List<int[]> getMergeList() {
		return mergeList;
	}

	public void setMergeList(List<int[]> mergeList) {
		this.mergeList = mergeList;
	}

	public String getTableStd() {
		return tableStd;
	}

	public void setTableStd(String tableStd) {
		this.tableStd = tableStd;
	}

	public String getTableEd() {
		return tableEd;
	}

	public void setTableEd(String tableEd) {
		this.tableEd = tableEd;
	}

	/**是否横着替换值1-是 0-否.默认横着
	 */
	public String getVertical() {
		return vertical;
	}
	/**是否横着替换值1-是 0-否.默认横着
	 */
	public void setVertical(String vertical) {
		this.vertical = vertical;
	}
	
}
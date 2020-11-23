package com.core.service.screen;

import com.core.base.CoreConstant;
import com.core.base.DocXml;
import com.core.domain.screen.Table;
import com.core.domain.screen.TableList;
import com.core.struts.SystemData;
import com.core.util.ExtFileFiter;
import com.core.util.oscache.ScreenCache;
import com.ibatis.common.util.PaginatedArrayList;
import com.ibatis.common.util.PaginatedList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.*;

/**
 * @version 1.0
 */

public class TableService extends DocXml{
  private String path = SystemData.getConfigPath();

  public TableService() {
  }

  public Table getTables(String tableId){
    Table table = new Table();
    
    String readXmlMode = SystemData.getReadXmlMode();
	if(readXmlMode!=null && readXmlMode.equals("cache")){
		   ScreenCache screenCache = new ScreenCache();
		   Table tableCache = screenCache.getTableByTableIdFromCache(tableId);
		   if(tableCache==null || tableCache.getTableId()==null){
			   table = getTableByXmlFile(tableId);
		   }else{
			   try {
				table = (Table) tableCache.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
	}else{
		table = getTableByXmlFile(tableId);
	}
    return table;
  }
  
  private Table getTableByXmlFile(String tableId){
	    Table table = new Table();
	    try {
	      path = SystemData.getConfigPath();
	      File fRead = new File(path+"/data/table/table"+tableId+".xml");
	      if(fRead.exists() )
	        table=readTable(fRead);
	      else{
	        System.out.println(path+"/data/table/table"+tableId+".xml文件不存在！");
	      }
	    }catch (Exception ex) {}
	     return table;
	  }
public int getTableCount(Table table) throws Exception {
	  int tableCount=0;
	
	  ScreenCache screenCache = new ScreenCache();
	  TreeMap tmpMap = screenCache.getTableMapFromCache();
	  if(tmpMap==null || tmpMap.size()==0){
		  tmpMap = new TreeMap();
		  File f = new File(path+"/data/table/");
		    ExtFileFiter eff = new ExtFileFiter("xml");
		    if(f.isDirectory()){
		      File[] fs = f.listFiles(eff) ;
		      for(int i=0;i<fs.length ;i++){
		        if(fs[i].exists() && fs[i].isFile()  ){
		        	 try{
		                tmpMap.put(fs[i].getName() ,this.readTableHead(fs[i]));
		             }catch(Exception e){}
		        }
		      }
		    }
	  }
    String tableId = table.getTableId() ;
    String name = table.getName() ;
    int type = table.getType() ;

    boolean idHave = true;
    boolean nameHave = true;
    boolean typeHave = true;
    if(tableId == null || tableId.equalsIgnoreCase("") )
      idHave = false;
    if(name == null || name.equalsIgnoreCase("") )
      nameHave = false;
    if(type <= 0)
      typeHave = false;

    boolean have =(idHave || nameHave || typeHave);

    Set key = tmpMap.keySet() ;
    List pList = new ArrayList();
    Iterator it = key.iterator() ;
    while(it.hasNext() ){
    	try{
		      Table tmpTable = (Table)tmpMap.get(it.next() );
		      if(have){
		        if(idHave){
		        	if(tmpTable.getTableId().indexOf(tableId) < 0){
		                continue;
		            }
		        }
		        if(typeHave){
		          if(tmpTable.getType() != type)
		            continue;
		        }
		        if(nameHave){
		          if(tmpTable.getName().indexOf(name) < 0)
		            continue;
		        }
		        pList.add(tmpTable);
		      }else{
		        pList.add(tmpTable);
		      }
    	}catch(Exception e){}
    }
    tableCount = pList.size();
	return tableCount;
	
}
  public PaginatedList searchTable(Table table) throws Exception {
    //ArrayList al = new ArrayList();
    ScreenCache screenCache = new ScreenCache();
	  TreeMap tmpMap = screenCache.getTableMapFromCache();
	  if(tmpMap==null || tmpMap.size()==0){
		  tmpMap = new TreeMap();
		  File f = new File(path+"/data/table/");
		    ExtFileFiter eff = new ExtFileFiter("xml");
		    if(f.isDirectory()){
		      File[] fs = f.listFiles(eff) ;
		      for(int i=0;i<fs.length ;i++){
		        if(fs[i].exists() && fs[i].isFile()  ){
		          try{
		              tmpMap.put(fs[i].getName() ,this.readTableHead(fs[i]));
		           }catch(Exception e){}
		        }
		      }
		    }
	  }
    String tableId = table.getTableId() ;
    String name = table.getName() ;
    int type = table.getType() ;

    boolean idHave = true;
    boolean nameHave = true;
    boolean typeHave = true;
    if(tableId == null || tableId.equalsIgnoreCase("") )
      idHave = false;
    if(name == null || name.equalsIgnoreCase("") )
      nameHave = false;
    if(type <= 0)
      typeHave = false;

    boolean have =(idHave || nameHave || typeHave);

    Set key = tmpMap.keySet() ;
    PaginatedArrayList pList = new PaginatedArrayList(10);
    Iterator it = key.iterator() ;
    while(it.hasNext() ){
    	try{
    		 Table tmpTable = (Table)tmpMap.get(it.next() );
    	      if(have){
    	        if(idHave){
    	        	 System.out.println(tmpTable.getTableId().indexOf(tableId));
    	          if(tmpTable.getTableId().indexOf(tableId) < 0){
    	            continue;
    	          }
    	        }
    	        if(typeHave){
    	          if(tmpTable.getType() != type)
    	            continue;
    	        }
    	        if(nameHave){
    	          if(tmpTable.getName().indexOf(name) < 0)
    	            continue;
    	        }
    	        pList.add(tmpTable);
    	      }else{
    	        pList.add(tmpTable);
    	      }
    	}catch(Exception e){}
    }
    return pList;
  }


  private Table readTableHead(File fRead) throws Exception {
	  if(fRead.getName().equals(CoreConstant.PRIVATEXML)||fRead.getName().equals(CoreConstant.PUBLICXML)){
		  return new Table();
	  }
    Document doc = super.docParse(fRead);
    Table table = new Table();
    Element root = doc.getDocumentElement();
    String id = root.getAttribute("id");
    table.setTableId(id);
    table.setName(root.getAttribute("title"));
    table.setType(Integer.parseInt(root.getAttribute("type"))) ;
    if(root.hasAttribute("link"))
      table.setPageLink(root.getAttribute("link"));
    if(root.hasAttribute("para"))
      table.setPara(root.getAttribute("para"));
    if(root.hasAttribute("trDbClick"))
        table.setTrDbClick(root.getAttribute("trDbClick"));
    if(root.hasAttribute("trClick"))
        table.setTrClick(root.getAttribute("trClick"));
    if(root.hasAttribute("colorCol"))
        table.setColorCol(root.getAttribute("colorCol"));
    if(root.hasAttribute("exportExcel")){
      table.setExportExcel(root.getAttribute("exportExcel"));
    } else{
      table.setExportExcel("0");
    }

    return table;
  }


    public String saveTable(Table table,String query) throws Exception {
        String reStr="";
        File f = new File(path+"/data/table/table"+table.getTableId() +".xml");
        ScreenCache screenCache = new ScreenCache();
        if(query.equalsIgnoreCase("insert")){
            if(f.exists() ){
                reStr = "已经存在此"+table.getTableId() +",不能进行增加！";
            }else{
                save(table);
                screenCache.addOrUpdateTableFromCache(table);
            }
        }else{
            if(f.exists() ){
                if(query.equalsIgnoreCase("delete")){
                    if(!f.renameTo(f)) {
                        System.out.println("table"+table.getTableId()+".xml,文件已被系统占用无法删除");
                    }
                    boolean delResult = f.delete();
                    int tryCount = 1;
                    while (!delResult && tryCount++ <= 10) {
                        System.out.println("第"+tryCount+"次删除尝试！");
                        System.gc(); // 回收资源
                        delResult = f.delete();
                    }
                    if(!delResult){
                        System.out.println("尝试多次仍无法删除，存在其他服务占用文件资源！");
                    }
                    screenCache.removeTableFromCache(table.getTableId());
                }else{
                    writeTableXml(table);
                    screenCache.addOrUpdateTableFromCache(table);
                }
            }
        }
        return reStr;
    }

  public void save(Table table) throws Exception {
    if(table.getTableList() != null ){
      int fieldCols = 0;
      int rows = 0;
      int sumCell = 0;
      TableList[] tableArray = table.getTableList() ;
      for(int i=0;i<tableArray.length ;i++){
        if(tableArray[i].getType() != 8)
          fieldCols++;
        if(tableArray[i].getRow() > rows)
          rows = tableArray[i].getRow() ;
        if(tableArray[i].getRowSpan() > 1 || tableArray[i].getColSpan() > 1)
          sumCell = sumCell + (tableArray[i].getRowSpan() + tableArray[i].getColSpan() - 1);
        else
          sumCell++;

      }
      //System.out.println(fieldCols * rows+":"+sumCell);
      if((fieldCols * rows) != sumCell)
        throw new Exception("列表头设置错误！");
    }

    writeTableXml(table);
    ScreenCache screenCache = new ScreenCache();
    screenCache.addOrUpdateTableFromCache(table);
  }

public int tableElementCount(Hashtable ht) throws Exception {
	int tableElementCount = 0;
	  List pal = new ArrayList();
	    List tmpList = new ArrayList();
	    Enumeration en = ht.keys() ;
	    while(en.hasMoreElements() ){
	      String id = en.nextElement().toString() ;
	      tmpList.add(ht.get(id));
	      //pal.add(ht.get(id));
	    }

	    TableList[] tableArray = this.order(tmpList);
	    for(int i=0;i<tableArray.length ;i++){
	      pal.add(tableArray[i]);
	    }
	    tableElementCount = pal.size();
	return tableElementCount;
}
  public PaginatedList getTableList(Hashtable ht) throws Exception {
    PaginatedArrayList pal = new PaginatedArrayList(100);
    List tmpList = new ArrayList();
    Enumeration en = ht.keys() ;
    while(en.hasMoreElements() ){
      String id = en.nextElement().toString() ;
      tmpList.add(ht.get(id));
      //pal.add(ht.get(id));
    }

    TableList[] tableArray = this.order(tmpList);
    for(int i=0;i<tableArray.length ;i++){
      pal.add(tableArray[i]);
    }
    return pal;
  }



  public Table readTable(File fRead) throws Exception {
	  if(fRead.getName().equals(CoreConstant.PRIVATEXML)||fRead.getName().equals(CoreConstant.PUBLICXML)){
		  return new Table();
	  }
    Document doc = super.docParse(fRead);
    Table table = new Table();
    Element root = doc.getDocumentElement();
    String id = root.getAttribute("id");
    table.setTableId(id);
    table.setName(root.getAttribute("title"));
    table.setType(Integer.parseInt(root.getAttribute("type"))) ;
    if(root.hasAttribute("link"))
      table.setPageLink(root.getAttribute("link"));
    if(root.hasAttribute("para"))
      table.setPara(root.getAttribute("para"));
    if(root.hasAttribute("trClick"))
        table.setTrClick(root.getAttribute("trClick"));
    if(root.hasAttribute("trDbClick"))
        table.setTrDbClick(root.getAttribute("trDbClick"));
    if(root.hasAttribute("colorCol"))
        table.setColorCol(root.getAttribute("colorCol"));
    if(root.hasAttribute("exportExcel"))
      table.setExportExcel(root.getAttribute("exportExcel"));
    else
      table.setExportExcel("0");

    ArrayList tList = new ArrayList();
    NodeList nl = root.getElementsByTagName("tableitem");
    int nodeLen = nl.getLength();
    for (int i = 0; i < nodeLen; i++) {
      Element tmpNode = (Element) (nl.item(i));
      TableList tmp = new TableList();

      tmp.setAlign(Integer.parseInt(tmpNode.getAttribute("align")));
      tmp.setFieldName(tmpNode.getAttribute("filedname"));
      tmp.setIndexed(Integer.parseInt(tmpNode.getAttribute("indexed")));
      tmp.setLabel(tmpNode.getAttribute("label")) ;
      tmp.setTableId(id) ;
      tmp.setTableListId(Integer.parseInt(tmpNode.getAttribute("id"))) ;
      tmp.setType(Integer.parseInt(tmpNode.getAttribute("type"))) ;
      if(tmpNode.hasAttribute("typepara"))
        tmp.setTypePara(tmpNode.getAttribute("typepara")) ;
      if(tmpNode.hasAttribute("mytitle"))
          tmp.setMytitle(tmpNode.getAttribute("mytitle")) ;
      if(tmpNode.hasAttribute("authority"))
          tmp.setAuthority(tmpNode.getAttribute("authority")) ;
      if(tmpNode.hasAttribute("buttonMark"))
          tmp.setButtonMark(tmpNode.getAttribute("buttonMark")) ;
      if(tmpNode.hasAttribute("width"))
        tmp.setWidth(Integer.parseInt(tmpNode.getAttribute("width")));
      if(tmpNode.hasAttribute("sortType"))
          tmp.setSortType(tmpNode.getAttribute("sortType")) ;
      if(tmpNode.hasAttribute("fieldStyle"))
          tmp.setFieldStyle(tmpNode.getAttribute("fieldStyle")) ;
      if(tmpNode.hasAttribute("widthType"))
          tmp.setWidthType(tmpNode.getAttribute("widthType")) ;
      if(tmpNode.hasAttribute("colorCol"))
          tmp.setColorCol(tmpNode.getAttribute("colorCol")) ;
      if(tmpNode.hasAttribute("row")){
        tmp.setRow(Integer.parseInt(tmpNode.getAttribute("row")));
      } else{
        tmp.setRow(1) ;
      }
      if(tmpNode.hasAttribute("rowspan")){
        tmp.setRowSpan(Integer.parseInt(tmpNode.getAttribute("rowspan")));
      }else{
        tmp.setRowSpan(1);
      }
      if(tmpNode.hasAttribute("colspan")){
        tmp.setColSpan(Integer.parseInt(tmpNode.getAttribute("colspan")));
      }else{
        tmp.setColSpan(1);
       }
      tList.add(tmp);
    }

    table.setTableList(order(tList)) ;
    return table;
  }

  private TableList[] order(List list){
    TreeMap tm = new TreeMap();
    TableList[] tableArray = (TableList[])list.toArray(new TableList[list.size()]);
    for(int i=0;i<tableArray.length ;i++){
      String row = String.valueOf(tableArray[i].getRow() ) ;
      if(tm.containsKey(row)){
        List tmpRow = (List)tm.get(row);
        tmpRow.add(tableArray[i]);
        tm.remove(row);
        tm.put(row,tmpRow);
      }else{
        ArrayList tmpRow = new ArrayList();
        tmpRow.add(tableArray[i]);
        tm.put(row,tmpRow);
      }
    }

    int k = 0;
    Set set = tm.keySet() ;
    Iterator it = set.iterator() ;
    while(it.hasNext() ){
      String row = it.next().toString()  ;
      List tmpRow = (List)tm.get(row);
      TableList[] rowArray = this.orderRow(tmpRow);
      for(int i=0;i<rowArray.length ;i++){
        tableArray[k] = rowArray[i];
        k++;
      }
    }
    return tableArray;

  }

  private TableList[] orderRow(List list){
    TableList[] tableArray = (TableList[])list.toArray(new TableList[list.size()]);
    for(int i=0;i<tableArray.length ;i++){
      for(int j=i+1;j<tableArray.length ;j++){
        if(tableArray[j].getIndexed() < tableArray[i].getIndexed() ){
          TableList tmp = tableArray[i];
          tableArray[i] = tableArray[j];
          tableArray[j] = tmp;
        }
      }
    }
    return tableArray;
  }


  private void writeTableXml(Table table) throws Exception {
    if(table != null){
      Document doc = super.getDoc();
      Element root = doc.createElement("table");
      String id = table.getTableId() ;
      root.setAttribute("id",table.getTableId() );
      root.setAttribute("title", table.getName() );
      root.setAttribute("type",String.valueOf(table.getType()));
      if(table.getPageLink() != null && !table.getPageLink().equalsIgnoreCase(""))
        root.setAttribute("link",table.getPageLink() );
      if(table.getPara() != null && !table.getPara().equalsIgnoreCase(""))
        root.setAttribute("para",table.getPara() );
      if(table.getTrClick()!= null && !table.getTrClick().equalsIgnoreCase(""))
          root.setAttribute("trClick",table.getTrClick() );
      if(table.getTrDbClick()!= null && !table.getTrDbClick().equalsIgnoreCase(""))
          root.setAttribute("trDbClick",table.getTrDbClick() );
      if(table.getColorCol()!= null && !table.getColorCol().equalsIgnoreCase(""))
          root.setAttribute("colorCol",table.getColorCol() );
      if(table.getExportExcel() != null && !table.getExportExcel().equalsIgnoreCase("")){
        root.setAttribute("exportExcel",table.getExportExcel() );
      }
     /* if(table.getExportExcel() != null
    	         && !table.getExportExcel().equalsIgnoreCase("")
    	         && !table.getExportExcel().equalsIgnoreCase("0")){
    	        root.setAttribute("exportExcel",table.getExportExcel() );
    	}*/
      doc.appendChild(root);
    
      TableList[] tArray = table.getTableList() ;
      if(tArray != null){
    	ArrayList tList = new ArrayList();
        for(int i=0;i<tArray.length ;i++){
          TableList tl = tArray[i];
          Element tmpNode = doc.createElement("tableitem");

          tmpNode.setAttribute("align",String.valueOf(tl.getAlign()) );
          tmpNode.setAttribute("filedname",tl.getFieldName() );
          tmpNode.setAttribute("indexed",String.valueOf(tl.getIndexed()) );
          tmpNode.setAttribute("label",tl.getLabel() );
          //tmpNode.setAttribute("tableid",id);
          tmpNode.setAttribute("id",String.valueOf(tl.getTableListId()) );
          tmpNode.setAttribute("type",String.valueOf(tl.getType()) );
          if(tl.getTypePara() != null && !tl.getTypePara().equalsIgnoreCase(""))
            tmpNode.setAttribute("typepara",tl.getTypePara() );
          if(tl.getWidth() > 0)
            tmpNode.setAttribute("width",String.valueOf(tl.getWidth()) );

          if(tl.getRow() > 0)
            tmpNode.setAttribute("row",String.valueOf(tl.getRow()) );
          if(tl.getRowSpan() > 1)
            tmpNode.setAttribute("rowspan",String.valueOf(tl.getRowSpan()) );
          if(tl.getColSpan() > 1)
            tmpNode.setAttribute("colspan",String.valueOf(tl.getColSpan()) );
          if(tl.getAuthority() != null && !tl.getAuthority().equalsIgnoreCase(""))
              tmpNode.setAttribute("authority",tl.getAuthority() );
          if(tl.getButtonMark()!= null && !tl.getButtonMark().equalsIgnoreCase(""))
              tmpNode.setAttribute("buttonMark",tl.getButtonMark() );
          if(tl.getMytitle() != null && !tl.getMytitle().equalsIgnoreCase(""))
              tmpNode.setAttribute("mytitle",tl.getMytitle() );
          if(tl.getSortType() != null && !tl.getSortType().equalsIgnoreCase(""))
              tmpNode.setAttribute("sortType",tl.getSortType() );
          if(tl.getFieldStyle() != null && !tl.getFieldStyle().equalsIgnoreCase(""))
              tmpNode.setAttribute("fieldStyle",tl.getFieldStyle() );
          if(tl.getWidthType() != null && !tl.getWidthType().equalsIgnoreCase(""))
              tmpNode.setAttribute("widthType",tl.getWidthType() );
          if(tl.getColorCol() != null && !tl.getColorCol().equalsIgnoreCase(""))
        	  tmpNode.setAttribute("colorCol",tl.getColorCol() );
          root.appendChild(tmpNode);
          tList.add(tl);
        }
        table.setTableList(order(tList)) ;
      }

      File f=new File(path+"/data/table/table"+id+".xml");
      super.save(f,doc);
    }
  }
  
  public PaginatedList searchTableAll(Table table) throws Exception
  {
    ScreenCache screenCache = new ScreenCache();
    TreeMap tmpMap = screenCache.getTableMapFromCache();
    int tableSize = tmpMap.size();
    if ((tmpMap == null) || (tmpMap.size() == 0)) {
      tmpMap = new TreeMap();
      File f = new File(this.path + "/data/table/");
      ExtFileFiter eff = new ExtFileFiter("xml");
      if (f.isDirectory()) {
        File[] fs = f.listFiles(eff);
        tableSize = fs.length;
        for (int i = 0; i < fs.length; i++) {
          if ((!fs[i].exists()) || (!fs[i].isFile())) continue;
          try {
            tmpMap.put(fs[i].getName(), readTableHead(fs[i]));
          } catch (Exception localException) {
          }
        }
      }
    }
    String tableId = table.getTableId();
    String name = table.getName();
    int type = table.getType();

    boolean idHave = true;
    boolean nameHave = true;
    boolean typeHave = true;
    if ((tableId == null) || (tableId.equalsIgnoreCase("")))
      idHave = false;
    if ((name == null) || (name.equalsIgnoreCase("")))
      nameHave = false;
    if (type <= 0) {
      typeHave = false;
    }
    boolean have = (idHave) || (nameHave) || (typeHave);

    Set key = tmpMap.keySet();
    PaginatedArrayList pList = new PaginatedArrayList(tableSize);
    Iterator it = key.iterator();
    while (it.hasNext())
      try {
        Table tmpTable = (Table)tmpMap.get(it.next());
        if (have) {
          if ((idHave) && 
            (tmpTable.getTableId().indexOf(tableId) < 0))
          {
            continue;
          }
          if ((typeHave) && 
            (tmpTable.getType() != type)) {
            continue;
          }
          if ((nameHave) && 
            (tmpTable.getName().indexOf(name) < 0)) {
            continue;
          }
          pList.add(tmpTable);
        } else {
          pList.add(tmpTable);
        }
      } catch (Exception localException1) {
      }
    return pList;
  }


}

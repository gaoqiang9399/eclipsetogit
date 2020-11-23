package com.core.service.screen;


import app.common.InitScreen;
import com.core.base.DocXml;
import com.core.domain.screen.FormActive;
import com.core.domain.screen.FormData;
import com.core.struts.SystemData;
import com.core.util.ExtFileFiter;
import com.core.util.Tools;
import com.core.util.oscache.ScreenCache;
import com.ibatis.common.util.PaginatedArrayList;
import com.ibatis.common.util.PaginatedList;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.text.Normalizer;
import java.util.*;


/**
 * @version 1.0
 */

public class FormService extends DocXml{
  Logger logger = LoggerFactory.getLogger(FormService.class);
  private boolean s;

    {
        try {
            s = SystemData.loadPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String path = SystemData.getConfigPath();
  private Tools ts = Tools.getInstance() ;

  public FormService() {
	 

  }
  //用于从数据库中得到选项
  public List getOtherOption(String sqlOption){
    return new InitScreen().getOtherOption(sqlOption);
  }

  //用于从数据库中得到选项
  public List getOtherOption(String sqlOption,Object obj){
    return new InitScreen().getOption(sqlOption,obj);
  }

  public List searchResultList(FormData formData) throws Exception{
    String sql = "";
    String sqlCond = "";
    String orderby = "";
    int inputCondNum = 0;
    HashMap formValueMap = new HashMap();
    FormActive[] faArray = formData.getFormActives() ;
    for(int i=0;i<faArray.length;i++){
      if(faArray[i].getDisplayContent() != 1){
        int dataType = faArray[i].getDataType();
        String para2 = faArray[i].getPara2() ;
        String fieldName = faArray[i].getFieldName() ;
        String value = faArray[i].getInitValue() ;
        if(fieldName.equalsIgnoreCase("hid_sql")){
          sql = para2;
        }else{
          if(fieldName.equalsIgnoreCase("hid_orderby")){
            orderby = para2;
          }else{
            if(fieldName.equalsIgnoreCase("hid_inputNum")){
              if(ts.isInteger(para2) ){
                inputCondNum = Integer.parseInt(para2,10);
              }
            }else{
              if(!value.equalsIgnoreCase("")){
                /*
                while (!ts.getParam(para2, '$').equals("")){
                  String fieldStr = ts.getParam(para2, '$') ;
                  if(fieldStr.equalsIgnoreCase(fieldName)){
                    para2 = ts.replace(para2,'$'+fieldStr+'$',value);
                  }
                }
                */
                switch (dataType ){
                  case 1:                         //int
                  case 2:                         //Long
                  case 3:                         //Double
                  case 4:                         //Float
                    formValueMap.put(fieldName.toUpperCase(),value);
                    /*
                    while (!ts.getParam(para2, '#').equals("")){
                      String fieldStr = ts.getParam(para2, '#') ;
                      if(fieldStr.equalsIgnoreCase(fieldName)){
                        para2 = ts.replace(para2,'#'+fieldStr+'#',value);
                      }
                    }
                    */
                    sqlCond = sqlCond + " " + para2;
                    break;
                  case 5:                         //Boolean
                    break;
                  case 6:                         //date
                  default:
                    formValueMap.put(fieldName.toUpperCase(),"'"+value+"'");
                    /*
                    while (!ts.getParam(para2, '#').equals("")){
                      String fieldStr = ts.getParam(para2, '#') ;
                      if(fieldStr.equalsIgnoreCase(fieldName)){
                        para2 = ts.replace(para2,'#'+fieldStr+'#',"'"+value+"'");
                      }
                      //logger.error(sqlCond);
                    }
                    */
                    sqlCond = sqlCond + " " + para2;
                }
              }
            }
          }
        }
      }
    }

    if(sql.equalsIgnoreCase(""))
      throw new Exception("没有配置hid_sql域！");
    if(inputCondNum > 0 && formValueMap.size() < inputCondNum){
      throw new Exception("至少要有 "+inputCondNum+" 个输入条件！");
    }

    //sql = sql.toUpperCase() ;
    if(!sqlCond.equalsIgnoreCase("")){
      sqlCond = " where 1=1 "+sqlCond;
    }
    sql = sql+sqlCond;

    if(!orderby.equalsIgnoreCase("")){
      sql = sql+" order by "+orderby;
    }

    int s=0;
    while (!ts.getParam(sql, '#').equals("")){
      s++;
      String fieldStr = ts.getParam(sql, '#') ;
      if(formValueMap.containsKey(fieldStr.toUpperCase()  )){
        sql = ts.replace(sql,'#'+fieldStr+'#',formValueMap.get(fieldStr.toUpperCase() ).toString() );
      }else{
        throw new Exception("配置错误，没有 "+fieldStr+" 输入域字段");
      }
      if(s > 100 ){
        throw new Exception("配置错误#"+fieldStr+"#，SQL: "+sql);
      }
    }

    s=0;
    while (!ts.getParam(sql, '$').equals("")){
      String fieldStr = ts.getParam(sql, '$') ;
      if(formValueMap.containsKey(fieldStr.toUpperCase()  )){
        String tmp = formValueMap.get(fieldStr.toUpperCase() ).toString();
        if(!ts.getParam(tmp, '\'').equals(""))
          tmp= ts.getParam(tmp, '\'');
        sql = ts.replace(sql,'$'+fieldStr+'$',tmp);
      }else{
        throw new Exception("hid_sql配置错误，没有 "+fieldStr+" 输入域字段");
      }
      if(s > 100 ){
        throw new Exception("配置错误$"+fieldStr+"$，SQL: "+sql);
      }
    }
    return new InitScreen().getOtherOption(sql);
  }


  public FormData getFormData(String formId) {
       FormData formData = new FormData();
       path = SystemData.getConfigPath();
    
	   String readXmlMode = SystemData.getReadXmlMode();
	   if(readXmlMode!=null && readXmlMode.equals("cache")){
		   ScreenCache screenCache = new ScreenCache();
		   FormData formDataCache =  screenCache.getFormDataByFromIdFromCache(formId);
		   if(formDataCache==null || formDataCache.getFormId()==null){
			   //formData = getFormDataByXmlFile(formId);
		   }else{
			   try {
				formData = (FormData) formDataCache.clone();
			   } catch (CloneNotSupportedException e) {
				e.printStackTrace();
			   }
		   }
		   
	   }else{
		   formData = getFormDataByXmlFile(formId);
	   }
   
    return formData;
  }
  private  FormData getFormDataByXmlFile(String formId){
	    FormData formData = new FormData();
	   
    	try {
    	      File fRead = new File(path+"/data/form/form"+formId+".xml");
    	      if(fRead.exists() )
    	        formData=readFromXml(fRead);
    	      else{
    	        logger.error(path+"/data/form/form"+formId+".xml文件不存在！");
    	      }
    	    }
    	    catch (Exception ex) {
    	    	 logger.error("获取FORM配置文件"+formId+"有误！"+ex.getMessage());
    	}
	   
	    return formData;
	  }



  public FormData readFromXml(File fRead) throws Exception {
    Document doc = super.docParse(fRead);
    FormData fd = new FormData();
    Vector vOrder = new Vector();
    Hashtable htElement = new Hashtable();
    Element root = doc.getDocumentElement();
    fd.setFormId(root.getAttribute("id"));
    if(root.hasAttribute("title"))
      fd.setTitle(root.getAttribute("title"));
    if(root.hasAttribute("formTitleShowFlag"))
        fd.setFormTitleShowFlag(root.getAttribute("formTitleShowFlag"));

    NodeList nl = root.getElementsByTagName("formelement");
    int nodeLen = nl.getLength();
    FormActive[] faArray = new FormActive[nodeLen];
    for (int i = 0; i < nodeLen; i++) {
      Element tmpNode = (Element) (nl.item(i));
      FormActive fa = new FormActive();
      if(tmpNode.hasAttribute("fieldtype")){
        int type = Integer.parseInt(tmpNode.getAttribute("fieldtype"));
        fa.setFieldType(type);
      }

      if (tmpNode.hasAttribute("alt"))
        fa.setAlt(tmpNode.getAttribute("alt"));
      if (tmpNode.hasAttribute("datatype"))
        fa.setDataType(Integer.parseInt(tmpNode.getAttribute("datatype")));
      if (tmpNode.hasAttribute("fieldalign"))
        fa.setFieldAlign(Integer.parseInt(tmpNode.getAttribute("fieldalign")));
      if (tmpNode.hasAttribute("fieldcol"))
        fa.setFieldCol(Integer.parseInt(tmpNode.getAttribute("fieldcol")));
      if (tmpNode.hasAttribute("fieldname"))
        fa.setFieldName(tmpNode.getAttribute("fieldname"));
      else
        fa.setFieldName("");
      if (tmpNode.hasAttribute("fieldsize"))
        fa.setFieldSize(tmpNode.getAttribute("fieldsize"));
      if (tmpNode.hasAttribute("fieldwidth"))
        fa.setFieldWidth(Integer.parseInt(tmpNode.getAttribute("fieldwidth")));
      if (tmpNode.hasAttribute("formactiveid"))
        fa.setFormActiveId(Integer.parseInt(tmpNode.getAttribute("formactiveid")));
      if (tmpNode.hasAttribute("initvalue"))
        fa.setInitValue(tmpNode.getAttribute("initvalue"));
      if (tmpNode.hasAttribute("labelalign"))
        fa.setLabelAlign(Integer.parseInt(tmpNode.getAttribute("labelalign")));
      if (tmpNode.hasAttribute("labelcol"))
        fa.setLabelCol(Integer.parseInt(tmpNode.getAttribute("labelcol")));
      if (tmpNode.hasAttribute("labellink"))
        fa.setLabelLink(tmpNode.getAttribute("labellink"));
      if (tmpNode.hasAttribute("labelname"))
        fa.setLabelName(tmpNode.getAttribute("labelname"));
      if (tmpNode.hasAttribute("labelwidth"))
        fa.setLabelWidth(Integer.parseInt(tmpNode.getAttribute("labelwidth")));
      if (tmpNode.hasAttribute("maxlength"))
        fa.setMaxLength(Integer.parseInt(tmpNode.getAttribute("maxlength")));
      if (tmpNode.hasAttribute("mustinput"))
        fa.setMustInput(tmpNode.getAttribute("mustinput"));
      else
        fa.setMustInput("0");
      if (tmpNode.hasAttribute("onblur"))
        fa.setOnblur(tmpNode.getAttribute("onblur"));
      if (tmpNode.hasAttribute("onchange"))
        fa.setOnchange(tmpNode.getAttribute("onchange"));
      if (tmpNode.hasAttribute("onclick"))
        fa.setOnclick(tmpNode.getAttribute("onclick"));
      if (tmpNode.hasAttribute("onfocus"))
        fa.setOnfocus(tmpNode.getAttribute("onfocus"));
      
      if (tmpNode.hasAttribute("calculationType"))
          fa.setCalculationType(tmpNode.getAttribute("calculationType"));
      if (tmpNode.hasAttribute("calculation"))
          fa.setCalculation(tmpNode.getAttribute("calculation"));
      
      
      if(tmpNode.hasAttribute("onkeyup"))
          fa.setOnkeyup(tmpNode.getAttribute("onkeyup"));
      if(tmpNode.hasAttribute("onkeydown"))
          fa.setOnkeydown(tmpNode.getAttribute("onkeydown"));
      if(tmpNode.hasAttribute("onkeypress"))
          fa.setOnkeypress(tmpNode.getAttribute("onkeypress"));
      if(tmpNode.hasAttribute("onmousedown"))
    	  fa.setOnmousedown(tmpNode.getAttribute("onmousedown"));
      if (tmpNode.hasAttribute("readonly"))
        fa.setReadonly(tmpNode.getAttribute("readonly"));
      else
        fa.setReadonly("0");
      if (tmpNode.hasAttribute("row"))
        fa.setRow(Integer.parseInt(tmpNode.getAttribute("row")));
      if (tmpNode.hasAttribute("unit"))
        fa.setUnit(tmpNode.getAttribute("unit"));


      if (tmpNode.hasAttribute("rowspan"))
        fa.setRowSpan(Integer.parseInt(tmpNode.getAttribute("rowspan")));
      else
        fa.setRowSpan(1) ;
      if (tmpNode.hasAttribute("labelcolspan"))
        fa.setLabelColSpan(Integer.parseInt(tmpNode.getAttribute("labelcolspan")));
      else
        fa.setLabelColSpan(1) ;
      if (tmpNode.hasAttribute("fieldcolspan"))
        fa.setFieldColSpan(Integer.parseInt(tmpNode.getAttribute("fieldcolspan")));
      else
        fa.setFieldColSpan(1) ;
      if (tmpNode.hasAttribute("labeldirect"))
        fa.setLabelDirect(Integer.parseInt(tmpNode.getAttribute("labeldirect")));
      else
        fa.setLabelDirect(0) ;
      if (tmpNode.hasAttribute("selrange"))
        fa.setSelRange(Integer.parseInt(tmpNode.getAttribute("selrange")));
      else
        fa.setSelRange(0) ;
      if (tmpNode.hasAttribute("labelstyle"))
        fa.setLabelStyle(tmpNode.getAttribute("labelstyle"));
      if (tmpNode.hasAttribute("fieldstyle"))
        fa.setFieldStyle(tmpNode.getAttribute("fieldstyle"));
      if (tmpNode.hasAttribute("param"))
        fa.setPara(tmpNode.getAttribute("param"));
      if (tmpNode.hasAttribute("param2"))
        fa.setPara2(tmpNode.getAttribute("param2"));
      if (tmpNode.hasAttribute("buttonCondition"))
          fa.setButtonCondition(tmpNode.getAttribute("buttonCondition"));
      if (tmpNode.hasAttribute("textAreaFieldRows"))
          fa.setTextAreaFieldRows(tmpNode.getAttribute("textAreaFieldRows"));
      
      if (tmpNode.hasAttribute("calculationShow"))
          fa.setCalculationShow(tmpNode.getAttribute("calculationShow"));
      
      if (tmpNode.hasAttribute("horVer"))
          fa.setHorVer(tmpNode.getAttribute("horVer"));
      if (tmpNode.hasAttribute("writeAuth"))
          fa.setWriteAuth(tmpNode.getAttribute("writeAuth"));
      if (tmpNode.hasAttribute("cutOff"))
          fa.setCutOff(tmpNode.getAttribute("cutOff"));
      
      


      faArray[i] = fa;

      //现在没有考虑隐藏域
      /*
      String row = (fa.getRow() > 9)?String.valueOf(fa.getRow()):"0"+String.valueOf(fa.getRow());
      String labelCol = (fa.getLabelCol() > 9)?String.valueOf(fa.getLabelCol()):"0"+String.valueOf(fa.getLabelCol());
      String fieldCol = (fa.getFieldCol() > 9)?String.valueOf(fa.getFieldCol()):"0"+String.valueOf(fa.getFieldCol());
      htElement.put(row+labelCol+fieldCol,fa);
      vOrder.add(row+labelCol+fieldCol);
      */
    }

    /*
    int len = vOrder.size() ;
    String[] orderArray = (String[])vOrder.toArray(new String[len]) ;
    for(int i=0;i<len;i++){
      for(int j=i+1;j<len;j++){
        if(Integer.parseInt(orderArray[j]) < Integer.parseInt(orderArray[i])){
          String tmp = orderArray[i];
          orderArray[i] = orderArray[j];
          orderArray[j] = tmp;
        }
      }
    }

    for(int i= 0;i<len ;i++){
      faArray[i] = (FormActive)htElement.get(orderArray[i]);
    }
    */

    fd.setFormActives(faArray);
    return fd;
  }

  private int toID(FormActive fa){
    String row = (fa.getRow() > 9)?String.valueOf(fa.getRow()):"0"+String.valueOf(fa.getRow());
    String labelCol = (fa.getLabelCol() > 9)?String.valueOf(fa.getLabelCol()):"0"+String.valueOf(fa.getLabelCol());
    String fieldCol = (fa.getFieldCol() > 9)?String.valueOf(fa.getFieldCol()):"0"+String.valueOf(fa.getFieldCol());
    return 0;
  }

  public void writeFromXml(FormData fd,String query) throws Exception {
    if(fd != null){
      Document doc = super.getDoc();
      Element root = doc.createElement("form");
      root.setAttribute("id",fd.getFormId());
      root.setAttribute("title", fd.getTitle());
      if(fd.getFormTitleShowFlag()==null || fd.getFormTitleShowFlag().equals("")){
    	  root.setAttribute("formTitleShowFlag", "false");
      }else{
    	  root.setAttribute("formTitleShowFlag", fd.getFormTitleShowFlag());
      }
      
      doc.appendChild(root);
      FormActive[] faArray = fd.getFormActives() ;
      if(faArray != null){
        for(int i=0;i<faArray.length ;i++){
          FormActive fa = faArray[i];
          Element tmpNode = doc.createElement("formelement");

          tmpNode.setAttribute("fieldtype",String.valueOf(fa.getFieldType()));
          if((fa.getAlt() != null) && (!fa.getAlt().equalsIgnoreCase("")))
            tmpNode.setAttribute("alt",fa.getAlt());
          tmpNode.setAttribute("datatype",String.valueOf(fa.getDataType()));
          tmpNode.setAttribute("fieldalign",String.valueOf(fa.getFieldAlign()));
          if(fa.getFieldCol() > 0)
            tmpNode.setAttribute("fieldcol",String.valueOf(fa.getFieldCol()));
          if((fa.getFieldName() != null) && (!fa.getFieldName().equalsIgnoreCase("")))
            tmpNode.setAttribute("fieldname",fa.getFieldName());
          if(fa.getFieldCol() > 0)
            tmpNode.setAttribute("fieldcol",String.valueOf(fa.getFieldCol()));
          if(fa.getFieldSize() != null && !fa.getFieldSize().trim().equalsIgnoreCase(""))
            tmpNode.setAttribute("fieldsize",String.valueOf(fa.getFieldSize()));
          if(fa.getFieldWidth() > 0)
            tmpNode.setAttribute("fieldwidth",String.valueOf(fa.getFieldWidth()));
          if(fa.getFormActiveId() > 0)
            tmpNode.setAttribute("formactiveid",String.valueOf(fa.getFormActiveId()));
          if((fa.getInitValue() != null) && (!fa.getInitValue().equalsIgnoreCase("")))
            tmpNode.setAttribute("initvalue",fa.getInitValue());
          if(fa.getLabelAlign() > 0)
            tmpNode.setAttribute("labelalign",String.valueOf(fa.getLabelAlign()));
          if(fa.getLabelCol() > 0)
            tmpNode.setAttribute("labelcol",String.valueOf(fa.getLabelCol()));
          if((fa.getLabelLink() != null) && (!fa.getLabelLink().equalsIgnoreCase("")))
            tmpNode.setAttribute("labellink",fa.getLabelLink());
          if((fa.getLabelName() != null) && (!fa.getLabelName().equalsIgnoreCase("")))
            tmpNode.setAttribute("labelname",fa.getLabelName());
          if(fa.getLabelWidth() > 0)
            tmpNode.setAttribute("labelwidth",String.valueOf(fa.getLabelWidth()));
          if(fa.getMaxLength() > 0)
            tmpNode.setAttribute("maxlength",String.valueOf(fa.getMaxLength()));
          tmpNode.setAttribute("mustinput",fa.getMustInput());

          if((fa.getOnblur() != null) && (!fa.getOnblur().equalsIgnoreCase("")))
            tmpNode.setAttribute("onblur",fa.getOnblur());
          if((fa.getOnchange() != null) && (!fa.getOnchange().equalsIgnoreCase("")))
            tmpNode.setAttribute("onchange",fa.getOnchange());
          if((fa.getOnclick() != null) && (!fa.getOnclick().equalsIgnoreCase("")))
            tmpNode.setAttribute("onclick",fa.getOnclick());
          if((fa.getOnfocus() != null) && (!fa.getOnfocus().equalsIgnoreCase("")))
            tmpNode.setAttribute("onfocus",fa.getOnfocus());
          
          if((fa.getCalculationType()!=null)&&(!fa.getCalculationType().equalsIgnoreCase("")))
          	  tmpNode.setAttribute("calculationType", fa.getCalculationType());
          if((fa.getCalculation()!=null)&&(!fa.getCalculation().equalsIgnoreCase("")))
          	  tmpNode.setAttribute("calculation", fa.getCalculation());
          
          
          if((fa.getOnkeydown()!=null)&&(!fa.getOnkeydown().equalsIgnoreCase("")))
          	  tmpNode.setAttribute("onkeydown", fa.getOnkeydown());
            if((fa.getOnkeyup()!=null)&&(!fa.getOnkeyup().equalsIgnoreCase("")))
          	  tmpNode.setAttribute("onkeyup", fa.getOnkeyup());
            if((fa.getOnkeypress()!=null)&&(!fa.getOnkeypress().equalsIgnoreCase("")))
          	  tmpNode.setAttribute("onkeypress", fa.getOnkeypress());
            if((fa.getOnmousedown()!=null)&&(!fa.getOnmousedown().equalsIgnoreCase("")))
          	  tmpNode.setAttribute("onmousedown", fa.getOnmousedown());
          if((fa.getReadonly() != null) && (!fa.getReadonly().equalsIgnoreCase("0")))
            tmpNode.setAttribute("readonly",fa.getReadonly());
          if(fa.getRow() > 0)
            tmpNode.setAttribute("row",String.valueOf(fa.getRow()));
          if((fa.getUnit() != null) && (!fa.getUnit().equalsIgnoreCase("")))
            tmpNode.setAttribute("unit",fa.getUnit());


          if(fa.getRowSpan() > 1)
            tmpNode.setAttribute("rowspan",String.valueOf(fa.getRowSpan()));
          if(fa.getLabelColSpan() > 1)
            tmpNode.setAttribute("labelcolspan",String.valueOf(fa.getLabelColSpan()));
          if(fa.getFieldColSpan() > 1)
            tmpNode.setAttribute("fieldcolspan",String.valueOf(fa.getFieldColSpan()));
          if(fa.getLabelDirect() > 0)
            tmpNode.setAttribute("labeldirect",String.valueOf(fa.getLabelDirect()));
          if(fa.getSelRange() > 0)
            tmpNode.setAttribute("selrange",String.valueOf(fa.getSelRange()));
          if((fa.getLabelStyle() != null) && (!fa.getLabelStyle().equalsIgnoreCase("")))
            tmpNode.setAttribute("labelstyle",fa.getLabelStyle());
          if((fa.getFieldStyle() != null) && (!fa.getFieldStyle().equalsIgnoreCase("")))
            tmpNode.setAttribute("fieldstyle",fa.getFieldStyle());
          if((fa.getPara() != null) && (!fa.getPara().equalsIgnoreCase("")))
            tmpNode.setAttribute("param",fa.getPara());
          if((fa.getPara2() != null) && (!fa.getPara2().equalsIgnoreCase("")))
            tmpNode.setAttribute("param2",fa.getPara2());
          if((fa.getButtonCondition() != null) && (!fa.getButtonCondition().equalsIgnoreCase("")))
              tmpNode.setAttribute("buttonCondition",fa.getButtonCondition());
          
          if((fa.getTextAreaFieldRows() != null) && (!fa.getTextAreaFieldRows().equalsIgnoreCase("")))
              tmpNode.setAttribute("textAreaFieldRows",fa.getTextAreaFieldRows());
          
          if((fa.getCalculationShow() != null) && (!fa.getCalculationShow().equalsIgnoreCase("")))
              tmpNode.setAttribute("calculationShow",fa.getCalculationShow());
          
          
          if((fa.getHorVer() != null) && (!fa.getHorVer().equalsIgnoreCase("")))
              tmpNode.setAttribute("horVer",fa.getHorVer());
          if((fa.getWriteAuth() != null) && (!fa.getWriteAuth().equalsIgnoreCase("")))
              tmpNode.setAttribute("writeAuth",fa.getWriteAuth());
          if((fa.getCutOff() != null) && (!fa.getCutOff().equalsIgnoreCase("")))
              tmpNode.setAttribute("cutOff",fa.getCutOff());
          

          root.appendChild(tmpNode);
        }
      }
      String userFormFlag = SystemData.getUserFormFlag();
	  if(query.equals("updateRowAndCol")&& userFormFlag.equals("true")){
		  String user = (String)((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute("tlrno");
		 
		  File fDirectory=new File(path+"/data/form/"+user);
		  if(!fDirectory.exists() ){
			  fDirectory.mkdir();
		  }
    	  File f=new File(path+"/data/form/"+user+"/"+"form"+fd.getFormId()+".xml");
          super.save(f,doc); 
	  }else{
		  File f=new File(path+"/data/form/form"+fd.getFormId()+".xml");
          super.save(f,doc);
	  }
	  ScreenCache screenCache = new ScreenCache();
	  if(query.equals("delete")){
		  screenCache.removeFormDataFromCache(fd.getFormId()); 
	  }else{
		  screenCache.addOrUpdateFormDataFromCache(fd);
	  }
    }
  }
  
  /**
   * @param form
   * @return
   * @throws Exception
   */
  public int getFormCount(FormData form) throws Exception {
	  int formCount = 0;
	  List pList = new ArrayList();
	   
	  ScreenCache screenCache = new ScreenCache();
	  TreeMap tmpMap = screenCache.getFormMapFromCache();
	   if(tmpMap==null || tmpMap.size()==0){
		   tmpMap = new TreeMap();
		    File f = new File(path+"/data/form/");
		    ExtFileFiter eff = new ExtFileFiter("xml");
		    if(f.isDirectory()){
		      File[] fs = f.listFiles(eff) ;
		      for(int i=0;i<fs.length ;i++){
		        if(fs[i].exists() && fs[i].isFile()  ){
		          try{
		        		tmpMap.put(fs[i].getName() ,this.readFormHead(fs[i]));
		        	}catch(Exception e){}
		        }
		      }
		    }
	   }
	  
	    String formId = form.getFormId() ;
	    String title = form.getTitle() ;
	    boolean idHave = true;
	    boolean nameHave = true;
	    if(formId == null || formId.equalsIgnoreCase("") )
	      idHave = false;
	    if(title == null || title.equalsIgnoreCase("") )
	      nameHave = false;

	    boolean have =(idHave || nameHave);
	    Set key = tmpMap.keySet() ;
	    Iterator it = key.iterator() ;
	    while(it.hasNext() ){
	    	try{
	    	  FormData tmpForm = (FormData)tmpMap.get(it.next() );
	   	      if(have){ 
	   	        if(idHave){
	   	            if(tmpForm.getFormId().indexOf(formId) < 0){
	   	              continue;
	   	            }
	   	          }
	   	          if(nameHave){
	   	            if(tmpForm.getTitle().indexOf(title) < 0)
	   	              continue;
	   	          }
	   	          pList.add(tmpForm);
	   	      }else{
	   	        pList.add(tmpForm);
	   	      }
	    	}catch(Exception e){}
	     
	    }
	  
	  formCount= pList.size();
	  return formCount;
  }

  public PaginatedList getFormList(FormData form) throws Exception {
   
    ScreenCache screenCache = new ScreenCache();
    TreeMap  tmpMap = screenCache.getFormMapFromCache();
   if(tmpMap==null || tmpMap.size()==0){
	   tmpMap = new TreeMap();
	   File f = new File(path+"/data/form/");
	    ExtFileFiter eff = new ExtFileFiter("xml");
	    if(f.isDirectory()){
	      File[] fs = f.listFiles(eff) ;
	      for(int i=0;i<fs.length ;i++){
	        if(fs[i].exists() && fs[i].isFile()  ){
	        	try{
	        		tmpMap.put(fs[i].getName() ,this.readFormHead(fs[i]));
	        	}catch(Exception e){}
	        }
	      }
	    }
   }
    String formId = form.getFormId() ;
    String title = form.getTitle() ;
    boolean idHave = true;
    boolean nameHave = true;
    if(formId == null || formId.equalsIgnoreCase("") )
      idHave = false;
    if(title == null || title.equalsIgnoreCase("") )
      nameHave = false;

    boolean have =(idHave || nameHave);

    Set key = tmpMap.keySet() ;
    PaginatedArrayList pList = new PaginatedArrayList(10);
    Iterator it = key.iterator() ;
    while(it.hasNext() ){
    	try{
		      FormData tmpForm = (FormData)tmpMap.get(it.next() );
		      if(have){
		        if(idHave){
		          if(tmpForm.getFormId().indexOf(formId) < 0){
		            continue;
		          }
		        }
		        if(nameHave){
		          if(tmpForm.getTitle().indexOf(title) < 0)
		            continue;
		        }
		        pList.add(tmpForm);
		      }else{
		        pList.add(tmpForm);
		      }
    	}catch(Exception e){}
    }

    return pList;
  }


  private FormData readFormHead(File fRead) throws Exception {
    Document doc = super.docParse(fRead);
    FormData fd = new FormData();
    Element root = doc.getDocumentElement();
    fd.setFormId(root.getAttribute("id"));
    if (root.hasAttribute("title")){
      fd.setTitle(root.getAttribute("title"));
    }
    if (root.hasAttribute("formTitleShowFlag")){
      fd.setFormTitleShowFlag(root.getAttribute("formTitleShowFlag"));
    }
    return fd;
  }

    public String saveForm(FormData form,String query) throws Exception {
      try{
          logger.error("form"+form.getFormId()+".xml表单执行"+query+"方法");
          String reStr="";
          File f = new File(path+"/data/form/form"+form.getFormId()+".xml");
          if(query.equalsIgnoreCase("insert")){
              logger.error(path+"/data/form/form"+form.getFormId()+".xml的1111f.exists()="+f.exists());
              if(f.exists() ){
                  reStr = "已经存在此FormId,不能进行增加！";
              }else{
                  this.writeFromXml(form,query);
              }
          }else{
              if(f.exists() ){
                  if(query.equalsIgnoreCase("delete")){
                      if(!f.renameTo(f)) {
                          logger.error("form"+form.getFormId()+".xml,文件已被系统占用无法删除");
                      }
                      boolean delResult = f.delete();
                      int tryCount = 1;
                      while (f.exists() && tryCount++ <= 10) {
                          logger.error("第"+tryCount+"次删除尝试！");
                          System.gc(); // 回收资源
                          delResult = f.delete();
                      }
                      if(!delResult){
                          logger.error("尝试多次仍无法删除，存在其他服务占用文件资源！");
                      }
                      ScreenCache screenCache = new ScreenCache();
                      screenCache.removeFormDataFromCache(form.getFormId());
                  }else{
                      FormData fd = this.readFromXml(f);
                      fd.setTitle(form.getTitle()) ;
                      fd.setFormTitleShowFlag(form.getFormTitleShowFlag());
                      this.writeFromXml(fd,query);
                  }
              }
          }
          return reStr;
      }catch (Exception e){
          logger.error("form"+form.getFormId()+".xml表单执行"+query+"方法出错",e);
          throw e;
      }

    }


  public void saveFormElement(FormData fd,List hiddenAndError){
    //求得表格的行数与列数
    FormActive[] fas = fd.getFormActives() ;
    int rows=0;
    int cols=0;
    for(int i=0;i<fas.length ;i++){
      if(fas[i].getFieldType() != 99){
        int tmpRow=fas[i].getRow() ;
        int tmpCol=(fas[i].getLabelCol() > fas[i].getFieldCol()) ? fas[i].getLabelCol():fas[i].getFieldCol()  ;
        rows = (tmpRow > rows) ? tmpRow:rows;
        cols = (tmpCol > cols) ? tmpCol:cols;
      }
    }

    if(rows > 0 && cols > 0){
      //当为隐藏域时 99，特殊，还有跨行，跨行区域重复，只显示控件的部分。
      FormActive[][] cellArray = new FormActive[rows][cols];
      for(int i=0;i<fas.length ;i++){
        FormActive cell = fas[i];
        int row = cell.getRow() ;
        int rowSpan = cell.getRowSpan() ;
        int labelCol = cell.getLabelCol() ;
        int fieldCol = cell.getFieldCol() ;
        int labelColSpan = cell.getLabelColSpan() ;
        int fieldColSpan = cell.getFieldColSpan() ;
        int validate = cell.getSelRange() ;         //定义范围
        int type = cell.getFieldType() ;            //类型
        cell.setDisplayContent(0);
        if(type == 99){
          cell.setErrorDes("隐藏域("+cell.getFieldName()+")") ;
          hiddenAndError.add(cell);
        }else if(type == 90){
              cell.setErrorDes("备用字段("+cell.getLabelName()+cell.getFieldName()+")") ;
              hiddenAndError.add(cell);
        }else{
          //最后的行与列不能超过最大行与列
          int maxCol = ((labelCol+labelColSpan) > (fieldCol+fieldColSpan)) ? (labelCol+labelColSpan-1):(fieldCol+fieldColSpan-1);
          if(((row + rowSpan -1) <= rows) && maxCol <= cols ){
            switch (validate){
              case 0:
                if(labelCol+labelColSpan <= fieldCol){                           //确保本身不重复
                  if(!isRepeat(cellArray,row-1,rowSpan,labelCol-1,labelColSpan)
                     && !isRepeat(cellArray,row-1,rowSpan,fieldCol-1,fieldColSpan)){
                    //处理合并标签
                    setMergeCell(cellArray,row-1,rowSpan,labelCol-1,labelColSpan);
                    //处理合并字段
                    setMergeCell(cellArray,row-1,rowSpan,fieldCol-1,fieldColSpan);
                  }else{
                    cell.setErrorDes("此区域已有值");
                    hiddenAndError.add(cell);
                  }
                }else{
                  cell.setErrorDes("字段列次错误");
                  hiddenAndError.add(cell);
                }
                break;
              case 1:
                if(!isRepeat(cellArray,row-1,rowSpan,labelCol-1,labelColSpan)){
                  //处理合并标签
                  setMergeCell(cellArray,row-1,rowSpan,labelCol-1,labelColSpan);
                }else{
                  cell.setErrorDes("此区域已有值");
                  hiddenAndError.add(cell);
                }
                break;
              case 2:
                if(!isRepeat(cellArray,row-1,rowSpan,fieldCol-1,fieldColSpan)){
                  //处理合并字段
                  setMergeCell(cellArray,row-1,rowSpan,fieldCol-1,fieldColSpan);
                }else{
                  cell.setErrorDes("此区域已有值");
                  hiddenAndError.add(cell);
                }
                break;
              default:
                  //continue;  //wusheng

                  cell.setErrorDes("跨越值错误");
                  hiddenAndError.add(cell);
            }
          }else{
            cell.setErrorDes("跨越值错误");
            hiddenAndError.add(cell);
          }
        }
      }
    }
    if(rows == 0 && cols == 0){
    	 for(int i=0;i<fas.length ;i++){
    	      if(fas[i].getFieldType() == 99){
    	    	  fas[i].setErrorDes("隐藏域("+fas[i].getFieldName()+")") ;
    	    	  hiddenAndError.add(fas[i]);
    	      }
    	  }
    }
  }



  private boolean isRepeat(FormActive[][] cellArray,int rowStart,int rowLen,int colStart,int colLen){
    boolean isRe = false;
    for(int j=rowStart; j<rowStart+rowLen;j++){
      for(int k=colStart;k<colStart+colLen;k++){
        if(cellArray[j][k] != null){
          isRe = true;
          break;
        }
      }
    }
    return isRe;
  }

  private void setMergeCell(FormActive[][] cellArray,int rowStart,int rowLen,int colStart,int colLen){
    for(int j=rowStart; j<rowStart+rowLen;j++){
      for(int k=colStart;k<colStart+colLen;k++){
        cellArray[j][k] = new FormActive();
        cellArray[j][k].setMerged(true);
      }
    }
  }


  public PaginatedList getFormListAll(FormData form) throws Exception
  {
    ScreenCache screenCache = new ScreenCache();
    TreeMap tmpMap = screenCache.getFormMapFromCache();
    int formSize = tmpMap.size();
    if ((tmpMap == null) || (tmpMap.size() == 0)) {
      tmpMap = new TreeMap();
      File f = new File(this.path + "/data/form/");
      ExtFileFiter eff = new ExtFileFiter("xml");
      if (f.isDirectory()) {
        File[] fs = f.listFiles(eff);
        formSize = fs.length;
        for (int i = 0; i < fs.length; i++) {
          if ((!fs[i].exists()) || (!fs[i].isFile())) continue;
          try {
            tmpMap.put(fs[i].getName(), readFormHead(fs[i]));
          } catch (Exception localException) {
          }
        }
      }
    }
    String formId = form.getFormId();
    String title = form.getTitle();
    boolean idHave = true;
    boolean nameHave = true;
    if ((formId == null) || (formId.equalsIgnoreCase("")))
      idHave = false;
    if ((title == null) || (title.equalsIgnoreCase(""))) {
      nameHave = false;
    }
    boolean have = (idHave) || (nameHave);

    Set key = tmpMap.keySet();
    PaginatedArrayList pList = new PaginatedArrayList(formSize);
    Iterator it = key.iterator();
    Map<String, Class> classMap = new HashMap<String, Class>();
    classMap.put("formActiveArray", FormActive.class);
    while (it.hasNext())
      try {
        FormData tmpForm = (FormData)JSONObject.toBean(JSONObject.fromObject(tmpMap.get(it.next())),FormData.class,classMap);
        if (have) {
          if ((idHave) && 
            (tmpForm.getFormId().indexOf(formId) < 0))
          {
            continue;
          }
          if ((nameHave) && 
            (tmpForm.getTitle().indexOf(title) < 0)) {
            continue;
          }
          pList.add(tmpForm);
        } else {
          pList.add(tmpForm);
        }
      }
      catch (Exception localException1) {
      }
    return pList;
  }
  
  public void writeFromXmlTest(FormData fd) throws Exception {
	    if(fd != null){
	      Document doc = super.getDoc();
	      Element root = doc.createElement("form");
	      root.setAttribute("id",fd.getFormId());
	      root.setAttribute("title", fd.getTitle());
	      if(fd.getFormTitleShowFlag()==null || fd.getFormTitleShowFlag().equals("")){
	    	  root.setAttribute("formTitleShowFlag", "false");
	      }else{
	    	  root.setAttribute("formTitleShowFlag", fd.getFormTitleShowFlag());
	      }
	      
	      doc.appendChild(root);
	      FormActive[] faArray = fd.getFormActives() ;
	      if(faArray != null){
	        for(int i=0;i<faArray.length ;i++){
	          FormActive fa = faArray[i];
	          Element tmpNode = doc.createElement("formelement");

	          tmpNode.setAttribute("fieldtype",String.valueOf(fa.getFieldType()));
	          if((fa.getAlt() != null) && (!fa.getAlt().equalsIgnoreCase("")))
	            tmpNode.setAttribute("alt",fa.getAlt());
	          tmpNode.setAttribute("datatype",String.valueOf(fa.getDataType()));
	          tmpNode.setAttribute("fieldalign",String.valueOf(fa.getFieldAlign()));
	          if(fa.getFieldCol() > 0)
	            tmpNode.setAttribute("fieldcol",String.valueOf(fa.getFieldCol()));
	          if((fa.getFieldName() != null) && (!fa.getFieldName().equalsIgnoreCase("")))
	            tmpNode.setAttribute("fieldname",fa.getFieldName());
	          if(fa.getFieldCol() > 0)
	            tmpNode.setAttribute("fieldcol",String.valueOf(fa.getFieldCol()));
	          if(fa.getFieldSize() != null && !fa.getFieldSize().trim().equalsIgnoreCase(""))
	            tmpNode.setAttribute("fieldsize",String.valueOf(fa.getFieldSize()));
	          if(fa.getFieldWidth() > 0)
	            tmpNode.setAttribute("fieldwidth",String.valueOf(fa.getFieldWidth()));
	          if(fa.getFormActiveId() > 0)
	            tmpNode.setAttribute("formactiveid",String.valueOf(fa.getFormActiveId()));
	          if((fa.getInitValue() != null) && (!fa.getInitValue().equalsIgnoreCase("")))
	            tmpNode.setAttribute("initvalue",fa.getInitValue());
	          if(fa.getLabelAlign() > 0)
	            tmpNode.setAttribute("labelalign",String.valueOf(fa.getLabelAlign()));
	          if(fa.getLabelCol() > 0)
	            tmpNode.setAttribute("labelcol",String.valueOf(fa.getLabelCol()));
	          if((fa.getLabelLink() != null) && (!fa.getLabelLink().equalsIgnoreCase("")))
	            tmpNode.setAttribute("labellink",fa.getLabelLink());
	          if((fa.getLabelName() != null) && (!fa.getLabelName().equalsIgnoreCase("")))
	            tmpNode.setAttribute("labelname",fa.getLabelName());
	          if(fa.getLabelWidth() > 0)
	            tmpNode.setAttribute("labelwidth",String.valueOf(fa.getLabelWidth()));
	          if(fa.getMaxLength() > 0)
	            tmpNode.setAttribute("maxlength",String.valueOf(fa.getMaxLength()));
	          tmpNode.setAttribute("mustinput",fa.getMustInput());

	          if((fa.getOnblur() != null) && (!fa.getOnblur().equalsIgnoreCase("")))
	            tmpNode.setAttribute("onblur",fa.getOnblur());
	          if((fa.getOnchange() != null) && (!fa.getOnchange().equalsIgnoreCase("")))
	            tmpNode.setAttribute("onchange",fa.getOnchange());
	          if((fa.getOnclick() != null) && (!fa.getOnclick().equalsIgnoreCase("")))
	            tmpNode.setAttribute("onclick",fa.getOnclick());
	          if((fa.getOnfocus() != null) && (!fa.getOnfocus().equalsIgnoreCase("")))
	            tmpNode.setAttribute("onfocus",fa.getOnfocus());
	          
	          if((fa.getCalculationType()!=null)&&(!fa.getCalculationType().equalsIgnoreCase("")))
	          	  tmpNode.setAttribute("calculationType", fa.getCalculationType());
	          if((fa.getCalculation()!=null)&&(!fa.getCalculation().equalsIgnoreCase("")))
	          	  tmpNode.setAttribute("calculation", fa.getCalculation());
	          
	          
	          if((fa.getOnkeydown()!=null)&&(!fa.getOnkeydown().equalsIgnoreCase("")))
	          	  tmpNode.setAttribute("onkeydown", fa.getOnkeydown());
	            if((fa.getOnkeyup()!=null)&&(!fa.getOnkeyup().equalsIgnoreCase("")))
	          	  tmpNode.setAttribute("onkeyup", fa.getOnkeyup());
	            if((fa.getOnkeypress()!=null)&&(!fa.getOnkeypress().equalsIgnoreCase("")))
	          	  tmpNode.setAttribute("onkeypress", fa.getOnkeypress());
	            if((fa.getOnmousedown()!=null)&&(!fa.getOnmousedown().equalsIgnoreCase("")))
	          	  tmpNode.setAttribute("onmousedown", fa.getOnmousedown());
	          if((fa.getReadonly() != null) && (!fa.getReadonly().equalsIgnoreCase("0")))
	            tmpNode.setAttribute("readonly",fa.getReadonly());
	          if(fa.getRow() > 0)
	            tmpNode.setAttribute("row",String.valueOf(fa.getRow()));
	          if((fa.getUnit() != null) && (!fa.getUnit().equalsIgnoreCase("")))
	            tmpNode.setAttribute("unit",fa.getUnit());


	          if(fa.getRowSpan() > 1)
	            tmpNode.setAttribute("rowspan",String.valueOf(fa.getRowSpan()));
	          if(fa.getLabelColSpan() > 1)
	            tmpNode.setAttribute("labelcolspan",String.valueOf(fa.getLabelColSpan()));
	          if(fa.getFieldColSpan() > 1)
	            tmpNode.setAttribute("fieldcolspan",String.valueOf(fa.getFieldColSpan()));
	          if(fa.getLabelDirect() > 0)
	            tmpNode.setAttribute("labeldirect",String.valueOf(fa.getLabelDirect()));
	          if(fa.getSelRange() > 0)
	            tmpNode.setAttribute("selrange",String.valueOf(fa.getSelRange()));
	          if((fa.getLabelStyle() != null) && (!fa.getLabelStyle().equalsIgnoreCase("")))
	            tmpNode.setAttribute("labelstyle",fa.getLabelStyle());
	          if((fa.getFieldStyle() != null) && (!fa.getFieldStyle().equalsIgnoreCase("")))
	            tmpNode.setAttribute("fieldstyle",fa.getFieldStyle());
	          if((fa.getPara() != null) && (!fa.getPara().equalsIgnoreCase("")))
	            tmpNode.setAttribute("param",fa.getPara());
	          if((fa.getPara2() != null) && (!fa.getPara2().equalsIgnoreCase("")))
	            tmpNode.setAttribute("param2",fa.getPara2());
	          if((fa.getButtonCondition() != null) && (!fa.getButtonCondition().equalsIgnoreCase("")))
	              tmpNode.setAttribute("buttonCondition",fa.getButtonCondition());
	          
	          if((fa.getTextAreaFieldRows() != null) && (!fa.getTextAreaFieldRows().equalsIgnoreCase("")))
	              tmpNode.setAttribute("textAreaFieldRows",fa.getTextAreaFieldRows());
	          
	          if((fa.getCalculationShow() != null) && (!fa.getCalculationShow().equalsIgnoreCase("")))
	              tmpNode.setAttribute("calculationShow",fa.getCalculationShow());
	          

	          root.appendChild(tmpNode);
	        }
	      }
		 
	    File f=new File("D:/formtest1000.xml");
	    super.save(f,doc);
		  
	    }
	  }


}

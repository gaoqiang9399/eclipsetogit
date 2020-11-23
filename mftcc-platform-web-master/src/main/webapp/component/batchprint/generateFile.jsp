<%@ page language="java"
	import="java.util.*,net.sf.json.*,cn.mftcc.util.WaterIdUtil,app.component.pss.utils.*,com.zhuozhengsoft.pageoffice.FileMakerCtrl, com.zhuozhengsoft.pageoffice.*, com.zhuozhengsoft.pageoffice.wordwriter.*"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
	FileMakerCtrl fmCtrl = new FileMakerCtrl(request);
	fmCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
	
	String fileNamePrefix = request.getParameter("fileNamePrefix");
	String fileName = WaterIdUtil.getWaterId(fileNamePrefix+"_"+request.getParameter("billId")+""+"_")+".doc";
	
	String templateFileName = request.getParameter("templateFileName");
	
	WordDocument doc = new WordDocument();
	
	String id = request.getParameter("id");
	
	String jsonObjectStr = request.getParameter("jsonObject");
	
	JSONObject jsonObject = JSONObject.fromObject(jsonObjectStr);
	
	JSONObject pageFieldMapJsonObj = (JSONObject)jsonObject.get("pageFieldMap");
	
	Iterator<?> it1 = pageFieldMapJsonObj.keys();
	while(it1.hasNext()){
		String key1 = (String) it1.next();
		Object value1 = pageFieldMapJsonObj.get(key1); 
		
		JSONObject valueJsonObj = (JSONObject)value1;
		String value2 = (String)valueJsonObj.get("value");
		
		doc.openDataRegion(key1.replace("$", "")).setValue(value2);
	}
	
	Iterator<?> it2 = JSONObject.fromObject(jsonObject.get("pageTableMap")).keys();
	String tableName = "";
	while(it2.hasNext()){
		tableName = (String) it2.next();
	}
	
	DataRegion dataRegion = doc.openDataRegion(tableName);
	Table table = dataRegion.openTable(1);
	
	List<List<String>> rowsList = (List<List<String>>)JSONObject.fromObject(JSONObject.fromObject(jsonObject.get("pageTableMap")).get(tableName)).get("rows");
	
	//List<List<String>> mergeList = (List<List<String>>)JSONObject.fromObject(JSONObject.fromObject(jsonObject.get("pageTableMap")).get(tableName)).get("mergeList");
	
	/* int i1 = 0,i2 = 0,i3 = 0,i4 = 0;
	for(List list : mergeList){
		i1 = Integer.parseInt(String.valueOf(list.get(0))) ;
		i2 = Integer.parseInt(String.valueOf(list.get(1)));
		i3 = Integer.parseInt(String.valueOf(list.get(2)));
		i4 = Integer.parseInt(String.valueOf(list.get(3)));
	}  */
	
	String mergeColumnData = "";
	for(int i = 0 ; i < rowsList.size() ; i++){
	
		List<String> rowList = rowsList.get(i);
		int columns = rowList.size();
		//if(i != rowsList.size()-1){
			
			for(int j = 0 ; j < rowList.size() ; j++){
				String rowStr = rowList.get(j);
				table.openCellRC(2+i, j+1).setValue(rowStr);
			}
			
			table.insertRowAfter(table.openCellRC(2+i, columns));
		//}else{//合计行
		//	List<String> countRow = rowsList.get(rowsList.size()-1);
		//	mergeColumnData = countRow.get(0);
		//	for(int j = 1 ; j < countRow.size() ; j++){
		//		String rowStr = countRow.get(j);
		//		table.openCellRC(2+i, j+i4).setValue(rowStr);
		//	}
		//}
	}

	//table.openCellRC(i1, i2).mergeTo(i3, i4);
	//table.openCellRC(i1, i2).setValue(mergeColumnData);
	
	fmCtrl.setSaveFilePage("saveSingleFile.jsp?fileName=" + fileName);
	fmCtrl.setWriter(doc);
	fmCtrl.setJsFunction_OnProgressComplete("OnProgressComplete()");
	//fmCtrl.fillDocument(request.getSession().getServletContext().getRealPath("component/model/docmodel/")+"/"+templateFileName, DocumentOpenType.Word);
	//System.out.println("generateFile.jsp");
	//System.out.println("http://localhost:"+request.getServerPort()+""+request.getContextPath()+"/component/batchprint/batchDoc/"+templateFileName);
	fmCtrl.fillDocument("http://localhost:"+request.getServerPort()+""+request.getContextPath()+"/component/batchprint/batchDoc/"+templateFileName, DocumentOpenType.Word);
	
	fmCtrl.setTagId("FileMakerCtrl1"); //此行必须 */

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
		<link rel="stylesheet" type="text/css" href="styles.css">
		-->
	</head>
	<body>
		<div>
			<script language="javascript" type="text/javascript">
				function OnProgressComplete() {
					//document.getElementById("FileMakerCtrl1").PrintOut();
					window.parent.batchFunc('<%=fileName %>'); //调用父页面的js函数
				}
			</script>
			<po:FileMakerCtrl id="FileMakerCtrl1"/>
		</div>
	</body>
</html>

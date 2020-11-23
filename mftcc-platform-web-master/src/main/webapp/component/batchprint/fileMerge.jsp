<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*, com.zhuozhengsoft.pageoffice.wordwriter.*"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
	String[] fileNameArray = request.getParameter("fileNameArray").split(",");
	
	WordDocument doc = new WordDocument();
	if(fileNameArray.length > 1){
		DataRegion mydr1 = doc.createDataRegion("PO_first", DataRegionInsertType.After, "[end]");
		mydr1.selectEnd();
		for(int i = 1 ; i < fileNameArray.length ; i++){
			//doc.insertPageBreak();//插入分页符
			DataRegion mydr2 = doc.createDataRegion("PO_second"+i, DataRegionInsertType.After, "[end]");
			mydr2.setValue("[word]batchDocTemp/"+fileNameArray[i]+"[/word]");
			doc.insertPageBreak();//插入分页符
		}
	}
	doc.setSaveEditedDROnly(true);
	
	PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
	//设置服务器页面
	poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
	poCtrl.addCustomToolButton("打印", "Print()", 6);
	poCtrl.addCustomToolButton("关闭", "CloseWindow()", 13);
	poCtrl.addCustomToolButton(" ", "ScreenWindow()", 4);
	
	poCtrl.setCaption("");
	poCtrl.setMenubar(false);
	poCtrl.setCustomToolbar(true);
	poCtrl.setTitlebar(false);
	poCtrl.setOfficeToolbars(false);

	poCtrl.setJsFunction_AfterDocumentOpened("AfterDocumentOpened");
		
	poCtrl.setWriter(doc);
	//设置保存页面
	//poCtrl.setSaveFilePage("SaveFile.jsp");
	//打开Word文档
	//System.out.println("batchDocTemp/"+fileNameArray[0]);
	poCtrl.webOpen("batchDocTemp/"+fileNameArray[0],OpenModeType.docReadOnly,"www.mftcc.cn");
	poCtrl.setTagId("PageOfficeCtrl1");//此行必需 */
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <title></title>
</head>
<body>
    <script type="text/javascript">   
       	function Print() {
       		document.getElementById("PageOfficeCtrl1").PrintOut();
       		//document.getElementById("PageOfficeCtrl1").ShowDialog(4);
       	}
       	function AfterDocumentOpened() {
           document.getElementById("PageOfficeCtrl1").FullScreen = true; //全屏
    	}
    	function ScreenWindow(){			
			document.getElementById("PageOfficeCtrl1").FullScreen = !document.getElementById("PageOfficeCtrl1").FullScreen;
    	}
    	function CloseWindow(){
    		window.external.close();
    	}
    </script> 
    <div style=" width:auto; height:700px;">
        <po:PageOfficeCtrl id="PageOfficeCtrl1">
        </po:PageOfficeCtrl>
    </div>
</body>
</html>

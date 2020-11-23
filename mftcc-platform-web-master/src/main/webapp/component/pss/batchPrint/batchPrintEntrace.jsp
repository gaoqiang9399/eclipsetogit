<%@ page language="java" import="java.util.*,com.zhuozhengsoft.pageoffice.*"
 pageEncoding="utf-8"%>

<%
String contextPath = request.getContextPath();
String pssBillsJson = request.getParameter("pssBillsJson");
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
String ajaxUrl = basePath+webPath+"/";
String fileNamePrefix = request.getParameter("fileNamePrefix");
String templateFileName = request.getParameter("templateFileName");
String printBizType = request.getParameter("printBizType");
%>

<script type="text/javascript" src="${webPath}/layout/ie8/js/json2.js"></script>
<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title></title>

    <script type="text/javascript">
        var count = 1;
        var pssBillsJson = <%=pssBillsJson %>;
        var fileNamePrefix = <%=fileNamePrefix %>;
        var templateFileName = <%=templateFileName %>;
        var fileNameArray = new Array();
        var printBizType = <%=printBizType %>; 
        
        $(function() {
			batchFunc('');
		});
        
        var batchFunc = function(returnFileName) {
            
            if(returnFileName != '' && returnFileName != "undefined"){
            	fileNameArray.push(returnFileName);
            }
            
            if(count <= pssBillsJson.length){	
            	var billId = "";
            	if(printBizType == 'GHDD'){
            		billId = pssBillsJson[count - 1].buyOrderId;
            	}    	
            	if(printBizType == 'GHD'){
            		billId = pssBillsJson[count - 1].buyBillId;
            	}
            	if(printBizType == 'GHTHD'){
            		billId = pssBillsJson[count - 1].buyReturnBillId;
            	}
            	if(printBizType == 'XHDD'){
            		billId = pssBillsJson[count - 1].saleOrderId;
            	}    	
            	if(printBizType == 'XHD'){
            		billId = pssBillsJson[count - 1].saleBillId;
            		
            	}
            	if(printBizType == 'XHTHD'){
            		billId = pssBillsJson[count - 1].saleReturnBillId;
            	}
            	if(printBizType == 'DBD'){
            		billId = pssBillsJson[count - 1];
            	}
            	if(printBizType == 'QTRKD'){
            		billId = pssBillsJson[count - 1];
            	}
            	if(printBizType == 'QTCKD'){
            		billId = pssBillsJson[count - 1];
            	}
            	
            	if(printBizType == 'SKD'){
            		billId = pssBillsJson[count - 1];
            	}
            	
				if(printBizType == 'FKD'){
					billId = pssBillsJson[count - 1];
				}
				if(printBizType == 'QTSRD'){
					billId = pssBillsJson[count - 1].otherRecId;
				}
				if(printBizType == 'QTZCD'){
					billId = pssBillsJson[count - 1];
				}
				            	
            	$.ajax({
            		url : '<%=ajaxUrl%>'+webPath+'/pssPrintBill/pssBatchPrintAjax',
            		data : {
						billId : billId,
						fileName : templateFileName,
						printBizType : printBizType
					},
					type : "POST",
					dataType : "json",
					success:function(data){
						var jsonObject = data.jsonObject;
						
						//设置进度条
                		document.getElementById("ProgressBarSide").style.visibility = "visible";
                		document.getElementById("ProgressBar").style.width = (count) * 25 - 1 + "%";
                		//加载文档打印页面（可传参）
                		document.getElementById("iframe1").src = "generateFile.jsp?billId="+encodeURIComponent(billId)+"&templateFileName="+encodeURIComponent(templateFileName)+"&fileNamePrefix="+encodeURIComponent(fileNamePrefix)+"&jsonObject="+encodeURIComponent(JSON.stringify(jsonObject))+"&id=" + count;
                		count++;
                		
					},
					error:function(data) {
					}
            	});
            }else{
            	//隐藏进度条div
                document.getElementById("ProgressBarSide").style.visibility = "hidden";
                count = 1;
                //重置进度条
                document.getElementById("ProgressBar").style.width = "0%";
               	window.location.href = "<%=ajaxUrl %>component/pss/batchPrint/fileMerge.jsp?fileNameArray=" + fileNameArray;
            }	
            
        };

    </script>

</head>
<body>
    <form id="form1">
    <div id="ProgressBarSide" style="color: Silver; width: 200px; visibility: hidden;
        position: absolute;  left: 40%; top: 50%; margin-top: -32px">
        <span style="color: gray; font-size: 12px; text-align: center;">正在生成批量打印文件,请稍候...</span><br />
	<div style=" border:solid 1px green;">
        	<div id="ProgressBar" style="background-color: Green; height: 16px; width: 0%; border-width: 1px;border-style: Solid;">
        	</div>
	</div>
    </div>
    <!-- <div style="text-align: center;">
        <br />
        <span style="color: Red; font-size: 12px;">提示：批量生成勾选数据的打印文件并合并</span><br />
        <input id="Button1" type="button" value="批量生成并合并Word文件" onclick="batchFunc('')" />
    </div> -->
    <div style="width: 1px; height: 1px; overflow: hidden;">
        <iframe id="iframe1" name="iframe1" src="" ></iframe>
    </div>
    </form>
</body>
</html>

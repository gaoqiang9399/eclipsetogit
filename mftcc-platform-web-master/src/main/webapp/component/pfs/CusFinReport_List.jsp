<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<body style="overflow: hidden;">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<button type="button" class="btn btn-primary pull-left" onclick="exportExcel();">导出报表模板</button>
						<ol class="breadcrumb pull-left">
							<li><a href="${webPath}/sysDevView/setC?setType=model">模型设置</a></li>
							<li class="active">财务报表 </li>
						</ol>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
						<dhcc:tableTag property="tablecusfinreport0001" paginate="parmDicList" head="true"></dhcc:tableTag>
					</div>
				</div>
			</div>
		</div>
	</body>	
	<script type="text/javascript">
	function getFinParm(parm){
		var reportType = parm.split("?")[1];
		window.location.href = "${webPath}/cusFinParm/getListPage1?"+reportType;
	};
	function getReportPreview(parm){
		var reportType = parm.split("?")[1];
		top.openBigForm("${webPath}/cusFinParm/getPageReportPreview?"+reportType,"报表预览",function(){});
	};
	function exportExcel(){
		$.ajax({
			url:webPath+"/cusFinMain/exportExcelAjax",
			dataType:"json",
			type:"POST",
			success:function(data){
				if(data.flag == "success"){
					if(data.exportFlag == "success"){
						window.top.location.href = "docUpload/getFileDownload_new?path="+data.path;
					}else{
						alert(data.msg,0);
					}
				}else{
					alert("error",0);
				}
			},error:function(){
				alert("error",0);
			}
		});
	};
	function importParm(){
		window.top.window.showDialog('${webPath}/cusFinUploadFiles/toUpload?info=1','导入报表元素',45,45,function(){
		
		});
	};
	</script>
</html>

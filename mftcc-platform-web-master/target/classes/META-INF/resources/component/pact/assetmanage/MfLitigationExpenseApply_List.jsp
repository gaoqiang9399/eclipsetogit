<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/pact/assetmanage/js/MfAssetManage_List2.js"></script>
		<script type="text/javascript" >
			var applyFlag='${applyFlag}';
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfLitigationExpenseApply/findByPageAjax",//列表数据查询的url
			    	tableId:"tablequerymflitigationexpenseapply0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize : 30,//加载默认行数(不填为系统默认行数)
					topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
			    });
			 });
			 function getDetailApply(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
			 	window.location.href=url;
			 }
			 function getDetailCus(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
			 	window.location.href=url;
			 }
			 function getDetailFive(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
			 	window.location.href=url;
			 }
			 //点击申请状态展示申请详情和审批历史
			 function thisShowById(url){
				 top.window.openBigForm(url,"申请详情",function(){
					window.updateTableData();
				});
			}
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
			<div class="btn-div">
				<div class="col-md-2">
					<button type="button" class="btn btn-primary" onclick="MfAssetManage_List.finForminput()">新增</button>
 				</div>
				<div class="col-md-8 text-center">
					<span class="top-title">诉讼费用申请</span>
				</div>
			</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/申请日期"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
				</div>
			</div>
		</div>
	</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
		</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
	<script type="text/javascript">
		filter_dic = [
		      {
				"optCode" : "applyStatus",
				"optName" : "申请状态",
				"parm" : ${classMethodJsonArray},
				"dicType" : "y_n"
			}];
	</script>
</html>

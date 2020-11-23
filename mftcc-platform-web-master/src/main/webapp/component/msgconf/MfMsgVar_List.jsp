<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" >
		
		$(function(){
		    myCustomScrollbar({
		    	obj : "#content", //页面内容绑定的id
		    	url : webPath + "/mfMsgVar/findByPageAjax", //列表数据查询的url
		    	tableId : "tablemsgconf0001", //列表数据查询的table编号
		    	tableType : "thirdTableTag", //table所需解析标签的种类
		    	pageSize:30, //加载默认行数(不填为系统默认行数)
			    callback:function(){
			    		$("table").tableRcswitcher({
			    		name:"flag",onText:"启用",offText:"停用"});
			    	}//方法执行完回调函数（取完数据做处理的时候）
		    });
		 });
			function insertAjax(obj){
				ajaxInput(obj,webPath + "/mfMsgVar/insertAjax?varId=${statu.varId}");
			};
			function updateVar(url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.addFlag = false;
				top.openBigForm(url,"修改变量",function(){
					if(top.addFlag){
						window.updateTableData();
					}
				});
			};
			function addVar(){
				top.window.openBigForm(webPath + '/mfMsgVar/getInsertPage', '新增',function(){
				window.updateTableData();});
	 		};
			
		</script>
	</head>
<body>
   <div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<div class="btn-div">
						<button type="button" class="btn btn-primary" onclick="addVar()">新增</button>
					</div>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=变量名"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
	<script type="text/javascript">
		filter_dic =[{"optCode":"varCol","optName":"来源字段","dicType":"val"},{"optCode":"varName","optName":"变量名","dicType":"val"},
					 {"optCode":"varTb","optName":"来源表","dicType":"val"}];
	</script>
</html>
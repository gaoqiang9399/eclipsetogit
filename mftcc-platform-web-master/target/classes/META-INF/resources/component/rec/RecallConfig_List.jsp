<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>催收配置</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>	
		<script type="text/javascript" src="${webPath}/component/rec/js/recallConfig.js" ></script>	
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" >
		$(function() {
			myCustomScrollbar({
				obj : "#content",//页面内容绑定的id
				url : webPath+"/recallConfig/findByPageAjax",//列表数据查询的url
				tableId : "tablerec0001",
				tableType : "thirdTableTag",//table所需解析标签的种类
				myFilter : true ,//是否有我的筛选
				callback:function(){
			    		$("table").tableRcswitcher({
			    		name:"defSts"});
			    	}//方法执行完回调函数（取完数据做处理的时候）
			});
		});
		</script>
		<style type="text/css">
		.table_content .ls_list tbody tr.selected{
			background-color:#F7F7F7;
			}
		</style>
	</head>
<body style="overflow-y: hidden;">
	<dhcc:markPoint markPointName="RecallConfig_List"/>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<button type="button" class="btn btn-primary pull-left" onclick="func_input('${webPath}/recallConfig/inputAjax');">新增</button>
					<ol class="breadcrumb pull-left">
						<li><a href="${webPath}/sysDevView/setC?setType=model">模型设置</a></li>
						<li class="active">催收配置</li>
					</ol>
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
</body>
</html>
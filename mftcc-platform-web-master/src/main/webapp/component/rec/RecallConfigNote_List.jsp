<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/recallConfigNote/findByPageAjax",//列表数据查询的url
			    	tableId:"tabledlrecallconfignote0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	callback:function(){
			    		$("#tablist td[mytitle]").initMytitle();
			    		$("table").tableRcswitcher({
			    		name:"useFlag",onText:"启用",offText:"停用"});
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
			
			function getById(obj,url){
				window.parent.openBigForm(url,"短信催收模版配置",function(){});
			}
			
			function addRecallConfigNote(){
				top.window.openBigForm(webPath+'/recallConfigNote/input','短信催收模版配置',function(){});
			}
			
		</script>
	</head>
	<body style="overflow-y: hidden;">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<button type="button" class="btn btn-primary pull-left" onclick="addRecallConfigNote();">新增</button>
					<ol class="breadcrumb pull-left">
						<li><a
							href=webPath+"/MfSysKindAction_mfKindConfig.action">产品设置</a></li>
						<li class="active">短信催收模版配置</li>
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

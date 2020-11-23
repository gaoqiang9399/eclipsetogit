<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src='${webPath}/component/pact/js/MfPactFiveclass_His.js'></script>
		<script type="text/javascript" >
			var path = "${webPath}";
			var fincId = "${fincId}";
			var fiveclassId = "${fiveclassId}";
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfPactFiveclass/listHis?fincId="+fincId,//列表数据查询的url
			    	tableId:"tablefiveclasshis0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数),
			    	,callback : function () {
						$("#tablist td[mytitle]:contains('...')").initMytitle();
					}
			    });
			 });
			//查看详情
			function getHisById(obj,url){
				var obj = $(top.window.document).find("body");
				obj.find("#bigFormShowiframe").attr("src",url);
			};
			//返回
			function getBack(){
				LoadingAnimate.start();
				if(top.lawsuitFlag=='query'){
					myclose();
				}else{
					var url = webPath+"/mfPactFiveclass/getById?fiveclassId="+fiveclassId;
					var obj = $(top.window.document).find("body");
					obj.find("#bigFormShowiframe").attr("src",url);
				}
			};
		</script>
	</head>
<body class="overflowHidden">
	<div class="container">
		<!--页面显示区域-->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content" style="height: auto;">
				</div>
			</div>
		</div>
	</div>
</body>
</html>

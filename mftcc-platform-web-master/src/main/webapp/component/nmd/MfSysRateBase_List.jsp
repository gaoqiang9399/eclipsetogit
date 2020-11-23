<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
				initPage(null);
			 });
			 function initPage(typedata){
			 	//刚点击进去，默认查询贷款基准利率
			 	if(typedata==null||typedata==""){
			 		typedata = 1;
			 	}
			 	var strType = String(typedata)
			 	myCustomScrollbar({
			    obj:"#content",//页面内容绑定的id
			    url:webPath+"/mfSysRateBase/findByPageAjax?ratetype="+strType,//列表数据查询的url
			    tableId:"tablebaserate",//列表数据查询的table编号
			    tableType:"thirdTableTag",//table所需解析标签的种类
			    pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 }
		</script>
	</head>
	<body>
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
				<div  class="btn-div">
						<button type="button" class="btn btn-info pull-left"
							 id="selectType001">贷款基准利率</button>
							<button type="button" class="btn btn-info pull-left"
							 id="selectType002">公积金贷款率</button>
							<button type="button" class="btn btn-info pull-left"
							 id="selectType003">贴现基准利率</button>
							 <ol class="breadcrumb pull-left">
								<li><a href="${webPath}/sysDevView/setC?setType=base">基础配置</a></li>
							</ol>
							 <h5 align="right">单位 : % &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</h5>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
		</div>
	</body>
	<script type="text/javascript">
		function clickTopButton(typenumber){
			//清空原有数据
			var contentObject = document.getElementById("content");
			contentObject.innerHTML ="";
			initPage(typenumber);
		}
		//给按钮绑定事件
		document.getElementById("selectType001").onclick=function(){clickTopButton(1)};
		document.getElementById("selectType002").onclick=function(){clickTopButton(2)};
		document.getElementById("selectType003").onclick=function(){clickTopButton(3)};
	</script>
</html>
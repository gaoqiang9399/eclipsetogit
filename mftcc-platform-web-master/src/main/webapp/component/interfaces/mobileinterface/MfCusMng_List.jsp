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
					obj : "#content", //页面内容绑定的id
					url : webPath+"/mfAppCusMng/findByPageAjax", //列表数据查询的url
					tableId : "tableappcusmng0001", //列表数据查询的table编号
					tableType : "thirdTableTag", //table所需解析标签的种类
					pageSize:30, //加载默认行数(不填为系统默认行数)
					//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
					callback:function(){
			     		$("table").tableRcswitcher({
			     			name:"opSts",onText:"启用",offText:"禁用"});
			 		}//方法执行完回调函数（取完数据做处理的时候）
				 });
				 /*我的筛选加载的json*/
				filter_dic = [
			      	  {
		             	  "optCode" : "channelType",
						  "optName" : "渠道类型",
						  "parm" : ${channelTypeJsonArray},
						  "dicType" : "y_n"
		              }
		          ];
			 });
			 
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<div  class="btn-div">
						<button type="button" class="btn btn-primary pull-left"
							onclick="mfAppCusMng.toAddCusMng();">新增</button>
					</div>
					<!-- 我的筛选选中后的显示块 -->
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户经理名称"/>
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
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
		<script type="text/javascript" src="${webPath}/component/interfaces/mobileinterface/js/MfCusMng_List.js"></script>
	</body>
</html>

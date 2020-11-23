<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript">
			$(function(){
			 	initPage();
			 });
			 function initPage(){
			 	 myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url:webPath+"/mfBusCensorFile/findByPageAjax", //列表数据查询的url
				tableId : "tablecensorfile0001", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			    });
			 }
		</script>
<!-- ${webPath}/component/include/myCustomScrollbar.js -->
<!-- /factor/WebRoot/component/oa/js/MfOaEntrance.js -->
 <link rel="stylesheet" href="${webPath}/component/sys/sysextend/css/MfBusCensorFile_List.css" />
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12">
				<div class="btn-div">
					<button type="button" class="btn btn-primary pull-left" onclick="openNewFile();">新增审查文件</button>
					<button type="button" class="btn btn-primary pull-left " onclick="openNewFileBase();">新增审查项</button>	
					<ol class="breadcrumb pull-left">	
						<li><a href="${webPath}/sysDevView/setC?setType=base">基础配置</a></li>
					</ol>
			</div>
				<!-- 列表上部的按钮区域（单独一行的形式），如有需要去掉注释，改为实际需要即可。
					<div class="btn-div">
						<button type="button" class="btn btn-primary" onclick="applyInsert();">进件</button>
					</div>
					-->
				<!-- 自定义筛选+智能搜索区域，参数请看说明。根据类型不同，在此页面看可以相应的调整布局。
					blockType：
						1——//头部只有自定义筛选的情况（无搜索框）
						2——//仅右侧有搜索框的情况，占3列。左侧由引用页面自定义
						3——//头部左侧自定义筛选，右侧搜索框的情况
						4——//头部左侧自定义筛选（无更多选项，财务模块在用），右侧有搜索框的情况
					placeholder：
						智能搜索框的提示信息内容。
					-->
				<div class="search-div">
					<jsp:include
						page="/component/include/mySearch.jsp?blockType=2&placeholder=编号/名称" />
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
	<!-- 测试弹框 -->
	<!--<%@ include file="/component/include/PmsUserFilter.jsp"%>-->
</body>
<!-- /factor/WebRoot/component/oa/consumable/js/MfOaConsList.js -->
<%-- ${webPath}/component/ --%>
<%-- <script type="text/javascript" src="${webPath}/component/oa/consumable/js/MfOaConsList.js"></script> --%>
<script type="text/javascript"
	src="${webPath}/component/include/closePopUpBox.js"></script>
<script type="text/javascript"
	src="${webPath}/component/oa/badge/js/MfOaBadge_List.js"></script>
<script type="text/javascript">
	
	function openNewFile(){
		top.window.openBigForm('${webPath}/mfBusCensorFile/input','新增审查文件',function(){
			initPage();
		});
	}
	
	function openNewFileBase(){
		top.window.openBigForm('${webPath}/mfBusCensorFile/baseinput','新增文件审查项',function(){
			initPage();
		});
	}
	function getByIdThis(url){//详情弹框
			top.window.openBigForm(url,'文件详情',function(){
				initPage();
			});
		}
	</script>
</html>
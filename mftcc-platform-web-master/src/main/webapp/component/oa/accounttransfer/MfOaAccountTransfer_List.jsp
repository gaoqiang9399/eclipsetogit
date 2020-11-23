<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			 	initPage();
			 });
			 function initPage(){
			 	 myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/mfOaAccountTransfer/findByPageAjax", //列表数据查询的url
				tableId : "tableaccount_transfer0001", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			    });
			 }
		</script>
		<!-- ${webPath}/component/include/myCustomScrollbar.js -->
		<!-- /factor/WebRoot/component/oa/js/MfOaEntrance.js -->
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div>
					<div class="btn-div">
						<button type="button" class="btn btn-primary" onclick="openWindow('1');">申请开户</button>
						<button type="button" class="btn btn-primary" onclick="openWindow('2');">申请转账</button>
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
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=章名称"/>
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
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>
	<script type="text/javascript">
	function finForm_input(url){//新增弹框
		top.addFlag = false;
		top.createShowDialog(url,"用章申请",'70','60',function(){
			if(top.addFlag){
				 updateTableData();//重新加载列表数据
	   		}
		});
	}
	
	function openWindow(flag){//大型新增弹框
	//申请开户
	if(flag=="1"){
		top.window.openBigForm(webPath+'/mfOaAccountTransfer/inputNewAccount','开户申请',function(){
			initPage();
		});
	}else{
	//申请转账
		top.window.openBigForm(webPath+'/mfOaAccountTransfer/inputNewTransfer','转账申请',function(){
			initPage();
		});
	}
	/*
		top.window.openBigForm('${webPath}/mfOaAccountTransfer/input','用章申请',function(){
			initPage();
		});
		*/
	}
	
	function getByIdThis(url){//详情弹框
		top.detailFlag = false;
		top.createShowDialog(url,"设置汇率",'70','60',function(){
			if(top.detailFlag){
				 updateTableData();//重新加载列表数据
	   		}
		});
	}
	

	
	</script>	
</html>

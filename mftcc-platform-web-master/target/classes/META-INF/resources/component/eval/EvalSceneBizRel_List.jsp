<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/evalSceneBizRel/findByPageAjax",//列表数据查询的url
			    	tableId:"tableeval4002",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			 function getDetail(url){
			 	window.top.openBigForm(url,"评级场景业务关联信息");
			 }
			 function getUpdate(url){
			 	window.top.openBigForm(url,"评级场景业务关联更新");
			 }
		</script>
	</head>
	<body class="bodybg-gray">
	<div>
		<div style="vertical-align: bottom; display: block;" class="tabCont">
			<strong>评级场景业务关联</strong>
			<div class="search-group">
				<!--我的筛选选中后的显示块-->
				<div class="search-lable" id="pills">
					<dhcc:thirdButton value="新增" action="EvalSceneBizRel001"
						onclick="window.top.openBigForm('${webPath}/evalSceneBizRel/input','评级场景业务关联信息')"></dhcc:thirdButton>
					<!--我的筛选按钮-->
					<div class="filter-btn-group">
						<!--自定义查询输入框-->
						<input placeholder="智能搜索" id="filter_in_input"
							class="filter_in_input" type="text" />
						<div class="filter-sub-group">
							<button id="filter_btn_search" class="filter_btn_search"
								type="button">
								<i class="i i-fangdajing"></i>
							</button>
							<button id="fiter_ctrl_btn" class="filter_btn_myFilter"
								type="button">
								<i class="i i-jiantou7"></i>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--页面显示区域-->
	<div id="content" class="table_content" style="height: auto;"></div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>	
			<script type="text/javascript">
				addDefFliter("0", "评级类型", "evalType", "EVAL_TYPE", "1,2");
				addDefFliter("0", "客户标识", "formal", "FORMAL", "P01,P02,P03,P04,P05,C01,C02,C03,C04,C05,C06,C07,C08");
			</script>
</html>

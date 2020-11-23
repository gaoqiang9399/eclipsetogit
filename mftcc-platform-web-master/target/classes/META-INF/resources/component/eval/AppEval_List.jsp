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
			    	url:webPath+"/appEval/findByPageAjax",//列表数据查询的url
			    	tableId:"tableeval1001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
		</script>
	</head>
	<body>
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
				<strong >评级详情信息</strong>
				<div class="search-group">
					<!--我的筛选选中后的显示块-->
					<div class="search-lable" id="pills">
						<dhcc:thirdButton value="申请" action="申请"
							onclick="window.top.openBigForm('AppEvalAction_input_bigform.action')"></dhcc:thirdButton>
						<dhcc:thirdButton value="发起" action="发起"
							onclick="window.top.openBigForm('${webPath}/appEval/input')"></dhcc:thirdButton>
						<!--我的筛选按钮-->
						<div class="filter-btn-group">
							<!--自定义查询输入框-->
							<input placeholder="智能搜索" id="filter_in_input" class="filter_in_input" type="text" />
							<div class="filter-sub-group">
								<button id="filter_btn_search" class="filter_btn_search"  type="button" ><i class="i i-fangdajing"></i></button>
						 		<button id="fiter_ctrl_btn"  class="filter_btn_myFilter" type="button"  ><i class="i i-jiantou7"></i></button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
		<!--待定是否放置自定义table标签?-->
		</div>
		
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
		<script type="text/javascript">filter_dic =[{"optCode":"rptDate","optName":"报表日期","dicType":"val"},{"optCode":"cusNo","optName":"客户号","dicType":"val"},{"optCode":"cusName","optName":"客户名称","dicType":"val"},{"optCode":"evalSts","optName":"评级申请状态","parm":[{"optName":"未提交","optCode":"0"},{"optName":"上传资料","optCode":"1"},{"optName":"流程中","optCode":"2"},{"optName":"退回","optCode":"3"},{"optName":"通过","optCode":"4"},{"optName":"否决","optCode":"5"}],"dicType":"y_n"}];</script>
	</body>	
</html>

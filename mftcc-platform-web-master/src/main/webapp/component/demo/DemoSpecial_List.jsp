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
			    	url:webPath+"/demo/findByPageAjax",//列表数据查询的url
			    	tableId:"tabledemo0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	myFilter:true, //是否有我的筛选
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
		</script>
		<style>
			.btn-group{right:-8px;}
			.search-lable{ margin-right:10px;}
		</style>
	</head>
	<body class="body_bg">
		<!--标记点 未后退准备-->
		<dhcc:markPoint markPointName="demo_list"/>
		<div>
			<div style="vertical-align: bottom;display: block;" class="tabCont">
				<strong>用户信息列表</strong>
				<div class="search-title">
					<i class="i i-yanjing"></i>
					<span value="demoId ,demoName,demoPosition,demoWork,demoTime,demoTel,demoCount,demoDay,demoMaxDay ,demoAmt,demoOther">合同情况</span>
					<div class="btn-group">
						<i class="fa fa-angle-down " data-toggle="dropdown"></i>
						<ul class="dropdown-menu">
							<li>
								<a onclick="showTable(this)" value="demoId ,demoName,demoPosition,demoWork,demoTime,demoTel,demoCount,demoDay,demoMaxDay ,demoAmt,demoOther">合同情况</a>
							</li>
							<li>
								<a onclick="showTable(this)" value="demoId ,demoName,demoPosition,demoWork,demoTime,demoTel,demoCount">客户情况</a>
							</li>
							<li>
								<a onclick="showTable(this)" value="demoId ,demoName,demoPosition,demoWork,demoTime,demoTel,demoAmt,demoOther ">融资情况</a>
							</li>
						</ul>
					</div>
				</div>

				<div class="search-group">
					<!--我的筛选选中后的显示块-->
					<div class="search-lable" id="pills">
						<dhcc:thirdButton value="新增" action="新增"
							onclick="window.top.openBigForm('${webPath}/demo/input')"></dhcc:thirdButton>
						<!--自定义查询输入框-->
						<input id="filter_in_input" class="filter_in_input" type="text">
						<!--我的筛选按钮-->
						<div class="btn-group open">
							<button id="fiter_ctrl_btn" class="btn btn-default fiter_ctrl_btn filter_btn_myFilter "><i class="i i-jiantou7"></i></button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content"  style="height: auto;">
			<!--待定是否放置自定义table标签?-->
		</div>
	    <%@ include file="./PmsUserFilter.jsp"%>
	</body>	
	<script type="text/javascript">
		/*我的筛选加载的json*/
		filter_dic = [
		      {
                  "optName": "余额",
                  "parm": [],
                  "optCode":"money",
                  "dicType":"num"
              }, {
                  "optName": "天数",
                  "parm": [],
                  "optCode":"demoDay",
                  "dicType":"num"
              }, {
                  "optName": "演示金额",
                  "parm": [],
                  "optCode":"demoAmt",
                  "dicType":"num"
              }, {
                  "optName": "演示名称",
                  "parm": [],
                  "optCode":"demoName",
                  "dicType":"val"
              }
          ];
	</script>
</html>

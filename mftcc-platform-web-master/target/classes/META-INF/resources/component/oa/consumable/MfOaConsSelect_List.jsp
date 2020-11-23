<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
				var classNo = $("#classNo").val();
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfOaCons/findByPageAjax?classNo="+classNo,//列表数据查询的url
			    	tableId:"tableconsselect0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			 });
			function choseCons(parm){
				parm=parm.split("?")[1];
				var parmArray=parm.split("&");
				var consInfo = new Object();
				consInfo.consNo = parmArray[0].split("=")[1];
				consInfo.consName = parmArray[1].split("=")[1];
				parent.dialog.get('consDialog').close(consInfo).remove();
			};
		</script>
	</head>
	
	<body class="bg-white" style="overflow-y: hidden;">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div col-xs-8 pull-right"">
						<div class="znsearch-div">
							<div class="input-group pull-right">
								<i class="i i-fangdajing"></i>
								<input type="text" class="form-control" id="filter_in_input" placeholder="智能搜索">
								<span class="input-group-addon" id="filter_btn_search">搜索</span>
							</div>
						</div>
						</div>
					</div>
				</div>
				<input type="hidden" id="classNo" value="${requestScope.classNo}">
				<!--页面显示区域-->
				<div class="row clearfix">
					<div class="col-md-12 column">
						<div id="content" class="table_content"  style="height: auto;">
						</div>
					</div>
				</div>
			</div>
	</body>
</html>

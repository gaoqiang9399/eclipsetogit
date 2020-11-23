<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
<script type="text/javascript">
	$(function() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/processInstance/findByPageAjax",//列表数据查询的url
			tableId : "tablewkf0022",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			myFilter : true
		//是否有我的筛选
		});
	});
</script>
	<body style="overflow-y: hidden;">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-md-9 column  color_theme">
							<ol class="breadcrumb pull-left padding_left_0">
								<li><a href="${webPath}/sysDevView/setC?setType=developer">开发者设置</a></li>
								<li class="active">流程实例信息列表 </li>
							</ol>
						</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2"/>
					</div>
				</div>
			</div>
			<!--页面显示区域-->
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
						<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript" >
	var cusNo = '${cusNo}';
	$(function(){
		myCustomScrollbar({
			obj:"#content",//页面内容绑定的id
			url:webPath+"/archiveConfig/findByPageAjax",//列表数据查询的url
			tableId:"tablearchiveconfiglist",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			data:{cusNo:cusNo},//指定参数
			pageSize:30//加载默认行数(不填为系统默认行数)
		});
	 });

	//新增界面
	function input() {
		top.openBigForm(webPath + "/archiveConfig/input", "新增", function () {
			window.location.reload();
		});
	}

	function getById(obj,lk) {
		top.openBigForm(webPath + lk, "编辑", function () {
			window.location.reload();
		});
	}
</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="btn-div">
				<dhcc:pmsTag pmsId="auth-credit-list-add">
					<button type="button" class="btn btn-primary" onclick="input();">新增</button>
				</dhcc:pmsTag>
			</div>
			<%--<div class="search-div">
				<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/授信申请号"/>
			</div>--%>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div id="content" class="table_content"  style="height: auto;">
			</div>
		</div>
	</div>
</div>
<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">
	/*我的筛选加载的json*/
	filter_dic = [

	  ];
</script>
</html>

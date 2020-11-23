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
			url:webPath+"/mfVouAfterPayManage/findListByAjax",//列表数据查询的url
			tableId:"tablemfvouafterpaymanageList",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			data:{},//指定参数
			pageSize:30,//加载默认行数(不填为系统默认行数)
			callback:function(){
			}
		});
	 });

	function addPayManage() {
		top.openBigForm("${webPath}/mfVouAfterPayManage/input", "支出申请", function () {
			window.location.reload();
		});
	}

	//修改
	function editPayManage(obj, lk) {
		top.openBigForm(webPath+lk, "编辑", function () {
			window.location.reload();
		});
	}
</script>
</head>
<body class="overflowHidden">
<div class="container" >
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div style="display:none;">
				<input name="cusName" type="hidden"></input>
				<input name="cusNo" type="hidden"></input>
			</div>
			<div class="btn-div">
				<button type="button" class="btn btn-primary" onclick="addPayManage();">新增</button>
			</div>
			<div class="search-div">
				<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称"/>
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div id="content" class="table_content"  style="height: auto;max-height: calc(100% - 140px);">
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

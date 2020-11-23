<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_List.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	$(function(){
	  myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/mfCusProspective/findByPageAjax",//列表数据查询的url
			tableId : "tablecusprospective0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
		});
	 });
	function getDetailPage(obj,url){		
		top.openBigForm(webPath+url,"潜在客户信息", function(){});
	}
	function getDetail(obj,url){
		top.openBigForm(webPath+url,"客户跟进登记", function(){
		updateTableData();
		}); 
	 }
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div style="display:none;">
					<input name="kindName" type="hidden"></input>
					<input name="kindNo" type="hidden"></input>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称"/>
				</div>
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
	filter_dic = [
		    {
				"optCode" : "trackType",
				"optName" : "跟进结果",
				"parm" : ${jsonArrayByKeyName},
				"dicType" : "y_n"
			}];
</script>
</html>

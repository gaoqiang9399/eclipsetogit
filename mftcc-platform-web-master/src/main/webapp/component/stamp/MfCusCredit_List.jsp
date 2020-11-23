<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<%--<script type="text/javascript" src="${webPath}/component/stamp/js/MfCusCredit_List.js?v=${cssJsVersion}"></script>--%>
<style type="text/css">
	.change-td-color-4{
		color:red !important;
	}
	.change-td-color-5{
		color:green !important;
	}
	.change-td-color-7{
		color:red !important;
	}
	.change-td-color-8{
		color:red !important;
	}
	.change-td-color-6{
		color:red !important;
	}

</style>
<script type="text/javascript">
	$(function(){
		myCustomScrollbar({
			obj: "#content",//页面内容绑定的id
			url: webPath + "/mfStampCredit/findStampContract",//列表数据查询的url
			tableId: "tablestampcreditListBase",//列表数据查询的table编号
			tableType: "tableTag",//table所需解析标签的种类
			pageSize: 30//加载默认行数(不填为系统默认行数)
		});
	});

	function stampApply(obj,lk) {
		top.openBigForm(webPath + lk, "用印申请", function () {
			window.location.reload();
		});
	}

	function sealApply(obj,lk) {
		top.openBigForm(webPath + lk, "盖章申请", function () {
			window.location.reload();
		});
	}

	function registApply(obj,lk) {
		top.openBigForm(webPath + lk, "合同登记", function () {
			window.location.reload();
		});
	}

	function shilfTab(){
		window.location.href = webPath+"/mfStampPact/getListPage";
	}

</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div style="display:none;">
					<input name="cusName" type="hidden"></input>
					<input name="cusNo" type="hidden"></input>
				</div>
				<div class="btn-div">
					<dhcc:pmsTag pmsId="auth-credit-list-add">
						<button type="button" class="btn btn-primary" onclick="shilfTab();">单笔合同视角</button>
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
		     {
                  "optName": "授信总额",
                  "parm": [],
                  "optCode":"creditSum",
                  "dicType":"num"
              }, {
                  "optName": "授信期限",
                  "parm": [],
                  "optCode":"creditTerm",
                  "dicType":"num"
              },{
                  "optName": "授信开始日期",
                  "parm": [],
                  "optCode":"beginDate",
                  "dicType":"date"
              },{
                  "optName": "授信结束日期",
                  "parm": [],
                  "optCode":"endDate",
                  "dicType":"date"
              }
          ];
          
	</script>
</html>

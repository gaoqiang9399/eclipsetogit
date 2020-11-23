<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript" src="${webPath}/component/stamp/js/MfCusCredit_List.js?v=${cssJsVersion}"></script>
	<script type="text/javascript" >
	var cusNo = '${cusNo}';
    var queryType = "${queryType}";
    var url = webPath+"/mfStampPact/findPactListByAjax";
    var tableId = "tablestamppactList";
    if(queryType == "2"){//工程担保
        url = webPath+"/mfStampPact/findPactListGCDBByAjax";
        tableId = "tablestamppactList_GCDB";
    }
	$(function(){
		myCustomScrollbar({
			obj:"#content",//页面内容绑定的id
			url:url,//列表数据查询的url
			tableId:tableId,//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			data:{cusNo:cusNo},//指定参数
			pageSize:30//加载默认行数(不填为系统默认行数)
		});
	 });

	function stampApply() {
		top.openBigForm(webPath + "/mfStampPact/input?queryType=" + queryType, "用印申请", function () {
			updateTableData();
		});
	}
    function stampApplyDetail(obj,lk) {
        top.openBigForm(webPath + lk + "&queryType="+queryType, "用印申请详情", function () {
            updateTableData();
        });
    }

	function registApply(obj,lk) {
		top.openBigForm(webPath + lk, "合同登记", function () {
            updateTableData();
		});
	}

	function shilfTab(){
		window.location.href = webPath+"/mfStampCredit/getListPage";
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
					<button type="button" class="btn btn-primary" onclick="stampApply();">新增用印</button>
				</dhcc:pmsTag>
			</div>
			<div class="search-div">
				<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/授信合同号/委托保证合同"/>
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
	/*我的筛选加载的json*/
	filter_dic = [
		  {
			  "optName": "合同金额",
			  "parm": [],
			  "optCode":"pactAmt",
			  "dicType":"num"
		  }, {
			  "optName": "签订日期",
			  "parm": [],
			  "optCode":"signDate",
			  "dicType":"date"
		  }, {
			  "optName": "合同期限",
			  "parm": [],
			  "optCode":"term",
			  "dicType":"num"
		  },{
			  "optCode" : "pactSts",
			  "optName" : "业务阶段",
			  "parm" : [ {
				 "optName" : "未完善",
				 "optCode" : "0"
			   }, {
				 "optName" : "未提交",
				 "optCode" : "1"
			   }, {
				 "optName" : "流程中",
				 "optCode" : "2"
			  }, {
				 "optName" : "退回",
				 "optCode" : "3"
			  }, {
				 "optName" : "审批通过",
				 "optCode" : "4"
			  }, {
				 "optName" : "已否决",
				 "optCode" : "5"
			  }, {
				 "optName" : "已完结",
				 "optCode" : "6"
			  } ],
			  "dicType" : "y_n"
		  },{
			  "optName": "开始日期",
			  "parm": [],
			  "optCode":"beginDate",
			  "dicType":"date"
		  },
	  ];
</script>
</html>

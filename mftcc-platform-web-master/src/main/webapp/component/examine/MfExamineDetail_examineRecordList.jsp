<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	<script type="text/javascript" src="${webPath}/component/examine/js/MfExamineDetail_examineRecordList.js"></script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="btn-div">
				<div class="col-md-2">
					<dhcc:pmsTag pmsId="query_examine_info">
						<button type="button" class="btn btn-primary" onclick="addExamineDetail('${webPath}/mfExamineHis/examineHisRegister','0');">新增</button>
					</dhcc:pmsTag>
				</div>
				<div class="col-md-8 text-center">
					<span class="top-title">保后检查登记</span>
				</div>
			</div>


			<div class="search-div" id="search-div">
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
    /*我的筛选加载的json*/
    filter_dic =[
        <%--{--%>
        <%--"optName": "检查主体",--%>
        <%--"parm": ${examineDataObjJsonArray},--%>
        <%--"optCode":"pasMinNo",--%>
        <%--"dicType":"y_n"--%>
    	<%--},{--%>
        <%--"optName": "是否逾期",--%>
        <%--"parm": ${isOverdueJsonArray},--%>
        <%--"optCode":"isOverdue",--%>
        <%--"dicType":"y_n"--%>
    <%--},{--%>
        <%--"optName": "五级分类",--%>
        <%--"parm": ${fiveClassJsonArray},--%>
        <%--"optCode":"fiveClass",--%>
        <%--"dicType":"y_n"--%>
    <%--},{--%>
        <%--"optName": "检查结果",--%>
        <%--"parm": ${riskLevelJsonArray},--%>
        <%--"optCode":"riskLevel",--%>
        <%--"dicType":"y_n"--%>
    <%--},{--%>
        <%--"optName": "检查状态",--%>
        <%--"parm": ${examStsJsonArray},--%>
        <%--"optCode":"examineSts",--%>
        <%--"dicType":"y_n"--%>
    <%--},{ "optCode" : "amtRest",--%>
        <%--"optName" : "贷款余额",--%>
        <%--"dicType" : "num"--%>
    <%--},{--%>
        <%--"optName": "检查日期",--%>
        <%--"optCode":"beginDate",--%>
        <%--"dicType":"date"--%>
    <%--}--%>
    ];

</script>
</html>
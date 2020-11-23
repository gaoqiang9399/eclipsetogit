<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>风控尽调</title>
    <link rel="stylesheet" href="${webPath}/component/thirdservice/juxinli/css/iconfont.css" />
    <link rel="stylesheet" href="${webPath}/component/thirdservice/juxinli/css/potreport.css" />
    <script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_RiskManagementResult.js"></script>
    <script type="text/javascript">
        var threeParty = '${threeParty}';
        var returnId = '${returnId}';
        var token = '${token}';
        var id = '${id}';
        $(function() {
            MfBusApply_RiskManagementResult.init();
        });
    </script>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag fourColumn">
                <form method="post" id="inputCommonForm" theme="simple" name="operform" >
                </form>
            </div>
        </div>
    </div>
    <div id="bussButton" class="formRowCenter">
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>

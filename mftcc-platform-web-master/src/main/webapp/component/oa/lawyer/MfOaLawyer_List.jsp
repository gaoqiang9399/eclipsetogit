<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%@ include file="/component/include/pub_view_table.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>列表</title>
    <script type="text/javascript">
        var opNo = '${opNo}';
        var fileName = '${fileName}';
        $(function () {
            MfOaLawyer_List.init();
        });
    </script>
</head>
<body class="overflowHidden">
<div class="container">
    <div class="row clearfix bg-white tabCont">
        <div class="col-md-12">
            <div class="btn-div">
                <div class="col-md-2">
                    <button type="button" class="btn btn-primary" onclick="MfOaLawyer_List.lawyerInsert();">新增</button>
                </div>
                <div class="col-md-8 text-center">
                    <span class="top-title">律师管理</span>
                </div>
            </div>
            <div class="search-div">
                <jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=律师姓名/证件号"/>
            </div>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12">
            <div id="content" class="table_content" style="height: auto;">
            </div>
        </div>
    </div>
</div>
<%--<%@ include file="/component/include/PmsUserFilter.jsp" %>--%>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
<script type="text/javascript" src="<%=webPath %>/component/oa/lawyer/js/MfOaLawyer_List.js"></script>
</body>
</html>

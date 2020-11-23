<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%@ include file="/component/include/pub_view_table.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>列表</title>
    <script type="text/javascript" src="${webPath}/component/cus/js/MfCusAssureCompany_List.js"></script>
    <script type="text/javascript">
        $(function () {
            MfCusAssureCompany_List.init();
        });
    </script>
</head>
<body class="overflowHidden">
<div class="container">
    <div class="row clearfix bg-white tabCont">
        <div class="col-md-12 column">
            <div class="btn-div">
                <div class="col-md-2">
                    <button type="button" class="btn btn-primary pull-left"
                            onclick="MfCusAssureCompany_List.input('${webPath}/mfCusAssureCompany/cusInput');">新增
                    </button>
                </div>
                <div class="col-md-8 text-center">
                    <span class="top-title">担保公司</span>
                </div>
            </div>
            <div class="search-div" id="search-div">
                <jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=公司名称"/>
            </div>
        </div>
    </div>
</div>
<div class="row clearfix">
    <div class="col-md-12 column">
        <div id="content" class="table_content" style="height: auto;"></div>
    </div>
</div>
</body>
</html>

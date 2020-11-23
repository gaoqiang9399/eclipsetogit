<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<%@ include file="/component/include/pub_view_table.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>列表</title>
    <script type="text/javascript" src="${webPath}/component/app/js/accountBalanceList.js?v=${cssJsVersion}"></script>
    <script type="text/javascript">
        var cusNo = '${cusNo}';
        var queryType = '${queryType}';

        $(function () {
            accountBalanceList.init();
        });

    </script>
</head>
<body class="overflowHidden">
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div id="content" class="table_content" style="height: auto;">
            </div>
        </div>
    </div>
</div>
</body>
</html>

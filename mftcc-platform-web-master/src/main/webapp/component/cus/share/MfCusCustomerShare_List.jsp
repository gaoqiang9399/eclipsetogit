<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>客户共享</title>
</head>
<body class="overflowHidden">
<div class="container form-container">

   <div class="row  bg-white">
        <div class="col-md-12 column" style="height: 50px; padding: 8px 0px 8px 15px;">
            <div class="search-div">
                <button type="button" class="btn btn-primary" onclick="MfCusCustomerShare.openInputPage();">客户共享</button>
            </div>
        </div>
    </div>
    <br/>
    <%--客户共享列表--%>
    <div id="cusCustomerShare" class="table_content">
        <dhcc:tableTag paginate="customerShareList" property="cusCustomerShareList" head="true" />
    </div>
</div>
</body>
<script type="text/javascript" src="${webPath}/component/cus/share/js/MfCusCustomerShare.js"></script>
<script type="text/javascript">
    cusNo = '${cusNo}';
    $(function() {
        MfCusCustomerShare.init();
    });
</script>
</html>

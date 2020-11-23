<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/component/include/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>后台管理系统</title>
    <script>
        $(document).ready(function () {

        });

        function validateSub(obj) {
            $(obj).submit();
        }
    </script>
</head>
<body class="overflowHidden bg-white">

<div class="container form-container" id="normal">
    <div class="scroll-content">
        <div class="col-md-10 col-md-offset-1 column margin_top_20">
            <div class="bootstarpTag fourColumn">
                <form id="validateSubForm" method="post" theme="simple" name="operform" action="${webPath}/sysLogin/validateSub">
                    <dhcc:bootstarpTag property="setViewValidate" mode="query"/>
                </form>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <dhcc:thirdButton value="验证" action="验证" onclick="validateSub('#validateSubForm');"></dhcc:thirdButton>
    </div>
</div>

</body>
</html>
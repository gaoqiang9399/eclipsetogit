<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<style>
    .table-c table{border-right:1px solid #AFC5D0;border-bottom:1px solid #AFC5D0}
    .table-c table td{border-left:1px solid #AFC5D0;border-top:1px solid #AFC5D0}
</style>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content" style="overflow: auto">
        <div id="htmlStr" class="table-c" style="margin-top: 2%;"/>
    </div>
</div>
<script type="text/javascript">
    var htmlStr = '${htmlStr}';
    $(function() {
        // $(".scroll-content").mCustomScrollbar({
        //     advanced: {
        //         //滚动条根据内容实时变化
        //         updateOnContentResize: true
        //     }
        // });
        $("#htmlStr").html(htmlStr);
        $("#mCSB_1").css("overflow-x", "auto");
    });
</script>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>忽略原因</title>
</head>
<body class="overflowHidden bg-white">
<div class="container form-container">
    <div class="scroll-content">
        <div class="col-md-8 col-md-offset-2 column margin_top_20">
            <div class="bootstarpTag">
                <form method="post" theme="simple" name="operform" id="ignoreExamineTaskForm" action="#">
                    <dhcc:bootstarpTag property="formignoreExamineDetail" mode="query" />
                </form>
            </div>
        </div>
    </div>
    <div class="formRowCenter">
        <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_showDialog();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>

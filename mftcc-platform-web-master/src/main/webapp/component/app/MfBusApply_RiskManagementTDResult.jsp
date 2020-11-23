<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<title>风控尽调</title>
    <script src="https://code.jquery.com/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="http://cdnjs.tongdun.cn/bodyguard/tdreportv4.1.0.min.js"></script>
</head>
<body >

<script type="text/javascript">
    $.showReport(${td_data});
</script>
</body>
</html>

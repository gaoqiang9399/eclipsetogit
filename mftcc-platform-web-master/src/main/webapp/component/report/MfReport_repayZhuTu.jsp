<%@ page language="java" contentType="text/html; charset=Utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>还款柱状图</title>
<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
<link rel="stylesheet" href="css/MfReport.css" />
</head>
<body class="bg-white">
	<div class="container">
	<form class="form-horizontal" role="form">
	<fieldset>
		<legend>还款柱状图</legend>
		<div class="form-group">
			<label class="col-xs-4 control-label" for="date1">查询期限：</label>
			<div class="col-xs-1">
				<input class="form-control" name="date1" id="date1" type="text">
			</div>
			<div class="col-xs-1 tubiao"><i class="i i-rili"></i>&nbsp;&nbsp;&nbsp;&nbsp;-----</div>
			<div class="col-xs-1">
				<input class="form-control" name="date2" id="date2" type="text">
			</div>
			<div class="col-xs-1 tubiao"><i class="i i-rili"></i>&nbsp;</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label" for="opno">操作员：</label>
			<div class="col-xs-4">
				<input class="form-control" name="opno" id="opno" type="text" />
				<input class="form-control" name="opname" id="opname" type="text" />
			</div>
		</div>
		<br />
		<div class="form-group">
			<div class="col-xs-offset-5 col-xs-2">
				<div id="query" class="btn btn-primary btn-lg pull-left">查询</div>
				<div id="reset" class="btn btn-default btn-lg pull-right">重置</div>
			</div>
		</div>
	</fieldset>
	</form>
	</div>
</body>
<script type="text/javascript" src="js/MfReport_repayZhuTu.js"></script>
<script>
	$(function(){
		repayZhuTu.init();
	});
</script>
</html>
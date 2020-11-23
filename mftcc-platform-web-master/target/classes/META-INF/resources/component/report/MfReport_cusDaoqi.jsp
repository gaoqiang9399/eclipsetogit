<%@ page language="java" contentType="text/html; charset=Utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>客户到期</title>
<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
<link rel="stylesheet" href="css/MfReport.css" />
</head>
<body class="bg-white">
	<div class="container">
	<form class="form-horizontal" role="form">
	<fieldset>
		<legend>客户到期</legend>
		<div class="form-group">
			<label class="col-xs-4 control-label" for="date1">到期日期(指合同到期日期)：</label>
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
			<label class="col-xs-4 control-label" for="opname">登记人：</label>
			<div class="col-xs-4">
				<input class="form-control" name="opname" id="opname" type="text">
				<input class="form-control" name="opno" id="opno" type="text">
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label" for="kind_name">产品名称：</label>
			<div class="col-xs-4">
				<input class="form-control" name="kind_name" id="kind_name" type="text">
				<input class="form-control" name="kind_no" id="kind_no" type="text">
			</div>
		</div>
		<br />
		<div class="form-group">
			<div class="col-xs-offset-5 col-xs-2">
				<div id="query" class="btn btn-info btn-lg pull-left">查询</div>
				<div id="reset" class="btn btn-default btn-lg pull-right">重置</div>
			</div>
		</div>
	</fieldset>
	</form>
	</div>
</body>
<script type="text/javascript" src="js/MfQuery_cusDaoqi.js"></script>
<script>
	$(function(){
		cusDaoqi.init();
	});
</script>
</html>
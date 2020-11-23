<%@ page language="java" contentType="text/html; charset=Utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>余额汇总</title>
<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
<link rel="stylesheet" href="css/MfReport.css" />
</head>
<body class="bg-white">
<div class="container">
	<form class="form-horizontal" role="form">
	<fieldset>
		<legend>余额汇总</legend>
		<div class="form-group">
			<label class="col-xs-4 control-label" for="opname">业务经理：</label>
			<div class="col-xs-4">
				<input type="text" name="opname" id="opname" class="form-control"/>
				<input type="text" name="opno" id="opno" class="form-control"/>
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
<script type="text/javascript" src="js/MfReport_yue.js"></script>
<script>
	$(function(){
		yue.init();
	});
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=Utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>客户分布</title>
<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
<link rel="stylesheet" href="css/MfReport.css" />
<style>
	#chartType{
	/* 仿照.form-control的样式  */
		height:34px;
		border-radius:4px;
	}
</style>
</head>
<body class="bg-white">
<div class="container">
	<form class="form-horizontal" role="form">
	<fieldset>
		<legend>客户分布</legend>
		<div class="form-group">
			<label class="col-xs-4 control-label" for="opname">登记人：</label>
			<div class="col-xs-4">
				<input type="text" name="opname" id="opname" class="form-control"/>
				<input type="text" name="opno" id="opno" class="form-control"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-xs-4 control-label" >客户性质：</label>
			<div>
				<select name="chartType" id="chartType" class="col-xs-4 selectpicker" data-style="btn-primary">
					<option value="wayclass">按行业分类</option>
					<option value="custype">按客户类型</option>
					<option value="manage">按客户经理</option>
				</select>
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
<script type="text/javascript" src="js/MfReport_cusDistribute.js"></script>
<script>
	$(function(){
		cusDistribute.init();
	});
</script>
</body>
</html>
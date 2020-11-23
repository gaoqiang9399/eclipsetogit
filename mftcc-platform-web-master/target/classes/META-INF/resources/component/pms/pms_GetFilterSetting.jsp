<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/webPath.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>


<head>
<meta charset="UTF-8">
<title></title>
<%--bootstrap框架--%>
<script type="text/javascript" src="${webPath}/component/pms/js/jquery.min.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript" src="${webPath}/UIplug/zTree/jquery.ztree.all.js"></script>
<link rel="stylesheet" href="${webPath}/component/pms/sweetAlert/sweetalert.css" />
<script type="text/javascript" src="${webPath}/component/pms/sweetAlert/sweetalert.min.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.min.js"></script>
<script type="text/javascript" src="${webPath}UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css">
<link rel="stylesheet" href="${webPath}/UIplug/zTree/metroStyle/metroStyle.css">
<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="${webPath}/component/pms/css/filter_setting.css" />
<script type="text/javascript" src="${webPath}/component/pms/js/filter_setting.js"></script>
<script type="text/javascript">
	(function($){
	    $(window).load(function(){
	    	$(".table_tree_body").mCustomScrollbar({
	    		scrollButtons:{
	    			enable:false,
	    			scrollType:"continuous",
	    			scrollSpeed:20,
	    			scrollAmount:40
	    		},
	    	 	horizontalScroll:false,
	    	});
	    	$(".filter_tree_body").mCustomScrollbar({
	    		scrollButtons:{
	    			enable:false,
	    			scrollType:"continuous",
	    			scrollSpeed:20,
	    			scrollAmount:40
	    		},
	    	 	horizontalScroll:false,
	    	});
	    });
	})(jQuery);
</script>
</head>

<body>
<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div class="page-header" style="margin: 0px;">
				<h3 class="text-center text-info">自定义筛选代码生成</h3>
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-4 column">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div class="form-group">
						<label >选择表</label>
						<select id="tableName" class="form-control" >
							<option value="" disabled selected>Select a Table</option>
						</select>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column table_tree_body">
					<ul class="unstyled ztree" id="columnName_tree">
					</ul>
				</div>
			</div>
		</div>
		<div class="col-md-4 column">
			<div class="row clearfix">
				<div class="col-md-12 column filter_tree_body">
					<ul class="unstyled ztree" id="filter_setting_tree">
					</ul>
				</div>
			</div>
		</div>
		<div class="col-md-4 column">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div class="form-group">
						<label class="control-label" for="inputEmail">显示名称</label>
						<input id="optName" type="text" class="form-control" />
					</div>
					<div class="form-group">
						<label class="control-label" for="inputPassword">条件类型</label>
						<select id="dicType" class="form-control" >
							<option value=""></option>
							<option value="y_n">字典项类型</option>
							<option value="num">数字金额类型</option>
							<option value="val">字符类型</option>
							<option value="date">日期类型</option>
						</select>
					</div>
					<div id="parmDiv" class="form-group">
						<label class="control-label" for="inputPassword">字典项名称(KEY_NAME)</label>
						<input id="parm" type="text" class="form-control" />
					</div>
					<div class="form-group">
							<input type="hidden" id="optCode" />
							<button type="button" id="save_btn" class="btn btn-default btn-primary">添加</button>
							<button type="button" id="del_btn" class="btn btn-default btn-danger">删除</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column col_textarea">
			<textarea spellcheck="false" class="text-success" id="show_code"></textarea>
		</div>
	</div>
</div>

<!-- 
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<h3 class="text-center text-info">自定义筛选代码生成</h3>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span4">
				<div class="control-group">
					<label class="control-label" for="inputPassword">选择表</label>
					<div class="controls">
						<select id="tableName">
							<option value=""></option>
						</select>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<input class="input-medium search-query" id="tableName"
							value="sys_user" type="text" />
						<button type="button" onclick="setTableName()" class="btn">获取字段</button>
					</div>
				</div>
				<div class="control-group columnName_body" >
					<ul class="unstyled ztree" id="columnName_tree">
					</ul>
				</div>
			</div>
			<div class="span4">
				<ul class="unstyled ztree" id="filter_setting_tree">
				</ul>
			</div>
			<div class="span4">
				<div class="control-group">
					<label class="control-label" for="inputEmail">显示名称</label>
					<div class="controls">
						<input id="optName" type="text" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="inputPassword">条件类型</label>
					<div class="controls">
						<select id="dicType">
							<option value=""></option>
							<option value="y_n">字典项类型</option>
							<option value="num">数字金额类型</option>
							<option value="val">字符类型</option>
						</select>
					</div>
				</div>
				<div id="parmDiv" class="control-group">
					<label class="control-label" for="inputPassword">字典项名称(KEY_NAME)</label>
					<div class="controls">
						<input id="parm" type="text" />
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<input type="hidden" id="optCode" />
						<button type="button" id="save_btn" class="btn">保存</button>
						<button type="button" id="del_btn" class="btn">删除</button>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span12">
				<textarea spellcheck="false" class="text-success" id="show_code"></textarea>
			</div>
		</div>
	</div> -->
</body>

</html>
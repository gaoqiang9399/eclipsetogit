<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript" >
    var accProperty = '${accProperty}';
	$(function() {
		myCustomScrollbar({
			obj: "#content", //页面内容绑定的id
			url: webPath + "/cwCusBankAccManage/findByPageAjax?accProperty="+accProperty, //列表数据查询的url
			tableId: "tablebankaccselect", //列表数据查询的table编号
			tableType: "thirdTableTag", //table所需解析标签的种类
			pageSize: 30 //加载默认行数(不填为系统默认行数)
		});
	});

	//选择公司担保账号
	function choseBank(parm){
		var id = parm.split("=")[1];
		$.ajax({
			url: webPath + "/cwCusBankAccManage/getByIdAjax?id="+id,
			dataType: "json",
			type: "POST",
			success: function (data) {
				if (data.flag == "success") {
					parent.dialog.get('cusBankAccDialog').close(data.cwCusBankAccManage).remove();
				} else {
					alert(top.getMessage("ERROR_SELECT"));
				}
			}, error: function () {
				alert(top.getMessage("ERROR_DATA_CREDIT", "担保账号信息"));
			}
		});
	}
</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12">
			<div class="search-div">
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12">
			<div id="content" class="table_content" style="height: auto;">
			</div>
		</div>
	</div>
</div>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript">
	var accLength = '${accLength}';
	$(function() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url:webPath+"/cwComItem/findByPage1Ajax",//列表数据查询的url
			tableId : "tableaccnoLength001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			data : {
				"ajaxData" : accLength
			},//指定参数
			ownHeight : true,
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			callback : function() {
			}//方法执行完回调函数（取完数据做处理的时候）
		});

	});
</script>
</head>
<body style="overflow-y: hidden;">
	<div class="container">
	</div>
	<!--页面显示区域-->
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div id="content" class="table_content" style="height: auto;">
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function addAccnoLength() {
		//var accLength = $("#accLength").text();
		top.addFlag = false;
		top.createShowDialog("${webPath}/cwComItem/inputAccLength", "科目编号长度设置",
				'30', '70', function() {
					if (top.addFlag) {
						updateTableData();//重新加载列表数据

					}
				});
	}
</script>
</html>

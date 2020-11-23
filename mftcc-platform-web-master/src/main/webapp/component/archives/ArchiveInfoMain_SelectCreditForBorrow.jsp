<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	<script type="text/javascript" >
		var cusNo = "${cusNo}";
		var archiveStatus = "${archiveStatus}";
		var archivePactStatus = "${archivePactStatus}";
		var dealMode = "${dealMode}";
		$(function(){
			myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath + "/archiveInfoMain/findCreditForBorrowAjax?archiveStatus=" + archiveStatus + "&cusNo="+ cusNo+"&dealMode="+dealMode+"&archivePactStatus="+archivePactStatus, //查询已归档，待封档的数据
				tableId : "tablearchivemianselectcredit", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			});
		});

		//选择授信信息
		function choseCredit(parm){
			var creditAppId = parm.split("=")[1];
			$.ajax({
				url: webPath + "/archiveInfoMain/getArchiveInfoMainByIdAjax?creditAppId="+creditAppId+"&archivePactStatus="+archivePactStatus,
				dataType: "json",
				type: "POST",
				success: function (data) {
					if (data.flag == "success") {
						parent.dialog.get('archiveCreditAppDialog').close(data.archiveInfoMain).remove();
					} else {
						alert(top.getMessage("ERROR_SELECT"));
					}
				}, error: function () {
					alert(top.getMessage("ERROR_DATA_CREDIT", "归档项目信息"));
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

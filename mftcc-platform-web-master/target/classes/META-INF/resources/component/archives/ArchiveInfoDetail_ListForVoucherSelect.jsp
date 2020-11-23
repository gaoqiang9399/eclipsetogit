<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	<script type="text/javascript" >
		var archivePactStatus = "${archivePactStatus}";
		var relationId = "${relationId}";
		var docType = "${docType}";
		$(function(){
			myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath + "/archiveInfoDetail/findVoucherByPage?relationId="+ relationId+"&docType="+docType+"&archivePactStatus="+archivePactStatus, //列表数据查询的url
				tableId : "tablearchivedetailselect", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			});
		});

		//选择资料
		function save(){
			var docNoStr = "";
			var docNameStr = "";
			var archiveMainNoStr = "";
			var archiveDetailNoStr = "";
			$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
				var docNoVal = this.value.split('&') [0] ;
				var docNameVal = this.value.split('&') [1] ;
				var archiveMainNoVal = this.value.split('&') [2] ;
				var archiveDetailNoVal = this.value.split('&') [3] ;
				docNoStr=docNoStr+","+docNoVal.split("=")[1];
				docNameStr=docNameStr+","+docNameVal.split("=")[1];
				archiveMainNoStr=archiveMainNoStr+","+archiveMainNoVal.split("=")[1];
				archiveDetailNoStr=archiveDetailNoStr+","+archiveDetailNoVal.split("=")[1];
			});
			docNoStr=docNoStr.substr(1);
			docNameStr=docNameStr.substr(1);
			archiveMainNoStr=archiveMainNoStr.substr(1);
			archiveDetailNoStr=archiveDetailNoStr.substr(1);
			var res = new Object();
			res.docNos = docNoStr;
			res.docNames = docNameStr;
			res.archiveMainNos = archiveMainNoStr;
			res.archiveDetailNos = archiveDetailNoStr;
			parent.dialog.get('archiveDocDialog').close(res).remove();
		}
	</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12">
			<div class="search-div">
				<div class="formRowCenter" style="margin-right: 0px;">
					<%--<input type="button" id="selectBtn" name="selectBtn" value="选择" onclick="aa();">--%>
					<dhcc:thirdButton value="选择" action="选择" onclick="save();"></dhcc:thirdButton>
				</div>
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

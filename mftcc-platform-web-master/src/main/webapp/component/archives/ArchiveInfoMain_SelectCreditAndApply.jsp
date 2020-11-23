<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	<script type="text/javascript" >
		var type = '${type}';
		$(function(){
			/*myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath + "/archiveInfoMain/findCreditAndApplyList", //查询已归档，待封档的数据
				tableId : "tablearchivepaperselect", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			});*/
		});

		//选择授信或者合同信息
		function choseInvoice(parm){
			var parmArr = parm.split("?")[1];
			var cusName = parmArr.split("&")[0].split("=")[1];
			var creditAppId = parmArr.split("&")[1].split("=")[1];
			var appId = parmArr.split("&")[2].split("=")[1];
			var archivePactStatus = parmArr.split("&")[3].split("=")[1];
			var busiNo = parmArr.split("&")[4].split("=")[1];
			var res = new Object();
			res.type = type;
			res.cusName = cusName;
			res.creditAppId = creditAppId;
			res.appId = appId;
			res.archivePactStatus = archivePactStatus;
			res.busiNo = busiNo;
			parent.dialog.get('selectCreditOrApplyDialog').close(res).remove();
		/*	$.ajax({
				url: webPath + "/archiveInfoMain/getDocConfigAjax?docSplitNo="+docSplitNo,
				dataType: "json",
				type: "POST",
				success: function (data) {
					if (data.flag == "success") {
						parent.dialog.get('archivePaperDialog').close(data.docTypeConfig).remove();
					} else {
						alert(top.getMessage("ERROR_SELECT"));
					}
				}
			});*/
		}

		function serach(){
			var cusName = $("[name=cusName]").val();
			var url = "/archiveInfoMain/getCreditAndApplyList?type="+type+"&cusName="+cusName;
			window.location.href = webPath + url;
		}
	</script>
</head>
<body class="overflowHidden">
<%--<div class="container form-container" style="overflow-y: auto">
	<div class="scroll-content" id="quanxuan">
		<div class="col-md-10 col-md-offset-1 column margin_top_20">
			<div class="list-table">
				<div class="templateList content collapse in" id="templateList" name="templateList">
					<dhcc:tableTag property="tablearchivecreditappselect" paginate="archiveInfoMainList" head="true"></dhcc:tableTag>
				</div>
			</div>
		</div>
	</div>
</div>--%>

<div class="container form-container" style="overflow-y: auto">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="search-div">
				<div class="col-xs-3 column znsearch-div pull-right">
					<div class="input-group pull-right">
						<i class="i i-fangdajing"></i>
						<input type="text" class="form-control" name="cusName" id="filter_in_input" placeholder="客户名称">
						<span class="input-group-addon" id="filter_btn_search" onclick="serach()">搜索</span>
					</div>
				</div>
			</div>
		</div>
		<div class="list-table" style="margin-left: 30px;margin-right: 30px;margin-top: 0px;">
			<div class="templateList content collapse in" id="templateList" name="templateList">
				<dhcc:tableTag property="tablearchivecreditappselect" paginate="archiveInfoMainList" head="true"></dhcc:tableTag>
			</div>
		</div>
	</div>
</div>
</body>
</html>

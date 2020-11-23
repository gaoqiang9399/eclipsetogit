<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ taglib uri="/struts-tags" prefix="s"%> 
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/initScroll.css" >
		<script type="text/javascript" src="${webPath}/component/include/initScroll.js"></script>
		<style type="text/css">
		.cover {
		    cursor: default;
		}
		</style>
		<script type="text/javascript">
		function submitBtnOne(formId,dataParam){
			var flag = false;
			var submitUrl = $("#"+formId).attr('action');
			//dataParam.demoId = "100002";
			var dataParam = JSON.stringify(dataParam); 
			jQuery.ajax({
				url:submitUrl,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				async:false,//关键
				success:function(data){
					if(data.flag=="error"){
						alert(data.msg);
					}else{
						alert(data.msg);
						flag = true;//必须写
					}
				},error:function(data){
					alert("保存失败！");
				}
			});
			return flag;//必须写
		}
		function covtab(t){
			var cell = $(t).parents(".cell");
			cell.find(".info .info_tree").hide();
			cell.find(".info .info_detail").hide();
			cell.find(".info .info_detail_all").hide();
			cell.find(".handle span").removeClass("curr");
			cell.find("."+$(t).attr("tab-id")).show();
			$(t).addClass("curr");
		}
		</script>
	</head>
<body style="overflow: hidden;overflow: auto\9;">
    <div class="layout" style="background:#f7f7f7">
		<div class="info_tree scroll_y">
			<ul id="wfTree" class="ztree">
		 	</ul>
		</div>
    </div>
    <script type='text/javascript' src='${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js'></script>
	<link rel="stylesheet" href="${webPath}/UIplug/zTree/zTreeStyle/zTreeStyle.css" />
	<link rel="stylesheet" href="${webPath}/component/app/pas/css/wkf_approval.css" />
	<script type="text/javascript" src='${webPath}/component/include/wkfApproveIdea.js'></script>
	<script type="text/javascript">
	//审批
	/* function Wkf_addDiyDom(treeId, treeNode) {
			var liObj = $("#" + treeNode.tId).empty();
			var icon = "<span id='" +treeNode.tId+"_icon' class='" +treeId+"_icon'><i></i></span>";
			var line = "<span id='" +treeNode.tId+"_line' class='" +treeId+"_line'></span>";
			var endDate = "<span id='" +treeNode.tId+"_date' class='" +treeId+"_date'>"+treeNode.end.split(" ")[0]+"</span>";
			var description = "<span id='" +treeNode.tId+"_description' class='" +treeId+"_description'>"+treeNode.description+"</span>";
			var endTime = "<span id='" +treeNode.tId+"_time' class='" +treeId+"_time'>"+treeNode.end.split(" ")[1]+"</span>";
			var optName = "<span id='" +treeNode.tId+"_optName' class='" +treeId+"_optName'>"+treeNode.optName+"</span>";
	// 		var result = "<span id='" +treeNode.tId+"_result' class='" +treeId+"_result'>"+treeNode.result+"</span>"; 
			var approveIdea = "<span id='" +treeNode.tId+"_approveIdea' class='" +treeId+"_approveIdea' >"+treeNode.approveIdea+"</span>";
			liObj.append(icon+line+endDate+description+endTime+optName+approveIdea);
			$("#" +treeNode.tId+"_line").css("height",liObj.outerHeight()-10+"px");
			
		};
	var Wkf_zTreeObj,
		Wkf_setting = {
			view: {
				selectedMulti: false,
				showIcon: false,
				addDiyDom: Wkf_addDiyDom
			}
		},
		Wkf_zTreeNodes = []; */

		$(document).ready(function(){
			//covtab($(".handle_tree")[0]);
			var ifShowReviewInfo = '<%=(String)request.getParameter("ifShowReviewInfo")%>';
			var urlApproval = "WkfApprovalOpinionActionAjax_getApprovalOpinionNoSignList.action";
			if(ifShowReviewInfo == '1'){
				urlApproval = "WkfApprovalOpinionActionAjax_getApplyApprovalOpinionList.action";
			}
			$.ajax({
				type: "post",
				data:{appNo:"<%=request.getParameter("appNo")%>"},
				dataType: 'json',
				url: urlApproval,
				contentType: "application/x-www-form-urlencoded; charset=UTF-8",
				success: function(data) {
					Wkf_zTreeNodes=data.zTreeNodes;
					Wkf_zTreeObj = $.fn.zTree.init($("#wfTree"), Wkf_setting, Wkf_zTreeNodes);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
				}
			});
		});
	</script>
</body>
</html>
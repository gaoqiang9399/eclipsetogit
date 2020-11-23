<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/webPath.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
<!-- 滚动轴 -->
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.min.css" />
<!--zTree  -->
<script type="text/javascript" src="${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/zTree/metroStyle/metroStyle.css" />
<script type="text/javascript" src="${webPath}/UIplug/artDialog/dist/dialog-plus-min.js"></script>

<script type="text/javascript">
var ajaxData = '${ajaxData}';
ajaxData = JSON.parse(ajaxData);
var showAllFlag = '${showAllFlag}';
var separator = '${separator}';
var zNodes = ajaxData;
var setting = {
	data: {
		simpleData: {
			enable: true
		}
	},
	check: {
		enable: true,
		chkStyle: "radio",
		radioType: "level"
	},
	view:{
		showLine:false,
		dblClickExpand: false,
		addDiyDom: addDiyDom
	},
	callback: {
		onClick: zTreeOnClick,
		onCheck: zTreeOnCheck
	}
};
$(document).ready(function(){
	$('body').mCustomScrollbar({
		theme:"minimal-dark"
	});
	$.fn.zTree.init($("#dataTreeNmdWay"), setting, zNodes);
	var zTreeObj = $.fn.zTree.getZTreeObj("dataTreeNmdWay");
	if(zTreeObj!=null){
		$('body').mCustomScrollbar("update");
	}
});

//去掉一级节点的radio
function addDiyDom(treeId, treeNode){
	if(treeNode.level==0 || !treeNode.isParent){
		$("#" + treeNode.tId + "_check").remove();
	}

}

function zTreeOnCheck(event, treeId, treeNode){
	var res = new Object();
	res.wayNo = treeNode.id;
	res.wayName = treeNode.name;
	res.industryClass = treeNode.industryClass;
	res.wayMaxClass=treeNode.id.substr(0,1);
	parent.dialog.get("nmdWayDialog").close(res).remove();
}

function zTreeOnClick(event, treeId, treeNode) {
	if(treeNode.isParent){
		var zTreeObj = $.fn.zTree.getZTreeObj("dataTreeNmdWay");
		zTreeObj.expandNode(treeNode, null, false);
	} else {
        var nodes = treeNode.getPath();
        var wayClassDetail='';
        var nodeLength = nodes.length;
        if(showAllFlag == '0'){
            wayClassDetail=nodes[nodeLength-1].name;
        }else{
            for(var i = 0;i<nodes.length;i++){
                if(i<nodeLength-1){
                    wayClassDetail+=nodes[i].name+"-";
                }else{
                    wayClassDetail+=nodes[i].name;
                }
            }
        }
		var res = new Object();
		res.wayNo = treeNode.id;
        res.wayName = treeNode.name;
		res.industryClass = treeNode.industryClass;
        res.wayClassDetail = wayClassDetail;
		res.wayMaxClass=treeNode.id.substr(0,1);
		parent.dialog.get("nmdWayDialog").close(res).remove();
	}
};
</script>
<style type="text/css">
	#dataTreeNmdWay.ztree li span.button{
		opacity: 0.5;
	}
	#dataTreeNmdWay {
		height: auto;
		overflow: auto;
	}
	.ztree li span.button.chk{
		margin-top:0px;
	}
</style>
</head>
<body>
<ul id="dataTreeNmdWay" class="ztree"></ul>
</body>
</html>
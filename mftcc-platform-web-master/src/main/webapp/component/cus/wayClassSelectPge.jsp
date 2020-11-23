<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
var ajaxData = ${dataMap.ajaxData};
var zNodes = ajaxData;
var setting = {
	data: {
		simpleData: {
			enable: true
		}
	},
	view:{
		showLine:false,
		dblClickExpand : false,
		addDiyDom: addDiyDom
	},
	callback: {
		beforeExpand: beforeExpand,
		onExpand: zTreeOnExpand,
				onClick: zTreeOnClick,
				onDblClick: zTreeOnDblClick
	}
};
$(document).ready(function(){
	$('body').mCustomScrollbar({
		theme:"minimal-dark"
	});
	$.fn.zTree.init($("#dataTreeWayclass"), setting, zNodes);
	var zTreeObj = $.fn.zTree.getZTreeObj("dataTreeWayclass");
	if(zTreeObj!=null){
		$('body').mCustomScrollbar("update");
	}
});
function addDiyDom(treeId, treeNode) {
	//var spanObj = $("#" + treeNode.tId + "_switch").hide();
};
var curExpandNode = null;
function beforeExpand(treeId, treeNode) {
	var pNode = curExpandNode ? curExpandNode.getParentNode():null;
	var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
	var zTree = $.fn.zTree.getZTreeObj("dataTreeWayclass");
	for(var i=0, l=!treeNodeP ? 0:treeNodeP.children.length; i<l; i++ ) {
		if (treeNode !== treeNodeP.children[i]) {
			zTree.expandNode(treeNodeP.children[i], false);
		}
	}
	while (pNode) {
		if (pNode === treeNode) {
			break;
		}
		pNode = pNode.getParentNode();
	}
	if (!pNode) {
		singlePath(treeNode);
	}

}
function singlePath(newNode) {
	if (newNode === curExpandNode) return;

    var zTree = $.fn.zTree.getZTreeObj("dataTreeWayclass"),
            rootNodes, tmpRoot, tmpTId, i, j, n;

    if (!curExpandNode) {
        tmpRoot = newNode;
        while (tmpRoot) {
            tmpTId = tmpRoot.tId;
            tmpRoot = tmpRoot.getParentNode();
        }
        rootNodes = zTree.getNodes();
        for (i=0, j=rootNodes.length; i<j; i++) {
            n = rootNodes[i];
            if (n.tId != tmpTId) {
                zTree.expandNode(n, false);
            }
        }
    } else if (curExpandNode && curExpandNode.open) {
		if (newNode.parentTId === curExpandNode.parentTId) {
			zTree.expandNode(curExpandNode, false);
		} else {
			var newParents = [];
			while (newNode) {
				newNode = newNode.getParentNode();
				if (newNode === curExpandNode) {
					newParents = null;
					break;
				} else if (newNode) {
					newParents.push(newNode);
				}
			}
			if (newParents!=null) {
				var oldNode = curExpandNode;
				var oldParents = [];
				while (oldNode) {
					oldNode = oldNode.getParentNode();
					if (oldNode) {
						oldParents.push(oldNode);
					}
				}
				if (newParents.length>0) {
					zTree.expandNode(oldParents[Math.abs(oldParents.length-newParents.length)-1], false);
				} else {
					zTree.expandNode(oldParents[oldParents.length-1], false);
				}
			}
		}
	}
	curExpandNode = newNode;
}
function zTreeOnExpand(event, treeId, treeNode) {
	curExpandNode = treeNode;
// 	$('body').mCustomScrollbar("update");
};
function zTreeOnClick(event, treeId, treeNode) {	
	if(treeNode.isParent){
		var zTreeObj = $.fn.zTree.getZTreeObj("dataTreeWayclass");
		zTreeObj.expandNode(treeNode, null, false);
	} else {
		var nodes = treeNode.getPath();
		var disName='';
		for(var i = 0;i<nodes.length;i++){
			if(i==0){
				disName+=nodes[i].name;
			}else{
				disName+=nodes[i].name;
			}
		}
		var res = new Object();
		res.disNo = treeNode.id;
		res.disName = treeNode.name;
		parent.dialog.get("wayClassDialog").close(res).remove();
		
		
	}
};
function zTreeOnDblClick(event, treeId, treeNode) {
	var nodes = treeNode.getPath();
	if(parent.AI_options=="Area"){
		parent.returnAIVal(treeNode.id,nodes);
	}else{
		if(treeNode.level==2){
			parent.returnAIVal(treeNode.id,nodes);
		}
	}
};
</script>
<style type="text/css">
	#dataTreeWayclass.ztree li span.button{
		opacity: 0.5;
	}
	#dataTreeWayclass {
		height: 400px;
		overflow: auto;
	}
</style>
</head>
<body>
<ul id="dataTreeWayclass" class="ztree"></ul>
</body>
</html>
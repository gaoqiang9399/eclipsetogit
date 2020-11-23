<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/webPath.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
<!--zTree  -->
<script type="text/javascript" src="${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/zTree/metroStyle/metroStyle.css" />
<script type="text/javascript" src="${webPath}/UIplug/artDialog/dist/dialog-plus-min.js"></script>

<script type="text/javascript">
var webPath = '${webPath}';
var showAllFlag = '${showAllFlag}';
var isStepLoading = '${isStepLoading}';
var ajaxData = '${ajaxData}';
var separator = '${separator}';
ajaxData = JSON.parse(ajaxData);
var zNodes = ajaxData;
setIsParent(zNodes);
function setIsParent(zNodes){
	if(isStepLoading == 'true'){
	for(var i = 0 ;i<zNodes.length;i++){
		var zNode = zNodes[i];
		if(zNode.ifLeaf == '1'){
			zNode.isParent = false;
		}else{
			zNode.isParent = true;
		}
	  }
	}
}
function filter(treeId, parentNode, data) {
	var childNodes = data.childNodeList;
	if(isStepLoading == 'true'){
    	if (!childNodes) return null;
   	 	setIsParent(childNodes);
    	return childNodes;
	}
}
var setting = {
	async:{  
     	autoParam:["areaNo"],    
        enable:true,   
        type: 'post',  
        dataType: 'json',
        url: webPath+"/nmdArea/getChildAjax",//异步加载时的请求地址
        dataFilter: filter
     },
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
		onDblClick: zTreeOnDblClick,
		onAsyncSuccess:zTreeOnAsyncSuccess,//树加载成功时的回调函数
	}
};
function zTreeOnAsyncSuccess(){
	setIsParent(zNodes);
}
$(document).ready(function(){
	$.fn.zTree.init($("#dataTree"), setting, zNodes);
	var zTreeObj = $.fn.zTree.getZTreeObj("dataTree");
});
function addDiyDom(treeId, treeNode) {
	//var spanObj = $("#" + treeNode.tId + "_switch").hide();
};
var curExpandNode = null;
function beforeExpand(treeId, treeNode) {
	var pNode = curExpandNode ? curExpandNode.getParentNode():null;
	var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
	var zTree = $.fn.zTree.getZTreeObj("dataTree");
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

    var zTree = $.fn.zTree.getZTreeObj("dataTree"),
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
};
function zTreeOnClick(event, treeId, treeNode) {
	if(treeNode.isParent){
		var zTreeObj = $.fn.zTree.getZTreeObj("dataTree");
		zTreeObj.expandNode(treeNode, null, false);
	} else {
		var nodes = treeNode.getPath();
		var disName='';
		var nodeLength = nodes.length;
		if(showAllFlag == '0'){
			disName=nodes[nodeLength-1].name;
		}else{
			for(var i = 0;i<nodes.length;i++){
				if(i<nodeLength-1){
					disName+=nodes[i].name+separator;
				}else{
					disName+=nodes[i].name;
				}
			}
		}
		var res = new Object();
		if(isStepLoading=="true"){
			res.disNo = treeNode.areaNo;
		}else{
			res.disNo = treeNode.id;
		}
		res.disName = disName;
        res.postalCode = treeNode.postalCode;
		parent.dialog.get("areaDialog").close(res).remove();
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
	#dataTree.ztree li span.button{
		opacity: 0.5;
	}
	#dataTree {
		height: 400px;
		overflow: auto;
	}
</style>
</head>
<body>
<ul id="dataTree" class="ztree"></ul>
</body>
</html>
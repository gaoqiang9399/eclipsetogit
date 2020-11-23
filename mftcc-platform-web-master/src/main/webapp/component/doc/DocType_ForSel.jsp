<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/component/include/webPath.jsp" %>
<%
String contextPath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+webPath+"/";
%>
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
ajaxData = eval(ajaxData);
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
		//addDiyDom: addDiyDom
	},
	check: {
		enable: true,
		chkStyle: "checkbox",
		chkboxType: { "Y": "ps", "N": "ps" }
	},
	callback: {
		/* beforeExpand: beforeExpand,
		onExpand: zTreeOnExpand,
		onClick: zTreeOnClick */
	}
}; 
var zTreeInitNode;
$(document).ready(function(){
	zTreeInitNode = $.fn.zTree.init($("#dataTree"), setting, zNodes);
// 	$('#dataTree').mCustomScrollbar({
// 		theme:"minimal-dark"
// 	});
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
// 	$('body').mCustomScrollbar("update");
};
function zTreeOnClick() {
	var selectNodes = zTreeInitNode.getCheckedNodes(true);
	if(selectNodes.length == 0){
		parent.dialog.get("docTypeDialog").close().remove();
		return;
		
	}			
	var docNo="";
	var docName = "";
	var docType = "";
	var formNo = "";
	$.each(selectNodes,function(i,treeNode){
		if(treeNode.isParent){
			
		}else{
			if( i == selectNodes.length-1){
				docNo = docNo + treeNode.id;
				docName = docName + treeNode.name;
				docType = docType + treeNode.docType;
				formNo = formNo + treeNode.formNo;
			}else{
				docNo = docNo + treeNode.id+"@";
				docName = docName + treeNode.name+"@";
				docType = docType + treeNode.docType+"@";
				formNo = formNo + treeNode.formNo+"@";
			}
		}
	});
	
	var doc = new Object();
	doc.docNo = docNo;
	doc.docName = docName;
	doc.docType = docType;
	doc.formNo = formNo;
	parent.dialog.get("docTypeDialog").close(doc).remove();
};
</script>
<style type="text/css">
	#dataTree.ztree li span.button{
		opacity: 0.5;
		margin-top: 0px;
	}
	#dataTree {
		height: 355px;
		overflow: auto;
	}
	
</style>
</head>
<body>
<div>
	<ul id="dataTree" class="ztree"></ul>
</div>
<div style="text-align: center;margin-top: 5px;">
<button type="button" style="border: none;background: #32B5CB;color: white;height: 30px;width: 60px;cursor: pointer;" onclick="zTreeOnClick();"onMouseOver="this.style.background='#009DB7'"onMouseOut="this.style.background='#32B5CB'">确认 </button>		
</div>
</body>
</html>
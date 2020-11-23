<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
var zNodes = ajaxData;
var setting = {
	data: {
		simpleData: {
			enable: true
		}
	},
	check: {
		enable: true,
		chkStyle: "checkbox",
		radioType: "level"
	},
	view:{
		showLine:false,
		dblClickExpand: false,
		addDiyDom: addDiyDom
	},
	callback: {
	}
};
$(document).ready(function(){
	$.fn.zTree.init($("#dataTree"), setting, zNodes);
	var zTreeObj = $.fn.zTree.getZTreeObj("dataTree");
	if(zTreeObj!=null){
		$('body').mCustomScrollbar("update");
	}
});

//去掉一级节点的radio
function addDiyDom(treeId, treeNode){
    if(treeNode.isParent){
        $("#" + treeNode.tId + "_check").remove();
    }
}

function save() {
    var treeObj = $.fn.zTree.getZTreeObj("dataTree");
    var ids="";
    var names="";
    var descs="";
    var nodes = treeObj.getCheckedNodes(true);
    for(var i=0;i<nodes.length;i++){
        if(nodes[i].pId != null && nodes[i].pId != "0"){
            ids += nodes[i].id+",";
            names += nodes[i].name+",";
            descs += nodes[i].desc+",";
        }
    }
    var res = new Object();
    res.ids = ids.substr(0, ids.length -1);
    res.names = names.substr(0, names.length -1);
    res.descs = descs.substr(0, descs.length -1);
    parent.dialog.get("archiveInfoDialog").close(res).remove();
}

</script>
<style type="text/css">
	#dataTree.ztree li span.button{
		opacity: 0.5;
	}
	#dataTree {
		height: auto;
		overflow: auto;
	}
	.ztree li span.button.chk{
		margin-top:0px;
	}
</style>
</head>
<body>
<div class="" style="overflow-y: auto;height: 100%">
    <div style="overflow-y: auto;height: 90%">
        <ul id="dataTree" class="ztree">

        </ul>
    </div>
    <div class="formRowCenter" style="margin-bottom: 0%;">
        <%--<input type="button" id="selectBtn" name="selectBtn" value="选择" onclick="aa();">--%>
       <dhcc:thirdButton value="选择" action="选择" onclick="save();"></dhcc:thirdButton>
    </div>
</div>
</body>
</html>
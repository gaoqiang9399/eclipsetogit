var setting = {
						data: {
							simpleData: {
								enable: true
							}
						},
						edit: {
							enable: true,
							editNameSelectAll: true,
							showRemoveBtn: showRemoveBtn,
							showRenameBtn: showRenameBtn
						},
						view:{
							addHoverDom: addHoverWkfTypeDom,
							removeHoverDom: removeHoverWkfTypeDom,
							selectedMulti: false,
							showIcon: false,
							showLine: false
						},
						callback:{
							onClick:wkfTypeOnClick,
							beforeEditName: beforeEditName,
							beforeRemove: beforeRemove,
						}
					};
$(document).ready(function(){
	$.ajax({
		type : 'post',
		url :webPath+"/wkfType/getAllAjax" ,
		dataType : 'json',
		async: false,
		error: function(xmlhq,ts,err){console.log(xmlhq+"||"+ts+"||"+err);},
		success : function(data) {
			$.fn.zTree.init($("#wkfTypeTree"), setting, data.nodes);
		}
	});
	
	$("#wkf-type-add").bind("click",function(){
		$(".wkf-body-form").show();
	});
	$("#vpCancle").bind("click",function(){
		
	});
	$("#vpAdd").bind("click",function(){
		var vpNo = $("#vpNo").val();
		var vpName = $("#vpName").val();
		var parm = {
				id:vpNo,
				name:vpName,
				wkfVpNo:vpNo,
				wkfVpName:vpName
		};
		$.ajax({
			type : 'post',
			url :webPath+"/wkfType/addAjax" ,
			dataType : 'json',
			data:parm,
			async: false,
			error: function(xmlhq,ts,err){console.log(xmlhq+"||"+ts+"||"+err);},
			success : function(data) {
				if(data.flag=="success"){
					var treeObj = $.fn.zTree.getZTreeObj("wkfTypeTree");
					var newNode = parm;
					newNode = treeObj.addNodes(null, newNode);
					$("#vpNo").val("");
					$("#vpName").val("");
					$(".wkf-body-form").hide();
					alert(data.msg);
				}else{
					alert(data.msg);
				}
			}
		});
		
	});
});
var sub_setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		edit: {
			enable: true,
			editNameSelectAll: true,
			showRemoveBtn: showSubRemoveBtn,
			showRenameBtn: showSubRenameBtn
		},
		view:{
			showIcon: false,
			showLine: false,
			addDiyDom: wkfVpTreeDiyDom
		},
		callback:{
		}
	};
function addHoverWkfTypeDom(treeId, treeNode){
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='add node' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
		return false;
	});
};
function removeHoverWkfTypeDom(treeId, treeNode){
	$("#addBtn_"+treeNode.tId).unbind().remove();
};

function beforeEditName(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("wkfVpTree");
	zTree.selectNode(treeNode);
	return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
}
function beforeRemove(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("wkfVpTree");
	zTree.selectNode(treeNode);
	var flag = false;
	if(confirm("确认删除 节点 -- " + treeNode.name + " 吗？")){
		$.ajax({
			type : 'post',
			url :webPath+"/wkfType/deleteAjax" ,
			dataType : 'json',
			data:{wkfVpNo:treeNode.wkfVpNo},
			async: false,
			error: function(xmlhq,ts,err){console.log(xmlhq+"||"+ts+"||"+err);},
			success : function(data) {
				if(data.flag=="success"){
					alert(data.msg);
					flag=true;
				}else{
					alert(data.msg);
				}
			}
		});
	}
	return flag;
}

function showRemoveBtn(treeId, treeNode) {
	return true;
}
function showRenameBtn(treeId, treeNode) {
	return true;
}
function wkfTypeOnClick(event, treeId, treeNode) {
	$.ajax({
		type : 'post',
		url :webPath+"/wkfVp/getByVpNoAjax" ,
		dataType : 'json',
		async: false,
		data:treeNode,
		error: function(xmlhq,ts,err){console.log(xmlhq+"||"+ts+"||"+err);},
		success : function(data) {
			$.fn.zTree.destroy("wkfVpTree");
			$.fn.zTree.init($("#wkfVpTree"), sub_setting, data.nodes);
		}
	});
};
function wkfVpTreeDiyDom(treeId, treeNode){
	$("#"+treeNode.tId+"_switch").remove();
	$("#"+treeNode.tId+"_ico").remove();
	var $wkfA = $("#"+treeNode.tId+"_a");
	$wkfA.addClass("wkf_vp_a");
	var $menuName  = $("#"+treeNode.tId+"_span");
	$menuName.addClass("wkfVpMenuName").before("<span id='"+treeNode.tId+"_menuNo' class='wkfVpMenuNo'>"+treeNode.wkfVpMenuNo+"</span>");
	
	$wkfA.append("<span id='"+treeNode.tId+"_url' title='"+treeNode.wkfVpMenuUrl+"'  class='wkfVpMenuUrl'>"+treeNode.wkfVpMenuUrl+"</span>")
	.append("<span id='"+treeNode.tId+"_jsM'  class='jsMethod'>"+treeNode.jsMethod+"</span>")
	.append("<span id='"+treeNode.tId+"_type' class='urlType'>"+treeNode.urlType+"</span>")
	.append("<span id='"+treeNode.tId+"_sts'  class='urlSts'>"+treeNode.urlSts+"</span>")
	.append("<span id='"+treeNode.tId+"_remark'  class='wkfVpRemark'>"+treeNode.wkfVpRemark+"</span>")
	.append("<span id='"+treeNode.tId+"_ctrl' class='wkfCtrl'></span>");
	$wkfA.after("<div style='display:none;' id='"+treeNode.tId+"_div'></div>");
	var $wkfDiv = $("#"+treeNode.tId+"_div");
	$wkfDiv.append("<input type='text' id='"+treeNode.tId+"_menuNo_str'>")
	.append("<input type='text' id='"+treeNode.tId+"_url_str'>")
	.append("<input type='text' id='"+treeNode.tId+"_jsM_str'>")
	.append("<input type='text' id='"+treeNode.tId+"_sts_str'>")
	.append("<input type='text' id='"+treeNode.tId+"_remark_str'>")
	.append("<input type='text' id='"+treeNode.tId+"_ctrl_str'>");
}
function showSubRemoveBtn(treeId, treeNode) {
	return true;
}
function showSubRenameBtn(treeId, treeNode) {
	return true;
}









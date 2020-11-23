var roleNo = GetQueryString("roleNo");
$(document).ready(function(){
	jQuery.ajax({
		type : "POST",
		cache : false,
		dataType:"json",
		url : webPath+"/pmsBiz/getAllBySts",
		data:{
			bizState:"1",
			roleNo:roleNo
		},
		success : function(data) {
			if(!data||data.length==0){
				data = [];
			}else{
				var zTree = $.fn.zTree.init($("#pmsBizTree"), setting_biz, data);
				selectCheckNode(data);
				zTree.expandAll(false);
				$("#pmsBizTree_1_switch").click();
			}
		}
	});
	$("#pmsBizSave").bind("click",changeBizState);
});
var setting_biz = {
	view: {
		selectedMulti: false,
		addDiyDom: addViewDom,
		showLine: false
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		onCheck: onCheck
	},
	check: {
		enable: true
	}
};
//初始化数据
var on_css={
		'msTransform':'translateX(-8px)',
		'-moz-transform':'translateX(-8px)',
		'-webkit-transform':'translateX(-8px)',
		'-0-transform':'translateX(-8px)'
};
var off_css={
		'msTransform':'translateX(-36px)',
		'-moz-transform':'translateX(-36px)',
		'-webkit-transform':'translateX(-36px)',
		'-0-transform':'translateX(-36px)'
};
//自定义 view
function addViewDom(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
	var aObj = $("#" + treeNode.tId + "_a");
	var root;
	if(treeNode.getParentNode()){
		root = treeNode.getParentNode().id;
	}else{
		root = "root";
	}
	
	var editStr = "<input value='"+treeNode.id+"' type='checkbox' class='checkbox_"+treeId+" checkboxBtn' id='checkbox_" +treeNode.id+ "' name='checkbox_"+root+"' onfocus='this.blur();' /><span class = 'tree_line'></span>";
	aObj.after(editStr);
	
	var btn = $("#checkbox_"+treeNode.id);
	if (btn) btn.rcSwitcher({
		width: 44,
		height: 16,
		theme: 'lease',
		blobOffset: 1,
	//	autoStick: true,
		}).on( 'turnon.rcSwitcher', function( e, data ){
			var arr = [];
			data.$input.parent().find(".stoggler").addClass("on").removeClass("off").css(on_css);
			data.$input.parent().find("ul").find(".stoggler").addClass("on").removeClass("off").css(on_css);
			data.$input.parent().find("ul").find("input[type=checkbox]").each(function(){
				$(this)[0].checked=true;
				var node = zTree.getNodeByTId($(this).closest("li").attr("id"));
				arr.push(node.id);
			});
			data.$input[0].checked=true;
			node = zTree.getNodeByTId(data.$input.closest("li").attr("id"));
			arr.push(node.id);
			zTree.checkNode(node, true, true);
			getCheckLvlTrue(data.$input.parent().parent(),arr);
//			changeBizState(arr,"1");
		} ).on('turnoff.rcSwitcher', function( e, data ){
			var arr = [];
			data.$input.parent().find(".stoggler").addClass("off").removeClass("on").css(off_css);
			data.$input.parent().find("ul").find(".stoggler").addClass("off").removeClass("on").css(off_css);
			data.$input.parent().find("ul").find("input[type=checkbox]").each(function(){
				$(this)[0].checked=false;
				var node = zTree.getNodeByTId($(this).closest("li").attr("id"));
				arr.push(node.id);
			});
			data.$input[0].checked=false;
			var node = zTree.getNodeByTId(data.$input.closest("li").attr("id"));
			arr.push(node.id);
			zTree.checkNode(node, false, true);
			getCheckLvlFalse(data.$input.parent().parent(),arr);
//			changeBizState(arr,"0");
		}
	);
}

function onCheck(event, treeId, treeNode){
	var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
	if(treeNode.checked){
		rcSwitcherOn($("#"+treeNode.tId+"_check"),zTree);
	}else{
		rcSwitcherOff($("#"+treeNode.tId+"_check"),zTree);
	}
}
function rcSwitcherOn(data,zTree){
	var arr = [];
	data.parent().find(".stoggler").addClass("on").removeClass("off").css(on_css);
	data.parent().find("ul").find(".stoggler").addClass("on").removeClass("off").css(on_css);
	data.parent().find("ul").find("input[type=checkbox]").each(function(){
		$(this)[0].checked=true;
		var node = zTree.getNodeByTId($(this).closest("li").attr("id"));
		arr.push(node.id);
	});
	data[0].checked=true;
	node = zTree.getNodeByTId(data.closest("li").attr("id"));
	arr.push(node.id);
	getCheckLvlTrue(data.parent().parent(),arr);
}
function rcSwitcherOff(data,zTree){
	var arr = [];
	data.parent().find(".stoggler").addClass("off").removeClass("on").css(off_css);
	data.parent().find("ul").find(".stoggler").addClass("off").removeClass("on").css(off_css);
	data.parent().find("ul").find("input[type=checkbox]").each(function(){
		$(this)[0].checked=false;
		var node = zTree.getNodeByTId($(this).closest("li").attr("id"));
		arr.push(node.id);
	});
	data[0].checked=false;
	var node = zTree.getNodeByTId(data.closest("li").attr("id"));
	arr.push(node.id);
	getCheckLvlFalse(data.parent().parent(),arr);
}
function getCheckLvlTrue(ul,arr){
	if(ul.hasClass("ztree")){
		return arr;
	}
	var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
	if(ischecked(ul)){
		var node = zTree.getNodeByTId(ul.parent().attr("id"));
		ul.parent().children(".swraper").children(".stoggler").addClass("on").removeClass("off").css(on_css);
		if(arr.indexOf(node.id)==-1){
			arr.push(node.id);
		}
		getCheckLvlTrue(ul.parent().parent(),arr);
	}
}
function getCheckLvlFalse(ul,arr){
	if(ul.hasClass("ztree")){
		return arr;
	}
	var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
	if(!ischecked(ul)){
		var node = zTree.getNodeByTId(ul.parent().attr("id"));
		ul.parent().children(".swraper").children(".stoggler").addClass("off").removeClass("on").css(off_css);
		if(arr.indexOf(node.id)==-1){
			arr.push(node.id);
		}
		getCheckLvlFalse(ul.parent().parent(),arr);
	}
}
function ischecked(ul){
	var flag = false;
	ul.find(".stoggler").each(function(){
		if($(this).hasClass("on")){
			flag = true;
			return flag;
		}
	});
	return flag;
}
function changeBizState(){
	var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
	var arr = [];
	$("#pmsBizTree").find(".stoggler").each(function(){
		if($(this).hasClass("on")){
			var node = zTree.getNodeByTId($(this).closest("li").attr("id"));
			arr.push(node.id);
		}
	});
	jQuery.ajax({
		type : "POST",
		cache : false,
		dataType:"json",
		url : path+"/pmsBizRoleRel/save",
		data : {
			pmsSerno : JSON.stringify(arr),
			roleNo : roleNo
		},
		success : function(data) {
			alert(data.msg,1);
		}
	});
}

function selectCheckNode(allData){
	jQuery.ajax({
		type : "POST",
		cache : false,
		dataType:"json",
		url : webPath+"/pmsBizRoleRel/getAllByRoleNo",
		data:{
			roleNo : roleNo
		},
		success : function(data) {
			entrNoStr = "";
			$(".checkbox_treeEntr").each(function(){
				if($(this)[0].checked){
					entrNoStr += $(this).attr("value") + ",";
				}
			});
			if(entrNoStr==""){
				$("#pmsBizTree").hide();
			}else{
				$("#pmsBizTree").show();
			}
			var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
            var i,nodes,node;
			for(i in allData){
				nodes = zTree.getNodesByParam("id", allData[i].id, null);
				if(nodes!=null&&nodes!=""){
					node=nodes[0];
					//如果节点类型为目录且没有子节点，删除该节点和其父节点
					if(node.bizType=="1"&&node.check_Child_State==-1){
						zTree.removeNode(node);
					}
				}
			}
			if(data.length>0){
				for(i in data){
					nodes = zTree.getNodesByParam("id", data[i].pmsSerno, null);
					if(nodes!=null&&nodes!=""){
						node=nodes[0];
						var tId = nodes[0].tId;
						zTree.checkNode(node, true, false);
						$("#"+tId).children(".swraper").children(".stoggler").addClass("on").removeClass("off").css(on_css);
					}
				}
			}
		}
	});
}

//正则表达式获取地址栏参数
function GetQueryString(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}
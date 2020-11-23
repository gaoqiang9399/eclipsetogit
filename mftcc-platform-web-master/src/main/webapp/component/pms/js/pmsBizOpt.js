$(document).ready(function(){
	/*var zNodes =[
	 			{ id:1, pId:0, name:"父节点 1", open:true},
	 			{ id:11, pId:1, name:"叶子节点 1-1"},
	 			{ id:12, pId:1, name:"叶子节点 1-2"},
	 			{ id:13, pId:1, name:"叶子节点 1-3"},
	 			{ id:2, pId:0, name:"父节点 2", open:true},
	 			{ id:21, pId:2, name:"叶子节点 2-1"},
	 			{ id:22, pId:2, name:"叶子节点 2-2"},
	 			{ id:23, pId:2, name:"叶子节点 2-3"},
	 			{ id:3, pId:0, name:"父节点 3", open:true},
	 			{ id:31, pId:3, name:"叶子节点 3-1"},
	 			{ id:32, pId:3, name:"叶子节点 3-2"},
	 			{ id:33, pId:3, name:"叶子节点 3-3"}
	 		];*/
	jQuery.ajax({
		type : "POST",
		cache : false,
		dataType:"json",
		url : webPath+"/pmsBiz/getAll",
		success : function(data) {
			if(!data||data.length==0){
				data = [{id:"root", pId:0, name:"", open:true}];
			}
			var zTree = $.fn.zTree.init($("#pmsBizTree"), setting, data);
			// 默认展开所有
			// zTree.expandAll(true);


            //默认展示1.2级
            var nodes = zTree.getNodes();
            for (var i = 0; i < nodes.length; i++) {
                zTree.expandNode(nodes[i], true, false, true);
            }

		}
	});
});

var setting = {
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		selectedMulti: false,
		addDiyDom: addViewDom,
		showLine: false,
		removeTitle: "删除节点",
		renameTitle: "编辑节点名称"
	},
	edit: {
		enable: true,
		editNameSelectAll: true,
		showRemoveBtn: true,
		showRenameBtn: true
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeDrag: beforeDrag,
		beforeEditName:beforeEditName,
		beforeRemove: beforeRemove,
		beforeRename: beforeRename,
		onRename: onRename,
		onCheck: onCheck,
        beforeClick: beforeClick,
        onExpand: onExpand

	},
	check: {
		enable: true
	}
};


//点击2级时默认展开所有子集
function beforeClick(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
    var nodes = zTree.getNodes();
    for (var i = 0; i < nodes.length; i++) { //设置节点展开
        var nodeSonList=nodes[i].children;
        if(nodeSonList!=undefined){
        	for(var j=0;j<nodeSonList.length;j++){
        		//比对是否点击的本节点
        		if(nodeSonList[j].id==treeNode.id){
                    zTree.expandNode(nodeSonList[j], true, true, true);
                    return;
				}
			}
		}
    }
}


//点击展开2级时默认展开所有子集
function onExpand(event, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
    var nodes = zTree.getNodes();
    for (var i = 0; i < nodes.length; i++) {
        var nodeSonList=nodes[i].children;
        if(nodeSonList!=undefined){
            for(var j=0;j<nodeSonList.length;j++){
                //比对是否点击的本节点
                if(nodeSonList[j].id==treeNode.id){
                    zTree.expandNode(nodeSonList[j], true, true, true);
                    return;
                }
            }
        }
    }
}

function onCheck(event, treeId, treeNode){
    // debugger
    var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
	if(treeNode.checked){
		rcSwitcherOn($("#"+treeNode.tId+"_check"),zTree);
	}else{
		rcSwitcherOff($("#"+treeNode.tId+"_check"),zTree);
	}
}
function beforeDrag(treeId, treeNodes) {
	return false;
}
function beforeRemove(treeId, treeNode) {
	clearOpt();
	var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
	zTree.selectNode(treeNode);
	var arr = [];
	$("#"+treeNode.tId).find("li").each(function(){
		var node = zTree.getNodeByTId($(this).attr("id"));
		arr.push(node.id);
	});
	arr.push(treeNode.id);
	if(confirm("确认删除 [" + treeNode.name + "] 吗？")){
		jQuery.ajax({
			type : "POST",
			cache : false,
			dataType:"json",
			url : webPath+"/pmsBiz/deleteById",
			data : {
				jsonData : JSON.stringify(arr)
			},
			success : function(data) {
				alert(data.msg);
			}
		});
		return true;
	}else{
		return false;
	}
}
function beforeRename(treeId, treeNode, newName, isCancel) {
	return false;
}
function beforeEditName(treeId, treeNode){
	clearOpt();
	var aObj = $("#" + treeNode.tId + "_a");
	aObj.find(".add").remove();
	aObj.find(".addPms").remove();
	aObj.find(".edit").remove();
	aObj.find(".remove").remove();
	var sObj = $("#" + treeNode.tId + "_span");
	sObj.hide();
	aObj.find(".rename").val(sObj.html()).show();
	aObj.find(".span_pms_id").hide();
	aObj.find(".input_pms_id").val(aObj.find(".span_pms_id").html()).show();
	aObj.find(".fa").show();
	
	return false;
}
function clearOpt(){
    // debugger
	var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
	$("#pmsBizTree").find(".fa").hide();
	$("#pmsBizTree").find(".new_node").each(function(){
		var node = zTree.getNodeByTId($(this).attr("id"));
		zTree.removeNode(node);
	});
	$("#pmsBizTree").find(".rename").each(function(){
		$(this).prev().show();
		$(this).hide();
	});
	$("#pmsBizTree").find(".input_pms_id").each(function(){
		$(this).parent().find(".span_pms_id").show();
		$(this).hide();
	});
}
function beforeAdd(newNode){
    // debugger
	clearOpt();
	var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
	zTree.selectNode(newNode[0]);
	var nlObj = $("#" + newNode[0].tId);
	nlObj.addClass("new_node");
	var nsObj = $("#" + newNode[0].tId + "_span");
	var naObj = $("#" + newNode[0].tId + "_a");
	naObj.find(".node_name").hide();
	naObj.find(".span_pms_id").hide();
	naObj.find(".rename").show();
	naObj.find(".input_pms_id").show();
	naObj.find(".fa").show();
	naObj.find(".add").remove();
	naObj.find(".addPms").remove();
	naObj.find(".edit").remove();
	naObj.find(".remove").remove();
}
function getId(){
	return new Date().getTime(); 
}
function addHoverDom(treeId, treeNode) {
	var aObj = $("#" + treeNode.tId + "_a");
	if(!aObj.find(".rename").is(":hidden")){
		aObj.find(".edit").remove();
		aObj.find(".remove").remove();
		return;
	}
	if(treeNode.bizType=="2"){
		return;
	}
	
	aObj = $("#" + treeNode.tId + "_a");
	if (treeNode.editNameFlag || $("#addPmsBtn_"+treeNode.tId).length>0) return;
	var addPmsStr = "<span class='button addPms' id='addPmsBtn_" + treeNode.tId
	+ "' title='添加控制' onfocus='this.blur();'></span>";
	aObj.append(addPmsStr);
	var btn = $("#addPmsBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
	    // debugger
		var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
		var newNode = zTree.addNodes(treeNode, {id:getId(), pId:treeNode.id, name:"",bizType:"2"});
		beforeAdd(newNode);
		return false;
	});
	
	if(treeNode.children&&treeNode.children.length>0&&treeNode.children[0].bizType=="2"){
		return;
	}
	
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='添加节点' onfocus='this.blur();'></span>";
	if(aObj.find(".span_pms_id").length>0){
		aObj.find(".span_pms_id").after(addStr);
	}else{
		sObj.after(addStr);
	}
	
	btn = $("#addBtn_"+treeNode.tId);
	if (btn) btn.bind("click", function(){
	    // debugger
		var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
		var newNode = zTree.addNodes(treeNode, {id:getId(), pId:treeNode.id, name:"",bizType:"1"});
		beforeAdd(newNode);
		return false;
	});
};
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
	$("#addPmsBtn_"+treeNode.tId).unbind().remove();
};
function onRename(event, treeId, treeNode, isCancel){
	if(treeNode.bizType=="2"){
		var spanObj = $("#" + treeNode.tId + "_span");
		var pmsStr = "<span id='span_" +treeNode.id+ "' class='span_pms_id'>"+$("#input_"+treeNode.id).val()+"</span>";
		$("#input_"+treeNode.id).remove();
		spanObj.after(pmsStr);
	}
}
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
	var checked4 = "";
	if(treeNode.bizState=="1"){
		checked4 = "checked";
		zTree.checkNode(treeNode, true, true);
	}else{
		zTree.checkNode(treeNode, false, true);
	}
	var root;
	if(treeNode.getParentNode()){
		root = treeNode.getParentNode().id;
	}else{
		root = "root";
	}
	
	var editStr = "<input value='"+treeNode.id+"' type='checkbox' class='checkbox_"+treeId+" checkboxBtn' id='checkbox_" +treeNode.id+ "' name='checkbox_"+root+"' onfocus='this.blur();'  "+checked4+"/><span class = 'tree_line'></span>";
	aObj.after(editStr);
	aObj.append('<input style="display:none;" type="text" class="rename" value="" placeholder="请输入名称">');
	aObj.find(".rename").click(function(){
	    // debugger
		$(this).focus();
	});
	if(treeNode.bizType=="2"){
		aObj.append('<span class="span_pms_id">'+treeNode.pmsBizNo+'</span>');
		var pmsStr = "<input  style='display:none;' id='input_" +treeNode.id+ "' type='text' class='input_pms_id' placeholder='请输入控制权限ID'/>";
		aObj.append(pmsStr);
		aObj.find(".input_pms_id").click(function(){
		    // debugger
			$(this).focus();
		});
	}
	var cancel = $('<span style="display:none;" class="fa fa-close" id="'+treeNode.tId+'_cancel" title="取消"></span>');
	var submit = $('<span style="display:none;" class="fa fa-check" id="'+treeNode.tId+'_submit" title="确定"></span>');
	aObj.append(cancel);
	aObj.append(submit);
	
	cancel.click(function(){
	    // debugger
		if($(this).closest("li").hasClass("new_node")){
			var node = zTree.getNodeByTId($(this).closest("li").attr("id"));
			zTree.removeNode(node);
		}else{
			$(this).closest("a").find(".node_name").show();
			$(this).closest("a").find(".span_pms_id").show();
			$(this).closest("a").find(".rename").hide();
			$(this).closest("a").find(".input_pms_id").hide();
			$(this).closest("a").find(".fa").hide();
		}
		return false;
	});
	submit.click(function(){
	    // debugger
		var dom = $(this),flag;
		var node = zTree.getNodeByTId(dom.closest("li").attr("id"));
		if(dom.closest("li").hasClass("new_node")||node.id=="root"){
			flag = "insert";
		}else{
			flag = "update";
		}
		var bizType = node.bizType;
		var pmsSerno = node.id;
		var parentPmsSerno;
		if(pmsSerno=="root"){
			pmsSerno = getId();
			parentPmsSerno = "0";
		}else{
			parentPmsSerno = node.getParentNode().id;
		}
		var pmsBizName = dom.closest("a").find(".rename").val();
		var pmsBizNo = dom.closest("a").find(".input_pms_id").val();
		var bizState = 0;
		if(dom.closest("li").find("input[type=checkbox]").is(':checked')){
			bizState = 1;
		}
		var data = {
				flag:flag,
				pmsBizNo:pmsBizNo,
				pmsBizName:pmsBizName,
				bizType:bizType,
				pmsSerno:pmsSerno,
				parentPmsSerno:parentPmsSerno,
				bizState:bizState
		};
		jQuery.ajax({
			type : "POST",
			cache : false,
			dataType:"json",
			url : webPath+"/pmsBiz/save",
			data : {
				jsonData : JSON.stringify(data)
			},
			success : function(data) {
				if(data.flag){
					dom.closest("li").removeClass("new_node");
					dom.closest("a").find(".node_name").html(pmsBizName);
					dom.closest("a").find(".span_pms_id").html(pmsBizNo);
					node.name = pmsBizName;
					clearOpt();
				}else{
					alert(data.msg);
				}
			}
		});
	});
	
	var btn = $("#checkbox_"+treeNode.id);
	if (btn) btn.rcSwitcher({
		width: 44,
		height: 16,
		theme: 'lease',
		blobOffset: 1,
	//	autoStick: true,
		}).on( 'turnon.rcSwitcher', function( e, data ){
			var node = zTree.getNodeByTId(data.$input.closest("li").attr("id"));
			zTree.checkNode(node, true, true);
			rcSwitcherOn(data.$input,zTree);
		} ).on('turnoff.rcSwitcher', function( e, data ){
			var node = zTree.getNodeByTId(data.$input.closest("li").attr("id"));
			zTree.checkNode(node, false, true);
			rcSwitcherOff(data.$input,zTree);
		}
	);
}
function rcSwitcherOn(data,zTree){
    // debugger
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
	changeBizState(arr,"1");
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
	changeBizState(arr,"0");
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
    // debugger
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

function changeBizState(arr,bizState){
	jQuery.ajax({
		type : "POST",
		cache : false,
		dataType:"json",
		url : webPath+"/pmsBiz/updateStsById",
		data : {
			jsonData : JSON.stringify(arr),
			bizState : bizState
		},
		success : function(data) {
			if(!data.flag){
				alert(data.msg);
			}else{
				/*var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
				zTree.getNodesByParam("level", "test", null);*/
				
			}
		}
	});
}
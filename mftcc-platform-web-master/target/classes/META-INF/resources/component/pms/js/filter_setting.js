var columnNameArr = [];
var parmDic=[];
var IDMark_A = "_a";
var IDMark_F = "_f";
$(function() {
	var filter_setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			showLine: false,
			showIcon: false ,
			addDiyDom: addDiyDom
		},
		callback: {
			onClick: zTreeOnClick,
			onRemove: zTreeOnRemove
		}
	};
	$.fn.zTree.init($("#filter_setting_tree"), filter_setting, []);
	
	$("#save_btn").click(function() {
		if($("#dicType").val()=="y_n"&&!isNotNull($("#parm").val())){
			swal("信息提示！","条件为字典项类型时，字典项名称不能空！","error");
		}else{
			var treeObj = $.fn.zTree.getZTreeObj("filter_setting_tree");
			var nodes = treeObj.getNodes();
			$.each(nodes, function(i, node) {
				if (node.optCode == $("#optCode").val()) {
					node.optName = $("#optName").val();
					node.parm = $("#parm").val();
					node.dicType = $("#dicType").val();
				}
			});
			createCode();
		}
	});
	$("#del_btn").click(function() {
		var treeObj = $.fn.zTree.getZTreeObj("filter_setting_tree");
		var nodes = treeObj.getNodes();
		$.each(nodes, function(i, node) {
			if (typeof(node)!= "undefined"&&node.optCode == $("#optCode").val()) {
				treeObj.removeNode(node, true);
			}
		});
		$("#optName").val("");
		$("#parm").val("");
		$("#dicType").val("");
		$("#optCode").val("");
	});
	$("#dicType").bind("change",function(){
		if($(this).val()=="y_n"){
			$("#parmDiv").css("display","block");
		}else{
			$("#parmDiv").css("display","none");
		}
	});
	getTableName();
	$("#tableName").bind("change",function(){
		setTableName();
	});
});
//自定义生成筛选列表
function addDiyDom(treeId, treeNode) {
	$("#" + treeNode.tId + "_switch").remove();
}
function getTableName(){
	var tableObj = $("#tableName");
	tableObj.empty();
	tableObj.append($('<option value="" disabled selected>Select a Table</option>'));
	$.ajax({
		type: "post",
		dataType: 'json',
		url: webPath+"/pmsUserFilter/getTableName",
		async: false,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			var tableArr = data.json;
			$.each(tableArr,function(i,node){
				tableObj.append($("<option value='"+node.id+"'>"+node.id+"("+node.name+")"+"</option>"));
			});
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal("",XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus,"error");
		}
	});
};

function isNotNull(obj) {
	if (typeof(obj) != "undefined" && obj != "" && obj != null) {
		return true;
	} else {
		return false;
	}
}

function zTreeOnRemove(event, treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj("columnName_tree");
	var nodes = treeObj.getNodes();
	$.each(nodes, function(i, node) {
		if (node.id == treeNode.optCode) {
			treeObj.checkNode(node, false, true);
		}
	});
}

function zTreeOnClick(event, treeId, treeNode) {
	$("#optName").val("");
	$("#parm").val("");
	$("#dicType").val("");
	$("#optCode").val("");
	if (isNotNull(treeNode.optName)) {
		$("#optName").val(treeNode.optName);
	} else {
		$("#optName").val(treeNode.name);
	}
	if (isNotNull(treeNode.parm)) {
		$("#parm").val(treeNode.parm);
	}
	if (isNotNull(treeNode.dicType)) {
		$("#dicType").val(treeNode.dicType);
	}
	if (isNotNull(treeNode.optCode)) {
		$("#optCode").val(treeNode.optCode);
	}
	createCode();
};


function getRootPath() {
	//获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	var curWwwPath = window.document.location.href;
	//获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	//获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	//获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
}

function setTableName(val) {
	
	var tableName = $("#tableName").val();
	if (tableName == "") {
		return false;
	}
	$.ajax({
		type: "post",
		dataType: 'json',
		url: webPath+"/pmsUserFilter/getTableColumn?filterName=" + tableName,
		async: false,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			columnNameArr=data.json;
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal("",XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus,"error");
		}
	});
	columnNameList(columnNameArr);
	removeTreeNodes();
}
function removeTreeNodes(){
	var treeObj = $.fn.zTree.getZTreeObj("filter_setting_tree");
	var nodes = treeObj.getNodes();
	for (var i=nodes.length-1;i >=0; i--) {
		treeObj.removeNode(nodes[i]);
	}
}
function columnNameList(columnNameArr) {
	//筛选列表初始化
	var columnName_setting = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			showLine: false,
			showIcon: false
		},
		callback: {
			onCheck: zTreeOnCheck
		}
	};
	$.fn.zTree.init($("#columnName_tree"), columnName_setting, columnNameArr);
}

function zTreeOnCheck(event, treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj("filter_setting_tree");
	if (treeNode.checked) {
		var newNode = {
			id: treeNode.id + IDMark_F,
			name: treeNode.name,
			optCode: treeNode.id
		};
		newNode = treeObj.addNodes(null, newNode);
	} else {
		var nodes = treeObj.getNodes();
		for (var i = nodes.length-1; i >=0; i--) {
			if ((treeNode.id + IDMark_F) == nodes[i].id) {
				treeObj.removeNode(nodes[i]);
			}
		}
	}
};

function createCode() {
	var filter_dic = [];
	var treeObj = $.fn.zTree.getZTreeObj("filter_setting_tree");
	var nodes = treeObj.getNodes();
	$.each(nodes, function(i, node) {
		if (isNotNull(node.optName) && isNotNull(node.dicType)) {
			var obj = {};
			obj.optCode = node.optCode;
			obj.optName = node.optName;
			if(isNotNull(node.parm)){
				obj.parm = getParmDic(node.parm);
			}
			obj.dicType = node.dicType;
			filter_dic.push(obj);
		}
	});
	var codStr = '<script type="text/javascript">filter_dic ='+JSON.stringify(filter_dic)+';</script>';
	$("#show_code").val(codStr);
}
function getParmDic(parm){
	var arr=[];
	$.ajax({
		type: "post",
		dataType: 'json',
		url: webPath+"/pmsUserFilter/getParmDic?filterName="+parm,
		async: false,
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success: function(data) {
			parmDic=data.json;
			if(parmDic.length>0){
				$.each(parmDic, function(i,dic) {
					console.log(dic);
					var parmObj={};
					parmObj.optName = dic.name;
					parmObj.optCode = dic.id;
					arr.push(parmObj);
				});
			}else{
				swal("信息提示!", "字典项"+parm+"不存在！","error");
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			swal("",XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus,"error");
		}
	});
	return arr;
}

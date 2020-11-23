var setting = {
		data : {
			simpleData : {
				enable : true
			}
		},
		view : {
			showLine : false,
			dblClickExpand : false,
			addDiyDom : addDiyDom,
			selectedMulti: false,
			addHoverDom: addHoverDom,
			removeHoverDom: removeHoverDom,
		},
		callback : {
			onClick : zTreeOnClick
		}
	};

function addDiyDom(treeId, treeNode) {
	var aObj = $("#" + treeNode.tId + "_a");
	aObj.find("span").eq(0).remove();
	if ($("#diyBtn_"+treeNode.id).length>0) return;
	var imagesStr ="<img src="+webPath+'/themes/factor/images/dept.png'+' tabindex=1 style=margin-left: 4px; width=16 height=16'+">";
	if(treeNode.tId=="sysOrgTree_1"){
		imagesStr ="<img src="+webPath+'/themes/factor/images/org.png'+' tabindex=1 style=margin-left: 4px; width=16 height=16'+">";
		aObj.find("span").eq(0).before(imagesStr);
	}else{
		aObj.find("span").eq(0).before(imagesStr);
	}
}
//鼠标移进，添加删除编辑
function addHoverDom(treeId, treeNode) {
		/**
		var num=0;
		var aObj = $("#" + treeNode.tId + "_a").find("span");
		$.each($("#" + treeNode.tId + "_a").find("a"),function(index,obj){
			num=index+1;
		});
		if(num==0){
			var $remove = $("<a style='position: absolute;margin-top: 5px;margin-left: 4px;'><i class='i i-x3'></i></a>");
			$remove.bind("click", function () {
				removeTreeNode(treeNode);
			});
			var $edit = $("<a style='position: absolute;margin-top: 4px;margin-left: 18px;'><i class='i i-editable'></i></a>");
			if(treeNode.tId=="sysOrgTree_1"){
				$edit = $("<a style='position: absolute;margin-top: 4px;margin-left: 10px;'><i class='i i-editable'></i></a>");
			}else{
				aObj.after($remove);
			}
			$edit.bind("click", function () {
				editSysOrg(treeNode);
			});
			aObj.after($edit);
		}
		**/
};
//鼠标移出，去掉删除编辑
function removeHoverDom(treeId, treeNode) {
	var aObj = $("#" + treeNode.tId + "_a").find("span");
	aObj.nextAll("a").remove();
};

var userInfoTmp = new Array();
function zTreeOnClick(event, treeId, treeNode) {
	var brNo = treeNode.brNo;
	$("#brNo_hide").val(brNo);
	myCustomScrollbar({
    	obj:"#sysUserList",//页面内容绑定的id
    	url:webPath+"/sysUser/getAllUserByBrNoByPageAjax?brNo="+brNo,//列表数据查询的url
    	tableId:"tablerecsys5001",//列表数据查询的table编号
    	tableType:"thirdTableTag",//table所需解析标签的种类
    	pageSize:30,//加载默认行数(不填为系统默认行数)
    	callback:function(){
    		var table = $("#sysUserListByNo table").get(0);
    		
    		
			if(recNo != ''){
				var recNoArray = recNo.split("@");
				$.each(recNoArray,function(i,recNoThis){
					$(table).find('tbody tr').each(function(i){
						var str2 = $($(this).children('td')[1]).text();
						if(recNoThis == str2){
							var cb = $($(this).children('td')[2]).find('input[type=checkbox]').get(0);
							$(cb).attr('checked',true);
						}
					});
				});
				
				recNo = '';
			}
			
    		
    		$(table).find('input[type=checkbox]').click(function(){
    			userInfoTmp = new Array();  			
    			$(table).find('input[type=checkbox]:checked').each(function(i){
    				userInfoTmp.push(this);
    			});
    			
    		});
    		if(userInfoTmp.length != 0){
    			var j = 0;
    			for(j in userInfoTmp){
    				var str1 = $(userInfoTmp[j]).val().split('&')[0].split('=')[1];
    				$(table).find('tbody tr').each(function(i){
    					var str2 = $($(this).children('td')[1]).text();
    					if(str1 == str2){
    						var cb = $($(this).children('td')[2]).find('input[type=checkbox]').get(0);
    						$(cb).attr('checked',true);
    					}
    				});
    			}
    		}
    		
    	}
    });
};
// 鼠标右击节点
function OnRightClick(event, treeId, treeNode) {
	if (!treeNode && event.target.tagName.toLowerCase() != "button"&& $(event.target).parents("a").length == 0) {
		// zTree.cancelSelectedNode();
		// showRMenu("root", event.clientX, event.clientY);
	}else if(treeNode && !treeNode.noR && treeNode.level == 0){
		showRMenu("root", event.clientX, event.clientY);
	}else if (treeNode && !treeNode.noR && treeNode.level < 3) {
		zTree.selectNode(treeNode);
		showRMenu("node", event.clientX, event.clientY);
	}else{
		showRMenu("end", event.clientX, event.clientY);
	}
}
// 显示菜单
function showRMenu(type, x, y) {
	$("#rMenu ul").show();
//	if (type == "root") {
//		$("#m_del").hide();
//		$("#m_add").show();
//	} else if(type == "node") {
//		$("#m_del").show();
//		$("#m_add").show();
//	}else if(type == "end") {
//		$("#m_del").show();
//		$("#m_add").hide();
//	}
	rMenu.css({
		"top" : y + "px",
		"left" : x + "px",
		"visibility" : "visible"
	});

	$("body").bind("mousedown", onBodyMouseDown);
}
// 隐藏菜单
function hideRMenu() {
	
	if (rMenu)
		rMenu.css({
			"visibility" : "hidden"
		});
	$("body").unbind("mousedown", onBodyMouseDown);
	
}
// 鼠标按下
function onBodyMouseDown(event) {
	
	if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
		rMenu.css({
			"visibility" : "hidden"
		});
	}
	
}
//新增部门
function addSysOrg(){
	/**
	var brNo= $("#brNo_hide").val();
	top.sysOrg="";
	top.window.openBigForm('SysOrgAction_input.action?brNo='+brNo,'新增部门',function(){
		resetTree();
	});
	**/
}
//编辑部门
function editSysOrg(treeNode){
	/**
	top.sysOrg="";
	top.window.openBigForm('SysOrgAction_getById.action?brNo='+treeNode.brNo,'编辑部门',function(){
		if(top.sysOrg!=""){
			resetTree();
		}
	});
	**/
}
var addCount = 1;
// 增加节点
function addTreeNode() {
	/**
	var newNode = {};
	newNode.name = top.sysOrg.brName;
	if(newNode.name!=""){
		if (zTree.getSelectedNodes()[0]) {
			var pNode = zTree.getSelectedNodes()[0];
			newNode.brNo=top.sysOrg.brNo;
			newNode.iconSkin = "orgIcon" + (Number(pNode.brLev) + 1);
			newNode.brLev = Number(pNode.brLev) + 1;
			newNode['upOne'] = pNode.brNo;
			newNode.upTwo = pNode.upOne;
			newNode.brLev = Number(pNode.brLev) + 1;
			newNode.editSts = false;
			newNode.checked = zTree.getSelectedNodes()[0].checked;
			zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
		} else {
			zTree.addNodes(null, newNode);
		}
	}
	**/
}
// 删除节点
function removeTreeNode(treeNode) {
	/**
	if (treeNode.children && treeNode.children.length > 0) {
		var msg = treeNode.name+"，包含子机构，\n\n请删除子机构后，再删除！";
		alert(msg,0);
	} else {
		alert("确认删除部门："+treeNode.name,2,function(){
			$.ajax({
				type : "POST",
				data:{ajaxData:treeNode.brNo},
				url : webPath+"/sysOrg/delAjax",
				dataType : "json",
				success : function(data) {
					if(data.flag=="success"){
						alert(data.msg,1);
						zTree.removeNode(treeNode);
					}else{
						alert(data.msg,0);
					}
				},
				error : function(xmlhq, ts, err) {
					alert("删除失败！",0);
				}
			});
		});
	}
	**/
}
// 初始化节点
function resetTree() {
	$.ajax({
		type : "POST",
		data:{},
		url : webPath+"/sysOrg/getOrgTreeDataAjax",
		dataType : "json",
		success : function(data) {
			if(data.flag=="success"){
				ajaxData = eval("("+data.ajaxData+")");
				//先移除数据
				$("#sysOrgTree").find("li").remove();
				//重新加载树
				$.fn.zTree.init($("#sysOrgTree"), setting, ajaxData);
				//选中编辑节点
				//var zTree = $.fn.zTree.getZTreeObj("sysOrgTree");
				//zTree.selectNode(treeNode);
			}else{
				alert(data.msg,0);
			}
		},
		error : function(xmlhq, ts, err) {
			alert("查询失败！",0);
		}
	});
}

var zTree, rMenu;
$(document).ready(function(){
	$.fn.zTree.init($("#sysOrgTree"), setting, ajaxData);
	zTree = $.fn.zTree.getZTreeObj("sysOrgTree");
	rMenu = $("#rMenu");
	$(".scroll-content").mCustomScrollbar({
		advanced:{
			theme:"minimal-dark",
			updateOnContentResize:true
		}
	});
});
function saveSysOrg(obj){
	/**
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
		$.ajax({
			type : "POST",
			data:{ajaxData:dataParam},
			url : url,
			dataType : "json",
			success : function(data) {
				if(data.flag=="success"){
					top.sysOrg = data.sysOrg;
					alert(data.msg,1);
					myclose_click();
				}else{
					alert(data.msg,0);
				}
			},
			error : function(xmlhq, ts, err) {
				console.log(xmlhq);
				console.log(ts);
				console.log(err);
				//myAlert("保存失败！");
				alert("保存失败！",0);
			}
		});
	}
	**/
}
//新增员工
function addSysUser(){
	/**
	var brNo= $("#brNo_hide").val();
	var url="SysUserAction_input.action?brNo="+brNo;
	$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
	$(top.window.document).find("#showDialog").remove();
	**/
}
function getSysUserInfo(url) {
	/**
	$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
	$(top.window.document).find("#showDialog").remove();
	**/
}

function setSysOrgInfo(sysOrg){
	/**
	$("input[name=upOneName]").val(sysOrg.brName);
	$("input[name=upOne]").val(sysOrg.brNo);
	$("input[name=upTwo]").val(sysOrg.upTwo);
	**/
}

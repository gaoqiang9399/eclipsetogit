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
var setting_qudao = {
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
	var maxLen="11";
	if(treeNode.upOne=="0"){
		if(opNoType == "2" || opNoType == "3"){
		}else{
			imagesStr ="<img src="+webPath+'/themes/factor/images/org.png'+' tabindex=1 style=margin-left: 4px; width=16 height=16'+">";
			maxLen="13";
		}
	}
	aObj.find("span").eq(0).before(imagesStr);
	if(treeNode.level>=5){
		maxLen="10";
	}
	//部门名称过长时省略，避免删除、编辑按钮展示不出来
    var switchObj = $("#" + treeNode.tId + "_switch"),
    icoObj = $("#" + treeNode.tId + "_ico");
    icoObj.parent().before(switchObj);
    var spantxt = $("#" + treeNode.tId + "_span").html();
    var len=maxLen-treeNode.level;
    if (spantxt.length > maxLen) {
        spantxt = spantxt.substring(0, len) + "...";
        $("#" + treeNode.tId + "_span").html(spantxt);
    }
}
//鼠标移进，添加删除编辑
function addHoverDom(treeId, treeNode) {
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
};
//鼠标移出，去掉删除编辑
function removeHoverDom(treeId, treeNode) {
	var aObj = $("#" + treeNode.tId + "_a").find("span");
	aObj.nextAll("a").remove();
};

function zTreeOnClick(event, treeId, treeNode) {
	var brNo = treeNode.brNo;
	$("#brNo_hide").val(brNo);
	var tableId = "tablesys5001";
	if(opNoType == "2" || opNoType == "3"){
		tableId = "tablesysTrenchList";
	}
	myCustomScrollbar({
    	obj:"#sysUserList",//页面内容绑定的id
    	url:webPath+"/sysUser/getAllUserByBrNoByPageAjax?brNo="+brNo+"&opNoType="+opNoType,//列表数据查询的url
    	tableId:tableId,//列表数据查询的table编号
    	tableType:"thirdTableTag",//table所需解析标签的种类
    	pageSize:30,//加载默认行数(不填为系统默认行数)
     	callback:function(){
     		$("table").tableRcswitcher({
     			name:"opSts",onText:"启用",offText:"禁用"});
 		}//方法执行完回调函数（取完数据做处理的时候）
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
	var brNo= $("#brNo_hide").val();
	top.sysOrg="";
	top.window.openBigForm(webPath+'/sysOrg/input?brNo='+brNo+"&opNoType="+opNoType,'新增部门',function(){
		resetTree();
	});
}
//编辑部门
function editSysOrg(treeNode){
	top.sysOrg="";	
	top.window.openBigForm(webPath+'/sysOrg/getById?brNo='+treeNode.brNo+'&pId='+treeNode.pId+"&opNoType="+opNoType,'编辑部门',function(){
		if(top.sysOrg!=""){
			resetTree();
		}
	});	
}

var addCount = 1;
// 增加节点
function addTreeNode() {
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
	}
// 删除节点
function removeTreeNode(treeNode) {
	if (treeNode.children && treeNode.children.length > 0) {
		var msg = treeNode.name+"，包含子机构，\n\n请删除子机构后，再删除！";
		alert(msg,0);
	} else {
		//alert("确认删除部门："+treeNode.name,2,function(){
		alert(top.getMessage("CONFIRM_DELETE"),2,function(){
			$.ajax({
				type : "POST",
				data:{ajaxData:treeNode.brNo,opNoType:opNoType},
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
					alert(top.getMessage("FAILED_DELETE"),0);
				}
			});
		});
	}
}
// 初始化节点
function resetTree() {
	$.ajax({
		type : "POST",
		data:{opNoType:opNoType},
		url : webPath+"/sysOrg/getOrgTreeDataAjax",
		dataType : "json",
		success : function(data) {
			if(data.flag=="success"){
				ajaxData = eval("("+data.ajaxData+")");
				//先移除数据
				$("#sysOrgTree").find("li").remove();
				//重新加载树
				if(opNoType == "2" || opNoType == "3" || opNoType == "4"){
					$.fn.zTree.init($("#sysOrgTree"), setting_qudao, ajaxData);
				}else{
					$.fn.zTree.init($("#sysOrgTree"), setting, ajaxData);
				}
				//选中编辑节点
				//var zTree = $.fn.zTree.getZTreeObj("sysOrgTree");
				//zTree.selectNode(treeNode);
			}else{
				alert(data.msg,0);
			}
		},
		error : function(xmlhq, ts, err) {
			alert(top.getMessage("ERROR_SELECT"),0);
		}
	});
}

var zTree, rMenu;
$(document).ready(function(){
	if(opNoType == "2" || opNoType == "3" || opNoType == "4"){
		$.fn.zTree.init($("#sysOrgTree"), setting_qudao, ajaxData);
	}else{
		$.fn.zTree.init($("#sysOrgTree"), setting, ajaxData);
	}
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
	
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
		$.ajax({
			type : "POST",
			data:{ajaxData:dataParam,opNoType:opNoType},
			url : url,
			dataType : "json",
			success : function(data) {
				if(data.flag=="success"){
					top.sysOrg = data.newSysOrg;
					window.alert("保存成功，若修改了部门名称，请您在调整完组织架构后刷新组织架构！",3);
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
}
//新增员工
function addSysUser(){
	var brNo= $("#brNo_hide").val();
	var url=webPath+"/sysUser/input?brNo="+brNo+"&opNoType="+opNoType;
	$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
	$(top.window.document).find("#showDialog").remove();
}

//角色管理
function addRolr(){
    var url=webPath+"/sysOrg/sysRoleManagement";
    $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
    $(top.window.document).find("#showDialog").remove();
}
function getSysUserInfo(url) {
	if(url.substr(0,1)=="/"){
		url =webPath + url; 
	}else{
		url =webPath + "/" + url;
	}
	url = url + "&opNoType="+opNoType;
	$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
	$(top.window.document).find("#showDialog").remove();
}

function setSysOrgInfo(sysOrg){
	$("input[name=upOneName]").val(sysOrg.brName);
	$("input[name=upOne]").val(sysOrg.brNo);
	$("input[name=upTwo]").val(sysOrg.upTwo);
	//选择上级部门异步带出新增的机构展示编号
	setBrAliasNo(sysOrg.brNo);
}
/**
 * 选择上级部门异步带出新增的机构展示编号
 * @param upOneNo
 */
function setBrAliasNo(upOneNo){
	$.ajax({
		url : webPath+"/sysOrg/getBrAliasNoByUpNoAjax",
		data:{upOneNo:upOneNo},
		type : "POST",
		dataType : "json",
		success : function(data) {
			if(data.flag=="success"){
				$("input[name=brAliasNo]").val(data.brAliasNo);
			}else{
				alert(data.msg,0);
			}
		},
		error : function(xmlhq, ts, err) {
			console.log(xmlhq);
			console.log(ts);
			console.log(err);
			alert("保存失败！",0);
		}
	});
}
function changeBrType(obj){
	var brType = $(obj).val();
	if(brType == '4'){
		$("input[name='brAliasNo']").val("");
	}else{
		var upOne = $("input[name=upOne]").val();
		if(typeof (upOne)=='undefined' || upOne=='0'){
            upOne = "100000"
            $.ajax({
                url : webPath+"/sysOrg/getSysOrgByBrNo",
                data:{brNo:upOne},
                type : "POST",
                dataType : "json",
                success : function(data) {
                    if(data.flag=="success"){
                        $("input[name=upOneName]").val(data.brName);
                        $("input[name=upOne]").val(data.brNo);
                        $("input[name=upTwo]").val(data.upTwo);
                    }else{
                        alert(data.msg,0);
                    }
                },
                error : function() {
                    alert("获取部门失败！",0);
                }
            });
		}
        setBrAliasNo(upOne);
	}
}
//角色配置
function roleConfig(url){
	var roleNo="";
	var roleStr=url.split("?")[1];
	roleStr=roleStr.split("=")[1];
	var roleArr=roleStr.split("|");
	//如果多个角色，取第一个角色
	if(roleArr.length>0&&roleArr.length==1){
		roleNo=roleArr[0];
	}else{
		for ( var i = 0; i < roleArr.length; i++) {
			if(roleArr[i]!=""){
				roleNo=roleArr[i];
				break;
			}
		}
	}
	top.window.openBigForm(webPath+'/pmsConfigure/configureNew?roleNo='+roleNo,'角色配置',function(){});
}

function getBankByCardNumber (obj) {
        var identifyNumber = obj.value.trim().replace(/\s/g, "");
        var num = /^\d*$/; //全数字
        if (!num.exec(identifyNumber)) {
            window.top.alert("请输入正确的银行卡号", 0);
            return false;
        }
        $.ajax({
            url: webPath + "/bankIdentify/getByIdAjax",
            data: {
                identifyNumber: identifyNumber
            },
            type: 'post',
            dataType: 'json',
            success: function (data) {
                if (data.flag == "success") {
                    BASE.removePlaceholder($("input[name=bankNo]"));
                    BASE.removePlaceholder($("input[name=bankName]"));
                    $("input[name=bankNo]").val(data.bankId);
                    $("input[name=bankName]").val(data.bankName);
                } else {
                    $("input[name=bankNo]").val("");
                    $("input[name=bankName]").val("");
                }
            }
        });
}
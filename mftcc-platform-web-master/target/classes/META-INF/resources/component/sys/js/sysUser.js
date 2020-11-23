var getNewFlag = true;//是否新增用户号 true为新增，false为不新增
$(function() {
	$(".scroll-content").mCustomScrollbar({
		advanced : {
			updateOnContentResize : true
		}
	})
	init();
	$(".sys-user-label .i-x5").bind("click", function() {
		$(".sys-user-body").animate({
			right : "-1000px"
		});
	});

	/*$("input[name=brNo]").popupSelection({
		searchOn:false,//启用搜索
		inline:false,//下拉模式
		multiple:false,//多选选
		ztree:true,
		title:"所在机构",
		items:ajaxData.org,
		changeCallback : function (elem) {
			var node = elem.data("treeNode");
			var brName=node.name;
			$("input[name=brName]").val(brName);
		},
	});*/

	//角色新组件
	$("input[name=roleNo]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:true,//多选选
			items:ajaxData.role,
			addBtn:{//添加扩展按钮
				"title":"新增角色",
				"fun":function(d,elem){
					top.roleArray = roleArray;
					top.window.openBigForm(webPath+'/pmsConfigure/configureNew?roleNo=&opNoType='+opNoType,'新增角色',function(){
						if(top.roleNo!=""&&top.roleName!=""&&top.roleNo!=undefined&&top.roleName!=undefined){
							var newNode = {"id":top.roleNo,"name":top.roleName};
							roleArray = roleArray.concat(newNode);
							top.roleArray = roleArray;
							$(elem).popupSelection("addItem",newNode);
							//新增后选择该数据
							$(elem).popupSelection("selectedItem",newNode);
							top.roleNo="";
							top.roleName="";
						}
						top.roleArray="";
					});
				}
			},
			labelEdit:function(d){
				top.window.openBigForm(webPath+'/pmsConfigure/configureNew?roleNo='+d.id+'&opNoType='+opNoType,'角色配置',function(){
				});
			}
			
	});
	detailInit();
	funRoleTypeChange();
});

function setSysOrgInfo(sysOrg){
	$("input[name=brName]").val(sysOrg.brName);
	$("input[name=brNo]").val(sysOrg.brNo);
}

function roleCallback(data){
	var value = data.id;
	if(value.indexOf("addRole")=="1"||value=="addRole"){
		$("span[data-item-id=addRole]").find("a.select3-multiple-selected-item-remove").click();
		top.roleNo="";
		top.roleName="";
		top.window.openBigForm(webPath+'/pmsConfigure/configureNew?roleNo=','新增角色',function(){
			if(top.roleNo!=""){
				var spanhtml = "<span class='select3-multiple-selected-item' data-item-id='"+top.roleNo+"'><span>"+top.roleName
				+"</span><a class='select3-multiple-selected-item-remove'><i class='fa fa-remove' onclick='deteleRole(this)'></i></a>" +
				"<a class='select3-multiple-selected-item-remove'><i class='fa fa-edit' onclick='configureRole(this)'></i></a>" +
				"</span>";
				$("#userRole").find("input").before(spanhtml);
			}
		});
	}else{
		$("input[name=roleNo]").val($("input[name=roleNo]").val()+"|"+data.id);
		$(".select3-multiple-input").prev().append("<a class='select3-multiple-selected-item-remove'><i class='fa fa-edit' onclick='configureRole(event,this)'></i></a>");
	}
}

function detailInit(){
	//处理打开编辑页面详情
	//部门
	var brNo = $("input[name=brNo]").val();
	if(brNo!=undefined){
		$.each(ajaxData.org, function(index,obj){
			if(brNo==obj.brNo){
				$("#userOrg").find("div.select3-placeholder").html(obj.brName);
			}
		});
	}
	//角色
	var roleNo = $("input[name=roleNo]").val();
	if(roleNo!=undefined){
		var spanhtml = "";
		var roleNoArr = roleNo.split("|");
		for ( var i = 0; i < roleNoArr.length; i++) {
			$.each(ajaxData.role, function(index,obj){
				if(roleNoArr[i]==obj.roleNo){
					spanhtml = spanhtml+"<span class='select3-multiple-selected-item' data-item-id='"+obj.roleNo+"'><span>"+obj.roleName
					+"</span><a class='select3-multiple-selected-item-remove'><i class='fa fa-remove' onclick='deteleRole(event,this)'></i></a>" +
					"<a class='select3-multiple-selected-item-remove'><i class='fa fa-edit' onclick='configureRole(event,this)'></i></a>" +
					"</span>";
				}
			});
		}
		$("#userRole").find("input").before(spanhtml);
		$("#userRole").find("input").hide();
	}
}
//配置角色
function configureRole(event,obj){
	event.stopPropagation();
	var $obj = $(obj).parent().parent();
	var roleNo = $obj.attr("data-item-id");
	top.roleNo="";
	top.roleName="";
	top.window.openBigForm(webPath+'/pmsConfigure/configureNew?roleNo='+roleNo,'角色配置',function(){
		if(top.roleNo!=""){
			$("#userRole").find("span[data-item-id="+top.roleNo+"]").find("span").html(top.roleName);
		}
	});
	//event.preventDefault();
	//return false;
}
//删除角色
function deteleRole(event,obj){
	event.stopPropagation();
	var $obj = $(obj).parent().parent();
	var roleNo = $obj.attr("data-item-id");
	$obj.remove();
	var roleNoStr = "";
	var roleTmp = "";
	$.each($("#userRole").find("span.select3-multiple-selected-item"), function(index,val){
		roleTmp = $(val).attr("data-item-id");
		if(roleNo!=roleTmp){
			if(roleNoStr==""){
				roleNoStr=$(val).attr("data-item-id");
			}else{
				roleNoStr=roleNoStr+"|"+$(val).attr("data-item-id");
			}
		}
	});
	$("input[name=roleNo]").val(roleNoStr);
}
function init(){
	var userRoleHtml ="<div id='userRole' onclick='clearError(this);' style='position: relative;margin-top: -4px;'></div>";
	$("input[name=roleNo]").before(userRoleHtml);
	$("input[name=roleNo]").hide();
	var userOrgHtml ="<div id='userOrg' onclick='clearError(this);' style='position: relative;margin-top: -4px;'></div>";
	$("input[name=brNo]").before(userOrgHtml);
	$("input[name=brNo]").hide();
}
function getOpNO() {
	var brNo = $("input[name=brNo]").val();
		$.ajax({
			url:webPath+"/sysUser/getUserPreAjax?brNo="+brNo,
			dataType:"json",
			type:"get",
			success: function(data) {
				if(data.flag=="success") {
					$("#opNo").val(data.data);
				}else {
					alert(data.msg,0);
				}
			},
			error: function() {
				alert("生成用户号失败，请稍后再试",0);
			}
		});
}
var _btnUrl = webPath+"/sysUser/updateAjax";

function getSysUserInfo(url) {
	_btnUrl = webPath+"/sysUser/updateAjax";
	var ajaxParam = {};
	if(url.indexOf("ActionAjax_")!=-1&&url.indexOf("?")!=-1){//ajax提交
		var urlParams = url.split("?");
		url = urlParams[0];
		$.each(urlParams[1].split("&"), function(index,val){
			var key = val.split("=")[0];
			var value = val.split("=")[1];
			ajaxParam[key] = value;
		});
	}
	$.ajax({
		type : "POST",
		url : url,
		data:ajaxParam,
		dataType : "json",
		success : function(data) {
			var formData = data.formData;
			$.each(formData, function(key, valArgs) {
				if(url.indexOf('query=query')>-1){
					$('.sys-user-save').hide();
					$('.sys-user-edit #' + key).attr("disabled","disabled");
					$('.sys-user-edit #' + key).css({"border":"none"});
				}else{
					$('.sys-user-save').show();            
					$('.sys-user-edit #' + key).removeAttr("disabled");
					$('.sys-user-edit #' + key).css({"border":"1px solid #DDDDDD"});
				}
				$('.sys-user-edit #' + key).val(valArgs);
				if ($('.sys-user-edit #' + key).attr("type") == "hidden") {
					var $this = $('.sys-user-edit #' + key);
					var val = $this.val();
					if(url.indexOf('query')>-1){
						$this.prev().select3('setOptions', { readOnly: true });
					}else{
						$this.prev().select3('setOptions', { readOnly: false });
					}
					if (val.indexOf("|") > -1) {
						$this.prev().select3('value', val.split("|"));
					} else {
						$this.prev().select3('value', val);
					}
				}
			});
			$("#lastModDate").attr("disabled","disabled").css({"border":"none","display":"none"});
			$("#pwdInvalDate").attr("disabled","disabled").css({"border":"none","display":"none"});
//			$("#frame").attr("disabled","disabled").css({"display":"none"});
//			$("#color").attr("disabled","disabled").css({"display":"none"});
//			$(".sys-user-edit input[name=lastModDate]").parent().parent().show();
//			$(".sys-user-edit input[name=pwdInvalDate]").parent().parent().show();
			$(".sys-user-body").animate({
				right : "0px"
			});
		},
		error : function(xmlhq, ts, err) {
			alert(xmlhq + "||" + ts + "||" + err);
		}
	});
}

function insertSysUser(){
	getNewFlag = false;
	$(".sys-user-body").animate({
		right : "0px"
	});
	_btnUrl = webPath+"/sysUser/insertAjax";
	$.each($(".sys-user-edit input"),function(i,obj){
		$(this).removeAttr("disabled");
		$(this).removeAttr("readonly");
		$(this).val("");
	});
	$("#opNo").prop("readonly","readonly");
	$(".sys-user-edit input[name=lastModDate]").parent().parent().hide();
	$(".sys-user-edit input[name=pwdInvalDate]").parent().parent().hide();
	$('#userOrg').select3('setOptions', { readOnly: false });
	$('#userRole').select3('setOptions', { readOnly: false });
	$('#userSts').select3('setOptions', { readOnly: false });
	$('#userSex').select3('setOptions', { readOnly: false });
	$('#userOrg').select3('value','');
	$('#userRole').select3('value',[]);
	$('#userSts').select3('value','');
	$('#userSex').select3('value','');
	$('.sys-user-save').show();
	$('#passwordhash').val('000000');
	getNewFlag = true;
}


function saveSysUserInfo(obj) {
	var check = true;
	$("#userTable").find(".Required").removeClass(".Required");
	$("#userTable").find("label").remove();
	if($("#loginUser").val()==""||$("#loginUser").val()==null) {
		$("#loginUser").addClass("Required");
		$("#loginUser").parent().append("<label class = 'Required-font'><i class='fa fa-exclamation-circle'></i>不能为空</label>");
		check = false;
	}
//	if($("#opNo").val()==""||$("#opNo").val()==null) {
//		$("#opNo").addClass("Required");
//		$("#opNo").parent().append("<label class = 'Required-font'><i class='fa fa-exclamation-circle'></i>不能为空</label>");
//		check = false;
//	}
	if($("#opName").val()==""||$("#opName").val()==null) {
		$("#opName").addClass("Required");
		$("#opName").parent().append("<label class = 'Required-font'><i class='fa fa-exclamation-circle'></i>不能为空</label>");
		check = false;
	}
	if($("#passwordhash").val()==""||$("#passwordhash").val()==null) {
		$("#passwordhash").addClass("Required");
		$("#passwordhash").parent().append("<label class = 'Required-font'><i class='fa fa-exclamation-circle'></i>不能为空</label>");
		check = false;
	}
	if($("#userOrg").text()=="选择机构"||$("#userOrg").text()==""||$("#userOrg").text()==null) {
		$("#userOrg").addClass("Required");
		$("#userOrg").parent().append("<label class = 'Required-font'><i class='fa fa-exclamation-circle'></i>不能为空</label>");
		check = false;
	}
	/*if($("#mobile").val()==""||$("#mobile").val()==null) {
		$("#mobile").addClass("Required");
		$("#mobile").parent().append("<label class = 'Required-font'><i class='fa fa-exclamation-circle'></i>不能为空</label>");
		check = false;
	}*/
	if($("#userRole").text()=="选择角色"||$("#userRole").text()==""||$("#userRole").text()==null) {
		$("#userRole").addClass("Required");
		$("#userRole").parent().append("<label class = 'Required-font'><i class='fa fa-exclamation-circle'></i>不能为空</label>");
		check = false;
	}
	if($("#userSts").text()=="用户状态"||$("#userSts").text()==""||$("#userSts").text()==null) {
		$("#userSts").addClass("Required");
		$("#userSts").parent().append("<label class = 'Required-font'><i class='fa fa-exclamation-circle'></i>不能为空</label>");
		check = false;
	}
	if(check) {
		$.ajax({
			type : "POST",
			data:{ajaxData:JSON.stringify($(obj).serializeArray())},
			url : _btnUrl,
			dataType : "json",
			success : function(data) {
				if(data.flag=="success"){
					window.location.href=webPath+"/sysOrg/listSysOrg?opNoType=" + opNoType;
				}else{
					alert(data.msg,0);
				}
			},
			error : function(xmlhq, ts, err) {
				console.log(xmlhq);
				console.log(ts);
				console.log(err);
			}
		});
		_btnUrl=webPath+"/sysUser/updateAjax";
	}
}
//保存员工信息
function saveSysUser(obj){
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			type : "POST",
			data:{ajaxData:dataParam,opNoType:opNoType},
			url : url,
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if(data.flag=="success"){
					alert(data.msg,1);
					cancelSysUserMange();
				}else{
					alert(data.msg,0);
				}
			},
			error : function(xmlhq, ts, err) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
}
function cancelSysUserMange(){
	window.location.href=webPath+"/sysOrg/listSysOrg?opNoType=" + opNoType;
};
//数据权限类型变化时，控制指定部门显示和隐藏
function funRoleTypeChange(){
	var funRoleType =$("select[name=funRoleType]").val();
	//指定部门
	var ztreeSetting = {
			check: {  
                enable: true,  
                chkStyle: "checkbox",  
                chkboxType: { "Y": "s", "N": "" }  
            },
            data:    {
                simpleData:{
                    enable:true
                }
            },
	}
	if(funRoleType=="6"){
		$("input[name=funRoleAssignOrg]").parents("tr").show();
		//加载部门选择组件
		$("input[name=funRoleAssignOrg]").popupSelection({
			ajaxUrl : webPath+"/sysOrg/sysOrgForSelectAjax?opNoType="+opNoType,
			searchOn : true,//启用搜索
			multiple : true,//多选
			ztree : true,
			parentSelect : true,
			ztreeSetting : ztreeSetting,
			title : "指定部门",
			splitStr:";",
			handle : BASE.getIconInTd($("input[name=funRoleAssignOrg]")),
			changeCallback : function (elem) {
				BASE.removePlaceholder($("input[name=funRoleAssignOrg]"));
			}
		});
	}else{
		$("input[name=funRoleAssignOrg]").parents("tr").hide();
		$("input[name=funRoleAssignOrg]").val("");
		$("input[name=funRoleAssignOrgName]").val("");
	}
}
//选择指点机构回调
function getSelectOrgData(orgInfo){
	$("input[name=funRoleAssignOrg]").val(orgInfo.brNo);
	$("input[name=funRoleAssignOrgName]").val(orgInfo.brName);
}

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

(function($){
	$.fn.selectBox = function() {
		var div_body=$("<div class='data-rand-body'></div>");
		$(this).parent().append(div_body);
		var id = $(this).attr("id");
		div_body.append($(this));
		var div_btn = $('<div class="data-rand-btn"></div>');
		var i_btn = $('<i class="fa fa-refresh"></i>');
		div_btn.append(i_btn)
		div_body.append(div_btn);
		i_btn.bind('click',function(){
			var obj = $("#"+id);
			var objo = $("#"+id+" option");
			(obj.get(0).selectedIndex+1)<(objo.length)?obj.get(0).selectedIndex=obj.get(0).selectedIndex+1:obj.get(0).selectedIndex=0;
		});
	};
	
})(jQuery);

window.onload=function(){
	var hh = window.innerHeight;
	var mh = parseInt($(".pms_configure_div").css('paddingTop'))+parseInt($(".pms_configure_div").css('paddingBottom'));
	var headH = $(".pms_configure_title").outerHeight(true);
	var footH = $(".pms_configure_btn").outerHeight(true);
	var bodyH = hh-mh-headH-footH;
	//$(".pms_configure_tree").height(bodyH);
	//角色表单赋值
	$("#roleNo").val(roleNo);
	$("#roleName").val(roleName);
	$("#roleType").val(roleType);
	$("#roleLev").val(roleLev);
	if(roleName!=null&&roleName!=""){
		$(".ajaxInsert").hide();
		$(".ajaxEdit").show();
	}else{
		$(".ajaxInsert").show();
		$(".ajaxEdit").hide();
	}
	$.ajax({
		type:"post",
		async:false,
		cache:false,
		url:webPath+"/pmsConfigure/configureAjax",
		dataType:"json",
		data:{
			roleNo:roleNo
		},
		success:function(jsonData){
			if(jsonData.flag=="success"){
				dataMap = jsonData.json;
				//入口树数据
				var zNodes = dataMap.entrArray;
				var view_zNodes = dataMap.viewArray;
				var data_zNodes = dataMap.dataArray;
				var entrTreeObj = $.fn.zTree.init($("#treeEntr"),setting,zNodes);
				entrTreeObj.expandAll(true);
				entrTreeObj.expandAll(false);
				/*var viewTreeObj = $.fn.zTree.init($("#treeView"), view_setting, view_zNodes);
				viewTreeObj.expandAll(true);
				viewTreeObj.expandAll(false);*/
				var dataTreeObj = $.fn.zTree.init($("#treeData"), data_setting, data_zNodes);
				dataTreeObj.expandAll(true);
				dataTreeObj.expandAll(false);
				var bodyHeight = document.body.clientHeight-110;
				//$(".pms_configure_tree").height(bodyHeight);
				$(".pms_configure_tree").each(function(i,obj){
					$(obj).fadeIn(2000,function(){
						$(obj).parents(".pms_configure_sub").find(".loading").remove();
					});
				});
			}else{
				window.top.alert(jsonData.msg,0);
			}
		},
		error:function(){
			alert("error");
		}
	});
} 

function changePmsBizRoleRel(){
	window.location.href=webPath+"/pmsBizRoleRel/getPage?roleNo="+$("#roleNo").val();
}
	//初始化树
	var setting = {
		data : {
			simpleData : {
				enable : true
			}
		},
		view: {
			addDiyDom: addEntrDom,
			showLine: false
		}
	};

	//视角树
	var view_setting = {
		data : {
			simpleData : {
				enable : true
				
			}
		},
		view: {
			addDiyDom: addViewDom,
			showLine: false
		}
	};
	

	//数据权限树
	var data_setting = {
		data : {
			simpleData : {
				enable : true
			}
		},
		view: {
			addDiyDom: addDataDom,
			showLine: false
		}
	};
	//自定义 入口tree
	function addEntrDom(treeId, treeNode) {
        var aObj;
        var editStr,btn;
		if(treeId=="pmsBizTree"){
			var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
			var root;
            aObj = $("#" + treeNode.tId + "_a");
			if(treeNode.getParentNode()){
				root = treeNode.getParentNode().id;
			}else{
				root = "root";
			}
			
			editStr = "<input value='"+treeNode.id+"' type='checkbox' class='checkbox_"+treeId+" checkboxBtn' id='checkbox_" +treeNode.id+ "' name='checkbox_"+root+"' onfocus='this.blur();' /><span class = 'tree_line'></span>";
			aObj.after(editStr);
			
			btn = $("#checkbox_"+treeNode.id);
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
					getCheckLvlTrue(data.$input.parent().parent(),arr);
//					changeBizState(arr,"1");
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
					getCheckLvlFalse(data.$input.parent().parent(),arr);
//					changeBizState(arr,"0");
				}
			);
		}else{
			aObj = $("#" + treeNode.tId + "_a");
			var checked4 = "";
			if(treeNode.checked){
				checked4 = "checked";
			}
			if (treeNode.level == 1) {
				editStr = "<input value='"+treeNode.id+"' type='checkbox' class='checkbox_"+treeId+" checkboxBtn' id='checkbox_" +treeNode.id+ "' name='checkbox_"+treeNode.getParentNode().id+"' onfocus='this.blur();'  "+checked4+"/><span class = 'tree_line'></span>";
				aObj.after(editStr);
				btn = $("#checkbox_"+treeNode.id);
				if (btn) btn.rcSwitcher({
					width: 44,
					height: 16,
					theme: 'lease',
					blobOffset: 1
				//	autoStick: true,
					}).on( 'turnon.rcSwitcher', function( e, data ){
						data.$input.parent().parent().find("input[name="+data.$input.attr("name")+"]").each(function(){
							$(this)[0].checked=false;
							
							$(this).parent().find(".stoggler").removeClass("on").addClass("off").css(off_css);
						});
						data.$input.parent().find(".stoggler").addClass("on").removeClass("off").css(on_css);
						data.$input[0].checked=true;
						rcEntrSwitcher();
					} ).on('turnoff.rcSwitcher', function( e, data ){
						rcEntrSwitcher();
					});
			}
		}
	}
	/**
	 * 入口权限开启按钮事件
	 * @param type
	 */
	function rcEntrSwitcher(){
		var entrNoStr = "";
		$(".checkbox_treeEntr").each(function(){
			if($(this)[0].checked){
				entrNoStr += $(this).attr("value") + ",";
			}
		});
		jQuery.ajax({
			type : "POST",
			cache : false,
			dataType:"json",
			url : webPath+"/pmsBiz/getAllPmsBizByEntr",
			data:{
				bizState:"1",
				roleNo:roleNo,
				entrNoStr:entrNoStr
			},
			success : function(data) {
				if(!data||data.length==0){
					data = [];
				}else{
					var zTree = $.fn.zTree.init($("#pmsBizTree"), setting_biz, data);
					selectCheckNode(data);
					zTree.expandAll(false);
					$("#pmsBizTree_1_switch").click();
					// $.each($("li[id^=pmsBizTree]").find("li[class=level1]"),function(i,obj){
					// 	if($(obj).find("ul").length==0){
					// 		$(obj).remove();
					// 	}
					// });
				}
			}
		});
	}
	//自定义 view
	function addViewDom(treeId, treeNode) {
		var aObj = $("#" + treeNode.tId + "_a");
		var checked4 = "";
		if(treeNode.checked){
			checked4 = "checked";
		}
		if (treeNode.level == 0) {
		} else{
			var editStr = "<input value='"+treeNode.id+"' type='checkbox' class='checkbox_"+treeId+" checkboxBtn' id='checkbox_" +treeNode.id+ "' name='checkbox_"+treeNode.getParentNode().id+"' onfocus='this.blur();'  "+checked4+"/><span class = 'tree_line'></span>";
			aObj.after(editStr);
			var btn = $("#checkbox_"+treeNode.id);
			if (btn) btn.rcSwitcher({
				width: 44,
				height: 16,
				theme: 'lease',
				blobOffset: 1,
			//	autoStick: true,
				}).on( 'turnon.rcSwitcher', function( e, data ){
					data.$input.parent().find(".stoggler").addClass("on").removeClass("off").css(on_css);
					data.$input.parent().find("ul").find(".stoggler").addClass("on").removeClass("off").css(on_css);
					data.$input.parent().find("ul").find("input[type=checkbox]").each(function(){
						$(this)[0].checked=true;
					});
					data.$input[0].checked=true;
				} ).on('turnoff.rcSwitcher', function( e, data ){
					data.$input.parent().find(".stoggler").addClass("off").removeClass("on").css(off_css);
					data.$input.parent().find("ul").find(".stoggler").addClass("off").removeClass("on").css(off_css);
					data.$input.parent().find("ul").find("input[type=checkbox]").each(function(){
						$(this)[0].checked=false;
					});
					data.$input[0].checked=false;
			}
		);
		}
	}
	//自定义 数据权限
	function addDataDom(treeId, treeNode) {
		var aObj = $("#" + treeNode.tId + "_a");
		var checked4 = "";
		if(treeNode.checked){
			checked4 = "checked";
		}
		var $select = $('<select class="select_'+treeId+'" id="select_' +treeNode.id+ '"></select>');
		var editStr = "<select class='select_"+treeId+"' id='select_" +treeNode.id+ "'><option></option><option value='1'>登记人</option><option value='4'>客户经理</option><option value='2'>本机构</option><option value='3'>本机构及其向下</option><option value='5'>上级机构及其向下</option><option value='9'>查看全部</option></select>";
			$select.append("<option></option>");
		$.each(treeNode.list,function(i,node){
			$select.append("<option value='"+node.pmsLv+"'>"+node.pmsName+"</option>");
		});
		aObj.after($select);
		aObj.css("width","90%");
		var btn = $("#select_"+treeNode.id);
		if(btn) {
			btn.val(treeNode.roleType);
			btn.selectBox();
		}
	}

	$(function() {
		$(".pms_configure_tree").mCustomScrollbar({theme:"minimal-dark"});
		
	});
	$(window).resize(function() {
		$(".pms_configure_tree").height("");
		var bodyHeight = document.body.clientHeight-140;
		$(".pms_configure_tree").height(bodyHeight);
	});
	function zTreeOnExpand(event, treeId, treeNode) {
		if(treeNode.children){
			getChildNode(treeNode.children);
		}
	};
	
	
	
	//获取入口
	function getEntrValue() {
		var flag = ifAddRoleInfo();
		if(flag){
			var str = "";
			$(".checkbox_treeEntr").each(function(){
				if($(this)[0].checked){
					str += $(this).attr("value") + ",";
				}
			});
			var roleNo = $("#roleNo").val();
	 	 	jQuery.ajax({
				url : webPath+"/pmsEntranceRole/insertByRoleNo",
				data : {
					roleNo : roleNo,
					pmsNo : str
				},
				type : "POST",
				dataType : "json",
				async : false,//关键
				success : function(data) {
					if (data.flag == "error") {
						alert(data.msg,0);
					} else {
						alert(data.msg,1);
					}
				},
				error : function(data) {
					alert(  top.getMessage("FAILED_SAVE"),0);
				}
			});
		}
	}
	//
	function ifAddRoleInfo(){
		var flag = $("#flag").val();
		if(flag!="1"){
			alert(top.getMessage("FIRST_OPERATION_ADD","角色"),0);
			return false;
		}
		return true;
	}
	//获取视角
	function getViewValue() {
		var flag = ifAddRoleInfo();
		if(flag){
			var str = "";
			$(".checkbox_treeView").each(function(){
				if($(this)[0].checked){
					str += $(this).attr("value") + ",";
				}
			});
			var roleNo = $("#roleNo").val();
			jQuery.ajax({
				url : webPath+"/pmsViewpointRole/insertByRoleNo",
				data : {
					roleNo : roleNo,
					viewpointMenuNo : str
				},
				type : "POST",
				dataType : "json",
				async : false,//关键
				success : function(data) {
					if (data.flag == "error") {
						alert(data.msg,0);
					} else {
						alert(data.msg,1);
					}
				},
				error : function(data) {
					alert(  top.getMessage("FAILED_SAVE"),0);
				}
			}); 
		}
	}
	//获取数据权限
	function getDataValue() {
		var flag = ifAddRoleInfo();
		if(flag){
			var dataTreeObj = $(".select_treeData");
			var str = "";
			$.each(dataTreeObj, function(i, list) {
				str += $(this).attr("id").split("_")[1]+"_"+$(this).val()+ ",";
			});
			var roleNo = $("#roleNo").val();
		 	jQuery.ajax({
				url : webPath+"/pmsDataRangRole/insertByRoleNo",
				data : {
					roleNo : roleNo,
					funNo : str
				},
				type : "POST",
				dataType : "json",
				async : false,//关键
				success : function(data) {
					if (data.flag == "error") {
						alert(data.msg,0);
					} else {
						alert(data.msg,1);
					}
				},
				error : function(data) {
					alert(  top.getMessage("FAILED_SAVE"),0);
				}
			}); 
		}
	}
	
	function saveSysRole(obj,flag){
		var dataParam = JSON.stringify($(obj).serializeArray());
		var roleName = $("#roleName").val();
		if(roleName==""){
			alert(top.getMessage("FIRST_OPERATION_ADD","角色名称"),0);
			return;
		}
		jQuery.ajax({
			url : webPath+"/sysRoleAction/insertOrUpdate",
			data : {ajaxData:dataParam,flag:flag},
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.flag=="1") {
					$("#saveButton").hide();
					$("#editButton").show();
					//$("#roleName").hide();
					//$("#roleNameShow").show().html($("#roleName").val());
					$("#saveSysRoleForm").find("font").hide();
					top.roleNo=$("#roleNo").val();
					top.roleName=$("#roleName").val();
					$("#flag").val("1");//标识角色已新增
					alert(data.msg,1);
				}else if(data.flag=="update"){
					top.roleNo=$("#roleNo").val();
					top.roleName=$("#roleName").val();
					alert(data.msg,1);
				}else if(data.flag=="0"){
					alert(data.msg,0);
				}
			},
			error : function(data) {
				alert(  top.getMessage("FAILED_SAVE"),0);
			}
		}); 
	}
	
	function switchEditAdd(){
		$("#saveButton").show();
		$("#editButton").hide();
		$("#roleName").show();
		$("#roleNameShow").hide();
		$("#saveSysRoleForm").find("font").show();
	}
	
	function saveSysRolePms(flag){
		//角色
		var dataParam = JSON.stringify($("#saveSysRoleForm").serializeArray());
		var roleName = $("#roleName").val();
		var roleArray = top.roleArray;
		if(roleName==""){
			alert(top.getMessage("FIRST_OPERATION_ADD","角色名称"),0);
			return;
		}
		if(roleArray!=undefined&&roleArray.length>0){
			var i = roleArray.length;
			  while (i--) {
			    if (roleArray[i].name === roleName) {
			    	alert(top.getMessage("ERROR_REPEAT","角色名称"),0);
			      return ;
			    }
			  }
		}
		//入口权限
		var entrNoStr = "";
		$(".checkbox_treeEntr").each(function(){
			if($(this)[0].checked){
				entrNoStr += $(this).attr("value") + ",";
			}
		});
		//功能权限
		var zTree = $.fn.zTree.getZTreeObj("pmsBizTree");
		var pmsBizArr = [];
		$("#pmsBizTree").find(".stoggler").each(function(){
			if($(this).hasClass("on")){
				var node = zTree.getNodeByTId($(this).closest("li").attr("id"));
				pmsBizArr.push(node.id);
			}
		});
		LoadingAnimate.start();
		jQuery.ajax({
			url : webPath+"/sysRole/saveRolePmsConfig",
			data : {
				flag:flag,
				ajaxData:dataParam,
				entrNoStr:entrNoStr,
				pmsBizArr:JSON.stringify(pmsBizArr),
				opNoType:opNoType},
				
			type : "POST",
			dataType : "json",
			success : function(data) {
				LoadingAnimate.stop();
				if(data.flag=="success"){
					if (flag=="add") {
						$(".ajaxInsert").hide();
						$(".ajaxEdit").show();
						top.roleNo=$("#roleNo").val();
						top.roleName=$("#roleName").val();
						alert(data.msg,1);
					}else if(flag=="edit"){
						top.roleNo=$("#roleNo").val();
						top.roleName=$("#roleName").val();
						alert(data.msg,1);
					}
					top.roleArray="";
				}else if(data.flag=="exist"){
					alert(data.msg,0);
				}
                myclose_click();
			},
			error : function(data) {
				alert(  top.getMessage("FAILED_SAVE"),0);
			}
		}); 
	}
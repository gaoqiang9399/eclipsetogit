<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="../include/message.jsp" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${webPath}">

<title>角色权限配置页面</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="角色权限配置页面">
<script type="text/javascript" src="${webPath}/component/pms/js/jquery.min.js"></script>
<%-- <script type="text/javascript" src="${webPath}component/pms/zTree_v3/js/jquery-1.4.4.min.js"></script> --%>
<script type="text/javascript" src="${webPath}/UIplug/zTree/jquery.ztree.all.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		
<%--滚动条js 和鼠标滚动事件js--%>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/zTree/metroStyle/metroStyle.css">
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<%--滚动样式--%>
<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css"/>
<link rel="stylesheet" href="${webPath}/component/pms/css/pms_configure.css">
<link rel="stylesheet" href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css">
<script type="text/javascript">
	//初始化数据
	var dataMap;
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
	var mh = parseInt($(".pms_configure_sub").css('marginTop'))+parseInt($(".pms_configure_sub").css('marginBottom'));
	var headH = $(".pms_configure_title").outerHeight(true);
	var footH = $(".pms_configure_btn").outerHeight(true);
	var bodyH = hh-mh-headH-footH;
	$(".pms_configure_tree").height(bodyH);
	$.ajax({
		type:"post",
		async:false,
		cache:false,
		url:webPath+"/pmsConfigure/configureAjax",
		dataType:"json",
		data:{
			roleNo:'${roleNo}'
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
				var viewTreeObj = $.fn.zTree.init($("#treeView"), view_setting, view_zNodes);
				viewTreeObj.expandAll(true);
				viewTreeObj.expandAll(false);
				var dataTreeObj = $.fn.zTree.init($("#treeData"), data_setting, data_zNodes);
				dataTreeObj.expandAll(true);
				dataTreeObj.expandAll(false);
				var bodyHeight = document.body.clientHeight-110;
				$(".pms_configure_tree").height(bodyHeight);
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
		var aObj = $("#" + treeNode.tId + "_a");
		var checked4 = "";
		if(treeNode.checked){
			checked4 = "checked";
		}
		if (treeNode.level == 1) {
			var editStr = "<input value='"+treeNode.id+"' type='checkbox' class='checkbox_"+treeId+" checkboxBtn' id='checkbox_" +treeNode.id+ "' name='checkbox_"+treeNode.getParentNode().id+"' onfocus='this.blur();'  "+checked4+"/><span class = 'tree_line'></span>";
			aObj.after(editStr);
			var btn = $("#checkbox_"+treeNode.id);
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
				} );
		}
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
		var bodyHeight = document.body.clientHeight-110;
		$(".pms_configure_tree").height(bodyHeight);
	});
	function zTreeOnExpand(event, treeId, treeNode) {
		if(treeNode.children){
			getChildNode(treeNode.children);
		}
	};
	
	
	
	//获取入口
	function getEntrValue() {
		var str = "";
		$(".checkbox_treeEntr").each(function(){
			if($(this)[0].checked){
				str += $(this).attr("value") + ",";
			}
		});
	 	jQuery.ajax({
			url : webPath+"//pmsEntranceRole/insertByRoleNo",
			data : {
				roleNo : dataMap.roleNo,
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
	//获取视角
	function getViewValue() {
		var str = "";
		$(".checkbox_treeView").each(function(){
			if($(this)[0].checked){
				str += $(this).attr("value") + ",";
			}
		});
		 jQuery.ajax({
			url : webPath+"/pmsViewpointRole/insertByRoleNo",
			data : {
				roleNo : dataMap.roleNo,
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
	//获取数据权限
	function getDataValue() {
		var dataTreeObj = $(".select_treeData");
		var str = "";
		$.each(dataTreeObj, function(i, list) {
			str += $(this).attr("id").split("_")[1]+"_"+$(this).val()+ ",";
		});
	 	jQuery.ajax({
			url : webPath+"/pmsDataRangRole/insertByRoleNo",
			data : {
				roleNo : dataMap.roleNo,
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
	
</script>
</head>
<body>
<!--标记点 未后退准备-->
	<dhcc:markPoint markPointName="pms_Configure"/>
	<div class="pms_configure_body">
		<div class="pms_configure_sub">
			<div class="pms_configure_title">
				<span>入口权限配置</span>
			</div>
			<div class="pms_configure_tree" style="display: none;">
				<ul id="treeEntr" class="ztree"></ul>
			</div>
			<div class="pms_configure_btn">
				<input class ="pms_configure_save" type="button" value="保存" onclick="getEntrValue();" />
			</div>
		</div>
		<div class="pms_configure_sub">
			<div class="pms_configure_title">
				<span>视角权限配置</span>
			</div>
			<div class="pms_configure_tree" style="display: none;">
				<ul id="treeView" class="ztree"></ul>
			</div>
			<div class="pms_configure_btn">
				<input class ="pms_configure_save"  type="button" value="保存" onclick="getViewValue();" />
			</div>
		</div>
		<div class="pms_configure_sub">
			<div class="pms_configure_title">
				<span>数据权限配置</span>
			</div>
			<div class="pms_configure_tree" style="display: none;">
				<ul id="treeData" class="ztree"></ul>
			</div>
			<div class="pms_configure_btn">
				<input class ="pms_configure_save"  type="button" value="保存" onclick="getDataValue();" />
			</div>
		</div>
	</div>
</body>
</html>

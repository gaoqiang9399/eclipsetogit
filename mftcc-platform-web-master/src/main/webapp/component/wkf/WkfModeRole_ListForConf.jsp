<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript" src="${webPath}/component/wkf/js/wkfModeRoleConf.js" ></script>
<link rel="stylesheet" href="${webPath}/component/wkf/css/wkfModeRoleConf.css" />
<script type="text/javascript">
$(function(){
	$.ajax({
		type:"post",
		async:false,
		cache:false,
		url:webPath+"/wkfModeRole/findByVpNoGroupByModeNoAjax",
		dataType:"json",
		data:{wkfVpNo:'${wkfVpNo}'},
		success:function(jsonData){
			if(jsonData.flag=="success"){
				createModeMenu(jsonData.list);
			}else{
				window.top.alert(jsonData.msg,0);
			}
		},
		error:function(){
			alert("error");
		}
	});
	$("#mode-menu-content").mCustomScrollbar({
		advanced : {
			updateOnContentResize : true
		}
	});
	$("#wkf-tab-content").mCustomScrollbar({
		advanced : {
			updateOnContentResize : true
		}
	});
	$("#addModeBtn").bind("click",function(){
		$("#add-mode-modal").modal('show');
	});
	$(".wkf-mode-body-left").height(window.innerHeight);
	$("#wkf-tab-content").height(window.innerHeight-70);
});
</script>
<style type="text/css">
	.wkf-right-body-content.mCustomScrollbar{
	   /*  height: calc(100% - 70px); */
        position: absolute;
	    top: 70px;
	    right: 0px;
	    bottom: 0px;
	    left: 0px;
	}
	.mCSB_scrollTools.mCS-light.mCSB_scrollTools_vertical {
	    width: 8px;
	}
	.wkf-right-body {
	    margin-bottom: 20px;
	}
	.mode-menu-content.mCustomScrollbar{
	   /* height: calc(100% - 40px); */
        position: absolute;
	    top: 40px;
	    bottom: 0px;
	    left: 0px;
	    width: 100%;
	}
	.mode-menu-content ul.mode-menu {
	    padding-bottom: 35px;
	}
</style>
</head>
<body>
	<dhcc:markPoint markPointName="WkfModeRole_List" />
	<div class="wkf-mode-body-left">
		<div class="wkf-left-head"><span>${wkfVpName"/>(<s:property value="wkfVpNo})</span><i id="addModeBtn" class="i i-jia3"></i></div>
		<div id="mode-menu-content" class="mode-menu-content">
			<ul id="mode-menu" class="mode-menu">
			</ul>
		</div>
	</div>
	<div class="wkf-mode-body-right">
	<div class="wkf-right-top">
		<button type="button" id="saveBtn" onclick="saveMenuNo()">保存</button>
	</div>
	<div class="wkf-right-thead">
		<ul>
			<li class="li_td li_td1">勾选</li>
			<li class="li_td li_td2">菜单号（或按钮编号）</li>
			<li class="li_td li_td3">菜单名称（或按钮名称）</li>
			<li class="li_td li_td4">描述</li>
			<li class="li_td li_td5">描述</li>
			<li class="li_td li_td5">类型 1-菜单；0-按钮</li>
		</ul>
	</div>
	<div class="wkf-right-body-content" id="wkf-tab-content">
		<div class="wkf-right-body" id="wkf-tab">
		</div>
	</div>
	</div>
	<input type="hidden" id="wkfVpNo" value="${wkfVpNo"/>}
	<input type="hidden" id="wkfModeNo" value=""/>
	<div class="modal fade" id="add-mode-modal" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">新增模块</h4>
				</div>
				<div class="modal-body" style="text-align: center;">
					<div class="form-group">
						<label>模块编号:</label> <input type="text" id="modeNo" maxlength="10">
					</div>
					<div class="form-group">
						<label>模块描述:</label> <input type="text" id="modeName" maxlength="100">
					</div>
					<input type="hidden" id="comId"/>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="addWkfModeRole();">保存</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

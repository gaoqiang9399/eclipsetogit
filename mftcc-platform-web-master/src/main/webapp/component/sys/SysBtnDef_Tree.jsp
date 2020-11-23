<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>列表</title>
<script src="${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js"
	type="text/javascript"></script>
<script src="${webPath}/component/sys/js/sysBtnDef.js"
	type="text/javascript"></script>
<link rel="stylesheet"
	href="${webPath}/UIplug/zTree/metroStyle/metroStyle.css" />
<link rel="stylesheet"
	href="${webPath}/component/sys/css/sysBtnDef.css" />
<script type="text/javascript">
	/* $(function(){
	 }); */
</script>
</head>
<body>
<dhcc:markPoint markPointName="SysBtnDef_Tree"/>
	<div class="sys-btn-body">
		<div class="sys-top">
			<button type="button" id="refurbish">刷新按钮缓存</button>
		</div>
		<div class="sys-btn-lv lv1">
			<div class="sys-btn-head">
				<h3>组件</h3>
				<i class="i i-jia3" id="add_com"></i>
			</div>
			<div class="sys-btn-tree">
				<ul id="treeLv1" class="ztree"></ul>
			</div>
		</div>
		<div class="sys-btn-lv lv2">
			<div class="sys-btn-head">
				<h3>功能</h3>
				<i class="i i-jia3" id="add_fun"></i>
			</div>
			<div class="sys-btn-tree">
				<ul id="treeLv2" class="ztree"></ul>
			</div>
		</div>
		<div class="sys-btn-lv lv3">
			<div class="sys-btn-head">
				<h3>按钮</h3>
				<i class="i i-jia3" id="add_btn"></i>
			</div>
			<div class="sys-btn-tree">
				<ul id="treeLv3" class="ztree"></ul>
			</div>
		</div>
	</div>
	<div class="modal fade" id="add-com-modal" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">新增组件</h4>
				</div>
				<div class="modal-body" style="text-align: center;">
					<div class="form-group">
						<label>组件名称:</label> <input type="text" id="componentName" maxlength="10">
					</div>
					<div class="form-group">
						<label>组件描述:</label> <input type="text" id="componentDesc" maxlength="40">
					</div>
					<input type="hidden" id="comId"/>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="comSaveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="add-fun-modal" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">新增功能</h4>
				</div>
				<div class="modal-body" style="text-align: center;">
					<div class="form-group">
						<label>功能编号:</label> <input type="text" id="funNo" maxlength="10">
					</div>
					<div class="form-group">
						<label>功能名称:</label> <input type="text" id="funName" maxlength="10">
					</div>
					<input type="hidden" id="funUpId"/>
					<input type="hidden" id="funId"/>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="funSaveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="add-btn-modal" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">新增按钮定义</h4>
				</div>
				<div class="modal-body" style="text-align: center;">
					<div class="form-group">
						<label>按钮编号:</label> <input style="width: 220px;" type="text" id="btnNo" maxlength="50">
					</div>
					<div class="form-group">
						<label>按钮名称:</label> <input style="width: 220px;" type="text" id="btnName"  maxlength="10">
					</div>
					<div class="form-group">
						<label>按钮描述:</label> <input style="width: 220px;" type="text" id="btnDesc"  maxlength="40">
					</div>
					<input type="hidden" id="btnUpId"/>
					<input type="hidden" id="btnId"/>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="btnSaveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	<!-- changeBtn -->
	<div class="modal fade" id="chan-btn-modal" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="false">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">修改链接</h4>
				</div>
				<div class="modal-body" style="text-align: center; max-height: 200px; overflow: auto; position:relative;" id="form-container">
				</div>
				<div class="modal-footer" style="position: relative;">
					<i class="i i-jia3" id="add_url_btn" style="display: inline; position: absolute; left: 15px; top: 22.5px; cursor: pointer; color: #6D96B1;"></i>
					<button type="button" class="btn btn-default" id="deleteBtn">删除</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="linkSaveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

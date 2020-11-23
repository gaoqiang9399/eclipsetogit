<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%
	String cellDatas = (String) request.getAttribute("cellDatas");
	String blockDatas = (String) request.getAttribute("blockDatas");
	String jsonBean = (String) request.getAttribute("jsonBean");
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
	
</script>
<script type="text/javascript"
	src="${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js"></script>
<link rel="stylesheet"
	href="${webPath}/UIplug/zTree/metroStyle/metroStyle.css" />
<script type="text/javascript"
	src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.js"></script>
<link rel="stylesheet"
	href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.css" />
<link rel="stylesheet"
	href="${webPath}/component/doc/css/DocBizSceConfig_main.css" />
<script type="text/javascript"
	src="${webPath}/component/doc/js/DocBizSceConfig_main.js"></script>
<link rel="stylesheet"
	href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" />	
	<script type="text/javascript">
	
	$(function(){
		/* $("#ifMustInput").rcSwitcher({
						height: 18,
						theme: 'lease',
						reverse: true,
						blobOffset: 1,
						onText:'启用',
						offText:'失效'
					}).on( 'change.rcSwitcher', function( e, data ){
						console.log(data.$input[0].checked);
				} );
		$("#ifMustRead").rcSwitcher({
						height: 18,
						theme: 'lease',
						reverse: true,
						blobOffset: 1,
						onText:'启用',
						offText:'失效'
					}).on( 'change.rcSwitcher', function( e, data ){
						console.log(data.$input[0].checked);
				} ); */	
	});
	</script>
	<script type="text/javascript">
		$(function(){
			
			$(".list-group1").mCustomScrollbar({
				advanced:{
					updateOnContentResize:true
				}
			});
			$(".list-group2").mCustomScrollbar({
				advanced:{
					updateOnContentResize:true
				}
			});
			$(".list-group3").mCustomScrollbar({
				advanced:{
					updateOnContentResize:true
				}
			});
			
		});
	</script>
	<style>
.container {
	width: calc(100% -     20px);
	height: calc(100% -     40px);
	padding: 20px;
	padding-bottom: 0;
}

.clearfix {
	height: 100%;
}

.doc-body .column {
	height: 100%;
	padding: 0 10px;
}

.doc-body .column h3 {
	background: none;
	border: 1px solid #ddd;
	border-bottom: none;
	border-top-left-radius: 4px;
	border-top-right-radius: 4px;
	margin: 0;
	padding: 2px 0 0 10px;
}

.doc-body .column .list-group {
	height: calc(100% -     20px);
	background: none;
	border: 1px solid #ddd;
	border-top: none;
	border-bottom-left-radius: 4px;
	border-bottom-right-radius: 4px;
}

.list-group li {
	border-bottom: 1px solid #ddd;
}

.doc-body .column .c {
	height: calc(100% -     20px);
	padding-bottom: 10px;
}

.ztree li {
	line-height: 40px;
	overflow: hidden;
}

.ztree li span {
	color: #666;
	line-height: 40px;
	float: left
}

.ztree li span.button.chk.checkbox_true_full {
	vertical-align: top;
	margin-top: 15px;
	line-height: 30px;
	margin-right: 3px;
}

.ztree li span.button.chk.checkbox_true_full_focus {
	margin-top: 15px;
}

.ztree li span.button.chk.checkbox_false_full {
	vertical-align: top;
	margin-top: 15px;
	line-height: 30px;
}

.ztree li span.button.chk.checkbox_false_full_focus {
	margin-top: 15px;
}

.ztree li span.button.ico_docu {
	background: none;
	display: none;
}

.OverViewModify {
	display: block;
	cursor: pointer;
	float: right;
	width: 12px;
	margin-top: 12px;
	height: 12px;
	vertical-align: baseline;
	background: url(component/doc/images/pencilCross.png) no-repeat 0px
		-12px;
}

.OverViewModify:hover {
	background-position: 0px 0px;
}

.OverViewDelete {
	display: block;
	cursor: pointer;
	float: right;
	width: 12px;
	margin-top: 12px;
	height: 12px;
	margin-left: 10px;
	vertical-align: baseline;
	background: url(component/doc/images/pencilCross.png) no-repeat -12px
		-12px;
	margin-right: 15px;
}

.OverViewDelete:hover {
	background-position: -12px 0px;
}

.fa-plus-square-o {
	margin-right: 14px;
}

.ztree li a.curSelectedNode {
	background: none;
}

.ztree li a.curSelectedNode .node_name {
	color: #32b5cb;
}

.ztree li a {
	height: auto;
	float: left;
}

.ztree li a.curSelectedNode {
	height: auto;
}

.modal-content {
	font-size: 12px;
}

.modal-content h4 {
	font-size: 12px;
}

.modal-header {
	padding: 10px;
}

.modal-content label {
	font-weight: 400;
}

.form-group {
	width: 240px;
	margin-left: auto;
	margin-right: auto;
	text-align: left;
}

.modal-footer {
	padding: 0;
	padding-bottom: 10px;
}

.modal-body {
	padding-bottom: 0;
}

.btn-primary {
	background-color: #32b5cb;
	border: medium none;
	border-radius: 2px;
	color: #fff;
	height: 25px;
	margin: 9px 5px 0;
	padding: 0 10px;
	width: auto;
	display: block;
	float: right;
}

/* .btn-primary:hover { */
/* 	background-color: #54aced; */
/* } */

/* .btn-primary:focus { */
/* 	background-color: #009ce2; */
/* } */
.btn-default {
	border-radius: 2px;
	height: 25px;
	margin: 9px 5px 0;
	padding: 0 10px;
	width: auto;
	display: block;
	float: right;
}

.btn-default:hover {
	color: #333;
	background-color: #fff;
	border-color: #ccc;
}

.list-group1 .ztree li span.button.chk {
	margin-left: 20px;
}

.col-md-3 {
	width: 22.5%;
}

.containerArrow {
	width: 3%;
	float: left;
	height: 50%;
	position: relative;
}

.containerArrow .i-jiantou2 {
	font-size: 22px;
	width: 100%;
	text-align: center;
	display: block;
	position: absolute;
	bottom: 0;
	color: #67CCDE;
}

.containerArrow .i-jiantou2:hover {
	color: #018FA7;
}

.ztree li a.level0 .i-jia3,.ztree li .i-xiugaia {
	display: block;
	font-size: 16px;
	float: right;
	color: #c9c9c9;
	margin-top: 16px;
}

.ztree li ul {
	background: #ffffff;
}
</style>
</head>
<body class="body_bg" style="height:98%;">
	<div class="container">
		<div class="row clearfix doc-body">
			<div class="col-md-3 column">
				<h3>文档业务场景</h3>
				<div class="list-group list-group2">
					<ul id="ScenByTypeTree" class="zTree"></ul>
				</div>
			</div>
			<div class="containerArrow">
				<i class="i i-jiantou2"></i>
			</div>
			<div class="col-md-3 column">
				<h3>文档类别</h3>
				<div class="list-group list-group1">
					<ul id="SceDocTypeRelsTree" class="zTree"></ul>
				</div>
			</div>
			<div class="containerArrow">
				<i class="i i-jiantou2"></i>
			</div>
			<div class="col-md-3 column">
				<h3>维度划分</h3>
				<div class="list-group">
					<ul id="DocDimmsTree" class="zTree"></ul>
				</div>
			</div>
			<div class="containerArrow">
				<i class="i i-jiantou2"></i>
			</div>
			<div class="col-md-3 column">
				<h3>
					文档细分类型
					<!--  <i id="switchEdit" role="button" 
					 class="fa  fa-plus-square-o"
						title="新增"></i> -->
				</h3>
				<div class="list-group list-group3">
					<ul id="SplitDocsTree" class="zTree SplitDocsTree"></ul>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="SceDocTypeRelsEdit" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">新增文档细分类型</h4>
				</div>
				<div class="modal-body" style="text-align: center;">
					<div class="form-group">
						<label>类型名称:</label> <input type="text" id="SceDocTypeRelsName" >
					</div>
				</div>	
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-primary" id="SceDocTypeRelsSaveBtn">保存</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="SplitDocsEdit" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">新增文档细分类型</h4>
				</div>
				<div class="modal-body" style="text-align: center;">
					<div class="form-group">
						<label>类型名称:</label> <input type="text" id="docSplitName" readonly="readonly">
					</div>
					<!-- <div class="form-group">
						<label>文档大小:</label> <input type="text" id="docSizeLimit">
					</div> -->
					<div class="form-group">
						<label>表单编号:</label> <input type="text" id="formId">
					</div>
					<div class="form-group">
						<label>是否必输:</label> <input value="1"  type="checkbox" id="ifMustInput" name="ifMustInput"/>
					<!-- 	<label>是否必读:</label> <input value="1"  type="checkbox" id="ifMustRead" name="ifMustRead"/> -->
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="splitSaveBtn">保存</button>
				</div>
			</div>

		</div>

	</div>

</body>
</html>
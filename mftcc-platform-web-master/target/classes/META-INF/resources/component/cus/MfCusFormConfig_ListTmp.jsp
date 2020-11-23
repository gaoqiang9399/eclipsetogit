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
<%-- <script type="text/javascript"
	src="${webPath}/component/doc/js/DocBizSceConfig_main.js"></script> --%>
<link rel="stylesheet"
	href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" />	

	<script type="text/javascript">
	var ScenByType_setting = {
			view: {
				showLine: false,
				addDiyDom: addBaseFormDiyDom
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			callback: {
				onClick: selectThisNode
			},
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "p", "N": "s" }
			}
		};
	 	var formType = '${optCode}';
	 	var selFormTree;  //右边树结构
	 	var unSelFormTree;  //
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
			$.ajax({
				type : 'post',
				url:webPath+"/mfCusFormConfig/getAllFormJsonAjax?formType="+formType,
				dataType : 'json',
				error : function(xmlhq, ts, err) {
					console.log(xmlhq + "||" + ts + "||" + err);
				},
				success : function(data) {
					var ScenByType_zNodes = data.sel;
					selFormTree = $.fn.zTree.init($("#ScenByTypeTree"), ScenByType_setting,ScenByType_zNodes);
					unSelFormTree = $.fn.zTree.init($("#SceDocTypeRelsTree"), ScenByType_setting,data.unsel);
					$("#ScenByTypeTree li").each(function(){
						$this = $(this);
						var treeNode = selFormTree.getNodeByTId($this.attr("id"));
						var htmlStr = '<a href="javaScript:void(0);" style="text-decoration: none;float:right;margin-right: 20px;" onclick="formDesign(\''+treeNode.id+'\',\'1\');">新增表单</a>';
						if(treeNode.showType == "1"){
							htmlStr = htmlStr + '<a href="javaScript:void(0);" style="text-decoration: none;float:right;margin-right: 20px;" onclick="formDesign(\''+treeNode.id+'\',\'2\');">展示表单</a>';

						}
						$this.append(htmlStr);
					});
					
				}
			});
		});
		function removeForm(){
			var treeNodes = selFormTree.getCheckedNodes(true);
			if(treeNodes.length == 0){
				return false;
			}
			var dataParmList = [];
			for (var i=0; i < treeNodes.length; i++) {
				if(treeNodes[i].isBase ==	"0"){//非基础表单才可以不使用
					var entity = {};
					entity.id = treeNodes[i].id;
					entity.useFlag = "0";
					dataParmList.push(entity);
				}
			}
			$.ajax({
				url:webPath+"/mfCusFormConfig/saveFormConfigAjax",
				data:{ajaxData:JSON.stringify(dataParmList)},
				type:"POST",
				dataType:"json",
				success:function(data){
					if(data.flag == "success"){
						dataParmList = [];
						//已配置表单列表中删除移走的非基础表单，基础表单不能移走
						for (var i=0; i < treeNodes.length; i++) {
							treeNodes[i].checked=false;
							if(treeNodes[i].isBase =="0"){
								dataParmList.push(treeNodes[i]);
								selFormTree.removeNode(treeNodes[i]);
							}
						}
						unSelFormTree.addNodes(null, dataParmList);
						unSelFormTree.cancelSelectedNode();
						selFormTree.cancelSelectedNode();
					}else{
						alert("修改失败",0);
					}
				},error:function(){
					alert("修改失败",0);
				}
			});
			
		};
		function addForm(){
			var dataParmList = [];
			var treeNodes = unSelFormTree.getCheckedNodes(true);
			 for (var i=0; i < treeNodes.length; i++) {
				var entity = {};
				entity.id = treeNodes[i].id;
				entity.useFlag = "1";
				/* entity.showType = treeNodes[i].showType;
				entity.showModel = treeNodes[i].showModel;
				entity.showModelDef = treeNodes[i].showModelDef;
				entity.addModel = treeNodes[i].addModel;
				entity.addModelDef = treeNodes[i].addModelDef;
				entity.formType = treeNodes[i].formType; */
				dataParmList.push(entity);
			} 
			 $.ajax({
					url:webPath+"/mfCusFormConfig/saveFormConfigAjax",
					data:{ajaxData:JSON.stringify(dataParmList)},
					type:"POST",
					dataType:"json",
					success:function(data){
						if(data.flag == "success"){
							for (var i=0; i < treeNodes.length; i++) {
								treeNodes[i].checked=false;
								unSelFormTree.removeNode(treeNodes[i]);
							}
							var newtreeNodes = selFormTree.addNodes(null, treeNodes);
							$.each(newtreeNodes,function(i,treeNode){
								var htmlStr = '<a href="javaScript:void(0);" style="text-decoration: none;float:right;margin-right: 20px;" onclick="formDesign(\''+treeNode.id+'\',\'1\');">新增表单</a>';
								if(treeNode.showType == "1"){
									htmlStr = htmlStr + '<a href="javaScript:void(0);" style="text-decoration: none;float:right;margin-right: 20px;" onclick="formDesign(\''+treeNode.id+'\',\'2\');">展示表单</a>';

								}
								var tId = "#"+ treeNode.tId;
								$(tId).append(htmlStr);
							});
							unSelFormTree.cancelSelectedNode();
							selFormTree.cancelSelectedNode();
						}else{
							alert("修改失败",0);
						}
					},error:function(){
						alert("修改失败",0);
					}
				});
			
		};
		
		function back(){
			window.location.href = "/factor/mfCusFormConfig/getAllList";
		};
		
		function formDesign(id,type){
			$.ajax({
				url:webPath+"/mfCusFormConfig/checkAndCreateFormAjax",
				data:{id:id,optCode:type},
				type:"POST",
				dataType:"json",
				success:function(data){
					if(data.flag == "success"){
						var url = '<%=webPath %>'+'/tech/dragDesginer/openForm.action?formId='+data.formId;
						window.open(url,'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
					}else{
						alert("获取表单文件出错",0);
					}
				},error:function(){
					alert("请求出错",0);
				}
			});
			
		};
		function resetForm(){
			var selectNodes = selFormTree.getCheckedNodes(true);
			if(selectNodes.length == 0){
				alert("请选择已配置表单",1);
				return;
			}			
			var ids='';
			$.each(selectNodes,function(i,treeNode){
				if( i == selectNodes.length-1){
					ids = ids + treeNode.id;
				}else{
					ids = ids + treeNode.id + ',';
				}
			});
			$.ajax({
				url:webPath+"/mfCusFormConfig/resetFormAjax",
				data:{id:ids},
				dataType:'json',
				type:'POST',
				success:function(data){
					if(data.flag == "success"){
						alert("重置成功",1);
					}else{
						alert("重置失败",0);
					}
				},error:function(){
					alert("重置失败",0);
				}
			});
		};
		function selectThisNode(event, treeId, treeNode){
			if(treeNode.checked == false){
				if(treeId=="ScenByTypeTree"){
					selFormTree.checkNode(treeNode, true);
				}else{
					unSelFormTree.checkNode(treeNode, true);
				}
			}else{
				if(treeId=="ScenByTypeTree"){
					selFormTree.checkNode(treeNode, false);
				}else{
					unSelFormTree.checkNode(treeNode, false);
				}
			}
		};
		
		function selectAll(obj,type) {
			var spanCheckBox = $(obj).parents(".checkbox_full").find(".button");
			var checkbox_false_full = spanCheckBox.hasClass("checkbox_false_full");
			if(checkbox_false_full){
				spanCheckBox.removeClass("checkbox_false_full");
				spanCheckBox.addClass("checkbox_true_full");
				if (type == "1") {
					unSelFormTree.checkAllNodes(true);
				} else {
					selFormTree.checkAllNodes(true);
				}
			}else{
				spanCheckBox.removeClass("checkbox_true_full");
				spanCheckBox.addClass("checkbox_false_full");
				if (type == "1") {
					unSelFormTree.checkAllNodes(false);
				} else {
					selFormTree.checkAllNodes(false);
				}
			}
			
		};
		function addBaseFormDiyDom(treeId, treeNode){
			if(treeNode.isBase == "1"){
				var aObj = $("#" + treeNode.tId + "_a");
				if ($("#diyBtn_"+treeNode.id).length>0) return;
				var editStr = "<span id='diyBtn_space_" +treeNode.id+ "'  style='font-size: 14px;color: #fc2736;'>*</span>";
				aObj.append(editStr);
			}
			
		};
	</script>
	<style>
.container {
	width: calc(100% -         20px);
	height: calc(100% -         40px);
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
	height: calc(100% -         20px);
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
	height: calc(100% -         20px);
	padding-bottom: 10px;
}

.ztree {
	padding-right: 10px;
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

.form-group {
	width: 240px;
	margin-left: auto;
	margin-right: auto;
	text-align: left;
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
	height: 55%;
	position: relative;
}

.containerArrow .i-jiantou3 {
	font-size: 22px;
	width: 100%;
	text-align: center;
	display: block;
	position: absolute;
	bottom: 0;
	color: #67CCDE;
	cursor: pointer;
}

.containerArrow .i-jiantou3:hover {
	color: #018FA7;
}

.containerArrow .i-jiantou2 {
	font-size: 22px;
	width: 100%;
	text-align: center;
	display: block;
	position: absolute;
	bottom: 100px;
	color: #67CCDE;
	cursor: pointer;
}

.containerArrow .i-jiantou2:hover {
	color: #018FA7;
}

.container span.button {
	line-height: 0;
	margin: 0;
	padding: 0;
	width: 21px;
	height: 21px;
	display: inline-block;
	vertical-align: middle;
	border: 0 none;
	cursor: pointer;
	outline: none;
	background-color: transparent;
	background-repeat: no-repeat;
	background-attachment: scroll;
	background-image: url("UIplug/zTree/metroStyle/img/metro.png");
}

.container span.button.checkbox_false_full {
	background-position: -1px -1px;
}

.container span.button.checkbox_true_full {
	background-position: -22px -1px;
}

.container .checkbox_full {
	font-size: 12px;
	color: #000;
	margin-left: 15px;
}
</style>
</head>
<body style="height:98%;">
	<div class="container">
		
		<div class="row clearfix doc-body">
			<div class="color_theme" style="padding-left: 20%;height: 30px;">${optName }表单配置(带*为基础表单)
<!-- 				<i class="i i-back" style="margin-left: 50px;cursor: pointer;" onclick="back();"></i>
 -->			</div>
			<div style="height: 80%">
			<div class="col-md-2 column"></div>
			<div class="col-md-4 column">
				<h3>
				<span>未配置表单</span>
<!-- 				<span onclick = "selectAll(1)">全选</span> -->
				<span class="checkbox_full" >
					<span  class="button  checkbox_false_full" onclick = "selectAll(this,1)"></span>
					<lable onclick = "selectAll(this,1)">全选</lable>
				</span>
				</h3>
				<div class="list-group list-group1">
					<ul id="SceDocTypeRelsTree" class="zTree"></ul>
				</div>
			</div>
			<div class="containerArrow">
				<i class="i i-jiantou3" onclick="removeForm();"></i>
				<i class="i i-jiantou2" onclick="addForm();"></i>
			</div>
			
			<div class="col-md-4 column">
				<h3>
				<span>已配置表单</span>
<!-- 				<span onclick = "selectAll(2)">全选</span> -->
				<span class="checkbox_full">
					<span  class="button  checkbox_false_full" onclick = "selectAll(this,2)"></span>
					<lable onclick = "selectAll(this,2)">全选</lable>
				</span>
				</h3>
				<div class="list-group list-group2">
					<ul id="ScenByTypeTree" class="zTree"></ul>
				</div>
			</div>
			</div>
			<div class="formRowCenter" style="margin-top: 30px;clear: both;">
<!-- 				<input type="button" value="保存" onclick="saveFormConfig();">
 -->				
 				<input type="button" value="重置表单" onclick="resetForm();">
				<input type="button" value="返回" onclick="back();" class="myclose">
			</div>
			
		</div>
	</div>
	
	

</body>
</html>
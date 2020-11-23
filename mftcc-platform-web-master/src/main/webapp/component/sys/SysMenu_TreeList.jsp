<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/pub.jsp"%>
<%@ page import="app.component.sys.entity.SysMenu" %>
<!DOCTYPE html>
<html>
	<head>
		<title>菜单列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${webPath}/creditapp/sys/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<style type="text/css">
	.contextMenu{
		visibility:hidden;
		z-index: 500; position: absolute; display: block; top: 141px; left: 196px;
	}
	.contextMenu ul{
		border-bottom: #999 1px solid; border-left: #999 1px solid; padding-bottom: 1px; background-color: #fff; list-style-type: none; margin: 0px; padding-left: 1px; width: 120px; border-top: #999 1px solid; border-right: #999 1px solid; padding-top: 1px;
	}
	.contextMenu ul li{
		border-bottom: #fff 1px solid; border-left: #fff 1px solid; padding-bottom: 3px; margin: 0px 0px 0px 0px; padding-left: -20px; display: block; color: #000; border-top: #fff 1px solid; cursor: pointer; border-right: #fff 1px solid; padding-top: 3px;
	}
	.contextMenu ul li:hover{
		background-color:#D6E7FF;
	}
	
	</style>
	<script type="text/javascript" src="${webPath}/creditapp/sys/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${webPath}/creditapp/sys/js/jquery.ztree.excheck-3.5.js"></script>
	<script type="text/javascript" src="${webPath}/creditapp/sys/js/jquery.ztree.exedit-3.5.js"></script>
	
	</head>
<body class="body_bg">
	<div class="right_bg">
		<div class="right_w">
			<div class="from_bg">
				<div class="right_v">
					<div class="contextMenu" id="rMenu1">
						<ul>
							<li id="m_add" onclick="addTreeNode();"><img src="${webPath}/creditapp/sys/images/folder_add.png" />新增菜单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
							<li id="m_edit" onclick="editTreeNode();"><img src="${webPath}/creditapp/sys/images/folder_edit.png" />编辑菜单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
							<li id="m_del" onclick="removeTreeNode();"><img src="${webPath}/creditapp/sys/images/folder_delete.png" />删除菜单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
						</ul>
					</div>
					<div class="contextMenu" id="rMenu2">
						<ul>
							<li id="m_edit" onclick="editTreeNode();"><img src="${webPath}/creditapp/sys/images/page_edit.png" />编辑菜单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
							<li id="m_editbtn" onclick="editbtnTreeNode();"><img src="${webPath}/creditapp/sys/images/page_edit.png" />配置菜单按钮</li>
							<li id="m_del" onclick="removeTreeNode();"><img src="${webPath}/creditapp/sys/images/page_delete.png" />删除菜单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
						</ul>
					</div>
					<table width="100%" height="100%" border="0" align="center" vAlign="top"
						class="TDstyle01">
						<tr>
							<td valign="top" align="left" width="40%" class="TDstyle02">

								<ul>
									<div class="zTreeDemoBackground left">
										<ul id="treeDemo" class="ztree"></ul>
									</div>
								</ul></td>
								
								<td  align="left" width="60%" class="TDstyle02" vAlign="top" style="vertical-align: top;text-align: right;">
			<div id="menuDiv" style="display:none;">
				<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0" class="from_w">
					<tr><td  colspan="2"align="center" class="bartitle" >修改菜单信息</td></tr>
					<tr><td align="right" width="30%" class="tdlable">菜单编号:</td><td width="70%" align="left" class="tdvalue"><input type="text" name="menuNo" id="menuNo" size="20" value="" readonly="readonly"/></td></tr>
					<tr><td align="right" class="tdlable">菜单名称:</td><td align="left" class="tdvalue"><input type="text" name="menuName" id="menuName"  size="20"  value=""/></td></tr>
					<tr><td align="right" class="tdlable">父菜单编号:</td><td align="left" class="tdvalue"><input type="text" name="menuParent" id="menuParent"  size="20"  value="" readonly="readonly"/></td></tr>
					<tr><td align="right" class="tdlable">菜单级别:</td><td align="left" class="tdvalue"><input type="text" name="menuLvl" id="menuLvl"  size="20" maxlength="1" value="" readonly="readonly"/></td></tr>
					<tr id="url"><td align="right" class="tdlable">菜单URL:</td><td align="left" class="tdvalue"><input type="text" name="menuUrl" id="menuUrl"  size="55"  value=""/></td></tr>
					<tr id="shape"><td align="right" class="tdlable">菜单形状:</td><td align="left" class="tdvalue"><select id="menuShape">
																											<option value=""></option>
																											<option value="menushape1">长方形</option>
																											<option value="menushape2">正方形</option>
																										</select></td></tr>
					<tr id="style"><td align="right" class="tdlable">菜单颜色:</td><td align="left" class="tdvalue"><select id="menuStyle">
																											<option value=""></option>
																											<option value="mc_qgray">浅灰色</option>
																											<option value="mc_tblue">天空蓝</option>
																											<option value="mc_mblue">明媚蓝 </option>
																											<option value="mc_jyellow">橘黄色 </option>
																											<option value="mc_qred">浅红色</option>
																											<option value="mc_sblue">深蓝色</option>
																											<option value="mc_dred">淡雅红</option>
																										</select></td></tr>
					<tr id="tain"><td align="right" class="tdlable">特殊样式:</td><td align="left" class="tdvalue"><input type="text" name="menuTain" id="menuTain"  size="20"  value=""/></td></tr>																		
																										
																										</td></tr>
						<p><span id="spanMsg" style="color: red"></span></p>
				</table>
					  <div class="from_btn">
						<input type="button" name="modbtn" id="modbtn" value="保存修改" onclick="updateMenu();" class="button3"/>
						<input type="button" name="delbtn" id="delbtn" value="删除" onclick="removeTreeNode();" class="button3"/>
					  </div>
			</div>
			<div id="menuAddDiv" style="display:none;">
				<table width="100%" border="0" align="center"  cellpadding="0" cellspacing="0" class="from_w">
					<tr><td  colspan="2"align="center" class="bartitle" >新增菜单信息</td></tr>
					<tr><td width="30%" align="right" class="tdlable">菜单编号:</td><td width="70%" class="tdvalue" align="left"><input type="text" name="menuAddNo" id="menuAddNo" size="20" value=""/></td></tr>
					<tr><td align="right" class="tdlable">菜单名称:</td><td align="left" class="tdvalue"><input type="text" name="menuAddName" id="menuAddName"  size="20"  value=""/></td></tr>
					<tr><td align="right" class="tdlable">父菜单编号:</td><td align="left" class="tdvalue"><input type="text" name="menuAddParent" id="menuAddParent"  size="20"  value="" readonly="readonly"/></td></tr>
					<tr><td align="right" class="tdlable">菜单级别:</td><td align="left" class="tdvalue"><input type="text" name="menuAddLvl" id="menuAddLvl"  size="20" maxlength="1" value="" readonly="readonly"/></td></tr>
					<tr id="url1"><td align="right" class="tdlable">菜单URL:</td><td align="left" class="tdvalue"><input type="text" name="menuAddUrl" id="menuAddUrl"  size="55"  value=""/></td></tr>
					<tr id="shape1"><td align="right" class="tdlable">菜单形状:</td><td align="left" class="tdvalue"><select id="menuAddShape">
																											<option value=""></option>
																											<option value="menushape1">长方形</option>
																											<option value="menushape2">正方形</option>
																										</select></td></tr>
					<tr id="style1"><td align="right" class="tdlable">菜单颜色:</td><td align="left" class="tdvalue"><select id="menuAddStyle">
																											<option value=""></option>
																											<option value="menucolor1">浅红色</option>
																											<option value="menucolor2">天空蓝</option>
																											<option value="menucolor3">浅黄色</option>
																											<option value="menucolor4">深灰色</option>
																											<option value="menucolor5">明媚蓝</option>
																											<option value="menucolor6">淡雅黑</option>
																										</select></td></tr>
					<tr id="tain1"><td align="right" class="tdlable">特殊样式:</td><td align="left" class="tdvalue"><input type="text" name="menuAddTain" id="menuAddTain"  size="20"  value=""/></td></tr>
						<p><span id="spanMsg" style="color: red"></span></p>
				</table>
					<div class="from_btn">
						<input type="button" name="addbtn" id="addbtn" value="保存新增" onclick="insertMenu();" class="button3"/>
					</div>
			</div>
		</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

<SCRIPT type="text/javascript">
		var zTree, rMenu1, rMenu2;
		var setting = {
				async : {
					autoParam:["id"],
					url:webPath+"/sysMenu/getRootMenu",
		            enable:false

				},
			edit: {
				drag: {
					autoExpandTrigger: true,
					prev: dropPrev,
					inner: dropInner,
					next: dropNext
				},
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDrag,
				beforeDrop: beforeDrop,
				beforeDragOpen: beforeDragOpen,
				onDrag: onDrag,
				onDrop: onDrop,
				onRightClick: OnRightClick,
				onExpand: onExpand
			}
		};
		
		function OnRightClick(event, treeId, treeNode) {
			if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
				zTree.cancelSelectedNode();
			} else if (treeNode && !treeNode.noR) {
				zTree.selectNode(treeNode);
				showRMenu(treeNode.lvl, event.clientX, event.clientY+document.body.scrollTop);
			}
		}
		
		function showRMenu(lvl, x, y) {
			if (lvl =="0") {
				$("#m_edit").hide();
				$("#m_del").hide();
			} else {
				$("#m_edit").show();
				$("#m_del").show();
			}
			 if (lvl!="3") {
				$("#rMenu1 ul").show();
				
				rMenu1.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
			} else {
				$("#rMenu2 ul").show();
				rMenu2.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
			}

			$("body").bind("mousedown", onBodyMouseDown);
		}
		
		function hideRMenu() {
			if (rMenu1) rMenu1.css({"visibility": "hidden"});
			if (rMenu2) rMenu2.css({"visibility": "hidden"});
			$("body").unbind("mousedown", onBodyMouseDown);
		}
		function onBodyMouseDown(event){
			if (!(event.target.id == "rMenu1" || $(event.target).parents("#rMenu1").length>0)) {
				rMenu1.css({"visibility" : "hidden"});
			}
			if (!(event.target.id == "rMenu2" || $(event.target).parents("#rMenu2").length>0)) {
				rMenu2.css({"visibility" : "hidden"});
			}
		}
		
		function dropPrev(treeId, nodes, targetNode) {
			var pNode = targetNode.getParentNode();
			if (pNode && pNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					var curPNode = curDragNodes[i].getParentNode();
					if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}
		function dropInner(treeId, nodes, targetNode) {
			if (targetNode && targetNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					if (!targetNode && curDragNodes[i].dropRoot === false) {
						return false;
					} else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}
		function dropNext(treeId, nodes, targetNode) {
			var pNode = targetNode.getParentNode();
			if (pNode && pNode.dropInner === false) {
				return false;
			} else {
				for (var i=0,l=curDragNodes.length; i<l; i++) {
					var curPNode = curDragNodes[i].getParentNode();
					if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
						return false;
					}
				}
			}
			return true;
		}

		var log, className = "dark", curDragNodes, autoExpandNode;
		function beforeDrag(treeId, treeNodes) {
			className = (className === "dark" ? "":"dark");
			for (var i=0,l=treeNodes.length; i<l; i++) {
				if (treeNodes[i].drag === false) {
					curDragNodes = null;
					return false;
				} else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
					curDragNodes = null;
					return false;
				}
			}
			curDragNodes = treeNodes;
			return true;
		}
		function beforeDragOpen(treeId, treeNode) {
			autoExpandNode = treeNode;
			return true;
		}
		function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
			var node = treeNodes[0];
			if(!node || !targetNode){
				return false;
			}
			var nlvl = node.lvl;
			var tlvl = targetNode.lvl;
			if(nlvl == 3 && tlvl == 2){
				if(confirm("确定把["+node.text+"]移动到["+targetNode.text+"]下?")){
					return true;
				}
				return false;
			}
			alert("错误!只允许移动三级菜单到二级菜单下!");
			return false;
		}
		function onDrag(event, treeId, treeNodes) {
			className = (className === "dark" ? "":"dark");
		}
		function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
			var node = treeNodes[0];
			if(!node || !targetNode){
				return;
			}
			$.get(webPath+"/sysMenu/moveMenu",{parentMenuNo:targetNode.id,menuNo:node.id,rdm:Math.random()},function(data){
				if(data=="success"){
					alert("菜单移动成功!");
				} else {
					alert("菜单移动提交数据失败!");
				}
			});
		}
		function onExpand(event, treeId, treeNode) {
			if (treeNode === autoExpandNode) {
				className = (className === "dark" ? "":"dark");
			}
		}

		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}
		
	
		
		function addTreeNode(){
			hideRMenu();
			var node = zTree.getSelectedNodes()[0];
			if(node.lvl!='2'){
				$("#url1").hide();
				$("#shape1").hide();
				$("#style1").hide();
				$("#tain1").hide();
				}
			else{
				$("#url1").show();
				$("#shape1").show();
				$("#style1").show();
				$("#tain1").show();
				}
			$("#menuDiv").css("display","none");
			$("#menuAddDiv").css("display","block");
			$("#menuAddParent").val(node.id);
			$("#menuAddLvl").val(Number(node.lvl)+1);
			
		}
		function editTreeNode(){
			hideRMenu();
			var node = zTree.getSelectedNodes()[0];
			if(node.lvl!='3'){
				$("#url").hide();
				$("#shape").hide();
				$("#style").hide();
				$("#tain").hide();
				}
			else{
				$("#url").show();
				$("#shape").show();
				$("#style").show();
				$("#tain").show();
				}
			$("#menuAddDiv").css("display","none");
			$("#menuDiv").css("display","block");
			$("#menuNo").val(node.id);
			$("#menuName").val(node.text);
			$("#menuParent").val(node.parent);
			$("#menuLvl").val(Number(node.lvl));
			if(node.shape!='null')			     
			$("#menuShape").val(node.shape);
			else $("#menuShape").val("");
			if(node.style!='null')
				$("#menuStyle").val(node.style);
			else $("#menuStyle").val("");
			var url = node.url=="null"?"":node.url;
			$("#menuUrl").val(url);
			if(node.tain!='null')		
			$("#menuTain").val(node.tain);
			else $("#menuTain").val("");
			
		}
		function removeTreeNode(){
			hideRMenu();
			var node = zTree.getSelectedNodes()[0];
			if(!node){
				alert("错误:节点不存在,可能已被删除!");
				return;
			}
			if(confirm("确定删除菜单["+node.text+"]?")){
				var url = webPath+"/sysMenu/deleteMenu";
				$.post(url,{tcode:node.id,lvl:node.lvl,rdm:Math.random()},function(data){
					if($.trim(data)!="success"){
						zTree.removeNode(node);
						$("#menuDiv").css("display","none");
						alert(top.getMessage("SUCCEED_DELETE"));
					}else{
						alert(top.getMessage("FAILED_DELETE"));
					}
				});
			 }
			
		}
		function editbtnTreeNode(){
			hideRMenu();
			var node = zTree.getSelectedNodes()[0];
			var url = webPath+"/sysButton/findAllByMenu?menuNo=" + node.id;
			 window.showModalDialog(
					    url,window,"dialogWidth=900px;dialogHeight=400px;resizable=no;scrollbars=no;status:yes;help:no;");
			
		}
		function updateMenu(){
			var node = zTree.getSelectedNodes()[0];
			var tcode = $("#menuNo").val();
			var tname = $("#menuName").val();
			var pcode = $("#menuParent").val();
			var lvl = $("#menuLvl").val();
			var url = $("#menuUrl").val();
			var shape = $("#menuShape").val();
			var style = $("#menuStyle").val();
			var tain = $("#menuTain").val();
			var menuStats = 1;
			node.id=tcode;
			node.text=tname;
			node.parent=pcode;
			node.url=url;
			node.lvl=lvl;
			node.shape=shape;
			node.style=style;
			node.tain=tain;
			if(tname=="" || tcode=="" || pcode=="" || lvl==""){
				alert(top.getMessage("NOT_FORM_EMPTY", "菜单要素"));
				return;
			}
			var reg = /[\d]{1}/;
			if(!reg.test(lvl)){
				alert("请正确输入菜单等级!");
				return;
			}
			$.post(webPath+"/sysMenu/updateMenu",{tcode:tcode,tname:encodeURI(tname),shape:shape,style:style,tain:tain,pcode:pcode,lvl:lvl,url:url,menuStats:menuStats,rdm:Math.random()},function(data){
				if($.trim(data)!="success"){
					alert($.trim(data));
					return;
				}else{
					var node = zTree.getSelectedNodes()[0];
					var newNode = {'tId':node.tId,'checked':true,'text':tname,'shape':shape,'style':style,'tain':tain,'id':tcode,'url':url,'stats':'1','lvl':lvl,'parent':pcode,'children':[]};
					zTree.updateNode(newNode,null);
					alert("修改菜单成功!");
				}
			});
		}
		function insertMenu(){
			var tcode = $("#menuAddNo").val();
			var tname = $("#menuAddName").val();
			var pcode = $("#menuAddParent").val();
			var lvl = $("#menuAddLvl").val();
			var url = $("#menuAddUrl").val();
			var shape = $("#menuAddShape").val();
			var style = $("#menuAddStyle").val();
			var tain = $("#menuAddTain").val();
			var menuStats = 1;
			if(tname=="" || tcode=="" || pcode=="" || lvl==""){
				alert("菜单要素不能为空!");
				return;
			}
			var reg = /[\d]{1}/;
			if(!reg.test(lvl)){
				alert("请正确输入菜单等级!");
				return;
			}
			$.post(webPath+"/sysMenu/insertMenu",{tcode:tcode,tname:encodeURI(tname),shape:shape,style:style,tain:tain,pcode:pcode,lvl:lvl,url:url,menuStats:menuStats,rdm:Math.random()},function(data){
				if($.trim(data)!="success"){
					alert($.trim(data));
					return;
				}else{
					alert("新增菜单成功!");
					var parentNode = zTree.getSelectedNodes()[0];
					var newNode = {'checked':true,'text':tname,'shape':shape,'style':style,'tain':tain,'id':tcode,'url':url,'stats':'1','lvl':lvl,'parent':pcode,'children':[]};
					zTree.addNodes(parentNode, newNode, false);
					
				}
			});
		}
		
		
			
		$(document).ready(function(){
			var zNodes= <%=(String)request.getAttribute("menuStr")%>;
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			zTree = $.fn.zTree.getZTreeObj("treeDemo");
			rMenu1 = $("#rMenu1");
			rMenu2 = $("#rMenu2");
		});
	</SCRIPT>
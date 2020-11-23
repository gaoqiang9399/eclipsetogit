<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/message.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<link rel="stylesheet" href="${webPath}/component/pms/css/zTreeStyle.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/zTree/jquery.ztree.all.js"></script>
		<%--滚动条js 和鼠标滚动事件js--%>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css">
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
		<link rel="stylesheet" href="${webPath}/component/pms/css/pms_viewpoint.css" />
		<SCRIPT type="text/javascript">
		var curMenu = null, zTree_Menu = null,zNodes;
		var viewpointNo,viewpointName,treeNodeLeft;
		var setting = {
			view: {
				showLine: true,
				showIcon: false ,
				selectedMulti: false,
				dblClickExpand: false
			},
			edit: {
				drag: {
					autoExpandTrigger: true,
					prev: dropPrev,
					inner: false,
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
				onDrop: onDrop,
				beforeClick: beforeClick,
				onClick: zTreeOnClick
			}
		};
		
		function beforeClick(treeId, treeNode) {
			if(treeNode.tId.indexOf("treeLeft") >= 0 ){  
				var childrenzNodes;
				treeNodeLeft = treeNode;
				viewpointNo = treeNode.id;
				viewpointName = treeNode.name;
				$("#treeLeft a").removeClass("content-left-active");
				$("#"+treeNode.tId+"_a").addClass("content-left-active");
				var treeCenter = $("#treeCenter");
				var flag = null;
				$(".content-center").hide();
				/*$.ajax({
					url:webPath+"/pmsViewpoint/getChildrens",
					type : "post",
					cache : false,
					async : false,
					dataType : "json",
					data:{ajaxData:JSON.stringify(treeNode)},
					success : function(data) {
						childrenzNodes = data;
					}
				});*/
                childrenzNodes = treeNode.children;
				$.fn.zTree.init(treeCenter, setting,childrenzNodes);
				prodInput(flag,treeNode);
				setTimeout(function(){
					$(".content-center").show();
            	}, 500);
			    return false;
			}
			/* else{
				var zTree = $.fn.zTree.getZTreeObj("treeCenter");
				zTree.expandNode(treeNode);
				return true;
			} */
		}
		function zTreeOnClick(event, treeId, treeNode){
			if(event.target.tagName=="INPUT"){
				$(event.target).focus();
				return;
			}
			var node = $("#" + treeNode.tId+"_a");
			var flag = false;
			node.find("label.checkbox").each(function(){
				var checkboxOffset = this.getBoundingClientRect();
		        var width = 60;
		        var height = 24;
		        event = event||window.event; 
		        var x=event.clientX,
		        	y=event.clientY;
		        if((x>checkboxOffset.left&&x<(checkboxOffset.left+width))&&(y>checkboxOffset.top&&y<(checkboxOffset.top+height))){
		        	flag = true;
		        }
			});
			if(!flag){
				$(".curSelectedNode").removeClass("curSelectedNode");
				node.addClass("curSelectedNode");
				if(!node.find("input.viewpointMenuName").eq(0).is(":hidden")) return;
				clearEmptyInput();
				clearInput();
				clearCurSelectedBtn(node);
			}
		}
		$(document).ready(function(){
			$(".content-center").mCustomScrollbar({
				axis:"xy",
				theme:"minimal-dark",
				advanced:{ 
					updateOnBrowserResize:true 
				},autoHideScrollbar: true
			});
			$.ajax({
				url:webPath+"/pmsViewpoint/getAll",
				type : "post",
				cache : false,
				async : false,
				dataType : "json",
				success : function(data) {
					zNodes = data;
				}
			});
			var treeLeft = $("#treeLeft");
			var left = $.fn.zTree.init(treeLeft, setting, zNodes);
		/* 	var nodes = left.getNodesByParam("level", 0, null);
			for(var i in nodes){
				var dom = $("#" + nodes[i].tId+"_a");
				dom.prepend("<i class='fa fa-"+nodes[i].id+"'></i>");
			} */
			$(".content-left").mCustomScrollbar({
				axis:"y",
				theme:"minimal-dark",
				advanced:{ 
					updateOnBrowserResize:true 
				},autoHideScrollbar: true
			});
		});
		
		function prodInput(flag,treeNode){
			zTree_Menu = $.fn.zTree.getZTreeObj("treeCenter");
			zTree_Menu.expandAll(true);
			var parentNodes = zTree_Menu.getNodes();
			$(".addParent").remove();
			$("#treeCenter").parent().append("<button name='addParent' class='btn btn-mini addParent' href='#'></button>");
			addEventAddParent($("#treeCenter").parent().find("button[name='addParent']"),treeNode);
			
			if(flag!=null){
				var dom = $("#" + parentNodes[0].tId+"_a");
				prodDom(dom,parentNodes[0]);
				dom.hover(function(){ 
					if($(this).find(".curSelectedBtn").length>0) return;
					$(this).find("button[name='update']").removeClass("hideBtn").addClass("showBtn");
					$(this).find("button[name='delete']").removeClass("hideBtn").addClass("showBtn");
				},function(){
					if($(this).find(".curSelectedBtn").length>0) return;
					$(this).find("button[name='update']").addClass("hideBtn").removeClass("showBtn");
					$(this).find("button[name='delete']").addClass("hideBtn").removeClass("showBtn");
				});
				$(".curSelectedNode").removeClass("curSelectedNode");
				clearInput();
				clearCurSelectedBtn(dom);
				dom.find("button[name='update']").removeClass("hideBtn").addClass("showBtn");
				dom.find("button[name='delete']").removeClass("hideBtn").addClass("showBtn");
				dom.find("button[name='update']").eq(0).click();
			}else{
				//动态添加输入域
				getChildNode(parentNodes);
			}
			zTree_Menu.expandAll(false);
		}
		function getChildNode(parentNodes){
			for(var i in parentNodes){
				var dom = $("#" + parentNodes[i].tId+"_a");
				var node = parentNodes[i];
				prodDom(dom,node);
				var children = parentNodes[i].children;
				getChildNode(children);
			}
		}
		function prodDom(dom,node){
			var checked3 = "",checkVal3="按钮";
			var checked4 = "",checkVal4="关闭";
			var checked5 = "",checkVal5="否";
			if(node.urlSts==1){
				checked4 = "checked";
				checkVal4 = "开启";
			}
			if(node.urlType==1){
				checked3 = "checked";
				checkVal3 = "菜单";
			}
			if(node.isrefresh==1||node.isrefresh==""){
				checked5 = "checked";
				checkVal5 = "是";
			}	
			//alert(node.viewpointMenuNo.replace("&nbsp", ""))
			dom.append("<span class='inputVal viewpointMenuNo'>&nbsp"+node.viewpointMenuNo+"</span>")
			.append("<span class='inputVal viewpointMenuName'>&nbsp"+node.viewpointMenuName+"</span>")
			.append("<span class='inputVal viewpointMenuUrl'>&nbsp"+node.viewpointMenuUrl+"</span>")
			.append("<span class='inputVal jsMethod'>&nbsp"+node.jsMethod+"</span>")
			.append("<span class='inputVal expr'>&nbsp"+node.expr+"</span>")
			.append("<input class='form-control viewpointMenuNo ' type='text' name='viewpointMenuNo' maxlength='5' value='"+node.viewpointMenuNo.replace("&nbsp", "")+"'>")
			.append("<input class='form-control viewpointMenuName ' type='text' name='viewpointMenuName' value='"+node.viewpointMenuName.replace("&nbsp", "")+"'>")
			.append("<input class='form-control viewpointMenuUrl ' type='text' name='viewpointMenuUrl' value='"+node.viewpointMenuUrl.replace("&nbsp", "")+"'>")
			.append("<input class='form-control jsMethod ' type='text' name='jsMethod' value='"+node.jsMethod.replace("&nbsp", "")+"'>")
			.append("<input class='form-control expr ' type='text' name='expr' value='"+node.expr.replace("&nbsp", "")+"'>")
			.append("<div class='checkboxdiv'><span>"+checkVal5+"</span><input name='isrefresh' type='checkbox' id='"+node.tId+"_checkbox3' class='chk_5' "+checked5+" /><label class='checkbox' for='"+node.tId+"_checkbox3'></label></div>")
			.append("<div class='checkboxdiv'><span>"+checkVal3+"</span><input name='urlType' type='checkbox' id='"+node.tId+"_checkbox' class='chk_3' "+checked3+" /><label class='checkbox' for='"+node.tId+"_checkbox'></label></div>")
			.append("<div class='checkboxdiv'><span>"+checkVal4+"</span><input name='urlSts' type='checkbox' id='"+node.tId+"_checkbox2' class='chk_4' "+checked4+" /><label class='checkbox' for='"+node.tId+"_checkbox2'></label></div>")
			.append("<button name='delete' class='btn delRow btn-mini hideBtn' href='#'></button>")
			.append("<button name='add' class='btn addRow btn-mini hideBtn' href='#'></button>")
			.append("<button name='update' class='btn updRow btn-mini hideBtn' href='#'></button>")
			.append("<button name='cancel' class='btn colrow btn-mini hideBtn' href='#'></button>")
			.append("<button name='save' class='btn saverow btn-mini hideBtn' href='#'></button>");
			addEventUpdate(dom.find("button[name='update']").eq(0),node);
			addEventCancel(dom.find("button[name='cancel']").eq(0),node);
			addEventSave(dom.find("button[name='save']").eq(0),node);
			if(node.level>2){
				dom.find("button[name='add']").eq(0).remove();
			}else{
				addEventAdd(dom.find("button[name='add']").eq(0),node);
			}
			addEventDelete(dom.find("button[name='delete']").eq(0),node);
			
			$("#treeCenter li a").hover(function(){ 
				if($(this).find(".curSelectedBtn").length>0) return;
				$(this).find("button[name='update']").removeClass("hideBtn").addClass("showBtn");
				$(this).find("button[name='delete']").removeClass("hideBtn").addClass("showBtn");
				$(this).find("button[name='add']").removeClass("hideBtn").addClass("showBtn");
			},function(){
				if($(this).find(".curSelectedBtn").length>0) return;
				$(this).find("button[name='update']").addClass("hideBtn").removeClass("showBtn");
				$(this).find("button[name='delete']").addClass("hideBtn").removeClass("showBtn");
				$(this).find("button[name='add']").addClass("hideBtn").removeClass("showBtn");
			});
		}
		function addEventAdd($dom,node){
			$dom.bind("click",function(event){
				clearEmptyInput();
				var treeLeft = $.fn.zTree.getZTreeObj("treeLeft");
				treeNodeLeft = treeLeft.getNodesByParam("id", node.id, null)[0];
				var newNode = [{name:"",viewpointMenuNo:"",viewpointMenuName:"",viewpointMenuUrl:"",jsMethod:"",expr:"",upViewpointMenuNo:node.viewpointMenuNo,urlType:"1",urlSts:"1",isrefresh:"1"}];
				newNode = zTree_Menu.addNodes(node, -1,newNode);
				var dom = $("#" + newNode[0].tId+"_a");
				prodDom(dom,newNode[0]);
				dom.hover(function(){ 
					if($(this).find(".curSelectedBtn").length>0) return;
					$(this).find("button[name='update']").removeClass("hideBtn").addClass("showBtn");
					$(this).find("button[name='delete']").removeClass("hideBtn").addClass("showBtn");
					$(this).find("button[name='add']").removeClass("hideBtn").addClass("showBtn");
				},function(){
					if($(this).find(".curSelectedBtn").length>0) return;
					$(this).find("button[name='update']").addClass("hideBtn").removeClass("showBtn");
					$(this).find("button[name='delete']").addClass("hideBtn").removeClass("showBtn");
					$(this).find("button[name='add']").addClass("hideBtn").removeClass("showBtn");
				});
				$(".curSelectedNode").removeClass("curSelectedNode");
				clearInput();
				clearCurSelectedBtn(dom);
				dom.find("button[name='update']").removeClass("hideBtn").addClass("showBtn");
				dom.find("button[name='delete']").removeClass("hideBtn").addClass("showBtn");
				dom.find("button[name='add']").removeClass("hideBtn").addClass("showBtn");
				dom.find("button[name='update']").eq(0).click();
				dom.find("input.viewpointMenuNo").css("display","block");
				dom.find(".inputVal.viewpointMenuNo").css("display","none");
				event.stopPropagation();
			});
		}
		function addEventAddParent($dom,treeNode){
			$dom.bind("click",function(event){
				clearEmptyInput();
				var newNodes = [{name:"",viewpointMenuNo:"",viewpointMenuName:"",viewpointMenuUrl:"",jsMethod:"",expr:"",upViewpointMenuNo:viewpointNo,urlType:"1",urlSts:"1",isrefresh:"1"}];
				/* var treeLeft = $.fn.zTree.getZTreeObj("treeLeft");
				var leftNodes = treeLeft.addNodes(treeNode, newNodes,true); */
				var treeCenter = $.fn.zTree.getZTreeObj("treeCenter");
				newNodes = treeCenter.addNodes(null, newNodes,true);
				var dom = $("#" + newNodes[0].tId+"_a");
				prodDom(dom,newNodes[0]);
				dom.hover(function(){ 
					if($(this).find(".curSelectedBtn").length>0) return;
					$(this).find("button[name='update']").removeClass("hideBtn").addClass("showBtn");
					$(this).find("button[name='delete']").removeClass("hideBtn").addClass("showBtn");
					$(this).find("button[name='add']").removeClass("hideBtn").addClass("showBtn");
				},function(){
					if($(this).find(".curSelectedBtn").length>0) return;
					$(this).find("button[name='update']").addClass("hideBtn").removeClass("showBtn");
					$(this).find("button[name='delete']").addClass("hideBtn").removeClass("showBtn");
					$(this).find("button[name='add']").addClass("hideBtn").removeClass("showBtn");
				});
				$(".curSelectedNode").removeClass("curSelectedNode");
				clearInput();
				clearCurSelectedBtn(dom);
				dom.find("button[name='update']").removeClass("hideBtn").addClass("showBtn");
				dom.find("button[name='delete']").removeClass("hideBtn").addClass("showBtn");
				dom.find("button[name='add']").removeClass("hideBtn").addClass("showBtn");
				dom.find("button[name='update']").eq(0).click();
				dom.find("input.viewpointMenuNo").css("display","block");
				dom.find(".inputVal.viewpointMenuNo").css("display","none");
				event.stopPropagation();
			});
		}
		function addEventUpdate($dom,node){
			$dom.bind("click",function(event){
				clearEmptyInput();
				clearInput();
				clearCurSelectedBtn($(this).parent());
				$(this).parent().find("input[type='text']").css("display","block");
				$(this).parent().find(".checkbox").css("visibility","visible");
				$(this).parent().find(".checkbox").css("display","");
				$(this).parent().find(".inputVal").css("display","none");
				$(this).parent().find("input.viewpointMenuNo").css("display","none");
				$(this).parent().find(".inputVal.viewpointMenuNo").css("display","block");
				btnShowHide($dom);
				$(".curSelectedNode").removeClass("curSelectedNode");
				if(!$(this).parent().hasClass("curSelectedNode")){
					$(this).parent().addClass("curSelectedNode");
				}
				event.stopPropagation();
			});
		}
		function addEventDelete($dom,node){
			$dom.bind("click",function(event){
				alert("确认删除？",2,function(){
				var arr = node.children;
				if(!arr){
					arr = [];
				}
				arr.push(node);
				for(var i in arr){
					$.ajax({
						url:webPath+"/pmsViewpoint/delete",
						type : "post",
						dataType : "json",
						data:{
							json:JSON.stringify({
								viewpointMenuNo:arr[i].viewpointMenuNo
							})
						},
						success : function(data) {
							var treeLeft = $.fn.zTree.getZTreeObj("treeLeft");
							var leftNode = treeLeft.getNodesByParam("viewpointMenuNo", arr[i].viewpointMenuNo, null);
							//treeLeft.removeChildNodes(leftNode[0]);
							treeLeft.removeNode(leftNode[0]);
							//zTree_Menu.removeChildNodes(node);
							zTree_Menu.removeNode(node);
						}
					});
				}
				event.stopPropagation();
				});
			});
		}
		function addEventCancel($dom,node){
			$dom.bind("click",function(event){
				clearEmptyInput();
				$(".curSelectedNode").removeClass("curSelectedNode");
				clearInput();
				btnShowHide($dom);
				event.stopPropagation();
			});
		}
		function addEventSave($dom,node){
			$dom.bind("click",function(event){
				var dom = this;
				var state = null;
				if($(this).parent().find("input[name='viewpointMenuNo']").is(":hidden")){
					state = "";
					node.viewpointMenuNo = node.viewpointMenuNo.replace(/(^\s*)|(\s*$)/g, "");
				}else{
					state = "new";
					node.viewpointMenuNo = $(this).parent().find("input[name='viewpointMenuNo']").val();
					if(node.viewpointMenuNo==""){
						zTree_Menu.removeNode(node);
						return false;
					}
				}
				node.viewpointMenuName = $(this).parent().find("input[name='viewpointMenuName']").val();
				node.viewpointMenuUrl = $(this).parent().find("input[name='viewpointMenuUrl']").val();
				node.jsMethod = $(this).parent().find("input[name='jsMethod']").val();
				node.expr = $(this).parent().find("input[name='expr']").val();
				node.urlSts="0";
				if($(this).parent().find(".chk_4:checked").length>0){
					node.urlSts="1";
				}
				var checked = false,checkVal="关闭";
				if(node.urlSts=="1"){
					checked = true;
					checkVal = "开启";
				}
				node.urlType="0";
				if($(this).parent().find(".chk_3:checked").length>0){
					node.urlType="1";
				}
				var checked2 = false,checkVal2="按钮";
				if(node.urlType=="1"){
					checked2 = true;
					checkVal2 = "菜单";
				}
				node.isrefresh="0";
				if($(this).parent().find(".chk_5:checked").length>0){
					node.isrefresh="1";
				}
				var checked3 = false,checkVal3="否";
				if(node.isrefresh=="1"){
					checked3 = true;
					checkVal3 = "是";
				}
				var treeObj = $.fn.zTree.getZTreeObj("treeCenter");
				var treenode = treeObj.getNodeByParam("viewpointMenuNo", node.upViewpointMenuNo, null);
				var childrenCut;
				if(treenode){
					childrenCut = treenode.children.length;
				}else{
					childrenCut = treeObj.getNodes().length;
				}
				var index = childrenCut+1;
				var jsonData = {
					viewpointMenuNo:node.viewpointMenuNo,
					viewpointMenuName:node.viewpointMenuName,
					viewpointMenuUrl:node.viewpointMenuUrl,
					upViewpointMenuNo:node.upViewpointMenuNo,
					viewpointNo:viewpointNo,
					viewpointName:viewpointName,
					jsMethod:node.jsMethod,
					urlType:node.urlType,
					urlSts:node.urlSts,
					expr:node.expr,
					isrefresh:node.isrefresh,
					seq:index
				};
				if(jsonData.viewpointMenuNo!=""&&jsonData.viewpointMenuNo!="&nbsp"){
					$.ajax({
						url:webPath+"/pmsViewpoint/save",
						type : "post",
						dataType : "json",
						data:{
							optSts:state,
							json:JSON.stringify(jsonData)
						},
						success : function(data) {
							if(data.type=="update"){
								alert("菜单已存在,是否覆盖?",2,function(){
									$.ajax({
										url:webPath+"/pmsViewpoint/update",
										type : "post",
										dataType : "json",
										data:{
											json:JSON.stringify(jsonData)
										},
										success : function(data) {
											updateTreeNode(dom,node);
											var treeLeft = $.fn.zTree.getZTreeObj("treeLeft");
											var leftNode = treeLeft.getNodesByParam("viewpointMenuNo", jsonData.viewpointMenuNo, null);
											treeLeft.removeNode(leftNode[0]);
											var cNode = zTree_Menu.getNodesByParam("viewpointMenuNo", jsonData.viewpointMenuNo, null);
											zTree_Menu.removeNode(cNode[0]);
											zTree_Menu.updateNode(node);
											treeLeft.addNodes(treeNodeLeft, node,true);
										}
									});
								});
								<%-- $.myAlert.Confirm("菜单已存在,是否覆盖?","",function(){
									$.ajax({
										url:webPath+"/PmsViewpointAction_update.action",
										type : "post",
										dataType : "json",
										data:{
											json:JSON.stringify(jsonData)
										},
										success : function(data) {
											updateTreeNode(dom,node);
											var treeLeft = $.fn.zTree.getZTreeObj("treeLeft");
											var leftNode = treeLeft.getNodesByParam("viewpointMenuNo", jsonData.viewpointMenuNo, null);
											treeLeft.removeNode(leftNode[0]);
											var cNode = zTree_Menu.getNodesByParam("viewpointMenuNo", jsonData.viewpointMenuNo, null);
											zTree_Menu.removeNode(cNode[0]);
											zTree_Menu.updateNode(node);
											treeLeft.addNodes(treeNodeLeft, node,true);
										}
									});
								}); --%>
							}else{
								updateTreeNode(dom,node);
								zTree_Menu.updateNode(node);
								var treeLeft = $.fn.zTree.getZTreeObj("treeLeft");
								if(state!=""){
									var leftNodes = treeLeft.addNodes(treeNodeLeft, node,true);
								}else{
									var leftNode = treeLeft.getNodesByParam("viewpointMenuNo", node.viewpointMenuNo, null);
									if(leftNode[0].getParentNode()==null){
										treeLeft.addNodes(treeNodeLeft, node,true);
									}else{
										treeLeft.addNodes(leftNode[0].getParentNode(), node,true);
									}
									treeLeft.removeNode(leftNode[0]);
								}
							}
						}
					});
				}
				clearInput();
				clearCurSelectedBtn($(this).parent());
				$(this).parent().find("input[type='text']").css("display","none");
				$(this).parent().find(".inputVal").css("display","block");
				btnShowHide($dom);
				$(".curSelectedNode").removeClass("curSelectedNode");
				if(!$(this).parent().hasClass("curSelectedNode")){
					$(this).parent().addClass("curSelectedNode");
				}
				event.stopPropagation();
			});
		}
		function updateTreeNode(dom,node){
			$(dom).parent().find("span.viewpointMenuNo").html("&nbsp"+node.viewpointMenuNo);
			$(dom).parent().find("span.viewpointMenuName").html("&nbsp"+node.viewpointMenuName);
			$(dom).parent().find("span.viewpointMenuUrl").html("&nbsp"+node.viewpointMenuUrl);
			$(dom).parent().find("span.jsMethod").html("&nbsp"+node.jsMethod);
			$(dom).parent().find("span.expr").html("&nbsp"+node.expr);
			var checkVal = "关闭",checkVal2 = "按钮",checkVal3 = "否",checked = false,checked2 = false,checked3 = false;
			if(node.urlType=="1"){
				checkVal2 = "菜单";
				checked2 = true;
			}
			if(node.urlSts=="1"){
				checkVal = "开启";
				checked = true;
			}
			if(node.isrefresh=="1"){
				checkVal3 = "是";
				checked3 = true;
			}
			$(dom).parent().find(".checkboxdiv span").eq(2).html(checkVal);
			$(dom).parent().find("input[name='urlSts']").attr("checked",checked);
			$(dom).parent().find(".checkboxdiv span").eq(1).html(checkVal2);
			$(dom).parent().find("input[name='urlType']").attr("checked",checked2);
			$(dom).parent().find(".checkboxdiv span").eq(0).html(checkVal3);
			$(dom).parent().find("input[name='isrefresh']").attr("checked",checked3);
		}
		function btnShowHide($dom){
			//var showBtn = $(".ztree li .showBtn");
			//var hideBtn = $(".ztree li .hideBtn");
			var showBtn = $dom.parent().find(".showBtn");
			var hideBtn = $dom.parent().find(".hideBtn");
			var curSelectedBtn = $dom.parent().find(".curSelectedBtn");
			showBtn.addClass("hideBtn").removeClass("showBtn");
			hideBtn.removeClass("hideBtn").addClass("showBtn curSelectedBtn");
			curSelectedBtn.removeClass("curSelectedBtn").addClass("hideBtn").removeClass("showBtn");
		}
		function clearInput(){
			$(".ztree li .inputVal").css("display","");
			$(".ztree li input[type='text']").css("display","none");
			$(".ztree li .checkbox").css("visibility","");
			$(".ztree li .checkbox").css("display","none");
		}
		function clearCurSelectedBtn(node){
			$(".curSelectedBtn").removeClass("curSelectedBtn");
			node.find(".showBtn").addClass("curSelectedBtn");
			$(".showBtn").removeClass("showBtn").addClass("hideBtn");
			$(".curSelectedBtn").removeClass("hideBtn").addClass("showBtn");
		}
		function clearEmptyInput(){
			$("#treeCenter").find("input.viewpointMenuNo").each(function(){
				if(!$(this).is(":hidden")){
					var nodes = zTree_Menu.getNodesByParam("tId", $(this).parent().parent().attr("id"), null);
					zTree_Menu.removeNode(nodes[0]);
				}
			});
		}
		function dropPrev(treeId, nodes, targetNode) {
			if(targetNode.tId.indexOf("treeLeft")!=-1){
				return false;
			}
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
		function dropNext(treeId, nodes, targetNode) {
			if(targetNode.tId.indexOf("treeLeft")!=-1){
				return false;
			}
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

		var curDragNodes;
		function beforeDrag(treeId, treeNodes) {
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
			return true;
		}
		function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
			return true;
		}
		function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			var nodes = treeObj.getNodesByParam("level", treeNodes[0].level, null);
			for(var i in nodes){
				var index = treeObj.getNodeIndex(nodes[i]);
				var viewpointMenuNo = nodes[i].viewpointMenuNo;
				var jsonData = {
						viewpointMenuNo:viewpointMenuNo,
						seq:index+1
					};
				$.ajax({
					url:webPath+"/pmsViewpoint/updateSeq",
					type : "post",
					dataType : "json",
					data:{
						json:JSON.stringify(jsonData)
					},
					success : function(data) {
						
					}
				});
			}
		}
	</SCRIPT>
	</head>
	<body>
		<div class="content-left">
			<div class="zTreeDemoBackground" >
				<ul id="treeLeft" class="ztree"></ul>
			</div>
		</div>
		<div class="content-center">
			<div class="zTreeDemoBackground" >
				<div class="header">
					<ul>
						<li>
							<span class="menu-no">&nbsp;菜单号</span>
							<span class="menu-name">&nbsp;菜单名称</span>
							<span class="menu-url">&nbsp;菜单地址</span>
							<span class="menu-js">&nbsp;调用函数</span>
							<span class="menu-expr">&nbsp;不显示条件</span>
							<span class="menu-isrefresh">&nbsp;是否刷新</span>
							<span class="menu-type">&nbsp;类型</span>
							<span class="menu-sts">&nbsp;状态</span>
							<span class="menu-opt">&nbsp;操作</span>
						</li>
					</ul>
				</div>
				<ul id="treeCenter" class="ztree" style="padding: 0;"></ul>
			</div>
		</div>
	</body>
</html>
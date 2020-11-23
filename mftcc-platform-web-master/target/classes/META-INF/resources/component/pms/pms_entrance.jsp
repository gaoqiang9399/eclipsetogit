<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ include file="/component/include/webPath.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<link rel="stylesheet" href="${webPath}/component/pms/css/zTreeStyle.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
			<%--滚动条js 和鼠标滚动事件js--%>
	<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css">
	<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel.min.js"></script>
	<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/zTree/jquery.ztree.all.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/myAlert.js"></script>
		<link rel="stylesheet" href="${webPath}/component/pms/css/pms_entrance.css" />
		<SCRIPT type="text/javascript">
		var curMenu = null, zTree_Menu = null;
		var setting = {
			view: {
				showLine: false,
				showIcon: false,
				selectedMulti: false,
				dblClickExpand: false,
				addDiyDom: addDiyDom
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: beforeClick,
				onClick: zTreeOnClick
			}
		};

		function addDiyDom(treeId, treeNode) {
			var spaceWidth = 5;
			var switchObj = $("#" + treeNode.tId + "_switch"),
			icoObj = $("#" + treeNode.tId + "_ico");
			switchObj.remove();
			icoObj.before(switchObj);

			if (treeNode.level > 1) {
				var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
				switchObj.before(spaceStr);
			}
		}

		function beforeClick(treeId, treeNode) {
			if (treeNode.level == 0 ) {
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				zTree.expandNode(treeNode);
				return false;
			}
			return true;
		}
		
		function zTreeOnClick(event, treeId, treeNode){
			var node = $("#" + treeNode.tId+"_a");
			if(treeNode.id=="add"){
				$(".curSelectedNode").removeClass("curSelectedNode");
				clearInput();
				clearCurSelectedBtn(node);
				return false;
			}
			var checkbox = node.find("label.checkbox")[0];
	        var checkboxOffset = checkbox.getBoundingClientRect();
	        var width = 60;
	        var height = 24;
	        event = event||window.event; 
	        var x=event.clientX,
	        	y=event.clientY;
	        if(!(x>checkboxOffset.left&&x<(checkboxOffset.left+width))||!(y>checkboxOffset.top&&y<(checkboxOffset.top+height))){
	        	$(".curSelectedNode").removeClass("curSelectedNode");
				node.addClass("curSelectedNode");
				if(!node.find("input").eq(0).is(":hidden")) return;
				clearInput();
				clearCurSelectedBtn(node);
	        }
		}
		
		function clearCurSelectedBtn(node){
			$(".curSelectedBtn").removeClass("curSelectedBtn");
			node.find(".showBtn").addClass("curSelectedBtn");
			$(".showBtn").removeClass("showBtn").addClass("hideBtn");
			$(".curSelectedBtn").removeClass("hideBtn").addClass("showBtn");
		}
		$(document).ready(function(){
			var zNodes;
			$(".content").hide();
			$.ajax({
				url : webPath+"/pmsEntrance/getAll",
				type : "post",
				cache : false,
				async : false,
				dataType : "json",
				success : function(data) {
					zNodes = JSON.parse(data.json);
				}
			});
			var treeObj = $("#treeDemo");
			$.fn.zTree.init(treeObj, setting, zNodes);
			zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
			
			//header
			var node = zTree_Menu.getNodeByParam("type", "header", null);
			node = $("#" + node.tId+"_a");
			node.append("<span class='url'>定制请求</span><span class='enName'>入口名称</span><span class='desc'>请求描述</span><span class='lev'>级别</span><span class='css'>图标样式</span><span class='sts'>是否生效</span><span class='opt'>操作</span>");
			
			zTree_Menu.expandAll(true);
			
			//动态添加输入域
			var parentNodes = zTree_Menu.getNodesByParam("type", "entor", null);
			for(var i in parentNodes){
				var children = parentNodes[i].children;
				for(var j in children){
					var dom = $("#" + children[j].tId+"_a");
					if(children[j].id=="add"){
						dom.append("<button name='add' class='btn btn-warning btn-mini add' href='#'>添加定制请求</button>");
						addEventAdd(dom.find("button[name='add']").eq(0),children[j]);
						continue;
					}
					var node = children[j];
					prodDom(dom,node);
				}
			}
			
			zTree_Menu.expandAll(false);
			
			$(".ztree li a").hover(function(){ 
				if($(this).find(".curSelectedBtn").length>0) return;
				$(this).find("button[name='update']").removeClass("hideBtn").addClass("showBtn");
				$(this).find("button[name='delete']").removeClass("hideBtn").addClass("showBtn");
			},function(){
				if($(this).find(".curSelectedBtn").length>0) return;
				$(this).find("button[name='update']").addClass("hideBtn").removeClass("showBtn");
				$(this).find("button[name='delete']").addClass("hideBtn").removeClass("showBtn");
			});
			setTimeout(function(){
				$(".content").show();
			},500);
			$(".zTreeDemoBackground").mCustomScrollbar({
				axis:"y",
				theme:"minimal-dark",
				advanced:{ 
					updateOnBrowserResize:true 
				},autoHideScrollbar: true
			});
		});
		function addEventAdd($dom,node){
			$dom.bind("click",function(event){
				var pnode = node.getParentNode();
				var newNode = {name:"",entranceUrl:"",entranceName:node.getParentNode().name,entranceUrlDesc:"",entranceImgCss:"",urlSts:"1",lev:0}
				newNode = zTree_Menu.addNodes(pnode,pnode.children.length-1, newNode);
				var dom = $("#" + newNode[0].tId+"_a");
				prodDom(dom,newNode[0]);
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
				event.stopPropagation();
			});
		}
		function addEventUpdate($dom){
			$dom.bind("click",function(event){
				clearInput();
				clearCurSelectedBtn($(this).parent());
				/* $(this).parent().find("input").css("visibility","visible");
				$(this).parent().find(".checkbox").css("visibility","visible");
				$(this).parent().find(".inputVal").css("visibility","hidden"); */
				$(this).parent().find("input").show();
				$(this).parent().find(".checkbox").show();
				$(this).parent().find(".inputVal").hide();
				$(this).parent().find(".chk_4").hide();
				btnShowHide($dom);
				$(".curSelectedNode").removeClass("curSelectedNode");
				if(!$(this).parent().hasClass("curSelectedNode")){
					$(this).parent().addClass("curSelectedNode")
				}
				event.stopPropagation();
			});
		}
		function addEventDelete($dom,node){
			$dom.bind("click",function(event){
				if(node.urlSts==1){
					//myAlert("入口启用，禁止删除！");
					alert("入口启用，禁止删除！",0);
					return;
				}
				zTree_Menu.removeNode(node);
				$.ajax({
					url : webPath+"/pmsEntrance/delete",
					type : "post",
					dataType : "json",
					data:{json:JSON.stringify({pmsNo:node.id})},
					success : function(data) {
					    if(data.flag=="success"){
                            alert(data.msg,3);
                        }else{
                            alert(data.msg,0);
						}
					}
				});
				event.stopPropagation();
			});
		}
		function addEventCancel($dom,node){
			$dom.bind("click",function(event){
				$(".curSelectedNode").removeClass("curSelectedNode");
				clearInput();
				btnShowHide($dom);
				event.stopPropagation();
			});
		}
		function addEventSave($dom,node){
			$dom.bind("click",function(event){
				clearInput();
				clearCurSelectedBtn($(this).parent());
				/* $(this).parent().find("input").css("visibility","hidden");
				$(this).parent().find(".inputVal").css("visibility","visible"); */
				$(this).parent().find("input").hide();
				$(this).parent().find(".inputVal").show();
				btnShowHide($dom);
				$(".curSelectedNode").removeClass("curSelectedNode");
				if(!$(this).parent().hasClass("curSelectedNode")){
					$(this).parent().addClass("curSelectedNode")
				}
				node.entranceUrl = $(this).parent().find("input[name='EntranceUrl']").val();
				$(this).parent().find("span.EntranceUrl").html(node.entranceUrl);
				node.entranceName = $(this).parent().find("input[name='EntranceName']").val();
				$(this).parent().find("span.EntranceName").html(node.entranceName);
				node.entranceUrlDesc = $(this).parent().find("input[name='EntranceUrlDesc']").val();
				$(this).parent().find("span.EntranceUrlDesc").html(node.entranceUrlDesc);
				node.lev = $(this).parent().find("input[name='EntranceLev']").val();
				$(this).parent().find("span.EntranceLev").html(node.lev);
				node.entranceImgCss = $(this).parent().find("input[name='EntranceImgCss']").val();
				$(this).parent().find("span.EntranceImgCss").html(node.entranceImgCss);
				node.urlSts="0";
				if($(this).parent().find(".chk_4:checked").length>0){
					node.urlSts="1";
				}
				var checked = false,checkVal="关闭";
				if(node.urlSts=="1"){
					checked = true;
					checkVal = "开启";
				}
				$(this).parent().find(".checkboxdiv span").html(checkVal);
				$(this).parent().find("input[name='urlSts']").attr("checked",checked);
				$.ajax({
					url : webPath+"/pmsEntrance/save",
					type : "post",
					dataType : "json",
					data:{
						json:JSON.stringify({
							pmsNo:node.id,
							entranceNo:node.pId,
							entranceName:node.entranceName,
							entranceUrl:node.entranceUrl,
							entranceUrlDesc:node.entranceUrlDesc,
							lev:node.lev,
							entranceImgCss:node.entranceImgCss,
							urlSts:node.urlSts
						})
					},
					success : function(data) {
					    if(data.flag=="success"){
                            if(data.type=="insert"){
                                node.id = data.pmsNo;
                            }
                            zTree_Menu.updateNode(node);
                        }else{
					        alert(data.msg,0);
						}
					}
				});
				event.stopPropagation();
			});
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
			/* $(".ztree li .inputVal").css("visibility","visible");
			$(".ztree li input").css("visibility","hidden");
			$(".ztree li .checkbox").css("visibility","hidden"); */
			$(".ztree li .inputVal").show();
			$(".ztree li input").hide();
			$(".ztree li .checkbox").hide();
		}
		function prodDom(dom,node){
			var checked = "",checkVal="关闭";
			if(node.urlSts==1){
				checked = "checked";
				checkVal = "开启";
			}
				
			dom.append("<span class='inputVal EntranceUrl'>"+node.entranceUrl+"</span>")
			.append("<span class='inputVal EntranceName'>"+node.entranceName+"</span>")
			.append("<span class='inputVal EntranceUrlDesc'>"+node.entranceUrlDesc+"</span>")
			.append("<span class='inputVal EntranceLev'>"+node.lev+"</span>")
			.append("<span class='inputVal EntranceImgCss'>"+node.entranceImgCss+"</span>")
			.append("<input class='form-control EntranceUrl ' type='text' name='EntranceUrl' value='"+node.entranceUrl+"'>")
			.append("<input class='form-control EntranceName ' type='text' name='EntranceName' value='"+node.entranceName+"'>")
			.append("<input class='form-control EntranceUrlDesc ' type='text' name='EntranceUrlDesc' value='"+node.entranceUrlDesc+"'>")
			.append("<input class='form-control EntranceLev ' type='text' name='EntranceLev' value='"+node.lev+"'>")
			.append("<input class='form-control EntranceImgCss ' type='text' name='EntranceImgCss' value='"+node.entranceImgCss+"'>")
			.append("<div class='checkboxdiv'><span>"+checkVal+"</span><input name='urlSts' type='checkbox' id='"+node.tId+"_checkbox' class='chk_4' "+checked+" /><label class='checkbox' for='"+node.tId+"_checkbox'></label></div>")
			.append("<button name='update' class='btn btn-primary btn-mini hideBtn' href='#'>编辑</button>")
			.append("<button name='delete' class='btn btn-danger btn-mini hideBtn' href='#'>删除</button>")
			.append("<button name='save' class='btn btn-success btn-mini hideBtn' href='#'>保存</button>")
			.append("<button name='cancel' class='btn btn-danger btn-mini hideBtn' href='#'>取消</button>");
			addEventUpdate(dom.find("button[name='update']").eq(0));
			addEventCancel(dom.find("button[name='cancel']").eq(0),node);
			addEventSave(dom.find("button[name='save']").eq(0),node);
			addEventDelete(dom.find("button[name='delete']").eq(0),node);
		}
	</SCRIPT>
	</head>
	<body>
		<div class="content">
			<div class="zTreeDemoBackground" >
				<ul id="treeDemo" class="ztree"></ul>
			</div>
		</div>
	</body>
</html>
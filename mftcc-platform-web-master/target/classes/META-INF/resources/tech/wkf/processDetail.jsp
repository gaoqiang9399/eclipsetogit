<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
  <head>
    <title>流程查看</title>
	<!--[if lt IE 9]>
<?import namespace="v" implementation="#default#VML" ?>
<![endif]-->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
<link rel="stylesheet" type="text/css" href="css/processDetail.css"/>
<script src="js/jquery-1.11.2.min.js"></script>
<script src="js/processDetail.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css"/>
<script type="text/javascript">

var realPath = '${webPath}';
var $workArea,$nodeDom = {},$nodeData = {},$lineDom = {}, spacing = 25,activityname,completeArr = [],refusedArr = [],rollBackArr = [],rollbacked,$position={},topData,isTaskMenu;
var zhua,shou;
var themes = "circle";
$(document).ready(function(){
	$(".double-bounce").show();
	$workArea = $("#modeler");
	var processInstanceId = GetQueryString("processInstanceId");
	var appNo = GetQueryString("appNo");
	var appWorkflowNo = GetQueryString("appWorkflowNo");
	isTaskMenu = GetQueryString("isTaskMenu");
	$.ajax({
		type : "POST",
		cache:false,
		async:true,
		url : realPath+"/wkf_findByProcessInstanceId.action",
		data:{
			query:processInstanceId,
			appNo:appNo,
			appWorkflowNo:appWorkflowNo
		},
		success : function(data) {
			if(!data.value) return;
			topData = data;
			var nodes = data.value.nodes;
			for(var i in nodes){
				if(data[i]&&data[i].state=="open"){
					activityname = i;
				}else if(data[i]&&data[i].state=="completed"||i=="start"||data[i]&&data[i].state=="refused"){
					completeArr.push(i);
					if(data[i]&&data[i].state=="refused"){
						refusedArr.push(i);
					}
				}else if(data[i]&&data[i].state=="rollbacked"){
					rollBackArr.push(i);
				}
				addNode(i,nodes[i]);
			}
			
			optActivity();
			optComplete();
			optRollBack();
			otherNodeCheckComplete();
			
			$workArea.css({
				width:($position["width"]-$position["left"]+35)+"px",
				height:($position["height"]-$position["top"]+35+18)+"px"
			});
			
			
			optRefused();
			
			if($(window.parent.document.body).find(".handle").length>0){
				setTimeout(autoSize,500);
			}else{
				autoSize();
			}
			
			
		}
	});
	$(".double-bounce").hide();
});
//新增节点
function addNode(id,node){
	$nodeData[id] = node;
	$nodeDom[id]=$("<div class='item "+themes+" "+node.type+"' id='"+id+"' style='top:"+node.top+"px;left:"+node.left+"px'><div class='span'>"+node.name.replace(/\\/g,"")+"</div></div>");
	var ua=navigator.userAgent.toLowerCase();
	if(ua.indexOf('msie')!=-1 && ua.indexOf('8.0')!=-1)
		$nodeDom[id].css("filter","progid:DXImageTransform.Microsoft.Shadow(color=#94AAC2,direction=135,strength=2)");
	$workArea.append($nodeDom[id]);
	
	if($position["left"]&&$position["left"]>node.left){
		$position["left"] = node.left;
	}else if(!$position["left"]){
		$position["left"] = node.left;
	}
	if($position["top"]&&$position["top"]>node.top){
		$position["top"] = node.top;
	}else if(!$position["top"]){
		$position["top"] = node.top;
	}
	if($position["width"]&&$position["width"]<node.left){
		$position["width"] = node.left;
	}else if(!$position["width"]){
		$position["width"] = node.left;
	}
	if($position["height"]&&$position["height"]<node.top){
		$position["height"] = node.top;
	}else if(!$position["height"]){
		$position["height"] = node.top;
	}
	
	//添加事件
	var nodeName = node.name.replace(/\\/g,"");
	var group = "";
	if(node.properties["appType-chooseRole"]){
		group = node.properties["appType-chooseRole"].value[0].text.replace(/\\/g,"");
	}
	var assignee = "";
	if(topData[id]){
		assignee = topData[id].assignee;
	}
	if(node.type.indexOf("task")!=-1||node.type.indexOf("node")!=-1){
		$nodeDom[id].css("cursor","pointer");
		$nodeDom[id].bind("mouseover",function(){
			showDetail($nodeDom[id],nodeName,group,assignee);
		});
		$nodeDom[id].bind("mouseout",function(){
			closeDetail();
		});
		$nodeDom[id].bind("click",function(){
			nodeOnClick($nodeDom[id],nodeName,group,assignee);
		});
	}
}

function otherNodeCheckComplete(){
	var nodes = topData.value.nodes;
	var lines = topData.value.lines;
	for(var i in nodes){
		var node = nodes[i];
		var id = i;
		var nextId = "";
		var nextIdFrom = "";
		if(node.type=="fork round"){
			for(var j in lines){
				if(lines[j].from==id){
					nextId += lines[j].to +",";
				}
				if(lines[j].to==id){
					nextIdFrom += lines[j].from +",";
				}
			}
			var booFrom = false;
			if(nextIdFrom!=""){
				nextIdFrom = nextIdFrom.substring(0,nextIdFrom.length-1);
				var nextFrom = nextIdFrom.split(",");
				for(var n in nextFrom){
					if($("#"+nextFrom[n]).hasClass("activity")||$("#"+nextFrom[n]).hasClass("complete")){
						booFrom = true;
						break;
					}
				}
			}
			if(nextId!=""&&booFrom){
				nextId = nextId.substring(0,nextId.length-1);
				var next = nextId.split(",");
				var index=1000;
				var boo = false;
				var boo2 = false;
				for(var n in next){
					if($("#"+next[n]).hasClass("activity")||$("#"+next[n]).hasClass("complete")){
						$("#"+id).addClass("complete");
						boo = true;
						if(topData[next[n]].index<index){
							index = topData[next[n]].index;
						}
					}else if($("#"+next[n]).hasClass("rollback")){
						$("#"+id).addClass("rollback");
						boo2 = true;
						if(topData[next[n]].index<index){
							index = topData[next[n]].index;
						}
					}
				}
				if(boo){
					for(var i in topData){
						if(topData[i].index>=index){
							topData[i].index += 1;
						}
					}
					topData[id] = {};
					topData[id]["state"] = "completed";
					topData[id]["index"] = index;
				}
				if(boo2){
					for(var i in topData){
						if(topData[i].index>=index){
							topData[i].index += 1;
						}
					}
					topData[id] = {};
					topData[id]["state"] = "rollback";
					topData[id]["index"] = index;
				}
			}
		}
	}
}
function optActivity(){
	$("#"+activityname).addClass("activity");
}
function optComplete(){
	for(var i=0;i<completeArr.length;i++){
		$("#"+completeArr[i]).addClass("complete");
	}
}
function optRefused(){
	for(var i=0;i<refusedArr.length;i++){
		$("#"+refusedArr[i]).addClass("refused");
	}
}
function optRollBack(){
	for(var i=0;i<rollBackArr.length;i++){
		if(rollBackArr[i]==activityname) break;
		$("#"+rollBackArr[i]).addClass("rollback");
	}
}
//正则表达式获取地址栏参数
function GetQueryString(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}
function resizeCanvas() {  
	var canvas = $("#myCanvas");
    canvas.attr("width", $workArea.width());  
    canvas.attr("height", $workArea.height());  
};  
function autoSize(){
	resizeProcess();
	var iframe = window.parent.document.getElementById("processDetailIframe");
	var wb = $workArea.width()/$("body").width(),
	hb = $workArea.height()/$("body").height(),
	rb = -1;
	if(wb<hb){
		rb = 1/hb;
	}else{
		rb = 1/wb;
	}
	if(rb>0&&rb<1){
		/* $workArea.css({
			MozTransform:"translate(-50%, -50%) scale("+rb+","+rb+")",  
		  	WebkitTransform:"translate(-50%, -50%) scale("+rb+","+rb+")",  
		  	OTransform:"translate(-50%, -50%) scale("+rb+","+rb+")",
		  	transform:"translate(-50%, -50%) scale("+rb+","+rb+")"
		}); */
		$workArea.css({
						MozTransform:"translate(-50%, -50%) scale("+rb+","+rb+")",  
		  	WebkitTransform:"translate(-50%, -50%) scale("+rb+","+rb+")",  
		  	OTransform:"translate(-50%, -50%) scale("+rb+","+rb+")",
		  	transform:"translate(-50%, -50%) scale("+rb+","+rb+")"

		});
	}
	
	if(iframe){
		var info = $(iframe).parent();
		
		$(".modelAlert").bind("click",openDetail);
		parent.$(".handle_tree").click();
	}else{
		$(".modelAlert").hide();
		if(isTaskMenu&&isTaskMenu=='1'){
			$('<a href="../../ProcessInstanceAction_getListPage.action" class="return">返回</a>').appendTo($("body"));
		}else if(isTaskMenu&&isTaskMenu=='2'){
			$('<a href="../../TaskAction_getListPage.action" class="return">返回</a>').appendTo($("body"));
		}
	}
	$("#modeler .item").each(function(){
		$(this).position()
		$(this).css("left",$(this).position().left-$position["left"]+"px");
		$(this).css("top",$(this).position().top-$position["top"]+"px");
	});
	$("#modeler .wjline").each(function(){
		$(this).position()
		$(this).css("left",$(this).position().left-$position["left"]+"px");
		$(this).css("top",$(this).position().top-$position["top"]+"px");
	});
}
function resizeProcess(){
	//resizeCanvas();
	
	var topObj = [],leftObj=[];
	var nodes = topData.value.nodes;
	for(var i in nodes){
		var flag = false;
		for(var t in topObj){
			var tNum = Number(nodes[i].top)-Math.abs(topObj[t]);
			if(Math.abs(tNum)<45){
				$("#"+i).css("top",topObj[t]+"px");
				flag = true;
				break;
			}
		}
		if(!flag){
			topObj.push(nodes[i].top);
		}
		flag = false;
		for(var l in leftObj){
			var lNum = Number(nodes[i].left)-Math.abs(leftObj[l]);
			if(Math.abs(lNum)<45){
				$("#"+i).css("left",leftObj[l]+"px");
				flag = true;
				break;
			}
		}
		if(!flag){
			leftObj.push(nodes[i].left);
		}
	}
	
	
	var lines = topData.value.lines;
	for(var j in lines){
		resizeAddLine(j,lines[j]);
	}
}

function resizeAddLine(id,line){
	var from = $("#"+line.from),to = $("#"+line.to);
	var sx = from.position().left+from.width()/2,sy = from.position().top+from.height()/2;
	var ex = to.position().left+to.width()/2,ey = to.position().top+to.height()/2;
	var g = [];
	var arr = line.g.substring(1,line.g.length-1).split("},{");
	for(var i in arr){
		var tmp = arr[i].split(",");
		g.push({x:parseInt(tmp[0]),y:parseInt(tmp[1])});
	}
	var className = "line-default";
	
	if(($("#"+line.to).hasClass("activity")||$("#"+line.to).hasClass("complete"))){
		if($("#"+line.from).hasClass("complete")){
			if(topData[line.to]&&topData[line.from]){
				if(Number(topData[line.to].index)-Number(topData[line.from].index)==1){
					className = "line-complete";
				}
			}
		}
	}else if($("#"+line.to).hasClass("rollback")){
		if($("#"+line.from).hasClass("activity")||$("#"+line.from).hasClass("rollback")){
			if(topData[line.to]&&topData[line.from]){
				if(Number(topData[line.from].index)-Number(topData[line.to].index)==1){
					className = "line-rollback";
				}
			}
		}
	}
	
	if($("#"+line.from).hasClass("complete")&&!$("#"+line.from).hasClass("fork")&&$("#"+line.to).hasClass("end")&&typeof(activityname)=="undefined"){
		className = "line-complete";
		$("#"+line.to).addClass("complete");
	}
	
	var nodes = topData.value.nodes;
	if(nodes[line.from].type=="start round"){
		className = "line-complete";
	}
	
	for(var i in g){
		if(i>0){
			if(i-1==0){
				if(g[i-1].x+spacing<g[i].x-spacing){
					g[i-1].x = sx+spacing;
				}else if(g[i].x+spacing<g[i-1].x-spacing){
					g[i-1].x = sx-spacing;
				}
				if(g[i-1].y+spacing<g[i].y-spacing){
					g[i-1].y = sy+spacing+15;
				}else if(g[i].y+spacing<g[i-1].y-spacing){
					g[i-1].y = sy-spacing;
				}
			}
			if(i==g.length-1){
				var num = 0;
				if(i==1){
					num = 25;
				}
				if(g[i-1].x+spacing-num<g[i].x-spacing){
					g[i].x = ex-spacing;
				}else if(g[i].x+spacing-num<g[i-1].x-spacing){
					g[i].x = ex+spacing;
				}
				if(g[i-1].y+spacing-num<g[i].y-spacing){
					g[i].y = ey-spacing;
				}else if(g[i].y+spacing-num<g[i-1].y-spacing){
					g[i].y = ey+spacing+15;
				}
			}
			
			var xDvalue = g[i].x-g[i-1].x;
			var yDvalue = g[i].y-g[i-1].y;
			var angle = 0;
			if(yDvalue==0&&xDvalue>0){
				angle=0;
			}else if(yDvalue==0&&xDvalue<0){
				angle=180;
			}else if(yDvalue>0&&xDvalue==0){
				angle=90;
			}else if(yDvalue<0&&xDvalue==0){
				angle=270;
			}else if(yDvalue<0&&xDvalue<0){
				angle=Math.atan(Math.abs(yDvalue/xDvalue))/Math.PI*180+180;
			}else if(yDvalue<0&&xDvalue>0){
				angle=360-Math.atan(Math.abs(yDvalue/xDvalue))/Math.PI*180;
			}else if(yDvalue>0&&xDvalue<0){
				angle=180-Math.atan(Math.abs(yDvalue/xDvalue))/Math.PI*180;
			}else if(yDvalue>0&&xDvalue>0){
				angle=Math.atan(Math.abs(yDvalue/xDvalue))/Math.PI*180;
			}
			if(angle<3||(angle>357&&angle<360)){
				angle = 0;
			}
			var linewidth = Math.pow((xDvalue * xDvalue + yDvalue * yDvalue), 0.5); 
			var $lineDiv = $("<div class='wjline "+themes+" "+className+"'></div>");
			$lineDiv.css({
				"height":"20px",
				"transform-origin": "0px 10px 0px",
				"position": "absolute",
				"width":linewidth+"px",
				"top":(g[i-1].y-10)+"px",
				"left":g[i-1].x+"px",
				"transform":"rotate("+angle+"deg)",
				"-ms-transform":"rotate("+angle+"deg)",
				"-moz-transform":"rotate("+angle+"deg)",
				"-webkit-transform":"rotate("+angle+"deg)",
				"-o-transform":"rotate("+angle+"deg)"
			});
			$workArea.append($lineDiv);
		}
	}
}
	
var dragged = false;
var dx,dy,mx=0,my=0;
var img_object;
function openDetail(){
	var view = $(window.top.document.body);
	view.append('<div class="cd-popup" role="alert" onselectstart="return false" ><div class="cd-popup-container"><div class="popup-close i i-x3" onclick="closeDetail();"></div></div></div>');
	var wa = $workArea.clone();
	view.find('.cd-popup-container').css({
	  	width:$workArea.width()+$position["left"]+"px",
	  	height:$workArea.height()+$position["top"]+"px",
	  	maxWidth:($(window.top).get(0).innerWidth-20)+"px",
	  	maxHeight:($(window.top).get(0).innerHeight-20)+"px",
	    overflow: "hidden"
	});
	view.find('.cd-popup-container').append(wa);
	if(wa.width()-25>wa.parent().width()||wa.height()-100>wa.parent().height()){
		wa.css({
			top:"0px",
			left:"0px",
		   // position: "absolute",
			MozTransform:"none",  
		  	WebkitTransform:"none",  
		  	OTransform:"none",
		  	transform:"none",
		  	width:wa.width()+$position["left"]+"px",
		  	height:wa.height()+$position["top"]+"px"
		});
		wa.mousedown(function(e){drag_start(e); }).
	    mousemove(function(e){drag(e);}).
	    mouseup(function(e){drag_end(e);});
		img_object = wa;
		zhua = "url('./tech/wkf/css/img/openhand.cur'),auto";
		shou = "url('./tech/wkf/css/img/closedhand.cur'),auto";
		img_object.css("cursor",zhua);
	}else{
		wa.css({
			top:"50%",
			left:"50%",
			MozTransform:"translate(-50%, -50%)",  
		  	WebkitTransform:"translate(-50%, -50%)",  
		  	OTransform:"translate(-50%, -50%)",
		  	transform:"translate(-50%, -50%)",
		  	width:wa.width()+$position["left"]+"px",
		  	height:wa.height()+$position["top"]+"px"
		});
	}
	view.find(".cd-popup-container").css("padding",0);
	view.find('.cd-popup').addClass('is-visible');
	view.find(".ui-layout-pane").addClass("row");
	
	wa.find(".task").each(function(){
		var id = $(this).attr("id");
		var node = topData.value.nodes[id];
		var dom = $(this);
		dom.css("cursor","pointer");
		dom.bind("mouseover",function(){
			var nodeName = node.name.replace(/\\/g,"");
			var group = "";
			if(node.properties["appType-chooseRole"]){
				group = node.properties["appType-chooseRole"].value[0].text.replace(/\\/g,"");
			}
			var assignee = "";
			if(topData[id]){
				assignee = topData[id].assignee;
			}
			showDetail4Top(dom,nodeName,group,assignee);
		});
		dom.bind("mouseout",function(){
			closeDetail4Top();
		});
	});
	
	wa.find(".node").each(function(){
		var id = $(this).attr("id");
		var node = topData.value.nodes[id];
		var dom = $(this);
		dom.css("cursor","pointer");
		dom.bind("mouseover",function(){
			var nodeName = node.name.replace(/\\/g,"");
			var group = "";
			if(node.properties["appType-chooseRole"]){
				group = node.properties["appType-chooseRole"].value[0].text.replace(/\\/g,"");
			}
			var assignee = "";
			if(topData[id]){
				assignee = topData[id].assignee;
			}
			showDetail4Top(dom,nodeName,group,assignee);
		});
		dom.bind("mouseout",function(){
			closeDetail4Top();
		});
	});
	wa.find(".item").each(function(){
		$(this).position()
		$(this).css("left",$(this).position().left+$position["left"]/2+"px");
		$(this).css("top",$(this).position().top+$position["top"]/2+"px");
	});
	wa.find(".wjline").each(function(){
		$(this).position()
		$(this).css("left",$(this).position().left+$position["left"]/2+"px");
		$(this).css("top",$(this).position().top+$position["top"]/2+"px");
	});
}
function drag_start(e){
	img_object.css("cursor",shou);
	dragged = true;
	dx = e.pageX-mx;
    dy = e.pageY-my;
}
function drag(e){
	if(this.dragged){
		var ltop =  e.pageY -dy;
        var lleft = e.pageX -dx;
        setCoords(lleft,ltop);
	}
}
function drag_end(e){
	img_object.css("cursor",zhua);
	dragged=false;
    return false;
}
function setCoords(x,y){
	var h = img_object.parent().height();
	var w = img_object.parent().width();
    if(y > 0){
        y = 0;
    }
    if(x > 0){
        x = 0;
    }
    if(y + img_object.height() < h){
        y = h - img_object.height();
    }
    if(x + img_object.width() < w){
        x = w - img_object.width();
    }
    if(img_object.width() <= w){
        x = -(img_object.width() - w)/2;
    }
    if(img_object.height() <= h){
        y = -(img_object.height() - h)/2;
    }
    
    mx = x;
    my = y;
    
    img_object.css("top",y + "px").css("left",x + "px");
}

function nodeOnClick(node,nodeName,group,assignee){
	window.top.alert("节点名称:"+nodeName+"\r\n审批用户:"+assignee,2);
}
</script>
</head>
<body onselectstart="return false"  oncontextmenu="self.event.returnValue=false;" ondragstart="return false;">
	<div id = "modeler">
		<canvas id="myCanvas"></canvas> 
	</div>
	<div class="modelAlert"></div>
	<div class="double-bounce">
		<div class="double-bounce1"></div>
		<div class="double-bounce2"></div>
	</div>
</body>
</html>
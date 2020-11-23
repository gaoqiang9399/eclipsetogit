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
<script src="${webPath}/tech/wkf/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
<%-- <link rel="stylesheet" href="${webPath}/themes/view/css/filter.css" />
<link rel="stylesheet" href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" /> --%>
<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${webPath}/tech/wkf/css/processDetail.css"/>
<link rel="stylesheet" type="text/css" href="${webPath}/tech/wkf/css/processDetailList.css"/>
<link rel="stylesheet" type="text/css" href="${webPath}/layout/view/themes/iconfont/css/iconfont.css"/>
<script type="text/javascript">
	var realPath = '${webPath}';
	var listUrl = realPath+"/wkf_listForShowView.action";
	var $workData = {};
	var $workArea,$nodeDom = {},$nodeData = {},$lineDom = {}, spacing = 25,activityname,rollbacked,$position={},topData;
	var leftListData = {};
	var key;
	//var themes = "default";
	var themes = "circle";
	window.onresize = function(){
		autoSize(key);
	}
	$(document).ready(function(){
		$(".double-bounce").show();
		var fieldName = GetQueryString("fieldName");
		$workArea = $("#modeler");
		var leftList = $(".Cag-leftList");
		jQuery.ajax({
			type : "POST",
			cache : false,
			async : true,
			url : listUrl,
			success: function(data) {
				leftListData = data;
				var processNo = window.top.document.getElementById('a_iframe').contentWindow.getProcessNo(fieldName);
				var cut = 0;
				for(var i in data){
					var li = $('<li version="'+data[i].version+'"><p>'+data[i].description+'</p><span>'+i+'</span></li>').appendTo(leftList);
					$workData[i] = data[i].value;
					li.bind("click",liClick);
					if(processNo!=""&&processNo==i){
						li.click();
					}
				}
				leftList.mCustomScrollbar({
					theme:"minimal-dark",
				});
				leftList.mCustomScrollbar("scrollTo",".curr");
				
				$(".i.i-fangdajing").bind("click",sreachClick);
				
				$('.Cagpic-search').bind('keypress',function(event){
		            if(event.keyCode == "13")    
		            {
		            	sreachClick();
		            }
		        });
				$(".double-bounce").hide();
			}
		});
		$(".Cagpic-Btn .xq").bind("click",CagpicApply);
		$(".Cagpic-Btn .gb").bind("click",CagpicCancel);
	});
	function sreachClick(){
		var leftList = $(".Cag-leftList");
		leftList.remove();
		leftList = $('<ul class="Cag-leftList"></ul>').appendTo($(".Cag-left"));
		$workArea.html('<canvas id="myCanvas"></canvas>');
		var value = $('.Cagpic-search').val();
		var fieldName = GetQueryString("fieldName");
		var flag = false;
		var processNo = window.top.document.getElementById('a_iframe').contentWindow.getProcessNo(fieldName);
		for(var i in leftListData){
			if((leftListData[i].description+i).indexOf(value)!=-1){
				var li = $('<li version="'+leftListData[i].version+'"><p>'+leftListData[i].description+'</p><span>'+i+'</span></li>').appendTo(leftList);
				li.bind("click",liClick);
				if(processNo!=""&&processNo==i){
					li.click();
					flag = true;
				}
			}
		}
		if(!flag&&leftList.find("li").length>0){
			leftList.find("li").eq(0).click();
		}
		leftList.mCustomScrollbar({
			theme:"minimal-dark",
		});
		leftList.mCustomScrollbar("scrollTo",".curr");
	}
	function CagpicApply(){
		// 增加获取描述的取值和赋值。20161201 GuoXu
		var key = $(".Cag-leftList li.curr p").html();//得到描述
		var fieldName = GetQueryString("fieldName");
		var key2 = $(".Cag-leftList li.curr span").html();//得到编号
		var fieldId = fieldName.substr(0,fieldName.length-4)+"Id";
		window.top.cloesShowDialog(function(){
			//window.top.document.getElementById('a_iframe').contentWindow.document.getElementById('pd_iframe').contentWindow.setProcessNo(key,fieldName);
			window.top.document.getElementById('a_iframe').contentWindow.setProcessNo(key,fieldName);
			window.top.document.getElementById('a_iframe').contentWindow.setProcessNo(key2,fieldId);
		});
	}
	function CagpicCancel(){
		window.top.cloesShowDialog();
	}
	function liClick(){
		$("li").removeClass("curr");
		$(this).addClass("curr");
		$workArea.html('<canvas id="myCanvas"></canvas>');
		$position={};
		key = $(this).find("span").eq(0).html();
		
		var nodes = $workData[key].nodes;
		for(var i in nodes){
			addNode(i,nodes[i]);
		}
		$workArea.css({
			width:($position["width"]+$position["left"]+35)+"px",
			height:($position["height"]+$position["top"]+35+18)+"px"
		});
		/* resizeCanvas();
		var canvas = document.getElementById("myCanvas"),ctx; 
		if(canvas.getContext){
			ctx = canvas.getContext("2d");
			ctx.lineWidth=3;
			ctx.lineCap="round";
		} 
		var lines = $workData[key].lines;
		for(var j in lines){
			addLine(j,lines[j],ctx);
		} */
		autoSize(key);
	}
	//新增节点
	function addNode(id,node){
		$nodeData[id] = node;
		$nodeDom[id]=$("<div class='item "+themes+" "+node.type+"' id='"+id+"' style='top:"+node.top+"px;left:"+node.left+"px'><div class='span'>"+node.name+"</div></div>");
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
	}
	/* //新增连线
	function addLine(id,line,ctx){
		var from = $nodeData[line.from],to = $nodeData[line.to];
		var sx = from.left+$nodeDom[line.from].width()/2,sy = from.top+$nodeDom[line.from].height()/2;
		var ex = to.left+$nodeDom[line.to].width()/2,ey = to.top+$nodeDom[line.to].height()/2;
		var g = [];
		var arr = line.g.substring(1,line.g.length-1).split("},{");
		for(var i in arr){
			var tmp = arr[i].split(",");
			g.push({x:parseInt(tmp[0]),y:parseInt(tmp[1])});
		}
		var color = "#AFAFAF";
		if($("#"+line.to).hasClass("activity")||$("#"+line.to).hasClass("complete")){
			color = "#018FDD";
		}else if($("#"+line.to).hasClass("rollback")){
			color = "#9ED7EC";
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
				ctx.beginPath();
				ctx.strokeStyle=color;
				ctx.moveTo(g[i-1].x, g[i-1].y);
				ctx.lineTo(g[i].x, g[i].y);
				ctx.stroke();
				ctx.closePath();
			}
		}
	} */
	function resizeCanvas() {  
		var canvas = $("#myCanvas");
	    canvas.attr("width", $workArea.width());  
	    canvas.attr("height", $workArea.height());  
	};  
	function autoSize(key){
		resizeProcess(key);
		var wb = $workArea.width()/$workArea.parent().width(),
			hb = $workArea.height()/$workArea.parent().height(),
			rb;
		if(wb<hb){
			rb = 1/hb;
		}else{
			rb = 1/wb;
		}
		$workArea.css({
			MozTransform:"translate(-50%, -50%) scale("+rb+","+rb+")",  
		  	WebkitTransform:"translate(-50%, -50%) scale("+rb+","+rb+")",  
		  	OTransform:"translate(-50%, -50%) scale("+rb+","+rb+")",
		  	transform:"translate(-50%, -50%) scale("+rb+","+rb+")"
		});
	}
	function resizeProcess(key){
		//resizeCanvas();
		if(!key) return;
		var topObj = [],leftObj=[];
		var nodes = $workData[key].nodes;
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
		
		/* var canvas = document.getElementById("myCanvas"),ctx; 
		if(canvas.getContext){
			ctx = canvas.getContext("2d");
			ctx.lineWidth=3;
			ctx.lineCap="round";
		}  */
		
		var lines = $workData[key].lines;
		for(var j in lines){
			resizeAddLine(j,lines[j]);
		}
	}

	function resizeAddLine(id,line){
		var from = $nodeData[line.from],to = $nodeData[line.to];
		var sx = from.left+$nodeDom[line.from].width()/2,sy = from.top+$nodeDom[line.from].height()/2;
		var ex = to.left+$nodeDom[line.to].width()/2,ey = to.top+$nodeDom[line.to].height()/2;
		var g = [];
		var arr = line.g.substring(1,line.g.length-1).split("},{");
		for(var i in arr){
			var tmp = arr[i].split(",");
			g.push({x:parseInt(tmp[0]),y:parseInt(tmp[1])});
		}
		var className = "line-default";
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
				/* ctx.beginPath();
				ctx.strokeStyle=color;
				ctx.moveTo(g[i-1].x, g[i-1].y);
				ctx.lineTo(g[i].x, g[i].y);
				ctx.stroke();
				ctx.closePath(); */
			}
		}
	}
	//正则表达式获取地址栏参数
	function GetQueryString(name){
	     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	}
</script>
</head>
<body onselectstart="return false"  oncontextmenu="self.event.returnValue=false;" ondragstart="return false;">
	<div class="Cag-left">
		<h3><b>流程描述 / </b>流程编号<input class="Cagpic-search" type="text"><i class="i i-fangdajing"></i></h3>
		<div class="double-bounce">
			<div class="double-bounce1"></div>
			<div class="double-bounce2"></div>
		</div>
		<ul class="Cag-leftList">
		</ul>
	</div>
	<div class="Cagpic-right">
		<div class="Cagpic-Rbox">
			<div id = "modeler" >
				<canvas id="myCanvas"></canvas> 
			</div>
		</div>
		<div class="Cagpic-Btn"><input type="button" class="xq" value="选 取"><input type="button" class="gb" value="关 闭"></div>
	</div>
</body>
</html>
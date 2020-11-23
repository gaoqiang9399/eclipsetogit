var taskNode;
var point_start,point_end;//全局坐标变量
$(document).ready(function(){
	$.getJSON("./ztree/tree.json", function(data){ 
		var ztree = $.fn.zTree.init($("#tree"), setting, data);
		ztree.expandAll(true);
		var node = ztree.getNodeByParam("id", 1, null);
		$("#"+node.tId+"_a").hide();
		$("#"+node.tId+"_switch").hide();
		
		var nodes = ztree.getNodesByParam("level", "1", null);
		for(var i in nodes){
//			$("#"+nodes[i].tId+"_span").html("");
			$("#"+nodes[i].tId+"_span").css("width","100%");
			$("#"+nodes[i].tId+"_ico").addClass(nodes[i].img);
		}
	}); 
});
var setting = {
		view : {
			selectedMulti : false,//设置是否允许同时选中多个节点。
			showLine : false,//设置 zTree 是否显示节点之间的连线。
			showTitle: true// 显示 /隐藏 提示信息
		},
		data: {
			simpleData: {
				enable : true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: 0
			}
		},
		callback: {
			beforeClick: beforeClick,
			onMouseDown: zTreeOnMouseDown
		}
};

function beforeClick(treeId, treeNode) {
	if (treeNode.level == 0 ) {
		var zTree = $.fn.zTree.getZTreeObj("tree");
		zTree.expandNode(treeNode);
		return false;
	}
	return true;
}
function zTreeOnMouseDown(event, treeId, treeNode){
	if(treeNode==null||treeNode.pId==0)
		return;
	var $div = createSelectNodeDiv(treeNode);
	$div.hide();
	golFlow.$nowType = treeNode.type;
	$('body').bind("mousemove",function(event){
		taskNode = treeNode;
		var point = mousePos(event);
		$('#modeler').hover(function(){
			$div.find('#dropIcon').attr('src','./ztree/img/drop-yes.gif');
		},function(){
			$div.find('#dropIcon').attr('src','./ztree/img/drop-no.gif');
		});
		$div.css('left',point.x+10)
		.css('top',point.y+20);
		$div.show();
		$('#topDiv').html((point.x)+','+(point.y)+","+treeNode.name);
	});
	$('body').bind('mouseup',bodyMouseUp);
}
function bodyMouseUp(evt){
	$('body').unbind('mousemove');
	$('.ui-layout-center').unbind('mouseover');
	$('.ui-layout-center').unbind('mouseout');
	$("#SelectNodeDiv").remove();
	taskNode=null;
}
function mousePos(event){
	var e = event||window.event;
	return {
	x:e.clientX+document.body.scrollLeft+document.documentElement.scrollLeft,
	y:e.clientY+document.body.scrollTop+document.documentElement.scrollTop
	};
};
function createSelectNodeDiv(treeNode){
	var $img = $('<img/>');
	$img.attr('id','dropIcon')
	.attr('src','./ztree/img/drop-no.gif')
	.css("margin","3px 0 0");
	var $iconDiv = $('<a style="margin-left:5px;position:absolute;top:-1px;">'+treeNode.name+'</a>');
	$iconDiv.css('font',' 12px Arial,Helvetica,sans-serif')
	.css('height','21px')
	.css('lineHeight','21px')
	.css('width','80px')
	.css('background-color','#F1F1F1')
	.css('border-radius','3px')
	.css('float','right')
	.css('textAlign','center')
	.css('margin','2px');
	var $div = $('<div></div>');
	$div.attr('id','SelectNodeDiv')
	.css('z-index','100')
	.css('background-color','#FFF')
	.css('width','100px')
	.css('height','25px')
	.css('border','1px solid #DBDBDB')
	.css('border-radius','3px')
	.css('position','absolute');
	$div.append($img);
	$div.append($iconDiv);
	$('body').append($div);
	return $div;
}
function createModel(event){
//	var model = $('<div style="top:83px;left:120px;pointer-events: none;width:84px;height:42px;" id="1" class="GooFlow_item task"><svg width="100%" height="100%" viewBox="-18 -10 336 322" preserveAspectRatio="none"></svg></div>');
	var model = $('<div id="SelectNodeDiv" class="task-node" ><svg width="100%" height="100%" viewBox="-18 -10 336 322" preserveAspectRatio="none"></svg></div>');
	model.css({
		width:150,
		height:30
	});
	$("body").append(model);
	modelPosition(event,model);
	
	var options = modelNode.init({
		type : true,
		selector : model[0],
		speed : { resetor : 1500, active : 1500 },
		easing : { resetor : mina.elastic, active : mina.elastic  }
	});
	modelNode.tools.doAnimate(options,"active");
	dragModelStart(event,model[0]);			
	return model;
}
function getScrollTop() {  
    var scrollPos;  
    if (window.pageYOffset) {  
    scrollPos = window.pageYOffset; }  
    else if (document.compatMode && document.compatMode != 'BackCompat')  
    { scrollPos = document.documentElement.scrollTop; }  
    else if (document.body) { scrollPos = document.body.scrollTop; }   
    return scrollPos;   
}
function getScrollLeft() {  
    var scrollPos;  
    if (window.pageYOffset) {  
    scrollPos = window.pageYOffset; }  
    else if (document.compatMode && document.compatMode != 'BackCompat')  
    { scrollPos = document.documentElement.scrollLeft; }  
    else if (document.body) { scrollPos = document.body.scrollLeft; }   
    return scrollPos;   
}  
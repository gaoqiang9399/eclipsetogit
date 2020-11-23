var startX,startY,width,height,endwidth,endheight,$resizeD;
function mouseDown(evt){
	$resizeD = $(this).parent();
	$Div.css("top",$resizeD.css("top"))
	.css("left",$resizeD.css("left"))
	.css("width",$resizeD.css("width"))
	.css("height",$resizeD.css("height"));
	$("#freewall").append($Div);
	evt.stopPropagation();
    evt = evt.originalEvent;
    startX = evt.clientX;
    startY = evt.clientY;
    width = parseInt($Div.css("width").split("p")[0]);
    height = parseInt($Div.css("height").split("p")[0])
    $("body").bind("mousemove",mouseMove);
    $("body").bind("mouseup",mouseUp);
}
function mouseMove(evt){
	evt.stopPropagation();
    evt = evt.originalEvent;
    var endX = evt.clientX;
    var endY = evt.clientY;
    //$("#tt").html(endY-startY);
    endwidth = width+(endX-startX);
    endheight = height+(endY-startY);
    if(endwidth<150){
		endwidth = 150;
	}
	if(endheight<50){
		endheight =50;
	}
    $Div.css("width",endwidth).css("height",endheight);
    $Div.attr("data-width",endwidth).attr("data-height",endheight);
}
function mouseUp(){
	var width = $Div.width();
	var height = $Div.height();
	$resizeD.css("width",width+"px").css("height",height+"px");
    $resizeD.attr("data-width",width).attr("data-height",height);
    $Div.css("top",0)
	.css("left",0)
	.css("width","0px")
	.css("height","0px");
	$("body").unbind("mousemove",mouseMove);
    $("body").unbind("mouseup",mouseUp);
    //var position = $resizeD.position();
   // refBlock(position.top,position.left,$resizeD.width(),$resizeD.height());
    //$resizeD.removeAttr("data-position");
    wall.fitWidth();
    var cellId = $resizeD.attr("cellid");
    var ch = blockDatas[cellId];
    if(cellId&&ch&&ch.plugintype=="charts"){
    	var $chart = blockDatas[cellId].chart.chartObj;
    	setTimeout(function(){
			$chart.resize();
		}, 500);
    }
}

function onDrag($item,wall){
	var position = $item.position();
    var top = Math.round(position.top / runtime.cellH);
    var left = Math.round(position.left / runtime.cellW);
    var width = Math.round($item.width() / runtime.cellW);
    var height = Math.round($item.height() / runtime.cellH);
    top = Math.min(Math.max(0, top), runtime.limitRow - height);
    left = Math.min(Math.max(0, left), runtime.limitCol - width);
    wall.setHoles({top: top, left: left, width: width, height: height});
    wall.refresh();
}
function onDrop($item,wall){
	$("body .fw-float").bind("dblclick",{cell:$("body .fw-float")}, cellDbclick);
	$("body .fw-float .bottomright").bind("mousedown", mouseDown);
	$("body .fw-float .smartTrash.jt-down").bind("click", {cell:$("body .fw-float")} , delCell);
	$("body .fw-float .fa-refresh").bind("click", {cell:$("body .fw-float")} , refreshCell);
	var $info = $("body .fw-float .info");
	$info.parent().append("<div class='pluginBorder info_hover' style='background-color:transparent;position: absolute;display: none;'><a class='btn' onclick='edit(this)'><i class='icon-edit'></i>编辑</a></div>");
	$info.bind("mouseenter",{$info:$info},infoHoverIn);
	var pb = $info.parent().find(".pluginBorder").eq(0);
	pb.bind("mouseleave",{$info:$info},infoHoverOut);
	
	var position = $item.position();
	if(!position) return;
    var top = Math.round(position.top / runtime.cellH);
    var left = Math.round(position.left / runtime.cellW);
    var width = Math.round($item.width() / runtime.cellW);
    var height = Math.round($item.height() / runtime.cellH);
    top = Math.min(Math.max(0, top), runtime.limitRow - height);
    left = Math.min(Math.max(0, left), runtime.limitCol - width);
    $item.removeClass('fw-float');
    $item.css({
        zIndex: "auto",
        top: top * runtime.cellH,
        left: left * runtime.cellW
    });
    refBlock(top,left,width,height);
    runtime.holes = {};                
    $item.attr({
        "data-width": $item.width(),
        "data-height": $item.height(),
        "data-position": top + "-" + left
    });
    wall.refresh();
    //$item.removeAttr("data-position");
}
function refBlock(top,left,width,height){
	var x, y, key, oldDropId;
    for (y = 0; y < height; ++y) {
        for (x = 0; x < width; ++x) {
            key = (y + top) + "-" + (x + left);
            oldDropId = runtime.matrix[key];
            if (oldDropId && oldDropId != true) {
                $("#" + oldDropId).removeAttr("data-position");
            }
        }
    }      
}

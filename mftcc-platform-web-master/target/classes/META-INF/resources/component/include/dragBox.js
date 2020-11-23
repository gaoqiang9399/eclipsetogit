//拖拽的实现
var dragParams = {
	left: 0,
	top: 0,
	currentX: 0,
	currentY: 0,
	flag: false
};
//获取相关CSS属性
var getCss = function(o,key){
	return o.currentStyle? o.currentStyle[key] : document.defaultView.getComputedStyle(o,false)[key]; 	
};

/**
 * bar 触发拖拽对象
 * target 被拖拽对象
 */
var startDrag = function(bar, target, callback){
	if(getCss(target, "left") !== "auto"){
		dragParams.left = getCss(target, "left");
	}
	if(getCss(target, "top") !== "auto"){
		dragParams.top = getCss(target, "top");
	}
	//o是移动对象
	bar.onmousedown = function(event){
		dragParams.flag = true;
		if(!event){
			event = window.event;
			//防止IE文字选中
			bar.onselectstart = function(){
				return false;
			}  
		}
		var e = event;
		dragParams.currentX = e.clientX;
		dragParams.currentY = e.clientY;
	};
	document.onmouseup = function(){
		dragParams.flag = false;	
		if(getCss(target, "left") !== "auto"){
			dragParams.left = getCss(target, "left");
		}
		if(getCss(target, "top") !== "auto"){
			dragParams.top = getCss(target, "top");
		}
	};
	document.onmousemove = function(event){
		var e = event ? event: window.event;
		if(dragParams.flag){
			var nowX = e.clientX, nowY = e.clientY;
			var disX = nowX - dragParams.currentX, disY = nowY - dragParams.currentY;
			target.style.left = parseInt(dragParams.left) + disX + "px";
			target.style.top = parseInt(dragParams.top) + disY + "px";
		}
		
		if (typeof callback == "function") {
			callback(parseInt(dragParams.left) + disX, parseInt(dragParams.top) + disY);
		}
	}	
};
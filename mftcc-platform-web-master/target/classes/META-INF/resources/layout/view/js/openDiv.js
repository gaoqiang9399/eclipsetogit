/**
 * 大表单弹出框
 * @param {Object} url (打开页面的链接) 必填
 * @param {Object} title (显示提示的标题) 可选添 不填默认为”提示“
 * @param {Object} ctrlFlag 控制标志
 * 					   可填入 1).false 为关闭不刷新父页面
 * 							2).true/不填  默认为刷新  
 * 						    3).链接  关闭跳转到当前链接(并且为action链接)
 * @param {number} minW	窗口拖拽最小宽度，默认为 0px
 * @param {number} minH	窗口拖拽最小高度，默认为 0px
 */
window.openBigFormBAK = function(url,title,ctrlFlag,minW,minH){
	url = addMoveBack(url);
	var obj =  $("body");
		if(!obj.find("#bigFormShow").length>0){
			bigFromShowHtml(obj);
		}
		obj.find("#bigFormShowiframe").attr("src","");
		obj.find("#bigFormShowiframe").attr("src",url);
		if(title!=null&&title!=""){
			obj.find("#bigFormShow #myModalLabel").html(title);
		}
		obj.find("#bigFormShow").modal({
	        backdrop:false,
	        show:true,
	        keyboard: false
	    });
	    obj.find("#bigFormShow .close").unbind();
	    obj.find("#bigFormShow .close").click(function(){
	    	if(ctrlFlag!==undefined&&typeof(ctrlFlag) == "function"){
	    		ctrlFlag.call(this);
	    	}else if(ctrlFlag!==undefined&&ctrlFlag!="false"&&!ctrlFlag==false&&ctrlFlag.indexOf(".action")!=-1){
	    		ctrlFlag = ctrlFlag.replace(new RegExp("amp;","gm"),"");
	    		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",ctrlFlag);
	    	} else if(ctrlFlag===undefined||(ctrlFlag!="false"&&!ctrlFlag==false)){
	    		$(top.window.document).find(".pt-page-current").find("iframe")[0].contentWindow.location.reload();
	    	}
	    	$(this).parents("#bigFormShow").find("#bigFormShowiframe").remove();
	    	$(this).parents("#bigFormShow").remove();
	    });
	    var modal = obj.find("#bigFormShow .modal-dialog");
    	var divcss;
	    obj.find("#bigFormShow .zoomBtn").click(function(){
	    	if($(this).hasClass("i-zuidahua")){
	    		$(this).removeClass("i-zuidahua").addClass("i-huanyuan");
	    		//$(this).data("divcss",modal.attr("style"));
	    		/*modal.css({
	    			width:"100%",
	    			height:"100%",
	    			margin:"0",
	    			top:"0px",
	    			left:"0px"
	    		});*/
	    		divcss = {
	    				height:modal[0].offsetHeight,
	    				left:modal[0].offsetLeft,
	    				top:modal[0].offsetTop,
	    				width:modal[0].offsetWidth
	    		};
	    		$(this).data("divcss",divcss);
	    		modal.animate({
	    			width:"100%",
	    			height:"100%",
	    			margin:"0",
	    			top:"0px",
	    			left:"0px"
	    		});
	    	}else{
	    		$(this).removeClass("i-huanyuan").addClass("i-zuidahua");
	    		//modal.attr("style",$(this).data("divcss"));
	    		//modal.attr("style",$(this).data("divcss"));
	    		divcss = $(this).data("divcss");
	    		modal.animate(divcss);
	    	}
	    });
	    //拖拽
	    if(typeof(startDrag)=="function"){
	    	startDrag(obj.find("#bigFormShow .modal-header")[0],obj.find("#bigFormShow .modal-dialog")[0]);
	    }
	    if(typeof(startDragSize)=="function"){
	    	startDragSize(obj.find("#bigFormShow .modal-dialog")[0],minW,minH);
	    }
};
window.openBigForm = function(url,title,ctrlFlag,w,h,minW,minH){
	url = addMoveBack(url);
	dhccModalDialog.open(url,title,ctrlFlag,w,h,"400","300");
};

/**
 * 最小化事件
 * @param node 最小化DOM对象
 * @param bigForm 大表单jQuery对象
 * @param bigClass 大表单唯一标识
 * @param title	标题
 * @param ctrlFlag	大表单关闭回调参数
 */
function bindSubtract(node,bigForm,bigClass,title,ctrlFlag){
	bigForm.slideUp(500);
	var obj = $("body");
	var modal = bigForm.find(".modal-dialog");
	if(!obj.find(".bigFormUl").length>0){
		bigFormUlHtml(obj);
		obj.find(".bigFormUl .i-lajitong1").qtip({  
	        content: info,
	        position: { 
	        	my: 'bottom right', 
	        	at: 'top center' 
	    	},
	    	style: { 
	    		classes: 'ui-tooltip-blue' 
			}
	    }).bind("click",function(){
	    	$(".qtip").remove();
	    	var count = (obj.find(".bigFormUl li").length*200+500);
	    	obj.find(".bigFormUl li").each(function(i,node){
	    		$(this).animate({'width':"0px"},(i*200+300));
	    	});
	    	setTimeout(function(){
	    		obj.find(".bigFormUl").remove();
	    	}, count);
	    	$(".bigFormShow").each(function(){
	    		$(this).find("#bigFormShowiframe").remove();
	    	});
	    	$(".bigFormShow").remove();
	    }); 
	}
	var bigFormUl = obj.find(".bigFormUl").show();
	var $ul = bigFormUl.find("ul");
	var $li = $('<li id="'+bigClass+'_li"></li>');
	if(title!=null&&title!=""){
		$li.html("<span>"+title+"</span>");
		$li.attr("title",title);
	}
	var info = $("#pageInfoContent").html();
	var len = $ul.width()/122;
	if($ul.find("li").length<parseInt(len-1)){
		$ul.append($li);
		$ul.find(".more").remove();
		$li.qtip({  
		        content: info,
		        position: { 
		        	my: 'bottom left', 
		        	at: 'top center' 
		    	},
		    	style: { 
		    		classes: 'ui-tooltip-blue' 
				}
		    }); 
	}else{
		if($ul.find(".more").length<=0){
			$ul.append("<li class='more'><span>更多</span><ul></ul></li>");
		}
		$ul.find(".more ul").append($li);
		$li.qtip({  
	        content: info,
	        position: { 
	        	my: 'bottom right', 
	        	at: 'top center' 
	    	},
	    	style: { 
	    		classes: 'ui-tooltip-blue' 
			}
	    }); 
		$ul.find(".more ul").css({
			'bottom':($(document).height()-$ul.find(".more").offset().top)+"px",
			'left':($ul.find(".more").offset().left - 2)+"px"
		});
	}
	$li.append('<i class="i i-x2"></i>');
	$li.data("bigClass",bigClass);
	var divcss = {
			height:modal[0].offsetHeight,
			left:modal[0].offsetLeft,
			top:modal[0].offsetTop,
			width:modal[0].offsetWidth
	};
	$li.bind("click",function(){
		modal.css("margin","0px");
		modal.animate(divcss);
		bigForm.show(function(){
			$li.remove();
			if(bigFormUl.find("li").length<1){
				bigFormUl.hide();
			}
			$(node).bind("click",function(){
				$(this).unbind();
				bindSubtract(this,bigForm,bigClass,title,ctrlFlag);
			});
			if($ul.find(".more ul li").length<=0){
				$ul.find(".more").remove();
			}
			$("#"+$li.attr("aria-describedby")).remove();
		});
	});
	$li.find(".i-x2").bind("click",function(e){
		e = e || win.event;
		if(ctrlFlag!==undefined&&typeof(ctrlFlag) == "function"){
    		ctrlFlag.call(this);
    	}else if(ctrlFlag!==undefined&&ctrlFlag!="false"&&!ctrlFlag==false&&ctrlFlag.indexOf(".action")!=-1){
    		ctrlFlag = ctrlFlag.replace(new RegExp("amp;","gm"),"");
    		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",ctrlFlag);
    	} else if(ctrlFlag===undefined||(ctrlFlag!="false"&&!ctrlFlag==false)){
    		$(top.window.document).find(".pt-page-current").find("iframe")[0].contentWindow.location.reload();
    	}
		$("#"+$li.attr("aria-describedby")).remove();
		$li.remove();
		if(bigFormUl.find("li").length<1){
			bigFormUl.hide();
		}
    	bigForm.find("#bigFormShowiframe").remove();
    	bigForm.remove();
    	e.stopPropagation ? e.stopPropagation() : e.cancelBubble = true;
	});
}
/**
 * 主动关闭大表单
 */
window.cloesBigFormBak = function(url,callback,iframeName){
	url = addMoveBack(url);
	if(url!==undefined&&callback===undefined&&url.indexOf(".action")!=-1){
		url = url.replace(new RegExp("amp;","gm"),"");
		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
	}
	if(typeof(callback)== "function"){
		callback.call(this);
	}else if(iframeName!=undefined&&iframeName!=""){
		document.getElementById("a_iframe").contentWindow.document.getElementById(iframeName).contentWindow.location.reload();
	}else if(callback!==undefined&&callback=="iframepage"){
		document.getElementById("a_iframe").contentWindow.document.getElementById("iframepage").contentWindow.location.reload();
	}else if(callback!==undefined&&callback=="a_iframe"){
		document.getElementById("a_iframe").contentWindow.location.reload();
	}
	$("#bigFormShow").find("#bigFormShowiframe").remove();
	$("#bigFormShow").remove();
};
//第一个参数为固定参数 window.top.cloesBigForm(window,"","iframepage");
//传window
window.cloesBigForm = function(node,url,callback,iframeName){
	url = addMoveBack(url);
	try {
		if(url!==undefined&&url!==null&&callback===undefined&&url.indexOf(".action")!=-1){
			url = url.replace(new RegExp("amp;","gm"),"");
			$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
		}
		if(typeof(callback)== "function"){
			callback.call(this);
		}else if(iframeName!=undefined&&iframeName!=""){
			document.getElementById("a_iframe").contentWindow.document.getElementById(iframeName).contentWindow.location.reload();
		}else if(callback!==undefined&&callback=="iframepage"){
			document.getElementById("a_iframe").contentWindow.document.getElementById("iframepage").contentWindow.location.reload();
		}else if(callback!==undefined&&callback=="a_iframe"){
			document.getElementById("a_iframe").contentWindow.location.reload();
		}
	} catch (e) {
		console.error(e);
	} finally{
		dhccModalDialog.closeModal(node);
	}
};
window.showDialogView = function(url,callback){
	if(typeof(url)!==undefined&&typeof(callback)===undefined&&url.indexOf(".action")!=-1){
		url = url.replace(new RegExp("amp;","gm"),"");
		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
	}
	if(typeof(callback)== "function"){
		callback.call(this);
	}else if(callback!==undefined&&callback=="iframepage"){
		$(top.window.document).find(".pt-page-current").find("iframe")[0].contentWindow.location.reload();
	}
	$("#showDialog").find("#showDialogiframe").remove();
	$("#showDialog").remove();
}
window.reloadFream = function (obj){
	//$(top.window.document).find(".pt-page-current").find("iframe")[0].contentWindow.location.reload();
};
function bigFromShowHtml(obj,tempClass){
	var showHtml = "";
		showHtml +='<div class="modal fade  bs-example-modal-lg bigFormShow '+tempClass+'" id="bigFormShow" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="overflow:hidden;">';
		showHtml +='<div class="modal-dialog" role="document" style="margin:1% 5% 0 5%;width: 90%;height:95%">';
		showHtml +='<div class="modal-content" style="height:100%">';
		showHtml +='<div class="modal-header" style=" padding: 4px 10px;cursor: move;">';
		//showHtml +='<i class="i i-subtract" style="position: absolute;top: 6px;right: 60px; font-size: 16px; color: #cccccc;cursor: pointer;"></i>';
		showHtml +='<i class="i i-zuidahua zoomBtn" style="position: absolute;top: 6px;right: 33px; font-size: 16px; color: #cccccc;cursor: pointer;"></i>';
		showHtml +='<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
		showHtml +='<h4 class="modal-title" id="myModalLabel" style="color: #1185b9;">&nbsp;</h4>';
		showHtml +='</div>';
		showHtml +='<div class="modal-body" style="padding:0px 4px;height: calc(100% - 34px); ">';
		showHtml +='<iframe frameborder="no" border="0" id="bigFormShowiframe" src="" style=" height:100%; width: 100%;"></iframe>';
		showHtml +='</div>';
		showHtml +='</div>';
		showHtml +='</div>';
		showHtml +='</div>';
	 $(obj).append(showHtml);
}
function createShowDiaglogHtml(showDialogId,fontShow){
	if(showDialogId===undefined||showDialogId==""||showDialogId==null){
		showDialogId = "showDialog";
	}
	var showDialogHtml = "";
		showDialogHtml +='<div class="modal fade  bs-example-modal-lg showDialog" id="'+showDialogId+'" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">';
		showDialogHtml +='<div class="modal-dialog" role="document" style="margin:0px;width: 90%;">';
		showDialogHtml +='<div class="modal-content" style="height:96%">';
		showDialogHtml +='<div class="modal-header" style=" padding: 10px 10px;cursor: move;background: #F8F9FD;text-align: center;">';
		showDialogHtml +='<i class="i i-zuidahua zoomBtn" style="position: absolute;top: 15px;right: 45px; font-size: 21px; color: #cccccc;cursor: pointer;"></i>';
		showDialogHtml +='<button type="button" class="close" data-dismiss="modal" aria-label="Close" style="margin-top:4px"><i class="i i-x5" aria-hidden="true"></i></button>';
		if(fontShow!==undefined&&showDialogId!=""&&showDialogId!=null){
			showDialogHtml +='<i class="i i-fukuanjihua" style="font-size:20px;color:#333;margin-right:7px"></i>';
		}
		showDialogHtml +='<h4 class="modal-title" id="myModalLabel" style="color:#333;display:inline-block;font-size: 16px;">&nbsp;</h4>';
		showDialogHtml +='</div>';
		showDialogHtml +='<div class="modal-body" style="padding: 4px;height: calc(100% - 40px); ">';//暂时修改一下 看看是否有问题，影响
		showDialogHtml +='<iframe frameborder="no" border="0" id="'+showDialogId+'iframe" src="" scrolling="no" style="height: 100%; width: 100%;"></iframe>';
		showDialogHtml +='</div>';
		showDialogHtml +='</div>';
		showDialogHtml +='</div>';
		showDialogHtml +='</div>';
	return showDialogHtml;
}

function createShowDialog(url,title,sWidth,sHeight,callback,minW,minH){
	url = addMoveBack(url);
	var $obj = $("body");
	if(!$obj.find("#showDialog").length>0){
		var html = createShowDiaglogHtml();
		$obj.append(html);
	}
	$obj.find("#showDialog").css({"overflow":"hidden"});
	dhccModalDialog.pos($obj.find("#showDialog").find(".modal-dialog"),sWidth,sHeight);
	//$obj.find("#showDialog").find(".modal-dialog").css({"width":sWidth+"%","marginTop":"calc((100% - "+sHeight+"%) / 4)","height":"calc("+sHeight+"% - 35px)"});
	$obj.find("#showDialog").find(".modal-content").css("height","100%");
	$obj.find("#showDialogiframe").attr("src","");
	$obj.find("#showDialogiframe").attr("src",url);
	if(title!=null&&title!=""){
		$obj.find("#showDialog #myModalLabel").html(title);
	}
	$obj.find("#showDialog").modal({
        backdrop:false,
        show:true,
        keyboard: false
    });
	$obj.find("#showDialog").css("z-index","1024");
    $obj.find("#showDialog .close").unbind();
    $obj.find("#showDialog .close").click(function(){
    	if(typeof(callback)== "function"){
    		callback.call(this);
    	}else if(callback!==undefined&&callback=="iframepage"){
    		document.getElementById("a_iframe").contentWindow.document.getElementById("iframepage").contentWindow.location.reload();
    	}
    	$(this).parents("#showDialog").find("#showDialogiframe").remove();
    	$(this).parents("#showDialog").remove();
    });
    var modal = $obj.find("#showDialog .modal-dialog");
    var divcss;
    $obj.find("#showDialog .zoomBtn").click(function(){
    	if($(this).hasClass("i-zuidahua")){
    		$(this).removeClass("i-zuidahua").addClass("i-huanyuan");
    		divcss = {
    				height:modal[0].offsetHeight,
    				left:modal[0].offsetLeft,
    				top:modal[0].offsetTop,
    				width:modal[0].offsetWidth
    		};
    		$(this).data("divcss",divcss);
//    		$(this).data("divcss",modal.attr("style"));
    		/*modal.css({
    			width:"100%",
    			height:"100%",
    			margin:"0",
    			top:"0px",
    			left:"0px"
    		});*/
    		modal.animate({
    			width:"100%",
    			height:"100%",
    			margin:"0",
    			top:"0px",
    			left:"0px"
    		});
    	}else{
    		$(this).removeClass("i-huanyuan").addClass("i-zuidahua");
    		//modal.attr("style",$(this).data("divcss"));
    		divcss = $(this).data("divcss");
    		modal.animate(divcss);
    	}
    });
    
  //拖拽
    if(typeof(startDrag)=="function"){
    	startDrag($obj.find("#showDialog .modal-header")[0],$obj.find("#showDialog .modal-dialog")[0]);
    }
    if(typeof(startDragSize)=="function"){
    	startDragSize($obj.find("#showDialog .modal-dialog")[0],minW,minH);
    }
}

/*
 * B面任务弹出操作
 */
function createTaskShowDialog(url,title,sWidth,sHeight,data, callback){
	url = addMoveBack(url);
	var $obj = $("body");
	if(!$obj.find("#taskShowDialog").length>0){
		var html = createShowDiaglogHtml("taskShowDialog");
		$obj.append(html);
	}
    top.callbackInViewpointByApprove = callback;
	if(typeof(data)!="undefined" && data){
		$obj.find("#taskShowDialog").data("taskData",data);
	}
	$obj.find("#taskShowDialog").css("overflow","hidden");
	//$obj.find("#taskShowDialog").find(".modal-dialog").css({"width":sWidth+"%","height":sHeight+"%"});
	$obj.find("#taskShowDialog").find(".modal-content").css("height","100%");
	$obj.find("#taskShowDialog").attr("src","");
	$obj.find("#taskShowDialogiframe").attr("src",url);
	dhccModalDialog.pos($obj.find("#taskShowDialog").find(".modal-dialog"),sWidth,sHeight);
	if(title!=null&&title!=""){
		$obj.find("#taskShowDialog #myModalLabel").html(title);
	}
	$obj.find("#taskShowDialog").modal({
        backdrop:false,
        show:true,
        keyboard: false
    });
    $obj.find("#taskShowDialog .close").unbind();
    $obj.find("#taskShowDialog .close").click(function(){
        if (callback !== undefined && typeof(callback) == "function") {
            callback.call(this);
        } else {
            var iframeId = document.getElementById("a_iframe").id;
            if(iframeId!=null&&iframeId!=''&&iframeId!=undefined){
                document.getElementById("a_iframe").contentWindow.taskB.changeTaskSts(data);
            }else{
                document.getElementById("b1_iframe").contentWindow.taskB.changeTaskSts(data);
			}
            $(this).parents("#taskShowDialog").find("#taskShowDialogiframe").remove();
            $(this).parents("#taskShowDialog").remove();
        }

    });
    
    var modal = $obj.find("#taskShowDialog .modal-dialog");
    var divcss;
    $obj.find("#taskShowDialog .zoomBtn").click(function(){
    	if($(this).hasClass("i-zuidahua")){
    		$(this).removeClass("i-zuidahua").addClass("i-huanyuan");
    		divcss = {
    				height:modal[0].offsetHeight,
    				left:modal[0].offsetLeft,
    				top:modal[0].offsetTop,
    				width:modal[0].offsetWidth
    		};
    		$(this).data("divcss",divcss);
    		modal.animate({
    			width:"100%",
    			height:"100%",
    			margin:"0",
    			top:"0px",
    			left:"0px"
    		});
    	}else{
    		$(this).removeClass("i-huanyuan").addClass("i-zuidahua");
    		divcss = $(this).data("divcss");
    		modal.animate(divcss);
    	}
    });
  //拖拽
    if(typeof(startDrag)=="function"){
    	startDrag($obj.find("#taskShowDialog .modal-header")[0],$obj.find("#taskShowDialog .modal-dialog")[0]);
    }
    if(typeof(startDragSize)=="function"){
    	startDragSize($obj.find("#taskShowDialog .modal-dialog")[0]);
    }
}

function taskShowDialog(url){
	url = addMoveBack(url);
	createTaskShowDialog(url,"任务操作","99","100");
}

/**
 * 主动B面关闭大表单
 */
window.cloesTaskBigForm = function(url){
	if(typeof(url)!="undefined"&&url!=""&&url!=null){
		url = addMoveBack(url);
		var flag = false;
		if($("#taskShowDialog").length>0){
			var ajaxParam = {};
			if(url.indexOf("ActionAjax_")!=-1&&url.indexOf("?")!=-1){//ajax提交
				var urlParams = url.split("?");
				url = urlParams[0];
				$.each(urlParams[1].split("&"), function(index,val){
					var key = val.split("=")[0];
					var value = val.split("=")[1];
					ajaxParam[key] = value;
				});
			}else{
				url = encodeURI(url);
			}
			$.ajax({
				type: "post",
				url: url,
				async:false,
				data:ajaxParam,
				success: function(data) {
					if(data["flag"]=="success"){
						alert(data.msg,1);
						data = $("#taskShowDialog").data("taskData");
						setTimeout(function(){
                            var iframeId = document.getElementById("a_iframe").id;
                            if(iframeId!=null&&iframeId!=''&&iframeId!=undefined){
                                document.getElementById("a_iframe").contentWindow.taskB.changeTaskSts(data);
                            }else{
                                document.getElementById("b1_iframe").contentWindow.taskB.changeTaskSts(data);
                            }
							// document.getElementById("b1_iframe").contentWindow.taskB.changeTaskSts(data);
						},1000);
						$("#taskShowDialog").find("#taskShowDialogiframe").remove();
						$("#taskShowDialog").remove();
					}else if(data["flag"]=="error"){
						alert(data.msg,0);
					}
				}
			});
			flag = true;
		}
		if(!flag){
			document.getElementById("a_iframe").src =url;
		}
	}
    var iframeId = document.getElementById("a_iframe").id;
    if(iframeId!=null&&iframeId!=''&&iframeId!=undefined){
        document.getElementById("a_iframe").contentWindow.taskB.changeTaskSts($("body").find("#taskShowDialog").data("taskData"));
    }else{
        document.getElementById("b1_iframe").contentWindow.taskB.changeTaskSts($("body").find("#taskShowDialog").data("taskData"));
    }
	// document.getElementById("b1_iframe").contentWindow.taskB.changeTaskSts($("body").find("#taskShowDialog").data("taskData"));
	$("body").find("#taskShowDialog").find("#taskShowDialogiframe").remove();
	$("body").find("#taskShowDialog").remove();
};
window.cloesShowDialog = function(callback){
	if(typeof(callback)== "function"){
		callback.call(this);
	}else if(callback!==undefined&&callback=="iframepage"){
		document.getElementById("a_iframe").contentWindow.document.getElementById("iframepage").contentWindow.location.reload();
	}
	$("#showDialog").find("#showDialogiframe").remove();
	$("#showDialog").remove();
};

window.showDialog = function(url,title,sWidth,sHeight,callback){
	url = addMoveBack(url);
	createShowDialog(url,title,sWidth,sHeight,callback);
};

function addMoveBack(url){
	if(url!=null&&url!=undefined&&url!=""){
		if(url.indexOf("?")>0){
			url+="&moveBack=true";
		}else{
			url+="?moveBack=true";
		}
	}
	return url;
}

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
 * 专门为弹出框定制的
 * bar 触发拖拽对象
 * target 被拖拽对象
 */
var startDrag = function(bar, target, callback){
	var oDiv;
	//target是移动对象
	bar.onmousedown = function(event){
		if(getCss(target, "left") !== "auto"){
			dragParams.left = getCss(target, "left");
		}
		if(getCss(target, "top") !== "auto"){
			dragParams.top = getCss(target, "top");
		}
		var dragDiv = document.getElementById("dragDivCell");
	    if (dragDiv != null){
	    	dragDiv.parentNode.removeChild(dragDiv);
	    }
		oDiv=document.createElement("div");
		oDiv.style.width=getCss(target, "width");
		oDiv.style.height=target.offsetHeight-bar.offsetHeight+"px";
		oDiv.style.top=target.offsetTop+bar.offsetHeight+"px";
		oDiv.style.left=target.offsetLeft+"px";
		oDiv.style.zIndex="999999";
		oDiv.style.position="absolute";
		oDiv.style.backgroundColor="#E1E1E1";
		oDiv.style.filter = "alpha(opacity=30)";
		oDiv.style.opacity = "0.3";
		oDiv.id = "dragDivCell";
		document.body.appendChild(oDiv);
		dragParams.flag = true;
		if(!event){
			event = window.event;
			//防止IE文字选中
			bar.onselectstart = function(){
				return false;
			};  
			document.body.onselectstart = function(){
				return false;
			};
		}
		var e = event;
		dragParams.currentX = e.clientX;
		dragParams.currentY = e.clientY;
		document.onmouseup = function(){
			var dragDiv = document.getElementById("dragDivCell");
		    if (dragDiv != null){
		    	dragDiv.parentNode.removeChild(dragDiv);
		    }
		    if(target.offsetLeft<=0){
				target.style.left = "0px";
				target.style.marginLeft = "0px";
		    }
		    if(target.offsetTop<=0){
				target.style.top = "0px";
				target.style.marginTop = "0px";
			}
		    if((document.body.clientHeight-target.offsetTop)<=bar.offsetHeight){
		    	target.style.marginTop = "0px";
		    	target.style.top = (document.body.clientHeight-bar.offsetHeight)+"px";
		    }
		    if((document.body.clientWidth-target.offsetLeft)<=50){
		    	target.style.marginLeft = "0px";
		    	target.style.left = (document.body.clientWidth-50)+"px";
		    }
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
				var returnX = 0,returnY = 0;
				target.style.left = parseInt(dragParams.left) + disX + "px";
				oDiv.style.left = target.offsetLeft+"px";
				returnX = parseInt(dragParams.left) + disX;
				
				target.style.top = parseInt(dragParams.top) + disY + "px";
				oDiv.style.top = target.offsetTop+bar.offsetHeight+"px";
				returnY = parseInt(dragParams.top) + disY;
			}
			
			if (typeof callback == "function") {
				callback(returnX, returnY);
			}
		};
	};
		
};

var dragSizeParams = {
		width: 0,
		height: 0,
		minW: 0,
		minH: 0,
		currentX: 0,
		currentY: 0,
		flag: false
	};
var startDragSize = function(target,minW,minH, callback){
	dragSizeParams.width = target.offsetWidth;
	dragSizeParams.height = target.offsetHeight;
	if(typeof(minW)=="number"){
		dragSizeParams.minW = minW;
	}
	if(typeof(minH)=="number"){
		dragSizeParams.minH = minH;
	}
	var oDiv=document.createElement("div");
	oDiv.style.width="20px";
	oDiv.style.height="20px";
	oDiv.style.cursor="se-resize";
	oDiv.style.borderRight="3px dashed #000000";
	oDiv.style.borderBottom="3px dashed #000000";
	oDiv.style.bottom="-1px";
	oDiv.style.right="-1px";
	oDiv.style.zIndex="999999";
	oDiv.style.position="absolute";
	oDiv.style.filter = "alpha(opacity=80)";
	oDiv.style.opacity = "0.8";
	oDiv.id = "dragSizeCell";
	target.appendChild(oDiv);
	var dragSizeflag = false;
	var bg;
	oDiv.onmousedown = function(event){
		var dragDiv = document.getElementById("dragDivCell");
	    if (dragDiv != null){
	    	dragDiv.parentNode.removeChild(dragDiv);
	    }
		dragSizeParams.width = target.offsetWidth;
		dragSizeParams.height = target.offsetHeight;
		bg=document.createElement("div");
		bg.style.width=getCss(target, "width");
		bg.style.height=target.offsetHeight+"px";
		bg.style.top=target.offsetTop+"px";
		bg.style.left=target.offsetLeft+"px";
		bg.style.zIndex="99999";
		bg.style.position="absolute";
		bg.style.backgroundColor="#E1E1E1";
		bg.style.filter = "alpha(opacity=30)";
		bg.style.opacity = "0.3";
		bg.style.userSelect = "none";
		bg.id = "dragDivCell";
		document.body.appendChild(bg);
		
		dragSizeParams.flag = true;
		if(!event){
			event = window.event;
			//防止IE文字选中
			target.onselectstart = function(){
				return false;
			}; 
			bg.onselectstart = function(){
				return false;
			}; 
			document.body.onselectstart = function(){
				return false;
			};
		}
		var e = event;
		dragSizeParams.currentX = e.clientX;
		dragSizeParams.currentY = e.clientY;
		
		document.onmouseup = function(){
			var dragDiv = document.getElementById("dragDivCell");
		    if (dragDiv != null){
		    	dragDiv.parentNode.removeChild(dragDiv);
		    }
			dragSizeParams.flag = false;
		};
		document.onmousemove = function(event){
			var e = event ? event: window.event;
			if(dragSizeParams.flag){
				var nowX = e.clientX, nowY = e.clientY;
				var disX = nowX - dragSizeParams.currentX, disY = nowY - dragSizeParams.currentY;
				if((dragSizeParams.width+ disX)<=minW){
					target.style.width = minW;
					bg.style.width = minW;
					returnX = minW;
				}else{
					target.style.width = (dragSizeParams.width+ disX) + "px";
					bg.style.width=(dragSizeParams.width+ disX) + "px";
					returnX =(dragSizeParams.width+ disX);
				}
				
				if((dragSizeParams.height + disY)<=minH){
					target.style.height = minH;
					bg.style.height = minH;
					returnY = minH;
				}else{
					target.style.height = (dragSizeParams.height + disY) + "px";
					bg.style.height=(dragSizeParams.height + disY) + "px";
					returnY = (dragSizeParams.height + disY);
				}
			}
			
			if (typeof callback == "function") {
				callback(returnX, returnY);
			}
		};
	};
	
};
function convertStyle(styleStr){
	var styleArr = styleStr.split(";");
	var styleObj = {};
	for(var x in styleArr){
		var arr = styleArr[x].split(":");
		styleObj[arr[0].trim()] = arr[1];
	}
}



//模拟window.showModalDialog
var dhccModalDialog = {};
dhccModalDialog.returnNodes  = {};
/*
 	* 父页面写法
	* <script type="text/javascript">
	*	 obj 触发事件的DOM对象
	*	 “#aDemo” 为自定义赋值对象，符合jquery选择器规则，可以为空
	*	 “b.html”目标url
	*	 "测试"窗口标题
	*	function openPop(obj){
	*		var returnVal ;
	*		dhccModalDialog.dialog(obj,"#aDemo","b.html","测试",function(val){
	*			alert(val);
	*		});
	*	}
	* </script>
*/
/*子页面写法
* <script type="text/javascript">
*	window参数必须传，为了获取当前页面的对象
*	var nodes = window.top.dhccModalDialog.returnVal(window,$("#demo1").val(),true);
* </script>
 */

dhccModalDialog.dialog = function(node,elem,url,title,sWidth,sHeight,callback){
	var temp = hex_md5(url);
	dhccModalDialog.returnNodes[temp]={
		parObj :node,
		targetObj : $(elem)[0],
		callback :callback
	};
	return dhccModalDialog.open(url,title,"",sWidth,sHeight);
}
dhccModalDialog.closeModal= function(node){
	var str = node.frameElement.getAttribute("action");
	if(str==null){
		try{
			node = node.parent.window;
			str = node.frameElement.getAttribute("action");
		}catch(e){}
	}
	if(str!=null){
		var temp = hex_md5(node.frameElement.getAttribute("action"));
		var dhccClass = "bigForm"+temp;
		var dhccModal = $("body").find("."+dhccClass);
		$(dhccModal.find("#bigFormShowiframe")[0].contentWindow.document).find("iframe").each(function(){
			$(this).remove();
		});
		dhccModal.find("#bigFormShowiframe").remove();
		dhccModal.remove();
		dhccModalDialog.hideBg();
	};
}
dhccModalDialog.returnVal= function(node,val,flag){
	var temp = hex_md5(node.frameElement.getAttribute("action"));
	var dhccClass = "bigForm"+temp;
	var dhccModal = $("body").find("."+dhccClass);
	var nodes = dhccModalDialog.returnNodes[temp];
	dhccModalDialog.setNodeVal(nodes.parObj,val);
	dhccModalDialog.setNodeVal(nodes.targetObj,val);
	if (flag) {
		$(dhccModal.find("#bigFormShowiframe")[0].contentWindow.document).find("iframe").each(function(){
			$(this).remove();
		});
		dhccModal.find("#bigFormShowiframe").remove();
		dhccModal.remove();
		dhccModalDialog.hideBg();
	}
	if (typeof(nodes.callback)=="function") {
		nodes.callback.call(this,val);
	}
	return nodes;
}
dhccModalDialog.setNodeVal =function(node,val){
	if(node!=null&&typeof(node)!="undefined"){
		var _prop =  /textarea|input/.test(node.tagName.toLocaleLowerCase()) ? 'value' : 'innerHTML';
		node[_prop] = val;
	}
}
dhccModalDialog.open = function(url,title,callback,mWidth,mHeight,minW,minH,options){
	dhccModalDialog.showBg();
	var dhccModal;
	//获取唯一Class
	var dhccClass = "bigForm"+hex_md5(url);
	var $body =  $("body");
	var info = $("#pageInfoContent").html();
    var dhccModalHead;
	//判断是否已经存在
	if($body.find("."+dhccClass).length==0){
		//初始化弹出窗结构
		dhccModalDialog.html($body,dhccClass);
		//获取弹出jquery对象
		dhccModal = $body.find("."+dhccClass);
		dhccModal.data("info",info);
		//计算大小位置
		dhccModalDialog.pos(dhccModal,mWidth,mHeight);
		//判断是否传入标题参数
		if(title!=null&&title!=""){
			dhccModal.find("#myModalLabel").html(title);
		}
		dhccModalHead = dhccModal.find(".dhcc-modal-header");
		dhccModalHead.click(function(){
			$(".dhccModalDialog").css("z-index","1024");
			dhccModal.css("z-index","1024");
		})
		//点击关闭按钮，触发事件
		dhccModalHead.find(".i-x5").click(function(){
			try{
		    	if(callback!==undefined&&typeof(callback) == "function"){
		    		callback.call(this);
		    	}else if(callback!==undefined&&callback=="iframepage"){
		    		if(document.getElementById("a_iframe").contentWindow.document.getElementById("iframepage")){
		    			document.getElementById("a_iframe").contentWindow.document.getElementById("iframepage").contentWindow.location.reload();
		    		}
		    	}else if(callback!==undefined&&callback!="false"&&!callback==false&&callback.indexOf(".action")!=-1){
		    		callback = callback.replace(new RegExp("amp;","gm"),"");
		    		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",callback);
		    	} else if(callback===undefined||(callback!="false"&&!callback==false)){
		    		$(top.window.document).find(".pt-page-current").find("iframe")[0].contentWindow.location.reload();
		    	}
			}catch(err){
				console.warn(err);
			}finally{
				//  contentWindow不存在，会报错，加个判断 By LiuYF
				if (dhccModal.find("#bigFormShowiframe")[0].contentWindow) {
					$(dhccModal.find("#bigFormShowiframe")[0].contentWindow.document).find("iframe").each(function(){
						$(this).remove();
					});
				}
		    	dhccModal.find("#bigFormShowiframe").remove();
		    	dhccModal.remove();
		    	dhccModalDialog.hideBg();
			}
	    });
		//最大化
		dhccModalHead.find(".zoomBtn").click(function(){
            var divcss;
	    	if($(this).hasClass("i-zuidahua")){
	    		$(this).removeClass("i-zuidahua").addClass("i-huanyuan");
	    		divcss = {
	    				height:dhccModal[0].offsetHeight,
	    				left:dhccModal[0].offsetLeft,
	    				top:dhccModal[0].offsetTop,
	    				width:dhccModal[0].offsetWidth,
	    				"opacity":"1"
	    		};
	    		$(this).data("divcss",divcss);
	    		dhccModal.animate({
	    			width:"100%",
	    			height:"100%",
	    			margin:"0",
	    			top:"0px",
	    			left:"0px"
	    		});
	    	}else{
	    		$(this).removeClass("i-huanyuan").addClass("i-zuidahua");
	    		divcss = $(this).data("divcss");
	    		dhccModal.animate(divcss);
	    	}
	    });
		//最小化
		dhccModalHead.find(".i-subtract").bind("click",function(){
	    	$(this).unbind();
	    	dhccModalDialog.bindSubtract(this,dhccModal,dhccClass,title,callback);
	    	dhccModalDialog.hideBg();
	    });
	}else{
		dhccModal = $body.find("."+dhccClass).show(function(){
			if($body.find(".bigFormUl").length>0){
				var bigFormUl = $body.find(".bigFormUl").show();
				$("#"+dhccClass+"_li").remove();
				if(bigFormUl.find("li").length<1){
					bigFormUl.hide();
				}
			}
		});
		var divcss = dhccModal.data("divcss");
		dhccModal.show().animate(divcss,function(){
			if($body.find(".bigFormUl").length>0){
				var bigFormUl = $body.find(".bigFormUl").show();
				$("#"+dhccClass+"_li").remove();
				if(bigFormUl.find("li").length<1){
					bigFormUl.hide();
				}
			}
		})
		//最小化
		dhccModalHead = dhccModal.find(".dhcc-modal-header");
		dhccModalHead.find(".i-subtract").bind("click",function(){
	    	$(this).unbind();
	    	dhccModalDialog.bindSubtract(this,dhccModal,dhccClass,title,callback);
	    	dhccModalDialog.hideBg();
	    });
	}
	
	dhccModal.find("#bigFormShowiframe").attr("src","");
	dhccModal.find("#bigFormShowiframe").attr("src",url);
	dhccModal.find("#bigFormShowiframe").attr("action",url);
    //拖拽
    if(typeof(startDrag)=="function"){
    	startDrag(dhccModalHead[0],dhccModal[0]);
    }
    if(typeof(startDragSize)=="function"){
    	startDragSize(dhccModal[0],minW,minH);
    }
    return dhccModal;
}

dhccModalDialog.pos=function(node,w,h){
	var HH = $(window).height();
	var WW = $(window).width();
	var x,y,mW,mH = 0;
	if(typeof(w)=="undefined"){
		w = "90%";
	}
	if(typeof(h)=="undefined"){
		h = "90%";
	}
	//20170210 javelin add 可使用像素固定弹窗大小
	if(String(w).indexOf('px')>0 && String(h).indexOf('px')>0){
		mW = String(w).replace("px","");
		mH = String(h).replace("px","");
	}else{
		mW = parseInt(WW*parseFloat(String(w).replace("%","")/100));
		mH = parseInt(HH*parseFloat(String(h).replace("%","")/100));
	}	
	x = parseInt((WW-mW)/2);
	y = parseInt((HH-mH)/2);
	node.css({
		"width":mW,
		"height":mH,
		"top":y,
		"left":x
	})
}

//弹窗结构
dhccModalDialog.html=function(obj,tempClass){
	var showHtml = "";
	showHtml +='<div class="dhccModalDialog '+tempClass+'" id="'+tempClass+'">';
		showHtml +='<div class="dhcc-modal-header">';
		showHtml +='<i class="i i-x5"></i>';
			showHtml +='<i class="i i-zuidahua zoomBtn"></i>';
//			showHtml +='<i class="i i-subtract" ></i>';
			showHtml +='<h4 class="title" id="myModalLabel">&nbsp;</h4>';
		showHtml +='</div>';
		showHtml +='<div class="dhcc-modal-body">';
			showHtml +='<iframe frameborder="no" border="0" id="bigFormShowiframe" src="" style=" height:100%; width: 100%;" allowfullscreen></iframe>';
		showHtml +='</div>';
	showHtml +='</div>';
	$(obj).append(showHtml);
}
//
dhccModalDialog.bindSubtract = function(node,dhccModal,dhccClass,title,callback){
	var $body = $("body");
	dhccModal.addClass("hideBigForm");
	var dhccModalHead = dhccModal.find(".dhcc-modal-header");
	//最小化状态栏
	if(!$body.find(".bigFormUl").length>0){
		bigFormUlHtml($body);
		//删除全部按钮
		$body.find(".bigFormUl .i-lajitong1").qtip({  
	        content: info,
	        position: { 
	        	my: 'bottom right', 
	        	at: 'top center' 
	    	},
	    	style: { 
	    		classes: 'ui-tooltip-blue' 
			}
	    }).bind("click",function(){
	    	$(".qtip").remove();
	    	//当前窗口数
	    	var count = ($body.find(".bigFormUl li").length*200+500);
	    	$body.find(".bigFormUl li").each(function(i,node){
	    		$(this).animate({'width':"0px"},(i*200+300));
	    	});
	    	setTimeout(function(){
	    		$body.find(".bigFormUl").remove();
	    	}, count);
	    	$(".dhccModalDialog").each(function(){
	    		$(this).find("#bigFormShowiframe").remove();
	    	});
	    	$(".dhccModalDialog").remove();
	    	dhccModalDialog.hideBg();
	    }); 
		$body.find(".bigFormUl .i-more").bind("click",function(){
			var len = $(".dhccModalDialog").not(".hideBigForm").length;
			var row2 = len%2;
			var row3 = len%2;
			var ww = (window.innerWidth)/len;
			var hh = window.innerHeight-$body.find(".bigFormUl").outerHeight(true);
			$.each($(".dhccModalDialog").not(".hideBigForm"),function(i,node){
				$(node).animate({
					width:ww+"px",
					height:hh+"px",
					left:(ww*i)+"px",
					top:"0px"
				});
			});
		});
	}
	var bigFormUl = $body.find(".bigFormUl").show();
	var $ul = bigFormUl.find("ul.dhcc-sub-list");
	var $li = $('<li id="'+dhccClass+'_li"></li>');
	if(title!=null&&title!=""){
		$li.html("<span>"+title+"</span>");
		$li.attr("title",title);
	}
	var info = dhccModal.data("info");
	var len = $ul.width()/122;
	if($ul.find("li").length<parseInt(len-1)){
		$ul.append($li);
		$ul.find(".more").remove();
		$li.qtip({  
		        content: info,
		        position: { 
		        	my: 'bottom left', 
		        	at: 'top center' 
		    	},
		    	style: { 
		    		classes: 'ui-tooltip-blue' 
				}
		    }); 
	}else{
		if($ul.find(".more").length<=0){
			$ul.append("<li class='more'><span>更多</span><ul></ul></li>");
		}
		$ul.find(".more ul").append($li);
		$li.qtip({  
	        content: info,
	        position: { 
	        	my: 'bottom right', 
	        	at: 'top center' 
	    	},
	    	style: { 
	    		classes: 'ui-tooltip-blue' 
			}
	    }); 
		$ul.find(".more ul").css({
			'bottom':($(document).height()-$ul.find(".more").offset().top)+"px",
			'left':($ul.find(".more").offset().left - 2)+"px"
		});
	}
	$li.append('<i class="i i-x2"></i>');
	$li.data("bigClass",dhccClass);
	var divcss = {
			height:dhccModal[0].offsetHeight,
			left:dhccModal[0].offsetLeft,
			top:dhccModal[0].offsetTop,
			width:dhccModal[0].offsetWidth,
			"opacity":"1"
	};
	dhccModal.data("divcss",divcss);
	$li.data("dhccModal",dhccModal);
	$li.data("divcss",divcss);
	$li.data("node",node);
	$li.data("dhccClass",dhccClass);
	$li.data("title",title);
	$li.data("callback",callback);
	$li.data("info",info);
	$li.bind("click",function(){
		$(".dhccModalDialog").css("z-index","1023");
		dhccModal.removeClass("hideBigForm");
		dhccModalDialog.showBg();
		var _li = this;
		dhccModal.css({
			"margin":"0px",
			"top":$li.offset().top+"px",
			"left":$li.offset().left+"px",
			"width":$li[0].offsetWidth+"px",
			"height":$li[0].offsetHeight+"px",
			"z-index":"1024"
				});
		dhccModal.show().animate(divcss,function(){
			$li.remove();
			if(bigFormUl.find("li").length<1){
				bigFormUl.hide();
			}
			$(node).bind("click",function(){
				$(this).unbind();
				dhccModalDialog.bindSubtract(this,dhccModal,dhccClass,title,callback);
			})
			if($ul.find(".more ul li").length<=0){
				$ul.find(".more").remove();
			}
			$("#"+$li.attr("aria-describedby")).remove();
		})
		
	});
	$li.find(".i-x2").bind("click",function(e){
		e = e || win.event;
		if(callback===undefined||(callback!="false"&&!callback==false)){
    		$(top.window.document).find(".pt-page-current").find("iframe")[0].contentWindow.location.reload();
    	}
		$("#"+$li.attr("aria-describedby")).remove();
		$li.remove();
		if(bigFormUl.find("li").length<1){
			bigFormUl.hide();
		}
		$(dhccModal.find("#bigFormShowiframe")[0].contentWindow.document).find("iframe").each(function(){
			$(this).remove();
		});
		dhccModal.find("#bigFormShowiframe").remove();
		dhccModal.remove();
    	e.stopPropagation ? e.stopPropagation() : e.cancelBubble = true;
	});
	var liCss = {
			"margin":"0px",
			"top":$li.offset().top+"px",
			"left":$li.offset().left+"px",
			"width":$li[0].offsetWidth+"px",
			"height":$li[0].offsetHeight+"px",
			"opacity":"0.1"
				}
	dhccModal.animate(liCss,function(){
		$(this).hide();
	});
	dhccModalDialog.hideBg();
};
dhccModalDialog.showBg = function(){
	if($(".dhccModalDialog_bg").length==0){
		var $bg = $('<div class="dhccModalDialog_bg"></div>')
		$("body").append($bg);
		$(".dhccModalDialog_bg").css({
			"z-index":"1023",
			"top":"0px",
			"left":"0px",
			"right":"0px",
			"bottom":"0px",
			"display":"block",
			"position": "absolute"
		})
	}else{
		$(".dhccModalDialog_bg").show();
	}
}
dhccModalDialog.hideBg = function(flag){
	var len = $(".dhccModalDialog").length;
	var hidelen = $(".dhccModalDialog.hideBigForm").length;
	if(len == hidelen){
		$(".dhccModalDialog_bg").hide();
	}
	if(flag){
		$(".dhccModalDialog_bg").hide();
	}
}
dhccModalDialog.str2num = function(str){
	var str_in = escape(str);
	var num_out = "";
	for(i = 0; i < str_in.length; i++) {
		num_out += str_in.charCodeAt(i) - 23;
	}
	return num_out;
}

function  bigFormUlHtml(obj){
	var showHtml = "";
	showHtml +='<div style="z-index:1070;" class="bigFormUl" id="bigFormUl" >';
	showHtml +='<ul class="dhcc-sub-list"></ul>';
	showHtml +='<i title="一键布局" class="i i-more"></i>';
	showHtml +='<i title="删除全部" class="i i-lajitong1"></i>';
	showHtml +='</div>';
	$(obj).append(showHtml);
}
dhccModalDialog.alertSubtractLi = function(){
	var $body = $("body");
	var arr = [];
	if($body.find(".bigFormUl").length>0){
		var bigFormUl = $body.find(".bigFormUl");
		var $ul = bigFormUl.find("ul.dhcc-sub-list");
		var $lis = bigFormUl.find("li").not(".more");
		$.each($lis,function(i,li){
			arr.push($(li).data());
		});
		$ul.empty();
		var len = $ul.width()/122;
		$.each(arr,function(i,node){
			node.dhccModal.addClass("hideBigForm");
			var $li = $('<li id="'+node.dhccClass+'_li"></li>');
			if(node.title!=null&&node.title!=""){
				$li.html("<span>"+node.title+"</span>");
				$li.attr("title",node.title);
			}
			if(i<parseInt(len-1)){
				$ul.append($li);
				$ul.find(".more").remove();
				$li.qtip({  
					content: node.info,
					position: { 
						my: 'bottom left', 
						at: 'top center' 
					},
					style: { 
						classes: 'ui-tooltip-blue' 
					}
				});
			}else{
				if($ul.find(".more").length<=0){
					$ul.append("<li class='more'><span>更多</span><ul></ul></li>");
				}
				$ul.find(".more ul").append($li);
				$li.qtip({  
			        content: node.info,
			        position: { 
			        	my: 'bottom right', 
			        	at: 'top center' 
			    	},
			    	style: { 
			    		classes: 'ui-tooltip-blue' 
					}
			    });
			}
			$li.append('<i class="i i-x2"></i>');
			$li.data("bigClass",node.dhccClass);
			var divcss = {
					height:node.dhccModal[0].offsetHeight,
					left:node.dhccModal[0].offsetLeft,
					top:node.dhccModal[0].offsetTop,
					width:node.dhccModal[0].offsetWidth,
					"opacity":"1"
			};
			node.dhccModal.data("divcss",node.divcss);
			$li.data("dhccModal",node.dhccModal);
			$li.data("divcss",node.divcss);
			$li.data("node",node.node);
			$li.data("dhccClass",node.dhccClass);
			$li.data("title",node.title);
			$li.data("callback",node.callback);
			$li.data("info",node.info);
			$li.bind("click",function(){
				$(".dhccModalDialog").css("z-index","1023");
				node.dhccModal.removeClass("hideBigForm");
				dhccModalDialog.showBg();
				var _li = this;
				node.dhccModal.css({
					"margin":"0px",
					"top":$li.offset().top+"px",
					"left":$li.offset().left+"px",
					"width":$li[0].offsetWidth+"px",
					"height":$li[0].offsetHeight+"px",
					"z-index":"1024"
						});
				node.dhccModal.show().animate(node.divcss,function(){
					$li.remove();
					if(bigFormUl.find("li").length<1){
						bigFormUl.hide();
					}
					$(node.node).bind("click",function(){
						$(this).unbind();
						dhccModalDialog.bindSubtract(this,node.dhccModal,node.dhccClass,node.title,node.callback);
					})
					if($ul.find(".more ul li").length<=0){
						$ul.find(".more").remove();
					}
					$("#"+$li.attr("aria-describedby")).remove();
				})
				
			});
			$li.find(".i-x2").bind("click",function(e){
				e = e || win.event;
				if(node.callback===undefined||(node.callback!="false"&&!node.callback==false)){
		    		$(top.window.document).find(".pt-page-current").find("iframe")[0].contentWindow.location.reload();
		    	}
				$("#"+$li.attr("aria-describedby")).remove();
				$li.remove();
				if(bigFormUl.find("li").length<1){
					bigFormUl.hide();
				}
				$(node.dhccModal.find("#bigFormShowiframe")[0].contentWindow.document).find("iframe").each(function(){
					$(this).remove();
				});
				node.dhccModal.find("#bigFormShowiframe").remove();
				node.dhccModal.remove();
		    	e.stopPropagation ? e.stopPropagation() : e.cancelBubble = true;
			});
			var liCss = {
					"margin":"0px",
					"top":$li.offset().top+"px",
					"left":$li.offset().left+"px",
					"width":$li[0].offsetWidth+"px",
					"height":$li[0].offsetHeight+"px",
					"opacity":"0.1"
						}
			node.dhccModal.animate(liCss,function(){
				$(this).hide();
			});
		});
		if($ul.find(".more").length>0){
			$ul.find(".more ul").css({
				'bottom':($ul.find(".more").outerHeight(true))+"px",
				'left':($ul.find(".more").offset().left - 2)+"px"
			});
		}
	}
}
$(function(){
	$(window).resize(function(){
		dhccModalDialog.alertSubtractLi();
	});
})

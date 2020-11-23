function showDetail(obj,nodeName,group,assignee){
	var X = obj.offset().top+17; 
	var Y = obj.offset().left+40; 
	var nodeType = "";
	if(obj.hasClass("activity")){
		nodeType = "正在审批";
	}else if(obj.hasClass("complete")){
		nodeType = "审批通过";
	}else if(obj.hasClass("rollback")){
		nodeType = "审批打回";
	}else if(obj.hasClass("refused")){
		nodeType = "审批否决";
	}
	var div = $("<div id='processDetailDiv' style='z-index: 100000;pointer-events: none;cursor: default;position: absolute;border-style: solid;white-space: nowrap;transition: left 0.4s, top 0.4s;background-color: rgba(50, 50, 50, 0.498039);border-width: 0px;border-color: rgb(51, 51, 51);border-radius: 4px;color: rgb(255, 255, 255);padding: 5px; font-family: 微软雅黑, Arial, Verdana, sans-serif;font-size: 1.2em;max-width: 300px;min-width:200px'></div>");
	var str = "<span style='word-break: normal;width: auto;display: block;overflow: hidden;'>审批状态&#12288;:&#12288;"+nodeType+"</span><span style='word-break: normal;width: auto;display: block;overflow: hidden;'>节点名称&#12288;:&#12288;"+nodeName+"</span>";
	if(group!=""){
		str += "<span style='word-break: normal;width: auto;display: block;overflow: hidden;'>审批角色&#12288;:&#12288;"+group+"</span>";
	}
	str += "<span style='word-break: normal;width: auto;display: block;white-space: pre-wrap;word-wrap: break-word;overflow: hidden;'>审批用户&#12288;:&#12288;"+assignee+"</span>";
	if($("#processDetailDiv").length==0){
		div.html(str);
		$("body").append(div);
	}else{
		$("#processDetailDiv").html(str);
		$("#processDetailDiv").show();
	}
	X = X-$("#processDetailDiv").height()/2;
	$("#processDetailDiv").css("top",X+"px");
	$("#processDetailDiv").css("left",Y+"px");
	
	if($("body").width()<(Y+$("#processDetailDiv").width()+10)){
		var width = (Y+$("#processDetailDiv").width())-$("body").width();
		$("#processDetailDiv").css("left",(Y-width-10)+"px");
	}
	
}
function closeDetail(){
	$("#processDetailDiv").hide();
}


function showDetail4Top(obj,nodeName,group,assignee){
	var X = obj.offset().top+17; 
	var Y = obj.offset().left+40; 
	var div = $("<div id='processDetailDiv' style='z-index: 100000;pointer-events: none;cursor: default;position: absolute;border-style: solid;white-space: nowrap;transition: left 0.4s, top 0.4s;background-color: rgba(50, 50, 50, 0.498039);border-width: 0px;border-color: rgb(51, 51, 51);border-radius: 4px;color: rgb(255, 255, 255);padding: 5px; font-family: 微软雅黑, Arial, Verdana, sans-serif;font-size: 1.2em;max-width: 300px;min-width:200px'></div>");
	var str = "<span style='word-break: normal;width: auto;display: block;overflow: hidden;'>节点名称&#12288;:&#12288;"+nodeName+"</span>";
	if(group!=""){
		str += "<span style='word-break: normal;width: auto;display: block;overflow: hidden;'>审批角色&#12288;:&#12288;"+group+"</span>";
	}
	str += "<span style='word-break: normal;width: auto;display: block;white-space: pre-wrap;word-wrap: break-word;overflow: hidden;'>审批用户&#12288;:&#12288;"+assignee+"</span>";
	if($(window.top.document.body).find("#processDetailDiv").length==0){
		div.html(str);
		$(window.top.document.body).append(div);
	}else{
		$(window.top.document.body).find("#processDetailDiv").html(str);
		$(window.top.document.body).find("#processDetailDiv").show();
	}
	X = X-$(window.top.document.body).find("#processDetailDiv").height()/2;
	$(window.top.document.body).find("#processDetailDiv").css("top",X+"px");
	$(window.top.document.body).find("#processDetailDiv").css("left",Y+"px");
	
	if($("body").width()<(Y+$(window.top.document.body).find("#processDetailDiv").width()+10)){
		var width = (Y+$(window.top.document.body).find("#processDetailDiv").width())-$("body").width();
		$(window.top.document.body).find("#processDetailDiv").css("left",(Y-width-10)+"px");
	}
	
}
function closeDetail4Top(){
	$(window.top.document.body).find("#processDetailDiv").hide();
}

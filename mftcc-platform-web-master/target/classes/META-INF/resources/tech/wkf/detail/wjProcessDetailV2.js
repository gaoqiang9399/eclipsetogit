$(document).ready(function() {
	$("body").click(function(e){
		if($(e.target).parents(".item-tip-hover").length>0||$(e.target).hasClass("icon")){
			return;
		}else{
			$(".item-tip-hover").remove();
		}
	});
});
function showWkfFlow(dom,appNo){
	var $workArea = dom;
	var modelDom = $workArea.parent();
	var isApp = $workArea.attr("isApp");
	$.ajax({
		url:webPath+"/wkfChart/getWkfChartInfoAjax",
		type:'post',
		data:{appNo:appNo,isApp:isApp},
		dataType:'json',
		success:function(data){
			var jsonArray = data.jsonArray;
			var nodes = [];
			var _arr = [];
			var temp = [];
			$.each(jsonArray, function(k, v) {
				if(v.type=="start"){
					nodes.push(v);
					_arr.push(v.nextName);
				}
				if(nodes.length>0&&nodes[nodes.length-1].type=="decision"){
					nodes.pop();
					var nodesArr = getDecisionNode(_arr[_arr.length - 1],jsonArray);
					nodes.push(nodesArr);
					_arr.push(decisionId);
					//$workArea.parent().css("height","450px");
				}
				$.each(jsonArray, function(k1, v1) {
					if(v1.id == _arr[_arr.length - 1]) {
						if(decisionId){
							v1["decisionEnd"] = "decisionEnd";
							decisionId = null;
						}
						_arr.push(v1.nextName);
						nodes.push(v1);
					}
				});
				
			});
	//		getNodes(nodes,data);
			nodes.shift();
			nodes.pop();
			createFlows($workArea,nodes);
			$workArea.find(".line").eq(0).remove();
			if("true"==$workArea.attr("isApp")){
				modelDom.addClass("isApp");
				$(".isApp .item-time").remove();
				$(".isApp .div-time").remove();
				$(".isApp .shanxing").remove();
				$(".isApp .open-m").remove();
				$(".isApp .span-user").remove();
			}
			var tipHeight1 = 0;
			var tipHeight2 = 0;
			var tipCut= 0;
			$(".item-tip").each(function(){
				if(2%tipCut==0){
					if(tipHeight1<$(this).height()){
						tipHeight1 = $(this).height();
					}
				}else{
					if(tipHeight2<$(this).height()){
						tipHeight2 = $(this).height();
					}
				}
				tipCut++;
			});
			if("true"!=$workArea.attr("isApp")){
				modelDom.parents(".approval-process").css("height",100*jsonArray.length+"px");
				$workArea.css("height",modelDom.height());
				$workArea.find(".item").css("top",(tipHeight1+50)+"px").css("transform","translateY(0%)");
			}else{
				$workArea.css("height",modelDom.height());
			}
			if($.mCustomScrollbar) {
				modelDom.mCustomScrollbar({
					theme: "minimal-dark",
					horizontalScroll: true,
					scrollButtons: {
						enable: true
					},
					advanced: {
						updateOnBrowserResize: true,
						updateOnContentResize: true,
						autoExpandHorizontalScroll: true
					},
					callbacks:{
						onInit : function(){
							var activityObj = $workArea.find('.activity');
							if (activityObj.length > 0) {
								var posLeft = activityObj.offset()["left"] - (modelDom.width() / 2 ) ;
								if (posLeft <= 0) {
									// undefined就不滚动了。
									posLeft = undefined;
								}
								setTimeout(function(){
									modelDom.mCustomScrollbar("scrollTo", posLeft);
								}, 100);
							}
						}
					}
	
				});
			}
		}

	});
}
function getNodes(arr,data){
	if(arr){
		for(var i=0;i<arr.length;i++){
			$.each(data, function(k, v) {
				if(arr[i].nextName.indexOf(v.id)!=-1){
					if(arr[i]["nextNode"]&&!checkNode(arr[i]["nextNode"],v)){
						arr[i]["nextNode"].push(v);
					}else{
						arr[i]["nextNode"] = [v];
					}
				}
			});
			getNodes(arr[i]["nextNode"],data);
		}
	}
}

function checkNode(arr,node){
	for(var i=0;i<arr.length;i++){
		if(arr[i].id==node.id){
			return true;
		}
	}
	return false;
}

/*function getDecisionNode(node,data){
	var arr = [];
	var nextNameArr = node.nextName.split(",");
	$.each(data, function(k, v) {
		for(var str in nextNameArr){
			if(str == v.id){
				arr.push(v);
			}
		}
	});
	node.push(arr)
	return arr;
}*/
var decisionId = "";
function getDecisionNode(nextName,data){
	var all = [];
	var nextNameArr = nextName.split(",");
	var cut = 0;
	for(var str in nextNameArr){
		var nodesArr = [];
		var arr = [nextNameArr[str]];
		var flag = false;
		$.each(data, function(k, v) {
			$.each(data, function(k1, v1) {
				if(nodesArr.length>0&&nodesArr[nodesArr.length-1].type=="join"){
					decisionId = nodesArr[nodesArr.length-1].nextName;
					flag = true;
					return false;
				}
				if(arr[arr.length - 1]==v1.id){
					arr.push(v1.nextName);
					if(nodesArr.length==0){
						if(all.length==0){
							v1["decisionStart"] = "decisionStart-up";
						}else{
							v1["decisionStart"] = "decisionStart-down";
						}
					}
					nodesArr.push(v1);
				}
			});
			if(flag){
				flag = false;
				return false;
			}
		});
		nodesArr.pop();
		if(cut<nodesArr.length){
			cut = nodesArr.length;
		}
		all.push(nodesArr);
	}
	for(var i in all){
		if(cut>all[i].length){
			for(var c = 0; c<cut-all[i].length;c++){
				all[i].push({"name": "","type": "lineToNode","userName": ""});
				if(all[i][all[i].length-2].state=="completed"){
					all[i][all[i].length-1].state = "completed";
				}
			}
		}
	}
	return all;
}

function createFlows($workArea,nodes) {
	for(var x=0;x<nodes.length;x++) {
		if(nodes[x] instanceof Array){
			addNode($workArea,nodes[x],"arr");
		}else{
			addNode($workArea,nodes[x],"obj");
		}
	}
}
function addNode($workArea,node,flag){
	var $li;
    var $div;
	if(flag=="obj"){
		if(node.decisionEnd){
			$li = $("<li class='item'></li>");
			$div = $("<div class='item-model'><div name = 'decisionEnd-up' class='icon decisionEnd-up " + node.type + "'><span class='span'>" + node.name.replace(/\\/g, "") + "</span><span class='span-user'>" + node.userName + "</span></div></div>");
			$li.append($div);
			addStyle($workArea,node,$div.find(".icon"));
			$div = $("<div class='item-model'><div name = 'decisionEnd-down' class='icon decisionEnd-down " + node.type + "'><span class='span'>" + node.name.replace(/\\/g, "") + "</span><span class='span-user'>" + node.userName + "</span></div></div>");
			$li.append($div);
			addStyle($workArea,node,$div.find(".icon"));
			$workArea.append($li);
//			$workArea.css("width",($workArea.width()+$li.width())+"px");
			addTip($workArea,node,$div.find(".icon"));
		}else{
			$li = $("<li class='item'><div class='item-model'><div class='icon " + node.type + "'><span class='span'>" + node.name.replace(/\\/g, "")  + "</span><span class='span-user'>" + node.userName + "</span></div></div></li>");
			addStyle($workArea,node,$li.find(".icon"));
			$workArea.append($li);
//			$workArea.css("width",($workArea.width()+$li.width())+"px");
			addTip($workArea,node,$li.find(".icon"));
		}
	}else{
		var cut = node[0].length;
		for(var c=0;c<cut;c++){
			$li = $("<li class='item'></li>");
			$workArea.append($li);
//			$workArea.css("width",($workArea.width()+$li.width())+"px");
			var upNode;
			for(var i in node){
				$div = $("<div class='item-model'><div class='icon " + node[i][c].type + "'><span class='span'>" + node[i][c].name.replace(/\\/g, "") + "</span><span class='span-user'>" + node[i][c].userName + "</span></div></div>");
				$li.append($div);
				addStyle($workArea,node[i][c],$div.find(".icon"));
				if(i==0){
					upNode = node[i][c];
				}else{
					tipUp = false;
					addTip($workArea,node[i][c],$div.find(".icon"),-30);
					addTip($workArea,upNode,$div.parents(".item").find(".icon").eq(0),30);
				}
			}
		}
	}
}
var tipUp = false;
function addTip($workArea,node,$div,flag){
	if("true"==$workArea.attr("isApp")){
		return;
	}
	if(!node.state||node.state=="open"||node.name==""){
		return;
	}
//	var X = $div.offset().top; 
//	var Y = $div.offset().left; 
	var tip = $("<div class='item-tip'><div class='jian'></div></dvi>");
//	if(flag){
//		X = X-flag;
//	}
	var refusedClassName = "";
	if(node.state=="refused"){
		refusedClassName = "no";
	}
	tip.append("<span>审批时间：<span class='span-time'>"+node.end+"</span>历时约<span class='span-time'>"+getHours(node)+"</span>小时</span>");
	tip.append("<table><tr><td class='first'>意见：</td><td class='"+refusedClassName+"'>"+node.approveIdea+"</td></tr></table>");
	//tip.append("<span style='position: relative;display: block;'>意见:<span style='padding-right: 5px;margin-left: 27px;display: block;position: absolute;margin-top: -16px;' class='"+refusedClassName+"'>"+node.approveIdea+"</span></span>");
	$div.append(tip);
	if(tipUp){
		tip.css({
			"bottom":"57px",
			"left":"-120px"
		});
		tip.find(".jian").css("border","none");
		tip.find(".jian").css({
			"top":tip.height()+5+"px",
			"border-right": "1px solid #EFF0F2",
		    "border-bottom": "1px solid #EFF0F2"
		});
		tipUp = false;
	}else{
		tip.css({
			"top":"85px",
			"left":"-120px"
		});
		tipUp = true;
	}
}
function getHours(node){
	if(!node.create) return 0;
	var date1= node.create.replace(/-/g, '/');  //开始时间  
	var date2;
	if(node.end){
		date2 = new Date(node.end.replace(/-/g, '/')); 
	}else{
		date2 = new Date();
	}
    var date3 = date2.getTime() - new Date(date1).getTime();
    var hours=date3/1000/60/60;
    return hours.toFixed(1);
}
function addStyle($workArea,node,$div){
	var className = "";
	/*if(node.type == "lineToNode"){
		
	}else */
	if(node.state=="open"){
		var cut = 720;//12小时 720分钟
		var date1= node.create;  //开始时间  
	    var date2 = new Date();    //结束时间  
//		var date2 = new Date("2017-04-30 20:13:26");    //结束时间  
	    var date3 = date2.getTime() - new Date(date1).getTime();
	    var minutes=date3/1000/60;
	    var du = minutes*(360/cut);
	    $div.find(".span-user").remove();
		$div.addClass("activity");
    	$div.append('<div class="shanxing"><div class="sx1"></div><div class="sx2"></div></div>');
		$div.append("<div class='open-m'></div>");
		$div.append("<span class='item-time'>"+node.create+"</span>");
		$div.append("<div class ='div-time'>历时"+getHours(node)+"小时</div>");
		changeDeg(du,$div.find(".sx1"),$div.find(".sx2"));
		className = "line-complete";
	}else if(node.state=="completed"||node.id=="start"){
		$div.addClass("complete");
		className = "line-complete";
	}else if(node.state=="refused"){
		$div.addClass("refused");
		className = "line-complete";
	}else if(node.state=="rollbacked"){
		$div.addClass("rollback");
		className = "line-rollback";
	}else{
		className = "line-default";
		$div.find(".span-user").remove();
	}
	if(node.decisionEnd){
		className += " "+$div.attr("name");
	}
	if(node.decisionStart){
		className += " "+node.decisionStart;
	}
	$div.before('<div class="line ' + className + '"></div>');
	//添加事件
	if(node.type.indexOf("task") != -1 || node.type.indexOf("node") != -1) {
		$div.css("cursor", "pointer");
		$div.bind("click", function() {
			if(node.state){//未审批的节点不展示详情
				showDetail($workArea,$div, node);
			}
		});
		/*$div.bind("mouseout", function() {
			closeDetail();
		});
		$div.bind("click", function() {
			nodeOnClick(node);
		});*/
	}
}
function changeDeg(du,sx1,sx2){
	if(du<=180){
		sx1.css("transform","rotate("+(du-180)+"deg)");
	}else if(du>180&&du<=360){
		sx2.css("transform","rotate(180deg)");
		sx1.parents(".shanxing").css("background-color","#D5D5D5");
		sx1.css("background-color","#75DD30");
		sx2.css("background-color","#75DD30");
		sx1.css("transform","rotate("+(du-180-180)+"deg)");
	}else{
		sx1.hide();
		sx2.hide();
		sx1.parents(".shanxing").css("background-color","#E14A47");
		sx1.parents(".icon").find(".div-time").css("background-color","#E14A47");
	}
}
function optOpenTask(node){
//	alert(node.url);
//	location.href=node.url;
//	if(node.url!=""){
//		toNextBusPoint(node.url,node.name,node.id);
//	}
}

function showDetail($workArea,obj, node) {
	if("true"==$workArea.attr("isApp") &&(!node.state||node.state=="open")){
		optOpenTask(node);
		return;
	}
	if("true"==$workArea.attr("isApp")){
		return;
	}
	$(".item").css("z-index","1");
	obj.parents("li").css("z-index","100");
	$(".item-tip-hover").remove();
	var X = obj.offset().top; 
	var Y = obj.offset().left-150; 
	var tip = $("<div class='item-tip-hover'><div class='jian'></div></dvi>");
	if(Y<0){
		var sy = 0-Y;
		tip.find(".jian").css("left",(174-sy-5)+"px");
		tip.css({
			"top":"86px",
			"left":"-145px"
		});
	}else{
		tip.css({
			"top":"86px",
			"left":"-150px"
		});
	}
	var refusedClassName = "";
	if(node.state=="refused"){
		refusedClassName = "no";
	}
	
	tip.append("<span>审批时间：<span class='span-time'>"+node.end+"</span>历时约<span class='span-time'>"+getHours(node)+"</span>小时</span>");
	tip.append("<table><tr><td class='first'>意见：</td><td class='"+refusedClassName+"'>"+node.approveIdea+"</td></tr></table>");
	tip.append("<span>附件：</span>");
	tip.append("<br>");
	var table = $("<table class='tip-table'></table>");
	var cut = 1;
	var tr;
	//window.top.location.href = "DocUploadAction_fileDownload.action?docNo="+imgObj.docNo+"&docBizNo="+imgObj.docBizNo;
	if(node.attachment){
		for (var i = 0; i < node.attachment.length; i++) {
//			if(cut<2){
				tr = $("<tr></tr>");
//				cut++;
//			}else{
//				cut = 1;
//			}
			var strHtml = '<td class="appVal"><a href="/docUpload/fileDownload?docNo='+node.attachment[i].docNo+'&docBizNo='+node.attachment[i].docBizNo+'">'+node.attachment[i].docName+'</a></td>';
			tr.append(strHtml);
			table.append(tr);
		}
		tip.append(table);
	}else{
		tr = $('<tr><td>暂无附件</td></tr>');
		table.append(tr);
		tip.append(table);
	}
	
	obj.append(tip);
	/*if($("body").width() < (Y + $("#processDetailDiv").width() + 10)) {
		var width = (Y + $("#processDetailDiv").width()) - $("body").width();
		$("#processDetailDiv").css("left", (Y - width - 10) + "px");
	}*/

}

function closeDetail() {
//	$(".item-tip-hover").remove();
}
//正则表达式获取地址栏参数
function GetQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) return unescape(r[2]);
	return null;
}

function nodeOnClick(node) {
	window.top.alert("节点名称:" + node.name + "\r\n审批用户:" + node.userName, 2);
}
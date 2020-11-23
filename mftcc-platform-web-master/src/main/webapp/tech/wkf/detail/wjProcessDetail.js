var $workArea,isEnd = false;
var endObj = {
    "id": "end",
    "name": "结束",
    "group": "",
    "groupName": "",
    "user": "",
    "userName": "",
    "nextName": "",
    "type": "end"
};
$(document).ready(function() {
	showWkfFlow();
});
function showWkfFlow(){
	$workArea = $("#wj-modeler");
	var processInstanceId = GetQueryString("processInstanceId");
	var appNo = GetQueryString("appNo");
	var appWorkflowNo = GetQueryString("appWorkflowNo");
	var isTaskMenu = GetQueryString("isTaskMenu");
	$.getJSON("wkfdata.json", function(data) {
		var nodes = [];
		var _arr = [];
		var temp = [];
		$.each(data, function(k, v) {
			if(v.id=="start"){
				nodes.push(v);
				_arr.push(v.nextName);
			}
			if(nodes.length>0&&nodes[nodes.length-1].type=="fork"){
				var nodesArr = getForkNode(_arr[_arr.length - 1],data);
				nodes.push(nodesArr);
				nodes.push(endObj);
				return false;
			}
			$.each(data, function(k1, v1) {
				if(v1.id == _arr[_arr.length - 1]) {
					_arr.push(v1.nextName);
					nodes.push(v1);
				}
			});
			
		});
//		getNodes(nodes,data);
		console.log(nodes)
		createFlows(nodes);
		$workArea.find(".line").eq(0).remove();
		if($.mCustomScrollbar) {
			$("#modeler").mCustomScrollbar({
				theme: "minimal-dark",
				horizontalScroll: true,
				scrollButtons: {
					enable: true
				},
				advanced: {
					updateOnBrowserResize: true,
					updateOnContentResize: true,
					autoExpandHorizontalScroll: true
				}
			});
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

/*function getForkNode(node,data){
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
function getForkNode(nextName,data){
	var all = [];
	var nextNameArr = nextName.split(",");
	for(var str in nextNameArr){
		var nodesArr = [];
		var arr = [nextNameArr[str]];
		var flag = false;
		$.each(data, function(k, v) {
			$.each(data, function(k1, v1) {
				if(nodesArr.length>0&&nodesArr[nodesArr.length-1].type=="fork"){
					nodesArr.push(getForkNode(arr[arr.length - 1],data));
					flag = true;
					return false;
				}
				if(arr[arr.length - 1]==v1.id){
					arr.push(v1.nextName);
					nodesArr.push(v1);
				}
				if(nodesArr.length>0&&nodesArr[nodesArr.length-1].type=="end"){
					nodesArr.pop();
					flag = true;
					return false;
				}
			});
			if(flag){
				flag = false;
				return false;
			}
		});
		all.push(nodesArr);
	}
	return all;
}

function createFlows(nodes) {
	for(var x in nodes) {
		if(nodes[x] instanceof Array){
			addTaskNode(nodes[x],"arr");
		}else{
			addTaskNode(nodes[x],"obj");
		}
	}
}
function addNode(node,flag){
	var $li;
	if(flag=="obj"){
		$li = $("<li class='item'><div class='item-model'><div class='icon " + node.type + "'></div><span class='span'>" + node.name.replace(/\\/g, "") + "</span></div></li>");
		addStyle($li);
		addStyle($li.find(".icon"));
	}else{
		$li = $("<li class='item'></li>");
		for(var i in node){
			var $div = $("<div class='item-model'><div class='icon " + node.type + "'></div><span class='span'>" + node.name.replace(/\\/g, "") + "</span></div>");
			$li.append();
			addStyle($div.find(".icon"));
		}
		$workArea.append($li);
	}
}
function addStyle($div){
	var className = "";
	if(node.state=="open"){
		$div.addClass("activity");
		className = "line-complete";
	}else if(node.state=="complete"||node.id=="start"){
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
	}
	$div.before('<li class="line ' + className + '"></li>');
	//添加事件
	var nodeName = node.name.replace(/\\/g, "");
	var group = node.groupName;
	var assignee = node.userName;
	
	if(node.type.indexOf("task") != -1 || node.type.indexOf("node") != -1) {
		$div.css("cursor", "pointer");
		$div.bind("mouseover", function() {
			showDetail($div, nodeName, group, assignee);
		});
		$div.bind("mouseout", function() {
			closeDetail();
		});
		$div.bind("click", function() {
			nodeOnClick(node);
		});
	}
}


function showDetail(obj, nodeName, group, assignee) {
	var X = obj.offset().top + 17;
	var Y = obj.offset().left + 40;
	var nodeType = "";
	if(obj.hasClass("activity")) {
		nodeType = "正在审批";
	} else if(obj.hasClass("complete")) {
		nodeType = "审批通过";
	} else if(obj.hasClass("rollback")) {
		nodeType = "审批打回";
	} else if(obj.hasClass("refused")) {
		nodeType = "审批否决";
	}
	var div = $("<div id='processDetailDiv' style='z-index: 100000;pointer-events: none;cursor: default;position: absolute;border-style: solid;white-space: nowrap;transition: left 0.4s, top 0.4s;background-color: rgba(50, 50, 50, 0.498039);border-width: 0px;border-color: rgb(51, 51, 51);border-radius: 4px;color: rgb(255, 255, 255);padding: 5px; font-family: 微软雅黑, Arial, Verdana, sans-serif;font-size: 1.2em;max-width: 300px;min-width:200px'></div>");
	var str = "<span style='word-break: normal;width: auto;display: block;overflow: hidden;'>审批状态&#12288;:&#12288;" + nodeType + "</span><span style='word-break: normal;width: auto;display: block;overflow: hidden;'>节点名称&#12288;:&#12288;" + nodeName + "</span>";
	if(group != "") {
		str += "<span style='word-break: normal;width: auto;display: block;overflow: hidden;'>审批角色&#12288;:&#12288;" + group + "</span>";
	}
	str += "<span style='word-break: normal;width: auto;display: block;white-space: pre-wrap;word-wrap: break-word;overflow: hidden;'>审批用户&#12288;:&#12288;" + assignee + "</span>";
	if($("#processDetailDiv").length == 0) {
		div.html(str);
		$("body").append(div);
	} else {
		$("#processDetailDiv").html(str);
		$("#processDetailDiv").show();
	}
	X = X - $("#processDetailDiv").height() / 2;
	X = X < 0 ? 0 : X;
	$("#processDetailDiv").css("top", X + "px");
	$("#processDetailDiv").css("left", Y + "px");

	if($("body").width() < (Y + $("#processDetailDiv").width() + 10)) {
		var width = (Y + $("#processDetailDiv").width()) - $("body").width();
		$("#processDetailDiv").css("left", (Y - width - 10) + "px");
	}

}

function closeDetail() {
	$("#processDetailDiv").hide();
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
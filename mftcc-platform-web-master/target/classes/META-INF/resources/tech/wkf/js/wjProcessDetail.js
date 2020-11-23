var $nodeDom = {},
	$nodeData = {},
	$lineDom = {},
	activityname, completeArr = [],
	refusedArr = [],
	rollBackArr = [],
	rollbacked,
	topData, $workArea,taskAppNo;
function showWkfFlow(appNo,appWorkflowNo,processInstanceId){
	$workArea = $("#wj-modeler");
//	var processInstanceId = GetQueryString("processInstanceId");
//	var appNo = GetQueryString("appNo");
//	var appWorkflowNo = GetQueryString("appWorkflowNo");
	isTaskMenu = GetQueryString("isTaskMenu");
	taskAppNo = appNo;
	$.ajax({
		type : "POST",
		cache:false,
		async:true,
		url : "wkf_findByProcessInstanceId.action",
		data:{
			query:processInstanceId,
			appNo:appNo,
			appWorkflowNo:appWorkflowNo
		},
		success : function(data) {
			if(!data.value) return;
			topData = data;
			var nodes = data.value.nodes;
			var lines = data.value.lines;
			var _arr = ["start"];
			$.each(nodes, function(k, v) {
				$.each(lines, function(k1, v1) {
					if(v1.from == _arr[_arr.length - 1]) {
						_arr.push(v1.to);
					}
				});
			});
			createFlows(_arr, data);
	
			if($.mCustomScrollbar) {
				$("#modeler").mCustomScrollbar({
					theme: "minimal-dark",
					horizontalScroll: true,
//					scrollButtons: {
//						enable: true
//					},
					advanced: {
						updateOnBrowserResize: true,
						updateOnContentResize: true,
						autoExpandHorizontalScroll: true
					},
					callbacks:{
						onInit : function(){
							var posLeft = $('#'+activityname).offset()["left"] - ($('#modeler').width() / 2 ) ;
							if (posLeft <= 0) {
								// undefined就不滚动了。
								posLeft = undefined;
							}
							setTimeout(function(){
								$("#modeler").mCustomScrollbar("scrollTo", posLeft);
							}, 100);
						}
					}
				});
			}
		}
	});
}

function createFlows(seqs, data) {
	var nodes = data.value.nodes;
	console.log(data);
	for(var x = 0; x< seqs.length;x++) {
		addTaskNode(seqs[x], nodes[seqs[x]]);
	}
	$.each(nodes,function(i,v){
		if(data[i] && data[i].state == "open") {
			activityname = i;
		} else if(data[i] && data[i].state == "completed" || i == "start" || data[i] && data[i].state == "refused") {
			completeArr.push(i);
			if(data[i] && data[i].state == "refused") {
				refusedArr.push(i);
			}
		} else if(data[i] && data[i].state == "rollbacked") {
			rollBackArr.push(i);
		}

	});
	optActivity();
	optComplete();
	optRollBack();
	var lines = data.value.lines;
	for(var j in lines) {
		addTaskLine(j, lines[j]);
	}
}

function addTaskNode(id, node) {
	$nodeData[id] = node;
	$nodeDom[id] = $("<li class='item' id='" + id + "'><div class='icon " + node.type + "'></div><span class='span'>" + node.name.replace(/\\/g, "") + "</span></li>");
	$workArea.append($nodeDom[id]);

	//添加事件
	var nodeName = node.name.replace(/\\/g, "");
	var group = "";
	if(node.properties["appType-chooseRole"]) {
		group = node.properties["appType-chooseRole"].value[0].text.replace(/\\/g, "");
	}
	var assignee = "";
	if(topData[id]) {
		assignee = topData[id].assignee;
	}
	if(node.type.indexOf("task") != -1 || node.type.indexOf("node") != -1) {
		$nodeDom[id].css("cursor", "pointer");
		$nodeDom[id].bind("mouseover", function() {
			showDetail($nodeDom[id], nodeName, group, assignee);
		});
		$nodeDom[id].bind("mouseout", function() {
			closeDetail();
		});
		$nodeDom[id].bind("click", function() {
			nodeOnClick(id, nodeName, group, assignee, topData.value.nodes[id]);
		});
	}
}

function addTaskLine(id, line) {
	var from = $("#" + line.from),
		to = $("#" + line.to);
	var className = "line-default";
	if(($("#" + line.to).find(".icon").hasClass("activity") || $("#" + line.to).find(".icon").hasClass("complete"))) {
		if($("#" + line.from).find(".icon").hasClass("complete")) {
			if(topData[line.to] && topData[line.from]) {
				if(Number(topData[line.to].index) - Number(topData[line.from].index) == 1) {
					className = "line-complete";
				}
			}
		}
	} else if($("#" + line.to).find(".icon").hasClass("rollback")) {
		if($("#" + line.from).find(".icon").hasClass("activity") || $("#" + line.from).find(".icon").hasClass("rollback")) {
			if(topData[line.to] && topData[line.from]) {
				if(Number(topData[line.from].index) - Number(topData[line.to].index) == 1) {
					className = "line-rollback";
				}
			}
		}
	}

	if($("#" + line.from).find(".icon").hasClass("complete") && !$("#" + line.from).find(".icon").hasClass("fork") && $("#" + line.to).find(".icon").hasClass("end") && typeof(activityname) == "undefined") {
		className = "line-complete";
		$("#" + line.to).find(".icon").addClass("complete");
	}

	var nodes = topData.value.nodes;
	if(nodes[line.from].type == "start round") {
		className = "line-complete";
	}
	from.after('<li class="line ' + className + '"></li>');
}

//当前节点
function optActivity() {
	$("#" + activityname).find(".icon").addClass("activity");
}
//完成节点
function optComplete() {
	for(var i = 0; i < completeArr.length; i++) {
		$("#" + completeArr[i]).find(".icon").addClass("complete");
	}
}

function optRefused() {
	for(var i = 0; i < refusedArr.length; i++) {
		$("#" + refusedArr[i]).find(".icon").addClass("refused");
	}
}

function optRollBack() {
	for(var i = 0; i < rollBackArr.length; i++) {
		if(rollBackArr[i] == activityname) break;
		$("#" + rollBackArr[i]).find(".icon").addClass("rollback");
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

/**
 * 
 * @param nodeID 节点ID，即流程设计器中的节点名称。
 * @param nodeName 节点名称
 * @param group
 * @param assignee 审批用户
 * @param wkfNode
 */
function nodeOnClick(nodeID, nodeName, group, assignee, wkfNode) {
	console.log(nodeID);
	if(topData[nodeID] && topData[nodeID].state == "open") {
		var wkfUrl = "";
//		wkfUrl = wkfNode.properties.approve.value;
		//取出当前节点对应的审批链接
		$.ajax({
			type : "POST",
			cache:false,
			async:false,
			url : "wkf_getApproveUrl.action",
			data:{
				appNo:taskAppNo//传参
			},
			success : function(data) {
				wkfUrl = data.approveUrl;
				toNextBusPoint(wkfUrl,nodeName,nodeID);
			}
		});
		
	} else if(topData[nodeID] && topData[nodeID].state == "completed" || nodeID == "start" || topData[nodeID] && topData[nodeID].state == "refused") {
		console.log("completed");
		
		if(topData[nodeID] && topData[nodeID].state == "refused") {
			
		}
		
	} else if(topData[nodeID] && topData[nodeID].state == "rollbacked") {
		console.log("rollbacked");
		
	}
}
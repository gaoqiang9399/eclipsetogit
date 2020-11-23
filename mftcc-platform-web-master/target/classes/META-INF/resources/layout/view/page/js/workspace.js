var wall,taskCut = 0,wsData,isList = false;
$(document).ready(function(){
	initWorkSpace();
	
	
//	for(var i in data){
//		var $cell = $("#freewall .cell[celltype="+data[i].type+"]");
//		var len = ($cell.height()-200)/100*3+2;
//		var cellInfo = data[i].content;
//		$cell.find("h2").eq(0).html(cellInfo.length);
//		$cell.find(".list").html("");
//		for(var n = 0;n<len;n++){
//			$cell.find(".list").append("<li><span>"+cellInfo[n].text+"</span></li>")
//		}
//	}
});
function initWorkSpace(){
	$.ajax({
		url : realPath+"/bwmTaskRoleRel/getAll",
		type : "post",
		cache : false,
		async : true,
		dataType : "json",
		success : function(data) {
			wsData = data;
			resFreeWall(data);
		}
	});
}
function initFreeWall(){
	wall = new Freewall("#freewall");
	wall.reset({
		draggable: false,
		selector: '.cell',
		animate: true,
		fixSize: 0,
		gutterX: 8,
		gutterY: 8,
		cellW: $("#freewall").width()/4,
		cellH: $("#freewall").height()/3,
		onResize: function() {
			wall.fillHoles();
			wall.fitWidth();
		}
	});
	wall.fillHoles();
	wall.fitWidth();
}
function resFreeWall(data){
	var top=0,left=0,typeCut = 0;
	var $freewall = $("#freewall");
	initFreeWall();
	for(var i in data){
		var width = $freewall.width()+"px";
		var height = "30%";
		var len =4;
		if(data[i][0].layoutType=="1"){
			width = "25%";
		}else if(data[i][0].layoutType=="2"){
			width = "50%";
		}else{
			width = "25%";
			height = "60%";
			len = 10;
		}
		var cell = $("<div class='cell' taskNo='"+i+"' style='width:"+width+"; height: "+height+"; background-color: #f7f7f7; z-index:100;border:1px solid #dedede;' data-handle='.handle'><div class='cover'><div class='handle-line handle'><span>"+data[i][0].taskName+"</span><div class='numLine'><h2></h2></div></div><div class='info infoBoxSize'><ul class='list'></ul><a href='#' class='more font-small'>全部</a></div></div>");
		cell.find(".more").bind("click",{type:"all"},more);
		cell.find("h2").eq(0).html("("+data[i].length+")");
		taskCut += data[i].length;
		initUl(cell,data[i],len);
		
//		if(cell.find(".list li").length==0){
//			cell.find(".list").append("<li><span>暂无任务</span></li>");
//			cell.find("h2").eq(0).html("(0)");
//		}else{
//			cell.find("h2").eq(0).html("("+data[i].length+")");
//			taskCut += data[i].length;
//		}
		
		wall.appendBlock(cell);
	}
	$("#taskCut").html(taskCut);
	wall.refresh();
}
function initUl(cell,data,len){
	if(data.length==0){
		cell.find(".list").append("<li><span>暂无任务</span></li>");
		cell.find("h2").eq(0).html("(0)");
	}else{
		for(var i in data){
			if(len!=0&&i==len){
				break;
			}
			var content = data[i].pasContent;
			if(data[i].pasContent==""){
				if(data[i].pasTitle==""){
					cell.find(".list").append("<li><span>暂无任务</span></li>");
					cell.find("h2").eq(0).html("(0)");
					taskCut--;
					continue;
				}else{
					content = data[i].pasTitle;
				}
			}else{
				if(content.indexOf("<")>-1){
					content = content.substring(0,content.indexOf("<"));
				}
			}
			var li = $("<li><span>"+content+"</span></li>").appendTo(cell.find(".list"));
			if(cell.parents(".free-wall-open").length>0){
				li.bind("click",function(){
					isList = true;
					index = $(this).index();
					cell.find(".list").remove();
					liClick(cell,data[i]);
				});
			}else{
				isList = false;
				li.bind("click",{type:"li"},more);
			}
		}
	}
}
function liClick(cell,data){
	$cellOpen.find("h2").hide();
	var ajaxData = {};
	var urlParam = getUrlParam(data.pasUrl);
	var submitUrl = "";
	if(data.optType==1){
		cell.find(".info").css("bottom","10px");
		submitUrl = data.pasUrl;
		$('<iframe src="'+submitUrl+'" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%"></iframe>').appendTo(cell.find(".info"));
	}else{
		if(data.pasType==1){
			submitUrl = urlParam.submitUrl+"?taskId="+data.wkfTaskNo;
			ajaxData = urlParam;
		}else if(data.pasType==2){
			submitUrl = data.pasUrl;
			ajaxData.formId = data.formId;
			$.ajax({
				type : "POST",
				url : webPath+"/sysTaskInfo/getFormHtmlAjax",
				dataType : "json",
				data:ajaxData,
				success : function(jsonData) {
					var pasContent = $("<div class='task_content'>"+data.pasContent+"</div>");
					var task = $("<div class='task_ctrl'></div>");
					var form = $("<form></form>");
					form.append(jsonData.formHtml).append(addFormHidden(urlParam,form,data)).append(addBtn(data));
					if(form.find("input[name=taskId]").length>0){
						form.find("input[name=taskId]").val(data.wkfTaskNo);
					}
					task.append(form);
					btnEvent(task,data,submitUrl);
					pasContent.appendTo(cell.find(".info"));
					task.appendTo(cell.find(".info"));
				},
				error : function(xmlhq, ts, err) {
				}
			});
		}
	}
}
function btnEvent(elem,info,submitUrl){
	var $this = this;
	var $elem = $(elem);
	var $btns = $elem.find(".task_ctrl_div");
	$btns.find(".cancle").bind("click",function(){
		closePopup();
		return false;
	});
	$btns.find(".task_submit").bind("click",function(){
		var $obj = $(this).parents("form");
		var dataParam = JSON.stringify($obj.serializeArray()); 
		console.log("展开操作提交URL:"+submitUrl);
		jQuery.ajax({
			url:submitUrl,
			data:{ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					changeTaskSts(info);
					// alert("操作成功!");
				}else if(data.flag=="error"){
					if(data.flag!==undefined&&data.flag!=null&&data.flag!=""){
						alert(data.msg);
					}else{
						alert("操作失败!");
					}
				}
			},error:function(data){
				 alert("操作失败！");
			}
		});
		return false;
	});
}
function changeTaskSts(data) {
	var $this = this;
	if(data.pasSts=="1"){
		return false;
	}
	$.ajax({
		type : "POST",
		url : webPath+"sysTaskInfo/getByIdAjax",
		dataType : "json",
		data : {
			pasNo : data.pasNo
		},
		success : function(jsonData) {
			closeTask(jsonData);
			window.parent.b1_iframe.taskB.closeTask(jsonData);
		},
		error : function(xmlhq, ts, err) {
		}
	});
}
function closeTask(jsonData){
	if(jsonData){
		var info = jsonData.info;
		if(info.pasSts==1){
			wsData[$cellOpen.attr("taskNo")].splice(index,1);
			$cell.find(".list li").eq(index).remove();
			$cell.find("h2").eq(0).html("("+wsData[$cellOpen.attr("taskNo")].length+")");
			if(wsData[$cellOpen.attr("taskNo")].length==0){
				$cell.find(".list").append("<li><span>暂无任务</span></li>");
			}
			$("#taskCut").html(--taskCut);
			closePopup();
			window.parent.b1_iframe.taskB.closeTask(jsonData);
		}
	}else{
		wsData[$cellOpen.attr("taskNo")].splice(index,1);
		$cell.find(".list li").eq(index).remove();
		$cell.find("h2").eq(0).html("("+wsData[$cellOpen.attr("taskNo")].length+")");
		if(wsData[$cellOpen.attr("taskNo")].length==0){
			$cell.find(".list").append("<li><span>暂无任务</span></li>");
		}
		$("#taskCut").html(--taskCut);
		closePopup();
	}
}
function addFormHidden(param,elem,data){
	var $elem = $(elem);
	var html = "";
	$.each(param,function(key,val){
		if($elem.find("input[name="+key+"]").length>0){
			$elem.find("input[name="+key+"]").val(val);
		}else{
			html+='<input type="hidden" value="'+val+'" name="'+key+'">';
		}
	});
	return html;
}
function addBtn(data){
	var $html = $("<div class='task_ctrl_div'></div>");
	$html.append('<input class="task_ctrl_btn task_submit" type="button" value="提交"/>');
	$html.append('<input class="task_ctrl_btn cancle" type="button" value="取消"/>');
	return $html;
}
function getUrlParam(url){
	var paramObj = {};
	if(typeof(url)!="undefined"){
		var str = url.substring(url.indexOf("?")+1,url.length).split("&");
		for(var x in str){
			var obj = str[x].split("=");
			paramObj[obj[0]]=obj[1];
		}
	}
	return paramObj;
}
//临时 函数
function enterKey(){};
function textareaInputCount(obj){};
function showCharsInfo(obj){};
function hideCharsInfo(obj){};

function cloesTaskBigForm(url){
	$.ajax({
		type: "post",
		url: url,
		success: function(data) {
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	closeTask();
};
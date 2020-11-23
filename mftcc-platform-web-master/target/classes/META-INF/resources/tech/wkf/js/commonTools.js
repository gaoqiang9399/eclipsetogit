// 保存流程设计
function Export() {
	var saveUrl = realPath+"/wkfDesgin/save";
	var processId = GetQueryString("processId");
	var data = golFlow.exportData();
	if(typeof(data.process.properties)=="undefined"){
		prop.procsJson = golFlow.$process;
		prop.createPropDialog($(".cd-popup-container").eq(0),{className:"process"});
		showModelAlert();
		return;
	}
	var jsonData = JSON.stringify(data);
		jQuery.ajax({
			type : "POST",
			cache : false,
			async : false,
			url : saveUrl,
			data : {
				jsonData : jsonData
			},
			success : function(data) {
				data = JSON.parse(data);
				if(data.flag){
					alert(data.msg);
					golFlow.$process["properties"]["key"] = {id:"key",type:"String",value:data.key};
//					alert(golFlow.$process.properties.key.value)
				}else{
					alert(data.msg);
				}
			}
		});
}
function ExportAndStart(){
	var saveUrl = realPath+"/wkfDesgin/saveAndStart.action";
	save(saveUrl);
}
function save(saveUrl){
	var processId = GetQueryString("processId");
	var data = golFlow.exportData();
	if(typeof(data.process.properties)=="undefined"){
		prop.procsJson = golFlow.$process;
		prop.createPropDialog($(".cd-popup-container").eq(0),{className:"process"});
		showModelAlert();
		return;
	}
	var jsonData = JSON.stringify(data);
	console.log(data);
	jQuery.ajax({
		type : "POST",
		cache : false,
		async : false,
		url : saveUrl,
		data : {
			jsonData : jsonData
		},
		success : function(data) {
			data = JSON.parse(data);
			if(data.flag){
				alert(data.msg);
				golFlow.$process["properties"]["key"] = {id:"key",type:"String",value:data.key};
//					alert(golFlow.$process.properties.key.value)
			}else{
				alert(data.msg);
			}
		}
	});
}
function open(){
	 $('#wkfopen').click();
}
$(document).ready(function(){
	$("#wkfopen").change(function(){
		Reload();
    	$(".double-bounce").show();
	    $("#wkfopenform").ajaxSubmit({  
	        success:function(data){
	        	golFlow.loadData(data);
	        	$(".double-bounce").hide();
	        	$("#wkfopen").val("");
	        }  
	    });  
	});
});
function Reload() {
	golFlow.clearData();
}
function DownLoad(){
	var downloadUrl = realPath+"/wkfDesgin/create";
	var jsonData = JSON.stringify(golFlow.exportData());
	console.log(golFlow.exportData());
	jQuery.ajax({
		type : "POST",
		cache : false,
		async : true,
		url : downloadUrl,
		data : {
			jsonData : jsonData
		},
		success: function(data) {
			location.href = realPath+"/wkfDesgin/download?jsonData="+data.fileName;
		}
	});
}
var listData,selected;
function openList(){
	var openListUrl = realPath+"/wkfDesgin/list";
	$(".cd-popup-container").find(".property").remove();
	$(".cd-popup-container").find(".tc-main").remove();
	$(".cd-popup-container").addClass("openList");
	$('<div class="double-bounce-popup"><div class="double-bounce1"></div><div class="double-bounce2"></div></div>').appendTo($(".cd-popup-container"));
//	$(".double-bounce-popup").show();
	$(' <div class="prop-string"><a class="sreach">查询</a><div class="prop-string-value"><input type="text" value=""></div><div class="prop-string-title"><span>流程描述:</span></div></div>').appendTo($(".cd-popup-container"));
	$(' <span>打开业务流程</span>').appendTo($(".cd-popup-container"));
	var $table = $(' <div class="tc-main" unselectable="on" onselectstart="return false;" style="-moz-user-select:none;"></div>').appendTo($(".cd-popup-container"));
	var bottomBtn = $('<div class="tc-btn"><div class="tc-btnr"><a class="apply">确定</a><a class="cancel">取消</a></div></div>').appendTo($(".cd-popup-container"));;
	$('.cd-popup').find(".sreach").bind("click",function(){
		$(".plclsr").remove();
		var data = {
				description:$('.cd-popup .prop-string').find("input").val()
		};
		$(".double-bounce-popup").show();
		jQuery.ajax({
			type : "POST",
			cache : false,
			async : true,
			url : openListUrl,
			data : {
				jsonData : JSON.stringify(data)
			},
			success: function(data) {
				listData = data;
				$(".cd-popup .tc-mainul").html("");
				for(var i in data){
					var $li = $('<li><table width="100%" border="0" cellspacing="0" cellpadding="0" class="addtab"><tr></tr></table></li>').appendTo($(".cd-popup .tc-mainul"));
	 				$li.find("tr").append('<td width="25%">'+data[i].id+'</td>');
	 				$li.find("tr").append('<td width="25%">'+data[i].name+'</td>');
	 				$li.find("tr").append('<td width="10%">'+data[i].version+'</td>');
	 				$li.find("tr").append('<td width="40%">'+data[i].description+'</td>');
	 				$li.click(function(){
	 					$(".tc-mainul li").css("background-color","#efefef");
	 					$(this).css("background-color","#e3e9f1");
	 					selected = listData[$(this).index()];
	 				});
	 				$li.dblclick(function(){
	 					hideModelAlert();
	 					Reload();
	 					$(".double-bounce").show();
	 					var transitionEvent = whichTransitionEvent($('.cd-popup')[0]);
	 					$('.cd-popup').one(transitionEvent, function(){
	 	 		        	golFlow.loadData(selected.value);
	 	 		        	$(".double-bounce").hide();
	 					});
	 				});
				}
				$(".double-bounce-popup").hide();
			}
		});
		});
	$('.cd-popup .prop-string').find("input").bind('keypress',function(event){
        if(event.keyCode == "13")    
        {
        	$('.cd-popup').find(".sreach").click();
        }
    });
	bottomBtn.find(".cancel").bind("click",function(){
		hideModelAlert();
		});
		bottomBtn.find(".apply").bind("click",function(){
			hideModelAlert();
			Reload();
		$(".double-bounce").show();
		var transitionEvent = whichTransitionEvent($('.cd-popup')[0]);
		$('.cd-popup').one(transitionEvent, function(){
		        	golFlow.loadData(selected.value);
		        	$(".double-bounce").hide();
			});
		});
	var $header = $('<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tabletit"><tr></tr></table>').appendTo($table);
	$('<td width="25%">选择</td>').appendTo($header.find("tr").eq(0));
	$('<td width="25%">流程名称</td>').appendTo($header.find("tr").eq(0));
	$('<td width="10%">流程版本</td>').appendTo($header.find("tr").eq(0));
	$('<td width="40%">流程描述</td>').appendTo($header.find("tr").eq(0));
	$('<td width="17px" style="display: block;min-width: 17px;">&nbsp</td>').appendTo($header.find("tr").eq(0));
	$table.append("<div class='plclsr' style=' color: #889aad;font-size: 14px;font-weight: bold;position: absolute;top: calc(50% - 15px);left: calc(50% - 80px);'>请点击[查询]按钮进行搜索...</div>")
	var $ul = $('<ul class="tc-mainul"></ul>').appendTo($table);
	showModelAlert();
	var transitionEvent = whichTransitionEvent($('.cd-popup')[0]);
	/*$('.cd-popup').one(transitionEvent, function(){
		jQuery.ajax({
			type : "POST",
			cache : false,
			async : true,
			url : openListUrl,
			success: function(data) {
				listData = data;
				for(var i in data){
					var $li = $('<li><table width="100%" border="0" cellspacing="0" cellpadding="0" class="addtab"><tr></tr></table></li>').appendTo($(".tc-mainul"));
	 				$li.find("tr").append('<td width="25%">'+data[i].id+'</td>');
	 				$li.find("tr").append('<td width="25%">'+data[i].name+'</td>');
	 				$li.find("tr").append('<td width="10%">'+data[i].version+'</td>');
	 				$li.find("tr").append('<td width="40%">'+data[i].description+'</td>');
	 				$li.click(function(){
	 					$(".tc-mainul li").css("background-color","#efefef");
	 					$(this).css("background-color","#e3e9f1");
	 					selected = listData[$(this).index()];
	 				});
	 				$li.dblclick(function(){
	 					hideModelAlert();
	 					Reload();
	 					$(".double-bounce").show();
	 					var transitionEvent = whichTransitionEvent($('.cd-popup')[0]);
	 					$('.cd-popup').one(transitionEvent, function(){
	 	 		        	golFlow.loadData(selected.value);
	 	 		        	$(".double-bounce").hide();
	 					});
	 				});
				}
				$(".double-bounce-popup").hide();
			}
		});
	});*/
}
/* 探测浏览器种类 */
function whichTransitionEvent(el){
    var t;
    var transitions = {
      'transition':'transitionend',
      'OTransition':'oTransitionEnd',
      'MozTransition':'transitionend',
      'WebkitTransition':'webkitTransitionEnd'
    };

    for(t in transitions){
        if( el.style[t] !== undefined ){
            return transitions[t];
        }
    }
}

function showModelAlert(){
	$('.cd-popup').addClass('is-visible');
	$(".ui-layout-pane").addClass("row");
}
function hideModelAlert(){
	$(".cd-popup-container").html('<div class="qutton_close" onclick="hideModelAlert();"></div>');
	$('.cd-popup').removeClass('is-visible');
	$(".ui-layout-pane").removeClass("row");
	$("#menuContent").remove();
}

Date.prototype.format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function cut(){
	
}
function copy(){
	
}
function paste(){
	
}
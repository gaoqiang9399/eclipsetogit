var isOnFreeWall = false;
var blockDatas = {};
var $cell = null;
var $Div = $("<div>");
	$Div.css("position", "absolute")
	.css("z-index", 1000)
	.css("border", "1px dotted orange");
$(function() {
	var $span;
	var inputVal;
	$("#save").bind("click", function() {
		var blocks = jQuery.extend(true,{}, blockDatas);
		var data = covCells(blocks);
		for(var key in blocks){
			if(blocks[key].plugintype=="charts"&&typeof(blocks[key].chart)!="undefined"){
				blocks[key].chart.chartObj=null;
			}
		}
		blocks = JSON.stringify(blocks);
		$.ajax({
			url: webPath+"/layoutDesginerAction_save.action",
			type: "post",
			data: {
				editSts:editSts,
				fileName : fileName,
				data: data,
				blockDatas : blocks
			},
			dataType: "text",
			success: function(data) {
				location.href = webPath+"/layoutDesginerAction_downLoad.action?fileName="+eval("("+data+")");
				//$.myAlert.Alert(eval("("+data+")"));
			}
		});
	});
	$("#save_block").bind("click",function(){
		var data = covBlock();
		$cell.attr("cellid",data.cellid);
		$cell.find(".handle span").eq(0).html(data.cellname);
		var $info = $cell.find('.info').eq(0);
		if(data.plugintype=="form"){
			var formId = data.formId;
			var formType = data.formType;
			$.ajax({
				url: webPath+"/FormDataForToolBean_desginFormHtml.action",
				type: "post",
				data: {
					formId:formId,
					formType : formType
				},
				dataType: "text",
				success: function(data) {
					if(data.substr(0,5)=="error"){
						$.myAlert.Alert(data);
					}else{
						$info.html(data);
					}
				}
			});
		}
		/*else{
			$info.css("height","100%");
		}*/
		addCellPlugin($cell,data);
	});
	$("#taddCell").mousedown(function(evt) {
	cellClose();
	evt.stopPropagation();
	evt = evt.originalEvent;
	var $fw = $("#freewall")[0];
	 var divx1 = $fw.offsetLeft-8; 
     var divy1 = $fw.offsetTop+27; 
     var divx2 = divx1 + $fw.offsetWidth; 
     var divy2 = divy1 + $fw.offsetHeight; 
	var sx = evt.clientX;
	var sy = evt.clientY;
	var trash = '<b class="cC0 nui-ico nui-ico-smartTrash tableTrash" sign="trash"><b class="nui-ico nui-ico-smartTrash-head"></b><b class="nui-ico nui-ico-smartTrash-body"></b></b>';
	var temp = "<div class='cell fw-float' style=' top :" + sy + "px; left :" + sx + "px; width:150px; height: 80px; background-color: #EBEBEB; z-index:9999' data-handle='.handle'><div class='cover'><div class='handle handle-line'><span>点此拖拽</span></i><i class='fa fa-refresh jt-down'></i><i class='smartTrash jt-down'>"+trash+"</i></div></div><i class='bottomright'></i><div class='info'></div></div>";
	//$("#freewall").append(temp);
	$("body").append(temp);
	$("body").mousemove(function(evtm) {
		evtm.stopPropagation();
		evtm = evtm.originalEvent;
		var mx = evtm.clientX;
		var my = evtm.clientY;
		$("body .fw-float").css("top", my + "px").css("left", mx + "px");
		onDrag($("body .fw-float"), wall);
		if( mx > divx1 && mx < divx2 && my > divy1 && my < divy2){ 
			isOnFreeWall = true;
        }else{
        	isOnFreeWall = false;
        }
	});
	$("body").mouseup(function() {
		if(isOnFreeWall){
			$("#freewall").append($("body .fw-float"));
			var position = $("body .fw-float").position();
			if (!position) return;
			var offset = $("#freewall").offset();
			$("body .fw-float").css({
				left: position.left - offset.left,
				top: position.top - offset.top
			});
			
			var data = {};
			data["cellid"]="cell_"+($(".cell").length);
			data["cellname"]="点此拖拽";
			addCellPlugin($("body .fw-float"),data);
			$("body .fw-float").attr("cellid",data.cellid);
			onDrop($("body .fw-float"), wall);
		}else{
			$("body .fw-float").remove();
		}
		$("body").unbind("mouseup");
		$("body").unbind("mousemove");
	});
});
});
function addCellPlugin($cell,data){
	var plugintype = data.plugintype;
	if(blockDatas[data.cellid]==null||typeof(blockDatas[data.cellid])=="undefined"){
		data["chart"]={};
	}else{
		data["chart"] = blockDatas[data.cellid].chart;
	}
	if(plugintype=="charts"){
		var chart = prodChartOption($cell);
		if(chart==null||typeof(chart)=="undefined"){
			data["chart"]={};
			data.plugintype="";
		}else{
			data["chart"]["option"] = chart.option;
			data["chart"]["type"] = chart.type;
			data["chart"]["name"] = chart.name;
			var myChart = prodChart($cell,data["chart"]);
			data["chart"]["chartObj"] = myChart;
		}
	}else if(plugintype=="form"){
		data["chart"]={};
	}
	blockDatas[data.cellid] = data;
}
function covCells(blocks) {
	var cellArr = new Array();
	$("#freewall").find(".cell").each(function() {
		var data = {};
		data["width"] = $(this).width() + "px";
		data["height"] = $(this).height() + "px";
		data["left"] = $(this).position().left + "px";
		data["top"] = $(this).position().top + "px";
		data["name"] = $(this).find("span").eq(0).html();
		data["cellid"] = $(this).attr("cellid");
		data["formId"] = blocks[$(this).attr("cellid")].formId;
		data["formType"] = blocks[$(this).attr("cellid")].formType;
		data["formUrl"] = blocks[$(this).attr("cellid")].formUrl;
		data["dispalySts"] = blocks[$(this).attr("cellid")].dispalySts;
		cellArr.push(data);
	});
	if (cellArr.length == 0) {
		return null;
	} else {
		return JSON.stringify(cellArr);
	}
}
function covBlock(){
	var data = {};
	$(".tab-pane").find(".table-plugin td").each(function(){
        var $item;
		if($(this).find("button").length>0){
			$item = $(this).find("button").eq(0);
			data[$item.attr("name")] = $item.val();
		}else if($(this).find("input").length>0){
			$item = $(this).find("input").eq(0);
			data[$item.attr("name")] = $item.val();
		}
	});
	return data;
}
function refreshCell(){
	var cellid = $cell.attr("cellid");
	if(cellid&&blockDatas[cellid].plugintype=="form"){
		
	}
}
function delCell(event){
	$cell = event.data.cell;
	$cell.remove();
	wall.fillHoles();
	wall.refresh();
}
function setDataToTabs(data){
	if(data.plugintype!=null&&data.plugintype=="charts"){
		var chartType = data.chart.type;
		var chartName = data.chart.name;
		$(".tab-pane .table-plugin").eq(1).find("td").eq(2).html("图形小类");
		$(".tab-pane .table-plugin").eq(1).find("td").eq(3).html(createBtnGroup());
		var $sub = $(".tab-pane .table-plugin").eq(1).find("td").eq(3).find("button").eq(0);
		$sub.find("span").eq(0).html(chartName);
		$sub.val(chartType);
		var option = data.chart.option;
		var $plquery = $(".plugin-query");
		changeQuery($plquery,chartType,option);
		$plquery.show();
	}else if(data.plugintype!=null&&data.plugintype=="form"){
		$(".tab-pane .table-plugin").eq(1).find("td").eq(2).html("");
		$(".tab-pane .table-plugin").eq(1).find("td").eq(3).html("");
		createFormGroup($(".tab-pane .table-plugin").eq(1).find("tbody").eq(0));
	}
	$(".tab-pane .table-plugin").find("td").each(function(){
        var $item;
		if($(this).find("button").length>0){
			$item = $(this).find("button").eq(0);
			var val = data[$item.attr("name")];
			var showVal = $item.next().find("a[name='"+val+"']").eq(0).html();
			$item.val(val);
			$item.find("span").eq(0).html(showVal);
		}else if($(this).find("input").length>0){
			$item = $(this).find("input").eq(0);
			$item.val(data[$item.attr("name")]);
		}
	});
}
function clearTabs(){
	$(".tab-pane .table-plugin").eq(1).find("td").eq(2).html("");
	$(".tab-pane .table-plugin").eq(1).find("td").eq(3).html("");
	$(".plugin-query").hide();
	
	$(".nav-tabs li").removeClass("active");
	$(".nav-tabs li").eq(0).addClass("active");
	$(".tab-pane .table-plugin td input").val("");
	$(".tab-pane .table-plugin td button").find("span").eq(0).html("选择控件类型");
	$(".tab-pane .table-plugin td button").val("");
	$(".tab-pane").removeClass("active");
	$(".tab-pane").eq(0).addClass("active");
	
	$(".subPluginOption").remove();
	
	$(".dtm-tab0-coni .cpt-tab").hide();
	$(".dtm-tab0-coni .cpt-tab").eq(0).show();
}
function infoHoverIn(event){
	var $info = event.data.$info;
	if($info.html()!=""&&$info.find("canvas").length==0){
		var pb = $info.parent().find(".pluginBorder").eq(0);
		pb.css({
			width:($info.width()+10)+"px",
			height:($info.height()+10)+"px",
			top:($info.position().top+5)+"px",
			left:($info.position().left+5)+"px"
		});
//					pb.show();
		pb.fadeIn("slow");
	}
}
function infoHoverOut(event){
	var $info = event.data.$info;
	var pb = $info.parent().find(".pluginBorder").eq(0);
//				pb.hide();
	pb.fadeOut("slow");
}
function edit(t){
//				$(t).attr("href","http://128.1.20.32:8080/dakaer/drag2/openForm.action?formId=cif0001");
	var ct = $(t).parent().parent().attr("cellid");
	var data = blockDatas[ct];
	openShowModalDialog(webPath+"/drag2/openForm.action?formId="+data.formId,"","",t);
}
function openShowModalDialog(url,param,whparam,e){
	// 传递至子窗口的参数
	 var paramObj = param || { };
	 // 模态窗口高度和宽度
	 var whparamObj = whparam || { width: 1400, height: 700 };
	 // 相对于浏览器的居中位置
	 var bleft = ($(window).width() - whparamObj.width) / 2;
	 var btop = ($(window).height() - whparamObj.height) / 2;
	 // 根据鼠标点击位置算出绝对位置
	 var tleft = e.screenX - e.clientX;
	 var ttop = e.screenY - e.clientY;
	 // 最终模态窗口的位置
	 var left = bleft + tleft;
	 var top = btop + ttop;
 	 var styleStr ='';
 	 styleStr += 'dialogLeft:' + left + 'px;';
     styleStr += 'dialogTop:' + top + 'px;';
     styleStr += 'dialogWidth:'+(whparamObj.width)+'px;';
 	 styleStr += 'dialogHeight:'+(whparamObj.height)+'px;';
 	 styleStr +='center:yes;help:no;minimize:no;maximize:no;border:thin;statusbar:no;resizable=no';
	 var show=window.showModalDialog(url,window,styleStr);
}
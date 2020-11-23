var listData = {},tempId,tempVpNo,tempVpName,index,initParam=true,initSelect,viewData;
$(function(){
	var bodyHeight = document.body.clientHeight-190;
	$(".table_content").height(bodyHeight);
	$(".table_content").each(function(){
		var $tbContObj = $(this);
		$(this).mCustomScrollbar({
			advanced:{ 
				updateOnContentResize:true
			},onTotalScrollOffset:30,//回调距什么位置回调
			callbacks:{ 
				onTotalScroll:function(){
					updateTableContent($tbContObj);
				}
			}
		});
	});
	$("#formdesctemp0001 textarea[name='desctempContent']").css("height",$("#formdesctemp0001 textarea[name='desctempContent']").parents(".col_list_content").eq(0).height()-180);
	$("#formdesctemp0001 textarea[name='desctempContent']").parent().prev().attr("valign","top");
	
	$("#formdesctemp0001 input[name='desctempSts']").parents("td").html('<input type="checkbox" name="desctempSts" value="1" checked/>');
	$("#formdesctemp0001 input[type=checkbox]").rcSwitcher({
		width: 50,
		height: 20,
		theme: 'lease',
		blobOffset: 1,
		onText:'是',
		offText:'否'
	});
	
	$("#SysDescTemp_getListPage input[name='desctempSts']").parents("td").html('<input type="checkbox" name="desctempSts" value="1" checked/>');
	$("#SysDescTemp_getListPage input[type=checkbox]").rcSwitcher({
		width: 50,
		height: 20,
		theme: 'lease',
		blobOffset: 1,
		onText:'是',
		offText:'否'
	});
	$.ajax({
		type:"post",
		url:webPath+"/pmsViewpoint/getAll",
		async:false,
		success:function(data){
			viewData={};
			for(var i in data){
				if(typeof(data[i].viewpointNo)!="undefined"&&typeof(viewData[data[i].viewpointNo])=="undefined"){
					viewData[data[i].viewpointNo] = data[i].viewpointName;
				}
			}
			var $form = $("#formdesctemp0001");
			var $formsearch = $("#SysDescTemp_getListPage");
			$form.find("select[name=viewpointNo]").html("");
			$formsearch.find("select[name=viewpointNo]").html("");
			$("<option value='' selected = 'selected' >&nbsp</option>").appendTo($form.find("select[name=viewpointNo]"));
			$("<option value='' selected = 'selected' >&nbsp</option>").appendTo($formsearch.find("select[name=viewpointNo]"));
			for(var v in viewData){
				$("<option value='"+v+"'>"+viewData[v]+"</option>").appendTo($form.find("select[name=viewpointNo]"));
				$("<option value='"+v+"'>"+viewData[v]+"</option>").appendTo($formsearch.find("select[name=viewpointNo]"));
			}
			$form.find("select[name=viewpointNo]").select3();
			$formsearch.find("select[name=viewpointNo]").select3();
		}
	});
//	$(".select-border .colmon input").bind({keyup:function(event){
//		var keyVal = event.which;
//		if(keyVal=="13"){
//			selectSysDescTemp();
//			initdbClick();
//		}
//	}});
	initParam=true;
	selectSysDescTemp();
});
/**
 * 字表parmDic的双击修改
 */
var dataVal = {},parmDicUrl,desctempNo,SynchronousLoadding = true;
function initdbClick(){
	$("#tablist tbody tr").unbind();
	$("#tablist tbody tr").bind("click",function(){
		index = $(this).parents(".ls_list").find("thead tr th[name=desctempNo]").index();
		tempId = $(this).find("td").eq(index).html();
		tempVpNo = listData[tempId].viewpointNo;
		var $form = $("#formdesctemp0001");
		$form.find("div[name=viewpointNo]").select3("clear");
		$form.find("div[name=viewpointNo]").select3("add",{id:" ",text:" "});
		for(var v in viewData){
			$form.find("div[name=viewpointNo]").select3("add",{id:v,text:viewData[v]});
		}
		
		if(tempVpNo!=""){
			$form.find("div[name=viewpointNo]").select3("add",{id:tempVpNo,text:listData[tempId].viewpointName});
			//viewData[tempVpNo] = listData[tempId].viewpointName;
		}
		optContent(true,listData[tempId]);
	});
}

/**
 * 滚动条滚动加载
 * @param {Object} $obj
 */
function updateTableContent($obj){
	var pager = getPager($obj);
	if(pager.pageNo<pager.pageSum){
		pager.pageNo = pager.pageNo+1;
		var url,tableId;
		var tableType = "thirdTableTag";
		var flag = $obj.hasClass("parent");
		if(flag){
			var $tableParent = $(".table_content.parent").find("table.ls_list");
			tableId = $tableParent.attr("title");
			url = webPath+"/sysDescTemp/findByPageAllAjax";
		}
		var dataParam = {};
		if(SynchronousLoadding){
			dataParam = getSelectInputVal();
		}
		var data = {
			ajaxData:JSON.stringify(dataParam),
			tableType:tableType,
			tableId:tableId
		};
		data = $.extend({}, data,pager);
		$.ajax({
			type:"post",
			url:url,
			data:data,
			success:function(data){
				$obj.find("table.ls_list tbody").append($(data.ipage.result).find("tbody").html());
				intiPageShow($obj,data.ipage);
				setPager($obj,data.ipage);
				initdbClick();
				for(var i in data.list){
					listData[data.list[i].desctempNo] = {};
					listData[data.list[i].desctempNo]["desctempName"] = data.list[i].desctempName;
					listData[data.list[i].desctempNo]["desctempContent"] = data.list[i].desctempContent;
					listData[data.list[i].desctempNo]["desctempSts"] = data.list[i].desctempSts;
					listData[data.list[i].desctempNo]["viewpointNo"] = data.list[i].viewpointNo;
					listData[data.list[i].desctempNo]["viewpointName"] = data.list[i].viewpointName;
				}
			}
		});
	}else{
		//console.log("加载完毕"+pager.pageNo+","+pager.pageSum);
	}
}
function intiPageShow($obj,pager){
	var flag = $obj.parent().find(".pagerShow").html();
	if(flag===undefined){
		var pageShowHtml = '<div class="pagerShow">';
    		pageShowHtml+= '<span>';
    		pageShowHtml+= '共计<span class="pageCount">'+pager.pageCounts+'</span>条';
    		pageShowHtml+= '<span class="split"></span>已加载<span class="loadCount">'+pager.endNum+'</span>条';
    		pageShowHtml+= '</span>';
    		pageShowHtml+= '</div>';
		$obj.parent().append(pageShowHtml);
	}else{
		var $pageShow = $obj.parent().find(".pagerShow");
		$pageShow.find(".pageCount").html(pager.pageCounts);
		$pageShow.find(".loadCount").html(pager.endNum);
	}
}

function getPager($obj){
	var pager = {};
	pager.pageSize = parseInt($obj.find(".pageer").attr("pageSize"));
	pager.pageNo = parseInt($obj.find(".pageer").attr("pageNo"));
	pager.pageSum = parseInt($obj.find(".pageer").attr("pageSum"));
	return pager;
}
function setPager($obj,pager){
	$obj.find(".pageer").attr("pageSize",pager.pageSize);
	$obj.find(".pageer").attr("pageNo",pager.pageNo);
	$obj.find(".pageer").attr("pageSum",pager.pageSum);
}

function tempSave(obj){
	var $form = $("#formdesctemp0001");
	var dataParam = {};
	dataParam.desctempName = $form.find("input[name='desctempName']").val();
	var viewpointNo = $form.find("div[name='viewpointNo']").select3("val");
	if(viewpointNo.length==0){
		viewpointNo = ""; 
	}
	viewpointNo  =  viewpointNo.replace(/\s+/g,""); 
	dataParam.viewpointNo = viewpointNo;
	if($form.find("div[name='viewpointNo']").select3("data")!=null){
		dataParam.viewpointName = $form.find("div[name='viewpointNo']").select3("data").text.replace(/\s+/g,"");
	}else{
		dataParam.viewpointName = "";
	}
	dataParam.desctempContent = $form.find("textarea[name='desctempContent']").val();
	dataParam.desctempSts = $form.find("input[name='desctempSts']").is(':checked')?1:0;
	if(dataParam.desctempName!=""){
		var url;
		if(tempId){
			dataParam["desctempNo"] = tempId;
			url = webPath+'/sysDescTemp/updateAjax';
		}else{
			url = webPath+'/sysDescTemp/insertAjax';
		}
		$form = $("#formdesctemp0001");
		$form.find("div[name=viewpointNo]").select3("remove",dataParam.viewpointNo);
		delete viewData[dataParam.viewpointNo];
		dataParam = JSON.stringify(dataParam);
		var data = {
			ajaxData:dataParam
		};
		$.ajax({
			type:"post",
			url:url,
			data:data,
			success:function(data){
				if(data.flag=="success"){
					var $tableContent = $(obj).parents(".col_content").prev().find(".table_content.parent");
					$tableContent.find("table.ls_list tbody").html($(data.ipage.result).find("tbody").html());
					setPager($tableContent,data.ipage);
					intiPageShow($tableContent,data.ipage);
					optContent(false);
					initdbClick();
//					$.myAlert.Alert(top.getMessage("SUCCEED_SAVE"));
					
					listData[data.temp.desctempNo] = {};
					listData[data.temp.desctempNo]["desctempName"] = data.temp.desctempName;
					listData[data.temp.desctempNo]["desctempContent"] = data.temp.desctempContent;
					listData[data.temp.desctempNo]["desctempSts"] = data.temp.desctempSts;
					listData[data.temp.desctempNo]["viewpointNo"] = data.temp.viewpointNo;
					listData[data.temp.desctempNo]["viewpointName"] = data.temp.viewpointName;
				}else{
					//$.myAlert.Alert(data.msg);
					alert(data.msg,0);
				}
			},error:function(){
				//$.myAlert.Alert("保存失败！");
				alert("保存失败！",0);
			}
		});
	}else{
		var msg = "";
		if(dataParam.desctempName==""){
			msg += top.getMessage("NOT_FORM_EMPTY", "模板名称:");
		}
		//$.myAlert.Alert(msg);
		alert(msg,0);
	}
}
/**
 * 删除
 * @param {Object} obj
 * @param {Object} url
 */
function ajaxDelete(obj,url){
	var flag = $(obj).parents(".table_content").hasClass("parent");
	alert("是否确定删除？",2,function(){
		$.ajax({
			type:"get",
			url:url,
			success:function(data){
				if(data.flag=="success"){
					var $pageShow = $(obj).parents(".col_content").find(".pagerShow");
					var pageCount = parseInt($pageShow.find(".pageCount").html().trim());
					var loadCount = parseInt($pageShow.find(".loadCount").html().trim());
					$pageShow.find(".pageCount").html((pageCount-1)>=0?(pageCount-1):0);
					$pageShow.find(".loadCount").html((pageCount-1)>=0?(pageCount-1):0);
//					$.myAlert.Alert(top.getMessage("SUCCEED_DELETE"));
					index = $(obj).parents(".ls_list").find("thead tr th[name=desctempNo]").index();
					var id = $(obj).parents("tr").find("td").eq(index).html();
					var $form = $("#formdesctemp0001");
					$form.find("div[name=viewpointNo]").select3("add",{id:id,text:listData[id].viewpointName});
					viewData[id] = listData[id].viewpointName;
					delete listData[id];
					optContent(false);
					$(obj).parents("tr").remove();
				}else{
					//$.myAlert.Alert(top.getMessage("FAILED_DELETE"));
					alert(top.getMessage("FAILED_DELETE"),0);
				}
			},error:function(){
				//$.myAlert.Alert(top.getMessage("FAILED_DELETE"));
				alert(top.getMessage("FAILED_DELETE"),0);
			}
		});
	});
	/*$.myAlert.Confirm("是否确定删除？","",function(){
		$.ajax({
		type:"get",
		url:url,
		success:function(data){
			if(data.flag=="success"){
				var $pageShow = $(obj).parents(".col_content").find(".pagerShow");
				var pageCount = parseInt($pageShow.find(".pageCount").html().trim());
				var loadCount = parseInt($pageShow.find(".loadCount").html().trim());
				$pageShow.find(".pageCount").html((pageCount-1)>=0?(pageCount-1):0);
				$pageShow.find(".loadCount").html((pageCount-1)>=0?(pageCount-1):0);
//				$.myAlert.Alert(top.getMessage("SUCCEED_DELETE"));
				index = $(obj).parents(".ls_list").find("thead tr th[name=desctempNo]").index();
				var id = $(obj).parents("tr").find("td").eq(index).html();
				var $form = $("#formdesctemp0001");
				$form.find("div[name=viewpointNo]").select3("add",{id:id,text:listData[id].viewpointName});
				viewData[id] = listData[id].viewpointName;
				delete listData[id];
				optContent(false);
				$(obj).parents("tr").remove();
			}else{
				$.myAlert.Alert(top.getMessage("FAILED_DELETE"));
			}
		},error:function(){
			$.myAlert.Alert(top.getMessage("FAILED_DELETE"));
		}
	});
	});*/
}
/**
 * 获得查询表头
 */
function getSelectInputVal(){
	var desctempName = $(".select-border").find("input[name='desctempName']").val();
	var viewpointNo = $(".select-border").find("div[name='viewpointNo']").select3("val");
	if(viewpointNo.length==0){
		viewpointNo = ""; 
	}
	viewpointNo  =  viewpointNo.replace(/\s+/g,""); 
	var desctempSts = $(".select-border").find("input[name='desctempSts']").is(':checked');
	if(desctempSts){
		desctempSts = "1";
	}else{
		desctempSts = "0";
	}
	if(initParam){
		desctempSts = "";
	}
	initParam = false;
	var data = {desctempName:desctempName,viewpointNo:viewpointNo,desctempSts:desctempSts};
	return data;
}
/**
 * 全局查询
 */
function selectSysDescTemp(){
	var dataParam = JSON.stringify(getSelectInputVal());
	var $tableParent = $(".table_content.parent").find("table.ls_list");
	var tableId = $tableParent.attr("title");
	var tableType = "thirdTableTag";
	$.ajax({
		type:"post",
		url:webPath+"/sysDescTemp/findByPageAllAjax",
		async:false,
		data:{
			ajaxData:dataParam,
			pageSize:25,
			tableId:tableId,
			tableType:tableType
		},success:function(data){
			$tableParent.find("tbody").html($(data.ipage.result).find("tbody").html());
			setPager($(".table_content.parent"),data.ipage);
			intiPageShow($(".table_content.parent"),data.ipage);
//			$(".table_content").mCustomScrollbar("scrollTo","top");
			SynchronousLoadding = true;
			initdbClick();
			
			listData = {};
			for(var i in data.list){
				listData[data.list[i].desctempNo] = {};
				listData[data.list[i].desctempNo]["desctempName"] = data.list[i].desctempName;
				listData[data.list[i].desctempNo]["desctempContent"] = data.list[i].desctempContent;
				listData[data.list[i].desctempNo]["desctempSts"] = data.list[i].desctempSts;
				listData[data.list[i].desctempNo]["viewpointNo"] = data.list[i].viewpointNo;
				listData[data.list[i].desctempNo]["viewpointName"] = data.list[i].viewpointName;
				var $form = $("#formdesctemp0001");
				$form.find("div[name=viewpointNo]").select3("remove",data.list[i].viewpointNo);
				delete viewData[data.list[i].viewpointNo];
			}
			
		},error:function(){
			
		}
	});
}
function optContent(flag,data){
	var list = $(".col_list");
	if(flag){
		list.stop().animate({width:"31%"},300);
		if(data){
			var $form = $("#formdesctemp0001");
			$form.find("input[name='desctempName']").val(data.desctempName);
			$form.find("div[name=viewpointNo]").select3("value",data.viewpointNo);
			$form.find("textarea[name='desctempContent']").val(data.desctempContent);
			if(data.desctempSts=="1"){
				$form.find("[name='desctempSts']").prop('checked',true);
				$form.find("[name='desctempSts']").prev().find(".stoggler").removeClass("off").addClass("on").css({transform:"translateX(-9px)"});
			}else{
				$form.find("[name='desctempSts']").prop('checked',false);
				$form.find("[name='desctempSts']").prev().find(".stoggler").removeClass("on").addClass("off").css({transform:"translateX(-41px)"});
			}

		}else{
			clearForm();
			tempId = null;
		}
	}else{
		list.stop().animate({width:"65.5%"},300);
	}
}
function clearForm(){
	var $form = $("#formdesctemp0001");
	$form.find("input[name='desctempName']").val("");
	$form.find("div[name=viewpointNo]").select3("value","");
	$form.find("textarea[name='desctempContent']").val("");
	$form.find("[name='desctempSts']").prop('checked',true);
	$form.find("[name='desctempSts']").prev().find(".stoggler").removeClass("off").addClass("on").css({transform:"translateX(-9px)"});
}
/**
 * @param {Object} json
 * return jsonArray
 */
function changeDateParm(json){
	var jsonArray = [];
	$.each(json,function(name,value){
		var jsonObj = {};
		jsonObj.name = name;
		jsonObj.value = value;
		jsonArray.push(jsonObj);
	});
	return jsonArray;
}
function refreshParmKey(obj){
	var $this = $(obj)
	$.ajax({
		type:"post",
		url:webPath+"/parmKey/refreshParmKey",
		async:false,
		beforeSend:function(){
			$this.parent().find(".fa-refresh").addClass("fa-hidden");
			$this.parent().find(".fa-spinner").removeClass("fa-hidden");
		},success:function(data){
			if(data.flag=="success"){
				//$.myAlert.Alert("刷新成功！");
				alert("刷新成功！",1);
				$this.parent().find(".fa-spinner").addClass("fa-hidden");
				$this.parent().find(".fa-refresh").removeClass("fa-hidden");
			}else if(data.flag=="error"){
				//$.myAlert.Alert("刷新失败！");
				alert("刷新失败！",0);
			}
		},error:function(){
			//$.myAlert.Alert("刷新失败,请检查连接！");
			alert("刷新失败,请检查连接！",0);
		}
	});
}
/**
 * 防止保持方法
 */
function enterKey(){};
function func_uior_valTypeImm(obj){};
function showCharsInfo(){};
function hideCharsInfo(){};
function textareaInputCount(){};
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
	$("select[name='ifEdit']").parents("td").html('<input type="checkbox" name="ifEdit" value="1" checked/>');
	$("select[name='ifSts']").parents("td").html('<input type="checkbox" name="ifSts" value="1" checked/>');
	
	var ssss = $(".select-border input[type=checkbox],.form_content input[type=checkbox]").rcSwitcher({
		width: 50,
		height: 20,
		theme: 'lease',
		blobOffset: 1,
		onText:'是',
		offText:'否'
	});
	$(".select-border .colmon input").bind({keyup:function(event){
		var keyVal = event.which;
		if(keyVal=="13"){
			selectParmKey();
			initdbClick();
		}
	}});
	selectParmKey();
});
/**
 * 字表parmDic的双击修改
 */
var dataVal = {},parmDicUrl,keyName,SynchronousLoadding = true;
function initdbClick(){
	$(".table_content.child .ls_list tbody tr").unbind();
	$(".table_content.child .ls_list tbody tr").bind("dblclick",function(){
		if($(this).parent().find(".editorTr").html()!=undefined){
			
		}else{
			$(this).addClass("editorTr");
			var col = new Array();
			$(this).parents(".ls_list").find("thead tr th").each(function(index,obj){
				if($(obj).attr("name")!==undefined){
					col[index] = $(obj).attr("name").trim();
				}
			});
			$(this).find("td").each(function(index,obj){
				var value = $(obj).html().trim();
				if(col.length>=index+1){
					dataVal[col[index]] = value;
					var width = $(obj).width();
					$(obj).css("width",width+"px");
					if(col[index]=="sts"){
						var checkbox;
						if(value == "是"){
							checkbox = '<input type="checkbox" checked="" value="1" name="'+col[index]+'">';
						}else{
							checkbox = '<input type="checkbox"  value="1" name="'+col[index]+'">';
						}
						$(obj).html(checkbox);
						$(obj).children().rcSwitcher({width: 50,height: 20,theme: 'lease',blobOffset: 1,onText:'是',offText:'否'});
					}else if(index!=0&&index!=1){
						$(obj).html('<input name="'+col[index]+'" type="text" style="width:'+(width-2)+'px;text-align: center;" value="'+value+'"/>');
					} 
				}
			});
		}
	});
	initClick();
}
function initClick(){
	$(".table_content.parent .ls_list tbody tr").unbind();
	$(".table_content.parent .ls_list tbody tr").bind("click",function(event){
		/*var functionUrl= $(this).find("td").eq(0).find("a").attr("onclick");
		keyName = functionUrl.split("?")[1].split("'")[0].split("=")[1];*/
		keyName = $(this).find("td").eq(0).text();
		initParmDic(keyName);
		formExit($(this));
	});
}
function parmDicCancel(obj){
	var $tableObj = $(obj).parents(".col_content").find(".table_content").find("table.ls_list");
	$tableObj.find("tbody").find(".editorTr td").each(function(index,obj){
		if($(obj).find("input").length>0){
			var name = $(obj).find("input").attr("name");
			$(obj).html(dataVal[name]);
		}
	});
	$tableObj.find("tbody").find(".editorTr").removeClass("editorTr");
	$tableObj.find("tbody").find(".addTr").remove();
}

function initdataVal($tableObj){
	$tableObj.find("tbody .editorTr td").each(function(index,obj){
		if($(obj).find("input").length>0){
			var name = $(obj).find("input").attr("name");
			var value;
			if($(obj).find("input[type='checkbox']").length>0){
				if($(obj).find("input[type='checkbox']").is(':checked')){
					value = 1;
				}else{
					value = 0;
				}
			}else{
				value = $(obj).find("input[type='text']").val();
			}
			dataVal[name] = value;
		}
	});
	$tableObj.find("tbody .addTr td").each(function(index,obj){
		if($(obj).find("input").length>0){
			var name = $(obj).find("input").attr("name");
			var value;
			if($(obj).find("input[type='checkbox']").length>0){
				if($(obj).find("input[type='checkbox']").is(':checked')){
					value = 1;
				}else{
					value = 0;
				}
			}else{
				value = $(obj).find("input[type='text']").val();
			}
			dataVal[name] = value;
		}
	});
}
function parmDicSave(obj,url){
	if(url.substr(0,1)=="/"){
		url =webPath + url; 
	}else{
		url =webPath + "/" + url;
	}
	var flag = "update";
	var $tableObj = $(obj).parents(".col_content").find(".table_content").find("table.ls_list");
	initdataVal($tableObj);
	if($tableObj.find("tbody .editorTr").length>0){
		flag = "update";
	}else if($tableObj.find("tbody .addTr").length>0){
		flag = "insert";
	}
	$.ajax({
		type:"post",
		url:(flag=="insert"?parmDicUrl:url),
		async:false,
		data:{ajaxData:JSON.stringify(changeDateParm(dataVal))},
		success:function(data){
			if(data.flag == "success"){
				var $tableContent = $(obj).parents(".col_content").find(".table_content.child");
				$tableContent.find("table.ls_list tbody").html($(data.ipage.result).find("tbody").html());
				setPager($tableContent,data.ipage);
				initPageShow($tableContent,data.ipage);
				initdbClick();
				alert(flag=="insert"?top.getMessage("SUCCEED_INSERT"):top.getMessage("SUCCEED_SAVE"),1);
			}else{
				alert(data.msg,0);
			}
		},error:function(){
			alert(  top.getMessage("FAILED_SAVE"),0);
		}
	});
}
function parmDicInput(obj,url){
	parmDicUrl = url;
	var $tableObj = $(obj).parents(".col_content").find(".table_content").find("table.ls_list");
	var trHtml = $('<tr class="addTr">');
	var tdHtml = '<td align="center"></td>';
	if(keyName!=undefined){
		$tableObj.find("thead tr th").each(function(index,obj){
			var width = $(obj).width();
			if($(obj).attr("name")!=undefined){
				var name = $(obj).attr("name").trim();
				var $tdHtml = $(tdHtml).css("width",width+"px");
				if(name=="keyName"&&keyName!=undefined&&keyName!=null&&keyName!=""){
					$tdHtml.append('<input class="readonly_border" name="'+name+'" type="text" style="width:'+(width-2)+'px;text-align: center;" value="'+keyName+'"/>');
				}else if(name=="sts"){
					$tdHtml.append('<input type="checkbox" checked="" value="1" name="'+name+'">');
				}else{
					$tdHtml.append('<input name="'+name+'" type="text" style="width:'+(width-2)+'px;text-align: center;"/>');
				}
				trHtml.append($tdHtml);
			}
		});
		if(	$tableObj.find("tbody tr").length>0){
			$tableObj.find("tbody tr").eq(0).before(trHtml);
		}else{
			$tableObj.find("tbody").append(trHtml);
		}
		$tableObj.find("tbody tr").eq(0).find("td input[type='checkbox']").rcSwitcher({width: 50,height: 20,theme: 'lease',blobOffset: 1,onText:'是',offText:'否'});
		$(obj).parents(".col_content").find(".table_content").mCustomScrollbar("scrollTo","top");
	}else{
		alert("请选择数据字典描述,然后新增！",0);
	}
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
			url =webPath+"/parmKey/findByPageAjax";
		}else{
			var $tableChild = $(".table_content.child").find("table.ls_list");
			tableId = $tableChild.attr("title");
			url =webPath+"/parmDic/findByPageAjax";
		}
		var dataParam = {};
		if(!SynchronousLoadding&&!flag){ 
			dataParam.keyName = keyName;
		}else{
			dataParam = getSelectInputVal();
		}
		var data = {
			ajaxData:JSON.stringify(dataParam),
			tableType:tableType,
			tableId:tableId,
		};
		data = $.extend({}, data,pager);
		$.ajax({
			type:"post",
			url:url,
			data:data,
			success:function(data){
				$obj.find("table.ls_list tbody").append($(data.ipage.result).find("tbody").html());
				initPageShow($obj,data.ipage);
				setPager($obj,data.ipage);
				initdbClick();
			}
		});
	}else{
		//console.log("加载完毕"+pager.pageNo+","+pager.pageSum);
	}
}
function initPageShow($obj,pager){
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
function openThisForm(obj){
	var $colContent = $(obj).parents(".col_content");
	var showFlag = $colContent.find(".form_content").css("display");
	var tableContentHeight = $colContent.find(".table_content").height();
	if(showFlag=="none"){
		$colContent.find(".table_content").animate({height:tableContentHeight-80});
		$colContent.find(".form_content").slideDown();
	}
}
function ParmKeyInput(obj,url){
	openThisForm(obj);
	var $formObj = $(obj).parents(".col_content").find(".form_content form");
	$formObj.attr("action",url);
	$formObj.find("input[type='text']").val("").removeAttr("readOnly").removeClass("readonly_border");
	$formObj.find("[type='checkbox']").prop('checked',true);
	$formObj.find(".swraper .stoggler").removeClass("off").addClass("on").css({transform:"translateX(-10px)"});
}
function formExit(obj){
    var $colContent = $(obj).parents(".col_content");
	if($colContent.find(".form_content").css("display")=="block"){
		var tableContentHeight = $colContent.find(".table_content").height();
		$colContent.find(".table_content").animate({height:tableContentHeight+100});
		$colContent.find(".form_content").slideUp();
	}
}

function ParmKeySave(obj,url){
	var saveInsertFalg = "insert";
	var ajaxUrl = $(obj).attr("action");
	if(ajaxUrl===undefined||ajaxUrl==""){
		ajaxUrl = url;
		saveInsertFalg = "update";
	}
	var $form = $("#formnmd0001");
	var dataParam = {};
	dataParam.keyName = $form.find("input[name='keyName']").val();
	dataParam.keyChnName = $form.find("input[name='keyChnName']").val();
	dataParam.ifSts = $(obj).find("input[name='ifSts']").is(':checked')?1:0;
	dataParam.ifEdit = $(obj).find("input[name='ifEdit']").is(':checked')?1:0;
	dataParam.FLag = {};
	if(dataParam.keyName!=undefined&&dataParam.keyName!=""&&dataParam.keyChnName!=null){
		dataParam = JSON.stringify(changeDateParm(dataParam));
		var tableId = $(obj).parents(".col_content").find(".table_content table.ls_list").attr("title");
		var data = {
			ajaxData:dataParam,
			tableId:tableId
		};
		$.ajax({
			type:"post",
			url:ajaxUrl,
			data:data,
			success:function(data){
				if(data.flag=="success"){
					var $tableContent = $(obj).parents(".col_content").find(".table_content.parent");
					$tableContent.find("table.ls_list tbody").html($(data.ipage.result).find("tbody").html());
					setPager($tableContent,data.ipage);
					initPageShow($tableContent,data.ipage);
					$(obj).removeAttr("action");
					initdbClick();
					formExit(obj);
				}else{
					alert(data.msg,0);
				}
			},error:function(){
				alert(  top.getMessage("FAILED_SAVE"),0);
			}
		});
	}else{
		var msg = "";
		if(dataParam.keyName==""){
			msg += top.getMessage("NOT_FORM_EMPTY", "数据字典名:");
		}
		if(dataParam.keyChnName==""){
			msg += top.getMessage("NOT_FORM_EMPTY", "<br>数据字典名:");
		}
		alert(msg,0);
	}
}
/**
 * 修改跳转
 * @param {Object} obj
 * @param {Object} url
 */
function parmKeyInput(obj,url){
	if(url.substr(0,1)=="/"){
		url =webPath + url; 
	}else{
		url =webPath + "/" + url;
	}
	$.ajax({
		type:"get",
		url:url,
		success:function(data){
			if(data.flag == "success"){
				openThisForm(obj);
				var $form = $(obj).parents(".col_content").find(".form_content form");
				$form.removeAttr("action");
				$form.find("input[name='keyName']").val(data.formData.keyName).attr("readOnly","readOnly").addClass("readonly_border");
				$form.find("input[name='keyChnName']").val(data.formData.keyChnName);
				if(data.formData.ifSts=="1"){
					$form.find("[name='ifSts']").prop('checked',true);
					$form.find("[name='ifSts']").prev().find(".stoggler").removeClass("off").addClass("on").css({transform:"translateX(-10px)"});
				}else{
					$form.find("[name='ifSts']").prop('checked',false);
					$form.find("[name='ifSts']").prev().find(".stoggler").removeClass("on").addClass("off").css({transform:"translateX(-40px)"});
				}
				if(data.formData.ifEdit=="1"){
					$form.find("[name='ifEdit']").prop('checked',true);
					$form.find("[name='ifEdit']").prev().find(".stoggler").removeClass("off").addClass("on").css({transform:"translateX(-10px)"});
				}else{
					$form.find("[name='ifEdit']").prop('checked',false);
					$form.find("[name='ifEdit']").prev().find(".stoggler").removeClass("on").addClass("off").css({transform:"translateX(-40px)"});
				}
			}else{
				alert(data.msg,0);
			}
		},error:function(){
			alert(top.getMessage("ERROR_SELECT"),0);
		}
	});
	
	 if(window.event){
        //e.returnValue=false;//阻止自身行为
        window.event.cancelBubble=true;//阻止冒泡
     }else if(arguments.callee.caller.arguments[0].preventDefault){
        //e.preventDefault();//阻止自身行为
        arguments.callee.caller.arguments[0].stopPropagation();//阻止冒泡
     }
}
/**
 * 删除
 * @param {Object} obj
 * @param {Object} url
 */
function ajaxDelete(obj,url){
	if(url.substr(0,1)=="/"){
		url =webPath + url; 
	}else{
		url =webPath + "/" + url;
	}
	var flag = $(obj).parents(".table_content").hasClass("parent");
	alert("是否确定删除？",2,function(){
		$.ajax({
			type:"get",
			url:url,
			success:function(data){
				if(data.flag=="success"){
					var $pageShow = $(obj).parents(".col_content").find(".pagerShow");
					$(obj).parents("tr").remove();
					var pageCount = parseInt($pageShow.find(".pageCount").html().trim());
					var loadCount = parseInt($pageShow.find(".loadCount").html().trim());
					$pageShow.find(".pageCount").html((pageCount-1)>=0?(pageCount-1):0);
					$pageShow.find(".loadCount").html((pageCount-1)>=0?(pageCount-1):0);
					if(flag){
						var params = url.split("?");
						var param = params[1].split("&");
						$.each(param,function(index,data){
							if(data!==undefined&&data.split("=")[0]=="keyName"){
								keyName = data.split("=")[1];
							}
						});
						if(keyName!=undefined&&keyName!=null&&keyName!=""){
							initParmDic(keyName);
						}
					}
					formExit(obj);
					alert(top.getMessage("SUCCEED_DELETE"),1);
				}else{
					alert(top.getMessage("FAILED_DELETE"),0);
				}
			},error:function(){
				alert(top.getMessage("FAILED_DELETE"),0);
			}
		});
	});
}
/**
 * 子表数据初始化
 * @param {Object} keyName
 */
function initParmDic(keyName){
	var data = {keyName:keyName};
	$.ajax({
		type:"post",
		url:webPath+"/parmDic/findByPageAjax",
		data:{ajaxData:JSON.stringify(data),pageSize:25,tableId:"tablenmd0002",tableType:"tableTag"},
		success:function(data){
			if(data.flag=="success"){
				var $tableContent = $(".table_content.child");
				$tableContent.find("table.ls_list tbody").html($(data.ipage.result).find("tbody").html());
				setPager($tableContent,data.ipage);
				initPageShow($tableContent,data.ipage);
				SynchronousLoadding = false;
				initdbClick();
			}else{
				alert(data.msg,0);
			}
		},error:function(){
			alert(  top.getMessage("FAILED_SAVE"),0);
		}
	});
}
/**
 * 获得查询表头
 */
function getSelectInputVal(){
	var keyName = $(".select-border").find("input[name='keyName']").val();
	var keyChnName = $(".select-border").find("input[name='keyChnName']").val();
	var ifSts = $(".select-border").find("input[name='ifStsFlag']").is(':checked');
	if(ifSts){
		ifSts = "1";
	}else{
		ifSts = "0";
	}
	var data = {keyName:keyName,keyChnName:keyChnName,ifSts:ifSts};
	return data;
}
/**
 * 全局查询
 */
function selectParmKey(){
	var dataParam = JSON.stringify(getSelectInputVal());
	var $tableParent = $(".table_content.parent").find("table.ls_list");
	var $tableChild = $(".table_content.child").find("table.ls_list");
	var tableId = $tableParent.attr("title");
	var tableIdc = $tableChild.attr("title");
	var tableType = "thirdTableTag";
	$.ajax({
		type:"post",
		url:webPath+"/parmKey/findByPageAllAjax",
		async:false,
		data:{
			ajaxData:dataParam,
			pageSize:25,
			tableId:tableId,
			tableIdc:tableIdc,
			tableType:tableType
		},success:function(data){
			$tableParent.find("tbody").html($(data.ipage.result).find("tbody").html());
			setPager($(".table_content.parent"),data.ipage);
			initPageShow($(".table_content.parent"),data.ipage);
			$tableChild.find("tbody").html($(data.ipagec.result).find("tbody").html());
			setPager($(".table_content.child"),data.ipage);
			initPageShow($(".table_content.child"),data.ipagec);
			$(".table_content").mCustomScrollbar("scrollTo","top");
			SynchronousLoadding = true;
			initdbClick();
		},error:function(){
			
		}
	});
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
	var $this = $(obj);
	$.ajax({
		type:"post",
		url:webPath+"/parmKey/refreshParmKey",
		async:false,
		beforeSend:function(){
			$this.parent().find(".refresh-ctrl").addClass("fa-hidden");
			$this.parent().find(".spinner-ctrl").removeClass("fa-hidden");
		},success:function(data){
			if(data.flag=="success"){
				alert("刷新成功！",1);
				$this.parent().find(".spinner-ctrl").addClass("fa-hidden");
				$this.parent().find(".refresh-ctrl").removeClass("fa-hidden");
			}else if(data.flag=="error"){
				alert("刷新失败！",0);
			}
		},error:function(){
			alert("刷新失败,请检查连接！",0);
		}
	});
}
/**
 * 防止保持方法
 */
function enterKey(){};
function func_uior_valTypeImm(obj){};
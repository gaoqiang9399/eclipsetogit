/**
 * 业务解释：
 *        财务指标指的是线性计算的结果
 *        定量指标指的是阶段性计算的结果
 */
var entityData = {
	"evalScenceFinRel":{//评级财务评分指标项关联
		title:"财务评分指标",
		data:{
			"scenceNo":"评级场景编号",
			"indexNo":"指标编号",
			"finCode":"对应财务指标编号",
			"indexName":"指标名称",
			"indexDesc":"指标描述",
			"level":"指标级别",
			"upIndexNo":"上级指标级别",
			"stdVal":"标准值",
			"stdScore":"标准得分",
			"minVal":"最低值",
			"minScore":"最低得分",
			"maxVal":"最高值",
			"maxScore":"最高得分",
			"useFlag":"是否可用",
			"ctrl_btn":"操作按钮"
		},
		display:"scenceNo,indexNo,finCode,indexDesc,level,upIndexNo,useFlag",//不显示
		insertUrl:webPath+"/evalScenceFinRel/insertAjax;indexNo-indexNo;scenceNo-scenceNo",
		updateUrl:webPath+"/evalScenceFinRel/updateAjax;indexNo-indexNo;scenceNo-scenceNo",
		deleteUrl:webPath+"/evalScenceFinRel/deleteAjax;indexNo-indexNo;scenceNo-scenceNo"
	},
	"evalScenceDlRel":{//评级定量评分指标项关联
		title:"定量评分指标",
		data:{
			"scenceNo":"评级场景编号",
			"indexNo":"指标编号",
			"javaItem":"业务参数",
			"indexName":"指标名称",
			"indexDesc":"指标描述",
			"opSymbol1":"区间符号1",
			"opVal1":"区间值1",
			"opSymbol2":"区间符号2",
			"opVal2":"区间值2",
			"stdCore":"指标得分",
			"level":"指标级别",
			"upIndexNo":"上级指标编号",
			"ctrl_btn":"操作按钮"
		},
		display:"scenceNo,indexNo,indexDesc,upIndexNo,level,indexName",//不显示
		insertUrl:webPath+"/evalScenceDlRel/insertAjax;indexNo-indexNo;scenceNo-scenceNo",
		updateUrl:webPath+"/evalScenceDlRel/updateAjax;indexNo-indexNo;scenceNo-scenceNo",
		deleteUrl:webPath+"/evalScenceDlRel/deleteAjax;indexNo-indexNo;scenceNo-scenceNo"
	},
	"evalScenceDxRel":{//评级定性评分指标项关联
		title:"定性评分指标",
		data:{
			"scenceNo":"评级场景编号",
			"indexNo":"指标编号",
			"javaItem":"业务参数",
			"indexName":"指标名称",
			"indexDesc":"指标描述",
			"stdCore":"指标得分",
			"level":"指标级别",
			"upIndexNo":"上级指标编号",
			"ctrl_btn":"操作按钮"
		},
		display:"scenceNo,indexNo,indexDesc,upIndexNo,level,indexName",//不显示
		insertUrl:webPath+"/evalScenceDxRel/insertAjax;indexNo-indexNo;scenceNo-scenceNo",
		updateUrl:webPath+"/evalScenceDxRel/updateAjax;indexNo-indexNo;scenceNo-scenceNo",
		deleteUrl:webPath+"/evalScenceDxRel/deleteAjax;indexNo-indexNo;scenceNo-scenceNo"
	},
	"evalScenceAdjRel":{//评级调整评分指标项关联
		title:"调整评分指标",
		data:{
			"scenceNo":"评级场景编号",
			"indexNo":"指标编号",
			"javaItem":"业务参数",
			"indexName":"指标名称",
			"indexDesc":"指标描述",
			"stdCore":"指标得分",
			"level":"指标级别",
			"upIndexNo":"上级指标编号",
			"ctrl_btn":"操作按钮"
		},
		display:"scenceNo,indexNo,indexDesc,upIndexNo,level,indexName",//不显示
		insertUrl:webPath+"/evalScenceAdjRel/insertAjax;indexNo-indexNo;scenceNo-scenceNo",
		updateUrl:webPath+"/evalScenceAdjRel/updateAjax;indexNo-indexNo;scenceNo-scenceNo",
		deleteUrl:webPath+"/evalScenceAdjRel/deleteAjax;indexNo-indexNo;scenceNo-scenceNo"
	},
	"evalScenceRestrictRel":{//评级约束等级指标项关联表
		title:"约束等级指标",
		data:{
			"scenceNo":"评级场景编号",
			"indexNo":"指标编号",
			"javaItem":"业务参数",
			"indexName":"指标名称",
			"indexDesc":"指标描述",
			"opSymbol1":"区间符号1",
			"opVal1":"区间值1",
			"opSymbol2":"区间符号2",
			"opVal2":"区间值2",
			"opSymbol3":"区间符号3",
			"evalLevel":"约束级别",
			"level":"指标级别",
			"upIndexNo":"上级指标编号",
			"ctrl_btn":"操作按钮"
		},
		display:"scenceNo,indexNo,indexDesc,upIndexNo,level,indexName",//不显示
		insertUrl:webPath+"/evalScenceRestrictRel/insertAjax;indexNo-indexNo;scenceNo-scenceNo",
		updateUrl:webPath+"/evalScenceRestrictRel/updateAjax;indexNo-indexNo;scenceNo-scenceNo",
		deleteUrl:webPath+"/evalScenceRestrictRel/deleteAjax;indexNo-indexNo;scenceNo-scenceNo"
	},
	/*"evalScoreGradeConfig":{//分数等级配置表
		title:"分数等级配置",
		data:{
			"scenceNo":"评级场景编号",
			"configNo":"配置编号",
			"opScore1":"区间分数1",
			"opSymbol1":"区间符号1",
			"evalGrade":"评级得分",
			"opSymbol2":"区间符号2",
			"opScore2":"区间分数2",
			"evalLevel":"指标级别",
			"ctrl_btn":"操作按钮"
		},
		display:"scenceNo",//不显示
		insertUrl:webPath+"/evalScoreGradeConfig/insertAjax;indexNo-indexNo;scenceNo-scenceNo",
		updateUrl:webPath+"/evalScoreGradeConfig/updateAjax;indexNo-indexNo;scenceNo-scenceNo",
		deleteUrl:webPath+"/evalScoreGradeConfig/deleteAjax;indexNo-indexNo;scenceNo-scenceNo"
	}*/
};
function makeOptionsJQ(obj, allowOptionStr,defVal) {
	var p =obj.parent();
	if(typeof(p.data("options"))=="undefined"){
		p.data("options",obj);
	}
	p.empty();
	$.each(p.data("options"), function(i,list) {
		if(allowOptionStr != '' && $(list).val() != ''&&allowOptionStr!==undefined&&allowOptionStr!=null){
			$.each(allowOptionStr.split(","),function(index,val){
				if($(list).val()==val){
					p.append($(list));
					return false;
				}
			});
		}
	});
	if(typeof(defVal)!="undefined"){
		p.val(defVal);
	}
}
var evalLevelParm,opSymbolParm,dataEntity;
var editAppProA = "<a onclick='editAppProperty(this)' href='javascript:void(0)' title = '修改评级指标'><i class='i i-bianji2'></i></a>";
$(function(){
	$(".table_content").each(function(){
		var $tbContObj = $(this);
	});
	$(".select-border .colmon input").unbind();
	$(".select-border .colmon input").bind({keyup:function(event){
		var keyVal = event.which;
		if(keyVal=="13"){
			//initTableSelect();
		}
		return false;
	}});
	evalLevelParm = getListForKeyName('EVAL_LEVEL');
	opSymbolParm = getListForKeyName('OP_SYMBOL');
});
/**
 * 字表parmDic的双击修改
 */
var dataVal = {},parmDicUrl,keyName,SynchronousLoadding = true;

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
		var $tableParent = $(".table_content.parent").find("table.ls_list");
		tableId = $tableParent.attr("title");
		url = webPath+"/evalScenceConfig/findByPageAjax";
		var dataParam = {};
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
			}
		});
	}
}
/**
 * 初始化翻页信息
 * @param {Object} $obj
 * @param {Object} pager
 */
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
/**
 * get pager
 * @param {Object} $obj
 */
function getPager($obj){
	var pager = {};
	pager.pageSize = parseInt($obj.find(".pageer").attr("pageSize"));
	pager.pageNo = parseInt($obj.find(".pageer").attr("pageNo"));
	pager.pageSum = parseInt($obj.find(".pageer").attr("pageSum"));
	return pager;
}
/**
 * set pager
 * @param {Object} $obj
 * @param {Object} pager
 */
function setPager($obj,pager){
	$obj.find(".pageer").attr("pageSize",pager.pageSize);
	$obj.find(".pageer").attr("pageNo",pager.pageNo);
	$obj.find(".pageer").attr("pageSum",pager.pageSum);
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
					alert(data.msg,1);
				}else{
					alert(data.msg,0);
				}
			},error:function(){
				alert(top.getMessage("FAILED_DELETE"),0);
			}
		});
	});
}
/**
 * 获得查询表头
 */
function getSelectInputVal(){
	var evalScenceName = $(".select-border").find("input[name='evalScenceName']").val();
	var data = [];
	data.push({customQuery:evalScenceName});
	return data;
}
/**
 * 全局查询
 */
function initTableSelect(){
	var dataParam = JSON.stringify(getSelectInputVal());
	var $tableParent = $(".table_content.parent").find("table.ls_list");
	var tableId = $tableParent.attr("title");
	var tableType = "thirdTableTag";
	$.ajax({
		type:"post",
		url:webPath+"/evalScenceConfig/findByPageAjax",
		async:false,
		data:{
			ajaxData:dataParam,
			pageSize:25,
			tableId:tableId,
			tableType:tableType
		},success:function(data){
			$tableParent.find("tbody").html($(data.ipage.result).find("tbody").html());
			setPager($(".table_content.parent"),data.ipage);
			initPageShow($(".table_content.parent"),data.ipage);
			$(".table_content").mCustomScrollbar("scrollTo","top");
			SynchronousLoadding = true;
		},error:function(){
			
		}
	});
}

window.getByIdForList = function(obj,urlArgs){
	var url = webPath+"/evalScenceConfig/getByIdForEvalListAjax?evalScenceNo="+evalScenceNo;
	$obj = $("#configDiv");
	$.ajax({
		type:"post",
		url:url,
		async:false,
		success:function(data){
			if(data.flag=="success"){
				var saveData = {};
				if(data.listData == null){
					
				}else{
					$.each(data.listData, function(key,obj) {
						saveData[key] = "1";
					});
					successToinitUpdate($obj,saveData,data.scenceNo,data.listData);
				}
			}else if(data.flag=="error"){
				alert(data.msg,0);
			}
		},error:function(){
			alert("查询数据链接出错",0);
		}
	});
};
/**
 * 
 * @param {Object} obj
 */
function inputConfig(obj,url){
	var $moduleParent = $(obj).parents(".module_parent");
	$moduleParent.animate({top:"-35px"});
	$moduleParent.parents(".select-border").animate({height:"70px"});
	var $tableContent = $(".table_content");
	$tableContent.animate({height:$tableContent.height()-22},function(){
		$(".table_content").mCustomScrollbar("update");
	});
	makeOptionsJQ($moduleParent.find(".insert-module").find("select").find("option"),'1,2',1);
	$moduleParent.attr("saveFlag","insert");
	$moduleParent.data("url",url);
	$moduleParent.find(".insert-module").find("input").each(function(index){
		var name = $(this).attr("name");
		var inputType =  $(this).attr("type");
		if(name == "evalIndexTypeRel"){
			$(this).prop('checked',false);
		}else if(inputType!="button"){
			$(this).val("");
		}
	});
}
function backConfig(obj){
	var $obj = $(obj);
	var $moduleParent = $obj.parents(".module_parent");
	$moduleParent.animate({top:"0px"});
	$moduleParent.parents(".select-border").animate({height:"38px"});
	var $tableContent = $(".table_content");
	$tableContent.animate({height:$tableContent.height()+42},function(){
		$tableContent.mCustomScrollbar("update");
	});
	showTd($obj);
}
/**
 * 评级配置场景保存
 * @param {Object} obj
 * @param {Object} url
 */
function saveConfig(obj,url){
	var $obj = $(obj);
	var parmData = $obj.serializeJSON();
	var  initFlag = false;
	var  saveFlag = $obj.parents(".module_parent").attr("saveFlag");
	if(parmData.evalScenceName!=null&&parmData.evalScenceName!=""){
		initFlag = true;
	}else{
		alert(top.getMessage("NOT_FORM_EMPTY", "[评级配置场景名称]"),0);
		return false;
	}
	if(getJsonObjLength(parmData)>1){ initFlag = true; }else{
		alert("评级指标类型 必须选择,可选多个！",0);
		return false;
	}
	var dlScorePercent = parseInt($obj.find("input[name='dlScorePercent']").val());
	var dxScorePercent = parseInt($obj.find("input[name='dxScorePercent']").val());
	if((dlScorePercent+dxScorePercent)>100){
		alert("定量评分权重, 定性评分权重之和不能超过100",0);
		return false;
	}
	if(dlScorePercent<0||dxScorePercent<0){
		alert("定量评分权重, 定性评分权重不可小于零",0);
		return false;
	}
	parmData = $(obj).serializeArray();
	if(saveFlag=="update"){
		var evalScenceNo = $obj.parents(".module_parent").data("evalScenceNo");
		parmData.push({"name":"evalScenceNo","value":evalScenceNo});
	}else{
		url = $obj.parents(".module_parent").data("url");
	}
	$.ajax({
		type:"post",
		url:url,
		data:{ajaxData:JSON.stringify(parmData)},
		async:false,
		success:function(data){
			if(data.flag=="success"){
				alert(data.msg,1);
				if(initFlag){
					var saveData = {};
					$.each(data.listData, function(key,obj) {
						saveData[key] = "1";
					});
					successToinitUpdate($obj,saveData,data.evalScenceNo,data.listData);
					$obj.parents(".module_parent").data("evalScenceNo",data.evalScenceNo);
					$obj.parents(".module_parent").attr("saveFlag","update");
					initTableSelect();
				}else{
					
				}
			}else if(data.flag=="error"){
				alert(data.msg,0);
			}
		},error:function(){
			alert("保存链接错误，请与管理员联系",0);
		}
	});
}

var successToinitUpdate = function($obj,saveData,scenceNo,listData){
	initSettings($(".content .content_setting .settings"),entityData,saveData,scenceNo,listData);
	var $content_config = $obj.parents(".content").find(".row_content").find(".content_config");
	var $content_setting = $obj.parents(".content").find(".row_content").find(".content_setting");
	var $content_type  = $obj.parents(".content").find(".row_content").find(".content_type");
	var $selectBorder = $obj.parents(".content").find(".select-border");
	var selectBorderHeight = $selectBorder.height();
	if(selectBorderHeight!="68"&&selectBorderHeight!="70"){//高度70系统获取的就是68
		$selectBorder.animate({height:"70px"});
		var $tableContent = $(".table_content");
		$tableContent.animate({height:$tableContent.height()-22},function(){
			$(".table_content").mCustomScrollbar("update");
		});
	}
	hiddenTd($content_config.find("table"),"evalScenceNo");
};

window.selectConfig = function(obj){
	initTableSelect();
};
/**
 * 隐藏列
 * @param {Object} obj
 * @param {Object} data
 */
function hiddenTd(obj,data){
	var $obj = $(obj);
	var array = data.split(",");
	$.each(array, function(index,val) {
		var colIndex = $obj.find("thead").find("th[name="+val+"]").index();
		$obj.find("thead").find("th").each(function(tdIndex,value){
			if($(this).attr("name")==val){
				$(this).show();
				$obj.find("tbody").find("tr").each(function(){
					$(this).find("td").eq(tdIndex).show();
				});
			}else{
				$(this).hide();
				$obj.find("tbody").find("tr").each(function(){
					$(this).find("td").eq(tdIndex).hide();
				});
			}
		});
	});
}
/**
 * 显示列
 * @param {Object} obj
 */
function showTd(obj){
	var $obj = $(obj);
	var $content_config = $obj.parents(".content").find(".row_content").find(".content_config");
	var $content_setting = $obj.parents(".content").find(".row_content").find(".content_setting");
	var $content_type  = $obj.parents(".content").find(".row_content").find(".content_type");
	$content_type.hide();
	$content_config.show();
	$content_config.animate({width:"100%"});
	$content_setting.animate({width:"0%"},function(){
		$(this).hide();
	});
	setTimeout(function(){$content_config.find(".ls_list").find("th,td").show();},300);
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
/**
 * 初始化setting 
 * @param {Object} obj
 * @param {Object} datas 初始化表数据
 * @param {Object} filter 过滤条件
 */
function initSettings(obj,datas,filter,scenceNo,listData){
	var $obj = $(obj);
	var types = {};
	var $settingUl = $('<ul class="data-content"></ul>');
	var li_index = 0;
	var evalGradeCardList=listData["evalGradeCardList"];
	$.each(evalGradeCardList,function(i,obj){
		$.each(datas,function(entityKey,data){
			var cardTitle = obj.gradeCardName;
			var cardId = obj.gradeCardId;
			var gradeCardType="";
			var flag = true;
			$.each(filter, function(key,val) {
				if(entityKey+cardId==key){
					flag = false;
					return false;
				}
			});
			if(flag){
				return true;
			}
			if(entityKey=="evalScenceFinRel"){
				gradeCardType="1";
			}
			if(entityKey=="evalScenceDlRel"){
				gradeCardType="2";
			}
			if(entityKey=="evalScenceDxRel"){
				gradeCardType="3";
			}
			if(entityKey=="evalScenceAdjRel"){
				gradeCardType="4";
			}
			if(entityKey=="evalScenceRestrictRel"){
				gradeCardType="5";
			}
			if(obj.gradeCardType==gradeCardType){
				types[entityKey]=cardTitle;
				var $li = $("<li id='"+cardId+"' style='margin-top: 20px;'></li>");
				if(li_index ==0){
					$li.css("display","block");
				}else{
					//$li.css("display","none");
				}
				var $lititle = $('<div class="title_btn"></div>');
				$lititle.append('<ol id='+entityKey+' class="li_title" style="color: #32b5cb;font-size: 14px;margin-left:-8px">'+
						'<li class="active"><span class="config-item"><span name="title">'+cardTitle+'</span><i class="i i-bianji2 item-delete" onclick="MfEvalGradeCardComm.editGradeCard(\''+cardId+'\');"></i><i class="i i-lajitong item-delete" onclick="MfEvalGradeCardComm.deleteGradeCard(\''+cardId+'\');"></i></span></li></ol>');
				if(entityKey=="evalScenceDlRel"||entityKey=="evalScenceFinRel"||entityKey=="evalScenceRestrictRel"||entityKey=="evalScenceDxRel"){
					$lititle.append('<div class="li_btn colse">'+
							//'<input type="button" value="新增评级指标项" onclick="addPropertyItem(this)"/>'+
							'<input type="button" value="添加分组" onclick="addfirstLev(this,\''+getUrlSplit(datas[entityKey].insertUrl)+'\')"/>'+
					'</div>');
				}else if(entityKey=="evalScenceAdjRel"){
					$lititle.append('<div class="li_btn colse">'+
							//'<input type="button" value="新增评级指标项" onclick="addPropertyItem(this)"/>'+
							'<input type="button" value="添加分组" onclick="addAdjRel(this,\''+getUrlSplit(datas[entityKey].insertUrl)+'\')"/>'+
					'</div>');
				}else{
					$lititle.append('<div class="li_btn colse"></div>');
				}
				$li.attr("data-name",entityKey);
				var $table = $('<table class="ls_list"><thead><tr></tr></thead><tbody></tbody></table>');
				var colKeyIndex = 0;
				$.each(data.data,function(colKey,detail){
					if(data.display.indexOf(colKey)==-1&&colKey!="ctrl_btn"){
						if(colKeyIndex>0){
							$table.find("thead tr").append('<th style="width:90px" name="'+colKey+'">'+detail+'</th>');
						}else{
							$table.find("thead tr").append('<th name="'+colKey+'">'+detail+'</th>');
						}
						colKeyIndex++;
					}else if(colKey=="ctrl_btn"){
						$table.find("thead tr").append('<th style="width:150px" name="'+colKey+'">'+detail+'</th>');
					}
				});
				if(listData!==undefined){
					var  thLength = $table.find("thead tr th").length;
					$.each(listData[entityKey+cardId], function(index,entityObj) {
						if(entityKey=="evalScenceDlRel"){
							initSeting.initDl(datas,$table,entityObj,thLength,entityKey,scenceNo);
						}else if(entityKey=="evalScenceFinRel"){
							initSeting.initFin(datas,$table,entityObj,thLength,entityKey,scenceNo);
						}else if(entityKey=="evalScenceRestrictRel"){
							initSeting.initRes(datas,$table,entityObj,thLength,entityKey,scenceNo);
						}else if(entityKey=="evalScenceDxRel"){
							initSeting.initDx(datas,$table,entityObj,thLength,entityKey,scenceNo);
						}else if(entityKey=="evalScenceAdjRel"){
							initSeting.initAdj(datas,$table,entityObj,thLength,entityKey,scenceNo);
						}
					});
				}
				$li.append($lititle);
				$li.append('<div class="li_content"></div>');
				$li.find(".li_content").append($table);
				$settingUl.append($li);
				li_index++;
			}
		});
	});
	$obj.html($settingUl);
	$obj.data("scenceNo",scenceNo);
	initTypes($obj,types);
}
var initSeting = {
	initFin:function(datas,$table,entityObj,thLength,entityKey,scenceNo){//初始化线性定量评分
		var $tr = $("<tr></tr>");
		$tr.data("entityObj",entityObj);
		var level = entityObj.level;
		var entityIndex = 0;
		$table.find("thead tr th").each(function(index) {
			var key = $(this).attr("name");
			var val = entityObj[key];
            var $td;
			if(level==1){
				if(key=="indexName"){
					$tr.append('<td class="font_weight" colspan="'+(thLength-1)+'" name="'+key+'">'+val+'</td>');
				}else if(key=="ctrl_btn"){
					$td = $('<td class="ctrl_btn"></td>');
					$td.append('<a onclick="addsecondtarget(this,\''+datas[entityKey].insertUrl+'\')" href="javascript:void(0)">添加</a>');
					$td.append('<a onclick="savefirst(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="updatefirsttarget(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="delfirsttarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
					$tr.append($td);
				}
				return true;
			}else if(level==2){
				$td = $("<td></td>");
			    if(key=="indexName"){
			    	$td = $('<td name="'+key+'">--'+val+'</td>');
			    }else if(key=="ctrl_btn"){
					$td.append('<a onclick="updatesecondtarget(this,\''+datas[entityKey].insertUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="savesecond(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="delsecondtarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
				}else{
					$td = $('<td><span name="'+key+'">'+val+'</span><input name="'+key+'" class="center" type="text"/></td>');
					if(key=="stdCore"){
						$td.find("input[type='text']").val(changeDoubleForStr(val)).hide();
					}else{
						$td.find("input[type='text']").val(val).hide();
					}
					$td.find("input[type='text']").attr("size","10");
				}
				$tr.append($td);
			}
			entityIndex++;
		});
		$tr.data("indexNo",entityObj.indexNo);
		$tr.data("level",entityObj.level);
		$tr.data("upIndexNo",entityObj.upIndexNo);
		$tr.data("finCode",entityObj.finCode);
		if(level==1){
			$table.find("tbody").append($tr);
		}else{//level=2  或 level=3
			var $targetTr = $table.find("tbody").find("tr").eq(0);
			var existeParentFlag = false;
			$table.find("tbody tr").each(function(){
				if($(this).data("indexNo")==entityObj.upIndexNo){
					$targetTr = $(this);
					existeParentFlag = true;
				}else if($(this).data("upIndexNo")==entityObj.upIndexNo){
					$targetTr = $(this);
				}
			});
			if(existeParentFlag){
				$targetTr.after($tr);
			}
		}
	},
	initDl:function(datas,$table,entityObj,thLength,entityKey,scenceNo){//初始化阶段性定量评分
		var $tr = $("<tr></tr>");
		$tr.data("entityObj",entityObj);
		$tr.data("indexNo",entityObj.indexNo);
		$tr.data("level",entityObj.level);
		$tr.data("upIndexNo",entityObj.upIndexNo);
		var level = entityObj.level;
		var entityIndex = 0;
		$table.find("thead tr th").each(function(index) {
			var key = $(this).attr("name");
			var val = entityObj[key];
            var $td;
			if(level==1){
				if(key=="indexName"){//indexName
				}else if(key=="ctrl_btn"){
					val = entityObj["indexName"];
					$tr.append('<td class="font_weight" colspan="'+(thLength-1)+'" name="indexName">'+val+'</td>');
					$td = $('<td class="ctrl_btn"></td>');
					$td.append('<a onclick="addsecondDltarget(this,\''+datas[entityKey].insertUrl+'\')" href="javascript:void(0)">添加</a>');
					$td.append('<a onclick="savefirst(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="updatefirsttarget(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="delfirsttarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
					$tr.append($td);
				}
				return true;
			}else if(level==2){
				$td = $("<td></td>");
				if(key=="javaItem"){
					var dicType = "DL";
					$td.append(getSelectTargHtml(AppPropertyDatas.finDatas,key,true));
					$td.append(editAppProA);
					$td.find("select").val(val);
					val = entityObj["indexName"];
					$td.append("<span>"+val+"</span>");
					$td.find("select,a").hide();
				}else if(key=="indexName"){
					$td = $('<td name="'+key+'">--'+val+'</td>');
				}else if(key=="ctrl_btn"){
					$td.append('<a class="abatch" onclick="addthirdDltarget(this,\''+entityData[entityKey].insertUrl+'\');return false;" href="javascript:void(0);">添加</a>');
					$td.append('<a onclick="updatesecondtarget(this,\''+datas[entityKey].insertUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="savesecond(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="delsecondtarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
				}
				$tr.append($td);
			}else if(level==3){
				$td = $("<td></td>");
			    if(key=="indexName"){
			    	$td = $('<td name="'+key+'">----'+val+'</td>');
			    }else if(key=="opSymbol1"||key=="opSymbol2"){
					$td.append(getSelectTargHtml(opSymbolParm,key));
					$td.find("select").val(val).hide();
					var optionhtml = $td.find("select").find("option[value="+val+"]").html();
					$td.append("<span>"+optionhtml+"</span>");
					$table.find("tbody tr").each(function(){
						if($(this).data("indexNo")==entityObj.upIndexNo){
							$targetTr = $(this);
							var $selectObj = $targetTr.find("select[name='javaItem']");
							if($selectObj){
								var $thisOp = $selectObj.find("option:selected");
								if($thisOp.data("data")!==undefined){
									var data = $thisOp.data("data");
									var type = data.type,keyName = data.keyName;
									if(type=="02"&&keyName!=null&&keyName!==undefined&&keyName!=""){//数据字典相
										$td.find("select[name=opSymbol1]").attr("disabled","disabled").css({"display":"none"});;
										$td.find("select[name=opSymbol2]").attr("disabled","disabled");
									}
								}
							}
						}
					});
				}else if(key=="javaItem"){
					//var dicType = "DL";
					$td.append(getSelectTargHtml2(AppPropertyDatas.finDatas,key,true,val));
					val = entityObj["indexName"];
					$td.append("<span>"+val+"</span>");
					$td.find("select").hide();
					$td.find("a").hide();
				}else if(key=="ctrl_btn"){
					$td.append('<a onclick="updatethirdtarget(this,\''+datas[entityKey].insertUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="savethird(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="delthirdtarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
				}else if(key=="opVal1"||key=="opVal2"){
					var $select;
					$table.find("tbody tr").each(function(){
						if($(this).data("indexNo")==entityObj.upIndexNo){
							$targetTr = $(this);
							var $selectObj = $targetTr.find("select[name='javaItem']");
							if($selectObj){
								var $thisOp = $selectObj.find("option:selected");
								if($thisOp.data("data")!==undefined){
									var data = $thisOp.data("data");
									var type = data.type,keyName = data.keyName;
									if(type=="02"&&keyName!=null&&keyName!==undefined&&keyName!=""){//数据字典相
										var parmDic = getListForKeyName(keyName);
										if(parmDic!==undefined&&parmDic!=null&&parmDic!=""){
											$select = $('<select><select>');
											$.each(parmDic,function(key,obj){
												var val = obj.val;
												var $option = $('<option></option>');
												$option.attr("value",key);
												$option.text(val);
												$option.appendTo($select);
											});
											$select.css({"width":"78px","text-align":"center"});
										}
									}
								}
							}
						}
					});
					if($select){
						if(key=="opVal1"){
							$select.attr("disabled","disabled");
							$select.css({"display":"none"});
						}
						if(key=="opVal2"){
							$select.attr("onchange","selectOpValOnchange(this);");
						}
						$td.append($select);
						$select.attr("name",key);
						$td.find("select").val(val);
					}else{
						$td = $('<td><input name="'+key+'" class="center" type="text"/></td>');
						$td.find("input[type='text']").val(val).hide();
						$td.find("input[type='text']").attr("size","10");
						$td.append("<span>"+val+"</span>");
					}
				}else{
					$td = $('<td><input name="'+key+'" class="center" type="text"/></td>');
					if(key=="stdCore"){
						$td.find("input[type='text']").val(changeDoubleForStr(val)).hide();
					}else{
						$td.find("input[type='text']").val(val).hide();
					}
					$td.find("input[type='text']").attr("size","10");
					$td.append("<span name='"+key+"'>"+changeDoubleForStr(val)+"</span>");
				}
				$tr.append($td);
			}
			entityIndex++;
		});
		/*if(entityKey=="evalScoreGradeConfig"){
			$table.find("tbody").append($tr);
		}else*/ 
		if(level==1){
			$table.find("tbody").append($tr);
		}else{//level=2  或 level=3
			var $targetTr = $table.find("tbody").find("tr").eq(0);
			var existeParentFlag = false;
			$table.find("tbody tr").each(function(){
				if($(this).data("indexNo")==entityObj.upIndexNo){
					$targetTr = $(this);
					existeParentFlag = true;
				}else if($(this).data("upIndexNo")==entityObj.upIndexNo){
					$targetTr = $(this);
				}
			});
			if(existeParentFlag){
				$targetTr.after($tr);
			}
		}
	},
	initDx:function(datas,$table,entityObj,thLength,entityKey,scenceNo){//初始化定性评分
		var $tr = $("<tr></tr>");
		$tr.data("entityObj",entityObj);
		var level = entityObj.level;
		var entityIndex = 0;
		$table.find("thead tr th").each(function(index) {
			var key = $(this).attr("name");
			var val = entityObj[key];
            var $td;
			if(level==1){
				if(key=="javaItem"){
					val = entityObj["indexName"];
					$tr.append('<td class="font_weight" name="indexName">'+val+'</td>');
				}else if(key=="ctrl_btn"){
					$td = $('<td class="ctrl_btn"></td>');
					$td.append('<a onclick="addDxfirstLev(this,\''+datas[entityKey].insertUrl+'\')" href="javascript:void(0)">添加</a>');
					$td.append('<a onclick="savefirst(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="updatefirsttarget(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="delfirsttarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
					$tr.append($td);
				}else{
					$tr.append('<tdname="'+key+'"></td>');
				}
			}else if(level==2){
				if(key=="indexName"){
					$tr.append('<td class="font_weight"  colspan="'+(thLength-1)+'" name="'+key+'">'+val+'</td>');
				}else if(key=="ctrl_btn"){
					$td = $('<td class="ctrl_btn"></td>');
					//$td.append('<a onclick="addsecondtarget(this,\''+datas[entityKey].insertUrl+'\')" href="javascript:void(0)">添加</a>');
					$td.append('<a onclick="updatesecondtarget(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="savefirst(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="delsecondtarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
					$tr.append($td);
				}else if(key=="javaItem"){
					$td = $("<td colspan='"+(thLength-1)+"'></td>");
					$td.append(getSelectTargHtml(AppPropertyDatas.dxDatas,key,true));
					$td.append(editAppProA);
					$td.find("select").val(entityObj["indexDesc"]);
					val = entityObj["indexName"];
					$td.append("<span>"+val+"</span>");
					$td.append('<input type="button" onclick="cancelOperation(this);" value="取消"/>');
					$td.find("select,a,input").hide();
					$tr.append($td);
				}
				return true;
			}else if(level==3){
				$td = $("<td></td>");
				if(key=="javaItem"){
					val = entityObj["indexName"];
		    		$td = $('<td name="indexName">----'+val+'</td>');
		    	}else if(key=="ctrl_btn"){
					$td = $('<td class="ctrl_btn"></td>');
					$td.append('<a onclick="updatethirdtarget(this,\''+datas[entityKey].insertUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="savethird(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="delthirdtarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
				}else{
					$td = $('<td><input name="'+key+'" class="center" type="text"/></td>');
					if(key=="stdCore"){
						$td.find("input[type='text']").val(changeDoubleForStr(val)).hide();
					}else{
						$td.find("input[type='text']").val(val).hide();
					}
					$td.append("<span name='"+key+"'>"+changeDoubleForStr(val)+"</span>");
					//$td.append('<input type="button" onclick="cancelOperation(this);" value="取消" style="display:none">');
					$td.find("input[type='text']").attr("size","10");
				}
				$tr.append($td);
				
			}
			entityIndex++;
		});
		$tr.data("indexNo",entityObj.indexNo);
		$tr.data("level",entityObj.level);
		$tr.data("upIndexNo",entityObj.upIndexNo);
		if(level==1){
			$table.find("tbody").append($tr);
		}else{//level=2  或 level=3
			var $targetTr = $table.find("tbody").find("tr").eq(0);
			var existeParentFlag = false;
			$table.find("tbody tr").each(function(){
				if($(this).data("indexNo")==entityObj.upIndexNo){
					$targetTr = $(this);
					existeParentFlag = true;
				}else if($(this).data("upIndexNo")==entityObj.upIndexNo){
					$targetTr = $(this);
				}
			});
			if(existeParentFlag){
				$targetTr.after($tr);
			}
		}
	},
	initAdj:function(datas,$table,entityObj,thLength,entityKey,scenceNo){//初始化调整
		var $tr = $("<tr></tr>");
		$tr.data("entityObj",entityObj);
		var level = entityObj.level;
		var entityIndex = 0;
		$table.find("thead tr th").each(function(index) {
			var key = $(this).attr("name");
			var val = entityObj[key];
            var $td;
			if(level==1){
				if(key=="javaItem"){
					key = $(this).attr("indexName");
					val = entityObj["indexName"];
					$tr.append('<td class="font_weight" name="'+key+'">'+val+'</td>');
				}else if(key=="ctrl_btn"){
					$td = $('<td class="ctrl_btn"></td>');
					$td.append('<a onclick="addsecondtarget(this,\''+datas[entityKey].insertUrl+'\')" href="javascript:void(0)">添加</a>');
					$td.append('<a onclick="savefirst(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="updatefirsttarget(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="delfirsttarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
					$tr.append($td);
				}else{
					$tr.append('<tdname="'+key+'"></td>');
				}
			}else if(level==2){
				$td = $("<td></td>");
				if(key=="indexName"){
		    		$td = $('<td name="'+key+'">--'+val+'</td>');
		    	}else if(key=="javaItem"){
		    		$td = $("<td></td>");
					$td.append(getSelectTargHtml(AppPropertyDatas.dxDatas,key,true));
					$td.append(editAppProA);
					$td.find("select").val(entityObj["indexDesc"]);
					val = entityObj["indexName"];
					$td.append("<span>"+val+"</span>");
					$td.append("<input type='button' onclick='cancelOperation(this);' value='取消' style='display:none'>");
					$td.find("select,a").hide();
					$tr.append($td);
		    	}else if(key=="ctrl_btn"){
					$td = $('<td class="ctrl_btn"></td>');
					//$td.append('<a class="abatch" onclick="addthirdtarget(this,\''+entityData[entityKey].insertUrl+'\');return false;" href="javascript:void(0);">添加</a>');
					$td.append('<a onclick="updatesecondtarget(this,\''+datas[entityKey].insertUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="savesecond(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="delsecondtarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
				}
				$tr.append($td);
			}else if(level==3){
				$td = $("<td></td>");
				if(key=="javaItem"){
					val = entityObj["indexName"];
		    		$td = $('<td name="indexName">----'+val+'</td>');
		    	}else if(key=="ctrl_btn"){
					$td = $('<td class="ctrl_btn"></td>');
					$td.append('<a onclick="updatethirdtarget(this,\''+datas[entityKey].insertUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="savethird(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="delthirdtarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
				}else{
					$td = $('<td><input name="'+key+'" class="center" type="text"/></td>');
					if(key=="stdCore"){
						$td.find("input[type='text']").val(changeDoubleForStr(val)).hide();
					}else{
						$td.find("input[type='text']").val(val).hide();
					}
					$td.append("<span name='"+key+"'>"+changeDoubleForStr(val)+"</span>");
					//$td.append('<input type="button" onclick="cancelOperation(this);" value="取消" style="display:none">');
					$td.find("input[type='text']").attr("size","10");
				}
				$tr.append($td);
				
			}
			entityIndex++;
		});
		$tr.data("indexNo",entityObj.indexNo);
		$tr.data("level",entityObj.level);
		$tr.data("upIndexNo",entityObj.upIndexNo);
		if(level==1){
			$table.find("tbody").append($tr);
		}else{//level=2  或 level=3
			var $targetTr = $table.find("tbody").find("tr").eq(0);
			var existeParentFlag = false;
			$table.find("tbody tr").each(function(){
				if($(this).data("indexNo")==entityObj.upIndexNo){
					$targetTr = $(this);
					existeParentFlag = true;
				}else if($(this).data("upIndexNo")==entityObj.upIndexNo){
					$targetTr = $(this);
				}
			});
			if(existeParentFlag){
				$targetTr.after($tr);
			}
		}
	},
	initRes:function(datas,$table,entityObj,thLength,entityKey,scenceNo){//初始化约束
		var $tr = $("<tr></tr>");
		$tr.data("entityObj",entityObj);
		var level = entityObj.level;
		var entityIndex = 0;
		$table.find("thead tr th").each(function(index) {
			var key = $(this).attr("name");
			var val = entityObj[key];
            var $td;
			if(level==1){
				if(key=="indexName"){
				}else if(key=="ctrl_btn"){
					val = entityObj["indexName"];
					$tr.append('<td class="font_weight" colspan="'+(thLength-1)+'" name="indexName">'+val+'</td>');
					$td = $('<td class="ctrl_btn"></td>');
					$td.append('<a onclick="addsecondtarget(this,\''+datas[entityKey].insertUrl+'\')" href="javascript:void(0)">添加</a>');
					$td.append('<a onclick="savefirst(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="updatefirsttarget(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="delfirsttarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
					$tr.append($td);
				}
				return true;
			}else if(level==2){
				var optionhtml="";
				$td = $("<td></td>");
				if(key=="indexName"){
			    	$td = $('<td name="'+key+'">--'+val+'</td>');
			    }else if(key=="evalLevel"){//约束级别
					$td.append(getSelectTargHtml(evalLevelParm,'evalLevel'));
					$td.find("select").val(val).hide();
					optionhtml = $td.find("select").find("option[value="+val+"]").html();
					$td.append("<span>"+optionhtml+"</span>");
				}else if(key=="opSymbol1"||key=="opSymbol2"||key=="opSymbol3"){
					$td.append(getSelectTargHtml(opSymbolParm,key));
					$td.find("select").val(val).hide();
					optionhtml = $td.find("select").find("option[value="+val+"]").html();
					$td.append("<span>"+optionhtml+"</span>");
				}else if(key=="javaItem"){
					$td.append(getSelectTargHtml(AppPropertyDatas.dxDatas,key,"this"));
					$td.append(editAppProA);
					$td.find("select").val(val).hide();
					$td.find("a").hide();
					optionhtml = $td.find("select").find("option[value="+val+"]").html();
					$td.append("<span>"+optionhtml+"</span>");
				}else if(key=="ctrl_btn"){
					$td.addClass("ctrl_btn");
					$td.append('<a onclick="updatesecondtarget(this,\''+datas[entityKey].insertUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="savesecond(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="delsecondtarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
					$tr.append($td);
				}else{
					$td = $('<td><input name="'+key+'" class="center" type="text"/></td>');
					if(key=="stdCore"){
						$td.find("input[type='text']").val(changeDoubleForStr(val)).hide();
					}else{
						$td.find("input[type='text']").val(val).hide();
					}
					$td.find("input[type='text']").attr("size","10");
					$td.append("<span>"+changeDoubleForStr(val)+"</span>")
				}
				$tr.append($td);
			}
			entityIndex++;
		});
		$tr.data("indexNo",entityObj.indexNo);
		$tr.data("level",entityObj.level);
		$tr.data("upIndexNo",entityObj.upIndexNo);
		if(level==1){
			$table.find("tbody").append($tr);
		}else{//level=2  或 level=3
			var $targetTr = $table.find("tbody").find("tr").eq(0);
			var existeParentFlag = false;
			$table.find("tbody tr").each(function(){
				if($(this).data("indexNo")==entityObj.upIndexNo){
					$targetTr = $(this);
					existeParentFlag = true;
				}else if($(this).data("upIndexNo")==entityObj.upIndexNo){
					$targetTr = $(this);
				}
			});
			if(existeParentFlag){
				$targetTr.after($tr);
				var $selectObj = $tr.find("select[name='javaItem']");
				if($selectObj){
					var $select;
					var $thisOp = $selectObj.find("option:selected");
					if($thisOp.data("data")!==undefined){
						var data = $thisOp.data("data");
						var type = data.type,keyName = data.keyName;
						if(type=="02"&&keyName!=null&&keyName!==undefined&&keyName!=""){//数据字典相
							var parmDic = getListForKeyName(keyName);
							if(parmDic!==undefined&&parmDic!=null&&parmDic!=""){
								$select = $('<select><option></option><select>');
								$.each(parmDic,function(key,obj){
									var val = obj.val;
									var $option = $('<option></option>');
									$option.attr("value",key);
									$option.text(val);
									$option.appendTo($select);
								});
								$select.css({"width":"78px","text-align":"center"});
							}
						}
					}
					if($select){
						var $opVal1 = $tr.find("input[name='opVal1']");
						var $opVal2 = $tr.find("input[name='opVal2']");
						var $select1 = $select.clone();
						var $select2 = $select.clone();
						$select1.attr("name","opVal1");
						$select2.attr("name","opVal2");
						$select1.val($opVal1.val());
						$select2.val($opVal2.val());
						$opVal1.parent().html($select1);
						$opVal2.parent().html($select2);
					}
				}
			}
		}
	},
	initGra:function(datas,$table,entityObj,thLength,entityKey,scenceNo){//初始化评分等级
		var $tr = $("<tr></tr>");
		$tr.data("entityObj",entityObj);
		var level = entityObj.level;
		var entityIndex = 0;
		$table.find("thead tr th").each(function(index) {
			var key = $(this).attr("name");
			var val = entityObj[key];
			var $td = $("<td></td>");
			if(key=="opSymbol1"||key=="opSymbol2"){
				$td.append(getSelectTargHtml(opSymbolParm,key));
				$td.find("select").val(val);
			}else if(key=="evalLevel"){//约束级别
				$td.append(getSelectTargHtml(evalLevelParm,'evalLevel'));
				//
				var editAssessAhtml =  "<a onclick='editAssess(this)' href='javascript:void(0)' title = '修改级别描述'><i class='i i-bianji2'></i></a>";
				/*if(val==null){
					editAssessAhtml = editAssessAhtml+" style='display:none'";
				}
				$td.append(editAssessAhtml+"><i class='i i-bianji2'></i></a>");*/
				$td.append(editAssessAhtml);
				$td.find("select").val(val);
			}else if(key=="opScore1"||key=="opScore2"){
				$td.append('<input name="'+key+'" class="center" type="text"/>');
				$td.find("input[type=text]").val(val);
				$td.find("input[type=text]").css({width:"90px"});
			}else if(key=="evalGrade"){
				$td.append('评级得分');
			}else if(key=="ctrl_btn"){
				$td.addClass("ctrl_btn");
				$td.append('<a onclick="saveTr(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
			}else{
				$td.text("--"+val);
			}
			$tr.append($td);
			entityIndex++;
		});
		$tr.data("indexNo",entityObj.indexNo);
		$tr.data("level",entityObj.level);
		$tr.data("upIndexNo",entityObj.upIndexNo);
		$table.find("tbody").append($tr);
	}
};



//操作方法
/**
 * 新增一级
 * @param {Object} obj
 * @param {Object} url
 */
function addfirstLev(obj,url){
	var $obj = $(obj);
	var $table =  $obj.parents("li").find(".ls_list");
	var ctrlFalg = $table.find("tr[ctrl=insert]");
	var entityKey = $obj.parents("li").attr("data-name");
	if(ctrlFalg.length==0){
		$obj.parents(".li_btn").removeClass("colse");
		var thlength = $table.find("thead").find("th").length;
		var scenceType = $obj.parents("li").attr("data-name");
		$table = $obj.parents("li").find(".li_content").find(".ls_list");
		var $tr = $('<tr ctrl="insert"></tr>');
		$tr.data("url",url);
		$tr.data("level","1");
		$table.find("thead th").each(function(index){
			var name = $(this).attr("name");
			if(name == "ctrl_btn"){   
				var btn = '<td style="text-align: center;">';
				if(entityKey=="evalScenceDlRel"){
					btn+='<a class="abatch" onclick="addsecondDltarget(this,\''+entityData[scenceType].insertUrl+'\');return false;" href="javascript:void(0);">添加</a>';
				}else if(entityKey=="evalScenceDxRel"){
					btn+='<a class="abatch" onclick="addDxfirstLev(this,\''+entityData[scenceType].insertUrl+'\');return false;" href="javascript:void(0);">添加</a>';
				}else{
					btn+='<a class="abatch" onclick="addsecondtarget(this,\''+entityData[scenceType].insertUrl+'\');return false;" href="javascript:void(0);">添加</a>';
				}
				btn+='<a class="abatch" onclick="savefirst(this,\''+entityData[scenceType].updateUrl+'\');return false;" href="javascript:void(0);">保存</a>'+
					  '<a class="abatch" onclick="updatefirsttarget(this,\'\');return false;" href="javascript:void(0);">修改</a>'+
					  '<a class="abatch" onclick="delfirsttarget(this,\''+entityData[scenceType].deleteUrl+'\');return false;" href="javascript:void(0);">删除</a>'+
					  '</td>';
				$tr.append(btn);
			}/*else if(entityKey=="evalScenceDxRel"){
				$tr.append('<td><input type="text" name="'+name+'"/></td></td>');
				if(name!="indexName"){
					$tr.find("input[type='text']").val("0").attr("size",10);
				}
			}*/else{
				if(entityKey=="evalScenceAdjRel"){
					$tr.append('<td><input type="text" name="'+name+'"/></td>');
				}else{
					if(index==0){
						if(entityKey=="evalScenceDlRel"){
							$tr.append('<td colspan="'+(thlength-1)+'"><input type="text" name="indexName"/></td>');
						}else{
							$tr.append('<td colspan="'+(thlength-1)+'"><input type="text" name="indexName"/></td>');
						}
					 }
				}
			}
		});
		$table.find("tbody").prepend($tr);
		$obj.parents("li").find(".li_content").slideDown();
	}else{
		alert("已有条新增未保存",0);
	}
}
/**
 * 新增定性一级
 * @param obj
 * @param url
 */
function addDxfirstLev(obj,url){
	var $obj = $(obj);
	var $table =  $obj.parents("li").find(".ls_list");
	var ctrlFalg = $table.find("tr[ctrl=insert]");
	var entityKey = $obj.parents("li").attr("data-name");
	if(ctrlFalg.length==0){
		var scenceNo = $obj.parents(".settings").data("scenceNo");
		$obj.parents(".li_btn").removeClass("colse");
		var thlength = $table.find("thead").find("th").length;
		var scenceType = $obj.parents("li").attr("data-name");
		var upIndexNo = $obj.parents("tr").data("indexNo");
		$table = $obj.parents("li").find(".li_content").find(".ls_list");
		var $tr = $('<tr ctrl="insert"></tr>');
		$tr.data("url",url);
		$tr.data("level","2");
		$tr.data("upIndexNo",upIndexNo);
		$table.find("thead th").each(function(index){
			var name = $(this).attr("name");
			if(name == "ctrl_btn"){   
				var btn = '<td style="text-align: center;">';
				btn+='<a class="abatch" onclick="updatesecondtarget(this,\'\');return false;" href="javascript:void(0);">修改</a>'+
					'<a class="abatch" onclick="savefirst(this,\''+entityData[scenceType].updateUrl+'\');return false;" href="javascript:void(0);">保存</a>'+
					  '<a class="abatch" onclick="delsecondtarget(this,\''+entityData[scenceType].deleteUrl+'\');return false;" href="javascript:void(0);">删除</a>'+
					  '</td>';
				$tr.append(btn);
			}else if(name=="javaItem"){
				$td=$("<td colspan='2'></td>");
				var AppPropertyData = AppPropertyDatas.dxDatas;
				$td.append(getSelectTargHtml(AppPropertyData,name,"this"));
				$td.append(editAppProA);
				$td.append("<span></span>");
				$tr.append($td);
			}else{
				if(entityKey=="evalScenceAdjRel"){
					$tr.append('<td><input type="text" name="'+name+'"/></td>');
				}else{
					if(index==0){
						if(entityKey=="evalScenceDlRel"){
							$tr.append('<td colspan="'+(thlength-1)+'"><input type="text" name="indexName"/></td>');
						}else{
							$tr.append('<td colspan="'+(thlength-1)+'"><input type="text" name="'+name+'"/></td>');
						}
					 }
				}
			}
		});
		$obj.parents("tr").after($tr);
		$obj.parents("li").find(".li_content").slideDown();
	}else{
		alert("已有条新增未保存",0);
	}
}
function addsecondtarget(obj,url){
	var $obj = $(obj);
	var $table =  $obj.parents("li").find(".ls_list");
	var ctrlFalg = $table.find("tr[ctrl=insert]");
	if(ctrlFalg.length==0){
		var scenceNo = $obj.parents(".settings").data("scenceNo");
		var upIndexNo = $obj.parents("tr").data("indexNo");
		var scenceType = $obj.parents("li").attr("data-name");
		var level = 2;
		var thlength = $table.find("thead").find("th").length;
		$table = $obj.parents("li").find(".li_content").find(".ls_list");
		$tr = $('<tr ctrl="insert"></tr>');
		$tr.data("url",url);
		$tr.data("upIndexNo",upIndexNo);
		$tr.data("level",level);
		$table.find("thead th").each(function(index){
			var name = $(this).attr("name");
            var $td;
            var AppPropertyData;
			if(name == "ctrl_btn"){   
				$td = $('<td class="ctrl_btn"></td>');
					if(scenceType=="evalScenceDlRel"){
						$td.append('<a class="abatch" onclick="addthirdtarget(this,\''+entityData[scenceType].insertUrl+'\');return false;" href="javascript:void(0);">添加</a>');
					}
					$td.append('<a onclick="updatesecondtarget(this,\''+entityData[scenceType].insertUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="savesecond(this,\''+entityData[scenceType].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="delsecondtarget(this,\''+entityData[scenceType].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
					$tr.append($td);
			}else{
				$td = $('<td></td>');
				if(name=="evalLevel"){//约束级别
					$td.append(getSelectTargHtml(evalLevelParm,name));
					$td.append("<span name='"+name+"' style='display:none'></span>");
					//$td.find("select").val(val);
				}else if(name=="opSymbol1"||name=="opSymbol2"||name=="opSymbol3"){
					$td.append(getSelectTargHtml(opSymbolParm,name));
					$td.append("<span name='"+name+"' style='display:none'></span>");
					//$td.find("select").val("");
				}else if(name=="javaItem"){
					if(scenceType=="evalScenceRestrictRel"){
						AppPropertyData = AppPropertyDatas.dxDatas;
						$td.append(getSelectTargHtml(AppPropertyData,name,"this"));
						$td.append(editAppProA);
						$td.append("<span name='indexName' style='display:none'></span>");
					}else if(scenceType=="evalScenceDlRel"){
						//var dicType = "DL";
						AppPropertyData = AppPropertyDatas.finDatas;
						$td.append(getSelectTargHtml(AppPropertyData,name,true));
						$td.append("<span name='indexName' style='display:none'></span>");
					}else if(scenceType=="evalScenceAdjRel"){
						//var dicType = "ADJ";
						AppPropertyData = AppPropertyDatas.dxDatas;
						$td.append(getSelectTargHtml(AppPropertyData,name,true));
						$td.append(editAppProA);
						$td.append("<span name='indexName' style='display:none'></span>");
					}
				}else if(scenceType=="evalScenceFinRel"&&name=="indexName"){
					//var dicType = "FIN";
					AppPropertyData = AppPropertyDatas.finDatas;
					console.log(AppPropertyData);
					$table.find("tbody").find("tr").each(function(){
						var entityObj = $(this).data("entityObj");
						if(entityObj!==undefined&&entityObj.finCode!==undefined&&entityObj.finCode!=""){
							delete AppPropertyData[entityObj.finCode];
						}
					});
					$td.append(getSelectTargHtml(AppPropertyData,name,true));
					$td.append(editAppProA);
				}else{
					if(scenceType!="evalScenceAdjRel"){
						$td.append('<input type="text" name="'+name+'"/></td><span name="'+name+'" style="display:none"></span>');
						if(name=="stdCore"){
							$td.find("input[type='text']").val("0.00");
						}else if(name!="indexName"){
							$td.find("input[type='text']").val("0");
						}
					}
				}
				if(index!=0&&name != "ctrl_btn"){
					$td.find("input[type=text]").addClass("center");
					$td.find("input[type=text]").attr("size","10");
				}
				$tr.append($td);
			}
		});
		var $nextObj = $obj.parents("tr");
		//寻找二级节点位置
		/** 注掉原因：添加新规则时，输入框出现在最上面，而不是最下面
		$table.find("tbody").find("tr").each(function(index){
			if($(this).data("upIndexNo")==upIndexNo){
				$nextObj = $(this);
			}
		});
		**/
		/*for(var i = 0;i<$table.find("tbody").find("tr").length;i++){
			if($nextObj.next().data("upIndexNo")==upIndexNo){
				$nextObj = $nextObj.next();
			}
		}*/
		
		/**注掉原因：添加新规则时，输入框出现在最上面，而不是最下面
		var secondIndexNo = $nextObj.data("indexNo");
		for(var i = 0;i<$table.find("tbody").find("tr").length-$nextObj.index();i++){
			if($nextObj.next().data("upIndexNo")==secondIndexNo){
				$nextObj = $nextObj.next();
			}
		}
		**/
		$nextObj.after($tr);
		$obj.parents("li").find(".li_content").slideDown();
	}else{
		alert("已有条新增未保存",0);
	}
}

function addsecondDltarget(obj,url){
	var $obj = $(obj);
	var $table =  $obj.parents("li").find(".ls_list");
	var ctrlFalg = $table.find("tr[ctrl=insert]");
	if(ctrlFalg.length==0){
		var scenceNo = $obj.parents(".settings").data("scenceNo");
		var upIndexNo = $obj.parents("tr").data("indexNo");
		var scenceType = $obj.parents("li").attr("data-name");
		var level = 2;
		var thlength = $table.find("thead").find("th").length;
		$table = $obj.parents("li").find(".li_content").find(".ls_list");
		$tr = $('<tr ctrl="insert"></tr>');
		$tr.data("url",url);
		$tr.data("upIndexNo",upIndexNo);
		$tr.data("level",level);
		$table.find("thead th").each(function(index){
			var name = $(this).attr("name");
			var $td = $('<td></td>');
			if(name=="indexName"){
				$td.append('<input type="text" name="'+name+'"/>');
			}else if(name=="javaItem"){
				var dicType = "DL";
				var AppPropertyData = AppPropertyDatas.finDatas;
				$td.append(getSelectTargHtml(AppPropertyData,name,true));
				$td.append(editAppProA);
				$td.append('<span name="indexName" style="display:none"></span>');
				//$td.append('<input type="hidden" name="indexName"/>');
			}else if(name == "ctrl_btn"){
				$td.addClass("ctrl_btn");
				$td.append('<a class="abatch" onclick="addthirdDltarget(this,\''+entityData[scenceType].insertUrl+'\');return false;" href="javascript:void(0);">添加</a>');
				$td.append('<a onclick="updatesecondtarget(this,\''+entityData[scenceType].insertUrl+'\')" href="javascript:void(0)">修改</a>');
				$td.append('<a onclick="savesecond(this,\''+entityData[scenceType].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
				$td.append('<a onclick="delsecondtarget(this,\''+entityData[scenceType].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
				$tr.append($td);
			}
			$tr.append($td);
		});
		var $nextObj = $obj.parents("tr");
		//寻找二级节点位置
		/**注掉原因：添加新规则时，输入框出现在最上面，而不是最下面
		  $table.find("tbody").find("tr").each(function(index){
			if($(this).data("upIndexNo")==upIndexNo){
				$nextObj = $(this);
			}
		});
		var secondIndexNo = $nextObj.data("indexNo");
		for(var i = 0;i<=$table.find("tbody").find("tr").length-$nextObj.index();i++){
			if($nextObj.next().data("upIndexNo")==secondIndexNo){
				$nextObj = $nextObj.next();
			}
		}**/
		$nextObj.after($tr);
		$obj.parents("li").find(".li_content").slideDown();
	}else{
		alert("已有条新增未保存",0);
	}
}

function addthirdtarget(obj,url){
	var $obj = $(obj);
	var $table =  $obj.parents("li").find(".ls_list");
	var ctrlFalg = $table.find("tr[ctrl=insert]");
	if(ctrlFalg.length==0){
		var upIndexNo = $obj.parents("tr").data("indexNo");
		var scenceType = $obj.parents("li").attr("data-name");
		var level = 3;
		var thlength = $table.find("thead").find("th").length;
		//var scenceType = $obj.parents("li").attr("data-name");
		$table = $obj.parents("li").find(".li_content").find(".ls_list");
		$tr = $('<tr ctrl="insert"></tr>');
		$tr.data("url",url);
		$tr.data("upIndexNo",upIndexNo);
		$tr.data("level",level);
		$table.find("thead th").each(function(index){
			var name = $(this).attr("name");
            var $td;
			if(name == "ctrl_btn"){   
				$td = $('<td class="ctrl_btn"></td>');
					$td.append('<a onclick="updatesecondtarget(this,\''+entityData[scenceType].insertUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="savesecond(this,\''+entityData[scenceType].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="delsecondtarget(this,\''+entityData[scenceType].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
					$tr.append($td);
			}else{
				if(scenceType=="evalScenceDlRel"){
					$td = $('<td></td>');
					if(name=="javaItem"){
						$td.append('业务参数');
					}else if(name=="opSymbol1"||name=="opSymbol2"){
						$td.append(getSelectTargHtml(opSymbolParm,name));
					}else{
						$td.append('<input type="text" name="'+name+'"/>');
						if(name=="stdCore"){
							$td.find("input[type='text']").val("0.00");
						}else if(name!="indexName"){
							$td.find("input[type='text']").val("0");
						}
					}
					if(index!=0&&name != "ctrl_btn"){
						$td.find("input[type=text]").addClass("center");
						$td.find("input[type=text]").attr("size","10");
					}
					$tr.append($td);
				}else{
					$td = $('<td><input type="text" name="'+name+'"/></td>');
					if(name=="stdCore"){
						$td.find("input[type='text']").val("0.00");
					}else if(name!="indexName"){
						$td.find("input[type='text']").val("0");
					}
					if(index!=0&&name != "ctrl_btn"){
						$td.find("input[type=text]").addClass("center");
						$td.find("input[type=text]").attr("size","10");
					}
					$tr.append($td);
				}
			}
		});
		var $nextObj = $obj.parents("tr");
		/**注掉原因：添加新规则时，输入框出现在最上面，而不是最下面
		  for(var i = 0;i<$table.find("tbody").find("tr").length;i++){
			if($nextObj.next().data("upIndexNo")==upIndexNo){
				$nextObj = $nextObj.next();
			}
		}
		**/
		$nextObj.after($tr);
	}else{
		alert("已有条新增未保存",0);
	}
}

function addthirdDltarget(obj,url){
	var $obj = $(obj);
	var $table =  $obj.parents("li").find(".ls_list");
	var ctrlFalg = $table.find("tr[ctrl=insert]");
	if(ctrlFalg.length==0){
		var upIndexNo = $obj.parents("tr").data("indexNo");
		var scenceNo = $obj.parents(".settings").data("scenceNo");
		var scenceType = $obj.parents("li").attr("data-name");
		var level = 3;
		var thlength = $table.find("thead").find("th").length;
		//var scenceType = $obj.parents("li").attr("data-name");
		$table = $obj.parents("li").find(".li_content").find(".ls_list");
		$tr = $('<tr ctrl="insert"></tr>');
		$tr.data("url",url);
		$tr.data("upIndexNo",upIndexNo);
		$tr.data("level",level);
		$table.find("thead th").each(function(index){
			var name = $(this).attr("name");
            var $td;
			if(name == "ctrl_btn"){   
					$td = $('<td class="ctrl_btn"></td>');
					$td.append('<a onclick="updatesecondtarget(this,\''+entityData[scenceType].insertUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="savesecond(this,\''+entityData[scenceType].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="delsecondtarget(this,\''+entityData[scenceType].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
					$tr.append($td);
			}else{
					$td = $('<td></td>');
				if(name=="opSymbol1"||name=="opSymbol2"){
					$td.append(getSelectTargHtml(opSymbolParm,name));
					$td.append("<span name='"+name+"' style='display:none'></span>");
					$table.find("tbody tr").each(function(){
						if($(this).data("indexNo")==upIndexNo){
							$targetTr = $(this);
							var $selectObj = $targetTr.find("select[name='javaItem']");
							if($selectObj){
								var $thisOp = $selectObj.find("option:selected");
								if($thisOp.data("data")!==undefined){
									var data = $thisOp.data("data");
									var type = data.type,keyName = data.keyName;
									if(type=="02"&&keyName!=null&&keyName!==undefined&&keyName!=""){//数据字典相
										$td.find("select[name=opSymbol1]").attr("disabled","disabled").css({"display":"none"});;
										$td.find("select[name=opSymbol2]").attr("disabled","disabled");
									}
								}
							}
						}
					});
					
				}else if(name=="javaItem"){
					var dicType = "DL";
					var val = $obj.parents("tr").find("select[name='javaItem']").val();
					$td.append(getSelectTargHtml2(AppPropertyDatas.finDatas,name,true,val));
					var valHtml = $obj.parents("tr").find("select[name='javaItem']").find("option[value="+val+"]").html();
					$td.append("<span name='indexName' style='display:none'>"+valHtml+"</span>");
					$obj.parents("tr").find("select[name='javaItem']").hide();
				}else if(name=="ctrl_btn"){
					$td.append('<a onclick="updatethirdtarget(this,\''+datas[entityKey].insertUrl+'\')" href="javascript:void(0)">修改</a>');
					$td.append('<a onclick="savethird(this,\''+datas[entityKey].updateUrl+'\')" href="javascript:void(0)">保存</a> ');
					$td.append('<a onclick="delthirdtarget(this,\''+datas[entityKey].deleteUrl+'\')" href="javascript:void(0)">删除</a>');
				}else if(name=="opVal1"||name=="opVal2"){
					var $select;
					$table.find("tbody tr").each(function(){
						if($(this).data("indexNo")==upIndexNo){
							$targetTr = $(this);
							var $selectObj = $targetTr.find("select[name='javaItem']");
							if($selectObj){
								var $thisOp = $selectObj.find("option:selected");
								if($thisOp.data("data")!==undefined){
									var data = $thisOp.data("data");
									var type = data.type,keyName = data.keyName;
									if(type=="02"&&keyName!=null&&keyName!==undefined&&keyName!=""){//数据字典相
										var parmDic = getListForKeyName(keyName);
										if(parmDic!==undefined&&parmDic!=null&&parmDic!=""){
											$select = $('<select><select>');
											$.each(parmDic,function(key,obj){
												var val = obj.val;
												var $option = $('<option></option>');
												$option.attr("value",key);
												$option.text(val);
												$option.appendTo($select);
											});
											$select.css({"width":"78px","text-align":"center"});
										}
									}
								}
							}
						}
					});
					if($select){
						if(name=="opVal1"){
							$select.attr("disabled","disabled");
							$select.css({"display":"none"});
						}
						if(name=="opVal2"){
							$select.attr("onchange","selectOpValOnchange(this);");
						}
						$td.append($select);
						$td.append("<span name='"+name+"' style='display:none'></span>");
						$select.attr("name",name);
					}else{
						$td = $('<td><input name="'+name+'" class="center" type="text"/><span name="'+name+'" style="display:none"></span></td>');
						$td.find("input[type='text']").attr("size","10");
					}
				}else{
					$td.append('<input type="text" name="'+name+'"/><span name="'+name+'" style="display:none"></span></td>');
					if(name=="stdCore"){
						$td.find("input[type='text']").val("0.00");
					}else if(name!="indexName"){
						$td.find("input[type='text']").val("0");
					}
				}
				if(index!=0&&name != "ctrl_btn"){
					$td.find("input[type=text]").addClass("center");
					$td.find("input[type=text]").attr("size","10");
				}
				$tr.append($td);
			}
		});
		var $nextObj = $obj.parents("tr");
	/** 注掉原因：添加新规则时，输入框出现在最上面，而不是最下面
	 	for(var i = 0;i<$table.find("tbody").find("tr").length;i++){
			if($nextObj.next().data("upIndexNo")==upIndexNo){
				$nextObj = $nextObj.next();
			}
		}
		**/
		$nextObj.after($tr);
	}else{
		alert("已有条新增未保存",0);
	}
}

function selectOpValOnchange(obj){
	var optVal = $(obj).val();
	$(obj).parent().parent().find("select[name=opVal1] option").each(function(index){
		if($(this).is(':selected')){
			$(this).removeAttr("selected");
		};
	});
	$(obj).parent().parent().find("select[name=opVal1]").find("option[value='"+optVal+"']").attr("selected",true);
	
}
function savefirst(obj,url){
	var $obj = $(obj);
	var $ThisTr = $obj.parents("tr");
	var saveFlag = $ThisTr.attr("ctrl");
	var scenceType = $obj.parents("li").attr("data-name");
	if(saveFlag != undefined && saveFlag != ""){
		if(saveFlag=="insert"){
			url = $ThisTr.data("url");
		}
		var scenceNo = $obj.parents(".settings").data("scenceNo");
		var indexNo = $obj.parents("tr").data("indexNo");
		var level = $obj.parents("tr").data("level");
		var gradeCardId = $obj.parents("li").attr("id");
		var dataParm = {scenceNo:scenceNo,level:level,indexNo:indexNo,gradeCardId:gradeCardId};
		var postFlag = true;
		$ThisTr.find("input[type=text]").each(function(){
			var name = $(this).attr("name");
			dataParm[name] = $(this).val();
			if(name =="indexName"&&($(this).val()==null||$(this).val()=="")){
				postFlag = false;
			}
		});
		$ThisTr.find("select").each(function(){
			var name = $(this).attr("name");
			dataParm[name] = $(this).val();
		});
		if(scenceType=="evalScenceDxRel"){
			$ThisTr.find("select").each(function(){
				var value = $(this).val();
				var valueHtml =$(this).find("option[value="+value+"]").html();
				dataParm["indexName"] = valueHtml;
				dataParm["indexDesc"] =$(this).val();
				$ThisTr.find("span").html(valueHtml);
			});
			dataParm["upIndexNo"]=$obj.parents("tr").data("upIndexNo");
		}
		if(postFlag){
			//var indexNo;
			$.ajax({
				type:"post",
				data:{ajaxData:JSON.stringify(dataParm)},
				url:getUrlSplit(url),
				async:false,
				success:function(data){
					if(data.flag=="success"){
						$ThisTr.data("entityData",data.entityData);
						$ThisTr.data("indexNo",data.entityData.indexNo);
						$ThisTr.data("level",data.entityData.level);
						$ThisTr.data("upIndexNo",data.entityData.upIndexNo);
						if(data.entityData.level=="1"){
							$ThisTr.find("input[name='indexName']").parent("td").addClass("font_weight");
						}
						$ThisTr.find("input[name='indexName']").parent("td").html(data.entityData.indexName);
						$ThisTr.removeAttr("ctrl");
						if(scenceType=="evalScenceDxRel"){
							$ThisTr.find("select").hide();
							$ThisTr.find("td").eq(0).find("a").hide();
							$ThisTr.find("span").show();
							$ThisTr.find("input[type=button]").hide();
							if(data.dxRelList == null){
								
							}else{
								$.each(data.dxRelList,function(index,obj){
									var $tr = $("<tr></tr>");
									$tr.data("entityData",obj);
									$tr.data("indexNo",obj.indexNo);
									$tr.data("level",obj.level);
									$tr.data("upIndexNo",obj.upIndexNo);
									$tr.append('<td name="'+obj.indexNo+'">----'+obj.indexName+'</td>');
									$tr.append('<td><input name="stdCore" class="center" type="text" size="10" value="'+obj.stdCore+'"><span name="stdCore" style="display: none;">'+obj.stdCore+'</span></td>');
									var $td = $('<td class="ctrl_btn"></td>');
									$td.append('<a onclick="updatesecondtarget(this,\'/evalScenceDxRel/insertAjax;indexNo-indexNo;scenceNo-scenceNo\')" href="javascript:void(0)">修改</a>');
									$td.append('<a onclick="savesecond(this,\'/evalScenceDxRel/updateAjax;indexNo-indexNo;scenceNo-scenceNo\')" href="javascript:void(0)">保存</a> ');
									$td.append('<a onclick="delsecondtarget(this,\'/evalScenceDxRel/deleteAjax;indexNo-indexNo;scenceNo-scenceNo\')" href="javascript:void(0)">删除</a>');
									$tr.append($td);
									$ThisTr.after($tr);
								});
							}
						}
						alert(data.msg,1);
						console.log($ThisTr.data("entityData"));
					}else if(data.flag=="error"){
						alert(data.msg,0);
					}
				},error:function(){
					alert(  top.getMessage("FAILED_SAVE"),0);
				}
			});
		}else{
			alert(top.getMessage("NOT_FORM_EMPTY", "指标名称"),0);
		}
	}else{
		alert("保存失败,新增或保存不明确",0);
	}
}
function updatefirsttarget(obj,url){
	var $obj = $(obj);
	var saveFlag = $obj.parents("tr").attr("ctrl");
	var scenceType = $obj.parents("li").attr("data-name");
	if(saveFlag!==undefined&&saveFlag!=""){
		if(saveFlag=="insert"){
			alert("为新增状态还未保存",0);
		}else if(saveFlag=="update"){
			alert("已为修改状态",0);
		}
	}else{
		if(scenceType=="evalScenceDxRel"){
			$obj.parents("tr").find("span").hide();
			$obj.parents("tr").find("td").eq(0).find("select,a,input").show();
		}else{
			var $td = $obj.parents("tr").find("td").eq(0);
			var name = $obj.parents("table").find("thead tr th").eq(0).attr("name");
			var text = $td.text();
			$td.data("value",text);
			var val = text.replace("--","");
			$td.html('<input type="text" name="'+name+'" value="'+val+'"/><input type="button" onclick="cancelOperation(this);" value="取消"/>');
			$obj.parents("tr").attr("ctrl","update");
			$obj.parents("tr").data("url",url);
		}
	}
	
}
function cancelOperation(obj){
	var $obj = $(obj);
	$obj.parents("tr").removeAttr("ctrl");
	$obj.parents("tr").data("url","");
	var $td = $obj.parents("tr").find("td").eq(0);
	$.each($obj.parents("tr").find("td"),function(index,obj){
		$(obj).find("input,select").hide();
		$(obj).find("span").show();
	});
	$td.find("a").hide();
	var scenceType = $obj.parents("li").attr("data-name");
	var level = $obj.parents("tr").data("level");
	if(scenceType=="evalScenceDlRel"||scenceType=="evalScenceRestrictRel"||scenceType=="evalScenceDxRel"||scenceType=="evalScenceAdjRel"){
		if(level=="1"){
			$td.html($td.data("value"));
		}
	}else{
		$td.html($td.data("value"));
	}
}
function delfirsttarget(obj,url){
	var $obj = $(obj);
	alert("是否确定删除",2,function(){
		var $TrObj = $obj.parents("tr");
		var indexNo = $TrObj.data("indexNo");
		if(indexNo!=undefined){
			var scenceNo = $obj.parents(".settings").data("scenceNo");
			url = gerUrlForData(url,{"indexNo":indexNo,"scenceNo":scenceNo});
			$.ajax({
				type:"get",
				url:url,
				async:false,
				success:function(data){
					if(data.flag == "success"){
						alert(data.msg,1);
						$obj.parents("tr").remove();
					}else if(data.flag == "error"){
						alert(data.msg,0);
					}
				},error:function(){
					alert(top.getMessage("FAILED_DELETE"),0);
				}
			});
		}else{
			alert(top.getMessage("SUCCEED_DELETE"),1);
			$obj.parents("tr").remove();
		}
	});
}
function updatesecondtarget(obj,url){
	var $obj = $(obj);
	var level = $obj.parents("tr").data("level");
	var saveFlag = $obj.parents("tr").attr("ctrl");
	if(saveFlag!==undefined&&saveFlag!=""){
		if(saveFlag=="insert"){
			alert("为新增状态还未保存",0);
		}else if(saveFlag=="update"){
			alert("已为修改状态",0);
		}
	}else{
		var $td = $obj.parents("tr").find("td").eq(0);
		var name = $obj.parents("table").find("thead tr th").eq(0).attr("name");
		var scenceType = $obj.parents("li").attr("data-name");
		var scenceNo = $obj.parents(".settings").data("scenceNo");
		var text = $td.text();
		$td.data("value",text);
		var val;
        var $thisTrObj;
		if(scenceType=="evalScenceFinRel"){
			$thisTrObj = $obj.parents("tr");
			$.each($thisTrObj.find("td"),function(index,obj){
				$(obj).find("span").hide();
				$(obj).find("input").show();
			});
			var thisFincode = $thisTrObj.data("entityObj").finCode;
			var dicType = "FIN";
			var AppPropertyData = AppPropertyDatas.finDatas;
			$obj.parents("table tbody").find("tr").each(function(){
				var entityObj = $(this).data("entityObj");
				if(entityObj.finCode!==undefined&&entityObj.finCode!=""){
					if(thisFincode!==entityObj.finCode){
						delete AppPropertyData[entityObj.finCode];
					}
				}
			});
			$td.html(getSelectTargHtml(AppPropertyData,name,true,thisFincode));
			$td.append(editAppProA);
			$td.append('<input type="button" onclick="cancelOperation(this);" value="取消"/>');
		}else{
			if(level==2){
				val= text.replace("--","");
			}else if(level==3){
				val= text.replace("----","");
			}
			if(scenceType=="evalScenceDlRel"||scenceType=="evalScenceDxRel"||scenceType=="evalScenceRestrictRel"||scenceType=="evalScenceAdjRel"){
				$thisTrObj = $obj.parents("tr");
				$.each($thisTrObj.find("td"),function(index,obj){
					$(obj).find("span").hide();
					$(obj).find("input,select,a").show();
				});
				$td.find("input[type=button]").remove();
				if(level!=3){
					$td.append('<input type="button" onclick="cancelOperation(this);" value="取消"/>');
				}
				if(scenceType=="evalScenceDxRel"&&level=="2"){
					//$td.find("input[type=button]").remove();
				}
			}else{
				$td.html('<input type="text" name="'+name+'" value="'+val+'"/><input type="button" onclick="cancelOperation(this);" value="取消"/>');
			}
		}
		$obj.parents("tr").attr("ctrl","update");
		$obj.parents("tr").data("url",url);
	}
}
function updatethirdtarget(obj,url){
	updatesecondtarget(obj,url);
}
function savethird(obj,url){
	savesecond(obj,url);
}
function delthirdtarget(obj,url){
	delsecondtarget(obj,url);
}
function savesecond(obj,url){
	var $obj = $(obj);
	var $tr = $obj.parents("tr");
	var saveFlag = $tr.attr("ctrl");
	if(saveFlag=="insert"){
		url = $tr.data("url");
	}
	var scenceNo = $obj.parents(".settings").data("scenceNo");
	var upIndexNo = $obj.parents("tr").data("upIndexNo");
	var indexNo = $obj.parents("tr").data("indexNo");
	var level = $obj.parents("tr").data("level");
	var gradeCardId = $obj.parents("li").attr("id");
	var dataParm = {scenceNo:scenceNo,level:level,indexNo:indexNo,upIndexNo:upIndexNo,gradeCardId:gradeCardId};
	var postFlag = true;
	$tr.find("input[type=text]").each(function(){
		var name = $(this).attr("name");
		dataParm[name] = $(this).val();
		$tr.find("span[name="+name+"]").html($(this).val());
		if(name =="indexName"&&($(this).val()==null||$(this).val()=="")){
			postFlag = false;
		}
	});
	$tr.find("select").each(function(){
		var name = $(this).attr("name");
		if(name =="indexName"){
			dataParm[name] = $(this).find("option:selected").text();;
		}else{
			dataParm[name] = $(this).val();
		}
	});
	var scenceType = $obj.parents("li").attr("data-name");
	if(scenceType=="evalScenceFinRel"){
		dataParm["finCode"] = $obj.parents("tr").data("finCode");
		if($tr.find("select[name=indexName]").length>0){
			dataParm["finCode"] = $tr.find("select[name=indexName]").find("option:selected").val();
		}
	}
	if(scenceType=="evalScenceDlRel"||scenceType=="evalScenceDxRel"){
		//如果是第三层，不更新指标名称indexName
		if(level!="3"){
			dataParm["indexName"] = $tr.find("span").html();
		}
	}
	if(scenceType=="evalScenceAdjRel"){
		$tr.find("select").each(function(){
				var value = $(this).val();
				var valueHtml =$(this).find("option[value="+value+"]").html();
				dataParm["indexName"] = valueHtml;
				dataParm["indexDesc"] =$(this).val();
				$tr.find("span").html(valueHtml);
		});
	}
	if(postFlag){
		//var indexNo;
		$.ajax({
			type:"post",
			data:{ajaxData:JSON.stringify(dataParm)},
			url:getUrlSplit(url),
			async:false,
			success:function(data){
				if(data.flag=="success"){
					$tr.data("indexNo",data.entityData.indexNo);
					$tr.data("level",data.entityData.level);
					$tr.data("upIndexNo",data.entityData.upIndexNo);
					$tr.data("entityObj",data.entityData);
					$tr.find("input[type=text]").each(function(){
						var name = $(this).attr("name");
						if(name=="stdCore"){
							$(this).val(changeDoubleForStr(data.entityData[name]));
						}else{
							$(this).val(data.entityData[name]);
						}
					});
					$tr.find("span").each(function(){
						var name = $(this).attr("name");
						if(scenceType=="evalScenceFinRel"){
							$(this).html(changeDoubleForStr(data.entityData[name]));
						}else{
							if(name=="stdCore"||name=="opVal1"||name=="opVal2"){
								$(this).html(changeDoubleForStr(data.entityData[name]));
							}else if(name=="opSymbol1"||name=="opSymbol2"||name=="opSymbol3"||name=="evalLevel"){
								var valueHtml =$tr.find("select[name="+name+"]").find("option[value="+data.entityData[name]+"]").html();
								$(this).html(valueHtml);
							}
						}
					});
					/*if(scenceType!="evalScenceDlRel"){
					}
					if(data.entityData.level==3){
						$tr.find("input[name='indexName']").parent("td").html("----"+data.entityData.indexName);
					}else{
					}*/
					$tr.find("input[name='indexName']").parent("td").html("--"+data.entityData.indexName);
					$tr.find("select[name='indexName']").parent("td").html("--"+data.entityData.indexName);
					$tr.each(function(index,obj){
						$(obj).find("span").show();
						$(obj).find("input").hide();
						$(obj).find("select").hide();
						if(scenceType=="evalScenceAdjRel"){
							$.each(data.adjRelList,function(index,obj){
								var $ThisTr = $("<tr></tr>");
								$ThisTr.data("entityData",obj);
								$ThisTr.data("indexNo",obj.indexNo);
								$ThisTr.data("level",obj.level);
								$ThisTr.data("upIndexNo",obj.upIndexNo);
								$ThisTr.append('<td name="'+obj.indexNo+'">----'+obj.indexName+'</td>');
								$ThisTr.append('<td><input name="stdCore" class="center" type="text" size="10" value="'+obj.stdCore+'"><span name="stdCore" style="display: none;">'+obj.stdCore+'</span></td>');
								var $td = $('<td class="ctrl_btn"></td>');
								$td.append('<a onclick="updatethirdtarget(this,\'/evalScenceAdjRel/insertAjax;indexNo-indexNo;scenceNo-scenceNo\')" href="javascript:void(0)">修改</a>');
								$td.append('<a onclick="savethird(this,\'/evalScenceAdjRel/updateAjax;indexNo-indexNo;scenceNo-scenceNo\')" href="javascript:void(0)">保存</a> ');
								$td.append('<a onclick="delthirdtarget(this,\'/evalScenceAdjRel/deleteAjax;indexNo-indexNo;scenceNo-scenceNo\')" href="javascript:void(0)">删除</a>');
								$ThisTr.append($td);
								$tr.after($ThisTr);
							});
						}
					});
					$tr.find("td").eq(0).find("a").hide();
					$tr.removeAttr("ctrl");
					alert(top.getMessage("SUCCEED_SAVE"),1);
				}else if(data.flag=="error"){
					alert(data.msg,0);
				}
			},error:function(){
				//$.myAlert.Alert(  top.getMessage("FAILED_SAVE"));
				alert(  top.getMessage("FAILED_SAVE"),0);
			}
		});
	}else{
		//$.myAlert.Alert(top.getMessage("NOT_FORM_EMPTY", "指标名称"));
		alert(top.getMessage("NOT_FORM_EMPTY", "指标名称"),0);
	}
}
function delsecondtarget(obj,url){
	var $obj = $(obj);
	var $TrObj = $obj.parents("tr");
	var indexNo = $TrObj.data("indexNo");
	var scenceNo = $obj.parents(".settings").data("scenceNo");
	url = gerUrlForData(url,{"indexNo":indexNo,"scenceNo":scenceNo});
	var $ThisTr = $obj.parents("tr");
	var saveFlag = $ThisTr.attr("ctrl");
	alert("是否确定删除",2,function(){
		if(saveFlag!==undefined&&saveFlag=="insert"){
			$obj.parents("tr").remove();
			alert(top.getMessage("SUCCEED_DELETE"),1);
		}else{
			$.ajax({
				type:"get",
				url:url,
				async:false,
				success:function(data){
					if(data.flag == "success"){
						$.each($obj.parents("tr").nextAll(),function(i,obj){
							if(indexNo==$(obj).data("upIndexNo")){
								$(obj).remove();
							}
						})
						$obj.parents("tr").remove();
						alert(data.msg,1);
					}else if(data.flag == "error"){
						alert("data.msg",0);
					}
				},error:function(){
					alert(top.getMessage("FAILED_DELETE"),0);
				}
			});
		}
	});
}
/**
 * 保存当前行数据
 * @param {Object} obj
 * @param {Object} url
 */
function saveTr(obj,url){
	var $obj = $(obj);
	var $ThisTr = $obj.parents("tr");
	var scenceNo = $obj.parents(".settings").data("scenceNo");
	var configNo = $obj.parents("tr").data("entityObj").configNo;
	var dataParm = {evalScenceNo:scenceNo,configNo:configNo};
	$ThisTr.find("input[type=text]").each(function(){
		var name = $(this).attr("name");
		dataParm[name] = $(this).val();
	});
	$ThisTr.find("select").each(function(){
		var name = $(this).attr("name");
		dataParm[name] = $(this).val();
	});
	$.ajax({
		type:"post",
		data:{ajaxData:JSON.stringify(dataParm)},
		url:getUrlSplit(url),
		async:false,
		success:function(data){
			if(data.flag=="success"){
				alert(data.msg,1);
			}else if(data.flag=="error"){
				alert(data.msg,0);
			}
		},error:function(){
			//$.myAlert.Alert(  top.getMessage("FAILED_SAVE"));
			alert(  top.getMessage("FAILED_SAVE"),0);
		}
	});
}
/**
 * 拆分获取url(不带参数)
 * @param {Object} url
 */
function getUrlSplit(url){
	return url.split(";")[0];
}
/**
 * 拆分获取url(带参数)
 * @param {Object} url
 * @param {Object} data
 */
function gerUrlForData(url,data){
	var array = url.split(";");
	url = array[0];
	$.each(array, function(index,parms) {
		if(index>0){
			var parm = parms.split("-")[0];
			var parmVal = parms.split("-")[1];
			if(url.indexOf("?")==-1){
				url+="?";
			}else{ 
				url+="&";
			}
			url+=parm+"="+data[parmVal];
		}
	});
	return url;
}
/**
 * 初始化评级类型
 * @param {Object} obj
 * @param {Object} array
 */
function initTypes(obj,array){
	var $obj = $(obj);
	var $types = $obj.parents(".content").find(".content_type").find(".types");
	var $table = $('<table class="ls_list"><tbody></tbody></table>');
	var index = 0;
	$.each(array, function(key,val) {
		var $tr = $('<tr></tr>');
		if(index==0){
			$tr.addClass("ckecked");
		}
		$tr.append('<td onclick="showType(this,\''+key+'\')">'+val+'</td>"');
		$table.find("tbody").append($tr);
		index++;
	});	
	$types.html($table);
}
/**
 * 显示评级类型配置
 * @param {Object} obj
 * @param {Object} type
 */
function showType(obj,type){
	var $obj = $(obj);
	var $settings = $obj.parents(".content").find(".content_setting .settings");
	$settings.find("ul.data-content li").each(function(index){
		var $this = $(this);
		var data_name = $this.attr("data-name");
		if(data_name==type){
			$this.slideDown();
			$obj.parents("tbody").find(".ckecked").removeClass("ckecked");
			$obj.parents("tr").addClass("ckecked");
		}else{
			$this.hide();
		}
	});
}

function addAdjRel(obj,url){
	var $obj = $(obj);
	$obj.parents(".li_btn").removeClass("colse");
	//var $obj = $(obj);
	var $table =  $obj.parents("li").find(".ls_list");
	var ctrlFalg = $table.find("tr[ctrl=insert]");
	if(ctrlFalg.length==0){
		$obj.parents(".li_btn").removeClass("colse");
		var thlength = $table.find("thead").find("th").length;
		var scenceType = $obj.parents("li").attr("data-name");
		$table = $obj.parents("li").find(".li_content").find(".ls_list");
		var $tr = $('<tr ctrl="insert"></tr>');
		$tr.data("url",url);
		$tr.data("level","1");
		$table.find("thead th").each(function(index){
			var name = $(this).attr("name");
			if(name == "ctrl_btn"){   
				var btn = '<td style="text-align: center;">'+
						  '<a class="abatch" onclick="addsecondtarget(this,\''+entityData[scenceType].insertUrl+'\');return false;" href="javascript:void(0);">添加</a>'+
						  '<a class="abatch" onclick="savefirst(this,\''+entityData[scenceType].updateUrl+'\');return false;" href="javascript:void(0);">保存</a>'+
						  '<a class="abatch" onclick="updatefirsttarget(this,\'\');return false;" href="javascript:void(0);">修改</a>'+
						  '<a class="abatch" onclick="delfirsttarget(this,\''+entityData[scenceType].deleteUrl+'\');return false;" href="javascript:void(0);">删除</a>'+
						  '</td>';
				$tr.append(btn);
			}else if(name=="javaItem"){
				$tr.append('<td><input type="text" name="indexName"/><span name="indexName" style="display:none"><span></td>');
			}else{
				$tr.append('<td></td>');
			}
		});
		if($table.find("tbody").find("tr").length==0){
			$table.find("tbody").append($tr);
		}else{
			$table.find("tbody").find("tr").eq(0).before($tr);
		}
		$obj.parents("li").find(".li_content").slideDown();
	}else{
		alert("已有条新增未保存",0);
	}
	$obj.parents("li").find(".li_content").slideDown();
}

function slideUpLi(obj){
	var $obj = $(obj);
	$obj.parents(".li_btn").addClass("colse");
	$obj.parents("li").find(".li_content").slideUp();
}
/**
 * 转成0.00形式
 * @param {Object} stdCore
 */
function changeDoubleForStr(stdCore){
	stdCore+="";
	if(stdCore!==undefined&&stdCore!=null&&stdCore!=""){
		if(stdCore.indexOf(".")!=-1){
			if(stdCore.split(".")[1]==1){
				stdCore+="0";
			}
		}else{
			stdCore+=".00";
		}
	}
	return stdCore;
}
/**
 * 根据数据创建select 标签
 * @param {Object} data
 * @param {Object} name
 * @param {Object} name
 */
function getSelectTargHtml(data,name,flag,value){
	var $select  = $('<select name="'+name+'"></select>');
	if(flag!==undefined&&flag==true){
		$select.attr("onchange","selectOnchange(this);");
		$select.append('<option></option>');
	}else if(flag=="this"){
		$select.attr("onchange","selectOnchangeThis(this);");
		$select.append('<option></option>');
	}
	$.each(data, function(val,cdata) {
		var text = cdata.val;
		var $option = $('<option value="'+val+'">'+text+'</option>');
		if(cdata["data"]!==undefined){
			//向option存放隐藏数据
			$option.data("data",cdata["data"]);
		}
		if(value!==undefined&&value!=""&&val==value){
			$option.attr("selected",true);
		}
		$select.append($option);
	});
	
	/*if(name=="evalLevel"){
		$select.parent().mouseenter(function(){
			$select.next().show();
		});
		$select.parent().mouseleave(function(){
			$select.next().hide();
		});
	}*/
	return $select;
}
/**
 * 根据数据创建select 标签只有选中的option
 * @param {Object} data
 * @param {Object} name
 * @param {Object} name
 */
function getSelectTargHtml2(data,name,flag,value){
	var $select  = $('<select name="'+name+'"></select>');
	$.each(data, function(val,cdata) {
		var text = cdata.val;
		var $option = $('<option value="'+val+'">'+text+'</option>');
		if(value!==undefined&&value!=""&&val==value){
			$option.attr("selected",true);
			$select.append($option);
			return false;
		}
	});
	return $select;
}
//时时获取未存在的业务参数
function initChangeOption(){
	var $this = $(".table_content .data-content .data-content");
    var $obj;
	if($this.find("li[data-name=evalScenceDlRel]").length>0){
		$obj = $this.find("li[data-name=evalScenceDlRel]");
		$obj.parents("table").find("tbody tr").each(function(){
			var $thisTr = $(this);
			var javaItem = $thisTr.data("entityObj").javaItem;
			var dicType = "DL";
			var data = AppPropertyDatas.finDatas;
			$obj.parents("table").find("tbody tr").each(function(){
				var entityObj = $thisTr.data("entityObj");
				if(entityObj!=undefined&&entityObj.javaItem!=javaItem){
					delete data[entityObj.javaItem];
				}
			});
			var $select = getSelectTargHtml(data,"temp",false,javaItem);
			$thisTr.find("select[name=javaItem]").html($select.html());
		});
	}else if($this.find("li[data-name=evalScenceRestrictRel]").length>0){
		$obj = $this.find("li[data-name=evalScenceRestrictRel]");
		$obj.parents("table").find("tbody tr").each(function(){
			var $thisTr = $(this);
			var javaItem = $thisTr.data("entityObj").javaItem;
			var dicType = "DL";
			var data = AppPropertyDatas.finDatas;
			$obj.parents("table").find("tbody tr").each(function(){
				var entityObj = $thisTr.data("entityObj");
				if(entityObj!=undefined&&entityObj.javaItem!=javaItem){
					delete data[entityObj.javaItem];
				}
			});
			var $select = getSelectTargHtml(data,"temp",false,javaItem);
			$thisTr.find("select[name=javaItem]").html($select.html());
		});
	}
}
/**
 * 根据keyName 获取数据字典项 值 和名称对象
 * @param {Object} keyName
 */
function getListForKeyName(keyName){
	var datas = {};
	$.ajax({
		type:"post",
		data:{ajaxData:JSON.stringify({"keyName":keyName})},
		url:webPath+"/parmDic/getListForKeyName",
		async:false,
		success:function(data){
			if(data.flag == "success"){
				$.each(data.parmListData, function(index,entity) {
					datas[entity.optCode] = {"val":entity.optName};
				});
			}else if(data.flag == "error"){
				//$.myAlert.Alert(data.msg);
				alert(data.msg,0);
			}
		},error:function(){
			
		}
	});
	return datas;
}

function selectOnchange(obj){
	var $obj = $(obj);
	var scenceType = $obj.parents("li").attr("data-name");
	var selectName = $obj.attr("name");
	var $select;
	var $thisSelect;
	var $thisOp =$obj.find("option:selected");
	$thisSelect = $("<select><select>");
	$thisSelect.attr("name",selectName);
	$thisSelect.append('<option value="'+$thisOp.attr("value")+'">'+$thisOp.text()+'</option>');
	if($thisOp.data("data")!==undefined){
		var data = $thisOp.data("data");
		var type = data.type,keyName = data.keyName;
		if(type=="02"&&keyName!=null&&keyName!==undefined&&keyName!=""){//数据字典相
			var parmDic = getListForKeyName(keyName);
			if(parmDic!==undefined&&parmDic!=null&&parmDic!=""){
				$select = $('<select><select>');
				$.each(parmDic,function(key,obj){
					var val = obj.val;
					var $option = $('<option></option>');
					$option.attr("value",key);
					$option.text(val);
					$option.appendTo($select);
					
				});
				$select.css({"width":"78px","text-align":"center"});
			}
		}
	}
	if(scenceType=="evalScenceFinRel"&&name=="indexName"){
		var val = $obj.val();
		$obj.parents("tr").data("finCode",val);
	}else{
		var text = $thisOp.text();
		$obj.parents("tr").find("span").html(text);
	}
    var $objTr = $obj.parents("tr");
    var $nextObj = $objTr.next();
	while($nextObj){
		var entityObj = $objTr.data("entityObj");
		if(entityObj===undefined){
			return false;
		}
    	var parentIndexNo = entityObj.indexNo;
    	var parentLevel = entityObj.indexNo;
    	var nextEntityObj = $nextObj.data("entityObj");
    	if(nextEntityObj===undefined){
    		return false;
    	}
    	var upIndexNo = nextEntityObj.upIndexNo;
    	var level = nextEntityObj.level;
        var opVal1,opVal2;
    	if(parentLevel!=level&&parentIndexNo==upIndexNo){
    		if($select!==undefined){
				if($nextObj.find("[name='opVal1']").get(0).tagName=="INPUT"){
					var $select1 = $select.clone();
					var $select2 = $select.clone();
					opVal1 = $nextObj.find("[name='opVal1']").val();
					opVal2 = $nextObj.find("[name='opVal2']").val();
					if(!$nextObj.find("[name='opVal1']").parent().data("tmpVal")){
						$nextObj.find("[name='opVal1']").parent().data("tmpVal",opVal1);
						$nextObj.find("[name='opVal2']").parent().data("tmpVal",opVal2);
					}
					opVal1 = $nextObj.find("[name='opVal1']").parent().data("tmpVal");
					opVal2 = $nextObj.find("[name='opVal2']").parent().data("tmpVal");
					$nextObj.find("[name='opVal1']").parent().html($select1);
					$nextObj.find("[name='opVal2']").parent().html($select2);
					//$nextObj.find("select[name='opSymbol1']").find("option[value='1']").attr("selected",true).setAttribute('disabled','true');;
					//$nextObj.find("select[name='opVal1']").find("option").get(0).attr("selected",true).setAttribute('disabled','true');;
					$select1.attr("name","opVal1");
					$select1.val(opVal1);
					$select2.attr("name","opVal2");
					$select2.val(opVal2);
				}
			}else{
				if($nextObj.find("[name='opVal1']").get(0).tagName=="SELECT"){
					opVal1 = $nextObj.find("[name='opVal1']").val();
					opVal2 = $nextObj.find("[name='opVal2']").val();
					if(!$nextObj.find("[name='opVal1']").parent().data("tmpVal")){
						$nextObj.find("[name='opVal1']").parent().data("tmpVal",opVal1);
						$nextObj.find("[name='opVal2']").parent().data("tmpVal",opVal2);
					}
					opVal1 = $nextObj.find("[name='opVal1']").parent().data("tmpVal");
					opVal2 = $nextObj.find("[name='opVal2']").parent().data("tmpVal");
					$nextObj.find("[name='opVal1']").parent().html('<input type="text" name="opVal1" class="center" size="10">');
					$nextObj.find("[name='opVal2']").parent().html('<input type="text" name="opVal2" class="center" size="10">');
					$nextObj.find("[name='opVal1']").val(opVal1);
					$nextObj.find("[name='opVal2']").val(opVal2);
				}
			}
			$nextObj.find("[name='"+selectName+"']").parent().html($thisSelect.clone());
    	}else{
    		break;
    	}
    	$nextObj = $nextObj.next();
	}
}

function selectOnchangeThis(obj){
	var $obj = $(obj);
	var scenceType = $obj.parents("li").attr("data-name");
	var selectName = $obj.attr("name");
	var $select;
	var $thisSelect;
	var $thisOp =$obj.find("option:selected");
	$thisSelect = $("<select><select>");
	$thisSelect.attr("name",selectName);
	$thisSelect.append('<option value="'+$thisOp.attr("value")+'">'+$thisOp.text()+'</option>');
	if($thisOp.data("data")!==undefined){
		var data = $thisOp.data("data");
		var type = data.type,keyName = data.keyName;
		if(type=="02"&&keyName!=null&&keyName!==undefined&&keyName!=""){//数据字典相
			var parmDic = getListForKeyName(keyName);
			if(parmDic!==undefined&&parmDic!=null&&parmDic!=""){
				$select = $('<select><option></option><select>');
				$.each(parmDic,function(key,obj){
					var val = obj.val;
					var $option = $('<option></option>');
					$option.attr("value",key);
					$option.text(val);
					$option.appendTo($select);
					
				});
				$select.css({"width":"78px","text-align":"center"});
			}
		}
	}
	var text = $thisOp.text();
	var value = $thisOp.attr("value");
	$obj.parents("tr").find("span[name=indexName]").html(text);
    var $objTr = $obj.parents("tr");
    var opVal1,opVal2;
	if($select!==undefined){
		if($objTr.find("[name='opVal1']").get(0).tagName=="INPUT"){
			var $select1 = $select.clone();
			var $select2 = $select.clone();
			opVal1 = $objTr.find("[name='opVal1']").val();
			opVal2 = $objTr.find("[name='opVal2']").val();
			if(!$objTr.data("tmpVal")){
				$objTr.data("tmpVal",opVal1);
				$objTr.data("tmpVal",opVal2);
			}
			opVal1 = $objTr.find("[name='opVal1']").parent().data("tmpVal");
			opVal2 = $objTr.find("[name='opVal2']").parent().data("tmpVal");
			$objTr.find("[name='opVal1']").parent().html($select1);
			$objTr.find("[name='opVal2']").parent().html($select2);
			$select1.attr("name","opVal1");
			$select1.val(opVal1);
			$select2.attr("name","opVal2");
			$select2.val(opVal2);
		}
	}else{
			if($objTr.find("[name='opVal1']").get(0).tagName=="SELECT"){
				opVal1 = $objTr.find("[name='opVal1']").val();
				opVal2 = $objTr.find("[name='opVal2']").val();
				if(!$objTr.data("tmpVal")){
					$objTr.data("tmpVal",opVal1);
					$objTr.data("tmpVal",opVal2);
				}
				opVal1 = $objTr.data("tmpVal");
				opVal2 = $objTr.data("tmpVal");
				$objTr.find("[name='opVal1']").parent().html('<input type="text" name="opVal1" class="center" size="10">');
				$objTr.find("[name='opVal2']").parent().html('<input type="text" name="opVal2" class="center" size="10">');
				$objTr.find("[name='opVal1']").val(opVal1);
				$objTr.find("[name='opVal2']").val(opVal2);
			}
	}
}

/**
 * 获取业务参数
 * @param {Object} keyName
 */
function getListForAppPropertyData(){
	var resultDatas=setListForAppPropertyData();
	dataEntity = resultDatas.finDatas;
	//财务指标
	var finDatas = {},
	propertyTypeTemp = "1",
	propertyValueTypeTmp = "00";//财务产生
	$.each(dataEntity, function(index,entity) {
		var propertyAppType = entity.propertyAppType;
		var propertyValueType = entity.propertyValueType;
		if(propertyTypeTemp==propertyAppType&&propertyValueType!=propertyValueTypeTmp){
			var vals = {"val":entity.propertyName};
			if(entity.propertyValueType=="02"){//数据字典项
				var data = {"type":entity.propertyValueType,"keyName":entity.propertyKeyName};
				vals["data"] = data;
			}
			finDatas[entity.propertyNo] = vals;
		}
	});
	//定性指标
	var dxDatas = {},
	dataEntity = resultDatas.dxDatas;
	propertyValueTypeTmp = "01";//财务产生
	propertyTypeTemp="2";//定性
	$.each(dataEntity, function(index,entity) {
		var propertyAppType = entity.propertyAppType;
		var propertyValueType = entity.propertyValueType;
		if(propertyTypeTemp==propertyAppType&&propertyValueType!=propertyValueTypeTmp){
			var vals = {"val":entity.propertyName};
			if(entity.propertyValueType=="02"){//数据字典项
				var data = {"type":entity.propertyValueType,"keyName":entity.propertyKeyName};
				vals["data"] = data;
			}
			dxDatas[entity.propertyNo] = vals;
		}
	});
	AppPropertyDatas={
			finDatas:finDatas,
			dxDatas:dxDatas
	};
}
//根据不同类型获得评级指标
function setListForAppPropertyData(){
	var scenceNo = evalScenceNo;
	var resultDatas={};
	var datas = {},
	parmData = {
		scenceNo:scenceNo
	};
	$.ajax({
		type:"post",
		data:{ajaxData:JSON.stringify(parmData)},
		url:webPath+"/appProperty/getAppPropertyForList",
		async:false,
		success:function(data){
			if(data.flag == "success"){
				datas = data.appPropertyData;
				resultDatas={
						finDatas:data.finAppPropertyData,
						dxDatas:data.dxAppPropertyData,
				}
			}else if(data.flag == "error"){
				alert(data.msg,0);
			}
		},error:function(){
			
		}
	});
	return resultDatas;
}
/**
 * select 标签赋值
 * @param {Object} trobj
 * @param {Object} name
 * @param {Object} val
 */
function setSelectTargVal(trobj,name,val){
	var $trobj= $(trobj);
	$trobj.find("select[name="+name+"]").val(val);
}
/**
 * 获取tr对象中的select value
 * @param {Object} trobj
 */
function getSelectTargVal(trobj){
	var $trobj = $(trobj);
	var data = {};
	$trobj.find("select").each(function(){
		var name = $(this).attr("name");
		data[name]= $(this).val();
	});
	return data;
}
/**
 * 计算json对象长度
 * @param {Object} jsonObj
 */
function getJsonObjLength(jsonObj) {
    var Length = 0;
    for (var item in jsonObj) {
      Length++;
    }
    return Length;
}
/**
 * 防止报错方法
 */
function enterKey(){};
function func_uior_valTypeImm(obj){};
/**
 * js对象深度复制
 * @param {Object} obj
 */
var cloneObj = function(obj){
    var str, newobj = obj.constructor === Array ? [] : {};
    if(typeof obj !== 'object'){
        return;
    } else if(window.JSON){
        str = JSON.stringify(obj), //系列化对象
        newobj = JSON.parse(str); //还原
    } else {
        for(var i in obj){
            newobj[i] = typeof obj[i] === 'object' ? 
            cloneObj(obj[i]) : obj[i]; 
        }
    }
    return newobj;
};

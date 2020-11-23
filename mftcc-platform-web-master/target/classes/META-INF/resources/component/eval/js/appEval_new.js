$(function(){
	//是否是查看 getFlag查看标志  true 为是
	if(getFlag===undefined){
		getFlag = false;
	}else if(getFlag=="true"){
		getFlag = true;
	}
	if(getFlag==true){//查看时将选择财报 不显示
		$(".content_show").find(".content_ul").data("entityData",dataMap.entityData);
		//个人
		if(cusBaseType=="2"){
			choseFininit("appevalpers0001");
			evalAppinit("evalpers1001");
		}else{
			choseFininit("appeval0001");
			evalAppinit("eval1001");
		}
	}
	var bodyWidth = $("body").width();
	var liLength= $(".content_ul li").length;
	$(".content_ul").css({width:(liLength)*bodyWidth+"px"});
	for(var i=0;i<liLength;i++){
		if(i==liLength-1){//最后一个div的宽度影响ie9的显示的处理
			$(".content_ul li:eq("+i+")").css({width:bodyWidth-3+"px"});
			continue;
		}
		$(".content_ul li:eq("+i+")").css({width:bodyWidth+"px"});
	}
	$(".showprogress ul li").click(function(){
		var $this = $(this);
		var name = $this.attr("name");
		var changeFlag =  false;
		if(name=="dl"||name=="dx"||name=="fin"){
			if(isTrue($this,"chosefin")){ changeFlag = true; }else{ changeFlag = false; }
		}else if(name=="adj"){
			changeFlag = true;
			if(isTrue($this,"dx")&&changeFlag){ changeFlag = true;}else{changeFlag = false;}
			if(isTrue($this,"dl")&&changeFlag){ changeFlag = true;}else{changeFlag = false;}
			if(isTrue($this,"fin")&&changeFlag){ changeFlag = true;}else{changeFlag = false;}
			if(isTrue($this,"chosefin")&&changeFlag){ changeFlag = true; }else{ changeFlag = false; }
		}else if(name=="evalapp"){
			if(isTrue($this,"chosefin")){ changeFlag = true; }else{ changeFlag = false;}
			if(isTrue($this,"dx")){ changeFlag = true; }else{ changeFlag = false;}
		}else{ changeFlag = true; }
		if(changeFlag){
			var evalFormId = "eval1001";
			//个人
			if(cusBaseType=="2"){
				evalFormId = "evalpers1001";
			}
			//发起评级
			if(name=="evalapp"&&query!="query"){
				evalAppinit(evalFormId);
				$("div[name=evalapp]").find(".li_content").eq(0).mCustomScrollbar("update");
			}else{
				//evalAppinit(evalFormId);		
				$("#bootstarpTag-div").find(".hidden-content").hide();
				$("#bootstarpTag-div").find("i").remove();
			}
			$(".selected").removeClass("selected");
			$(this).addClass("selected");
			var liIndex = $(this).index();
			$(".content_ul").animate({left:"-"+(liIndex*100)+"%"});
			//$("#addEval").hide();
			//$("#editEval").hide();
			$("#gradeCardButton").hide();
			$("#evalappButton").hide();
			$("#chosefinButton").hide();
			if(name=="dx"){
				name="gradeCard";
				$("#addEval").show();
				if(evalStage=="2"){//已保存评级评分卡
					$("#editEval").show();
					$("#addEval").hide();
				}
				$("div[id=evalapp] div[id=moreBar]").remove();
				myCustomScrollbarForForm({
					obj:"#gradeCard",
					advanced : {
						updateOnContentResize : true
					}
				});
			}
			if(name=="evalapp"||name=="chosefin"){
				$("#addEval").hide();
				$("#editEval").hide();
			}
			$("#"+name+"Button").show();
		}else{
			alert(top.getMessage("FIRST_OPERATION","前面的步骤"),0);
		}
	});
	//加载滚动条
	//初始化页面的表头
	initThead("",showData);
	$(window).resize(function() {
		var bodyWidth = $("body").width();
		var bodyheight = $("body").height();
		var liLength= $(".content_ul li").length;
		$(".content_ul").css({width:(liLength)*bodyWidth+"px"});
		for(var i=0;i<liLength;i++){
			if(i==liLength-1){//最后一个div的宽度影响ie9的显示的处理
				$(".content_ul li:eq("+i+")").css({width:bodyWidth-3+"px"});
				continue;
			}
			$(".content_ul li:eq("+i+")").css({width:bodyWidth+"px"});
		}
//		$(".content_ul li").css({width:bodyWidth+"px"});
	});
	//调整下拉事件
	$("select[name=restrictLevel]").attr("onfocus","this.defaultIndex=this.selectedIndex;");
	$("select[name=restrictLevel]").attr("onchange","this.selectedIndex=this.defaultIndex;");
	//发起评级阶段
	if(evalStage=="1"){
		//初始化查看数据
		initData(dataMap.listData);
		$(".showprogress ul").find("li[name=chosefin]").addClass("success");
		$(".showprogress ul").find("li[name=dx]").click();
	}else if(evalStage=="2"){//已保存评级评分卡
		//初始化查看数据
		initData(dataMap.listData);
		/*//如果没有定性和调整类型的评分卡，直接跳转综合评分
		if(!dataMap["dxData"]&&!dataMap["dxData"]){
			//$(".showprogress ul").find("li[name=evalapp]").click();
		}*/
		$.each(dataMap.gradeCardListData,function(iArgs,obj){
			var str,i,name,vuale;
			if(dataMap["dxData"+obj.gradeCardId]){
				str="dxData"+obj.gradeCardId;
				var dxList = dataMap[str].scoreList;
				for(i=0;i<dxList.split("@").length-1;i++){
					var dxobj = dxList.split("@")[i];
					name = dxobj.split(":")[0];
					vuale = dxobj.split(":")[1];
					$(".content_ul li div[name=dx"+obj.gradeCardId+"]").find("input[type=radio][name="+name+"]").each(function(index){
						if(vuale==$(this).attr("value")){
							$(this).prop("checked",true);
							$(this).attr("checked","checked");
							return false;
						}else{//如果选中的不是第一个，去掉第一个默认选中状态
							$(this).parents("tbody").eq(0).find("input[checked=checked]").removeAttr("checked");
						}
					});
				}
				$(".content_ul li div[name=dx"+obj.gradeCardId+"]").find("input[type=radio]").attr("disabled","true");
				$("#editEval").show();
				$("#addEval").hide();
			}
			if(dataMap["adjData"+obj.gradeCardId]){
				str="adjData"+obj.gradeCardId;
				var adjList = dataMap[str].scoreList;
				for(i=0;i<adjList.split("@").length-1;i++){
					var adjobj = adjList.split("@")[i];
					name = adjobj.split(":")[0];
					vuale = adjobj.split(":")[1];
					$(".content_ul li div[name=adj"+obj.gradeCardId+"]").find("input[type=radio][name="+name+"]").each(function(index){
						if(vuale==$(this).attr("value")){
							$(this).prop("checked",true);
							$(this).attr("checked","checked");
							return false;
						}else{//如果选中的不是第一个，去掉第一个默认选中状态
							$(this).parents("tbody").eq(0).find("input[checked=checked]").removeAttr("checked");
						}
					});
				}
				$(".content_ul li div[name=adj"+obj.gradeCardId+"]").find("input[type=radio]").attr("disabled","true");
			}
		});
		$(".showprogress ul").find("li[name=chosefin]").addClass("success");
		$(".showprogress ul").find("li[name=dx]").addClass("success");
		$(".showprogress ul").find("li[name=evalapp]").click();
		$("#editEval").show();
		$("#addEval").hide();
	}
	//加载完成 显示数据
	$(".eval-content").css({"display":"block"});
	//隐藏财务、定量、定性右上角总分
	$(".content_ul li").find(".li_title .scoreShow").hide();
	//隐藏页面中所有thead
	$("thead").hide();
	var check = $(".selected").attr("name");
	if(check == 'chosefin'){
		$("div[id=gradeCard] div[id=moreBar]").remove();
		$("div[id=evalapp] div[id=moreBar]").remove();
	}
	if(check == 'dx'){
		$("div[id=evalapp] div[id=moreBar]").remove();
		myCustomScrollbarForForm({
			obj:"#gradeCard",
			advanced : {
				updateOnContentResize : true
			}
		});
	}
	if(check == 'evalapp'){
		$("div[id=gradeCard] div[id=moreBar]").remove();
		myCustomScrollbarForForm({
			obj:"#evalapp",
			advanced : {
				updateOnContentResize : true
			}
		});
	}
	$("#evalappButton").css("position","fixed");
});

function addGradeCard(dataMap){
	$.each(dataMap.gradeCardListData,function(i,obj){
		var $tr=$('<tr id='+obj.gradeCardId+' name="gradeCard"></tr>');
		var strTd='<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">'+obj.gradeCardName+'</label></td>'+
		'<td class="tdvalue  half right" colspan="1" rowspan="1"><div class="input-group">';
		if(dataMap["dlData"+obj.gradeCardId]){
			strTd=strTd+'<input type="text" value="'+dataMap["dlData"+obj.gradeCardId].score+'" readonly="" class="form-control">'+
			'<input type="hidden" name="dlData'+obj.gradeCardId+'" value="1"></div></td>';
			$tr.append(strTd);
			$("input[name=grade]").parents("tr").eq(0).before($tr);
		}
		if(dataMap["dxData"+obj.gradeCardId]){
			strTd=strTd+'<input type="text" value="'+dataMap["dxData"+obj.gradeCardId].score+'" readonly="" class="form-control">'+
			'<input type="hidden" name="dxData'+obj.gradeCardId+'" value="1"></div></td>';
			$tr.append(strTd);
			$("input[name=grade]").parents("tr").eq(0).before($tr);
		}
		if(dataMap["finData"+obj.gradeCardId]){
			strTd=strTd+'<input type="text" value="'+dataMap["finData"+obj.gradeCardId].score+'" readonly="" class="form-control">'+
			'<input type="hidden" name="finData'+obj.gradeCardId+'" value="1"></div></td>';
			$tr.append(strTd);
			$("input[name=grade]").parents("tr").eq(0).before($tr);
		}
		if(dataMap["adjData"+obj.gradeCardId]){
			strTd=strTd+'<input type="text" value="'+dataMap["adjData"+obj.gradeCardId].score+'" readonly="" class="form-control">'+
			'<input type="hidden" name="adjData'+obj.gradeCardId+'" value="1"></div></td>';
			$tr.append(strTd);
			$("input[name=grade]").parents("tr").eq(0).before($tr);
		}
	});
	$.each($("tr[name=gradeCard]"),function(i,obj){
		var len = $(obj).find("td").length;
		if(len<4){
			if($(obj).next().attr("name")){
				$(obj).append($(obj).next().html());
				$(obj).next().remove();
			}
		}
	});
}
function Wkf_addDiyDom(treeId, treeNode) {
	var liObj = $("#" + treeNode.tId).empty();
	var icon = "<span id='" +treeNode.tId+"_icon' class='" +treeId+"_icon'><i></i></span>";
	var line = "<span id='" +treeNode.tId+"_line' class='" +treeId+"_line'></span>";
	var endDate = "<span id='" +treeNode.tId+"_date' class='" +treeId+"_date'>"+treeNode.end.split(" ")[0]+"</span>";
	var description = "<span id='" +treeNode.tId+"_description' class='" +treeId+"_description'>"+treeNode.description+"</span>";
	var endTime = "<span id='" +treeNode.tId+"_time' class='" +treeId+"_time'>"+treeNode.end.split(" ")[1]+"</span>";
	var optName = "<span id='" +treeNode.tId+"_optName' class='" +treeId+"_optName'>"+treeNode.optName+"</span>";
/* 	var result = "<span id='" +treeNode.tId+"_result' class='" +treeId+"_result'>"+treeNode.result+"</span>"; */
	var approveIdea = "<span id='" +treeNode.tId+"_approveIdea' class='" +treeId+"_approveIdea' >"+treeNode.approveIdea+"</span>";
	liObj.append(icon+line+endDate+description+endTime+optName+approveIdea);
	$("#" +treeNode.tId+"_line").css("height",liObj.outerHeight()-10+"px");
};

var isTrue = function($this,str){
	var $obj = $this.parent().find("li[name="+str+"]");
	if($obj.length>0){
		if($obj.hasClass("success")){
			return true;
		}else{
			return false;
		}
	}else{
		return true;
	}
};

var showData = {
	fin:{//财务
		"indexName":"指标名称",
		"stdCore":"指标值"
		/*"stdVal":"标准值",
		"stdScore":"标准得分",
		"minVal":"最低值",
		"minScore":"最低得分",
		"maxVal":"最高值",
		"maxScore":"最高得分",
		"stdCore":"得分"*/},
	dx:{//定性
		"indexName":"指标名称",
		"ctrl_btn":"打分选项"},
	dl:{//
		"indexName":"指标名称",
		/*"opVal1":"区间值1",
		"opSymbol1":"区间符号1",*/
		"stdCore":"指标值"
		/*"opSymbol2":"区间符号2",
		"opVal2":"区间值2",
		"stdCore":"指标得分",
		"score":"得分"}
		"stdCore":"得分"*/},
	adj:{
		"indexName":"指标名称",
		"ctrl_btn":"加减分选项卡",
		/*"stdCore":"得分明细",*/
	},
};

function initThead(obj,showData){
	var $obj = $(obj);
	if(dataMap.gradeCardListData){
		$.each(dataMap.gradeCardListData,function(i,obj){
			$.each(showData, function(index,dataObj) {
				var $liContent =  $(".eval-content .content_ul li").find("div[name="+index+obj.gradeCardId+"]");
				var $table = $liContent.find("table");
				if(index=="adj"+obj.gradeCardId){
					$table = $(".eval-content .content_ul li").find("form[id=initadj"+obj.gradeCardId+"]");
				}
				var $thTr = $('<tr></tr>');
				$.each(dataObj, function(key,dic) {
					var $th = $('<th name='+key+'>'+dic+'</th>');
					$thTr.append($th);
					if(key=="indexName"){
						$th.before('<th style="width:10%;" name="noneshow">&nbsp;</th>');
						$th.addClass("text_align_s");
						/*if(index=="dl"||index=="fin"||index=="adj"||index=="dx"){
					}else{
						$th.css({width:"20%"});
					}*/
					}
				});
				$table.find("thead").append($thTr);
				if(index=="dl"+obj.gradeCardId){
					$table.find("thead").find("tr th[name=indexName]").css({width:"36%"});
					$table.find("thead").find("tr th[name=javaItem]").css({width:"36%"});
				}else if(index=="fin"+obj.gradeCardId){
					$table.find("thead").find("tr th[name=indexName]").css({width:"36%"});
					$table.find("thead").find("tr th[name=javaItem]").css({width:"36%"});
				}
			});
		});
		
	}
}

var dxScoreThis = 0;//czk 2016-11-03 定性评分总分,并当更改选择时，总分随之更改
function dxScoreChange(obj){
	$(obj).parents("tbody").eq(0).find("input[checked=checked]").removeAttr("checked");
	$(obj).attr("checked","checked");
	dxScoreThis = 0;
	$("div[name=dx]").find(".li_content").find("input[type=radio]:checked").each(function(i,o){
		dxScoreThis = dxScoreThis + parseInt($(this).val());
	});
	$("div[name=dx]").find(".li_title .scoreShow .score").text(dxScoreThis);

};
var totalAdjustScore = 0;//调整指标得分总分数
function changeAdj(obj){
	var $obj = $(obj);
	var value = $obj.val();
	var trIndex = $obj.parent().parent().index();
	$obj.parents("tbody").eq(0).find("input[checked=checked]").removeAttr("checked");
	$obj.attr("checked","checked");
	if(value==""){
		value = "&nbsp;";
	}else{
		totalAdjustScore = totalAdjustScore + parseInt(value);
		$("div[name=adj]").find("input[name=adjustScore]").val(totalAdjustScore);
		
	}
	$obj.parent().parent().parents("td").next("td").find("table tbody tr").eq(trIndex).find("td").html(value);
};
window.setFinTableVal = function (data){
	var jsonData;
	if(data!==undefined&&data.indexOf("{")!=-1){
		jsonData = eval("("+data+")");
	}
	if(jsonData!==undefined){
		//财务为线性定量为阶段性
		var $table = $(".content_show .content_ul li div[name=dl]").find(".ls_list_a");
		$table.find("tbody tr").each(function(index){
			var $thisTr = $(this);
			var finCode = $thisTr.data("entityObj").javaItem;
			if(finCode!==undefined&&finCode!=null&&finCode!=""){
				if(jsonData[finCode]!==undefined){
					$thisTr.find("td[name=javaItem]").text(jsonData[finCode][0]);
					$thisTr.find("td[name=score]").text(jsonData[finCode][1]);
				}
			}else{
				$thisTr.find("td[name=javaItem]").text("0.0");
				$thisTr.find("td[name=score]").text("0.0");
			}
		});
	}
};
window.setDlTableVal = function(data){
	var jsonData;
	if(data!==undefined&&data.indexOf("{")!=-1){
		jsonData = eval("("+data+")");
	}
	if(jsonData!==undefined){
		var $table = $(".content_show .content_ul li div[name=dl]").find(".ls_list_a");
		$table.find("tbody tr").each(function(index){
			var $thisTr = $(this);
			var javaItem = $thisTr.data("entityObj").javaItem;
			if(javaItem!==undefined&&javaItem!=null&&javaItem!=""){
				if(jsonData[javaItem]!==undefined){
					$thisTr.find("td[name=javaItem]").text(jsonData[javaItem][0]);
					$thisTr.find("td[name=score]").text(jsonData[javaItem][1]);
				}
			}else{
				$thisTr.find("td[name=javaItem]").text("0.0");
				$thisTr.find("td[name=score]").text("0.0");
			}
		});
	}
};

/**
 * 获得定性详情
 */
window.getEvalDxDetailInfo = function(){
	$("input[type=radio]").attr("disabled","true");
	$("#editEval").show();
	$("#addEval").hide();
};
/**
 * 获得调整详情
 */
window.getEvalAdjDetailInfo = function(obj){
	$("input[type=radio]").attr("disabled","true");
	$("#editEval").show();
	$("#addEval").hide();
};
/**
 * 定性评分点击编辑
 */
window.editEval = function(){
	$("input[type=radio]").removeAttr("disabled");
	$("#editEval").hide();
	$("#addEval").show();
};
/**
 * 调整评分点击编辑
 */
window.editEvalAdj = function(obj){
	var $obj = $(obj);
	$obj.find("input[type=radio]").parent().parent().show();
	$obj.find("input[checked=checked]").show();
	$("#editEvalAdj").hide();
	$("#addEvalAdj").show();
};

window.evalAppinit = function(evalFormId){
	var evalAppNo = $(".content_show").find(".content_ul").data("entityData").evalAppNo;
	jQuery.ajax({
			url:webPath+"/appEval/getByIdAjaxForForm",
			data:{ajaxData:"",evalAppNo:evalAppNo,evalFormId:evalFormId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					$(".content_ul li div[name=evalapp]").find("#bootstarpTag-div").html(data.formHtml);
                    if(typeof (useType) != "undefined" && (useType == "2" || useType == "3")){

                    }else{
                        $("#evalMsg select[name=mangGrade] option:eq(0)").remove();
                    }
					addGradeCard(data);
					if(query=="query"){//详情
						$("#evalapp").find("i").remove();//隐藏输入框中的小笔或的放大镜图标
						$("#evalapp").find("font").remove();//隐藏输入框中的小笔或的放大镜图标
						$("#evalapp").find("input,select,textarea").attr("readonly","true");
						$("#evalapp").find("input,select,textarea").removeAttr("onclick");
					}
					
				}else if(data.flag=="error"){
					alert(data.msg,0);
				}
			},error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
};
window.choseFininit = function(evalFormId){
	var evalAppNo = $(".content_show").find(".content_ul").data("entityData").evalAppNo;
	jQuery.ajax({
		url:webPath+"/appEval/getByIdAjaxForChoseFinForm",
		data:{ajaxData:"",evalAppNo:evalAppNo,evalFormId:evalFormId},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			if(data.flag == "success"){
				$(".content_ul li div[name=chosefin]").find("#choseFinDiv").html(data.formHtml);
				setGradeModelOption();
				$("#choseFinForm").find("i").remove();//隐藏输入框中的小笔或的放大镜图标
				$("#choseFinForm").find("select").attr("readonly","true");
			}else if(data.flag=="error"){
				alert(data.msg,0);
			}
		},error:function(data){
			alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
};

function nextStep(obj){
	var $obj = $(obj);
	var liIndex = $obj.parents("li").index();
	if($obj.parents(".li_content").attr("data-flag") == "success"){
		$obj.parents(".content_ul").animate({left:"-"+((liIndex+1)*100)+"%"});
	}else{
		alert("当前操作未完成",0);
	}
}
function previousStep(obj){
	var $obj = $(obj);
	var liIndex = $obj.parents("li").index();
	$obj.parents(".content_ul").animate({left:"-"+((liIndex-1)*100)+"%"});
	
}
/**
 * 验证客户经理调整分数以及添加到总分上
 * @param obj
 */
function adjustSouseChange(obj){
	var $obj=$(obj);
	var manAdjustScore = $obj.val();
	if(manAdjustScore!=""){
		if(manAdjustScore>5||manAdjustScore<-5){
			$obj.val("");
			var title = $("input[name=manAdjustScore]").attr("title");
			alert(top.getMessage("ONLY_FORM_VALUE_SCOPE",{"field":title,"value1":"-5","value2":"5"}),0);
			return;
		}else if(manAdjustScore>=-5&&manAdjustScore<=5){
			var totalScoreTmp = $("input[name=totalScoreTmp]").val();
			$("input[name=totalScore]").val(parseFloat(manAdjustScore)+parseFloat(totalScoreTmp));
		}
	}
}

var evalRulesConfirmFlag ;
$(function(){
	if(evalCredit=="evalCredit"||evalCredit=="evalApp"){
		$.ajax({
			url : webPath+"/appEval/getEvalCardDetailAjaxForResult",
			data : {
				cusNo : cusNo,
				appSts:"4"
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if(data.flag=="success"){
					dataMap=data;
                    evalRulesConfirmFlag = data.evalRulesConfirmFlag;
					initCardData();
					$("#evalCard").show();
				}
			},
			error : function() {
				LoadingAnimate.stop();
			}
		});
	}else{
		$("#evalCard").remove();
	}
});
var showData;
if(evalRulesConfirmFlag==2){
    showData = {
        index:{//财务
            "indexName":"指标名称",
            "stdCore":"指标值"

        },
    };
}else{
    showData = {
        index:{//财务
            "indexName":"指标名称",
            "stdCore":"指标值",
            "rulesCore":"得分值"
        },
    };
}
//初始化评级评分卡信息
function initCardData(){
	//初始化页面的表头
	initThead(showData);
	initData(dataMap.listData);
	$.each($("input:radio"),function(index,item){
		if($(item).attr("checked") == "checked"){
			$(item).parent().parent().parent().parent().parent().parent().wrap("<table style='height:26px;width:100%'></table>");
		}else{
			 if($(item).parent().parent().find("input:radio[checked='checked']").size() == 0){
				$(item).parent().parent().remove();
			}else{
				$(item).parent().remove();
			} 
		}
	}); 
};
function initThead(showData){
	if(dataMap.gradeCardListData){
		$.each(dataMap.gradeCardListData,function(i,obj){
			$.each(showData, function(index,dataObj) {
				var $liContent =  $("#evalCard-content").find("div[name="+obj.gradeCardId+"]");
				var $table = $liContent.find("table");
				var $thTr = $('<tr></tr>');
                var $th;
				$.each(dataObj, function(key,dic) {
                    if(evalRulesConfirmFlag==2){
                        $th = $('<th name='+key+'>'+dic+'</th>');
                        $thTr.append($th);
                        if(key=="indexName"){
                            $th.before('<th style="width:10%;" name="noneshow">&nbsp;</th>');
                            $th.addClass("text_align_s");
                        }
                    }else{
                        if(key=="indexName"){
                            $th = $('<th style="width:21%  !important;" name='+key+'>'+dic+'</th>');
                            $thTr.append($th);
                            $th.before('<th style="width:12%;" name="noneshow">&nbsp;</th>');
                            $th.addClass("text_align_s");
                        }else{
                            $th = $('<th style="width:30%  !important;" name='+key+'>'+dic+'</th>');
                            $thTr.append($th);
                        }
                    }
				});
				$table.find("thead").append($thTr);
			});
		});
	}
}
function initData(listData){
	$.each(listData, function(key,data) {
		initTbody(key,data);
	});
	$.each(dataMap.gradeCardListData,function(iArgs,obj){
		if(dataMap["codeValue"+obj.gradeCardId]){
			var str="codeValue"+obj.gradeCardId;
			var dxList = dataMap[str].scoreList;
			for(var i=0;i<dxList.split("@").length-1;i++){
				var dxobj = dxList.split("@")[i];
				var name = dxobj.split(":")[0];
				var vuale = dxobj.split(":")[1];
				$("div[name="+obj.gradeCardId+"]").find("input[type=radio][name="+name+"]").each(function(index){
					if(vuale==$(this).attr("value")){
						$(this).prop("checked",true);
						$(this).attr("checked","checked");
						return false;
					}else{//如果选中的不是第一个，去掉第一个默认选中状态
						//$(this).parents("tbody").eq(0).find("input[checked=checked]").removeAttr("checked");
					}
				});
			}
			$("div[name="+obj.gradeCardId+"]").find("input[type=radio]").attr("disabled","true");
		}
		/*if(dataMap["adjData"+obj.gradeCardId]){
			var str="adjData"+obj.gradeCardId;
			if(dataMap[str].score!="0"){
				var adjList = dataMap[str].scoreList;
				for(var i=0;i<adjList.split("@").length-1;i++){
					var adjobj = adjList.split("@")[i];
					var name = adjobj.split(":")[0];
					var vuale = adjobj.split(":")[1];
					$("div[name=adj"+obj.gradeCardId+"]").find("input[type=radio][name="+name+"]").each(function(index){
						if(vuale==$(this).attr("value")){
							$(this).prop("checked",true);
							$(this).attr("checked","checked");
							return false;
						}else{//如果选中的不是第一个，去掉第一个默认选中状态
							$(this).parents("tbody").eq(0).find("input[checked=checked]").removeAttr("checked");
						}
					});
				}
				$("div[name=adj"+obj.gradeCardId+"]").find("input[type=radio]").attr("disabled","true");
			}
		}*/
	});
	$(".li_content_type").find("tr").css({height:"26px"});
};

function initTbody(type,data){
	$(".li_content_type").each(function(index){
		var $this = $(this);
		var name = $(this).attr("name");
		var nameUpperCase=name.toUpperCase();
		//data.indexOf("{")!=-1
		if(name!==undefined&&nameUpperCase.indexOf(type)!=-1){
			var $table = $(this).find("table");
			var thLength = $table.find("thead th").length;
			$table.find("tbody").html("");
			$.each(data, function(index,entityObj) {
				var $tr = $("<tr></tr>");
				var level = entityObj.level;
				var upIndexNo = entityObj.upIndexNo;
				var indexType = entityObj.indexType;
				$tr.data("entityObj",entityObj);
				if(level==1){
                    if(evalRulesConfirmFlag==2){
                        $tr.append('<td class="font_weight border_left first-td">'+entityObj.indexName+'</td><td class="border_left td" colspan="2"></td>');
                    }else{
                        $tr.append('<td class="font_weight border_left first-td">'+entityObj.indexName+'</td><td class="border_left td" colspan="3"></td>');
                    }					$table.find("tbody").eq(0).append($tr);
				}else if(level==2){
					if(indexType=="3"||indexType=="4"){//定性、调整
						$table.find("tbody tr").each(function(index){
							if($(this).data("entityObj")!==undefined){
								var $ValTd = $(this).find("td").eq(1);
								var thisIndexNo = $(this).data("entityObj").indexNo;
								if(upIndexNo==thisIndexNo){
                                    /*$tr.html("");
                                    $tr.append('<td name = "'+upIndexNo+'" class="border_left second-td-adj" rowspan="1">'+entityObj.indexName+'</td>');
                                    $tr.append('<td class="border_right" style="width: 66%;" colspan="2"></td>');
                                    $(this).find("td").eq(1).append($tr);*/
                                    var $thisTable = $('<table style="width: 100%;height:26px;"><tbody><tr></tr></tbody></table>');
                                    $thisTable.find("tr").append('<td name="'+entityObj.indexNo+'" class="border_left second-td-adj" style="color:#666;font-size:14px;padding-left: 5px;padding-top: 2px;border-bottom: 0px;" rowspan="1">'+entityObj.indexName+'</td>');
                                    $thisTable.find("tr").append('<td class="border_right" style="width: 33%;color:#666;font-size:14px;padding-left: 5px;padding-top: 2px;" colspan="2">'+entityObj.codeValue+'</td>');
                                    if((evalRulesConfirmFlag!=2)){
                                        $thisTable.find("tr").append('<td class="border_right" style="padding-left:15px" colspan="2">'+entityObj.resultValue+'</td>');
                                    }
                                    $ValTd.append($thisTable);
								}
							}
						});
					}else{
						$table.find("tbody tr").each(function(index){
							if($(this).data("entityObj")!==undefined){
								var $ValTd = $(this).find("td").eq(1);
								var thisIndexNo = $(this).data("entityObj").indexNo;
								if(upIndexNo==thisIndexNo){
									var $thisTable = $('<table style="width: 100%;height:26px;"><tbody><tr></tr></tbody></table>');
									$thisTable.find("tr").append('<td name="'+entityObj.indexNo+'" class="border_left second-td-adj" style="color:#666;font-size:14px;padding-left: 5px;padding-top: 2px;border-bottom: 0px;" rowspan="1">'+entityObj.indexName+'</td>');
									$thisTable.find("tr").append('<td class="border_right" style="width: 33%;color:#666;font-size:14px;padding-left: 5px;padding-top: 2px;" colspan="2">'+entityObj.codeValue+'</td>');
                                    if((evalRulesConfirmFlag!=2)){
                                        $thisTable.find("tr").append('<td class="border_right" style="padding-left:15px" colspan="2">'+entityObj.resultValue+'</td>');
                                    }
                                    $ValTd.append($thisTable);
								}
							}
						});
					}
				}else if(level==3){
					/*$table.find("tbody tr").each(function(index){
						if($(this).data("entityObj")!==undefined){
							var $ValTd = $(this).find("td").eq(1);
							var thisIndexNo = $(this).data("entityObj").indexNo;
							if(upIndexNo==thisIndexNo){
								var $radioInput = $('<input onclick="dxScoreChange(this);" name="'+thisIndexNo+'" type="radio" value="'+entityObj.indexNo+'">');
								var $thisTbody=$ValTd.find("table tbody");
								if($ValTd.find("table").length>0){
									var trNum=false;
									$thisTbody.find("tr").each(function(index){
										for ( var int = 0; int < $(this).find("td").length; int++) {
											var len=$(this).find("td").eq(int).html().length;
											if(len<=0){
												$(this).find("td").eq(int).append($radioInput.prop('outerHTML')+entityObj.indexName);
												trNum=true;
												break;
											}
										}
									});
									if(!trNum){
										$thisTbody.append('<tr><td>'+$radioInput.prop('outerHTML')+entityObj.indexName+'</td><td></td><td></td></tr>');
									}
								}else{
									var $thisTable = $('<table class="ls_list" style="width: 100%; margin: 0px 0px"><tbody><tr><td></td><td></td><td></td></tr></tbody></table>');
									//$radioInput.attr("checked","checked");
									//dxScoreThis = dxScoreThis + parseInt(entityObj.stdCore);
									$thisTable.find("tr").find("td").eq(0).append($radioInput.prop('outerHTML')+entityObj.indexName);
									$ValTd.append($thisTable);
								}
							}
						}
					});*/
				}
			});
		}
	});
	$(".second-td-adj").css('border-bottom','0px');
	$(".td").children("tr:first-child").children().css('border-top', '0px');
};
$(function(){
	if(dataMap!=undefined&&dataMap!=null&&dataMap!=""){
		var $table = $(".info .evel_table tbody");
		var $tr = $("<tr></tr>");
		var totalScore = 0;
		var debtTotalScore = 0;
		if(dataMap.entityData!==undefined){
			var $td1 = $('<td style="width:50%"></td>');
			var level = dataMap.level;
            var fontColor;
			if(useType == "1"){
                fontColor = "#40B745";
                if(level.indexOf("B")=="0"){
                    fontColor = "#47ADC3";
                }else if(level.indexOf("C")=="0"){
                    fontColor = "#C3A34C";
                }
                totalScore = dataMap.entityData.totalScore;
				debtTotalScore = dataMap.entityData.debtTotalScore;
                var evalStartDateTemp = dataMap.evalStartDate;
                var evalEndDateTemp = dataMap.evalEndDate;
                var evalStartDate = evalStartDateTemp.substring(0,4)+"-"+evalStartDateTemp.substring(4,6)+"-"+evalStartDateTemp.substring(6,8);
                var evalEndDate = evalEndDateTemp.substring(0,4)+"-"+evalEndDateTemp.substring(4,6)+"-"+evalEndDateTemp.substring(6,8);
                $td1.append('<span class="evel_title">信用等级</span>'+
                    '<span style="font-size: 30px;color: '+fontColor+';margin-left: 10px;">'+level+'</span>'+
					'<span class="evel_title" style="margin-left: 40px;">债项等级</span>'+
					'<span style="font-size: 30px;color: '+fontColor+';margin-left: 10px;">'+dataMap.debtLevel+'</span>'+
                    '<span style="font-size: 11px;float:right;margin-right:5px;">评级到期日期&nbsp;'+evalEndDate+'</span>'+
                    '<span style="font-size: 11px;float:right;margin-right:15px;">评级开始日期&nbsp;'+evalStartDate+'</span></br>'+
                    '<span style="font-size: 11px;"><a href="#evalHis-div" name="evalHis-div">评级历史</a></span></br>'
                );
            }
			if(useType == "2" || useType == "3"){
                fontColor = "#40B745";
                if(level.indexOf("低风险")=="0"){
                    fontColor = "#C3A34C";
                }else if(level.indexOf("高风险")=="0" || level.indexOf("业务拒绝")=="0"){
                    fontColor = "#E14A47";
                }
                totalScore = dataMap.entityData.totalScore;
                $td1.append('<span class="evel_title">综合评价</span>'+
                    '<span style="font-size: 41px;color: '+fontColor+';margin-left: 25px;">'+level+'</span><span style="font-size: 11px;margin-left: 30px;"><a href="#evalHis-div" name="evalHis-div">风险检查历史</a></span></br>'
                );
            }

			$tr.append($td1);
			/*非外部评级查询指标项*/
			if (evalType!='2') {
				var $td2 = $('<td style="width:50%"></td>');
				if(gradeType == '4'){
					if(typeof (ifExisRisk) != "undefined" && ifExisRisk == 'true'){
                        $td2.append('<div style="text-align: right;"><button id="evalRisk-button" class="btn btn-view btn-danger" type="button" onclick="riskDetail(\''+evalAppNo+'\',\'cus_eval_tip\');"> <span>风险提示</span></button></div>');
					}else{
                        $td2.append('<div style="text-align: right;"><button id="evalRisk-button" class="btn btn-view btn-forestgreen" type="button" onclick="riskDetail(\''+evalAppNo+'\',\'cus_eval_tip\');"> <span>风险提示</span></button></div>');
                    }
				}
                if(typeof (useType) != "undefined" && (useType == "2" || useType == "3")){
                }else{
                    $td2.append('<div style="text-align: right;">'+
                        '<span class="score_lable">信用得分</span>'+
                        '<span class="count_score">'+totalScore+'</span>&nbsp;&nbsp;&nbsp;&nbsp;'+
						'<span class="score_lable">债项得分</span>'+
						'<span class="count_score">'+debtTotalScore+'</span>'+
                        '</div>');

                    $td2.append('<div class="bar_div"><span class="bar_outter">'+
                        '<span class="bar_inner"></span>'+
                        '</span></div>');
                }
				var $showEval  =  $('<div class="div_line ">'+
								    '<span class="radio_span">'+
								    '<span class="radio_span_inner"></span>'+
									'</span>'+
									'<span class="radio_lable"></span>'+
									'<span class="radio_filed"></span>'+
									'</div>');
				//$(".li_content_type").hide();
				/*这块注释掉是因为某个项目里暂时隐藏了几个评分卡的分数显示
				 * $.each(dataMap.gradeCardListData,function(i,obj){
					var $tempShow = $showEval.clone(true);
					if(i>4){
						i=0;
					}
					$tempShow.addClass('show_'+i);
					$tempShow.find(".radio_lable").html('<a href="#'+obj.gradeCardId+'" name="'+obj.gradeCardId+'">'+obj.gradeCardName+'</a>');
					if(dataMap["finData"+obj.gradeCardId].score>0){
						$tempShow.find(".radio_filed").text("+"+dataMap["finData"+obj.gradeCardId].score);
					}else{
					}
					$tempShow.find(".radio_filed").text("+0");
					$td2.append($tempShow);
				})*/
				$tr.append($td2);
			}
		}else{
			var $td = $('<td></td>');
			$td.append('<span class="evel_title">综合评价</span>');
            // if(typeof (useType) != "undefined" && (useType == "2" || useType == "3")){
            //     $td.append('<span class="evaluation">该客户没有审批通过的风险检查</span>');
            // }else{
            //     $td.append('<span class="evaluation">该客户没有审批通过的评级</span>');
            // }
			$tr.append($td);
		}
		$table.append($tr);
		$table.find(".bar_inner").animate({width:totalScore+"%"},1200);
	}
	initThead(showData);
	//外部评级不查询
	if(evalType!='2'){
		//初始化页面的表头
		if(typeof(dataMap.listData) != "undefined"){
			initData(dataMap.listData);
		}
	}
	//评级走审批时才展示审批历史流程图
	if(appWorkFlowNo!=""){
		$("#wj-modeler2").empty();
		showWkfFlowVertical($("#wj-modeler2"),evalAppNo,"2","eval_approval");
	}else{
		$("#evalApproveHis-div").hide();
	};
	//评级历史列表上点超链查看评级详情时，详情中历史列表隐藏
	if(detailFlag=="hisDetail"){
		$("#evalHis-div").hide();
	}
	//区分个人和企业客户评级历史表
	if(cusBaseType=="1"){
		$("#persEvalHis").hide();
	}else if(cusBaseType=="2"){
		$("#corpEvalHis").hide();
	}
	
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
	$("#persEvalHis").html(dataMap.tableHtml);
	$("#corpEvalHis").html(dataMap.tableHtml);
	$("#tablist").show();
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
            "stdCore":"指标值"
        },
    };
}
if(typeof (useType) != "undefined" && (useType == "2" || useType == "3")){
    showData = {
        index:{//财务
            "indexName":"指标名称",
            "stdCore":"指标值",
            "rulesCore":"风险等级"
        },
    };
}
function initThead(showData){
	if (evalType!='2') {
		if(dataMap.gradeCardListData){
			$.each(dataMap.gradeCardListData,function(i,obj){
				jQuery.ajax({
					url:webPath+"/mfEvalGradeCard/getByIdAjax",
					data:{gradeCardId:obj.gradeCardId},
					type:"POST",
					dataType:"json",
					beforeSend:function(){
					},success:function(data){
						if(data.flag == "success"){
							var tmpShowData;
							if(data.formData.gradeCardNameEn == 'dlpg'){
								var dlData = {index:{"indexName":"指标名称","stdCore":"指标分","score":"指标值"},};
								tmpShowData = dlData;
							}else{
								tmpShowData = showData;
							}
							$.each(tmpShowData, function(index,dataObj) {
								var $liContent =  $("#freewall").find("div[name="+obj.gradeCardId+"]");
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
						}
					},error:function(data){
						alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});



			});
		}
	}

}
function riskDetail(relNo,nodeNo){
    top.window.openBigForm(webPath+'/riskForApp/preventListForCusEval?relNo='+relNo+'&nodeNo='+nodeNo,'风险提示信息',function(){});
};

function getFinancialRatio(){
	top.window.openBigForm(webPath+'/appEval/getFinancialRatio?evalAppNo='+evalAppNo,'财务比率辅助计算工具',function(){});
};

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
		}

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
                    }
					$table.find("tbody").eq(0).append($tr);
				}else if(level==2){
					if(indexType=="3"||indexType=="4"){//定性、调整
						$table.find("tbody tr").each(function(index){
							if($(this).data("entityObj")!==undefined){
								var $ValTd = $(this).find("td").eq(1);
								var thisIndexNo = $(this).data("entityObj").indexNo;
								if(upIndexNo==thisIndexNo){
                                    var $thisTable = $('<table class="ls_list" style="width: 100%; margin: 0px 0px"><tbody><tr></tr></tbody></table>');
                                    $thisTable.find("tr").append('<td name="'+entityObj.indexId+'" class="border_left second-td-adj" rowspan="1" style="border-bottom: 0px;">'+entityObj.indexName+'</td>');
                                    $thisTable.find("tr").append('<td class="border_right" style="padding-left:15px" colspan="2">'+entityObj.codeValue+'</td>');
                                 if((evalRulesConfirmFlag!=2)){
                                     // if(entityObj.resultValue == "高风险"){
                                     //     $thisTable.find("tr").append('<td class="border_right" style="padding-left:15px;color: red" colspan="2">'+entityObj.resultValue+'</td>');
                                     // }else{
                                     //     $thisTable.find("tr").append('<td class="border_right" style="padding-left:15px" colspan="2">'+entityObj.resultValue+'</td>');
                                     // }
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
									jQuery.ajax({
										url:webPath+"/mfEvalGradeCard/getByIdAjax",
										data:{gradeCardId:entityObj.gradeCardId},
										type:"POST",
										dataType:"json",
										beforeSend:function(){
										},success:function(data){
											if(data.flag == "success"){
												var $thisTable = $('<table class="ls_list" style="width: 100%; margin: 0px 0px"><tbody><tr></tr></tbody></table>');
												$thisTable.find("tr").append('<td name="'+entityObj.indexId+'" class="border_left second-td-adj" rowspan="1" style="border-bottom: 0px;">'+entityObj.indexName+'</td>');
												$thisTable.find("tr").append('<td class="border_right" style="padding-left:15px" colspan="2">'+entityObj.codeValue+'</td>');
												if(data.formData.gradeCardNameEn == 'dlpg' && typeof (entityObj.codeScore)!= 'undefined' && entityObj.codeScore !=''){
													$thisTable.find("tr").append('<td class="border_right" style="padding-left:15px" colspan="2">'+entityObj.codeScore+'</td>');
												}
												if((evalRulesConfirmFlag!=2)){
													// if(entityObj.resultValue == "高风险"){
													//     $thisTable.find("tr").append('<td class="border_right" style="padding-left:15px;color: red" colspan="2">'+entityObj.resultValue+'</td>');
													// }else{
													//     $thisTable.find("tr").append('<td class="border_right" style="padding-left:15px" colspan="2">'+entityObj.resultValue+'</td>');
													// }
												}
												$ValTd.append($thisTable);
											}
										},error:function(data){
											alert(top.getMessage("FAILED_OPERATION"," "),0);
										}
									});

								}
							}
						});
					}
				}else{
					/*$table.find("tbody tr").each(function(index){
						if($(this).data("entityObj")!==undefined){
							var $ValTd = $(this).find("td").eq(1);
							var thisIndexNo = $(this).data("entityObj").indexNo;
							if(upIndexNo==thisIndexNo){
                                var $thisTable = $('<table class="ls_list" style="width: 100%; margin: 0px 0px"><tbody><tr></tr></tbody></table>');
                                $thisTable.find("tr").append('<td name="'+entityObj.indexId+'" class="border_left second-td-adj" rowspan="1" style="border-bottom: 0px;">'+entityObj.indexName+'</td>');
                                //$thisTable.find("tr").append('<td class="border_right" style="padding-left:15px" colspan="2">'+entityObj.codeValue+'</td>');
                                // $thisTable.find("tr").append('<td class="border_right" style="padding-left:15px" colspan="2">'+entityObj.resultValue+'</td>');
                                $ValTd.append('<td name="'+entityObj.indexId+'" class="border_left second-td-adj" rowspan="1" style="border-bottom: 0px;">'+entityObj.indexName+'</td>');
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

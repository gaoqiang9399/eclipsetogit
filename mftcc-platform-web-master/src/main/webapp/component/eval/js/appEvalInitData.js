function initData(listData){
	$.each(listData, function(key,data) {
		initEvalIndexTbody(key,data);
	});
};

function initEvalIndexTbody(type,data){
	$("div[name=gradeCard] .li_content_type").each(function(index){
		var $this = $(this);
		var name = $(this).attr("name");
		var nameUpperCase = name.toUpperCase();
		if(name!==undefined&&nameUpperCase.indexOf(type)!=-1){
			var $table = $(this).find("table");
			var thLength = $table.find("thead th").length;
			$table.find("tbody").html("");
			$.each(data, function(index,entityObj) {
				var $tr = $("<tr></tr>");
				var level = entityObj.level;
				var upIndexNo = entityObj.upIndexNo;
				var indexType = entityObj.indexType;
				var propertyType = entityObj.propertyType;
				var gradeCardId = entityObj.gradeCardId;
				$tr.data("entityObj",entityObj);
				if(level==1){
					$tr.append('<td class="font_weight border_left first-td">'+entityObj.indexName+'</td><td class="border_left td" colspan="2"></td>');
					$table.find("tbody").eq(0).append($tr);
				}else if(level==2){
					$table.find("tbody tr").each(function(index){
						if($(this).data("entityObj")!==undefined){
							var $ValTd = $(this).find("td").eq(1);
							var thisIndexNo = $(this).data("entityObj").indexNo;
							if(upIndexNo==thisIndexNo){
								$tr.html("");
								if(indexType=="3"||indexType=="4"){//定性、调整
									$tr.append('<td name = "'+upIndexNo+'" class="border_left second-td-adj" rowspan="1">'+entityObj.indexName+'</td>');
									$tr.append('<td class="border_right" style="width: 66%;" colspan="2"></td>');
									$ValTd.append($tr);
								}else{
									jQuery.ajax({
										url:webPath+"/mfEvalGradeCard/getByIdAjax",
										data:{gradeCardId:gradeCardId},
										type:"POST",
										dataType:"json",
										beforeSend:function(){
										},success:function(data){
											if(data.flag == "success"){
												if(propertyType == '02'){
													var $thisTable = $('<table class="ls_list" style="width: 100%; margin: 0px 0px"><tbody><tr></tr></tbody></table>');
													$thisTable.find("tr").append('<td name="'+entityObj.indexId+'" class="border_left second-td-adj" rowspan="1" style="border-bottom: 0px; width: 24.1% !important;">'+entityObj.indexName+'</td>');
													$thisTable.find("tr").append('<td class="border_right"  colspan="2"><input type="text" onblur="AppEval_InitiateApp.checkNum(this)" id="property_'+entityObj.indexNo+'" style="border: 0;width: 50% ;cursor:pointer;" name="'+ entityObj.indexNo+'" value="'+ entityObj.codeValue+'"></input></td>');
													$ValTd.append($thisTable);
												}else{
													var $thisTable = $('<table class="ls_list" style="width: 100%; margin: 0px 0px"><tbody><tr></tr></tbody></table>');
													$thisTable.find("tr").append('<td name="'+entityObj.indexId+'" class="border_left second-td-adj" rowspan="1" style="border-bottom: 0px; width: 24.1% !important;">'+entityObj.indexName+'</td>');
													$thisTable.find("tr").append('<td class="border_right" style="padding-left:15px" colspan="2">'+entityObj.codeValue+'</td>');
													if(data.formData.gradeCardNameEn == 'dlpg' && typeof (entityObj.codeScore)!= 'undefined' && entityObj.codeScore !=''){
														$thisTable.find("tr").append('<td class="border_right" style="padding-left:15px" colspan="2">'+entityObj.codeScore+'</td>');
													}
													$ValTd.append($thisTable);
												}
											}
										},error:function(data){
											alert(top.getMessage("FAILED_OPERATION"," "),0);
										}
									});
								}
							}
						}
					});
				}else if(level==3){
					$table.find("tbody tr").each(function(index){
						if($(this).data("entityObj")!==undefined){
							var $ValTd = $(this).find("td").eq(1);
							var thisIndexNo = $(this).data("entityObj").indexNo;
							if(upIndexNo==thisIndexNo){
								var $radioInput = $('<input onclick="AppEval_InitiateApp.dxScoreChange(this);" name="'+thisIndexNo+'" type="radio" value="'+entityObj.indexValue+'">');
								var $thisTbody=$ValTd.find("table tbody");
								if($ValTd.find("table").length>0){
									var trNum=false;
									$thisTbody.find("tr").each(function(index){
										for ( var int = 0; int < $(this).find("td").length; int++) {
											var len=$(this).find("td").eq(int).html().length;
											if(len<=0){
                                                if(entityObj.indexName=='否'){
                                                    $radioInput.attr("checked","checked");
                                                }
												$(this).find("td").eq(int).append("<label>" + $radioInput.prop('outerHTML')+entityObj.indexName + "</label>");
												trNum=true;
												break;
											}
										}
									});
									if(!trNum){
                                        if(entityObj.indexName=='否'){
                                            $radioInput.attr("checked","checked");
                                        }
										$thisTbody.append('<tr><td>'+ "<label>" + $radioInput.prop('outerHTML')+entityObj.indexName + "</label>"+'</td><td></td><td></td></tr>');
									}
								}else{
									var $thisTable = $('<table class="ls_list" style="width: 100%; margin: 0px 0px"><tbody><tr><td></td><td></td><td></td></tr></tbody></table>');
									if(entityObj.indexName!='是'){
                                        $radioInput.attr("checked","checked");
									}
									//dxScoreThis = dxScoreThis + parseInt(entityObj.stdCore);
									$thisTable.find("tr").find("td").eq(0).append("<label>" + $radioInput.prop('outerHTML')+entityObj.indexName + "</label>");
									$ValTd.append($thisTable);
								}
							}
						}
					});
				}
			});
		}
	});
	$(".td").children("tr:first-child").children().css('border-top', '0px');
};
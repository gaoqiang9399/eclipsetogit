function initData(listData){
	$.each(listData, function(key,data) {
		initTbody(key,data);
	});
};
function initTbody(type,data){
	$("div[name=gradeCard] .li_content_type").each(function(index){
		var $this = $(this);
		var name = $(this).attr("name");
		var nameUpperCase = name.toUpperCase();
		if(name!==undefined&&nameUpperCase.indexOf(type)!=-1){
			var $table = $(this).find("table");
			var thLength = $table.find("thead th").length;
			if(type==nameUpperCase&&nameUpperCase.indexOf("DX")!=-1){
				$table.find("tbody").html("");
				$.each(data, function(index,entityObj) {
					var $tr = $("<tr></tr>");
					var level = entityObj.level;
					var upIndexNo = entityObj.upIndexNo;
					$tr.data("entityObj",entityObj);
					if(level==1){
						$tr.append('<td class="font_weight border_left first-td">'+entityObj.indexName+'</td><td class="border_left td" colspan="2"></td>');
						$table.find("tbody").append($tr);
					}else if(level==2){
						$table.find("tbody tr").each(function(index){
							if($(this).data("entityObj")!==undefined){
								var $ValTd = $(this).find("td").eq(1);
								var thisIndexNo = $(this).data("entityObj").indexNo;
								if(upIndexNo==thisIndexNo){
									$tr.append('<td name = "'+upIndexNo+'" class="border_left second-td-adj" rowspan="1">'+entityObj.indexName+'</td><td class="border_right" style="width: 66%;" colspan="2"></td>');
									$(this).find("td").eq(1).append($tr);
								}
							}
						});
					}else{
						$table.find("tbody tr").each(function(index){
							if($(this).data("entityObj")!==undefined){
								var $ValTd = $(this).find("td").eq(1);
								var thisIndexNo = $(this).data("entityObj").indexNo;
								if(upIndexNo==thisIndexNo){
									var $radioInput = $('<input onclick="dxScoreChange(this);" name="'+thisIndexNo+'" type="radio" value="'+entityObj.stdCore+'">');
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
										$radioInput.attr("checked","checked");
										dxScoreThis = dxScoreThis + parseInt(entityObj.stdCore);
										$thisTable.find("tr").find("td").eq(0).append($radioInput.prop('outerHTML')+entityObj.indexName);
										$ValTd.append($thisTable);
									}
								}
							}
						});
					}
				});
			}else if(type==nameUpperCase&&nameUpperCase.indexOf("ADJ")!=-1){
				$table.find("tbody").html("");
				$.each(data, function(index,entityObj) {
					var upIndexNo = entityObj.upIndexNo;
					var indexNo = entityObj.indexNo;
					var $tr = $("<tr></tr>");
					var level = entityObj.level;
					$tr.data("entityObj",entityObj);
					if(level==1){
						$tr.append('<td class="font_weight border_left first-td">'+entityObj.indexName+'</td><td class="border_left td" colspan="2"></td>');
						$table.find("tbody").append($tr);
					}else if(level==2){
						$table.find("tbody tr").each(function(index){
							if($(this).data("entityObj")!==undefined){
								var $ValTd = $(this).find("td").eq(1);
								var thisIndexNo = $(this).data("entityObj").indexNo;
								if(upIndexNo==thisIndexNo){
									$tr.append('<td name = "'+upIndexNo+'" class="border_left second-td-adj">'+entityObj.indexName+'</td><td class="border_right" style="width: 66%;" colspan="2"></td>');
									$(this).find("td").eq(1).append($tr);
								}
							}
						});
					}else if(level==3){
						$table.find("tbody tr").each(function(index){
							if($(this).data("entityObj")!==undefined){
								var $ValTd = $(this).find("td").eq(1);
								var thisIndexNo = $(this).data("entityObj").indexNo;
								if(upIndexNo==thisIndexNo){
									var $radioInput = $('<input onclick="changeAdj(this);" name="'+thisIndexNo+'" type="radio" value="'+entityObj.stdCore+'">');
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
										$radioInput.attr("checked","checked");
										dxScoreThis = dxScoreThis + parseInt(entityObj.stdCore);
										$thisTable.find("tr").find("td").eq(0).append($radioInput.prop('outerHTML')+entityObj.indexName);
										$ValTd.append($thisTable);
									}
								}
							}
						});
					}
				});
			}else if(type==nameUpperCase&&nameUpperCase.indexOf("DL")!=-1){//定量
				$table.find("tbody").html("");
				$.each(data, function(index,entityObj) {
					var $tr = $("<tr></tr>");
					var level = entityObj.level;
					var indexNo = entityObj.indexNo;
					var upIndexNo = entityObj.upIndexNo;
					$tr.data("entityObj",entityObj);
					if(level==1){
						$tr.append('<td class="font_weight  border_left first-td" rowspan="0">'+entityObj.indexName+'</td>');
						$tr.append('<td class="border_center" colspan="'+thLength+'"></td>');
						$table.append($tr);
					}else if(level==2){
						var $appendTrObj;
						$table.find("tbody tr").each(function(){
							var thisIndexNo = $(this).data("entityObj").indexNo;
							var thisUpIndexNo = $(this).data("entityObj").upIndexNo;
							if(upIndexNo == thisIndexNo){
								$appendTrObj = $(this);
							}
						});
						if($appendTrObj!==undefined){
							var $tdTable = $appendTrObj.find("td").eq(1).find("table");
							if($tdTable!==undefined&&$tdTable.length>0){
								$table.find("thead th").each(function(index){
									var $td = $('<td class="border_left second-td"></td>');
									var colName = $(this).attr("name");
									if(colName!="noneshow"){
										$td.attr("name",colName);
										if(colName == "indexName"){
											$td.text(entityObj[colName]);
										}else{
											$td.text(entityObj[colName]);
										}
										$td.css({"padding-left":"15px"});
										$tr.append($td);
									}
								});
								$tdTable.find("tbody").append($tr);
							}else{
								$appendTrObj.find("td").eq(1).append('<table class="ls_list" style="width: 100%; margin: -1px 0px"><tbody></tbody></table>');
								$table.find("thead th").each(function(index){
									var colName = $(this).attr("name");
									var $td = $('<td class="border_left second-td"></td>');
									if(colName!="noneshow"){
										$td.attr("name",colName);
										if(colName == "indexName"){
											$td.text(entityObj[colName]);
											$td.css({"width":"23%"});
										}else{
											$td.text(entityObj[colName]);
										}
										$td.css({"padding-left":"15px"});
										if(index<3){
											//$td.css({"width":"22%"});
										}
										$tr.append($td);
									}
								});
								$appendTrObj.find("td").find("table tbody").append($tr);
							}
						}
					}
				});
			}else{//财务
				$table.find("tbody").html("");
				$.each(data, function(index,entityObj) {
					var $tr = $("<tr></tr>");
					var level = entityObj.level;
					var indexNo = entityObj.indexNo;
					var upIndexNo = entityObj.upIndexNo;
					$tr.data("entityObj",entityObj);
					if(level==1){
						$tr.append('<td class="font_weight border_left first-td" rowspan="0">'+entityObj.indexName+'</td>');
						$tr.append('<td class="border_center" colspan="2"></td>');
						$table.append($tr);
					}else if(level==2){
						var $appendTrObj;
						$table.find("tbody tr").each(function(){
							var thisIndexNo = $(this).data("entityObj").indexNo;
							var thisUpIndexNo = $(this).data("entityObj").upIndexNo;
							if(upIndexNo == thisIndexNo){
								$appendTrObj = $(this);
							}
						});
						if($appendTrObj!==undefined){
							var $tdTable = $appendTrObj.find("td").eq(1).find("table");
							if($tdTable!==undefined&&$tdTable.length>0){
								$table.find("thead th").each(function(index){
									var $td = $('<td class="border_left second-td"></td>');
									var colName = $(this).attr("name");
									if(colName!="noneshow"){
										$td.attr("name",colName);
										if(colName == "indexName"){
											$td.text(entityObj[colName]);
										}else{
											$td.text(entityObj[colName]);
										}
										$td.css({"padding-left":"15px"});
										$tr.append($td);
									}
								});
								$tdTable.find("tbody").append($tr);
							}else{
								$appendTrObj.find("td").eq(1).append('<table class="ls_list" style="width: 100%; margin: -1px 0px"><tbody></tbody></table>');
								$table.find("thead th").each(function(index){
									var colName = $(this).attr("name");
									var $td = $('<td class="border_left"></td>');
									if(colName!="noneshow"){
										$td.attr("name",colName);
										if(colName == "indexName"){
											$td.text(entityObj[colName]);
											$td.css({"width":"23%"});
										}else{
											$td.text(entityObj[colName]);
										}
										if(index<3){
											//$td.css({"width":"22%"});
										}
										$td.css({"padding-left":"15px"});
										$tr.append($td);
									}
								});
								$appendTrObj.find("td").find("table tbody").append($tr);
							}
						}
					}
				});
			}
		}
	});
	$(".td").children("tr:first-child").children().css('border-top', '0px');
};
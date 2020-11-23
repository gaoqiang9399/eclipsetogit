;
var ExamCardInitData=function(window,$){
	var _initData=function(listData){
		$.each(listData, function(key,data) {
			_initTbody(key,data);
		});
	};
	var _initTbody=function(type,data){
		$("div[name=gradeCard] .li_content_type").each(function(index){
			var $this = $(this);
			var name = $(this).attr("name");
			var nameUpperCase = name.toUpperCase();
			type=type.toUpperCase();
			if(name!==undefined&&nameUpperCase.indexOf(type)!=-1){
				var $table = $(this).find("table[class=ls_list_a]");
				var thLength = $table.find("thead th").length;
				if(type==nameUpperCase){
					$table.find("tbody[class=level1]").html("");
					$.each(data, function(index,entityObj) {
						var $tr = $("<tr></tr>");
						var level = entityObj.level;
						var upIndexId = entityObj.upIndexId;
						$tr.data("entityObj",entityObj);
						if(level==1){
							$tr.append('<td class="font_weight border_left first-td">'+entityObj.indexName+'</td><td class="border_left td" colspan="2"></td>');
							$table.find("tbody[class=level1]").append($tr);
						}else if(level==2){
							$table.find("tbody tr").each(function(index){
								if($(this).data("entityObj")!==undefined){
									var $ValTd = $(this).find("td").eq(1);
									var thisIndexId = $(this).data("entityObj").indexId;
									if(upIndexId==thisIndexId){
										$tr.append('<td name = "'+upIndexId+'" class="border_left second-td-adj" rowspan="1">'+entityObj.indexName+'</td><td class="border_right" style="width: 66%;" colspan="2"></td>');
										$(this).find("td").eq(1).append($tr);
									}
								}
							});
						}else{
							$table.find("tbody tr").each(function(index){
								if($(this).data("entityObj")!==undefined){
									var $ValTd = $(this).find("td").eq(1);
									var thisIndexId = $(this).data("entityObj").indexId;
									if(upIndexId==thisIndexId){
										var $radioInput = $('<input onclick="ExamCardInitData.indexChange(this);" id="'+entityObj.indexId+'" name="'+entityObj.indexId+'" type="radio" value="'+entityObj.indexRiskLevel+'">');
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
											var $thisTable = $('<table class="ls_list" style="width: 100%; margin: 0px 0px"><tbody class="level3"><tr><td></td><td></td><td></td></tr></tbody></table>');
											$radioInput.attr("checked","checked");
											$thisTable.find("tr").find("td").eq(0).append($radioInput.prop('outerHTML')+entityObj.indexName);
											$ValTd.append($thisTable);
										}
									}
								}
							});
						}
					});
				}
			}
		});
		$(".td").children("tr:first-child").children().css('border-top', '0px');
	};
	var _indexChange=function(obj){
		$(obj).parents("tbody").eq(0).find("input[checked=checked]").prop("checked",false);
		$(obj).parents("tbody").eq(0).find("input[checked=checked]").removeAttr("checked");
		$(obj).attr("checked","checked");
	};
	return{
		initData:_initData,
		indexChange:_indexChange,
	}
}(window,jQuery);
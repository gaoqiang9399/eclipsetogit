$.fn.tableRcswitcher =  function(options){
	
	var reloadTable = function(){
		$table.tableRcswitcher(options);
	}
	var falseRcSwitcher =  function($toggler,$checkInput){
		 var offOn = $toggler.hasClass("on")?true:false;
		 if(offOn){
			$toggler.removeClass("on").addClass("off");
			$toggler.css("transform","translateX(-41px)");
			$checkInput.prop('checked',false);
		}else{
			$toggler.removeClass("off").addClass("on");
			$toggler.css("transform","translateX(-9px)");
			$checkInput.prop('checked',true);
		}
	}
	
	var setTableRcswitcher = function(){
		
		if(thName!=undefined){
			
			
			$table.find("tbody tr").each(function(index){
				var $tr = $(this);
				var thNameAyyay = thName.split(",");
				$.each(thNameAyyay,function(ind,thNameThis){
					var thIndex = $table.find("thead th[name="+thNameThis+"]").index();
					var $td = $tr.find("td").eq(thIndex);
					var tableId = $table.attr("title");
					if($td!==undefined&&$td.length>0){
						var $a = $td.find("a");
						if($a!==undefined&&$a.length>0){
							var href = $a.attr("href");
							var text = $a.text();
//							if(href.indexOf(".action")!=-1){
								var indexCheckBox = '<input name="'+thNameThis+'" type="checkbox" value="1"/>';
								$td.html(indexCheckBox);
								if(text==onVal){
									$td.find("input[type=checkbox]").prop('checked',true);
								}
								$td.find("input[type=checkbox]").rcSwitcher({
									width: width,
									height: height,
								 	theme: theme,
								 	blobOffset: 1,
								 	onText:onText,
								 	offText:offText}).on("change.rcSwitcher",
									function(e,dataObj,changeType){
										var $toggler = dataObj.components.$toggler;
										var $checkInput = dataObj.$input;
										var thNameVal = $checkInput.is(":checked")?onVal:offVal;
										var parmData = {};
										var url = href.split("?")[0];
										if(href.split("?")[1]!==undefined){
											$.each(href.split("?")[1].split("&"), function(index,data) {
												var col = data.split("=")[0];
												var val = data.split("=")[1];
												parmData[col]=val;
											});
										}
										parmData[thNameThis]=thNameVal;
										if(url.substr(0,1)=="/"){
											url =webPath + url; 
										}else{
											url =webPath + "/" + url;
										}
										
										$.ajax({
											type:"post",
											url:url,
											async:false,
											data:{ajaxData:JSON.stringify(parmData),tableId:tableId},
											success:function(data){
												if(data.flag=="success"){
													//$.myAlert.Alert(data.msg);
													if(data.tableData!=undefined&&data.tableData!=null){
														var trHtml = $(data.tableData).find("tbody tr").html();
														$tr.html(trHtml);
														$tr.find("td[mytitle]").initMytitle();
														reloadTable();
													 }
													//alert(data.msg,1);
													if(callback!==undefined&&typeof(callback)=="function"){
														callback(this);
													}
												}else if(data.flag=="error"){
													falseRcSwitcher($toggler,$checkInput);
													//$.myAlert.Alert(data.msg);
													alert(data.msg,0);
												}
											},error:function(){
												//$.myAlert.Alert("操作失败,请检查链接！");
												alert("操作失败,请检查链接！",0);
												falseRcSwitcher($toggler,$checkInput);
											}
										});
									});
//							}else{
//								//$.myAlert.Alert("未配置字段链接或配置的是点击事件！");
//							}
						}else{
							//$.myAlert.Alert("未配置字段链接！");
						}
					}
				});
				
				
		
			});
		}else{
			//$.myAlert.Alert("未配置相应字段！");
			alert("未配置相应字段！",0);
		}
	}
	
	
	
	
	var $table =  $(this);
	var thName;
	var width;
	var height;
	var theme;
	var onText;
	var offText;
	var onVal ;
	var offVal ;
	var callback ;
	if(options.length >0){
		$.each(options,function(i,oneOption){
			thName = oneOption.name;
			width = oneOption.width!=undefined?oneOption.width:50;
			height= oneOption.height!=undefined?oneOption.height:18;
			theme = oneOption.theme!=undefined?oneOption.theme:'lease';
			onText = oneOption.onText!=undefined?oneOption.onText:'是';
			offText = oneOption.offText!=undefined?oneOption.offText:'否';
			onVal = oneOption.onVal!=undefined?oneOption.onVal:'1';
			offVal = oneOption.offVal!=undefined?oneOption.offVal:'0';
			callback = oneOption.callback;
			setTableRcswitcher();
		});
	}else{
		thName = options.name;
		width = options.width!=undefined?options.width:50;
		height= options.height!=undefined?options.height:18;
		theme = options.theme!=undefined?options.theme:'lease';
		onText = options.onText!=undefined?options.onText:'是';
		offText = options.offText!=undefined?options.offText:'否';
		onVal = options.onVal!=undefined?options.onVal:'1';
		offVal = options.offVal!=undefined?options.offVal:'0';
		callback = options.callback;
		setTableRcswitcher();
	}
	
	
	
	
	
}

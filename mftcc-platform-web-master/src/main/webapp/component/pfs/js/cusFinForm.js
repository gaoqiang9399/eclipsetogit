 function func_getById(obj ,url){
	 tr = $(obj).parents("tr");
	$(tr).parents("table").find(".selected").removeClass("selected");
	$(tr).addClass("selected");
	c =	new RightForm(
		{actionUrl:url,formUrl:webPath+"/cusFinForm/updateAjax",title:"财务指标公式"
		,btns:[{value:"保存",type:"button",onClick:"ajaxTrUpdate(this,reload)","data-elem" : tr}],
		beforeCallback:initCalculate}
	);
}
//初始化计算
function initCalculate(data){
	var modelList = data.jsonData.modelList;
	var $trHtml = $('<tr class="calculate"><td></td><td style="vertical-align:top;"></td></tr>');
	if(modelList!=undefined){
		var $select = $('<select id="model1" size="7"></select>');
		$.each(modelList,function(index,obj){
			var $option = $('<option></option>');
			$option.val(obj.codeColumn);
			$option.text(obj.codeName);
			$select.append($option);
		});
		$trHtml.find("td").first().append($select);
		var $calculateDiv  =  $('<div class="calculateCtrl"></div>');
		$calculateDiv.append('<span class="cBtn">+</span>');
		$calculateDiv.append('<span class="cBtn">-</span>');
		$calculateDiv.append('<span class="cBtn">*</span>');
		$calculateDiv.append('<span class="cBtn">/</span>');
		$calculateDiv.append('<span class="cBtn">(</span>');
		$calculateDiv.append('<span class="cBtn">)</span>');
		$calculateDiv.append('<span class="cBtn">←</span>');
		$calculateDiv.append('<span class="cBtn clear" style="font-size: 12px;">清空</span>');
		
		$calculateDiv.find(".cBtn").click(function(){
	  		var formFormula = $("textarea[name='formFormula']");
	  		//var formName = $("textarea[name='formName']");
	  		var text = $(this).text();
	  		if(text=="←"){
	  			var vals = formFormula.val();
	  			//var texts = formName.val();
	  			var tempVals,tempTexts;
	  			$(".calculate select option").each(function(index){
	  				//var optionText = $(this).text();
	  				var optionVal = $(this).val();
	  				//if(optionText==texts.substr(-optionText.length,optionText.length)&&optionVal==vals.substr(-optionVal.length,optionVal.length)){
	  				if(optionVal==vals.substr(-optionVal.length,optionVal.length)){
	  					tempVals = vals.substring(0,vals.length-optionVal.length);
	  					//tempTexts = texts.substring(0,texts.length-optionText.length);
	  					return false;
	  				}
	  			});
	  			//if(tempVals!==undefined&&tempTexts!==undefined){
	  			if(tempVals!==undefined){
	  				formFormula.val(tempVals);
	  				//formName.val(tempTexts);
	  			}else{
	  				formFormula.val(vals.substring(0,vals.length-1));
	  				//formName.val(texts.substring(0,texts.length-1));
	  			}
	  		}else if($(this).hasClass("clear")){
	  			formFormula.val("");
	  			//formName.val("");
	  		}else{
	  			formFormula.val(formFormula.val()+$(this).text());
	  			//formName.val(formName.val()+$(this).text());
	  		}
	  		
	  	});
	  	$select.find("option").dblclick(function(){
	  		var formFormula = $("textarea[name='formFormula']");
	  		//var formName = $("textarea[name='formName']");
	  		formFormula.val(formFormula.val()+$(this).val());
	  		//formName.val(formName.val()+$(this).text());
	  	});
		$trHtml.find("td").last().append($calculateDiv);
	}
	$(".rightForm-body").find("table").find("tr").last().after($trHtml);
}
function reload(){
	c.close();
	$("table").tableRcswitcher({
    	name:"useFlag"});
	$(tr).parents("table").find(".selected").removeClass("selected");
}
//新增
function func_input(url){
	c =	new RightForm(
		{actionUrl:url,formUrl:webPath+"/cusFinForm/toInsertAjax",title:"财务指标公式",btns:[{value:"继续",type:"button",onClick:"ajaxInput(this.form,ajaxInsertCallback)"}]}
	);
}
function ajaxInsertCallback(data){
	c =	new RightForm(
		{formFlag:true,dataForm:data,formUrl:webPath+"/cusFinForm/insertAjax",title:"财务指标公式"
		,btns:[{value:"保存",type:"button",onClick:"ajaxInsert(this.form,reload)"}],
		beforeCallback:initCalculate}
	);
}

function ajaxInput(obj,callback){
	var flag = submitJsMethod(obj, '');
	if(flag){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray()); 
		jQuery.ajax({
			url:url,
			data:{ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					 if(typeof(callback)=="function"){
					 	callback.call(this,data);
					 	$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
					 }
				}
			},error:function(data){
				 $.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
			}
		});
	}
}
function ajaxInsert(obj,callback){
	var flag = submitJsMethod(obj, '');
	if(flag){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray()); 
		jQuery.ajax({
			url:url,
			data:{ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					 $.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
					 if(typeof(callback)=="function"){
					 	callback.call(this);
					 }
					 myCustomScrollbar({
				    	obj:"#content",//页面内容绑定的id
				    	url:webPath+"/cusFinForm/findByPageAjax",//列表数据查询的url
				    	tableId:"tablepfs2001",//列表数据查询的table编号
				    	tableType:"thirdTableTag",//table所需解析标签的种类
				    	myFilter:false, //是否有我的筛选(列表列动态切换)
				    	pageSize:30,//加载默认行数(不填为系统默认行数)
				    	data:{},//指定参数
				    	callback:function(){
				    	}//方法执行完回调函数（取完数据做处理的时候）
				    });
				}
			},error:function(data){
				 $.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
			}
		});
	}
}

function ajaxTrUpdate(obj,callback){
	var contentForm = $(obj).parents("#rightFormInfo");
	var tr = $(obj).data("elem");
	var table = tr.parents("table");
	var tableId = table.attr("title");
	var flag = submitJsMethod(contentForm[0], '');
	if(flag){
		var ajaxUrl = contentForm.attr("action");
		var dataParam = JSON.stringify(contentForm.serializeArray()); 
		var alertFlag = window.parent.window.$.myAlert;
		jQuery.ajax({
			url:ajaxUrl,
			data:{ajaxData:dataParam,tableId:tableId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					if(alertFlag){
						 window.parent.window.$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
					}else{
						$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
					}
					 if(data.tableData!=undefined&&data.tableData!=null){
						var trHtml = $(data.tableData).find("tbody tr").html();
						tr.html(trHtml);
					 }
					 if(typeof(callback)=="function"){
					 	callback.call(this);
					 }
				}else if(data.flag=="error"){
					if(alertFlag){
						 window.parent.window.$.myAlert.Alert(data.msg);
					}else{
						$.myAlert.Alert(data.msg);
					}
				}
			},error:function(data){
				if(alertFlag){
					 window.parent.window.$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				}else{
					$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
				}
			}
		});
	}
}
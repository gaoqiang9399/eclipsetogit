;
var MfExamineTemplateConfig=function(window, $){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		$('input[name=cusType]').popupSelection({
			searchOn: false, //启用搜索
			inline: false, //下拉模式
			multiple: true, //单选
			title:"客户类型",
			labelShow:false,
			items:cusTypes
		});
		$('input[name=repayType]').popupSelection({
			searchOn: false, //启用搜索
			inline: false, //下拉模式
			multiple: true, //单选
			title:"还款方式",
			labelShow: false,
			items:repayTypes
		});
		$('input[name=kindNo]').popupSelection({
			searchOn: true, //启用搜索
			inline: false, //下拉模式
			multiple: true, //单选
			title:"产品种类",
			items:kindNos
		});
		$("input[type=checkbox]").parent(".input-group").find("br").remove();
		_addKindOptions();
		_busModelChange();
		_templateTypeInit();
		_kindChange();
		_ajaxInsertThis("#ecamTempCongInsertForm");
		_ajaxUpdateThis("#ecamTempCongInsertForm");
		_riskWarmModelNoChange();
		_templateTypeChange();
		_close();
	};
	//业务模式添加change,业务模式变化是动态改变产品种类下拉框数据
	var _busModelChange = function(){
		$("select[name=busModel]").bind("change",function(event){
			_addKindOptions();
		});
	};
	//动态添加产品种类
	var _addKindOptions = function(){
		var busModel = $("select[name=busModel]").val();
		$.ajax({
			url : webPath+"/mfSysKind/getByBusModelAjax?busModel="
					+ busModel,
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.flag == "success") {
					var optionHtml = "";
					kindList = data.kindList;
					$.each(data.kindList, function(i, kind) {
						if(kindNo==kind.kindNo){
							optionHtml = optionHtml + "<option value='" + kind.kindNo
							+ "' selected>" + kind.kindName + "</option>";
						}else{
							optionHtml = optionHtml + "<option value='" + kind.kindNo
							+ "'>" + kind.kindName + "</option>";
						}
					});
					$("select[name=kindNo]").html(optionHtml);
					_kindChange();
				} else {
					alert("查询产品信息错误", 0);
				}
			},
			error : function() {
				alert("查询产品信息错误", 0);
			}
		});
	}
	//打开页面时，根据当前检查模板，操作风险模型元素的显隐
	var _templateTypeInit =function(){
		var templateType = $("select[name=templateType]").val();
		if(templateType=="1"){
			$("select[name=riskWarmModelNo]").parents("tr").hide();
		}else{
			_addRiskWarmModelOptions();
			$("select[name=riskWarmModelNo]").parents("tr").show();
		}
	};
	//检查模板变化时，动态添加风险模型下拉框数据
	var _templateTypeChange = function(){
		$("select[name=templateType]").bind("change",function(event){
			var templateType = $("select[name=templateType]").val();
			if(templateType=="1"){
				$("select[name=riskWarmModelNo]").parents("tr").hide();
			}else{
				_addRiskWarmModelOptions();
				$("select[name=riskWarmModelNo]").parents("tr").show();
			}
		});
	};
	//添加风险模型下拉框数据
	var  _addRiskWarmModelOptions= function(){
		$.ajax({
			url : webPath+"/mfExamRiskConfig/getExamRiskConfigAjax",
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.flag == "success") {
					var optionHtml = "";
					riskConfigList = data.riskConfigList;
					$.each(riskConfigList, function(i, riskConfig) {
						if(riskConfig.modelId==riskWarmModelNo){
							optionHtml = optionHtml + "<option value='" + riskConfig.modelId
							+ "' selected='selected'>" + riskConfig.modelName + "</option>";
						}else{
							optionHtml = optionHtml + "<option value='" + riskConfig.modelId
							+ "'>" + riskConfig.modelName + "</option>";
						}
					});
					$("select[name=riskWarmModelNo]").html(optionHtml);
					_riskWarmModelNoChange();
				}
			},
			error : function() {
				alert("查询风险模型错误", 0);
			}
		});
		$("select[name=riskWarmModelNo]").parent().parent().show();
	
	};
	//风险模型变化时，给风险模型名称赋值
	var _riskWarmModelNoChange =function(){
		$("select[name=riskWarmModelNo]").bind("change",function(){
			riskWarmModelNo = $("select[name=riskWarmModelNo]").val();
			$.each(riskConfigList,function(i,riskConfig){
				if(riskConfig.modelId==riskWarmModelNo){
					$("input[name=riskWarnModel]").val(riskConfig.modelName);
				}
			});
		});
	};
	//新增保存
	var _ajaxInsertThis = function(obj){
		$(".ajaxInsert").bind("click",function(event){
			var flag = submitJsMethod($(obj).get(0), '');
			if(flag){
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							LoadingAnimate.stop();
							if(data.flag == "success"){
								top.examTempConfig=data.examTempConfig;
								top.flag=true;
								window.top.alert(data.msg, 1);
								_myclose();
							}else{
								 window.top.alert(data.msg,0);
							}
						},error:function(data){
							LoadingAnimate.stop();
							 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				
				}
		});
	};
	//删除
	var _ajaxDeleteThis = function(){
		alert(top.getMessage("CONFIRM_OPERATION","删除"),2,function(){
			LoadingAnimate.start();
			jQuery.ajax({
				url:webPath+"/mfExamineTemplateConfig/deleteAjax",
				data:{templateId:templateId},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						top.deleteFlag=true;
						window.top.alert(data.msg, 1);
						_myclose();
					}else{
						window.top.alert(data.msg,0);
					}
				},error:function(data){
					LoadingAnimate.stop();
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		});
	};
	//编辑保存
	var _ajaxUpdateThis = function(obj){
		$(".ajaxUpdate").bind("click",function(){
			var flag = submitJsMethod($(obj).get(0), '');
			if(flag){
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							LoadingAnimate.stop();
							if(data.flag == "success"){
								window.top.alert(data.msg, 1);
								top.templateName=data.configData.templateName;
								top.remark=data.configData.remark;
								top.flag=true;
								_myclose();
							}
						},error:function(data){
							LoadingAnimate.stop();
							 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				
				}
		});
	};
	//产品种类变化时，给产品种类名称赋值
	var _kindChange = function(){
		var kindNo = $("select[name=kindNo]").val();
		$.each(kindList, function(i, kind) {
			if(kindNo==kind.kindNo){
				$("input[name=kindName]").val(kind.kindName);
			}
		});
	};
	//给关闭按钮绑定关闭事件
	var _close = function(){
		$(".cancel").bind("click",function(){
			_myclose();
		});
	};
	var _myclose=function(){
		myclose_click();
	};
	return{
		init:_init,
		ajaxDeleteThis:_ajaxDeleteThis
	};
}(window, jQuery);
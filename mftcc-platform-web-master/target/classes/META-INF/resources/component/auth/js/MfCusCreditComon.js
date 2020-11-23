//选择客户类型
 function selectCusType(){
	 selectCusTypeMutiDialog(callBackCusTypeMutiDialog, $("input[name=cusTypeNo]").val());
 }
 
//选择客户类型回调函数
function callBackCusTypeMutiDialog(cusType){
	 $("input[name=cusType]").val("");
	 $("input[name=cusTypeNo]").val("");
	 $("input[name=cusType]").val(cusType.cusTypeDes);
	 $("input[name=cusTypeNo]").val(cusType.cusTypeNo);
}

//选择业务流程回调函数
function getProcessNo(fieldName){
	return $("input[name='"+fieldName+"']").val();
}
function setProcessNo(key,fieldName){
	$("input[name='"+fieldName+"']").val(key);
}
var mfCusCreditComon = function(window,$){
	var _init = function(){
		_queryCreditDynForm();
		_queryModelList();
	};
	
	//查询授信申请动态表单列表
	var _queryCreditDynForm = function(){
		$.ajax({
			url:webPath+"/mfCusCreditModel/getCreditDynFormAjax",
			type:"post",	
			dataType:"json",
			success:function(data){
				if(data.flag == "success"){
					var optionStr = "";
					$.each(data.creditDynFormList,function(i,creditDynForm){
						if(creditFormId == creditDynForm.formNo){
							optionStr += "<option value='" + creditDynForm.formNo +"' selected>" + creditDynForm.formName + "</option>";
						}else{
							optionStr += "<option value='" + creditDynForm.formNo +"'>" + creditDynForm.formName + "</option>";
						}
					});
					$("select[name=creditFormName]").html(optionStr);
				}else{
					alert(data.msg,0);
				}
			},
			error:function(){
				alert("请求查询授信申请动态表单请求异常",0);
			}
			
		});
	};
	
	//查询模板列表
	var _queryModelList = function(){
		$.ajax({
			url:webPath+"/mfCusCreditModel/getModelListAjax",
			type:"post",
			dataType:"json",
			success:function(data){
				if(data.flag == "success"){
					var reportOptionStr = "";
					var protocolOptionStr = "";
					$.each(data.modelList,function(i,model){
						if(reportTemplateId == model.templateNo){
							reportOptionStr += "<option value='" + model.templateNo +"' selected>" + model.templateNameZh + "</option>";
						}else{
							reportOptionStr += "<option value='" + model.templateNo +"'>" + model.templateNameZh + "</option>";
						}
						if(protocolTemplateId == model.templateNo){
							protocolOptionStr += "<option value='" + model.templateNo +"' selected>" + model.templateNameZh + "</option>";
						}else{
							protocolOptionStr += "<option value='" + model.templateNo +"'>" + model.templateNameZh + "</option>";
						}
					});
					$("select[name=reportTemplateName]").html(reportOptionStr);
					$("select[name=protocolTemplateName]").html(protocolOptionStr);
				}else{
					alert(data.msg,0);
				}
			},
			error:function(){
				alert("请求查询查询模板列表异常",0);
			}
			
		});
	};
	
	return{
		init:_init
	};
}(window,jQuery); 
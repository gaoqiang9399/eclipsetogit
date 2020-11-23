/**
 * 验证唯一性 
 * unVal:唯一值
 * unValtitle：对应页面中字段名
 * relationId：唯一值关联编号 客户号或是列表中流水号
 * tabName：唯一值录入对应表单（暂时系统没有去判断指定表，验证所有系统下的值）
 * unValType：唯一值类型 （01证件号20手机号）
 * saveType：保单保存类型 新增保存/编辑保存
 * cusNoExclude：客户号（如果传入值则表示排除当前客户下存在的该值）
 * 
 */
window.checkUniqueVal = function(unVal,unValtitle,relationId,tabName,unValType,saveType,cusNoExclude){
	var result = "";
	var	relationIdTmp = "";
	var flag = "1";//验证通过与否标识 0通过 1不通过
	if(saveType == "update"){
		relationIdTmp = relationId;
	}else if(saveType == "insert"){
		relationIdTmp = "";
	}
	$.ajax({
		url:webPath+"/mfUniqueVal/doCheckUniqueAjax",
		data:{unVal:unVal,unType:unValType,tabName:tabName,relationId:relationIdTmp,saveType:saveType,cusNoExclude:cusNoExclude},
		type:'post',
		dataType:'json',
		async:false,
		success:function(data){
			if(data.flag == "1"){
				result=top.getMessage("ERROR_INFO_REPEAT", {"unValtitle":unValtitle , "unVal": unVal,"msg":data.msg})+"请确认当前登记的"+unValtitle+"是否正确";
				//result = "您登记的 "+unValtitle+unVal+" 与 以下信息重复：<br>"+data.msg;
			}else{
				flag = "0";
			}
		},error:function(){
			 window.top.alert(top.getMessage("ERROR_REQUEST_URL"),0);
		}
	});
	return flag+"&"+result;
};

//身份证唯一性验证
window.doCheckUniqueIdNum = function(unVal){
	var result = "";
	var flag = "1";//验证通过与否标识 0通过 1不通过
	$.ajax({
		url:webPath+"/mfUniqueVal/doCheckUniqueIdNumAjax",
		data:{unVal:unVal},
		type:'post',
		dataType:'json',
		async:false,
		success:function(data){
			if(data.flag == "1"){
				result=top.getMessage("ERROR_INFO_REPEAT", {"unValtitle":"身份证号码","unVal": unVal,"msg":data.msg})+" 请确认当前登记的号码是否正确!";
			}else{
				flag = "0";
			}
		},error:function(){
			 window.top.alert(top.getMessage("ERROR_REQUEST_URL"),0);
		}
	});
	return flag+"&"+result;
};


/**
 * 验证唯一性 
 * unVal:唯一值
 * relationId：唯一值关联编号 客户号或是列表中流水号
 * tabName：唯一值录入对应表单（暂时系统没有去判断指定表，验证所有系统下的值）
 * unValType：唯一值类型 （01证件号20手机号）
 * saveType：保单保存类型 新增保存/编辑保存
 * 
 */
window.checkUniqueValCusName = function(unVal,unValType){
	var result = "";
	var	relationIdTmp = "";
	var flag = "1";//验证通过与否标识 0通过 1不通过
	$.ajax({
		url:webPath+"/mfUniqueVal/doCheckUniqueCusNameAjax",
		data:{unVal:unVal,unType:unValType},
		type:'post',
		dataType:'json',
		async:false,
		success:function(data){
			if(data.flag == "1"){
				result=top.getMessage("ERROR_INFO_REPEAT", {"unValtitle":"客户名称","unVal": unVal,"msg":data.msg})+" 请确认当前客户名称录入是否正确!";
			}else{
				flag = "0";
			}
		},error:function(){
			 window.top.alert(top.getMessage("ERROR_REQUEST_URL"),0);
		}
	});
	return flag+"&"+result;
};


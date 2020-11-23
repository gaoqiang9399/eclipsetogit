function init(){
//	$(".scroll-content").mCustomScrollbar({
//		advanced:{
//			theme:"minimal-dark",
//			updateOnContentResize:true
//		}
//	});
	myCustomScrollbarForForm({
		obj:".scroll-content",
		advanced : {
			theme : "minimal-dark",
			updateOnContentResize : true
		}
	});
	examTypeChange();
	$("input[name=examDate]").parents("tr").hide();
	//patternChange();
}
//新增保存
function ajaxInsertThis(obj){
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
						var url=webPath+"/mfExamRemindConfig/getListPage";
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
						myclose_click();
					}
				},error:function(data){
					LoadingAnimate.stop();
					 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		
		}
}
//编辑保存
function ajaxUpdateThis(obj){
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
						var url=webPath+"/mfExamineTemplateConfig/getListPage";
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
						myclose_click();
					}
				},error:function(data){
					LoadingAnimate.stop();
					 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		
		}
}
//业务模式变化时，改变产品种类数据源
function busModelChange() {
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
					optionHtml = optionHtml + "<option value='" + kind.kindNo
							+ "'>" + kind.kindName + "</option>";
				});
				$("select[name=kindNo]").html(optionHtml);
				kindChange();
			} else {
				alert("查询产品信息错误", 0);
			}
		},
		error : function() {
			alert("查询产品信息错误", 0);
		}
	});
};
/**
 * 检查类型变化
 */
function examTypeChange(){
	var examType = $("select[name=examType]").val();
	if(examType=="1"){//一次性
		$("input[name=aheadWarnTime]").parents("tr").show();
		$("select[name=pattern]").parents("tr").hide();
		$("input[name=laonAfterTime]").parent().prev().html("放款后天数");
		$("input[name=laonAfterTime]").parents("tr").show();
		$("select[name=examDate]").parents("tr").hide();
	}
	if(examType=="2"){//周期
		$("input[name=aheadWarnTime]").parents("tr").show();
		$("select[name=pattern]").parents("tr").show();
		$("input[name=laonAfterTime]").parents("tr").hide();
		patternChange();
	}
	if(examType=="3"){//专项
		$("input[name=aheadWarnTime]").parents("tr").hide();
		$("select[name=pattern]").parents("tr").hide();
		$("input[name=laonAfterTime]").parents("tr").hide();
		$("select[name=examDate]").parents("tr").hide();
	}
}

function patternChange(){
	var pattern = $("select[name=pattern]").val();
	$("input[name=examDate]").parents("tr").hide();
	$("input[name=laonAfterTime]").parents("tr").hide();
	if(pattern=="3"){
		$("input[name=laonAfterTime]").parent().prev().html("指定天数");
		$("input[name=laonAfterTime]").parents("tr").show();
		$("select[name=examDate]").parents("tr").hide();
	}
	if(pattern=="1"){
		$("input[name=laonAfterTime]").parents("tr").hide();
		$("select[name=examDate]").parents("tr").show()
	}
	if(pattern=="2"){
		$("input[name=laonAfterTime]").parents("tr").hide();
		$("select[name=examDate]").parents("tr").hide();
	}
}
//给产品种类名称赋值
function kindChange(){
	var kindNo = $("select[name=kindNo]").val();
	$.each(kindList, function(i, kind) {
		if(kindNo==kind.kindNo){
			$("input[name=kindName]").val(kind.kindName);
		}
	});
}
//模板类型,选择表单模板，查询风险预警模型数据；当选择文档模板时隐藏预警预警模型
function templateTypeChange(){
	var templateType = $("select[name=templateType]").val();
	if(templateType=="1"){
		$("select[name=riskWarmModelNo]").parents("tr").hide();
	}else{
		$.ajax({
			url : webPath+"/mfSysKind/getByBusModelAjax",
			type : "POST",
			dataType : "json",
			success : function(data) {
				if (data.flag == "success") {
					var optionHtml = "";
					$.each(data.kindList, function(i, kind) {
						optionHtml = optionHtml + "<option value='" + kind.kindNo
						+ "'>" + kind.kindName + "</option>";
					});
					$("select[name=kindNo]").html(optionHtml);
				} else {
					alert("查询产品信息错误", 0);
				}
			},
			error : function() {
				alert("查询产品信息错误", 0);
			}
		});
		$("select[name=riskWarmModelNo]").parents("tr").show();
	}
}
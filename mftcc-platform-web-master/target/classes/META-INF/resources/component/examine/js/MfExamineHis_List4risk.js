;
var MfExamineHis_List4risk=function(window,$){
	var _addExam=function(obj,url){
		var ajaxUrl = webPath+"/"+url;
		ajaxUrl = ajaxUrl.replace(/ExamineApply4Risk/,"ExamineApply4RiskAjax");
		$.ajax({
			url:ajaxUrl,
			type:"GET",
			dataType:"json",
			beforeSend:function(){  
			},
			success:function(data){
				if(data.flag="success"){
					if(data.getFlag!="detail"){
						top.window.openBigForm(webPath+"/"+url,"风险登记",function(){
							_refreshExamHis();
							_getExamineResultAjax();
						});
					}else{
						alert("此业务存在审批中的风险信息，请先等待审批完成后再次发起",0);
					}
				}else{
					alert("操作失败",0);
				}
			}
		})
	};
	return{
		addExam: _addExam
	}
}(window,jQuery);
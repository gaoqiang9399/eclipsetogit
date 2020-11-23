
var viewPointApprove = function(window, $) {
	var _init = function () {
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced:{
				updateOnContentResize:true
			}
		});
	};
	
	 //审批页面
	var _getApprovaPage = function(){
	 	$("#infoDiv").css("display","none"); 
	 	$("#approvalBtn").css("display","none"); 
	 	$("#approvalDiv").css("display","block"); 
	 	$("#submitBtn").css("display","block"); 
	 };
	 //返回详情页面
	 var _approvalBack = function(){
	 	$("#infoDiv").css("display","block"); 
	 	$("#approvalBtn").css("display","block"); 
	 	$("#approvalDiv").css("display","none"); 
	 	$("#submitBtn").css("display","none");
	 };

	var _doSubmit = function(obj){
		var opinionType = $("select[name=opinionType]").val();
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			commitProcess(webPath+"/mfLoanApply/updateLoanApproveCommonAjax?opinionType="+opinionType+"&appNo="+appId,obj,'applySP');
		}
	};
	var  _updatePactEndDate = function (){
		var beginDate =  $("input[name=beginDate]").val();
		var term = $("input[name=term]").val();
		var termType = $("select[name=termType]").val();
		var intTerm = parseInt(term);
        var d,str;
		if(1==termType){ //融资期限类型为月 
			d = new Date(beginDate);
			d.setMonth(d.getMonth()+intTerm);
			str = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
			$("input[name=endDate]").val(str);
		}else{ //融资期限类型为日 
			d = new Date(beginDate);
		 	d.setDate(d.getDate()+intTerm);
			str = d.getFullYear()+"-"+(d.getMonth()>=9?beginDate.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
			$("input[name=endDate]").val(str);
		}
	};
	return {
		init : _init,
		doSubmit :_doSubmit,
		getApprovaPage:_getApprovaPage,
		approvalBack:_approvalBack,
		updatePactEndDate :_updatePactEndDate,
	};
}(window, jQuery);
var MfFundPlan_fundPlanTable = function(window, $) {
	var _init = function(){
		var width = $(window).width();
		var height = $(window).height()-100;
		$('#tableDiv').css('width',width+'px').css('height',height+'px');//调整列表的宽高
	};
	var _search = function(){
		var beginDate = $("input[name='beginDate']").val();
		var endDate = $("input[name='endDate']").val();
		if(beginDate == ""){
			alert("开始日期不能为空",0);
			return false;
		}
		if(endDate == ""){
			alert("结束日期不能为空",0);
			return false;
		}
		if(beginDate > endDate){
			alert("开始日期不能大于结束日期",0);
			return false;
		}
		jQuery.ajax({
				url : webPath+"/mfFundPlan/getDaysAjax",
				data : {
					beginDate : beginDate,
					endDate : endDate
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if (data.flag == "success") {
						window.location.href = webPath+"/mfFundPlan/createFundPlanTable?beginDate="+beginDate+"&endDate="+endDate;
					}
					if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					alert(top.getMessage("FAILED_OPERATION", " "), 0);
				}
			});
		
	};
	var _exportExcel = function(){
		var beginDate = $("input[name='beginDate']").val();
		var endDate = $("input[name='endDate']").val();
		if(beginDate == ""){
			alert("开始日期不能为空",0);
			return false;
		}
		if(endDate == ""){
			alert("结束日期不能为空",0);
			return false;
		}
		if(beginDate > endDate){
			alert("开始日期不能大于结束日期",0);
			return false;
		}
		jQuery.ajax({
				url : webPath+"/mfFundPlan/exportExcelAjax",
				data : {
					beginDate : beginDate,
					endDate : endDate
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if (data.flag == "success") {
						window.top.location.href = webPath
						"/mfFundPlan/fileDownload?filePath="+encodeURIComponent(data.filePath);
					}
					if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					alert(top.getMessage("FAILED_OPERATION", " "), 0);
				}
			});
	};
	var _returnPrePage = function(){
		window.location.href = webPath+"/mfFundPlan/getListPage";
	};
	return {
		init:_init,
		search:_search,
		exportExcel:_exportExcel,
		returnPrePage:_returnPrePage
	};
}(window, jQuery);
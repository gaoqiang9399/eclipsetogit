var yue = function(window,$){
	var _init=function(){
		$('#opno').css("display","none");
		$('#opname').click(function(){
			selectUserDialog(getCusMngNameDialog);
		});
		$('#query').click(function(){
			submit_onclick();
		});
		$('#reset').click(function(){
			submit_reset();
		});
	};
	 var submit_onclick = function(){
			var opNo = $("#opno").val();
			var flagA = '0';
			if(opNo!=null&&opNo!=""){
				flagA = '1';
			}
			top.createShowDialog("/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=6cd0ac6bf19de84a5bc1b865e520a3a2&opno="+opNo+"&flagA="+flagA,"余额汇总");
		}; 
	var submit_reset = function(){
			$('#opname').val("");
			$('#opno').val("");
		}; 
	function getCusMngNameDialog(userInfo){
			$("input[name=opname]").val(userInfo.opName);
			$("input[name=opno]").val(userInfo.opNo);
	};
	return{
		init:_init
	}
}(window,jQuery);
 
	




var cusDistribute = function(window,$){
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
			var way = $("#chartType").val();
			switch(way){
				case "wayclass" :
					top.createShowDialog("/report_new/report/rbc/designPreviewMain_new.jsp?reporttype=C&uid=55335b09763a7f6e0318f7d6b4e254a2&opno="+opNo,"客户分布");
				break;
				case "custype" :
					top.createShowDialog("/report_new/report/rbc/designPreviewMain_new.jsp?reporttype=C&uid=3d1c57b9496949750958553cb3e67a76&opno="+opNo,"客户分布");
				break;
				case "manage" :
					top.createShowDialog("/report_new/report/rbc/designPreviewMain_new.jsp?reporttype=C&uid=4b8ceb81800033d228e7e5d1e664bd01&opno="+opNo,"客户分布");
				break;
				default :
				break;
			}
		}; 
	var submit_reset = function(){
			$('#opname').val("");
			$('#opno').val("");
			$('#chartType').val("wayclass");
		}; 
	function getCusMngNameDialog(userInfo){
			$("input[name=opname]").val(userInfo.opName);
			$("input[name=opno]").val(userInfo.opNo);
	};
	return{
		init:_init
	}
}(window,jQuery);
 
	




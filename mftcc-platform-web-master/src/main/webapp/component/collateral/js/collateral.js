var Collateral = function(window,$){
	var _editCollateral = function(entrance){
		$.ajax({
			url:webPath+"/mfBusCollateralRel/getCollateralInfoAjax",
			data:{appId:appId,cusNo:cusNo,entrance:entrance},
			type:"POST",
			dataType:"json",
			success:function(data) {
				if (data.flag == "success") {
					if(entrance=="pactDeatil"){
						top.LoadingAnimate.start();
						window.location.href=webPath+"/mfBusCollateralRel/getCollateralInfo?cusNo="+cusNo+"&relId="+appId+"&appId="+appId+"&busCollateralId="+data.collateralRel.busCollateralId+"&entrance=business";
					}else if(entrance=="bussFlow"){
						if(data.instockflag=="1"){//押品全部入库，提醒业务提交下一个业务节点
							alert(data.msg,2);
							getNextBusPoint();//刷新显示下个节点
						}else if(data.instockflag=="0"){//存在未入库押品，跳转到押品详情
							top.LoadingAnimate.start();
							window.location.href=webPath+"/mfBusCollateralRel/getCollateralInfo?cusNo="+cusNo+"&relId="+appId+"&appId="+appId+"&busCollateralId="+data.collateralRel.busCollateralId+"&entrance=business";
						}
					}
				}else{
					alert(top.getMessage("NO_COLLATERAL"),0);
				}
			},
			error:function() {

			}
		});
	};
	return{
		editCollateral:_editCollateral,
	};
}(window,jQuery);
;
var MfMoveableCommon=function(window,$){
	var _init=function(){
	};
	//巡库登记新增
	var _patrolInventoryApplyInput=function(){
		top.window.openBigForm(webPath+'/mfMoveablePatrolInventory/input?busPleId='+busCollateralId+"&appId="+appId+"&cusNo="+cusNo,"巡库登记",function(){
		});
	};
	//移库申请新增
	var _transferApplyInput=function(){
	    // debugger;
		top.flag=false;
		top.window.openBigForm(webPath+'/mfMoveableTransferApply/input?busPleId='+busCollateralId+"&appId="+appId,"移库申请",function(){
			if(top.flag){
				MfBusCollateralRelDetail.controlOperateForMoveable();
			}
		});
	};
	//提货申请新增
	var _aheadDeliveryApplyInput=function(){
		top.flag=false;
		top.window.openBigForm(webPath+'/mfMoveableClaimGoodsApply/input?busPleId='+busCollateralId+"&appId="+appId+"&cusNo="+cusNo,"提前出库申请",function(){
			if(top.flag){
				MfBusCollateralRelDetail.controlOperateForMoveable();
			}
		});
	};
	//提前出库申请新增
	var _claimGoodsApplyInput=function(){
		top.flag=false;
		top.window.openBigForm(webPath+'/mfMoveableClaimGoodsApply/input?busPleId='+busCollateralId+"&appId="+appId+"&cusNo="+cusNo,"提前出库申请",function(){
			if(top.flag){
				MfBusCollateralRelDetail.controlOperateForMoveable();
			}
		});
	};
	//提货确认
	var _claimGoodsAffirm=function(){
		top.flag=false;
		top.window.openBigForm(webPath+'/mfMoveableClaimGoodsAffirm/input?busPleId='+busCollateralId+"&appId="+appId+"&cusNo="+cusNo,"提货确认",function(){
			if(top.flag){
				MfBusCollateralRelDetail.controlOperateForMoveable();
			}
		});
	};
	//核库信息
	var _getCheckInventoryInfo=function(){
		top.window.openBigForm(webPath+'/mfMoveableCheckInventoryInfo/getById?busPleId='+busCollateralId+"&appId="+appId+"&cusNo="+cusNo,"核库信息",function(){
		});
	};
	//最低监管价值调整
	var _lowestWorthAdjust=function(pledgeNo){
		top.window.openBigForm(webPath+'/mfMoveableLowestWorthAdjust/input?busPleId='+busCollateralId+"&pledgeNo="+pledgeNo,"最低监管价值调整",function(){
		});
	};
	//押品对账
	var _accountCheckInput=function(){
		top.window.openBigForm(webPath+'/mfMoveableAccountCheckInfo/getListPage?busPleId='+busCollateralId+"&appId="+appId+"&cusNo="+cusNo,"押品对账",function(){
		});
	}
	//调价历史
	var _getModifyHistory=function(){
		top.window.openBigForm(webPath+'/mfMoveableModifyApply/getListPage?busPleId='+busCollateralId+"&appId="+appId,"价格变动",function(){
		});
	};
	//调价
	var _moveableModify=function(){
		top.flag=false;
		top.window.openBigForm(webPath+'/mfMoveableModifyApply/input?busPleId='+busCollateralId+"&appId="+appId,"调价申请",function(){
			if(top.flag){
				MfBusCollateralRelDetail.controlOperateForMoveable();
			}
		});
	};
	//跌价补偿
	var _moveableCompentstate=function(){
		top.flag=false;
		top.window.openBigForm(webPath+'/mfMoveableCompensation/input?busPleId='+busCollateralId+"&appId="+appId,"跌价补偿",function(){
			if(top.flag){
				MfBusCollateralRelDetail.controlOperateForMoveable();
			}
		});
	};
	//跌价补偿确认
	var _moveableCompentstateConfirm=function(){
		top.flag=false;
		top.window.openBigForm(webPath+'/mfMoveableCompensationConfirm/input?busPleId='+busCollateralId+"&appId="+appId,"跌价补偿确认",function(){
			if(top.flag){
				MfBusCollateralRelDetail.controlOperateForMoveable();
			}
		});
	};
	return{
		init:_init,
		patrolInventoryApplyInput:_patrolInventoryApplyInput,
		transferApplyInput:_transferApplyInput,
		claimGoodsAffirm:_claimGoodsAffirm,
		claimGoodsApplyInput:_claimGoodsApplyInput,
		getCheckInventoryInfo:_getCheckInventoryInfo,
		lowestWorthAdjust:_lowestWorthAdjust,
		accountCheckInput:_accountCheckInput,
		getModifyHistory:_getModifyHistory,
		moveableModify:_moveableModify,
		moveableCompentstate:_moveableCompentstate,
		moveableCompentstateConfirm:_moveableCompentstateConfirm,
		aheadDeliveryApplyInput:_aheadDeliveryApplyInput,
	};
}(window,jQuery);
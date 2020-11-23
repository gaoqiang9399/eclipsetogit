;
var MfMoveableClaimGoodsAffirm=function(window,$){
	var _init=function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		_getPledgeData();
        _refreshGoodsDetailList();
	};
	//初始化选择押品数据源
	var _getPledgeData=function(){
		jQuery.ajax({
			url : webPath+"/mfMoveableClaimGoodsApply/getPledgeDataBySelectedAjax?claimId="+claimId,
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("input[name=claimPledge]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,
						multiple:false,//单选
						items:data.items,
						changeCallback:_refreshGoodsDetailList
					});
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	var _refreshGoodsDetailList=function(){
		var pledgeNo=$("input[name=claimPledge]").val();
		jQuery.ajax({
			url : webPath+"/pledgeBaseInfoBill/getTableDataByPledgeNoAjax",
			type : "POST",
			dataType : "json",
			data:{tableId:"tabledlpledgebaseinfobill0006",pledgeNo:pledgeNo},
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("#goodsDetailList").html(data.tableData);
					$("#goodsDetailListdiv").show();
                    $('.table_content #tab').find("tr").find("input[name=pleCount]").each(function () {
                        $(this).bind("change",function(){
                            var trObj = $(this).parents("tr");
                            var ableOutValue = $("input[name=ableOutValue]").val().replace(/,/g,"");
                            var affirmClaimGoodsAmt=0.00;
                            var affirmGoodsNum=0;
                            var unitPrice = trObj.find(".unitPrice").html();
                            var count = trObj.find(".count").html();
                            var ableCount = trObj.find(".ableCount").html();
                            var pleCount = trObj.find("[name=pleCount]").val();
                            var pleCountValue = 0.00;
                            if (pleCount!=""&&pleCount>0){
                                if (parseInt(pleCount)>parseInt(ableCount)){
                                    window.top.alert("提货数量不能大于可提货数量",0);
                                    trObj.find("[name=pleCount]").val(0);
                                    return false;
                                }
                                var pledgeBills = [];
                                $('.table_content #tab').find("tr").each(function (i,obj) {
                                    var entity = {};
                                    var pledgeBillNo = $(obj).find("input[name=pledgeBillNo]").val();
                                    var pleCount = $(obj).find("input[name=pleCount]").val();
                                    entity.pleId = pledgeBillNo;
                                    entity.pleCount = parseInt(pleCount);
                                    pledgeBills.push(entity);
                                });
                                _calcPledgeValue(pledgeBills,trObj);
                            }
                        });
                    });
					/*$("th[name=pledgeBillNo]").html('<a href="javascript:void(0);" onclick="MfMoveableClaimGoodsAffirm.checkAllGoodsDetail()">全选</a>');
					$('.table_content #tab').find($('input[type=checkbox]')).each(function () {
						$(this).bind("click",function(){
							var val=this.value;
							var unitPriceStr=val.split("&")[1];
							var unitPrice=unitPriceStr.split("=")[1];
							var countStr=val.split("&")[2];
							var count=countStr.split("=")[1];
							var goodsNum=$("input[name=affirmGoodsNum]").val();
							var claimGoodsAmt=$("input[name=affirmClaimGoodsAmt]").val();
							if(goodsNum==""){
								goodsNum=0;
							}
							if(claimGoodsAmt==""){
								claimGoodsAmt=0;
							}
							if($(this).prop("checked")){
								goodsNum=parseInt(goodsNum)+parseInt(count)
								claimGoodsAmt=parseFloat(claimGoodsAmt)+parseFloat(unitPrice)*parseInt(count);
							}else{
								goodsNum=parseInt(goodsNum)-parseInt(count)
								claimGoodsAmt=parseFloat(claimGoodsAmt)-parseFloat(unitPrice)*parseInt(count);
							}
							$("input[name=affirmGoodsNum]").val(goodsNum);
							$("input[name=affirmClaimGoodsAmt]").val(claimGoodsAmt);
							$("input[name=affirmClaimGoodsBal]").val(affirmClaimGoodsBal-claimGoodsAmt);
						});
					});*/
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	}
	var _checkAllGoodsDetail=function(){
		var claimGoodsAmt = 0.00;
		var goodsNum = 0;
		$('.table_content #tab').find($('input[type=checkbox]')).each(function () {
			if($(this).prop("checked")){
				$(this).prop("checked",false);
			}else{
				$(this).prop("checked",true);
				var val=this.value;
				var unitPriceStr=val.split("&")[1];
				var unitPrice=unitPriceStr.split("=")[1];
				var countStr=val.split("&")[2];
				var count=countStr.split("=")[1];
				claimGoodsAmt=parseFloat(claimGoodsAmt)+parseFloat(unitPrice)*parseInt(count);
				goodsNum=parseInt(goodsNum)+parseInt(count);
			}
    	 });
		$("input[name=affirmClaimGoodsAmt]").val(claimGoodsAmt);
		$("input[name=affirmGoodsNum]").val(goodsNum);
		$("input[name=affirmClaimGoodsBal]").val(affirmClaimGoodsBal-claimGoodsAmt);
	};
	//选择押品时，回调给押品编号赋值，给货物明细选择域添加数据源
	var _chosePledge=function(){
		var claimPledge=$("input[name=claimPledge]").val();
		jQuery.ajax({
			url : webPath+"/mfBusCollateralDetailRel/getRelGoodsDeailByPledgeAjax",
			type : "POST",
			dataType : "json",
			data:{pleStr:claimPledge},
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					pledgeBillNoStr=data.pledgeBillNoStr;
					pledgeBillTtems=data.pledgeBillTtems;
					allRelPledgeBillList=data.allRelPledgeBillList;
					pledgeSumAmt=data.pledgeSumAmt;
					$("input[name=claimPledgeNo]").val(data.pleShowNoStr);
					$("input[name=affirmGoodsDetail]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,
						multiple:false,//单选
						items:pledgeBillTtems,
						changeCallback:_goodsDetailChange
					});
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	//选择货物明细时，更新提取货物总结和剩余押品价值
	var _goodsDetailChange=function(){
		var affirmGoodsDetail=$("input[name=affirmGoodsDetail]").val();
		var affirmGoodsNum=$("input[name=affirmGoodsNum]").val();
		var applyGoodsNum=$("input[name=applyGoodsNum]").val();
		if(applyGoodsNum<affirmGoodsNum){
			var affirmGoodsNumTitle=$("input[name=affirmGoodsNum]").attr("title");
			var applyGoodsNumTitle=$("input[name=applyGoodsNum]").attr("title");
			alert(top.getMessage("NOT_FORM_TIME",{"timeOne":affirmGoodsNumTitle,"timeTwo":applyGoodsNumTitle}),3);
			$("input[name=affirmGoodsNum]").val("");
			return;
		}
		var claimGoodsAmt=0.00;
		$.each(allRelPledgeBillList,function(i,obj){
			if(affirmGoodsDetail==obj.pledgeBillNo){
				$("input[name=inventoryNum]").val(obj.count);
				if(affirmGoodsNum!=""&&affirmGoodsDetail!=""){
					var unitPrice=parseFloat(obj.unitPrice);
					claimGoodsAmt=unitPrice*affirmGoodsNum;
					$("input[name=affirmClaimGoodsAmt]").val(claimGoodsAmt);
					$("input[name=affirmClaimGoodsBal]").val(parseFloat(pledgeSumAmt)-parseFloat(claimGoodsAmt));
				}
			}
		});
	};
	
	//点击货物明细初始化货物明细数据源
	var _clickGoodsDetail=function(){
		var claimType=$("select[name=claimType]").val();
		var claimPledge=$("input[name=claimPledge]").val();
		if(claimPledge!=""){
			$("input[name=goodsDetail]").popupSelection({
				searchOn:true,//启用搜索
				inline:true,
				multiple:false,//单选
				items:pledgeBillTtems,
				changeCallback:_chosePledge
			});
		}
	};
	//提货方式变化时，如果是全部提取，货物明细自动全选；如果是部分提货，选择需要提取的货物明细
	var _claimTypeChange=function(){
		var claimType=$("select[name=claimType]").val();
		var claimPledge=$("input[name=claimPledge]").val();
		if(claimPledge!=""){
			//部分
			if(claimType=="0"){
				
			}
			//全部
			if(claimType=="1"){
				$("input[name=goodsDetail]").val(pledgeBillNoStr);
				$("input[name=goodsDetail]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,
					multiple:true,//单选
					items:pledgeBillTtems,
				});
			}
		}
	};
	//保存方法
	var _ajaxClaimGoodsAffirmSave = function(formObj){
        var pledgeBills = [];
        $('.table_content #tab').find("tr").each(function () {
            var entity = {};
            var pledgeBillNo = $(this).find("input[name=pledgeBillNo]").val();
            var pleCount = $(this).find("input[name=pleCount]").val();
            entity.pleId = pledgeBillNo;
            entity.pleCount = parseInt(pleCount);
            pledgeBills.push(entity);
        });
        var affirmClaimGoodsAmt = $("input[name=affirmClaimGoodsAmt]").val().replace(/,/g,"");
        if (parseFloat(affirmClaimGoodsAmt)==0){
            window.top.alert("没有需要提取的货物！",0);
        }
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataForm,
					appId:appId,
                    pledgeBills:JSON.stringify(pledgeBills)
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					top.flag=true;
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.flag=true;
						window.top.alert(data.msg, 3);
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					top.loadingAnimate.stop();
					window.top.alert(data.msg, 0);
				}
			});
		}
	};

    //计算押品货值
    var _calcPledgeValue=function(pledgeBills,trObj){
        var ableOutValue = $("input[name=ableOutValue]").val().replace(/,/g,"");
        var affirmClaimGoodsAmt=0.00;
        var affirmGoodsNum=0;
        jQuery.ajax({
            url : webPath+"/pledgeBaseInfoBill/calcPledgeValueByBillAjax",
            data:{
                billListData:JSON.stringify(pledgeBills),
                appId:"null"
            },
            type : "POST",
            dataType : "json",
            async: false,
            beforeSend : function() {
            },
            success : function(data) {
                if (data.flag == "success") {
                    affirmClaimGoodsAmt = data.pleValue;
                    affirmGoodsNum = data.pleCount;
                    if (parseFloat(affirmClaimGoodsAmt)>parseFloat(ableOutValue)){
                        window.top.alert("提货总金额不能大于可自由出库货值金额",0);
                        trObj.find("[name=pleCount]").val(0);
                        return false;
                    }else {
                        $("input[name=affirmClaimGoodsAmt]").val(affirmClaimGoodsAmt);
                        $("input[name=affirmGoodsNum]").val(affirmGoodsNum);
                    }
                }
            },
            error : function(data) {
                LoadingAnimate.stop();
                window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
            }
        });
    };

	return{
		init:_init,
		ajaxClaimGoodsAffirmSave:_ajaxClaimGoodsAffirmSave,
		claimTypeChange:_claimTypeChange,
		clickGoodsDetail:_clickGoodsDetail,
		goodsDetailChange:_goodsDetailChange,
		checkAllGoodsDetail:_checkAllGoodsDetail
	};
}(window,jQuery);
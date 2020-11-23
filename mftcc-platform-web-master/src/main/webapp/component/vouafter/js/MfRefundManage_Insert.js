;
var MfRefundManage_Insert = function(window, $) {
	var _init = function() {
        $(".scroll-content").mCustomScrollbar({
            advanced : {
                // 滚动条根据内容实时变化
                updateOnContentResize : true
            }
        });
        // 实收担保费
		var actualReceivedGuaranteeAmt = $("[name='actualReceivedGuaranteeAmt']").val();
        $("[name='refundGuaranteeAmt']").attr("placeholder","应退担保费不能超过实收担保费" + actualReceivedGuaranteeAmt + "元。");
        // 实收手续费
		var actualReceivedHandAmt = $("[name='actualReceivedHandAmt']").val();
        $("[name='refundHandAmt']").attr("placeholder","应退手续费不能超过实收手续费" + actualReceivedHandAmt + "元。");
        // 实收保证金
		var actualReceivedBond = $("[name='actualReceivedBond']").val();
        $("[name='refundBond']").attr("placeholder","应退保证金不能超过实收保证金" + actualReceivedBond + "元。");
	};
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
            var refundGuaranteeAmt = parseFloat($("input[name='refundGuaranteeAmt']").val().replace(/,/g,''));
            var refundHandAmt = parseFloat($("input[name='refundHandAmt']").val().replace(/,/g,''));
            var refundBond = parseFloat($("input[name='refundBond']").val().replace(/,/g,''));
            if(refundGuaranteeAmt+refundHandAmt+refundBond==0){
                alert("应退金额为零", 0);
                return;
			}
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url : url,
				data : {
					ajaxData:dataParam
					},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						window.top.alert(data.msg, 3);
						myclose_click();
					} else {
						alert(data.msg, 0);
					}
				},
				error : function() {
				}
			});
		}
	};
	var _refundGuaranteeAmtChange = function(){
        var refundGuaranteeAmt = $("[name='refundGuaranteeAmt']").val();
        if(refundGuaranteeAmt == null && refundGuaranteeAmt == ""){
            refundGuaranteeAmt = 0.00;
		}else{
            refundGuaranteeAmt = refundGuaranteeAmt.replace(/,/g,"");
		}
        var actualReceivedGuaranteeAmt = $("[name='actualReceivedGuaranteeAmt']").val();
        if(actualReceivedGuaranteeAmt == null && actualReceivedGuaranteeAmt == ""){
            actualReceivedGuaranteeAmt = 0.00;
        }else{
            actualReceivedGuaranteeAmt = actualReceivedGuaranteeAmt.replace(/,/g,"");
		}
		if(CalcUtil.subtract(refundGuaranteeAmt,actualReceivedGuaranteeAmt) > 0){
            $("[name='refundGuaranteeAmt']").val("0.00");
            alert("应退担保费"+CalcUtil.formatMoney(refundGuaranteeAmt,2)+"元，不能超过实收担保费"+CalcUtil.formatMoney(actualReceivedGuaranteeAmt,2)+"元。", 0);
        }
	};
	var _refundHandAmtChange = function(){
        var refundHandAmt = $("[name='refundHandAmt']").val();
        if(refundHandAmt == null && refundHandAmt == ""){
            refundHandAmt = 0.00;
		}else{
            refundHandAmt = refundHandAmt.replace(/,/g,"");
		}
        var actualReceivedHandAmt = $("[name='actualReceivedHandAmt']").val();
        if(actualReceivedHandAmt == null && actualReceivedHandAmt == ""){
            actualReceivedHandAmt = 0.00;
        }else{
            actualReceivedHandAmt = actualReceivedHandAmt.replace(/,/g,"");
		}
		if(CalcUtil.subtract(refundHandAmt,actualReceivedHandAmt) > 0){
            $("[name='refundHandAmt']").val("0.00");
            alert("应退手续费"+CalcUtil.formatMoney(refundHandAmt,2)+"元，不能超过实收手续费"+CalcUtil.formatMoney(actualReceivedHandAmt,2)+"元。", 0);
        }
	};
	var _refundBondChange = function(){
        var refundBond = $("[name='refundBond']").val();
        if(refundBond == null && refundBond == ""){
            refundBond = 0.00;
		}else{
            refundBond = refundBond.replace(/,/g,"");
		}
        var actualReceivedBond = $("[name='actualReceivedBond']").val();
        if(actualReceivedBond == null && actualReceivedBond == ""){
            actualReceivedBond = 0.00;
        }else{
            actualReceivedBond = actualReceivedBond.replace(/,/g,"");
		}
		if(CalcUtil.subtract(refundBond,actualReceivedBond) > 0){
            $("[name='refundBond']").val("0.00");
            alert("应退保证金"+CalcUtil.formatMoney(refundBond,2)+"元，不能超过实收保证金"+CalcUtil.formatMoney(actualReceivedBond,2)+"元。", 0);
        }
	};


	return {
		init : _init,
		ajaxSave:_ajaxSave,
        refundGuaranteeAmtChange:_refundGuaranteeAmtChange,
        refundHandAmtChange:_refundHandAmtChange,
        refundBondChange:_refundBondChange,
	};
}(window, jQuery);

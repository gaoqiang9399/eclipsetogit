var flowAssetsInfo = function(window,$){
	var _changeFlowAssets = function(obj,url){
		/*var assetsType = $(obj).val();
		//资产类型（1现金及银行存款，2 应收帐款 3预付帐款 4存货）
		if("2" == assetsType){
			$("input[name='assetsName']").parents("tr").find("label").text("应收客户对象");
			$("input[name='nowPrice']").parents("tr").find("label").text("现值");
			$("input[name='accountAge']").parents("tr").show();
			$("input[name='accountMoney']").parents("tr").show();
			$("textarea[name='remark']").parents("tr").show();
			$("input[name='nowPrice']").parents("tr").show();
			$("select[name='accountType']").parents("tr").hide();
			$("input[name='prepaidDate']").parents("tr").hide();
			$("input[name='purchasePrice']").parents("tr").hide();
			$("input[name='accountNo']").parents("tr").hide();
		}else if("3" == assetsType){
			$("input[name='assetsName']").parents("tr").find("label").text("预付款对象");
			$("input[name='nowPrice']").parents("tr").find("label").text("金额");
			$("input[name='prepaidDate']").parents("tr").show();
			$("textarea[name='remark']").parents("tr").show();
			$("input[name='nowPrice']").parents("tr").show();
			$("select[name='accountType']").parents("tr").hide();
			$("input[name='purchasePrice']").parents("tr").hide();
			$("input[name='accountNo']").parents("tr").hide();
			$("input[name='accountMoney']").parents("tr").hide();
			$("input[name='accountAge']").parents("tr").hide();
		}else if("4" == assetsType){ 
			$("input[name='assetsName']").parents("tr").find("label").text("存货分类");
			$("input[name='nowPrice']").parents("tr").find("label").text("现价");
			$("input[name='purchasePrice']").parents("tr").show();
			$("textarea[name='remark']").parents("tr").show();
			$("input[name='nowPrice']").parents("tr").show();
			$("select[name='accountType']").parents("tr").hide();
			$("input[name='accountAge']").parents("tr").hide();
			$("input[name='accountNo']").parents("tr").hide();
			$("input[name='accountMoney']").parents("tr").hide();
			$("input[name='prepaidDate']").parents("tr").hide();
		}else{
			$("input[name='assetsName']").parents("tr").find("label").text("存款银行");
			$("input[name='nowPrice']").parents("tr").find("label").text("余额");
			$("select[name='accountType']").parents("tr").show();
			$("input[name='accountNo']").parents("tr").show();
			$("input[name='accountAge']").parents("tr").hide();
			$("input[name='prepaidDate']").parents("tr").hide();
			$("input[name='purchasePrice']").parents("tr").hide();
			$("input[name='accountMoney']").parents("tr").hide();
			$("textarea[name='remark']").parents("tr").hide();
		}*/		
		changeSubFormBySelect(obj,url);
	};
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		//_changeFlowAssets("select[name='accountType']");
		//账号绑定事件
		$("input[name='accountNo']").on('keyup input',function(){
	        var  accountNo =$(this).val();
	        var reg=/^-?[0-9,\s]*$/;//此写法允许首字符为0
			if(!reg.test(accountNo)){
				$(this).val("");
			}else{
				 if(/\S{5}/.test(accountNo)){
		            this.value = accountNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ");
				}
	       	}		       	
        });
	};
	var _saveCusPersonFlowAssetsInfo = function (obj){
		// debugger;
		
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			ajaxInsertCusForm(obj);
		}
	};
	var _saveCusPersonFlowAssetsInfoAndAdd = function (obj){
		var flag = submitJsMethod($(obj).get(0), '');
        var cusNo = $("input[name='cusNo']").val();
        var inputUrl = webPath+"/mfCusPersonFlowAssetsInfo/input?cusNo="+cusNo;
		if(flag){
            ajaxInserAndAddCusForm(obj,inputUrl);
		}
	};
	var _quantityChang = function () {
		//数量
		var quantity =$("input[name='quantity']").val();
		var purchasePrice =$("input[name='purchasePrice']").val();
		if(quantity!=undefined&&quantity!=''&&purchasePrice!=undefined&&purchasePrice!=''){
            quantity =  quantity.replace(/,/g,'');
            purchasePrice =  purchasePrice.replace(/,/g,'');
            var nowPrice =(quantity*1)*(purchasePrice*1);
            if (isNaN(nowPrice))
			{
                $("input[name='nowPrice']").val("0");//数量或单价输入非法字符金额归0
				return false;
			}
            var nowPrice1 = nowPrice.toFixed(2);//保留两位小数
            $("input[name='nowPrice']").val(nowPrice1);
		}
    }
	return {
		init : _init,
		changeFlowAssets : _changeFlowAssets,
		saveCusPersonFlowAssetsInfo : _saveCusPersonFlowAssetsInfo,
        quantityChang : _quantityChang,
        saveCusPersonFlowAssetsInfoAndAdd:_saveCusPersonFlowAssetsInfoAndAdd,
	};
}(window, jQuery);
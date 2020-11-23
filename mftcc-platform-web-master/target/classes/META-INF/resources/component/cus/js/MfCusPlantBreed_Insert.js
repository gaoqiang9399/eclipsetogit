;
var MfCusPlantBreed_Insert = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
		$(function(){
            myCustomScrollbarForForm({
                obj:".scroll-content",
                advanced : {
                    updateOnContentResize : true
                }
            });
		 });
	};
	var _changeType = function(){
        $("input[name='num']").val("");
        $("input[name='yield']").val("");
        $("input[name='price']").val("");
        $("input[name='income']").val("");
        $("input[name='seedPup']").val("");
        $("input[name='feiFeed']").val("");
        $("input[name='pesticideCorn']").val("");
        $("input[name='dripSoybean']").val("");
        $("input[name='laborCost']").val("");
        $("input[name='otherFee']").val("");
        $("input[name='totalCost']").val("");
        $("input[name='premisesRental']").val("");
        $("input[name='wages']").val("");
        $("input[name='waterElec']").val("");
        $("input[name='fixedExp']").val("");
        $("input[name='totalProfit']").val("");
    }
    var _calcAmt = function () {
    	//亩数/出栏数
     	var num = $("input[name='num']").val();
     	//亩产/单个重量
     	var yield = $("input[name='yield']").val();
     	//单价
     	var price = $("input[name='price']").val();
     	//收入
		var income=0.00;
		if(num!=null&&num!=''&&num!=undefined&&yield!=null&&yield!=undefined&&yield!=''&&price!=null&&price!=undefined&&price!=''){
            num = num.replace(",","");
            yield = yield.replace(",","");
            price =price.replace(",","");
            income = (num*1)*(yield*1)*(price*1);
            $("input[name='income']").val(income);

		}
        var profit =income;
		var totalCost = 0.00;
        //种子/幼畜
        var seedPup = $("input[name='seedPup']").val();
        if (seedPup!=null&&seedPup!=undefined&&seedPup!=''){
            seedPup =seedPup.replace(",","");
            totalCost = totalCost*1+seedPup*1;
        }
        //化肥/饲料
        var feiFeed = $("input[name='feiFeed']").val();
        if (feiFeed!=null&&feiFeed!=undefined&&feiFeed!=''){
            feiFeed =feiFeed.replace(",","");
            totalCost = totalCost*1+feiFeed*1;
        }
        //农药/玉米
        var pesticideCorn = $("input[name='pesticideCorn']").val();
        if (pesticideCorn!=null&&pesticideCorn!=undefined&&pesticideCorn!=''){
            pesticideCorn =pesticideCorn.replace(",","");
            totalCost = totalCost*1+pesticideCorn*1;
        }
        //滴灌费用/豆粕
        var dripSoybean = $("input[name='dripSoybean']").val();
        if (dripSoybean!=null&&dripSoybean!=undefined&&dripSoybean!=''){
            dripSoybean =dripSoybean.replace(",","");
            totalCost = totalCost*1+dripSoybean*1;
        }
        //劳务成本
        var laborCost = $("input[name='laborCost']").val();
        if (laborCost!=null&&laborCost!=undefined&&laborCost!=''){
            laborCost =laborCost.replace(",","");
            totalCost = totalCost*1+laborCost*1;
        }
        //其他费用
        var otherFee = $("input[name='otherFee']").val();
        if (otherFee!=null&&otherFee!=undefined&&otherFee!=''){
            otherFee =otherFee.replace(",","");
            totalCost = totalCost*1+otherFee*1;
        }
        if(totalCost!=''){
            $("input[name='totalCost']").val(totalCost);
            profit = profit*1-totalCost*1;
        }
        $("input[name='profit']").val(profit);
        //经营场地租金
        var otherTotal = 0.00;
        var premisesRental = $("input[name='premisesRental']").val();
        if (premisesRental!=null&&premisesRental!=undefined&&premisesRental!=''){
            premisesRental =premisesRental.replace(",","");
            otherTotal = otherTotal*1+premisesRental*1;
        }
        //人员工资
        var wages = $("input[name='wages']").val();
        if (wages!=null&&wages!=undefined&&wages!=''){
            wages =wages.replace(",","");
            otherTotal = otherTotal*1+wages*1;
        }
        //水、电、气费用
        var waterElec = $("input[name='waterElec']").val();
        if (waterElec!=null&&waterElec!=undefined&&waterElec!=''){
            waterElec =waterElec.replace(",","");
            otherTotal = otherTotal*1+waterElec*1;
        }
        //水、电、气费用
        var fixedExp = $("input[name='fixedExp']").val();
        if (fixedExp!=null&&fixedExp!=undefined&&fixedExp!=''){
            fixedExp =fixedExp.replace(",","");
            otherTotal = otherTotal*1+fixedExp*1;
        }
        if(otherTotal!=''){
            profit = profit*1-otherTotal*1;
        }
        $("input[name='totalProfit']").val(profit);
    };
    var _calcAmt2 = function () {
        //亩数/出栏数
        var num = $("input[name='num']").eq(1).val();
        //亩产/单个重量
        var yield = $("input[name='yield']").eq(1).val();
        //单价
        var price = $("input[name='price']").eq(1).val();
        //收入
        var income=0.00;
        if(num!=null&&num!=''&&num!=undefined&&yield!=null&&yield!=undefined&&yield!=''&&price!=null&&price!=undefined&&price!=''){
            num = num.replace(",","");
            yield = yield.replace(",","");
            price =price.replace(",","");
            income = (num*1)*(yield*1)*(price*1);
            $("input[name='income']").eq(1).val(income);

        }
        var profit =income;
        var totalCost = 0.00;
        //种子/幼畜
        var seedPup = $("input[name='seedPup']").eq(1).val();
        if (seedPup!=null&&seedPup!=undefined&&seedPup!=''){
            seedPup =seedPup.replace(",","");
            totalCost = totalCost*1+seedPup*1;
        }
        //化肥/饲料
        var feiFeed = $("input[name='feiFeed']").eq(1).val();
        if (feiFeed!=null&&feiFeed!=undefined&&feiFeed!=''){
            feiFeed =feiFeed.replace(",","");
            totalCost = totalCost*1+feiFeed*1;
        }
        //农药/玉米
        var pesticideCorn = $("input[name='pesticideCorn']").eq(1).val();
        if (pesticideCorn!=null&&pesticideCorn!=undefined&&pesticideCorn!=''){
            pesticideCorn =pesticideCorn.replace(",","");
            totalCost = totalCost*1+pesticideCorn*1;
        }
        //滴灌费用/豆粕
        var dripSoybean = $("input[name='dripSoybean']").eq(1).val();
        if (dripSoybean!=null&&dripSoybean!=undefined&&dripSoybean!=''){
            dripSoybean =dripSoybean.replace(",","");
            totalCost = totalCost*1+dripSoybean*1;
        }
        //劳务成本
        var laborCost = $("input[name='laborCost']").eq(1).val();
        if (laborCost!=null&&laborCost!=undefined&&laborCost!=''){
            laborCost =laborCost.replace(",","");
            totalCost = totalCost*1+laborCost*1;
        }
        //其他费用
        var otherFee = $("input[name='otherFee']").eq(1).val();
        if (otherFee!=null&&otherFee!=undefined&&otherFee!=''){
            otherFee =otherFee.replace(",","");
            totalCost = totalCost*1+otherFee*1;
        }
        if(totalCost!=''){
            $("input[name='totalCost']").eq(1).val(totalCost);
            profit = profit*1-totalCost*1;
        }
        $("input[name='profit']").eq(1).val(profit);
        //经营场地租金
        var otherTotal = 0.00;
        var premisesRental = $("input[name='premisesRental']").eq(1).val();
        if (premisesRental!=null&&premisesRental!=undefined&&premisesRental!=''){
            premisesRental =premisesRental.replace(",","");
            otherTotal = otherTotal*1+premisesRental*1;
        }
        //人员工资
        var wages = $("input[name='wages']").eq(1).val();
        if (wages!=null&&wages!=undefined&&wages!=''){
            wages =wages.replace(",","");
            otherTotal = otherTotal*1+wages*1;
        }
        //水、电、气费用
        var waterElec = $("input[name='waterElec']").eq(1).val();
        if (waterElec!=null&&waterElec!=undefined&&waterElec!=''){
            waterElec =waterElec.replace(",","");
            otherTotal = otherTotal*1+waterElec*1;
        }
        //水、电、气费用
        var fixedExp = $("input[name='fixedExp']").eq(1).val();
        if (fixedExp!=null&&fixedExp!=undefined&&fixedExp!=''){
            fixedExp =fixedExp.replace(",","");
            otherTotal = otherTotal*1+fixedExp*1;
        }
        if(otherTotal!=''){
            profit = profit*1-otherTotal*1;
        }
        $("input[name='totalProfit']").eq(1).val(profit);
    };
    //新增保存
    var  _ajaxSave = function(obj) {
        var flag = submitJsMethod($(obj).get(0),'');
        if(flag){
            ajaxInsertCusForm(obj);
        }
    };
    //保存继续新增
    var  _ajaxSaveAndAdd = function(obj) {
        var flag = submitJsMethod($(obj).get(0),'');
        var cusNo = $("input[name='cusNo']").val();
        var inputUrl = webPath+"/mfCusPlantBreed/input?cusNo="+cusNo;
        if(flag){
            ajaxInserAndAddCusForm(obj,inputUrl);
        }
    };

    var _totalProfit = function (){
        var income = $("input[name='income']").not(':disabled').val();
        income = income.replace(/,/g,'');
        var totalCost = $("input[name='totalCost']").not(':disabled').val();
        totalCost = totalCost.replace(/,/g,'');
        var   totalProfit  =CalcUtil.subtract(income,totalCost);
        $("input[name='totalProfit']").val(CalcUtil.formatMoney(totalProfit,2));

    }
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
        calcAmt : _calcAmt,
        calcAmt2 : _calcAmt2,
        ajaxSave : _ajaxSave,
        changeType : _changeType,
        ajaxSaveAndAdd : _ajaxSaveAndAdd,
        totalProfit:_totalProfit
	};
	
}(window, jQuery);


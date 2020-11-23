
function updateCusTableInfo (obj, url) {// 当客户信息是列表，编辑一条记录的时候
    action = webPath + "/" + action.substring(0, 1).toLowerCase() + action.substring(1);
    action = action.replace("Action", "");
    var inputUrl = webPath+url;
    top.openBigForm(inputUrl, title, function () {
        window.location.reload();
    });
}


function oneCallback(data,disVal) {
    var name = data[0].name;
    var value = data[0].value;
    var $_form = this;
    var formAction = $_form.attr("action");
    // 如果修改的是XXX表单，则进行XXX相关的业务处理。
    if (formAction == "") {

    } else if (formAction == webPath+"//mfCusCorpBaseInfo/updateAjaxByOne") {
        if("wayClass"==name || "assetSum"==name || "bussIncome"==name || "empCnt"==name){
            //如果修改了行业分类，资产总额 营业收入，从业人员，需要重写企业规模 getByIdAjax
            $.post(webPath+"/mfCusCorpBaseInfo/getByIdAjax", {cusNo: cusNoTmp}, function(data) {
                if(data.mfCusCorpBaseInfo!=null&&data.mfCusCorpBaseInfo!=''){
                    var proj=data.mfCusCorpBaseInfo.projSize;
                    var proJectName="";
                    if("1"==proj){
                        proJectName="大型企业";
                    }
                    if("2"==proj){
                        proJectName="中型企业";
                    }
                    if("3"==proj){
                        proJectName="小型企业";
                    }
                    if("4"==proj){
                        proJectName="微型企业";
                    }
                    if("5"==proj){
                        proJectName="其他";
                    }
                    $(".projSize").html(proJectName);
                }
            });
        }
        if(name=="contactsName"||name=="commAddress"||name=="contactsTel"||name=="postalCode"){
            $("span[id="+name+"]").html(value);
        }
        //工商信息
    } else if (formAction == webPath+"//mfCusBusinessInfo/updateAjaxByOne") {
        if("wayClass"==name || "assetSum"==name || "bussIncome"==name || "empCnt"==name){
            //如果修改了行业分类，资产总额 营业收入，从业人员，需要重写企业规模 getByIdAjax
            $.post(webPath+"/mfCusCorpBaseInfo/getByIdAjax", {cusNo: cusNoTmp}, function(data) {
                if(data.mfCusCorpBaseInfo!=null&&data.mfCusCorpBaseInfo!=''){
                    var proj=data.mfCusCorpBaseInfo.projSize;
                    var proJectName="";
                    if("1"==proj){
                        proJectName="大型企业";
                    }
                    if("2"==proj){
                        proJectName="中型企业";
                    }
                    if("3"==proj){
                        proJectName="小型企业";
                    }
                    if("4"==proj){
                        proJectName="微型企业";
                    }
                    if("5"==proj){
                        proJectName="其他";
                    }
                    $(".projSize").html(proJectName);
                }
            });
        }
    } else if (formAction === webPath+"/mfCusBankAccManage/updateByOneAjax") {
        if (name=="accountNo"||name=="accountName"||name=="useType"||name=="bank"){
            getBankByCardNumbe(name,data[1].value);
        }
    } else if (formAction === webPath+"/mfCusProfitLossAnalyse/updateAjaxByOne") {
        if(name=="incomeTotal"||name=="variableCostTotal"||name=="fixFeePay"||name=="otherIncome"||name=="otherOftenFeePay"){//损益分析
            var incomeTotal = $('input[name=incomeTotal]', $_form).val();
            var variableCostTotal = $('input[name=variableCostTotal]', $_form).val();
            var grossProfit = CalcUtil.subtract(incomeTotal.replace(/,/g,''),variableCostTotal.replace(/,/g,''));
            //修改毛利
            $('input[name=fixFeePay]', $_form).parents("tr").eq(0).find('td').eq(0).find('.fieldShow').html(CalcUtil.formatMoney(grossProfit,2));
            var fixFeePay = $('input[name=fixFeePay]', $_form).val();
            var netProfit = CalcUtil.subtract(grossProfit,fixFeePay.replace(/,/g,''));
            //修改净利润
            $('input[name=fixFeePay]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html(CalcUtil.formatMoney(netProfit,2));
            var otherIncome = $('input[name=otherIncome]', $_form).val();
            var profitSum = CalcUtil.add(netProfit,otherIncome.replace(/,/g,''));
            var otherOftenFeePay = $('input[name=otherOftenFeePay]', $_form).val();
            var disposableIncome = CalcUtil.subtract(profitSum,otherOftenFeePay.replace(/,/g,''));
            //修改可支配收入
            $('input[name=otherOftenFeePay]', $_form).parents("tr").eq(0).find('td').eq(1).find('.fieldShow').html(CalcUtil.formatMoney(disposableIncome,2));
            BASE.oneRefreshTable("incomeTotal",incomeTotal);
            BASE.oneRefreshTable("variableCostTotal",variableCostTotal);
            BASE.oneRefreshTable("grossProfit",CalcUtil.formatMoney(grossProfit,2));
            BASE.oneRefreshTable("fixFeePay",fixFeePay);
            BASE.oneRefreshTable("netProfit",CalcUtil.formatMoney(netProfit,2));
            BASE.oneRefreshTable("otherIncome",otherIncome);
            BASE.oneRefreshTable("otherOftenFeePay",otherOftenFeePay);
            BASE.oneRefreshTable("disposableIncome",CalcUtil.formatMoney(disposableIncome,2));
        }
    }else if (formAction === webPath+"/mfCusSaleProduct/updateAjaxByOne") {
        if(name=="productNum"||name=="productPrice"||name=="cosRaw1"||name=="cosRaw2"||name=="cosRaw3"||name=="cosRaw4"||"cosLabour1"||"cosLabour2"){//销售产品
            //销售数量
            var productNum = $('input[name=productNum]', $_form).val();
            //销售单价
            var productPrice = $('input[name=productPrice]', $_form).val();
            //销售额
            var saleAmt=0.00;
            if(productNum!=null&&productNum!=''&&productNum!=undefined&&productPrice!=null&&productPrice!=undefined&&productPrice!=''){
                productNum =productNum.replace(",","");
                productPrice =productPrice.replace(",","");
                saleAmt = (productNum*1)*(productPrice*1);
                //销售额
                $('input[name=cosRaw1]', $_form).parents("tr").eq(0).find('td').eq(0).find('.fieldShow').html(CalcUtil.formatMoney(saleAmt,2));
            }
            var profit =saleAmt;
            //原料成本一
            var cosRaw1 = $('input[name=cosRaw1]', $_form).val();
            if (cosRaw1!=null&&cosRaw1!=undefined&&cosRaw1!=''){
                cosRaw1 =cosRaw1.replace(",","");
                profit = profit*1-cosRaw1*1;
            }
            //原料成本二
            var cosRaw2 = $('input[name=cosRaw2]', $_form).val();
            if (cosRaw2!=null&&cosRaw2!=undefined&&cosRaw2!=''){
                cosRaw2 =cosRaw2.replace(",","");
                profit = profit*1-cosRaw2*1;
            }
            //原料成本三
            var cosRaw3 = $('input[name=cosRaw3]', $_form).val();
            if (cosRaw3!=null&&cosRaw3!=undefined&&cosRaw3!=''){
                cosRaw3 =cosRaw3.replace(",","");
                profit = profit*1-cosRaw3*1;
            }
            //原料成本四
            var cosRaw4 = $('input[name=cosRaw4]', $_form).val();
            if (cosRaw4!=null&&cosRaw4!=undefined&&cosRaw4!=''){
                cosRaw4 =cosRaw4.replace(",","");
                profit = profit*1-cosRaw4*1;
            }
            //劳务成本一
            var cosLabour1 = $('input[name=cosLabour1]', $_form).val();
            if (cosLabour1!=null&&cosLabour1!=undefined&&cosLabour1!=''){
                cosLabour1 =cosLabour1.replace(",","");
                profit = profit*1-cosLabour1*1;
            }
            //劳务成本二
            var cosLabour2 = $('input[name=cosLabour2]', $_form).val();
            if (cosLabour2!=null&&cosLabour2!=undefined&&cosLabour2!=undefined){
                cosLabour2 =cosLabour2.replace(",","");
                profit = profit*1-cosLabour2*1;
            }
            $('input[name=cosLabour2]', $_form).parents("tr").eq(0).find('td').eq(1).find('.fieldShow').html(CalcUtil.formatMoney(profit,2));
            var profitRate=0;
            if(saleAmt!=''&&profit!=''){
                if(saleAmt!=0.00){
                    profitRate =(profit*1)/(saleAmt*1)*100;
                    $('input[name=cosLabour2]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html(CalcUtil.formatMoney(profitRate,2));
                    BASE.oneRefreshTable("profitRate",CalcUtil.formatMoney(profitRate,2));
                }
            }

            BASE.oneRefreshTable("saleAmt",CalcUtil.formatMoney(saleAmt,2));
            BASE.oneRefreshTable("profit",CalcUtil.formatMoney(profit,2));

        }
    } else if (formAction === webPath+"/mfCusPlantBreed/updateAjaxByOne") {
        if(name=="num"||name=="yield"||name=="price"||name=="seedPup"||name=="feiFeed"||name=="pesticideCorn"||name=="dripSoybean"||name=="laborCost"||name=="otherFee"||name=="premisesRental"||name=="wages"||name=="waterElec"||name=="fixedExp"){//种植养殖
            //亩数/出栏数
            var num =  $('input[name=num]', $_form).val();
            //亩产/单个重量
            var yield = $('input[name=yield]', $_form).val();
            //单价
            var price =  $('input[name=price]', $_form).val();
            //收入
            var income=0.00;
            if(num!=null&&num!=''&&num!=undefined&&yield!=null&&yield!=undefined&&yield!=''&&price!=null&&price!=undefined&&price!=''){
                num = num.replace(",","");
                yield = yield.replace(",","");
                price =price.replace(",","");
                income = (num*1)*(yield*1)*(price*1);
                $('input[name=price]', $_form).parents("tr").eq(0).find('td').eq(1).find('.fieldShow').html(CalcUtil.formatMoney(income,2));
                BASE.oneRefreshTable("income",CalcUtil.formatMoney(income,2));
            }
            var profit =income;
            var totalCost = 0.00;
            //种子/幼畜
            var seedPup = $('input[name=seedPup]', $_form).val();
            if (seedPup!=null&&seedPup!=undefined&&seedPup!=''){
                seedPup =seedPup.replace(",","");
                totalCost = totalCost*1+seedPup*1;
            }
            //化肥/饲料
            var feiFeed = $('input[name=feiFeed]', $_form).val();
            if (feiFeed!=null&&feiFeed!=undefined&&feiFeed!=''){
                feiFeed =feiFeed.replace(",","");
                totalCost = totalCost*1+feiFeed*1;
            }
            //农药/玉米
            var pesticideCorn =$('input[name=pesticideCorn]', $_form).val();
            if (pesticideCorn!=null&&pesticideCorn!=undefined&&pesticideCorn!=''){
                pesticideCorn =pesticideCorn.replace(",","");
                totalCost = totalCost*1+pesticideCorn*1;
            }
            //滴灌费用/豆粕
            var dripSoybean =$('input[name=dripSoybean]', $_form).val();
            if (dripSoybean!=null&&dripSoybean!=undefined&&dripSoybean!=''){
                dripSoybean =dripSoybean.replace(",","");
                totalCost = totalCost*1+dripSoybean*1;
            }
            //劳务成本
            var laborCost = $('input[name=laborCost]', $_form).val();
            if (laborCost!=null&&laborCost!=undefined&&laborCost!=''){
                laborCost =laborCost.replace(",","");
                totalCost = totalCost*1+laborCost*1;
            }
            //其他费用
            var otherFee = $('input[name=otherFee]', $_form).val();
            if (otherFee!=null&&otherFee!=undefined&&otherFee!=''){
                otherFee =otherFee.replace(",","");
                totalCost = totalCost*1+otherFee*1;
            }
            if(totalCost!=''){
                $('input[name=premisesRental]', $_form).parents("tr").eq(0).find('td').eq(0).find('.fieldShow').html(CalcUtil.formatMoney(totalCost,2));
                BASE.oneRefreshTable("totalCost",CalcUtil.formatMoney(totalCost,2));
                profit = profit*1-totalCost*1;
            }
            $('input[name=premisesRental]', $_form).parents("tr").eq(0).find('td').eq(1).find('.fieldShow').html(CalcUtil.formatMoney(profit,2));
            BASE.oneRefreshTable("profit",CalcUtil.formatMoney(profit,2));
            //经营场地租金
            var otherTotal = 0.00;
            var premisesRental = $("input[name='premisesRental']").val();
            if (premisesRental!=null&&premisesRental!=undefined&&premisesRental!=''){
                premisesRental =premisesRental.replace(",","");
                otherTotal = otherTotal*1+premisesRental*1;
            }
            //人员工资
            var wages = $('input[name=wages]', $_form).val();
            if (wages!=null&&wages!=undefined&&wages!=''){
                wages =wages.replace(",","");
                otherTotal = otherTotal*1+wages*1;
            }
            //水、电、气费用
            var waterElec = $('input[name=waterElec]', $_form).val();
            if (waterElec!=null&&waterElec!=undefined&&waterElec!=''){
                waterElec =waterElec.replace(",","");
                otherTotal = otherTotal*1+waterElec*1;
            }
            //水、电、气费用
            var fixedExp = $('input[name=fixedExp]', $_form).val();
            if (fixedExp!=null&&fixedExp!=undefined&&fixedExp!=''){
                fixedExp =fixedExp.replace(",","");
                otherTotal = otherTotal*1+fixedExp*1;
            }
            if(otherTotal!=''){
                profit = profit*1-otherTotal*1;
            }
            $('input[name=fixedExp]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html(CalcUtil.formatMoney(profit,2));
            BASE.oneRefreshTable("totalProfit",CalcUtil.formatMoney(profit,2));
        }
    }else if (formAction === webPath+"/mfCusBusService/updateAjaxByOne") {
        if(name=="inNum"||name=="inPrice"||name=="expNum"||name=="expPrice"||name=="busIncome"||name=="busExp"||name=="profit" || name=="profitRate" || name=="regDate"){//商业服务信息
            //收入数量
            var inNum = $('input[name=inNum]', $_form).val();
            //收入单价
            var inPrice = $('input[name=inPrice]', $_form).val();
            //收入合计
            var busIncome=0.00;
            if(inNum!=null&&inNum!=''&&inNum!=undefined&&inPrice!=null&&inPrice!=undefined&&inPrice!=''){
                inNum =inNum.replace(",","");
                inPrice =inPrice.replace(",","");
                busIncome = (inNum*1)*(inPrice*1);
                //收入合计
                $('input[name=inPrice]', $_form).parents("tr").eq(0).find('td').eq(3).find('.fieldShow').html(CalcUtil.formatMoney(busIncome,2));
                BASE.oneRefreshTable("busIncome",CalcUtil.formatMoney(busIncome,2));
            }

            //支出数量
            var expNum = $('input[name=expNum]', $_form).val();
            //支出单价
            var expPrice = $('input[name=expPrice]', $_form).val();
            //支出合计
            var busExp=0.00;
            if(expNum!=null&&expNum!=''&&expNum!=undefined&&expPrice!=null&&expPrice!=undefined&&expPrice!=''){
                expNum =expNum.replace(",","");
                expPrice =expPrice.replace(",","");
                busExp = (expNum*1)*(expPrice*1);
                //销售额
                $('input[name=expPrice]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html(CalcUtil.formatMoney(busExp,2));
                BASE.oneRefreshTable("busExp",CalcUtil.formatMoney(busExp,2));
            }

            //业务毛利润
            var profit = 0;
            if(busExp!=null&&busExp!=''&&busExp!=undefined&&busIncome!=null&&busIncome!=undefined&&busIncome!=''){
                profit = (busIncome*1)-(busExp*1);
                //毛利润
                $('input[name=expPrice]', $_form).parents("tr").eq(0).find('td').eq(3).find('.fieldShow').html(CalcUtil.formatMoney(profit,2));
                BASE.oneRefreshTable("profit",CalcUtil.formatMoney(profit,2));
            }

            //业务毛利率
            var profitRate =0;
            if(busExp!=null&&busExp!=''&&busExp!=undefined&&busIncome!=null&&busIncome!=undefined&&busIncome!=''){
                profitRate = (busExp*1)/(busIncome*1)*100;
                //毛利率
                $('input[name=regDate]', $_form).parents("tr").eq(0).find('td').eq(1).find('.fieldShow').html(CalcUtil.formatMoney(profitRate,2));
                BASE.oneRefreshTable("profitRate",CalcUtil.formatMoney(profitRate,2));
            }
        }
    }else if (formAction === webPath+"/mfCusPersonFlowAssetsInfo/updateAjaxByOne") {

        if(name=="quantity"||name=="purchasePrice"){//个人资产存货
            //收入数量
            var quantity = $('input[name=quantity]', $_form).val();
            //收入单价
            var purchasePrice = $('input[name=purchasePrice]', $_form).val();
            var nowPrice =0.00;
            if(quantity!=null&&quantity!=''&&quantity!=undefined&&purchasePrice!=null&&purchasePrice!=undefined&&purchasePrice!=''){
                quantity = quantity.replace(",","");
                purchasePrice = purchasePrice.replace(",","");
                nowPrice = (quantity*1)*(purchasePrice*1);
                //毛利率
                // $('input[name=quantity]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html(CalcUtil.formatMoney(nowPrice,2));
                BASE.oneRefreshTable("nowPrice",CalcUtil.formatMoney(nowPrice,2));
            }
        }
        if(name == "nowPrice") { BASE.oneRefreshTable("nowPrice",disVal);}
        if(name == "assetsType"){BASE.oneRefreshTable("assetsTypeName",disVal);}
        if(name == "assetsName"){BASE.oneRefreshTable("assetsName",disVal);}//资产名称
    }else if (formAction === webPath+"/mfCusDebtArmourInfo/updateAjaxByOne") {//负债信息(铁甲网)
        if(name=="overAmt"||name=="loanAmt"||name=="pettyLoanAmt"||name=="creditCardDebtAmt"
            ||name=="externalGuaranteeAmt"||name=="otherDebtAmt"){
            var overAmt = $('input[name=overAmt]', $_form).val().replace(/,/g,'');
            var loanAmt = $('input[name=loanAmt]', $_form).val().replace(/,/g,'');
            var pettyLoanAmt = $('input[name=pettyLoanAmt]', $_form).val().replace(/,/g,'');
            var creditCardDebtAmt = $('input[name=creditCardDebtAmt]', $_form).val().replace(/,/g,'');
            var externalGuaranteeAmt = $('input[name=externalGuaranteeAmt]', $_form).val().replace(/,/g,'');
            var otherDebtAmt = $('input[name=otherDebtAmt]', $_form).val().replace(/,/g,'');
            //求和
            var sumAmt1 = CalcUtil.add(overAmt,loanAmt);
            var sumAmt2 = CalcUtil.add(pettyLoanAmt,creditCardDebtAmt);
            var sumAmt3 = CalcUtil.add(externalGuaranteeAmt,otherDebtAmt);
            var sumAmt11 = CalcUtil.add(sumAmt1,sumAmt2);
            var sumAmt = CalcUtil.add(sumAmt11,sumAmt3);
            $('input[name=otherDebtAmt]', $_form).parents("tr").eq(0).find('td').eq(3).find('.fieldShow').html(CalcUtil.formatMoney(sumAmt,2));
        }
    } else if(formAction === webPath+"/mfCusFarmerIncExpe/updateAjaxByOne"){//农户收支情况(吉时与)
        if(name=="operateIncome"||name=="otherIncome"||name=="laborIncome"||name=="subsidyIncome"){

            var operateIncome = $("input[name=operateIncome]", $_form).val().replace(/,/g,'');//工资收入
            var otherIncome = $("input[name=otherIncome]", $_form).val().replace(/,/g,'');//其他收入
            var laborIncome = $("input[name=laborIncome]", $_form).val().replace(/,/g,'');//其他家庭成员收入
            var subsidyIncome = $("input[name=subsidyIncome]", $_form).val().replace(/,/g,'');//房屋租赁收入
            //
            var addTotal1 = CalcUtil.add(operateIncome,otherIncome);
            var addTotal2 = CalcUtil.add(laborIncome,subsidyIncome);
            //
            var totalIncome = CalcUtil.add(addTotal1,addTotal2);

            $('input[name=plantCost]', $_form).parents("tr").eq(0).find('td').eq(0).find('.fieldShow').html(CalcUtil.formatMoney(totalIncome,2));

            var totalOutgo  = $('input[name=medicaOut]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html().replace(/,/g,'');
            var income =	CalcUtil.subtract(totalIncome,totalOutgo);
            $('input[name=otherCost]', $_form).parents("tr").eq(0).find('td').eq(1).find('.fieldShow').html(CalcUtil.formatMoney(income,2));

        }else if(name=="plantCost"||name=="feedCost"||name=="medicaOut"||name=="consumeOut"||name=="liveCost"||name=="otherCost"){
            var plantCost = $("input[name=plantCost]", $_form).val().replace(/,/g,'');//房租支出
            var feedCost = $("input[name=feedCost]", $_form).val().replace(/,/g,'');//水、电、气费支出
            var medicaOut = $("input[name=medicaOut]", $_form).val().replace(/,/g,'');//医疗支出
            var consumeOut = $("input[name=consumeOut]", $_form).val().replace(/,/g,'');//教育支出

            var liveCost = $("input[name=liveCost]", $_form).val().replace(/,/g,'');//生活费支出
            var otherCost = $("input[name=otherCost]", $_form).val().replace(/,/g,'');//其他支出

            var addTotal1 = CalcUtil.add(plantCost,feedCost);
            var addTotal2 = CalcUtil.add(medicaOut, consumeOut);
            var addTotal3 = CalcUtil.add(liveCost, otherCost);
            //求和
            var addTotal4 = CalcUtil.add(addTotal1,addTotal2);
            var totalOutgo = CalcUtil.add(addTotal3,addTotal4);

            $('input[name=medicaOut]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html(CalcUtil.formatMoney(totalOutgo,2));
            var  totalIncome  =	$('input[name=plantCost]', $_form).parents("tr").eq(0).find('td').eq(0).find('.fieldShow').html().replace(/,/g,'');

            var income =	CalcUtil.subtract(totalIncome,totalOutgo);
            $('input[name=otherCost]', $_form).parents("tr").eq(0).find('td').eq(1).find('.fieldShow').html(CalcUtil.formatMoney(income,2));

        }
    }else if(formAction === webPath+"/mfCusHighInfo/updateByOneAjax")//高管信息
    {
        if (name == "sex" || name =="idNum"|| name == "brithday")
        {
            var idnum = $('input[name=idNum]', $_form).val();
            BASE.oneRefreshTable("idNum",idnum);
        }
        BASE.oneRefreshTable("highName",$('input[name=highName]', $_form).val());
        BASE.oneRefreshTable("highCusType",$('input[name=highCusType]', $_form).val());
        BASE.oneRefreshTable("education",$('input[name=education]', $_form).val());
    }else if(formAction === webPath+"/mfCusPersonCorp/updateAjaxByOne")//个人名下企业
    {
        var creditCode = $('input[name=idNum]',$_form).val();
        BASE.oneRefreshTable("idNum",creditCode);
        BASE.oneRefreshTable("registeredCapital",$('input[name=registeredCapital]', $_form).val());
        if(name=="corpNature"){
            refreshCusPersonCorpTable(name,data[1].value);
        }
    } else {
        /**
         *  基本不会重复的，或者基本完全通用的，可以不加条件来处理
         *  有重复的，需要把两个都独立出去。
         */
        //mf_cus_customer mf_cus_register mf_cus_corp_base_info
        if(name=="contactsName"||name=="commAddress"||name=="contactsTel"||name=="postalCode"){
            $("span[id="+name+"]", $_form).html(value);
        }
        if(name=="debtType"||name=="useType"||name=="highCusType"||name=="education"||name=="highCusType"||name=="education"||name=="sellArea"||name=="sellWayclass"||name=="isLegal"||"relative"==name||name=="saleArea"||name=="goodsRule"||name=="changeType"||name=="corpNature"||name=="registeredType"||name=="idCardInfo"||name=="regBookInfo"||name=="conEnvironment"||name=="conPaymentt"||name=="casePeopleType"||name=="dishonestPersonType"||name=="isJudg"||name=="assetsType"||name=="agrOrgType"||name=="corpKind"||name=="duty"||name=="expType"||name=="expName"||"projectType"){
            BASE.oneRefreshTable(name,disVal);
        }else if(name=="careaCity"){
            var careaProvice = $("input[name=careaProvice]", $_form).val();
            var cusNo = $("input[name=cusNo]", $_form).val();
            $.ajax({
                url : webPath+"/mfCusCorpBaseInfo/updateCareaAjax",
                data : {
                    ajaxData : careaProvice ,cusNo:cusNoTmp
                },
                type : 'post',
                dataType : 'json',
                async:false,
                success : function(data) {
                }
            });
        }else if(name=="initialCapital"||name=="durateDisposableIncomeTotal"||name=="durateOutNotOftenPay"||name=="assetDepreciate"||name=="durateAssetInject"){//权益分析
            var initialCapital = $('input[name=initialCapital]').val();//启动资本
            var durateDisposableIncomeTotal = $('input[name=durateDisposableIncomeTotal]', $_form).val();//期间可支配收入合计
            var durateOutNotOftenPay = $('input[name=durateOutNotOftenPay]', $_form).val();//期间表外非经常性支出
            var assetDepreciate = $('input[name=assetDepreciate]', $_form).val();//资产折旧
            var durateAssetInject = $('input[name=durateAssetInject]', $_form).val();//期间注资

            var incomeValue = CalcUtil.add(initialCapital.replace(/,/g,''),durateDisposableIncomeTotal.replace(/,/g,''));
            var incomeTotalValue = CalcUtil.add(incomeValue,durateAssetInject.replace(/,/g,''));
            var payValue = CalcUtil.add(durateOutNotOftenPay.replace(/,/g,''),assetDepreciate.replace(/,/g,''));
            var ownerAssetValue = CalcUtil.subtract(incomeTotalValue,payValue);
            //修改所有者权益
            $('input[name=durateAssetInject]', $_form).parents("tr").eq(0).find('td').eq(2).find('.fieldShow').html(CalcUtil.formatMoney(ownerAssetValue,2));
        }else if(name=="ifGroup"){
            updateGroupName();
        }else{
            if ($_form.attr("id") === 'listForm') {
                BASE.oneRefreshTable(name,value);
            }
        }
    }

}
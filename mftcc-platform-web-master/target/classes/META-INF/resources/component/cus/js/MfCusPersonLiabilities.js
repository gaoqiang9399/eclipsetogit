var MfCusPersonLiabilities=function(window, $){
	var _init=function(){
        // $("#out_content").html(ajaxData.tableHtml);
        $("#tablist").show();
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		$("table").find("tr").css("border-right"," 1px solid #ddd ");
		
	}
	//计算现金资产合计
	var _cashTotalAmt=function(){
		//现值金额合计
		var cashAmt = $("input[name=cashAmt]").val();//现金
		var currentAccAmt = $("input[name=currentAccAmt]").val();//活期存款
		var fixedAccAmt = $("input[name=fixedAccAmt]").val();//定期存款
		var monetaryFundAmt = $("input[name=monetaryFundAmt]").val();///货币基金
		var loanRecAmt = $("input[name=loanRecAmt]").val();//应收借款
		var otherCashAmt = $("input[name=otherCashAmt]").val();//其他现金资
		var addTotal1 = CalcUtil.add(cashAmt.replace(/,/g,''),currentAccAmt.replace(/,/g,''));
		var addTotal2 = CalcUtil.add(fixedAccAmt.replace(/,/g,''), monetaryFundAmt.replace(/,/g,''));
		var addTotal3 = CalcUtil.add(loanRecAmt.replace(/,/g,''),otherCashAmt.replace(/,/g,''));
		var cashTotalAmt =CalcUtil.add(CalcUtil.add(addTotal1,addTotal2),addTotal3);
		//赋值
		$("input[name=cashTotalAmt]").val(cashTotalAmt);
		_assetsTotal();
	}
	//计算现金资产合计
	var _cashTotalScale=function(){
		//比例合计
		var cashScale = $("input[name=cashScale]").val();//现金
		var currentAccScale = $("input[name=currentAccScale]").val();//活期存款
		var fixedAccScale = $("input[name=fixedAccScale]").val();//定期存款
		var monetaryFundScale = $("input[name=monetaryFundScale]").val();///货币基金
		var loanRecScale = $("input[name=loanRecScale]").val();//应收借款
		var otherCashScale = $("input[name=otherCashScale]").val();//其他现金资
		var addTotal1 = CalcUtil.add(cashScale.replace(/,/g,''),currentAccScale.replace(/,/g,''));
		var addTotal2 = CalcUtil.add(fixedAccScale.replace(/,/g,''), monetaryFundScale.replace(/,/g,''));
		var addTotal3 = CalcUtil.add(loanRecScale.replace(/,/g,''),otherCashScale.replace(/,/g,''));
		var cashTotalScale =CalcUtil.add(CalcUtil.add(addTotal1,addTotal2),addTotal3);
		//赋值
		$("input[name=cashTotalScale]").val(cashTotalScale);
	}
	//计算金融资产合计
	var _financeTotalAmt=function(){
		//现值金额合计
		var stockAmt = $("input[name=stockAmt]").val();//股票
		var fundAmt = $("input[name=fundAmt]").val();//基金
		var bondAmt = $("input[name=bondAmt]").val();//债券
		var futuresAmt = $("input[name=futuresAmt]").val();///期货
		var foreignExchangeAmt = $("input[name=foreignExchangeAmt]").val();//外汇
		var insuranceAmt = $("input[name=insuranceAmt]").val();//保险
		var financialAmt = $("input[name=financialAmt]").val();//理财
		var addTotal1 = CalcUtil.add(stockAmt.replace(/,/g,''),fundAmt.replace(/,/g,''));
		var addTotal2 = CalcUtil.add(bondAmt.replace(/,/g,''), futuresAmt.replace(/,/g,''));
		var addTotal3 = CalcUtil.add(foreignExchangeAmt.replace(/,/g,''),insuranceAmt.replace(/,/g,''));
		var financeTotalAmt =CalcUtil.add(CalcUtil.add(addTotal1,addTotal2),CalcUtil.add(addTotal3,financialAmt.replace(/,/g,'')));
		//赋值
		$("input[name=financeTotalAmt]").val(financeTotalAmt);
		_assetsTotal();
	}
	//计算金融资产合计
	var _financeTotalScale=function(){
		//现值金额合计
		var stockScale = $("input[name=stockScale]").val();//股票
		var fundScale = $("input[name=fundScale]").val();//基金
		var bondScale = $("input[name=bondScale]").val();//债券
		var futuresScale = $("input[name=futuresScale]").val();///期货
		var foreignExchangeScale = $("input[name=foreignExchangeScale]").val();//外汇
		var insuranceScale = $("input[name=insuranceScale]").val();//保险
		var financialScale = $("input[name=financialScale]").val();//理财
		var addTotal1 = CalcUtil.add(stockScale.replace(/,/g,''),fundScale.replace(/,/g,''));
		var addTotal2 = CalcUtil.add(bondScale.replace(/,/g,''), futuresScale.replace(/,/g,''));
		var addTotal3 = CalcUtil.add(foreignExchangeScale.replace(/,/g,''),insuranceScale.replace(/,/g,''));
		var financeTotalScale =CalcUtil.add(CalcUtil.add(addTotal1,addTotal2),CalcUtil.add(addTotal3,financialScale.replace(/,/g,'')));		//赋值
		$("input[name=financeTotalScale]").val(financeTotalScale);
	}
	//计算无形资产合计
	var _intangibleTotalAmt=function(){
		//现值金额合计
		var patentAmt = $("input[name=patentAmt]").val();//专利
		var trademarkAmt = $("input[name=trademarkAmt]").val();//商标
		var copyrightAmt = $("input[name=copyrightAmt]").val();//著作权
		var otherIntangibleAmt = $("input[name=otherIntangibleAmt]").val();///其他
		var addTotal1 = CalcUtil.add(patentAmt.replace(/,/g,''),trademarkAmt.replace(/,/g,''));
		var addTotal2 = CalcUtil.add(copyrightAmt.replace(/,/g,''), otherIntangibleAmt.replace(/,/g,''));
		var intangibleTotalAmt =CalcUtil.add(addTotal1,addTotal2);
		//赋值
		$("input[name=intangibleTotalAmt]").val(intangibleTotalAmt);
		_assetsTotal();
	}
	//计算无形资产合计
	var _intangibleTotalScale=function(){
		//现值金额合计
		var patentScale = $("input[name=patentScale]").val();//专利
		var trademarkScale = $("input[name=trademarkScale]").val();//商标
		var copyrightScale = $("input[name=copyrightScale]").val();//著作权
		var otherIntangibleScale = $("input[name=otherIntangibleScale]").val();///其他
		var addTotal1 = CalcUtil.add(patentScale.replace(/,/g,''),trademarkScale.replace(/,/g,''));
		var addTotal2 = CalcUtil.add(copyrightScale.replace(/,/g,''), otherIntangibleScale.replace(/,/g,''));
		var intangibleTotalScale =CalcUtil.add(addTotal1,addTotal2);
		//赋值
		$("input[name=intangibleTotalScale]").val(intangibleTotalScale);
	}
	//计算实物资产合计
	var _physicalTotalAmt=function(){
		//现值金额合计
		var owerHouseAmt = $("input[name=owerHouseAmt]").val();//自住房产
		var investmentHouseAmt = $("input[name=investmentHouseAmt]").val();//投资性房产
		var carAmt = $("input[name=carAmt]").val();//汽车
		var jewelleryAmt = $("input[name=jewelleryAmt]").val();///珠宝
		var collectionAmt = $("input[name=collectionAmt]").val();//收藏品
		var otherPhysicalAmt = $("input[name=otherPhysicalAmt]").val();//其他
		var addTotal1 = CalcUtil.add(owerHouseAmt.replace(/,/g,''),investmentHouseAmt.replace(/,/g,''));
		var addTotal2 = CalcUtil.add(carAmt.replace(/,/g,''), jewelleryAmt.replace(/,/g,''));
		var addTotal3 = CalcUtil.add(collectionAmt.replace(/,/g,''),otherPhysicalAmt.replace(/,/g,''));
		var physicalTotalAmt =CalcUtil.add(CalcUtil.add(addTotal1,addTotal2),addTotal3);
		//赋值
		$("input[name=physicalTotalAmt]").val(physicalTotalAmt);
		_assetsTotal();
	}
	//计算实物资产比例合计
	var _physicalTotalScale=function(){
		//现值金额合计
		var owerHouseScale = $("input[name=owerHouseScale]").val();//自住房产
		var investmentHouseScale = $("input[name=investmentHouseScale]").val();//投资性房产
		var carScale = $("input[name=carScale]").val();//汽车
		var jewelleryScale = $("input[name=jewelleryScale]").val();///珠宝
		var collectionScale = $("input[name=collectionScale]").val();//收藏品
		var otherPhysicalScale = $("input[name=insuranceScale]").val();//其他
		var addTotal1 = CalcUtil.add(owerHouseScale.replace(/,/g,''),investmentHouseScale.replace(/,/g,''));
		var addTotal2 = CalcUtil.add(carScale.replace(/,/g,''), jewelleryScale.replace(/,/g,''));
		var addTotal3 = CalcUtil.add(collectionScale.replace(/,/g,''),otherPhysicalScale.replace(/,/g,''));
		var physicalTotalScale =CalcUtil.add(CalcUtil.add(addTotal1,addTotal2),addTotal3);
		//赋值
		$("input[name=physicalTotalScale]").val(physicalTotalScale);
	}
	//计算长期负债合计
	var _loanTotalAmt=function(){
		//长期负债合计
		var housingLoanAmt = $("input[name=housingLoanAmt]").val();//房屋贷款
		var carLoanAmt = $("input[name=carLoanAmt]").val();//汽车贷款
		var businessLoanAmt = $("input[name=businessLoanAmt]").val();//创业贷款金额
		var otherLoanAmt = $("input[name=otherLoanAmt]").val();///其它长期负债
		var addTotal1 = CalcUtil.add(housingLoanAmt.replace(/,/g,''),carLoanAmt.replace(/,/g,''));
		var addTotal2 = CalcUtil.add(businessLoanAmt.replace(/,/g,''), otherLoanAmt.replace(/,/g,''));
		var loanTotalAmt =CalcUtil.add(addTotal1,addTotal2);
		//赋值
		$("input[name=loanTotalAmt]").val(loanTotalAmt);
		_liabilitiesTotal();
	}
	//计算长期负债合计
	var _loanTotalScale=function(){
		//长期负债合计
		var housingLoanScale = $("input[name=housingLoanScale]").val();//房屋贷款
		var carLoanScale = $("input[name=carLoanScale]").val();//汽车贷款
		var businessLoanScale = $("input[name=businessLoanScale]").val();//创业贷款金额
		var otherLoanScale = $("input[name=otherLoanScale]").val();///其它长期负债
		var addTotal1 = CalcUtil.add(housingLoanScale.replace(/,/g,''),carLoanScale.replace(/,/g,''));
		var addTotal2 = CalcUtil.add(businessLoanScale.replace(/,/g,''), otherLoanScale.replace(/,/g,''));
		var loanTotalScale =CalcUtil.add(addTotal1,addTotal2);
		//赋值
		$("input[name=loanTotalScale]").val(loanTotalScale);
	}
	//计算流动负债计
	var _currentLoanTotalAmt=function(){
		//流动负债合计
		var creditCardLoanAmt = $("input[name=creditCardLoanAmt]").val();//信用卡
		var houseRentLoanAmt = $("input[name=houseRentLoanAmt]").val();//房屋租金
		var pettyLoanAmt = $("input[name=pettyLoanAmt]").val();//小额借款
		var insuranceLoanAmt = $("input[name=insuranceLoanAmt]").val();//保险费
		var propertyTaxLoanAmt = $("input[name=propertyTaxLoanAmt]").val();///房产税
		var incomeTaxLoanAmt = $("input[name=incomeTaxLoanAmt]").val();//所得税
		var otherCurrentLoanAmt = $("input[name=otherCurrentLoanAmt]").val();//其它流动负债
		var addTotal1 = CalcUtil.add(houseRentLoanAmt.replace(/,/g,''),pettyLoanAmt.replace(/,/g,''));
		var addTotal2 = CalcUtil.add(insuranceLoanAmt.replace(/,/g,''), propertyTaxLoanAmt.replace(/,/g,''));
		var addTotal3 = CalcUtil.add(incomeTaxLoanAmt.replace(/,/g,''),otherCurrentLoanAmt.replace(/,/g,''));
		var currentLoanTotalAmt =CalcUtil.add(CalcUtil.add(addTotal1,addTotal2),CalcUtil.add(addTotal3,creditCardLoanAmt.replace(/,/g,'')));
		//赋值
		$("input[name=currentLoanTotalAmt]").val(currentLoanTotalAmt);
		_liabilitiesTotal();
	}
	//计算流动负债计
	var _currentLoanTotalScale=function(){
		//流动负债合计
		var creditCardLoanScale = $("input[name=creditCardLoanScale]").val();//信用卡
		var houseRentLoanScale = $("input[name=houseRentLoanScale]").val();//房屋租金
		var pettyLoanScale = $("input[name=pettyLoanScale]").val();//小额借款
		var insuranceLoanScale = $("input[name=insuranceLoanScale]").val();//保险费
		var propertyTaxLoanScale = $("input[name=propertyTaxLoanScale]").val();///房产税
		var incomeTaxLoanScale = $("input[name=incomeTaxLoanScale]").val();//所得税
		var otherCurrentLoanScale = $("input[name=otherCurrentLoanScale]").val();//其它流动负债
		var addTotal1 = CalcUtil.add(houseRentLoanScale.replace(/,/g,''),pettyLoanScale.replace(/,/g,''));
		var addTotal2 = CalcUtil.add(insuranceLoanScale.replace(/,/g,''), propertyTaxLoanScale.replace(/,/g,''));
		var addTotal3 = CalcUtil.add(incomeTaxLoanScale.replace(/,/g,''),otherCurrentLoanScale.replace(/,/g,''));
		var currentLoanTotalScale =CalcUtil.add(CalcUtil.add(addTotal1,addTotal2),CalcUtil.add(addTotal3,creditCardLoanScale.replace(/,/g,'')));
		//赋值
		$("input[name=currentLoanTotalScale]").val(currentLoanTotalScale);
	}
	//计算总资产
	var _assetsTotal = function(){
		//现金资产
		var cashTotalAmt = $("input[name=cashTotalAmt]").val();
		//金融资产
		var financeTotalAmt = $("input[name=financeTotalAmt]").val();
		//无形资产
		var intangibleTotalAmt = $("input[name=intangibleTotalAmt]").val();
		//实物资产
		var physicalTotalAmt = $("input[name=physicalTotalAmt]").val();
		var addTotal1 = CalcUtil.add(cashTotalAmt.replace(/,/g,''),financeTotalAmt.replace(/,/g,''));
		var addTotal2 = CalcUtil.add(intangibleTotalAmt.replace(/,/g,''),physicalTotalAmt.replace(/,/g,''));
		var totalAssetsAmt = CalcUtil.add(addTotal1,addTotal2);
		$("input[name=totalAssetsAmt]").val(totalAssetsAmt);
		//负债合计totalLiabilitiesAmt
		var totalLiabilitiesAmt = $("input[name=totalLiabilitiesAmt]").val();
		//个人净资产
		var personalAssetsAmt = CalcUtil.subtract(totalAssetsAmt,totalLiabilitiesAmt);
		$("input[name=personalAssetsAmt]").val(personalAssetsAmt);
		if(totalLiabilitiesAmt>0.00){
			 var debtRatio = CalcUtil.divide(totalLiabilitiesAmt,totalAssetsAmt);
			 $("input[name=debtRatio]").val(CalcUtil.multiply(debtRatio,100));
		}
	
	}
	//计算总负债
	var _liabilitiesTotal = function(){
		//长期负债
		var loanTotalAmt = $("input[name=loanTotalAmt]").val();
		//流动负债
		var currentLoanTotalAmt = $("input[name=currentLoanTotalAmt]").val();
		//总负债
		var totalLiabilitiesAmt = CalcUtil.add(loanTotalAmt.replace(/,/g,''),currentLoanTotalAmt.replace(/,/g,''));
		$("input[name=totalLiabilitiesAmt]").val(totalLiabilitiesAmt);
		var totalAssetsAmt = $("input[name=totalAssetsAmt]").val();;
		//个人净资产
		var personalAssetsAmt = CalcUtil.subtract(totalAssetsAmt,totalLiabilitiesAmt);
		$("input[name=personalAssetsAmt]").val(personalAssetsAmt);
		if(totalLiabilitiesAmt>0.00&&totalAssetsAmt>0.00){
			 var debtRatio = CalcUtil.divide(totalLiabilitiesAmt,totalAssetsAmt);
			 $("input[name=debtRatio]").val(CalcUtil.multiply(debtRatio,100));
		}else{
			 $("input[name=debtRatio]").val("0");
		}
	}
	var  _insertCusPersonLiabilitiesBase = function(obj) {
			ajaxInsertCusForm(obj);
	}
	//其他经营性资产金额改变调用事件
	var _insuranceAmtChange = function(){
		//其他经营性资产金
		var insuranceAmt =  $("input[name=insuranceAmt]").val();
        insuranceAmt= insuranceAmt.replace(/,/g,'');
		//非流动资产小计
		var financeTotalAmt =  $("input[name=financeTotalAmt]").val();
        financeTotalAmt= financeTotalAmt.replace(/,/g,'');
        financeTotalAmt = CalcUtil.add(financeTotalAmt,insuranceAmt);
        $("input[name=financeTotalAmt]").val(financeTotalAmt);
		//总资产小计
		var totalAssetsAmt =  $("input[name=totalAssetsAmt]").val();
        totalAssetsAmt= totalAssetsAmt.replace(/,/g,'');
        totalAssetsAmt = CalcUtil.add(totalAssetsAmt,insuranceAmt);
        $("input[name=totalAssetsAmt]").val(totalAssetsAmt);
        //重新计算占比
		//现金
		var cashAmt = $("input[name=cashAmt]").val();
        cashAmt= cashAmt.replace(/,/g,'');
		var cashScale = CalcUtil.formatMoney(CalcUtil.divide(cashAmt,totalAssetsAmt)*100,2);
        $("input[name=cashScale]").val(cashScale);
        //银行存库
		var currentAccAmt = $("input[name=currentAccAmt]").val();
        currentAccAmt= currentAccAmt.replace(/,/g,'');
		var currentAccScale = CalcUtil.formatMoney(CalcUtil.divide(currentAccAmt,totalAssetsAmt)*100,2);
        $("input[name=currentAccScale]").val(currentAccScale);
        //应收账款
		var loanRecAmt = $("input[name=loanRecAmt]").val();
        loanRecAmt= loanRecAmt.replace(/,/g,'');
		var loanRecScale = CalcUtil.formatMoney(CalcUtil.divide(loanRecAmt,totalAssetsAmt)*100,2);
        $("input[name=loanRecScale]").val(loanRecScale);
        //预付款项
		var stockAmt = $("input[name=stockAmt]").val();
        stockAmt= stockAmt.replace(/,/g,'');
		var stockScale = CalcUtil.formatMoney(CalcUtil.divide(stockAmt,totalAssetsAmt)*100,2);
        $("input[name=stockScale]").val(stockScale);
        //存货
		var monetaryFundAmt = $("input[name=monetaryFundAmt]").val();
        monetaryFundAmt= monetaryFundAmt.replace(/,/g,'');
		var monetaryFundScale = CalcUtil.formatMoney(CalcUtil.divide(monetaryFundAmt,totalAssetsAmt)*100,2);
        $("input[name=monetaryFundScale]").val(monetaryFundScale);
        //流动资产小计
		var cashTotalAmt = $("input[name=cashTotalAmt]").val();
        cashTotalAmt= cashTotalAmt.replace(/,/g,'');
        var cashTotalScale = CalcUtil.formatMoney(CalcUtil.divide(cashTotalAmt,totalAssetsAmt)*100,2);
        $("input[name=cashTotalScale]").val(cashTotalScale);
        //固定资产
		var fundAmt = $("input[name=fundAmt]").val();
        fundAmt= fundAmt.replace(/,/g,'');
		var fundScale = CalcUtil.formatMoney(CalcUtil.divide(fundAmt,totalAssetsAmt)*100,2);
        $("input[name=fundScale]").val(fundScale);
        //其他经营资产
		var insuranceScale = CalcUtil.formatMoney(CalcUtil.divide(insuranceAmt,totalAssetsAmt)*100,2);
        $("input[name=insuranceScale]").val(insuranceScale);
	}
    //其他流动负债改变调用事件
    var _incomeTaxLoanAmtChange = function(){
        //其他流动负债
        var incomeTaxLoanAmt =  $("input[name=incomeTaxLoanAmt]").val();
        incomeTaxLoanAmt= incomeTaxLoanAmt.replace(/,/g,'');
        //短期负债小计
        var loanTotalAmt =  $("input[name=loanTotalAmt]").val();
        loanTotalAmt= loanTotalAmt.replace(/,/g,'');
        loanTotalAmt = CalcUtil.add(loanTotalAmt,incomeTaxLoanAmt);
        $("input[name=loanTotalAmt]").val(loanTotalAmt);
        //总负债
        var totalLiabilitiesAmt =  $("input[name=totalLiabilitiesAmt]").val();
        totalLiabilitiesAmt= totalLiabilitiesAmt.replace(/,/g,'');
        totalLiabilitiesAmt = CalcUtil.add(totalLiabilitiesAmt,incomeTaxLoanAmt);
        $("input[name=totalLiabilitiesAmt]").val(totalLiabilitiesAmt);
        //负债及所有者权益总计
        var personalAssetsAmt =  $("input[name=personalAssetsAmt]").val();
        personalAssetsAmt= personalAssetsAmt.replace(/,/g,'');
        personalAssetsAmt = CalcUtil.add(personalAssetsAmt,incomeTaxLoanAmt);
        $("input[name=personalAssetsAmt]").val(personalAssetsAmt);
        _refreshZhanBi(personalAssetsAmt);
    }
    //权益改变调用事件
    var _otherCurrentLoanAmtChange = function(){
        //其他流动负债
        var otherCurrentLoanAmt =  $("input[name=otherCurrentLoanAmt]").val();
        otherCurrentLoanAmt= otherCurrentLoanAmt.replace(/,/g,'');
        //负债及所有者权益总计
        var personalAssetsAmt =  $("input[name=personalAssetsAmt]").val();
        personalAssetsAmt= personalAssetsAmt.replace(/,/g,'');
        personalAssetsAmt = CalcUtil.add(personalAssetsAmt,otherCurrentLoanAmt);
        $("input[name=personalAssetsAmt]").val(personalAssetsAmt);
        _refreshZhanBi(personalAssetsAmt);
    }

    var _refreshZhanBi = function (totalLiabilitiesAmt) {
        //重新计算占比
        //应付账款
        var housingLoanAmt = $("input[name=housingLoanAmt]").val();
        housingLoanAmt= housingLoanAmt.replace(/,/g,'');
        var housingLoanScale = CalcUtil.formatMoney(CalcUtil.divide(housingLoanAmt,totalLiabilitiesAmt)*100,2);
        $("input[name=housingLoanScale]").val(housingLoanScale);
        //预收款项
        var carLoanAmt = $("input[name=carLoanAmt]").val();
        carLoanAmt= carLoanAmt.replace(/,/g,'');
        var carLoanScale = CalcUtil.formatMoney(CalcUtil.divide(carLoanAmt,totalLiabilitiesAmt)*100,2);
        $("input[name=carLoanScale]").val(carLoanScale);
        //信用卡负债
        var creditCardLoanAmt = $("input[name=creditCardLoanAmt]").val();
        creditCardLoanAmt= creditCardLoanAmt.replace(/,/g,'');
        var creditCardLoanScale = CalcUtil.formatMoney(CalcUtil.divide(creditCardLoanAmt,totalLiabilitiesAmt)*100,2);
        $("input[name=creditCardLoanScale]").val(creditCardLoanScale);
        //短期贷款及一年内长期贷款
        var pettyLoanAmt = $("input[name=pettyLoanAmt]").val();
        pettyLoanAmt= pettyLoanAmt.replace(/,/g,'');
        var pettyLoanScale = CalcUtil.formatMoney(CalcUtil.divide(pettyLoanAmt,totalLiabilitiesAmt)*100,2);
        $("input[name=pettyLoanScale]").val(pettyLoanScale);
        //i其他流动负债
        var incomeTaxLoanAmt = $("input[name=incomeTaxLoanAmt]").val();
        incomeTaxLoanAmt= incomeTaxLoanAmt.replace(/,/g,'');
        var incomeTaxLoanScale = CalcUtil.formatMoney(CalcUtil.divide(incomeTaxLoanAmt,totalLiabilitiesAmt)*100,2);
        $("input[name=incomeTaxLoanScale]").val(incomeTaxLoanScale);
        //短期负债小计
        var loanTotalAmt = $("input[name=loanTotalAmt]").val();
        loanTotalAmt= loanTotalAmt.replace(/,/g,'');
        var loanTotalScale = CalcUtil.formatMoney(CalcUtil.divide(loanTotalAmt,totalLiabilitiesAmt)*100,2);
        $("input[name=loanTotalScale]").val(loanTotalScale);
        //长期贷款
        var houseRentLoanAmt = $("input[name=houseRentLoanAmt]").val();
        houseRentLoanAmt= houseRentLoanAmt.replace(/,/g,'');
        var houseRentLoanScale = CalcUtil.formatMoney(CalcUtil.divide(houseRentLoanAmt,totalLiabilitiesAmt)*100,2);
        $("input[name=houseRentLoanScale]").val(houseRentLoanScale);
        //其它负债
        var otherLoanAmt = $("input[name=otherLoanAmt]").val();
        otherLoanAmt= otherLoanAmt.replace(/,/g,'');
        var otherLoanScale = CalcUtil.formatMoney(CalcUtil.divide(otherLoanAmt,totalLiabilitiesAmt)*100,2);
        $("input[name=otherLoanScale]").val(otherLoanScale);
        //长期负债小计
        var currentLoanTotalAmt = $("input[name=currentLoanTotalAmt]").val();
        currentLoanTotalAmt= currentLoanTotalAmt.replace(/,/g,'');
        var currentLoanTotalScale = CalcUtil.formatMoney(CalcUtil.divide(currentLoanTotalAmt,totalLiabilitiesAmt)*100,2);
        $("input[name=currentLoanTotalScale]").val(currentLoanTotalScale);

        //权益
        var otherCurrentLoanAmt = $("input[name=otherCurrentLoanAmt]").val();
        otherCurrentLoanAmt= otherCurrentLoanAmt.replace(/,/g,'');
        var otherCurrentLoanScale = CalcUtil.formatMoney(CalcUtil.divide(otherCurrentLoanAmt,totalLiabilitiesAmt)*100,2);
        $("input[name=otherCurrentLoanScale]").val(otherCurrentLoanScale);
    }
	return{
		init:_init,
		cashTotalAmt:_cashTotalAmt,
		cashTotalScale:_cashTotalScale,
		financeTotalAmt:_financeTotalAmt,
		financeTotalScale:_financeTotalScale,
		intangibleTotalAmt:_intangibleTotalAmt,
		intangibleTotalScale:_intangibleTotalScale,
		physicalTotalAmt:_physicalTotalAmt,
		physicalTotalScale:_physicalTotalScale,
		loanTotalAmt:_loanTotalAmt,
		loanTotalScale:_loanTotalScale,
		currentLoanTotalAmt:_currentLoanTotalAmt,
		currentLoanTotalScale:_currentLoanTotalScale,
		insertCusPersonLiabilitiesBase:_insertCusPersonLiabilitiesBase,
        otherCurrentLoanAmtChange:_otherCurrentLoanAmtChange,
        incomeTaxLoanAmtChange:_incomeTaxLoanAmtChange,
        insuranceAmtChange:_insuranceAmtChange,

	};
}(window, jQuery);


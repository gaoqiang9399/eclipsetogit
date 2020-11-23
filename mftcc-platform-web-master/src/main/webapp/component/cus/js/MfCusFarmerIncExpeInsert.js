;
var MfCusFarmerIncExpeInsert = function(window, $){
	
	//计算农户收支情况的收入合计
	var    _incSum   =   function (){
		var operateIncome = $("input[name=operateIncome]").val();//工资收入
		var otherIncome = $("input[name=otherIncome]").val();//其他收入
		var laborIncome = $("input[name=laborIncome]").val();//其他家庭成员收入
		var subsidyIncome = $("input[name=subsidyIncome]").val();//房屋租赁收入	
		
		var addTotal1 = CalcUtil.add(operateIncome.replace(/,/g,''),otherIncome.replace(/,/g,''));
		var addTotal2 = CalcUtil.add(laborIncome.replace(/,/g,''), subsidyIncome.replace(/,/g,''));
		//求和
		var totalIncome =CalcUtil.add(addTotal1,addTotal2);
		//赋值
		$("input[name=totalIncome]").val(totalIncome);
		_incomeNew();
	};
	
	//计算农户收支情况的支出合计
    var   _expeSum   =   function (){
    	var plantCost = $("input[name=plantCost]").val();//房租支出
		var feedCost = $("input[name=feedCost]").val();//水、电、气费支出
		var medicaOut = $("input[name=medicaOut]").val();//医疗支出
		var consumeOut = $("input[name=consumeOut]").val();//教育支出	
		
		var liveCost = $("input[name=liveCost]").val();//生活费支出
		var otherCost = $("input[name=otherCost]").val();//其他支出	
		
		var addTotal1 = CalcUtil.add(plantCost.replace(/,/g,''),feedCost.replace(/,/g,''));
		var addTotal2 = CalcUtil.add(medicaOut.replace(/,/g,''), consumeOut.replace(/,/g,''));
		var addTotal3 = CalcUtil.add(liveCost.replace(/,/g,''), otherCost.replace(/,/g,''));
		//求和
		var addTotal4 = CalcUtil.add(addTotal1,addTotal2);
		var totalOutgo = CalcUtil.add(addTotal3,addTotal4);
		
		//赋值
		$("input[name=totalOutgo]").val(totalOutgo);
		_incomeNew();
    	
     };
	
    //计算农户的家庭净收入
    var  _incomeNew  =   function (){
    	var     totalIncome =  $("input[name=totalIncome]").val();
    	var 	totalOutgo  =  $("input[name=totalOutgo]").val();
        //计算出家庭净收入
    	var income =	CalcUtil.subtract(totalIncome.replace(/,/g,''),totalOutgo.replace(/,/g,''));
    	//赋值
    	$("input[name=income]").val(income);
    }

    //计算收入合计
    var    _incomeSum   =   function (){
        var plantIncome = $("input[name=plantIncome]").val();//种植收入
        var feedIncome = $("input[name=feedIncome]").val();//养殖收入
        var operateIncome = $("input[name=operateIncome]").val();//经营收入
        var otherIncome = $("input[name=otherIncome]").val();//其他收入
        var laborIncome = $("input[name=laborIncome]").val();//劳务收入
        var subsidyIncome = $("input[name=subsidyIncome]").val();//补贴收入

        var addTotal1 = CalcUtil.add(operateIncome.replace(/,/g,''),otherIncome.replace(/,/g,''));
        var addTotal2 = CalcUtil.add(laborIncome.replace(/,/g,''), subsidyIncome.replace(/,/g,''));
        var addTotal3 = CalcUtil.add(plantIncome.replace(/,/g,''), feedIncome.replace(/,/g,''));
        //求和
        var totalIncome =CalcUtil.add(addTotal1,addTotal2);
        var totalIncomeFinal =CalcUtil.add(totalIncome,addTotal3);
        //赋值
        $("input[name=totalIncome]").val(totalIncomeFinal);
    };

    //计算净利润
    var    _netProfit   =   function (){
        var plantIncome = $("input[name=plantIncome]").val();//种植收入
        var otherIncome = $("input[name=otherIncome]").val();//其他收入
        var plantCost = $("input[name=plantCost]").val();//种植费用
        var addTotal1 = CalcUtil.add(plantIncome.replace(/,/g,''),otherIncome.replace(/,/g,''));
        var totalIncome = CalcUtil.subtract(addTotal1, plantCost.replace(/,/g,''));
        //净利润
        $("input[name=totalIncome]").val(totalIncome);
    };






		
	return{
		incSum:_incSum,
		expeSum:_expeSum,
        incomeSum:_incomeSum,
        netProfit:_netProfit
	};
}(window, jQuery);

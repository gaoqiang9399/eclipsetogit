
var PssQueryEntrance = function(window,$){
	var _initKeyDataCount = function(){
		$.ajax({
		 	url : webPath+"/pssReport/getGeneralPageKeyDataCount",
			data : {},
			type : 'post',
			dataType : 'json',
			async : false,
			success : function(data) {
				for(var i in data.generalPageKeyDataCountJson){
					switch(i){
						case '0':{
							$("#storeWarning span").html(data.generalPageKeyDataCountJson[i].storeWarningCount);
							break;
						}
						case '1':{
							$("#unSendSaleBill span").html(data.generalPageKeyDataCountJson[i].unSendSaleBillCount);
							break;
						}
						case '2':{
							$("#unExamBuyBill span").html(data.generalPageKeyDataCountJson[i].unExamBuyBillCount);
							break;
						}
						case '3':{
							$("#unExamSaleBill span").html(data.generalPageKeyDataCountJson[i].unExamSaleBillCount);
							break;
						}
						case '4':{
							$("#unExamBuyOrder span").html(data.generalPageKeyDataCountJson[i].unExamBuyOrderCount);
							break;
						}
					}
				}
			},
			error : function() {
				
			}
		 });
	};
	
	var _openLabel = function(parm){
		//$("#pssGeneralLi",window.parent.document).removeClass();
		//$("#pssBuyLi",window.parent.document).addClass("menu-active");
		switch(parm){
		case '1':{
			//_showTips(parm);
			//window.location = webPath+'/PssStoreStockAction_getListPageForWarn.action';
			window.parent.openBigForm(webPath+'/pssStoreStock/getListPageForWarn', '商品库存预警', function(){
				
			});
			break;
		}
		case '2':{
			$("#pssGeneralLi",window.parent.document).attr("class","");
			$("#pssSaleLi",window.parent.document).attr("class","menu-active");
			window.location = webPath+'/pssSaleOrder/getListPage?orderState=01,02&auditStsed=1&enabledStatus=1';
			break;
		}
		case '3':{
			$("#pssGeneralLi",window.parent.document).attr("class","");
			$("#pssBuyLi",window.parent.document).attr("class","menu-active");
			window.location = webPath+'/pssBuyOrder/getListPage?auditStsed=0';
			break;
		}
		case '4':{
			$("#pssGeneralLi",window.parent.document).attr("class","");
			$("#pssSaleLi",window.parent.document).attr("class","menu-active");
			window.location = webPath+'/pssSaleOrder/getListPage?auditStsed=0';
			break;
		}
		case '5':{
			$("#pssGeneralLi",window.parent.document).attr("class","");
			$("#pssBuyLi",window.parent.document).attr("class","menu-active");
			window.location = webPath+'/pssBuyBill/getListPage?auditStsed=0';
			break;
		}
		}
		
	};
	
	var _openQuickLink = function(parm){
		switch(parm){
		case '1':{
			$("#pssGeneralLi",window.parent.document).attr("class","");
			$("#pssBuyLi",window.parent.document).attr("class","menu-active");
			window.location = webPath+'/pssBuyBill/getListPage';
			break;
		}
		case '2':{
			$("#pssGeneralLi",window.parent.document).attr("class","");
			$("#pssSaleLi",window.parent.document).attr("class","menu-active");
			window.location = webPath+'/pssSaleBill/getListPage';
			break;
		}
		case '3':{
			$("#pssGeneralLi",window.parent.document).attr("class","");
			$("#pssStoreLi",window.parent.document).attr("class","menu-active");
			window.location = webPath+'/pssAlloTransBill/getListPage';
			break;
		}
		case '4':{
			$("#pssGeneralLi",window.parent.document).attr("class","");
			$("#pssFundLi",window.parent.document).attr("class","menu-active");
			window.location = webPath+'/pssReceiptBill/getListPage';
			break;
		}
		case '5':{
			$("#pssGeneralLi",window.parent.document).attr("class","");
			$("#pssStoreLi",window.parent.document).attr("class","menu-active");
			window.location = webPath+'/pssCheckStockBill/getListPage';
			break;
		}
		case '6':{
			$("#pssGeneralLi",window.parent.document).attr("class","");
			$("#pssBuyLi",window.parent.document).attr("class","menu-active");
			window.location = webPath+'/pssSaleOrderDetail/getToBuyOrderListPage';
			break;
		}
		case '7':{
			$("#pssGeneralLi",window.parent.document).attr("class","");
			$("#pssFundLi",window.parent.document).attr("class","menu-active");
			window.location = webPath+'/pssPaymentBill/getListPage';
			break;
		}
		case '8':{
			$("#pssGeneralLi",window.parent.document).attr("class","");
			$("#pssFundLi",window.parent.document).attr("class","menu-active");
			window.location = webPath+'/pssCancelVerificationBill/getListPage';
			break;
		}
		}
	};
	
	var _initReport = function() {
		var eChartContainer ;
		var myChart ;
		var option ;
		
		var dateData = ["暂无数据"];
		var amountData = ["暂无数据"];
		
		$.ajax({
		 	url : webPath+"/pssReport/reportQueryForGraph",
			data : {reportId:'Pss-Sale-Total-Amount'},
			type : 'post',
			dataType : 'json',
			async : false,
			success : function(data) {
				if(data.flag == 'success'){
					if(data.data2 != '' && data.data1 != ''){
						dateData = data.data2;
						amountData = data.data1;
					}
				}
			},
			error : function() {
				
			}
		 });
		
		eChartContainer = document.getElementById('mine-sale1');
			var resizeEChartContainer = function () {
				eChartContainer.style.width = 1000+'px';
				eChartContainer.style.height = 300+'px';
				eChartContainer.style.margin = -23+'px';
				eChartContainer.style.top = 40+'px';
				eChartContainer.style.position = 'relative';
			};
			resizeEChartContainer();
			
			myChart = echarts.init(eChartContainer);

			option = {
			    title: {
			        text: '近7日销售对比分析',
			        x:'center',
			        y:'top',
			        textAlign:'left'
			    },
			    tooltip: {},
			    legend: {
			        //data:['销量'],
			        //height: eChartContainer.style.height,
			        //width: eChartContainer.style.width,
			        //left: 'center',
			        //padding: [10000, 0, 1000,0]
			    },
			    xAxis: {
			        data: dateData
			    },
			    yAxis: {},
			    series: [{
			        name: '销量',
			        type: 'bar',
			        data: amountData
			    }]
			};
			myChart.setOption(option);

			window.onresize = function () {
				resizeEChartContainer();
			    myChart.resize();
			};
	};
	var _init = function(){
		$("#home-quick-link").css("display","block");
		$("#home-key-data").css("display","none");
			
		var eChartContainer ;
		var myChart ;
		var option ;
        var resizeEChartContainer;
		
		//销货、购货、仓库选项卡绑定点击事件
		$(".mysearch1 .search-title li").click(function(){
	 		$(".mysearch1 .search-title li").find("a").removeClass("active");
	 		$(this).find("a").addClass("active");
	 		var thisId = $(this).attr("id");
	 		$(".mysearch1 .mysearch .mysearch-content").css("display","none");
	 		if(thisId=="menu-sale"){//销货
	 			
	 			var dateData = ["暂无数据"];
	 			var amountData = ["暂无数据"];
	 			
	 			$.ajax({
				 	url : webPath+"/pssReport/reportQueryForGraph",
					data : {reportId:'Pss-Sale-Total-Amount'},
					type : 'post',
					dataType : 'json',
					async : false,
					success : function(data) {
						if(data.flag == 'success'){
							if(data.data2 != '' && data.data1 != ''){
								dateData = data.data2;
								amountData = data.data1;
							}
						}
					},
					error : function() {
						
					}
				 });
	 			
	 			eChartContainer = document.getElementById('mine-sale1');
	 				resizeEChartContainer = function() {
	 				eChartContainer.style.width = 1000+'px';
	 				eChartContainer.style.height = 300+'px';
	 				eChartContainer.style.margin = -23+'px';
	 				eChartContainer.style.top = 40+'px';
	 				eChartContainer.style.position = 'relative';
	 			};
	 			resizeEChartContainer();
	 			
	 			myChart = echarts.init(eChartContainer);

	 			option = {
	 			    title: {
	 			        text: '近7日销售对比分析',
	 			        x:'center',
	 			        y:'top',
	 			        textAlign:'left'
	 			    },
	 			    tooltip: {},
	 			    legend: {
	 			        //data:['销量'],
	 			        height: eChartContainer.style.height,
	 			        width: eChartContainer.style.width,
	 			        left: 'center',
	 			        padding: [10000, 0, 1000,0]
	 			    },
	 			    xAxis: {
	 			        data: dateData
	 			    },
	 			    yAxis: {},
	 			    series: [{
	 			        name: '销量',
	 			        type: 'bar',
	 			        data: amountData
	 			    }]
	 			};
	 			myChart.setOption(option);

	 			window.onresize = function () {
	 				resizeEChartContainer();
	 			    myChart.resize();
	 			};
	 			$("#mine-sale").css("display","block");
	 			$("#mine-buy").css("display","none");
	 			$("#mine-store").css("display","none");
	 		}else if(thisId=="menu-buy"){//购货
	 			var dataList = '';
	 			var strDiv = '';
	 			$("#mine-buy").empty();
	 			$.ajax({
				 	url : webPath+"/pssReport/reportQueryForGraph",
					data : {reportId:'Pss-Buy-Bill-Amount-Rank'},
					type : 'post',
					dataType : 'json',
					async : false,
					success : function(data) {
						if(data.flag == 'success'){
							if(data.pssBuyBillAmountRankList != ''){
								dataList = data.pssBuyBillAmountRankList;
							}
						}
					},
					error : function() {
						
					}
				 });
                var i;
	 			if(dataList != ''){
	 				strDiv = "<div style=\"text-align: center; font-weight: bold; margin: 20px 0 5px 0;font-size:16px;\">近7日购货金额排名</div>";
	 				strDiv = strDiv + "<div id=\"purchaseBarLeft\" style=\"float:left;width:50%;height:241px\">";
	 				for(i = 0; i < 5 ; i++){
	 					if(i+1 == 1){
	 						if(dataList[i].name != '暂无商品信息'){
	 							strDiv = strDiv + "<div class=\"itemWrap\"><div class=\"page\"></div><div><span class=\"index i  i-eval\" style=\"color: gold\">"+(i+1)+"</span><div class=\"item\"><div class=\"name\">"+dataList[i].name+"</div><div class=\"total\">¥"+_fmoney(dataList[i].value,2)+"</div></div></div></div>";
	 						}else{
	 							strDiv = strDiv + "<div class=\"itemWrap\"><div class=\"page\"></div><div><span class=\"index i  i-eval\" style=\"color: gold\">"+(i+1)+"</span><div class=\"item\"><div class=\"name\">"+dataList[i].name+"</div><div class=\"total\"></div></div></div></div>";
	 						}
	 					}else if(i+1 == 2){
	 						if(dataList[i].name != '暂无商品信息'){
	 							strDiv = strDiv + "<div class=\"itemWrap\"><div class=\"page\"></div><div><span class=\"index i  i-eval\" style=\"color: #CFCFCF\">"+(i+1)+"</span><div class=\"item\"><div class=\"name\">"+dataList[i].name+"</div><div class=\"total\">¥"+_fmoney(dataList[i].value,2)+"</div></div></div></div>";
	 						}else{
	 							strDiv = strDiv + "<div class=\"itemWrap\"><div class=\"page\"></div><div><span class=\"index i  i-eval\" style=\"color: CFCFCF\">"+(i+1)+"</span><div class=\"item\"><div class=\"name\">"+dataList[i].name+"</div><div class=\"total\"></div></div></div></div>";
	 						}
	 					}else if(i+1 == 3){
	 						if(dataList[i].name != '暂无商品信息'){
	 							strDiv = strDiv + "<div class=\"itemWrap\"><div class=\"page\"></div><div><span class=\"index i  i-eval\" style=\"color: #CD8500\">"+(i+1)+"</span><div class=\"item\"><div class=\"name\">"+dataList[i].name+"</div><div class=\"total\">¥"+_fmoney(dataList[i].value,2)+"</div></div></div></div>";
	 						}else{
	 							strDiv = strDiv + "<div class=\"itemWrap\"><div class=\"page\"></div><div><span class=\"index i  i-eval\" style=\"color: CD8500\">"+(i+1)+"</span><div class=\"item\"><div class=\"name\">"+dataList[i].name+"</div><div class=\"total\"></div></div></div></div>";
	 						}
	 					}else{
	 						if(dataList[i].name != '暂无商品信息'){
	 							strDiv = strDiv + "<div class=\"itemWrap\"><div class=\"page\"></div><div><span class=\"index\">"+(i+1)+"</span><div class=\"item\"><div class=\"name\">"+dataList[i].name+"</div><div class=\"total\">¥"+_fmoney(dataList[i].value,2)+"</div></div></div></div>";
	 						}else{
	 							strDiv = strDiv + "<div class=\"itemWrap\"><div class=\"page\"></div><div><span class=\"index\">"+(i+1)+"</span><div class=\"item\"><div class=\"name\">"+dataList[i].name+"</div><div class=\"total\"></div></div></div></div>";
	 						}
	 					}
	 				}
	 				strDiv = strDiv + "</div>";
	 				
	 				strDiv = strDiv + "<div id=\"purchaseBarRight\" style=\"float:left;width:50%;height:241px\">";
	 				for(i = 5; i < 10 ; i++){
	 					if(dataList[i].name != '暂无商品信息'){
	 						strDiv = strDiv + "<div class=\"itemWrap\"><div class=\"page\"></div><div><span class=\"index\">"+(i+1)+"</span><div class=\"item\"><div class=\"name\">"+dataList[i].name+"</div><div class=\"total\">¥"+_fmoney(dataList[i].value,2)+"</div></div></div></div>";
	 					}else{
	 						strDiv = strDiv + "<div class=\"itemWrap\"><div class=\"page\"></div><div><span class=\"index\">"+(i+1)+"</span><div class=\"item\"><div class=\"name\">"+dataList[i].name+"</div><div class=\"total\"></div></div></div></div>";
 						}
	 				}
	 				strDiv = strDiv + "</div>";
	 				
	 			}else{
	 				strDiv = "<div style=\"text-align: center; font-weight: bold; margin: 20px 0 5px 0;font-size:16px;\">近7日购货金额排名</div>";
	 				strDiv = strDiv + "<div id=\"purchaseBarLeft\" style=\"float:left;width:50%;height:241px\">";
	 				for(i = 1; i <= 5 ; i++){
	 					if(i == 1){
	 						strDiv = strDiv + "<div class=\"itemWrap\"><div class=\"page\"></div><div><span class=\"index i  i-eval\" style=\"color: gold\">"+i+"</span><div class=\"item\"><div class=\"name\">暂无商品信息</div><div class=\"total\"></div></div></div></div>";
	 					}else if(i == 2){
	 						strDiv = strDiv + "<div class=\"itemWrap\"><div class=\"page\"></div><div><span class=\"index i  i-eval\" style=\"color: #CFCFCF\">"+i+"</span><div class=\"item\"><div class=\"name\">暂无商品信息</div><div class=\"total\"></div></div></div></div>";
	 					}else if(i == 3){
	 						strDiv = strDiv + "<div class=\"itemWrap\"><div class=\"page\"></div><div><span class=\"index i  i-eval\" style=\"color: #CD8500\">"+i+"</span><div class=\"item\"><div class=\"name\">暂无商品信息</div><div class=\"total\"></div></div></div></div>";
	 					}else{
	 						strDiv = strDiv + "<div class=\"itemWrap\"><div class=\"page\"></div><div><span class=\"index\">"+i+"</span><div class=\"item\"><div class=\"name\">暂无商品信息</div><div class=\"total\"></div></div></div></div>";
	 					}
	 				}
	 				strDiv = strDiv + "</div>";
	 				
	 				strDiv = strDiv + "<div id=\"purchaseBarRight\" style=\"float:left;width:50%;height:241px\">";
	 				for(i = 6 ; i <= 10 ; i++){
	 					strDiv = strDiv + "<div class=\"itemWrap\"><div class=\"page\"></div><div><span class=\"index\">"+i+"</span><div class=\"item\"><div class=\"name\">暂无商品信息</div><div class=\"total\"></div></div></div></div>";
	 				}
	 				strDiv = strDiv + "</div>";
	 			}
	 			
	 			$("#mine-buy").append(strDiv);
	 			
	 			$("#mine-buy").css("display","block");
	 			$("#mine-sale").css("display","none");
	 			$("#mine-store").css("display","none");
	 		}else if(thisId=="menu-store"){//仓库
	 			var data1 = ['暂无数据'];
	 			var data2 = [{value:0,name:'暂无数据'}];
	 			$.ajax({
				 	url : webPath+"/pssReport/reportQueryForGraph",
					data : {reportId:'Pss-Stock-Count-Distribute'},
					type : 'post',
					dataType : 'json',
					async : false,
					success : function(data) {
						if(data.flag == 'success'){
							 if(data.data1 != ''){
								 data1 = data.data1;
								 data2 = data.data2;
							 }
						}
					},
					error : function() {
						
					}
				 });
	 			
	 			eChartContainer = document.getElementById('mine-store1');
	 				resizeEChartContainer = function () {
	 				eChartContainer.style.width = 1000+'px';
	 				eChartContainer.style.height = 300+'px';
	 				eChartContainer.style.margin = 20+'px';
	 				eChartContainer.style.top = 40+'px';
	 				eChartContainer.style.position = 'relative';
	 			};
	 			resizeEChartContainer();
	 			
	 			myChart = echarts.init(eChartContainer);

	 			option = {
 				    title : {
 				    	text: '库存数量分布',
 				        //subtext: '',
 				        x:'center'
 				    },
 				    tooltip : {
 				        trigger: 'item',
 				        formatter: "{a} <br/>{b} : {c} ({d}%)"
 				    },
 				    legend: {
 				        orient: 'vertical',
 				        left: 'left',
 				        data:  data1
 				    },
 				    series : [
 				        {
 				            name: '访问来源',
 				            type: 'pie',
 				            radius : '55%',
 				            center: ['50%', '60%'],
 				            data: data2,
 				            itemStyle: {
 				                emphasis: {
 				                    shadowBlur: 10,
 				                    shadowOffsetX: 0,
 				                    shadowColor: 'rgba(0, 0, 0, 0.5)'
 				                }
 				            }
 				        }
 				    ]
 				};
	 			myChart.setOption(option);

	 			window.onresize = function () {
	 				resizeEChartContainer();
	 			    myChart.resize();
	 			};
	 			
	 			$("#mine-store").css("display","block");
	 			$("#mine-sale").css("display","none");
	 			$("#mine-buy").css("display","none");
	 		}

	 	});
		
		$(".mysearch2 .search-title li").click(function(){
			$(".mysearch2 .search-title li").removeClass("li-active");
	 		$(this).addClass("li-active");
	 		var thisId = $(this).attr("id");
	 		$(".mysearch2 .mysearch .mysearch-content").css("display","none");
	 		if(thisId == 'menu-fuc'){
	 			$("#home-quick-link").css("display","block");
	 			$("#home-key-data").css("display","none");
	 		}else if(thisId == 'menu-data'){
	 			
	 			$.ajax({
	 			 	url : webPath+"/pssReport/getGeneralPageMenuData",
	 				data : {},
	 				type : 'post',
	 				dataType : 'json',
	 				async : false,
	 				success : function(data) {
	 					for(var i in data.generalPageMenuDataJson){
	 						switch(i){
	 						case '0':{
	 							$("#storeCountValue").html(data.generalPageMenuDataJson[i].storeCount);
	 							break;
	 						}
	 						case '1':{
	 							$("#storeCostValue").html(_fmoney(data.generalPageMenuDataJson[i].storeCost,2));
	 							break;
	 						}
	 						case '2':{
	 							$("#cashValue").html(_fmoney(data.generalPageMenuDataJson[i].cash,2));
	 							break;
	 						}
	 						case '3':{
	 							$("#depositValue").html(_fmoney(data.generalPageMenuDataJson[i].deposit,2));
	 							break;
	 						}
	 						case '4':{
	 							$("#cusDebtValue").html(_fmoney(data.generalPageMenuDataJson[i].cusDebt,2));
	 							break;
	 						}
	 						case '5':{
	 							$("#supplierDebtValue").html(_fmoney(data.generalPageMenuDataJson[i].supplierDebt,2));
	 							break;
	 						}
	 						case '6':{
	 							$("#saleIncomeValue").html(_fmoney(data.generalPageMenuDataJson[i].saleIncome,2));
	 							break;
	 						}
	 						case '7':{
	 							$("#saleGrossValue").html(_fmoney(data.generalPageMenuDataJson[i].saleGross,2));
	 							break;
	 						}
	 						case '8':{
	 							$("#buyAmountValue").html(_fmoney(data.generalPageMenuDataJson[i].buyAmount,2));
	 							break;
	 						}
	 						case '9':{
	 							$("#goodsTypeValue").html(data.generalPageMenuDataJson[i].goodsType);
	 							break;
	 						}
	 						}
	 					}
	 				},
	 				error : function() {
	 					
	 				}
	 			 });
	 			
	 			$("#home-quick-link").css("display","none");
	 			$("#home-key-data").css("display","block");
	 			//_checkNavigatorType();
	 		}
		});
		
	};
	
	function _fmoney(s, n) {
		var flag = 0;
		if(s < 0){
			s = Math.abs(s);
			flag = 1;
		}
		n = n > 0 && n <= 20 ? n : 2; 
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1]; 
		t = ""; 
		for (var i = 0; i < l.length; i++) { 
			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		}
		if(flag != 1){
			return t.split("").reverse().join("") + "." + r;
		}else{
			return "-" + t.split("").reverse().join("") + "." + r;
		}
	} 
	
	var tipsTimeoutId = 5;
	var _showTips = function (obj) {
		top.LoadingAnimate.stop();
		var d = dialog({
			id : "oaInBuilding",
			content : "正在建设中，敬请期待。",
			padding : "3px"
		}).show(obj);
		if (tipsTimeoutId) {
			clearTimeout(tipsTimeoutId);
		}
		tipsTimeoutId = setTimeout(function() {
			d.close().remove();
		}, 1000);
	};
	
	function _checkNavigatorType(){
		if(window.navigator.userAgent.indexOf("Opera") > -1){
			$(".home-key-data-li").css("width","145%");
		}else if(window.navigator.userAgent.indexOf("Firefox") > -1){
			$(".home-key-data-li").css("width","145%");
		}else if(window.navigator.userAgent.indexOf("Chrome") > -1){
			$(".home-key-data-li").css("width","145%");
		}else if(window.navigator.userAgent.indexOf("Safari") > -1){
			$(".home-key-data-li").css("width","145%");
		}else{
			
		}
	}

	return{ 
		init:_init,
		initReport:_initReport,
		openLabel:_openLabel,
		openQuickLink:_openQuickLink,
		showTips:_showTips,
		initKeyDataCount:_initKeyDataCount,
		checkNavigatorType:_checkNavigatorType
	};
	 
}(window,jQuery);
var mfCusCreditOpenHisData = function(window,$){
	var _init = function(){
		$(".creditbeginDate").html(beginDate);
		$(".creditendDate").html(endDate);
		$("body").mCustomScrollbar({
			advanced:{
				updateOnContentResize:true
			}
		});
		//审批结束后展示授信历史
		if(creditSts>=3){
			$("#authHisInfo").show();
			if(creditSts==5){//已签约,展示合同明细，并支持补录合同明细
				$("#pactDetailListInfo").removeClass("hide");
				$("#pactDetailListInfo").addClass("show");
				//立项授信展示资金机构合同信息块
				if(creditModel == "2"){
					$("#childPactListInfo").removeClass("hide");
					$("#childPactListInfo").addClass("show");
				}
			}
		}
		$.each(showParmsJson,function(i,node){
			if(node=="collateral"){
				$("div[name=authPlege]").show();
			}
		});
		//展示审批历史
		if(creditApproveId!=""&&creditApproveId!="null"){
			showWkfFlowVertical($("#wj-modeler2"),creditApproveId,"1","credit_approval");
			$("div[name=authApproveInfo]").show();
		}
		_showCreditAmtLineChart();
		_showPledgeInfo();
	};
	var _showCreditTemplateInfo=function(node){
		var tempType="REPORT";
		var htmlStr = "";
		if(node=="report"){
			htmlStr = '<button type="button" class="btn btn-primary" onclick="mfCusCreditOpenHisData.openDocTemplate(\''+tempType+'\');" style="border-radius: 7px;padding: 6px 54px;background-color: #b7c5c8;border-color: #b7c5c8;margin-top:-4%">尽职报告</button>';
			$(".reportTemplate").html(htmlStr);
		}
		if(node=="protocolPrint"){
			tempType="PROTOCOL";
			htmlStr = '<button type="button" class="btn btn-primary" onclick="mfCusCreditOpenHisData.openDocTemplate(\''+tempType+'\');" style="border-radius: 7px;padding: 6px 54px;background-color: #b7c5c8;border-color: #b7c5c8; margin-top:-4%">授信协议</button>';
			$(".pactTemplate").html(htmlStr);
		}
	}
	//REPORT
	var _openDocTemplate = function(tempType){
		$.ajax({
				url: webPath+"/mfCusCreditApply/getIfSaveModleInfo",
				type:"post",
				dataType:"json",
				data:{
					relId:appId,
					creditAppId:appId,
					tempType:tempType
				},
				error:function(){
					alert('error');
				},
				success:function(data){
					var type = "detail";
					var filepath = "/component/model/docword/";
					var filename = data.filename;
					var templateNo=data.templateNo;
					var returnUrl = window.location.href;
					var urlParm=returnUrl.split("?")[1];
					returnUrl=returnUrl.split("?")[0];
					urlParm = encodeURIComponent(urlParm);
					window.location.href = webPath+"component/model/toPageOffice.jsp?filename="+filename+"&templateNo="+templateNo+"&returnUrl="+returnUrl+"&type="+type+"&filepath="+filepath+"&urlParm="+urlParm;
				}
			});
	};
	//展示用信历史折线图
	var _showCreditAmtLineChart=function(){
		var data=null;
		$.each(creditHisJsonObject,function(name,value){
			data={};
			data["name"]=name;
			data["data"]=value;
			series.push(data);
		});
		var categoriesData=[];
		$.each(dateValuesJsonObject,function(name,value){
			categoriesData.push(value);
		});
		var wjChart;
		 wjChart = new Highcharts.Chart({
			 chart: {
	                renderTo: 'productInfo',
	                type: 'spline',
					borderWidth:3,
					borderColor:'#E5E5E5',
					borderRadius:0
	            },
				credits:{
					enabled:false
				},
	            title: {
	                text: '客户授信统计',
	                style:{color:'#3d4750',fontSize:'14px',fontWeight:'bold'}
	            },
	            legend:{
	            	itemStyle:{color: '#696969',
								fontWeight: 'bold',
								fontSize: '12px',
								fontFamily: '微软雅黑'
	            		}
	            },
	            xAxis: {
	               	categories: categoriesData,
/*	                    categories: ['一月', '二月', '三月', '四月', '五月', '六月',
	                                 '七月', '八月', '九月', '十月', '十一月', '十二月'],
*/					gridLineWidth: 1,
					lineColor: '#000',
					tickColor: '#000',
					labels: {
						style: {
							color: '#3d4750',
							fontWeight: 'bold',
							fontSize: '12px',
							fontFamily: '微软雅黑'
						}
					}
		        },
	             yAxis: {
					// 坐标最小值
					min:0,
					// 小网格线
					minorTickInterval: 'auto',
					lineColor: '#000',
					lineWidth: 0.5,
					minorTickInterval: 'auto',
					labels: {
	                    formatter: function() {
	                        return this.value;
	                    }
	                },
		 			title: {
		 				style: {
		 					color: '#696969',
							fontWeight: 'bold',
							fontSize: '12px',
							fontFamily: '微软雅黑'
						},
				 		text: '金额(万元)'
					}
	            },
	             tooltip: {
	            	crosshairs: true,
	            	shared: true
	        	},
	            plotOptions: {
	                line: {
	                    dataLabels: {
	                        enabled: true
	                    },
	                    enableMouseTracking: false
	                }
	            },
	            series:series
	            /*series:[{
	                name: '月放款',
	                data: [300.00, 1000.00, 1500.00, 890.00, 2000.00, 1100.00, 1240, 1030.00, 670.00, 2000.00, 1800.00, 1455.00]
	            }, {
	                name: '月回收',
	                data: [200.00, 1500.03, 800.02, 100.00, 1000.00, 600.00, 1000.00, 1300.00, 1000.00, 700.00, 900.00, 1000.00]
	            }]*/
		 });
	}
	//展示担保信息
	var _showPledgeInfo=function(){
		jQuery.ajax({
			url:webPath+"/mfBusCollateralRel/getCollateralListHtmlAjax",
			data:{appId:appId,relId:appId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag=="success"){
					var htmlStr="";
					$.each(data.htmlMap,function(index,obj){
							var collateralId = index;
							htmlStr=htmlStr+obj;
					});
					$("#pedgeInfoform").html(htmlStr);
					$("#creditPledgeInfo").show();
				}
				top.LoadingAnimate.stop();
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
	
	//跳转到担保信息详情
	var _toCollateralDetail = function(){
		top.toCollateralDetail = true;
		top.creditAppId = creditAppId;
		top.appId = appId;
		myclose_click();
	};
	//授信历史详细信息
	var _queryCreditHisDetail = function(obj,url){
		//MfCusCreditApplyAction_queryCreditHisDetail.action?creditAppId=SX17062723105430410
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.openBigForm(url, '授信历史详情信息', function() {});
	};
	//关闭
	var _close = function(){
		myclose_click();
	};
	//授信历史详细信息
	var _showCreditPactDetail = function(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.openBigForm(url, '立项授信合同', function() {});
	};
	
	//授信文件归档
	var _creditFileArchive = function(){
		var archivePactStatus = "01";
		/*if(pactSts=="7"){
			archivePactStatus="02";
		}*/
		var dataParam = '?optType=01&archivePactStatus='+archivePactStatus+'&cusNo='+cusNo+'&appId='+appId+'&pactId='+pactId+'&pleId='+pleId;
		top.window.openBigForm(webPath+'/archiveInfoMain/getArchiveNodes'+dataParam,"文件归档",function(){});
	};
	return{
		init:_init,
		close:_close,
		queryCreditHisDetail:_queryCreditHisDetail,
		openDocTemplate:_openDocTemplate,
		showCreditPactDetail:_showCreditPactDetail,
		toCollateralDetail:_toCollateralDetail
	};
}(window,jQuery);
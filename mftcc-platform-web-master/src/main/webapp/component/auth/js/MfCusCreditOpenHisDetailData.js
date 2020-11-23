var mfCusCreditOpenHisDetailData = function(window,$){
	var _init = function(){
//		showWkfFlow($("#wj-modeler2"),creditApproveId);
		showWkfFlowVertical($("#wj-modeler2"),creditApproveId,"1","credit_approval");
		$(".container").mCustomScrollbar({
			advanced:{
				updateOnContentResize:true
			}
		});
		$(".creditbeginDate").html(beginDate);
		$(".creditendDate").html(endDate);
		$("body").mCustomScrollbar({
			advanced:{
				updateOnContentResize:true
			}
		});
		_showProductInfo();
		_showPledgeInfo();
		$("div[name=creditTemplateInfo]").show();
		var tempType="REPORT";
		htmlStr = '<button type="button" class="btn btn-primary" onclick="mfCusCreditOpenHisDetailData.openDocTemplate(\''+appId+'\',\''+tempType+'\');" style="border-radius: 7px;padding: 6px 54px;background-color: #b7c5c8;border-color: #b7c5c8;margin-top:-4%">尽职报告</button>';
		$(".reportTemplate").html(htmlStr);
		$.each(showParmsJson,function(i,node){
			if(node=="protocolPrint"){
				$("div[name=authProtocolInfo]").show();
				tempType="PROTOCOL";
				htmlStr = '<button type="button" class="btn btn-primary" onclick="mfCusCreditOpenHisDetailData.openDocTemplate(\''+pactId+'\',\''+tempType+'\');" style="border-radius: 7px;padding: 6px 54px;background-color: #b7c5c8;border-color: #b7c5c8; margin-top:-4%">授信协议</button>';
				$(".pactTemplate").html(htmlStr);
			}
		});
	};
	var _openDocTemplate = function(relId,tempType){
		$.ajax({
				url: webPath+"/mfCusCreditApply/getIfSaveModleInfo",
				type:"post",
				dataType:"json",
				data:{
					relId:relId,
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
					window.location.href = webPath+"/component/model/toPageOffice.jsp?filename="+filename+"&templateNo="+templateNo+"&returnUrl="+returnUrl+"&type="+type+"&filepath="+filepath+"&urlParm="+urlParm;
				}
			});
	};
	var _showProductInfo=function(){
		var myChart = echarts.init(document.getElementById("productInfo"));
		myChart.showLoading({  
            text: '正在努力加载中...'  
        });
		// 指定图表的配置项和数据
		var option = {
			color: ['#3398DB'],
			tooltip : { 
				show: true,
				trigger: "axis",
				showDelay: 0, 			  // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
				axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：‘line‘ | ‘shadow‘
                }
			},
			grid:{
                x:75,
                y:45,
                x2:50,
                y2:130,
                borderWidth:1
            },
			xAxis : {
				data : productTitle,
				type : 'category',     //横轴类型 有'category'类目型，'value'数值型，'time'时间型
			    axisLabel:{
					interval:0,
					rotate:45,     //旋转多少度
					margin:2,
					textStyle:{
						color:"#222"
					}
				}
			},
			yAxis : {},
			series : [ {
				name : '授信额度',
				type : 'bar',
				barWidth:20,     //柱子宽度
				data : productValue
			} ]
		};
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
		myChart.hideLoading();
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
				if(data.flag="success"){
					var htmlStr="";
					$.each(data.htmlMap,function(index,obj){
							var collateralId = index;
							htmlStr=htmlStr+obj;
					});
					$("#pedgeInfoform").html(htmlStr);
				}
				top.LoadingAnimate.stop();
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
	//审批信息
	var _getSPInfo = function(){
		$.ajax({
			type: "post",
			data:{appNo:appId},
			dataType: 'json',
			url: webPath+"/wkfApprovalOpinion/getApplyApprovalOpinionList",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				Wkf_zTreeNodes=data.zTreeNodes;
				Wkf_zTreeObj = $.fn.zTree.init($("#wfTree"), Wkf_setting, Wkf_zTreeNodes);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {}
		});
	};
	//关闭
	var _close = function(){
		myclose();
	};
	return{
		init:_init,
		close:_close,
		getSPInfo:_getSPInfo,
		openDocTemplate:_openDocTemplate
	};
}(window,jQuery);
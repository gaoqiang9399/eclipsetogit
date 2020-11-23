;
var MfBusApply_RiskReport = function(window, $) {
	var _init = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/mfThirdServiceRecord/findByPageAjax?cusNo="+cusNo+"&appId="+appId,//列表数据查询的url
			tableId : "tableriskreportloglist",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight:235
		});
		//三方数据查询类型初始化
		$.each(typeList, function(i, parmDic) {
			$("#serviceType").append('<li role="presentation" onclick="MfBusApply_RiskReport.queryThirdData(\''+parmDic.optCode+'\',\''+parmDic.optName+'\');" style="cursor:pointer;"><a role="menuitem">'+parmDic.optName+'</a></li>');
		});
		$(".dropdown-menu").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		});
		
	};
	
	var _doCommitAjax = function(){
		$.ajax({
			url : webPath+"/mfBusApply/riskReportAjax",
			data : {appId:appId,wkfAppId:wkfAppId},
			success : function(data) {
				if (data.flag == "success") {
					window.top.alert(data.msg, 3);
					top.flag = true;
					myclose_click();
				} 
			},error : function() {
				alert(top.getMessage("ERROR_SERVER"),0);
			}
		});
	};
	var _queryThirdData = function(type,name){
		if(type=="honeybee"){//需特殊处理(蜜蜂 )
			top.window.openBigForm(webPath+"/thirdJxlBeeRecord/input?appId=" + appId +"&cusNo="+cusNo+"&from=tcph",'蜜蜂报告', function(){
				updateTableData();
			});
		}else if(type=="che300"){
			top.window.openBigForm(webPath+"/mfPledgeCarEval/input?appId=" + appId +"&cusNo="+cusNo+"&from=tcph",'车辆评估价查询', myclose);
		}else if(type=="onlinebook"){
			top.window.openBigForm(webPath+"/mfThirdServiceRecord/jumpNetworkPage?url="+encodeURI("http://onlinebook.szreorc.com:8888/onlinebook/"),'不动产登记预约', function(){
			});
		}else if(type=="guoce"){
			top.window.openBigForm(webPath+"/mfThirdServiceRecord/jumpNetworkPage?url="+encodeURI("http://gj.guocedc.cn/appraisal/auto.shtml"),'国策评估', function(){
			});
		}else{
			top.window.openBigForm(webPath+"/mfBusApply/thirdInput?appId=" + appId +"&cusNo="+cusNo+"&ajaxData="+type,name, function(){
				updateTableData();
			});
		}
	};
	//重新查询
	var _againQueryThirdData = function(url){
		var name = url.split("?")[1].split("&")[1].split("=")[1];
		var ifHoneybee = url.split("?")[1].split("&")[2].split("=")[1];
		if("honeybee" == ifHoneybee){
			top.window.openBigForm(webPath+"/thirdJxlBeeRecord/input?appId=" + appId +"&cusNo="+cusNo+"&from=tcph",'蜜蜂报告', function(){
				updateTableData();
			});
		}else if(ifHoneybee=="che300"){
			top.window.openBigForm(webPath+"/mfPledgeCarEval/input?appId=" + appId +"&cusNo="+cusNo+"&from=tcph",'车辆评估价查询', myclose);
		}else{
			top.window.openBigForm(url,name, function(){
				updateTableData();
			});
		}
	};
	var _queryHtml = function(url){
		var name = url.split("?")[1].split("&")[1].split("=")[1];
		var ifHoneybee = url.split("?")[1].split("&")[2].split("=")[1];
		if("honeybee" == ifHoneybee){
			id = url.split("?")[1].split("&")[0].split("=")[1];
			url = webPath+"/thirdJxlBeeRecord/lookpreAjax?id="+id+"&from=tcph";
			$.ajax({
				url:url,
				type:"POST",
				dataType:"json",
				traditional:true,
				success:function(data) {
					if(data.flag=='success'){//
					 top.window.openBigForm(webPath+"/thirdJxlBeeRecord/getDetailPage?id="+data.id+"&from=tcph",name, function(){
						});
					}else{
						alert(data.msg,0);
					}
				},
				error:function() {
					alert('查询失败',0);
				}
			});
		}else if("che300" == ifHoneybee){
			id = url.split("?")[1].split("&")[0].split("=")[1];
			top.window.openBigForm(webPath+'/mfPledgeCarEval/getById?evalId='+id+"&appId="+appId+"&cusNo="+cusNo ,'车辆评估报告',function(){
			});
		}else{
			top.window.openBigForm(url,name, function(){
			});
		}
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		doCommitAjax:_doCommitAjax,
		queryThirdData:_queryThirdData,
		againQueryThirdData:_againQueryThirdData,
		queryHtml:_queryHtml,
	};
}(window, jQuery);
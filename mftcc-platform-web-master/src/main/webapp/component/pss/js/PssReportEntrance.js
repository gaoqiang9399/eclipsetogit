top.brNoData;
var queryConditionTop = 0;
var queryConditionLeft = 0;
var underQueryConditionTop = 0;
var inputClickFlag = false;
var PssReportEntrance = function(window,$){
	var _init = function(){
		$("body").mCustomScrollbar({
			advanced:{
				updateOnContentResize:true
			}
		});	
		
		$.each(totalList,function(i,cusTable){
			var itemId = cusTable.itemId;
			var flag = cusTable.attentionFlag;
			if(flag == "1"){//为选中
				$("#"+itemId).css("display","block");
			}else{//已选中		
				$("#"+itemId).css("display","none");
			}
		});
		
		//仓库弹窗
		$('#storehouseName').on('click', function(){
			openDialogShowValue('1','storehouseId','storehouseName');
		});
		
		//商品弹窗
		$('#goodsName').on('click', function(){
			openDialogShowValue('2','goodsId','goodsName');		
		});
		
		//客户弹窗
		$('#cusName1').on('click', function(){
			openDialogShowValue('3','cusNo1','cusName1');
		});
		
		//供应商弹窗
		$('#cusName2').on('click', function(){
			openDialogShowValue('4','cusNo2','cusName2');
		});
		
		//销售人员弹窗
		$('#salerName').on('click', function(){
			openDialogShowValue('5','salerNo','salerName');
		});
		
		$('.leftBorderDiv').click(function(){
			_clearAllInputValue();//清空数据
			_hideQueryTr();
			_dealSaveFlag();
			//var X = $(this).offset().top;
			//var parentX = $(this).parent().parent().position().top;
			var parentX = $(this).parent().position().top;
			var Y = $(this).offset().left+1;
			$('.ui-btn-menu .search_con').css({position:"absolute"});
			$('.ui-btn-menu .search_con').css("left",Y-1);
			var infoContentId = $(this).parents(".row .info-content").attr('id');
			if(infoContentId == 'PssBuyReportDiv'){//购货报表
				$('.ui-btn-menu .search_con').css("top",parentX+68);
			}else if(infoContentId == 'PssSaleReportDiv'){//销售报表
				$('.ui-btn-menu .search_con').css("top",parentX+68);
			}else if(infoContentId == 'PssStoreReportDiv'){//仓库报表
				$('.ui-btn-menu .search_con').css("top",parentX+68);
			}
			var reportId = $(this).attr('reportid');
			/**_showOpNameLable(infoContentId);**/
			_showQueryMessage(reportId);
			_showFormConditionVal(reportId);
			_popQueryDiv($('.ui-btn-menu .menu-btn'));
			$('.search_con').show();
			iObj = $(this);
			queryConditionTop = $(this).offset().top;
			underQueryConditionTop = $(this).offset().top+65;
			queryConditionLeft = Y;
			
			/**
			if(reportId=='report-loan-loanStatus' && $('#month').val()==''){//贷款情况汇总表中,月份默认上个月
				$('#month').val(lastMonth);
			}
			if(reportId=='report-loan-loanCnt' && $('#month').val()==''){//贷款累放累月月报表,月份默认当前月
				$('#month').val(currMonth);
			}
			**/
		});
		
		//隐藏查询条件div
		$('body').click(function(e){
			if($('.modal-content').length<=0 && !inputClickFlag){
				var queryConditionBottom = queryConditionTop+65;
				var queryConditionRight = queryConditionLeft+65;
				e = e || window.event;
	            _xx = e.pageX || e.clientX + document.body.scroolLeft;//获取鼠标坐标
	            _yy = e.pageY || e.clientY + document.body.scrollTop;
	            var inConditionFlag = false;
				if(_xx>=queryConditionLeft && _xx<=queryConditionRight 
						&& _yy>=queryConditionTop && _yy<=queryConditionBottom){//判断鼠标位置是不是在图标div里
					inConditionFlag = true;
				}else{
					//不在图标div里，再判断是不是在条件div里
					var divTop = underQueryConditionTop;
					var divBottom = divTop+$('.search_con').height()+32;
					queryConditionRight = queryConditionLeft+340;
					if(_xx>=queryConditionLeft && _xx<=queryConditionRight 
							&& _yy>=divTop && _yy<=divBottom){//判断鼠标位置是不是在条件div里
						inConditionFlag = true;
					}
					queryConditionTop = divTop;
					queryConditionBottom = divBottom;
				}
				if(!inConditionFlag){
					$('.search_con').hide();
				}
			}
			inputClickFlag = false;
		});
		
		$("input").click(function(){
			inputClickFlag = true;
		});
		
	};
	
	//显示查询条件
	var _showQueryMessage = function(reportId){
		wholeReportId = reportId;
		if(reportId == 'report-buyorder-track'){//采购订单跟踪表
			$('#dateIntervalLi').show();
			$('#goodsIdLi').show();
			$('#cusNo2Li').show();
		}else if(reportId == 'report-buybill-detail'){//采购明细表
			$('#dateIntervalLi').show();
			$('#goodsIdLi').show();
			$('#cusNo2Li').show();
			$('#storehouseIdLi').show();
		}else if(reportId == 'report-saleorder-track'){//销售订单跟踪表
			$('#dateIntervalLi').show();
			$('#goodsIdLi').show();
			$('#cusNo1Li').show();
		}else if(reportId == 'report-salebill-detail'){//销售明细表
			$('#dateIntervalLi').show();
			$('#goodsIdLi').show();
			$('#cusNo1Li').show();
			$('#storehouseIdLi').show();
		}else if(reportId == 'report-stock-detail'){//商品库存明细表
			$('#dateIntervalLi').show();
			$('#goodsIdLi').show();
			$('#storehouseIdLi').show();
		}else if(reportId == 'report-buytotal-buygoods'){//采购汇总表（按商品）
			$('#dateIntervalLi').show();
			$('#goodsIdLi').show();
			$('#cusNo2Li').show();
		}else if(reportId == 'report-buytotal-buysupplier'){//采购汇总表（按供应商）
			$('#dateIntervalLi').show();
			$('#goodsIdLi').show();
			$('#cusNo2Li').show();
		}else if(reportId == 'report-buybill-buypament'){//采购付款一览表
			$('#dateIntervalLi').show();
			$('#cusNo2Li').show();
		}else if(reportId == 'report-saletotal-buygoods'){//销售汇总表（按商品）
			$('#dateIntervalLi').show();
			$('#goodsIdLi').show();
			$('#cusNo1Li').show();
		}else if(reportId == 'report-saletotal-buycus'){//销售汇总表（按客户）
			$('#dateIntervalLi').show();
			$('#goodsIdLi').show();
			$('#cusNo1Li').show();
		}else if(reportId == 'report-saletotal-buysaler'){//销售汇总表（按销售人员）
			$('#dateIntervalLi').show();
			$('#goodsIdLi').show();
			$('#salerNoLi').show();
		}else if(reportId == 'report-salebill-salereceipt'){//销售收款一览表
			$('#dateIntervalLi').show();
			$('#cusNo1Li').show();
		}else if(reportId == 'report-salebill-debt'){//往来单位欠款表
			$('#cusNo1Li').show();
			$('#cusNo2Li').show();
		}else if(reportId == 'report-salebill-profit'){//销售利润表
			$('#dateIntervalLi').show();
			$('#cusNo1Li').show();
			$('#salerNoLi').show();
		}else if(reportId == 'report-salebill-rankbycus'){//销售排行表（按客户）
			$('#dateIntervalLi').show();
			$('#cusNo1Li').show();
		}else if(reportId == 'report-salebill-rankbygoods'){//销售排行表（按商品）
			$('#dateIntervalLi').show();
			$('#goodsIdLi').show();
		}
	};
	
	var _popQueryDiv = function(obj){
		if(obj.hasClass("ui-btn-dis")) {
			return false;
	    }
		$(obj).parent().toggleClass('ui-btn-menu-cur');
	};
	
	//清空查询条件文本框内容
	var _clearAllInputValue = function(){
		$("input").each(function(index, element){
			$(this).val('');
		});
	};
	
	//隐藏查询条件
	var _hideQueryTr = function(obj){
		$('#dateIntervalLi').hide();
		$('#storehouseIdLi').hide();
		$('#goodsIdLi').hide();
		$('#cusNo1Li').hide();
		$('#cusNo2Li').hide();
		$('#salerNoLi').hide();
	};
	
	//保存标志 
	var _dealSaveFlag = function(obj){
		 $('#dateIntervalLi').attr('saveFlag','0');
		 $('#storehouseIdLi').attr('saveFlag','0');
		 $('#goodsIdLi').attr('saveFlag','0');
		 $('#cusNo1Li').attr('saveFlag','0');
		 $('#cusNo2Li').attr('saveFlag','0');
		 $('#salerNoLi').attr('saveFlag','0');
	};
	
	//展示查询条件值
	_showFormConditionVal = function(id){
		jQuery.ajax({
			url:webPath+"/pssReport/showFormConditionVal",
			data:{reportId:id},
			type:"POST",
			dataType:"json",
			async: false,
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					var result = data.result;
					if(result!=''){
						var jsonObj =  JSON.parse(result);//转换为json对象
						var num = 0;
						$("input").each(function(index, element){
							if($(this).attr('noFlag') == '0' && $(this).parent().css("display")!='none') {
								if($(this).attr('saveFlag') == '0'){
									$(this).prev().val(jsonObj[num].value);
									$(this).val(jsonObj[num].nameValue);
								}else{
									$(this).val(jsonObj[num].value);
								}
								num++;
							}
						});
					}
				}else if(data.flag == "error"){
				}
			},error:function(data){
			}
		});
	};
	
	//弹出列表,回显值
	var openDialogShowValue = function(itemsno,noObj,nameObj){
		openConditionListDialog(itemsno, function(data){
			if(data){
				if(data.no!=''){
					/*if(noObj=='brNo'){//选择部门时,清空操作员信息
						if(data.no!=$('#'+noObj).val()){
							$('#opNo').val('');
							$('#opName').val('');
						}
					}*/
					$('#'+noObj).val(data.no);
					$('#'+nameObj).val(data.name);
					top.brNoData = '';
				}else{
					$('#'+noObj).val('');
					$('#'+nameObj).val('');
				}
			}
		});
	};
	
	//查询条件弹窗选择
	var openConditionListDialog = function(txType, callback){
		var storehouseDef = $('#storehouseId').val();
		var goodsNoDef = $('#goodsId').val();
		var cusNo1Def = $('#cusNo1').val();
		var cusNo2Def = $('#cusNo2').val();
		var salerNoDef = $('#salerNo').val();
		top.createShowDialog(encodeURI(webPath+'/component/pss/information/pssSqlConditionDialog.jsp?txType=' + txType +"&salerNoDef="+salerNoDef+"&storehouseDef="+storehouseDef+"&goodsNoDef="+goodsNoDef+"&cusNo1Def="+cusNo1Def+"&cusNo2Def="+cusNo2Def), "选择",'450px','600px',function(){
			if(callback&&typeof(callback)=="function"){
				callback.call(this,top.brNoData);
			}
		});
	};
	
	var tipsTimeoutId;
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
	
	//保存查询条件
	var _reportSave= function(){
		if(_validateSave(wholeReportId)){
			$('.search_con').hide();
			_saveSqlCondition(wholeReportId);
		}
	};
	
	//重置查询条件
	var _resetQueryInput= function(){
		$("input").each(function(index, element){
			/*var objId = $(this).attr("id");
			if(objId == 'brNo' || objId == 'brName' || objId == 'opNo' || objId == 'opName'){
				if($(this).attr("readonly") != 'readonly'){
					$(this).val('');
				}
			}else{*/
				$(this).val('');
			/*}*/
		});
	};
	
	//保存查询条件
	var _saveSqlCondition = function(reportId){
			var url = webPath+"/pssReport/saveReoprtSqlCondition";
			var subArr=[];
			$("input").each(function(index, element){
				if($(this).attr('saveFlag') == '1' && $(this).parent().css("display")!='none' ){
					var subObj = {};
					if($(this).attr('noFlag') == '1'){
						subObj.value= $(this).val();
						subObj.nameValue= $(this).next().val();
					}else{
						subObj.value= $(this).val();
						subObj.nameValue=$(this).val();
					}
					if($(this).id='date'){
						subObj.value= $(this).val().replace(new RegExp("-","gm"),"");//替换日期之间的横杠
					}
					subArr.push(subObj);
				}
			});
		    var condition = JSON.stringify(subArr);
			//LoadingAnimate.start();
			jQuery.ajax({
				url:url,
				data:{reportId:reportId,sqlCondition:condition},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					//LoadingAnimate.stop();
					if(data.flag == "success"){
					}else if(data.flag == "error"){
					}
				},error:function(data){
					//LoadingAnimate.stop();
				}
			});
			
	};
	
	//日期验证
	var _validateSave = function(reportId){
		if($('#dateIntervalLi').css("display")!='none'){
			var beginDate = $('#beginDate').val();
			var endDate = $('#endDate').val();
			if(beginDate!='' && endDate!=''){
				beginDate = beginDate.replace(new RegExp("-","gm"),"");//替换日期之间的横杠
				endDate = endDate.replace(new RegExp("-","gm"),"");//替换日期之间的横杠
				if(beginDate>endDate){
					alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"开始日期" , "timeTwo": "结束日期"}),1); 
					return false;
				}
			}
		}
		return true;
	};
	
	//展示日期框
	var _showCalendarDlg = function(obj){
		/**
		var objId = $(obj).attr('id');
		
		if(wholeReportId=='report-expect-return-money' && (objId == 'beginDate' || objId == 'endDate' )){
			var minDate = currDate.substring(0, 4)+"-"+currDate.substring(4,6)+"-"+currDate.substring(6,8);
			fPopUpCalendarDlg({
				isclear: false,
				min: minDate + ' 00:00:00', //最小日期
				max: '2099-12-31 23:59:59', //最大日期
				choose:function(data){
				}	
			});
		}else if(wholeReportId=='report-prtSaleYear'){
			fPopUpCalendarDlg({
				isclear: false,
				dateFormat:"YYYY",
				choose:function(data){
					$('#year').val(data.substring(0,4));
				}	
			});
		}else{
			fPopUpCalendarDlg(obj);
		}
		**/
		
		//fPopUpCalendarDlg(obj);
		fPopUpCalendarDlg({
			isclear: true,
			choose:function(data){
			}	
		});
		var laydate_box_Left = parseFloat($('#laydate_box').css("left").replace("px",""));//日期控件
		var laydate_box_width = parseFloat($('#laydate_box').width());//日期控件宽度
		var row_info_content_width =  parseFloat($('.row.info-content').eq(0).width())+30;//日期控件宽度
		if(laydate_box_Left+laydate_box_width>row_info_content_width){
			laydate_box_Left = parseFloat(laydate_box_Left)-116;
			$('#laydate_box').css("left",laydate_box_Left+"px");
		}
	};
	
	//查询追加条件,同时弹出报表
	var _reportQuery = function(type,obj,id,title){
		jQuery.ajax({
			url:webPath+"/pssReport/reportQuery",
			data:{reportId:id},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					if(type=='1'){
						_openBuyOrder(obj,id,title,data.querySqlCondition);
					}else if(type=='2'){
						_openSaleOrder(obj,id,title,data.querySqlCondition);
					}else{
						_openStoreOrder(obj,id,title,data.querySqlCondition);
					}
				}else if(data.flag == "error"){
				}
			},error:function(data){
			}
		});
	};
	
	var _openBuyOrder = function(obj,parm,title,sqlCondition){
		var url= '';
		var rq1 = sqlCondition.substr(sqlCondition.indexOf(">")+2,10).replace(/(^\s*)|(\s*$)/g, "");
		var rq2 = sqlCondition.substr(sqlCondition.indexOf("<")+2,10).replace(/(^\s*)|(\s*$)/g, "");
		rq1 = rq1.substr(0,4)+"-"+rq1.substr(4,2)+"-"+rq1.substr(6,2);
		rq2 = rq2.substr(0,4)+"-"+rq2.substr(4,2)+"-"+rq2.substr(6,2);
		if(sqlCondition.trim() == "1=1"){
			rq1 = "";
			rq2 = "";
		}
		switch(parm){
			case "report-buyorder-track":{
				url= "/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=ebdae55e34b17fe0d4a3a5f9ef6e1147&startDate="+rq1+"&endDate="+rq2+"&sqlCondition="+sqlCondition;
				break;
			} 
			case "report-buybill-detail":{
				url= "/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=c63056544e89b389f881ed89b904c87f&startDate="+rq1+"&endDate="+rq2+"&sqlCondition1="+sqlCondition+"&sqlCondition2="+sqlCondition;
				break;
			}
			case "report-buytotal-buygoods":{
				url= "/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=6bfe2e50120354c2a374ea8cc34d375a&startDate="+rq1+"&endDate="+rq2+"&sqlCondition1="+sqlCondition+"&sqlCondition2="+sqlCondition;
				break;
			}
			case "report-buytotal-buysupplier":{
				url= "/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=009c8448b6f26d68ab63dae91fcc4ec7&startDate="+rq1+"&endDate="+rq2+"&sqlCondition1="+sqlCondition+"&sqlCondition2="+sqlCondition;
				break;
			}
			case "report-buybill-buypament":{
				url= "/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=72ace5ebdb3ac3ab79a3f3e2cd100d2b&rq1="+rq1+"&rq2="+rq2+"&sqlCondition1="+sqlCondition+"&sqlCondition2="+sqlCondition+"&sqlCondition3="+sqlCondition+"&sqlCondition4="+sqlCondition+"&sqlCondition5="+sqlCondition+"&sqlCondition6="+sqlCondition;
				break;
			}
			default :
			_showTips(obj);
			return false;
		}
		
		if(url != ''){
			top.openBigForm(url,title,function(){});
		}
	};
	
	var _openSaleOrder = function(obj,parm,title,sqlCondition){
		var url= '';
		var rq1 = sqlCondition.substr(sqlCondition.indexOf(">")+2,10).replace(/(^\s*)|(\s*$)/g, "");
		var rq2 = sqlCondition.substr(sqlCondition.indexOf("<")+2,10).replace(/(^\s*)|(\s*$)/g, "");
		rq1 = rq1.substr(0,4)+"-"+rq1.substr(4,2)+"-"+rq1.substr(6,2);
		rq2 = rq2.substr(0,4)+"-"+rq2.substr(4,2)+"-"+rq2.substr(6,2);
		if(sqlCondition.trim() == "1=1"){
			rq1 = "";
			rq2 = "";
		}
		switch(parm){
			case "report-saleorder-track":{
				url= "/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=684983a260f1e13ecf23e87473629feb&startDate="+rq1+"&endDate="+rq2+"&sqlCondition="+sqlCondition;
				break;
			}
			case "report-salebill-detail":{
				url= "/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=cf652fe7e3c7d2f83243b8165f2e348a&startDate="+rq1+"&endDate="+rq2+"&sqlCondition1="+sqlCondition+"&sqlCondition2="+sqlCondition;
				break;
			}
			case "report-saletotal-buygoods":{
				url= "/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=4de82e3a5d8c561f913ae13f9e220e0f&startDate="+rq1+"&endDate="+rq2+"&sqlCondition1="+sqlCondition+"&sqlCondition2="+sqlCondition;
				break;
			}
			case "report-saletotal-buycus":{
				url= "/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=4d34ff60b5ef5d43258f6b27e57e1ad0&startDate="+rq1+"&endDate="+rq2+"&sqlCondition1="+sqlCondition+"&sqlCondition2="+sqlCondition;
				break;
			}
			case "report-saletotal-buysaler":{
				url= "/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=a26f6a7c7e420ace603a4edabc3478cb&startDate="+rq1+"&endDate="+rq2+"&sqlCondition1="+sqlCondition+"&sqlCondition2="+sqlCondition;
				break;
			}
			case "report-salebill-salereceipt":{
				url= "/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=39282299970156674172d227e0fb7a50&rq1="+rq1+"&rq2="+rq2+"&sqlCondition1="+sqlCondition+"&sqlCondition2="+sqlCondition+"&sqlCondition3="+sqlCondition+"&sqlCondition4="+sqlCondition+"&sqlCondition5="+sqlCondition+"&sqlCondition6="+sqlCondition;
				break;
			}
			case "report-salebill-debt":{
				var sqlCondition1 = "";
				var sqlCondition2 = "";
				
				if(sqlCondition.indexOf("supplier_id") > 0){
					if(sqlCondition.indexOf("cus_no") > 0){
						sqlCondition2 = sqlCondition.substr(sqlCondition.indexOf("supplier_id"),sqlCondition.indexOf("and")-2);
					}else{
						sqlCondition2 = sqlCondition.substr(sqlCondition.indexOf("supplier_id"));
					}
				}else{
					sqlCondition2 = "1=1";
				}
				
				if(sqlCondition.indexOf("cus_no") > 0){
					sqlCondition1 = sqlCondition.substr(sqlCondition.indexOf("cus_no"));
				}else{
					sqlCondition1 = "1=1";
				}
				
				url= "/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=d78387e705e09ae4964e69298f4c017d&sqlCondition1="+sqlCondition1+"&sqlCondition2="+sqlCondition2;
				break;
			}
			case "report-salebill-profit":{
				url= "/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=83ca7c12c931bdee626dd73e3b6c2b07&startDate="+rq1+"&endDate="+rq2+"&sqlCondition1="+sqlCondition+"&sqlCondition2="+sqlCondition;
				break;
			}
			case "report-salebill-rankbycus":{
				url= "/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=31830960046532c46025ee22415e322a&startDate="+rq1+"&endDate="+rq2+"&sqlCondition1="+sqlCondition+"&sqlCondition2="+sqlCondition;
				break;
			}
			case "report-salebill-rankbygoods":{
				url= "/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=ed8e24f85cc517296462ede089e30358&startDate="+rq1+"&endDate="+rq2+"&sqlCondition1="+sqlCondition+"&sqlCondition2="+sqlCondition;
				break;
			}
			default :
			_showTips(obj);
			return false;
		}
		if(url != ''){
			top.openBigForm(url,title,function(){});
		}
	};
		
	var _openStoreOrder = function(obj,parm,title,sqlCondition){
		var url= '';
		switch(parm){
			case "report-stock-detail":{
				url= '/report_new/report/rbc/designPreviewIndex.jsp?reporttype=D&uid=249118454e556c57ce2cdcb112bb468f&sqlCondition='+sqlCondition;
				break;
			}
			default :
			_showTips(obj);
			return false;
		}
		if(url != ''){
			top.openBigForm(url,title,function(){});
		}
	};
	
	var _openGraph = function(parm,title){
		var url = webPath + '/component/pss/information/PssReportGraph.jsp?parm='+parm;		
		top.openBigForm(url,title,function(){},'90','90');
	};
	
	return {
		init:_init,
		reportSave:_reportSave,
		resetQueryInput:_resetQueryInput,
		showFormConditionVal:_showFormConditionVal,
		reportQuery:_reportQuery,
		openGraph:_openGraph,
		showCalendarDlg:_showCalendarDlg
	};
}(window,jQuery);
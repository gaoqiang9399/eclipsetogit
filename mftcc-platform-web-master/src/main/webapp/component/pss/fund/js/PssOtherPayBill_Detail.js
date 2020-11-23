;
var pssOtherPayBillDetail = function(window, $){
	var _init = function(){
		//初始赋值
		$("input[name=supp]").val($("#formpssotherpaybill0002 input[name='supplierId']").val());
		$("#top-billDate").val($("#formpssotherpaybill0002 input[name='billDate']").val());
		var $otherPayNoVal = $("#formpssotherpaybill0002 input[name='otherPayNo']").val();
		$("#top-otherPayNo").val($otherPayNoVal);
		if(auditSts == '1'){
			/*$("#formpssotherpaybill0002 input[name='memo']").attr("readonly","readonly");
			$("#formpssotherpaybill0002 input[name='fullDiscount']").attr("readonly","readonly");*/
		}
		
		//供应商组件
		$("input[name=supp]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.supp,
			changeCallback : function (obj, elem) {
				if(obj.val() != $("#formpssotherpaybill0002 input[name=supplierId]").val()){
					$("#formpssotherpaybill0002 input[name=supplierId]").val(obj.val());
					$("#formpssotherpaybill0002 input[name=supplierName]").val(obj.data("text"));
				}
			}
		});
		var suppPopsDiv = $("#supp").nextAll("div .pops-value").get(0);
		if(suppPopsDiv != undefined){
			var suppLeft = $(suppPopsDiv).position().left;
			var suppSelectDiv = $("#supp").next("div .pops-select").get(0);
			$(suppPopsDiv).width(120);
			$(suppSelectDiv).css({'left':suppLeft});
			$(suppSelectDiv).width(228); //selectDiv比PopsDiv长28px
			$(suppSelectDiv).find("li").width(180);
			$(suppSelectDiv).find("div .pops-search").width(228);
		}
		
		//结算账户组件
		$("#formpssotherpaybill0002 input[name=clearanceAccountId]").popupSelection({
			searchOn : false,//启用搜索
			inline : true,//下拉模式
			multiple : false,//单选
			items : ajaxData.settlementAccount
		});
		
		if(auditSts == '1'){
			$("input[name=popssupp]").remove();
			$("input[name=popclearanceAccountId]").remove();
		}
		
		myCustomScrollbar({
			obj:"#content",//页面内容绑定的id
			url:webPath+"/pssOtherPayDetailBill/findByPageAjax",//列表数据查询的url
			tableId:"tablepssotherpaydetailbill0001",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			pageSize:30,//加载默认行数(不填为系统默认行数)
			ownHeight:true,
			//data:{"buySaleExpIds":buySaleExpIds},
			callback:function(options, data){
				if(auditSts != '1'){
					Pss.addRowOperateEvent("sequence",null,null,null,pssOtherPayBill.refreshFormData);
					var moneyThs = new Array("payAmt");
		    		Pss.addMoneyEvent(moneyThs, ajaxData.pssConfig, '', pssOtherPayBill.refreshFormData);
		    		var stringThs = new Array("memo");
		    		Pss.addStringEvent(stringThs);
				}
				pssFund.addSaleTypeEvent("saleType", "#content");
				
				var tabHeadThs = new Array("saleType", "payAmt");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content");
				
				$('.footer_loader').remove();
			}//方法执行完回调函数（取完数据做处理的时候）
		});
		
		if(auditSts != '1'){
			$('.pss-date').on('click', function() {
				fPopUpCalendarDlg({
					isclear : true,
					choose : function(data) {
					}
				});
			});
		}
		
		$("table.table-float-head").remove();
		
		$('.pss_detail_list').css('height', 'auto');
	    $('#mCSB_1').css('height', 'auto');
	    
	    myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	
	var _printBill = function(){
		
		window.top.alert("请确认是否打印其他支出单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
			$.ajax({
				url : webPath+"/pssPrintBill/getPssOtherPayBillAjax?otherPayId=" + otherPayId,
				data : {
					fileName : 'templateFile_pssqtzcd.doc'
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					if(data.flag){
						var pssOtherPayBillObj = $.parseJSON(data.pssOtherPayBill);
						mfPageOffice.openPageOffice(pssOtherPayBillObj);
					}else{
						window.top.alert(data.msg, 0);
					}
				}
			});
		});
		
	};
	
	return {
		init : _init,
		printBill : _printBill
	};
}(window, jQuery);
//获取 查询条件（方法名固定写法）
function getFilterValArr(){ 
	return JSON.stringify($('#formpssotherpaybill0002').serializeArray());
};
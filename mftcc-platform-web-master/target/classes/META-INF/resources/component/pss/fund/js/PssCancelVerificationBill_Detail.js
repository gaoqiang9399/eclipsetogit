;
var pssCancelVerificationBillDetail = function(window, $){
	var _init = function(){
		var cancelDetailType = "";
		
		//初始赋值
		if(cancelType == "1"){
			$("input[name=cus]").val($("#formpsscancelverificationbill0002 input[name='cusNo']").val());
			cancelDetailType = "2";
		}else if(cancelType == "2"){
			$("input[name=supp]").val($("#formpsscancelverificationbill0002 input[name='supplierId']").val());
			cancelDetailType = "4";
		}else if(cancelType == "3"){
			$("input[name=cus]").val($("#formpsscancelverificationbill0002 input[name='cusNo']").val());
			$("input[name=supp]").val($("#formpsscancelverificationbill0002 input[name='supplierId']").val());
			cancelDetailType = "4";
		}
		
		$("#top-billDate").val($("#formpsscancelverificationbill0002 input[name='billDate']").val());
		var $cancelNoVal = $("#formpsscancelverificationbill0002 input[name='cancelNo']").val();
		$("#top-cancelNo").val($cancelNoVal);
		
		$("input[name=top-cancelType]").val(cancelType);
		//核销类型新组件
		$("input[name=top-cancelType]").popupSelection({
			searchOn:false,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.cancelTypeArr
		});
		var cancelTypePopsDiv = $("#top-cancelType").nextAll("div .pops-value").get(0);
		if(cancelTypePopsDiv != undefined){
			var cancelTypeLeft = $(cancelTypePopsDiv).position().left;
			var cancelTypeSelectDiv = $("#top-cancelType").next("div .pops-select").get(0);
			$(cancelTypePopsDiv).width(80);
			$(cancelTypeSelectDiv).css({'left':cancelTypeLeft});
			$(cancelTypeSelectDiv).width(108); //selectDiv比PopsDiv长28px
			$(cancelTypeSelectDiv).find("li").width(60);
			
			$("input[name=popstop-cancelType]").remove();
		}
		
		//客户新组件
		$("input[name=cus]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.cus,
			changeCallback : function (obj, elem) {
				$("input[name=cusNo]").val(obj.val());
				$("input[name=cusName]").val(obj.data("text"));
			}
		});
		var cusPopsDiv = $("#cus").nextAll("div .pops-value").get(0);
		if(cusPopsDiv != undefined){
			var cusLeft = $(cusPopsDiv).position().left;
			var cusSelectDiv = $("#cus").next("div .pops-select").get(0);
			$(cusPopsDiv).width(120);
			$(cusSelectDiv).css({'left':cusLeft});
			$(cusSelectDiv).width(228); //selectDiv比PopsDiv长28px
			$(cusSelectDiv).find("li").width(180);
			$(cusSelectDiv).find("div .pops-search").width(228);
			
			$("input[name=popscus]").remove();
		}
		
		//供应商新组件
		$("input[name=supp]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.supp,
			changeCallback : function (obj, elem) {
				$("input[name=supplierId]").val(obj.val());
				$("input[name=supplierName]").val(obj.data("text"));
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
			
			$("input[name=popssupp]").remove();
		}
		
		
		myCustomScrollbar({
			obj:"#content2",//页面内容绑定的id
			url:webPath+"/pssSourceDetailBill/getSourceDetailBillListForRecPayCancelAjax",//列表数据查询的url
			tableId:"tablepsssourcedetailbill0002",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			data:{"billNo":$cancelNoVal,"cancelDetailType":cancelDetailType},
			pageSize:30,//加载默认行数(不填为系统默认行数)
			ownHeight:true,
			callback:function(options, data){
				pssFund.addRowOperateEvent("sequence", "#content2");
				pssFund.addMoneyEvent("currCancelAmt", ajaxData.pssConfig.amtDecimalDigit, "#content2", true, "notCancelAmt");
				var tabHeadThs = new Array("currCancelAmt");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content2");
				
				$('.footer_loader').remove();
			}//方法执行完回调函数（取完数据做处理的时候）
		});
		
		$("table.table-float-head").remove();
		
		var tableId = "tablepsssourcedetailbill00001";
		if(cancelType == "1"){
			cancelDetailType = "1";
		}else if(cancelType == "2"){
			cancelDetailType = "3";
		}else if(cancelType == "3"){
			cancelDetailType = "2";
			tableId = "tablepsssourcedetailbill0001";
		}

		myCustomScrollbar({
			obj:"#content1",//页面内容绑定的id
			url:webPath+"/pssSourceDetailBill/getSourceDetailBillListForRecPayCancelAjax",//列表数据查询的url
			tableId:tableId,//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			data:{"billNo":$cancelNoVal,"cancelDetailType":cancelDetailType},
			pageSize:30,//加载默认行数(不填为系统默认行数)
			ownHeight:true,
			callback:function(options, data){
				pssFund.addRowOperateEvent("sequence", "#content1");
				pssFund.addMoneyEvent("currCancelAmt", ajaxData.pssConfig.amtDecimalDigit, "#content1", true, "notCancelAmt");
				var tabHeadThs = new Array("currCancelAmt");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content1");
				
				$('.footer_loader').remove();
			}//方法执行完回调函数（取完数据做处理的时候）
		});
		
		$('.pss-date').on('click', function() {
			fPopUpCalendarDlg({
				isclear : true,
				choose : function(data) {
				}
			});
		});
		
		$("table.table-float-head").remove();
		
		$('.pss_detail_list').css('height', 'auto');
	    $('#mCSB_1').css('height', 'auto');
	    $('#mCSB_2').css('height', 'auto');
	    
	    myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	    
	};

	return {
		init : _init
	};
}(window, jQuery);
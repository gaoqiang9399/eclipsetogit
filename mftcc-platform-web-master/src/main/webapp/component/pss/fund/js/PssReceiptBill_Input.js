;
var pssReceiptBillInput = function(window, $){
	var _init = function(){
		//客户新组件
		$("input[name=cus]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.cus,
			changeCallback : function (obj, elem) {
				if(obj.val() != $("#formpssreceiptbill0002 input[name=cusNo]").val()){
					$("#formpssreceiptbill0002 input[name=cusNo]").val(obj.val());
					$("#formpssreceiptbill0002 input[name=cusName]").val(obj.data("text"));
					
					myCustomScrollbar({
						obj:"#content2",//页面内容绑定的id
						url:webPath+"/pssSourceDetailBill/findByPageAjax",//列表数据查询的url
						tableId:"tablepsssourcedetailbill0001",//列表数据查询的table编号
						tableType:"thirdTableTag",//table所需解析标签的种类
						pageSize:30,//加载默认行数(不填为系统默认行数)
						ownHeight:true,
						callback:function(options, data){
							pssFund.addRowOperateEvent("sequence", "#content2");
//							var tabHeadThs = new Array("currCancelAmt");
//							Pss.pssAddTabMustEnter(tabHeadThs, "#content2");
							
							$('.footer_loader').remove();
						}//方法执行完回调函数（取完数据做处理的时候）
					});
					
					$("table.table-float-head").remove();
					
					$('.pss_detail_list').css('height', 'auto');
				    $('#mCSB_1').css('height', 'auto');
				    $('#mCSB_2').css('height', 'auto');
				}
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
		}
		
		//收款人新组件-操作员
		$("input[name=top-PayeeId]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.sysUser,
			changeCallback : function (obj, elem) {
				$("#formpssreceiptbill0002 input[name=payeeId]").val(obj.val());
				$("#formpssreceiptbill0002 input[name=payeeName]").val(obj.data("text"));
			}
		});
		var topPayeeIdPopsDiv = $("#top-PayeeId").nextAll("div .pops-value").get(0);
		var topPayeeIdLeft = $(topPayeeIdPopsDiv).position().left;
		var topPayeeIdSelectDiv = $("#top-PayeeId").next("div .pops-select").get(0);
		$(topPayeeIdPopsDiv).width(80);
		$(topPayeeIdSelectDiv).css({'left':topPayeeIdLeft});
		$(topPayeeIdSelectDiv).width(224); //selectDiv比PopsDiv长28px
		$(topPayeeIdSelectDiv).find("li").width(80);
		
		myCustomScrollbar({
			obj:"#content2",//页面内容绑定的id
			url:webPath+"/pssSourceDetailBill/findByPageAjax",//列表数据查询的url
			tableId:"tablepsssourcedetailbill0001",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			pageSize:30,//加载默认行数(不填为系统默认行数)
			ownHeight:true,
			callback:function(options, data){
				pssFund.addRowOperateEvent("sequence", "#content2");
				var tabHeadThs = new Array("currCancelAmt");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content2");
				
				$('.footer_loader').remove();
			}//方法执行完回调函数（取完数据做处理的时候）
		});
		
		$("table.table-float-head").remove();

		myCustomScrollbar({
			obj:"#content1",//页面内容绑定的id
			url:webPath+"/pssReceiptDetailBill/findByPageAjax",//列表数据查询的url
			tableId:"tablepssreceiptdetailbill0001",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			pageSize:30,//加载默认行数(不填为系统默认行数)
			ownHeight:true,
			callback:function(options, data){
				pssFund.addRowOperateEvent("sequence", "#content1", pssReceiptBill.calCurrAdvRecAmtCover);
				pssFund.addAccountEvent("clearanceAccountName", "clearanceAccountId", "#content1");
				pssFund.addMoneyEvent("recAmt", ajaxData.pssConfig.amtDecimalDigit, "#content1", false, "", true, pssReceiptBill.calCurrAdvRecAmtCover);
				var stringThs = new Array("clearanceAccountNum", "memo");
				pssFund.addStringEvent(stringThs, "#content1");
				pssFund.addClearAccTypeEvent("clearanceAccountType", "#content1");
				var tabHeadThs = new Array("clearanceAccountName", "recAmt");
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
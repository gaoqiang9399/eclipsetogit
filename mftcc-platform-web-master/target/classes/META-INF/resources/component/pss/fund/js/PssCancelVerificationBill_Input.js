;
var pssCancelVerificationBillInput = function(window, $){
	var _init = function(){
		$("input[name=top-cancelType]").val(cancelType);
		$("input[name=cancelType]").val(cancelType);
		//核销类型新组件
		$("input[name=top-cancelType]").popupSelection({
			searchOn:false,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.cancelTypeArr,
			changeCallback : function (obj, elem) {
				$("input[name=cancelType]").val(obj.val());
				if(cancelType != obj.val()){
					LoadingAnimate.start();
					$('button[type="button"]').attr("disabled", true);
					window.location.href = webPath+"/pssCancelVerificationBill/input?cancelType=" + obj.val();
					LoadingAnimate.stop();
				}
				
			}
		});
		var cancelTypePopsDiv = $("#top-cancelType").nextAll("div .pops-value").get(0);
		if(cancelTypePopsDiv != undefined){
			var cancelTypeLeft = $(cancelTypePopsDiv).position().left;
			var cancelTypeSelectDiv = $("#top-cancelType").next("div .pops-select").get(0);
			$(cancelTypePopsDiv).width(80);
			$(cancelTypeSelectDiv).css({'left':cancelTypeLeft});
			$(cancelTypeSelectDiv).width(108); //selectDiv比PopsDiv长28px
			$(cancelTypeSelectDiv).find("li").width(60);
		}
		
		//客户新组件
		$("input[name=cus]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.cus,
			changeCallback : function (obj, elem) {
				if(obj.val() != $("#formpsscancelverificationbill0002 input[name=cusNo]").val()){
					$("#formpsscancelverificationbill0002 input[name=cusNo]").val(obj.val());
					$("#formpsscancelverificationbill0002 input[name=cusName]").val(obj.data("text"));
					
					chgInit('1', cancelType);
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
		
		//供应商新组件
		$("input[name=supp]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//下拉模式
			multiple:false,//多选选
			items:ajaxData.supp,
			changeCallback : function (obj, elem) {
				if(obj.val() != $("#formpsscancelverificationbill0002 input[name=supplierId]").val()){
					$("#formpsscancelverificationbill0002 input[name=supplierId]").val(obj.val());
					$("#formpsscancelverificationbill0002 input[name=supplierName]").val(obj.data("text"));
					
					chgInit('2', cancelType);
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
		
		
		myCustomScrollbar({
			obj:"#content2",//页面内容绑定的id
			url:webPath+"/pssSourceDetailBill/findByPageAjax",//列表数据查询的url
			tableId:"tablepsssourcedetailbill0002",//列表数据查询的table编号
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
			url:webPath+"/pssSourceDetailBill/findByPageAjax",//列表数据查询的url
			tableId:"tablepsssourcedetailbill00001",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			pageSize:30,//加载默认行数(不填为系统默认行数)
			ownHeight:true,
			callback:function(options, data){
				pssFund.addRowOperateEvent("sequence", "#content1");
				var tabHeadThs = new Array("currCancelAmt");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content1");
				
				$('.footer_loader').remove();
			}//方法执行完回调函数（取完数据做处理的时候）
		});
		
		$("table.table-float-head").remove();
		
		$('.pss-date').on('click', function() {
			fPopUpCalendarDlg({
				isclear : true,
				choose : function(data) {
				}
			});
		});
		
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
	
	function chgInit(cusSuppType, cancelType){
		if((cancelType == '1' || cancelType == '2') || (cancelType == '3' && cusSuppType == '2')){
			myCustomScrollbar({
				obj:"#content2",//页面内容绑定的id
				url:webPath+"/pssSourceDetailBill/findByPageAjax",//列表数据查询的url
				tableId:"tablepsssourcedetailbill0002",//列表数据查询的table编号
				tableType:"thirdTableTag",//table所需解析标签的种类
				pageSize:30,//加载默认行数(不填为系统默认行数)
				ownHeight:true,
				callback:function(options, data){
					pssFund.addRowOperateEvent("sequence", "#content2");
					
					$('.footer_loader').remove();
				}//方法执行完回调函数（取完数据做处理的时候）
			});
			
			$("table.table-float-head").remove();
		}
		
		if((cancelType == '1' || cancelType == '2') || (cancelType == '3' && cusSuppType == '1')){
			myCustomScrollbar({
				obj:"#content1",//页面内容绑定的id
				url:webPath+"/pssSourceDetailBill/findByPageAjax",//列表数据查询的url
				tableId:"tablepsssourcedetailbill00001",//列表数据查询的table编号
				tableType:"thirdTableTag",//table所需解析标签的种类
				pageSize:30,//加载默认行数(不填为系统默认行数)
				ownHeight:true,
				callback:function(options, data){
					pssFund.addRowOperateEvent("sequence", "#content1");
					
					$('.footer_loader').remove();
				}//方法执行完回调函数（取完数据做处理的时候）
			});
			
			$("table.table-float-head").remove();
		}
		
		$('.pss_detail_list').css('height', 'auto');
	    $('#mCSB_1').css('height', 'auto');
	    $('#mCSB_2').css('height', 'auto');
		
	};

	return {
		init : _init
	};
}(window, jQuery);
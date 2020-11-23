;
var MfMoveableTransferApply=function(window,$){
	var _init=function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		_getPledgeData();
	};
	//初始化选择押品数据源
	var _getPledgeData=function(){
		jQuery.ajax({
			url : webPath+"/mfBusCollateralDetailRel/getPledgeDataAjax?busCollateralId="+busPleId,
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("input[name=transferPledge]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,
						multiple:true,//单选
						items:data.items,
						changeCallback:_refreshGoodsDetailList
					});
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	var _chosePledge=function(){
		htmlButton= "<button type='button' class='btn btn-primary' onclick='MfMoveableTransferApply.goodsDetailList();' style='border-radius: 7px;padding: 6px 54px;background-color: #4bafc5;border-color: #4bafc5;'>货物清单</button>";
		$("input[name=pledgeDetail]").parent().html(htmlButton);
	};
	//打开货物明细列表
	var _goodsDetailList=function(){
		var pledgeNo=$("input[name=transferPledge]").val();
		dialog({
			id:"goodsDialog",
    		title:'货物明细',
    		url: webPath+'/pledgeBaseInfoBill/getListByPage?pledgeNo='+pledgeNo,
    		width:700,
    		height:400,
    		backdropOpacity:0,
    		onshow:function(){
    			this.returnValue = null;
    		},onclose:function(){
    			if(this.returnValue){
    				//返回对象的属性:opNo,opName
    				if(typeof(callback)== "function"){
    					callback(this.returnValue);
    				}else{
    				}
    			}
    		}
    	}).showModal();
	};
	var _refreshGoodsDetailList=function(){
		var pledgeNo=$("input[name=transferPledge]").val();
		jQuery.ajax({
			url : webPath+"/pledgeBaseInfoBill/getTableDataByPledgeNoAjax",
			type : "POST",
			dataType : "json",
			data:{tableId:"tabledlpledgebaseinfobill0006",pledgeNo:pledgeNo},
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("#goodsDetailList").html(data.tableData);
					$("#goodsDetailListdiv").show();
					$("th[name=pledgeBillNo]").html('<a href="javascript:void(0);" onclick="MfMoveableTransferApply.checkAllGoodsDetail()">全选</a>');
					$('.table_content #tab').find($('input[type=checkbox]')).each(function () {
						$(this).bind("click",function(){
							var val=this.value;
							var countStr=val.split("&")[2];
							var count=countStr.split("=")[1];
							var goodsNum=$("input[name=pledgeNum]").val();
							if(goodsNum==""){
								goodsNum=0;
							}
							if($(this).prop("checked")){
								goodsNum=parseInt(goodsNum)+parseInt(count)
							}else{
								goodsNum=parseInt(goodsNum)-parseInt(count)
							}
							$("input[name=pledgeNum]").val(goodsNum);
						});
					});
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	}
	var _checkAllGoodsDetail=function(){
		var goodsNum = 0;
		$('.table_content #tab').find($('input[type=checkbox]')).each(function () {
			if($(this).prop("checked")){
				$(this).prop("checked",false);
			}else{
				$(this).prop("checked",true);
				$(this).prop("checked",true);
				var val=this.value;
				var countStr=val.split("&")[2];
				var count=countStr.split("=")[1];
				goodsNum=parseInt(goodsNum)+parseInt(count);
			}
    	 });
		$("input[name=pledgeNum]").val(goodsNum);
	};
	//保存方法
	var _ajaxTransferApplySave = function(formObj){
		var pledgeBillNoStr ="";
		$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
    	    val = this.value.split('&') [0] ;
    	    pledgeBillNoStr=pledgeBillNoStr+"|"+val.split("=")[1];
    	  });
    	if(pledgeBillNoStr.length==0){
    		alert(top.getMessage("FIRST_SELECT_FIELD","货物明细"), 0);
    		return;
    	}
    	$("input[name=pledgeDetail]").val(pledgeBillNoStr);
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataForm,
					appId:appId
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					top.flag=true;
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.flag=true;
						window.top.alert(data.msg, 3);
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					top.loadingAnimate.stop();
					window.top.alert(data.msg, 0);
				}
			});
		}
	};
	return{
		init:_init,
		ajaxTransferApplySave:_ajaxTransferApplySave,
		goodsDetailList:_goodsDetailList,
		checkAllGoodsDetail:_checkAllGoodsDetail
	};
}(window,jQuery);
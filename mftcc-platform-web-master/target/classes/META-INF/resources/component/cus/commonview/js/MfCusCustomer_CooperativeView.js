var MfCusCustomer_CooperativeView = function(window, $) {
	var _inputOpenFundAccount = function() {
		var coopCusNo = initCoopCusNo;
		var url = webPath+'/mfCusCooperativeAgency/inputOpenFundAccount?cusNo='+coopCusNo;
		window.parent.openBigForm(url, '新增基金开户信息', function() {
			//window.location.href = webPath + "/mfCusCooperativeAgency/getCooperativeAgencyView?orgaNo="+coopCusNo+"&busEntrance=cus_coopAgency"+"&opNo="+opNo;
			//$("#addMfBusFundList .ls_list").remove();
			_refreshMfBusFundHtmlDiv(coopCusNo);
		});
	};
	
	var _inputFundPurchaseInfo = function() {
		var coopCusNo = initCoopCusNo;
		var url = webPath+'/mfCusCooperativeAgency/inputFundPurchaseInfo?cusNo='+coopCusNo;
		window.parent.openBigForm(url, '新增基金认购信息', function() {
			//window.location.href = webPath + "/mfCusCooperativeAgency/getCooperativeAgencyView?orgaNo="+coopCusNo+"&busEntrance=cus_coopAgency"+"&opNo="+opNo;
			_refreshMfBusFundDetailHtmlDiv(coopCusNo);
		});
	};
	
	var _appendMfBusFundDetail = function(url) {
		var coopCusNo = initCoopCusNo;
		if (url.substr(0, 1) == "/") {
			url = webPath + url;
		} else {
			url = webPath + "/" + url;
		}
		window.parent.openBigForm(url, '追加基金', function() {
			_refreshMfBusFundDetailHtmlDiv(coopCusNo);
		});
	};
	
	var _initMfBusFundDetailInput = function() {
		$('input[name=fundCompanyId]').popupSelection({
			searchOn: true, //启用搜索
			inline: true, //下拉模式
			multiple: false, //单选
			items : fundCompanyNameObj.fundCompanyName,
			changeCallback : function (obj, elem) {
				$("input[name=fundCompanyName]").val(obj.data("text"));
				$("input[name=fundCompanyId]").val(obj.val());				
			}
		});
	};
	
	var _insertMfBusFundAjax = function(dom) {
		var flag = submitJsMethod($(dom).get(0), '');
		if(flag){
			var url = $(dom).attr("action");
			var dataParam = JSON.stringify($(dom).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url:url,
				data : {
					ajaxData : dataParam,
					cusNo : cusNo
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop(); 
					if (data.flag == "success") {
						window.top.alert(data.msg, 1);
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function() {
					LoadingAnimate.stop(); 
					alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		}
	};
	
	var _insertMfBusFundDetailAjax = function(dom) {
		var flag = submitJsMethod($(dom).get(0), '');
		if(flag){
			var url = $(dom).attr("action");
			var dataParam = JSON.stringify($(dom).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url:url,
				data : {
					ajaxData : dataParam,
					cusNo : cusNo
					//mfBusFundId : mfBusFundId
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop(); 
					if (data.flag == "success") {
						window.top.alert(data.msg, 1);
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function() {
					LoadingAnimate.stop(); 
					alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		}
	};
	
	var _insertMfBusFundPurchaseHisAjax = function(dom) {
		var flag = submitJsMethod($(dom).get(0), '');
		if(flag){
			var url = $(dom).attr("action");
			var dataParam = JSON.stringify($(dom).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url:url,
				data : {
					ajaxData : dataParam,
					mfBusFundDetailId : mfBusFundDetailId
					//mfBusFundId : mfBusFundId
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop(); 
					if (data.flag == "success") {
						window.top.alert(data.msg, 1);
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function() {
					LoadingAnimate.stop(); 
					alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		}
	};
	 
	var _getMfBusFundById = function(url) {
		var coopCusNo = initCoopCusNo;
		if (url.substr(0, 1) == "/") {
			url = webPath + url;
		} else {
			url = webPath + "/" + url;
		}
		url = url + "&cusNo="+coopCusNo;
		window.parent.openBigForm(url, '基金公司开户详情', function() {
			//window.location.href = webPath + "/mfCusCooperativeAgency/getCooperativeAgencyView?orgaNo="+coopCusNo+"&busEntrance=cus_coopAgency"+"&opNo="+opNo;
			_refreshMfBusFundHtmlDiv(coopCusNo);
		});
	};
	
	var _getMfBusFundDetailById = function(url) {
		var coopCusNo = initCoopCusNo;
		if (url.substr(0, 1) == "/") {
			url = webPath + url;
		} else {
			url = webPath + "/" + url;
		}
		url = url + "&cusNo="+coopCusNo;
		window.parent.openBigForm(url, '基金详情信息', function() {
			//window.location.href = webPath + "/mfCusCooperativeAgency/getCooperativeAgencyView?orgaNo="+coopCusNo+"&busEntrance=cus_coopAgency"+"&opNo="+opNo;
			_refreshMfBusFundDetailHtmlDiv(coopCusNo);
		});
	};
	
	var _getMfBusFundRedeemDetailById = function(url) {
		var coopCusNo = initCoopCusNo;
		if (url.substr(0, 1) == "/") {
			url = webPath + url;
		} else {
			url = webPath + "/" + url;
		}
		url = url + "&cusNo="+coopCusNo;
		window.parent.openBigForm(url, '基金详情信息', function() {
			//window.location.href = webPath + "/mfCusCooperativeAgency/getCooperativeAgencyView?orgaNo="+coopCusNo+"&busEntrance=cus_coopAgency"+"&opNo="+opNo;
			_refreshMfBusFundDetailHtmlDiv(coopCusNo);
		});
	};
	
	var _ajaxTrDeleteFund = function(url) {
		var coopCusNo = initCoopCusNo;
		if (url.substr(0, 1) == "/") {
			url = webPath + url;
		} else {
			url = webPath + "/" + url;
		}
		alert(top.getMessage("CONFIRM_DELETE"),2,function(){
		 	$.ajax({
	 		url:url,
	 		dataType:'json',
	 		type:'post',
	 		success : function(data){
	 			if (data.flag == "success") {
					window.top.alert(data.msg, 1);
					//window.location.href = webPath + "/mfCusCooperativeAgency/getCooperativeAgencyView?orgaNo="+coopCusNo+"&busEntrance=cus_coopAgency"+"&opNo="+opNo;
					_refreshMfBusFundHtmlDiv(coopCusNo);
				} else {
					window.top.alert(data.msg, 0);
				}
	 		}
	 	})
	 });
	};
	
	var _ajaxTrDeleteFundDetail = function(url) {
		var coopCusNo = initCoopCusNo;
		if (url.substr(0, 1) == "/") {
			url = webPath + url;
		} else {
			url = webPath + "/" + url;
		}
		alert(top.getMessage("CONFIRM_DELETE"),2,function(){
		 	$.ajax({
	 		url:url,
	 		dataType:'json',
	 		type:'post',
	 		success : function(data){
	 			if (data.flag == "success") {
					window.top.alert(data.msg, 1);
					//window.location.href = webPath + "/mfCusCooperativeAgency/getCooperativeAgencyView?orgaNo="+coopCusNo+"&busEntrance=cus_coopAgency"+"&opNo="+opNo;
					_refreshMfBusFundDetailHtmlDiv(coopCusNo);
				} else {
					window.top.alert(data.msg, 0);
				}
	 		}
	 	})
	 });
	};
	
	var _updateMfBusFundAjax = function(dom){
		var flag = submitJsMethod($(dom).get(0), '');
		if(flag){
			var url = $(dom).attr("action");
			var dataParam = JSON.stringify($(dom).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url:url,
				data : {
					ajaxData : dataParam
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop(); 
					if (data.flag == "success") {
						window.top.alert(data.msg, 1);
						myclose_click();
						//window.location.href = webPath + "/mfCusCooperativeAgency/getCooperativeAgencyView?orgaNo="+coopCusNo+"&busEntrance=cus_coopAgency"+"&opNo="+opNo;
						_refreshMfBusFundHtmlDiv(coopCusNo);
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function() {
					LoadingAnimate.stop(); 
					alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		}
	};
	
	var _updateMfBusFundDetailAjax = function(dom){
		var flag = submitJsMethod($(dom).get(0), '');
		if(flag){
			var url = $(dom).attr("action");
			var dataParam = JSON.stringify($(dom).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url:url,
				data : {
					ajaxData : dataParam
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop(); 
					if (data.flag == "success") {
						window.top.alert(data.msg, 1);
						myclose_click();
						//window.location.href = webPath + "/mfCusCooperativeAgency/getCooperativeAgencyView?orgaNo="+coopCusNo+"&busEntrance=cus_coopAgency"+"&opNo="+opNo;
						_refreshMfBusFundDetailHtmlDiv(coopCusNo);
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function() {
					LoadingAnimate.stop(); 
					alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		}
	};
	
	var _updateMfBusFundRedeemDetailAjax = function(dom){
		var flag = submitJsMethod($(dom).get(0), '');
		if(flag){
			var url = $(dom).attr("action");
			var dataParam = JSON.stringify($(dom).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url:url,
				data : {
					ajaxData : dataParam
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop(); 
					if (data.flag == "success") {
						window.top.alert(data.msg, 1);
						myclose_click();
						//window.location.href = webPath + "/mfCusCooperativeAgency/getCooperativeAgencyView?orgaNo="+coopCusNo+"&busEntrance=cus_coopAgency"+"&opNo="+opNo;
						_refreshMfBusFundDetailHtmlDiv(coopCusNo);
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function() {
					LoadingAnimate.stop(); 
					alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		}
	};
	
	var _refreshMfBusFundHtmlDiv = function(coopCusNo) {
		$("#addMfBusFundList .ls_list").remove();
		$.ajax({
			url:webPath+'/mfCusCooperativeAgency/getMfBusFundListHtmlByCusNoAjax',
			data : {
				cusNo : coopCusNo
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop(); 
				if (data.flag == "success") {
					//$("#addMfBusFundList .list-table").html(data.mfBusFundListHtml);
					$("#addMfBusFundList").html(data.mfBusFundListHtml);
					//$("#addMfBusFundList .ls_list").css('display','block');
					//$("#addMfBusFundList .ls_list")[0].style.display = 'block';
					$("#addMfBusFundList .ls_list")[0].style = '';
					
					$("#addMfBusFundList .ls_list").find("thead").find("tr").each(function(){
						var tdArr = $(this).children("th");
						
						tdArr.eq(0).css("text-align","center");
						tdArr.eq(1).css("text-align","center");
						tdArr.eq(2).css("text-align","center");
						tdArr.eq(3).css("text-align","center");
						tdArr.eq(4).css("text-align","center");
					});
					$("#addMfBusFundList .ls_list").find("tbody").find("tr").each(function(){
						var tdArr = $(this).children("td");
						
						tdArr.eq(0).css("text-align","left");
						tdArr.eq(1).css("text-align","center");
						tdArr.eq(2).css("text-align","center");
						tdArr.eq(3).css("text-align","center");
						tdArr.eq(4).css("text-align","center");
					});
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error : function() {
				LoadingAnimate.stop(); 
				alert(top.getMessage("ERROR_REQUEST_URL", url));
			}
		});
	};
	
	var _refreshMfBusFundDetailHtmlDiv = function(coopCusNo) {
		$("#addMfBusFundDetailList .ls_list").remove();
		$.ajax({
			url:webPath+'/mfCusCooperativeAgency/getMfBusFundDetailListHtmlByCusNoAjax',
			data : {
				cusNo : coopCusNo
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop(); 
				if (data.flag == "success") {
					$("#addMfBusFundDetailList").html(data.mfBusFundDetailListHtml);
					$("#addMfBusFundDetailList .ls_list")[0].style = '';
					
					$("#addMfBusFundDetailList .ls_list").find("thead").find("tr").each(function(){
						var tdArr = $(this).children("th");
						
						tdArr.eq(0).css("text-align","center");
						tdArr.eq(1).css("text-align","center");
						tdArr.eq(2).css("text-align","right");
						tdArr.eq(3).css("text-align","right");
						tdArr.eq(4).css("text-align","center");
						tdArr.eq(5).css("text-align","center");
						tdArr.eq(6).css("text-align","center");
						tdArr.eq(7).css("text-align","center");
					});
					$("#addMfBusFundDetailList .ls_list").find("tbody").find("tr").each(function(){
						var tdArr = $(this).children("td");
						
						tdArr.eq(0).css("text-align","left");
						tdArr.eq(1).css("text-align","center");
						tdArr.eq(2).css("text-align","right");
						tdArr.eq(3).css("text-align","right");
						tdArr.eq(4).css("text-align","center");
						tdArr.eq(5).css("text-align","center");
						tdArr.eq(6).css("text-align","center");
						tdArr.eq(7).css("text-align","center");
						tdArr.eq(8).css("text-align","center");
					});
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error : function() {
				LoadingAnimate.stop(); 
				alert(top.getMessage("ERROR_REQUEST_URL", url));
			}
		});
	};
	
	return {
		inputOpenFundAccount : _inputOpenFundAccount,
		inputFundPurchaseInfo : _inputFundPurchaseInfo,
		initMfBusFundDetailInput : _initMfBusFundDetailInput,
		insertMfBusFundAjax : _insertMfBusFundAjax,
		insertMfBusFundDetailAjax : _insertMfBusFundDetailAjax,
		getMfBusFundDetailById : _getMfBusFundDetailById,
		getMfBusFundById : _getMfBusFundById,
		updateMfBusFundAjax : _updateMfBusFundAjax,
		updateMfBusFundDetailAjax : _updateMfBusFundDetailAjax,
		ajaxTrDeleteFund : _ajaxTrDeleteFund,
		ajaxTrDeleteFundDetail : _ajaxTrDeleteFundDetail,
		getMfBusFundRedeemDetailById : _getMfBusFundRedeemDetailById,
		updateMfBusFundRedeemDetailAjax : _updateMfBusFundRedeemDetailAjax,
		appendMfBusFundDetail : _appendMfBusFundDetail,
		insertMfBusFundPurchaseHisAjax : _insertMfBusFundPurchaseHisAjax
	};
}(window, jQuery);
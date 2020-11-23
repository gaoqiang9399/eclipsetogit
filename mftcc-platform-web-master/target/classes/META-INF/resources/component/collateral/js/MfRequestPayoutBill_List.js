;
var mfRequestPayoutBillList = function(window, $) {
	var _init = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/mfRequestPayoutBill/findByPageAjax",//列表数据查询的url
			tableId : "tablerequestpayoutbilllist",//列表数据查询的table编号
			tableType : "tableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
		});
	};
	
	//请款列表业务新申请入口
	var _applyInitInput=function(){
        top.flag=false;
        top.window.openBigForm(webPath+'/mfRequestPayoutBill/input','新增请款申请',function(){
            if(top.flag){
                _init();
            }
        });
	}
    var _ajaxGetById = function(obj ,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.openBigForm(encodeURI(url),"请款单明细", function(){
            updateTableData();
        });
    };

    //付款登记
    var _paymentRegAjax=function(url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.flag=false;
        top.window.openBigForm(encodeURI(url),'付款登记',function(){
            if(top.flag){
                _init();
            }
        });
    };

    //结束请款
    var _finishPaymentAjax=function(url){
        alert(top.getMessage("CONFIRM_OPERATION","结束请款"),2,function(){
            LoadingAnimate.start();
            jQuery.ajax({
                url : url,
                data : {
                    ajaxData : "",
                },
                type : "POST",
                dataType : "json",
                beforeSend : function() {
                },
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {

                        window.top.alert(data.msg, 3);
                        _init();
                    }else if (data.flag == "error") {
                        alert(data.msg, 0);
                    }
                },
                error : function(data) {
                    LoadingAnimate.stop();
                    alert(top.getMessage("FAILED_OPERATION"," "), 0);
                }
            });
        })
    };


	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		applyInitInput:_applyInitInput,
        ajaxGetById:_ajaxGetById,
        paymentRegAjax:_paymentRegAjax,
        finishPaymentAjax:_finishPaymentAjax,
	};
}(window, jQuery);
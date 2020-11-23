var MfStampPact_input = function(window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
    var _initStamp= function(){
        $("[name='pactNoShow']").parent().find(".pops-value").remove();
        $('input[name=pactNoShow]').popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl:webPath+"/mfStampPact/getStampBaseList?queryType="+queryType,//请求数据URL
            valueElem:"input[name=stampId]",//真实值选择器
            title: "选择用印合同",//标题
            changeCallback:function(elem){//回调方法
                var sltVal = elem.data("selectData");
                var stampId = sltVal.stampId;
                var url = webPath+"/mfStampPact/inputForView?stampId=" + stampId + "&queryType="+queryType;//初始化
                window.location.href = url;
            },
            tablehead:{//列表显示列配置
                "cusName":"客户名称",
                "creditPactNo":"授信合同号",
                "pactNo":"委托担保合同号"
            },
            returnData:{//返回值配置
                disName:"pactNo",//显示值
                value:"stampId"//真实值
            }
        });
        $("input[name=pactNoShow]").parent().find(".pops-value").click();
    }
    var _checkExtendAll = function(){
        $("#mfBusPactExtendListDiv").find("input[type='checkbox']").each(function(i,n){
            if ($(n).is(":checked")) {
                $(n).prop('checked', false);
            }else{
                $(n).prop('checked', true);
            }
        });
    };
     var _extendDetail = function(obj, url) {
        top.window.openBigForm( webPath + url + "&queryType="+queryType ,'非系统生成相关合同详情',function() {
        });
    }
	return{
		init:_init,
        initStamp:_initStamp,
        checkExtendAll:_checkExtendAll,
        extendDetail:_extendDetail,
	};
}(window,jQuery);
//获取客户信息
function getCusInfo(){
	top.LoadingAnimate.start();
	window.location.href=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId="+appId+"&fincId="+fincId+"&busEntrance="+busEntrance;
}

//获取申请信息
function getBusInfo(){
	top.LoadingAnimate.start();
	window.location.href=webPath+"/mfBusApply/getSummary?appId="+appId+"&busEntrance="+busEntrance;
}
//获取合同信息
function getPactInfo(){
	window.location.href=webPath+"/mfBusPact/getById?appId="+appId+"&busEntrance="+busEntrance;
};

;
var  MfBusReceBaseInfo_DynaDetail = function(window,$){
	var _init = function(){
        /**滚动条**/
        $("body").mCustomScrollbar({
            advanced:{
                updateOnContentResize:true
            },
            callbacks: {//解决单字段编辑输入框位置随滚动条变化问题
                whileScrolling: function(){
                    if ($(".changeval").length>0) {
                        $(".changeval").css("top", parseInt($(".changeval").data("top")) + parseInt(this.mcs.top) - $(".changeval").data("msctop"));
                    }
                }
            }
        });
        top.LoadingAnimate.stop();
	};
	return {
		init:_init,
	};
}(window, jQuery);
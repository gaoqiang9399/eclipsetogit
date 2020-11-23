;
var MfCusCustomer_EntList=function(window,$){
	var _init=function(){
		 myCustomScrollbar({
		    	obj:"#content",//页面内容绑定的id
		    	url:webPath+"/mfCusCustomer/findEntByPageAjax.action",//列表数据查询的url
		    	tableId:"tablecusInfoList",//列表数据查询的table编号
		    	tableType:"tableTag",//table所需解析标签的种类
		    	pageSize:30,//加载默认行数(不填为系统默认行数)
		    	topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
		    	data:{cusType:cusType},//指定参数
		    	callback:function(){
		    	}//方法执行完回调函数（取完数据做处理的时候）
		    });
	};
	var _getDetailPage = function (obj,url){		
		top.LoadingAnimate.start();
		window.location.href=webPath+url;			
	};

	//根据客户基础类型跳转详情页面
    var _getCusDetailByCusBaseType = function (obj,url){
        top.LoadingAnimate.start();
        var parms = url.split("?");
		var path = parms[0];
		var parm = parms[1];
        var cusNo = parm.split("&")[0].split("=")[1];
        var cusBaseType = parm.split("&")[1].split("=")[1];
        $.ajax({
            url : webPath+"/mfCusType/getUrlAjax",
            data : {cusNo:cusNo,cusBaseType:cusBaseType,parms:parm},
            type : 'post',
            dataType : 'json',
            success : function(data) {
                if(data.flag=="success"){
                    window.location.href=webPath+data.url;
                }else if(data.flag=="none"){
                    window.location.href=webPath+url;
				}
            },
            error : function() {
            }
        });
    };

	var _cusInput = function(){
		var from = "MfCusCustomer";
		window.location.href=webPath+"/mfCusCustomer/input?from=" + from;
	};
    var _cusInput2 = function(){
        var from = "MfCusCustomer";
        window.location.href=webPath+"/mfCusCustomer/input?from=coopAgency";
    };
	return{
		init:_init,
		getDetailPage:_getDetailPage,
		cusInput:_cusInput,
		cusInput2:_cusInput2,
        getCusDetailByCusBaseType:_getCusDetailByCusBaseType
	};
}(window,jQuery);
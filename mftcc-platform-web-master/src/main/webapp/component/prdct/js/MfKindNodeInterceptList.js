;
var MfKindNodeInterceptList=function(window, $){
	//页面初始化函数
	var _init = function(){
        myCustomScrollbar({
            obj : "#content",//页面内容绑定的id
            url : webPath+"/mfKindNodeIntercept/findByPageAjax",//列表数据查询的url
            tableId : "tableinterceptlistfornode",//列表数据查询的table编号
            tableType : "tableTag",//table所需解析标签的种类
            pageSize : 30//加载默认行数(不填为系统默认行数)
			,data:{kindNo:kindNo,nodeNo:nodeNo}
        });
	};
	var datas = [];
	var _selectConfirm = function(){
		$('.table_content #tab').find($('input[type=checkbox]:checked')).each(function () {
    	    val = this.value.split('=') [1] ;
    	    datas.push(val);
    	  });
		top.flag=true;
		top.itemNos = JSON.stringify(datas);
		myclose_click();
	};
	
	return{
		init:_init,
		selectConfirm:_selectConfirm,
	};
	
}(window, jQuery);
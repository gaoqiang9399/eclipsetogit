;
var MfRepayApply_List = function(window, $) {
	var _init = function() {
        myCustomScrollbar({
            obj : "#content", //页面内容绑定的id
            url : webPath + "/mfRepayApply/findByPageAjax", //列表数据查询的url
            tableId : "tableRepayAppList", //列表数据查询的table编号
            tableType : "thirdTableTag", //table所需解析标签的种类
            pageSize:30 //加载默认行数(不填为系统默认行数)
            //,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
        });
	};
    var _repaymentApply = function(url){
        top.openBigForm(webPath+"/mfRepayApply/repaymentApply?source=repay","还款申请", function(){
            updateTableData();
        });
    }
    var _getById = function(url){
        top.openBigForm(webPath + url,"还款申请详情", function(){
            updateTableData();
        });
    }

    var _batchRepayment = function(url){
        top.openBigForm(webPath + url,"还款", function(){
            updateTableData();
        });
    }

	return {
		init : _init,
        getById:_getById,
        repaymentApply:_repaymentApply,
        batchRepayment:_batchRepayment
	};
}(window, jQuery);

;
var MfCusAssureCompany_List = function(window, $) {
	/** 初始化 */
	var _init = function() {
		myCustomScrollbar({
			obj : "#content", // 页面内容绑定的id
			url : webPath+"/mfCusAssureCompany/findByPageAjax", // 列表数据查询的url
			tableId : "tableassurecompany0001", // 列表数据查询的table编号
			tableType : "thirdTableTag", // table所需解析标签的种类
			pageSize : 30
		});
	};

	/** 新增 */
	var _input = function(url) {// 新增弹框
		top.addFlag = false;
		top.createShowDialog(url, "新增担保公司", '90', '90', function() {
			if (top.addFlag) {
				updateTableData();// 重新加载列表数据
			}
		});
	};

	/** 详情 */
    var _getDetailPage = function (obj, url) {
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        window.location.href=url;
        // top.addFlag = false;
        // top.createShowDialog(webPath + url, "担保公司信息", '90', '90', function () {
        //     if (top.addFlag) {
        //         updateTableData();//重新加载列表数据
        //     }
        // });
    };

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		input : _input,
		getDetailPage : _getDetailPage
	};
}(window, jQuery);

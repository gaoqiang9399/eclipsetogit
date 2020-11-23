;
var MfBusReceTransferMain_List = function(window, $) {
	var _init = function () {
        myCustomScrollbar({
            obj : "#content", //页面内容绑定的id
            url : webPath+"/mfBusReceTransferMain/findByPageAjax", //列表数据查询的url
            tableId : "tablerecetransfermainlist", //列表数据查询的table编号
            tableType : "thirdTableTag", //table所需解析标签的种类
            pageSize:30 //加载默认行数(不填为系统默认行数)
            //,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。

        });

        //初始化转让按钮选择组件事件
        $("input[name=pactNo]").popupList({
            searchOn : true, // 启用搜索
            multiple : false, // false单选，true多选，默认多选
            ajaxUrl : webPath+"/mfBusReceTransferMain/getPactListAjax",
            valueElem : "input[name=pactId]",// 真实值选择器
            title : "合同号",// 标题
            labelShow : false,
            changeCallback : function(elem) {// 回调方法
                BASE.removePlaceholder($("input[name=pactNo]"));
                var sltVal = elem.data("selectData");
                $(".btn-div .pops-value").hide();
                // 跳转至账款转让页面（列表加要件布局）
                // top.window.openBigForm(webPath+"/mfBusReceTransferMain/getReceTransList?pactId="+sltVal.pactId, '帐款转让', myclose);
                window.location.href = webPath+"/mfBusReceTransfer/getReceTransList?pactId="+sltVal.pactId;
            },
            tablehead : {// 列表显示列配置
                "pactNo" : {"disName":"合同编号","width":"20%"},
                "appName" : {"disName":"项目名称","width":"30%"},
                // "pactAmt":{"disName":"合同金额(元)","align":"right","width":"20%","dataType":"money"},
                "usableFincAmt":{"disName":"已转账款金额(元)","align":"right","width":"20%","dataType":"money"}
            },
            returnData : {// 返回值配置
                disName : "pactNo",// 显示值
                value : "pactId"// 真实值
            }
        });
	};

	//账款转让
	var _addReceTransfer = function(obj){
        $(".btn-div .pops-value").click();

	};

	var _getReceDetailPage = function(obj,url){
        top.window.openBigForm(webPath+url, '帐款转让', myclose);
    };

    var _getDetailPage = function(obj,url){
        top.LoadingAnimate.start();
        window.location.href=webPath+url;
        event.stopPropagation();
    }
	return {
		init : _init,
        addReceTransfer : _addReceTransfer,
        getReceDetailPage : _getReceDetailPage,
        getDetailPage : _getDetailPage,
	};
}(window, jQuery);

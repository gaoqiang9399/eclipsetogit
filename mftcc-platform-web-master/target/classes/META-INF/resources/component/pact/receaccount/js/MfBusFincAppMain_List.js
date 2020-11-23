;
var MfBusFincAppMain_List = function(window, $) {
	var _init = function () {
        myCustomScrollbar({
            obj : "#content", //页面内容绑定的id
            url : webPath+"/mfBusFincAppMain/findFincByPageAjax", //列表数据查询的url
            tableId : "tablefincappmainlist", //列表数据查询的table编号
            tableType : "thirdTableTag", //table所需解析标签的种类
            pageSize:30 //加载默认行数(不填为系统默认行数)
            //,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。

        });

        //初始化转让按钮选择组件事件
        $("input[name=confirmId]").popupList({
            searchOn : true, // 启用搜索
            multiple : false, // false单选，true多选，默认多选
            ajaxUrl : webPath+"/mfBusFincAppMain/getPactListAjax",
            valueElem : "input[name=pactId]",// 真实值选择器
            title : "合同信息",// 标题
            labelShow : false,
            changeCallback : function(elem) {// 回调方法
                BASE.removePlaceholder($("input[name=confirmId]"));
                var sltVal = elem.data("selectData");
                $(".btn-div .pops-value").hide();
                // 跳转至账款融资申请页面（表单加列表布局）
                // top.window.openBigForm(webPath+"/mfBusReceTransferMain/getReceTransList?pactId="+sltVal.pactId, '帐款转让', myclose);
                window.location.href = webPath+"/mfBusFincAppMain/input?confirmId="+sltVal.confirmId;
            },
            tablehead : {// 列表显示列配置
                "confirmId" : {"disName":"额度确认编号","width":"20%"},
                "pactNo" : {"disName":"合同编号","width":"20%"},
                "remark" : {"disName":"项目名称","width":"30%"},
                "confirmAmt" : {"disName":"确认额度(元)","align":"right","width":"12%","dataType":"money"},
                "usableFincAmt":{"disName":"可融资金额(元)","align":"right","width":"12%","dataType":"money"}

            },
            returnData : {// 返回值配置
                disName : "pactNo",// 显示值
                value : "confirmId"// 真实值
            }
        });
	};

	//应收账款融资申请
	var _receFincApply = function(obj){
        $(".btn-div .pops-value").click();

	};


    var _getDetailPage = function(obj,url){
        top.LoadingAnimate.start();
        window.location.href=webPath+url;
        event.stopPropagation();
    }
	return {
		init : _init,
        receFincApply : _receFincApply,
        getDetailPage : _getDetailPage,
	};
}(window, jQuery);

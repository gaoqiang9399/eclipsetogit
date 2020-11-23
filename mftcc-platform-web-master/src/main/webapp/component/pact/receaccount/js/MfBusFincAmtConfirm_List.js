;
var MfBusFincAmtConfirm_List = function(window, $) {
	var _init = function () {
        myCustomScrollbar({
            obj : "#content", //页面内容绑定的id
            url : webPath+"/mfBusFincAmtConfirm/findByPageAjax", //列表数据查询的url
            tableId : "tablefincappmainlistforconfirm", //列表数据查询的table编号
            tableType : "thirdTableTag", //table所需解析标签的种类
            pageSize:30 //加载默认行数(不填为系统默认行数)
            //,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。

        });

        //初始化转让按钮选择组件事件
        $("input[name=pactNo]").popupList({
            searchOn : true, // 启用搜索
            multiple : false, // false单选，true多选，默认多选
            ajaxUrl : webPath+"/mfBusFincAmtConfirm/getPactListForFincConfirmAjax",
            valueElem : "input[name=pactId]",// 真实值选择器
            title : "合同信息",// 标题
            labelShow : false,
            changeCallback : function(elem) {// 回调方法
                BASE.removePlaceholder($("input[name=pactNo]"));
                var sltVal = elem.data("selectData");
                $(".btn-div .pops-value").hide();
                window.location.href = webPath+"/mfBusFincAmtConfirm/input?pactId="+sltVal.pactId;
            },
            tablehead : {// 列表显示列配置
                "pactNo" : {"disName":"合同编号","width":"20%"},
                "appName" : {"disName":"项目名称","width":"40%"}
                // ,"endDate":{"disName":"合同到期日","width":"20%"}

            },
            returnData : {// 返回值配置
                disName : "pactNo",// 显示值
                value : "pactId"// 真实值
            }
        });
	};

	//应收账款融资申请
	var _fincAmtConfirm = function(obj){
        $(".btn-div .pops-value").click();

	};

	//获取额度申请详情信息
	var _getDetailInfo = function (obj,url) {
        top.confirmFlag=false;
        top.window.openBigForm(webPath+url, '融资额度确认', function(){
            if(top.confirmFlag){
                updateTableData();
            }
        });
    };

    var _getDetailPage = function(obj,url){
        top.LoadingAnimate.start();
        window.location.href=webPath+url;
        event.stopPropagation();
    }
	return {
		init : _init,
        fincAmtConfirm : _fincAmtConfirm,
        getDetailInfo : _getDetailInfo,
        getDetailPage : _getDetailPage,
	};
}(window, jQuery);

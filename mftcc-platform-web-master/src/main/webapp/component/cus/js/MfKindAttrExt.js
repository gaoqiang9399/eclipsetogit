var MfKindAttrExt = function(window,$){
	var _init = function(){
        myCustomScrollbar({
            obj : "#content", //页面内容绑定的id
            url : webPath + "/mfKindAttrExt/findByPageAjax", //列表数据查询的url
            tableId : "tablekindAttrExt", //列表数据查询的table编号
            tableType : "thirdTableTag", //table所需解析标签的种类
            pageSize:30 //加载默认行数(不填为系统默认行数)
            //,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
        });
    }

    var _kindExtForm_input = function (url){//新增弹框
        top.kindExtFormAddFlag = false;
        top.createShowDialog(url,"新增产品类型",'90','90',function(){
            if(top.kindExtFormAddFlag){
                updateTableData();//重新加载列表数据
            }
        });
    }

    //列表中改变产品类型操作
    var _updateAjax = function(obj,url){
        $.ajax({
            url:webPath+url,
            dataType:'json',
            type:'post',
            success : function(data){
                if (data.flag == "success") {
                    window.top.alert(data.msg, 3);
                    updateTableData();//重新加载列表数据
                } else {
                    window.top.alert(data.msg, 0);
                }
            }
        });
    };

    //列表中改变产品类型操作
    var _deleteAjax = function(obj,url){
        $.ajax({
            url:webPath+url,
            dataType:'json',
            type:'post',
            success : function(data){
                if (data.flag == "success") {
                    window.top.alert(data.msg, 3);
                    updateTableData();//重新加载列表数据
                } else {
                    window.top.alert(data.msg, 0);
                }
            }
        });
    };

	return {
		init : _init,
        updateAjax:_updateAjax,
        kindExtForm_input : _kindExtForm_input
	};
}(window, jQuery);
;
var MfMakingMeetingSummary_List = function(window, $) {
	var _init = function() {
		   myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/mfMakingMeetingSummary/findByPageAjax", //列表数据查询的url
				tableId : "tableMakingMeetingSummaryList", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
		    });
	};
	//跳转至新增
	var _applyInsert = function() {
		top.openBigForm(webPath+"/mfMakingMeetingSummary/input","新增", function(){
 			updateTableData();
 		});	
	};
	//跳转至详情
	var _detail = function(url) {
		top.openBigForm(webPath + url,"详情", function(){
 		});
	};
	//删除
	var _ajaxDelete = function(url) {
        alert(top.getMessage("CONFIRM_DELETE"),2,function(){
            $.ajax({
                url : webPath + url,
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    if (data.flag == "success") {
                        updateTableData();
                    }else{
                        alert("删除失败");
                    }
                },
                error : function() {
                    alert("删除失败");
                }
            });
        });
	};
	return {
		init : _init,
        detail:_detail,
        ajaxDelete:_ajaxDelete,
		applyInsert:_applyInsert,
	};
}(window, jQuery);

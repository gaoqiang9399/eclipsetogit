var MfCreditQueryApp_List = function(window, $){
	var _init = function(){
        myCustomScrollbar({
            obj : "#content", //页面内容绑定的id
            url : webPath + "/mfCreditQueryApp/findByPageAjax?cusNo=" + cusNo, //列表数据查询的url
            tableId : "tableCreditQueryAppList", //列表数据查询的table编号
            tableType : "thirdTableTag", //table所需解析标签的种类
            pageSize:30 //加载默认行数(不填为系统默认行数)
            //,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
        });
		
	};
	//新增
	var _addCreditQueryApp = function(){
        top.openBigForm(webPath+"/mfCreditQueryApp/input?cusNo="+cusNo,"新增征信查询申请", function(){
            window.location.href = window.location.href;
        });
	};
	//详情
	var _getById = function(url){
        top.openBigForm(url,"详情", function(){
        });
	};

	//作废
	var _invalid = function(url){
        alert("作废后，本次征信查询申请将废弃，确认继续？",2,function(){
            $.ajax({
                url : url,
                type : 'post',
                dataType : 'json',
                success : function(data) {
                    if (data.flag == "success") {
                        window.location.href = window.location.href;
                    } else {
                        alert(data.msg, 0);
                    }
                },
                error : function() {
                    alert("操作失败", 0);
                }
            });
        });
	};


	
	return{
		init:_init,
		addCreditQueryApp:_addCreditQueryApp,
        invalid:_invalid,
        getById:_getById
	};
}(window, jQuery);
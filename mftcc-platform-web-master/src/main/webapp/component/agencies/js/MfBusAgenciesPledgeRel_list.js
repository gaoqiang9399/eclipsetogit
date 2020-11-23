var MfBusAgenciesPledgeRel_list=function(window, $){
	//初始化列表
	var _init=function(){
		myCustomScrollbar({
            obj:"#content",//页面内容绑定的id
            url:webPath+"/mfBusAgenciesPledgeRel/findByPageAjax?appId="+appId,//列表数据查询的url
            tableId:"tablemfBusAgenciesPledgeRelList",//列表数据查询的table编号
            // tableType:"thirdTableTag",//table所需解析标签的种类
            tableType:"tableTag",//table所需解析标签的种类
            pageSize:30//加载默认行数(不填为系统默认行数)
        });
	};

	var _getDetailPage = function (obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		// top.LoadingAnimate.start();
		// window.location.href=url;
        top.window.openBigForm(url,"详情",function(){
            // updateTableData();//重新加在列表数据
        });
	};




	//打开登记页面
    function _input(url){
        if(url!=""&&url!=null) {
            var parm = url.split("?")[1];
            var appId = parm.split("=")[1];
        }
        window.location.href=url;
    };
    var _deleteAjax = function(obj, url) {
        alert(top.getMessage("CONFIRM_DELETE"), 2, function() {
            var ajaxParam = {};
            url=webPath+url;
            $.post(url, ajaxParam, function(data) {
                if (data.flag == "success") {
                    alert(top.getMessage("SUCCEED_OPERATION"), 1);
                    updateTableData();
                }else {
                    alert(top.getMessage("FAILED_OPERATION","操作失败！"),0);
                }
            })
        })

    }


	return{
		init:_init,
		getDetailPage:_getDetailPage,
        input:_input,
        deleteAjax:_deleteAjax,
	};
}(window,jQuery);

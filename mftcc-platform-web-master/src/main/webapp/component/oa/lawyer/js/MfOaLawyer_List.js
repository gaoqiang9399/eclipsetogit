;
var MfOaLawyer_List = function(window, $) {
	var _init = function() {
		   myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/mfOaLawyer/findByPageAjax", //列表数据查询的url
				tableId : "tableLawyer0001", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
			//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
		    });
	};
	//跳转至新增
	var lawyerInsert = function() {
		top.openBigForm(webPath+"/mfOaLawyer/input","新增律师信息", function(){
 			updateTableData();
 		});	
	};
	//跳转至详情
    var _ajaxGetById = function(obj ,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.openBigForm(encodeURI(url),"律师信息详情", function(){
            updateTableData();
        });
    };

    //删除律师信息



	return {
		init : _init,
        ajaxGetById: _ajaxGetById,
        lawyerInsert: lawyerInsert
		// printApproveTable:_printApproveTable
	};
}(window, jQuery);

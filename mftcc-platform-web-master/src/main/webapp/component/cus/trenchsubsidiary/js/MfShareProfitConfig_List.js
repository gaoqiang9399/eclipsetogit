;
var MfShareProfitConfig_List = function(window,$){
	var _init=function(){
		$(function(){
		    myCustomScrollbar({
			obj : "#content", //页面内容绑定的id
			url : webPath + "/mfShareProfitConfig/findByPageAjax", //列表数据查询的url
			tableId : "tableMfShareProfitConfigList", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:30 //加载默认行数(不填为系统默认行数)
			//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
		    });
		 });
	};
	// 选择分润配置
	var _choseShareProfitConfig = function(url){
		$.ajax({
			url:webPath + url,
			dataType:"json",
			type:"POST",
			success:function(data){
				if(data.flag == "success"){
					parent.dialog.get('shareProfitConfig').close(data.mfShareProfitConfig).remove();
				}else{
					alert(top.getMessage("ERROR_SELECT"));
				}
			},error:function(){
				alert(top.getMessage("ERROR_DATA_CREDIT","分润配置"));
			}
		});
	};
	// 进入详情页
    var _getById = function(url){
    	top.openBigForm(webPath + url,"分润配置详情", function(){
 			updateTableData();
 		});	
    }
	return{
		init:_init,
		choseShareProfitConfig:_choseShareProfitConfig,
		getById:_getById
	};
}(window,jQuery);
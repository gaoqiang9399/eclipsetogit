;
var MfFinancingOptions_List = function(window, $) {
	var _init = function() {
		myCustomScrollbar({
			obj : "#content", //页面内容绑定的id
			url : webPath + "/mfFinancingOptions/findByPageAjax", //列表数据查询的url
			tableId : "tablefinancingoptionslist", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:30,//加载默认行数(不填为系统默认行数)
			//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			callback:function(){
		    		$('#tablist tr').each(function (){
						var docNo = $(this).children('td').eq(0).children('input').val();
						var docBizNo = $(this).children('td').eq(1).children('input').val();
						if(docNo==""||docBizNo==""){
							$(this).children('td').eq(3).append("<img src="+webPath+"/component/pss/css/image.png>");
						}else{
							$(this).children('td').eq(3).append("<img src="+webPath+"/docUpLoad/viewCompressImage?docNo="+docNo+"&docBizNo="+docBizNo+">");
						}
		    		});
				}//方法执行完回调函数（取完数据做处理的时候）
		    });
	};
	//跳转至新增
	var _applyInsert = function() {
		top.openBigForm(webPath+"/mfFinancingOptions/input","新增金融方案", function(){
 			updateTableData();
 		});	
	};
	//跳转至详情
	var _getById = function(url) {
		top.openBigForm(webPath + url,"金融方案详情", function(){
 			updateTableData();
 		});	
	};
	//删除金融方案
	var _deleteOptions = function(url) {
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
		getById:_getById,
		applyInsert:_applyInsert,
		deleteOptions:_deleteOptions
	};
}(window, jQuery);

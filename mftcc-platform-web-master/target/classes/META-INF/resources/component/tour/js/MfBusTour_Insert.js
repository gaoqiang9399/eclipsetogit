;
var MfBusTour_Insert = function(window, $) {
	var _init = function(fincChildId) {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
			
		});
		$.ajax({
			url:webPath+"/mfBusTour/findByPageAjax?fincChildId="+fincChildId,
			type : 'post',
			dataType : 'json',
			success : function(data) {
			}
			
		});
		myCustomScrollbar({
			obj : "#content", //页面内容绑定的id
			url : webPath+"/mfBusTour/queryRecord?fincChildId="+fincChildId, //列表数据查询的url
			tableId : "tablebustourbase", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:30 //加载默认行数(不填为系统默认行数)
	    });
		$(".table-float-head").css("display","none");
		$(".footer_loader").css("display","none");
	};
	
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url : url,
				data : {
					ajaxData:dataParam
					},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						window.top.alert(data.msg, 3);
						myclose_click();
					} else {
						alert(data.msg, 0);
					}
				},
				error : function() {
				}
			});
		}
	};
	//跳转至详情
	var _dimissionDetail = function(obj,url) {
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.openBigForm(url+"&entryFlag=1","巡视详情", function(){
 			updateTableData();
 		});	
	};
	
	var _myclose = function(){
		myclose_click();
	};
	return {
		init : _init,
		ajaxSave:_ajaxSave,
		myclose:_myclose,
		dimissionDetail:_dimissionDetail
	};
}(window, jQuery);

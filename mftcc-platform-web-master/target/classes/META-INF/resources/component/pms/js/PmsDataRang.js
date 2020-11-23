;
var PmsDataRang=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	//保存
	 var _ajaxInsert=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						  alert(top.getMessage("SUCCEED_OPERATION"),1);
						  top.pmsDataRang=true;
						  myclose_click();
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0)
				}
			});
		}
	};
	var _showList=function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pmsDataRang/findByPageAjax",//列表数据查询的url
			tableId : "tablepms0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			myFilter : true
		//是否有我的筛选
		});
	};
	return{
		init:_init,
		ajaxInsert:_ajaxInsert
	}
}(window,jQuery);
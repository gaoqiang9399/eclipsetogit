;  
var cwZtBooksList = function(window, $) {
	var _init = function(){
		 myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/cwZtBooks/findByPageAjax", //列表数据查询的url
				tableId : "tableztbooks0001", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			 });
	};
	/*
	 * 新增帐套
	 * */
	var _ztbooksInsert = function(){
		top.addFlag = false;
		top.htmlStrFlag = false;
		top.createShowDialog(webPath+"/cwZtBooks/input","新增帐套",'70','70',function(){
			
			if(top.addFlag){
				
				updateTableData();//重新加载列表数据
			}
		});
	};
	
var _saveZtBooksAjax = function (obj){
	var url = $(obj).attr("action");
	var flag= submitJsMethod($(obj).get(0), '');
	if(flag){
		url = $(obj).attr("action");

		var dataParam = JSON.stringify($(obj).serializeArray());
		//loadingAnimate();
		jQuery.ajax({
			url:url,
			data:{ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				//loadingAnimateClose();
				if(data.flag == "success"){
					//top.showName = accNo + '/' + accName;
					top.addFlag = true;
					myclose_showDialogClick();
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
	};
		
	var _goBooksDetail = function (url){		
		top.addFlag = false;
		top.htmlStrFlag = false;
		top.createShowDialog(webPath+url,"帐套详情",'70','70',function(){
			if(top.addFlag){
				updateTableData();//重新加载列表数据
			}
		});		
	};
	
	/*
	 * 修改操作
	 * */
	var _uptZtBooksAjax = function (obj) {
	
	
		var url = $(obj).attr("action");
		var flag= submitJsMethod($(obj).get(0), '');
		if(flag){
			url = $(obj).attr("action");

			var dataParam = JSON.stringify($(obj).serializeArray());
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						top.addFlag = true;
						myclose_showDialogClick();
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
	/*
	 * 删除
	 * */
	var _deleteUpdateBooksAjax = function (url) {
		//console.log(obj);
		//var url = webPath+'/cwZtBooks/deleteUpdateBooksAjax';
		jQuery.ajax({
			url:webPath+"/"+url,
			data:null,
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					top.addFlag = true;
					//alert(data.msg,0);
					alert(top.getMessage("SUCCEED_OPERATION"),1);
					updateTableData();//重新加载列表数据
					myclose_showDialogClick();
				}else if(data.flag == "error"){
					alert(data.msg,0);
				}
			},error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
		
	};
	
	var _copyAddInsert = function(url){
		top.addFlag = false;
		top.htmlStrFlag = false;
		//webPath+"/cwZtBooks/copyAddInput"
		top.createShowDialog(url,"复制帐套",'70','70',function(){
			if(top.addFlag){
				updateTableData();//重新加载列表数据
			}
		});
	};
	/**
	 * 账套复制的功能
	 */
	var _copyAddBooks = function (obj) {
		var url = $(obj).attr("action");
		var flag= submitJsMethod($(obj).get(0), '');
		
		if(flag){
			//loadingAnimate();
			url = $(obj).attr("action");

			var dataParam = JSON.stringify($(obj).serializeArray());
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					//loadingAnimateClose();
					if(data.flag == "success"){
						top.addFlag = true;
						myclose_showDialogClick();
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
		
	};
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ztbooksInsert:_ztbooksInsert,
		saveZtBooksAjax :_saveZtBooksAjax,
		goBooksDetail :_goBooksDetail,
		uptZtBooksAjax :_uptZtBooksAjax,
		deleteUpdateBooksAjax :_deleteUpdateBooksAjax,
		copyAddInsert :_copyAddInsert,
		copyAddBooks :_copyAddBooks,
	};
}(window, jQuery);
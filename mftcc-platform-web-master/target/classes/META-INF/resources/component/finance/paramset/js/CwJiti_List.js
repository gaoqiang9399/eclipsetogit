;  
var cwJiTiList = function(window, $) {
	var _init = function(){
		 myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/cwJiti/findByPageAjax", //列表数据查询的url
				tableId : "tablecwjiti0001", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30, //加载默认行数(不填为系统默认行数)
				callback:function(options,data){
					_addEvent();
		    	}//方法执行完回调函数（取完数据做处理的时候）
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			    });
	};
	/*
	 * 新增计提
	 * */
	var _jiTiInsert = function(){
		top.addFlag = false;
		top.htmlStrFlag = false;
		//window.parent.openBigForm(webPath+"/cwJiti/input","计提方式设置",'90','90');
		//top.createShowDialog
		/*top.createShowDialog(webPath+"/cwJiti/input","新增计提",'90','90',function(){
			if(top.addFlag){
				updateTableData();//重新加载列表数据
			}
		});*/
		createShowDialog(webPath+"/cwJiti/input","新增计提",'90','90',function(){
			 updateTableData();//重新加载列表数据
		});
	};
	
	
	var _getJtItemByjtId = function (url){		
		top.addFlag = false;
		top.htmlStrFlag = false;
		top.createShowDialog(webPath+"/"+url,"计提详情",'90','90',function(){
			if(top.addFlag){
				updateTableData();//重新加载列表数据
			}
		});		
	};
	
	
	var _addEvent = function (){
	//生成凭证
	$('.createVoucher').off('click').on('click', function(){
		var uid = $(this).data('id') + '';
		var param = JSON.stringify({'which': 'jiti', 'uid': uid});
		window.parent.openBigForm(encodeURI(webPath+'/cwVoucherMst/toVoucherAddSet?ajaxData='+param), '凭证新增',_callBackFunction);
	});
	
	//凭证详情
	$('.openLink').off('click').on('click', function(){
		var voucherid = $(this).data('voucherid') + '';
		window.parent.openBigForm(encodeURI(webPath+'/cwVoucherMst/toVoucherEdit?which=review&voucherNo='+voucherid), '凭证详情',updateTableData);
	});
	
	//凭证删除
	$('.deleteVoucher').off('click').on('click', function(){
		var voucherid = $(this).data('voucherid') + '';
		jQuery.ajax({
			url:webPath+'/cwVoucherMst/deleteAjax',
			data:{voucherNo:voucherid},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					alert(top.getMessage("SUCCEED_OPERATION") ,1);
					_callBackFunction();//重新加载列表数据
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	});
	
	};
		

	/*
	 * 返回函数
	 * */
	var _callBackFunction = function () {
		updateTableData();
	};

	
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		jiTiInsert:_jiTiInsert,
		getJtItemByjtId :_getJtItemByjtId,
		addEvent :_addEvent,
		callBackFunction :_callBackFunction,
	};
}(window, jQuery);



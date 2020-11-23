var MfDiscountManage = function(){
	var _ajaxDiscountSaveList =function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			top.LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataForm,
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					top.flag=true;
					top.LoadingAnimate.stop();
					if (data.flag == "success") {
					/*	window.top.alert(data.msg, 3);*/
						//一般为弹出框所以需要改成弹出这样就不用跳转页面
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					top.loadingAnimate.stop();
					window.top.alert(data.msg, 0);
				}
			});
		}
	};
	//新增一个优惠券
	var _ajaxDiscountSave =function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = webPath+"/mfDiscountManage/insertAjax?cusNo="+cusNo;
			var dataForm = JSON.stringify($(formObj).serializeArray());
			top.LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataForm,
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					top.flag=true;
					top.LoadingAnimate.stop();
					if (data.flag == "success") {
					/*	window.top.alert(data.msg, 3);*/
						//一般为弹出框所以需要改成弹出这样就不用跳转页面
						myclose_click();
						_getMfDiscountManageList();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					top.loadingAnimate.stop();
					window.top.alert(data.msg, 0);
				}
			});
		}
	};
	var _getMfDiscountManageList=function(){
		var url = webPath+"/mfDiscountManage/getMfDiscountManageListByCusNoAjax?cusNo="+cusNo;
		$.ajax({
		url:url,
		type:'post',
		dataType:'json',
		success:function(data){ 
			var tableHtmlDiscountManage=data.tableHtmlDiscountManage;
			$("#mfDiscountManageList").html(tableHtmlDiscountManage);
		},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	var  _applyInsertList=function(){
		top.itemId="";
		top.flag=false;
		top.window.openBigForm(webPath+"/mfDiscountManage/input","批量新增",function(){
			if(top.flag){
				window.updateTableData();
			}
		},"90","90");
	};
	var _getDetailPage=function(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.itemId="";
		top.flag=false;
		top.window.openBigForm(url,"优惠券",function(){
			if(top.flag){
				window.updateTableData();
			}
		},"90","90");
	};
	//弹出框
	var _getById = function (obj ,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.window.openBigForm(url,null,function(){});
	};
	var _toUpdatePage = function (obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.window.openBigForm(url,null,function(){});
	};
	return {
		ajaxDiscountSaveList:_ajaxDiscountSaveList,
		applyInsertList:_applyInsertList,
		ajaxDiscountSave:_ajaxDiscountSave,
		getMfDiscountManageList:_getMfDiscountManageList,
		getById:_getById,
		toUpdatePage:_toUpdatePage,
	};
}(window,jQuery);
function getDetailPage(obj,url){
	if(url.substr(0,1)=="/"){
		url =webPath + url; 
	}else{
		url =webPath + "/" + url;
	}
	top.itemId="";
	top.flag=false;
	top.window.openBigForm(url,"优惠券",function(){
		if(top.flag){
			window.updateTableData();
		}
	},"90","90");
}

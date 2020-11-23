;
var TrenchShareProfit = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
		  url = webPath+"/mfBusFincApp/findByTrenchAjax?cusNo=" + cusNo;//放款金额
		  tableId = "tabletrenchShareProfitFincList";
		  if(calcBase == "1"){// 计算基数为客户数
			  url = webPath +"/mfCusCustomer/findByTrenchAjax?trenchUid=" + cusNo;
			  tableId = "tabletrenchShareProfitCusList";
		  }
		  myCustomScrollbar({
		    	obj:"#content",//页面内容绑定的id
		    	url:url,//列表数据查询的url
		    	tableId:tableId,//列表数据查询的table编号
		    	tableType:"thirdTableTag",//table所需解析标签的种类
		    	pageSize:30,//加载默认行数(不填为系统默认行数)
		    	topHeight : 100,//顶部区域的高度，用于计算列表区域高度。
		    	callback:function(){
		    		$("th[class=table-float-th]").eq(0).html('<a href="javascript:void(0);" onclick="TrenchShareProfit.checkAllBox()">全选</a>');
		    	}
		    });
		  $("th[class=table-float-th]").eq(0).html('<a href="javascript:void(0);" onclick="TrenchShareProfit.checkAllBox()">全选</a>');
	};
	//全选
	var _checkAllBox = function(){
		var checkAllFlag = true;//全选标志
		$('.table_content #tab').find($('input[type=checkbox]')).each(function () {
			if(!$(this).prop("checked")){
				checkAllFlag = false;
				return false;
			}
    	});
    	if(checkAllFlag){
    		$('.table_content #tab').find($('input[type=checkbox]')).each(function () {
				$(this).prop("checked",false);
    	 	});
    	}else{
    		$('.table_content #tab').find($('input[type=checkbox]')).each(function () {
				$(this).prop("checked",true);
    	 	});
    	}
	};
	// 跳转至详情页
	var _getDetailPage = function(obj,url){
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		top.LoadingAnimate.start();		
		top.openBigForm(url,"详情",function(){
 		});			
	};
	// 保存选中项
	var _ajaxSave = function(){
		var checkedBox = $("input[type='checkbox']");
		var fincShowIds = new Array()
		var j = 0;
		for(var i = 0;i < checkedBox.length;i++){
			if($(checkedBox[i]).is(":checked")){
				if(calcBase == "1"){
					fincShowIds[j] = $(checkedBox[i]).parent().parent().find("[name='cusNo']").val();
				}else{
					fincShowIds[j] = $(checkedBox[i]).parent().parent().find("[name='fincShowId']").val();
				}
				j++;
			}
		}
		if(fincShowIds.length == 0){
			window.top.alert("没有选中项", 3);
			return;
		}
		DIALOG.confirm("确定要对选中项进行分润操作么？", function(){
			$.ajax({
				url : webPath + "/mfCommissionChangeRecord/insertAjax",
				data : {
					ajaxData:fincShowIds.toString(),
					calcBase:calcBase,
					cusNo:cusNo
					},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						updateTableData();
					} else {
						alert(data.msg, 0);
					}
				},
				error : function() {
					alert(top.getMessage("FAILED_OPERATION"),0);
				}
			});
		});
	}
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		getDetailPage:_getDetailPage,
		ajaxSave:_ajaxSave,
		checkAllBox:_checkAllBox
		
	};
}(window, jQuery);
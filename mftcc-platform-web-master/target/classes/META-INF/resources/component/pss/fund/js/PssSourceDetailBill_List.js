;
var pssSourceDetailBillList = function(window, $){
	var _initRec = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssSourceDetailBill/getListPageForRecAjax",//列表数据查询的url
			tableId : "tablepsssourcedetailbill0011",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			data : {"cusNo":cusNo},//指定参数 (不会过滤掉已经封挡的数据)
			topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
			callback : function(){
				isCheckAll = false;
			}
		});
		
		addCheckAllEvent();
		
	};
	
	var _initPay = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssSourceDetailBill/getListPageForPayAjax",//列表数据查询的url
			tableId : "tablepsssourcedetailbill0011",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			data : {"supplierId":supplierId},//指定参数 (不会过滤掉已经封挡的数据)
			topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
			callback : function(){
				isCheckAll = false;
			}
		});
		
		addCheckAllEvent();
		
	};
	
	var _initBefRec = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssSourceDetailBill/getListPageForBefRecAjax",//列表数据查询的url
			tableId : "tablepsssourcedetailbill0012",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			data : {"cusNo":cusNo},//指定参数 (不会过滤掉已经封挡的数据)
			topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
			callback : function(){
				isCheckAll = false;
			}
		});
		
		addCheckAllEvent();
		
	};
	
	var _initBefPay = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssSourceDetailBill/getListPageForBefPayAjax",//列表数据查询的url
			tableId : "tablepsssourcedetailbill0012",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			data : {"supplierId":supplierId},//指定参数 (不会过滤掉已经封挡的数据)
			topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
			callback : function(){
				isCheckAll = false;
			}
		});
		
		addCheckAllEvent();
		
	};
	
	var _billConfirm = function(){
		var sourceBillNos = getCheckedSNos();
		if(sourceBillNos != ''){
			top.flag = true;
			top.sourceBillNos = sourceBillNos;
			myclose_showDialogClick();
		}
	};
	
	//去除表头 点击事件 换成 全选事件
	function addCheckAllEvent() {
		 $(".table-float-head").delegate("th:first-child","click",function(){
			if (isCheckAll) {
				$(".table_content").find(':checkbox').each(function() {
					this.checked = false;
				});
				isCheckAll = false;
			} else {
				$(".table_content").find(':checkbox').each(function() {
					this.checked = true;
				});
				isCheckAll = true;
			}
		});
	};
	
	//获取收款单选择的收款单NO
	function getCheckedSNos(){
		var sourceBillNos = "";
        var sourceBillNo = "";
        var vals=0;
        $(".table_content").find(':checkbox').each(function() {
        	if($(this).is(":checked")){
        		sourceBillNo = $(this).val().substring(13);
        		if(sourceBillNo != "" && sourceBillNo != null){
            		sourceBillNos = sourceBillNos + ",'" + sourceBillNo + "'";
            		vals++;
        		}
        	}
        });
        if(vals==0){
        	alert(top.getMessage("FIRST_SELECT_FIELD","需要操作的单据"), 1);
        }else{
        	sourceBillNos = sourceBillNos.substring(1);
        }
        return sourceBillNos;
	};
	
	return {
		initRec : _initRec,
		initPay : _initPay,
		initBefRec : _initBefRec,
		initBefPay : _initBefPay,
		billConfirm : _billConfirm 
	};
}(window, jQuery);
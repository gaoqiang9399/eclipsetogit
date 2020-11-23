;
var pssBuySaleExpBillList = function(window, $){
	var _init = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssBuySaleExpBill/findByPageAjax",//列表数据查询的url
			tableId : "tablepssbuysaleexpbill0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
			callback : function(){
				isCheckAll = false;
				
				//停止checkBox事件冒泡
				$("#tablist>tbody tr").find(':checkbox').bind('click', function(event) {
					event.stopPropagation();
				});
			}
		});
		
		addCheckAllEvent();
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
	
	var _payExp = function(){
		var buySaleExpIds = getCheckedBSEIds();
		if(buySaleExpIds != ''){
			var supplierId = buySaleExpIds.split(",", 1)[0];
			buySaleExpIds = buySaleExpIds.substring(supplierId.length + 1);
			var url = webPath+'/pssOtherPayBill/input?buySaleExpIds='+buySaleExpIds+'&supplierId='+supplierId;
			window.parent.openBigForm(url, '支付费用', updateTableData);
		}
	};
	
	//获取其采购销售费用清单选择的其采购销售费用清单ID
	function getCheckedBSEIds(){
		var buySaleExpIds = "";
        var buySaleExpId = "";
        var flag = false;
        var supplierIdFirstVal = "";
        var supplierIdTempVal = "";
        var vals=0;
        $(".table_content").find(':checkbox').each(function() {
        	if($(this).is(":checked")){
        		buySaleExpId = $(this).val().substring(13);
        		if(buySaleExpId != "" && buySaleExpId != null){
        			supplierIdTempVal = $(this).parent().parent().find('input[name="supplierId"]').val();
        			if(supplierIdFirstVal == ""){
        				supplierIdFirstVal = supplierIdTempVal;
        				buySaleExpIds = buySaleExpIds + "," + supplierIdFirstVal;
        			}
        			if(supplierIdFirstVal != supplierIdTempVal){
        				alert("请选择供应商相同的单据",1);
        				flag = true;
        				return false;
        			}
            		buySaleExpIds = buySaleExpIds + "," + buySaleExpId;
            		vals++;
        		}
        	}
        });
        if(flag){
        	return "";
        }
        if(vals==0){
        	alert(top.getMessage("FIRST_SELECT_FIELD","需要操作的其采购销售费用清单"), 1);
        }else{
        	buySaleExpIds = buySaleExpIds.substring(1);
        }
        return buySaleExpIds;
	};
	return {
		init : _init,
		payExp : _payExp
	};
}(window, jQuery);
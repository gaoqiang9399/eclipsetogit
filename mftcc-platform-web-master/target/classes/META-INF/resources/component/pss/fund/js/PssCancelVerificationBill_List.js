;
var pssCancelVerificationBillList = function(window, $){
	var _init = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssCancelVerificationBill/findByPageAjax",//列表数据查询的url
			tableId : "tablepsscancelverificationbill0001",//列表数据查询的table编号
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
	
	
	//查看
	var _getByIdThis = function(obj, url){
//		window.location.href = url;
		window.parent.openBigForm(url, '查看核销单', updateTableData);
	};
	
	//批量删除
	var _deleteBatch = function(){
		var cancelIds = getCheckedCIds();
		if(cancelIds != ''){
			var url = webPath+'/pssCancelVerificationBill/deleteCBBatchAjax';
			var dataParam = '[{"name":"cancelIds","value":"'+cancelIds+'"}]'; 
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						if(data.data.failed !=''){
							alert(data.data.failed ,2);
						}else{
							alert(top.getMessage("SUCCEED_OPERATION") ,1);
						}
						 updateTableData();//重新加载列表数据
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
	
	//获取收款单选择的收款单ID
	function getCheckedCIds(){
		var cancelIds = "";
        var cancelId = "";
        var vals=0;
        $(".table_content").find(':checkbox').each(function() {
        	if($(this).is(":checked")){
        		cancelId = $(this).val().substring(9);
        		if(cancelId != "" && cancelId != null){
            		cancelIds = cancelIds + "," + cancelId;
            		vals++;
        		}
        	}
        });
        if(vals==0){
        	alert(top.getMessage("FIRST_SELECT_FIELD","需要操作的收款单"), 1);
        }else{
        	cancelIds = cancelIds.substring(1);
        }
        return cancelIds;
	};
	
	return {
		init : _init,
		getByIdThis : _getByIdThis,
		deleteBatch : _deleteBatch
	};
}(window, jQuery);
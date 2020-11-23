;
var pssSettleAccounts_List = function(window, $){
	var _init = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssSettleAccounts/findByPageAjax",//列表数据查询的url
			tableId : "tablepsssettleaccounts0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
			callback : function(){
				
			}
		});
	};
	
	//结账
	var _settleaccounts = function(){
		var pssSettleAccountsDateVal = $('#pssSettleAccountsDate').val();
		if(pssSettleAccountsDate != undefined && pssSettleAccountsDate != ''){
			var url = webPath+'/pssSettleAccounts/insertAjax';
			var dataParam = '[{"name":"settleAccountsDate","value":"'+pssSettleAccountsDateVal+'"}]'; 
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
		}else{
			alert("请选择结账日期！", 1);
		}
	};
	
	//反结账
	var _antiSettlement = function(){
		var url = webPath+'/pssSettleAccounts/deleteAjax';
		jQuery.ajax({
			url:url,
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
	};
	
	return{
		init : _init,
		settleaccounts : _settleaccounts,
		antiSettlement : _antiSettlement
	};
}(window, jQuery);
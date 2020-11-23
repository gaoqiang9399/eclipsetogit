;
var pssOtherPayBillList = function(window, $){
	var _init = function(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pssOtherPayBill/findByPageAjax",//列表数据查询的url
			tableId : "tablepssotherpaybill0001",//列表数据查询的table编号
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
		
		$('#pssChkOPB').hover(function(){
	    	$('#pssChkOPB').find('button:eq(0) span').addClass('triangle-up');
	    	$('#pssChkOPB').find('button:eq(0) span').removeClass('triangle-down');
	    	$('#pssHideChkOPB').addClass('pss-chk-left').addClass('pss_block');
	    },function(){
	    	$('#pssChkOPB').find('button:eq(0) span').addClass('triangle-down');
	    	$('#pssChkOPB').find('button:eq(0) span').removeClass('triangle-up');
	    	$('#pssHideChkOPB').removeClass('pss-chk-left').removeClass('pss_block');
	    });
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
		window.parent.openBigForm(url, '其他支出单', updateTableData);
	};
	
	//批量删除
	var _deleteBatch = function(){
		var otherPayIds = getCheckedOPIds();
		if(otherPayIds != ''){
			var url = webPath+'/pssOtherPayBill/deleteOPBBatchAjax';
			var dataParam = '[{"name":"otherPayIds","value":"'+otherPayIds+'"}]'; 
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
	
	//批量审核
	var _auditBatch = function(){
		var otherPayIds = getCheckedOPIds();
		if(otherPayIds != ''){
			var url = webPath+'/pssOtherPayBill/auditBatchAjax';
			var dataParam = '[{"name":"otherPayIds","value":"'+otherPayIds+'"}]'; 
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
	
	//批量反审核
	var _reAuditBatch = function(){
		var otherPayIds = getCheckedOPIds();
		if(otherPayIds != ''){
			var url = webPath+'/pssOtherPayBill/reAuditBatchAjax';
			var dataParam = '[{"name":"otherPayIds","value":"'+otherPayIds+'"}]'; 
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
	function getCheckedOPIds(){
		var otherPayIds = "";
        var otherPayId = "";
        var vals=0;
        $(".table_content").find(':checkbox').each(function() {
        	if($(this).is(":checked")){
        		otherPayId = $(this).val().substring(11);
        		if(otherPayId != "" && otherPayId != null){
            		otherPayIds = otherPayIds + "," + otherPayId;
            		vals++;
        		}
        	}
        });
        if(vals==0){
        	alert(top.getMessage("FIRST_SELECT_FIELD","需要操作的其他支出单"), 1);
        }else{
        	otherPayIds = otherPayIds.substring(1);
        }
        return otherPayIds;
	};
	
	var _batchPrintBill = function(){
		var otherPayIds = getCheckedOPIds();
		if (otherPayIds != '') {
			
			window.top.alert("请确认是否打印其他支出单<br/>如果未安装打印插件,请点击<a href=\"javascript:void(0);\" onclick=\"window.location.href = webPath+'/pssPrintBill/downLoadPrintPlugin';\">下载</a>", 2, function() {
				var fileNamePrefix = "'QTZCD'";
				var templateFileName = "'templateFile_pssqtzcd_batch.doc'";
				var printBizType = "'QTZCD'";
				window.location.href = 'PageOffice://|'+basePath+'component/pss/batchPrint/batchPrintEntrace.jsp?printBizType='+printBizType+'&templateFileName='+templateFileName+'&fileNamePrefix='+fileNamePrefix+'&pssBillsJson='+encodeURIComponent(JSON.stringify(otherPayIds.split(",")))+'|width=1200px;height=800px;||';
			});

		}
	};
	
	return {
		init : _init,
		getByIdThis : _getByIdThis,
		deleteBatch : _deleteBatch,
		auditBatch : _auditBatch,
		reAuditBatch : _reAuditBatch,
		batchPrintBill : _batchPrintBill
	};
}(window, jQuery);
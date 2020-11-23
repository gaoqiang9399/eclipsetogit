function getStandard(){
		var takeNode = $("select[name=takeNode]").val();  
		if (takeNode == "1") {
			makeOptionsJQ(options, '1');
			$("select[name=standard]").val("1");
		} else if (takeNode == "2") {
			makeOptionsJQ(options, '1,2,3');
			$("select[name=standard]").val("1");
		}else if (takeNode == "3") {
			makeOptionsJQ(options, '1,4');
			$("select[name=standard]").val("1");
		}else if (takeNode == "4") {
			makeOptionsJQ(options, '1,5');
			$("select[name=standard]").val("1");
		}else if (takeNode == "5") {
			makeOptionsJQ(options, '1,6');
			$("select[name=standard]").val("1");
		}
	};
	function check(){
		
	};
	function ajaxInsertThis(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			$.ajax({
				url:url,
				data:{ajaxData:dataParam,tableId:top.tableId},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.flag == "success"){
						  window.top.alert(data.msg,1);
						  top.addFlag = true;
						  top.mfSysFeeItemList = data.mfSysFeeItemList;
						  top.mfSysFeeItem = data.mfSysFeeItem;
						  myclose_click();
					}else{
						  alert(top.getMessage("ERROR_INSERT"),0);
					}
				},error:function(){
					alert(top.getMessage("ERROR_INSERT"),0);
				}
			}); 
		}
	};
	
	/*$("input[name=itemNo]").popupSelection({
		ajaxUrl:webPath+'/mfSysFeeItem/getPageFeeItemAjax?feeStdNo='+feeStdNo,//异步获取选项的url
		inline:true,//下拉模式
		multiple:false,//多选选
		}
});*/
	
	
	/*function selectFeeItemDialog(callback,obj){
		var feeStdNo =$(obj).parents().find("form").find("input[name=feeStdNo]").val();
		dialog({
			id:"sysFeeItemDialog",
			title:'基础费用项',
			url: webPath+'/mfSysFeeItem/getPageFeeItem?feeStdNo='+feeStdNo,
			width:650,
			height:300,
			backdropOpacity:0,
			onshow:function(){
				this.returnValue = null;
			},onclose:function(){
				if(this.returnValue){
					//返回对象的属性fincUse,fincUseShow
					if(typeof(callback)== "function"){
						callback(this.returnValue);
					}else{
					}
				}
			}
		}).showModal();
	};
	function feeItemClose(feeItem){
		$("input[name=itemName]").val(feeItem.itemName);
		$("input[name=itemNo]").val(feeItem.itemNo);
		$("input[name=itemName]").removeAttr("readonly");
	};*/
	//删除费用项
	function ajaxDeleteThis(){
		var id = $("input[name=id]").val();
		var url =webPath+"/mfSysFeeItem/deleteAjax?id="+id;
		alert(top.getMessage("CONFIRM_DELETE"),2,function(){
			$.ajax({
				url:url,
				data:{},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.flag == "success"){
						top.deleteFlag=true;
						myclose_click();
					}else{
						window.top.alert(top.getMessage("ERROR_INSERT"),1);
					}
				},error:function(){
					alert(top.getMessage("ERROR_INSERT"),0);
				}
			}); 
		});
	};
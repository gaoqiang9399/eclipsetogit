var mfDynamicFormList = function(window, $){
	var _init = function(){
		 myCustomScrollbar({
			obj : "#content",// 页面内容绑定的id
			url : path + "/MfDynamicFormActionAjax_findByPageAjax.action",// 列表数据查询的url
			tableId : "tablecredit0001",// 列表数据查询的table编号
			tableType : "thirdTableTag",// table所需解析标签的种类
			pageSize : 30, // 加载默认行数(不填为系统默认行数)
			callback : function() {
				$("table").tableRcswitcher({
					name : "useFlag",
					onText : "启用",
					offText : "停用"
				});
			}// 方法执行完回调函数（取完数据做处理的时候）
		  });
	};
	
	//新增表单操作
	var _addCreditForm = function(){
		var url = webPath+"/MfDynamicForm/input";
		top.openBigForm(path + url,"新增客户授信申请动态表单","80","80");
	};
	
	//新增动态表单--打开动态表单设计器
	var _getCreditForm = function(op,url){
		var id = url.split("?")[1].split("&")[1].split("=")[1];
		var motherFormId = url.split("?")[1].split("&")[2].split("=")[1];
		$.ajax({
			url:webPath+ "/MfDynamicFormActionAjax_checkForm.action",
			type:'post',
			data:{id:id,formtype:op},
			success:function(data){
				if(data.flag=="1"){
					var url = webPath + '/tech/dragDesginer/openForm.action?formId='+data.formNo;
					window.open(url,'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
				}else{
					mfDynamicFormList.doCopyForm(op,motherFormId,id);
				}
			},
			error:function(){
				alert("打开表单请求异常");
			}
		});
	};
	
	//复制表单
	var _doCopyForm = function(op,motherFormId,id){
		$.ajax({
			url:webPath+ "/MfDynamicFormActionAjax_doCopyForm.action",
			type:'post',
			data:{formtype:op,formid_old:motherFormId,id:id},
			success:function(data){
				if(data.flag=="success"){
					var url = webPath +'/tech/dragDesginer/openForm.action?formId='+data.formid_new;
					window.open(url,'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
				}else{
					window.top.alert(data.msg,0);
				}
			},error:function(){
				alert("打开表单请求异常");
			}
		});
	};
	
	//重置表单
	var _restoreForm = function(obj,url){
		var id = url.split("?")[1].split("&")[1].split("=")[1];
		alert(top.getMessage("CONFIRM_OPERATION_SERIOUS", ""),2,function(){
			$.ajax({
				url:webPath+ "/MfDynamicFormActionAjax_doRestoreForm.action",
				type:'post',
    			data:{id:id},
    			async:false,
				success:function(data){
					if(data.flag=="success"){
						window.top.alert(data.msg,1);
					}else{
						window.top.alert(data.msg,0);
					}
				},error:function(){
					alert("重置表单操作异常");
				}
			});
		});
	};

	//表单编辑
	var _updateCreditDyForm = function(obj,url){
		top.openBigForm(url,"表单编辑","10","80");
	};
	
	//删除操作
	var _deleteCredit = function(obj,url){
		var id = url.split("?")[1].split("=")[1];
		$.ajax({
			url:webPath+ "/MfDynamicFormActionAjax_checkDelCredit.action",
			type:"post",
			data:{id:id},
			success:function(data){
				if(data.flag=="1"){
					window.top.alert("该表单已被使用，不能删除！",0);
				}else{
					ajaxTrDelete(obj,url);
				}
			},
			error:function(){
				alert("删除表单请求异常");
			}
		});
	};
	
	return{
		init:_init,
		addCreditForm:_addCreditForm,
		getCreditForm:_getCreditForm,
		doCopyForm:_doCopyForm,
		restoreForm:_restoreForm,
		updateCreditDyForm:_updateCreditDyForm,
		deleteCredit:_deleteCredit
	};
}(window,jQuery);
var mfOaDebtExpenseList=function(window, $){
	var _init = function () {
		_openList();
		_debtInsert();
		_closeCallBack();
		_returnDebt();
		_expenseInsert();
		_getSysUserInfo();
		
	};
	var _expenseInsert = function () {
		$("#expenseInsert").bind("click", function(event){
			top.window.openBigForm(webPath+'/mfOaExpense/input','新增报销申请',_closeCallBack);
		});
	};
	_openList=function(){
		 myCustomScrollbar({
		    obj:"#content",//页面内容绑定的id
		    url:webPath+"/mfOaDebtexpense/findByPageAjax",//列表数据查询的url
		    tableId:"tabledebtexpense0001",//列表数据查询的table编号
		    tableType:"thirdTableTag",//table所需解析标签的种类
		    pageSize:30,//加载默认行数(不填为系统默认行数)
		    ownHeight : true,
		    callback:function(){
	    		top.LoadingAnimate.stop();
			}//方法执行完回调函数（取完数据做处理的时候）
		 });
	};
	//进入借款申请表单
	var _debtInsert = function () {
		$("#debtInsert").bind("click", function(event){
			top.window.openBigForm(webPath+'/mfOaDebt/input','新增借款',_closeCallBack);
		});
		
	};
	function _getSysUserInfo() {
		$.ajax({
			type : "POST",
			url : url,
			dataType : "json",
			success : function(data) {
				var formData = data.formData;
				if(formData.mobile==undefined||formData.mobile==""||formData.mobile==false){
					$("#user_mobile").text("无资料");
				}else{
					$("#user_mobile").text(formData.mobile);
				}
				if(formData.opName==undefined||formData.opName==""||formData.opName==false){
					$("#user_name").text("无资料");
				}else{
					$("#user_name").text(formData.opName);
				}
				if(formData.job==undefined||formData.job==""||formData.job==false){
					$("#user_job").text("无资料");
				}else{
					$("#user_job").text(formData.job);
				}
				if(formData.brName==undefined||formData.brName==""||formData.brName==false){
					$("#user_brName").text("无资料");
				}else{
					$("#user_brName").text(formData.brName);
				}
				if(formData.idNo==undefined||formData.idNo==""||formData.idNo==false){
					$("#user_idNo").text("无资料");
				}else{
					$("#user_idNo").text(formData.idNo);
				}
				if(formData.email==undefined||formData.email==""){
					$("#user_email").text("无资料");
				}else{
					$("#user_email").text(formData.email);
				}
				var headImg = data.headImg;
				if(data.ifUploadHead!="1"){
				    headImg = "themes/factor/images/"+headImg;
				}
				headImg = encodeURIComponent(encodeURIComponent(headImg));
				document.getElementById('headImgShow').src=webPath+"/uploadFile/viewUploadImageDetail?srcPath="+headImg+"&fileName=op_user.jpg";
			},
			error : function(xmlhq, ts, err) {
				alert(xmlhq + "||" + ts + "||" + err);
			}
		});
	}
	//打开还款申请表单
	var _returnDebt = function () {
		$("#returnDebt").bind("click", function(event){
			var url=webPath+'/mfOaDebtReturnHis/getById';
			top.window.openBigForm(url,'还款信息核对',_closeCallBack);
		});
	};
	var _closeCallBack = function (){
    	myclose();
   };
   var _approvalHis=function (obj ,url){
		var gaga=url.split("?")[1];
		var parmArray=gaga.split("&");
		var relId= parmArray[0].split("=")[1];
		var busType = parmArray[1].split("=")[1];
				if("1"==busType){
					top.addFlag = false;
					url = webPath+"/mfOaExpense/approvalHis?expenseId="+relId;
					top.openBigForm(url,"审批历史", function() {
						myclose();
						if (top.addFlag) {
							window.location.reload();
						}
					});		
				}else if("2"==busType){
					top.addFlag = false;
					url = webPath+"/mfOaDebt/approvalHis?debtId="+relId;
					top.openBigForm(url,"审批历史", function() {
						myclose();
						if (top.addFlag) {
							window.location.reload();
						}
					});	
				}				
   };
   var _ajaxGetById = function (obj ,url){
	   	var gaga=url.split("?")[1];
		var parmArray=gaga.split("&");
		var relId= parmArray[0].split("=")[1];
		var debtexpenseSts = parmArray[1].split("=")[1];
		var busType = parmArray[2].split("=")[1];
		//得到busType的值，进行判定如果是2，弹出借款信息详情。
		if("1"==busType){
			top.addFlag = false;
			url = webPath+"/mfOaExpense/input?expenseId="+relId;
			top.openBigForm(url,"报销申请信息", function() {
			myclose();
				if (top.addFlag) {
					window.location.reload();
				}
			});		
		}
		else if("2"==busType){
			top.addFlag = false;
			if("1"==debtexpenseSts){
				url = webPath+"/mfOaDebt/getById?debtId="+relId;
			}else{
				url = webPath+"/mfOaDebt/getById01?debtId="+relId;
			}
			top.openBigForm(url,"借款申请信息", function() {
				myclose();
				if (top.addFlag) {
					window.location.reload();
				}
			});
		}
		else if("3"==busType){
			top.addFlag = false;
			url = webPath+"/mfOaDebtReturnHis/getByRealId?hisId="+relId;
			top.openBigForm(url,"还款申请信息", function() {
				myclose();
				if (top.addFlag) {
					window.location.reload();
				}
			});
		}
   };
   var _ajaxTrDeleteOaDebtExpenseList = function (obj,url, func_success){
		window.top.alert("是否确认删除!",2,function(){
			var gaga=url.split("?")[1];
			var parmArray=gaga.split("&");
			var relId= parmArray[0].split("=")[1];
			var busType = parmArray[1].split("=")[1];
			//得到busType的值，进行判定如果是2，弹出借款信息详情。
			if("1"==busType){
				url=webPath+"/mfOaExpense/deleteAjax?expenseId="+relId;
			}
			else if("2"==busType){
				url = webPath+"/mfOaDebt/deleteAjax?debtId="+relId;
			}else if("3"==busType){
				url = webPath+"/mfOaDebtReturnHis/deleteAjax?hisId="+relId;
			}
			jQuery.ajax({
				url:url,
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
						window.location.reload();
					}else if(data.flag == "error"){
						alertFlag.Alert(data.msg);
					}
				},error:function(data){
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
			$(obj).parents(".content_form").hide();
		});

   };
	return {
		init:_init,
		ajaxGetById:_ajaxGetById,
		approvalHis:_approvalHis,
		ajaxTrDeleteOaDebtExpenseList:_ajaxTrDeleteOaDebtExpenseList,
	};
}(window, jQuery);
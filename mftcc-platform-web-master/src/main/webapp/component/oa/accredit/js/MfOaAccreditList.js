var mfOaAccreditList=function(window, $){
	var _init = function () {
		 myCustomScrollbar({
			    obj:"#content",//页面内容绑定的id
			    url:webPath+"/mfOaAccredit/findByPageAjax",//列表数据查询的url
			    tableId:"tableaccredit0001",//列表数据查询的table编号
			    tableType:"thirdTableTag",//table所需解析标签的种类
			    pageSize:30,//加载默认行数(不填为系统默认行数)
			    topHeight : 50, //顶部区域的高度，用于计算列表区域高度。
			    data:{accreditSts:"1"},
			    callback:function(){
		    		top.LoadingAnimate.stop();
				}//方法执行完回调函数（取完数据做处理的时候）
			 });
		_accreditInsert();
//		_getSysUserInfo();
		_accreditHis();
		
	};
	var _accreditInsert = function () {
		$("#accreditInsert").bind("click", function(event){
			top.addFlag = false;
			top.window.openBigForm(webPath+'/mfOaAccredit/input','工作托管',_closeCallBack);
		});
	};
	var _accreditHis = function () {
		$("#accreditHis").bind("click", function(event){
			top.addFlag = false;
			top.window.openBigForm(webPath+'/mfOaAccredit/getListPageForHis','托管历史',_closeCallBack);
		});
	};
	var _closeCallBack = function (obj){
    	myclose();
    	if (top.addFlag) {
			window.location.reload();
		}
   };
   var _updateAccreitSts = function (obj,urlone){
	   alert(top.getMessage("CONFIRM_ACCREDIT_TAKE"),2,function(){
  			jQuery.ajax({
  				url:urlone,
  				type:"POST",
  				dataType:"json",
  				beforeSend:function(){  
  				},success:function(data){
  					if(data.flag == "success"){
  						window.top.alert(data.msg+"共回收任务："+data.count+"条，其中托管期间新的任务共："+data.countNew+"条。",3);
  						window.location.reload();
  					}else if(data.flag == "error"){
  						$.myAlert.Alert(data.msg);
  					}
  				},error:function(data){
  					alert(top.getMessage("FAILED_OPERATION"," "),0);
  				}
  			});
		   });
   };
	var _getSysUserInfo = function (){
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
	};

 
	return {
		init:_init,
		updateAccreitSts: _updateAccreitSts
	};
}(window, jQuery);
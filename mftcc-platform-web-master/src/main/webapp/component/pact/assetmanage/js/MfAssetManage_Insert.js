;
var MfAssetManage_Insert = function(window, $) {
	var _init = function() {
		$(".scroll-content").mCustomScrollbar({//滚动条的生成
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};

	 _myclose = function (){//关闭当前弹窗的方法
		 myclose_click();//关闭弹窗
		 //window.location.href = webPath+"/mfCusWhitename/getListPage";//重新刷新列表
		 };
	//demo新增
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag && isf == '1'){
			window.top.alert("是否提交审批？",2,function(){
				submit(obj);
			},function(){
				save(obj);
			});
		}else if(flag && isf == '0'){
            submit(obj);
		}
	};

	var _updateApply = function(obj){
		window.top.alert("是否确认提交？",2,function(){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){ 
					LoadingAnimate.start();
				},success:function(data){
					if(data.flag == "success"){
						top.flag = true;
						window.top.alert(data.msg,3);
						window.location.reload();
						myclose_click();
						//$(top.window.document).find("#showDialog .close").click();
					}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
				},error:function(data){
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				},complete:function(){
					LoadingAnimate.stop();
				}
			});
		})
	}
	/**
	 * 申请保存
	 */
	function save(obj){
		var dataParam = JSON.stringify($(obj).serializeArray());	
		var url = $(obj).attr("action");
		jQuery.ajax({
			url:url,
			data:{ajaxData:dataParam},
			type:"POST",
			dataType:"json",
			beforeSend:function(){ 
				LoadingAnimate.start();
			},success:function(data){
				if(data.flag == "success"){
					top.alert(data.msg,1);
					myclose_click();//保存完成之后关闭弹窗，回到列表
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			},complete:function(){
				LoadingAnimate.stop();
			}
		});
	}
	/**
	 * 申请保存且提交
	 */
	function submit(obj){
		var dataParam = JSON.stringify($(obj).serializeArray());
		jQuery.ajax({
			url:"/mfAssetManage/applyUpdate",
			data:{ajaxData:dataParam,assetId:relNo,applyStatus:applyStatus},
			type:"POST",
			dataType:"json",
			beforeSend:function(){ 
				LoadingAnimate.start();
			},success:function(data){
				if(data.flag == "success"){
					window.top.alert(data.msg,3);
					myclose_click();
				}else if(data.flag == "error"){
					 alert(data.msg,0);
				}
			},error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			},complete:function(){
				LoadingAnimate.stop();
			}
		});
	}
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		myclose : _myclose,
		ajaxSave:_ajaxSave,
		
	};
}(window, jQuery);

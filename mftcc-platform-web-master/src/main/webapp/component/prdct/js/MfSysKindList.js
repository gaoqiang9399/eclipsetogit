;
var MfSysKindList=function(window, $){
	//初始化产品配置
	var _init = function(){
		$(".nav-content").mCustomScrollbar({
			advanced: {
				updateOnContentResize: true,
			}
		});
		_getKindList();
	};	
	//获取产品设置下的产品列表
	var _getKindList = function(){
		$.ajax({
			url:webPath+'/mfSysKind/getKindListAjax',
			success:function(data){
				if(data.flag=='success'){
					var htmlStr = getKindListHtml(data.mfSysKindList);
					$(".kind-list").html(htmlStr);
					$(".nav-content").mCustomScrollbar("update");
					$(".kindUseFlag").useFlagRcswitcher({name:"useFlag",height:18,onText:"启用",offText:"禁用",callback:function(obj){;
						_getKindList();
					}});
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl),0);
			}
		});
	};
	
	//获取产品列表的html
	var getKindListHtml = function(mfSysKindList){
		var htmlStr ="";
		$.each(mfSysKindList,function(i,mfSysKind){
			var disableStr="";
			var useFlagStr="禁用";
			if(mfSysKind.useFlag=='0'){
				disableStr="disabled";
				useFlagStr="启用";
			}
			var remark = mfSysKind.remark;
			if(remark==null){
				remark="";
			}
			htmlStr = htmlStr+'<div class="list-item bg-white '+disableStr+'" data-kindno="'+mfSysKind.kindNo+'" onclick="MfSysKindList.getkindConfig(this);">'
			+'<div class="block-header"><i class="i i-chanpin"></i><span class="kind-name">'+mfSysKind.kindName+'</span></div>'
			+'<div class="block-body" title="'+remark+'">'+remark+'</div>'
			+'<div class="block-footer">'
			+'<div class="full-div margin_bottom_10" title="'+mfSysKind.brNo+'"><i class="i i-dian"></i>'+mfSysKind.brNo+'</div>'
			+'<div class="row"><div class="col-md-6 half-div"><i class="i i-dian"></i>'+mfSysKind.vouType+'</div><div class="col-md-6 half-div"><i class="i i-dian"></i>'+mfSysKind.cusType+'</div></div>'
			+'</div>'
			+'<span class="kindUseFlag use-link"><a href="'+webPath+'/mfSysKind/updateUseFlagAjax?kindNo='+mfSysKind.kindNo+'">'+mfSysKind.useFlag+'</a></span>'
			+'<i class="i i-x5 del-icon" onclick="MfSysKindList.deleteKind(this);"></i>'
			+'<div class="list-mask mask-div"></div>'
			+'</div>';
		});	
		htmlStr = htmlStr+'<div class="list-item bg-white" onclick="MfSysKindList.addKind(this);">'
		+'<div class="add-item"><i class="i i-jia1 margin_0"></i></div>'
		+'</div>';
		return htmlStr;
		
	};
	
	//新增产品
	var _addKind = function(obj){
		top.mfSysKind = "";
        top.addFlag=false;
		window.parent.openBigForm(webPath+"/mfSysKind/input","新增产品",function(){
			if(top.addFlag){
				var mfSysKind = top.mfSysKind;
				window.location.href = webPath+"/mfSysKind/getKindConfig?kindNo="+mfSysKind.kindNo;

//				if(mfSysKind!=""){
//					var disableStr="";
//					var useFlagStr="禁用";
//					if(mfSysKind.useFlag=='0'){
//						disableStr="disabled";
//						useFlagStr="启用";
//					}
//					var htmlStr ='<div class="list-item bg-white '+disableStr+'" data-kindno="'+mfSysKind.kindNo+'" onclick="MfSysKindList.getkindConfig(this);">'
//					+'<div class="block-header"><i class="i i-chanpin"></i><span class="kind-name">'+mfSysKind.kindName+'</span></div>'
//					+'<div class="block-body" title="'+mfSysKind.remark+'">'+mfSysKind.remark+'</div>'
//					+'<div class="block-footer">'
//					+'<div class="full-div margin_bottom_10" title="'+mfSysKind.brNo+'"><i class="i i-dian"></i>'+mfSysKind.brNo+'</div>'
//					+'<div class="row"><div class="col-md-6 half-div"><i class="i i-dian"></i>'+mfSysKind.vouType+'</div><div class="col-md-6 half-div"><i class="i i-dian"></i>'+mfSysKind.cusType+'</div></div>'
//					+'</div>'
//					+'<span class="kindUseFlag use-link"><a href="MfSysKindActionAjax_updateUseFlagAjax.action?kindNo='+mfSysKind.kindNo+'">'+mfSysKind.useFlag+'</a></span>'
//					+'<i class="i i-x5 del-icon" onclick="MfSysKindList.deleteKind(this);"></i>'
//					+'<div class="list-mask"></div>'
//					+'</div>';
//			
//					$(obj).before(htmlStr);
//					//配置导航下拉框中添加新增的产品
//					$("#box-body").append('<div id="bodyItem'+mfSysKind.kindNo+'" class="body-item" data-kindno="'+mfSysKind.kindNo+'"><i class="i i-chanpin"></i>'+mfSysKind.kindName+'</div>');
//					$(".panel-body").mCustomScrollbar("update");
//				}
			}
		});
	};
	
	//删除产品
	var _deleteKind = function(obj){
		var event = window.event;
		if(event.target.className.indexOf("del-icon") !=-1){
			var kindNo = $(obj).parents(".list-item").data("kindno");
			//先判断该产品是否发生过业务，如果发生过不允许删除、如果没有发生过，可以删除
			$.ajax({
				url:webPath+'/mfBusApply/getMfBusApplyByKindNoAjax?kindNo='+kindNo,
				success:function(data){
					if(data.flag == "success"){
						if(data.hasBiz=="1"){
							alert(top.getMessage("EXIST_PRDCT_USING"),0);
						}else{
							alert(top.getMessage("CONFIRM_DELETE"),2,function(){
								$.ajax({
									url:webPath+'/mfSysKind/deleteAjax?kindNo='+kindNo,
									success:function(data){
										if(data.flag == "success"){
											$(obj).parents(".list-item").remove();
											$("#bodyItem"+kindNo).remove();
											$(".panel-body").mCustomScrollbar("update");
										}else{
											alert(data.msg,0);
										}
									},error:function(){
										alert(top.getMessage("FAILED_DELETE"),0);
									}
								});
							});
						}
					}else{
						alert(top.getMessage("SUCCEED_DELETE"),0);
					}
				},error:function(){
					alert(top.getMessage("FAILED_DELETE"),0);
				}
			});
		}
	
	};
	//更新产品启用禁用标志
	var _updateKindUseFlag = function(obj){
		var mfSysKind={};
		mfSysKind.kindNo = $(obj).parents(".list-item").data("kindno");
		var curUseFlag =  $(obj).data("useflag");
		if(curUseFlag=="0"){
			mfSysKind.useFlag="1";
		}else{
			mfSysKind.useFlag="0";
		}
		$.ajax({
			url:webPath+'/mfSysKind/updateUseFlagAjax',
			data:{ajaxData:JSON.stringify(mfSysKind)},
			success:function(data){
				if(data.flag="success"){
					if(curUseFlag=="0"){//由禁用改启用
						$(obj).data("useflag","1");
						$(obj).text("启用");
						$(obj).parents(".list-item").find(".btn-app").attr("disabled",false);
					}else{//由启用改禁用
						$(obj).data("useflag","0");
						$(obj).text("禁用");
						$(obj).parents(".list-item").find(".btn-app").attr("disabled",true);
					}
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl),0);
			}
		});
	};
	
	//获取产品的配置信息
	var _getkindConfig = function(obj){
		var event = window.event;
		if(event.target.className.indexOf("mask-div") !=-1){
			window.location.href = webPath+'/mfSysKind/getKindConfig?kindNo='+$(obj).data("kindno");
		}
	};
	
	var _toKindConfig = function(){
		window.location.href = webPath+'/mfSysKind/toKindConfig';
	};
	
	return{
		toKindConfig:_toKindConfig,
		init:_init,
		getKindList:_getKindList,
		addKind:_addKind,
		deleteKind:_deleteKind,
		updateKindUseFlag:_updateKindUseFlag,
		getkindConfig:_getkindConfig,
	};
}(window, jQuery);
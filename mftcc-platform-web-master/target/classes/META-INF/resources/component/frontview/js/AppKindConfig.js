;
var AppKindConfig=function(window, $){
	var _init=function(){
//		$.each(mfSysKindList,function(index,obj){
//			_getFeeItemByKindNo(obj.kindNo);
//		});
		initVwSetImg();
	};
	//新增产品
	var _addKind=function(url){
		top.addFrontKindJsonObj={};
		top.addFrontKindFlag=false;//为true保证至少有一个成功
		top.itemId="";
		top.flag=false;
		top.window.openBigForm(path+"/"+url,"选择移动端产品",function(){
			location.reload();
		},"900px","540px");
		//top.createShowDialog(path+"/"+url,'选择移动端产品','56','62',function(){location.reload();});
	};
	//编辑移动端产品详情
	var _editformItem=function(kindNo,kindName){
		top.window.openBigForm(webPath+"/mfFrontKind/getAppKindConfigList?kindNo="+kindNo,kindName+"的表单配置",function(){
		});
//		window.location.href=path+"/MfFrontKindAction_getAppKindConfigList.action?kindNo="+kindNo;
	};
	//新建并进入设计表单页面
	var _formDesignThis = function(kindNo,url){
		$.ajax({
			url:url,
			data:{kindNo:kindNo},
			type:"POST",
			dataType:"json",
			success:function(data){
				if(data.flag == "success"){
					var url = path+'/tech/dragDesginer/openForm.action?formId='+data.formId;
					window.open(url,'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
				}else{
					alert("获取表单文件出错",0);
				}
			},error:function(){
				alert("请求出错",0);
			}
		});
		
	};
	var _resetForm = function(kindNo,url){
		$.ajax({
			url:url,
			data:{kindNo:kindNo},
			dataType:'json',
			type:'POST',
			success:function(data){
				if(data.flag == "success"){
					alert("重置成功",1);
				}else{
					alert("重置失败",0);
				}
			},error:function(){
				alert("重置失败",0);
			}
		});
	};
	var _addDescribeInfo=function(kindNo,kindName){
		top.window.openBigForm(webPath+"/mfFrontKind/getMfFrontKindDescription?kindNo="+kindNo+"&kindName="+kindName,"设置"+kindName+"描述信息",function(){
		});
	};
	
	
	var _updateMobileUseFlag = function(obj,kindno,useflag,kindName){
		if(useflag=="1"){//禁用
			$(obj).removeClass("curChecked");
			useflag = "0";
		}else{//启用
			$(obj).addClass("curChecked");
			useflag = "1";
		}
		var jsonObj ={"kindNo":kindno,"mobileUse":useflag};
		var paramData=JSON.stringify(jsonObj);
		$.ajax({
			url:webPath+"/mfFrontKind/updateUseFlagAjax",
			type:'post',
			data:{ajaxData:paramData},
			async:false,
			beforeSend:function(){  
				LoadingAnimate.start();
			},success:function(data){
				if(data.flag="success"){
					var htmlStr="";
					if(useflag=="0"){
						$(obj).removeAttr("onclick");
						$(obj).attr("onclick",'AppKindConfig.updateMobileUseFlag(this,\''+kindno+'\',\''+useflag+'\',\''+kindName+'\');');
						$(obj).parent(".item-checkbox").find("a").removeClass("config-font");
						$(obj).parent(".item-checkbox").find("a").addClass("link-disabled");
						$(obj).parent(".item-checkbox").find("a").removeAttr("onclick");
						
					}else{
						$(obj).removeAttr("onclick");
						$(obj).attr("onclick",'AppKindConfig.updateMobileUseFlag(this,\''+kindno+'\',\''+useflag+'\',\''+kindName+'\');');
						$(obj).parent(".item-checkbox").find("a").removeClass("link-disabled");
						$(obj).parent(".item-checkbox").find("a").addClass("config-font");
						$(obj).parent(".item-checkbox").find("a").attr("onclick",'AppKindConfig.editformItem(\''+kindno+'\',\''+kindName+'\');');
					}
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			},complete: function(){
				LoadingAnimate.stop();
			}
		});
	};
	var _updatePcUseFlag = function(obj,kindno,useflag,kindName){
		if(useflag=="1"){//禁用
			$(obj).removeClass("curChecked");
			useflag = "0";
		}else{//启用
			$(obj).addClass("curChecked");
			useflag = "1";
		}
		var jsonObj ={"kindNo":kindno,"pcUse":useflag};
		var paramData=JSON.stringify(jsonObj);
		$.ajax({
			url:webPath+"/mfFrontKind/updateUseFlagAjax",
			type:'post',
			data:{ajaxData:paramData},
			async:false,
			beforeSend:function(){  
				LoadingAnimate.start();
			},success:function(data){
				if(data.flag="success"){
					var htmlStr="";
					if(useflag=="0"){
						$(obj).removeAttr("onclick");
						$(obj).attr("onclick",'AppKindConfig.updatePcUseFlag(this,\''+kindno+'\',\''+useflag+'\',\''+kindName+'\');');
						$(obj).parent(".item-checkbox").find("a").removeClass("config-font");
						$(obj).parent(".item-checkbox").find("a").addClass("link-disabled");
						$(obj).parent(".item-checkbox").find("a").removeAttr("onclick");
						
					}else{
						$(obj).removeAttr("onclick");
						$(obj).attr("onclick",'AppKindConfig.updatePcUseFlag(this,\''+kindno+'\',\''+useflag+'\',\''+kindName+'\');');
						$(obj).parent(".item-checkbox").find("a").removeClass("link-disabled");
						$(obj).parent(".item-checkbox").find("a").addClass("config-font");
						$(obj).parent(".item-checkbox").find("a").attr("onclick","AppKindConfig.formDesignThis(\'"+kindno+"\',"+webPath+"/mfFrontKind/pcKindDesignAjax);");
//						$(obj).parent(".item-checkbox").find("a.clear-form").attr("onclick",'AppKindConfig.resetForm(\''+kindno+'\',\webPath+'/mfFrontKind/deleteFormIdAjax\');');
					}
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			},complete: function(){
				LoadingAnimate.stop();
			}
		});
	};
	var _windowJump=function(url){
		window.location.href=webPath+"/"+url;
	}
	var _openFeatureForm=function(dom,no){
		var id=$(dom).attr("data");
		top.createShowDialog(webPath+"/vwFeatureManage/input?id="+id,"平台特点"+no+"--配置",'90','90');
	}
	var _getFormHtml=function(){
		//获取网站基础设置表单
		$.ajax({
			url:webPath+'/vwSetManage/getSetFormHtml',
			type:'post',
			dataType:'json',
			success:function(data){
				var htmlStr=data.data;//组装的form
				$("#vwSetHtml").html(htmlStr);
				dblclickflag();//单子段编辑事件
				$(".content .fieldshow").css("max-width","20em");
			}
		})
	};
	var _getMobileSettingHtml=function(){
		//获取移动端客服设置表单
		$.ajax({
			url:webPath+'/mfFrontAppSetting/getMobileFormHtmlAjax',
			type:'post',
			dataType:'json',
			success:function(data){
				var htmlStr=data.data;//组装的form
				$("#mobileSettingHtml").html(htmlStr);
				dblclickflag();//单子段编辑事件
				$(".content .fieldshow").css("max-width","20em");
			}
		})
	};
	var _getMobileShareHtml=function(){
		//获取移动端客服设置表单
		$.ajax({
			url:webPath+'/mfFrontAppSetting/getMobileShareHtmlAjax',
			type:'post',
			dataType:'json',
			success:function(data){
				var htmlStr=data.data;//组装的form
				$("#mobileShareHtml").html(htmlStr);
				dblclickflag();//单子段编辑事件
				$(".content .fieldshow").css("max-width","20em");
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		})
	};
	var _updatePutoutCountDay=function(obj,kindNo){
		//当天次数
		var putoutCountDay=$(obj).val();
		jsonObj ={"kindNo":kindNo,"putoutCountDay":putoutCountDay};
		var paramData=JSON.stringify(jsonObj);
		$.ajax({
			url:webPath+'/mfFrontKind/updatePutoutCountDayAjax',
			type:'post',
			data:{ajaxData:paramData},
			async:false,
			dataType:'json',
			success:function(data){
				if(data.flag="success"){
					$(obj).val(putoutCountDay);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		})
	};
	var _updatePutoutAmtDay=function(obj,kindNo){
		//当天放款金额
		var putoutAmtDay=$(obj).val();
		jsonObj ={"kindNo":kindNo,"putoutAmtDay":putoutAmtDay};
		var paramData=JSON.stringify(jsonObj);
		$.ajax({
			url:webPath+'/mfFrontKind/updatePutoutAmtDayAjax',
			type:'post',
			data:{ajaxData:paramData},
			async:false,
			dataType:'json',
			success:function(data){
				if(data.flag="success"){
					$(obj).val(putoutAmtDay);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		})
	};
	var _updatePutoutCountMonth=function(obj,kindNo){
		//当月次数
		var putoutCountMonth=$(obj).val();
		jsonObj ={"kindNo":kindNo,"putoutCountMonth":putoutCountMonth};
		var paramData=JSON.stringify(jsonObj);
		$.ajax({
			url:webPath+'/mfFrontKind/updatePutoutCountMonthAjax',
			type:'post',
			data:{ajaxData:paramData},
			async:false,
			dataType:'json',
			success:function(data){
				if(data.flag="success"){
					$(obj).val(putoutCountMonth);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		})
	};
	var _updatePutoutAmtMonth=function(obj,kindNo){
		//当月放款金额
		var putoutAmtMonth=$(obj).val();
		jsonObj ={"kindNo":kindNo,"putoutAmtMonth":putoutAmtMonth};
		var paramData=JSON.stringify(jsonObj);
		$.ajax({
			url:webPath+'/mfFrontKind/updatePutoutAmtMonthAjax',
			type:'post',
			data:{ajaxData:paramData},
			async:false,
			dataType:'json',
			success:function(data){
				if(data.flag="success"){
					$(obj).val(putoutAmtMonth);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		})
	};
	return{
		addKind:_addKind,
		formDesignThis:_formDesignThis,
		resetForm:_resetForm,
		addDescribeInfo:_addDescribeInfo,
		updateMobileUseFlag:_updateMobileUseFlag,
		updatePcUseFlag:_updatePcUseFlag,
		editformItem:_editformItem,
		windowJump:_windowJump,
		openFeatureForm:_openFeatureForm,
		getFormHtml:_getFormHtml,
		getMobileSettingHtml:_getMobileSettingHtml,
		getMobileShareHtml:_getMobileShareHtml,
		init:_init,
		updatePutoutCountDay:_updatePutoutCountDay,
		updatePutoutAmtDay:_updatePutoutAmtDay,
		updatePutoutCountMonth:_updatePutoutCountMonth,
		updatePutoutAmtMonth:_updatePutoutAmtMonth
	};
}(window, jQuery);
function initVwSetImg(){
	var wxFileName="";
	var qqFileName="";
	var logoFileName="";
	$.ajax({
		url:webPath+"/vwSetManage/getByIdAjax",
		dataType:"json",
		async: false,
		type:"POST",
		success:function(data){
			if(data && data.data){
				wxFileName=data.data.wechatCodeImg;
				qqFileName=data.data.qqGroupCodeImg;
				logoFileName=data.data.logoImg;
			}
		}
	});
	var srcWx=getImg(wxFileName);
	if(srcWx==""){//还未上传图片
		
	}else{
		$("#wxLi").find("img").attr("src",srcWx);
		$("#wxLi").find(".disShow").removeClass("disShow");
		$("#wxLi").find("form").addClass("disShow");
	}
	var srcQQ=getImg(qqFileName);
	if(srcQQ==""){//还未上传图片
		
	}else{
		$("#qqLi").find("img").attr("src",srcQQ);
		$("#qqLi").find(".disShow").removeClass("disShow");
		$("#qqLi").find("form").addClass("disShow");
	}
	var srcLogo=getImg(logoFileName);
	if(srcLogo==""){//还未上传图片
		
	}else{
		$("#logoLi").find("img").attr("src",srcLogo);
		$("#logoLi").find(".disShow").removeClass("disShow");
		$("#logoLi").find("form").addClass("disShow");
	}
	
}
function uploadDiv(dom){
	var vl=$(dom).parents("li").find("[name=flag]").val();
	if(vl=='logo'){
		window.top.alert("推荐图片高为52px,最大宽度300px,否则可能影响显示效果",3,function(){
			$(dom).parents("li").find("[name=upload]").click();
		}); 
	}else{
		$(dom).parents("li").find("[name=upload]").click();
	}
}

$(document).delegate("[name=upload]","change",function(e){
	$form=$(e.target).parents("form");
	var flag=$form.find("[name=flag]").val();
	var filePath=e.target.value;
	if(null!=filePath&&filePath!=''){
		var arr=filePath.split(".");
		$form.find("[name=fileType]").val(arr[1]);
		$form.ajaxSubmit({
			url:webPath+"/vwSetManage/uploadImg",
			success:function(data){
				if(data.data){
					var src=getImg(data.data);//fileName
					$(e.target).parents("li").find("img").attr("src",src);
					$(e.target).parents("li").find(".disShow").removeClass("disShow");
					$form.addClass("disShow");	
				}else{
					window.top.alert(top.getMessage("ERROR_UPLOAD_FILE_TYPE","图片类型"),0);
				}
			}
		})
	}else{
		$form.find("[name=fileType]").val("");
	}
})
function showImg(dom){
	var src=$(dom)[0].src;
	var flag=$(dom).parents("li").find("[name=flag]").val();
	$("#template").attr("src",src);
	if("wx"==flag){
		$("#template").attr("alt","微信二维码");
	}else if("qq"==flag){
		$("#template").attr("alt","QQ群二维码");
	}else if("logo"==flag){
		$("#template").attr("alt","网站logo");
	}
	$("#template").click();
}
function getImg(fileName){//初始化使用
	var src="";
	if(fileName==""||fileName==null){//还未上传图片
	}else{//已上传图片
		//src=webPath+"/vwBannerManage/viewImage?uploadFileName="+fileName+"&rundom="+Math.random();//返回流
		src=factorWebUrl+"/vwImage/viewImage?filePath="+fileName+"&rundom="+Math.random();//返回流
	}
	return src;
}
function deleteDiv(dom){
	var src=$(dom).parents("li").find("img").attr("src");
	var arr=src.split("?");
	var parm=arr[1];
	$.ajax({
		url:webPath+'/vwSetManage/deleteImage?'+parm,
		success:function(){
			$li=$(dom).parents("li");
			$li.find("img").attr("src","");
			$li.find("form").removeClass()
			$li.find(".disShow").removeClass("disShow");
			$li.find(".has-img").addClass("disShow");
			$li.find(".title-name").addClass("disShow");
			$li.find(".btn-primary").addClass("disShow");
			updateVwset(dom);
		}
	})
}
function updateVwset(dom){
	var flag=$(dom).parents("li").find("[name=flag]").val();
	var ajaxData="";
	var arr=new Array();
	var o=new Object();
	if(flag=="wx"){
		o.name="wechatCodeImg";
		o.value="";
	}else if(flag=="qq"){
		o.name="qqGroupCodeImg";
		o.value="";
	}else if(flag=="logo"){
		o.name="logoImg";
		o.value="";
	}
	arr[0]=o;
	$.ajax({
		url:webPath+"/vwSetManage/updateAjax",
		async: false,
		data : {
			ajaxData : JSON.stringify(arr)
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
		}
	})
}
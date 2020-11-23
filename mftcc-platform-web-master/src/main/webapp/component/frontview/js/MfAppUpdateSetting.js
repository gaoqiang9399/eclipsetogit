;
var MfAppUpdateConfig=function(window, $){
	var _init=function(){
		getAppSettingHtml();	
		inputRadioBindEvent();//input-radio事件
		_getAppProdItemList();//产品设置
	};
	var _openImg=function(obj){
		var src="";
		if(obj==1){
			src=$("input[name='redirectUrl']:checked").attr("data");
		}else if(obj==2){
			src=$("input[name='creditmodel']:checked").attr("data");
		}else if(obj==3){
			src=$("input[name='showProdDeail']:checked").attr("data");
		}else if(obj==4){
			src=$("input[name='repayModel']:checked").attr("data");
		}
		top.createShowDialog(webPath+src,"示例图片",'35','90');
	};
	var _getAppProdItemList=function(){
		$.ajax({
			url:webPath+'/mfAppProdItem/getAppProdItemListAjax',
			type:'post',
			data:'',
			async:false,
			dataType:'json',
			success:function(data){
				if(data.flag="success"){
					var mfAppProdItemList=data.mfAppProdItemList;
					getAppProdItemListHtml(mfAppProdItemList);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	var getAppProdItemListHtml=function(mfAppProdItemList){
		var htmlStr = "";
		$.each(mfAppProdItemList, function(i, mfAppProdItem) {
			htmlStr+='<div class="item-title margin_bottom_10">';
			htmlStr+='<span class="color_black">'+mfAppProdItem.kindName+'</span>';
			htmlStr+='<span class="color_black">认证信息</span>';
			htmlStr+='<a class="config-font" href="'+path+'/MfAppProdItemAction_getListPage.action?kindNo='+mfAppProdItem.kindNo+'">';
			htmlStr+='配置';
			htmlStr+='</a>';
			htmlStr+='<a class="config-font" href="javascript:void(0);" onclick="MfAppUpdateConfig.cancelProductItem('+mfAppProdItem.kindNo+')">删除</a>'
			htmlStr+='</div>';
		});
		$(".productItemList").append(htmlStr);
	};
	var _cancelProductItem=function(kindNo){
		$.ajax({
			url:webPath+'/mfAppProdItem/deleteByKindNoAjax',
			type:'post',
			data:{"kindNo":kindNo},
			async:false,
			dataType:'json',
			success:function(data){
				if(data.flag="success"){
					location.reload();
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	var _publishVersion=function(){
		var appVersion=$("#appVersion").val();
		$.ajax({
			url:webPath+'/mfAppUpdateSetting/createJsonFileAjax',
			type:'post',
			data:{"appVersion":appVersion},
			async:false,
			dataType:'json',
			success:function(data){
				if(data.flag="success"){
					//top.alert(data.msg,1);
					alert(top.getMessage("SUCCEED_OPERATION","发布（"+data.version+"）版本更新包"),2);
				}else{
					alert(top.getMessage("ERROR_OPERATION",data.msg),2);
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	
	//app模式配置
	var getAppSettingHtml = function(){
		var htmlStr = "";
		$.each(mfAppSetingList, function(i, mfAppSeting) {
			if(mfAppSeting.modelType==0){
				var	modelHtml=getloginModelHtml(mfAppSeting);
				htmlStr+=modelHtml;
			}else if(mfAppSeting.modelType==1){
				var redirectUrl=getredirectUrlHtml(mfAppSeting);
				htmlStr+=redirectUrl;
			}else if(mfAppSeting.modelType==2){
				var creditmodel=getcreditmodelHtml(mfAppSeting);
				htmlStr+=creditmodel;
			}else if(mfAppSeting.modelType==3){
				var showProdDeaill=getshowProdDeaillHtml(mfAppSeting);
				htmlStr+=showProdDeaill;
			}else if(mfAppSeting.modelType==4){
				var widthdrawModel=getwidthdrawModelHtml(mfAppSeting);
				htmlStr+=widthdrawModel;
			}else if(mfAppSeting.modelType==5){
				var repayModel=getrepayModelHtml(mfAppSeting);
				htmlStr+=repayModel;
			}
		});
		$(".appconfig").append(htmlStr);
	};
	//登录模式html
	var getloginModelHtml=function(mfAppSeting){
		var checkStr1 = "",checkStr2 = "",checkStr3 = "",checkStr4 = "";
		if(mfAppSeting.modelValue=="0"){
			checkStr1='checked="checked"';
		}else if(mfAppSeting.modelValue=="1"){
			checkStr2='checked="checked"';
		}else if(mfAppSeting.modelValue=="2"){
			checkStr3='checked="checked"';
		}else if(mfAppSeting.modelValue=="3"){
			checkStr4='checked="checked"';
		}
		
		var htmlStr = "";
		htmlStr+='<div class="p-title margintop10 marginbottom10"><span class="content_title">登录模式</span></div>';
		htmlStr+='<div class="p-content">';
		htmlStr+='<div class="item-div marginleft20 loginModel" data-id="'+mfAppSeting.id+'">';
		htmlStr+='<div class="item-content  margin_bottom_10"'+checkStr1+'>';
		htmlStr+='<span><input type="radio"  class="margin_right_5" name="loginModel" value="0" '+checkStr1+'/>';
		htmlStr+='手机验证码登录</span>';
		htmlStr+='</div>';
		htmlStr+='</div>';
		htmlStr+='<div class="item-div marginleft20 loginModel"  data-id="'+mfAppSeting.id+'">';
		htmlStr+='<div class="item-content  margin_bottom_10">';
		htmlStr+='<span><input type="radio"  class="margin_right_5" name="loginModel" value="1"  '+checkStr2+'/>';
		htmlStr+='密码登录</span>';
		htmlStr+='</div>';
		htmlStr+='</div>';
		htmlStr+='<div class="item-div marginleft20 loginModel" data-id="'+mfAppSeting.id+'">';
		htmlStr+='<div class="item-content  margin_bottom_10" >';
		htmlStr+='<span><input type="radio"  class="margin_right_5" name="loginModel" value="2" '+checkStr3+'/>';
		htmlStr+='短信验证码及微信qq登录</span>';
		htmlStr+='</div>';
		htmlStr+='</div>';
		htmlStr+='<div class="item-div marginleft20 loginModel"  data-id="'+mfAppSeting.id+'">';
		htmlStr+='<div class="item-content  margin_bottom_10">';
		htmlStr+='<span><input type="radio"  class="margin_right_5" name="loginModel" value="3" '+checkStr4+'/>';
		htmlStr+='密码及微信qq登录</span>';
		htmlStr+='</div>';
		htmlStr+='</div>';
		htmlStr+='</div>';
		return htmlStr;
		//$("#loginModel").append(htmlStr);
	};
	//登录后跳转页面html
	var getredirectUrlHtml=function(mfAppSeting){
		var checkStr1 = "",checkStr2 = "",checkStr3 = "";
		if(mfAppSeting.modelValue=="0"){
			checkStr1='checked="checked"';
		}else if(mfAppSeting.modelValue=="1"){
			checkStr2='checked="checked"';
		}else if(mfAppSeting.modelValue=="2"){
			checkStr3='checked="checked"';
		}
		var htmlStr = "";
		htmlStr+='<div class="p-title margintop10 marginbottom10">';
			htmlStr+='<span class="content_title">登录后跳转页面</span>';
			htmlStr+='<a class="config-font" href="javascript:void(0);" data="" onclick="MfAppUpdateConfig.openImg(\'1\')">';
			htmlStr+='预览';
			htmlStr+='</a>';
			htmlStr+='</div>';
			htmlStr+='<div class="p-content">';
			htmlStr+='<div class="item-div marginleft20" data-id="'+mfAppSeting.id+'">';
			htmlStr+='<div class="item-content margin_bottom_20"><span>';
			htmlStr+='<input class="margin_right_5" type="radio" name="redirectUrl" value="0" '+checkStr1+' data="/component/frontview/img/home.jpg">始终显示首页';
			htmlStr+='<input class="margin_right_5 margin_left_10" type="radio" name="redirectUrl" '+checkStr2+' value="1" data="/component/frontview/img/withdraw.jpg">授信完成后显示提现页面，提现完成后仍然显示首页'; 
			htmlStr+='<input class="margin_right_5 margin_left_10" type="radio" name="redirectUrl" '+checkStr3+' value="2">显示活动页面 ';
			htmlStr+='</span></div>';
			htmlStr+='</div>';
			htmlStr+='</div>';
			htmlStr+='</div>';
			return htmlStr;
		//$("#redirectUrl").append(htmlStr);
	};
	//授信模式html
	var getcreditmodelHtml=function(mfAppSeting){
		var checkStr1 = "",checkStr2 = "";
		if(mfAppSeting.modelValue=="0"){
			checkStr1='checked="checked"';
		}else if(mfAppSeting.modelValue=="1"){
			checkStr2='checked="checked"';
		}
		var htmlStr = "";
		htmlStr+='<div class="p-title margintop10 marginbottom10">';
			htmlStr+='<span class="content_title">授信模式</span>';
			htmlStr+='<a class="config-font" href="javascript:void(0);" data="" onclick="MfAppUpdateConfig.openImg(\'2\')">';
			htmlStr+='预览';
			htmlStr+='</a>';
			htmlStr+='</div>';
			htmlStr+='<div class="p-content">';
			htmlStr+='<div class="item-div marginleft20" data-id="'+mfAppSeting.id+'">';
			htmlStr+='<div class="item-content margin_bottom_20"><span>';
			htmlStr+='<input class="margin_right_5" type="radio" name="creditmodel" value="0" '+checkStr1+' data="/component/frontview/img/productDetail.jpg">不允许客户调整授信金额和期限';  
			htmlStr+='<input class="margin_right_5 margin_left_10" type="radio" name="creditmodel" value="1" '+checkStr2+' data="/component/frontview/img/productDetail.jpg">允许客户调整客户金额和期限  ';
			htmlStr+='</span></div>';
			htmlStr+='</div>';
			htmlStr+='</div>';
			htmlStr+='</div>';
			return htmlStr;
		//$("#creditmodel").append(htmlStr);
	};
	//产品详情页html
	var getshowProdDeaillHtml=function(mfAppSeting){
		var checkStr1 = "",checkStr2 = "";
		if(mfAppSeting.modelValue=="0"){
			checkStr1='checked="checked"';
		}else if(mfAppSeting.modelValue=="1"){
			checkStr2='checked="checked"';
		}
		var htmlStr = "";
		htmlStr+='<div class="p-title margintop10 marginbottom10">';
			htmlStr+='<span class="content_title">产品详情页</span>';
			htmlStr+='<a class="config-font" href="javascript:void(0);" data="" onclick="MfAppUpdateConfig.openImg(\'3\')">';
			htmlStr+='预览';
			htmlStr+='</a>';
			htmlStr+='</div>';
			htmlStr+='<div class="p-content">';
			htmlStr+='<div class="item-div marginleft20" data-id="'+mfAppSeting.id+'">';
			htmlStr+='<div class="item-content margin_bottom_20"><span>';
			htmlStr+='<input class="margin_right_5" type="radio" name="showProdDeail" value="0" '+checkStr1+' data="/component/frontview/img/productDetail.jpg">不显示详情';  
			htmlStr+='<input class="margin_right_5 margin_left_10" type="radio" name="showProdDeail" value="1" '+checkStr2+' data="/component/frontview/img/productDetail.jpg">显示详情';
			htmlStr+='</span></div>';
			htmlStr+='</div>';
			htmlStr+='</div>';
			htmlStr+='</div>';
			return htmlStr;
		//$("#showProdDeail").append(htmlStr);
	};
	//提现模式html
	var getwidthdrawModelHtml=function(mfAppSeting){
		var checkStr1 = "",checkStr2 = "";
		if(mfAppSeting.modelValue=="0"){
			checkStr1='checked="checked"';
		}else if(mfAppSeting.modelValue=="1"){
			checkStr2='checked="checked"';
		}
		var htmlStr = "";
		htmlStr+='<div class="p-title margintop10 marginbottom10">';
			htmlStr+='<span class="content_title">提现模式</span>';
			htmlStr+='</div>';
			htmlStr+='<div class="p-content">';
			htmlStr+='<div class="item-div marginleft20" data-id="'+mfAppSeting.id+'">';
			htmlStr+='<div class="item-content margin_bottom_20"><span>';
			htmlStr+='<input class="margin_right_5" type="radio" name="widthdrawModel" value="0" '+checkStr1+'>一次性提现';  
			htmlStr+='<input class="margin_right_5 margin_left_10" type="radio" name="widthdrawModel" value="1" '+checkStr2+'>多次提现';
			htmlStr+='</span></div>';
			htmlStr+='</div>';
			htmlStr+='</div>';
			htmlStr+='</div>';
			return htmlStr;
		//$("#widthdrawModel").append(htmlStr);
	};
	//还款模式html
	var getrepayModelHtml=function(mfAppSeting){
		var checkStr1 = "",checkStr2 = "";
		if(mfAppSeting.modelValue=="0"){
			checkStr1='checked="checked"';
		}else if(mfAppSeting.modelValue=="1"){
			checkStr2='checked="checked"';
		}
		var htmlStr = "";
		htmlStr+='<div class="p-title margintop10 marginbottom10">';
			htmlStr+='<span class="content_title">还款模式</span>';
			htmlStr+='<a class="config-font" href="javascript:void(0);" data="" onclick="MfAppUpdateConfig.openImg(\'4\')">';
			htmlStr+='预览';
			htmlStr+='</a>';
			htmlStr+='</div>';
			htmlStr+='<div class="p-content">';
			htmlStr+='<div class="item-div marginleft20" data-id="'+mfAppSeting.id+'">';
			htmlStr+='<div class="item-content margin_bottom_20"><span>';
			htmlStr+='<input class="margin_right_5" type="radio" name="repayModel" value="0" '+checkStr1+' data="/component/frontview/img/zhifubao.jpg">不支持终端还款';  
			htmlStr+='<input class="margin_right_5 margin_left_10" type="radio" name="repayModel" value="1" '+checkStr2+' data="/component/frontview/img/repayAmt.png">支持终端还款';
			htmlStr+='</span></div>';
			htmlStr+='</div>';
			htmlStr+='</div>';
			htmlStr+='</div>';
			return htmlStr;
		//$("#repayModel").append(htmlStr);
	};
	//input-radio绑定事件
	var inputRadioBindEvent = function(){
		$(".item-div input[type=radio]").bind("click",function(){
			var mfAppSeting={};
			mfAppSeting.id=$(this).parents(".item-div").data("id");
			mfAppSeting.modelValue=$(this).val();
			var ajaxData = JSON.stringify(mfAppSeting);
			updateAppSetting(ajaxData);
		});
	};
	
	//更新app配置方法
	var updateAppSetting = function(ajaxData){
		$.ajax({
			url:webPath+"/mfAppSeting/updateAppSettingAjax",
			type:'post',
			data:{ajaxData:ajaxData},
			async:false,
			beforeSend:function(){  
				LoadingAnimate.start();
			},success:function(data){
				if(data.flag="success"){
					
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			},complete: function(){
				LoadingAnimate.stop();
			}
		});
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
	};
	return{
		init:_init,
		openImg:_openImg,
		publishVersion:_publishVersion,
		addKind:_addKind,
		cancelProductItem:_cancelProductItem
	};
}(window, jQuery);

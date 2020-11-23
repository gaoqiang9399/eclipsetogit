;
var MfAppBusApply = function(){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".list-item",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	/**
	 * 获取jsapi的配置信息
	 */
	var _getDDReady = function(){
		var _config;
		$.ajax({
			url: webPath+"/dingInterface/getDingJSConfigAjax?htmlUrl=" + encodeURIComponent(location.href.split('#')[0]),
			type: "get",
			dataType: 'json',
			async: false,
			success: function(data) {
				_config = data.data;
			},
			error: function(returnJSON) {
				$.alert("连接失败，请刷新页面重试");
			}
		})
		dd.config({
			corpId: _config.corpId,
			timeStamp: _config.timeStamp,
			nonceStr: _config.nonceStr,
			signature: _config.signature,
			agentId: _config.agentid,
			jsApiList: ['runtime.info', 'biz.user.get', 'runtime.permission.requestAuthCode', 'biz.contact.choose',
				 'biz.navigation.setRight','device.notification.alert',
				'device.notification.prompt', 'biz.ding.post',
				'biz.telephone.showCallMenu','biz.util.openLink'
			]
		});
		dd.error(function(error){
			  alert('dd error: ' + JSON.stringify(error));
		});
		return _config;
	}
	/**
	 * 获取客户id然后获取当前人员信息
	 */
	var _getAuth = function() {
		dd.runtime.permission.requestAuthCode({
			corpId: _config.corpId,
			onSuccess: function(result) {
				$.ajax({
					url: webPath+"/dingInterface/getUserInfoAjax?code=" + result.code,
					type: "get",
					dataType: 'json',
					async: false,
					success: function(data) {
						//获得操作员信息，操作员实体的json格式
						/*var opNo;
						var obj = JSON.parse(data.data);
						opNo=obj.opNo
						var opInfo = data.data;
						*//*setSessionAjax(opInfo);*/
						if(typeof(opInfo)!="String"){
							
						}else{
							/*如果不是操作员做处理 $("#userInfo").text("确认你是操作员，如果是请确认操作员手机号"); */
						}
					},
					error: function(returnJSON) {
						alert("连接失败，请刷新页面重试");
					}
				})
			},
			onFail: function(err) {

			}
		})
	};
	/**
	 * 设置右侧按钮
	 */
	var _setRight=function(){
		dd.biz.navigation.setRight({
		    show: true,//控制按钮显示， true 显示， false 隐藏， 默认true
		    control: true,//是否控制点击事件，true 控制，false 不控制， 默认false
		    text: '进件',//控制显示文本，空字符串表示显示默认文本
		    onSuccess : function(result) {
		       window.location.href=webPath+'/mfAppCusCustomer/input'
		    },
		    onFail : function(err) {}
		});	
	}
	/**
	 * 表单验证:适合所有字段均为必填，并且没有空的隐藏域
	 */
	var _saveValidat=function(){
  			var flag=true;
  			var inputs = $("input[mustinput='1']");
			 inputs.each(function(){
			 	var val = $(this).val();
			    if(val==""||typeof(val)=="undefined"){
			    	flag =false;
			    	var msg ;
			    	msg = $(this).parent().siblings().children('label').text();
			    	
			    	/*dd.device.notification.alert({
			    	    message: "请填写"+msg,
			    	    buttonName: "确定",
			    	    onSuccess : function() {
			    	        //onSuccess将在点击button之后回调
			    	    },
			    	    onFail : function(err) {}
			    	});*/
			    	
			    	$.alert("请填写"+msg);
			    	return false;
			    }
			 });
			 return flag;
	}
	var _dingPhone =function (phoneNumber){
		dd.biz.telephone.showCallMenu({
		    phoneNumber: phoneNumber, // 期望拨打的电话号码
		    code: '+86', // 国家代号，中国是+86
		    showDingCall: true, // 是否显示钉钉电话
		    onSuccess : function() {},
		    onFail : function() {}
		})
	};
	var _approvalBack = function (){
		document.addEventListener('backbutton', function(e) {
              // 在这里处理你的业务逻辑
				alert($('#appDiv').is(':hidden'))
			  	 if($('#appDiv').is(':hidden')){
				  	 toList();
				 }else{
					 backDiv();
				 }	
              e.preventDefault(); //backbutton事件的默认行为是回退历史记录，如果你想阻止默认的回退行为，那么可以通过preventDefault()实现
  			 });
			//ios处理
//			dd.biz.navigation.setLeft({
//			    show: true,//控制按钮显示， true 显示， false 隐藏， 默认true
//			    control: true,//是否控制点击事件，true 控制，false 不控制， 默认false
//			    showIcon: false,//是否显示icon，true 显示， false 不显示，默认true； 注：具体UI以客户端为准
//			    text: '',//控制显示文本，空字符串表示显示默认文本
//			    onSuccess : function(result) {
//				     if($('#appDiv').is(':hidden')){
//					  	 toList();
//					 }else{
//						 backDiv();
//					 }	
//			    },
//			    onFail : function(err) {}
//			});
	};
	return {
		init:_init,
		getDDReady:_getDDReady,
		getAuth:_getAuth,
		setRight:_setRight,
		saveValidat:_saveValidat,
		approvalBack:_approvalBack,
		dingPhone:_dingPhone
	};
}(window,jQuery);


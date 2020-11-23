;
var MfOaInternalCommunication_Detail=function(window, $){
    var textAareas="messageContent";
    var _init=function(){
        setUEeditor(textAareas,320);
        myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		$(".save").hide();
		//回复的消息详情，不展示保存和回复按钮
		if(detailFlag=="onlyDetail"){
			$(".save").hide();
			$(".recovery").hide();
		}
	};
	//消息回复
	var _messageRecovery=function(){
        $.ajax({
			url : webPath+"/mfOaInternalCommunication/getRecoveryFromHtmlAjax",
			data : {
				messageId:messageId,
				messageType:"2",
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
                LoadingAnimate.stop();
				if (data.flag == "success") {
					$("#MfOaInternalCommunicationForm").html(data.htmlStr);
					$(".save").show();
					$(".recovery").hide();
                    recoveryMessageId=data.recoveryMessageId;
					top.messageId=recoveryMessageId;
					aloneFlag = true;
					dataDocParm={
						relNo:recoveryMessageId,
						docType:"messageDoc",
						docTypeName:"附件资料",
						docSplitName:"附件资料",
						query:''
					};
					initDocNodes();
                    setUEeditor(textAareas,320);
                } else {
					window.top.alert(data.msg, 0);
				}
			},
			error : function() {
				LoadingAnimate.stop();
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	//发消息
	var _messageSaveAjax=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			//var dataParam = JSON.stringify($(obj).serializeArray());
            var dataParam = UEeditorEncodeURI(JSON.stringify($(obj).serializeArray()),textAareas)
            LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
					recoveryMessageId:recoveryMessageId
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						window.top.alert(data.msg, 1);
						 top.addFlag = true;
						 myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function() {
					LoadingAnimate.stop();
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
		}	
	};
	/**
	 * 选择收信人回显到页面上
	 */
	var _getAcceptInfoDialog = function(acceptInfo){
		$("input[name=messageAcceptOpNo]").val(acceptInfo.brNo);
		$("input[name=messageAcceptOpName]").val(acceptInfo.brName);
	};
	return{
		init:_init,
		messageSaveAjax:_messageSaveAjax,
		getAcceptInfoDialog:_getAcceptInfoDialog,
		messageRecovery:_messageRecovery
	};
}(window, jQuery);
;
var MfOaInternalCommunication_SendList=function(window, $){
	var _init=function(){
		top.LoadingAnimate.start();
		 myCustomScrollbar({
			obj : "#content", //页面内容绑定的id
			url : webPath+"/mfOaInternalCommunication/findByPageAjax", //列表数据查询的url
			tableId : "tablecommunicationlist", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:30,//加载默认行数(不填为系统默认行数) 
	        ownHeight : true,
	        data:{
	        	messageType:"1",
	        },
		    callback:function(){
		    	top.LoadingAnimate.stop();
			}//方法执行完回调函数（取完数据做处理的时候）
	    });
	};
	//发消息
	var _messageInsert=function(){
		top.addFlag = false;
		top.messageId = "";
		top.openBigForm(webPath+"/mfOaInternalCommunication/input?messageType=1", "发消息", function() {
			if (top.addFlag) {
				window.location.reload();
			}else{
				_deleteDocByNoSave();
			}
		});
	};
	//跳转收件箱列表
	var _toMessageAcceptInfoList=function(){
		location.href = webPath+"/mfOaInternalCommunication/getAcceptInfoListPage";
	};
	//跳转发件箱列表
	var _toMessageSendInfoList=function(){
		location.href = webPath+"/mfOaInternalCommunication/getSendInfoListPage";
	};
	//消息回复详情，只查看
	var _messageRecoveryDetail=function(obj,url){
		top.openBigForm(url+"&detailFlag=onlyDetail", "消息回复详情", function() {});
	};
	//发消息时上传要件，未发送删除上传的要件
	var _deleteDocByNoSave=function(){
		$.ajax({
			url : webPath+"/docManage/deleteByBizNoAjax",
			data : {
				docBizNo:top.messageId
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop();
			},
			error : function() {
				LoadingAnimate.stop();
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	return{
		init:_init,
		messageInsert:_messageInsert,
		toMessageAcceptInfoList:_toMessageAcceptInfoList,
		toMessageSendInfoList:_toMessageSendInfoList,
		messageRecoveryDetail:_messageRecoveryDetail
	};
}(window, jQuery);
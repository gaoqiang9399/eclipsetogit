;
var MfOaInternalCommunication_Insert=function(window, $){
    var textAareas="messageContent";
    var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
          setUEeditor(textAareas,320);
        top.messageId=messageId;
		$("[name=messageAcceptOpNo]").popupSelection({
			ajaxUrl : webPath+"/sysUser/getSysUserListAjax?opNoType=1",
			searchOn : true,//启用搜索
			multiple : true,//单选
			ztree : true,
			ztreeSetting : setting,
			title : "收件人",
			handle : BASE.getIconInTd($("input[name=messageAcceptOpNo]")),
			changeCallback : function (elem) {
				BASE.removePlaceholder($("input[name=messageAcceptOpNo]"));
				var AcceptOpNo=elem.data("values").val();
				var nodes = elem.data("treeNode");
				var AcceptOpName="";
				var len = elem.data("treeNode").length;
				for(var i=0;i<len;i++){
					AcceptOpName+=nodes[i].name+"|";
				}
				$("input[name=messageAcceptOpName]").val(AcceptOpName);
			}
		});


	};
	//发消息
	var _messageSaveAjax=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
            var dataParam = UEeditorEncodeURI(JSON.stringify($(obj).serializeArray()),textAareas)
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
					messageId:messageId
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
		getAcceptInfoDialog:_getAcceptInfoDialog
	};
}(window, jQuery);
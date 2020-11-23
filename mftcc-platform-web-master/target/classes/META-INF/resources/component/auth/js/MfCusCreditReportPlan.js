;
var MfCusCreditReportPlan = function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		$("[name=reportOpNo]").popupSelection({
			ajaxUrl : webPath+"/sysUser/getSysUserListAjax?opNoType=1",
			searchOn : true,//启用搜索
			multiple : true,//单选
			ztree : true,
			ztreeSetting : setting,
			title : "列席人员",
			handle : BASE.getIconInTd($("input[name=reportOpNo]")),
			changeCallback : function (elem) {
				BASE.removePlaceholder($("input[name=reportOpNo]"));
				var AcceptOpNo=elem.data("values").val();
				var nodes = elem.data("treeNode");
				var AcceptOpName="";
				var len = elem.data("treeNode").length;
				for(var i=0;i<len;i++){
					AcceptOpName+=nodes[i].name+"|";
				}
				$("input[name=reportOpName]").val(AcceptOpName);
			}
		});
	};
	//保存方法
	var _ajaxInsert = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {ajaxData:dataForm,creditAppId:creditAppId,wkfAppId:wkfAppId},
				type : "post",
				dataType : "json",
				success : function(data) {
					//LoadingAnimate.stop();
					if (data.flag == "success") {
						top.creditFlag=true;
						top.wkfAppId = wkfAppId;
						//top.creditType=creditType;
						top.creditAppId=creditAppId;
						window.top.alert(data.msg, 1);
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
						myclose_click();
					}
				},
				error : function(data) {
					//loadingAnimate.stop();
					window.top.alert(top.getMessage("ERROR_INSERT"), 0);
				}
			});
		}
	};
	return{
		init:_init,
		ajaxInsert:_ajaxInsert
	}
}(window,jQuery)
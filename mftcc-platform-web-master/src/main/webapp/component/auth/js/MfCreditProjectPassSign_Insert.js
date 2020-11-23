;
MfCreditProjectPassSign_Insert = function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		//_setContactsOpNo();
	};
	var _setContactsOpNo = function(){
		$.each($("input[name=passSignId]"),function(i,obj){
			var passSignBrNo = $(obj).parents("tr").find("input[name=passSignBrNo]").val();
			if(currBrNo==passSignBrNo){
				/*bindDataSource($(obj).parents("tr").find("input[name=contactsOpName]"),'1','reportOpNo','尽调参与人'.true);
				$(obj).parents("tr").find("input[name=contactsOpName]").click(function () {
					bindDataSource(this,'1','contactsOpNo','尽调参与人'.true);
		        });*/
			}else if(currBrNo!=passSignBrNo){
				$(obj).parents("tr").find("input[name=contactsOpName]").attr("readOnly",true);
				$(obj).parents("tr").find("select[name=opinionType]").attr("disabled","disabled");
				$(obj).parents("tr").find("input[name=passSignReason]").attr("readOnly",true);
			}
			
		})
	};
	//保存方法
	var _ajaxInsert = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			var passSignDatas = [];
			/*var passSignDatas = [];
			$.each($("input[name=passSignBrNo]"),function(i,obj){
				if(currBrNo==$(obj).val()){
					var data={};
					var $tr = $(obj).parents("tr");
					data["passSignId"]=$tr.find("input[name=passSignId]").val();
					data["contactsOpNo"]=$tr.find("input[name=contactsOpNo]").val();
					var contactsOpName = $tr.find("input[name=contactsOpName]").val();
					if(contactsOpName==""||contactsOpName==null){
						//window.top.alert(top.getMessage("NOT_FORM_EMPTY", "负责人"), 3);
						return ;
					}
					data["contactsOpName"]=$tr.find("input[name=contactsOpName]").val();
					var opinionType = $tr.find("select[name=opinionType]").val();
					if(opinionType==""||opinionType==null){
						//window.top.alert(top.getMessage("NOT_FORM_EMPTY", "意见类型"), 3);
						return ;
					}
					data["opinionType"]=$tr.find("select[name=opinionType]").val();
					data["passSignReason"]=$tr.find("input[name=passSignReason]").val();
					passSignDatas.push(data);
				}
			});
			if(passSignDatas==null||passSignDatas==""){
				window.top.alert(top.getMessage("NOT_FORM_EMPTY", "传签内容"), 3);
				return ;
			}*/
			LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {ajaxData:dataForm,passSignList:JSON.stringify(passSignDatas),creditAppId:creditAppId,wkfAppId:wkfAppId},
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
	};
}(window,jQuery);
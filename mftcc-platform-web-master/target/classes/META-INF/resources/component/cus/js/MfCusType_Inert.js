;
var MfCusTypeInsert = function(window, $){
	
	var _init = function(){
		//滚动条
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		//明细类别（选择组件）
	    /** $("[name=subType]").popupSelection({
	    	 searchOn:true,//启用搜索
	    	 inline:true,//下拉模式
	    	 title:"客户类别明细",
	    	 multiple : true,//单选
	    	 items:ajaxData.subType,
	    	 addBtn:{
					"title":"新增",
					"fun":function(hiddenInput, elem){
						$(elem).popupSelection("hideSelect", elem);
						BASE.openDialogForSelect('新增明细类别','CUS_SUB_TYPE', elem);
					}
				}
		 });**/
	     $("#mCSB_1_container").css("overflow","visible");
	};
	
	//保存方法
	var _ajaxUpdateThis = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
					url:url,
					data:{ajaxData:dataParam},
					success:function(data){
						if(data.flag == "success"){
							top.flag=true;
							top.coop=data.coop;
							top.typeNo=data.typeNo;
							top.remark=data.remark;
							top.typeName=data.typeName;
							top.useFlag=data.useFlag;
							window.top.alert(data.msg, 1);
							myclose_click();
						}
					},error:function(data){
						 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
			});
		}
	};
	
	return{
		init:_init,
		ajaxUpdateThis:_ajaxUpdateThis,
	};
}(window, jQuery);

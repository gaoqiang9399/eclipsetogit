;
var MfCusInfoChange_Detail = function(window, $) {

	var _init = function() {
		$(".scroll-content").mCustomScrollbar({
			advanced:{
				//滚动条根据内容实时变化
				updateOnContentResize:true
			}
		});
		if(applySts != 0){
			_showApproveHis();
		}
        fieldUpdateList();

	};
	
	//展示审批历史
    function _showApproveHis(){
		//获得审批历史信息
		showWkfFlowVertical($("#wj-modeler2"),changeId,"31","cusinfo_change_approval");
		$("#cusInfoChangeApproveHis").show();
	}
	function _ajaxSave(){
		$.ajax({
			url : webPath+"/mfCusInfoChange/submitProcessAjax",
			data : {
				changeId:changeId
				},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if (data.flag == "success") {
					window.top.alert(data.msg, 3);
					myclose_click();
				} else {
					alert(data.msg, 0);
				}
			},
			error : function() {
			}
		});
	}
    //展示客户字段修改列表信息
	function fieldUpdateList(){
        $.ajax({
            url:webPath+"/mfCusInfoChange/fieldUpdateList?changeId="+changeId+"&beforeValue="+""+"&aftervalue="+""+"&changeFieldName="+""+"&fieldType="+"",
            success:function(data){
                var html = data.htmlStr;
                $("#tablechangeInfo0001").empty().html(html);
            }
        });
	}



    /**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		ajaxSave:_ajaxSave,
		showApproveHis:_showApproveHis
	};
}(window, jQuery);


//单字段编辑的保存回调方法。
function oneCallback(data) {
	for(var i in data){
		var value = data[i].value;
		var name = data[i].name;
		if(name == "changeReason"){
			var changeReason = window.parent.document.getElementsByName("changeReason");
			$(changeReason).text(value);
		}
		
	}
	
}

;
var MfBusCompensatoryConfirmList = function(window,$){
	var _init = function(){

		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		$("input[name=guarantyAgencyNo]").popupSelection({//申请机构选择
			searchOn : true, // false-不启用搜索，true-启用搜索
			inline : true, // true-内联,false-弹出
			ajaxUrl : webPath+"/mfCusAssureCompany/getAssureData",
			multiple : false, // false-单选,true-复选
			changeCallback:function(elem){//回调方法
				BASE.removePlaceholder($("input[name=guarantyAgency]"));
				$("input[name=guarantyAgency]").val($("input[name=guarantyAgencyNo]").parents("td").find(".pops-value").html());
			},
		});	

	};

			//更新操作
	var _ajaxInsert = function(obj){
		var datas = [];
		$.each($("#mfBusCompensatoryDocList").find("tbody tr"),function(index) {
			var entity = {};
			$thisTr = $(this);
			entity.isProvide = $thisTr.find("select[name=isProvide]").val();
			entity.remark = $thisTr.find("input[name=remark]").val();
            entity.docCode = $thisTr.find("input[name=docCode]").val();
            datas.push(entity);
		});
		var dataParam = JSON.stringify($(obj).serializeArray());
		$.ajax({
			url:webPath+"/mfBusCompensatoryConfirm/insertAjax",
			data:{
				ajaxData : dataParam,
				ajaxDataList : JSON.stringify(datas),
                compensatoryId : compensatoryId
			},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					window.top.alert(data.msg,1);
					myclose_click();
                }else{
					alert(top.getMessage("ERROR_INSERT"),0);
				}
			},error:function(){
				alert(top.getMessage("ERROR_INSERT"),0);
			}
		});
	};
	

	return{
		init:_init,
		ajaxInsert:_ajaxInsert,
	};
}(window,jQuery);
var mfApplyCusPer = {
		//新增保存申请
		insertSave:function(formObj){
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			top.LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataForm,
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					top.flag=true;
					top.LoadingAnimate.stop();
					if (data.flag == "success") {
                        window.location.href=webPath+"/mfApplyCusPer/getListPage";
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					top.loadingAnimate.stop();
					window.top.alert(data.msg, 0);
				}
			});
		},
		//弹出客户列表，选择客户
		selectCus: function (obj,url){
			$("#ajaxurl").val(url);
			selectCusDialog(this.getCifInfo,null,null,1);
		},
		//选择客户回调函数
		getCifInfo:function(obj){
            $("input[name='cusName']").val(obj.cusName);
			$("input[name='cusNo']").val(obj.cusNo);
			$("input[name='cusMngNo']").val(obj.cusMngNo);
			$("input[name='cusMngName']").val(obj.cusMngName);
			$("input[name='cusBrNo']").val(obj.cusBrNo);
			$("input[name='cusBrName']").val(obj.cusBrName);
			$("input[name='approvePartNo']").val(obj.cusMngNo);
		},
		//返回
		myclose:function(){
            window.location.href=webPath+"/mfApplyCusPer/getListPage";
		}
}
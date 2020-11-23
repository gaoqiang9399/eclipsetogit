;
var MfKindNodeFeeInsert=function(window, $){
	
	var _init = function(){
		$("select[name=itemNo]").popupSelection({		
			searchOn:false,//启用搜索
			inline:true,//下拉模式
			multiple:false,//单选
//			items:ajaxData.items,
			changeCallback : function (obj, elem) {
				$("input[name=itemName]").val(obj.data("text"));
			}
		});
	};
	var _insertNodeFee = function(obj){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray()); 
		$.ajax({
			url:url,
			data:{ajaxData:dataParam},
			type:'post',
			dataType:'json',
			beforeSend:function(){  
				LoadingAnimate.start();
			},success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					top.feeList = data.feeList;
					top.flag=true;
					myclose_click();
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			},complete: function(){
				LoadingAnimate.stop();
			}
		});
	};
	
	return{
		init:_init,
		insertNodeFee:_insertNodeFee
	};
	
}(window, jQuery);
;
var MfMoveableModifyApply=function(window,$){
	var _init=function(){
//		$(".scroll-content").mCustomScrollbar({
//			advanced:{
//				updateOnContentResize:true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	//保存方法
	var _ajaxModifyApplySave = function(formObj){
		var adjustedUnitPrice="";
		var pledgeBillNo="";
		$(".table_content .ls_list tbody tr").each(function(index,obj){
			$(obj).find("td").each(function(index,obj2){
				if(index==0){
					pledgeBillNo=pledgeBillNo+"|"+$(obj2).find('input').val();
				}
				if(index==3){
					if($(obj2).find('input').val()!=undefined){
						adjustedUnitPrice=adjustedUnitPrice+"|"+$(obj2).find('input').val();
					}else{
						adjustedUnitPrice=adjustedUnitPrice+"|"+$(obj2).html();
					}
				}
			});
		});
		$("input[name=pledgeBillNo]").val(pledgeBillNo);
		$("input[name=adjustedUnitPrice]").val(adjustedUnitPrice);
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var url = $(formObj).attr("action");
			var dataForm = JSON.stringify($(formObj).serializeArray());
			top.LoadingAnimate.start();
			$.ajax({
				url : url,
				data : {
					ajaxData : dataForm,
					appId:appId,
					busPleId:busPleId
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					top.flag=true;
					top.LoadingAnimate.stop();
					if (data.flag == "success") {
						window.top.alert(data.msg, 3);
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					top.loadingAnimate.stop();
					window.top.alert(data.msg, 0);
				}
			});
		}
	};
	//移库申请新增
	var _modifyApplyInput=function(){
		top.window.openBigForm(webPath+'/mfMoveableModifyApply/input?busPleId='+busCollateralId+"&appId="+appId,"移库",function(){
		});
	};
	//初始化选择押品数据源
	var _getPledgeData=function(){
		jQuery.ajax({
			url : webPath+"/mfBusCollateralDetailRel/getPledgeDataAjax?busCollateralId="+busPleId,
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("input[name=pledgeNo]").popupSelection({
						searchOn:true,//启用搜索
						inline:true,
						multiple:false,//单选
						items:data.items,
						changeCallback:function(obj,elem){
							$("input[name=pledgeName]").val(obj.data('text'));
							var pledgeNo=$("input[name=pledgeNo]").val();
							_chosePledge(pledgeNo);
						}
					});
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	var _chosePledge=function(pledgeNo){
		jQuery.ajax({
			url : webPath+"/pledgeBaseInfo/getPledgeBaseInfoAjax?pledgeNo="+pledgeNo,
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					if(data.pledgeBaseInfo!=""){
						var pledgeBaseInfo= data.pledgeBaseInfo;
						$("input[name=pledgeShowNo]").val(pledgeBaseInfo.pledgeShowNo);
						$("input[name=pledgeWorth]").val(pledgeBaseInfo.extNum01);
					//	$("input[name=unitPrice]").val('100');
						_refreshGoodsDetailList(pledgeNo);
						_initdbClick();
					}
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	}
	var _refreshGoodsDetailList=function(pledgeNo){
		jQuery.ajax({
			url : webPath+"/pledgeBaseInfoBill/getTableDataByPledgeNoAjax",
			type : "POST",
			dataType : "json",
			data:{tableId:"tabledlpledgebaseinfobill00006",pledgeNo:pledgeNo},
			async:false,
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("#goodsDetailList").html(data.tableData);
					$("#goodsDetailListdiv").show();
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	}
	var dataVal = {},parmDicUrl,keyName,SynchronousLoadding = true;
	var _initdbClick=function (){
		$(".table_content .ls_list tbody tr").unbind();
		$(".table_content .ls_list tbody tr").bind("dblclick",function(){
			$(this).addClass("editorTr");
			var col = new Array();
			$(this).parents(".ls_list").find("thead tr th").each(function(index,obj){
				if($(obj).attr("name")!==undefined){
					col[index] = $(obj).attr("name").trim();
				}
			});
			$(this).find("td").each(function(index,obj){
				var value = $(obj).html().trim();
				if(col.length>=index+1){
					dataVal[col[index]] = value;
					var width = $(obj).width();
					$(obj).css("width",width+"px");
					 if(index!=0&&index!=1&&index!=2&&index!=4){
						$(obj).html('<input name="'+col[index]+'" type="text" style="width:'+(width-2)+'px;text-align: center;" value="'+value+'"/>');
					} 
				}
				$(this).parent().unbind();
			});
		});
	}
	return{
		init:_init,
		ajaxModifyApplySave:_ajaxModifyApplySave,
		modifyApplyInput:_modifyApplyInput,
		getPledgeData:_getPledgeData
	};
}(window,jQuery);
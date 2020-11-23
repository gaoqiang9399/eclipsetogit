function init(){
//	$(".scroll-content").mCustomScrollbar({
//		advanced:{
//			theme:"minimal-dark",
//			updateOnContentResize:true
//		}
//	});
	myCustomScrollbarForForm({
		obj:".scroll-content",
		advanced : {
			theme : "minimal-dark",
			updateOnContentResize : true
		}
	});
}
//新增保存
function ajaxInsertThis(obj){
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
		LoadingAnimate.start();
		jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						window.top.alert(data.msg, 1);
						var url=webPath+"/mfExamRiskConfig/getListPage";
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
						myclose_click();
					}
				},error:function(data){
					LoadingAnimate.stop();
					 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		
		}
}
//编辑保存
function ajaxUpdateThis(obj){
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
		LoadingAnimate.start();
		jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						window.top.alert(data.msg, 1);
						var url=webPath+"/mfExamRiskConfig/getListPage";
						$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
						myclose_click();
					}
				},error:function(data){
					LoadingAnimate.stop();
					 window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		
		}
}

function selectExamIndex(){
	var indexNo = $("input[name=indexNo]").val();
	dialog({
		id:"examIndexMutiDialog",
		title:'选择检查指标',
		url: webPath+'/mfExamIndex/getExamIndexsForModel?indexNo='+indexNo,
		width:900,
		height:510,
		backdropOpacity:0,
		onshow:function(){
			this.returnValue = null;
		},onclose:function(){
			if(this.returnValue){
				var data = this.returnValue;
				$("input[name=indexNo]").val(data.indexNameEn);
				$("input[name=indexName]").val(data.indexName);
				
			}
		}
	}).showModal();
}


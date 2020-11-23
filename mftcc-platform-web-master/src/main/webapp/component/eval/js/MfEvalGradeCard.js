;
var MfEvalGradeCard=function(window,$){
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
	//新增时初始化评分卡类型，过滤掉已使用的类型
	var _gradeCardTypeInit=function(){
		var arrType = evalIndexTypeRel.split("|");
		for ( var i = 0; i < arrType.length; i++) {
			$("select[name=gradeCardType] option[value="+arrType[i]+"]").remove();
		}
	};
	//评分卡保存
	var _ajaxGradeCardSave=function(obj){
		var $obj = $(obj);
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
						top.addFlag=true;
						top.gradeCard=data.mfEvalGradeCard;
						window.top.alert(data.msg,1);
						myclose_click();
					}else if(data.flag=="error"){
						window.top.alert(data.msg,0);
					}
				},error:function(data){
					LoadingAnimate.stop();
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
	//检查评分卡权重综合不能大于100
	var _checkScorePercent=function(obj){
		var scorePercent = $("input[name=scorePercent]").val();
		jQuery.ajax({
			url:webPath+"/mfEvalGradeCard/checkScorePercentAjax",
			data:{evalScenceNo:evalScenceNo,gradeCardId:gradeCardId,scorePercent:scorePercent},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag=="1"){
					_ajaxGradeCardSave(obj);
				}else{
					window.top.alert(top.getMessage("NOT_FORM_TIME",{"timeOne":"各项权重之和","timeTwo":"100"}),3);
					$("input[name=scorePercent]").val("");
				}
			},error:function(data){
				window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	return{
		init:_init,
		ajaxGradeCardSave:_ajaxGradeCardSave,
		gradeCardTypeInit:_gradeCardTypeInit,
		checkScorePercent:_checkScorePercent
	};
}(window,jQuery);
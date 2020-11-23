window.evalAjaxSave = function(obj,url){
	var $obj = $(obj);
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		var ajaxUrl = $(obj).attr("action");
		var updateFlag = false;
		if(ajaxUrl===undefined||ajaxUrl==null||ajaxUrl==""){
			ajaxUrl = url;
			updateFlag = true;
		}
		var dataParam = JSON.stringify($(obj).serializeArray()); 
		LoadingAnimate.start();
		jQuery.ajax({
			url:ajaxUrl,
			data:{ajaxData:dataParam,cusBaseType:cusBaseType},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					alert(data.msg,1);
					$obj.find(".from_btn").remove();
					$obj.parents(".li_content").attr("data-flag","success");
					$obj.parents(".content_ul").data("entityData",data.entityData);
					$(".showprogress ul li[name=chosefin]").addClass("success");
					$.each(data.entityData, function(name,val) {
						setFormEleValue(name,val,$obj.parents(".content_ul").find("li div[name=evalapp] form"));
					});
					$obj.removeAttr("action");
					dataMap=data;
					refreshGradeCard(data.gradeCardListData);
					initThead("",showData);
					if(!updateFlag){
						initData(data.listData);
					}
					$("form[id=choseFinForm] input[name=evalAppNo]").val(data.entityData.evalAppNo);
					evalScenceNo=data.entityData.evalScenceNo;
					evalAppNo=data.entityData.evalAppNo;
					$("#chosefinButton").remove();//去掉操作按钮，避免重复操作
					$(".showprogress ul").find("li[name=dx]").click();
					
				}else if(data.flag=="error"){
					alert(data.msg,0);
				}
			},error:function(data){
				LoadingAnimate.stop();
                alert(data.msg,0);
			}
		});
	}
};
//发起评级保存后刷新评分卡
window.refreshGradeCard = function(gradeCardListData){
	var StrHtml="";
	$.each(gradeCardListData,function(i,obj){
		var divName="";
		var dataTarget="";
		var gradeCardName=obj.gradeCardName;
		var tableHtml ='<table class="ls_list_a" style="width: 100%;"><thead></thead><tbody></tbody></table>';
		if(obj.gradeCardType=="1"){//财务
			divName="fin"+obj.gradeCardId;
			dataTarget="finDetailInfo"+obj.gradeCardId;
		}
		if(obj.gradeCardType=="2"){//定量
			divName="dl"+obj.gradeCardId;
			dataTarget="dlDetailInfo"+obj.gradeCardId;
		}
		if(obj.gradeCardType=="3"){//定性
			divName="dx"+obj.gradeCardId;
			dataTarget="dxDetailInfo"+obj.gradeCardId;
			tableHtml='<form id="evalDx'+obj.gradeCardId+'">'+tableHtml+'</form>';
		}
		if(obj.gradeCardType=="4"){//调整
			divName="adj"+obj.gradeCardId;
			dataTarget="adjDetailInfo"+obj.gradeCardId;
			tableHtml='<div name="initadj'+obj.gradeCardId+'"><form id="initadj'+obj.gradeCardId+'">'+tableHtml+'</form></div>';
		}
		if(obj.gradeCardType!="5"){
			StrHtml=StrHtml+'<div class="row clearfix"><div class="col-xs-12 column">'+
			'<div name="'+divName+'" class="panel panel-default li_content_type">'+
			'<div class="panel-heading"><h4 class="panel-title"><span class="span-title">'+gradeCardName+'</span>'+
			'<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#'+dataTarget+'" data-parent="#accordion" style="height: auto;">'+
			'<i class="i i-close-up"></i><i class="i i-open-down"></i></button></h4></div>'+
			'<div id="'+dataTarget+'" class="li_content panel-collapse collapse in">'+tableHtml+
			"</div></div></div></div>";
		}
	})
	$("#gradeCard-content").html(StrHtml);
	
	$("div[id=gradeCard] div[id=moreBar]").remove();
	$("div[id=evalapp] div[id=moreBar]").remove();
	myCustomScrollbarForForm({
		obj:"#gradeCard",
		advanced : {
			updateOnContentResize : true
		}
	});
};
/**
 * 保存评分卡
 */
window.evalUpdate = function(){
	var dataParam="";
	$("form[id^=evalDx]").each(function(i,obj){
		var gradeCardId=$(obj).attr("id");
		dataParam=dataParam+"@"+gradeCardId+"&"+JSON.stringify($(obj).serializeJSON());
	});
	$("form[id^=initadj]").each(function(i,obj){
		var gradeCardId=$(obj).attr("id");
		dataParam=dataParam+"@"+gradeCardId+"&"+JSON.stringify($(obj).serializeJSON());
	});
	
	LoadingAnimate.start();
	jQuery.ajax({
		url:webPath+"/appEval/updateEvalAjax",
		data:{ajaxData:dataParam,evalAppNo:evalAppNo,evalScenceNo:evalScenceNo},
		type:"POST",
		dataType:"json",
		async:false,
		beforeSend:function(){  
		},success:function(data){
			if(data.flag == "success"){
				//$("evalDx").parents(".li_content").attr("data-flag","success");
				$(".showprogress").find("li[name=dx]").addClass("success");
				getEvalAdjDetailInfo();
				LoadingAnimate.stop();
				evalStage=data.appEval.evalStage;
				$(".showprogress ul").find("li[name=evalapp]").click();
				var evalFormId = "eval1001";
				if(cusType=="202"){
					evalFormId = "evalpers1001";
				}
				evalAppinit(evalFormId);
				
			}else if(data.flag=="error"){
				LoadingAnimate.stop();
				alert(data.msg,0);
			}
		},error:function(data){
			LoadingAnimate.stop();
			alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
	var check = $(".selected").attr("name");
	if(check == 'evalapp'){
		$("div[id=gradeCard] div[id=moreBar]").remove();
		$("div[id=evalapp] div[id=moreBar]").remove();
		myCustomScrollbarForForm({
			obj:"#evalapp",
			advanced : {
				updateOnContentResize : true
			}
		});
	}
};
/**
 * 定性提交
 * @param {Object} obj
 * @param {Object} url
 */
window.evalDxUpdate = function(obj,url){
	var $obj = $(obj);
	var evalScenceNo = $obj.find("table>tbody>tr").eq(0).data("entityObj").scenceNo;
	var evalAppNo = $(obj).parents(".content_ul").data("entityData").evalAppNo;
	var dataParam = JSON.stringify($obj.serializeJSON()); 
	LoadingAnimate.start();
	jQuery.ajax({
			url:url,
			data:{ajaxData:dataParam,evalScenceNo:evalScenceNo,evalAppNo:evalAppNo},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					$(obj).parents(".li_content").attr("data-flag","success");
					$(".showprogress").find("li[name=dx]").addClass("success");
					$obj.parents("li").find(".li_title .scoreShow .score").text(data.formData.score);
					$(".content_ul li div[name=adj]").find("form").find("input[name=dxScore]").val(data.formData.score);
					$obj.removeAttr("action");
					getEvalDxDetailInfo(obj);
					LoadingAnimate.stop();
					$(".showprogress ul").find("li[name=evalapp]").click();
				}else if(data.flag=="error"){
					alert(data.msg,0);
				}
			},error:function(data){
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
};

/**
 * 保存调整评分
 */
window.evalAdjUpdate = function(obj,url){
	var $obj = $(obj);
	var evalScenceNo = $obj.parents(".content_ul").data("entityData").evalScenceNo;
	var evalAppNo = $(obj).parents(".content_ul").data("entityData").evalAppNo;
	var dataParam = JSON.stringify($obj.serializeJSON()); 
	LoadingAnimate.start();
	jQuery.ajax({
		url:url,
		data:{ajaxData:dataParam,evalAppNo:evalAppNo,evalScenceNo:evalScenceNo},
		type:"POST",
		dataType:"json",
		beforeSend:function(){  
		},success:function(data){
			if(data.flag == "success"){
				$(obj).parents(".li_content").attr("data-flag","success");
				$(".showprogress").find("li[name=dx]").addClass("success");
				if(data.entityData!==undefined){
					$(obj).parents(".li_content").find("input[name=adjustScore]").val(data.entityData.adjustScore);
				}
				$(obj).removeAttr("action");
				getEvalAdjDetailInfo(obj);
				LoadingAnimate.stop();
				$(".showprogress ul").find("li[name=evalapp]").click();
			}else if(data.flag=="error"){
				alert(data.msg,0);
			}
		},error:function(data){
			loadingAnimateClose();
			alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
};

window.evalAppUpdate = function(obj,url){
	 
	var $obj = $(obj);
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		LoadingAnimate.start();
		jQuery.ajax({
			url:url,
			data:{ajaxData:JSON.stringify($obj.serializeArray())},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					$(obj).parents(".li_content").attr("data-flag","success");
					$(".showprogress ul li[name=evalapp]").addClass("success");
					alert(data.msg,1);
				}else if(data.flag=="error"){
					alert(data.msg,0);
				}
			},error:function(data){
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
};

/**
 * 综合评分更新
 * @param {Object} obj
 * @param {Object} url
 */
window.evalCompreUpdate = function(obj,url){
	var $obj = $(obj);
	var $formObj = $obj.parents(".li_content").find("form[name=operform]");
	var evalAppNo = $obj.parents(".content_ul").data("entityData").evalAppNo;
	var evalScenceNo = $obj.parents(".content_ul").data("entityData").evalScenceNo;
	var $initadj = $obj.parents(".li_content").find("form[id=initadj]");
	var dataParam = JSON.stringify($formObj.serializeJSON()); 
	var adjParm = JSON.stringify($initadj.serializeJSON());
	var flag = submitJsMethod($formObj[0], '');
	var manAdjustScore = $formObj.serializeJSON().manAdjustScore;
	if(manAdjustScore>5||manAdjustScore<-5){
		flag = false;
		alert("客户经理调整分数必须在(-5~5)之间",0);
	}
	if(flag){
		LoadingAnimate.start();
		jQuery.ajax({
			url:url,
			data:{ajaxData:dataParam,evalAppNo:evalAppNo,evalScenceNo:evalScenceNo,adjParm:adjParm},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					alert(data.msg,1);
					$(obj).parents(".li_content").attr("data-flag","success");
					$(".showprogress ul li[name=adj]").addClass("success");
					if(data.entityData!==undefined){
						$(obj).parents(".li_content").find("input[name=adjustScore]").val(data.entityData.adjustScore);
					}
					$(obj).removeAttr("action");
				}else if(data.flag=="error"){
					alert(data.msg,0);
				}
			},error:function(data){
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	}
};

window.evalAppSubmit = function(obj,url){
	var $obj = $(obj);
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		LoadingAnimate.start();
		jQuery.ajax({
			url:url,
			data:{ajaxData:JSON.stringify($obj.serializeArray())},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					//审批提醒弹窗，不自动关闭
					window.top.alert(data.msg,3);
					$obj.find(".from_btn").remove();
					//window.top.cloesBigForm(window,"","iframepage");
					top.cusLevelName = data.cusLevelName;
					top.creditFlag=true;
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
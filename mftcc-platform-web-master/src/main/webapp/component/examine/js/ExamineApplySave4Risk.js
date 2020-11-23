;
var ExamineApplySave4Risk=function(window,$){
	var _examApplySaveAjax=function(obj){
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
				data:{ajaxData:dataParam,entrance:"2"},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					LoadingAnimate.stop();
					if(data.flag == "success"){
						$obj.find(".from_btn").remove();
						$obj.parents(".li_content").attr("data-flag","success");
						$obj.parents(".content_ul").data("entityData",data.entityData);
						$(".showprogress ul li[name=chosefin]").addClass("success");
						$obj.removeAttr("action");
						dataMap=data;
						templateId=data.entityData.templateId;
						examHisId=data.entityData.examHisId;
						resultFormId=data.baseForm;
						$("#chosefinButton").remove();//去掉操作按钮，避免重复操作
						if(data.indexFlag=="1"){
							_refreshGradeCard(data.examCardListData);
							ExamineApply4Risk.initThead("",ExamineApply4Risk.showData);
							ExamCardInitData.initData(data.listData);
							$(".showprogress ul").find("li[name=dx]").click();
						}else{
							$(".showprogress ul li[name=dx]").addClass("success");
							$(".showprogress ul li[name=dx]").hide();
							$(".showprogress ul").find("li[name=evalapp]").click();
						}
						//初始化检查要件
						initDocNodes();
						ExamineApply4Risk.template_init();
						myCustomScrollbarForForm({
							obj:"#evalapp",
							advanced : {
								updateOnContentResize : true
							}
						});
					}else if(data.flag=="error"){
						console.log(data.msg);
					}
				},error:function(data){
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
	var _refreshGradeCard=function(examCardListData){
		var StrHtml="";
		$.each(examCardListData,function(i,obj){
			var divName="";
			var dataTarget="";
			var examineCardName=obj.examineCardName;
			var tableHtml ='<table class="ls_list_a" style="width: 100%;"><thead></thead><tbody class="level1"></tbody></table>';
			divName="dx"+obj.examineCardId;
			dataTarget="dxDetailInfo"+obj.examineCardId;
			tableHtml='<form id="evalDx'+obj.examineCardId+'">'+tableHtml+'</form>';
			StrHtml=StrHtml+'<div class="row clearfix"><div class="col-xs-12 column">'+
			'<div name="'+divName+'" class="panel panel-default li_content_type">'+
			'<div class="panel-heading"><h4 class="panel-title"><span class="span-title">'+examineCardName+'</span>'+
			'<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#'+dataTarget+'" data-parent="#accordion" style="height: auto;">'+
			'<i class="i i-close-up"></i><i class="i i-open-down"></i></button></h4></div>'+
			'<div id="'+dataTarget+'" class="li_content panel-collapse collapse in">'+tableHtml+
			"</div></div></div></div>";
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
	//保存检查卡评分信息
	var _examCardSave=function(){
		var dataParam="";
		$("form[id^=evalDx]").each(function(i,obj){
			var gradeCardId=$(obj).attr("id");
			dataParam=dataParam+"@"+gradeCardId+"&"+JSON.stringify($(obj).serializeJSON());
		});
		LoadingAnimate.start();
		jQuery.ajax({
			url:webPath+"/mfExamineHis/examCardSaveAjax",
			data:{ajaxData:dataParam,examHisId:examHisId,templateId:templateId},
			type:"POST",
			dataType:"json",
			async:false,
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					$(".showprogress").find("li[name=dx]").addClass("success");
					LoadingAnimate.stop();
					$(".showprogress ul").find("li[name=evalapp]").click();
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
	//检查提交审批
	var _examApplySubmit=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			alert(top.getMessage("CONFIRM_OPERATION","贷后检查提交"),2,function(){
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				jQuery.ajax({
					url:url,
					data:{ajaxData:dataParam,formId:resultFormId},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						LoadingAnimate.stop();
						if(data.flag == "success"){
							_submitAjax();
						}
					},error:function(data){
						LoadingAnimate.stop();
						window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			});
		}
	};
	var _submitAjax=function(){
		LoadingAnimate.start();
		jQuery.ajax({
			url:webPath+"/mfExamineHis/submitAjax",
			data:{examHisId:examHisId,entrance:entrance},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					window.top.alert(data.msg, 3);
					myclose_click();
					myclose_task();
				}else{
					window.top.alert(data.msg, 0);
				}
			},error:function(data){
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	//检查是否存在正在进行的贷后检查
	var _checkExistExamining=function(){
		
	};
	return{
		examApplySaveAjax:_examApplySaveAjax,
		examCardSave:_examCardSave,
		examApplySubmit:_examApplySubmit,
	};
}(window,jQuery);
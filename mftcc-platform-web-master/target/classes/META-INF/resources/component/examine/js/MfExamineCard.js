;
var MfExamineCard=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	//添加检查卡
	var _addExamCard=function(){
		top.addFlag=false;
		top.examCard="";
		top.window.openBigForm(webPath+'/mfExamineCard/input?examTemplateId='+templateId,"新增检查卡",function(){
			if(top.addFlag){
				var cardId=top.examCard.examineCardId;
				var cardTitle=top.examCard.examineCardName;
				var gradeCardType="";
				var entityKey="evalScenceDxRel";
				var $li = $("<li id='"+cardId+"' style='margin-top: 20px;'></li>");
				var $lititle = $('<div class="title_btn"></div>');
				$lititle.append('<ol id='+entityKey+' class="li_title color_theme" style="font-size: 14px;margin-left:-8px">'+
						'<li class="active"><span class="config-item"><span name="title">'+cardTitle+'</span><i class="i i-bianji2 item-delete" onclick="MfEvalGradeCardComm.editGradeCard(\''+cardId+'\');"></i><i class="i i-lajitong item-delete" onclick="MfEvalGradeCardComm.deleteGradeCard(\''+cardId+'\');"></i></span></li></ol>');
				$lititle.append('<div class="li_btn colse">'+
						'<input type="button" value="新增评级指标项" onclick="addPropertyItem(this)"/>'+
						'<input type="button" value="添加分组" onclick="addfirstLev(this,\''+getUrlSplit(entityData[entityKey].insertUrl)+'\')"/>'+
				'</div>');
				$li.attr("data-name",entityKey);
				var $table = $('<table class="ls_list"><thead><tr></tr></thead><tbody></tbody></table>');
				var colKeyIndex = 0;
				$.each(entityData[entityKey].data,function(colKey,detail){
					if(entityData[entityKey].display.indexOf(colKey)==-1&&colKey!="ctrl_btn"){
						if(colKeyIndex>0){
							$table.find("thead tr").append('<th style="width:90px" name="'+colKey+'">'+detail+'</th>');
						}else{
							$table.find("thead tr").append('<th name="'+colKey+'">'+detail+'</th>');
						}
						colKeyIndex++;
					}else if(colKey=="ctrl_btn"){
						$table.find("thead tr").append('<th style="width:150px" name="'+colKey+'">'+detail+'</th>');
					}
				});
				$li.append($lititle);
				$li.append('<div class="li_content"></div>');
				$li.find(".li_content").append($table);
				$(".data-content").append($li);
				//更新导航轴
				navLine.createNavLine();
			}
		});
	};
	var _editExamCard=function(examineCardId){
		top.addFlag=false;
		top.examCard="";
		top.window.openBigForm(webPath+'/mfExamineCard/getById?examineCardId='+examineCardId,"编辑检查卡",function(){
			if(top.addFlag){
				$("#"+examineCardId).find("span[name=title]").html(top.examCard.examineCardName);
			}
		});
	};
	var _deteleExamCard=function(examineCardId){
		//LoadingAnimate.start();
		jQuery.ajax({
			url:webPath+"/mfExamineCard/deleteAjax",
			data:{examineCardId:examineCardId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				//LoadingAnimate.stop();
				if(data.flag == "success"){
					$("li[id="+examineCardId+"]").remove();
					window.top.alert(data.msg,3);
				}else if(data.flag == "error"){
					window.top.alert(data.msg,0);
				}
			},error:function(data){
				//LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	//检查卡保存
	var _ajaxExamCardSave=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			//LoadingAnimate.start();
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					//LoadingAnimate.stop();
					if(data.flag == "success"){
						top.addFlag=true;
						top.examCard=data.examCard;
						myclose_click();
					}
				},error:function(data){
					LoadingAnimate.stop();
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
	
	//新增检查指标
	var _addExamineIndex=function(){
		top.addFlag=false;
		top.window.openBigForm(webPath+'/mfExamIndex/input','检查指标配置',function(){
			if(top.addFlag){
				getListForAppPropertyData();
			}
		});
	};
	//编辑检查指标
	var _editExamineIndex=function(obj){
		var indexId=$(obj).parents("tr").find("select").find("option:selected").val();
		top.addFlag=false;
		top.window.openBigForm(webPath+'/mfExamIndex/getById?indexId='+indexId,'检查指标配置',function(){
			if(top.addFlag){
				getListForAppPropertyData();
			}
		});
	};
	return{
		init:_init,
		addExamCard:_addExamCard,
		ajaxExamCardSave:_ajaxExamCardSave,
		editExamCard:_editExamCard,
		deteleExamCard:_deteleExamCard,
		addExamineIndex:_addExamineIndex,
		editExamineIndex:_editExamineIndex
	}
}(window,jQuery);
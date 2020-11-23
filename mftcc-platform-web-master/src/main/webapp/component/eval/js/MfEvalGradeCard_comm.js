;
var MfEvalGradeCardComm=function(window,$){
	var _addGradeCard=function(){
		top.addFlag=false;
		top.gradeCard="";
		top.window.openBigForm(webPath+'/mfEvalGradeCard/input?evalScenceNo='+evalScenceNo,"新增评分卡",function(){
			if(top.addFlag){
				/*//添加评分卡
				var cardTitle = top.gradeCard.gradeCardName;
				var cardId = top.gradeCard.gradeCardId;
				var cardType = top.gradeCard.gradeCardType;
				$.each(entityData,function(entityKey,data){
					var gradeCardType="";
					if(entityKey=="evalScenceFinRel"){
						gradeCardType="1";
					}
					if(entityKey=="evalScenceDlRel"){
						gradeCardType="2";
					}
					if(entityKey=="evalScenceDxRel"){
						gradeCardType="3";
					}
					if(entityKey=="evalScenceAdjRel"){
						gradeCardType="4";
					}
					if(entityKey=="evalScenceRestrictRel"){
						gradeCardType="5";
					}
					if(cardType==gradeCardType){
						var $li = $("<li id='"+cardId+"' style='margin-top: 20px;'></li>");
						var $lititle = $('<div class="title_btn"></div>');
						$lititle.append('<ol id='+entityKey+' class="li_title color_theme" style="font-size: 14px;margin-left:-8px">'+
								'<li class="active"><span class="config-item"><span name="title">'+cardTitle+'</span><i class="i i-bianji2 item-delete" onclick="MfEvalGradeCardComm.editGradeCard(\''+cardId+'\');"></i><i class="i i-lajitong item-delete" onclick="MfEvalGradeCardComm.deleteGradeCard(\''+cardId+'\');"></i></span></li></ol>');
						if(entityKey=="evalScenceDlRel"||entityKey=="evalScenceFinRel"||entityKey=="evalScenceRestrictRel"||entityKey=="evalScenceDxRel"){
							$lititle.append('<div class="li_btn colse">'+
									'<input type="button" value="新增评级指标项" onclick="addPropertyItem(this)"/>'+
									'<input type="button" value="添加分组" onclick="addfirstLev(this,\''+getUrlSplit(entityData[entityKey].insertUrl)+'\')"/>'+
							'</div>');
						}else if(entityKey=="evalScenceAdjRel"){
							$lititle.append('<div class="li_btn colse">'+
									'<input type="button" value="新增评级指标项" onclick="addPropertyItem(this)"/>'+
									'<input type="button" value="添加分组" onclick="addAdjRel(this,\''+getUrlSplit(entityData[entityKey].insertUrl)+'\')"/>'+
							'</div>');
						}else{
							$lititle.append('<div class="li_btn colse"></div>');
						}
						$li.attr("data-name",entityKey);
						var $table = $('<table class="ls_list"><thead><tr></tr></thead><tbody></tbody></table>');
						var colKeyIndex = 0;
						$.each(data.data,function(colKey,detail){
							if(data.display.indexOf(colKey)==-1&&colKey!="ctrl_btn"){
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
					}
				});
				//更新导航轴
				navLine.createNavLine();*/
				EvalScenceIndexRelConfig.init();
			}
		});
	}
	var _editGradeCard=function(gradeCardId){
		top.addFlag=false;
		top.gradeCard="";
		top.window.openBigForm(webPath+'/mfEvalGradeCard/getById?gradeCardId='+gradeCardId,"新增评分卡",function(){
			if(top.addFlag){
				$("#"+gradeCardId).find("span").html(top.gradeCard.gradeCardName);
			}
		});
	};
	//评分卡删除
	var _deleteGradeCard=function(gradeCardId){
		alert(top.getMessage("CONFIRM_DELETE"),2,function(){
			//LoadingAnimate.start();
			jQuery.ajax({
				url:webPath+"/mfEvalGradeCard/deleteAjax",
				data:{gradeCardId:gradeCardId},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					//LoadingAnimate.stop();
					if(data.flag == "success"){
						window.top.alert(data.msg,1);
						$("li[id="+gradeCardId+"]").remove();
					}else if(data.flag=="error"){
						window.top.alert(data.msg,0);
					}
				},error:function(data){
					//LoadingAnimate.stop();
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		});
	};
	return{
		addGradeCard:_addGradeCard,
		editGradeCard:_editGradeCard,
		deleteGradeCard:_deleteGradeCard
	};
}(window,jQuery);
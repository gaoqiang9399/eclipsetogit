var formHtml,isHtml,projectName;
//现阶段根据传递的type，获取不同的表单
//isShowNoData 无数据显示暂无数据 1显示
function showWkfFlowVertical(dom,appNo,formType,approveType,isShowNoData){
	var $workArea = dom;
	var modelDom = $workArea.parent();
	$.ajax({
		url:webPath+"/wkfChart/getHisWkfChartInfoAjax",
		type:'post',
		data:{appNo:appNo,formType:formType,approveType:approveType,isApp:false},
		dataType:'json',
		success:function(data){
			if(data.flag=="success"){
				if(data.approveHisShowFlag=="2"){//列表展示
                    modelDom.html(data.htmlStr);
                    $workArea.remove();
				}else{//图文展示
                    formHtml = data.formHtml;
                    isHtml = data.isHtml;
                    projectName = data.projectName;
                    var inputShowFlag = data.inputShowFlag;
                    var jsonArray = data.jsonArray;
                    if(jsonArray.length>0){
                        for(var i=0;i<jsonArray.length;i++){
                            addNodeVertical($workArea,jsonArray[i],inputShowFlag);
                        }

                        var lineList = $workArea.find(".item-line");
                        if(lineList.length>0){
                            lineList.eq(lineList.length-1).remove();
                        }
                        $workArea.parents(".approval-process").css("height",100*jsonArray.length+"px");
                        if($.mCustomScrollbar) {
                            modelDom.mCustomScrollbar({
                                theme: "minimal-dark",
                                scrollButtons: {
                                    enable: true
                                },
                                advanced: {
                                    updateOnBrowserResize: true,
                                    updateOnContentResize: true,
                                    autoExpandHorizontalScroll: true
                                },
                                callbacks:{
                                    onInit : function(){}
                                }

                            });
                        }
                    }else{
                    	if(isShowNoData!='1'){
                            $workArea.parents(".approval-hist").remove();
						}else{
                            $workArea.parents(".approval-process").html('<span >暂无审批历史</span>');
						}

					}
                }
			}else{
                if(isShowNoData!='1'){
                    $workArea.parents(".approval-hist").remove();
                }else{
                    $workArea.parents(".approval-process").html('<span >暂无审批历史</span>');
                }
			}
		}
	});
}

function addNodeVertical($workArea,node,inputShowFlag){
//	node.result = "refused";
//	node.state = "refused";
	var opinion="";
	if(!node.parentExecution && node.result&&node.result=="pass"){
		opinion="yes";
	}else if(!node.parentExecution && node.result&&node.result=="pass_condition"){
        opinion="yes";
	}else if(!node.parentExecution && node.result&&node.result=="rollback"){
		opinion="rollback";
	}else if(!node.parentExecution && node.result=="disagree"){
		opinion="disagree";
	}else if(!node.parentExecution && node.result=="refuse"){
		opinion="no";
	}else if(node.parentExecution && node.result=="disagree"){//隐藏会签审批意见时，会签节点不同意时的样式与“同意”时一样
        node.result="pass";
        node.state="completed";
	}else if(!node.parentExecution && node.result=="rollback_rejected"){//驳回
        opinion="rejected";
	}else if(!node.parentExecution && node.result=="rollback_cancel"){//取消
        opinion="cancel";
    }else if(!node.parentExecution && node.result=="reconside"){//复议
        opinion="reconside";
        node.state="completed";
    } else if (!node.parentExecution && node.result.indexOf("supplement") >= 0) {// 退回补充资料
        opinion = "supplement";
        node.result = "pass";
    }

//	if(node.state=="refused"){
//		opinion="no";
//	}
	var title = "",lineClass = "item-line";
	if(node.state&&node.state!="open"&&node.state!="refused"){
		title = "<span class='title' title='"+node.name.replace(/\\/g, "")+"'>"+node.name.replace(/\\/g, "")+"</span><span class='user'>"+node.userName+"</span>";
		lineClass += " complete";
	}else{
		title = node.name.replace(/\\/g, "");
	}
	var $li = $("<li class='item item-vert'><span class='item-vert-opinion "+opinion+"'></span><span class='"+lineClass+"'></span><span class='item-vert-ico'><span class='icon "+node.type+"'></span></span><div class='item-vert-text'>"+title+"</div><div class='item-vert-content'></div></li>");
    var i;
	if(node.state&&node.state!="open"){
		$li.find(".item-vert-content").append("<span>审批时间：<span class='span-date'>"+node.end+"</span>历时约<span class='span-time'>"+getHours(node)+"</span>小时</span>");
		if(node.attachment){
			$li.find(".item-vert-content").append("<span class='fj'></span><br/>");
			for (i = 0; i < node.attachment.length; i++) {
				$li.find(".item-vert-content").append('<a title="下载" href="'+webPath+'/docUpLoad/getFileDownload?docNo='+node.attachment[i].docNo+'&docBizNo='+node.attachment[i].docBizNo+'">'+node.attachment[i].docName+'</a><br/>');
			}
		}else{
			$li.find(".item-vert-content").append('<span style="margin:0 5px">暂无附件</span>');
		}
		if(!node.parentExecution){//不是会签节点
            if(node.result&&node.result=="pass"){
            	if(node.formId == "" || node.formId == null){
                    $li.find(".item-vert-content").append("<table id='approveIdea'><tr><td class='first'>意见：</td><td class='approveIdea'>"+node.approveIdea);
				}else {
                    $li.find(".item-vert-content").append("<table id='approveIdea'><tr><td class='first'>意见：</td><td class='approveIdea'>"+node.approveIdea+"<span class='ideaOpt'><i></i><span>展开</span></span></td></tr></table>");
                }
            }else if(node.result){
                $li.find(".item-vert-content").append("<table><tr><td class='first'>意见：</td><td class='approveIdea no'>"+node.approveIdea+"<span class='ideaOpt'><i></i><span>展开</span></span></td></tr></table>");
            }
        }
        $li.find(".item-vert-content").append("<div class='formHtml' style='display:none;' id='"+node.id+"'>"+node.formHtml+"</div>");
		$li.find(".formHtml table").attr("align","");    //


		// if("false"==isHtml){
		// 	$li.find(".formHtml .fieldShow").each(function(){
		// 		for(var key in node.appValue){
		// 			if(key == $(this).hasClass("name")){
		// 				$(this).text(node.appValue[key]);
		// 				// $(this).closest("td").prepend(node.appValue[key]);
		// 				// $(this).remove();
		// 			}
		// 		}
		// 	});
		// 	$li.find(".formHtml select").each(function(){
		// 		for(var key in node.appValue){
		// 			if(key == $(this).attr("name")){
		// 				var val = node.appValue[key].replace(/\|/g,"");
		// 				if(!val){
		// 					val = 0;
		// 				}
		// 				var text = $(this).find("option[value="+val+"]").html();
         //                $(this).parent("td").text(text);
		// 				// $(this).closest("td").prepend(text);
		// 				// $(this).remove();
		// 			}
		// 		}
		// 	});
		// 	if(inputShowFlag == '1'){
		// 		// input框追加展示值
		// 		$li.find(".formHtml input").each(function(){
		// 			$(this).after($(this).val());
		// 		});
		// 	}
		// 	$li.find(".formHtml input").remove();
		// 	$li.find(".formHtml select").remove();
		// }
	}else if(node.state&&node.state=="open"){
		$li.find(".item-vert-content").append("<span>提交时间：<span class='span-date'>"+node.create+"</span>历时约<span class='span-time'>"+getHours(node)+"</span>小时</span>");
		if(node.attachment){
			$li.find(".item-vert-content").append("<span class='fj'></span>");
			for (i = 0; i < node.attachment.length; i++) {
				$li.find(".item-vert-content").append('<a href="DocUploadAction_fileDownload.action?docNo='+node.attachment[i].docNo+'&docBizNo='+node.attachment[i].docBizNo+'">'+node.attachment[i].docName+'</a>');
			}
		}else{
			$li.find(".item-vert-content").append('<span style="margin:0 5px">暂无附件</span>');
		}
	}
	
	$li.find(".ideaOpt").click(function(){
		if($(this).find("i").hasClass("opened")){
			$(this).find("i").removeClass("opened");
			$(this).find("span").html("展开");
			closeForm($li,$(this).closest(".item-vert-content").children(".formHtml"));
			
			
		}else{
			$(this).find("i").addClass("opened");
			$(this).find("span").html("收起");
			openForm($li,$(this).closest(".item-vert-content").children(".formHtml"));
		}
	});
	
	addStyleVertical($workArea,node,$li.find(".icon"),$li.find(".item-vert-text"));
	$workArea.append($li);
	if($li.width()==0){
        $li.find(".item-vert-content").css("width",(828-270)+"px");
	}else{
        //$li.find(".item-vert-content").css("width",($li.width()-270)+"px");
	}
	var tableWith = $li.find(".item-vert-content").find('#approveIdea').prev().width() +
		            $li.find(".item-vert-content").find('#approveIdea').prev().prev().width();
	//$li.find(".item-vert-content").find('#approveIdea').css("width",tableWith+"px");
	//setLiHeight($li);
}
function openForm($li,formDom){
	formDom.animate({height: 'toggle', opacity: 'toggle'}, 200);
}
function closeForm($li,formDom){
	formDom.animate({height: 'toggle', opacity: 'toggle'}, 200);
}
function setLiHeight($li){
	var contentHeight = $li.find(".item-vert-content").height();
	if(contentHeight<90){
		contentHeight = 90;
		$li.css("height",contentHeight+"px");
		$li.find(".item-line").css("height",contentHeight+"px");
	}else{
		$li.css("height",contentHeight+16+"px");
		$li.find(".item-line").css("height",contentHeight+16+"px");
	}
}

function addStyleVertical($workArea,node,$div,$content){
	var className = "";
	/*if(node.type == "lineToNode"){
		
	}else */
	if(node.state=="open"){
		var cut = 720;//12小时 720分钟
		var date1= node.create;  //开始时间  
	    var date2 = new Date();    //结束时间  
//		var date2 = new Date("2017-04-30 20:13:26");    //结束时间  
	    var date3 = date2.getTime() - new Date(date1).getTime();
	    var minutes=date3/1000/60;
	    var du = minutes*(360/cut);
		$div.addClass("activity");
		$content.addClass("activity");
		$content.attr("title",node.name);
    	/*$div.append('<div class="shanxing"><div class="sx1"></div><div class="sx2"></div></div>');
		$div.append("<div class='open-m'></div>");*/
		/*$content.append("<span class='item-time'>"+node.create+"</span>");
		$content.append("<div class ='div-time'>历时"+getHours(node)+"小时</div>");*/
		className = "line-complete";
	}else if(node.result=="pass"||node.id=="start" || node.result=="pass_condition"|| node.result=="reconside"){
		$div.addClass("complete");
		$content.addClass("complete");
		className = "line-complete";
	}else if(node.result=="refuse"||node.result=="disagree"||node.result=="rollback" || node.result=="rollback_supplement"|| node.result=="rollback_rejected"|| node.result=="rollback_cancel"){
		$div.addClass("refused");
		$content.addClass("refused");
		$content.attr("title",node.name);
		className = "line-complete";
	}else{
		className = "line-default";
	}
}

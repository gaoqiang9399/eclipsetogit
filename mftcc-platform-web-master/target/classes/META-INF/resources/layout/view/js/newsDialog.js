var newsDialog = {
	

/**
 * 几秒后隐藏
 * delaySecond 延时时间
 * animatSecond 动画渐变时间
 * @returns
 */
hideAfterSomeSecond : function(delaySecond,animatSecond){   
		setTimeout(function () {
			newsDialog.setDivHideSlowly(animatSecond);
		}, delaySecond);
		
},


/**
 * 隐藏消息框
 */
setDivHide:function(){   
		$("#newsFrameDiv").hide();
},
/**
 * 慢慢隐藏消息框
 */
setDivHideSlowly:function(second){   
	$("#newsFrameDiv").animate({opacity:"0"},second,function(){
		$("#newsFrameDiv").hide();
		$("#newsFrameDiv").css("opacity","1"); //恢复透明度
	});
},


/**
 * 显示消息框
 */
setDivShow:function(){   
	$("#newsFrameDiv").stop();  
	$("#newsFrameDiv").css("opacity","1"); //恢复透明度
	$("#newsFrameDiv").show(); 
},


/**
 * 设置初始化文件内容
 */
setInitContent:function(){
	var result=getWaitToDoCnt();
	var tmpHtml=""
	if(result==0){
		result=getNotReadNewsCnt();
		if(result>0){
			$("#newsTypeVal").val(2);
			tmpHtml=("你有<span class=\"newsFrame_content_summary_count\" >"+result+"</span>条未读消息，请及时查阅。");	
		}
	}else{
		$("#newsTypeVal").val(1);
		tmpHtml=("你有<span class=\"newsFrame_content_summary_count\">"+result+"</span>条待办任务，请及时处理。");
	}
	if(tmpHtml.length>0){
		$("#waitToProcessCnt").html(tmpHtml);
		$("#newsFrameDiv").draggable({handle : ".newsFrame_title", containment : "body"});
		$("#newsFrameDiv").show();
		
		newsDialog.hideAfterSomeSecond(5000,2000);//默认4秒钟隐藏消息框
	}
	
	$("#newsFrameDiv").mouseenter(function(){//鼠标移到消息框时，消息框不隐藏
		newsDialog.setDivShow(); 
	});
	
	$("#newsFrameDiv").mouseleave(function(){//鼠标离开消息框时，消息框隐藏
		newsDialog.hideAfterSomeSecond(2000,1000);//2秒钟隐藏消息框
	});
},
/**
 * 展示及时推送消息
 * @param concent 消息内容
 * @param taskObj 任务对象，方便处理跳转的功能
 */
showPushNews:function(taskObj){
	//alert($("#waitToProcessCnt").attr("id"));
	//$("#waitToProcessCnt").text("");
	$("#waitToProcessCnt").html("");
	$("#toContentUrl").val("");
	var tmpContent=taskObj.pasContent;
	if(tmpContent.indexOf("<")>-1){
		
	}else{
		if(tmpContent.length>50){
			tmpContent=tmpContent.substring(0,50)+"....";
		}
	}
	$("#waitToProcessCnt").html(tmpContent);
	$("#toContentUrl").val(taskObj.pasUrl);
	$("#taskId").val(taskObj.pasNo);
	if(taskObj.pasUrl==""){
		$("#newsTypeVal").val(3);//消息明细
	}else{
		$("#newsTypeVal").val(4);//代办明细
	}
	//alert($("#newsFrameDiv").css("display"));
	$("#newsFrameDiv").show();
	//alert($("#newsFrameDiv").css("display"));
	//同步更新待办任务的条数
	if($("#task_count").length>0){
		var curCount = $("#count_input").val();
			curCount = curCount*1+1;
		if(curCount<101){
			if(curCount==100){
				$("#task_count").text("99+");
			}else{
				$("#task_count").text(curCount);
			}
		}
        if(curCount<0){
            curCount=0;
            $("#task_count").text("");
        }
		$("#count_input").val(curCount);
	}else{
		htmlStr = "<span class='badge task-count' id='task_count' style='width:18px;'>1</span><input type=hidden id='count_input' value='1'/>";
 		$("#messagePage").append(htmlStr);
	}
	
	
	
	//alert($("#waitToProcessCnt",window.top.document).attr("id"));
	newsDialog.hideAfterSomeSecond(4000, 1500);//默认4秒钟隐藏消息框
},
    /**
     * 展示及时推送消息
     * @param concent 消息内容
     * @param taskObj 任务对象，方便处理跳转的功能
     */
    showInnerMessage:function(taskObj){
        //alert($("#waitToProcessCnt").attr("id"));
        //$("#waitToProcessCnt").text("");
        $("#waitToProcessCnt").html("");
        $("#toContentUrl").val("");
        var tmpContent=taskObj.content;
        if(tmpContent.indexOf("<")>-1){

        }else{
            if(tmpContent.length>50){
                tmpContent=tmpContent.substring(0,50)+"....";
            }
        }
        $("#waitToProcessCnt").html(tmpContent);
        //$("#toContentUrl").val(taskObj.pasUrl);
        $("#taskId").val(taskObj.pasNo);
        if(taskObj.pasUrl==""){
            $("#newsTypeVal").val(3);//消息明细
        }else{
            $("#newsTypeVal").val(4);//代办明细
        }
        //alert($("#newsFrameDiv").css("display"));
        $("#newsFrameDiv").show();
        //alert($("#newsFrameDiv").css("display"));
        //同步更新待办任务的条数
        if($("#task_count").length>0){
            var curCount = $("#count_input").val();
            curCount = curCount*1+1;
            if(curCount<101){
                if(curCount==100){
                    $("#task_count").text("99+");
                }else{
                    $("#task_count").text(curCount);
                }
            }
            if(curCount<0){
                curCount=0;
                $("#task_count").text("");
            }
            $("#count_input").val(curCount);
        }else{
            htmlStr = "<span class='badge task-count' id='task_count' style='width:18px;'>1</span><input type=hidden id='count_input' value='1'/>";
            $("#messagePage").append(htmlStr);
        }



        //alert($("#waitToProcessCnt",window.top.document).attr("id"));
        newsDialog.hideAfterSomeSecond(4000, 1500);//默认4秒钟隐藏消息框
    },
/**
 * 消息页面初始化
 * newsCount -消息条数
 * ifNext -是否需要展示下一条
 * @param newsCount
 */
newsPageInit:function (newsCount,ifNext){
	if(newsCount<=1){
		$(".formRowCenter").find("input[type='button']").prop("disabled",true);
		$(".formRowCenter").find("input[type='button']").addClass("formRowCenter_undo");//修改灰色不可点击样式
	}else{
		$(".formRowCenter").find("input[type='button']").prop("disabled",false);
	}
	if(ifNext==1){
		$(".formRowCenter").show();
	}
},
toNextNews:function (type){
	var nextTaskId=$("#nextTaskId").val();
	if(nextTaskId!=""){
		$.ajax({
			url : webPath+"/sysTaskInfo/getNextNewsDetailAjax",
			data : {
				"taskId" : nextTaskId
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				if(data.flag=="success"){
					$("input[name='regName']").val(data.newsObj.regName);
					$("input[name='createDate']").val(data.newsObj.createDate);
					$("textarea[name='pasContent']").val(data.newsObj.pasContent);
					$("#nextTaskId").val(data.nextTaskId);
					var totalCnt=data.newsCnt;
					newsDialog.newsPageInit(totalCnt*1);
					if(type=='1'){//未读
						$("#taskUnReadCount",window.top.frames[0].document).html(totalCnt);//iframe赋值
					}
				}
			},
			error : function() {
				LoadingAnimate.stop();
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				
			}
		});
		
	}
},

toWaitToListPge:function(){
	var newtype=$("#newsTypeVal").val();
	var toContentUrl=$("#toContentUrl").val();//跳转路径
	
	if(toContentUrl.length>0){
		window.top.createTaskShowDialog(webPath+toContentUrl,"消息查看","95","95");
		
	}else{
		var taskId=$("#taskId").val();
		
		
		if(newtype==1){
			//跳转到代办列表
			$("iframe[name='iframepage']").attr("src",webPath+"/sysTaskInfo/getListPage");
		}
		if(newtype==2){
			//跳转到消息列表
			$("iframe[name='iframepage']").attr("src",webPath+"/sysUser/getPersonalCenterInfo?opNo="+regNo);
		}
		if(newtype==3){
			//跳转到消息列表
			window.top.createTaskShowDialog(webPath+"/sysTaskInfo/getNewsDetail?ifNext=1&taskId="+taskId,"消息查看","80","50");
			
		}
	}
}
};  
/**
 * 渐变隐藏消息框
 * @returns
 */
function  setDivHideSlow(){
	$("#newsFrameDiv").animate({opacity:"0"},2000,function(){
		$("#newsFrameDiv").hide();
	});
}
/**
 * 返回当前操作员的代办条数
 * @returns {Number}
 */
function getWaitToDoCnt(){
	var result=0;
	var url=webPath+"/sysUser/getNewsWarningCntAjax?opNo="+regNo+"&query=query";
	$.ajax({
		type : "POST",
		url : url,
		async : false,
		dataType : "json",
		success : function(data) {
			result=data.count;
		},
		error : function(xmlhq, ts, err) {
		}
	});
	return result;
}
/**
 * 获取短消息未读条数
 * @returns {Number}
 */
function getNotReadNewsCnt(){
	var result=0;
	var url=webPath+"/sysUser/getNewsCnt?opNo="+regNo+"&query=query";
	$.ajax({
		type : "POST",
		url : url,
		async : false,
		dataType : "json",
		success : function(data) {
			result=data.newsCnt;
		},
		error : function(xmlhq, ts, err) {
		}
	});
	return result;
}






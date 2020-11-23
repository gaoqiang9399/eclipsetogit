$(document).ready(function() {
			document.onclick=function(eArgs){
					var e= eArgs? eArgs : window.event;
					var tar = e.srcElement||e.target; 
					var $tar = $(tar);
					var $task = $tar.parents(".task_style").length>0?$tar.parents(".task_style") : $tar;
					if($task.hasClass("task_select")){
						if(this.taskObj!=$task.data("info").pasNo){
							this.taskObj = $task.data("info").pasNo;
							if(typeof(this.taskInfo)!="undefined"){
								taskB.changeTaskSts(this.taskInfo);
							}
						}
						this.taskInfo = $task.data("info");
					}else if($(".task_select").length>0){
						var data = $(".task_select").data("info");
						$(".task_select").removeClass("task_correct").removeClass("task_select").data("open",true).find(".task_contents").animate({height:"25px"},300,function(){
							$(this).parents(".task_style").find(".task_ctrl").slideUp(function(){
								$(this).empty();
								mcSelector.mCustomScrollbar("update");
							});
						});
						taskB.changeTaskSts(data);
					}
				};
				
			});

var IDMark_Switch = "_switch", IDMark_Icon = "_ico",IDMark_Tips = "_tips", IDMark_Span = "_span", IDMark_Input = "_input", IDMark_Check = "_check", IDMark_Edit = "_edit", IDMark_Remove = "_remove", IDMark_Ul = "_ul", IDMark_A = "_a";
var curMenu = null, treeObj = null;
var menu_setting = {
	view : {
		showIcon : false,
		showLine : false,
		selectedMulti : false,
		dblClickExpand : false,
		addDiyDom : menuAddDiyDom
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		onClick : menu_OnClick,
		onNodeCreated: zTreeOnNodeCreated
	}
};
function zTreeOnNodeCreated(event, treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj("menu_tree");
    if(!pasMaxNo){
    	$("#menu_tree_1").addClass("correct");
    }else{
    	treeNode = treeObj.getNodeByParam("id",pasMaxNo,null);
    	$("#"+treeNode.tId).addClass("correct");
    }
    
};

var menu_zNodes = [];
function menuAddDiyDom(treeId, treeNode) {
		var aObj = $("#" + treeNode.tId + IDMark_A);
		var $new = $('<i class="task-new-icon"></i>');
		aObj.append($new);
		var countStr = "<span class='task_count task_max_no_"+treeNode.id+"' id='" + treeNode.tId + IDMark_Tips
		+ "' title='" + treeNode.count + "'>" + treeNode.count + "</span>";
		aObj.append(countStr);
		if(treeNode.count==0||treeNode.count=="0"){
			$('#'+treeNode.tId + IDMark_Tips).hide();
		}
}
function menu_OnClick(event, treeId, treeNode) {
	var jsonData={};
	if(treeNode.id=="pasAware"){
		jsonData.pasMaxNo="";
		jsonData.pasAware="1";
		
	}else{
		jsonData.pasAware="";
		jsonData.pasMaxNo =treeNode.id;
	}
	var dataId = $(".input-select").attr("id");
    jsonData.passType="";//任务完成类型0否决1通过
    if(dataId=="all-task"){
        jsonData.pasSts = "";
    }else if(dataId=="yb-task"){
        jsonData.pasSts = "1";
    }else if(dataId=="wb-task"){
        jsonData.pasSts = "0";
    }else if(dataId=="pass-task"){//已通过
        jsonData.pasSts = "1";
        jsonData.passType="1";//任务完成类型 通过
    }else if(dataId=="veto-task"){//已否决
        jsonData.pasSts = "1";
        jsonData.passType="0";//任务完成类型 否决
    }
			
	taskB.ajaxViewTask(jsonData);
	var aObj = $("#" + treeNode.tId + IDMark_A);
	aObj.find(".task-new-icon").css("display","none");
	$(".level0").removeClass("correct");
	$("#"+treeNode.tId).addClass("correct");
	$(".task-sts").removeClass("alltasks-background");
}
/**
 * taskB
 * options 默认参数
 * initTasks 初始化任务和消息
 * initMsgCount 初始化消息数量
 * createTask 生成任务
 * initEvent 初始化页面元素事件
 * addEvent 新增事件
 * initJsonData 设置查询条件
 * 
 * openIframe 弹出iframe
 * viewTaskMsg 查看消息
 * changeTaskSts 查询任务状态并修改
 */
var taskB = {
	msgCount:0,
	options : {
		url :webPath+"/sysTaskInfo/findByPageToBAjax",
		pageNo : '1',
		sysDate:"",
		jsonData:{pageNo:1}, 
		ajaxData:[],
		pageNo:1,
		nextPageFlag:false
		
	},
	B_task_html : '<div class="task_style" id="">' + '<div class="task_ico">'
			+ '<div class="task_alt"></div>' + '</div>'
			+ '<div class="task_content">' + '<div class="task_name">'
			+ '<span></span>' + '<div class="task_title"></div></div>' + '<div class="time_icon">'
			+ '<span></span>' + '</div>'
			+ '<div class="task_info">'
			+ '<div class="task_contents"></div></div></div>'
			+ '<div class="task_ctrl"></div>' + '<div class="content_edit">'
			+ '<div class="content_btn">'
			+ '<span class="content_pasAware"><i class="i i-xing2"></i></span>'
			+ '<span class="content_pasLock"><i class="i i-openlock"></i></span>'
			+ '<span class="content_btn_ctrl"><i class="i i-editable"></i>操作</span>' + '</div></div></div>',
	No_task_html : '<div class="no_task_style" id="">'+
					'<i class="i i-kulian"></i>'+
					'<span class="no_task_span"><span>'
					+'</div>',
	initTasks : function(elem, data, dic,operationDic) {
		var $this = this;		
		this.$elem = $(elem);		
		$this.dic = dic;
		$this.operationDic = operationDic;
		$this.SysDate = $this.options.SysDate;
		if(data.length==0){
			$this.addSearchEmpty();
		}else{
			$this.createTask(data);
		}				
		$this.initEvent();
		//mcTimeline($('body'));
		mcTimeline($('#task-div')); 
	},
	initJsonData:function(jsonData){
		var dataId=$(".task-sts .input-select").attr("id");
		if(dataId=="all-task"){
			jsonData.pasSts = "";
		}else if(dataId=="yb-task"){
			jsonData.pasSts = "1";
		}else if(dataId=="wb-task"){
			jsonData.pasSts = "0";				
		}else if(dataId=="pass-task"){//已通过
			jsonData.pasSts = "1";	
			jsonData.passType="1";//任务完成类型 通过
		}else if(dataId=="veto-task"){//已否决
			jsonData.pasSts = "1";		
			jsonData.passType="0";//任务完成类型 否决
		}
	},
	initPasAwareCount:function(count){
		if(count==0||count=='0'){
			$("#.task_max_no_pasAware").hide();
		}else{
			$("#.task_max_no_pasAware").html(count).attr("title",count).show();
		}
	},
	makeOneTask : function (i, info) {
		var $this = this;
		var $task = $($this.B_task_html);
		var dic = $this.dic;
		$task.data("info",info);
		$task.attr('id', info.createDate);
		$task.addClass('scrollTo'+info.pasNo);
		$task.addClass("task_max_"+info.pasMaxNo);
		$task.addClass('task_must_' + info.pasIsMust);
		$task.addClass('task_sts_' + info.pasSts);

		if (info.pasAware === "1") {
			$task.find(".content_pasAware i").removeClass("i-xing2").addClass('i-xing');
		}
		if(info.pasMaxNo!='1'){
			$task.find(".content_pasLock").remove();
		}else{
			if (info.pasLock === "1") {
				$task.find(".content_pasLock i").removeClass("i-openlock").addClass('i-locked');
			}
		}
		var operationDic = $this.operationDic;
		if(operationDic){
			var pasOperation;
			var index = info.pasMaxNo;
			if(index == "5"){
				index = "4";
			}
			if(index == "6"){
				index = "5";
			}
			pasOperation = operationDic[index];
			if(pasOperation){
				var optName = pasOperation.optName;
				if(typeof(optName) == "undefined" || optName == "" || optName == null){
					optName = "操作";
				}
				$task.find("span.content_btn_ctrl").html("<i class='i i-editable'></i>"+optName);
			}
		}
		
		var pasSubType = "";
		$.each(dic, function(j, type) {
			if (type.optCode == info.pasMinNo) {
				pasSubType = type.optName;
			}
		});
		$task.find(".task_name span").html("【"+pasSubType+"】");
		if(info.pasMaxNo==0){
			$this.viewTaskMsg($task);
		}
		$task.find(".task_alt").html(pasSubType.subCHStr(0, 2));
		var currDate = formatStringyyyyMMddToDateDesc(info.createDate,$this.SysDate);
		$task.find(".time_icon span").html(currDate +"  "+info.createTime);
		$task.find(".task_title").html(info.pasTitle);
		if(info.pasSts==1&&info.pasMaxNo!=0){
			var remark = info.pasContent?info.pasContent.replace(/\\/g,""):"";
			remark +="<br /><br />";
			remark +=info.pasResult&&info.pasResult!="null"?info.pasResult:"";
			$task.find(".task_contents").html(remark);
		}else{
			$task.find(".task_contents").html(info.pasContent?info.pasContent.replace(/\\/g,""):"");
		}			
		$task.data("open",true);
		$this.addEvent($task, info);
		return $task;
	},
	createTask : function(data) {
		var $this = this;
		var $div = $this.$elem;
		$this.clearElem();
		
		$.each(data, function (i, info) {
			var task = $this.makeOneTask(i, info);
			$div.append(task);
		});
		
		$div.append($('<div class="foot" ></div>'));
		$div.slideDown(function() {
			mcSelector.mCustomScrollbar("update").mCustomScrollbar("scrollTo","top");
			var searchStr = $("#work-zone-text").val();
			if(typeof(searchStr)!="undefined"&&searchStr!=""){
				fHl(document.getElementById('work-zone-tasks'),searchStr,false);
			}
		});
	},
	initEvent : function() {
		var $this = this;
		if(pasMaxNo!=null&&pasMaxNo!=''&&pasMaxNo!=undefined){
			var jsonData_ = $this.options.jsonData;
			jsonData_.pasMaxNo = pasMaxNo;
		}
		$(".task-sts .input-btn").bind("click", function() {
			$(".task-sts .input-btn").removeClass("input-select");
			$(this).addClass("input-select");						
			var jsonData = $this.options.jsonData;
			var dataId= $(this).attr("id");
			jsonData.passType="";//任务完成类型0否决1通过
			if(dataId=="all-task"){
				jsonData.pasSts = "";
			}else if(dataId=="yb-task"){
				jsonData.pasSts = "1";
			}else if(dataId=="wb-task"){
				jsonData.pasSts = "0";				
			}else if(dataId=="pass-task"){//已通过
				jsonData.pasSts = "1";	
				jsonData.passType="1";//任务完成类型 通过
			}else if(dataId=="veto-task"){//已否决
				jsonData.pasSts = "1";		
				jsonData.passType="0";//任务完成类型 否决
			}
			
//			$(".ztree li").removeClass("correct");
			//$('.time-line-dl dd').find('a').removeClass('line-a-on').removeClass('line-second-dot-on');
			//$('.time-line-dl dd').find('span').removeClass('line-dot-on i i-duihao1').removeClass('line-second-dot-on');
			$this.ajaxViewTask(jsonData, [{
				"customQuery" : ""
			}]);
			$(".work-zone-text").val("");
		});
		/*$("#last_info").bind("click", function() {
			
			$(".work-zone-title i").removeClass("task_select_i");
			$(".ztree li").removeClass("correct");
			$('.time-line-dl dd').find('a').removeClass('line-a-on').removeClass('line-second-dot-on');
			$('.time-line-dl dd').find('span').removeClass('line-dot-on i i-duihao1').removeClass('line-second-dot-on');
			$this.ajaxViewTask({
				pasSts : "",
				pasIsMust : "",
				pasMaxNo : "",
				pasMinNo : "",
				createDate : "",
				pasAware : ""
			},[{
				"customQuery" : ""
			}]);
			$('.time-line-dl dd').siblings('dd').find('a').removeClass('line-a-on').removeClass('line-second-dot-on');
			$('.time-line-dl dd').siblings('dd').find('span').removeClass('line-dot-on i i-duihao1').removeClass('line-second-dot-on');
			$(".work-zone-text").val("");
		});*/
		/*$(".work-zone-title ul li").bind("click",function(){			
			if($(this).hasClass("correct")){				
			}else{
				$(".work-zone-title ul li").removeClass("correct");
				$(this).addClass("correct");			
				
			}
			
		})*/
		
		$(".work-zone-search .i").bind("click",function(){
			var val = $(".work-zone-text").val();
			$this.ajaxViewTask({},[{
				"customQuery" : val
			}]);
		});
		$(".work-zone-text").keydown(function(e){
			if(e.keyCode==13){
				var val = $(".work-zone-text").val();
				$this.ajaxViewTask({},[{
					"customQuery" : val
				}]);
			}
		}); 
		$("#pasAware").bind("click", function() {
			$(this).addClass("task_select_i").siblings('.task-sts i').removeClass("task_select_i");
			$this.ajaxViewTask({
				pasAware : 1,
				pasMaxNo : "",
				pasMinNo : ""
			});
			$(".level0").removeClass("correct");
			$(this).addClass("correct");
			$(".task-sts").removeClass("alltasks-background");
		});
	},
	addEvent : function(elem, data) {
		var $this = this;
		var $elem = $(elem);		
		var url = data.pasUrl;
		$elem.bind("click",function(){
			$elem.addClass("task_select").siblings('.task_style').removeClass("task_select").data("open",true).find(".task_contents").animate({height:"25px"},300,function(){
					$(this).parents(".task_style").find(".task_ctrl").slideUp(function(){
						$(this).empty();
						mcSelector.mCustomScrollbar("update");
					});
				});
			//$elem.addClass("task_correct").siblings('.task_style').removeClass("task_correct");
		});
		
		$elem.find(".content_btn .content_pasAware").bind("click", function() {
			
			var $pasAware = $(this);
			if($pasAware.find("i").hasClass("i-xing")){
				$pasAware.find("i").removeClass("i-xing").addClass("i-xing2");
			}else{
				$pasAware.find("i").removeClass("i-xing2").addClass("i-xing");
			}
			
			if(window.event){
		        //e.returnValue=false;//阻止自身行为
		        window.event.cancelBubble=true;//阻止冒泡
		     }else if(arguments.callee.caller.arguments[0].preventDefault){
		        //e.preventDefault();//阻止自身行为
		        arguments.callee.caller.arguments[0].stopPropagation();//阻止冒泡
		     }
			LoadingAnimate.start();
			$.ajax({
				type : "POST",
				url : webPath+"/sysTaskInfo/updataPasAwareStsAjax",
				dataType : "json",
				data:data,
				complete:function(){
					  LoadingAnimate.stop(); 
					},
				success : function(jsonData) {
					var pasAwareCount = jsonData.pasAwareCount;
					if(pasAwareCount==0||pasAwareCount=='0'){
						$(".task_max_no_pasAware").hide();
						var jData = $this.options.jsonData;
						if(jData.pasAware){
							taskB.ajaxViewTask(jData)
						}												
					}else{
						$(".task_max_no_pasAware").html(pasAwareCount).attr("title",pasAwareCount).show();
					}
					//$pasAware.attr("class","content_pasAware content_pasAware_"+jsonData.sysTaskInfo.pasAware);
					/*$pasAware.find("i").attr("class","i i-xing2 content_pasAware content_pasAware_"+jsonData.sysTaskInfo.pasAware);*/

				},
				error : function(xmlhq, ts, err) {
				}
			});
		});
		//锁单
		$elem.find(".content_btn .content_pasLock").bind("click", function() {
			if(window.event){
				//e.returnValue=false;//阻止自身行为
				window.event.cancelBubble=true;//阻止冒泡
			}else if(arguments.callee.caller.arguments[0].preventDefault){
				//e.preventDefault();//阻止自身行为
				arguments.callee.caller.arguments[0].stopPropagation();//阻止冒泡
			}
			var $pasAware = $(this);
			if($pasAware.find("i").hasClass("i-locked")){
				LoadingAnimate.start();
				$.ajax({
					type : "POST",
					url : webPath+"/sysTaskInfo/updataPasLockStsAjax",
					dataType : "json",
					data:data,
					complete:function(){
						LoadingAnimate.stop(); 
					},
					success : function(data) {
						if(data.flag=='success'){
							$pasAware.find("i").removeClass("i-locked").addClass("i-openlock");
						}else{
							alert(data.msg, 1)
						}
					},
					error : function(xmlhq, ts, err) {
					}
				});
			}else{
				alert("是否确认锁单，锁单后仅能本人操作！", 2,function(dt){
					LoadingAnimate.start();
					$.ajax({
						type : "POST",
						url : webPath+"/sysTaskInfo/updataPasLockStsAjax",
						dataType : "json",
						data:data,
						complete:function(){
							LoadingAnimate.stop(); 
						},
						success : function(data) {
							if(data.flag=='success'){
								$pasAware.find("i").removeClass("i-openlock").addClass("i-locked");
							}else{
								alert(data.msg, 0);
							}
						},
						error : function(xmlhq, ts, err) {
						}
					});
				})
			}
		});
		if(data.pasSts==1||data.pasMaxNo==0||data.pasMaxNo==3){
			$elem.find(".content_btn .content_btn_ctrl").remove();
			return false;
		}
		if(data.optType==2){
			var ajaxData = {};
			var urlParam = $this.getUrlParam(url);
			
			var submitUrl = "";
			if(data.pasType==1){
				submitUrl = urlParam.submitUrl+"?taskId="+data.wkfTaskNo+"&appNo="+urlParam.appNo;				
				ajaxData = urlParam;
			}else if(data.pasType==2){
				submitUrl = url;
				ajaxData.formId = data.formId;
			}
			
			//$elem.find(".content_btn .content_btn_ctrl").remove();
			$elem.find(".content_btn .content_btn_ctrl").bind("click", function(){
				if($elem.data("open")){
					    LoadingAnimate.start();
						$.ajax({
							type : "POST",
							url : webPath+"/sysTaskInfo/getFormHtmlAjax",
							dataType : "json",
							data:ajaxData,
							complete:function(){
								  LoadingAnimate.stop(); 
								},
							success : function(jsonData) {
								$elem.find(".task_ctrl").empty().hide(function() {
									var form = $("<form></form>");
									form.append(jsonData.formHtml).append($this.addFormHidden(urlParam,form,data)).append($this.addBtn(data));
									if(form.find("input[name=taskId]").length>0){
										form.find("input[name=taskId]").val(data.wkfTaskNo);
									}									
									$elem.find(".task_ctrl").append(form);
									$this.btnEvent($elem,data,submitUrl);
									$elem.find(".task_ctrl").slideDown(function(){
										mcSelector.mCustomScrollbar("update");
										mcSelector.mCustomScrollbar("scrollTo",$elem);
									});
									$elem.data("open",false);
								});
							},
							error : function(xmlhq, ts, err) {
							}
						});
				}
			});
		}else if(data.optType==1){
			$elem.find(".content_btn .content_btn_ctrl").bind("click", function() {
				var pasLock;
				var jsonDataMsg;
				$.ajax({
					type : "POST",
					url : webPath+"/sysTaskInfo/getByIdAjax",
					dataType : "json",
					data:data,
					async: false,
					complete:function(){
						  LoadingAnimate.stop(); 
					},
					success : function(jsonData) {
						if(jsonData.flag=="success"){
							pasLock="0";
						}else{
							jsonDataMsg = jsonData.msg;
							pasLock="1";
						}
					},
					error : function(xmlhq, ts, err) {
					}
				});
				if(pasLock=="1"){
					alert(jsonDataMsg, 3,function(){
						window.location.href = window.location.href;
					});
					return;
				}
				var url = data.pasUrl;
				if(url.substr(0,1)=='/'){
					url = webPath+url;	
				}else{
					url = webPath+"/"+url;
				}
				
				if(data.pasUrl.indexOf("taskId")==-1){
					url +="&taskId="+data.wkfTaskNo;
				}
				var pasBigType = "";
				var dic = $this.dic;
				$.each(dic, function(j, type) {
					if (type.optCode == data.pasMinNo) {
						pasBigType = type.optName;
					}
				});
				if(data.pasMaxNo == '6'){//公告时,需要修改查看状态
					$.ajax({
						type : "POST",
						url : webPath+"/sysTaskInfo/updataMsgAjax",
						dataType : "json",
						data:data,
						complete:function(){
							  LoadingAnimate.stop(); 
						},
						success : function(jsonData) {
						},
						error : function(xmlhq, ts, err) {
						}
					});
					var $obj = $(this);
					window.parent.openBigForm(url,"通知公告",function(){
						$obj.parent().parent().parent().remove();//删除通知公告行
					});
				}else{
					if(data.pasMaxNo == '1'){//业务审批全屏展示
                        window.top.createTaskShowDialog(url,pasBigType,"100","100",$elem.data("info"));
					} else {
                        window.top.createTaskShowDialog(url,pasBigType,"90","90",$elem.data("info"));
					}

				}
			});
		}
	},
	addFormHidden:function(param,elem,data){
		var $elem = $(elem);
		var html = "";
		$.each(param,function(key,val){
			if($elem.find("input[name="+key+"]").length>0){
				$elem.find("input[name="+key+"]").val(val);
			}else{
				html+='<input type="hidden" value="'+val+'" name="'+key+'">';
			}
		});
		return html;
	},
	getUrlParam:function(url){
		var paramObj = {};
		if(typeof(url)!="undefined"&&url.indexOf("?") != -1){
			var str = url.substring(url.indexOf("?")+1,url.length).split("&");
			for(var x in str){
				var obj = str[x].split("=");
				paramObj[obj[0]]=obj[1];
			}
		}
		return paramObj;
	},
	btnEvent:function(elem,info,submitUrl){
		var $this = this;
		var $elem = $(elem);
		var $btns = $elem.find(".task_ctrl_div");
		$btns.find(".cancel").bind("click",function(){
			$elem.data("open",true);
			$elem.removeClass("task_select").find(".task_ctrl").slideUp(
						function(){
							$(this).empty();
							mcSelector.mCustomScrollbar("update").mCustomScrollbar("scrollTo",$elem);
						});
			 if(window.event){
			        //e.returnValue=false;//阻止自身行为
			        window.event.cancelBubble=true;//阻止冒泡
			     }else if(arguments.callee.caller.arguments[0].preventDefault){
			        //e.preventDefault();//阻止自身行为
			        arguments.callee.caller.arguments[0].stopPropagation();//阻止冒泡
			     }
			$elem.find(".task_ctrl").slideUp(function(){
				mcSelector.mCustomScrollbar("update");
				$elem.find(".task_ctrl").empty();
			});
		});
		$btns.find(".task_submit").bind("click",function(){
			var $obj = $(this).parents("form");
			var dataParam = JSON.stringify($obj.serializeArray());
			
			var appNo=$('[name=appNo]').val();
			var Url=$('[name=submitUrl]').val();
			var taskId = $('[name=taskId]').val();
			var isAssignNext = $('[name=isAssignNext]').val();
			var activityType = $('[name=activityType]').val();
			var formId = $('[name=formId]').val();
			var flag=false;
			var opinionType= $('input:radio[name="opinionType"]:checked').val();
			if(opinionType){
				flag=true;
			}
			if(flag){
				commitProcessForOption(appNo,submitUrl,formId,'applySP',taskId,isAssignNext,opinionType,activityType,dataParam);
			}
			/*if(flag){
				jQuery.ajax({
					url:submitUrl,
					data:{ajaxData:dataParam},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						if(data.flag == "success"){
							$this.changeTaskSts(info);
							window.top.alert(data.msg,3);
							$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src","<%=webPath%>/factor/SysTaskInfo/getListPage?entranceNo=1");
							$(top.window.document).find("#taskShowDialog").remove();
							// alert(top.getMessage("SUCCEED_OPERATION"));
						}else if(data.flag=="error"){
							if(data.flag!==undefined&&data.flag!=null&&data.flag!=""){
								alert(data.msg,0);
							}else{
								alert(top.getMessage("FAILED_OPERATION"," "),0);
							}
						}
					},error:function(data){
						 alert(top.getMessage("FAILED_OPERATION"," "),0);						 
					}
				});
			}else{
				window.top.alert(top.getMessage("NOT_APPROVAL_TYPE_EMPTY"),3);
			}*/
			
		});
	},
	addBtn:function(data){
		var $html = $("<div class='task_ctrl_div'></div>");
		$html.append('<input class="task_ctrl_btn task_submit" type="button" value="提交"/>');
		$html.append('<input class="task_ctrl_btn cancel" type="button" value="取消"/>');
		return $html;
	},
	openIframe:function(elem,data){
		
	},
	removeTask : function(elem) {

	},
	viewTaskMsg : function(elem){
		var info = $(elem).data("info");
		$(elem).data("view",true);
		$(elem).bind("click",function(){
			if($(elem).data("view")&&info.pasSts!="1"){
				$.ajax({
					type : "POST",
					url : webPath+"/sysTaskInfo/updataMsgAjax",
					dataType : "json",
					data : {
						pasNo : info.pasNo
					},
					success : function(jsonData) {
					},
					error : function(xmlhq, ts, err) {
					}
				});
			}
		});
	},
	changeTask:function(elem,info){
		var $this = this;
		var $task = $(elem);
		$task.unbind();
		$this.addEvent($task, info);
	},
	changeTaskSts : function(data) {
		if(data==undefined){
			return false;
		}
		var $this = this;
		if(data.pasSts=="1"){
			return false;
		}
		
		$.ajax({
			type : "POST",
			url : webPath+"/sysTaskInfo/getByIdAjax",
			dataType : "json",
			data : {
				pasNo : data.pasNo
			},
			success : function(jsonData) {
				//$this.closeTask(jsonData);
				var info = jsonData.info;
				if(info.pasMaxNo != "0"){
					var iframeId = window.parent.a_iframe.id;
					if(iframeId!=null&&iframeId!=''&&iframeId!=undefined){
                        window.parent.a_iframe.contentWindow.taskB.closeTask(jsonData);
					}else{
						window.parent.b2_iframe.closeTask(jsonData);
					}
				}
			},
			error : function(xmlhq, ts, err) {
			}
		});
	},
	closeTask:function(jsonData){
		var $this = this;
		var list = jsonData.list;
		var info = jsonData.info;
		var pasAwareCount = jsonData.pasAwareCount;
		if(pasAwareCount==0||pasAwareCount=='0'){
			$("#pasAware .task_count").hide();
		}else{
			$("#pasAware .task_count").html(pasAwareCount).attr("title",pasAwareCount).show();
		}
		if(info.pasSts==1){
			$('.scrollTo'+info.pasNo).removeClass("task_sts_0").addClass("task_sts_1");
			$('.scrollTo'+info.pasNo).find(".content_btn .content_btn_ctrl").remove();
			var pasResult = info.pasContent.replace(/\\/g,"")+"<br /><br />";
			pasResult+=info.pasResult?info.pasResult:"";
			$('.scrollTo'+info.pasNo).find(".task_contents").html(pasResult);
			if(info.optType==2){
				$('.scrollTo'+info.pasNo).find(".task_ctrl").empty();
				$this.changeTask($('.scrollTo'+info.pasNo),info);
			}else{
				$('.scrollTo'+info.pasNo).removeClass("task_correct").removeClass("task_select").data("open",true).find(".task_contents").animate({height:"25px"},300,function(){
					$(this).parents(".task_style").find(".task_ctrl").slideUp(function(){
						$(this).empty();
						mcSelector.mCustomScrollbar("update");
						$this.changeTask($('.scrollTo'+info.pasNo),info);
					});
				});
			}
		}
		var treeObj = $.fn.zTree.getZTreeObj("menu_tree");
		var nodes = treeObj.getNodes();
		$.each(nodes,function(i,node){
			node.count="0";
			$("#"+node.tId + IDMark_Tips).html("0").attr("title","0").hide();
			$.each(list,function(j,obj){
				if(node.optCode==obj.pasMaxNo){
					node.count=obj.pasContent;
					$("#"+node.tId + IDMark_Tips).html(obj.pasContent).attr("title",obj.pasContent).show();
				}
			});
		});
	},
	clearElem : function(elem) {
		var $this = this;
		$this.$elem.empty();
	},
	addTask : function(data) {
		var $this = this;
		var $div = $this.$elem;
		var dic = $this.dic;
		var operationDic = $this.operationDic;
		$div.find(".foot").remove();

		$.each(data, function (i, info) {
			var task = $this.makeOneTask(i, info);
			$div.append(task);
		});
		
		$div.append($('<div class="foot" ></div>'));
		var searchStr = $("#work-zone-text").val();
		if(typeof(searchStr)!="undefined"&&searchStr!=""){
			fHl(document.getElementById('work-zone-tasks'),searchStr,false);
		}
		mcSelector.mCustomScrollbar("update");
	},
	addMsg : function(info,mian) {
		var $this = this;
		var $div = $this.$elem;
		var dic = $this.dic;
		var $task = $($this.B_task_html);
		$task.data("info",info);
		$task.attr('id', info.createDate);
		$task.addClass('scrollTo'+info.pasNo);
		$task.addClass("task_max_"+info.pasMaxNo);
		$task.addClass('task_must_' + info.pasIsMust);
		$task.addClass('task_sts_' + info.pasSts);
		$task.find(".content_pasAware").addClass('content_pasAware_' + info.pasAware);
		var pasSubType = "";
		$.each(dic, function(j, type) {
			
			if (type.optCode == info.pasMinNo) {
				pasSubType = type.optName;
			}
		});
		$task.find(".task_name span").html("【"+pasSubType+"】");
		if(info.pasMaxNo==0){
			$this.viewTaskMsg($task);
		}
		$task.find(".task_alt").html(pasSubType.subCHStr(0, 2));
		var currDate = formatStringyyyyMMddToDateDesc(info.createDate,$this.SysDate);
		$task.find(".time_icon span").html(currDate +"  "+info.createTime);
		$task.find(".task_title").html(info.pasTitle);
		if(info.pasSts==1&&info.pasMaxNo!=3){
			var remark = info.pasContent?info.pasContent.replace(/\\/g,""):"";
			remark +="<br /><br />";
			remark +=info.pasResult&&info.pasResult!="null"?info.pasResult:"";
			$task.find(".task_contents").html(remark);
		}else{
			$task.find(".task_contents").html(info.pasContent?info.pasContent.replace(/\\/g,""):"");
		}
		$task.css({height:'0px',minHeight:'0px'});
		$div.prepend($task);
		$(".task_max_no_"+info.pasMaxNo).html(Number($(".task_max_no_"+info.pasMaxNo).html())+1);
		if(typeof(mian)=="undefined"||mian=="B"){
			$(".task_max_no_"+info.pasMaxNo).parent().find(".task-new-icon").css("display","block");
		}
		$task.data("open",true);
		$this.addEvent($task, info);
		var searchStr = $("#work-zone-text").val();
		if(typeof(searchStr)!="undefined"&&searchStr!=""){
			fHl(document.getElementById('work-zone-tasks'),searchStr,false);
		}
		$task.animate({height:'92px'},500,function(){
			mcSelector.mCustomScrollbar("update");
			$task.removeAttr("style");
		});
	},
	addFootMsg : function(msg) {
		var $this = this;
		var $elem = $this.$elem;
		var $foot = $elem.find(".foot");
		$foot.html(msg);
	},
	nextPage:function(){
		var $this = this;
		if($this.options.nextPageFlag){
			$this.addFootMsg("<span>没有更多任务！</span>");
			return false;
		}
		var url = this.options.url;
		var jData = $this.options.jsonData;
		var aData = $this.options.ajaxData;
		$this.options.pageNo = Number($this.options.pageNo)+1;
		jData.pageNo = $this.options.pageNo;
		//封装查询参数
		$this.initJsonData(jData);
		$.ajax({
			type : "POST",
			url : url,
			dataType : "json",
			data : {
				ajaxData : JSON.stringify(aData),
				jsonData : JSON.stringify(jData)
			},
			async:false, 
			success : function(jsonData) {
				if(jsonData.ipage.result.length>0){
					$this.addTask(jsonData.ipage.result);
				}else{
					$this.options.nextPageFlag = true;
					$this.addFootMsg("<span>没有更多任务！</span>");
				}
			},
			error : function(xmlhq, ts, err) {
			}
		});
	},
	ajaxViewTask : function(jsonData, ajaxData) {
		var $this = this;
		var $div = $this.$elem;
		var url = this.options.url;
		$this.options.nextPageFlag = false;
		$this.options.pageNo=1;
		$this.options.jsonData.pageNo=1;		
		var jData = $this.options.jsonData = $.extend({}, $this.options.jsonData, $.isPlainObject(jsonData) && jsonData);
		var aData = $this.options.ajaxData = $.extend([], $this.options.ajaxData, ajaxData);
		
		$div.slideUp(function() {
			LoadingAnimate.start();
			$.ajax({
				type : "POST",
				url : url,
				dataType : "json",
				complete:function(){
					  LoadingAnimate.stop(); 
					},
				data : {
					ajaxData : JSON.stringify(aData),
					jsonData : JSON.stringify(jData)
				},
				success : function(data) {
					var list = data.list;
					var pasAwareCount = data.pasAwareCount;	
					if(jData.pasAware!=null && jData.pasAware!=""){
						pasAwareCount =data.ipage.endNum;
					}
					if(pasAwareCount==0||pasAwareCount=='0'){
						$(".task_max_no_pasAware").hide();
					}else{						
						$(".task_max_no_pasAware").html(pasAwareCount).attr("title",pasAwareCount).show();
					}
					
					var treeObj = $.fn.zTree.getZTreeObj("menu_tree");
					var nodes = treeObj.getNodes();
					$.each(nodes,function(i,node){
						if(node.id=="pasAware"){
							return false;
						}else{
							node.count="0";
						}
						
						$("#"+node.tId + IDMark_Tips).html("0").attr("title","0").hide();
						$.each(list,function(j,obj){
							if(node.optCode==obj.pasMaxNo){
								node.count=obj.pasContent;
								$("#"+node.tId + IDMark_Tips).html(obj.pasContent).attr("title",obj.pasContent).show();
							}
						});
					});
					if(data.ipage.result.length==0){
						$this.addSearchEmpty();
					}else{
						taskB.createTask(data.ipage.result);
					};
				},
				error : function(xmlhq, ts, err) {
				}
			});
		});
	},
	addSearchEmpty : function(){
		var $this = this;
		var $div = $this.$elem;
		$div.empty();
		var $html = $($this.No_task_html);
		var str = "找不到您查询的";
		if(typeof($this.options.ajaxData[0])!="undefined"&&$this.options.ajaxData[0].customQuery!=""){
			str+="“"+$this.options.ajaxData[0].customQuery+"”";
		}
		str+="相关信息!";
		$html.find(".no_task_span").html(str);
		$div.append($html);
		$div.show();
		mcSelector.mCustomScrollbar("update");
	}
};

// js扩展，截取汉字
String.prototype.strLen = function() {
	var len = 0;
	for ( var i = 0; i < this.length; i++) {
		if (this.charCodeAt(i) > 255 || this.charCodeAt(i) < 0)
			len += 2;
		else
			len++;
	}
	return len;
};
// 将字符串拆成字符，并存到数组中
String.prototype.strToChars = function() {
	var chars = new Array();
	for ( var i = 0; i < this.length; i++) {
		chars[i] = [this.substr(i, 1), this.isCHS(i)];
	}
	String.prototype.charsArray = chars;
	return chars;
};
// 判断某个字符是否是汉字
String.prototype.isCHS = function(i) {
	if (this.charCodeAt(i) > 255 || this.charCodeAt(i) < 0)
		return true;
	else
		return false;
};
// 截取字符串（从start字节到end字节）
String.prototype.subCHString = function(start, end) {
	var len = 0;
	var str = "";
	this.strToChars();
	for ( var i = 0; i < this.length; i++) {
		if (this.charsArray[i][1])
			len += 2;
		else
			len++;
		if (end < len)
			return str;
		else if (start < len)
			str += this.charsArray[i][0];
	}
	return str;
};
// 截取字符串（从start字节截取length个字节）
String.prototype.subCHStr = function(start, length) {
	return this.subCHString(start, start + length);
};

// 时间轴
var mcSelector;
var mcTimeline = function(mc) {
	mcSelector = mc;
	mcSelector.mCustomScrollbar({
		scrollButtons : {
			autoHideScrollbar : true
		},
		advanced : {
			autoExpandHorizontalScroll : true,
			updateOnBrowserResize : true,
			updateOnContentResize : false,
			autoScrollOnFocus : true
		},
		autoDraggerLength : true,
		callbacks : {
			onScroll : function() {
			},
			whileScrolling : function() {
			},
			onTotalScroll : function() {
				taskB.addFootMsg(' <div class="sk-double-bounce"><div class="sk-child sk-double-bounce1"></div><div class="sk-child sk-double-bounce2"></div></div>');
				setTimeout("taskB.nextPage()",1000);
			},
			onTotalScrollOffset:40,
			alwaysTriggerOffsets : false
		}
	});
};
var sysDate = "";

function createTimeLine1(timeArr) {
	var ddArr = [];
	//var len = ['1小时内-h1','2小时内-h2','3小时内-h3','5小时内-h5','今天-d0','三天内-d3','一周内-d7','两周内-d14','一个月内-m1','三个月内-m3','半年内-m6'];
	var len = timeArr;
	for(var i = 0;i<len.length;i++){
		var ddHtml;
		var parm = len[i].split("-");
		ddHtml = '<dd class="time-line-dd time-line-point" data-dit="' + parm[1] + '">'
					+ '<span class="time-line-dot"><em></em></span>'
					+ '<a data-dit="' + parm[1]
					+ '" class="time-line-a">'+ parm[0] 
					+ '</a>' + '</dd>';
		ddArr.push(ddHtml);
	}
	//根据查询条件个数计算并设置时间轴样式
	$(".time-line-line").css({"height":(len.length+1)*49});
	$(".time-line-border2").css({"top":(len.length+2)*49});
	$(".time-line-border2").show();
	
	$('.time-line-dl').html(ddArr.join(''));
	$('.time-line-bg').delegate('.time-line-point', 'click', function(evt) {
		if($(this).hasClass("time-line-point-select")){
			return false;
		}else{
			$('.time-line-bg').find('.time-line-point-select').removeClass('time-line-point-select');
			$(this).addClass('time-line-point-select');
			$('.time-line-bg').find("*").removeClass('line-a-on').removeClass('line-dot-on i i-duihao1');
			$(this).find('a').addClass('line-a-on');
			$(this).find('span').addClass('line-dot-on i i-duihao1');
			var jsonData={};
				var dataId = $(this).data('dit');
				jsonData.timeParm=dataId;
				/*if(dataId == "all"){
					jsonData.createDate="";	
					jsonData.createTime="";
				}else{
					var d=new Date();
					if(dataId.indexOf("d")!=-1){//按天查询
						dataId=dataId.replace("d","");
						d.setDate("0"+d.getDate()-parseInt(dataId));
						
					}else if(dataId.indexOf("m")!=-1){//按月查询
						dataId=dataId.replace("m","");
						d.setMonth(d.getMonth()-parseInt(dataId));				
					}else if(dataId.indexOf("s")!=-1){
						
						d.setMonth(d.getMonth()-6);
					}else if(dataId.indexOf("h")!=-1){//按小时查询
						var tempdataId=dataId.replace("h","");
						var hour=d.getHours()-parseInt(tempdataId);
						if(hour*1<10){
							hour = '0'+hour;
						}
						var min = d.getMinutes();
						var second = d.getSeconds();
						if(min*1<10){
							min = '0'+d.getMinutes();
						}
						if(second*1<10){
							second = '0'+ d.getSeconds();
						}
						var queryTime=hour+":"+min+":"+second;
						jsonData.createTime=">='"+queryTime+"'";
					}else if(dataId.indexOf("f")!=-1){//按分钟查询
						//默认处理分钟小于60的情况
						var tempDataId=dataId.replace("f","");
						var hour=d.getHours();
						var min = d.getMinutes();
						if(min>=tempDataId){//查询分钟数值小于当前分钟数值时，直接相减
							min=min-tempDataId;
						}else if(min<tempDataId){//查询分钟数值大于当前分钟数值时，小时减一
							hour=hour-1;
							min=60-(tempDataId-min);
						}
						if(min*1<10){
							min = '0'+min;
						}
						
						if(hour*1<10){
							hour = '0'+hour;
						}
						var second = d.getSeconds();
						if(second*1<10){
							second = '0'+ d.getSeconds();
						}
						var queryTime=hour+":"+min+":"+second;
						jsonData.createTime=">='"+queryTime+"'";
					}
					var dataTime=d.Format("yyyyMMdd").toLocaleString();
					if (dataId){
						
						if(dataId.indexOf("s")!=-1){
							jsonData.createDate ="<"+dataTime;
						}else{
							jsonData.createDate =">="+dataTime;
						}
					}else{
						jsonData.createDate="";	
						jsonData.createTime="";
					}  
				}
				//如果不是按照小时查询时，时间设置为空
				if(dataId.indexOf("h")==-1&&dataId.indexOf("f")==-1){
					jsonData.createTime="";
				}*/
				taskB.ajaxViewTask(jsonData);
		}
	
			
		
	});
	
}

function createTimeLine(nowDate,len) {
	var ddArr = [];
	for(var i = 0;i<len;i++){
		var currDate = getCurrDate(nowDate,i);
		var ddHtml;
			ddHtml = '<dd class="time-line-dd">' + '<a data-dit="'
					+ currDate + '" class="time-line-day ">'
					+ showLocale(currDate) + '</a>'
					+ '<span class="time-line-dot"><em></em></span>'
					+ '<a data-dit="' + currDate
					+ '" class="time-line-a">'
					+ formatStringyyyyMMddToyyyy_MM_dd(currDate)
					+ '</a>' + '</dd>';
		ddArr.push(ddHtml);
	}
	$('.time-line-dl').html(ddArr.join(''));

	$('.time-line-dl').delegate('dd', 'click', function(evt) {
		var index = $('.time-line-dl dd').index($(this));
		$(this).find('a').addClass('line-a-on');
		$(this).find('span').addClass('line-dot-on i i-duihao1');
		$(this).siblings('dd').find('a').removeClass('line-a-on').removeClass('line-second-dot-on');
		$(this).siblings('dd').find('span').removeClass('line-dot-on i i-duihao1').removeClass('line-second-dot-on');
		if($(this).find('a').length>0){
			var ddId = $(this).find('a').data('dit');
			var jsonData = {createDate:"="+ddId};
			taskB.ajaxViewTask(jsonData);
		}else{
			//console.log("more");
		}
	});
	
	
//	$(".time-line-border2").dateRangePicker({
//		//alwaysOpen:true,
//		format:"YYYY-MM-DD",
//		endDate:nowDate,
//		separator: '到',
//		showShortcuts: false,
//		language:'cn'
//	}).bind('datepicker-apply',function(event,obj){
//		var jsonData = {createDate:"BETWEEN "+obj.date1.format("yyyyMMdd") +" AND "+obj.date2.format("yyyyMMdd")};
//		taskB.ajaxViewTask(jsonData);
//		$(".time-line-body").find('dd').find('a').removeClass('line-a-on').removeClass('line-second-dot-on');
//		$(".time-line-body").find('dd').find('span').removeClass('line-dot-on i i-duihao1').removeClass('line-second-dot-on');
//	});
//	$(".time-line-dl b.time-line-more-span").parents(".time-line-dd").dateRangePicker({
//		//alwaysOpen:true,
//		format:"YYYY-MM-DD",
//		endDate:nowDate,
//		separator: '到',
//		showShortcuts: false
//		//language:'en'
//	}).bind('datepicker-apply',function(event,obj){
//		var jsonData = {createDate:"BETWEEN "+obj.date1.format("yyyyMMdd") +" AND "+obj.date2.format("yyyyMMdd")};
//		taskB.ajaxViewTask(jsonData);
//		$(this).find('a').addClass('line-a-on');
//		$(this).find('span').addClass('line-dot-on fa fa-check');
//		$(this).siblings('dd').find('a').removeClass('line-a-on').removeClass('line-second-dot-on');
//		$(this).siblings('dd').find('span').removeClass('line-dot-on fa fa-check').removeClass('line-second-dot-on');
//	});
}
//返回N天前日期
var getCurrDate = function(sysDate,n){
    var now = new Date(sysDate.substring(0, 4),Number(sysDate.substring(4, 6))-1,sysDate.substring(6, 8));
    now.setDate(now.getDate() - n);
    var yy = now.getYear();
	if (yy < 1900) yy = yy + 1900;
	var MM = now.getMonth() + 1;
	if (MM < 10) MM = '0' + MM;
	var dd = now.getDate();
	if (dd < 10) dd = '0' + dd;
    return yy+MM+dd;
};

function formatStringyyyyMMddToyyyy_MM_dd(value) {
	if (value.length == 8) {
		return Number(value.substring(4, 6)) + "月" + value.substring(6, 8)
		+ "日";
	} else if (value.length == 6) {
		return value.substring(0, 4) + "月" + value.substring(4, 6) + "日";
	} else {
		return value + "日";
	}
}
function formatStringyyyyMMddToDateDesc(value,sysDate) {
	var DateDesc = ["今天","昨天"];
	if(value==getCurrDate(sysDate,0)){
		return DateDesc[0];
	}
	if(value==getCurrDate(sysDate,1)){
		return DateDesc[1];
	}
	if (value.length == 8) {
		return Number(value.substring(4, 6)) + "月" + value.substring(6, 8)
		+ "日";
	} else if (value.length == 6) {
		return value.substring(0, 4) + "月" + value.substring(4, 6) + "日";
	} else {
		return value + "日";
	}
}


function showLocale(objD) {
	var strDate = new Date();
	var yy = Number(objD.substring(0, 4));
	var mm = Number(objD.substring(4, 6)) - 1;
	var dd = Number(objD.substring(6, 8));
	strDate.setFullYear(yy, mm, dd);
	var str;
	var ww = strDate.getDay();
	if (ww == 0)
		ww = "周日";
	if (ww == 1)
		ww = "周一";
	if (ww == 2)
		ww = "周二";
	if (ww == 3)
		ww = "周三";
	if (ww == 4)
		ww = "周四";
	if (ww == 5)
		ww = "周五";
	if (ww == 6)
		ww = "周六";
	str = ww;
	return (str);
}

/*
 * 页面高亮
 * */
function fHl(o, flag, rndColor, url) {
	// / <summary>
	// / 使用 javascript HTML DOM 高亮显示页面特定字词.
	// / 实例:
	// / fHl(document.body, '纸伞|她');
	// / 这里的body是指高亮body里面的内容。
	// / fHl(document.body, '希望|愁怨', false, '/');
	// / fHl(document.getElementById('at_main'), '独自|飘过|悠长', true,
	// 'search.asp?keyword=');
	// / 这里的'at_main'是指高亮id='at_main'的div里面的内容。search.asp?keyword=指给关键字加的链接地址，
	// / 我这里加了一个参数，在后面要用到。可以是任意的地址。
	// / </summary>
	// / <param name="o"type="Object">
	// / 对象, 要进行高亮显示的对象.
	// / </param>
	// / <param name="flag"type="String">
	// / 字符串, 要进行高亮的词或多个词, 使用 竖杠(|) 分隔多个词 .
	// / </param>
	// / <param name="rndColor"type="Boolean">
	// / 布尔值, 是否随机显示文字背景色与文字颜色, true 表示随机显示.
	// / </param>
	// / <param name="url"type="String">
	// / URI, 是否对高亮的词添加链接.
	// / </param>
	// / <return></return>
	var bgCor = fgCor = '';
	if (rndColor) {
		bgCor = fRndCor(10, 20);
		fgCor = fRndCor(230, 255);
	} else {
		bgCor = '#3399FF';
		fgCor = '#ffffff';
	}
	var re = new RegExp(flag, 'i');
	for ( var i = 0; i < o.childNodes.length; i++) {
		var o_ = o.childNodes[i];
		var o_p = o_.parentNode;
		if (o_.nodeType == 1) {
			fHl(o_, flag, rndColor, url);
		} else if (o_.nodeType == 3) {
			// if(!(o_p.nodeName=='A')){
			if (o_.data.search(re) == -1)
				continue;
			var temp = fEleA(o_.data, flag);
			o_p.replaceChild(temp, o_);
			// }
		}
	}
	// ------------------------------------------------
	function fEleA(text, flag) {
		var style = ' style="background-color:' + bgCor + ';color:' + fgCor
				+ ';"';
		//font-weight:700;
		var o = document.createElement('span');
		var str = '';
		var re = new RegExp('(' + flag + ')', 'gi');
		if (url) {
			str = text.replace(re, '<a href="' + url + '$1"' + style
					+ '>$1</a>'); // 这里是给关键字加链接，红色的$1是指上面链接地址后的具体参数。
		} else {
			str = text.replace(re, '<span ' + style + '>$1</span>'); // 不加链接时显示
		}
		o.innerHTML = str;
		return o;
	}
	// ------------------------------------------------
	function fRndCor(under, over) {
		if (arguments.length == 1) {
			//var over = under;
            over = under;
			under = 0;
		} else if (arguments.length == 0) {
			//var under = 0;
			//var over = 255;
            under = 0;
            over = 255;
		}
		var r = fRandomBy(under, over).toString(16);
		r = padNum(r, r, 2);
		var g = fRandomBy(under, over).toString(16);
		g = padNum(g, g, 2);
		var b = fRandomBy(under, over).toString(16);
		b = padNum(b, b, 2);
		// defaultStatus=r+' '+g+' '+b
		return '#' + r + g + b;
		function fRandomBy(under, over) {
			switch (arguments.length) {
				case 1 :
					return parseInt(Math.random() * under + 1);
				case 2 :
					return parseInt(Math.random() * (over - under + 1) + under);
				default :
					return 0;
			}
		}
		function padNum(str, num, len) {
			var temp = '';
			for ( var i = 0; i < len; temp += num, i++);
			return temp = (temp += str).substr(temp.length - len);
		}
	}
}
//JS日期时间函数扩展 格式化
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1? o[k]: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};
//临时 函数
function enterKey(){};
function textareaInputCount(obj){};
function showCharsInfo(obj){};
function hideCharsInfo(obj){};


//引导
$(function(){
	//$(".sysTaskHelp").bind("click",domoGuide);
})
function domoGuide() {

	var guide = $.guide({
		actions: [
			{
				element: $('.work-zone-search'),
				content: '<span>通过关键字搜索任务</span>',
				offsetX: 220,
				offsetY: 0
			},
			{
				element: $('#menu_tree'),
				content: '<span>显示不同的任务类型</span>',
				offsetX: 220,
				offsetY: 0,
				beforeFunc: function(g) {
				}
			},
//			{
//				element: $('#pasMsg'),
//				content: '<span>显示系统消息</span>',
//				offsetX: 220,
//				offsetY: 0,
//				//isBeforeFuncExec: true,
//				beforeFunc: function(g) {
//				}
//			},
			{
				element: $('#pasAware'),
				content: '<span>显示当前用户已关注的任务</span>',
				offsetX: 220,
				offsetY: 0,
				//isBeforeFuncExec: true,
				beforeFunc: function(g) {
				}
			},
			{
				element: $('#work-zone-tasks'),
				content: '<span>任务显示区域</span>',
				offsetX: 400,
				offsetY: 200,
				//isBeforeFuncExec: true,
				beforeFunc: function(g) {
				}
			},{
				element: $('.work-zone-timeLine'),
				content: '<span>根据时间轴进行任务筛选</span>',
				offsetX: -200,
				offsetY: 20,
				//isBeforeFuncExec: true,
				beforeFunc: function(g) {
				}
			},{
				element: $('.time-line-border2'),
				content: '<span>通过更多可以定义一个时间段，进行筛选</span>',
				offsetX: -250,
				offsetY: 0,
				//isBeforeFuncExec: true,
				beforeFunc: function(g) {
				}
				
			}
		]
	});

}
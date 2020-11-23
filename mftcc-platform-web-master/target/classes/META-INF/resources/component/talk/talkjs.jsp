<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${webPath}/dwr/engine.js"></script>
<script type="text/javascript" src="${webPath}/dwr/interface/MessagePush.js"></script>

<script language="javascript" type="text/javascript">
	var unRead = 0;
	var unReadList = new Array();
	var allUser = new Array(); //用户列表
	var regNo = '<%=session.getAttribute("regNo")%>';
	var regName = '<%=session.getAttribute("regName") %>';
		
	//显示聊天
	function showTalk() {
		if ($("#iconTalk").attr("class") == "infoIconTalk2") {
			$("#infoMember").hide();
			$("#infoTalk").show();
			$("#infoContent").hide();
			$("#infoContentInput").hide();
			$("div[id^='infoContent']").hide();
			$("#iconTalk").removeClass();
			$("#iconTalk").addClass("infoIconTalk");
			$("#iconMenu").removeClass();
			$("#iconMenu").addClass("infoIconMem"); 
		}
	}
	//显示联系人
	function showMenu() {
		if ($("#iconMenu").attr("class") == "infoIconMem") {
			$("#infoMember").show();
			$("#infoTalk").hide();
			$("#infoContent").hide();
			$("#infoContentInput").hide();
			$("div[id^='infoContent']").hide();
			$("#iconMenu").removeClass();
			$("#iconMenu").addClass("infoIconMem2");
			$("#iconTalk").removeClass();
			$("#iconTalk").addClass("infoIconTalk2");
		}
	}
	//加载所有用户
	function flashUserList(data){
		$(".infoMember_mCS2").children("[class!=infoMemberTop]").remove();
		var brName = "";
		for(var i=0; i<data.length; i++){
			var user = eval("(" + data[i] + ")");
			if (brName != user.brName) {
				//先增加部门
				$(".infoMember_mCS2").append("<div class='fenzu' brNo='"+user.brNo+"'>"+user.brName+"</div>");				
				brName = user.brName;
			}
			//添加部门下的用户
			$(".infoMember_mCS2").append("<ul brNo='"+user.brNo+"'><li><a onclick='showTalking(\""+user.opNo+"\", \""+user.opName+"\")'><i class='memberIcon'></i><h3>"+user.opName+"("+user.opNo+")"+"</h3></a></li></ul>");
		}
	}
	
	//搜索用户
	function searchUserList(data, userName){
		$(".infoMember_mCS2").children("[class!=infoMemberTop]").remove();
		var brName = "";		
		for(var i=0; i<data.length; i++){
			var user = eval("(" + data[i] + ")");
			if (userName.trim().length == 0 || user.opNo.indexOf(userName) > -1 || user.opName.indexOf(userName) > -1) {
				if (brName != user.brName) {
					//先增加部门
					$(".infoMember_mCS2").append("<div class='fenzu' brNo='"+user.brNo+"'>"+user.brName+"</div>");				
					brName = user.brName;
				}
				//添加部门下的用户
				$(".infoMember_mCS2").append("<ul brNo='"+user.brNo+"'><li><a onclick='showTalking(\""+user.opNo+"\", \""+user.opName+"\")'><i class='memberIcon'></i><h3>"+user.opName+"("+user.opNo+")"+"</h3></a></li></ul>");
			}
		}
	}
	
	//显示当前用户与opNo的聊天记录
	function showTalking(opNo, opName) {
		if (opNo != null && opNo != "undefined" && opNo != "null") {
			$("#talkingList").children("li").removeClass();
			if ($("#talkingList li[opNo="+opNo+"]").length > 0) {
				$("#talkingList li[opNo="+opNo+"]").addClass("current");
			} else {
				$("#talkingList").append("<li class='current' opNo='"+opNo+"'><a onclick='currentClick(\""+opNo+"\", \""+opName+"\")'><i class='memberIcon'></i><p><span class='title'>"+opName+"("+opNo+")"+"</span><br><span id='last_"+opNo+"'><span></p><em class='infoTime' id='time_"+opNo+"'></em></a></li>");	
			}		
			showTalk();	
			showContent(opNo, opName);
		}
	}
	
	//单击当前聊天对象
	function currentClick(opNo, opName) {
		showContent(opNo, opName);
		
		setTimeClass(opNo, $("#infoContent_" + opNo + " .infoContentMain .concentTime").html());
		
		//更新消息状态
		MessagePush.updateStatus(opNo, regNo, "1", function() {});
	}
	
	//显示当前用户与opNo的聊天内容窗口
	function showContent(opNo, opName) {
		$("input[name=opNo]").val(opNo);
		$("input[name=opName]").val(opName);
		$("#talkingList").children("li").removeClass();		
		$("#talkingList li[opNo="+opNo+"]").addClass("current");
		
		$("div[id^='infoContent']").hide();
		if ($("#infoContent_" + opNo).length == 0) {
			var $div=$("<div class='infoContent' id='infoContent_"+opNo+"'></div>");			
			$div.html("<h2>"+opName+"</h2><div class='infoContentMain'><p class='hisLink'><a href='javascript:void(0);' onclick='searchHis(this)' lastTime=''>查看历史消息</a></p><span id='concentTime_"+opNo+"'></span>");
			$(".infoMain").append($div);
			//加载消息
		}
		$("#infoContent_" + opNo).show();
		$("#infoContent_" + opNo + " .infoContentMain").scrollTop($("#infoContent_" + opNo + " .infoContentMain").outerHeight(true));
		$("#infoContentInput").show();		
	} 
	
	//发送
	function sendRecord() {
		var targetUser = $("input[name='opNo']").val();
		if(targetUser == null || targetUser == "" || targetUser == "undefined"){
			alert("请选择接受方!");
			return;
		}		
		var $content=$("#messageInput");
		if($content.val()!=""){
			if ($content.val().length > 200) {
				alert("发送内容过长！");
				return false;
			}
			sendContent($content.val(), targetUser + ":" + $("input[name='opName']").val());
		}else{
			alert(top.getMessage("NOT_FORM_EMPTY", "发送内容"));
			return false;
		}
		$content.focus();
	}
	
	//发送消息
	function sendContent(content, targetUser){
		if(content == ""){
			alert(top.getMessage("NOT_FORM_EMPTY", "发送内容"));
			return;
		}
		var opNo = $("input[name='opNo']").val();	
		var sourceUser = regNo+":"+regName;
		MessagePush.sendMessageAuto(targetUser, sourceUser, content, function(data){
			if(data != null && data != "" && data.indexOf("发送失败")!=0){
				//刷新聊天窗口
				fillTalkWindow(regNo,regName,opNo,$("input[name='opName']").val(), data, content);
				$("#messageInput").val(""); 
			} else {
				alert(data);
			}
		});			
	}
	
	//刷新聊天窗口 	
	function fillTalkWindow(sourceNo, sourceName, targetNo, targetName, time, content) {
		if (sourceNo != null && sourceNo != "undefined" && sourceNo != "null") {
			if (targetNo == regNo) {//接收
				if ($("#concentTime_" + sourceNo).attr("class") != "concentTime") {
					$("#concentTime_").attr("class", "concentTime");
				}
				var $div = $("<div class='contentTalk'><i class='memberIcon'></i><div class='memberTalk1'><p>"+sourceName+"</p><span>"+content+"</span><em></em></div></div>");
				$("#infoContent_" + sourceNo + " .infoContentMain").append($div);
				$("#infoContent_" + sourceNo + " .infoContentMain").scrollTop($("#infoContent_" + sourceNo + " .infoContentMain")[0].scrollHeight);	
				$("#last_"+sourceNo).html((content.length <= 8) ? content : content.substring(0, 6) + "...");
				$("#infoContent_" + sourceNo + " .infoContentMain .concentTime").html(time.substring(11, 16));
				if ($("#time_"+sourceNo).attr("class") == "infoTime") {
					setNumClass(sourceNo, 1);
				} else {
					var count = parseInt($("#time_"+sourceNo).html());
					count++;
					setNumClass(sourceNo, count);
				}
			} else {//发送
				if ($("#concentTime_" + targetNo).attr("class") != "concentTime") {
					$("#concentTime_" + targetNo).attr("class", "concentTime");
				}
				var $div = $("<div class='contentTalk2'><i class='memberIcon'></i><div class='memberTalk2'><p>"+sourceName+"</p><span>"+content+"</span><em></em></div></div>");
				$("#infoContent_" + targetNo + " .infoContentMain").append($div);
				$("#infoContent_" + targetNo + " .infoContentMain").scrollTop($("#infoContent_" + targetNo + " .infoContentMain")[0].scrollHeight);	
				$("#last_"+targetNo).html((content.length <= 8) ? content : content.substring(0, 6) + "...");
				setTimeClass(targetNo, time.substring(11, 16));
				$("#infoContent_" + targetNo + " .infoContentMain .concentTime").html(time.substring(11, 16));
			}
		}
	}
	
	//显示推送消息
	function showMessageForTalk(sourceNo, sourceName, targetNo, targetName, time, content){
		showTalking(sourceNo, sourceName);
		fillTalkWindow(sourceNo, sourceName, targetNo, targetName, time, content);
	}
	
	function oninputkeyup(event) {  
		if(event.keyCode == 13){  
			$("#messageInput").val(""); 
		 }   
	}
	function oninputkeydown(event) {  
		if(event.keyCode == 13){   
			sendRecord(); 
		 }   
	}
	
	//设置未读消息样式
	function setNumClass(sourceUserId,count) {
		if ($("#time_"+sourceUserId).length > 0) {
			$("#time_"+sourceUserId).removeClass();
			$("#time_"+sourceUserId).addClass("infoNum");
			$("#time_"+sourceUserId).html(count);
		}
	}
	//设置时间消息样式
	function setTimeClass(sourceUserId, time) {
		if ($("#time_"+sourceUserId).length > 0) {
			$("#time_"+sourceUserId).removeClass();
			$("#time_"+sourceUserId).addClass("infoTime");
			$("#time_"+sourceUserId).html(time);
		}
	}
	
	function showMessageForTalkMsg(msgType,msgCount,autoMessage,username){  
		 window.top.rzzl.showMessages(msgCount,username,msgType,autoMessage); 
		 
		 $(".task_max_no_7", window.parent.document).parent().find(".task-new-icon").css("display","block");
    }
    
    //查看历史消息
    function searchHis(obj) {
    	MessagePush.getHisRecords(regNo, $("input[name='opNo']").val(), $(obj).attr("lastTime"), function(data) {
    		if (data == null || data.length == 0) {
    			$("#infoContent_" + $("input[name='opNo']").val() + " .infoContentMain .hisLink").hide();
    			return;
    		}
    		var orgHeight = $("#infoContent_" + $("input[name='opNo']").val() + " .infoContentMain")[0].scrollHeight;
    		for(var i = 0; i < data.length; i++) {
    			if (data[i].targetUserId == regNo) {//接收
					var $div = $("<div class='contentTalk'><i class='memberIcon'></i><div class='memberTalk1'><p>"+data[i].soruceUserName+data[i].sendTime+"</p><span>"+data[i].recordContent+"</span><em></em></div></div>");
					$("#infoContent_" + data[i].soruceUserId + " .infoContentMain .hisLink").after($div);
				} else {//发送
					var $div = $("<div class='contentTalk2'><i class='memberIcon'></i><div class='memberTalk2'><p>"+data[i].sendTime+data[i].soruceUserName+"</p><span>"+data[i].recordContent+"</span><em></em></div></div>");
					$("#infoContent_" + data[i].targetUserId + " .infoContentMain .hisLink").after($div);					
				}
    		}
    		$(obj).attr("lastTime", data[data.length-1].sendTime);
    		$("#infoContent_" + $("input[name='opNo']").val() + " .infoContentMain").scrollTop($("#infoContent_" + $("input[name='opNo']").val() + " .infoContentMain")[0].scrollHeight - orgHeight);
    	});
    }
	
	window.onload = function(){
		dwr.engine.setActiveReverseAjax(true);
		dwr.engine.setNotifyServerOnPageUnload(true);
		
		//初始化消息推送
		//MessagePush.onPageLoad(regNo,regName);
		
		//构造离线消息
		MessagePush.getUnReadRecords(regNo, function(data) {
			var sourceUserId = "";
			var count = 0;
			for(var i = 0; i < data.length; i++) {
				if (sourceUserId != data[i].soruceUserId) {					
					showTalking(data[i].soruceUserId, data[i].soruceUserName);
					if (sourceUserId != "") {
						setNumClass(sourceUserId, count);
					}
					sourceUserId = data[i].soruceUserId;
					count = 1;
				} else {
					count++;
				}
				fillTalkWindow(data[i].soruceUserId, data[i].soruceUserName, data[i].targetUserId, data[i].targetUserName, data[i].sendTime, data[i].recordContent);
				if (i == data.length - 1) setNumClass(sourceUserId, count);
				$("#infoContent_" + data[i].soruceUserId + " .infoContentMain .hisLink a").attr("lastTime", data[i].sendTime);
				
				if ($("#concentTime_" + data[i].soruceUserId).attr("class") != "concentTime") {
					$("#concentTime_" + data[i].soruceUserId).attr("class", "concentTime");
				}
				$("#infoContent_" + data[i].soruceUserId + " .infoContentMain .concentTime").html(data[i].sendTime.substring(11, 16));
			}
			
			if (data.length > 0) {
				$(".task_max_no_7", window.parent.document).parent().find(".task-new-icon").css("display","block");
			}
		});
		
		//构造通讯录
		MessagePush.getAllUser(function(data){
			allUser = data;
			flashUserList(data);
		});
		
		//聊天图标
		$("#iconTalk").bind("click", function () {
			showTalk();
		});
		
		//联系人图标
		$("#iconMenu").bind("click", function () {
			showMenu();
		});
		
		//发送消息
		$("#btnSendContent").bind("click", function() {
			sendRecord();
		});
		
		$("#messageInput").bind("keydown",function(event){
			oninputkeydown(event);
		});
		$("#messageInput").bind("keyup",function(event){
			oninputkeyup(event);
		});
		
		//搜索
		$(".infoMemberTop input").bind("input propertychange", function() {
			showMenu();
			searchUserList(allUser, $(this).val());
		});		
	}
</script>

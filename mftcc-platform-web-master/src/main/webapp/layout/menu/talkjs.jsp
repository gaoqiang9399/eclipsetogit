<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${webPath}/dwr/engine.js"></script>
<%-- <script type="text/javascript" src="${webPath}/dwr/interface/MessagePush.js"></script> --%>
<%
	Object themeObj = session==null?null:session.getAttribute("color");
	String theme = (themeObj==null||"".equals(themeObj))?"Cred":((String)themeObj);
%>
<script language="javascript" type="text/javascript">
	var unRead = 0;
	var unReadList = new Array();
	var allUser = new Array(); //用户列表
	var tlrno = '<%=session.getAttribute("tlrno")%>';
	var userName = '<%=session.getAttribute("displayname") %>';
	/* dwr.engine.setActiveReverseAjax(true);
	dwr.engine.setNotifyServerOnPageUnload(true); */
	/* MessagePush.onPageLoad(tlrno,userName);
	MessagePush.getUnReadSource(tlrno, function(data){
		if(data != null && data.length > 0){
			unReadList = data;
			$("#newMsgNum").text("你还有"+data.length+"条未读消息!");
			$("#noticeDiv").unbind("click");
			$("#noticeDiv").bind("click",function(){showunread();});
			$(".noticeDiv").show("normal");
		}
	}); */
	function showOnlineUser(userId,userName){
		//alert(userId+"("+userName+")上线!");
		if(tlrno != userId){
			$("ul.chat_list").append("<li title='"+userId+"'><img name='ico' src='../themes/theme_<%=theme%>/images/pp.gif'/>"+userName+"("+userId+")"+"</li>");
		}
		
	}
	
/* 	MessagePush.getAllUser(function(data){
		allUser = data;
		flashUserList(data);
	}); */
	
	function flashUserList(data){
		//if(data != null && data.length > 0){
			$("ul.chat_list").html("");
			for(var i=0; i<data.length; i++){
				var user = data[i];
				var index = user.indexOf(":");
				var userId = user.substring(0, index);
				if(user != null && user.length > 0 && index>0 && tlrno != userId){
					$("ul.chat_list").append("<li title='"+userId+"'><img name='ico' src='../themes/theme_<%=theme%>/images/pp.gif'/>"+user.substring(index+1)+"("+userId+")"+"</li>");
				}
			}
		//}
		$("#sumusers").text(data.length);
	}
	
	function goToBottom(userId){
		var div=$("#"+userId+" > div.talk_body > ul > li").find("div.talkDivShow");
		 div.scrollTop(div[0].scrollHeight);
	}
	
	/**	
	MessagePush.findOnlineUser(function(data){
		if(data != null && data.length > 0){
			$("ul.chat_list").
			for(var i=0; i<data.length; i++){
				var user = data[i];
				var index = user.indexOf(":");
				var userId = user.substring(0, index);
				if(user != null && user.length > 0 && index>0 && tlrno != userId){
					$("ul.chat_list").append("<li title='"+userId+"'><img name='ico' src='../themes/theme_<%=theme%>/images/pp.gif'/>"+user.substring(index+1)+"("+userId+")"+"</li>");
				}
			}
		}
	});
	*/
	
	function showMessage(userId, userName, time, automessage){
		if(userId == tlrno){
			return;
		}
		
		if(!$("#"+userId).get(0)){
			var newDiv = $("#talk").clone();
			newDiv.attr("id",userId);
			$("#talk").after(newDiv);
			newDiv.find("input[name='sendbutton']").bind("click",function(){
				sendMessage(userId);
			});
			newDiv.find("textarea[name='messageInput']").bind("keydown",function(event){
				oninputkeydown(event,userId);
			});
			newDiv.find("textarea[name='messageInput']").bind("keyup",function(event){
				oninputkeyup(event,userId);
			});
		}
		
		if($("#"+userId).find(".talk_body").css("display")!="block"){
			var has = false;
			for(var i=0; i<unReadList.length;i++){
				if(unReadList[i]==(userId+":"+userName)){
					has = true;
				}
			}
			if(!has){
				unReadList.push(userId+":"+userName);
			} 
			var frame = '<%=session.getAttribute("frame")%>';
			if($("#noticeDiv").css("display") == "block" ){
				$("#newMsgNum").text("你还有"+(unReadList.length)+"条未读消息!");
				alert("你还有"+(unReadList.length)+"条未读消息!")
				$("#noticeDiv").unbind("click"); 
				$("#noticeDiv").bind("click",function(){showunread();});
				$(".noticeDiv").show("normal");
			} else {
				if(frame=="view"){
					alert(userName+" 发来新消息@"+automessage);
					rzzl.showMessages(unReadList.length,userId,userName,time, automessage);
				}else{
					alert(userName+" 发来新消息");
					$("#newMsgNum").text(userName+" 发来新消息");
					$("#noticeDiv").unbind("click"); 
					$("#noticeDiv").bind("click",function(){
						showcontent(userId, userName+"("+userId+")");
					});
					$(".noticeDiv").show("normal");
				}
			}
		}
		$("#"+userId+" > div.talk_body > ul > li").find("div.talkDivShow").append("<font color='#006EFE'>" + userName +"("+userId+")" + "&nbsp;&nbsp;" + time + "</font>"+"<br/>");
		$("#"+userId+" > div.talk_body > ul > li").find("div.talkDivShow").append("&nbsp;&nbsp;"+automessage+"<br/>");
		goToBottom(userId);
	}
	
	function oninputkeyup(event,userId) {  
		if(event.keyCode == 13){  
			$("#"+userId).find("textarea[name='messageInput']").val("");  
		 }   
	}
	function oninputkeydown(event,userId) {  
		if(event.keyCode == 13){   
			sendMessage(userId); 
		 }   
	}
	
	function sendMessage(userId){
		var targetUser = $("#"+userId).find("input[name='targetUser']").val();
		if(targetUser == null || targetUser == "" || targetUser == "undefined"){
			alert("请选择接受方!");
			return;
		}
		targetUser = targetUser.replace("(",":");
		targetUser = targetUser.replace(")","");
		var index = targetUser.indexOf(":");
		targetUser = targetUser.substring(index+1)+":" + targetUser.substring(0,index);
		var $content=$("#"+userId).find("textarea[name='messageInput']");
		if($content.val()!=""){
			sendContent($content.val(), targetUser);
		}else{
			alert("发送内容不能为空!");
			return false;
		}
		$content.focus();
	}

	//发送消息
	function sendContent(content, targetUser){
		if(content == ""){
			alert("发送内容不能为空!");
			return;
		}
		var sourceUser = tlrno+":"+userName;
		var userId = targetUser.substring(0,targetUser.indexOf(":"));
		MessagePush.sendMessageAuto(targetUser, sourceUser,content, function(data){
			if(data != null && data != ""){
				$("#"+userId+" > div.talk_body > ul > li").find("div.talkDivShow").append(data);
				goToBottom(userId);
			}
		});
		
		$("#"+userId).find("textarea[name='messageInput']").val("");
		
		
	}
	function showcontent(userId,user){
		$(".noticeDiv").hide("normal");
		var lititle = userId;
		if(!$("#"+lititle).get(0)){
			var newDiv = $("#talk").clone();
			newDiv.attr("id",lititle);
			$("#talk").after(newDiv);
			newDiv.find("input[name='sendbutton']").bind("click",function(){
				sendMessage(userId);
			});
			newDiv.find("textarea[name='messageInput']").bind("keydown",function(event){
				oninputkeydown(event,userId);
			});
			newDiv.find("textarea[name='messageInput']").bind("keyup",function(event){
				oninputkeyup(event,userId);
			});
			
			$.ajax({
    			type:"POST",
    			url:webPath+"/SysTalkRecordActionGetMsg.action",
    			data:"status=0&sourceId="+lititle,
    			success:function(data){
    				if(data != null && data != "" && data != "undefined"){
    					var textar = $("#"+userId+" > div.talk_body > ul > li").find("div.talkDivShow")
    					textar.html(data + textar.html());
    				}
    				goToBottom(lititle);
    			}
    		});
    		
		}
		if(talkStatus==""){
			$("#"+lititle).show("fast");
			$("#"+lititle).find(".talk_body").slideDown();
			$("#"+lititle).find("label").attr("title",user);
			$("#"+lititle).find("label").text(formatStr(user));
			$("#"+lititle).find("input[name='targetUser']").val(user);
			$("#"+lititle).find("textarea[name='messageInput']").focus();
			talkStatus = lititle;
		} else {
			$("#"+talkStatus).hide("fast");
			$("#"+talkStatus).find(".talk_body").slideUp();
			if(talkStatus!=lititle){
				$("#"+lititle).show("fast");
				$("#"+lititle).find(".talk_body").slideDown();
				$("#"+lititle).find("label").attr("title",user);
				$("#"+lititle).find("label").text(formatStr(user));
				$("#"+lititle).find("input[name='targetUser']").val(user);
				$("#"+lititle).find("textarea[name='messageInput']").focus();
				talkStatus = lititle;
			}else {
				talkStatus = "";
			}
		}
		goToBottom(lititle);
	}
	function liclick(userId,user){
		var hasmsg = false;
		for(var i = 0; i < unReadList.length; i++){
			var cuser = unReadList[i];
			if(cuser.substring(0,cuser.indexOf(":")) == userId){
				unReadList.splice(i, 1);
				unReadList.push(cuser);
				hasmsg = true;
				break;
			}
		}
		if(hasmsg){
			if(unReadList.length > 0){
				showunread();
			} else {
				$(".noticeDiv").hide("normal");
			}
		} else if(!hasmsg){
	
			var lititle = userId;
			if(!$("#"+lititle).get(0)){
				var newDiv = $("#talk").clone();
				newDiv.attr("id",lititle);
				$("#talk").after(newDiv);
				newDiv.find("input[name='sendbutton']").bind("click",function(){
					sendMessage(userId);
				});
				newDiv.find("textarea[name='messageInput']").bind("keydown",function(event){
					oninputkeydown(event,userId);
				});
				newDiv.find("textarea[name='messageInput']").bind("keyup",function(event){
					oninputkeyup(event,userId);
				});
				
				$.ajax({
	    			type:"POST",
	    			url:webPath+"/SysTalkRecordActionGetMsg.action",
	    			data:"status=0&sourceId="+lititle,
	    			success:function(data){
	    				if(data != null && data != "" && data != "undefined"){
	    					var textar = $("#"+userId+" > div.talk_body > ul > li").find("div.talkDivShow")
	    					textar.html(data + textar.html());
	    				}
	    				goToBottom(lititle);
	    			}
	    		});
	    		
			}
			if(talkStatus==""){
				$("#"+lititle).show("fast");
				$("#"+lititle).find(".talk_body").slideDown();
				$("#"+lititle).find("label").attr("title",user);
				$("#"+lititle).find("label").text(formatStr(user));
				$("#"+lititle).find("input[name='targetUser']").val(user);
				$("#"+lititle).find("textarea[name='messageInput']").focus();
				talkStatus = lititle;
			} else {
				$("#"+talkStatus).hide("fast");
				$("#"+talkStatus).find(".talk_body").slideUp();
				if(talkStatus!=lititle){
					$("#"+lititle).show("fast");
					$("#"+lititle).find(".talk_body").slideDown();
					$("#"+lititle).find("label").attr("title",user);
					$("#"+lititle).find("label").text(formatStr(user));
					$("#"+lititle).find("input[name='targetUser']").val(user);
					$("#"+lititle).find("textarea[name='messageInput']").focus();
					talkStatus = lititle;
				}else {
					talkStatus = "";
				}
			}
			goToBottom(lititle);
		}
	}
	
	function formatStr(strValue){
		var byteCount = 0; 
		var cs = '';
	    for (var i = 0; i < strValue.length; i++) {
	    	cs = strValue.charAt(i);
	    	var   c = strValue.charCodeAt(i);
	        if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
	            byteCount++; //单字节加1
	        } else {
	            byteCount += 2;
	        }
	        if(byteCount>=20){
	        	break;
	        }
	    }
	    if(byteCount>=20){
		    strValue = strValue.substring(0,strValue.indexOf(cs))+"...";
		}
	    return strValue;
	}
	function showunread(){
		var allUnReadCount = unReadList.length;
		unRead = allUnReadCount - 1 ;
		var user = unReadList[unRead];
		var index = user.indexOf(":");
		var userId = user.substring(0, index);
		var userName = user.substring(index+1);
		if(user != null && user.length > 0 && index>0 && tlrno != userId){
			unReadList=unReadList.slice(0, unRead) ;
			showcontent(userId,userName+"("+userId+")");
		}
		if(unReadList.length > 0){
			$("#newMsgNum").text("你还有"+(unReadList.length)+"条未读消息!");
			$("#noticeDiv").unbind("click"); 
			$("#noticeDiv").bind("click",function(){
				showunread(unReadList[unReadList.length-1]);
			});
			$(".noticeDiv").show("normal");
		} else {
			$(".noticeDiv").hide("normal");
		}
	}
	
	function dosearchuser(){
		var users = new Array();
		var searchText = $("#chat_search").val();
		if(searchText != null && searchText != ""){
			for(var i= 0; i < allUser.length; i++){
				if(allUser[i].indexOf(searchText)>=0){
					users.push(allUser[i]);	
				}
			}
			flashUserList(users);
		} else {
			flashUserList(allUser);
		}
	}
	
	function closeDia(obj){
		$(obj).parent("li").parent("ul").parent("div.talk_body").slideUp("fast");
		$(obj).parent("li").parent("ul").parent("div").parent("div.talk").hide("fast");
		talkStatus = "";
	}
	
	function queryMsg(obj){
		var id;
		if(obj.tagName=="a" || obj.tagName=="A"){
			id = $(obj).parent("div").parent("li").parent("ul").find("input[name='targetUser']").val();
		}else {
			id = $(obj).parent("li").parent("ul").find("input[name='targetUser']").val();
		}
		window.open("${webPath}/SysTalkRecordAction_findHisMsg.action?target="+id,"_blank","");
	}

</script>
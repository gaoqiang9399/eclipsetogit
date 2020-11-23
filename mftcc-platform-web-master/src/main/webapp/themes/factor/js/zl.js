var rzzl;var descFa = "";var menuIndex,isInitMenu,menuType,parmDics,pageInfoData;
(function($) {
	 function main(){
	 	var gsData;//全局搜索数据
	 	var $Box_A;//A页面
	 	var pageNum = 0;
	 	var $this = this;
	 	$.extend(this, {
	 		setPageInfo:function(data,selector){
	 			var container = $(selector);
	 			if(menuType&&menuType=="A"){
		 			container.html(data);
		 			pageInfoData = data;
		 			$("#pageInfoIcon").addClass(descFa);
	 			}else{
	 				container.html("");
		 			$("#pageInfoIcon").attr("class",null);
	 			}
	 		},
	 		skipPage:function(url){
	 			$Box_A.attr("src",url);
	 		},
	 		getBox_A:function(){
	 			return $Box_A;
	 		},
	 		setBox_A:function(selector){
	 			$Box_A = $(selector);
	 		},
	 		setGlobalSearchData:function(data){
	 			gsData = data;
	 		},
	 		getGlobalSearchData:function(){
	 			return gsData;
	 		},
	 		addMenuEvent:function(selector,event){
	 			$(selector).bind(event,function(){
	 				var $li = $(this);
	 				var a = $li.find("a").eq(0);
	 				descFa = $li.find("i").eq(0).attr("class");
	 				$li.parent().find("li").removeClass("menu-active");
	 				$(".middle-center").removeClass("desgin-view");
	 				if(isInitMenu){
	 					if(menuType=="A"){
	 						$li.addClass("menu-active");
	 			 			menuIndex = $li.index();
	 						$this.skipPage(a.attr("href"));
	 					}else{
	 						alert("是否跳转到已存在入口？按“取消”继续进行当前操作！",2,function(){
	 							rzzl.turnAorB(0);
	 						},function(){
	 							$li.addClass("menu-active");
	 				 			menuIndex = $li.index();
	 							rzzl.turnAorB(0);
		 						$this.skipPage(a.attr("href"));
	 						});
	 					}
	 				}else{
	 					$li.addClass("menu-active");
			 			menuIndex = $li.index();
	 					rzzl.turnAorB(0);
	 					$this.skipPage(a.attr("href"));
	 				}
	 				isInitMenu = true;
	 			});
	 		},
	 		removeMenuEvent:function(selector,event){
	 			$(selector).unbind(event);
	 		},
	 		turnAorB:function(flag){
	 			var $d = $(".rotate");
	 			if(flag==null){
	 				if($d.hasClass("turnA")){
	 					flag = 1;
	 					$d.removeClass("turnA");
	 				}else{
	 					flag = 0;
	 					$d.addClass("turnA");
	 				}
	 			}
//	 			if(flag==pageNum) return;
				$d = $(".rotate");
				var $Box = $(".middle-center");
				var $UL = $(".outer-west-center ul");
				if(flag==0){
					$("#pageInfoContent").html(pageInfoData);
		 			$("#pageInfoIcon").addClass(descFa);
					menuType="A";
					$d.addClass("turnA");
					if(typeof(menuIndex)!="undefined"){
						$UL.find("li").eq(menuIndex).addClass("menu-active");
					}
					$(".switchB").hide();
					//$d = $(a).parent();
					$UL.attr("class","menu_A");
//					$(".outer-west-center").css("background-color","#009BED");
//					$d.css("background-color","#CBD4E3");
					$(".outer-west-center").removeClass("toB-center");
					$d.removeClass("toB-switch");
					$(".outer-west-center").addClass("toA-center");
					$d.addClass("toA-switch");
					$d.find("img").eq(0).attr("src","../drag/layout/images/A_B.png");
					//$this.addMenuEvent(".outer-west-center ul li","click");
					//$this.setPageInfo(prev_A.find("a").eq(0).attr("pageType"),"",".pageInfo");
				}else{
					$("#pageInfoContent").html("");
		 			$("#pageInfoIcon").attr("class",null);
					menuType="B";
					$d.removeClass("turnA");
					$(".menu_A li").removeClass("menu-active");
					$(".switchB").show();
					$(".middle-center").removeClass("desgin-view");
					$UL.attr("class","menu_B");
//					$(".outer-west-center").css("background-color","#D7DFEC");
//					$d.css("background-color","#3EAFED");
					$(".outer-west-center").removeClass("toA-center");
					$d.removeClass("toA-switch");
					$(".outer-west-center").addClass("toB-center");
					$d.addClass("toB-switch");
					$d.find("img").eq(0).attr("src","../drag/layout/images/A_B2.png");
					//$this.removeMenuEvent(".outer-west-center ul li","click");
					//$this.setPageInfo("B","B",".pageInfo");
				}
				var $pages = $(".pt-page");
				$pages.attr("class","pt-page");
				$pages.eq(flag).addClass("pt-page-current");
//				var outClass = '', inClass = '',animation=pageNum+"-"+flag;
//				switch (animation){
//					case "0-1"://A-B1
//						outClass = 'pt-page-scaleDown';
//						inClass = 'pt-page-moveFromTop pt-page-ontop pt-page-current';
//						break;
//					case "1-0"://B1-A
//						outClass = 'pt-page-moveToTop';
//						inClass = 'pt-page-scaleUp pt-page-ontop pt-page-current';
//						break;
//					default:
//						break;
//				}
//				$pages.eq(flag).addClass(inClass);
//				$pages.eq(pageNum).addClass(outClass);
//				pageNum = flag;
	 		},
	 		initMenu:function(selector){
	 			$(selector).html("");
	 			isInitMenu = false;
	 			menuType = "B";
	 			var isC = false;
	 			for(var i in viewMenuData){
	 				$(selector).append("<li><i class='i i-"+viewMenuData[i].css+"'></i><a href='"+path+"/"+viewMenuData[i].url+"'pageType='"+viewMenuData[i].css+"' onclick='return false'>"+viewMenuData[i].name+"</a></li>");
	 				if(GetQueryString("entranceNo",viewMenuData[i].url)=="99"){
	 					isC = true;
	 					$this.skipPage(viewMenuData[i].url);
	 				}
	 			}
	 			if(!isC){
	 				$this.skipPage($(selector).find("li a").eq(0).attr("href"));
	 				menuIndex = 0;
	 			}
	 			$this.addMenuEvent(".outer-west-center ul li","click");
	 			$(".outer-west-logo").bind("click",$this.logoClick);
	 			$(".switchB").show();
	 			rzzl.turnAorB(0);
	 			$(".outer-west-center ul li:first").click();
	 		},
	 		logoClick:function(){
	 			$(".rotate2").click();
	 		},
	 		showMessages:function(msgCount,userName,messageType, automessage,taskInfo){
	 			if($(".pt-page[name='A']").hasClass("pt-page-ontop")){
	 				var msgType = "消息",msgContent = userName+" 发来信息 : "+automessage;
		 			if(messageType==1){
		 				msgType = "审批任务";
		 				msgContent = automessage;
		 			}else if(messageType=="2"){
		 				msgType = "催收任务";
		 				msgContent = automessage;
		 			}else if(messageType=="3"){
		 				msgType = "检查任务";
		 				msgContent = automessage;
		 			}
		 			Notification.create(
		 				msgCount,
			            msgType,
			            msgContent,
			            "fadeInLeft",
			            4
			        );
		 			/*var info = {"bizPkNo":"","createDate":"20160420","createTime":"14:49:16","criteriaLists":[],"customQuery":[],"dueDate":"","endDate":"","endNum":0,"formId":"","importLev":"1","isMustReply":"0","optType":"","pasAware":"","pasContent":"系统测试消息，5分钟一次","pasIsMust":"0","pasMaxNo":"3","pasMinNo":"301","pasNo":"1000000466","pasResult":"","pasSts":"0","pasTitle":"","pasUrl":"","startNum":0,"userNo":"0000","wkfTaskNo":""};
		 			if(typeof(document.getElementById("b1_iframe").contentWindow.taskB)!="undefined"){
		 				document.getElementById("b1_iframe").contentWindow.taskB.addMsg(info,"A");
		 			}*/
	 			}else{//B
	 				var info;
	 				if(taskInfo){
	 					info = eval("("+taskInfo+")");
	 					if(typeof(document.getElementById("b1_iframe").contentWindow.taskB)!="undefined"){
	 						document.getElementById("b1_iframe").contentWindow.taskB.addMsg(info,"B");
	 					}
	 				}
	 			}
	 		},initAnimationShow_Hide:function(){
	 			setInterval(function(){
	 				$(".A_Buser").addClass("show_anim");
 				},5200);
	 		},
	 		fsBanner:function(that){
	 			var className;
	 			var $Ag = $(that).find(".Ag").eq(0);
	 			var $Bg = $(that).find(".Bg").eq(0);
	 			if($(that).find(".Ag").eq(0).width()>=50){
	 				className = "Bg";
	 			}else{
	 				className = "Ag";
	 			}
	 			if(className == "Ag"){
	 				$Ag.animate({width:'50px'});
	 				$Ag.find(".A_Bg").animate({opacity:'1'});
	 				$Ag.find(".A_B2").animate({width:'15px',height:'15px',left:"25%"});
	 				$Ag.find(".msgNum").animate({fontSize:'13px',left:"21%",top:"24%",width:'20px',height:'20px',lineHeight:'20px'});
	 				$Bg.animate({width:'20px'});
	 				$Bg.find(".B_Ag").animate({opacity:'0'});
	 				$Bg.find(".B_A2").animate({width:'25px',height:'25px',left:"30%"});
	 				rzzl.turnAorB(0);
	 			}else{
	 				$Bg.animate({width:'50px'});
	 				$Bg.find(".B_Ag").animate({opacity:'1'});
	 				$Bg.find(".B_A2").animate({width:'15px',height:'15px',left:"35%"});
	 				$Ag.animate({width:'20px'});
	 				$Ag.find(".A_Bg").animate({opacity:'0'});
	 				$Ag.find(".A_B2").animate({width:'25px',height:'25px',left:"-15%"});
	 				$Ag.find(".msgNum").animate({fontSize:'10px',left:"0",top:"0",width:'15px',height:'15px',lineHeight:'15px'});
	 				rzzl.turnAorB(1);
	 			}
	 		},
	 		switchB:{
	 			init:function($selector){
	 				var $current = $selector.find(".current").eq(0);
	 				$selector.children().hover(function(){
	 					$(this).parent().css("opacity","1");
	 				},function(){
	 					$(this).parent().css("opacity","0.5");
	 				});
	 				$selector.find(".A").click(function(){
	 					$current.animate({left:$(this).position().left});
	 					rzzl.switchB.turn(0);
	 				});
	 				$selector.find(".B").click(function(){
	 					$current.animate({left:$(this).position().left+10});
	 					rzzl.switchB.turn(1);
	 				});
	 				$selector.find(".A").click();
	 				rzzl.switchB.turn(0);
	 			},
	 			turn:function(cut){
	 				var $selector = $(".pt-page[name='B']");
	 				$selector.css("width",$selector.children().length*100 + "%");
	 				$selector.children().css("width",1/$selector.children().length * 100 + "%");
	 				var curIndex =cut
				      , mlValue = '-' + curIndex * 100 + '%';
	 				$selector.animate({marginLeft: mlValue});
	 			}
	 		}
	 	});
	 }
	 window.RZZL = main;
})(jQuery);

$(function(){
	rzzl = new RZZL();
});
function GetQueryString(name,url){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r;
     if(url==null){
    	 r = window.location.search.substr(1).match(reg);
     }else{
    	 if (url.indexOf("?") != -1) {
    		 url = url.substr(url.indexOf("?"));
    	 }
    	 r = url.substr(1).match(reg);
     }
     if(r!=null)return  unescape(r[2]); return null;
}
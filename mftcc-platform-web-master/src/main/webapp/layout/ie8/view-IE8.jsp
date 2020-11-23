<%@page import="cn.mftcc.util.DateUtil"%>
<%@page import="app.base.User"%>
<%@page import="org.apache.struts2.ServletActionContext"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray" pageEncoding="UTF-8"%>
<%@ page import="app.component.common.SysGlobalParams"%>
<%@ page import="app.component.sys.entity.MfSysCompanyMst"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String layout = "layout/view";
String sysDate = (String)request.getSession().getAttribute("sysDate");
JSONArray calendarDaylist = (JSONArray)request.getSession().getAttribute("calendarDaylist");
String weekDay = DateUtil.getWeekDay(sysDate);
String viewDate = DateUtil.getDiyDate(sysDate, "yyyy年MM月dd日");
String orgName =User.getOrgName(ServletActionContext.getRequest());
String regName =User.getRegName(ServletActionContext.getRequest());
MfSysCompanyMst mfSysCompanyMst=( MfSysCompanyMst) SysGlobalParams.get("COMPANY");
%>
<%-- <jsp:include page="/creditapp/talkjs.jsp"></jsp:include> --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  		<script type="text/javascript">
		var webPath = "${webPath}";
		var servicemanagePath = "${servicemanagePath}";
		var loadingGifPath = "<%=mfSysCompanyMst.getLoadAnimationImg()%>";
		var loadingSmallGifPath = "<%=mfSysCompanyMst.getLoadAnimationSmallImg()%>";
		</script>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=8"/>
		<title>金融业务系统</title>
		<link rel="shortcut icon" type="image/x-icon" href="${webPath}/themes/login/images/favicon.ico" />
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/layout/ie8/js/es5-shim.js"></script>
		<script type="text/javascript" src="${webPath}/layout/ie8/js/es5-sham.js"></script>
		<script type="text/javascript" src="${webPath}/layout/ie8/js/json2.js"></script>
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery.layout.min.js"></script>
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/zl.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery.autocompleter.js"></script>
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/notification.js"></script>
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/fixedDiv.js" ></script>
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/fixedDivFun.js" ></script>
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/moveBack.js" ></script>
		<script type="text/javascript" src="${webPath}/dwr/engine.js"></script>
		<script type='text/javascript' src="${webPath}/dwr/util.js"></script>  
  		<script type="text/javascript" src="${webPath}/dwr/interface/MessagePush.js"></script> 
  		<script type="text/javascript" src="${webPath}/<%=layout%>/js/jAlert.js"></script>
  		
  		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" type="text/css"/>
		<%--bootstrap--%>
		<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${webPath}/layout/view/js/openDiv.js" ></script>
		<link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/layout.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/menu.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/iconfont/css/iconfont.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/pageInfo.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/A2B.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/autocompleter.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/animate.min.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/notification.css" />
		<script type="text/javascript" src="${webPath}/component/help/sysHelp.js" ></script>
		<script type="text/javascript" src="${webPath}/layout/view/js/warns.js" ></script>
		<link rel="stylesheet" href="${webPath}/component/help/sysHelp.css" />
		<link rel="stylesheet" href="${webPath}/tech/wkf/css/modelAlert.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/fixedDiv.css" />
		<link rel="stylesheet" href="${webPath}/tech/wkf/css/processDetail.css" />
		
		
		<!--artDialog控件  -->
		<script type="text/javascript" src="${webPath}/UIplug/artDialog/dist/dialog-plus-min.js"></script>
  		<link rel="stylesheet" href="${webPath}/UIplug/artDialog/css/ui-dialog.css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/dialog.js?v=${cssJsVersion}"></script>
  		
  		<!--加载动画js  -->
  		<script type="text/javascript" src="${webPath}/themes/factor/js/loadingAnimate.js?v=${cssJsVersion}"></script>
  		
  		<link rel="stylesheet" href="${webPath}/themes/view/css/filter.css" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/BS-factor.css?v=${cssJsVersion}" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/layout.css?v=${cssJsVersion}" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/A2B.css" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/viewUserDef.css" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/pageInfo.css" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/modelAlert.css" />
		<!-- tab控件 -->
	   <%--  <script type="text/javascript" src="${webPath}/<%=layout%>/js/tabulous.js" ></script>
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/css/tabulous.css" />  --%>
		<script>
		try {
			if(supports_html5_storage()){
				var recWarnd = new RecWarnd(<%=calendarDaylist%>);
			}
		} catch (e) {
			console.error("获取本地存储失败，日程功能可能不能使用！-" + e.message + "-" + e.name);
		}
		
		
		function onPageLoad(){  
	       	var userId = '<%=(String)request.getSession().getAttribute("regNo")%>';
	        MessagePush.onPageLoad(userId);  
        }
		 function showMessage(msgType,msgCount,autoMessage,taskInfo){  
			 rzzl.showMessages(msgCount,"管理员",msgType,autoMessage,taskInfo); 
     	}
		function closePop(){
			var dm = document.getElementById("b1_iframe").contentWindow;
			if($(dm.document).find(".task_select").length>0){
				var $task = $(dm.document).find(".task_select");
				var data = $task.data("info");
				$task.removeClass("task_correct").removeClass("task_select").data("open",true).find(".task_contents").animate({height:"25px"},300,function(){
					$(this).parents(".task_style").find(".task_ctrl").slideUp(function(){
						$(this).empty();
						document.getElementById("b1_iframe").contentWindow.mcSelector.mCustomScrollbar("update");
					});
				});
				document.getElementById("b1_iframe").contentWindow.taskB.changeTaskSts(data);
			}
		}
		function showLocale(objD)   
	    {   
	        var str,colorhead,colorfoot;   
	        var yy = objD.getYear();   
	            if(yy<1900) yy = yy+1900;   
	        var MM = objD.getMonth()+1;   
	            if(MM<10) MM = '0' + MM;   
	        var dd = objD.getDate();   
	            if(dd<10) dd = '0' + dd;   
	        var hh = objD.getHours();   
	            if(hh<10) hh = '0' + hh;   
	        var mm = objD.getMinutes();   
	            if(mm<10) mm = '0' + mm;   
	        var ss = objD.getSeconds();   
	            if(ss<10) ss = '0' + ss;   
	        var ww = objD.getDay();   
	            if ( ww==0 ) colorhead="<font color=\"#FF0000\">";   
	            if ( ww > 0 && ww < 6 ) colorhead="<font color=\"#373737\">";   
	            if ( ww==6 ) colorhead="<font color=\"#008000\">";   
	            if (ww==0) ww="星期日";   
	            if (ww==1) ww="星期一";   
	            if (ww==2) ww="星期二";   
	            if (ww==3) ww="星期三";   
	            if (ww==4) ww="星期四";   
	            if (ww==5) ww="星期五";   
	            if (ww==6) ww="星期六";   
	            colorfoot="</font>";
	             //  str = colorhead + yy + "年" + MM + "月" + dd + "日" + hh + ":" + mm + ":" + ss + " " + ww + colorfoot;   
	            str = hh + ":" + mm + ":" + ss;
	            return(str);   
	    } 
		function tick()   
	    {   
	        var today;   
	        today = new Date();   
	       	$("#currTime").html(showLocale(today));   
	        window.setTimeout("tick()", 1000);   
	    }   
	    tick();   
		
			function showPageInfo(value){  
	            rzzl.setPageInfo(value, "#pageInfoContent");
	    	}
			var path = '${webPath}';
			var viewMenuData = eval("("+'<%=request.getSession().getAttribute("viewMenuData") %>'+")");
			var myLayout;
			$(document).ready(function() {
				rzzl.setBox_A(".pt-page[name='A'] iframe");
				myLayout = $('body').layout({
					center__paneSelector: ".outer-center",
					west__paneSelector: ".outer-west",
					west__size: 100,
					spacing_open: 0,
					west__childOptions: {
						center__paneSelector: ".outer-west-center",
						north__paneSelector: ".outer-west-logo",
						north__size: 55,
						spacing_open: 0
					},
					center__childOptions: {
						center__paneSelector: ".middle-center",
						north__paneSelector: ".middle-north",
						north__size:56,
						spacing_open: 0,
						spacing_closed:0,
						north__childOptions: {
							center__paneSelector: ".middle-north-center",
							west__paneSelector: ".middle-north-west",
							east__paneSelector: ".middle-north-east",
							west__size: 500,
							east__size: 550,
							spacing_open: 0,
							east__childOptions: {
								center__paneSelector: ".middle-north-east-center",
								west__paneSelector: ".middle-north-east-west",
								west__size: 210,
								spacing_open: 0
							}
						}
					}
				});
				// add by LiuYF 文字被后边的div覆盖。不知道怎么改layout的单个z-index属性。
				$(".middle-north-west").css("z-index","1");
				rzzl.initMenu(".menu_B");
				$(".pageInfo").click(function(){
	 				$(this).hide();
					$("#nope").val("");
					$("#nope").show();
					$("#nope").focus();
				});
				//MessagePush.sendMessageAuto("0000:0000", "DL0001:DL0001","测试消息");
				/* var cn = 1;
				rzzl.showMessages(cn,"管理员","0","测试消息");
				cn++;
				var si = setInterval(function(){
					rzzl.showMessages(cn,"管理员","0","测试消息");
					cn++;
 				},240000); */
 				rzzl.switchB.init($(".switchB"));
			});
			function whichTransitionEvent(el){
			    var t;
			    var transitions = {
			      'transition':'transitionend',
			      'OTransition':'oTransitionEnd',
			      'MozTransition':'transitionend',
			      'WebkitTransition':'webkitTransitionEnd'
			    };

			    for(t in transitions){
			        if( el.style[t] !== undefined ){
			            return transitions[t];
			        }
			    }
			}
			
			
		//消息中心
		function getMessagePage(){
			
			rzzl.skipPage("SysTaskInfoAction_getListPage.action");
			LoadingAnimate.stop();
			$("#messagePage").addClass("messagePageSel");
			$(".menu-active").removeClass("menu-active");
		}
		</script>
		<style type="text/css">
			div[name='A']{
				background-color:#fff;
			}
		</style>
	</head>
	<body onLoad="onPageLoad();dwr.engine.setActiveReverseAjax(true);dwr.engine.setNotifyServerOnPageUnload(true);">
		<div class="outer-center">
			<div class="middle-center pt-perspective">
				<div class="pt-page" name="A">
					<iframe id="a_iframe" src="${webPath}/sysSkip/skipToC?entranceNo=99" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" name="iframepage"></iframe>
				</div>
				<div class="pt-page pt-page-current" name="B">
					<div class="pg" name="B1">
						<iframe id="b1_iframe"  src="${webPath}/sysTaskInfo/getListPage" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" name="b1_iframe"></iframe>
					</div>
					<div class="pg" name="B2">
						<iframe id="b2_iframe" src="${webPath}/bwmTaskRoleRel/getListPage" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height="100%" name="b2_iframe"></iframe>
					</div>
				</div>
				<div class="switchB">
					<div class="A"></div>
					<div class="B"></div>
					<div class="current"></div>
				</div>
			</div>
				<div id="middle-north" class="middle-north">
				<!-- 切换层 -->
				<div class="middle-north-west TopBar-chevron" style="background-color:#32b5cb;height:55px;">
					<span class="logodes">金融业务系统</span>
					 <img src="${webPath}/themes/factor/images/logo_03.png" style="width: 214px; position: relative; top: 15px; margin-left:283px;" />
				</div>
				<!-- 详情 -->
			    <div class="middle-north-center" style="background-color:#32b5cb;"></div>
				<div class="middle-north-east">
					<!-- 欢迎-->
					<div id="perDa" class=" middle-north-east-west TopBar-user" >
						<img src="${webPath}/themes/factor/images/op_user.jpg" class="user-image">
						<span title="<%=request.getSession().getAttribute("regJob")%>" class="welcome-span">
							欢迎您，<%=request.getSession().getAttribute("regName")%>
						</span>
						<i class="i i-jiantou8 welcome-i" ></i>
					</div>
					<!-- 时间/功能-->
					<div class="middle-north-east-center middle-north-east-center1" style="padding:3px 10px 3px; -moz-padding:6px 10px 0;color: #fff;font-size: 12px;background-color:#32b5cb; width:165px !important;">
						 <!-- 时间-->
						 <div>
							 <i class="i i-time time"></i>
							 <span>
								 <span  class="datetime-span" id="currTime"></span><br /><span class="datetime-span"><%=viewDate%></span>
							 </span>
						 </div>
						 <!--帮助-->
<!-- 						 <i class="i i-help help" onclick="addSelectChose('set',this);"></i> -->
						<!--  <i class="i i-help help"></i> -->
						 <!--消息中心-->
					</div>
														<!--消息中心-->
					 <div id="messagePage" class="messagePage" onclick="getMessagePage();" >
							 <i class="i i-xinxi xinxi"  ></i>
							 <span class="xinxinfont">待办任务</span>
							<!--  <i class="i i-dian  dian"></i> -->
<!-- 							 <div class="dian" ></div> -->
					</div>
							 <!--工具栏-->
					<div id="more-div" class="more-div" onClick="getfixedDiv();">
							 <i class="i i-menu menu"></i>
					</div>
				</div>
				
			</div>
		</div>
		<div class="outer-west" style="overflow:visible!important;">
			<!-- LOGO-->
			
			<!-- 视角-->
			<div class="outer-west-center" style="background-color: #e2e6f0;overflow:visible;">
				<div class="outer-west-logo" style=" height:55px;">
					 <img  src="${webPath}themes/factor/images/enterLogo.png" class="logoimg" > 
				</div>
				<ul class="menu_B">
				</ul>
			</div>
		</div>
		
				
		<div id="my-info" class="my-info">
			<div class="row clearfix my-info-detail">
				<div>
					<div class="col-md-8 column">	
						<p><%=regName%></p>
						<p id="phone"></p>
					</div>
					<div id="u-pic" class="col-md-4 column">
						<img id="headImgShow1" class="account-img"  >
					</div>
				</div>
				<div>	
					<p><%=orgName%></p>
					<p id="email"></p>
				</div>
			</div>
			<div class="row clearfix my-info-opt btn-group">
				<button class="btn btn-link" onclick="optSliders();">
					<i class="i i-password" ></i><span>修改密码</span>
				</button>
				<div class="my-line"></div>
				<button class="btn btn-link pull-right" onclick="sysQuit();">
					<i class="i i-tuichu"></i><span>退出系统</span>
				</button>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/<%=layout%>/js/view.js" ></script>
<script type="text/javascript">
	 var viewFlag = "view"; 
	 var opNo='${opNo}';
	 var url="SysUserActionAjax_getByIdAjax.action?opNo="+opNo+"&query=query";
	 var homePage = eval("("+'<%=request.getSession().getAttribute("homePage") %>'+")");
 	$(function () { 
	    view.init(); 
  		$('#myModal').modal('hide');
	 });
</script>
</html>

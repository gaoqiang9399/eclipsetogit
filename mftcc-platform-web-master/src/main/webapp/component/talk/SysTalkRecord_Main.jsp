<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<style>
html{}
body,html{ height:100%;}
body{ margin:0; padding:0;}
ul,p, h2,h3{ margin:0; padding:0;}
li{ list-style:none;}
.infoMain{ overflow:hidden; height:100%;positon:relative}
.infoMain .infoIcon{ float:left; width:60px; background:#434447; padding-top:10px; height:99%;}
.infoIcon a{ display:block; width:60px; height:60px;}
.infoIcon a.infoIconTalk{ background:url(img/talk.png) no-repeat center center;}
.infoIcon a.infoIconMem{ background:url(img/member.png) no-repeat center center;}
.infoIcon a.infoIconTalk2{ background:url(img/talk2.png) no-repeat center center;}
.infoIcon a.infoIconMem2{ background:url(img/member2.png) no-repeat center center;}

.infoMain .infoMember{ float:left; width:28%; background:#f7f7f7; border:1px solid #dcdcdc; height:auto;border-left:none;display:none;}

.infoMember ul li{ height:59px; border-bottom:1px solid #dcdcdc; padding-top:9px;}
.infoMember ul li.current{ border-left:3px solid #008fdc; background:#dcdcdc}
.infoMember ul li i{ display: block; float:left;  width:40px; height:40px; border-radius:20px; margin-left:20px;}
.infoMember ul li i.memberIcon{background:url(img/memberIcon.png) no-repeat;}
.infoMember ul li p{ float:left; font-size:12px;  color:#696969; line-height:18px; margin-left:10px;}
.infoMember ul li p span{ color:#000;}
.infoMember ul li h3{ float:left; font-size:12px;  color:#000; line-height:42px; margin-left:10px; font-weight:normal}
.infoMember ul li em.infoNum{ height:15px; line-height:15px; background:#f03402; width:30px; border-radius:8px; display:block; float:right; color:#fff; font-size:12px; font-style:normal; text-align:center; margin-right:20px;}
.infoMember ul li em.infoTime{ height:15px; line-height:15px; width:30px; display:block; float:right;  font-size:12px; font-style:normal; text-align:center; margin-right:20px;}

.infoMain .infoTalk{ float:left; width:28%; background:#f7f7f7; border:1px solid #dcdcdc; height:99%;border-left:none;}
.infoMemberTop { padding:16px 0px 16px 12px;position:relative}
.infoMemberTop input[type="text"]{ background:#fff; border:1px solid #dcdcdc; height:28px; line-height:20px; font-size:12px; padding:4px; width:calc( 100% - 18px)}
.infoMemberTop a{ display:block; width:28px; height:26px;  position:absolute; bottom:12px; right:10px;color:#ddd}
.infoMemberTop input[type="button"]{ width:30px; height:30px; background:#dcdcdc url(img/plus.png) no-repeat center center; border:none; position:relative; left:-18px; top:10px; cursor:pointer}
.infoTalk ul li{ height:59px; border-bottom:1px solid #dcdcdc; padding-top:9px;}
.infoTalk ul li.current{ border-left:3px solid #008fdc; background:#dcdcdc}
.infoTalk ul li i{ display: block; float:left;  width:40px; height:40px; border-radius:20px; margin-left:20px;}
.infoTalk ul li i.memberIcon{background:url(img/memberIcon.png) no-repeat;}
.infoTalk ul li p{ float:left; font-size:12px;  color:#696969; line-height:18px; margin-left:10px;}
.infoTalk ul li p span.title{ color:#000;}
.infoTalk ul li h3{ float:left; font-size:12px;  color:#000; line-height:42px; margin-left:10px; font-weight:normal}
.infoTalk ul li em.infoNum{ height:15px; line-height:15px; background:#f03402; width:30px; border-radius:8px; display:block; float:right; color:#fff; font-size:12px; font-style:normal; text-align:center; margin-right:20px;}
.infoTalk ul li em.infoTime{ height:15px; line-height:15px; width:30px; display:block; float:right;  font-size:12px; font-style:normal; text-align:center; margin-right:20px;}

.infoMain .infoContent{ float:left;border:1px solid #dcdcdc; border-left:none; width:calc(72% - 62px); background:#fff; position:absolute;top:0;right:0;height:100%;display:none;}
.infoContent h2{ height:50px; line-height:50px; font-size:14px; color:#000; background:#f0f0f0; text-align:center}
.infoContentInput{position:absolute; bottom:20px; width:calc(72% - 82px);right:0px; display:none; z-index:99;}
.infoContentInput textarea{ resize:none; height:60px; width:calc(100% - 20px); background:#f9f9f9; border:1px solid #dcdcdc; padding:20px; border-bottom:none}
.infoContentInput p{width:calc(100% - 20px); background:#dee5ec; border:1px solid #dcdcdc; padding:10px 20px; border-top:none;margin-top:-4px; text-align:right}
.infoContentInput p button{ background:#f5f5f5; border:1px solid #e3e3e3; line-height:24px;}
.infoContentMain{ text-align:center; padding-top:20px;height: calc(100% - 170px);overflow:auto;}
.infoContentMain span.concentTime{ color:#fff; background:#d3d3d3; margin:0 auto; padding:4px; font-size:12px}
.infoContentMain p.hisLink {color:#008fdc; margin:0 auto 10px; font-size:12px;  }
.fenzu{ line-height:28px; background:#f0f0f0; color:#b8b8b8; font-size:12px; padding-left:10px; border-top:1px solid #dcdcdc}
.contentTalk{ overflow:hidden;}
.contentTalk i{ display: block; float:left;  width:40px; height:40px; border-radius:20px; margin-left:20px; position:relative;top:26px;}
.contentTalk i.memberIcon{background:url(img/memberIcon.png) no-repeat;}
.memberTalk1{ float:left; font-size:12px; padding-left:10px; position:relative; margin-left:8px;max-width:60%}
.memberTalk1 p{ color:#aeaeae; text-align:left; line-height:32px;}
.memberTalk1 span{width:100%; word-wrap: break-word; word-break: normal； display:block; line-height:22px; border:1px solid #eaeaea; color:#676767; padding:8px 24px 8px 14px; border-radius:4px; text-align:left; float:left;}
.memberTalk1 em{ display:block; width:8px; height:10px; background:url(img/talkSan1.png) no-repeat; position:absolute; left:3px; top:43px;}
.contentTalk2{ overflow:hidden;}
.contentTalk2 i{ display: block; float:right;  width:40px; height:40px; border-radius:20px; margin-right:20px; position:relative;top:28px;}
.contentTalk2 i.memberIcon{background:url(img/memberIcon.png) no-repeat;}
.memberTalk2{ float:right; font-size:12px; padding-right:10px; position:relative; margin-right:8px;max-width:60%}
.memberTalk2 p{ color:#aeaeae; text-align:right; line-height:32px;}
.memberTalk2 span{ display:block; line-height:22px;  color:#fff; padding:8px 14px 8px 24px; border-radius:4px; background:#008fdc;text-align:left; float:right;width:100%; word-wrap: break-word; word-break: normal；}
.memberTalk2 em{ display:block; width:8px; height:10px; background:url(img/talkSan2.png) no-repeat; position:absolute; right:3px; top:43px;}
.talkingList li a{cursor:pointer;}
.mCustomScrollBox{ height:90%}
</style>
<jsp:include page="talkjs.jsp"></jsp:include>
<script type="text/javascript">
	var talkStatus = "";
	$(function(){
			$(".infoMember_mCS").mCustomScrollbar({
				advanced:{
					updateOnContentResize:true,
				},
			});
		});
</script>
</head>
<body style="padding:0;height:100%;" >
	<div class="infoMain">
    	<div class="infoIcon">
        	<a href="#" class="infoIconTalk" id="iconTalk"></a>
            <a href="#" class="infoIconMem" id="iconMenu"></a>
        </div>
        <div class="infoTalk" id="infoTalk">
        	<div class="infoMemberTop">
            	<input type="text" placeholder="搜索">
                <a href="javascript:void(0);"><i class="i i-fangdajing"></i></a>
            </div> 
            <ul class="talkingList" id="talkingList">
            </ul>          
        </div>
        
        <div class="infoMember" id="infoMember">
        	<div class="infoMemberTop">
            	<input type="text" placeholder="搜索">
                <a href="javascript:void(0);"><i class="i i-fangdajing"></i></a>
            </div>
            <div class="infoMember_mCS"> 
            	<div class="infoMember_mCS2">
            	</div>   
            </div>        
        </div>
         
        <div class="infoContentInput" id="infoContentInput">
          	<textarea id="messageInput"></textarea>
          	<input type="hidden" name="opNo" /><input type="hidden" name="opName" />
              <p>
              	<button type="button" id="btnSendContent">发送</button>
              </p>
        </div>    
         
    </div>
</body>
</html>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${webPath}/UIplug/jqueryUI/js/jquery-ui-1.10.1.custom.min.js"></script>

<link id="newsDialog" rel="stylesheet" href="${webPath}/layout/view/themes/css/newsDialog${skinSuffix}.css" />
<script type="text/javascript" src="${webPath}/layout/view/js/meiLaiPhone.js?v=${cssJsVersion}"></script>
<style type="text/css">
	.MeilaiPhoneDiv{
		background-color: #FFFFFF;
		width: 460px;
		height: 260px;
		font-family: 微软雅黑;
		box-shadow: 0px 0px 10px #5f5f5f;
		position: absolute;
		right: 10px;
		bottom: 5px;
		z-index: 100;
	}
	.MeilaiPhone_bottom{
		height: 30px;
		background-color: #F8F9FD;
		text-align: center;
		font-size: 12px;
		line-height: 30px;
		bottom: 0px;
	}
	.MeilaiPhone_content{
		height: 200px;
		border-bottom: 1px solid #EAE8E8;
		font-size: 12px;
		padding-left: 20px;
		padding-right: 20px;
		padding-top: 10px;
	}
	.MeilaiPhoneDiv-label{
		width: 15%;
	}
	.MeilaiPhoneDiv-span{
		width: 30%;
		display: inline-block;
		height: 40px;
	}
	.MeilaiPhoneDiv-textArea{
		width: 100%;
		height: 70px;
	}

	.callInDiv{
		background-color: #FFFFFF;
		width: 260px;
		height: 200px;
		font-family: 微软雅黑;
		box-shadow: 0px 0px 10px #5f5f5f;
		position: absolute;
		right: 10px;
		bottom: 5px;
		z-index: 100;
	}
	.callInDiv-label{
		width: 30%;
	}

	.callInDiv-span{
		width: 60%;
		display: inline-block;
		height: 20px;
	}
	.callInDiv-content{
		height: 140px;
		border-bottom: 1px solid #EAE8E8;
		font-size: 12px;
		padding-left: 20px;
		padding-right: 20px;
		padding-top: 10px;
	}

</style>
<div id='MeilaiPhoneDiv' class='MeilaiPhoneDiv' style="display: none">
	<div  class="newsFrame_title">
		<span class="newsFrame_title_icon">
		<img src="${webPath}/layout/view/themes/images/logo-white.png" style="width:18px;height:18px;"/></span>
		<span class="newsFrame_title_tip">
		正在通话</span>
		<%--<div class="newsFrame_title_close" onclick="meiLaiPhone.setDivHide();">×</div>--%>
	</div>
	<div class="MeilaiPhone_content">
		<label class="MeilaiPhoneDiv-label">用户姓名:</label><div id="MeilaiPhoneDiv-cusName" class="MeilaiPhoneDiv-span">王朝月</div>
		<label class="MeilaiPhoneDiv-label">用户电话:</label><div id="MeilaiPhoneDiv-cusTel" class="MeilaiPhoneDiv-span">15928391028</div><br>
		<label class="MeilaiPhoneDiv-label">拨打时间:</label><div id="MeilaiPhoneDiv-startTime" class="MeilaiPhoneDiv-span">15:00:01</div>
		<label class="MeilaiPhoneDiv-label">通话时长:</label><div id="MeilaiPhoneDiv-timeLong" class="MeilaiPhoneDiv-span">1:07</div><br>
		<label class="MeilaiPhoneDiv-label">备注:</label><br>
		<textarea class="MeilaiPhoneDiv-textArea" id="MeilaiPhoneDiv-textArea"></textarea>
	</div>
	<div class="MeilaiPhone_bottom color_theme">
	<!--当前详细类型 1-代办汇总 2-消息汇总 3-消息明细  4--代办信息-->
		<%--<input id="newsTypeVal" type="hidden" value="1"></input>--%>
		<%--<input id="toContentUrl" type="hidden" ></input>--%>
		<%--<input id="taskId" type="hidden" ></input>--%>

		<input id="audioUrl" type="hidden" />
		<a onclick="meiLaiPhone.hangUpByOp()">通话结束</a>
	</div>
</div>

<div id='callInDiv' class='callInDiv' style="display: none">
	<div  class="newsFrame_title">
		<span class="newsFrame_title_icon">
		<img src="${webPath}/layout/view/themes/images/logo-white.png" style="width:18px;height:18px;"/></span>
		<span class="newsFrame_title_tip">
		来电提醒</span>
		<%--<div class="newsFrame_title_close" onclick="meiLaiPhone.setDivHide();">×</div>--%>
	</div>
	<div class="callInDiv-content">
		<div id="callInDiv_cusTel" class="callInDiv-span" style="width: 150px;font-size: 20px;height: 50px;">15928391028</div>&nbsp;呼叫中<br>
		<label class="callInDiv-label">是否会员:</label><div id="callInDiv-isCus" class="callInDiv-span">……</div><br>
		<label class="callInDiv-label">会员姓名:</label><div id="callInDiv-cusName" class="callInDiv-span">……</div>
	</div>
	<div class="MeilaiPhone_bottom">
		<!--当前详细类型 1-代办汇总 2-消息汇总 3-消息明细  4--代办信息-->
		<%--<input id="newsTypeVal" type="hidden" value="1"></input>--%>
		<%--<input id="toContentUrl" type="hidden" ></input>--%>
		<%--<input id="taskId" type="hidden" ></input>--%>

		<input id="audioUrl" type="hidden" />
		<a onclick="meiLaiPhone.answer()">接通</a>
	</div>
</div>

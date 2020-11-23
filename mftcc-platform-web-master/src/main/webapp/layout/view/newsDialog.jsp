<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${webPath}/UIplug/jqueryUI/js/jquery-ui-1.10.1.custom.min.js"></script>

<link id="newsDialog" rel="stylesheet" href="${webPath}/layout/view/themes/css/newsDialog${skinSuffix}.css" />
<script type="text/javascript" src="${webPath}/layout/view/js/newsDialog.js?v=${cssJsVersion}"></script>

<div id='newsFrameDiv' class='newsFrame' style="display:none;">	
			<div  class="newsFrame_title">
				<span class="newsFrame_title_icon i i-lingdang"></span>
				<span class="newsFrame_title_tip">
				系统消息</span>
				<div class="newsFrame_title_close" onclick="newsDialog.setDivHide();">×</div>
			</div>
			<div class="newsFrame_content">
					<div id="waitToProcessCnt" class="newsFrame_content_summary"></div>
			</div>
			<div class="newsFrame_bottom">
			<!--当前详细类型 1-代办汇总 2-消息汇总 3-消息明细  4--代办信息-->
				<input id="newsTypeVal" type="hidden" value="1"></input>
				<input id="toContentUrl" type="hidden" ></input>
				<input id="taskId" type="hidden" ></input>
				<a onclick="newsDialog.toWaitToListPge()">查看</a>
			</div>
		</div>
<script type="text/javascript">
// 	newsDialog.setInitContent();
</script>
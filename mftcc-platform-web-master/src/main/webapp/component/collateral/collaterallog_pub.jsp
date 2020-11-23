<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="row clearfix padding_top_10">
	<div class="col-xs-12 col-md-12 column padding_left_15">
		<button class="btn btn-link block-title his-detail-opt">押品信息追踪</button>
		<button type="button" class="btn btn-font-qiehuan pull-right his-detail-opt" ><i class="i i-qiehuan" style="font-size:22px;"></i></button>
	</div>
</div>
<div class="row clearfix padding_left_30 his-info">
	<div class="col-xs-12 col-md-12 column" >
		<c:forEach items="${ pledgeImpawnLogList}" var="pledgeImpawnLog" varStatus="statu" > 
			<div>
				<p class="his-title">
					<span>${pledgeImpawnLog.logId}</span>
					<span class="change-date">${pledgeImpawnLog.logDate}</span>
					<span class="change-time">${00:00:00}
				 </p>
				 <p class="his-cont">
				 	<span>${pledgeImpawnLog.logCont}</span>
				 </p>
			</div>
		</c:forEach>
	</div>
</div>
<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>					

	
								
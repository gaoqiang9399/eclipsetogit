<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <link rel="stylesheet" href="${webPath}/component/eval/css/detailResult.css" /> --%>
<link type="text/css" rel="stylesheet" href="${webPath}/component/eval/css/detailResult.css?v=${cssJsVersion}"/>
<link id="appEvalInfo"  type="text/css" rel="stylesheet" href="${webPath}/component/eval/css/appEvalInfo${skinSuffix}.css" />
<script type="text/javascript">
    var evalCredit = '${evalCredit}';
    var dataMap = "";
</script>
<div id="evalCard" class="form-table" style="display:none">
	<div class="title">
		<span class="formName"><i class="i i-xing blockDian"></i>评分卡</span>
		<button class=" btn btn-link pull-right formAdd-btn"
			data-toggle="collapse" data-target="#evalCard-content">
			<i class="i i-close-up"></i><i class="i i-open-down"></i>
		</button>
	</div>
	<div class="evalCard content collapse in" id="evalCard-content">
		<div id = "detailInfo-div" class="scroll_xy">
				<c:forEach items="${ evalGradeCardList}" var="evalGradeCard">
					<!-- 定性 -->
					<div id="${evalGradeCard.gradeCardId}" name = "${evalGradeCard.gradeCardId}" class="li_content_type">
						<div class='cover'>
							<div class='handle'>
								<span>${evalGradeCard.gradeCardName}</span>
							</div>
						</div>
						<div class="li_content">
								<table class="ls_list_a" style="width:100%">
									<thead></thead>
									<tbody></tbody>
								</table>
						</div>
					</div>
				</c:forEach>
			</div>
	</div>
</div>
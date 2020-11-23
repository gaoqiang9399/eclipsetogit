<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>列表表单</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet"
	href="${webPath}/themes/view/css/tableFour.css" />
<link rel="stylesheet"
	href="${webPath}/component/cus/custracing/css/MfCusTrack_List.css?v=${cssJsVersion}" />
<script id="tracktype-template" type="text/x-handlebars-template">
	{{#each this}}
		<option value="{{key}}">{{value}}</option>	
	{{/each}}
</script>
<script type="text/javascript">
	var cusNo = '${cusNo}';
	var editbtn;
	var handelType = "1";
	var mfCusTrackList = '${mfCusTrackList}';
	var newDate = '${newDate}';
	var trackTypeArray = ${dataMap.trackTypeArray};
	var notFollowupFlag = '${dataMap.notFollowupFlag}';
	//用于填充跟进类型模板的数据源
	//var trackTypeData = $.parseJSON('${dataMap.trackTypeArray}');
</script>
</head>
<body class="body_bg overFlowHidden">
	<div class="mf_content  track_div">
		<div id="show-form" class="btn-div margin_top_10">
			<button type="button" class="btn btn-primary" onclick="addTrackForm()">新增跟进</button>
			<button type="button" class="btn btn-primary" onclick="addTrackPlan()">跟进计划</button>
			<dhcc:pmsTag pmsId="loan-allot-cusmng-btn">
				<button type="button" class="btn btn-primary" id="allotCusMngButton" onclick="selectCusMngNameDialog(this)">分配客户经理</button>
			</dhcc:pmsTag>
		</div>

		<c:if test='${query==""}'>
			<div id="trackForm" class="addtracdiv collapse">
				<form role="form" class="form-horizontal">
					<input type="hidden" value="${cusNo}" name="cusNo"> <input
						type="hidden" name="trackId">
					<p class="tip">
						<span>说明：</span>带*号的为必填项信息，请填写完整。
					</p>
					<div class="form-group">
						<label for="track_type" class="col-sm-3 control-label"> <span
							class='required'>*</span>跟进类型
						</label>
						<div class="col-sm-8">
							<input name="trackType" type="text">
						</div>
					</div>
					<div class="form-group">
						<label for="track_type" class="col-sm-3 control-label"><span
							class='required'>*</span>跟进内容</label>
						<div class="col-sm-8">
							<textarea type="text" rows="4" maxlength="200"
								class="form-control" name="trackContent"
								style="border-radius: 0px; resize: none;"
								placeholder="请在这里登记您的跟进情况（最多输入200个字）"></textarea>
						</div>
					</div>
					<div class="formRowCenter background-border-none btn-position">
						<dhcc:thirdButton value="提交" action="" typeclass="insert-track"
							onclick="ajaxInsertTrack(this)"></dhcc:thirdButton>
						<dhcc:thirdButton value="跟进计划" action="" typeclass="cancel"
							onclick="addTrackPlan()"></dhcc:thirdButton>
					</div>
				</form>
			</div>
		</c:if>
		<div class="track-div padding_top_10">
			<c:if test="${mfCusTrackList.size()==0}">
				<div class="message margin_top_10">暂无跟进信息</div>
			</c:if>
			<c:if test="${mfCusTrackList.size()!=0}">
				<c:forEach items="${mfCusTrackList}" var="cusTrack" varStatus="status">
					<div class="container track-line" id="track-line">
						<div id="track-info" class="track-info">
							<div class="row" style="text-align: left;">
								<span class="track-type-list">
									[${cusTrack.trackTypeName}] </span> <span class="padding_0">${cusTrack.opName}</span>
								<span class="track-reg-time">登记于 ${cusTrack.regTime}</span>
							</div>
							<div class="row">
								<div class="track-content">
									${cusTrack.trackContent}
								</div>
								<div class="track-opt">
									<a href="javascript:void(0);" id="comment-num"
										data-toggle="collapse"
										data-target="#${cusTrack.trackId}Comment"
										num='${cusTrack.commentList.size()}'>评论(${cusTrack.commentList.size()})
									</a>
								</div>
							</div>
						</div>
						<div class="comment-div collapse" id="${cusTrack.trackId}Comment">
							<div class="comment-add margin_top_10">
								<form>
									<div class="form-group padding_0">
										<input type="hidden" value="${cusNo}" name="cusNo"> <input
											type="hidden" name="parentId" value="${cusTrack.trackId}">
										<input type="hidden" value="1" id="handleType">
										<!--1表示是新增操作2表示是更新操作  -->
										<div>
											<!-- <input type="text" class="form-control" name="trackContent" style="border-radius: 0px;" placeholder="请在这里登记您的评论（最多输入200个字）"> -->
											<textarea type="text" rows="4" class="form-control"
												maxlength="200" name="trackContent"
												style="border-radius: 0px; resize: none;"
												placeholder="请在这里登记您的评论（最多输入200个字）"></textarea>
										</div>
										<div style="text-align: left;">
											<!-- <span class="input-group-btn"> -->
											<button type="button" class="btn"
												style="border-radius: 0px; background: #32b5cb; color: #fff; font-size: 12px; margin-top: 10px;"
												onclick="ajaxInsertComment(this)">提交评论</button>
											<!-- </span> -->
										</div>
									</div>
								</form>
							</div>

							<div class="comment-info">
								<c:if test="${cusTrack.commentList.size()==0}">
								</c:if>
								<c:if test="${cusTrack.commentList.size()!=0}">
									<c:forEach items="${cusTrack.commentList}" var="comment"
										varStatus="status1">
										<div class="row comment-reg-time">
											<div class="col-sm-12">${comment.opName}&nbsp;&nbsp;登记于${comment.regTime }</div>
										</div>
										<div class="row">
											<div class="col-sm-12">${comment.trackContent }</div>
										</div>

									</c:forEach>
								</c:if>
							</div>
						</div>
					</div>
				</c:forEach>
			</c:if>
		</div>
	</div>
	<!--跟进计划弹窗页面-->
	<%@ include file="/component/nmd/eventDialog.jsp"%>
	<script type="text/javascript"
		src="${webPath}/component/include/tableFour.js?v=${cssJsVersion}"></script>
	<script type="text/javascript"
		src="${webPath}/component/cus/custracing/js/handlebars-v4.0.5.js"></script>
	<script type="text/javascript"
		src="${webPath}/component/cus/custracing/js/MfCusTrack_List.js?v=${cssJsVersion}"></script>
</body>
</html>
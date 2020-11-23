<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>五级分类详情</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/view-detail.css?v=${cssJsVersion}" />
		<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'></script>
		<script type="text/javascript">
			var path = "${webPath}";
			var fincId = "${mfPactFiveclass.fincId}";
			var fiveclassId = "${mfPactFiveclass.fiveclassId}";
			$(function(){
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
			});
			function getBack(){
				LoadingAnimate.start();
				if(top.lawsuitFlag=='query'){
					myclose();
				}else{
					var url = webPath+"/mfPactFiveclass/getListHisPage?fincId="+fincId+"&fiveclassId="+fiveclassId;
					var obj = $(top.window.document).find("body");
					obj.find("#bigFormShowiframe").attr("src",url);
				}
			}
		</script>
	</head>
	<style type="text/css">
		.business-span{
			font-size: 18px;
	    	color: #32b5cb;
	    	margin-right:5px;
		}
		.body_bg{
			line-height: 3.125em
		}
		.col-md-3{
			width:50%
		}
	</style>
	<body class="body_bg overFlowHidden">
		<div class="mf_content">
			<div class="row clearfix" style="padding:10px 100px;">
				<div class="col-md-12 column">
					<div class="block-title"><span>五级分类修改历史信息</span></div>
					<div class="row clearfix padding_top_15 padding_left_40">
						<div class="col-md-3 column">
							<span>系统初分：</span>
							<span class="margin_top_15">
								<span class="business-span">
									<c:if test="${mfPactFiveclass.fiveclass == 1 }">正常</c:if>
									<c:if test="${mfPactFiveclass.fiveclass == 2 }">关注</c:if>
									<c:if test="${mfPactFiveclass.fiveclass == 3 }">次级</c:if>
									<c:if test="${mfPactFiveclass.fiveclass == 4 }">可疑</c:if>
									<c:if test="${mfPactFiveclass.fiveclass == 5 }">损失</c:if>
								</span> 
							</span>
						</div>
						<div class="col-md-3 column">
							<span>登记时间：</span>
							<span class="margin_top_15">
								<span class="business-span">${mfPactFiveclass.regTime}</span>
							</span>
						</div>
				   		<div class="col-md-3 column">
							<span>公司认定：</span>
							<span class="margin_top_15">
								<span class="business-span">
									<c:if test="${mfPactFiveclass.fiveclass == 1 }">正常</c:if>
									<c:if test="${mfPactFiveclass.fiveclass == 2 }">关注</c:if>
									<c:if test="${mfPactFiveclass.fiveclass == 3 }">次级</c:if>
									<c:if test="${mfPactFiveclass.fiveclass == 4 }">可疑</c:if>
									<c:if test="${mfPactFiveclass.fiveclass == 5 }">损失</c:if>
								</span> 
							</span>
						</div>
				   		<div class="col-md-3 column">
							<span>公司认定时间：</span>
							<span class="margin_top_15"><span class="business-span">${mfPactFiveclass.confirmDate}</span> </span>
							</div>
				   		<div class="col-md-12 column">
							<span>公司认定原因：</span>
							<span class="margin_top_15">
								<span class="business-span">${mfPactFiveclass.confirmReason}</span> 
							</span>
						</div>
				   		<div class="col-md-3 column">
							<span>客户经理：</span>
							<span class="margin_top_15">
								<span class="business-span">${mfPactFiveclass.opName}</span> 
							</span>
						</div>
				   		<div class="col-md-3 column">
							<span>客户经理调整日期：</span>
							<span class="margin_top_15">
								<span class="business-span">${mfPactFiveclass.regTime}</span> 
							</span>
						</div>
						<div class="col-md-1 column">
							<span>客户经理调整原因：</span>
							<span class="margin_top_15">
							<span class="business-span">${mfPactFiveclass.changeReason}</span> 
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="getBack()"></dhcc:thirdButton>
		</div>
	</body>
</html>
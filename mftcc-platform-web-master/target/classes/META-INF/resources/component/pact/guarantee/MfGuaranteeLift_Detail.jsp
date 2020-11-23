<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<link type="text/css" rel="stylesheet" href="${webPath}/UIplug/umeditor-dev/themes/default/_css/umeditor.css"/>
		<script type="text/javascript" src="${webPath}/UIplug/umeditor-dev/umeditor.config.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/umeditor-dev/editor_api.js"></script>
		<script type="text/javascript" >
            var pactId = "${pactId}";
            var id = "${id}";
            var guaId = "${guaId}";
            var queryFile = "${queryFile}";
            var aloneFlag = true;
            var dataDocParm={
                relNo:guaId,
                docType:'guarantee',
                docTypeName:"",
                docSplitName:"保函要件",
                query:queryFile
            };
            var ue;
            window.UPLOAD_IMG_URL = webPath + "/ueditor?action=uploadimage";
            window.UPLOAD_IMAGE_PATH = webPath;
            $(function(){
                _getUeditor();
                $(".scroll-content").mCustomScrollbar({
                    advanced:{
                        //滚动条根据内容实时变化
                        updateOnContentResize:true
                    }
                });
				showApproveHis();
            });

            //展示审批历史
            function showApproveHis(){
                //获得审批历史信息
                showWkfFlowVertical($("#wj-modeler2"),id,"23","guarantee_lift_approval");
            }
            var _getUeditor = function () {
                $("[name='courierNo']").hide();
                var ueditorHtml = '<script id="courierNo"  type="text/plain"style="width:100%;height:390px;"><\/script>';
				var oldVal=$('textarea[name="courierNo"]').val(); //获取文本原来的值
				$("[name='courierNo']").after(ueditorHtml);
				ue = UM.getEditor('courierNo', {
				width : "100%",
				initialFrameHeight:320,
				});
				ue.ready(function () {
				ue.setContent(decodeURIComponent(oldVal));
				ue.setDisabled();
				});
		};
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-xs-10 col-xs-offset-1" style="margin-top:20px;">
					<div class="arch_sort" style="border: 0px;">
						<div class="dynamic-block" title="解保申请" name="MfGuaranteeLiftAction" data-sort="14" data-tablename="mf_guarantee_lift">
							<div class="list-table">
								<div class="title">
									<span class="formName"><i class="i i-xing blockDian"></i>解保申请详情</span>
									<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfGuaranteeLiftAction">
										<i class="i i-close-up"></i><i class="i i-open-down"></i>
									</button>
								</div>
								<div disable="true" class="content collapse in" id="MfGuaranteeLiftAction" style="margin-top: 10px;">
									<div class="bootstarpTag">
										<form method="post" id="MfGuaranteeLiftActionForm" theme="simple" name="operform" action="">
											<dhcc:bootstarpTag property="formguaranteeLiftDetail" mode="query"/>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="arch_sort" style="border: 0px;">
						<div class="dynamic-block" title="保函要件" name="MfGuaranteeLiftFileAction" data-sort="14" data-tablename="mf_guarantee_lift_file">
							<div class="list-table">
								<div class="title">
									<span class="formName"><i class="i i-xing blockDian"></i>保函要件</span>
									<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfGuaranteeLiftFileAction">
										<i class="i i-close-up"></i><i class="i i-open-down"></i>
									</button>
								</div>
								<div disable="true" class="content collapse in" id="MfGuaranteeLiftFileAction" style="margin-top: 10px;">
									<div class="row clearfix">
										<div class="col-xs-12 column">
											<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<c:if test = "${liftType != '1'}">
					<div class="arch_sort">
						<div id="guaranteeLiftApproveHis" class="row clearfix">
							<div class="col-xs-12 column info-block">
								<div id="spInfo-block" class="approval-hist">
									<div class="list-table">
										<div class="title">
											<span><i class="i i-xing blockDian"></i>解保申请审批历史</span>
											<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
												<i class="i i-close-up"></i><i class="i i-open-down"></i>
											</button>
										</div>
										<div class="content margin_left_15 collapse in " id="spInfo-div">
											<div class="approval-process">
												<div id="modeler1" class="modeler">
													<ul id="wj-modeler2" class="wj-modeler" isApp = "false">
													</ul>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					</c:if>
				</div>
	   		</div>
			<div id="approvalBtn" class="formRowCenter " style="display:block;">
				<c:if test = "${from == null}">
					<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
				</c:if>
			</div>
	   	</div>
	</body>
</html>
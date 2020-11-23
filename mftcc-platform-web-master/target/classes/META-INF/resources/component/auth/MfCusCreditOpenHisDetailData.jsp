<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>授信申请表单详情</title>
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/echarts-all.js"></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js'></script>
		<script type="text/javascript" src="${webPath}/themes/factor/js/viewpointggl.js"></script>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditOpenHisDetailData.js'></script>
		<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}">
		<script type="text/javascript" src='${webPath}/component/auth/js/MfCusCreditTemplate.js'></script>
		<!-- <script type='text/javascript' src='dwr/engine.js'></script>  
	<script type='text/javascript' src='dwr/util.js'></script>
	<script type="text/javascript" src="${webPath}/dwr/interface/PageMessageSend.js"></script>   -->
	<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
			var menuBtn = [];
			var menuUrl = [];
			var basePath = "<%=basePath%>";
			var viewPointData = eval("("+'<%=request.getSession().getAttribute("viewPointData") %>'+")");
			var vpNo = '8';
			var cusNo = "${cusNo}";
			var creditAppId = "${creditAppId}";
			var appId = "${creditAppId}";
			var creditApproveId = "${creditApproveId}";
			var pactId = "${pactId}";
			var scNo = "${scNo}";
			var cusNo = "${cusNo}";
			var docParm = "cusNo="+cusNo+"&relNo="+appId+"&scNo="+scNo;//查询上传文档信息的url的参数
			var query = "${query}";     //该值标志位是否可以上传
			var index = 0; 
			var showParmsJson = eval("(" + '${showParmsJson}' + ")");
			var productTitle = eval("(" + '${productTitleJson}' + ")");
			var productValue = eval("(" + '${productValueJson}' + ")");
			var beginDate = creditHandleUtil.convertToYYYYMMDD("${mfCusCreditUseHis.beginDate}");
			var endDate = creditHandleUtil.convertToYYYYMMDD("${mfCusCreditUseHis.endDate}");
			/* var aloneFlag = true;
			var dataDocParm={
					relNo:appId,
					docType:"creditDoc",
					docTypeName:"尽职报告要件资料",
					docSplitName:"尽职报告资料",
					query:query,
			}; */
			var tempType="ALL";//尽调报告和授信协议
			var relId=pactId;
			var userId = '<%=(String)request.getSession().getAttribute("regNo")%>';
			$(function(){
				mfCusCreditOpenHisDetailData.init();
				//加载尽调尽调报告模板
				//MfCusCreditTemplate.template_init();
			});
		</script>
		<style type="text/css">
			.list-table .title span, .form-table .title span {
			    font-size: 16px;
			    font-weight: bold;
			    height: 37px;
			    line-height: 37px;
			    margin-left: 24px;
		    }
		    .content .fieldShow {
			    max-width: 14em;
			}
		</style>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="bg-white">
				<div class="row clearfix">
					<div class="col-xs-12 column">
						<div name="auth" title="授信申请信息" class="dynamic-block" style="">
							<div class="" style="margin-top: 71px;margin-left: 76px;line-height:30px;height: 250px;">
								<p>
									<span id="cusName" style="font-size: 25px;">
									${mfCusCreditUseHis.cusName}
									&nbsp;&nbsp;</span><br>
								</p>
								<p>
									<c:if test="${mfCusCreditUseHis.creditUseType == 1}"><span class="creditSumTitle">申请金额&nbsp;&nbsp;&nbsp;&nbsp;</span></c:if>
									<c:if test="${mfCusCreditUseHis.creditUseType == 3}"><span class="creditSumTitle">业务占用金额&nbsp;&nbsp;&nbsp;&nbsp;</span></c:if>
									<c:if test="${mfCusCreditUseHis.creditUseType == 6}"><span class="creditSumTitle">还款释放金额&nbsp;&nbsp;&nbsp;&nbsp;</span></c:if>
									<span  id="creditSum" class="creditSum" style="color: 32B5CB;font-size:30px;">
									${mfCusCreditUseHis.applySum}</span>
									<span class="creditSumTitle" style="margin-left:10px;">万</span><br>
								</p>
								<p>	
									<span class="creditDate">期限：&nbsp;&nbsp;&nbsp;&nbsp;</span>
									<span id="creditbeginDate" class="creditbeginDate" style="color:32B5CB;">&nbsp;&nbsp;</span>
									&nbsp;&nbsp;至&nbsp;&nbsp;<span id="creditendDate" class="creditendDate" style="color:32B5CB;">&nbsp;&nbsp;</span>
								</p>
								<p>
									<span id="creditisCeilingLoop" class="creditisCeilingLoop">
									额度是否循环：&nbsp;&nbsp;</span>
									<span id="isCeilingLoop" class="isCeilingLoop" style="color:32B5CB;">
										<c:if  test='${mfCusCreditUseHis.isCeilingLoop == 1}'>
											是
										</c:if>
										<c:if  test='${mfCusCreditUseHis.isCeilingLoop == 0}'>
											否
										</c:if>
									</span>
								</p>
							<%-- 	<s:iterator value="mfCusPorductCredits">
									<p>${kindName}&nbsp;&nbsp;&nbsp;&nbsp;
									${CreditKindAmt}
									<span class="creditSumTitle" style="margin-left:10px;">万</span>
									</p>
								</s:iterator> --%>
								
								<c:forEach items="${mfCusPorductCredits}" var="mfCusPorductCredit" varStatus="status">
									<p>${mfCusPorductCredit.kindName}&nbsp;&nbsp;&nbsp;&nbsp;
									${mfCusPorductCredit.CreditKindAmt}
									<span class="creditSumTitle" style="margin-left:10px;">万</span>
									</p>
								</c:forEach>
								<p id="creditRemak" class="creditRemak" style="width:46%;word-wrap:break-word;">
									备注：${mfCusCreditUseHis.remark}
								</p>
							</div>
							<div class="" style="position:absolute;margin-top:-240px;margin-left:28em;">
								<c:if  test='${mfCusCreditUseHis.creditSts == 1}'>
									<img src="${webPath}/component/auth/image/credit_appliying.png">
								</c:if>
								<c:if  test='${mfCusCreditUseHis.creditSts == 2}'>
									<img src="${webPath}/component/auth/image/credit_approving.png">
								</c:if>
								<c:if  test='${mfCusCreditUseHis.creditSts == 3}'>
									<img src="${webPath}/component/auth/image/approve-ok.png">
								</c:if>
								<c:if  test='${mfCusCreditUseHis.creditSts == 4}'>
									<img src="${webPath}/component/auth/image/credit_no.png">
								</c:if>
								<c:if  test='${mfCusCreditUseHis.creditSts == 5}'>
									<img src="${webPath}/component/auth/image/signed.png">
								</c:if>
							</div>
							<div id="productInfo" style="width: 600px;height:380px; margin-top:-304px;margin-left:52%"></div>
						</div>
						<div style="clear:both;"></div>
						<div class=" list-table " name="authPlege" title="反担保信息">
							<div class="title">
								<span> <i class="i i-xing blockDian"></i>反担保信息</span>
								<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#applyHis">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
							</div>	
								<div id="applyHis"class="collapse in">
								<div class="row clearfix " >
								 	<div class="col-xs-12 column">
								 		<div class="form-table base-info">
											<div class="content">
												<form  method="post" theme="simple" name="pedgeInfoform" id = "pedgeInfoform">
													<dhcc:propertySeeTag property="formpledydetail0001_new" mode="query"/>
												</form>
											</div>
										</div>
								 	</div>
								 </div>
								 </div>
							</div>
						</div>
						<div style="clear:both;"></div>
							
						<div name="authDocInfo" title="尽职报告要件信息" class="dynamic-block">
							<div class="row clearfix" style="width:100%;margin:0 auto;">
								<div class="col-xs-12 column">
									<%-- <%@ include file="/component/doc/webUpload/uploadutil.jsp"%> --%>
								</div>
							</div>
						</div>
						<div style="clear:both;"></div>
							
						<div name="authProtocolInfo" title="授信签约信息" class="dynamic-block list-table" style="display: none;">
							<div class="title">
								<span> <i class="i i-xing blockDian"></i> 授信签约信息
								</span>
								<button class="btn btn-link pull-right formAdd-btn"
									data-toggle="collapse" data-target="#authProtocolInfoDiv">
									<i class='i i-close-up'></i> <i class='i i-open-down'></i>
								</button>
							</div>
							<div class="row clearfix collapse in" id="authProtocolInfoDiv">
								<div class="col-xs-12 column">
									<div class="form-table base-info">
										<div class="content">
											<form  method="post" theme="simple" id="authProtocolForm">
												<dhcc:propertySeeTag property="formcreditpact0001_new"
													mode="query" />
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div style="clear:both;"></div>
						<div name="creditTemplateInfo" title="授信模板信息" class="dynamic-block list-table" style="display: none;">
							<div class="title">
								<span> <i class="i i-xing blockDian"></i> 授信资料
								</span>
								<button class="btn btn-link pull-right formAdd-btn"
									data-toggle="collapse" data-target="#creditTemplateInfoDiv">
									<i class='i i-close-up'></i> <i class='i i-open-down'></i>
								</button>
							</div>
							<div class="row clearfix collapse in" id="creditTemplateInfoDiv">
								<!-- 尽调模板模板 -->
								<div class="col-md-12 col-md-offset-0 column" >
									<%@ include file="/component/include/biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
								</div>
								<%-- <div class="col-xs-12 column">
									<div class="form-table base-info">
										<div class="content">
											<form  method="post" theme="simple" id="creditTemplateForm">
												<dhcc:propertySeeTag property="formcredittemplateinfo"
													mode="query" />
											</form>
										</div>
									</div>
								</div> --%>
							</div>
						</div>
						<div style="clear:both;"></div>
						<div name="authApproveInfo" title="授信审批信息" class="dynamic-block approval-hist">
							<div class="list-table">
								<div class="title" style="background:#f8f9fc;height: 37px;">
									 <span><i class="i i-xing blockDian"></i>审批历史</span>
									 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
										<i class='i i-close-up'></i>
										<i class='i i-open-down'></i>
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
						<div style="clear:both;"></div>
						
						<%-- <div class="formRowCenter">
							 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="mfCusCreditOpenHisData.close();"></dhcc:thirdButton>
						</div> --%>
				</div>	
			</div>
		</div>	
	</body>
</html>
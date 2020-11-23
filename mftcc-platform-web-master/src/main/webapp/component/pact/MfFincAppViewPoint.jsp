<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String fincId = (String)request.getParameter("fincId");
String pactId = (String)request.getParameter("pactId");
%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src="${webPath}/component/pact/js/MfBusBankAccCom.js"></script>
		<script type="text/javascript" src="${webPath}/component/pact/js/MfBusFincAppInsert.js"></script>
		<script type="text/javascript">
			var cusNo = '${mfBusFincAppChild.cusNo}';
			var appId = '${mfBusFincAppChild.appId}';
			var pactId = '${mfBusFincAppChild.pactId}';
			var fincId = '${mfBusFincAppChild.fincId}';
			var fincChildId = '${mfBusFincAppChild.fincChildId}';
			var storageMergeFlag = '${mfBusPact.storageMergeFlag}';
			var vouType = '${mfBusPact.vouType}';
			var nodeNo = '${nodeNo}';
            var approvalNodeNo = '${approvalNodeNo}';// 用于加载配置在审批节点要件或模板, primary_apply_approval-初选审批, apply_approval-融资审批, contract_approval-合同审批, certidInfo_appr-权证审批, putout_approval-支用审批
			var ajaxData = '${ajaxData}';
			var projectName = '${projectName}';
		  		ajaxData = JSON.parse(ajaxData);
			$(function(){
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
				bindVouTypeByKindNo($("input[name=vouType]"), '');
				var collectAccIdClass = $("input[name=collectAccount]").parents("td").attr("class");
				MfBusBankAccCom.bankAccInit();
				//还款方式处理
				var $repayObj = $("input[name=repayType]");
				$("input[name=repayType]").popupSelection({
					searchOn:false,//启用搜索
					inline:false,//下拉模式
					multiple:false,//多选选
					title:"还款方式",
					valueClass:"show-text",
					items:ajaxData.repayTypeMap,
					changeCallback : function (obj, elem) {
					}
				}); 
				if(typeof($repayObj.attr('readonly'))!='undefined'){
					$repayObj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
				} 
				var $repayDateSetObj = $("input[name=repayDateSet]");
				if($repayDateSetObj.attr('type') != 'hidden'){
					$("input[name=repayDateSet]").popupSelection({
						searchOn:false,//启用搜索
						inline:false,//下拉模式
						multiple:false,//多选选
						title:"还款日设置",
						valueClass:"show-text",
						items:ajaxData.repayDateSetMap,
						changeCallback : function ( elem) {
							var callBackVal = elem.data("values").val();
							var callBackValArr = callBackVal.split("_");
                            if(callBackValArr[1] == "3"){
                                $("input[name=repayDateDef]").parents("td").show();// 字段td
                                $("input[name=repayDateDef]").parents("td").prev("td").show();// 标签td
                                $("input[name=repayDateDef]").removeAttr("readonly");
                            } else {
                                $("input[name=repayDateDef]").parents("td").hide();// 字段td
                                $("input[name=repayDateDef]").parents("td").prev("td").hide();// 标签td
                                $("input[name=repayDateDef]").attr("readonly","readonly");
                            }

						}
					}); 
				}
                if(typeof($repayDateSetObj.attr('readonly'))!='undefined'){
                    $repayDateSetObj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
                }
                //初始化判断如果不是是固定还款日（3_3），则为repayDateDef为只读
                if($repayDateSetObj.attr('value')!='3_3'){
                    $("input[name=repayDateDef]").parents("td").hide();// 字段td
                    $("input[name=repayDateDef]").parents("td").prev("td").hide();// 标签td
                    $("input[name=repayDateDef]").attr("readonly","readonly");
                }
				//是否入库标识为不入库，或是担保方式为信用担保，同意入库字段隐藏
				if(storageMergeFlag=="0"||vouType=="1"){
					$("select[name=agreeStorageFlag]").parents("tr").remove();
				}
				if(nodeNo=="finc_supplement_data"){
					$("[name=opinionType]").parents("tr").hide();
				}
				
			});
			
			//账号与合同不一致提示次数
			var validateCnt = 0;
			//验证银行卡账号是否被修改
			function validateAccIsModify(){
				var dataParam = JSON.stringify($("#edit-form").serializeArray());
				//判断放款审批环节的账号信息与合同签约阶段的是否一致，不一致的情况下，给出提醒
				jQuery.ajax({
					url:webPath+"/mfBusFincApp/validateAccIsModifyAjax",
					data:{ajaxData:dataParam,appId:appId},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						if(data.flag == "error"){
							validateCnt = validateCnt+1;
							window.top.alert(top.getMessage("NOT_ACCNO_SAME_WARN",data.msg),4);
						}
					}
				});
			};
			 //审批页面
			 function getApprovaPage(){
			 	// $("#infoDiv").css("display","none");
			 	// $("#approvalBtn").css("display","none");
			 	// $("#approvalDiv").css("display","block");
			 	// $("#submitBtn").css("display","block");
			 	if(validateCnt==0){
			 		validateAccIsModify();
			 	}
			 };
			 // //返回详情页面
			 // function approvalBack(){
			 // 	$("#infoDiv").css("display","block");
			 // 	$("#approvalBtn").css("display","block");
			 // 	$("#approvalDiv").css("display","none");
			 // 	$("#submitBtn").css("display","none");
			 // };
			
			 //审批提交
			function doSubmit(obj){
				var opinionType = $("[name=opinionType]").val();
				var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					commitProcess(webPath+"/mfBusFincApp/updateProcess?opinionType="+opinionType+"&appNo="+fincChildId+"&fincId="+"<%=fincId%>",obj,'applySP');
				}
			};
            /*从合同方法--------------start*/
            function getTemplateBizConfigId(obj , id){//获取从合同
                id = id.substring(10);
                $.ajax({
                    url:webPath+"/mfBusCollateralDetailRel/getTemplateBizConfigIdAjax",
                    data:{id:id},
                    type:'post',
                    dataType:'json',
                    success:function(data){
                        if(data.flag == "success"){
                            printFollowPactFile(data.templateBizConfigId ,data.repayDetailId);
                        }else{
                            window.top.alert(data.msg,0);
                        }
                    },error:function(){
                        window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
                    }
                });
            }
            function printFollowPactFile (templateBizConfigId , repayDetailId) {//点击查看按钮或者合同打印
                var url = webPath+"/mfTemplateBizConfig/getOfficeUrlAjax?templateBizConfigId=" + templateBizConfigId;
                var backUrl = encodeURIComponent(location.href);// 关闭office文档时返回目前的页面
                var temParm = 'cusNo=' + cusNo + '&appId=' + appId + '&pactId=' + pactId + '&repayDetailId=' + repayDetailId;
                $.ajax({
                    url : url + "&" + temParm,
                    data : {
                        "returnUrl" : backUrl
                    },
                    type : 'post',
                    dataType : 'json',
                    beforeSend : function() {
                        LoadingAnimate.start();
                    },
                    complete : function() {
                        LoadingAnimate.stop();
                    },
                    error : function() {
                        alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
                    },
                    success : function(data) {
                        var poCntObj = $.parseJSON(data.poCnt);
                        mfPageOffice.openPageOffice(poCntObj);
                    }
                });
            };
            function editFollowPactNo(obj , id){//编辑从合同号
                id = id.substring(10);
                $(obj).hide();
                $(obj).after("<input name=\"followPactNo\" style=\"width:165px;text-align: center;\" value=\"\" maxlength=\"30\" type=\"text\" onblur=\"updateFollowPactNo(this,'" + id + "');\">");
                $("input[name='followPactNo']")[0].focus();
            }
            /*从合同方法--------------end*/
					
		</script>
</head>
 
<body class="overFlowHidden bg-white">
	<div class="container form-container">
		<div id="infoDiv" style="height:100%;width:75%;float: left;">

		<c:choose>
			<c:when test="${sysProjectName=='WangXin'}">
				<iframe src="${webPath}/mfBusPact/getById?pactId=${mfBusFincApp.pactId }&busEntrance=6&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
			</c:when>
			<c:otherwise>
				<iframe src="${webPath}/mfBusPact/getById?pactId=${mfBusFincApp.pactId }&busEntrance=finc_approve&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
			</c:otherwise>
		</c:choose>

		</div>
		<div id="approvalDiv" class="scroll-content" style="width: 25%;float: right;">
			<div style="text-align: center;font-size: 16px;">
				<a href="#formButton" style="padding:0px 25px 0px 25px;">审批</a>
				<a href="#fileButton" id="buttonFile" style="padding:0px 25px 0px 25px;display: none;">电子文档</a>
				<a href="#docButton" id="buttonDoc" style="padding:0px 25px 0px 25px;display: none;">调查资料</a>
			</div>
			<div class="col-md-10 col-md-offset-1 column margin_top_20" >
				<div class="bootstarpTag fourColumn" id="formButton">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="edit-form">
						<dhcc:bootstarpTag property="formfincapp0005" mode="query" />
					</form>
				</div>
				<%@ include file="/component/include/approval_biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
			</div>
		</div>
		<%--<div id="approvalBtn" class="formRowCenter " style="display:block;">--%>
			<%--<dhcc:thirdButton value="审批" action="审批" onclick="getApprovaPage();"></dhcc:thirdButton>--%>
			<%--<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>--%>
		<%--</div>--%>
		<div id="submitBtn" class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#edit-form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
	</div>
	<input name="taskId" id="taskId" type="hidden" value="${taskId}" />
	<input name="activityType" id="activityType" type="hidden" value="${activityType}" />
	<input name="isAssignNext" id="isAssignNext" type="hidden" value="${isAssignNext}" />
	<input name="transition" type="hidden" />
	<input name="nextUser" type="hidden" />
	<input name="designateType" type="hidden" value="${designateType}" />
</body>
<script type="text/javascript">
	$(document.body).height($(window).height());
</script>
  <style>
	  .block-left {
		  padding-top: 10px;
		  padding-right: 5px;
		  padding-left: 2px;
	  }
	  .col-md-offset-1 {
		  margin-left: 3%;
	  }
	  .bootstarpTag .table>tbody>tr>td, .bootstarpTag .table>tbody>tr>th, .bootstarpTag .table>tfoot>tr>td, .bootstarpTag .table>tfoot>tr>th, .bootstarpTag .table>thead>tr>td, .bootstarpTag .table>thead>tr>th {
		  padding: 2px 2px;
		  line-height: 1.42857143;
		  vertical-align: middle;
		  border-top: 1px solid #ddd;
	  }
	  .bootstarpTag.fourColumn .tdlable {
		  width: 105px;
		  min-width: 105px;
	  }
	  .mCustomScrollBox {
		  background-color: #fff;
	  }
	  .mCSB_draggerRail {
		  background: #fff;
		  width: 9px;
		  border-radius: 3px;
	  }
	  .upload-div .filelist {
		  padding: 0px 0px 10px 30px;
	  }
	  .form-container {
		  padding-bottom: 0px;
	  }
	  .scroll-content {
		  border-top: 10px solid #F0F5FB;
		  border-right: 9.5px solid #F0F5FB;
	  }
  </style>
</html>
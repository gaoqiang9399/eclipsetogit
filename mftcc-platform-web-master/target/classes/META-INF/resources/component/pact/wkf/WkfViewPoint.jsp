<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%
String pactId = (String)request.getParameter("pactId");
String appId =  (String)request.getParameter("appId");
String vouType =  (String)request.getParameter("vouType");
String cusType = (String)request.getParameter("cusType");
%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript">
			var cusNo = '${mfBusPact.cusNo}';
			var appId = '${mfBusPact.appId}';
			var pactId = '${mfBusPact.pactId}';
			var primaryPactId = '${mfBusPact.primaryPactId}';
            var isPrimary = '${isPrimary}';
			var ajaxData = JSON.parse('${ajaxData}');
			var cmpdRateType='${mfBusPact.cmpFltRateShow}';
			var storageMergeFlag = '${mfBusPact.storageMergeFlag}';
			var feePower = '2';
			var nodeNo = '${nodeNo}';
            var approvalNodeNo = '${approvalNodeNo}';// 用于加载配置在审批节点要件或模板, primary_apply_approval-初选审批, apply_approval-融资审批, contract_approval-合同审批, certidInfo_appr-权证审批, putout_approval-支用审批
			var querySaveFlag = '${querySaveFlag}';
			var vouType='${mfBusPact.vouType}';
            //添加项目标识原因，审批初始化弹出层的字段有的客户不需要没配置，还在后面展示。（这个问题解决了可以去掉标识）
            var checkCode = '${checkCode}';
            var baseRateChange = '${baseRateChange}';
			if(nodeNo=='fee_approval'){
				feePower = '3';
			}
			$(function(){
				// 是否隐藏 复利利率上浮字段
				if(cmpdRateType =="0"){//隐藏						
					$('input[name=cmpdFloat]').parents("td").hide();// 字段td
					$('input[name=cmpdFloat]').parents("td").prev("td").hide();// 标签td
				}
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
					//意见类型新版选择组件
				$('select[name=opinionType]').popupSelection({
					inline: true, //下拉模式
					multiple: false, //单选
					changeCallback:WkfApprove.opinionTypeChange
				});
	 			//是否入库标识为不入库，或是担保方式为信用担保，同意入库字段隐藏
				if(storageMergeFlag=="0"||vouType=="1"){
					$("select[name=agreeStorageFlag]").parents("tr").remove();
				}
				if(nodeNo=="pact_supplement_data"){
					$("[name=opinionType]").parents("tr").hide();
				}
                if (baseRateChange == "false") {
                    window.top.alert("市场报价利率已重新发布,该业务将按照新的市场报价利率进行执行", 3);
                }
			});
			
			
			 // //审批页面
			 // function getApprovaPage(){
			 // 	$("#infoDiv").css("display","none");
			 // 	$("#approvalBtn").css("display","none");
			 // 	$("#approvalDiv").css("display","block");
			 // 	$("#submitBtn").css("display","block");
			 // }
			 // //返回详情页面
			 // function approvalBack(){
			 // 	$("#infoDiv").css("display","block");
			 // 	$("#approvalBtn").css("display","block");
			 // 	$("#approvalDiv").css("display","none");
			 // 	$("#submitBtn").css("display","none");
			 // }
			
			 //审批提交
			function doSubmit(obj){
				var opinionType = $("[name=opinionType]").val();//下拉框换成选择组件后，直接从input中取值
				var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
                    if(isPrimary=="1"){//合同清稿审批
                        commitProcess(webPath+"/mfBusPactWkf/submitUpdateForPrimaryAjax?opinionType="+opinionType+"&pactId="+pactId+"&primaryPactId="+primaryPactId+"&appNo="+primaryPactId,obj,'applySP');
					}else{
                        commitProcess(webPath+"/mfBusPactWkf/submitUpdateAjax?opinionType="+opinionType+"&pactId="+pactId+"&appNo="+pactId,obj,'applySP');
                    }
				}
			}

			 function getPactDoc(){
                $.ajax({
                		url:webPath+"/mfTemplateModelRel/getIfSaveModleInfo",
                         type:"post",
                         data:{"relNo":"<%=pactId%>"},
                         async: false,
                         dataType:"json",
                         error:function(){alert('error')},
                         success:function(data){
                              var filepath = "";
                              /* if(data.flag=="0"){
                            	  type = "add";
                                  filepath = "/factor/component/model/docmodel/";
                              }else{
                                  type = "detail";
                                  filepath = "/factor/component/model/docword/";
                              } */
                              var modelid = "dczyp";
                              var filename = "dczyp.doc";
                              var returnUrl = window.location.href;
                              var urlParm=returnUrl.split("?")[1];
          					  returnUrl=returnUrl.split("?")[0];
          					  urlParm = encodeURIComponent(urlParm);
                              window.location.href=webPath+"component/model/toPageOffice.jsp?cifno="+cusNo+"&modelid="+modelid+"&filename="+filename+"&pactno=<%=pactId%>&appno=<%=appId%>&loanNo=&traceNo=&returnUrl="+returnUrl+"&type="+type+"&filepath="+filepath+"&urlParm="+urlParm;
                         }
               });
          } 
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
			/* 校验批复金额不能大于申请金额 */
			function checkPactAmt(obj) {
				var pactAmtShow = $("input[name=pactAmtShow]").val();//申请金额
				var pactAmt = $("input[name=pactAmt]").val();//批复金额
				pactAmtShow = pactAmtShow.replace(/,/g, '');
				pactAmt = pactAmt.replace(/,/g, '');
				if (parseFloat(pactAmt) > parseFloat(pactAmtShow)) {
					$("input[name=pactAmt]").val(pactAmtShow);
					window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne" : "批复金额", "timeTwo" : "合同金额"}), 3);
				}
			}


            //处理基础利率以及浮动点数及百分比（输入期限或者修改期限时使用）
            function getCalcRateByTermAjax() {
                var term = $("[name=term]").val();//申请期限
                var termType = $("[name=termType]").val();//期限类型
                var kindNo = $("[name=kindNo]").val();//产品号
                var fincRate = $("[name=fincRate]").val();//申请利率
                var baseRateType = $("[name=baseRateType]").val();//基础利率类型
                var rateType = $("[name=rateType]").val();//执行利率类型
                $.ajax({
                    type: "post",
                    dataType: 'json',
                    url: webPath + "/mfBusApply/getCalcRateByTermAjax",
                    data: {
                        term: term,
                        termType: termType,
                        kindNo: kindNo,
                        fincRate: fincRate,
                        baseRateType: baseRateType,
                        rateType: rateType
                    },
                    async: false,
                    success: function (data) {
                        if (data.flag == "success") {
                            if (baseRateType == "4") {
                                $("input[name=baseRate]").val(data.baseRate);
                                $("input[name=floatNumber]").val(data.floatNumber);
                                $("input[name=lprNumber]").val(data.rateNo);
                            } else {
                                $("input[name=baseRate]").val(data.baseRate);
                                $("input[name=fincRateFloat]").val(data.fincRateFloat);
                            }
                        } else {
                            window.alert(data.msg, 0);
                        }
                    },
                    error: function () {
                        window.alert("输入或修改期限处理利率失败", 0);
                    }
                });
            };

            //处理以及浮动点数及百分比（输入执行利率或修改逾期利率时使用）
            function getCalcRateByFincRateAjax() {
                var term = $("[name=term]").val();//申请期限
                if (term == "") {
                    window.alert("请先输入期限", 0);
                    $("input[name=fincRate]").val("");
                    return false;
                }
                var baseRate = $("input[name=baseRate]").val();//基础利率
                var fincRate = $("[name=fincRate]").val();//执行利率
                var baseRateType = $("[name=baseRateType]").val();//基础利率类型
                var rateType = $("[name=rateType]").val();//利率类型
                var kindNo = $("[name=kindNo]").val();//产品号
                $.ajax({
                    type: "post",
                    dataType: 'json',
                    url: webPath + "/mfBusApply/getCalcRateByFincRateAjax",
                    data: {
                        fincRate: fincRate,
                        baseRateType: baseRateType,
                        rateType: rateType,
                        kindNo: kindNo,
                        baseRate: baseRate
                    },
                    async: false,
                    success: function (data) {
                        if (data.flag == "success") {
                            if (baseRateType == "4") {
                                $("input[name=floatNumber]").val(data.floatNumber);
                            } else {
                                $("input[name=fincRateFloat]").val(data.fincRateFloat);
                            }
                        } else {
                            window.alert(data.msg, 0);
                        }
                    },
                    error: function () {
                        window.alert("输入或修改执行利率处理失败", 0);
                    }
                });
            };

		</script>
</head>
 
<body class="overflowHidden bg-white" style="height: 100%;">
	<div class="container form-container">
		<div id="infoDiv" style="height:100%;width:75%;float: left;">
			<iframe src="${webPath}/mfBusPact/getById?pactId=${pactId }&busEntrance=pact_approve&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
		</div>
		<div id="approvalDiv" class="scroll-content" style="width: 25%;float: right;">
			<div style="text-align: center;font-size: 16px;">
				<a href="#formButton" style="padding:0px 25px 0px 25px;">表单</a>
				<a href="#fileButton" id="buttonFile" style="padding:0px 25px 0px 25px;display: none;">电子文档</a>
				<a href="#docButton" id="buttonDoc" style="padding:0px 25px 0px 25px;display: none;">调查资料</a>
			</div>
			<div class="col-md-10 col-md-offset-1 column margin_top_20" >
				<div class="bootstarpTag fourColumn" id="formButton">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="edit-form"  >
						<dhcc:bootstarpTag property="formpact0005" mode="query" />
					</form>
					<c:if test="${nodeNo =='pact_supplement_data'}">
					<div id="coborrNumName" class="row clearfix" style="display: none">
						<%@ include file="/component/app/MfBusCoborrPactList.jsp"%>
					</div>
					</c:if>
					<%--<c:if test="${mfBusFollowPactList != null}">--%>
							<%--<div class="list-table">--%>
								<%--<div class="title">--%>
									<%--<span><i class="i i-xing blockDian"></i>从合同文档</span>--%>
									<%--<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#followPact">--%>
										<%--<i class="i i-close-up"></i>--%>
										<%--<i class="i i-open-down"></i>--%>
									<%--</button>--%>
								<%--</div>--%>
								<%--<div id="followPact" class="content collapse in" aria-expanded="true">--%>
									<%--<dhcc:tableTag paginate="mfBusFollowPactList" property="tablemfBusFollowPactNo" head="true" />--%>
								<%--</div>--%>
							<%--</div>--%>
						<%--</c:if>	--%>
				</div>
				<%--<%@ include file="/component/include/approval_biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->--%>
			</div>
		</div>
		<div id="submitBtn" class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#edit-form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
	</div>
	<input name="taskId" id="taskId" type="hidden" value="${taskId }" />
	<input name="activityType" id="activityType" type="hidden" value="${activityType }" />
	<input name="isAssignNext" id="isAssignNext" type="hidden" value="${isAssignNext }" />
	<input name="transition" type="hidden" />
	<input name="nextUser" type="hidden" />
	<input name="designateType" type="hidden" value="${designateType }" />
</body>
<script type="text/javascript">
		$(document.body).height($(window).height());
</script>
  <script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
  <script type="text/javascript" src="${webPath}/component/pact/js/pactWkfViewPoint.js"></script>
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
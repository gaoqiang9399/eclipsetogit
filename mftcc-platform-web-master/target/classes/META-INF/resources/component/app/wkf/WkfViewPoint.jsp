<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java" import="java.util.*,net.sf.json.JSONArray"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src="${webPath}/component/pact/js/MfBusBankAccCom.js"></script>
     	 <script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_applyInput.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" src="${webPath}/component/include/calcUtil.js"></script>
	  <script type="text/javascript">
			var cusNo = '${mfBusApply.cusNo}';
			var appId = '${mfBusApply.appId}';
			var primaryAppId = '${mfBusApply.primaryAppId}';
			var isPrimary = '${isPrimary}';
			var termType = '${mfBusApply.termType}';
			var ajaxData = JSON.parse('${ajaxData}');
			var mfSysKind = '${mfSysKind}';
			var from = '${from}';
			var busModel = '${busModel}';
			var appTerm = '${mfBusApply.term}';
			var maxTerm = '${mfSysKind.maxTerm}';
			var minTerm = '${mfSysKind.minTerm}';
			var unit = termType=="1"?"个月":"天";
			var minTime = minTerm+unit;
			var maxTime = maxTerm+unit;
			var cmpdRateType='${mfBusApply.cmpFltRateShow}';
			var approveRefuseType='${approveRefuseType}';//是否展示拒单原因:1-是 0-否
			var nodeNo = '${nodeNo}';
            var approvalNodeNo = '${approvalNodeNo}';// 用于加载配置在审批节点要件或模板, primary_apply_approval-初选审批, apply_approval-融资审批, contract_approval-合同审批, certidInfo_appr-权证审批, putout_approval-支用审批
			var processId = '${mfBusApply.applyProcessId}';
            var baseRateChange = '${baseRateChange}';
            var creditAppId = '${mfBusApply.creditAppId}';
            var breedInitFlag=0;
            var agenicesInitFlag=0;
			$(function(){
				if($("input[name=term]").length>0){
					$("input[name=term]").bind("change",function(){
						checkTerm();
					});
				}
                breedInit1();
				if ($("input[name=vouType]").length > 0) {
					$("input[name=vouType]").popupSelection({
						searchOn: true,//启用搜索
						inline: false,//下拉模式
						multiple: false,//多选
						valueClass: "show-text",
						items: ajaxData.map,
						title: "担保方式",
						handle: false
					});
					$("input[name=vouType]").parent().children(".pops-value").unbind("click");
					$("input[name=vouType]").parent().children(".pops-value").children().children(".pops-close").remove();
				}
				if($("select[name=pledgeMethod]").is(':visible')){
					$('select[name=pledgeMethod]').popupSelection({
						searchOn: false, //启用搜索
						inline: true, //下拉模式
						multiple: false, //单选
						changeCallback : function (obj, elem) {
						}
					});
					bindVouTypeByKindNo($("select[name=pledgeMethod]"), '');
				}

				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced:{
						updateOnContentResize:true
					}
				});
				// 是否隐藏 复利利率上浮字段
				if (cmpdRateType == "0") {// 隐藏
					$('input[name=cmpdFloat]').parents("td").hide();// 字段td
					$('input[name=cmpdFloat]').parents("td").prev("td").hide();// 标签td
					$("input[name=cmpdFloat]").attr("mustinput", "0");// 隐藏后因必填造成提交无反应
				}
				//当前审批节点是发回补充资料回来的
				if(nodeNo=="supplement_data"){
					$("input[name=opinionType]").parents("tr").hide();
					$("textarea[name=approvalOpinion]").attr("title","补充资料说明");
					$("textarea[name=approvalOpinion]").parents("tr").find(".control-label").html("<font color='#FF0000'>*</font>补充资料说明");
				}

				$("select[name=cusNoFund]").popupSelection({// 中汇鑫德，资金机构选择
					searchOn : true, // false-不启用搜索，true-启用搜索
					inline : true, // true-内联,false-弹出
					ajaxUrl : webPath+"/mfBusAgencies/getPopupData",
					multiple : false // false-单选,true-复选
				});
				MfBusBankAccCom.bankAccInit();
                if (baseRateChange == "false") {
                    window.top.alert("市场报价利率已重新发布,该业务将按照新的市场报价利率进行执行", 3);
                }
                if(creditAppId!=''&&creditAppId!='null'){
                    MfBusApply_applyInput.agenciesInit(creditAppId);
                    var agenciesId = $("input[name='agenciesId']").val();
                    MfBusApply_applyInput.breedInit(agenciesId,creditAppId);
				}else{

				}
				if(busModel == "12"){
                    $("[name='approvalOpinion']").attr("style","height:200px");
                }

			});
			function checkTerm(){
				var term = $("input[name=term]").val();						
				if(parseFloat(term)<parseFloat(minTerm)||parseFloat(term)>parseFloat(maxTerm)){
					$("input[name=term]").val(appTerm);
					alert(top.getMessage("ONLY_APPLY_VALUE_SCOPE",{"info":"产品设置","field":"融资期限","value1":minTime,"value2":maxTime}),0);
				}
			}
            var agenciesInput = function () {
                var kindNo =  $("input[name='kindNo']").val();
                top.openBigForm(webPath + "/mfCusCreditApply/agenciesApplyView?kindNo=" + kindNo, "添加合作银行", function () {
                    $("input[name='agenciesId1']").val(top.applyAgenceisId);
                    $("input[name='agenciesName1']").val(top.applyAgenceisName);
                    $("input[name='agenciesName1']").parent().find(".error").remove()
                });
            }
            var breedInit1 = function () {
                var kindNo =  $("input[name='kindNo']").val();
                $.ajax({
                    url:  webPath + "/mfCusCreditApply/breedInitForApply",
                    data:{kindNo:kindNo},
                    type:'post',
                    dataType:'json',
                    success: function (data) {
                        if (data.flag == "success") {
                            $("input[name='breedNo1']").popupSelection({
                                searchOn: false,//启用搜索
                                inline: true,//下拉模式
                                multiple: false,//多选
                                items : data.items,
                                changeCallback: function (obj,elem) {
                                    $("input[name='breedName1']").val(obj.data("text"));
                                }
                            })
                        }
                    }
                });
            }
			 //审批页面
			 function getApprovaPage(){
			 	// $("#infoDiv").css("display","none");
			 	// $("#approvalBtn").css("display","none");
			 	// $("#approvalDiv").css("display","block");
			 	// $("#submitBtn").css("display","block");
			 	//当前审批节点是发回补充资料回来的,刷新补充资料表单
			 		if(nodeNo=="supplement_data"){
				 	$.ajax({
						url : webPath+"/mfBusApplyWkf/getApproveFormDataAjax",
						data : {appId : appId,isPrimary:isPrimary},
						success : function(data) {
							$("#edit-form").html(data.htmlStr);
							$("[name=opinionType]").parents("tr").hide();
							$("textarea[name=approvalOpinion]").attr("title","补充资料说明");
							$("textarea[name=approvalOpinion]").parents("tr").find(".control-label").html("<font color='#FF0000'>*</font>补充资料说明");
							if($("input[name=vouType]").is(':visible')){
								$("input[name=vouType]").popupSelection({
									searchOn:true,//启用搜索
									inline:false,//下拉模式
									multiple:false,//多选
									valueClass:"show-text",
									items:data.map,
									title:"担保方式",
									handle:false
								}); 
						 		$("input[name=vouType]").parent().children(".pops-value").unbind("click");
						 		$("input[name=vouType]").parent().children(".pops-value").children().children(".pops-close").remove(); 
							}
						}
					});
				}
			 }
            function creditRatioOnblur() {
                var rechargeAmt = $("[name='rechargeAmt']").val();
                var withdrawAmt = $("[name='withdrawAmt']").val();
                var rechargeAmt1 = $("[name='rechargeAmt1']").val();
                var withdrawAmt1 = $("[name='withdrawAmt1']").val();
                if(CalcUtil.compare(rechargeAmt,rechargeAmt1) == 1){
                    alert("批复充值金额不能大于充值金额",0);
                    return false;
                }
                if(CalcUtil.compare(withdrawAmt,withdrawAmt1) == 1){
                    alert("批复提现金额不能大于提现金额",0);
                    return false;
                }
                if(rechargeAmt != null && rechargeAmt != "" && withdrawAmt != null && withdrawAmt != ""){
                    var appAmtList = [
                        $("[name='withdrawAmt']").val(),
                        $("[name='rechargeAmt']").val(),
                    ];
                    var appAmt = CalcUtil.sum(appAmtList);
                    $("[name='appAmt']").val(fmoney(appAmt, 2));
                }
                return true;
            };
			 // //返回详情页面
			 // function approvalBack(){
			 // 	$("#infoDiv").css("display","block");
			 // 	$("#approvalBtn").css("display","block");
			 // 	$("#approvalDiv").css("display","none");
			 // 	$("#submitBtn").css("display","none");
			 // }
			 
			 //审批提交
			function doSubmit(obj){
			    //移除disabled属性，为了取值
                $("[name='realFlag']").removeAttr("disabled");
                $("[name='finalApproveFlag']").removeAttr("disabled");
				var opinionType = $("[name=opinionType]").val();
				//没有选择审批意见默认同意
				if(opinionType == undefined || opinionType == ''){
					opinionType = "1";
				}
				var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
                if(!creditRatioOnblur()){
                    return;
                }
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					//调用规则验证必填的表单是否完善
					// 准入拦截
					var parmData = {'nodeNo':nodeNo, 'appId': appId, 'cusNo': cusNo, 'relNo': appId};
					$.ajax({
						url : webPath+"/riskForApp/getNodeRiskDataForBeginAjax",
						data : {ajaxData: JSON.stringify(parmData)},
						success : function(data) {
							if (data.exsitRefused == true) {// 存在业务拒绝
								dialog({
									id:"userDialog",
						    		title:'风险拦截信息',
						    		url: webPath+'/riskForApp/getPreventListByNodeNo?relNo='+appId+'&nodeNo='+nodeNo,
						    		width:900,
						    		height:500,
						    		backdropOpacity:0,
						    		onshow:function(){
						    			this.returnValue = null;
						    		},onclose:function(){
						    			myclose_click();
						    		}
						    	}).showModal();
							}else {
								if(isPrimary=="1"){//业务初选
									commitProcess(webPath+"/mfBusApplyWkf/submitUpdateForPrimaryAjax?appId="+appId+"&primaryAppId="+primaryAppId+"&appNo="+primaryAppId+"&opinionType="+opinionType,obj,'applySP');
								}else{
								//审批意见类型opinionType必须传，否则影响后续commitProcess方法中的if判断
								commitProcess(webPath+"/mfBusApplyWkf/submitUpdateAjax?appId="+appId+"&appNo="+appId+"&opinionType="+opinionType,obj,'applySP');
								}
							}
						},
						error : function() {
						}
					});
				}
				
			}
			 
			 function updatePactEndDate(){
				var beginDate =  $("input[name=beginDate]").val();
				var term = $("input[name=term]").val();
				var termType = $("select[name=termType]").val();
				var intTerm = parseInt(term);
				if(1==termType){ //融资期限类型为月 
					var d = new Date(beginDate);
					d.setMonth(d.getMonth()+intTerm);
					var str = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
					$("input[name=endDate]").val(str);
				}else{ //融资期限类型为日 
					var d = new Date(beginDate);
				 	d.setDate(d.getDate()+intTerm);
					var str = d.getFullYear()+"-"+(d.getMonth()>=9?beginDate.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate()); 
					$("input[name=endDate]").val(str);
				}
			};
			/* 校验批复金额不能大于申请金额 */
			function checkAppAmt(obj){
				var appAmt1 =  $("input[name=appAmt1]").val();//申请金额
				var appAmt = $("input[name=appAmt]").val();//批复金额
				appAmt1=appAmt1.replace(/,/g,'');
				appAmt=appAmt.replace(/,/g,'');
				if(parseFloat(appAmt)>parseFloat(appAmt1)){
					$("input[name=appAmt]").val(appAmt1);
					window.top.alert(top.getMessage("NOT_FORM_TIME",{"timeOne":"批复金额" , "timeTwo":"申请金额"}), 3);
				}
			}
			//根据期限和开始日期计算结束日期赋值
			function updateEndDateByTerm(obj,endElement){
			    var $parentTr =  $(obj).parents("tr");
                var beginDate =  $parentTr.find("[dataType='6']").val();
                var term = $parentTr.find("[dataType='1']").val();
                if(term){
                    var intTerm = parseInt(term);
                    var d = new Date(beginDate);
                    d.setMonth(d.getMonth()+intTerm);
                    var str = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
                    $(endElement).val(str);
                }
            }
			// 初始化资金机构
			function initCusNameFund(){
				$("input[name=cusNameFund]").popupList({
					searchOn: true, //启用搜索
					multiple: false, //false单选，true多选，默认多选
					ajaxUrl : webPath+"/mfBusAgencies/getMfBusAgenciesListAjax",// 请求数据URL
					valueElem:"input[name='cusNoFund']",//真实值选择器
					title: "选择资金机构",//标题
					changeCallback:function(elem){//回调方法
						var tmpData = elem.data("selectData");
						$("input[name=cusNoFund]").val(tmpData.agenciesId);
						$("input[name=cusNameFund]").val(tmpData.agenciesName);
						$("input[name=fincRate]").val(tmpData.investorYieldRate);
						$("input[name=payTotalRate]").val(tmpData.payTotalRate);
					},
					tablehead:{//列表显示列配置
						"agenciesName":"资金机构名称",
						"agenciesId":"资金机构编号"
					},
					returnData:{//返回值配置
						disName:"agenciesName",//显示值
						value:"agenciesId"//真实值
					}
				});
				$("input[name=cusNameFund]").next().click();
			}
			
			//计算放款成数:放款成数=批复金额/车辆评估值
			function countLoanRate(obj){
                var name = $(obj).attr("name");
                var appAmt = "";
                var evalAmt = "";
                if(name == "appAmt"){
                    appAmt = $(obj).val();
				}else{
                    appAmt = $("input[name='appAmt']").val();
				}

                if(name == "evalAmt"){
                    evalAmt = $(obj).val();
                }else{
                    evalAmt = $("input[name='evalAmt']").val();
                }
                appAmt = appAmt.replace(/,/g,'');
                evalAmt = evalAmt.replace(/,/g,'');
				var loanRate = CalcUtil.divide(appAmt,evalAmt);
                loanRate = CalcUtil.multiply(loanRate,100);
				loanRate =  CalcUtil.formatMoney(loanRate,2);
				$("input[name='storeLoanRate']").val(loanRate);
			}
			//是否抵押转质押,级联控制
			function selectChange(obj){
                var mortgageTransfer = $(obj).val();
                //是
                if(mortgageTransfer == "1"){
                    $("[name='realFlag']").find("option[value='0']").attr("selected",true);
                    $("[name='finalApproveFlag']").find("option[value='0']").attr("selected",true);
                    $("[name='realFlag']").attr("disabled",'false');
                    $("[name='finalApproveFlag']").attr("disabled",'false');
				}else{
                    $("[name='realFlag']").removeAttr("disabled");
                    $("[name='finalApproveFlag']").removeAttr("disabled");
				}
			}
			//根据名义利率获得实际利率
			function getFincRate() {
                var appAmt = $("input[name=appAmt]").val().replace(/,/g, "");
                var term = $("input[name=term]").val();
                var rateType = $("input[name=rateType]").val();
                var nominalRate = $("input[name=nominalRate]").val();
                $.ajax({
                    url : webPath+"/mfBusApply/getFincRateAjax",
                    data : {appAmt:appAmt,term:term,rateType:rateType,nominalRate:nominalRate},
                    async : false,
                    success : function(data) {
                        $("input[name=fincRate]").val(data.fincRateShow);
                        $("input[name=replyFincRate]").val(data.fincRateShow);
                    },error : function() {
                    }
                });
            }
            //修改定金联动修改申请金额
           function downPaymentsChange() {
                var fullPackagePrice = $("input[name=fullPackagePrice]").val();
                var downPayments = $("input[name=downPayments]").val();
                if(fullPackagePrice!=""&&downPayments!=""){
                    fullPackagePrice = fullPackagePrice.replace(/,/g, "");
                    downPayments = downPayments.replace(/,/g, "");
                    var fullPackagePriceNum = new Number(fullPackagePrice);
                    var downPaymentsNum = new Number(downPayments);
                    if(downPaymentsNum>fullPackagePriceNum){
                        $("input[name=downPayments]").val("0");
                        window.top.alert(top.getMessage("NOT_FORM_TIME", {"timeOne":"首付","timeTwo":"全包价"}), 0);
                    }else{
                        var appAmt = CalcUtil.formatMoney(fullPackagePriceNum-downPaymentsNum,2);
                        $("input[name=appAmt]").val(appAmt);
                        $("input[name=appAmt1]").val(appAmt);
                        getFincRate();
                    }
                }
            };
            //返回详情页面
            function approvalBack(){
                $("#infoDiv").css("display","block");
                $("#approvalBtn").css("display","block");
                $("#approvalDiv").css("display","none");
                $("#submitBtn").css("display","none");
            };
            //进入审批页面
            function getApprovaPage(){
                $("#infoDiv").css("display","none");
                $("#approvalBtn").css("display","none");
                $("#approvalDiv").css("display","block");
                $("#submitBtn").css("display","block");
            };
		</script>
</head>

<body  class="overflowHidden bg-white" >
	<div class="container form-container">
		<div id="infoDiv" style="height:100%;width:75%;float: left;">
			<iframe src="${webPath}/mfBusApply/getSummary?appId=${mfBusApply.appId}&busEntrance=apply_approve&operable=&ifAnalysisTable=${ifAnalysisTable}&queryForm=${queryForm}&queryFileApproval=${queryFile}" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
		</div>
		<div id="approvalDiv" class="scroll-content" style="width: 25%;float: right;">
			<div style="text-align: center;font-size: 16px;">
				<a href="#formButton" style="padding:0px 25px 0px 25px;">
					<c:choose>
						<c:when test="${busModel == '12'}">
							工程审批
						</c:when>
						<c:otherwise>
							表单
						</c:otherwise>
					</c:choose>
				</a>
				<a href="#fileButton" id="buttonFile" style="padding:0px 25px 0px 25px;display: none;">电子文档</a>
				<a href="#docButton" id="buttonDoc" style="padding:0px 25px 0px 25px;display: none;">调查资料</a>
			</div>
			<div class="col-md-10 col-md-offset-1 column margin_top_20" >
				<div class="bootstarpTag fourColumn" id="formButton">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form id="edit-form">
						<dhcc:bootstarpTag property="formapply0003" mode="query" />
					</form>	
				</div>
				<%@ include file="/component/include/approval_biz_node_plugins.jsp"%><!-- 功能挂载(要件、文档、费用...) -->
			</div>
		</div>
		<div id="submitBtn" class="formRowCenter">
			<dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#edit-form');"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
	</div>
	<input name="taskId" id="taskId" type="hidden" value=${taskId} />
	<input name="activityType" id="activityType" type="hidden" value=${activityType} />
	<input name="isAssignNext" id="isAssignNext" type="hidden" value=${isAssignNext} />
	<input name="transition" type="hidden" />
	<input name="nextUser" type="hidden" />
	<input name="designateType" type="hidden" value=${designateType} />
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
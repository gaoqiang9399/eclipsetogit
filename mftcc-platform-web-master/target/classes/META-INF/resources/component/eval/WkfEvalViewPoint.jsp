<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript">
			var vpNo = '${vpNo}';
			var evalAppNo = '${evalAppNo}';
			var dataMap = '${dataGradeCardMap}';
			var evalType = '${appEval.evalType}';
			var query = '${query}';
			var aloneFlag = true;
			var dataDocParm={
					relNo:evalAppNo,
					docType:"evalPic",
					docTypeName:"外部评级资料",
					docSplitName:"外部评级相关文件",
					query:query,
			};
			
			$(function(){
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
				addGradeCard(dataMap);
				//审批意见选择组件
		        $("select[name=opinionType]").popupSelection({
							searchOn:false,//启用搜索
							inline:true,//下拉模式
							multiple:false,//单选
							changeCallback : function (elem) {
								WkfApprove.opinionTypeChange();
							}
				});
			});
			 //审批页面
			 function getApprovaPage(){
			 	$("#infoDiv").css("display","none"); 
			 	$("#approvalBtn").css("display","none"); 
			 	$("#approvalDiv").css("display","block"); 
			 	$("#submitBtn").css("display","block"); 
			 }
			 //返回详情页面
			 function approvalBack(){
			 	$("#infoDiv").css("display","block"); 
			 	$("#approvalBtn").css("display","block"); 
			 	$("#approvalDiv").css("display","none"); 
			 	$("#submitBtn").css("display","none");
			 }
			 
			 //审批提交
			function doSubmit(obj){
				var opinionType = $("input[name=opinionType]").val();
				var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					commitProcess(webPath+"/appEvalWkf/submitUpdate?opinionType="+opinionType+"&appNo="+evalAppNo,obj,'evalSP');
				}
			}
			function addGradeCard(dataMap){
				if(dataMap.gradeCardListData){
					$.each(dataMap.gradeCardListData,function(i,obj){
						var $tr=$('<tr id='+obj.gradeCardId+' name="gradeCard"></tr>');
						var strTd='<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">'+obj.gradeCardName+'</label></td>'+
						'<td class="tdvalue  half right" colspan="1" rowspan="1"><div class="input-group">';
						if(dataMap["dlData"+obj.gradeCardId]){
							strTd=strTd+'<input type="text" value="'+dataMap["dlData"+obj.gradeCardId].score+'" readonly="" class="form-control">'+
							'<input type="hidden" name="dlData'+obj.gradeCardId+'" value="1"></div></td>';
							$tr.append(strTd);
							$("input[name=grade]").parents("tr").eq(0).before($tr);
						}
						if(dataMap["dxData"+obj.gradeCardId]){
							strTd=strTd+'<input type="text" value="'+dataMap["dxData"+obj.gradeCardId].score+'" readonly="" class="form-control">'+
							'<input type="hidden" name="dxData'+obj.gradeCardId+'" value="1"></div></td>';
							$tr.append(strTd);
							$("input[name=grade]").parents("tr").eq(0).before($tr);
						}
						if(dataMap["finData"+obj.gradeCardId]){
							strTd=strTd+'<input type="text" value="'+dataMap["finData"+obj.gradeCardId].score+'" readonly="" class="form-control">'+
							'<input type="hidden" name="finData'+obj.gradeCardId+'" value="1"></div></td>';
							$tr.append(strTd);
							$("input[name=grade]").parents("tr").eq(0).before($tr);
						}
						if(dataMap["adjData"+obj.gradeCardId]){
							strTd=strTd+'<input type="text" value="'+dataMap["adjData"+obj.gradeCardId].score+'" readonly="" class="form-control">'+
							'<input type="hidden" name="adjData'+obj.gradeCardId+'" value="1"></div></td>';
							$tr.append(strTd);
							$("input[name=grade]").parents("tr").eq(0).before($tr);
						}
					});
					$.each($("tr[name=gradeCard]"),function(i,obj){
						var len = $(obj).find("td").length;
						if(len<4){
							if($(obj).next().attr("name")){
								$(obj).append($(obj).next().html());
								$(obj).next().remove();
							}
						}
					});
				}
			};
		</script>
</head>
<body  class="overflowHidden bg-white">
	<div class="container form-container">
		<div id="infoDiv" style="display:block;height:100%;">
			<!-- 审批流程 -->
			<iframe src="${webPath}/mfCusCustomer/getById?cusNo=${appEval.cusNo}&evalAppNo=${appEval.evalAppNo}&evalCredit=evalApp&operable=" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%" id="iframepage" name="iframepage"></iframe>
		</div>
		<div id="approvalDiv" class="scroll-content" style="display:none;">
			<div class="col-md-8 col-md-offset-2 column margin_top_20" >
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form  method="post" id="evalApprovForm" theme="simple" name="operform" action="${webPath}/mfCusCustomer/insertForBusAjax">
						<dhcc:bootstarpTag property="formevalapp1001" mode="query"/>
					</form>
				</div>
				<!--外部评级资料 -->
				<c:if test="${appEval.evalType == '2'}">
					<div class="row clearfix">
					<div class="form-tips" style='color: #FE4202;font-size:12px'>说明：下方选择上传外部评级相关资料，请确保内容准确性。</div>
						<div class="col-xs-12 column" >
							<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
						</div>
					</div>
				</c:if>
			</div>	
		</div>
		<div id="approvalBtn" class="formRowCenter " style="display:block;">
			<dhcc:thirdButton value="审批" action="审批" onclick="getApprovaPage();"></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_task();"></dhcc:thirdButton>
		</div>
		<div id="submitBtn" class="formRowCenter" style="display:none;">
			<dhcc:thirdButton value="提交" action="提交" onclick="doSubmit('#evalApprovForm');"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="approvalBack();"></dhcc:thirdButton>
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
</html>
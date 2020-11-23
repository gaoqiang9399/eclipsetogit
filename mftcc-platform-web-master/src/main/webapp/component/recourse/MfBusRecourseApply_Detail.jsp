<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript" src='${webPath}/component/include/calcUtil.js'></script>
		<script type="text/javascript" src='${webPath}/component/recourse/js/MfBusRecourseApplyDetail.js'></script>
	<script type="text/javascript">
		var projectName = '${projectName}';

        var projectName = '${projectName}';
		$(function(){
			MfBusRecourseApplyDetail.init();
		});
		//计算追偿总额
		function getRecourseTotal(date){
            var recourseFee = $("input[name=recourseFee]").val().toString();
            var compensatoryDate = $("input[name=compensatoryDate]").val().toString();
		    var recoursePenaltyFee = $("input[name=recoursePenaltyFee]").val().toString();
			date = date.toString().replace(/-/g, "");
            compensatoryDate = compensatoryDate.replace(/-/g, "");
			$.ajax({
				url: webPath+'/mfBusRecourseApply/getRecourseDate',
				data: {
				    "recoverDate": date,
					"compensatoryDate": compensatoryDate,
					"recourseFee": recourseFee,
					"recoursePenaltyFee": recoursePenaltyFee
				},
                type : "POST",
                dataType : "json",
				success: function (data) {
                    $("input[name=compensatoryAlreadyTerm]").val(data.differDate);
                    $("input[name=actualAmt]").val(data.totel);
                }
			});
		}
		//计算优惠金额
		function getDiscountMoney() {
			var actualAmt = parseFloat($("input[name=actualAmt]").val());
            var actualAmount = parseFloat($("input[name=actualAmount]").val());
            if(Number(actualAmount)>Number(actualAmt)){
            	alert("实际追偿总额不能大于追偿总额");
            	$("input[name=actualAmount]").val(actualAmt);
            	$("input[name=discountAmount]").val('0.0');
            }else{
            	var discountAmount = Number(actualAmt - actualAmount).toFixed(2);
                $("input[name=discountAmount]").val(discountAmount);
            }
            
        }
	</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">追偿申请表</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="mfBusRecourseApplyForm" theme="simple" name="operform">
							<dhcc:bootstarpTag property="formrecourseApply0001" mode="query"/>
						</form>
					</div>
					<div class="list-table" id="mfBusCompensatoryDocListDiv">
						<div class="title">
							<span><i class="i i-xing blockDian"></i>资料清单</span>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfBusCompensatoryDocList">
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div class="content collapse in" id="mfBusCompensatoryDocList" name="mfBusCompensatoryDocList">
							<dhcc:tableTag property="tablecompensatorydocregister" paginate="mfBusCompensatoryDocList" head="true"></dhcc:tableTag>
						</div>
					</div>
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="title">
								<span><i class="i i-xing blockDian"></i>追偿资料</span>
								<iframe id="mfBusRecourseApplyIframe" frameborder=0 width="100%" src="${webPath}/component/compensatory/mfBusRecourseUpload.jsp?query=${query }&recourseId=${recourseId}"></iframe>
							</div>
						</div>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	   		</div>	
	   	</div>
	</body>
</html>
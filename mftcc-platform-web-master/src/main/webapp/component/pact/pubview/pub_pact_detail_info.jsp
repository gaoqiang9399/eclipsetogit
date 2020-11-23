<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var appId = '${param.appId}';
	var pactId = '${param.pactId}';
	var pactFormId = '${param.formId}';
	$(function() {
		pubPactDetailInfo.init();
	});
</script>
<!-- 申请表单信息 -->
<div class="row clearfix">
	<div class="col-xs-12 column">
		<div class="form-table base-info">
			<div class="title">
				<span id="biaodanSpan"><i class="i i-xing blockDian"></i>${param.blockName}</span>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#pactDetailInfo">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
				<button class="btn btn-link pull-right formAdd-btn" id="hbxdA" data-toggle="collapse" data-target="#elecPactButtom" onclick="pubPactDetailInfo.showElecPact('1001')" style="display: none;">
					电子合同
				</button>
				<button class="btn btn-link pull-right formAdd-btn" id="hbxdB" data-toggle="collapse" data-target="#elecPactButtom" onclick="pubPactDetailInfo.showElecPact('1000')" style="display: none;">
					委托扣款授权书
				</button>
				<button class="btn btn-link pull-right formAdd-btn" id="hbxdC" data-toggle="collapse" data-target="#elecPactButtom" onclick="pubPactDetailInfo.showElecPact('1002')" style="display: none;">
					征信查询授权书
				</button>
			</div>
			<div class="content collapse in" id="pactDetailInfo" name="pactDetailInfo">
				<form method="post" theme="simple" id="pactDetailForm" name="operform" action="${webPath}/mfBusPact/updateAjaxByOne">
					<dhcc:propertySeeTag property="formpact0004" mode="query" />
				</form>
			</div>
		</div>
	</div>
</div>
<!-- 客户表单信息 -->
<div class="row clearfix bus-cus-info">
	<div class="col-xs-12 column info-block">
		<div class="block-add" style="display: none;"></div>
	</div>
</div>


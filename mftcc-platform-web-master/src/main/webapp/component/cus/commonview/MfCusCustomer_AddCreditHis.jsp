<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<script type="text/javascript">
	var agenciesUid='${param.agenciesUid}';
	$(function(){
		var trenchListSize = ${trenchListSize};
		if(trenchListSize != 0){
			$("#addCreditHisRowDiv").show(); 
			$("#addCreditHisList .ls_list").show(); 
		}
	});
</script>
<!--额度追加历史-->
<div class="arch_sort" id = "addCreditHisRowDiv" style="border: 0px;display:none;">
	<div class="dynamic-block" title="额度追加历史" name="MfTrenchCreditAmtModifyHisAction"
		data-sort="14" data-tablename="mf_trench_credit_amt_modify_his">
		<div class="list-table">
			<div class="title">
				<span class="formName"><i class="i i-xing blockDian"></i>额度追加历史</span>
				<button class=" btn btn-link pull-right formAdd-btn"
					data-toggle="collapse" data-target="#addCreditHisList">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div disable="true" class="content collapse in"
				id="addCreditHisList" style="margin-top: 10px;">
				${addCreditHisListHtml}
			</div>
		</div>
	</div>
</div>


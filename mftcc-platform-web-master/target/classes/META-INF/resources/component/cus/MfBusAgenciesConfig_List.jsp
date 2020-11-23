<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<script type="text/javascript">
	var agenciesUid='${param.agenciesUid}';
	$(function(){
		var agenciesConfigListSize = ${agenciesConfigListSize};
		if(agenciesConfigListSize != 0){
			$("#addAgenciesConfigList .ls_list").find("thead").find("tr").each(function(){
				var tdArr = $(this).children("th");
				tdArr.eq(0).css("text-align","center");
				tdArr.eq(1).css("text-align","right");
				tdArr.eq(2).css("text-align","right");
				tdArr.eq(3).css("text-align","center");
			});
			
			$("#addAgenciesConfigList .ls_list").find("tbody").find("tr").each(function(){
				var tdArr = $(this).children("td");
				tdArr.eq(0).css("text-align","left");
				tdArr.eq(1).css("text-align","right");
				tdArr.eq(2).css("text-align","right");
				tdArr.eq(3).css("text-align","center");
			});
			$("#addAgenciesConfigRowDiv").show(); 
			$("#addAgenciesConfigList .ls_list").show(); 
		}
	});
</script>
<!--额度追加历史-->
<div class="arch_sort" id = "addAgenciesConfigRowDiv" style="border: 0px;display:none;">
	<div class="dynamic-block" title="资金机构配置明细" name="MfBusAgenciesConfigAction"
		data-sort="14" data-tablename="mf_bus_agencies_config">
		<div class="list-table">
			<div class="title">
				<span class="formName"><i class="i i-xing blockDian"></i>资金机构配置明细</span>
				<button class=" btn btn-link pull-right formAdd-btn"
					data-toggle="collapse" data-target="#addAgenciesConfigList">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div disable="true" class="content collapse in"
				id="addAgenciesConfigList" style="margin-top: 10px;">
				${addAgenciesConfigListHtml}
			</div>
		</div>
	</div>
</div>

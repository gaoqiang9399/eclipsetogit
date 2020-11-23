<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<script type="text/javascript">
	var pactSts= '${param.pactSts}';
	var advanceLoanId= '${param.advanceLoanId}';
	var nodeNo= '${param.nodeNo}';
	$(function(){
        if(pactSts=="4" || pactSts=="6"){
            $('.btn-file-archive').removeClass('hidden');
            if(pactSts=="4"){
                $("#certiInfo").attr("disabled",false);
                $("#certiInfo").removeClass("btn-opt-dont");
                $("#certiInfo").addClass("btn-opt");
            }
        }
        if(nodeNo !="certidInfo_reg" || advanceLoanId != ''){
            $("#MfBusPact_DynaDetail_advance_button").attr("disabled","disabled").addClass("btn-opt-dont");
        }
	});
</script>
<!--信息登记操作入口 -->
<div class="row clearfix btn-opt-group">
	<div class="col-xs-12 column">
		<div class="btn-group pull-right">
			<button class="btn btn-opt" onclick="MfBusPact_DynaDetail.filePrint();" type="button">
				<i class="i i-x"></i><span>文件打印</span>
			</button>
		</div>
	</div>
</div>


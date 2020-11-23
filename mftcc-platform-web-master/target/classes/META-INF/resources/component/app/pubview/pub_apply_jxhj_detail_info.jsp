<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript" src="${webPath}/component/app/js/pub_apply_jxhj_detail_info.js"></script>
<script type="text/javascript">
    $(function() {
        var tableId = "${param.formId}";
        var appId="${param.appId}";
        pubApplyJxhjDetailInfo.init(tableId,appId);
    });
</script>
<!-- 借新还旧借据 -->
<div class="row clearfix" id="finc-old-list">
	<div class="dynamic-block" title="借新还旧借据" name="fincOldDetail">
		<div class="list-table" id="finc-list">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>${param.blockName}</span>
				<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#fincOldDetail">
					<i class="i i-close-up"></i><i class="i i-open-down"></i>
				</button>
			</div>
			<div class="content collapse in" id="fincOldDetail" name="fincOldDetail"></div>
		</div>
	</div>
</div>

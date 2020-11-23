<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var appId = '${param.appId}';
	var formId = '${param.formId}';
	var queryForm = '${queryForm}';
	$(function() {
		pubApplyDetailInfo.init();
	});
	
</script>
<!-- 申请表单信息 -->
<div class="row clearfix">
	<div class="col-xs-12 column">
		<div class="form-table base-info">
			<div class="content" id="applyDetailInfo">
				<form  method="post" theme="simple" id="applyDetailForm" name="operform" action="${webPath}/mfBusApply/updateAjaxByOne">
					<dhcc:propertySeeTag property="formapply0006" mode="query" />
				</form>
			</div>
		</div>
	</div>
</div>
<%--<!-- 客户表单信息 -->--%>
<%--<div class="row clearfix bus-cus-info">--%>
	<%--<div class="col-xs-12 column info-block">--%>
		<%--<div class="block-add" style="display: none;"></div>--%>
	<%--</div>--%>
<%--</div>--%>
<script type="text/javascript">
        /** 贷款投向细类选择组件(征信用) */
         function getFincUseSmList(thiz) {
            var fincUse=$("select[name='fincUse']").val();
           // var fincUse = fincUseObj.value;
            if (!fincUse) {
                alert("请先选择贷款投向", 0);
                return;
            }
            top.dialog({
                id : "areaDialog",
                title : '征信用途',
                url : webPath + '/parLoanuse/getFincUseSmList?fincUse=' + fincUse,
                width : 350,
                height : 420,
                backdropOpacity : 0,
                onshow : function() {
                    this.returnValue = null;
                },
                onclose : function() {
                    if (this.returnValue) {
                        thiz.value = this.returnValue.disName;
                        $("[name='fincUseSm']").val(this.returnValue.disNo);
                    }
                }
            }).showModal();
        }
</script>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var chapterkindNo = '${chapterkindNo}';
</script>
<c:if test='${operable != "query"}'>
	<!--信息登记操作入口 -->
	<div class="row clearfix btn-opt-group">
		<div class="col-xs-12 column">
			<div class="btn-group pull-right">
				<dhcc:pmsTag pmsId="use-chapter-btn">
					<button class="btn btn-opt"  onclick="useChapterInsert();">
						<i class="i i-jia2"></i><span>用章申请</span>
					</button>
				</dhcc:pmsTag>
                <%--授信详情入口废弃，统一授信列表上面授信按钮--%>
                <dhcc:pmsTag pmsId="cus-credit-stop">
                    <button id="cusCreditStop" class="btn btn-opt"
                            onclick="cusCreditStop();" type="button">
                        <i class="i i-stop"></i><span>授信终止</span>
                    </button>
                </dhcc:pmsTag>
				<dhcc:pmsTag pmsId="cus-credit-invalid">
					<button id="cusCreditInvalid" class="btn btn-opt"
						onclick="cusCreditInvalid();" type="button">
						<i class="i i-invalid"></i><span>授信失效</span>
					</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="cus-auth-btn">
					<button id="creditApply" class="btn btn-opt"
						onclick="adjCreditApply('3');" type="button">
						<i class="i i-adjust"></i><span>授信调整</span>
					</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="cus-credit-temporary-btn">
					<button id="creditTemporaryApply" class="btn btn-opt"
						onclick="adjCreditApply('4');" type="button">
						<i class="i i-temporary"></i><span>临额调整</span>
					</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="credit-frozen-btn">
					<button id="creditFrozen" class="btn btn-opt"
							onclick="creditFrozeThaw('1');" type="button">
						<i class="i i-creditFroze"></i><span>授信冻结</span>
					</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="credit-thaw-btn">
					<button id="creditThaw" class="btn btn-opt"
							onclick="creditFrozeThaw('2');" type="button">
						<i class="i i-thaw"></i><span>授信解冻</span>
					</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="cus-creditTemplate-btn">
					<button  class="btn btn-opt"
							onclick="creditTemplatePrint();" type="button">
						<i class="i i-credit"></i><span>授信模板打印</span>
					</button>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="cus-eval-credit-debts-btn">
					<button class="btn btn-opt" title="详审评级" id="creditDebtBtn" onclick="cusEval.getInitatEcalApp('${creditAppId}','4','1');"
							type="button">
						<i class="i i-eval1"></i><span>详审评级</span>
					</button>
				</dhcc:pmsTag>
			</div>
		</div>
	</div>
</c:if>

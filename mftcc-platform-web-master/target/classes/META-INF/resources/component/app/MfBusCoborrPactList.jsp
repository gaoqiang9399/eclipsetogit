<%@ page language="java" pageEncoding="utf-8" %>
<script type="text/javascript" src="${webPath}/component/app/js/MfBusCoborrList.js?v=${cssJsVersion}"></script>
<script>
    var sign = "apply";//申请
    MfBusCoborrList.showCocoborrPactList();
</script>
          <dhcc:pmsTag pmsId="apply-gjr-detail-info">
			<div class="dynamic-block" title="共同借款人" name="coborrNumName" data-sort="14" >
				<div class="list-table">
					<div class="title">
						<span class="formName">共同借款人</span>
					<dhcc:pmsTag pmsId="apply-gjr-add-info">
						<button class="btn btn-link formAdd-btn"  onclick="MfBusCoborrList.selectCoborrNumPactList()" title="新增"><i class="i i-jia3"></i></button>
		            </dhcc:pmsTag>
					</div>
					<div class="content collapse in" class="bootstarpTag fourColumn">
						<div class="bootstarpTag" id="tableHtml">
						</div>
					</div>
				</div>
		     </div>
		  </dhcc:pmsTag>

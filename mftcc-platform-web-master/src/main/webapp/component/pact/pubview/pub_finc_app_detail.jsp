<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var fincId = '${param.fincId}';
	var fincFormId = '${param.formId}';
	$(function() {
		pubFincAppDetail.init();
	});
	//选择打印方式
	function fkDaYinPrint(){
		var url ;//将方式设置为还款
		url = '${webPath}/cwPrintTmplItem/choosePrintType?busType=fk&fincId='+fincId+"&cusNo="+cusNo+"&pactId="+pactId+"&appId="+appId+"&pactModelNo="+pactModelNo;
		top.addFlag = false;
		top.htmlStrFlag = false;
		top.createShowDialog(url,"选择打印方式",'50','50',function(){
			
		});
	}
	
</script>
<!-- 放款申请表单信息 -->
<div class="row clearfix">
	<div class="col-xs-12 column">
		<div class="form-table">
			<div class="title">
				<span><i class="i i-xing blockDian"></i>${param.blockName}</span>
				
				<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#fincappchild">
					<i class='i i-close-up'></i> <i class='i i-open-down'></i>
				</button>
				<%--<button class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#fkprint" onclick="fkDaYinPrint()">--%>
					<%--放款打印--%>
				<%--</button>--%>
			</div>
			<div class="content collapse in" style="margin-top: 15px;" id="fincappchild" name="fincappchild">
				<form id="fincAppDetail" method="post" theme="simple" name="operform">
					<dhcc:propertySeeTag property="formfincapp0002" mode="query" />
				</form>
			</div>
		</div>
	</div>
</div>



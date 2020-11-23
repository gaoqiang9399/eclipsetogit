<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<script type="text/javascript">
	var operable='${param.operable}';//显示不显示
	$(function(){
		if(operable=='operable'){
			$("#rotateRow").show();
		}
	});
</script>
<!--底部待完善表单 -->
	<div class="row clearfix" id="rotateRow" style="display:none;">
		<div class="col-xs-12 column" >
			<div id="rotate-body"></div>
		</div>
	</div> 

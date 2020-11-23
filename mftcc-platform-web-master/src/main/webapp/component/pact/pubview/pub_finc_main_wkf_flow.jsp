<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var fincSts= '${param.fincSts}';
	var wkfAppId= '${param.wkfAppId}';
	var operable= '${operable}';
	$(function(){
		//pubFincWkfFlow.init();

	});

</script>
<!-- 展期业务流程图 -->
<div class="row clearfix hide" id="showExtenWkf">
	<div class="app-process">
		<div id="exten-modeler" class="modeler">
			<ul id="exten-wj-modeler" class="wj-modeler" isApp = "true">
			</ul>
		</div>
	</div>
</div>
<div class="row  btn clearfix bg-danger next-div hide">
	<div class="col-xs-12 column text-center">
		<div class="block-next"></div>
	</div>
</div>


<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
	var fincSts= '${param.fincSts}';
	var wkfAppId= '${param.wkfAppId}';
	var operable= '${operable}';
	$(function(){
		pubFincWkfFlow.init();
		MfBusExtensionCommon.showExtenWkfFlowInfo();
	});

</script>
<!--  业务流程视图 -->
<div class="row clearfix">
	<div class="app-process hide">
		<div id="modeler1" class="modeler">
			<ul id="wj-modeler1" class="wj-modeler" isApp = "true">
			</ul>
		</div>
	</div>
</div>

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
<!-- 授信申请业务流程提示信息 -->
<!-- <div class="row clearfix btn bg-danger next-div hide">
	<div class="col-xs-12 column text-center">
		<div class="block-next"></div>
	</div>
</div> -->

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var appId= '${param.appId}';
	var wkfAppId= '${param.wkfAppId}';
	var operable= '${operable}';
	$(function(){
		pubWkfFlow.init();
	});

</script>
<!--  业务流程视图 -->
<div class="row clearfix">
	<div class="app-process">
		<div id="modeler1" class="modeler">
			<ul id="wj-modeler1" class="wj-modeler" isApp = "true">
			</ul>
		</div>
	</div>
</div>

<div class="row  btn clearfix bg-danger next-div hide hidden">
	<div class="col-xs-12 column text-center">
		<div class="block-next"></div>
	</div>
</div>
	

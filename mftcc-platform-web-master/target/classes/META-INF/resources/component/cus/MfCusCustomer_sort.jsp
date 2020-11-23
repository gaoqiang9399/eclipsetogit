<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--客户分类审批历史 -->
<div class="approval-hist hide" id="sort-spInfo-block">
	<div class="list-table">
		<div class="title">
			<span><i class="i i-xing blockDian"></i>审批历史</span>
			<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
				<i class='i i-close-up'></i>
				<i class='i i-open-down'></i>
			</button>
		</div>
		<div class="content margin_left_15 collapse in " id="spInfo-div">
			<div class="approval-process">
				<div id="modeler1" class="modeler">
					<ul id="wj-modeler2" class="wj-modeler" isApp = "false">
					</ul>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
    var evalCredit ="${evalCredit}";
	//把传入的标识拆分
	if(evalCredit.length>3){
       var sort=evalCredit.substring(0,4)
       var flowWaterId=evalCredit.substring(4,evalCredit.length);
        if(sort =="sort"){
            $("#sort-spInfo-block").removeClass("hide")
            showWkfFlowVertical($("#wj-modeler2"),flowWaterId,"","");
        }
	}
</script>
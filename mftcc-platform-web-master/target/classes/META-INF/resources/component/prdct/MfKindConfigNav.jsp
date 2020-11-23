<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function(){
		kindSearch();
		$("#kindSearch").keyup(function(event){
// 			  if(event.keyCode ==13){
			   	 kindSearch();
// 			  }
		});
		$(".panel-body").mCustomScrollbar({
			advanced: {
				updateOnContentResize: true,
			}
		});
	});

	var kindSearch = function(){
		var kindName = $("#kindSearch").val();
		$.ajax({
			url:webPath+"/mfSysKind/getKindListAjax",
			data:{kindName:kindName},
			success:function(data){
				if(data.flag=='success'){
					var kindList = data.mfSysKindList;
					var htmlStr="";
					$.each(kindList,function(i,mfSysKind){
						htmlStr = htmlStr +'<div id="bodyItem'+mfSysKind.kindNo+'" class="body-item mask-div" data-kindno="'+mfSysKind.kindNo+'" onclick="MfSysKindList.getkindConfig(this);"><i class="i i-chanpin font_size_20"></i>'+mfSysKind.kindName+'</div>';
					});
					$("#box-body").html(htmlStr);
					$(".panel-body").mCustomScrollbar("update");
				}
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl),0);
			}
		});
	};
	
	var toggleBoardNavDiv=function(){
		if($(".nav_dropdown").hasClass("hidden")){
			$(".nav_dropdown").removeClass("hidden");
		}else{
			$(".nav_dropdown").addClass("hidden");
		}
	};
</script>
<li class="dropdown pull-right padding_right_10" style="position: relative;">
	<button type="button" class="btn dropdown-toggle margin_top_3" onclick="toggleBoardNavDiv();">配置导航 <span class="caret"></span></button>
	<div class="panel nav_dropdown hidden"> 
		<div class="panel-head">
			配置导航<i class="i i-x2 pull-right " onclick="toggleBoardNavDiv();"></i>
		</div>
		<div class="panel-body">
			<div class="kind-search">
				<input id="kindSearch" class="form-control" required="required" autofocus="autofocus" placeholder="输入产品名称进行过滤..." data-i18n="[placeholder]board_nav.filter_placeholder">
				<i class="i i-fangdajing"></i>
			</div>
			<div class="kind-box">
				<div class="box-header">
	            	<h3 class="box-title">产品列表</h3>
	            	<div class="box-tools pull-right">
	            		<button type="button" class="btn btn-box-tool" data-toggle="collapse" data-target="#box-body"><i class="fa fa-minus"></i><i class="fa fa-plus"></i></button>
	            	</div>
		        </div>
	            <div id="box-body" class="box-body collapse in">
<%-- 	            	<c:forEach items="${dataMap.mfSysKindList}" var="mfSysKind"> --%>
<%-- 			              <div id="bodyItem${mfSysKind.kindNo}" class="body-item mask-div" data-kindno="${mfSysKind.kindNo}" onclick="MfSysKindList.getkindConfig(this);"><i class="i i-chanpin font_size_20"></i><c:out value="${mfSysKind.kindName}"></c:out></div> --%>
<%-- 	             	</c:forEach> --%>
	            </div>
		     </div>
		</div>
	    <div class="panel-footer color_theme">
	    	<dhcc:pmsTag pmsId="set-kind-conf-tab">
	     		<div class="set-btn prdct-set" onclick="MfSysKindList.toKindConfig();">
		     		<i class="i i-chilun"></i><span>产品设置</span>
		     	</div>
	     	</dhcc:pmsTag>
	     	<dhcc:pmsTag pmsId="set-pub-conf-tab">
		    	<div class="set-btn pub-set" onclick="MfKindPubConfig.toPubConfig();">
		     		<i class="i i-chilun"></i><span>公共设置</span>
		     	</div>
	     	</dhcc:pmsTag>
	    </div>
	</div>
</li>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<title>多笔合同</title>
	</head>
	<script type="text/javascript">
	var busEntrance='${busEntrance}';
    var mfBusApplyAssureInfoListSize='${mfBusApplyAssureInfoListSize}';
    var mfBusApplyAssureInfoProjectListSize='${mfBusApplyAssureInfoProjectListSize}';
    $(function(){
        //处理暂无数据的情况
        if(mfBusApplyAssureInfoListSize == "0" && mfBusApplyAssureInfoProjectListSize == "0"){
            $('.clearfix').append('<div style="text-align: center;margin-top: 50px;" >暂无数据</div>');
        }
        $(".scroll-content").mCustomScrollbar({
            advanced : {
                updateOnContentResize : true
            }
        });
    });
	//多业务跳转
	function enterClick(obj,url){
			//跳页面
		if(busEntrance=="4" ||busEntrance=="5"||busEntrance=="6"){//审批页面多笔业务跳转在弹层页面中进行
			window.location.href=url+"&busEntrance=4"
		}else{
			$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url+"&busEntrance=1");
			myclose();
		}

	};
</script>
<body class="bg-white">
 	<div class="container scroll-content">
 		 <div class="row clearfix">
			 <c:if test="${mfBusApplyAssureInfoList != null and fn:length(mfBusApplyAssureInfoList) > 0}">
				 <div class="arch_sort">
					 <div id="contentDiv" class="row clearfix">
						 <div class="col-xs-12 column info-block">
							 <div id="content-block" class="approval-hist">
								 <div class="list-table">
									 <div class="title">
										 <span><i class="i i-xing blockDian"></i>小微详情列表</span>
										 <button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#content">
											 <i class="i i-close-up"></i><i class="i i-open-down"></i>
										 </button>
									 </div>
									 <div class="content margin_left_15 collapse in " id="content">
											 ${tableHtml}
									 </div>
								 </div>
							 </div>
						 </div>
					 </div>
				 </div>
			 </c:if>
			 <c:if test="${mfBusApplyAssureInfoProjectList != null and fn:length(mfBusApplyAssureInfoProjectList) > 0}">
				 <div class="arch_sort">
					 <div id="contentProjectDiv" class="row clearfix">
						 <div class="col-xs-12 column info-block">
							 <div id="contentProject-block" class="approval-hist">
								 <div class="list-table">
									 <div class="title">
										 <span><i class="i i-xing blockDian"></i>工程详情列表</span>
										 <button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#contentProject">
											 <i class="i i-close-up"></i><i class="i i-open-down"></i>
										 </button>
									 </div>
									 <div class="content margin_left_15 collapse in " id="contentProject">
											 ${projectTableHtml}
									 </div>
								 </div>
							 </div>
						 </div>
					 </div>
				 </div>
			 </c:if>
		</div>
	</div>
</body>
</html>
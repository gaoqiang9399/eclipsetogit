<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
	<head>
		<title>多笔业务</title>
	</head>
	<script type="text/javascript">
	var mfVouAfterTrackProjectListSize='${mfVouAfterTrackProjectListSize}';
	$(function(){
        //处理暂无数据的情况
        if(mfVouAfterTrackProjectListSize == "0"){
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
		if(url.substr(0,1)=="/"){
			url =webPath + url; 
		}else{
			url =webPath + "/" + url;
		}
		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url+"&busEntrance=pact");
		myclose();

	};
</script>
<body class="bg-white">
 	<div class="container scroll-content">
 		 <div class="row clearfix">
			<c:if test="${mfVouAfterTrackProjectList != null and fn:length(mfVouAfterTrackProjectList) > 0}">
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
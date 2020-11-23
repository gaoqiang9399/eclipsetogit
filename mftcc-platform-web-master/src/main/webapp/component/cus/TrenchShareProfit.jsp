<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>列表</title>
		<style>
			.footer_loader{
				bottom: 54px;
			}
		</style>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<!-- 我的筛选选中后的显示块 -->
					<div class="search-div" id="search-div">
							<!-- begin -->
							<c:if test="${calcBase != '1' }">
								<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/项目名称"/>
							</c:if>
							<c:if test="${calcBase == '1' }">
								<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称"/>
							</c:if>
							<!-- end -->
					</div>
				</div>
			</div>
		</div>
		<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
					</div>
				</div>
		</div>
		<div class="formRowCenter">
   			<dhcc:thirdButton value="保存" action="保存" onclick="TrenchShareProfit.ajaxSave();"></dhcc:thirdButton>
   			<dhcc:thirdButton value="取消" action="取消" typeclass="myclose cancel" onclick="myclose();"></dhcc:thirdButton>
   		</div>	
				<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
	<script src="${webPath}/component/cus/js/TrenchShareProfit.js"></script>
	<script type="text/javascript">
		var calcBase = "${calcBase}";
		var cusNo = "${cusNo}";
		filter_dic =
			[{"optCode":"cusName",
			  "optName":"客户名称",
			  "dicType":"val"
			}];
		if(calcBase != "1"){
			filter_dic =
			[{"optCode":"cusName",
			  "optName":"客户名称",
			  "dicType":"val"
			},{"optCode":"appName",
				  "optName":"项目名称",
				  "dicType":"val"
			},{"optCode":"putoutDate","optName":"放款日期","dicType":"date"}];
		}
		$(function(){
			TrenchShareProfit.init();
		});
	</script>
</html>

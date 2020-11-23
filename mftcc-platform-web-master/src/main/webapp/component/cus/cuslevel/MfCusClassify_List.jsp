<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/component/oa/notice/css/MfOaNoticeList.css" />
	</head>

<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix classfiyinfo">
			<div class="col-xs-3 column text-center">
				<div>
					<img id="headImgShow" name="headImgShow" class="img-circle classify-headImg"/>
				</div>
			</div>
			<div class="col-xs-7 column margin-left-30px padding_top_10">
				<div class="col-xs-10 column">
					<div class="clearfix">
						<div class="bus head-title pull-left">${mfCusCustomer.cusName}</div>
					</div>
					<div>
						<p>
							<span><i class="i i-ren1 "></i>
							<c:if test="${mfCusCustomer.cusType==202}">
								<span id="cusName"> 
									<c:if test="${mfCusCustomer.cusName!=''}">
												${mfCusCustomer.cusName}
												
									</c:if> 
									<c:if test="${mfCusCustomer.cusName==''}">
										<span class="unregistered">未登记</span>
									</c:if>
								</span> 
							</c:if> 
							<c:if test="${mfCusCustomer.cusType!=202}">
								<span id="contactsName"> 
									<c:if test="${mfCusCustomer.contactsName!=''}">
												${mfCusCustomer.contactsName}
									</c:if> <c:if  test="${mfCusCustomer.contactsName==''}">
										<span class="unregistered">未登记</span>
									</c:if>
								</span> 
							</c:if> 								
						    </span> 
						    <span class="vertical-line"></span> 
						    <span><i class="i i-dianhua "></i>							    
								<c:if test="${mfCusCustomer.cusType==202}">
								<span id="cusTel"> 
									<c:if test="${mfCusCustomer.cusTel!=''&&mfCusCustomer.cusTel!=null}">
												${mfCusCustomer.cusTel}
									</c:if> 
									<c:if test="${mfCusCustomer.cusTel==''||mfCusCustomer.cusTel==null}">
										<span class="unregistered">未登记</span>
									</c:if>
								</span> 
							</c:if> 
							<c:if test="${mfCusCustomer.cusType!=202}">
								<span id="contactsTel">
									<c:if test="${mfCusCustomer.contactsTel!=''&&mfCusCustomer.contactsTel!=null}">
									${mfCusCustomer.contactsTel}
									</c:if> 
									<c:if test="${mfCusCustomer.contactsTel==''||mfCusCustomer.contactsTel==null}">
										<span class="unregistered">未登记</span>
									</c:if>
								</span>
							</c:if> 					
							</span> 
							<span class="vertical-line"></span> 
							<span><i class="i i-idcard2 "></i>
								<span id="idNum">
									<c:if test="${mfCusCustomer.idNum!=''&&mfCusCustomer.idNum!=null}">
											${mfCusCustomer.idNum}
									</c:if> 
									<c:if  test="${mfCusCustomer.idNum==''||mfCusCustomer.idNum==null}">
											<span class="unregistered">未登记</span>
									</c:if>
								</span>
							</span>
						</p>
						<p>
							<i class="i i-location "></i> <span id="commAddress">
								<c:if test="${mfCusCustomer.commAddress!=''&&mfCusCustomer.commAddress!=null}">
									${mfCusCustomer.commAddress}
								</c:if> <c:if test="${mfCusCustomer.commAddress==''||mfCusCustomer.commAddress==null}">
									<span class="unregistered">未登记</span>
								</c:if>
							</span> 
							<span class="vertical-line"></span> 
							<%--<c:if test="${mfCusCustomer.cusType!=202}">--%>
							<i class="i i-youjian1 "></i> 
							<span id="postalCode">
								<c:if test="${mfCusCustomer.postalCode!=''&&mfCusCustomer.postalCode!=null}">
									${mfCusCustomer.postalCode}
								</c:if> <c:if  test="${mfCusCustomer.postalCode==''||mfCusCustomer.postalCode==null}">
									<span class="unregistered">未登记</span>
								</c:if>
							</span>
							<%--</c:if> --%>
						</p>
					</div>
				</div>
				<div class="col-xs-2 column ">
					<!--客户类型图标-->
					<div class="i i-warehouse class-type">
						<div class="classtype-name"></div>
					</div>
				</div>
			</div>
			<div class="col-xs-2 column ">
				<div class="cusclassify-button">
					<button type="button" class="btn btn-primary pull-right"id="noticeInsert" onclick="cusTag();">客户分类</button>
				</div>
			</div>
		</div>
	</div>
	<div id="list-div">
		<div class="list-min-div">
			<table class="list-div-table">
				<tbody>
					<c:forEach items="${mfCusClassifyList}" varStatus="statu" var="mfCusClassify">
						<tr class="<c:if test='${ statu.index%2 == 1}'>nth-child-even</c:if><c:if test='${ statu.index%2 == 0}'>nth-child-odd</c:if>">
							<td class="td-classify-first">
								<p class="marginNone">${fn:substring(mfCusClassify.regTime,0,4)}-${fn:substring(mfCusClassify.regTime,4,6)}-${fn:substring(mfCusClassify.regTime,6,8)} ${fn:substring(mfCusClassify.regTime,9,17)}</p>
							</td>
							<td class="td-classify-second">
								<c:if test='${rankType=="1"}'>								
									<span class="i-blacklist">${mfCusClassify.rankTypeName}</span>								
								</c:if>
								<c:if test='${rankType=="2"}'>								
									<span class="i-good">${mfCusClassify.rankTypeName}</span>								
								</c:if>
								<c:if test='${rankType=="3"}'>								
									<span class="i-comcom">${mfCusClassify.rankTypeName}</span>								
								</c:if>
								<c:if test='${rankType=="4"}'>								
									<span class="i-potential">${mfCusClassify.rankTypeName}</span>								
								</c:if>
								<c:if test='${rankType=="5"}'>								
									<span class="i-good">${mfCusClassify.rankTypeName}</span>								
								</c:if>
							</td>
							<td class="td-classify-three">
								<p class="marginNone">操作员：${mfCusClassify.opName}</p>
							</td>
							<td class="td-classify-four">
								<p class="marginNone">
								 ${mfCusClassify.mergerReason}
								</p>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="footer_loader">
		<div class="loader">
			<i class="fa fa-spinner fa-pulse fa-3x"></i>
		</div>
		<div class="pagerShow">
			当前显示&nbsp;
			<span class="loadCount"></span>&nbsp;条数据，一共&nbsp;
			<span class="pageCount"></span>&nbsp;条数据
		</div>
		<div class="backToTop"></div>
	</div>		
</body>
<script type="text/javascript">
	var webPath = '${webPath}';
	var data = '${headImg}';
	var ifUploadHead = '${mfCusCustomer.ifUploadHead}';
	var cusClassify = '${mfCusClassify.rankType}';
	var rankTypeName = '${mfCusClassify.rankTypeName}';
	var	cusNo = '${cusNo}';
	var	opNo = '${opNo}';
	var	cusMngNo = '${cusMngNo}';
	top.cusClassify = '${mfCusClassify.rankType}';
	top.cusTag = '${mfCusClassify.rankTypeName}';
	$(function() {
	$("#list-div").height($("#list-div").parent().height()-40-150);
	$(".list-div-table p[mytitle]:contains('...')").initMytitle();
	var len = $("#list-div tbody tr").length;
		$(".pageCount").text(len);
		$(".loadCount").text(len);
	    $("#list-div").mCustomScrollbar({
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			},
			callbacks: {
			onUpdate : function(){
				 $(window).resize(function() {
				 	$("#list-div").height($("#list-div").parent().height()-40-150);
				 });
				}
			}
		});	 
		//处理暂无数据的情况
		if ($('#list-div tbody tr').length == 0) {
			var thCount = $('.notice-div thead th').length;
			$('#list-div tbody')
					.append(
							'<tr><td style="text-align: center;padding-top:20px;font-size:18px;">您未对该客户进行分类，系统默认为${mfCusClassify.rankTypeName}</td></tr>');
		};
		if (ifUploadHead != "1") {
			data = "themes/factor/images/" + data;
		}
		data = encodeURIComponent(encodeURIComponent(data));
		document.getElementById('headImgShow').src = webPath
				+ "/uploadFile/viewUploadImageDetail?srcPath="
				+ data + "&fileName=user2.jpg";
		$(".classtype-name").text(rankTypeName);
		if (cusClassify == '1') {
			$(".class-type").addClass("i-blacklist");
			$(".classtype-name").addClass("i-blacklist");
		} else if (cusClassify == '2') {
			$(".class-type").addClass("i-good");
			$(".classtype-name").addClass("i-good");
		} else if (cusClassify == '4') {
			$(".class-type").addClass("i-potential");
			$(".classtype-name").addClass("i-potential");
		}else if (cusClassify == '5') {
			$(".class-type").addClass("i-good");
			$(".classtype-name").addClass("i-good");
		}else if (cusClassify == '3') {
            $(".class-type").addClass("i-comcom");
            $(".classtype-name").addClass("i-comcom");
        }else {
			$(".classtype-name").text("潜在客户");
			$(".class-type").addClass("i-potential");
            $(".classtype-name").addClass("i-potential");
		}		
	});
	//客户分类
	function cusTag() {
		if(opNo === cusMngNo){
			top.classify = true;
			top.updateFlag = true;
			window.location.href = "${webPath}/mfCusClassify/getByCusNo?cusNo=" + cusNo;
		}else{
			window.top.alert('您不是此客户项目经理，不能进行客户分类！', 0);
		}
    };
</script>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title></title>
		<style type="text/css">
			.root{
				width: 100%;
				overflow: hidden;
				text-align: center;
				margin: 150px auto;
			}
			.imgwrap{
				margin: 0 auto;
			}
			.title{
				color: #929292;
			}
		</style>
	</head>
	<body>
		<div class="root">
			<c:if test="${successFlag == '1'}">
				<div class="imgwrap"><img src="/factor/component/ncfgroup/img/over.png"/></div>
				<div class="title">交易成功，订单状态需等待1个小时更新！如有疑问，请联系客服。</div>
			</c:if>
			<c:if test="${successFlag == '0'}">
				<div class="imgwrap"><img src="/factor/component/hzey/img/gantan.png"/></div>
				<div class="title">交易失败，如需帮助，请联系客服。</div>
			</c:if>
			<c:if test="${successFlag == '2'}">
				<!--银行卡绑定失败错误原因提示出来-->
				<div class="imgwrap"><img src="/factor/component/hzey/img/gantan.png"/></div>
				<div class="title">绑定失败，原因：${empty result?'':result}；如需帮助，请联系客服。</div>
			</c:if>
		</div>
	</body>
<script>
</script>
</html>

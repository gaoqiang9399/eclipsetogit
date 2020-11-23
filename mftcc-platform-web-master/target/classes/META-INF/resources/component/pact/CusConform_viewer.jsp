<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title></title>
<link rel="shortcut icon" type="image/x-icon" href="${webPath}/favicon.ico" />
<script type="text/javascript">
	var ctxPath='${pageContext.request.contextPath}';
	var webPath ='${webPath}';
</script>

<script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${webPath}/wechatDesign/weblib/layer-v3.0.1/layer/mobile/layer.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/bootstrap/css/bootstrap.min.css" />
<c:if test="${'1' ne mfTemplateBizConfig.agreeSign && flag eq 'true'}">
<script type="text/javascript" >
$(function(){
	var layLoad = null;
	var dealTemplateBizConfig=function(templateBizConfigId,agreeSign){
		layLoad = layer.open({type: 2,shadeClose:false});
		$.ajax({
			url:webPath+"/cusConform/dealAjax",
			type:"POST",
			dataType:"json",
			data:{templateBizConfigId:templateBizConfigId,agreeSign:agreeSign},
			success:function(data){
				layer.close(layLoad);
				layer.open({content: data.msg,skin: 'msg',time: 4 });
				if(data.code=='0000'){
					if(agreeSign=='1'){
						$('#agree_sign_out').remove();
						$('#tpinfo').text('您已同意该合同，查看请下载');
					}else if(agreeSign=='0'){
						$('#tpinfo').text('你已拒绝了该合同');
					}
				}else{
					layer.open({content: data.msg,skin: 'msg',time: 4 });
				}
			},error:function(data){
				layer.close(layLoad);
			}
		});
	};
	
	var templateBizConfigId='${templateBizConfigId}';
	$('#agree').on('click',function(){
		//询问框
		layer.open({
			content: '您确定要同意本合同么？',btn: ['确定', '取消'],yes: function(index){
				layer.close(index);
				dealTemplateBizConfig(templateBizConfigId,'1');
		    }
		});
	});
	
	$('#refuse').on('click',function(){
		//询问框
		layer.open({
			content: '您确定要拒绝本合同么？',btn: ['确定', '取消'],yes: function(index){
				layer.close(index);
				dealTemplateBizConfig(templateBizConfigId,'0');
		    }
		});
	});
});
</script>
</c:if>
<style type="text/css">
body{
	background-color: rgb(50, 181, 203);
}
.down-out{
	top: 40%;
}
.navbar-btm{
	bottom: 24px !important;
}
.tpinfo{
	line-height: 20px;
	color: #fff;
	padding: 20px 0px 0px 0px;
}
</style>
</head>
<body>
	<div class="navbar-fixed-top down-out">
		<div class="container">
			<div class="row ">
				<div class="col-md-2 col-sm-2 col-xs-2">
	  			</div>
	  			<div class="col-md-8 col-sm-8 col-xs-8 text-center">
	  				<a class="btn btn-info btn-lg" target="_blank" href="${webPath}/cusConform/download?templateBizConfigId=${templateBizConfigId}" role="button"><span class="glyphicon glyphicon-download-alt" aria-hidden="true"></span>下载查看合同文档</a>
	  			</div>
	  			<div class="col-md-2 col-sm-2 col-xs-2">
	  			</div>
			</div>
			<div class="row ">
	  			<div class="col-md-12 col-sm-12 col-xs-12 text-center">
	  				<%-- <c:if test="${empty mfTemplateBizConfig.agreeSign }">
	  				<p class="tpinfo">是否接受合同？下载查看后点击下面按钮</p>
	  				</c:if>--%>
	  				<c:if test="${empty mfTemplateBizConfig.agreeSign || '0' eq mfTemplateBizConfig.agreeSign}">
	  				<p class="tpinfo" id="tpinfo">是否接受该合同？下载查看后点击下面按钮</p>
	  				</c:if>
	  				<%-- <c:if test="${'0' eq mfTemplateBizConfig.agreeSign }">
	  				<p class="tpinfo" id="tpinfo">你已拒绝了该合同</p>
	  				</c:if>  --%>
	  				<c:if test="${'1' eq mfTemplateBizConfig.agreeSign }">
	  				<p class="tpinfo" id="tpinfo">您已同意该合同，查看请下载</p>
	  				</c:if>
	  			</div>
			</div>
		</div>
	</div>
	<c:if test="${'1' ne mfTemplateBizConfig.agreeSign && flag eq 'true' }">
	<nav class="navbar navbar-fixed-bottom navbar-btm" id="agree_sign_out">
		<div class="container">
			<div class="row ">
				<div class="col-md-6 col-sm-6 col-xs-6 text-center">
	  				<button type="button" class="btn btn-success btn-lg" id="agree">同&nbsp;&nbsp;意</button>
	  			</div>
	  			<div class="col-md-6 col-sm-6 col-xs-6 text-center">
	  				<button type="button" class="btn btn-danger btn-lg" id="refuse">拒&nbsp;&nbsp;绝</button>
	  			</div>
			</div>
		</div>
	</nav>
	</c:if>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	var webPath = '${webPath}';
	var basePath = '${webPath}';
	var cusNo = '${dataMap.cusNo}';
	var showType = '${dataMap.showType}';
	$(function(){
		LoadingAnimate.start();
		$.ajax({
		url :webPath+"/mfCusCustomer/toAddServicePage",
		type:"post",
		data:{"cusNo":cusNo,"showType":showType},
		success:function(data){
			LoadingAnimate.stop();
			if(data.flag=="success"){
				var url = data.url;
				var customer = data.mfCusCustomer;
				var data =customer.headImg;
				if (customer.ifUploadHead != "1") {
					data = "themes/factor/images/" + customer.headImg;
				}
				data = encodeURIComponent(encodeURIComponent(data));
				headImgShowSrc = basePath+webPath+"/uploadFile/viewUploadImageDetail?srcPath=" + data+ "&fileName=user2.jpg";
				if(customer.cusType=="202"){//个人客户
					//url +="?show=0&showType="+data.showType+"&idCardName="+encodeURI(encodeURI(customer.cusName))+"&idCardNum="+customer.idNum+"&phoneNo="+customer.cusTel+"&bankCardNum=622899911919911&blacklistType=person&searchKey="+encodeURI(encodeURI(customer.cusName))+"&dataType=all&reportType=GR";
					url +="?show=0&showType="+data.showType+"&idCardName="+encodeURI(encodeURI(customer.cusName))+"&address="+encodeURI(encodeURI(customer.commAddress))+"&idCardNum="+customer.idNum+"&phoneNo="+customer.cusTel+"&bankCardNum=123456789customer&blacklistType=person&searchKey="+encodeURI(encodeURI(customer.cusName))+"&dataType=all&reportType=GR&cusType="+customer.cusType+""+"&headImgShowSrc="+encodeURI(encodeURI(headImgShowSrc));
				}else{
					//url +="?show=0&showType="+data.showType+"&enterpriseName="+encodeURI(encodeURI(customer.cusName))+"&enterpriseNumber="+customer.idNum+"&dataType=all&reportType=QY";
					url +="?show=0&showType="+data.showType+"&enterpriseName="+encodeURI(encodeURI(customer.cusName))+"&enterpriseNumber="+customer.idNum+"&dataType=all&reportType=QY&address="+encodeURI(encodeURI(customer.commAddress))+"&phoneNo="+customer.contactsTel+"&cusType="+customer.cusType+"&contactsName="+customer.contactsName+"&headImgShowSrc="+encodeURI(encodeURI(headImgShowSrc));
				}
				url+="&returnUrl="+data.backUrl;
				window.location.href=url;
			}else{
				LoadingAnimate.stop();
				alert(data.msg, 0);
			}
		}
	});
	});
</script>
</head>
<body>
	<div></div>
</body>
</html>
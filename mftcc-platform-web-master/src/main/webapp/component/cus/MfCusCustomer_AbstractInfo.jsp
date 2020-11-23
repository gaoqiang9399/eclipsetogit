<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String cusNo = (String)request.getParameter("cusNo");
	String type = (String)request.getParameter("type");
	String contextPath = request.getContextPath();
%>
<script type="text/javascript">
var cusNo = '<%=cusNo%>';
var cusName = "";
var type = '<%=type%>';
$(function(){
		$.ajax({
			url:webPath+"/mfCusCustomer/getCusByIdAjax",
			data:{cusNo:cusNo},
			type:"POST",
			dataType:"json",
			success:function(data) {
				if (data.flag == "success") {
					 var cusInfo = data.cusInfo;
					 var htmlStr = '<div class="col-xs-3 col-md-3 column text-center padding_top_20">'
									+'<img id="headImgShow" name="headImgShow" class="img-circle" onclick="getCusInfo(\''+cusInfo.cusNo+'\');" />'
									+'</div>';
                    cusName = cusInfo.cusName;
					var cusName = cusInfo.cusName;
					if (cusName.length > 8) {
						cusName = cusName.substring(0, 8) + "...";
					}
					htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
								+'<div class="row clearfix">'
									+'<div class="col-xs-10 col-md-10 column margin_top_20">'
									+'<button class="btn btn-link content-title"  title="'+cusInfo.cusName+'" onclick="getCusInfo(\''+cusInfo.cusNo+'\');">'
									+ cusName
									+'</button>'
									+'</div>'
									+'<div class="col-xs-2 col-md-2 column">'
									+'<button type="button" class="btn btn-font-qiehuan" onclick="getCusInfo(\''+cusInfo.cusNo+'\');"><i class="i i-qiehuan qiehuan-font"></i></button>'
									+'</div>'
								+'</div>'
								+'</div>'
								+'<div class="row clearfix cus-content">'
								+'<p>';
					if(cusInfo.cusBaseType=="2"){
						if(cusInfo.cusName==""||cusInfo.cusName==null||cusInfo.cusName=="null"){
							htmlStr = htmlStr +'<span><i class="i i-ren1"></i><span class="unregistered">未登记</span></span><span class="vertical-line">|</span>';
						}else{
							htmlStr = htmlStr +'<span><i class="i i-ren1"></i>'+cusInfo.cusName+'</span><span class="vertical-line">|</span>';
						}		
						if(cusInfo.cusTel==""||cusInfo.cusTel==null||cusInfo.cusTel=="null"){
							htmlStr = htmlStr +'<span><i class="i i-dianhua"></i><span class="unregistered">未登记</span></span>';
						}else{
							htmlStr = htmlStr +'<span><i class="i i-xiaoxi" onclick="sendMessage('+cusInfo.cusTel+');"></i><i class="i i-dianhua" onclick="agentdialout(\''+cusInfo.cusTel+'\');"></i>'+cusInfo.cusTel+'</span>';
						}		
					}else{						
						if(cusInfo.contactsName==""||cusInfo.contactsName==null||cusInfo.contactsName=="null"){
							htmlStr = htmlStr +'<span><i class="i i-ren1"></i><span class="unregistered">未登记</span></span><span class="vertical-line">|</span>';
						}else{
							htmlStr = htmlStr +'<span><i class="i i-ren1"></i>'+cusInfo.contactsName+'</span><span class="vertical-line">|</span>';
						}		
						if(cusInfo.contactsTel==""||cusInfo.contactsTel==null||cusInfo.contactsTel=="null"){
							htmlStr = htmlStr +'<span><i class="i i-dianhua"></i><span class="unregistered">未登记</span></span>';
						}else{
							htmlStr = htmlStr +'<span><i class="i i-dianhua"></i>'+cusInfo.contactsTel+'</span>';
						}	
					}	
					htmlStr = htmlStr +'</p><p>';
						
					if(cusInfo.commAddress==""||cusInfo.commAddress==null||cusInfo.commAddress=="null"){
						htmlStr = htmlStr +'<span><i class="i i-location"></i><span class="unregistered">未登记</span></span>';
					}else{
						htmlStr = htmlStr +'<span><i class="i i-location"></i>'+cusInfo.commAddress+'</span>';
					}	
					//处理客户分类印证
					var rankHtml = "";
					if(data.rankType=="1"){
						rankHtml = '<div class="i i-warehouse classify_type" style="color:#D9534F;"><div class="classify_type_name" >'+data.rankTypeName+'</div></div>';
					}else if(data.rankType=="2"){
						rankHtml = '<div class="i i-warehouse classify_type" style="color:#00AA00;"><div class="classify_type_name">'+data.rankTypeName+'</div></div>';
					}else if(data.rankType=="4"){
						rankHtml = '<div class="i i-warehouse classify_type" style="color:#7C7C7E;"><div class="classify_type_name">'+data.rankTypeName+'</div></div>';
					}else{
						rankHtml = '<div class="i i-warehouse classify_type color_theme"><div class="classify_type_name">'+data.rankTypeName+'</div></div>';
					}	
					htmlStr = htmlStr +'</p>'+rankHtml+'</div>';
					$("#cusInfo").html(htmlStr);
					dealHeadImg(cusInfo);
				}else{
					
				}
			},
			error:function() {
	
			}
		});
		
	function  dealHeadImg(cusInfo){
	
		var headImg=cusInfo.headImg;
		var ifUploadHead = cusInfo.ifUploadHead;
		var data = headImg;
		if(ifUploadHead!="1"){
			data = "themes/factor/images/"+headImg;
		}
		data = encodeURIComponent(encodeURIComponent(data));
		document.getElementById('headImgShow').src=webPath+"/uploadFile/viewUploadImageDetail?srcPath="+data+"&fileName=user2.jpg";
	}
});
//短信发送
function sendMessage(cusTel){
	//默认发给该客户
	var sendMsgType = "1";
	top.showDialog("${webPath}/mfToolsSendMessage/input?cusTel="+cusTel+"&sendMsgType="+sendMsgType,"短信发送","90","95");
}
function agentdialout(tel) {
    window.parent.agentdialout("90"+tel);

    window.parent.meiLaiPhone.showPhoneDialog(cusName,tel,getNowFormatDate(),"1");
}
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();;
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (hour >= 0 && hour <= 9) {
        hour = "0" + hour;
    }
    if (minute >= 0 && minute <= 9) {
        minute = "0" + minute;
    }
    if (second >= 0 && second <= 9) {
        second = "0" + second;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + hour + seperator2 + minute
        + seperator2 + second;
    return currentdate;
}

</script>

<!--客户摘要信息-->
<div id="cusInfo" class="row clearfix has-content padding_bottom_20 padding_top_10">
</div>
<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>				

	
								
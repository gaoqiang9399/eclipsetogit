<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String uId = (String)request.getParameter("cusNo");
	String baseType=(String)request.getParameter("baseType");
	String cusType=(String)request.getParameter("cusType");
	String id=(String)request.getParameter("id");
	String contextPath = request.getContextPath();
%>
<script type="text/javascript">
var id='<%=id%>';
var uId= '<%=uId%>';
var baseType = '<%=baseType%>';
var cusType='<%=cusType%>';
var headImg;
var ifUploadHead;
var cusSubTypeName = '${param.cusSubTypeName}';
$(function(){
		$("body").attr("style","display:none");
		var data;
		var onclickStr='';
		var url=webPath+'/mfCusGroup/getByIdForViewAjax?cusNo='+uId;//改为从客户主表里取值，其它客户类型可共用
		$.ajax({
			url:url,
			type:"POST",
			dataType:"json",
			async:false,
			success:function(data) {
				var cusInfo = data.cusInfo;
				dealBeanToHtml(cusInfo);
				headImg=cusInfo.headImg;
				ifUploadHead=cusInfo.ifUploadHead;
			},
			error:function() {
	
			}
		});
		/**处理头像信息**/
	var data = headImg;
	if (ifUploadHead != "1") {
		data = "themes/factor/images/" + headImg;
	}
	data = encodeURIComponent(encodeURIComponent(data));
	document.getElementById('headImgShow').src = webPath+"/uploadFile/viewUploadImageDetail?srcPath=" + data+ "&fileName=user2.jpg";
	$("body").attr("style","");
});

//处理头部实体对象赋值
function dealBeanToHtml(cusInfo){
	var cusName = cusInfo.cusName;
	if (cusName.length > 15) {
		cusName = cusName.substring(0, 15) + "...";
	}
	$("#cusShowName").html(dealNull(cusName));
	$("#contactsName").html(dealNull(cusInfo.contactsName));
	$("#contactsTel").html(dealNull(cusInfo.contactsTel));
	$("#idNum").html(dealNull(cusInfo.idNum));
	$("#commAddress").html(dealNull(cusInfo.commAddress));
	$("#postalCode").html(dealNull(cusInfo.postalCode));
	initCusIntegrity(cusInfo.infIntegrity);
	$("#cusNameRate-span").html(dealNull(cusInfo.cusNameRate));

	if(cusInfo.cusBaseType=="2"){//企业
		$(".renDynamic").addClass("i-ren2");
	}else{	
		$(".renDynamic").addClass("i-ren1");
	}
	$(".type-name-div").html(cusSubTypeName);
}
function dealNull(name){
	if(name==null||name==''||name=='undefined'){
		return "<span class='unregistered'>未登记</span>";
	}else{
		return name;
	}
}
//初始化客户信息完整度
function initCusIntegrity(cusInfIntegrity){
	if(isNaN(cusInfIntegrity)){
		cusInfIntegrity = 0;
	}
	var b = (cusInfIntegrity * 100).toFixed(0) + "%";
	$("#integrity-span").text("完整度"+b);	
	var level;
	if (cusInfIntegrity == '' || cusInfIntegrity == '0.00') {
		level = "L1";
	} else if (cusInfIntegrity < '0.50') {
		level = "L2";
	} else if (cusInfIntegrity >= '0.50'&&cusInfIntegrity < '0.80') {
		level = "L3";
	} else {
		level = "L4";
	}
	$(".cus-integrity").attr("level", level);
};
//根据业务编号关联其它
function getInfList(){
	top.openBigForm(webPath+'/mfCusCustomer/getCusInfIntegrityList?cusNo='+uId+"&baseType="+baseType,'客户信息完整度详情',myclose);
}
//关联管理
function cusRelation() {
	top.openBigForm(webPath+'/mfCusRelation/forSEDetail?cusNo=' + uId+"&baseType="+baseType,
			'关联关系', function(){});
};
// 上传头像
function uploadHeadImg() {
	window.parent.openBigForm(webPath+'mfCusCustomer/uploadHeadImg?relNo='+ uId + '&cusNo=' + uId, '上传头像', showNewHeadImg);
};
function showNewHeadImg() {
	var data;
	$.ajax({
				url : webPath+"/mfCusCustomer/getIfUploadHeadImg",
				data : {
					cusNo : cusNo
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "1") {
						data = encodeURIComponent(encodeURIComponent(data.headImg));
						document.getElementById('headImgShow').src = webPath+"/uploadFile/viewUploadImageDetail?srcPath="
								+ data + "&fileName=user2.jpg";
					} else {
						data = "themes/factor/images/" + data.headImg;
						document.getElementById('headImgShow').src = webPath+"/uploadFile/viewUploadImageDetail?srcPath="
								+ data + "&fileName=user2.jpg";
					}
				},
				error : function() {
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
	delFile();
};
// 删除文件
function delFile() {
	var srcPath = "/tmp/";
	$.ajax({
		url : webPath+"/uploadFile/delFile",
		data : {
			srcPath : srcPath
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {

		},
		error : function() {
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
};

//获取用户信息变更记录
function openCusInfoChangeRecord(){
    top.window.openBigForm(webPath+"/mfCusInfoChange/getListPage?cusNo="+cusNo,"客户信息变更记录",function(){
    });
};
</script>

<div class="row clearfix head-info">
	<!--头像 -->
	<div class="col-xs-3 column text-center head-img">
		<div class="btn btn-link">
			<img id="headImgShow" name="headImgShow" class="img-circle" src="${webPath}/uploadFile/viewUploadImageDetail?srcPath=themes%252Ffactor%252Fimages%252Fuser_0.jpg&amp;fileName=user2.jpg">
				<a class="btn btn-link head-word" onclick="uploadHeadImg();">更换头像</a>
		</div>
	</div>
	<!--概要信息 -->
	<div class="col-xs-9 column head-content">
		<div class="row clearfix">
			<div class="col-xs-10 column">
				<div class="margin_bottom_5">
				  	<button class="btn btn-link cus head-title" id='cusShowName'>
						
					</button>
				</div>
				<!--信息查看入口 -->
				<div class="margin_bottom_10">
						<button class="btn btn-view cus-tag btn-dodgerblue" type="button">
							<i class="i renDynamic"></i><span id="cusNameRate-span"></span>
						</button>
						<!-- 客户信息变更记录-->
						<dhcc:pmsTag pmsId="cus-info-change-record">
							<button id="cusInfoChangeRecordQuery" class="btn btn-view" type="button" onclick="openCusInfoChangeRecord();">
								<i class="i i-ren2"></i><span>客户信息变更记录</span>
							</button>
						</dhcc:pmsTag>
				</div>
				<div>
					<p>
						<span ><i class="i i-idcard2 "></i>
						<span id="idNum">
						</span></span>
						<span class="vertical-line"></span>
						<span><i class="i renDynamic"></i>
							<span id="contactsName">
									
							</span></span>
							
						<span class="vertical-line"></span> 
						<span><i class="i i-dianhua "></i>
						<span id="contactsTel">
								
						</span></span>
						<span class="vertical-line"></span> 
						<span style="display:none;"><i class="i i-idcard2 "></i>
						<span id="idNum">
								
						</span></span>
					</p>
				</div>
			</div>
				<div class="col-xs-2 column">
					<div class="i i-warehouse cus-type-font">
						<div class="type-name-div"></div>
					</div>
				</div>
		</div>
		<div class="row clearfix" style="display:none;">
			<div>
				<p>
					<i class="i i-location "></i>
						<span id="commAddress">
						</span>
						<span class="vertical-line"></span>
						<i class="i i-youjian1 "></i>
						<span id="postalCode">
						</span>
					</p>
			</div>
		</div>
	</div>
</div>			

	
								
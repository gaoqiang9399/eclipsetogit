<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="app.component.common.BizPubParm"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%
	String uId = (String)request.getParameter("cusNo");
	String baseType=(String)request.getParameter("baseType");
	String cusType=(String)request.getParameter("cusType");
	String id=(String)request.getParameter("id");
	String trenchName=(String)request.getParameter("trenchName");
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
		var url=webPath+'/mfBusTrench/getByIdForViewAjax?cusNo='+uId;//改为从客户主表里取值，其它客户类型可共用
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
    if(baseType == '<%=BizPubParm.CUS_BASE_TYPE_HEXIAN%>'){//核心企业
        $.ajax({
            url:webPath + "/mfTemplateEsignerList/getNotSignedCount?cusNo=" + cusNo,
            type:"POST",
            dataType:"json",
            async:false,
            success:function(data) {
                if(data.flag=="success"){
                    $("#cusElectronicEsignInfoCount").text(data.count);
                }
            },
            error:function() {

            }
        });
    }
    /**信息变更记录处理**/
    cusInfoChangeButton();
});
//修改渠道商
function updateTrench(){
	var url=webPath+"/mfBusTrench/getById?trenchId="+id;
	top.updateFlag=false;
	top.cusInfo='';
	top.createShowDialog(url,"渠道详情",'90','90',function(){
		if(top.updateFlag==true){
			dealBeanToHtml(top.cusInfo);
			$("#MfBusTrenchActionAjax_updateByOne_action").html(top.htmlStr);
			dblclickflag();//单子段编辑事件
		}
	})
}
//修改核心企业
function updateCoreCompany(){
	var url=webPath+"/mfCusCoreCompany/getById?coreCompanyId="+id;
	top.updateFlag=false;
	top.cusInfo='';
	top.createShowDialog(url,"核心企业详情",'90','90',function(){
		if(top.updateFlag==true){
			dealBeanToHtml(top.cusInfo);
			$("#MfBusTrenchActionAjax_updateByOne_action").html(top.htmlStr);
			dblclickflag();//单子段编辑事件
		}
	})
}
// 修改资金机构
function updateAgencies(){
	var url=webPath+"/mfBusAgencies/getById?agenciesId="+id;
	top.updateFlag=false;
	top.cusInfo='';
	top.createShowDialog(url,"资金机构详情",'90','90',function(){
		if(top.updateFlag==true){
			dealBeanToHtml(top.cusInfo);
			$("#MfBusAgenciesActionAjax_updateByOne_action").html(top.htmlStr);
			dblclickflag();//单子段编辑事件
		}
	})
}
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
	// 注释掉所有cusShowName按钮的click事件
    $("#qrcodeId").hide();
	if(baseType=='<%=BizPubParm.CUS_BASE_TYPE_QUDAO%>'){//渠道商
	    $("#qrcodeId").show();
		/* $('body').delegate("#cusShowName","click",updateTrench); */
		$(".type-name-div").html(baseTypeName);
	}else if(baseType=='<%=BizPubParm.CUS_BASE_TYPE_QUDAOB%>'){//个人渠道
	/* 	$('body').delegate("#cusShowName","click",updateTrench); */
		$(".type-name-div").html(baseTypeName);
	}else if(baseType=='<%=BizPubParm.CUS_BASE_TYPE_ZIJIN%>'){//资金机构
		/* $('body').delegate("#cusShowName","click",updateAgencies); */
		$(".type-name-div").html("资金机构");
	} else if (baseType == '<%=BizPubParm.CUS_BASE_TYPE_DANBAO%>') {// 担保公司
		// $('body').delegate("#cusShowName", "click", updateAgencies);
		$(".type-name-div").html(cusSubTypeName);
	} else if (baseType == '<%=BizPubParm.CUS_BASE_TYPE_HEXIAN%>') {// 核心企业
		/* $('body').delegate("#cusShowName", "click", updateCoreCompany); */
		$(".type-name-div").html("核心企业");
	}else if (baseType == '<%=BizPubParm.CUS_BASE_TYPE_COOPAGENCY%>') {// 合作机构
		/* $('body').delegate("#cusShowName", "click", updateCoreCompany); */
		$(".type-name-div").html("合作机构");
	}else if (baseType == '<%=BizPubParm.CUS_BASE_TYPE_WAERHOUSE%>') {// 仓储机构
        $(".type-name-div").html("仓储机构");
    }
	else {//其它未来新增的类型
	
	}
	if(cusInfo.cusBaseType=="2"){//企业
		$(".renDynamic").addClass("i-ren2");
	}else{	
		$(".renDynamic").addClass("i-ren1");
	}	
	initCusAccountStsInfo(cusInfo.cusAccountStatus, cusInfo.cusAccountStatusName);
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

//获取用户信息变更记录
function openCusInfoChangeRecord(){
    top.window.openBigForm(webPath+"/mfCusInfoChange/getListPage?cusNo="+cusNo,"客户信息变更记录",function(){
    });
};
// 上传头像
function uploadHeadImg() {
	window.parent.openBigForm(webPath+'/mfCusCustomer/uploadHeadImg?relNo='+ uId + '&cusNo=' + uId, '上传头像', showNewHeadImg);
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
//初始化客户账户状态信息
function initCusAccountStsInfo(cusAccountStatus, cusAccountStatusName){
	if(cusAccountStatus){
		if(cusAccountStatusName){
			$("#cusAccountStatus-span").text(cusAccountStatusName);			
		}
		if (cusAccountStatus == '01' || cusAccountStatus == '03') {
			$("#cusAccountStatus-button").attr("class","btn btn-view btn-dodgerblue");//系统蓝
		} else if (cusAccountStatus == '02' || cusAccountStatus == '04') {
			$("#cusAccountStatus-button").attr("class","btn btn-view btn-danger");//提醒红
		} else if (cusAccountStatus == '05' || cusAccountStatus == '06') {
			$("#cusAccountStatus-button").attr("class","btn btn-view cus-middle");//预警黄
		}  else {
			$("#cusAccountStatus-span").text("未开户");
			$("#cusAccountStatus-button").attr("class","btn btn-view btn-lightgray");//灰的
		}
	}
};

//查询客户是否有信息变更记录
function cusInfoChangeButton(){
    if ($('#cusInfoChangeRecordQuery').length > 0) {//检查是否存在信息变更记录按钮
        $.ajax({
            url: webPath + '/mfCusInfoChange/getListByCusNoAjax',
            type: 'post',
            data: {cusNo: cusNo},
            async: false,
            dataType: "json",
            success: function (data) {
                if (data.flag == "success") {
                    $("#cusInfoChangeRecordQuery").removeClass("btn-lightgray");// 去掉灰色样式
                    $("#cusInfoChangeRecordQuery").addClass("btn-dodgerblue");// 添加蓝色
                } else {
                    $("#cusInfoChangeRecordQuery").removeClass("btn-dodgerblue");// 去掉蓝色
                    $("#cusInfoChangeRecordQuery").addClass("btn-lightgray");// 添加灰色
                }
            }, error: function () {
                LoadingAnimate.stop();
            }
        });
    }
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
					<!--<button class="btn btn-link cus head-title" id='cusShowName'>
					</button>-->
					<span class="cus head-title" id='cusShowName'>
					</span>
				</div>
				<!--信息查看入口 -->
				<div class="margin_bottom_10">
						<button class="btn btn-view cus-tag btn-dodgerblue" type="button">
							<i class="i renDynamic"></i><span id="cusNameRate-span"></span>
						</button>
						<button class="btn btn-view cus-integrity" type="button" onclick="getInfList();" level="L1">
							<i class="i i-xing2"></i><span id="integrity-span">完整度0%</span>
						</button>
						<button class="btn btn-view  btn-dodgerblue" type="button" onclick="cusRelation();">
							<i class="i i-guanXi"></i><span>关联关系</span>
						</button>
						<!-- 客户信息变更记录-->
						<dhcc:pmsTag pmsId="cus-info-change-record">
							<button id="cusInfoChangeRecordQuery" class="btn btn-view" type="button" onclick="openCusInfoChangeRecord();">
								<i class="i i-ren2"></i><span>客户信息变更记录</span>
							</button>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="cus_credit_info">
						<button id="cusCredit-button" class="btn btn-lightgray btn-view" title="授信总额" type="button" onclick="">
							<i class="i i-credit"></i><span class="creditBus">未授信</span>
						</button>
						</dhcc:pmsTag>
                    <dhcc:pmsTag pmsId="cus-electronic-esign-info">
                        <button id="cusElectronicEsignInfo" class="btn btn-view btn-dodgerblue" title="委托付款协议电子签约" type="button" onclick="cusElectronicEsignBtn()">
                            <i class="i i-dengji"></i><span class="creditBus">委托付款协议电子签约</span>
                            <span class="badge task-count" id="cusElectronicEsignInfoCount" style="width:auto;padding: 3px;">0</span>
                        </button>
                    </dhcc:pmsTag>
						<!-- 账户状态 -->
						<dhcc:pmsTag pmsId="cus-state-account">
						<button id="cusAccountStatus-button" class="btn btn-lightgray btn-view" type="button" onclick="">
							<i class="i i-ren2"></i><span id="cusAccountStatus-span">未开户</span>
						</button>
						</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="trench-qrcode">
					<button id = "qrcodeId" class="btn btn-view  btn-dodgerblue" type="button" onclick="produceQrcode(this);">
						<%--<i class="i i-guanXi"></i>--%><span>生成二维码</span>
					</button>
					</dhcc:pmsTag>
				</div>
				<div>
					<p>
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
						<div class="type-name-div">${cusTypeName}</div>
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

<div id="qrcode"></div>
<script type="text/javascript" src="${webPath}/component/model/js/qrcode.min.js"></script>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
	String cusType = (String) request.getAttribute("cusType");
	String cusForms = (String) request.getAttribute("cusForms");
%>
<!DOCTYPE html>
<html>
<head>
<title>业务填报</title>
</head>
<script type="text/javascript">
	var cusForm = '<%=cusForms%>';
	var cusFormArr = cusForm.split("@");
	//var len = cusFormArr.length;
		$(function(){
			window.parent.reloadFream();
			
	});
</script>
<body class="body_bg">
		<div class="bigform_content bigform_wrap">
			<c:if test='${fn:indexOf(cusForms,"mf_cus_cooperative_agency")!="-1"}'>
				<div name="mf_cus_cooperative_agency" class="formDiv">
					<h3>基本信息</h3>
					<form  method="post" theme="simple" name="operform"
						action="${webPath}/mfCusCooperativeAgency/insertAjax">
						<dhcc:bigFormTag property="formcuscoop00002" mode="query" />
						<div class="formRow">
							<dhcc:button value="保存" action="保存" onclick="ajaxSave(this.form)"></dhcc:button>
						</div>
					</form>
				</div>
			</c:if>
			<c:if test='${fn:indexOf(cusForms,"mf_cus_corp_base_info")!="-1"}'>
				<div name="mf_cus_corp_base_info" class="formDiv">
					<h3>基本信息</h3>
					<form  method="post" theme="simple" name="operform"
						action="${webPath}/mfCusCorpBaseInfo/insertAjax">
						<dhcc:bigFormTag property="formcuscorp00002" mode="query" />
						<div class="formRow">
							<dhcc:button value="保存" action="保存" onclick="ajaxSave(this.form)"></dhcc:button>
						</div>
					</form>
				</div>
			</c:if>
			<c:if test='${fn:indexOf(cusForms,"mf_cus_pers_base_info")!="-1"}'>
				<div name="mf_cus_pers_base_info" class="formDiv">
					<h3>基本信息</h3>
					<form  method="post" theme="simple" name="operform"
						action="${webPath}/mfCusPersBaseInfo/insertAjax">
						<dhcc:bigFormTag property="formcusper00002" mode="query" />
						<div class="formRow">
							<dhcc:button value="保存" action="保存" onclick="ajaxSave(this.form)"></dhcc:button>
						</div>
					</form>
				</div>
			</c:if>
			<c:if test='${fn:indexOf(cusForms,"mf_cus_register")!="-1"}'>
				<div name="mf_cus_register" class="formDiv">
					<h3>注册信息</h3>
					<form  method="post" theme="simple" name="operregform"
						action="${webPath}/mfCusRegister/insertAjax">
						<dhcc:bigFormTag property="formcusreg00002" mode="query" />
						<div class="formRow">
							<dhcc:button value="保存" action="保存" onclick="ajaxSave(this.form)"></dhcc:button>
						</div>
					</form>
				</div>
			</c:if>
			<c:if test='${fn:indexOf(cusForms,"mf_cus_financial_info")!="-1"}'>
				<div name="mf_cus_financial_info" class="formDiv">
					<h3>金融业务信息</h3>
					<form  method="post" theme="simple" name="operregform"
						action="${webPath}/mfCusFinancialInfo/insertAjax">
						<dhcc:bigFormTag property="formcusfina00002" mode="query" />
						<div class="formRow">
							<dhcc:button value="保存" action="保存" onclick="ajaxSave(this.form)"></dhcc:button>
						</div>
					</form>
				</div>
			</c:if>
			<c:if test='${fn:indexOf(cusForms,"mf_cus_key_man")!="-1"}'>
				<div name="mf_cus_key_man" class="formDiv">
					<h3>关键人信息</h3>
					<div class="iframe_content">
						<iframe id="iframe_content"
							src="${webPath}/mfCusKeyMan/getListPageBig?cusName=${cusName}&cusNo=${cusNo}&flag=insert"
							frameborder="0" style="overflow: hidden;" scrolling="no"></iframe>
					</div>
				</div>
			</c:if>
			<c:if test='${fn:indexOf(cusForms,"mf_cus_tax_info")!="-1"}'>
				<div name="mf_cus_tax_info" class="formDiv">
					<h3>上缴税费信息</h3>
					<div class="iframe_content">
						<iframe id="iframe_content"
							src="${webPath}/mfCusTaxInfo/getListPageBig?cusName=${cusName}&cusNo=${cusNo}&flag=insert"
							frameborder="0" style="overflow: hidden;" scrolling="no"></iframe>
					</div>
				</div>
			</c:if>
			<c:if test='${fn:indexOf(cusForms,"mf_cus_shareholder")!="-1"}'>
				<div name="mf_cus_shareholder" class="formDiv">
					<h3>股东信息</h3>
					<div class="iframe_content">
						<iframe id="iframe_content"
							src="${webPath}/mfCusShareholder/getListPageBig?cusName=${cusName}&cusNo=${cusNo}&flag=insert"
							frameborder="0" style="overflow: hidden;" scrolling="no"></iframe>
					</div>
				</div>
			</c:if>
			<c:if test='${fn:indexOf(cusForms,"mf_cus_equity_info")!="-1"}'>
				<div name="mf_cus_equity_info" class="formDiv">
					<h3>股权投资情况</h3>
					<div class="iframe_content">
						<iframe id="iframe_content"
							src="${webPath}/mfCusEquityInfo/getListPageBig?cusName=${cusName}&cusNo=${cusNo}&flag=insert"
							frameborder="0" style="overflow: hidden;" scrolling="no"></iframe>
					</div>
				</div>
			</c:if>
			<c:if test='${fn:indexOf(cusForms,"mf_cus_assets")!="-1"}'>
				<div name="mf_cus_assets" class="formDiv">
					<h3>固定资产信息</h3>
					<div class="iframe_content">
						<iframe id="iframe_content"
							src="${webPath}/mfCusAssets/getListPageBig?cusName=${cusName}&cusNo=${cusNo}&flag=insert"
							frameborder="0" style="overflow: hidden;" scrolling="no"></iframe>
					</div>
				</div>
			</c:if>
			<c:if test='${fn:indexOf(cusForms,"mf_cus_high_info")!="-1"}'>
				<div name="mf_cus_high_info" class="formDiv">
					<h3>高管信息</h3>
					<div class="iframe_content">
						<iframe id="iframe_content"
							src="${webPath}/mfCusHighInfo/getListPageBig?cusName=${cusName}&cusNo=${cusNo}&flag=insert"
							frameborder="0" style="overflow: hidden;" scrolling="no"></iframe>
					</div>
				</div>
			</c:if>

			<c:if test='${fn:indexOf(cusForms,"mf_cus_staff")!="-1"}'>
				<div name="mf_cus_staff" class="formDiv">
					<h3>员工信息</h3>
					<form  method="post" theme="simple" name="operregform"
						action="${webPath}/mfCusStaff/insertAjax">
						<dhcc:bigFormTag property="formcusstaff00002" mode="query" />
						<div class="formRow">
							<dhcc:button value="保存" action="保存" onclick="ajaxSave(this.form)"></dhcc:button>
						</div>
					</form>
				</div>
			</c:if>
			<c:if test='${fn:indexOf(cusForms,"mf_cus_legal_member")!="-1"}'>
				<div name="mf_cus_legal_member" class="formDiv">
					<h3>法人代表家庭成员</h3>
					<div class="iframe_content">
						<iframe id="iframe_content"
							src="${webPath}/mfCusLegalMember/getListPageBig?cusName=${cusName}&cusNo=${cusNo}&flag=insert"
							frameborder="0" style="overflow: hidden;" scrolling="no"></iframe>
					</div>
				</div>
			</c:if>
			<c:if test='${fn:indexOf(cusForms,"mf_cus_person_job")!="-1"}'>
				<div name="mf_cus_person_job" class="formDiv">
					<h3>职业信息</h3>
					<div class="iframe_content">
						<iframe id="iframe_content"
							src="${webPath}/mfCusPersonJob/getListPageBig?cusName=${cusName}&cusNo=${cusNo}&flag=insert"
							frameborder="0" style="overflow: hidden;" scrolling="no"></iframe>
					</div>
				</div>
			</c:if>

			<c:if test='${fn:indexOf(cusForms,"mf_cus_person_inc_expe")!="-1"}'>
				<div name="mf_cus_person_inc_expe" class="formDiv">
					<h3>收支情况</h3>
					<form  method="post" theme="simple" name="operregform"
						action="${webPath}/mfCusPersonIncExpe/insertAjax">
						<dhcc:bigFormTag property="formcusinc00002" mode="query" />
						<div class="formRow">
							<dhcc:button value="保存" action="保存" onclick="ajaxSave(this.form)"></dhcc:button>
						</div>
					</form>
				</div>
			</c:if>
			<c:if test='${fn:indexOf(cusForms,"mf_cus_person_door")!="-1"}'>
				<div name="mf_cus_person_door" class="formDiv">
					<h3>经营信息</h3>
					<div class="iframe_content">
						<iframe id="iframe_content"
							src="${webPath}/mfCusPersonDoor/getListPageBig?cusName=${cusName}&cusNo=${cusNo}&flag=insert"
							frameborder="0" style="overflow: hidden;" scrolling="no"></iframe>
					</div>
				</div>
			</c:if>
			<c:if test='${fn:indexOf(cusForms,"mf_cus_resume")!="-1"}'>
				<div name="mf_cus_resume" class="formDiv">
					<h3>个人履历</h3>
					<div class="iframe_content">
						<iframe id="iframe_content"
							src="${webPath}/mfCusResume/getListPageBig?cusName=${cusName}&cusNo=${cusNo}&flag=insert"
							frameborder="0" style="overflow: hidden;" scrolling="no"></iframe>
					</div>
				</div>
			</c:if>
			<c:if test='${fn:indexOf(cusForms,"mf_cus_family_info")!="-1"}'>
				<div name="mf_cus_family_info" class="formDiv">
					<h3>社会关系</h3>
					<div class="iframe_content">
						<iframe id="iframe_content"
							src="${webPath}/mfCusFamilyInfo/getListPageBig?cusName=${cusName}&cusNo=${cusNo}&flag=insert"
							frameborder="0" style="overflow: hidden;" scrolling="no"></iframe>
					</div>
				</div>
			</c:if>
			<c:if test='${fn:indexOf(cusForms,"mf_cus_bank_acc_manage")!="-1"}'>
				<div name="mf_cus_bank_acc_manage" class="formDiv">
					<h3>银行卡账号管理</h3>
					<div class="iframe_content">
						<iframe id="iframe_content"
							src="${webPath}/mfCusBankAccManage/getListPageBig?cusName=${cusName}&cusNo=${cusNo}&flag=insert"
							frameborder="0" style="overflow: hidden;" scrolling="no"></iframe>
					</div>
				</div>
			</c:if>
		</div>
	<!--时间轴的问题-->
	<div class="timeLine">
		<div class="time_contents">
			<div class="time-line-bg">
				<div style="height: 37px;" class="time-line-line"></div>
				<div class="time-line-body">
					<dl class="time-line-dl">
					</dl>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

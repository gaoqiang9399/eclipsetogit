<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String receId = (String)request.getParameter("receId");
%>
<script type="text/javascript" src="${webPath}/component/pact/receaccount/pubview/js/pub_rece_base_head_info.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    pub_rece_base_head_info.receId = '<%=receId%>';
    $(function(){
        pub_rece_base_head_info.init();
    });

</script>
<!--头部主要信息 rece-account-head-info -->
<div class="row clearfix head-info">
	<!--头像 -->
	<div class="col-xs-3 column text-center head-img padding_top_20">
		<div style="position:relative;">
			<button type="button" class="btn btn-font-pledge font-pledge-div padding_left_15">
				<i class="i i-rece font-icon"></i>
				<div class="font-text-left">应收账款</div>
			</button>
		</div>
	</div>
	<!--概要信息 -->
	<div class="col-xs-9 column head-content">
		<div class="row clearfix">
			<div class="col-xs-10 column button-his">
				<div class="margin_bottom_10">
					<dhcc:pmsTag pmsId="pledge-prove">
						<button class="btn btn-view  btn-lightgray" type="button" id= "recePledge" onclick="pub_rece_base_head_info.getReceTranInfo();">
							<i class="i i-ruku"></i><span>转让证明</span>
						</button>
					</dhcc:pmsTag>
					<dhcc:pmsTag pmsId="pledge-repo-affirm-info">
						<button class="btn btn-view  btn-lightgray" type="button" id="repoAffirmInfo" onclick="pub_rece_base_head_info.getRepoAffirmInfo();">
							<i class="i i-fanzhuanrang"></i><span>反转让确认信息</span>
						</button>
					</dhcc:pmsTag>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-xs-12 column info-collect">
				<table>
					<tr>
						<td>
							<p class="cont-title">转让总额</p>
							<p><span id='receTransAmt' class="content-span"></span><span class="unit-span">元</span></p>
						</td>
						<td>
							<p class="cont-title">转让余额</p>
							<p><span id='receTransBal' class="content-span"></span><span class="unit-span">元</span></p>
						</td>
						<td>
							<p class="cont-title">账款到期日</p>
							<p><span id='receEndDate' class="content-span"></span></p>
						</td>
						<td>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>
	

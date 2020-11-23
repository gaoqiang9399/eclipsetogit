<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="app.component.common.BizPubParm"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    request.setAttribute("CUS_BASE_TYPE_QUDAO", BizPubParm.CUS_BASE_TYPE_QUDAO);
    request.setAttribute("CUS_BASE_TYPE_QUDAOB", BizPubParm.CUS_BASE_TYPE_QUDAOB);
    request.setAttribute("CUS_BASE_TYPE_ZIJIN", BizPubParm.CUS_BASE_TYPE_ZIJIN);
    request.setAttribute("CUS_BASE_TYPE_HEXIAN", BizPubParm.CUS_BASE_TYPE_HEXIAN);
    request.setAttribute("CUS_BASE_TYPE_WAERHOUSE", BizPubParm.CUS_BASE_TYPE_WAERHOUSE);
%>
<script type="text/javascript">
	var cusNo='${param.cusNo}';
	$(function(){
		var baseType='${param.baseType}';//客户类型
		var projectName='${projectName}';//项目名称
		if(baseType == '<%=BizPubParm.CUS_BASE_TYPE_QUDAO%>' || baseType == '<%=BizPubParm.CUS_BASE_TYPE_QUDAOB%>'){//渠道商和个人渠道
			$.ajax({
				url:webPath+"/mfBusTrench/getTrenchBusHisAjax",
				data:'trenchUid='+cusNo,
				dataType:'json',
				type:'post',
				success:function(data){
					if(data.flag=="success"){
						$("#totalCnt").text(data.totalCnt);
						$("#appTotalAmt").text(data.appTotalAmt);
						$("#fincTotalAmt").text(data.fincTotalAmt);
						$("#fincCount").text(data.fincCount);
						$("#overdueAmt").text(data.overdueAmt);
						$("#overdueCount").text(data.overdueCount);
					}
				}
			})
		}else if(baseType=='<%=BizPubParm.CUS_BASE_TYPE_HEXIAN%>'){//核心企业
			$.ajax({
				url:webPath+"/mfCusCoreCompany/getCoreCompanyBusHisAjax",
				data:{cusNo:cusNo},
				dataType:'json',
				type:'post',
				success:function(data){
					if(data.flag=="success"){
						$("#totalAmt").text(data.totalAmt);
						$("#totalCnt").text(data.totalCnt);
					}
				}
			})
		}else if(baseType=='<%=BizPubParm.CUS_BASE_TYPE_WAERHOUSE%>'){//仓储机构
            $.ajax({
                url:webPath+"/mfCusWarehouseOrg/getWarehouseOrgBusHisAjax",
                data:{cusNo:cusNo},
                dataType:'json',
                type:'post',
                success:function(data){
                    if(data.flag=="success"){
                        $("#totalAmt").text(data.totalAmt);
                        $("#totalCnt").text(data.totalCnt);
                    }
                }
            })
        }
		
	});
	//渠道商或者个人渠道
	function getTrenchCus(){
		top.openBigForm('${webPath}/mfBusApply/getTrhListPage?cusNo='+cusNo,'历史申请业务',function() {});
	}
	function getChengJiaoList(){
		top.openBigForm('${webPath}/mfBusPact/getFinshPactTrhListPage?cusNo='+cusNo,'历史放款业务',function() {});
	}
    function cusInfoByTrenchPage() {
        top.openBigForm('${webPath}/mfCusCustomer/getEntCusListPage?cusNo='+cusNo,'推荐客户列表',function() {});
    }
    function getOverdueList() {
        top.openBigForm('${webPath}/mfBusFincApp/getOverdueListPage?cusNo='+cusNo,'还款逾期放款列表',function() {});
    }
	//资金机构 
	function getAgenciesChengJiaoList(){
		top.openBigForm('${webPath}/mfBusFincApp/getAgenciesChengJiaoListPage?agenciesUid='+cusNo,'历史完结业务',function() {});
	}
	function getAgenciesCus(){
		top.openBigForm('${webPath}/mfBusPact/getAgenciesCusListPage?agenciesUid='+cusNo,'历史业务详情',function() {});
	}
	//核心企业
	function getCoreCompanyChengJiaoList(){
		top.openBigForm('${webPath}/mfBusFincApp/getCoreCompanyChengJiaoListPage?cusNo='+cusNo,'历史完结业务',function() {});
	}
	function getCoreCompanyCus(){
		top.openBigForm('${webPath}/mfBusPact/getCoreCompanyCusListPage?cusNo='+cusNo,'历史业务详情',function() {});
	}
	//仓储机构
	function getWarehouseOrgChengJiaoList(){
		top.openBigForm('${webPath}/mfBusFincApp/getWarehouseOrgChengJiaoListPage?cusNo='+cusNo,'历史完结业务',function() {});
	}
	function getWarehouseOrgCus(){
		top.openBigForm('${webPath}/mfBusPact/getWarehouseOrgCusListPage?cusNo='+cusNo,'历史业务详情',function() {});
	}
</script>
<!--历史业务统计 -->
<div class="row clearfix padding_top_10">
	<div class="col-xs-12 col-md-12 column padding_left_15">
		<!-- 核心企业 -->
		<c:if test="${param.baseType == CUS_BASE_TYPE_HEXIAN}">
			<button class="btn btn-link block-title" onClick="getCoreCompanyCus()">历史业务统计</button>
			<button type="button" class="btn btn-font-qiehuan pull-right" onClick="getCoreCompanyCus()"><i class="i i-qiehuan" style="font-size:22px;"></i></button>
		</c:if>	
		<!-- 仓储机构 -->
		<c:if test="${param.baseType == CUS_BASE_TYPE_WAERHOUSE}">
			<button class="btn btn-link block-title" onClick="getWarehouseOrgCus()">历史业务统计</button>
			<button type="button" class="btn btn-font-qiehuan pull-right" onClick="getWarehouseOrgCus()"><i class="i i-qiehuan" style="font-size:22px;"></i></button>
		</c:if>
	</div>
</div>
<div class="row clearfix padding_left_12 his-statistic">
	<div class="col-xs-12 col-md-12 column" >
		<table>
			<tbody>
				<tr><!--等以后有了其它客户类型，可将此区域改为动态生成-->
					<!-- 渠道商 -->
					<c:if test="${(param.baseType == CUS_BASE_TYPE_QUDAO or param.baseType == CUS_BASE_TYPE_QUDAOB) }">
						<td >
							<p class="ptitle">客户总数</p>
							<p class="pvalue"><a href='javascript:void(0)' onclick='cusInfoByTrenchPage()'><span id="totalCnt">0</span></a><span>&nbsp;个</span></p>
						</td>
						<td >
							<p class="ptitle">申请金额</p>
							<p class="pvalue"><a href='javascript:void(0)' onclick="getTrenchCus()"><span id="appTotalAmt"></span></a><span>&nbsp;万元</span></p>
						</td>
					</c:if>
					<!-- 核心企业 -->
					<c:if test="${param.baseType == CUS_BASE_TYPE_HEXIAN}">
						<td >
							<p class="ptitle">客户总成交额</p>
							<p class="pvalue"><a href='javascript:void(0)' onclick="getCoreCompanyChengJiaoList()"><span id="totalAmt">0.00</span></a><span>&nbsp;万元</span></p>
						</td>
						<td >
							<p class="ptitle">客户总数</p>
							<p class="pvalue"><a href='javascript:void(0)' onclick='getCoreCompanyCus()'><span id="totalCnt">0</span></a><span>&nbsp;个</span></p>
						</td>
					</c:if>
					<!-- 仓储机构 -->
					<c:if test="${param.baseType == CUS_BASE_TYPE_WAERHOUSE}">
						<td >
							<p class="ptitle">客户总成交额</p>
							<p class="pvalue"><a href='javascript:void(0)' onclick="getWarehouseOrgChengJiaoList()"><span id="totalAmt">0.00</span></a><span>&nbsp;万元</span></p>
						</td>
						<td >
							<p class="ptitle">客户总数</p>
							<p class="pvalue"><a href='javascript:void(0)' onclick='getWarehouseOrgCus()'><span id="totalCnt">0</span></a><span>&nbsp;个</span></p>
						</td>
					</c:if>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>					
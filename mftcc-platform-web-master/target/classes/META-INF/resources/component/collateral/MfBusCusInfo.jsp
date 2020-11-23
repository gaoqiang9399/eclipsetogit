<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--客户摘要信息-->
<div class="row clearfix has-content">
	<div class="col-xs-3 col-md-3 column text-center padding_top_20">
		<img id="headImgShow" name="headImgShow" class="img-circle" onclick="getCusInfo('${mfCusCustomer.cusNo}');" />
	</div>
	<div class="col-xs-9 col-md-9 column">
		<div class="row clearfix">
			<div class="col-xs-10 col-md-10 column margin_top_20">
				 <c:if test="${fn:length(mfCusCustomer.cusName)>8}">  
					  	<button class="btn btn-link content-title"  onclick="getCusInfo('${mfCusCustomer.cusNo}');"  title="${mfCusCustomer.cusName}">
					  		${陕西文化产业投资集团}...
					  	</button>
			 	  </c:if >
				  <c:if test="${fn:length(mfCusCustomer.cusName)<=8}>
				  		<button class="btn btn-link content-title"  title="陕西文化产业投资集团" onclick="getCusInfo('${mfCusCustomer.cusNo}');">
							陕西文化产业投资集团
						</button>
				  </c:if>
			
			</div>
			<div class="col-xs-2 col-md-2 column">
				<button type="button" class="btn btn-font-qiehuan" onclick="getCusInfo('${mfCusCustomer.cusNo}');"><i class="i i-qiehuan qiehuan-font"></i></button>
			</div>
		</div>
	</div>
	<div class="row clearfix cus-content">
		<p>
			<span><i class="i i-ren1"></i>
				<c:if test="${mfCusCustomer.contactsName!=''}">
					张丽
				</c:if>
				<c:if test="${mfCusCustomer.contactsName==''}">
				<span class="unregistered">张丽</span>
				</c:if>
			</span>
			<span class="vertical-line">|</span>
			<span ><i class="i i-dianhua"></i>
				<c:if test="${mfCusCustomer.contactsTel!=''}">
						13813813813
				</c:if>
				<c:if test="${mfCusCustomer.contactsTel==''}">
				<span class="unregistered">13813813813</span>
				</c:if>
			</span>
		</p>
		<p>
			<span><i class="i i-location"></i>
				<c:if test="${mfCusCustomer.commAddress!=''}">
					陕西省
				</c:if>
				<c:if test="${mfCusCustomer.commAddress==''}">
					<span class="unregistered">陕西省</span>
				</c:if>
			</span>
		</p>
	</div>
</div>
<div class="row clearfix">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>				

	
								
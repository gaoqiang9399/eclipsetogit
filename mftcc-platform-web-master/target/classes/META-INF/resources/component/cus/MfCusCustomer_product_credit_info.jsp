<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="productCreditInfo" name="productCreditInfo" title="产品授信明细信息" class="dynamic-block" style="height: auto;display: none;">
    <div class="list-table">
        <div class="title" style="background:#f8f9fc;height: 37px;">
            <span><i class="i i-xing blockDian"></i>产品授信明细信息</span>
            <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#mfCusProductCreditInfo">
                <i class='i i-close-up'></i>
                <i class='i i-open-down'></i>
            </button>
        </div>
        <div class="content margin_left_15 collapse in" id="mfCusProductCreditInfo">
        </div>
    </div>
</div>
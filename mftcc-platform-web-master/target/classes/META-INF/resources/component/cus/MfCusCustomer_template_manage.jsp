<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc"%>
<script type="text/javascript">
    var cusNo_pl = (typeof (cusNo) == 'undefined') ? '' : cusNo;// 客户号
    // 文档
    var temBizNo = cusNo_pl;// 文档关联的业务主键，可以是申请号、客户号、申请号、借据号及其他功能编号
    var temParm = 'cusNo=' + cusNo_pl ;// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId
    var nodeNo = cusType;
    var querySaveFlag_pl =(typeof (querySaveFlag) == 'undefined') ? '0' : querySaveFlag; //电子文档列表查询方法（1-该节点只查询已经保存过的文档0-查询全部）
    var approvalNodeNo = '${approvalNodeNo}';
</script>
<!-- 文档 -->
<div class="row clearfix">
    <%@ include file="/component/model/templateIncludePage.jsp"%>
</div>
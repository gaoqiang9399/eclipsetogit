<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String allianceId = (String) request.getParameter("allianceId");
%>
<script type="text/javascript">
    var allianceId = '<%=allianceId%>';

    function openAddLimitWindow() {
        top.openBigForm(webPath + "/mfBusAlliance/allianceTakupHisInput?allianceId=" + allianceId, "追加额度", function () {
            allianceDetailInfo.init();
            takeupHis.init();
            getByIdAlliance();
        });
    }

    function getByIdAlliance() {
        $.ajax({
            url: webPath + "/mfBusAlliance/getByIdAlliance?allianceId=" + allianceId,
            type: 'post',
            dataType: 'json',
            success: function (data) {
                $("#loanLimit").html(data.loanLimit);
                $("#availableBalance").html(data.availableBalance);
            }
        });
    }

</script>
<div class="row clearfix btn-opt-group">
    <div class="col-xs-12 column">
        <div class="btn-group pull-right">
            <dhcc:pmsTag pmsId="alliance-takup-but">
                <button class="btn btn-opt cus-add" onclick="openAddLimitWindow();" stype="display:none;" type="button">
                    <i class="i i-bi"></i><span>额度变更</span>
                </button>
            </dhcc:pmsTag>
        </div>
    </div>
</div>

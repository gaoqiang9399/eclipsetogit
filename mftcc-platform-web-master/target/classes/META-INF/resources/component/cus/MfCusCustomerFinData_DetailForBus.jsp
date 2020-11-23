<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusFinData_Detail.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    $(function() {debugger;
        MfCusFinData_Detail.init();
    });
</script>
<div name="MfCusFinMainAction" title="财务报表" class="dynamic-block cus-fin">
	<div class="list-table">
		<div class="title">
			<span class="formName"> <i class="i i-xing blockDian"></i>财务报表</span>
			<button title="新增" onclick="MfCusFinData_Detail.getPfsDialog();return false;" class="btn btn-link formAdd-btn"><i class="i i-jia3"></i></button>
			<button data-target="#cusFinMainList" data-toggle="collapse" class=" btn btn-link pull-right formAdd-btn">
				<i class="i i-close-up"></i><i class="i i-open-down"></i>
			</button>
		</div>
		<div id="cusFinMainList" class="content collapse in">
			<table cellspacing="1" border="0" align="center" width="100%" class="ls_list" id="tablist">
				<colgroup style="width: 10%"></colgroup>
				<colgroup style="width: 10%"></colgroup>
				<colgroup style="width: 10%"></colgroup>
				<colgroup style="width: 10%"></colgroup>
				<colgroup style="width: 10%"></colgroup>
				<thead>
					<tr>
						<th align="center" width="10%" scope="col" name="rptDate" sorttype="0">报表日期</th>
						<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">资产负债表</th>
						<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">利润分配表</th>
						<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">现金流量表</th>
						<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">科目余额表</th>
						<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">操作</th>
					</tr>
				</thead>
				<tbody id="tab">

				</tbody>
			</table>
		</div>
	</div>
</div>

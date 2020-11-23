<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>日程管理</title>

<link href='${webPath}/component/nmd/fullcalendar/fullcalendar.css' rel='stylesheet' />
<link href='${webPath}/component/nmd/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
<link href='${webPath}/component/nmd/fullcalendar/FullCalender_list.css' rel='stylesheet' />
<script type="text/javascript" src="${webPath}/UIplug/jqueryUI/js/jquery-ui-1.10.1.custom.min.js"></script>
<!-- 若需要农历和节假日展示，则将fullcalendar.min.js替换为fullcalendar.js即可-->
<script src='${webPath}/component/nmd/fullcalendar/fullcalendar.min.js'></script>
</head>
<body>
	<div id='calendar'></div>
	
	<%@ include file="/component/nmd/eventDialog.jsp"%>
	<!--/.modal-dialog -->

	<div class="modal" id="viewEvent">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<table>
						<tr>
							<td><span class="event-type">类型1</span></td>
							<td><button type="button" class="edite">编辑</button></td>
							<td><button type="button" class="remove">删除</button></td>
							<td>
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
							</td>
						</tr>
					</table>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal -->
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/nmd/fullcalendar/FullCalendar_month_list.js?v=${cssJsVersion}" /></script>
<script type="text/javascript">
	FullCalendar_month_list.init();
</script>
</html>
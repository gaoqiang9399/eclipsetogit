<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="../include/pub.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta HTTP-EQUIV="pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<meta HTTP-EQUIV="expires" CONTENT="0">
<title>菜单[ ${statu.menuNo} ]的按钮配置</title>
<style type="text/css">
.lev_one {
	background-color: #FCEECD;
}

.lev_two {
	background-color: rgb(250, 250, 250);
}

.lev_blank {
	background-color: rgb(243, 250, 243);
}
</style>
<script type="text/javascript">
	function updateBtn(obj) {
		var buttonNo = $(obj).parent("td").parent("tr").attr("id");
		var desc = $(obj).parent("td").prev("td").find(
				"input[name='buttonDesc']").val();
		$.post(webPath+"/sysButton/updateBtn", {
			buttonNo : encodeURI(buttonNo),
			menuNo : $("#menuNo").val(),
			buttonDesc : encodeURI(desc),
			rdm : Math.random()
		}, function(data) {
			if ($.trim(data) == "fail") {
				alert("按钮修改失败!");
			} else {
				alert("按钮修改成功!");
			}
		});
	}
	function deleteBtn(obj) {
		if (!$(obj).parent("td").parent("tr").attr("id")) {
			if ($("#lastline").attr("class") == "lev_two") {
				$("#lastline").attr("class", "lev_blank");
			} else {
				$("#lastline").attr("class", "lev_two");
			}
			$("#lastline").prev("tr").remove();
			return;
		}
		var buttonNo = $(obj).parent("td").parent("tr").find("td:first-child")
				.find("input:text").val();
		if (confirm("确定删除按钮[ " + buttonNo + " ]?")) {
			$.post(webPath+"/sysButton/deleteBtn", {
				buttonNo : encodeURI(buttonNo),
				menuNo : $("#menuNo").val(),
				rdm : Math.random()
			}, function(data) {
				if ($.trim(data) == "fail") {
					alert("按钮删除失败!");
				} else {
					$("#" + buttonNo).remove();
				}
			});
		}
	}
	function insertBtn() {
		if ($("#lastline").attr("class") == "lev_two") {
			$("#lastline").attr("class", "lev_blank");
		} else {
			$("#lastline").attr("class", "lev_two");
		}
		$("#lastline")
				.before(
						"<tr class=\"lev_two\"><td align=\"center\"><input type=\"text\" name=\"buttonNo\" /></td><td align=\"center\"><input type=\"text\" name=\"buttonDesc\" size=\"40\"/></td><td align=\"center\"><a href=\"#\" onclick=\"saveNew(this)\">保存新增</a> | <a href=\"#\" onclick=\"deleteBtn(this)\">删除</a></td></tr>");
	}
	function saveNew(obj) {
		var buttonNo = $(obj).parent("td").parent("tr").find("td:first-child")
				.find("input:text").val();
		if ($.trim(buttonNo) == "") {
			alert("请输入按钮编号!");
			return;
		}
		var desc = $(obj).parent("td").prev("td").find(
				"input[name='buttonDesc']").val();
		$.post(webPath+"/sysButton/insertBtn", {
			buttonNo : encodeURI(buttonNo),
			menuNo : $("#menuNo").val(),
			buttonDesc : encodeURI(desc),
			rdm : Math.random()
		}, function(data) {
			if ($.trim(data) == "fail") {
				alert("按钮新增失败!");
			} else {
				alert("按钮新增成功!");
				$(obj).parent("td").parent("tr").attr("id", buttonNo);
				$(obj).parent("td").parent("tr").find("td:first-child").find(
						"input:text").attr("readonly", "readonly");
				$(obj).parent("td").parent("tr").find("td:nth-child(2)").find(
						"a:first-child").bind("click", function(event) {
					updateBtn($(this));
				});
				$(obj).text("保存修改");
			}
		});
	}
</script>
</head>
<body class="body_bg">
	<div class="right_bg">
		<div class="right_w">
			<div class="from_bg">
				<div class="right_v">

					<div class="tabCont">
						<div class="tabTitle" style="float: left;">菜单按钮配置</div>
						<input type="button" value="添加按钮" onclick="insertBtn();"
							class="t_ico_tj">
					</div>
					<form name="cms_form" theme="simple" validate="false">
					     ${statu.menuNo}
						<table class="ls_list" width="100%" border="0" cellpadding="0"
							cellspacing="1">
							<colgroup>
							</colgroup>
							<colgroup>
							</colgroup>
							<colgroup>
							</colgroup>
							<thead>
								<tr>
									<th scope="col" nowrap="nowrap"><b>按钮编号</b>
									</th>
									<th scope="col" nowrap="nowrap"><b>按钮描述</b>
									</th>
									<th scope="col" width="120px"><b>操作</b>
									</th>
								</tr>
							</thead>
							<tbody id="tab">
								<c:forEach items="${sysButtonList }" vaStatus="status">
									<tr id="${buttonNo}"
									                     
										class="<c:if test="${status.index%2==0 }">lev_two</c:if>
										    <c:if test="${status.index%2!=0 }">lev_blank</c:if>">
										<td align="center"><input type="text" name="buttonNo"
											value="${buttonNo}" readonly="readonly" />
										</td>
										<td align="center"><input type="text" name="buttonDesc"
											value="${buttonDesc}" size="40" />
										</td>
										<td align="center"><a href="#" onclick="updateBtn(this)">保存修改</a>
											| <a href="#" onclick="deleteBtn(this)">删除</a></td>
									</tr>
								</c:forEach>
								<tr
									class="<c:if test="${statu.sysButtonList.size()%2!=0}">lev_two</c:if>
									<c:if test="${statu.sysButtonList.size()%2==0}">lev_blank</c:if>"
									id="lastline">

									<td colspan="3" align="center"><div class="from_btn">
											<input type="button" value="关闭" class="button3"
												onclick="javascript:window.close();" />
										</div>
									</td>
								</tr>

							</tbody>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
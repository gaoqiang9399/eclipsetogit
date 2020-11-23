<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
			$(function() {
				$('input,textarea,select').focus(function(){
					var $this = $(this);
					$this.parent().parent().find('.formLbl').addClass("formLbl-focus");
					$this.blur(function(){
						$this.parent().parent().find('.formLbl').removeClass("formLbl-focus");
					});
				});
				
				$('input[type=text]').poshytip({
						//content: 'A 总价款-T;实际融资额-A;首付款-D;保证金-S;手续费-F;杂费-L;其他金额-O;名义价款-N',
						content: '[title]',
						className: 'tip-twitter',
						showOn: 'focus',
						alignTo: 'target',
						alignX: 'inner-left',
						alignY: 'bottom',
						offsetY:5,
						offsetX:2
				});
				$(".ls_list tbody").find("tr").each(function() {
					var obj = $(this).find("td").eq(0);
					$(obj).unbind();
					$(obj).bind("click",
						function() {
							var url = $(obj).find("a").attr("href");
							$(".content_form").slideDown("slow");
							return false;
						});
				});
				$(".form_select").click(function() {
					$(this).css("background", "#fff")
				})
				$(".form_select").blur(function() {
					$(this).css("background", "#fefefe")
				})
				$('.dateBtn').bind("click",
					function() {
						var $this = $(this).parent().find("input[type=text]");
						var id = "#" + $this.attr("id");
						laydate({
							elem: id
						});
					}
				)
			});

			function submitBtn(obj) {
				$(".content_form").slideUp();
			}

			function colseBtn() {
				$(".content_form").slideUp();
			}

/* 			function func_uior_valTypeImm(obj) {
				var $this = $(obj);
				var val = $this.val();
				if ($this.hasClass("Required")) {
					$this.removeClass("Required");
				}
				if ($this.parent().find(".Required-font").length > 0) {
					$this.parent().find(".Required-font").remove();
				}
				if (val == null || val == "" || typeof(val) == "undefined") {
					var $label = $('<label class="Required-font"><span></span>' + $this.attr("title") + '不能为空</label>')
					$label.appendTo($this.parent());
					$this.addClass("Required");
				}
			}
 */
			function namefirm() {}

			function enterKey() {}

			function viewDate(obj) {
				var $this = $(obj);
				var val = $this.val();
				if (val != null && val != "" && typeof(val) != "undefined") {
					$this.val(val.replace(/-/g, ""));
				}
			}

			function formatDate(obj) {
				var $this = $(obj);
				var val = $this.val();
				if (val != null && val != "" && typeof(val) != "undefined") {
					$this.val(val.substring(0, 4) + "-" + val.substring(4, 6) + "-" + val.substring(6, 8));
				}
			}
		</script>
	</head>
	<body>
	<div class="bigform_content" style="padding: 18px 320px 50px 50px;">
		<div name="formtest-1" class="formDiv">
			<h3> 评级信息信息 </h3>
			<form  method="post" theme="simple" name="operform"
				action="${webPath}/demo/insert">
				<dhcc:bigFormTag property="formeval1004" mode="query"/>
				<div class="formRow">
	    			<input type="button" onclick="ajaxSave(this.form)" value="保存"  />
	    		</div>
			</form>
		</div>
		<div name="formtest-2" class="formDiv">
			<h3> 评级结果信息 </h3>
			<form  method="post" theme="simple" name="operform"
				action="${webPath}/demo/insert">
				<dhcc:bigFormTag property="formeval1005" mode="query"/>
				<div class="formRow">
	    			<input type="submit" value="提交"  />
	    		</div>
			</form>
		</div>
		<div name="formtest-4" class="formDiv">
				<h3> 评级列表 </h3>
				<div class="content_body">
					<div class="content_table">
						<dhcc:tableTag property="tableeval1001" paginate="appEvalList" head="true"></dhcc:tableTag>
						<div class="tool-container gradient tool-top tool-rounded" style="display: block; position: absolute; opacity:0">
							<div class="tool-items"> </div>
						</div>
						<div style="display: none;" class="content_form">
							<form  method="post" theme="simple" name="operform" action="${webPath}/appEval/insert">
								<div class="content_Btn">
									<input value="提交" onclick="submitBtn(this.form)" type="button">
									<input value="取消关闭" onclick="colseBtn()" type="button">
								</div>
								<dhcc:formTag property="formeval1003" mode="query"></dhcc:formTag>
						    </form>
						</div>
					</div>
				</div>
		</div>
	</div>
	<div class="timeLine">
			<div class="time_contents">
				<div class="time-line-bg">
					<div style="height: 37px;" class="time-line-line"> </div>
					<div class="time-line-body">
						<dl class="time-line-dl">
						</dl>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body>
		<div class="bigform_content">
			<div name="formtest-1" class="formDiv">
				<h3> 客户信息 </h3>
				<form  method="post" theme="simple" name="operform" action="${webPath}/demo/updateAjax">
					<dhcc:bigFormTag property="formdemo0006" mode="query"/>
					<div class="formRow">
		    			<dhcc:button value="保存" action="保存" onclick="ajaxUpdate(this.form)"></dhcc:button>
		    		</div>
				</form>
			</div>
			<div name="formtest-2" class="formDiv">
				<h3> 业务信息 </h3>
				<form  method="post" theme="simple" name="operform" action="${webPath}/demo/updateAjax">
					<dhcc:bigFormTag property="formdemo0006" mode="query"/>
					<div class="formRow">
						<dhcc:button value="保存" action="保存" onclick="ajaxUpdate(this.form)"></dhcc:button>
		    		</div>
				</form>
			</div>
			<div name="formtest-4" class="formDiv">
				<h3> 租赁物信息 </h3>
				<div class="content_body">
					<div class="content_table">
						<div class="input_btn">
							<dhcc:button value="新增" action="新增" onclick="ajaxInput(this)"></dhcc:button>
						</div>
						<dhcc:tableTag property="tabledemo0002" paginate="demoList" head="true"></dhcc:tableTag>
						<div class="tool-container gradient tool-top tool-rounded" style="display: block; position: absolute; opacity:0">
							<div class="tool-items"> </div>
						</div>
						<div style="display: none;" class="content_form">
							<form  method="post" theme="simple" name="operform" action="${webPath}/demo/updateAjax">
								<div class="content_Btn">
									<dhcc:button value="保存" action="保存" onclick="ajaxUpdate(this.form)"></dhcc:button>
									<dhcc:button value="取消" action="取消" onclick="colseBtn()"></dhcc:button>
								</div>
								<dhcc:formTag property="formdemo0007" mode="query"></dhcc:formTag>
						    </form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--时间轴的问题-->
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

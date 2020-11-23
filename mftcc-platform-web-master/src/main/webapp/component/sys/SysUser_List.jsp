<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href="${webPath}/component/sys/css/sysUser.css" />
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="${webPath}/component/sys/js/sysUser.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/idvalidate.js"></script>
		<script type="text/javascript" >
		var ajaxData = '${ajaxData}';
		var mcsBar;
		var opNoType = "${opNoType}";
	    ajaxData = eval("("+ajaxData+")");
			$(function(){
			   mcsBar = myCustomScrollbar({
				    	obj:"#content",//页面内容绑定的id
				    	url:webPath+"/sysUser/findByPageAjax?opNoType="+opNoType,//列表数据查询的url
				    	tableId:"tablesys5001",//列表数据查询的table编号
				    	tableType:"thirdTableTag"//table所需解析标签的种类
			    		/* callback:function(){
					    	$("table").tableRcswitcher({
					    	name:"userFlag",onText:"生效",offText:"失效"});
					    }// */
				    });
			 });
		</script>
		<script type="text/javascript">
			function clearError(obj) {
				$(obj).removeClass("Required");
				//console.log("下一个节点"+$(obj).next().next());
				//console.log($(obj).next().next().text());
				if($(obj).next().next().text()=="不能为空") {
					$(obj).next().next().remove();
				}
			}
		</script>
		<style type="text/css">
			.Required {
				border:1px solid red;
			}
			.Required-font {
				color:red;
			}
			.search-group .filter_btn_myFilter{
				margin-left:-6px;
			}
			.select3-placeholder{
				line-height:16px;
			}
		</style>
	</head>	
<body style="overflow-y: hidden;">
	<dhcc:markPoint markPointName="SysUser_List"/>
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-9 column">
							<button type="button" class="btn btn-primary pull-left" onclick="insertSysUser();">新增</button>
							<ol class="breadcrumb pull-left">
								<li><a href="${webPath}/sysDevView/setC?setType=orga">机构设置</a></li>
								<li class="active">用户列表 </li>
							</ol>
						</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2"/>
					</div>
				</div>
			</div>
			<!--页面显示区域-->
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
					</div>
				</div>
			</div>
		</div>
		
		<div class="sys-user-body">
			<div class="sys-user-model">
			<form name="sysUserInfo">
				<div class="sys-user-edit">
					<div class="sys-user-label">
						<span>用户信息</span> <i class="i i-x5"></i>
					</div>
					<table id="userTable">
						<tr>
							<td>所在机构：<font color="#FF0000">*</font></td>
							<td>
								<div id="userOrg" onclick="clearError(this);"></div> <input name="brNo" id="brNo"
								type="hidden" />
							</td>
							
							<td>用户名称：<font color="#FF0000">*</font></td>
							<td><input name="opName" mustinput="1" id="opName" type="text" onblur="func_uior_valTypeImm(this);" /></td>
						</tr>
						<tr>
							<td>登录账号:&nbsp;&nbsp;</td>
							<td><input name="opNo" id="opNo" readonly="readonly" type="text" value="111111"/></td>
							<td>用户密码：<font color="#FF0000">*</font></td>
							<td><input name="passwordhash" id="passwordhash"
								type="password" mustinput="1" onblur="func_uior_valTypeImm(this);"/></td>
								
						</tr>
						<tr class="sys-user-roles">
							<td>角色设置：<font color="#FF0000">*</font></td>
							<td colspan="3">
								<div id="userRole"onclick="clearError(this);" onclick="clearError(this);"></div> <input name="roleNo" id="roleNo"
								type="hidden" mustinput="1" onblur="func_uior_valTypeImm(this);"/>
							</td>
						</tr>
						<tr>
							<td>生日：</td>
							<td><input name="birthday" id="birthday"
								onclick="fPopUpCalendarDlg()" type="text" /></td>
							<td>身份证号：</td>
							<td><input name="idNo" id="idNo" type="text" /></td>
						</tr>
						<tr>
							<td>手机：&nbsp;&nbsp;</td>
							<td><input name="mobile" id="mobile" type="text" onblur="checkMobileOnly(mobile);"/></td>
							<td>办公电话：</td>
							<td><input name="businesstelephone" id="businesstelephone"
								type="text" /></td>
						</tr>
						<tr>
							<td>性别：</td>
							<td>
								<div id="userSex"></div> <input name="sex" id="sex"
								type="hidden" />
							</td>
							<td>职位：</td>
							<td><input name="job" id="job" type="text" /></td>
						</tr>
						<tr>
							<td>传真：</td>
							<td><input name="businessfax" id="businessfax" type="text" /></td>
							<td>邮箱：</td>
							<td><input name="email" id="email" type="text" /></td>
						</tr>
						<tr>
							<td>状态：<font color="#FF0000">*</font></td>
							<td >
								<div id="userSts" mustinput="1" onblur="func_uior_valTypeImm(this);" onclick="clearError(this);"></div> <input name="opSts" id="opSts"
								type="hidden"  />
							</td>
							<td></td>
							<td></td>
						</tr>
						<tr style="display: none;">
							<td>菜单框架：</td>
							<td><input name="frame" id="frame" type="text" /></td>
							<td>系统皮肤：</td>
							<td><input name="color" id="color" type="text" /></td>
						</tr>
						<tr style="display: none;">
							<td colspan="2">上次修改密码日期：</td>
							<td colspan="2"><input name="lastModDate" id="lastModDate"
								disabled="disabled" /></td>
						</tr>
						<tr style="display: none;">
							<td colspan="2">密码失效日期：</td>
							<td colspan="2"><input name="pwdInvalDate"
								id="pwdInvalDate" disabled="disabled" /></td>
						</tr>
						<tr>
							<td colspan="4"><button class="sys-user-save" type="button"
								onclick="saveSysUserInfo(this.form)"  >保存</button></td>
						</tr>
					</table>
				</div>
			</form>
		</div>
		</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
	<script type="text/javascript">
		addDefFliter("3","用户角色","roleNo","ROLE_NO","");
		
	filter_dic =[{"optCode":"opName","optName":"操作员名称","dicType":"val"},{"optCode":"idNo","optName":"身份证","dicType":"val"},{"optCode":"mobile","optName":"手机","dicType":"val"}];</script>
</html>

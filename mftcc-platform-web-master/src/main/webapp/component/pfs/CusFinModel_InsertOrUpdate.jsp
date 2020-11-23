<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%
	String path = request.getContextPath();
%>
<html>
  <head>
      <title>财务报表模型管理</title>
      <!--czk 201609-29 相对lease项目，factor未在web.xml中配置该dwr，所以无法查询到结果
      	<create javascript="finmodeldwr" creator="new">
			<param name="class" value="app.component.pfs.fajax.FinModelDwr" />
			<include method="getFinModelList"></include>
		</create>
        -->
		<script type="text/javascript" src="${webPath}/component/pfs/js/fin_model.js" ></script>
    	<script type='text/javascript' src='${webPath}/dwr/interface/finmodeldwr.js'></script>
		<script type='text/javascript' src='${webPath}/dwr/engine.js'></script>
		<style>
		.TabOrup{ font-size:12px;}
		.TabOrup select, .TDstyle01 select{padding:0 8px;}
		.TabOrupInput {
		    background-color: #54aced;
		    border: medium none;
		    border-radius: 2px;
		    color: #fff;
		    height: 25px;
		    padding: 0 10px;
		    width: auto;
		    display:inline-block;
		}
		.TabOrupInput:hover{
			background-color: #0c90db;
		}
		.TabMainTr{
			 background-color: #fff!important;
			 font-size:12px;
		}
		.ls_list1{
			 border:1px solid #d7d9d6;
			 margin-top:16px;
			 margin-left:40px;	
			 width:90%;
		}
		.ls_list2{
			 border:1px solid #d7d9d6;
			 margin-top:16px;
			 width:90%;
			 float:left;
		}
		.ls_list1 th{
			 height:26px;
			 line-height:26px; 
			 background-color:#f1f1f1;
			 border-bottom:1px solid #d7d9d6;
	    }
	    .ls_list2 th{
			 height:26px;
			 line-height:26px; 
			 background-color:#f1f1f1;
			 border-bottom:1px solid #d7d9d6;
	    }
	    .button_form{
	    	background-color: #ececec;
		    border: medium none;
		    border-radius: 2px;
		    color: #666;
		    height: 25px;
		    padding: 0 10px;
		    width: 130px;
		    text-align:center;
		    display:block;
		    margin-bottom:6px;
	    }
	     .button_form:hover{background-color: #cfdae6;}
	    .button_form2{
	    	background-color: #54aced;
		    color: #fff;
	    }
	    .button_form3{
	    	background-color: #e0edf6;
	    }
	     .button_form2:hover{
	    	background-color: #0c90db;
	    }
	    .button_form3:hover{
	    	background-color: #d2d9dc ;
	    }
	     
	    .button_form4{
	    	background:url(component/pfs/imgs/arright.png) no-repeat #ececec center;
	    }
	    .button_form5{
	    	background:url(component/pfs/imgs/arleft.png) no-repeat #ececec center;
	    }
  		</style>
  </head>
  
<body class="body_bg">

<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v" style=" height:40px;line-height:40px;border-bottom:1px solid #ebebeb;">
		<table class="searchstyle TabOrup">
			<tr>
				<td style="line-height:38px;">报表类别:
					<!--<dhcc:translate keyName="REPAY_TYPE" fieldName="reportType" beanObj="cusFinModel"/>
	     			<dict:select property="reportType" ddname="REPORT_TYPE" allownull="true" />-->
	     			<select name="reportType">
						<option value=""></option>
						<option value="1">资产负债表</option>
						<option value="2">利润及利润分配表</option>
						<option value="3">现金流量表</option>
					</select>
	     			&nbsp;&nbsp;&nbsp;&nbsp;
	     			会计准则:
	     			<!--<dhcc:translate keyName="ACC_RULE" fieldName="accRule" beanObj="cusFinModel"/>
	     			<dict:select property="accRule" ddname="ACC_RULE" allownull="true" />-->
	     			<select name="accRule">
						<option value=""></option>
						<option value="0">旧会计准则</option>
						<option value="1">新会计准则</option>
					</select>
	     			&nbsp;&nbsp;&nbsp;&nbsp;
	     			<span>
	     			<input type="button" name="bt_query" value="&nbsp;查 询&nbsp;" onclick="func_query();" class="btn_80 TabOrupInput"/></span>
				</td>
			</tr>
		</table>
		</div></div></div></div>
		<div class="right_bg">
			<div class="right_w">
				<div class="from_bg">
					<div class="right_v" >
		<table width="100%"  cellspacing="0"><tr class="TabMainTr">
		<td style="vertical-align: top;" width="30%">
		<table id="left_table" class="ls_list ls_list1" cellpadding="0" cellspacing="1">
			<thead>
				<tr align="center">
					<th width="30px" class="first"><input type="checkbox" id="left_checkbox" onclick="checkbox_select(this,'left_checkbox')"></th>
					<th width="90%">字段名称</th>
				</tr>
			</thead>
			<tbody id="fin_parm_body"></tbody>
		</table>
    </td><td style="vertical-align: top;" width="30%">
		<table id="mid_table" class="ls_list" cellpadding="0" cellspacing="1">
			<thead>
				<tr align="center">
					<th>&nbsp;</th>
				</tr>
				</thead>
				<tbody>
				<tr align="center" class="t1">
					<td>&nbsp;</td>
				</tr>
				<tr align="center" class="t2">
					<td>&nbsp;</td>
				</tr>
				<tr align="center" class="t1">
					<td>&nbsp;</td>
				</tr>
				<tr align="center" class="t2">
					<td>&nbsp;</td>
				</tr>
				<tr align="center" class="t1">
					<td><span><input type="button" name="btn2" value="" onclick="func_toright();" class="button_form button_form4"/></span>
					</td>
				</tr>
				<tr align="center" class="t2">
					<td><span><input type="button" name="btn2" value="" onclick="func_toleft();" class="button_form button_form5"/></span></td>
				</tr>
				<tr align="center" class="t1">
					<td><span><input type="button" name="btn2" value="&nbsp;&nbsp;上移&nbsp;&nbsp;" onclick="func_moveup();" class="button_form"/></span>
					</td>
				</tr>
				<tr align="center" class="t2">
					<td><span><input type="button" name="btn2" value="&nbsp;&nbsp;下移&nbsp;&nbsp;" onclick="func_movedown();" class="button_form"/></span></td>
				</tr>
				<tr align="center" class="t1">
					<td>&nbsp;</td>
				</tr>
				<tr align="center" class="t2">
					<td>&nbsp;</td>
				</tr>
				<tr align="center" class="t1">
					<td><span><input type="button" name="btn2" value="&nbsp;&nbsp;保存&nbsp;&nbsp;" onclick="func_save();" class="button_form button_form2"/></span></td>
				</tr>
				<tr align="center" class="t2">
					<td><span><input type="button" name="btn2" value="&nbsp;&nbsp;修改&nbsp;&nbsp;" onclick="func_list();" class="button_form button_form3"/></span></td>
				</tr>
				<tr align="center" class="t1">
					<td>&nbsp;</td>
				</tr>
				<tr align="center" class="t2">
					<td>&nbsp;</td>
				</tr>
			</tbody>
		</table>
    </td><td style="vertical-align: top;">
		<table id="right_table" class="ls_list ls_list2" cellpadding="0" cellspacing="1">
			<thead>
				<tr align="center">
					<th width="30px"><input type="checkbox" id="right_checkbox" onclick="checkbox_select(this,'right_checkbox')"></th>
					<th>字段名称</th>
					<th>数据类型</th>
					<th class="last">行次</th>
				</tr>
			</thead>
			<tbody id="fin_model_body"></tbody>
		</table>
    </td></tr></table>
</div></div></div></div>
    <form name="operform" action="#" method="post">
    	<input type="hidden" id="cusFinModel_reportType" name="cusFinModel.reportType" value="${cusFinModel.reportType }"/>
        <input type="hidden" id="cusFinModel_codeName" name="cusFinModel.codeName" value="${cusFinModel.codeName }"/>
        <input type="hidden" id="cusFinModel_codeType" name="cusFinModel.codeType" value="${cusFinModel.codeType }"/>
        <input type="hidden" id="cusFinModel_codeColumn" name="cusFinModel.codeColumn" value="${cusFinModel.codeColumn }"/>
        <input type="hidden" id="cusFinModel_accRule" name="cusFinModel.accRule" value="${cusFinModel.accRule }"/>
        <input type="hidden" id="cusFinModel_cnts" name="cusFinModel.cnts" value="${cusFinModel.cnts }"/>
    </form>
	<div id="DIV_CODE_TYPE" style="display:none">
		<select name="code_type">
			<option value="1">字符</option>
			<option value="2">数字</option>
			<option value="3">金额</option>
		</select>
	</div>
</body>
</html>
<script language="javascript">
	javascript:window.history.forward(1);
</script>
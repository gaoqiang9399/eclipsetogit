<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
	<title>管理区域</title>
</head>
<body style="background:#f3f3f3;">
	
	<div class="right_bg">
		<div class="right_w">
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			
			<table class="tableStyle04" align="center" >
				
				<caption light><font class="tableStyleTitle"> SORRY<b>您的密码已失效&nbsp;:&nbsp;-&nbsp;(</b> <br>
				
				<span>为了您的账号安全，请重新设置密码！</span>
				</font></caption>
			
				<tr>
					<td align="right"  >
					<font size="2" color="blue" class="tableStyleFont">原始密码：</font>
					</td>
					
					<td align="left" >
						<input type="password" name="password1"  onblur="chkpwd_old(this)" maxlength="6"/>
					</td>
					<td style="width:150px; overflow:hidden; text-overflow:ellipsis">
						<div id="old_pwdDiv" ></div>
					</td>
				</tr>
				<tr>
					<td align="right">
						<font size="2" color="blue" class="tableStyleFont">新密码：</font>
					</td>
					<td align="left">
						<input type="password" name="password2" id="pwd2" onblur="chkpwd(this)" maxlength="6"/>
					</td>
					<td  style="width:150px; overflow:hidden; text-overflow:ellipsis">
						<div id="pwdDiv" style="color:red;"></div>
						<div id="chkResult"></div>
					</td>
				</tr>
				<tr>
					<td align="right">
						<font size="2" color="blue" class="tableStyleFont"> 再次输入：</font>
					</td>
					<td align="left">
						<input type="password" name="password3" id="pwd3" maxlength="6"/>
					</td>
					<td></td>
				</tr>
				<br>
			</table>

			<table align="center" class="tableStyle04Button">
					<tr>
						<td>
						<input type="button" value="提交" class="btn_80"  name="submit" onclick='func_submit()' />
						</td>
					</tr>
			</table>
			<form action="changePassWord.action" name="operform">
				<!--  <s:hidden name="changePWInfo" />-->
			</form>
		</div>
	</div>
	</div>
</body>
<script type="text/javascript">
    function chkpwd_old(obj){
        var value = obj.value;
        if(value==""){
        	old_pwdDiv.innerHTML = '<font color="red">原始密码不能为空！</font>';	
        	return false;
        }else{
        	old_pwdDiv.innerHTML = '';	
        }
    }
	function chkpwd(obj){
		var t=obj.value;
		var id=getResult(t);
		if(t==""){
        	pwdDiv.innerHTML = '<font color="red">新密码不能为空！</font>';	
        	return false;
        }else{
        	pwdDiv.innerHTML = '';	
        }
		//定义对应的消息提示
		var msg=new Array(4);
		msg[0]="密码过短。";
		msg[1]="密码强度差。";
		msg[2]="密码强度良好。";
		msg[3]="密码强度高。";
		
		var sty=new Array(4);
		sty[0]=-45;
		sty[1]=-30;
		sty[2]=-15;
		sty[3]=0;
		
		var col=new Array(4);
		col[0]="gray";
		col[1]="red";
		col[2]="#ff6600";
		col[3]="Green";
		
		//设置显示效果
		var bImg="images/imgs/checkpwd.gif";//一张显示用的图片
		var sWidth=155;
		var sHeight=15;
		var Bobj=document.getElementById("chkResult");
		getPwdMessage(t);
		Bobj.style.fontSize="11px";
		Bobj.style.color=col[id];
		Bobj.style.width=sWidth + "px";
		Bobj.style.height=sHeight + "px";
		Bobj.style.lineHeight=sHeight + "px";
		Bobj.style.background="url(" + bImg + ") no-repeat left " + sty[id] + "px";
		Bobj.style.textIndent="20px";
		Bobj.innerHTML="检测提示：" + msg[id];
			
	}
	  function getPwdMessage(s){
	  var selStr;
	  		$.getJSON("/CMSII/sec/securityAudit/modifyPwd",{pwd2:s,rdm:Math.random()},function(json){
	  		if(json.length > 0){
	  			 selStr = json[i].pwdmes;
	  			 if(selStr=="seak"){
	  			 	$("#pwdDiv").text('');
	  			 }else{
	  			 	$("#pwdDiv").text(selStr);
	  			 }
	  		}else{
	  			 selStr = "miss!";
	  		}
			});
	}
	
	//定义检测函数,返回0/1/2/3分别代表无效/差/一般/强
	function getResult(s){
		if(s.length < 4){
			return 0;
		}
		var ls = 0;
		if (s.match(/[a-z]/ig)){
			ls++;
		}
		if (s.match(/[0-9]/ig)){
			ls++;
		}
	 	if (s.match(/(.[^a-z0-9])/ig)){
			ls++;
		}
		if (s.length < 6 && ls > 0){
			ls--;
		}
		return ls
	}
function func_submit(){
	var pw1 = document.all("password1").value;
	var pw2 = document.all("password2").value;
	var pw3 = document.all("password3").value;
	if(pw1 == ""){
		alert(top.getMessage("NOT_FORM_EMPTY", "原始密码"));		
		return false;
	}
	if(pw2 == "" ){
		alert("请输入新密码！");		
		return false;
	}
	if(pw3==""){
	    alert("请再次输入新密码！");	  
		return false;
	}
	if(pw2 != pw3){
		alert("两次输入新密码必须一致！");
		return false;
	}
	var pw = pw1 + "/" + pw2;
	window.location.href='changePassWord.action?changePWInfo='+pw;
}
</script>
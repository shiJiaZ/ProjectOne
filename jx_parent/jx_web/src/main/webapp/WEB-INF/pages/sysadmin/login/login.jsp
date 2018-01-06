<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>商务综合管理平台</title>
	<link rel="stylesheet" rev="stylesheet" type="text/css" href="${ctx}/skin/default/css/login.css" media="all" />
	<script src="${ctx}/components/pngfix/DD_belatedPNG.js"></script>
	<script> DD_belatedPNG.fix('*'); </script>
</head>
<!-- 顶级enter，切换文本框，主要使用的技术：捕获按键的值 -->

<script type="text/javascript">
	var index=-1;

	function enter(e){
		var code = e.which;
		var inputs = document.getElementsByTagName("input");
		//捕获按键的值
		if(code==13){
			if(index==-1){
				//第一个文本框聚焦
				index=0;
				inputs[0].focus();
				inputs[0].select();
			}else if(index==0){
				index=1;
				inputs[1].focus();
				inputs[1].select();
			}else{
				document.forms[0].submit();			
			}
		}
	}
	//页面所有的文档元素都绑定这个时间
	//onkeydown可以识别键盘上任何位置的按键，但是不区分大小写
    //document.onkeydown=enter;
	//onkeypress区分大小写，但是只能识别键盘可读区域的内容
	  document.onkeypress=enter;
</script>


<body>
<form id="login_main" method="post">
<div id="png">
	<div class="box">
			<div class="inputstyle">
				<div class="inputlable">用户名：
					<input type="text" value="${userName}" name="username" id="userName" onFocus="this.select();" title="请您输入用户名"/>
					<div id="ts" style="z-index:1;">
					</div>
				</div>

			    <div class="inputlable">密　码：
					<input type="password" value="${password}" name="password" id="password" onfocus="$('#ts').css('display','none');this.select();"
						onKeyDown="javascript:if(event.keyCode==13){ submitFind(); }" title="请您输入密码"/>
				</div>
			</div>
			<div class="btnstyle">
				<input  class="loginImgOut" value="" type="button" onclick="formSubmit('${ctx}/login.action','_self');"
				  onmouseover="this.className='loginImgOver'" 
				  onmouseout="this.className='loginImgOut'"
				/>
				<input class="resetImgOut" value="" type="button"   
				  onmouseover="this.className='resetImgOver'" 
				  onmouseout="this.className='resetImgOut'"
				/>
			</div>
		  	<div class="msgtip">
				<c:if test="${!empty errorInfo}">
					${errorInfo}
				</c:if>
			</div>
	</div>
</div>
</form>

<script type="text/JavaScript">
	document.getElementById('login_main').userName.focus();
</script>

</body>
</html>



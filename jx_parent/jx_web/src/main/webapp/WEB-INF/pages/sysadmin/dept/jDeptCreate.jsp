<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>
<script type="text/javascript" src="${ctx }/js/jquery-1.8.3.min.js"></script>

<script type="text/javascript">
	function deptNameExist(){
		//获取文本框的值
		var deptName = $("#deptName").val();
		//发送ajax请求
		//此时还是乱码
		/* $.ajax({
			url:"deptAction_deptNameExist.action?deptName="+deptName,
			type:"post",
			dataType:"text",
			success:function(data)
						{
							alert(data);
						}
		}); */
		
		//解决方案一
		//随便type采用get还是post，我们采用页面二次编码，服务端解码的方式，去解决乱码问题
		//对中文进行二次编码
		/* deptName = encodeURI(encodeURI(deptName));
		
		$.ajax({
			url:"deptAction_deptNameExist.action?deptName="+deptName,
			type:"post",
			dataType:"text",
			success:function(data)
						{
							alert(data);
						}
		}); */
		
		//解决方案二
		//使用post的请求方式，不通过url传递参数，采用data传递参数
		$.ajax({
			url:"deptAction_deptNameExist.action",
			type:"post",
			data:{'deptName':deptName},
			dataType:"text",
			success:function(data){
							if(data=="true"){
								$("#deptNameInfo").html("<font color='red'>部门名字已经存在，无法注册</font>");
							}else{
								$("#deptNameInfo").html("<font color='green'>部门名字不存在，可以注册</font>");
							}
						}
		});
		
		
		
		
		$.ajax({
			// 1 url:"deptAction_deptNameExist.action",// 可以
			url:"${ctx}/sysadmin/deptAction_deptNameExist.action", // 可以
			
			// 2 bu可以, /就代表回到localhost:8080 这个位子，http://localhost:8088/deptAction_deptNameExist.action
			//url:"/deptAction_deptNameExist.action", 
			
			// 3  不可以 http://localhost:8088/jx_web/sysadmin/sysadmin/deptAction_deptNameExist.action
			//url:"sysadmin/deptAction_deptNameExist.action", 
			
			// 4 不可以 localhost:8088/sysadmin/deptAction...
			//url:"/sysadmin/deptAction_deptNameExist.action", 
			type:"post",
			data:{'deptName':deptName},
			dataType:"text",
			success:function(data){
							if(data=="true"){
								$("#deptNameInfo").html("<font color='red'>部门名字已经存在，无法注册</font>");
							}else{
								$("#deptNameInfo").html("<font color='green'>部门名字不存在，可以注册</font>");
							}
						}
		}); 
		
	}	
	
	
</script>


<body>
<form name="icform" method="post">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('deptAction_insert','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   新增部门
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">上级部门：</td>
	            <td class="tableContent">
	            	<s:select name="parent.id" list="#deptList" headerKey="" headerValue="--请选择--"
	            	 listKey="id" listValue="deptName"></s:select> <span id="deptNameInfo"></span>
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">部门名称：</td>
	            <td class="tableContent"><input type="text" id="deptName" name="deptName" value="" onchange="deptNameExist()"/></td>
	        </tr>		
		</table>
	</div>
 </form>
</body>
</html>
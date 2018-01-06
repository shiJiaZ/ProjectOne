<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>

<script type="text/javascript" src="${ctx }/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	
	function deptNameChange(){
		//获取隐藏域的值--这个原始的名字
		var oldName = $("#oldName").val();
		//修改之后的名字
		var deptName = $("#deptName").val();
		
		if(oldName==deptName){
			$("#save").html("<a href='#' onclick=\"formSubmit('deptAction_update','_self');this.blur();\">保存</a>");
			$("#error").html("<font color='green'>部门名字没有修改，无需提交</font>");
			return;
		}
		//进入这一行呢，表明需要修改
		$.ajax({
			url:'deptAction_deptNameExist',
			data:{'deptName':deptName},
			dataType:'text',
			type:'post',
			success:function(data){
				if(data=="true"){
					//数据库中存在，不能添加
					$("#save").html("<a href='#' >保存</a>");
					$("#error").html("<font color='red'>数据库中存在该部门名字，无法添加</font>");
				}else{
					//数据库中不存在，可以修改
					$("#error").html("<font color='green'>数据库中bu存在该部门名字，可以添加</font>");
					$("#save").html("<a href='#' onclick=\"formSubmit('deptAction_update','_self');this.blur();\">保存</a>");
				}
			}
		});
	}
	
	
</script>

<body>
<form name="icform" method="post">
      <input type="hidden" name="id" value="${id}"/>
      
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('deptAction_update','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/currency_yen.png"/>
   修改部门
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">上级部门：</td>
	            <td class="tableContent">
	            	<s:select name="parent.id" list="deptList" 
	            		listKey="id" listValue="deptName"
	            		headerKey="" headerValue="--请选择--"
	            	></s:select>
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">部门名称：</td>
	           <input type="hidden" id="oldName" name="oldName" value="${deptName }"/>
	            <td class="tableContent"><input type="text" id="deptName" name="deptName" value="${deptName }" 
	            onchange="deptNameChange()"/><span id="error"></span>
	            </td>
	        </tr>
	        <tr>
	            <td class="columnTitle">状态：</td>
	            <td class="tableContent">
	            	<!-- 构造map集合 
	            	    struts标签可以自动回显，name="state"，然后当标签显示的时候，会自动去值栈中查找state，然后跟option比较，选中默认值
	            	
	            	-->
	            	<s:select list="#{'0':'禁用','1':'启用'}" name="state"></s:select>
	            	
	            	
	            </td>
	        </tr>		
		</table>
	</div>
 </form>
</body>
</html>
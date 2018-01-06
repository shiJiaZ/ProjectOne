<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<head>
	<title></title>
</head>

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
   查看用户
  </div> 
  

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">所在部门：</td>
	            <td class="tableContent">
	            	${dept.deptName }
	            </td>
	            <td class="columnTitle"> </td>
	            <td class="tableContent">
	            </td>
	        </tr>		
	        <tr>
	            <td class="columnTitle">用户名：</td>
	            <td class="tableContent">${userName }</td>
	            <td class="columnTitle">状态：</td>
	            <td class="tableContent">
	            	${state==0?'停用':'启用' }
	            </td>
	        </tr>	
	         <tr>
	            <td class="columnTitle">姓名：</td>
	            <td class="tableContent">${userInfo.name }</td>
	            <td class="columnTitle">直属领导:</td>
	            <td class="tableContent">${userInfo.manager.userName }</td>
	        </tr>	
	        <tr>
	            <td class="columnTitle">入职时间</td>
	            <td class="tableContent">
	            	<fmt:formatDate value="${userInfo.joinDate }" pattern="yyyy年MM月dd日" />
	            </td>
	            <td class="columnTitle">薪水：</td>
	            <td class="tableContent">${userInfo.salary }</td>
	        </tr>	
	        
		</table>
	</div>
 </form>
</body>
</html>
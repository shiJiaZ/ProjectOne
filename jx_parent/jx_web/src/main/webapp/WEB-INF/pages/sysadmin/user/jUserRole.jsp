<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<style type="text/css">
	   span{display: inline-block;width: 200px}
	</style>
</head>

<body>
<form name="icform" method="post">
	<!-- 用户的id -->
	<input type="hidden" name="id" value="${id}"/>

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('userAction_role','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="formSubmit('userAction_list','_self');this.blur();">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
    用户 [${userInfo.name}] 角色列表
  </div> 
  </div>
  </div>
  
<div>

<!-- <span style="padding:3px;"> -->
<div style="text-align:left">
	<!-- 页面实现思路是什么？
	     1 遍历所有的角色
	     2 嵌套遍历用户的角色，如果当前的角色是用户拥有的角色，就打钩  
	 -->
	<%-- <c:forEach items="${roleList }" var="role">
		<span style="padding:3px;">
	
			<!-- 判断当前的role是否是用户已经拥有的吧  , 默认用户不拥有这个角色-->
			<c:set var="flag" value="false"></c:set>
			
			<c:forEach items="${roles }" var="r">
				<c:if test="${r.name==role.name }">
					<c:set var="flag"  value="true"></c:set>
				</c:if>
			</c:forEach>
			
			<c:if test="${flag==true }">
				<!-- 打钩 -->
				<input type="checkbox" name="roleIds" value="${role.id }" checked>${role.name }
			</c:if>
			<c:if test="${flag==false }">
				<!-- 不打钩 -->
				<input type="checkbox" name="roleIds" value="${role.id }" >${role.name }
			</c:if>
		
		</span>
	
	</c:forEach>
	
	<hr>
	<c:forEach items="${roleList }" var="role">
		<span style="padding:3px;">
	
			<!-- 判断当前的role是否是用户已经拥有的吧  , 默认用户不拥有这个角色-->
			<c:set var="flag" value="false"></c:set>
			
			<c:forEach items="${roles }" var="r">
				<c:if test="${r.name==role.name }">
					<c:set var="flag"  value="true"></c:set>
				</c:if>
			</c:forEach>
			
			<input type="checkbox" name="roleIds" value="${role.id }" ${flag==true?"checked":"" }>${role.name }
		
		</span>
	
	</c:forEach> --%>
	
	<hr>
	<!-- 遍历所有的角色 -->
	<c:forEach items="${roleList }" var="role">
		<span style="padding:3px;">
			<input type="checkbox" value="${role.id }"  name="roleIds"
				${fn:contains(userRoleStr,role.name)==true?"checked":"" }
			 >${role.name }
			
		</span>
	</c:forEach>
	
	
	
	
	
</div>
 
</div>
 
 
</form>
</body>
</html>


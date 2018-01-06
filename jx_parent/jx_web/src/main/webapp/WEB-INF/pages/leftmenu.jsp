<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul>
      <!-- 
      	         遍历用户拥有的当前主菜单的左侧菜单？
      	   实现步骤：
      	 0 获取用户的角色  
      	 1 根据角色获取模块--当前用户的
      	 2 判断是否是左侧模块	
      	 3 判断是否是当前一级菜单的
      	 4 判断是否已经显示
       -->
       <c:set var="aaa" value=""></c:set>
       
	   <c:forEach items="${_CURRENT_USER.roles }" var="role">
	   	   <!-- 遍历角色对应的模块 -->	
	   	   <c:forEach items="${role.modules }" var="m">
	   	   		<!-- 判断是否是左侧 -->
	   	   		<c:if test="${m.ctype==1 }">
	   	   			<!-- 判断是否是当前主菜单的 -->
	   	   			<c:if test="${m.remark==moduleName }">
	   	   				<!-- 判断是否已经显示 -->
	   	   				<c:if test="${fn:contains(aaa,m.name) eq false }">
	   	   					<!-- 当aaa中不包含当前模块名字的时候，需要进来显示 -->
	   	   					<c:set var="aaa" value="${aaa },${m.name }"></c:set>
	   	   					<li><a href="${ctx }/${m.curl}" onclick="linkHighlighted(this)" target="main" id="aa_1">${m.name}</a></li>
	   	   				</c:if>
	   	   				
	   	   			</c:if>
	   	   		</c:if>
	   	   </c:forEach>	 
	   </c:forEach>	 
</ul>


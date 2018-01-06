<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul>
     <!-- 
                         三级菜单的是实现思路：
               1、 获取当前系统用户的所有的角色   
               2、遍历角色，获取角色对应的所有的模块
               3、判断是否是三级菜单
               4、判断是否是当前左侧菜单的
               5、判断是否已经显示
      -->
      <%-- <c:set var="aaa" value=""></c:set>
      <!-- 遍历当前用户的所有的角色 -->
      <c:forEach items="${_CURRENT_USER.roles }" var="role">
		   <!-- 遍历角色对应的所有的模块 -->   
      	   <c:forEach items="${role.modules }" var="m">
				<!-- 判断是否是三级 菜单 -->      	   
      	   		<c:if test="${m.ctype==2 }">
      	   		    <!-- 判断是否是左侧菜单的下级菜单 -->
      	   		    <c:if test="${fn:contains(url,m.remark) eq true }">
      	   		    	<!-- 判断是否已经显示过了 -->
      	   		    	<c:if test="${fn:contains(aaa,m.cpermission) eq false }">
      	   		    		<c:set var="aaa" value="${aaa },${m.cpermission }"></c:set>
      	   		    		<li id="${m.ico }"><a href="${m.curl }">${m.cpermission }</a></li>
      	   		    	
      	   		    	</c:if>
      	   		    </c:if>
      	   		    
      	   		</c:if>
      	   </c:forEach>
      </c:forEach> --%>
      
      
      
      
      <c:set var="aaa" value=""></c:set>
      <!-- 遍历当前用户的所有的角色 -->
      <c:forEach items="${_CURRENT_USER.roles }" var="role">
		   <!-- 遍历角色对应的所有的模块 -->   
      	   <c:forEach items="${role.modules }" var="m">
				<!-- 判断是否是三级 菜单 -->      	   
      	   		<c:if test="${m.ctype==2 }">
      	   		    <!-- 判断是否是左侧菜单的下级菜单 -->
      	   		    <c:if test="${symbol==m.remark }">
      	   		    	<!-- 判断是否已经显示过了 -->
      	   		    	<c:if test="${fn:contains(aaa,m.cpermission) eq false }">
      	   		    		<c:set var="aaa" value="${aaa },${m.cpermission }"></c:set>
      	   		    		<li id="${m.ico }"><a href="${m.curl }">${m.cpermission }</a></li>
      	   		    	
      	   		    	</c:if>
      	   		    </c:if>
      	   		    
      	   		</c:if>
      	   </c:forEach>
      </c:forEach>
      
      
      
      
      
      
      
      
</ul>


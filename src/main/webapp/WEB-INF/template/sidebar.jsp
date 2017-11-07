<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<aside class="main-sidebar">
	<!-- sidebar: style can be found in sidebar.less -->
	<section class="sidebar">
		<!-- Sidebar user panel -->
		<div class="user-panel">
			<div class="pull-left image">
				<img src="<c:url value='/resources/dist/img/user2-160x160.jpg'/>"
					class="img-circle" alt="User Image">
			</div>
			<div class="pull-left info">
				<p>
					<security:authorize access="isAuthenticated()">
						<security:authentication property="principal.name" />
					</security:authorize>
				</p>
				<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
			</div>
		</div>
		<!-- sidebar menu: : style can be found in sidebar.less -->
		<ul class="sidebar-menu">
			<li class="header">MAIN NAVIGATION ${sessionScope.PAGE_INDEX }</li>
			
			<li class="treeview ${sessionScope.PAGE_INDEX == 'advs' ? 'active' : '' }"><a
				href="${pageContext.request.contextPath }/"><i
					class="fa fa-table"></i> <span>View Data</span>
			</a></li>
			<li class="treeview ${sessionScope.PAGE_INDEX == 'adv' ? 'active' : '' }"><a
				href="${pageContext.request.contextPath }/adv"> <i
					class="fa fa-edit"></i> <span>Create New</span>
			</a></li>
			
			<security:authorize access="hasRole('ROLE_ADMIN')">
			<li class="treeview ${sessionScope.PAGE_INDEX == 'users' || sessionScope.PAGE_INDEX == 'user' ? 'active' : '' }"><a href="#"> <i class="fa fa-pie-chart"></i>
					<span>User management</span> <span class="pull-right-container">
						<i class="fa fa-angle-left pull-right"></i>
				</span>
			</a>
				<ul class="treeview-menu menu-open">
					<li class="${sessionScope.PAGE_INDEX == 'users' ? 'active' : '' }"><a href="${pageContext.request.contextPath }/users"><i
							class="fa fa-circle-o"></i> Users</a></li>
					<li class="${sessionScope.PAGE_INDEX == 'user' ? 'active' : '' }"><a href="${pageContext.request.contextPath }/user"><i
							class="fa fa-circle-o"></i> Create new</a></li>
				</ul>
			</li>
			</security:authorize>
		</ul>
	</section>
	<!-- /.sidebar -->
</aside>
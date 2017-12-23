<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
	
<header class="main-header">
	<!-- Logo -->
	<a href="${pageContext.request.contextPath }/adv/view?page=0&size=10" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
		<span class="logo-mini"><b>NL</b></span> <!-- logo for regular state and mobile devices -->
		<span class="logo-lg"><b>NamLong</b>Adv</span>
	</a>
	<!-- Header Navbar: style can be found in header.less -->
	<nav class="navbar navbar-static-top">
		<!-- Sidebar toggle button-->
		<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
			role="button"> <span class="sr-only">Toggle navigation</span> <span
			class="icon-bar"></span> <span class="icon-bar"></span> <span
			class="icon-bar"></span>
		</a>

		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">
				<!-- User Account: style can be found in dropdown.less -->
				<li class="dropdown user user-menu"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"> <img
						src="<c:url value='/resources/dist/img/user2-160x160.jpg'/>"
						class="user-image" alt="User Image"> <span class="hidden-xs">
							<security:authorize access="isAuthenticated()">
								<security:authentication property="principal.name" />
							</security:authorize>
					</span>
				</a>
					<ul class="dropdown-menu">
						<!-- User image -->
						<li class="user-header"><img
							src="<c:url value='/resources/dist/img/user2-160x160.jpg'/>"
							class="img-circle" alt="User Image">

							<p>
								<security:authorize access="isAuthenticated()">
									<security:authentication property="principal.name" />
								</security:authorize>
								<small>Nov. 2017</small>
							</p></li>
						<!-- Menu Footer-->
						<li class="user-footer">
							<div class="pull-left">
								<a href="#" class="btn btn-default btn-flat">Profile</a>
							</div>
							<div class="pull-right">
								<form:form action="${pageContext.request.contextPath}/logout" method="POST">
									<input type="submit" class="btn btn-default btn-flat"
										value="Sign out" />
								</form:form>
							</div>
						</li>
					</ul></li>
				<!-- Control Sidebar Toggle Button -->
				<li><a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a></li>
			</ul>
		</div>
	</nav>
</header>
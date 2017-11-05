<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>NamLongAdv</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet"
	href="<c:url value='/resources/bootstrap/css/bootstrap.min.css'/>">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
<!-- DataTables -->
<link rel="stylesheet"
	href="<c:url value='/resources/plugins/datatables/dataTables.bootstrap.css'/>">
<!-- Theme style -->
<link rel="stylesheet"
	href="<c:url value='/resources/dist/css/AdminLTE.min.css'/>">
<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link rel="stylesheet"
	href="<c:url value='/resources/dist/css/skins/_all-skins.min.css'/>">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<header class="main-header">
			<!-- Logo -->
			<a href="${pageContext.request.contextPath }/view" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
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
								class="user-image" alt="User Image"> <span
								class="hidden-xs">NamLong</span>
						</a>
							<ul class="dropdown-menu">
								<!-- User image -->
								<li class="user-header"><img
									src="<c:url value='/resources/dist/img/user2-160x160.jpg'/>"
									class="img-circle" alt="User Image">

									<p>
										NamLong - Admin <small>Nov. 2017</small>
									</p></li>
								<!-- Menu Footer-->
								<li class="user-footer">
									<div class="pull-left">
										<a href="#" class="btn btn-default btn-flat">Profile</a>
									</div>
									<div class="pull-right">
										<a href="${pageContext.request.contextPath }/login" class="btn btn-default btn-flat">Sign out</a>
									</div>
								</li>
							</ul></li>
						<!-- Control Sidebar Toggle Button -->
						<li><a href="#" data-toggle="control-sidebar"><i
								class="fa fa-gears"></i></a></li>
					</ul>
				</div>
			</nav>
		</header>
		<!-- Left side column. contains the logo and sidebar -->
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
						<p>Toan Ngo</p>
						<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
					</div>
				</div>
				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="sidebar-menu">
					<li class="header">MAIN NAVIGATION</li>
					<li class="treeview"><a
						href="${pageContext.request.contextPath }/view"> <i
							class="fa fa-table"></i> <span>View Data</span>
					</a></li>
					<li class="treeview active"><a
						href="${pageContext.request.contextPath }/create"> <i
							class="fa fa-edit"></i> <span>Create New</span>
					</a></li>
					<li class="treeview"><a href="#"> <i
							class="fa fa-pie-chart"></i> <span>User management</span> <span
							class="pull-right-container"> <i
								class="fa fa-angle-left pull-right"></i>
						</span>
					</a>
						<ul class="treeview-menu menu-open">
							<li><a href="${pageContext.request.contextPath }/users"><i
									class="fa fa-circle-o"></i> Users</a></li>
							<li><a href="${pageContext.request.contextPath }/user"><i
									class="fa fa-circle-o"></i> Create new</a></li>
						</ul>
					</li>
				</ul>
			</section>
			<!-- /.sidebar -->
		</aside>

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>

					<small></small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Management</a></li>
					<li><a href="#">Create new</a></li>
				</ol>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-xs-9">
						<div class="box box-info">
							<div class="box-header with-border">
								<h3 class="box-title">Tạo Mới</h3>
							</div>
							<!-- /.box-header -->
							<!-- form start -->
							<form class="form-horizontal">
								<div class="box-body">
									<div class="form-group">
										<label for="inputEmail3" class="col-sm-2 control-label">Tên</label>
										<div class="col-sm-10">
											<input type="email" class="form-control" id="inputEmail3"
												placeholder="Nhập tên">
										</div>
									</div>
									<div class="form-group">
										<label for="inputEmail3" class="col-sm-2 control-label">Số
											nhà</label>
										<div class="col-sm-10">
											<input type="email" class="form-control" id="inputEmail3"
												placeholder="Nhập số nhà">
										</div>
									</div>
									<div class="form-group">
										<label for="inputEmail3" class="col-sm-2 control-label">Phường</label>
										<div class="col-sm-10">
											<select class="form-control">
												<option>-- Chọn phường --</option>
												<option>Bình Chánh</option>
												<option>Linh Trung</option>
												<option>Linh Tây</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label for="inputEmail3" class="col-sm-2 control-label">Quận</label>
										<div class="col-sm-10">
											<select class="form-control">
												<option>-- Chọn quận --</option>
												<option>Quận 1</option>
												<option>Tân Bình</option>
												<option>Quận 2</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label for="inputEmail3" class="col-sm-2 control-label">Tỉnh</label>
										<div class="col-sm-10">
											<select class="form-control">
												<option>-- Chọn tỉnh --</option>
												<option>Bình Định</option>
												<option>Đà Nẵng</option>
												<option>HCM</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label for="map" class="col-sm-2 control-label">Google map</label>
										<div class="col-sm-10">
											<input type="text" class="form-control" id="map"
												placeholder="Link goole map">
										</div>
									</div>

									<div class="row">
										<div class="col-sm-6">
											<div class="box box-info">
												<div class="box-header with-border">
													<h3 class="box-title">Thông Tin Chủ Nhà</h3>
												</div>
												<!-- /.box-header -->
												<!-- form start -->
												<form class="form-horizontal">
													<div class="box-body">
														<div class="form-group">
															<label for="phone" class="col-sm-2 control-label">ĐT</label>
															<div class="col-sm-10">
																<input type="text" class="form-control" id="phone"
																	placeholder="Số điện thoại">
															</div>
														</div>
														<div class="form-group">
															<label for="email" class="col-sm-2 control-label">Email</label>
															<div class="col-sm-10">
																<input type="text" class="form-control" id="email"
																	placeholder="Email">
															</div>
														</div>
														<div class="form-group">
															<label for="price" class="col-sm-2 control-label">Giá</label>
															<div class="col-sm-10">
																<input type="text" class="form-control" id="price"
																	placeholder="Giá thuê">
															</div>
														</div>
														<div class="form-group">
															<label for="custName" class="col-sm-2 control-label">NLH</label>
															<div class="col-sm-10">
																<input type="text" class="form-control" id="custName"
																	placeholder="Người liên hệ">
															</div>
														</div>
													</div>
													<!-- /.box-footer -->
												</form>
											</div>
										</div>

										<div class="col-sm-6">
											<div class="box box-info">
												<div class="box-header with-border">
													<h3 class="box-title">Thông Tin CT Quảng Cáo</h3>
												</div>
												<!-- /.box-header -->
												<!-- form start -->
												<form class="form-horizontal">
													<div class="box-body">
														<div class="form-group">
															<label for="phone" class="col-sm-2 control-label">ĐT</label>
															<div class="col-sm-10">
																<input type="text" class="form-control" id="phone"
																	placeholder="Số điện thoại">
															</div>
														</div>
														<div class="form-group">
															<label for="email" class="col-sm-2 control-label">Email</label>
															<div class="col-sm-10">
																<input type="text" class="form-control" id="email"
																	placeholder="Email">
															</div>
														</div>
														<div class="form-group">
															<label for="price" class="col-sm-2 control-label">Giá</label>
															<div class="col-sm-10">
																<input type="text" class="form-control" id="price"
																	placeholder="Giá thuê">
															</div>
														</div>
														<div class="form-group">
															<label for="custName" class="col-sm-2 control-label">NLH</label>
															<div class="col-sm-10">
																<input type="text" class="form-control" id="custName"
																	placeholder="Người liên hệ">
															</div>
														</div>
													</div>
													<!-- /.box-footer -->
												</form>
											</div>
										</div>
									</div>
									
									
									<iframe src="https://www.google.com/maps/embed?pb=!1m14!1m12!1m3!1d15673.3169232859!2d106.76861631158515!3d10.862544375601214!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!5e0!3m2!1svi!2s!4v1509812552105" width="100%" height="450" frameborder="0" style="border:0" allowfullscreen></iframe>
								</div>
								<!-- /.box-body -->
								<div class="box-footer">
									<button type="submit" class="btn btn-default">Huỷ</button>
									<button type="submit" class="btn btn-info pull-right">Lưu</button>
								</div>
								<!-- /.box-footer -->
							</form>
						</div>
					</div>
					
					<div class="col-md-3">
						<img src="<c:url value='/resources/dist/img/photo1.png'/>" class="img-thumbnail" alt="Cinque Terre"/>
						<input class="form-control" type="file"/>
						<img src="<c:url value='/resources/dist/img/photo2.png'/>" class="img-thumbnail" alt="Cinque Terre"/>
						<input class="form-control" type="file"/>
						<img src="<c:url value='/resources/dist/img/photo1.png'/>" class="img-thumbnail" alt="Cinque Terre"/>
						<input class="form-control" type="file"/>
						<img src="<c:url value='/resources/dist/img/photo2.png'/>" class="img-thumbnail" alt="Cinque Terre"/>
						<input class="form-control" type="file"/>
						<img src="<c:url value='/resources/dist/img/photo1.png'/>" class="img-thumbnail" alt="Cinque Terre"/>
						<input class="form-control" type="file"/>
						<img src="<c:url value='/resources/dist/img/photo2.png'/>" class="img-thumbnail" alt="Cinque Terre"/>
						<input class="form-control" type="file"/>
					</div>
				</div>
				<!-- /.row -->
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->
		<footer class="main-footer">
			<div class="pull-right hidden-xs">
				<b>Version</b> 0.0.1
			</div>
			<strong>Copyright &copy; 2017 <a
				href="http://almsaeedstudio.com">BTIT95</a>.
			</strong> All rights reserved.
		</footer>

		<!-- Control Sidebar -->
		<aside class="control-sidebar control-sidebar-dark">
			<!-- Create the tabs -->
			<ul class="nav nav-tabs nav-justified control-sidebar-tabs">
				<li><a href="#control-sidebar-home-tab" data-toggle="tab"><i
						class="fa fa-home"></i></a></li>
				<li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i
						class="fa fa-gears"></i></a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<!-- Home tab content -->
				<div class="tab-pane" id="control-sidebar-home-tab">
					<h3 class="control-sidebar-heading">Recent Activity</h3>
					<ul class="control-sidebar-menu">
						<li><a href="javascript:void(0)"> <i
								class="menu-icon fa fa-birthday-cake bg-red"></i>

								<div class="menu-info">
									<h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

									<p>Will be 23 on April 24th</p>
								</div>
						</a></li>
						<li><a href="javascript:void(0)"> <i
								class="menu-icon fa fa-user bg-yellow"></i>

								<div class="menu-info">
									<h4 class="control-sidebar-subheading">Frodo Updated His
										Profile</h4>

									<p>New phone +1(800)555-1234</p>
								</div>
						</a></li>
						<li><a href="javascript:void(0)"> <i
								class="menu-icon fa fa-envelope-o bg-light-blue"></i>

								<div class="menu-info">
									<h4 class="control-sidebar-subheading">Nora Joined Mailing
										List</h4>

									<p>nora@example.com</p>
								</div>
						</a></li>
						<li><a href="javascript:void(0)"> <i
								class="menu-icon fa fa-file-code-o bg-green"></i>

								<div class="menu-info">
									<h4 class="control-sidebar-subheading">Cron Job 254
										Executed</h4>

									<p>Execution time 5 seconds</p>
								</div>
						</a></li>
					</ul>
					<!-- /.control-sidebar-menu -->

					<h3 class="control-sidebar-heading">Tasks Progress</h3>
					<ul class="control-sidebar-menu">
						<li><a href="javascript:void(0)">
								<h4 class="control-sidebar-subheading">
									Custom Template Design <span
										class="label label-danger pull-right">70%</span>
								</h4>

								<div class="progress progress-xxs">
									<div class="progress-bar progress-bar-danger"
										style="width: 70%"></div>
								</div>
						</a></li>
						<li><a href="javascript:void(0)">
								<h4 class="control-sidebar-subheading">
									Update Resume <span class="label label-success pull-right">95%</span>
								</h4>

								<div class="progress progress-xxs">
									<div class="progress-bar progress-bar-success"
										style="width: 95%"></div>
								</div>
						</a></li>
						<li><a href="javascript:void(0)">
								<h4 class="control-sidebar-subheading">
									Laravel Integration <span
										class="label label-warning pull-right">50%</span>
								</h4>

								<div class="progress progress-xxs">
									<div class="progress-bar progress-bar-warning"
										style="width: 50%"></div>
								</div>
						</a></li>
						<li><a href="javascript:void(0)">
								<h4 class="control-sidebar-subheading">
									Back End Framework <span class="label label-primary pull-right">68%</span>
								</h4>

								<div class="progress progress-xxs">
									<div class="progress-bar progress-bar-primary"
										style="width: 68%"></div>
								</div>
						</a></li>
					</ul>
					<!-- /.control-sidebar-menu -->

				</div>
				<!-- /.tab-pane -->
				<!-- Stats tab content -->
				<div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab
					Content</div>
				<!-- /.tab-pane -->
				<!-- Settings tab content -->
				<div class="tab-pane" id="control-sidebar-settings-tab">
					<form method="post">
						<h3 class="control-sidebar-heading">General Settings</h3>

						<div class="form-group">
							<label class="control-sidebar-subheading"> Report panel
								usage <input type="checkbox" class="pull-right" checked>
							</label>

							<p>Some information about this general settings option</p>
						</div>
						<!-- /.form-group -->

						<div class="form-group">
							<label class="control-sidebar-subheading"> Allow mail
								redirect <input type="checkbox" class="pull-right" checked>
							</label>

							<p>Other sets of options are available</p>
						</div>
						<!-- /.form-group -->

						<div class="form-group">
							<label class="control-sidebar-subheading"> Expose author
								name in posts <input type="checkbox" class="pull-right" checked>
							</label>

							<p>Allow the user to show his name in blog posts</p>
						</div>
						<!-- /.form-group -->

						<h3 class="control-sidebar-heading">Chat Settings</h3>

						<div class="form-group">
							<label class="control-sidebar-subheading"> Show me as
								online <input type="checkbox" class="pull-right" checked>
							</label>
						</div>
						<!-- /.form-group -->

						<div class="form-group">
							<label class="control-sidebar-subheading"> Turn off
								notifications <input type="checkbox" class="pull-right">
							</label>
						</div>
						<!-- /.form-group -->

						<div class="form-group">
							<label class="control-sidebar-subheading"> Delete chat
								history <a href="javascript:void(0)" class="text-red pull-right"><i
									class="fa fa-trash-o"></i></a>
							</label>
						</div>
						<!-- /.form-group -->
					</form>
				</div>
				<!-- /.tab-pane -->
			</div>
		</aside>
		<!-- /.control-sidebar -->
		<!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->

	<!-- jQuery 2.2.3 -->
	<script
		src="<c:url value='/resources/plugins/jQuery/jquery-2.2.3.min.js'/>"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="<c:url value='/resources/bootstrap/js/bootstrap.min.js'/>"></script>
	<!-- DataTables -->
	<script
		src="<c:url value='/resources/plugins/datatables/jquery.dataTables.min.js'/>"></script>
	<script
		src="<c:url value='/resources/plugins/datatables/dataTables.bootstrap.min.js'/>"></script>
	<!-- SlimScroll -->
	<script
		src="<c:url value='/resources/plugins/slimScroll/jquery.slimscroll.min.js'/>"></script>
	<!-- FastClick -->
	<script
		src="<c:url value='/resources/plugins/fastclick/fastclick.js'/>"></script>
	<!-- AdminLTE App -->
	<script src="<c:url value='/resources/dist/js/app.min.js'/>"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="<c:url value='/resources/dist/js/demo.js'/>"></script>
	<!-- page script -->
	<script>
		$(function() {
			$('#example2').DataTable({
				"paging" : true,
				"lengthChange" : false,
				"searching" : false,
				"ordering" : true,
				"info" : true,
				"autoWidth" : false
			});
		});
	</script>
</body>
</html>

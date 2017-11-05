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

<style type="text/css">
table tr td {
	vertical-align: middle !important;
}

.table-bordered>thead>tr>th, .table-bordered>tbody>tr>th,
	.table-bordered>tfoot>tr>th, .table-bordered>thead>tr>td,
	.table-bordered>tbody>tr>td, .table-bordered>tfoot>tr>td {
	border: 1px solid #d2d6de;
}

.striped {
	background-color: #f4f4f4;
}
</style>
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
					<li class="treeview active"><a
						href="${pageContext.request.contextPath }/view"> <i
							class="fa fa-table"></i> <span>View Data</span>
					</a></li>
					<li class="treeview"><a
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
					Data <small>list of elements</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Management</a></li>
					<li><a href="#">View Data</a></li>
				</ol>
			</section>

			<!-- Main content -->
			<section class="content">
				<div class="row">
					<div class="col-xs-12">
						<div class="box box-danger">
							<div class="box-header with-border">
								<h3 class="box-title">Bộ Lọc</h3>
							</div>
							<div class="box-body">
								<div class="row">
									<div class="col-xs-2">
										<input type="text" class="form-control" placeholder="Mã">
									</div>
									<div class="col-xs-3">
										<input type="text" class="form-control" placeholder="Tên">
									</div>
									<div class="col-xs-3">
										<input type="text" class="form-control" placeholder="Địa chỉ">
									</div>
									<div class="col-xs-2">
										<select class="form-control">
											<option>-- Quận/Huyện --</option>
											<option>Quận 1</option>
											<option>Quận 2</option>
											<option>Quận 3</option>
											<option>Quận 4</option>
										</select>
									</div>
									<div class="col-xs-2">
										<select class="form-control">
											<option>-- Tỉnh/Thành phố --</option>
											<option>Hồ Chí Minh</option>
											<option>Đà Nẵng</option>
											<option>Bình Định</option>
											<option>Biên Hoà</option>
										</select>
									</div>
								</div>
							</div>
							<!-- /.box-body -->
							<div class="footer">
								<div class="box-footer">
									<button type="submit" class="btn btn-info">Lọc</button>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<div class="box">
							<div class="box-header">
								<h3 class="box-title">Data</h3>
							</div>
							<!-- /.box-header -->
							<div class="box-body" style="overflow: auto;">
								<table class="table table-bordered"
									style="width: 150% !important; max-width: none !important; margin-right: 10px;">
									<tbody>
										<tr>
											<th>EX</th>
											<th>STT</th>
											<th>Tên</th>
											<th>Số Nhà</th>
											<th>Xã</th>
											<th>Quận</th>
											<th>Tỉnh</th>
											<th>Kích thước</th>
											<th>Đơn giá</th>
											<th></th>
											<th>SĐT</th>
											<th>Email</th>
											<th>Giá thuê</th>
											<th>Người liên hệ</th>
											<th>Hình ảnh</th>
											<th>Action</th>
										</tr>
										<tr>
											<td rowspan="2"><input checked="checked" type="checkbox"/></td>
											<td rowspan="2">1</td>
											<td rowspan="2">Nguyen Van A</td>
											<td rowspan="2">23/3</td>
											<td rowspan="2">Phú Quang</td>
											<td rowspan="2">Tân Bình</td>
											<td rowspan="2">Bình Định</td>
											<td rowspan="2">23000mm</td>
											<td rowspan="2">234.643.000</td>
											<td>TTCN</td>
											<td>01649001142</td>
											<td>baotoan.95@gmail.com</td>
											<td>342.342.000</td>
											<td>Nguyen Thi B</td>
											<td></td>
											<td rowspan="2" class="action">
												<a href="#"><i class="fa fa-fw fa-trash"></i>Xoá</a>
												<br />
												<a href="${pageContext.request.contextPath }/create"><i class="fa fa-fw fa-edit"></i>Xem</a>
											</td>
										</tr>
										<tr>
											<td>TTCTQC</td>
											<td>01649001142</td>
											<td>baotoan.95@gmail.com</td>
											<td>342.342.000</td>
											<td>Nguyen Thi B</td>
											<td></td>
										</tr>

										<tr class="striped">
											<td rowspan="2"><input checked="checked" type="checkbox"/></td>
											<td rowspan="2">2</td>
											<td rowspan="2">Nguyen Van A</td>
											<td rowspan="2">23/3</td>
											<td rowspan="2">Phú Quang</td>
											<td rowspan="2">Tân Bình</td>
											<td rowspan="2">Bình Định</td>
											<td rowspan="2">23000mm</td>
											<td rowspan="2">234.643.000</td>
											<td>TTCN</td>
											<td>01649001142</td>
											<td>baotoan.95@gmail.com</td>
											<td>342.342.000</td>
											<td>Nguyen Thi B</td>
											<td></td>
											<td rowspan="2" class="action">
												<a href="#"><i class="fa fa-fw fa-trash"></i>Xoá</a>
												<br />
												<a href="${pageContext.request.contextPath }/create"><i class="fa fa-fw fa-edit"></i>Xem</a>
											</td>
										</tr>
										<tr class="striped">
											<td>TTCTQC</td>
											<td>01649001142</td>
											<td>baotoan.95@gmail.com</td>
											<td>342.342.000</td>
											<td>Nguyen Thi B</td>
											<td></td>
										</tr>

										<tr>
											<td rowspan="2"><input checked="checked" type="checkbox"/></td>
											<td rowspan="2">3</td>
											<td rowspan="2">Nguyen Van A</td>
											<td rowspan="2">23/3</td>
											<td rowspan="2">Phú Quang</td>
											<td rowspan="2">Tân Bình</td>
											<td rowspan="2">Bình Định</td>
											<td rowspan="2">23000mm</td>
											<td rowspan="2">234.643.000</td>
											<td>TTCN</td>
											<td>01649001142</td>
											<td>baotoan.95@gmail.com</td>
											<td>342.342.000</td>
											<td>Nguyen Thi B</td>
											<td></td>
											<td rowspan="2" class="action">
												<a href="#"><i class="fa fa-fw fa-trash"></i>Xoá</a>
												<br />
												<a href="${pageContext.request.contextPath }/create"><i class="fa fa-fw fa-edit"></i>Xem</a>
											</td>
										</tr>
										<tr>
											<td>TTCTQC</td>
											<td>01649001142</td>
											<td>baotoan.95@gmail.com</td>
											<td>342.342.000</td>
											<td>Nguyen Thi B</td>
											<td></td>
										</tr>

										<tr class="striped">
											<td rowspan="2"><input checked="checked" type="checkbox"/></td>
											<td rowspan="2">3</td>
											<td rowspan="2">Nguyen Van A</td>
											<td rowspan="2">23/3</td>
											<td rowspan="2">Phú Quang</td>
											<td rowspan="2">Tân Bình</td>
											<td rowspan="2">Bình Định</td>
											<td rowspan="2">23000mm</td>
											<td rowspan="2">234.643.000</td>
											<td>TTCN</td>
											<td>01649001142</td>
											<td>baotoan.95@gmail.com</td>
											<td>342.342.000</td>
											<td>Nguyen Thi B</td>
											<td></td>
											<td rowspan="2" class="action">
												<a href="#"><i class="fa fa-fw fa-trash"></i>Xoá</a>
												<br />
												<a href="${pageContext.request.contextPath }/create"><i class="fa fa-fw fa-edit"></i>Xem</a>
											</td>
										</tr>
										<tr class="striped">
											<td>TTCTQC</td>
											<td>01649001142</td>
											<td>baotoan.95@gmail.com</td>
											<td>342.342.000</td>
											<td>Nguyen Thi B</td>
											<td></td>
										</tr>
									</tbody>
								</table>

							</div>
							<!-- /.box-body -->

							<div class="box-footer clearfix">
								<button type="submit" class="btn btn-info">Xuất Excel</button>
								<button type="submit" class="btn btn-info">Xuất Powerpoint</button>
							
								<ul class="pagination pagination-sm no-margin pull-right">
									<li><a href="#">«</a></li>
									<li><a href="#">1</a></li>
									<li><a href="#">2</a></li>
									<li><a href="#">3</a></li>
									<li><a href="#">»</a></li>
								</ul>
							</div>
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col -->
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

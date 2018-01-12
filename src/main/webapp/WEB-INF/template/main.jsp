<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>NamLongAdv</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link rel="shortcut icon" type="image/x-icon"
	href="<c:url value='/resources/dist/img/namlong_logo.ico'/>" />

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
	
<link rel="stylesheet"
	href="<c:url value='/resources/dist/css/custom.css'/>">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
<link rel="stylesheet"
	href="<c:url value='/resources/jquery-ui-1.12.1/jquery-ui.css'/>">

<!-- jQuery 2.2.3 -->
<script
	src="<c:url value='/resources/plugins/jQuery/jquery-2.2.3.min.js'/>"></script>

</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<!-- Header -->
		<tiles:insertAttribute name="header"></tiles:insertAttribute>
		<!-- Left side column. contains the logo and sidebar -->
		<tiles:insertAttribute name="sidebar"></tiles:insertAttribute>

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
			<tiles:insertAttribute name="content"></tiles:insertAttribute>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

		<!-- Footer -->
		<tiles:insertAttribute name="footer"></tiles:insertAttribute>

		<!-- Control Sidebar -->
		<tiles:insertAttribute name="skin-menu"></tiles:insertAttribute>
	</div>
	<!-- ./wrapper -->

	<div class="modal modal-success" id="modal-success" style="display: none;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">Preview</h4>
				</div>
				<div class="modal-body">
					<img id="image-preview"/>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>

	<script
		src="<c:url value='/resources/jquery-ui-1.12.1/jquery-ui.min.js'/>"></script>
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
	<!-- Lazy loading -->
	<script src="<c:url value='/resources/dist/js/jquery.lazyload.js'/>"></script>
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
		$(document).ready(function() {
			$("img.lazy").lazyload({
				effect : "fadeIn"
			});
			
			$(".btn-preview-image").click(function() {
				$('#image-preview').attr('src', $(this).prev().attr('src'));
			});
		});
	</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>	

<section class="content">
	<div class="row">
		<div class="col-xs-9">
			<div class="box box-info">
				<div class="box-header with-border">
					<h3 class="box-title">Tạo Mới Thành Viên</h3>
				</div>
				<!-- /.box-header -->
				<!-- form start -->
				<form class="form-horizontal">
					<div class="box-body">
						<div class="form-group">
							<label for="username" class="col-sm-2 control-label">Username</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="username"
									placeholder="Username">
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-sm-2 control-label">Password</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" id="password"
									placeholder="Password">
							</div>
						</div>
						<div class="form-group">
							<label for="name" class="col-sm-2 control-label">Tên</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="name"
									placeholder="Tên">
							</div>
						</div>
						<div class="form-group">
							<label for="room" class="col-sm-2 control-label">Room</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="room"
									placeholder="Phòng">
							</div>
						</div>
						<div class="form-group">
							<label for="phone" class="col-sm-2 control-label">SĐT</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="room"
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
	</div>
	<!-- /.row -->
</section>
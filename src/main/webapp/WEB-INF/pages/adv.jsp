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
							<label for="inputEmail3" class="col-sm-2 control-label">Tên đường</label>
							<div class="col-sm-10">
								<input type="email" class="form-control" id="inputEmail3"
									placeholder="Nhập tên đường">
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
							<label for="map" class="col-sm-2 control-label">Google
								map</label>
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


						<iframe
							src="https://www.google.com/maps/embed?pb=!1m14!1m12!1m3!1d15673.3169232859!2d106.76861631158515!3d10.862544375601214!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!5e0!3m2!1svi!2s!4v1509812552105"
							width="100%" height="450" frameborder="0" style="border: 0"
							allowfullscreen></iframe>
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
			<img src="<c:url value='/resources/dist/img/photo1.png'/>"
				class="img-thumbnail" alt="Cinque Terre" /> <input
				class="form-control" type="file" /> <img
				src="<c:url value='/resources/dist/img/photo2.png'/>"
				class="img-thumbnail" alt="Cinque Terre" /> <input
				class="form-control" type="file" /> <img
				src="<c:url value='/resources/dist/img/photo1.png'/>"
				class="img-thumbnail" alt="Cinque Terre" /> <input
				class="form-control" type="file" /> <img
				src="<c:url value='/resources/dist/img/photo2.png'/>"
				class="img-thumbnail" alt="Cinque Terre" /> <input
				class="form-control" type="file" /> <img
				src="<c:url value='/resources/dist/img/photo1.png'/>"
				class="img-thumbnail" alt="Cinque Terre" /> <input
				class="form-control" type="file" /> <img
				src="<c:url value='/resources/dist/img/photo2.png'/>"
				class="img-thumbnail" alt="Cinque Terre" /> <input
				class="form-control" type="file" />
		</div>
	</div>
	<!-- /.row -->
</section>
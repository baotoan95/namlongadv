<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
	
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
								<th>Vị Trí</th>
								<th>Tên Đường</th>
								<th>Số Nhà</th>
								<th>Xã</th>
								<th>Quận</th>
								<th>Tỉnh</th>
								<th>Kích thước</th>
								<th>Đơn giá</th>
								<th></th>
								<th>Tên</th>
								<th>SĐT</th>
								<th>Người liên hệ</th>
								<th>Email</th>
								<th>Giá thuê</th>
								<th>Hình ảnh</th>
								<th>Action</th>
							</tr>
							<tr>
								<td rowspan="2"><input checked="checked" type="checkbox" /></td>
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
								<td></td>
								<td rowspan="2" class="action"><a href="#"><i
										class="fa fa-fw fa-trash"></i>Xoá</a> <br /> <a
									href="${pageContext.request.contextPath }/create"><i
										class="fa fa-fw fa-edit"></i>Xem</a></td>
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
								<td rowspan="2"><input checked="checked" type="checkbox" /></td>
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
								<td></td>
								<td rowspan="2" class="action"><a href="#"><i
										class="fa fa-fw fa-trash"></i>Xoá</a> <br /> <a
									href="${pageContext.request.contextPath }/create"><i
										class="fa fa-fw fa-edit"></i>Xem</a></td>
							</tr>
							<tr class="striped">
								<td>TTCTQC</td>
								<td>01649001142</td>
								<td>baotoan.95@gmail.com</td>
								<td>342.342.000</td>
								<td>Nguyen Thi B</td>
								<td></td>
								<td></td>
							</tr>

							<tr>
								<td rowspan="2"><input checked="checked" type="checkbox" /></td>
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
								<td></td>
								<td rowspan="2" class="action"><a href="#"><i
										class="fa fa-fw fa-trash"></i>Xoá</a> <br /> <a
									href="${pageContext.request.contextPath }/create"><i
										class="fa fa-fw fa-edit"></i>Xem</a></td>
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
								<td rowspan="2"><input checked="checked" type="checkbox" /></td>
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
								<td></td>
								<td rowspan="2" class="action"><a href="#"><i
										class="fa fa-fw fa-trash"></i>Xoá</a> <br /> <a
									href="${pageContext.request.contextPath }/create"><i
										class="fa fa-fw fa-edit"></i>Xem</a></td>
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
					<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')">
						<button type="submit" class="btn btn-info">Xuất Excel</button>
					</security:authorize>
					<security:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_BUSINESS')">
						<button type="submit" class="btn btn-info">Xuất Powerpoint</button>
					</security:authorize>
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
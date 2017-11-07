<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
	
<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<div class="box-header">
					<h3 class="box-title">Quản lý thành viên</h3>
				</div>
				<!-- /.box-header -->
				<div class="box-body" style="overflow: auto;">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<th>Username</th>
								<th>Tên</th>
								<th>Phòng</th>
								<th>SĐT</th>
								<th>Email</th>
								<th>Action</th>
							</tr>
							<tr>
								<td>abc1234</td>
								<td>Nguyen Van A</td>
								<td>23/3</td>
								<td>01649001142</td>
								<td>vana@gmail.com</td>
								<td class="action"><a href="#"><i
										class="fa fa-fw fa-trash"></i>Xoá</a> <br /> <a
									href="${pageContext.request.contextPath }/user"><i
										class="fa fa-fw fa-edit"></i>Xem</a></td>
							</tr>
							<tr>
								<td>abc1234</td>
								<td>Nguyen Van A</td>
								<td>23/3</td>
								<td>01649001142</td>
								<td>vana@gmail.com</td>
								<td class="action"><a href="#"><i
										class="fa fa-fw fa-trash"></i>Xoá</a> <br /> <a
									href="${pageContext.request.contextPath }/user"><i
										class="fa fa-fw fa-edit"></i>Xem</a></td>
							</tr>
							<tr>
								<td>abc1234</td>
								<td>Nguyen Van A</td>
								<td>23/3</td>
								<td>01649001142</td>
								<td>vana@gmail.com</td>
								<td class="action"><a href="#"><i
										class="fa fa-fw fa-trash"></i>Xoá</a> <br /> <a
									href="${pageContext.request.contextPath }/user"><i
										class="fa fa-fw fa-edit"></i>Xem</a></td>
							</tr>
							<tr>
								<td>abc1234</td>
								<td>Nguyen Van A</td>
								<td>23/3</td>
								<td>01649001142</td>
								<td>vana@gmail.com</td>
								<td class="action"><a href="#"><i
										class="fa fa-fw fa-trash"></i>Xoá</a> <br /> <a
									href="${pageContext.request.contextPath }/user"><i
										class="fa fa-fw fa-edit"></i>Xem</a></td>
							</tr>
							<tr>
								<td>abc1234</td>
								<td>Nguyen Van A</td>
								<td>23/3</td>
								<td>01649001142</td>
								<td>vana@gmail.com</td>
								<td class="action"><a href="#"><i
										class="fa fa-fw fa-trash"></i>Xoá</a> <br /> <a
									href="${pageContext.request.contextPath }/user"><i
										class="fa fa-fw fa-edit"></i>Xem</a></td>
							</tr>
						</tbody>
					</table>

				</div>
				<!-- /.box-body -->

				<div class="box-footer clearfix">
					<a href="${pageContext.request.contextPath }/user"
						class="btn btn-info">Thêm mới</a>

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
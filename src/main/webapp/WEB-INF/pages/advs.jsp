<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<style>
table tr th {
	text-align: center;
}
</style>

<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-danger">
				<div class="box-header with-border">
					<h3 class="box-title">Bộ Lọc</h3>
				</div>
				<div class="box-body">
					<div class="row">
						<div class="col-xs-3">
							<input type="text" class="form-control" placeholder="Mã">
						</div>
						<div class="col-xs-3">
							<input type="text" class="form-control" placeholder="Địa chỉ">
						</div>
						<div class="col-xs-2">
							<select class="form-control">
								<c:forEach items="${provinces }" var="province">
									<option>${province.name }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-xs-2">
							<select class="form-control">
								<c:forEach items="${districts }" var="district">
									<option>${district.name }</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-xs-2">
							<select class="form-control">
								<c:forEach items="${wards }" var="ward">
									<option>${ward.name }</option>
								</c:forEach>
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
						<thead>
							<tr>
								<th>EX</th>
								<th>STT</th>
								<th width="50px">Vị Trí</th>
								<th>Tên Đường</th>
								<th>Số Nhà</th>
								<th>Xã</th>
								<th>Quận</th>
								<th>Tỉnh</th>
								<th>Kích thước</th>
								<th>Đơn giá</th>
								<th></th>
								<th>Người liên hệ</th>
								<th>SĐT</th>
								<th>Email</th>
								<th>Giá thuê</th>
								<th>Hình ảnh</th>
								<th>Ghi chú</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.content }" var="adv" varStatus="loop">
							<tr>
								<td rowspan="2"><input checked="checked" type="checkbox" /></td>
								<td rowspan="2">${loop.index + 1 }</td>
								<td rowspan="2">${adv.location }</td>
								<td rowspan="2">${adv.street }</td>
								<td rowspan="2">${adv.houseNo }</td>
								<td rowspan="2">${adv.ward }</td>
								<td rowspan="2">${adv.district }</td>
								<td rowspan="2">${adv.province }</td>
								<td rowspan="2">${adv.size }</td>
								<td rowspan="2">${adv.price }</td>
								<td>TTCN</td>
								<td>${adv.ownerContactPerson }</td>
								<td>${adv.ownerPhone }</td>
								<td>${adv.ownerEmail }</td>
								<td>${adv.ownerPrice }</td>
								<td rowspan="2"></td>
								<td rowspan="2">${adv.note }</td>
								<td rowspan="2" class="action">
									<a href="${pageContext.request.contextPath }/adv/delete/${adv.id }"><i class="fa fa-fw fa-trash"></i>Xoá</a> <br /> 
									<a href="${pageContext.request.contextPath }/adv/${adv.id }"><i class="fa fa-fw fa-edit"></i>Xem</a>
								</td>
							</tr>
							<tr>
								<td>TTCTQC</td>
								<td>${adv.advCompContactPerson }</td>
								<td>${adv.advCompPhone }</td>
								<td>${adv.advCompEmail }</td>
								<td>${adv.advCompPrice }</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>
				<!-- /.box-body -->

				<div class="box-footer clearfix">
					<security:authorize
						access="hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')">
						<button type="submit" class="btn btn-info">Xuất Excel</button>
					</security:authorize>
					<security:authorize
						access="hasAnyRole('ROLE_ADMIN', 'ROLE_BUSINESS')">
						<button type="submit" class="btn btn-info">Xuất
							Powerpoint</button>
					</security:authorize>
					
					<ul class="pagination pagination-sm no-margin pull-right">
						<c:if test="${page.number > 0 }">
						<li class=""><a href="${pageContext.request.contextPath }/user/view?page=${page.number - 1 }&size=${page.size }">«</a></li>
						</c:if>
						
						<c:forEach begin="0" end="${page.totalPages - 1 }" varStatus="loop">
							<li class="${loop.index == page.number ? 'active' : ''}"><a href="${pageContext.request.contextPath }/user/view?page=${loop.index }&size=${page.size }">${loop.index }</a></li>
						</c:forEach>
						
						<c:if test="${page.number < page.totalPages - 1 }">
						<li class=""><a href="${pageContext.request.contextPath }/user/view?page=${page.number + 1 }&size=${page.size }">»</a></li>
						</c:if>
					</ul>
				</div>
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</section>
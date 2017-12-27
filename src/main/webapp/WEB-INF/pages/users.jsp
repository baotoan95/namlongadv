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
								<th>Quyền</th>
								<th></th>
							</tr>
							<c:forEach items="${page.content }" var="user">
							<tr>
								<td>${user.username }</td>
								<td>
									<a href="${pageContext.request.contextPath }/user/${user.id }">
										${user.name }
									</a>
								</td>
								<td>${user.department }</td>
								<td>${user.phone }</td>
								<td>${user.email }</td>
								<td>${user.roles }</td>
								<td class="action">
									<a title="Xoá" class="delete" href="${pageContext.request.contextPath }/user/delete/${user.id }"><i class="fa fa-fw fa-trash"></i></a> 
									<br /> 
									<a title="Xem" href="${pageContext.request.contextPath }/user/${user.id }">
									<i class="fa fa-fw fa-edit"></i></a>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>
				<!-- /.box-body -->

				<div class="box-footer clearfix">
					<a href="${pageContext.request.contextPath }/user"
						class="btn btn-info">Thêm mới</a>
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
<script>
$('.delete').click(function(e) {
	e.preventDefault();
	var answer = confirm("Bạn có chắc muốn xoá?");
	if(answer === true) {
		window.location.href = $(this).attr('href');
	}
});
</script>
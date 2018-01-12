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
					<h3 class="box-title">${id != null ? 'Cập Nhật Thông Tin Thành Viên' : 'Tạo Mới Thành Viên' }</h3>
				</div>
				<!-- /.box-header -->
				<!-- form start -->
				<form:form modelAttribute="user" accept-charset="utf-8" enctype="multipart/form-data"
				 action="${pageContext.request.contextPath }/user?${_csrf.parameterName }=${_csrf.token}"
				  method="${user.id != null ? 'put' : 'post' }" class="form-horizontal">
					<form:hidden path="id"/>
					<div class="box-body">
						<div class="form-group">
							<label for="username" class="col-sm-2 control-label">Tên đăng nhập</label>
							<div class="col-sm-10">
								<form:input path="username" class="form-control" id="username" placeholder="Username"/>
								<form:errors path="username" cssClass="error"></form:errors>
								<span class="error">${errorMsg }</span>
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-sm-2 control-label">Mật khẩu</label>
							<div class="col-sm-10">
								<form:password autocomplete="new-password" path="password" class="form-control" id="password" placeholder="${user.id != null ? 'Nhập nếu cần thay đổi mật khẩu' : 'Nhập mật khẩu' }"/>
								<form:errors path="password" cssClass="error"></form:errors>
							</div>
						</div>
						<div class="form-group">
							<label for="name" class="col-sm-2 control-label">Tên</label>
							<div class="col-sm-10">
								<form:input path="name" class="form-control" id="name" placeholder="Tên"/>
								<form:errors path="name" cssClass="error"></form:errors>
							</div>
						</div>
						<div class="form-group">
							<label for="department" class="col-sm-2 control-label">Phòng ban</label>
							<div class="col-sm-10">
								<form:input path="department" class="form-control" id="department"
									placeholder="Phòng"/>
							</div>
						</div>
						<div class="form-group">
							<label for="phone" class="col-sm-2 control-label">Số điện thoại</label>
							<div class="col-sm-10">
								<form:input path="phone" class="form-control" id="phone"
									placeholder="Số điện thoại"/>
							</div>
						</div>
						<div class="form-group">
							<label for="email" class="col-sm-2 control-label">Email</label>
							<div class="col-sm-10">
								<form:input path="email" type="email" class="form-control" id="email"
									placeholder="Email"/>
							</div>
						</div>
						<div class="form-group">
							<label for="accountNonLocked" class="col-sm-2 control-label"></label>
							<div class="checkbox col-sm-5">
			                  <label>
			                    <form:checkbox path="accountNonLocked"/> Được quyền đăng nhập
			                  </label>
			                </div>
						</div>
						<div class="form-group">
							<label for="roles" class="col-sm-2 control-label">Quyền</label>
							<div class="col-sm-10">
								<form:select multiple="false" cssClass="form-control" path="roles" items="${roles}" itemLabel="name" itemValue="id" />
							</div>
						</div>
					</div>
					<!-- /.box-body -->
					<div class="box-footer">
						<a href="${pageContext.request.contextPath }/user/view?page=0&size=10" class="btn btn-default">Huỷ</a>
						<button type="submit" class="btn btn-info pull-right">${user.id == null ? 'Thêm' : 'Cập Nhật' }</button>
					</div>
					<!-- /.box-footer -->
				</form:form>
			</div>
		</div>
	</div>
	<!-- /.row -->
</section>
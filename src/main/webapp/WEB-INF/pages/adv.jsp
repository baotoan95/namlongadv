<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<style>
#mapCanvas {
	width: 300px;
	height: 400px;
}
</style>

<section class="content">
	<div class="row">
		<form:form modelAttribute="advertDto"
			action="${pageContext.request.contextPath }/adv?${_csrf.parameterName }=${_csrf.token}"
			method="${advertDto.advertisement.id == null ? 'post' : 'put' }"
			enctype="multipart/form-data">
			<form:hidden path="advertisement.id" />
			<div class="col-md-9">
				<div class="box box-info">
					<div class="box-header with-border">
						<h3 class="box-title">${advertDto.advertisement.id == null ? 'Tạo Mới Thông Tin Bảng Quảng Cáo' : 'Cập Nhật Thông Tin Bảng Quảng Cáo' }</h3>
						<c:if test="${errorMsg != null }">
							<span class="error">${errorMsg }</span>
						</c:if>
					</div>
					<!-- /.box-header -->
					<!-- form start -->
					<div class="form-horizontal">
						<div class="box-body">
							<div class="form-group">
								<label for="code" class="col-md-2 control-label">Mã</label>
								<div class="col-md-10">
									<form:input type="text" path="advertisement.code"
										class="form-control" id="code" placeholder="Nhập mã" />
								</div>
							</div>
							<div class="form-group">
								<label for="title" class="col-md-2 control-label">Tiêu đề</label>
								<div class="col-md-10">
									<form:input type="text" path="advertisement.title"
										class="form-control" id="title" placeholder="Nhập tiêu đề" />
								</div>
							</div>
							<div class="form-group">
								<label for="houseNo" class="col-md-2 control-label">Số
									nhà</label>
								<div class="col-md-10">
									<form:input path="advertisement.houseNo" type="text"
										class="form-control" id="houseNo" placeholder="Nhập số nhà" />
								</div>
							</div>
							<div class="form-group">
								<label for="street" class="col-md-2 control-label">Tên
									đường</label>
								<div class="col-md-10">
									<form:input path="advertisement.street" type="text"
										class="form-control" id="street" placeholder="Nhập tên đường" />
								</div>
							</div>
							<div class="form-group">
								<label for="ward" class="col-md-2 control-label">Phường</label>
								<div class="col-md-10">
									<form:input cssClass="form-control" path="advertisement.ward"
										placeholder="Nhập tên phường/xã" />
								</div>
							</div>
							<div class="form-group">
								<label for="district" class="col-md-2 control-label">Quận</label>
								<div class="col-md-10">
									<form:input cssClass="form-control"
										path="advertisement.district"
										placeholder="Nhập tên quận/huyện" />
								</div>
							</div>
							<div class="form-group">
								<label for="province" class="col-md-2 control-label">Tỉnh</label>
								<div class="col-md-10">
									<form:input cssClass="form-control"
										path="advertisement.province" placeholder="Nhập tên tỉnh" />
								</div>
							</div>
							<div class="form-group">
								<label for="map" class="col-md-2 control-label">Toạ độ</label>
								<div class="col-md-10">
									<form:input path="advertisement.map" type="text" onchange="initialize()"
										class="form-control" id="coordinates" name="coordinates" placeholder="" />
								</div>
							</div>
							<div class="form-group">
								<label for="size" class="col-md-2 control-label">Kích
									thước</label>
								<div class="col-md-10">
									<form:input path="advertisement.size" type="text"
										class="form-control" id="size" placeholder="Nhập kích thước" />
								</div>
							</div>
							<div class="form-group">
								<label for="describe" class="col-md-2 control-label">Mô tả</label>
								<div class="col-md-10">
									<form:textarea path="advertisement.describe" class="form-control"
										id="describe" style="resize: none;" placeholder="Nhập Mô tả" />
								</div>
							</div>
							<div class="form-group">
								<label for="note" class="col-md-2 control-label">Ghi chú</label>
								<div class="col-md-10">
									<form:textarea path="advertisement.note" class="form-control"
										id="note" style="resize: none;" placeholder="Nhập ghi chú" />
								</div>
							</div>

							<div class="row">
								<div class="col-md-6">
									<div class="box box-info">
										<div class="box-header with-border">
											<h3 class="box-title">Thông Tin Chủ Nhà</h3>
										</div>
										<!-- /.box-header -->
										<!-- form start -->
										<div class="form-horizontal">
											<div class="box-body">
												<div class="form-group">
													<label for="ownerPhone" class="col-md-3 control-label">Phone</label>
													<div class="col-md-9">
														<form:input path="advertisement.ownerPhone" type="text"
															class="form-control" id="ownerPhone"
															placeholder="Số điện thoại" />
													</div>
												</div>
												<div class="form-group">
													<label for="ownerEmail" class="col-md-3 control-label">Email</label>
													<div class="col-md-9">
														<form:input path="advertisement.ownerEmail" type="text"
															class="form-control" id="ownerEmail" placeholder="Email" />
													</div>
												</div>
												<div class="form-group">
													<label for="ownerPrice" class="col-md-3 control-label">Giá</label>
													<div class="col-md-9">
														<form:input path="advertisement.ownerPrice" type="text"
															class="form-control" id="ownerPrice"
															placeholder="Giá thuê" />
													</div>
												</div>
												<div class="form-group">
													<label for="ownerContactPerson"
														class="col-md-3 control-label">NLH</label>
													<div class="col-md-9">
														<form:input path="advertisement.ownerContactPerson"
															type="text" class="form-control" id="ownerContactPerson"
															placeholder="Người liên hệ" />
													</div>
												</div>
												<div class="form-group">
													<label for="ownerStartDate"
														class="col-md-3 control-label">Bắt đầu</label>
													<div class="col-md-9">
														<form:input path="advertisement.ownerStartDate"
															type="date" class="form-control" id="ownerStartDate" />
													</div>
												</div>
												<div class="form-group">
													<label for="ownerEndDate"
														class="col-md-3 control-label">Kết thúc</label>
													<div class="col-md-9">
														<form:input path="advertisement.ownerEndDate"
															type="date" class="form-control" id="ownerEndDate" />
													</div>
												</div>
											</div>
											<!-- /.box-footer -->
										</div>
									</div>
								</div>

								<div class="col-md-6">
									<div class="box box-info">
										<div class="box-header with-border">
											<h3 class="box-title">Thông Tin CT Quảng Cáo</h3>
										</div>
										<!-- /.box-header -->
										<!-- form start -->
										<div class="form-horizontal">
											<div class="box-body">
												<div class="form-group">
													<label for="advCompName" class="col-md-3 control-label">Tên Cty</label>
													<div class="col-md-9">
														<form:input path="advertisement.advCompName" type="text"
															class="form-control" id="advCompName"
															placeholder="Tên công ty" />
													</div>
												</div>
												<div class="form-group">
													<label for="advCompPhone" class="col-md-3 control-label">Phone</label>
													<div class="col-md-9">
														<form:input path="advertisement.advCompPhone" type="text"
															class="form-control" id="advCompPhone"
															placeholder="Email" />
													</div>
												</div>
												<div class="form-group">
													<label for="advCompEmail" class="col-md-3 control-label">Email</label>
													<div class="col-md-9">
														<form:input path="advertisement.advCompEmail" type="text"
															class="form-control" id="advCompEmail"
															placeholder="Email" />
													</div>
												</div>
												<div class="form-group">
													<label for="advCompPrice" class="col-md-3 control-label">Giá</label>
													<div class="col-md-9">
														<form:input path="advertisement.advCompPrice" type="text"
															class="form-control" id="advCompPrice"
															placeholder="Giá thuê" />
													</div>
												</div>
												<div class="form-group">
													<label for="advCompContactPerson"
														class="col-md-3 control-label">NLH</label>
													<div class="col-md-9">
														<form:input path="advertisement.advCompContactPerson"
															type="text" class="form-control"
															id="advCompContactPerson" placeholder="Người liên hệ" />
													</div>
												</div>
												<div class="form-group">
													<label for="advCompStartDate"
														class="col-md-3 control-label">Bắt đầu</label>
													<div class="col-md-9">
														<form:input path="advertisement.advCompStartDate"
															type="date" class="form-control" id="advCompStartDate" />
													</div>
												</div>
												<div class="form-group">
													<label for="advCompEndDate"
														class="col-md-3 control-label">Kết thúc</label>
													<div class="col-md-9">
														<form:input path="advertisement.advCompEndDate"
															type="date" class="form-control" id="advCompEndDate" />
													</div>
												</div>
											</div>
											<!-- /.box-footer -->
										</div>
									</div>
								</div>
							</div>

							<div style="width: 100%" id="mapCanvas"></div>
							<div id="infoPanel">
								<b>Marker status:</b>
								<div id="markerStatus">
									<i>Click and drag the marker.</i>
								</div>
								<b>Current position:</b>
								<div id="info"></div>
								<b>Closest matching address:</b>
								<div id="address"></div>
							</div>
						</div>
						<!-- /.box-body -->
						<div class="box-footer">
							<a
								href="${pageContext.request.contextPath }/adv/view?page=0&size=10"
								class="btn btn-default">Huỷ</a>
							<button type="submit" class="btn btn-info pull-right">${advertDto.advertisement.id == null ? 'Thêm' : 'Cập Nhật' }</button>
						</div>
						<!-- /.box-footer -->
					</div>
				</div>
			</div>

			<div class="col-md-3">
				<div id="preview">
					<c:forEach items="${advertDto.advertisement.advImages }"
						var="advImage">
						<img class="img-thumbnail"
							src="${pageContext.request.contextPath }/resources/images?url=${advImage.url }"
							alt="${advImage.name }"></img>
					</c:forEach>
				</div>

				<form:input id="imageBrowser" onchange="previewImages()"
					class="form-control" path="files" type="file" multiple="multiple"
					accept="image/x-png,image/gif,image/jpeg" />
				<script type="text/javascript">
					function previewImages() {
						$('#preview').empty();

						var files = document.getElementById("imageBrowser").files;
						for (var i = 0; i < files.length; i++) {
							var oFReader = new FileReader();
							oFReader.readAsDataURL(files[i]);

							oFReader.onload = function(oFREvent) {
								var image = "<img src='"+oFREvent.target.result+"' class='img-thumbnail'/>";
								$('#preview').append(image);
							};
						}
					};
				</script>
			</div>
		</form:form>
	</div>
	<!-- /.row -->
</section>

<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&key=AIzaSyCf3QMz3TbdiJvz7goQinnfwLoQcStQqLg"></script>
<script src="<c:url value='/resources/app/location-api.js'/>"></script>
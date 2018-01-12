<%@page import="net.namlongadv.models.AdvImage"%>
<%@page import="java.util.List"%>
<%@page import="net.namlongadv.models.Advertisement"%>
<%@page import="net.namlongadv.dto.AdvertisementDTO"%>
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
.error {
	color: red;
}
.preview-item {
	background: white;
	margin-bottom: 20px;
	border: 1px solid white;
}
.close {
	height: 20px;
	text-align: right;
	font-weight: bold;
	width: 100%;
}
.close:hover {
	color: red;
}
</style>

<section class="content">
	<div class="row">
		<form:form modelAttribute="advertDto"
			action="${pageContext.request.contextPath }/adv?${_csrf.parameterName }=${_csrf.token}"
			method="${advertDto.advertisement.id == null ? 'post' : 'put' }"
			enctype="multipart/form-data">
			<form:hidden path="advertisement.id" />
			<input type="hidden" id="allowEdit" value="${advertDto.advertisement.allowEdit }">
			<div class="col-md-9">
				<div class="box box-info">
					<div class="box-header with-border">
						<h3 class="box-title">${advertDto.advertisement.id == null ? 'Tạo Mới Thông Tin Bảng Quảng Cáo' : (advertDto.advertisement.allowEdit ? 'Cập Nhật Thông Tin Bảng Quảng Cáo' : 'Thông Tin Bảng Quảng Cáo') }</h3>
						<c:if test="${errorMsg != null }">
							<br/>
							<br/>
							<p class="error">${errorMsg }</p>
						</c:if>
					</div>
					<!-- /.box-header -->
					<!-- form start -->
					<div class="form-horizontal">
						<div class="box-body">
							<div class="form-group">
								<label for="code" class="col-md-3 control-label">Mã</label>
								<div class="col-md-3">
									<form:input type="text" path="advertisement.code"
										class="form-control" id="code" placeholder="Nhập mã (sẽ tự tạo nếu không nhập)" />
								</div>
								<label for="provinceCode" class="col-md-3 control-label">Mã tỉnh</label>
								<div class="col-md-3">
									<form:input type="text" path="advertisement.provinceCode"
										class="form-control" id="provinceCode" placeholder="Nhập mã tỉnh" />
								</div>
							</div>
							<div class="form-group">
								<label for="title" class="col-md-3 control-label">Tiêu đề</label>
								<div class="col-md-9">
									<form:input type="text" path="advertisement.title"
										class="form-control" id="title" placeholder="Nhập tiêu đề" />
								</div>
							</div>
							<div class="form-group">
								<label for="houseNo" class="col-md-3 control-label">Số nhà</label>
								<div class="col-md-9">
									<form:input path="advertisement.houseNo" type="text"
										class="form-control" id="houseNo" placeholder="Nhập số nhà" />
								</div>
							</div>
							<div class="form-group">
								<label for="street" class="col-md-3 control-label">Tên
									đường</label>
								<div class="col-md-9">
									<form:input path="advertisement.street" type="text"
										class="form-control" id="street" placeholder="Nhập tên đường" />
								</div>
							</div>
							<div class="form-group">
								<label for="ward" class="col-md-3 control-label">Phường</label>
								<div class="col-md-9">
									<form:input cssClass="form-control" path="advertisement.ward"
										placeholder="Nhập tên phường/xã" />
								</div>
							</div>
							<div class="form-group">
								<label for="district" class="col-md-3 control-label">Quận</label>
								<div class="col-md-9">
									<form:input cssClass="form-control"
										path="advertisement.district"
										placeholder="Nhập tên quận/huyện" />
								</div>
							</div>
							<div class="form-group">
								<label for="province" class="col-md-3 control-label">Tỉnh</label>
								<div class="col-md-9">
									<form:select path="advertisement.province" items="${provinces }" itemLabel="name" itemValue="code"></form:select>
<%-- 									<form:input cssClass="form-control" --%>
<%-- 										path="advertisement.province" placeholder="Nhập tên tỉnh" /> --%>
								</div>
							</div>
							<div class="form-group">
								<label for="map" class="col-md-3 control-label">Toạ độ</label>
								<div class="col-md-9">
									<form:input path="advertisement.map" type="text" onchange="initialize()"
										class="form-control" id="coordinates" name="coordinates" placeholder="" />
								</div>
							</div>
							<security:authorize access="hasRole('ROLE_ADMIN')">
							<div class="form-group">
								<label for="price" class="col-md-3 control-label">Đơn giá</label>
								<div class="col-md-9">
									<form:input path="advertisement.price" type="text"
										class="form-control" id="price" name="price" placeholder="Nhập đơn giá (USD/năm)" />
								</div>
							</div>
							</security:authorize>
							<div class="form-group">
								<label for="size" class="col-md-3 control-label">Kích thước</label>
								<div class="col-md-4">
									<form:input path="advertisement.widthSize" type="text"
										class="form-control" id="size" placeholder="Chiều rộng (m)" />
								</div>
								<div class="col-md-1" style="text-align: center;">
									<label class="control-label">x</label>
								</div>
								<div class="col-md-4">
									<form:input path="advertisement.heightSize" type="text"
										class="form-control" id="size" placeholder="Chiều cao (m)" />
								</div>
							</div>
							<div class="form-group">
								<label for="views" class="col-md-3 control-label">Tầm nhìn</label>
								<div class="col-md-9">
									<form:input path="advertisement.views" class="form-control"
										id="views" style="resize: none;" placeholder="Nhập tầm nhìn" />
								</div>
							</div>
							<div class="form-group">
								<label for="flow" class="col-md-3 control-label">Lưu lượng</label>
								<div class="col-md-9">
									<form:input type="number" path="advertisement.flow" class="form-control"
										id="views" style="resize: none;" placeholder="Nhập số lượng người/ngày" />
								</div>
							</div>
							<div class="form-group">
								<label for="implTime" class="col-md-3 control-label">Thời gian thực hiện</label>
								<div class="col-md-9">
									<form:input type="number" path="advertisement.implTime" class="form-control"
										id="implTime" style="resize: none;" placeholder="Nhập số ngày thực hiện" />
								</div>
							</div>
							<div class="form-group">
								<label for="implForm" class="col-md-3 control-label">Hình thức thực hiện</label>
								<div class="col-md-9">
									<form:input path="advertisement.implForm" class="form-control"
										id="implForm" style="resize: none;" placeholder="Nhập hình thức thực hiện" />
								</div>
							</div>
							<div class="form-group">
								<label for="lightSystem" class="col-md-3 control-label">Hệ thống chiếu sáng</label>
								<div class="col-md-9">
									<form:input path="advertisement.lightSystem" class="form-control"
										id="lightSystem" style="resize: none;" placeholder="Nhập hệ thống chiếu sáng" />
								</div>
							</div>
							<div class="form-group">
								<label for="describe" class="col-md-2 control-label">Mô tả</label>
								<div class="col-md-10">
									<form:textarea path="advertisement.describe" class="form-control"
										id="describe" placeholder="Nhập mô tả" />
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
												<div class="form-group">
													<label for="ownerNote" class="col-md-3 control-label">Ghi chú</label>
													<div class="col-md-9">
														<form:textarea path="advertisement.ownerNote" class="form-control"
															id="ownerNote" placeholder="Nhập ghi chú" />
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
															placeholder="Số điện thoại" />
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
												<div class="form-group">
													<label for="advCompNote" class="col-md-3 control-label">Ghi chú</label>
													<div class="col-md-9">
														<form:textarea path="advertisement.advCompNote" class="form-control"
															id="advCompNote" placeholder="Nhập ghi chú" />
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
							<c:if test="${advertDto.advertisement.allowEdit }">
								<a href="${pageContext.request.contextPath }/adv/view?page=0&size=10" class="btn btn-default">Huỷ</a>
							</c:if>
							<button type="submit" ${!advertDto.advertisement.allowEdit ? 'disabled' : '' } class="btn btn-info pull-right">${advertDto.advertisement.id == null ? 'Thêm' : 'Cập Nhật' }</button>
						</div>
						<!-- /.box-footer -->
					</div>
				</div>
			</div>

			<div class="col-md-3">
				<div id="preview">
				<%
					AdvertisementDTO advertDto = (AdvertisementDTO) request.getAttribute("advertDto");
					List<AdvImage> images = advertDto.getAdvertisement().getAdvImages();
					int numOfImagesAvailable = images != null ? images.size() : 0;
					int numOfImages = 6;
					AdvImage advImage = null;
					for(int i = 0; i < numOfImages; i++) {
						if(i < numOfImagesAvailable) {
							advImage = images.get(i);
				%>
						<div class="preview-item">
							<div class="close" title="Xoá" onclick="deleteImage(this)">X</div>
							<input type="hidden" name="prevImages[<%= i %>]" value="<%= advImage.getId() %>"/>
							<img class="img-thumbnail"
		 						src="${pageContext.request.contextPath }/resources/images?url=<%= advImage.getUrl() %>"
		 						alt="${advImage.name }"></img>
							<input type="file" onchange="previewImages(this)" accept="image/gif,image/jpeg" name="files[<%= i %>]" class="form-control"/>
						</div>
				<%
						} else {
				%>
						<div class="preview-item">
							<img class="img-thumbnail" src="" alt="${advImage.name }"></img>
							<input type="file" onchange="previewImages(this)" accept="image/gif,image/jpeg" name="files[<%= i %>]" class="form-control"/>
						</div>
				<%
						}
					}
				%>
				</div>

				<script type="text/javascript">
					function previewImages(input, isPrevImage) {
						if (input.files && input.files[0]) {
						    var reader = new FileReader();

						    reader.onload = function(e) {
						    	$(input).prev().attr('src', e.target.result);
						    }
						    reader.readAsDataURL(input.files[0]);
						}
						deleteImage(input);
					}
					
					function deleteImage(element) {
						$(element).parent().find('input[type=hidden]').remove();
						$(element).parent().find('img').removeAttr('src');
						$(element).parent().find('img').removeAttr('alt');
						$(element).parent().find('.close').remove();
					}
				</script>
			</div>
		</form:form>
	</div>
	<!-- /.row -->
</section>

<script>
	$(document).ready(function() {
		var disabled = $('#allowEdit').val();
		if(disabled === 'true') {
			$('input').removeAttr('disabled');
			$('textarea').removeAttr('disabled');
		} else {
			$('input[type=text], input[type=number], input[type=date], input[type=file]').attr('disabled', true);
			$('textarea').attr('disabled', true);
		}
	});
</script>

<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&key=AIzaSyCf3QMz3TbdiJvz7goQinnfwLoQcStQqLg"></script>
<script src="<c:url value='/resources/app/location-api.js'/>"></script>
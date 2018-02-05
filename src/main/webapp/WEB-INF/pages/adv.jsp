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
	display: inline-block;
	float: right;
}
.close:hover {
	color: red;
}
</style>

<section class="content">
	<div class="row">
		<form:form modelAttribute="advertDto" id="formData"
			action="${pageContext.request.contextPath }/adv?${_csrf.parameterName }=${_csrf.token}"
			method="${advertDto.advertisement.id == null ? 'post' : 'put' }"
			enctype="multipart/form-data">
			<form:hidden path="advertisement.id" />
			<form:hidden path="advertisement.publishedId" id="publishedId" />
			
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
									<form:input readonly="true" maxlength="254" type="text" path="advertisement.code"
										class="form-control" id="code" placeholder="Nhập mã (sẽ tự tạo nếu không nhập)" />
								</div>
								<c:if test="${not empty advertDto.advertisement.updatedDate }">
								<label for="updatedDate" class="col-md-3 control-label">Cập nhật ngày</label>
								<div class="col-md-3">
									<form:input readonly="true" type="text" path="advertisement.updatedDate"
										class="form-control" id="updatedDate" placeholder="Ngày cập nhật" />
								</div>
								</c:if>
							</div>
							<div class="form-group">
								<label for="title" class="col-md-3 control-label">Tiêu đề</label>
								<div class="col-md-9">
									<form:input maxlength="254" type="text" path="advertisement.title"
										class="form-control" id="title" placeholder="Nhập tiêu đề" />
								</div>
							</div>
							<div class="form-group">
								<label for="houseNo" class="col-md-3 control-label">Số nhà</label>
								<div class="col-md-9">
									<form:input maxlength="254" path="advertisement.houseNo" type="text"
										class="form-control" id="houseNo" placeholder="Nhập số nhà" />
								</div>
							</div>
							<div class="form-group">
								<label for="street" class="col-md-3 control-label">Tên
									đường</label>
								<div class="col-md-9">
									<form:input maxlength="254" path="advertisement.street" type="text"
										class="form-control" id="street" placeholder="Nhập tên đường" />
								</div>
							</div>
							<div class="form-group">
								<label for="ward" class="col-md-3 control-label">Phường</label>
								<div class="col-md-9">
									<form:input maxlength="254" cssClass="form-control" path="advertisement.ward" id="ward"
										placeholder="Nhập tên phường/xã" />
								</div>
							</div>
							<div class="form-group">
								<label for="district" class="col-md-3 control-label">Quận</label>
								<div class="col-md-9">
									<form:input maxlength="254" cssClass="form-control"
										path="advertisement.district" id="district"
										placeholder="Nhập tên quận/huyện" />
								</div>
							</div>
							<div class="form-group">
								<label for="province" class="col-md-3 control-label">Tỉnh</label>
								<div class="col-md-9">
									<form:select id="province" onchange="updateCode()" cssClass="form-control select2" path="advertisement.provinceCode">
										<form:option value="">Chọn tỉnh</form:option>
										<c:forEach items="${provinces }" var="province">
										<form:option name="${province.name }" value="${province.code }">${province.name }</form:option>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="form-group">
								<label for="map" class="col-md-3 control-label">Toạ độ</label>
								<div class="col-md-9">
									<form:input maxlength="254" path="advertisement.map" type="text" onchange="initialize()"
										class="form-control" id="coordinates" name="coordinates" placeholder="" />
								</div>
							</div>
							<security:authorize access="hasRole('ROLE_ADMIN')">
							<div class="form-group">
								<label for="price" class="col-md-3 control-label">Đơn giá</label>
								<div class="col-md-9">
									<form:input maxlength="254" path="advertisement.price" type="text"
										class="form-control" id="price" name="price" placeholder="Nhập đơn giá (USD/năm)" />
								</div>
							</div>
							</security:authorize>
							<div class="form-group">
								<label for="heightSize" class="col-md-3 control-label">Kích thước</label>
								<div class="col-md-4">
									<form:input maxlength="254" path="advertisement.heightSize" type="text"
										class="form-control" id="heightSize" placeholder="Chiều cao (m)" />
								</div>
								<div class="col-md-1" style="text-align: center;">
									<label class="control-label">x</label>
								</div>
								<div class="col-md-4">
									<form:input maxlength="254" path="advertisement.widthSize" type="text"
										class="form-control" id="widthSize" placeholder="Chiều rộng (m)" />
								</div>								
							</div>
							<div class="form-group">
								<label for="views" class="col-md-3 control-label">Tầm nhìn</label>
								<div class="col-md-9">
									<form:input maxlength="254" path="advertisement.views" class="form-control"
										id="views" style="resize: none;" placeholder="Nhập tầm nhìn" />
								</div>
							</div>
							<div class="form-group">
								<label for="flow" maxlength="254" class="col-md-3 control-label">Lưu lượng</label>
								<div class="col-md-9">
									<form:input type="number" path="advertisement.flow" class="form-control"
										id="flow" style="resize: none;" placeholder="Nhập số lượng người/ngày" />
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
								<label for="implForm" maxlength="254" class="col-md-3 control-label">Hình thức thực hiện</label>
								<div class="col-md-9">
									<form:input path="advertisement.implForm" class="form-control"
										id="implForm" style="resize: none;" placeholder="Nhập hình thức thực hiện" />
								</div>
							</div>
							<div class="form-group">
								<label for="lightSystem" class="col-md-3 control-label">Hệ thống chiếu sáng</label>
								<div class="col-md-9">
									<form:input maxlength="254" path="advertisement.lightSystem" class="form-control"
										id="lightSystem" style="resize: none;" placeholder="Nhập hệ thống chiếu sáng" />
								</div>
							</div>
							<div class="form-group">
								<label for="type" class="col-md-3 control-label">Loại hình</label>
								<div class="col-md-9">
									<form:input maxlength="254" path="advertisement.type" class="form-control"
										id="type" style="resize: none;" placeholder="Nhập loại hình" />
								</div>
							</div>
							<c:if test="${not empty advertDto.advertisement.updatedDate }">
							<div class="form-group">
								<label for="createdBy"
									class="col-md-3 control-label">Người tạo</label>
								<div class="col-md-9">
									<input value="${advertDto.advertisement.createdBy.name }"
										type="text" readonly class="form-control" id="createdBy" />
								</div>
							</div>
							</c:if>
							<div class="form-group">
								<label for="describe" class="col-md-2 control-label">Mô tả</label>
								<div class="col-md-10">
									<form:textarea maxlength="254" path="advertisement.describe" class="form-control"
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
														<form:input maxlength="254" path="advertisement.ownerPhone" type="text"
															class="form-control" id="ownerPhone"
															placeholder="Số điện thoại" />
													</div>
												</div>
												<div class="form-group">
													<label for="ownerEmail" class="col-md-3 control-label">Email</label>
													<div class="col-md-9">
														<form:input maxlength="254" path="advertisement.ownerEmail" type="text"
															class="form-control" id="ownerEmail" placeholder="Email" />
													</div>
												</div>
												<div class="form-group">
													<label for="ownerPrice" class="col-md-3 control-label">Giá</label>
													<div class="col-md-9">
														<form:input maxlength="254" path="advertisement.ownerPrice" type="text"
															class="form-control" id="ownerPrice"
															placeholder="Giá thuê" />
													</div>
												</div>
												<div class="form-group">
													<label for="ownerContactPerson"
														class="col-md-3 control-label">NLH</label>
													<div class="col-md-9">
														<form:input maxlength="254" path="advertisement.ownerContactPerson"
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
														<form:textarea maxlength="254" path="advertisement.ownerNote" class="form-control"
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
														<form:input maxlength="254" path="advertisement.advCompName" type="text"
															class="form-control" id="advCompName"
															placeholder="Tên công ty" />
													</div>
												</div>
												<div class="form-group">
													<label for="advCompPhone" class="col-md-3 control-label">Phone</label>
													<div class="col-md-9">
														<form:input maxlength="254" path="advertisement.advCompPhone" type="text"
															class="form-control" id="advCompPhone"
															placeholder="Số điện thoại" />
													</div>
												</div>
												<div class="form-group">
													<label for="advCompEmail" class="col-md-3 control-label">Email</label>
													<div class="col-md-9">
														<form:input maxlength="254" path="advertisement.advCompEmail" type="text"
															class="form-control" id="advCompEmail"
															placeholder="Email" />
													</div>
												</div>
												<div class="form-group">
													<label for="advCompPrice" class="col-md-3 control-label">Giá</label>
													<div class="col-md-9">
														<form:input maxlength="254" path="advertisement.advCompPrice" type="text"
															class="form-control" id="advCompPrice"
															placeholder="Giá thuê" />
													</div>
												</div>
												<div class="form-group">
													<label for="advCompContactPerson"
														class="col-md-3 control-label">NLH</label>
													<div class="col-md-9">
														<form:input maxlength="254" path="advertisement.advCompContactPerson"
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
														<form:textarea maxlength="254" path="advertisement.advCompNote" class="form-control"
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
							<button type="button" onclick="submitData()" ${!advertDto.advertisement.allowEdit ? 'disabled' : '' } class="btn btn-info pull-right">${advertDto.advertisement.id == null ? 'Thêm' : 'Cập Nhật' }</button>
							
							<security:authorize
								access="hasAnyRole('ROLE_ADMIN')">
							<c:if test="${not empty advertDto.advertisement.id }">
								<button style="margin-right: 5px;" 
									onclick="publish()" 
									type="button" ${!advertDto.advertisement.allowEdit ? 'disabled' : '' } 
									class="btn btn-info pull-right">Xuất Bản</button>
							</c:if>
							<c:if test="${advertDto.advertisement.publishedId > 0 }">
								<button style="margin-right: 5px;" 
									onclick="unpublish()" 
									type="button" ${!advertDto.advertisement.allowEdit ? 'disabled' : '' } 
									class="btn btn-info pull-right">Huỷ Xuất Bản</button>
							</c:if>
							</security:authorize>
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
		 						alt="<%= advImage.getName() %>" name="<%= advImage.isMap() ? "map" : "" %>"></img>
		 					<input type="file" onchange="previewImages(this)" accept="image/gif,image/jpeg,image/png" name="files" class="form-control"/>
						</div>
				<%
						}
					}
				%>
					<div id="new-preview"></div>
					<input type="file" multiple="multiple" onchange="previewImages(this, true)" accept="image/gif,image/jpeg,image/png" name="files" class="form-control"/>
				</div>

				<script type="text/javascript">
					function previewImages(input, isNew) {
						if (input.files && input.files[0] && !isNew) {
						    var reader = new FileReader();

						    reader.onload = function(e) {
						    	$(input).prev().attr('src', e.target.result);
						    	if($(input).parent().find('.close').length === 0) { 
						    		$(input).parent().prepend('<div class="close" title="Xoá" onclick="deleteImage(this)">X</div>');
						    	}
								$(input).parent().find('input[type=hidden]').remove();
						    }
						    reader.readAsDataURL(input.files[0]);
						} else {
							$('#new-preview').empty();
							for(var i = 0; i < input.files.length; i++) {
								
								var reader = new FileReader();

							    reader.onload = function(e) {
							    	$('#new-preview').append(
							    		'<div class="preview-item">' +
											'<img class="img-thumbnail" src="'+ e.target.result +'" alt="${advImage.name }"></img>' +
										'</div>'
									);
							    }
							    reader.readAsDataURL(input.files[i]);
							}
						}
					}
					
					function deleteImage(element) {
						$(element).parent().find('input[type=hidden]').remove();
						
						$(element).parent().find('input[type=file]').wrap('<form>').closest('form').get(0).reset();
						$(element).parent().find('input[type=file]').unwrap();
						
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
	function submitData() {
		var code = $('#code').val();
		var title = $('#title').val();
		
		// Get auto code
		if(code.length > 4) {
			var autoCode = code.split('-')[1];
		} else {
			var autoCode = code;
		}
		
		var titleParts = title.split(' - ');
		if(titleParts.length > 1) {
			var lastPart = titleParts[titleParts.length - 1];
			if(lastPart.lastIndexOf(autoCode) !== -1) {
				$('#title').val(title.replace(' - ' + lastPart, ' - ' + code));
			} else {
				$('#title').val(title + ' - ' + code);	
			}
		} else {
			$('#title').val(title + ' - ' + code);
		}
		$('#formData').submit();
	}

	$(document).ready(function() {
		$(".select2").select2();
		
		var disabled = $('#allowEdit').val();
		if(disabled === 'true') {
			$('input').removeAttr('disabled');
			$('textarea').removeAttr('disabled');
		} else {
			$('input[type=text], input[type=number], input[type=date], input[type=file], select').attr('disabled', true);
			$('textarea').attr('disabled', true);
		}
		
		updateCode();
	});
	
	function updateCode() {
		var codeComponent = $('#code');
		var codeParts = codeComponent.val().split("-");
		var code = codeParts[0];
		if(codeParts && codeParts.length === 2) {
			code = codeParts[1];
		}
		var provinceCode = $('#province').val();
		if(provinceCode) {
			codeComponent.val(provinceCode + "-" + code);
		} else {
			codeComponent.val(code);
		}
	}
	
	function publish() {
		// Init data
		var location = $('#coordinates').val().split(", ");
		var lat = location[0], lng = location[1];
		
		var province = document.getElementById('province').options[document.getElementById('province').selectedIndex].text;
		
		var detail = "<p>Vị trí: " + $('#houseNo').val() + " " + $('#street').val() 
			+ " " + $('#ward').val() + " " + $('#district').val() 
			+ " " + province + "</p>";
		detail += "<p>Loại hình: " + $('#type').val() + "</p>";
		detail += "<p>Tầm nhìn: " + $('#views').val() + "</p>";
		detail += "<p>Kích thước: " + $('#widthSize').val() + " x " + $('#heightSize').val() + "</p>";
		detail += "<p>Mật độ: " + $('#flow').val() + " người/ngày</p>";
		detail += "<p>Hình thức thực hiện: " + $('#implForm').val() + "</p>";
		detail += "<p>Hệ thống chiếu sáng: " + $('#lightSystem').val() + "</p>";
		detail += "<p>Tình trạng: Đang chào bán</p>";
		detail += "<p>Đơn giá: Liên hệ để biết giá</p>";
		
		var published = "<p>Vị trí: " + $('#houseNo').val() + " " + $('#street').val() 
		+ " " + $('#ward').val() + " " + $('#district').val() 
		+ " " + province + "</p>";
		published += "<p>Loại hình: " + $('#type').val() + "</p>";
		
		var data = {
				"title": $('#title').val(),
				"price": $('#price').val(),
				"description": $('#describe').val(),
				"published": 0,
				"ordering": 0,
				"lat": lat,
				"long": lng,
				"detail": detail
			}
		
		var images = $('#preview img');
		if(images.length > 0) {
			// Avatar
			data['images'] = "http://namlongadv.ddns.net:7070" + images[0].getAttribute('src');
			// Other image
			for(var i = 1; i < images.length; i++) {
				if(images[i].getAttribute('name') === 'map') {
					data['image3'] = "http://namlongadv.ddns.net:7070" + images[i].getAttribute('src');
				} else {
					data['image' + i] = "http://namlongadv.ddns.net:7070" + images[i].getAttribute('src');
				}
			}
		}
		
		var publishedId = $('#publishedId').val();
		if(publishedId === undefined || publishedId < 0 || publishedId === '') {
			publishedId = 0;
		}
		// Validate if it existed
			$.ajax({
				method: "GET",
				url: "http://billboardquangcao.com/api.php/firerox_jv_article/" + publishedId,
			}).done(function(msg) {
				console.log(msg);
				// Update if it existed
				$.ajax({
		 			method: "PUT",
		 			url: "http://billboardquangcao.com/api.php/firerox_jv_article/" + publishedId,
		 			data: data
		 		}).done(function(msg) {
		 			if(msg === 1) {
			 			alert("Xuất bản thành công!!!");
			 			$('#publishedId').attr("value", publishedId);
			 			$('#formData').submit();
		 			} else {
		 				alert("An error occurred. Please contact IT department to get support.")
		 			}
		 		});
			}).error(function(e) {
				// Create if it does not existed
				if(e.status === 404) {
			 		$.ajax({
			 			method: "POST",
			 			url: "http://billboardquangcao.com/api.php/firerox_jv_article",
			 			data: data
			 		}).done(function(msg) {
			 			alert("Xuất bản thành công!!!");
			 			$('#publishedId').attr("value", msg);
			 			$('#formData').submit();
			 		});
				} else {
					alert("An error occurred. Please contact IT department to get support.")
				}
			});
	}
	
	function unpublish() {
		var publishedId = $('#publishedId').val();
		if(publishedId !== undefined && publishedId > 0 && publishedId !== '') {
			var conf = confirm("Bạn có chắc muốn huỷ xuất bản?");
			if(conf) {
				$.ajax({
		 			method: "DELETE",
		 			url: "http://billboardquangcao.com/api.php/firerox_jv_article/" + publishedId,
		 		}).done(function(msg) {
		 			alert("Huỷ xuất bản thành công!!!");
		 			$('#publishedId').attr("value", 0);
		 			$('#formData').submit();
		 		});
			}
		} else {
			alert("Chưa được xuất bản!!!");
		}
	}
</script>

<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&key=AIzaSyCf3QMz3TbdiJvz7goQinnfwLoQcStQqLg"></script>
<script src="<c:url value='/resources/app/location-api.js'/>"></script>
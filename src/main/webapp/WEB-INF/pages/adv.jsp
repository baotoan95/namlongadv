<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
	
<section class="content">
	<div class="row">
		<form:form modelAttribute="advertDto"
			action="${pageContext.request.contextPath }/adv?${_csrf.parameterName }=${_csrf.token}" 
				method="${advertDto.advertisement.id == null ? 'post' : 'put' }" enctype="multipart/form-data">
			<form:hidden path="advertisement.id"/>
		<div class="col-xs-9">
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
							<label for="code" class="col-sm-2 control-label">Mã</label>
							<div class="col-sm-10">
								<form:input type="text" path="advertisement.code" class="form-control" id="code"
 									placeholder="Nhập mã"/>
							</div>
						</div>
						<div class="form-group">
							<label for="location" class="col-sm-2 control-label">Vị trí</label>
							<div class="col-sm-10">
								<form:input type="text" path="advertisement.location" class="form-control" id="location"
 									placeholder="Nhập vị trí"/>
							</div>
						</div>
						<div class="form-group">
							<label for="houseNo" class="col-sm-2 control-label">Số nhà</label>
							<div class="col-sm-10">
								<form:input path="advertisement.houseNo" type="text" class="form-control" id="houseNo"
									placeholder="Nhập số nhà"/>
							</div>
						</div>
						<div class="form-group">
							<label for="street" class="col-sm-2 control-label">Tên đường</label>
							<div class="col-sm-10">
								<form:input path="advertisement.street" type="text" class="form-control" id="street"
									placeholder="Nhập tên đường"/>
							</div>
						</div>
						<div class="form-group">
							<label for="province" class="col-sm-2 control-label">Tỉnh</label>
							<div class="col-sm-10">
								<form:select cssClass="form-control" path="advertisement.province" items="${provinces }" itemLabel="name" itemValue="name"></form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="district" class="col-sm-2 control-label">Quận</label>
							<div class="col-sm-10">
								<form:select cssClass="form-control" path="advertisement.district" items="${districts }" itemLabel="name" itemValue="name"></form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="ward" class="col-sm-2 control-label">Phường</label>
							<div class="col-sm-10">
								<form:select cssClass="form-control" path="advertisement.ward" items="${wards }" itemLabel="name" itemValue="name"></form:select>
							</div>
						</div>
						<div class="form-group">
							<label for="map" class="col-sm-2 control-label">Google map</label>
							<div class="col-sm-10">
								<form:input path="advertisement.map" type="text" class="form-control" id="map"
									placeholder="Link goole map"/>
							</div>
						</div>
						<div class="form-group">
							<label for="size" class="col-sm-2 control-label">Kích thước</label>
							<div class="col-sm-10">
								<form:input path="advertisement.size" type="text" class="form-control" id="size"
									placeholder="Nhập kích thước"/>
							</div>
						</div>
						<div class="form-group">
							<label for="price" class="col-sm-2 control-label">Đơn giá</label>
							<div class="col-sm-10">
								<form:input path="advertisement.price" type="text" class="form-control" id="price"
									placeholder="Nhập đơn giá"/>
							</div>
						</div>
						
						<div class="form-group">
							<label for="note" class="col-sm-2 control-label">Ghi chú</label>
							<div class="col-sm-10">
								<form:textarea path="advertisement.note" class="form-control" id="note" style="resize: none;"
									placeholder="Ghi chú"/>
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
									<div class="form-horizontal">
										<div class="box-body">
											<div class="form-group">
												<label for="ownerPhone" class="col-sm-2 control-label">ĐT</label>
												<div class="col-sm-10">
													<form:input path="advertisement.ownerPhone" type="text" class="form-control" id="ownerPhone"
														placeholder="Số điện thoại"/>
												</div>
											</div>
											<div class="form-group">
												<label for="ownerEmail" class="col-sm-2 control-label">Email</label>
												<div class="col-sm-10">
													<form:input path="advertisement.ownerEmail" type="text" class="form-control" id="ownerEmail"
														placeholder="Email"/>
												</div>
											</div>
											<div class="form-group">
												<label for="ownerPrice" class="col-sm-2 control-label">Giá</label>
												<div class="col-sm-10">
													<form:input path="advertisement.ownerPrice" type="text" class="form-control" id="ownerPrice"
														placeholder="Giá thuê"/>
												</div>
											</div>
											<div class="form-group">
												<label for="ownerContactPerson" class="col-sm-2 control-label">NLH</label>
												<div class="col-sm-10">
													<form:input path="advertisement.ownerContactPerson" type="text" class="form-control" id="ownerContactPerson"
														placeholder="Người liên hệ"/>
												</div>
											</div>
										</div>
										<!-- /.box-footer -->
									</div>
								</div>
							</div>

							<div class="col-sm-6">
								<div class="box box-info">
									<div class="box-header with-border">
										<h3 class="box-title">Thông Tin CT Quảng Cáo</h3>
									</div>
									<!-- /.box-header -->
									<!-- form start -->
									<div class="form-horizontal">
										<div class="box-body">
											<div class="form-group">
												<label for="advCompPhone" class="col-sm-2 control-label">Phone</label>
												<div class="col-sm-10">
													<form:input path="advertisement.advCompPhone" type="text" class="form-control" id="advCompPhone"
														placeholder="Email"/>
												</div>
											</div>
											<div class="form-group">
												<label for="advCompEmail" class="col-sm-2 control-label">Email</label>
												<div class="col-sm-10">
													<form:input path="advertisement.advCompEmail" type="text" class="form-control" id="advCompEmail"
														placeholder="Email"/>
												</div>
											</div>
											<div class="form-group">
												<label for="advCompPrice" class="col-sm-2 control-label">Giá</label>
												<div class="col-sm-10">
													<form:input path="advertisement.advCompPrice" type="text" class="form-control" id="advCompPrice"
														placeholder="Giá thuê"/>
												</div>
											</div>
											<div class="form-group">
												<label for="advCompContactPerson" class="col-sm-2 control-label">NLH</label>
												<div class="col-sm-10">
													<form:input path="advertisement.advCompContactPerson" type="text" class="form-control" id="advCompContactPerson"
														placeholder="Người liên hệ"/>
												</div>
											</div>
										</div>
										<!-- /.box-footer -->
									</div>
								</div>
							</div>
						</div>

						<iframe
							src="${advertDto.advertisement.map }"
							width="100%" height="450" frameborder="0" style="border: 0"
							allowfullscreen></iframe>
					</div>
					<!-- /.box-body -->
					<div class="box-footer">
						<a href="${pageContext.request.contextPath }/adv/view?page=0&size=10" class="btn btn-default">Huỷ</a>
						<button type="submit" class="btn btn-info pull-right">${advertDto.advertisement.id == null ? 'Thêm' : 'Cập Nhật' }</button>
					</div>
					<!-- /.box-footer -->
				</div>
			</div>
		</div>

		<div class="col-md-3">
			<div id="preview">
				<c:forEach items="${advertDto.advertisement.advImages }" var="advImage">
					<img class="img-thumbnail" src="${pageContext.request.contextPath }/resources/images?url=${advImage.url }" alt="${advImage.name }"></img>
				</c:forEach>
			</div>
			
			<form:input id="imageBrowser" onchange="previewImages()" class="form-control" path="files" type="file" multiple="multiple" accept="image/x-png,image/gif,image/jpeg"/>
			<script type="text/javascript">
				function previewImages() {
					$('#preview').empty();
					
					var files = document.getElementById("imageBrowser").files;
					for(var i = 0; i < files.length; i++) {
						var oFReader = new FileReader();
				        oFReader.readAsDataURL(files[i]);
				
				        oFReader.onload = function (oFREvent) {
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
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.namlongadv.utils.StringUtils"%>
<%@page import="net.namlongadv.models.AdvChangeHistory"%>
<%@page import="net.namlongadv.models.AdvImage"%>
<%@page import="java.util.List"%>
<%@page import="net.namlongadv.models.Advertisement"%>
<%@page import="net.namlongadv.dto.AdvertisementDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

.select2-selection--single, .select2-selection__rendered {
	margin-top: -7px !important;
}

.select2-selection__arrow {
	top: -7px !important;
}

.tbl-row {
	border-top: 2px solid;
}
</style>

<%
	@SuppressWarnings("unchecked")
	List<AdvChangeHistory> history = request.getAttribute("history") != null ? (List<AdvChangeHistory>) request.getAttribute("history") : new ArrayList<AdvChangeHistory>();
%>

<section class="content">
	<div class="row">
		<form:form modelAttribute="advertDto" id="formData"
			action="${pageContext.request.contextPath }/adv/${advertDto.advertisement.id == null ? 'add' : 'update' }?${_csrf.parameterName }=${_csrf.token}"
			method="POST"
			enctype="multipart/form-data">
			<form:hidden path="advertisement.id" />
			<form:hidden path="advertisement.publishedId" id="publishedId" />
			<form:hidden path="advertisement.createdDate" id="createdDate" />
			
			<input type="hidden" id="allowEdit" value="${advertDto.advertisement.allowEdit }">
			<div class="col-md-9">
				<div class="box box-info">
					<div class="box-header with-border">
						<div class="pull-left">
							<h3 class="box-title">${advertDto.advertisement.id == null ? 'Tạo Mới Thông Tin Bảng Quảng Cáo' : (advertDto.advertisement.allowEdit ? 'Cập Nhật Thông Tin Bảng Quảng Cáo' : 'Thông Tin Bảng Quảng Cáo') }</h3>
							<c:if test="${errorMsg != null }">
								<br/>
								<br/>
								<p class="error">${errorMsg }</p>
							</c:if>
						</div>
						<c:if test="${history.size() > 0 }">
						<security:authorize access="hasAnyRole('ROLE_ADMIN')">
						<div class="pull-right">
							<button type="button" title="Lịch sử nhập liệu" class="btn btn-primary pull-right" data-toggle="modal" data-target="#modal-logs">...</button>
						</div>
						</security:authorize>
						</c:if>
					</div>
					<!-- /.box-header -->
					<!-- form start -->
					<div class="form-horizontal">
						<div class="box-body">
							<div class="form-group">
								<label for="code" class="col-md-3 control-label">Mã</label>
								<div class="col-md-3">
									<form:input readonly="true" type="text" path="advertisement.code"
										class="form-control" id="code" placeholder="Tự động tạo" />
								</div>
								<c:if test="${not empty advertDto.advertisement.updatedDate }">
								<label for="updatedDate" class="col-md-3 control-label">Ngày tạo</label>
								<div class="col-md-3">
									<fmt:formatDate value="${advertDto.advertisement.updatedDate}" type="date" pattern="dd/MM/yyyy" var="updatedDate"/>
									<fmt:formatDate value="${advertDto.advertisement.createdDate}" type="date" pattern="dd/MM/yyyy" var="createdDate"/>
									<form:input title="Ngày cập nhật: ${updatedDate }" readonly="true" type="text" path="advertisement.updatedDate" value="${createdDate }"
										class="form-control" id="createdDate" placeholder="Ngày cập nhật" />
								</div>
								</c:if>
							</div>
							<div class="form-group">
								<label for="title" class="col-md-3 control-label">Tiêu đề</label>
								<div class="col-md-9">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.title }">
										<form:input type="text" path="advertisement.title"
											class="form-control custom-form-control" id="title" placeholder="Nhập tiêu đề" />
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.title }">
										<input type="text" class="form-control" id="title" placeholder="#####" readonly />
										<form:hidden path="advertisement.title"/>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label for="houseNo" class="col-md-3 control-label">Số nhà</label>
								<div class="col-md-9">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.houseNo || advertDto.advertisement.houseNo eq 'Số ' || advertDto.advertisement.houseNo eq 'Số' }">
										<form:input path="advertisement.houseNo" type="text"
											class="form-control" id="houseNo" placeholder="Nhập số nhà" />
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.houseNo && advertDto.advertisement.houseNo ne 'Số ' && advertDto.advertisement.houseNo ne 'Số' }">
										<input type="text" class="form-control" id="houseNo" placeholder="#####" readonly />
										<form:hidden path="advertisement.houseNo"/>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label for="street" class="col-md-3 control-label">Tên đường</label>
								<div class="col-md-9">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.street || advertDto.advertisement.street eq 'Đường ' || advertDto.advertisement.street eq 'Đường'}">
										<form:input path="advertisement.street" type="text"
											class="form-control" id="street" placeholder="Nhập tên đường" />
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.street && advertDto.advertisement.street ne 'Đường ' && advertDto.advertisement.street ne 'Đường'}">
										<input type="text" class="form-control" id="street" placeholder="#####" readonly />
										<form:hidden path="advertisement.street"/>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label for="ward" class="col-md-3 control-label">Phường</label>
								<div class="col-md-9">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.ward }">
										<form:input cssClass="form-control" path="advertisement.ward" id="ward"
											placeholder="Nhập tên phường/xã" />
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.ward }">
										<input type="text" class="form-control" id="ward" placeholder="#####" readonly />
										<form:hidden path="advertisement.ward"/>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label for="district" class="col-md-3 control-label">Quận</label>
								<div class="col-md-9">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.district }">
										<form:input cssClass="form-control"
											path="advertisement.district" id="district"
											placeholder="Nhập tên quận/huyện" />
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.district }">
										<input type="text" class="form-control" id="district" placeholder="#####" readonly />
										<form:hidden path="advertisement.district"/>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label for="province" class="col-md-3 control-label">Tỉnh</label>
								<div class="col-md-9">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.provinceCode }">
										<form:select id="province" cssClass="form-control select2" path="advertisement.provinceCode">
											<form:option value="">Chọn tỉnh</form:option>
											<c:forEach items="${provinces }" var="province">
											<form:option name="${province.name }" value="${province.code }">${province.name }</form:option>
											</c:forEach>
										</form:select>
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.provinceCode }">
										<input type="text" class="form-control" id="provinceCode" placeholder="#####" readonly />
										<form:hidden path="advertisement.provinceCode"/>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label for="coordinates" class="col-md-3 control-label">Toạ độ</label>
								<div class="col-md-9">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.map }">
										<form:input path="advertisement.map" type="text" onchange="initialize()"
											class="form-control" id="coordinates" name="coordinates" placeholder="Nhập toạ độ (VD: 123.2334, 234.2343)" />
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.map }">
										<input type="text" class="form-control" placeholder="#####" readonly />
										<form:hidden path="advertisement.map"/>
									</c:if>
								</div>
							</div>
							<security:authorize access="hasRole('ROLE_ADMIN')">
							<div class="form-group">
								<label for="price" class="col-md-3 control-label">Đơn giá</label>
								<div class="col-md-9">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.price }">
										<form:input path="advertisement.price" type="text"
										class="form-control" id="price" name="price" placeholder="Nhập đơn giá (USD/năm)" />
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.price }">
										<input type="text" class="form-control" id="price" placeholder="#####" readonly />
										<form:hidden path="advertisement.map"/>
									</c:if>
								</div>
							</div>
							</security:authorize>
							<div class="form-group">
								<label for="heightSize" class="col-md-3 control-label">Kích thước</label>
								<div class="col-md-2">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.heightSize || advertDto.advertisement.heightSize eq ' m' || advertDto.advertisement.heightSize eq 'm' }">
										<form:input path="advertisement.heightSize" type="text"
											class="form-control" id="heightSize" placeholder="Chiều cao" />
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.heightSize && advertDto.advertisement.heightSize ne ' m' && advertDto.advertisement.heightSize ne 'm' }">
										<input type="text" class="form-control" id="heightSize" placeholder="#####" readonly />
										<form:hidden path="advertisement.heightSize"/>
									</c:if>
								</div>
								<div class="col-md-1" style="text-align: center;">
									<label class="control-label">x</label>
								</div>
								<div class="col-md-2">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.widthSize || advertDto.advertisement.widthSize eq 'm' || advertDto.advertisement.widthSize eq ' m' }">
										<form:input path="advertisement.widthSize" type="text"
											class="form-control" id="widthSize" placeholder="Chiều rộng" />
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.widthSize && advertDto.advertisement.widthSize ne 'm' && advertDto.advertisement.widthSize ne ' m' }">
										<input type="text" class="form-control" id="widthSize" placeholder="#####" readonly />
										<form:hidden path="advertisement.widthSize"/>
									</c:if>
								</div>
								<div class="col-md-2" style="text-align: center;">
									<label class="control-label">Số lượng</label>
								</div>
								<div class="col-md-2">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.amount }">
										<form:input path="advertisement.amount" type="text"
											class="form-control" id="amount" placeholder="Số lượng" />
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.amount }">
										<input type="text" class="form-control" id="amount" placeholder="#####" readonly />
										<form:hidden path="advertisement.amount"/>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label for="views" class="col-md-3 control-label">Tầm nhìn</label>
								<div class="col-md-9">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.views }">
										<form:input path="advertisement.views" class="form-control"
											id="views" style="resize: none;" placeholder="Nhập tầm nhìn" />
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.views }">
										<input type="text" class="form-control" id="views" placeholder="#####" readonly />
										<form:hidden path="advertisement.views"/>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label for="flow" class="col-md-3 control-label">Lưu lượng</label>
								<div class="col-md-9">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.flow }">
										<form:input type="number" path="advertisement.flow" class="form-control"
											id="flow" style="resize: none;" placeholder="Nhập số lượng người/ngày" />
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.flow }">
										<input type="text" class="form-control" id="flow" placeholder="#####" readonly />
										<form:hidden path="advertisement.flow"/>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label for="implTime" class="col-md-3 control-label">Thời gian thực hiện</label>
								<div class="col-md-9">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.implTime || advertDto.advertisement.implTime eq '20'}">
										<form:input type="number" path="advertisement.implTime" class="form-control"
											id="implTime" style="resize: none;" placeholder="Nhập số ngày thực hiện" />
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.implTime && advertDto.advertisement.implTime ne '20' }">
										<input type="text" class="form-control" id="implTime" placeholder="#####" readonly />
										<form:hidden path="advertisement.implTime"/>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label for="implForm" class="col-md-3 control-label">Hình thức thực hiện</label>
								<div class="col-md-9">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.implForm || advertDto.advertisement.implForm eq 'in bạt hiflex 720 DPI'}">
										<form:input path="advertisement.implForm" class="form-control"
											id="implForm" style="resize: none;" placeholder="Nhập hình thức thực hiện" />
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.implForm && advertDto.advertisement.implForm ne 'in bạt hiflex 720 DPI'}">
										<input type="text" class="form-control" id="implForm" placeholder="#####" readonly />
										<form:hidden path="advertisement.implForm"/>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label for="lightSystem" class="col-md-3 control-label">Hệ thống chiếu sáng</label>
								<div class="col-md-9">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.lightSystem }">
										<form:input path="advertisement.lightSystem" class="form-control"
											id="lightSystem" style="resize: none;" placeholder="Nhập hệ thống chiếu sáng" />
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.lightSystem }">
										<input type="text" class="form-control" id="lightSystem" placeholder="#####" readonly />
										<form:hidden path="advertisement.lightSystem"/>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label for="type" class="col-md-3 control-label">Loại hình</label>
								<div class="col-md-9">
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.type }">
									<form:select path="advertisement.type" cssClass="form-control" id="type">
										<form:option value="">Chọn loại hình</form:option>
										<form:option value="Trụ pano Quảng cáo">Trụ pano Quảng cáo</form:option>
										<form:option value="Trụ pano Quảng cáo ngoài trời">Trụ pano Quảng cáo ngoài trời</form:option>
										<form:option value="Trụ pano Quảng cáo ngoài trời 1 mặt">Trụ pano Quảng cáo ngoài trời 1 mặt</form:option>
										<form:option value="Trụ pano Quảng cáo ngoài trời 2 mặt">Trụ pano Quảng cáo ngoài trời 2 mặt</form:option>
										<form:option value="Trụ pano Quảng cáo ngoài trời 3 mặt">Trụ pano Quảng cáo ngoài trời 3 mặt</form:option>
										<form:option value="Trụ pano Quảng cáo ngoài trời 4 mặt">Trụ pano Quảng cáo ngoài trời 4 mặt</form:option>
										<form:option value="Billboard Quảng cáo">Billboard Quảng cáo</form:option>
										<form:option value="Billboard Quảng cáo ngoài trời">Billboard Quảng cáo ngoài trời</form:option>
										<form:option value="Billboard Quảng cáo ngoài trời 1 mặt">Billboard Quảng cáo ngoài trời 1 mặt</form:option>
										<form:option value="Billboard Quảng cáo ngoài trời 2 mặt">Billboard Quảng cáo ngoài trời 2 mặt</form:option>
										<form:option value="Billboard Quảng cáo ngoài trời 3 mặt">Billboard Quảng cáo ngoài trời 3 mặt</form:option>
										<form:option value="Billboard Quảng cáo ngoài trời 4 mặt">Billboard Quảng cáo ngoài trời 4 mặt</form:option>
										<form:option value="Billboard ốp tường">Billboard ốp tường</form:option>
									</form:select>
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.type }">
										<input type="text" class="form-control" id="type" placeholder="#####" readonly />
										<form:hidden path="advertisement.type"/>
									</c:if>
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
									<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.describe }">
										<form:textarea path="advertisement.describe" class="form-control"
											id="describe" placeholder="Nhập mô tả" />
									</c:if>
									<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.describe }">
										<textarea class="form-control" id="describe" placeholder="#####" readonly></textarea>
										<form:hidden path="advertisement.describe"/>
									</c:if>
								</div>
							</div>

							<div class="row">
								<div class="col-md-6">
									<div class="box box-info" style="z-index: 99">
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
														<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.ownerPhone }">
															<form:input path="advertisement.ownerPhone" type="text"
																class="form-control" id="ownerPhone"
																placeholder="Số điện thoại" />
														</c:if>
														<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.ownerPhone }">
															<input type="text" class="form-control" id="ownerPhone" placeholder="#####" readonly />
															<form:hidden path="advertisement.ownerPhone"/>
														</c:if>
													</div>
												</div>
												<div class="form-group">
													<label for="ownerEmail" class="col-md-3 control-label">Email</label>
													<div class="col-md-9">
														<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.ownerEmail }">
															<form:input path="advertisement.ownerEmail" type="text"
																class="form-control" id="ownerEmail" placeholder="Email" />
														</c:if>
														<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.ownerEmail }">
															<input type="text" class="form-control" id="ownerEmail" placeholder="#####" readonly />
															<form:hidden path="advertisement.ownerEmail"/>
														</c:if>
													</div>
												</div>
												<div class="form-group">
													<label for="ownerPrice" class="col-md-3 control-label">Giá</label>
													<div class="col-md-9">
														<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.ownerPrice }">
															<form:input path="advertisement.ownerPrice" type="text"
																class="form-control" id="ownerPrice"
																placeholder="Giá thuê" />
														</c:if>
														<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.ownerPrice }">
															<input type="text" class="form-control" id="ownerPrice" placeholder="#####" readonly />
															<form:hidden path="advertisement.ownerPrice"/>
														</c:if>
													</div>
												</div>
												<div class="form-group">
													<label for="ownerContactPerson"
														class="col-md-3 control-label">NLH</label>
													<div class="col-md-9">
														<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.ownerContactPerson }">
															<form:input path="advertisement.ownerContactPerson"
																type="text" class="form-control" id="ownerContactPerson"
																placeholder="Người liên hệ" />
														</c:if>
														<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.ownerContactPerson }">
															<input type="text" class="form-control" id="ownerContactPerson" placeholder="#####" readonly />
															<form:hidden path="advertisement.ownerContactPerson"/>
														</c:if>
													</div>
												</div>
												<div class="form-group">
													<label for="ownerStartDate"
														class="col-md-3 control-label">Bắt đầu</label>
													<div class="col-md-9">
														<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.ownerStartDate }">
															<fmt:formatDate value="${advertDto.advertisement.ownerStartDate}" type="date" pattern="dd/MM/yyyy" var="ownerStartDate"/>
															<form:input path="advertisement.ownerStartDate" value="${ownerStartDate }"
																placeholder="dd/mm/yyyy" cssClass="datepicker form-control" id="ownerStartDate" />
														</c:if>
														<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.ownerStartDate }">
															<input type="text" class="form-control" id="ownerStartDate" placeholder="#####" readonly />
															<form:hidden path="advertisement.ownerStartDate"/>
														</c:if>
													</div>
												</div>
												<div class="form-group">
													<label for="ownerEndDate"
														class="col-md-3 control-label">Kết thúc</label>
													<div class="col-md-9">
														<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.ownerEndDate }">
															<fmt:formatDate value="${advertDto.advertisement.ownerEndDate}" type="date" pattern="dd/MM/yyyy" var="ownerEndDate"/>
															<form:input path="advertisement.ownerEndDate" value="${ownerEndDate }"
																placeholder="dd/mm/yyyy" cssClass="datepicker form-control" id="ownerEndDate" />
														</c:if>
														<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.ownerEndDate }">
															<input type="text" class="form-control" id="ownerEndDate" placeholder="#####" readonly />
															<form:hidden path="advertisement.ownerEndDate"/>
														</c:if>
													</div>
												</div>
												<div class="form-group">
													<label for="ownerNote" class="col-md-3 control-label">Ghi chú</label>
													<div class="col-md-9">
														<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.ownerNote }">
															<form:textarea path="advertisement.ownerNote" class="form-control"
																id="ownerNote" placeholder="Nhập ghi chú" />
														</c:if>
														<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.ownerNote }">
															<textarea class="form-control" id="ownerNote" placeholder="#####" readonly></textarea>
															<form:hidden path="advertisement.ownerNote"/>
														</c:if>
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
														<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.advCompName }">
															<form:input path="advertisement.advCompName" type="text"
																class="form-control" id="advCompName"
																placeholder="Tên công ty" />
														</c:if>
														<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.advCompName }">
															<input type="text" class="form-control" id="advCompName" placeholder="#####" readonly />
															<form:hidden path="advertisement.advCompName"/>
														</c:if>
													</div>
												</div>
												<div class="form-group">
													<label for="advCompPhone" class="col-md-3 control-label">Phone</label>
													<div class="col-md-9">
														<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.advCompPhone }">
															<form:input path="advertisement.advCompPhone" type="text"
																class="form-control" id="advCompPhone"
																placeholder="Số điện thoại" />
														</c:if>
														<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.advCompPhone }">
															<input type="text" class="form-control" id="advCompPhone" placeholder="#####" readonly />
															<form:hidden path="advertisement.advCompPhone"/>
														</c:if>
													</div>
												</div>
												<div class="form-group">
													<label for="advCompEmail" class="col-md-3 control-label">Email</label>
													<div class="col-md-9">
														<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.advCompEmail }">
														<form:input path="advertisement.advCompEmail" type="text"
															class="form-control" id="advCompEmail"
															placeholder="Email" />
														</c:if>
														<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.advCompEmail }">
															<input type="text" class="form-control" id="advCompEmail" placeholder="#####" readonly />
															<form:hidden path="advertisement.advCompEmail"/>
														</c:if>
													</div>
												</div>
												<div class="form-group">
													<label for="advCompPrice" class="col-md-3 control-label">Giá</label>
													<div class="col-md-9">
														<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.advCompPrice }">
															<form:input path="advertisement.advCompPrice" type="text"
																class="form-control" id="advCompPrice"
																placeholder="Giá thuê" />
														</c:if>
														<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.advCompPrice }">
															<input type="text" class="form-control" id="advCompPrice" placeholder="#####" readonly />
															<form:hidden path="advertisement.advCompPrice"/>
														</c:if>
													</div>
												</div>
												<div class="form-group">
													<label for="advCompContactPerson"
														class="col-md-3 control-label">NLH</label>
													<div class="col-md-9">
														<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.advCompContactPerson }">
															<form:input path="advertisement.advCompContactPerson"
																type="text" class="form-control"
																id="advCompContactPerson" placeholder="Người liên hệ" />
														</c:if>
														<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.advCompContactPerson }">
															<input type="text" class="form-control" id="advCompContactPerson" placeholder="#####" readonly />
															<form:hidden path="advertisement.advCompContactPerson"/>
														</c:if>
													</div>
												</div>
												<div class="form-group">
													<label for="advCompStartDate"
														class="col-md-3 control-label">Bắt đầu</label>
													<div class="col-md-9">
														<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.advCompEndDate }">
															<fmt:formatDate value="${advertDto.advertisement.advCompStartDate}" type="date" pattern="dd/MM/yyyy" var="advCompStartDate"/>
															<form:input path="advertisement.advCompStartDate" value="${advCompStartDate }"
																placeholder="dd/mm/yyyy" cssClass="datepicker form-control" id="advCompStartDate" />
														</c:if>
														<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.advCompEndDate }">
															<input type="text" class="form-control" id="advCompStartDate" placeholder="#####" readonly />
															<form:hidden path="advertisement.advCompStartDate"/>
														</c:if>
													</div>
												</div>
												<div class="form-group">
													<label for="advCompEndDate"
														class="col-md-3 control-label">Kết thúc</label>
													<div class="col-md-9">
														<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.advCompEndDate }">
															<fmt:formatDate value="${advertDto.advertisement.advCompEndDate}" type="date" pattern="dd/MM/yyyy" var="advCompEndDate"/>
															<form:input path="advertisement.advCompEndDate" value="${advCompEndDate }"
																placeholder="dd/mm/yyyy" cssClass="datepicker form-control" id="advCompEndDate" />
														</c:if>
														<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.advCompEndDate }">
															<input type="text" class="form-control" id="advCompEndDate" placeholder="#####" readonly />
															<form:hidden path="advertisement.advCompEndDate"/>
														</c:if>
													</div>
												</div>
												<div class="form-group">
													<label for="advCompNote" class="col-md-3 control-label">Ghi chú</label>
													<div class="col-md-9">
														<c:if test="${ advertDto.advertisement.belongCurrentUser || empty advertDto.advertisement.advCompNote }">
															<form:textarea path="advertisement.advCompNote" class="form-control"
																id="advCompNote" placeholder="Nhập ghi chú" />
														</c:if>
														<c:if test="${ !advertDto.advertisement.belongCurrentUser && not empty advertDto.advertisement.advCompNote }">
															<textarea class="form-control" id="advCompNote" placeholder="#####" readonly></textarea>
															<form:hidden path="advertisement.advCompNote"/>
														</c:if>
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
							<c:if test="${not empty errorMsg }">
								<input type="hidden" name="ignoreError" value="true"/>
							</c:if>
							<button type="submit" ${!advertDto.advertisement.allowEdit ? 'disabled' : '' } class="btn btn-info pull-right">${advertDto.advertisement.id == null ? 'Thêm' : 'Cập Nhật' }</button>
							
							<security:authorize
								access="hasAnyRole('ROLE_ADMIN')">
							<c:if test="${not empty advertDto.advertisement.id }">
								<button style="margin-right: 5px;" 
									onclick="publish()" 
									type="button" ${!advertDto.advertisement.allowEdit ? 'disabled' : '' } 
									class="btn btn-info pull-right">${advertDto.advertisement.publishedId > 0 ? 'Tái Xuất Bản' : 'Xuất Bản' }</button>
								<c:if test="${advertDto.advertisement.publishedId > 0 }">
									<button style="margin-right: 5px;" 
										onclick="unpublish()" 
										type="button" ${!advertDto.advertisement.allowEdit ? 'disabled' : '' } 
										class="btn btn-info pull-right">Huỷ Xuất Bản</button>
								</c:if>
							</c:if>
							</security:authorize>
						</div>
						<!-- /.box-footer -->
					</div>
				</div>
			</div>

			<div class="col-md-3" id="images">
				<div id="preview">
				<%
					AdvertisementDTO advertDto = (AdvertisementDTO) request.getAttribute("advertDto");
					List<AdvImage> images = advertDto.getAdvertisement().getAdvImages();
					int numOfImagesAvailable = images != null ? images.size() : 0;
					int mapIndex = -1;
					AdvImage advImage = null;
					for(int i = 0; i < numOfImagesAvailable; i++) {
						advImage = images.get(i);
						if(!advImage.isMap()) {
				%>
						<div class="preview-item">
							<input type="checkbox" onchange="addImageToPublish(this)" data="${pageContext.request.contextPath }/resources/images?url=<%= advImage.getUrl() %>">
							<c:if test="${advertDto.advertisement.belongCurrentUser }">
								<div class="close" title="Xoá" onclick="deleteImage(this)">X</div>
							</c:if>
							<input type="hidden" name="prevImages[<%= i %>]" value="<%= advImage.getId() %>"/>
							<img class="img-thumbnail" data=""
		 						src="${pageContext.request.contextPath }/resources/images?url=<%= advImage.getUrl() %>"
		 						alt="<%= advImage.getName() %>" name="<%= advImage.isMap() ? "map" : "" %>"></img>
		 					<input type="file" onchange="previewImages(this)" accept="image/gif,image/jpeg,image/png,.heic" name="files" class="form-control"/>
						</div>
				<%
						} else {
							mapIndex = i;
						}
					}
				%>
					<div id="new-preview"></div>
					<input type="file" multiple="multiple" onchange="previewImages(this, true)" accept="image/gif,image/jpeg,image/png,.heic" name="files" class="form-control"/>
				</div>
				
				<div id="imgMap">
					<br/>
					<b>Map</b>
					<div id="imgMap-preview">
					<%
						if(mapIndex != -1) {
							advImage = images.get(mapIndex);
					%>
						<div class="preview-item">
							<c:if test="${advertDto.advertisement.belongCurrentUser }">
								<div class="close" title="Xoá" onclick="deleteImage(this)">X</div>
							</c:if>
							<input type="hidden" name="prevImages[<%= mapIndex %>]" value="<%= advImage.getId() %>"/>
							<img class="img-thumbnail" id="mapImage"
		 						src="${pageContext.request.contextPath }/resources/images?url=<%= advImage.getUrl() %>"
		 						alt="<%= advImage.getName() %>" name="<%= advImage.isMap() ? "map" : "" %>"></img>
		 					<c:if test="${advertDto.advertisement.belongCurrentUser }">
		 						<input type="file" accept="image/gif,image/jpeg,image/png,.heic" class="form-control" name="map" onchange="previewImages(this, false)">
		 					</c:if>
						</div>
		 			<%
						} else {
					%>
					</div>
					<input type="file" accept="image/gif,image/jpeg,image/png,.heic" class="form-control" name="map" onchange="previewImages(this, true, true)">
					<%
						}
		 			%>
				</div>

				<script type="text/javascript">
					function previewImages(input, isNew, isMap) {
						$(input).parent().find('input[type=checkbox]').remove();
						if (input.files && input.files[0] && !isNew) {
						    var reader = new FileReader();

						    reader.onload = function(e) {
						    	$(input).prev().attr('src', e.target.result);
						    	if($(input).parent().find('.close').length === 0) { 
						    		$(input).parent().prepend('<input type="checkbox" data><div class="close" title="Xoá" onclick="deleteImage(this)">X</div>');
						    	}
								$(input).parent().find('input[type=hidden]').remove();
						    }
						    reader.readAsDataURL(input.files[0]);
						} else {
							var previewElement = $('#new-preview');
							if(isMap) {
								previewElement = $('#imgMap-preview');
							}
							previewElement.empty();
							
							for(var i = 0; i < input.files.length; i++) {
								var reader = new FileReader();

							    reader.onload = function(e) {
							    	previewElement.append(
							    		'<div class="preview-item">' +
											'<img class="img-thumbnail" ' + (isMap ? 'name="map"' : '')  + ' src="'+ e.target.result +'" alt="${advImage.name }"></img>' +
										'</div>'
									);
							    }
							    reader.readAsDataURL(input.files[i]);
							}
						}
					}
					
					function deleteImage(element) {
						$(element).parent().find('input[type=checkbox]').remove();
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

<div class="modal modal-default" id="modal-logs" style="display: none;">
	<div class="modal-dialog" style="width: 95% !important;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close" title="Đóng">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title">Lịch Sử Nhập Liệu</h4>
			</div>
			<div class="modal-body" style="text-align: left; padding: 10px;">
		          <div class="box">
		            <!-- /.box-header -->
		            <div class="box-body table-responsive no-padding">
		              <table class="table" style="border: 1px solid">
		              	<thead>
		              		<tr>
			                	<th>Ngày Cập Nhật</th>
			                	<th>Thông Tin Cũ</th>
			                	<th>Thông Tin Mới</th>
			                	<th>Người Sửa</th>
			                	<th>Người Tạo</th>
			                </tr>
		              	</thead>
		                <tbody>
		                	<% for(int i = 0; i < history.size() - 1; i += 2) {
		                		AdvChangeHistory preHistory = history.get(i);
		                		AdvChangeHistory currHistory = history.get(i + 1);
		                		
		                	%>
		                	<%! %>
		                	<fmt:formatDate value="<%= currHistory.getUpdatedDate() %>" type="date" pattern="HH:mm:ss dd/MM/yyyy" var="updatedDate"/>

			                <tr class="tbl-row">
			                	<td rowspan="<%= currHistory.getNumOfChanges() %>" style="border: 1px solid">${updatedDate }</td>
				                <td title="<%= preHistory.getId() %>">Địa chỉ: 
				                <%= 
				                	preHistory.getHouseNo() + ", " +
				                	preHistory.getStreet() + ", " +
				                	preHistory.getWard() + ", " +
				                	preHistory.getDistrict() + ", " +
				                	preHistory.getProvince()
				                %>
				                </td>
				                <td title="<%= currHistory.getId() %>">Địa chỉ: 
				                <%=
				                	currHistory.getHouseNo() + ", " +
				                	currHistory.getStreet() + ", " +
				                	currHistory.getWard() + ", " +
				                	currHistory.getDistrict() + ", " +
				                	currHistory.getProvince()
				                %>
				                </td>
				                
				                <td rowspan="<%= currHistory.getNumOfChanges() %>" style="border: 1px solid"><%= currHistory.getUpdatedBy().getName() %></td>
				                <td rowspan="<%= currHistory.getNumOfChanges() %>" style="border: 1px solid"><%= currHistory.getCreatedBy().getName() %></td>
			                </tr>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getMap()) || !StringUtils.isEmptyOrNull(currHistory.getMap())) {%>
			                <tr>
			                	<td>Toạ độ: <%= preHistory.getMap() %></td>
			                	<td>Toạ độ: <%= currHistory.getMap() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getTitle()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getTitle())) {%>
			                <tr>
			                	<td>Tiêu đề: <%= preHistory.getTitle() %></td>
			                	<td>Tiêu đề: <%= currHistory.getTitle() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getWidthSize()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getWidthSize())) {%>
			                <tr>
			                	<td>Chiều rộng: <%= preHistory.getWidthSize() %></td>
			                	<td>Chiều rộng: <%= currHistory.getWidthSize() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getHeightSize()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getHeightSize())) {%>
			                <tr>
			                	<td>Chiều cao: <%= preHistory.getHeightSize() %></td>
			                	<td>Chiều cao: <%= currHistory.getHeightSize() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getAmount()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getAmount())) {%>
			                <tr>
			                	<td>Số lượng: <%= preHistory.getAmount() %></td>
			                	<td>Số lượng: <%= currHistory.getAmount() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getViews()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getViews())) {%>
			                <tr>
			                	<td>Tầm nhìn: <%= preHistory.getViews() %></td>
			                	<td>Tầm nhìn: <%= currHistory.getViews() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getFlow()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getFlow())) {%>
			                <tr>
			                	<td>Lưu lượng: <%= preHistory.getFlow() %></td>
			                	<td>Lưu lượng: <%= currHistory.getFlow() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getImplTime()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getImplTime())) {%>
			                <tr>
			                	<td>Thời gian thực hiện: <%= preHistory.getImplTime() %></td>
			                	<td>Thời gian thực hiện: <%= currHistory.getImplTime() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getImplForm()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getImplForm())) {%>
			                <tr>
			                	<td>Hình thực thực hiện: <%= preHistory.getImplForm() %></td>
			                	<td>Hình thức thực hiện: <%= currHistory.getImplForm() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getLightSystem()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getLightSystem())) {%>
			                <tr>
			                	<td>Hệ thống chiếu sáng: <%= preHistory.getLightSystem() %></td>
			                	<td>Hệ thống chiếu sáng: <%= currHistory.getLightSystem() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getPrice()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getPrice())) {%>
			                <tr>
			                	<td>Giá: <%= preHistory.getPrice() %></td>
			                	<td>Giá: <%= currHistory.getPrice() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getType()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getType())) {%>
			                <tr>
			                	<td>Loại: <%= preHistory.getType() %></td>
			                	<td>Loại: <%= currHistory.getType() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getDescribe()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getDescribe())) {%>
			                <tr>
			                	<td>Mô tả: <%= preHistory.getDescribe() %></td>
			                	<td>Mô tả: <%= currHistory.getDescribe() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getOwnerPhone()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getOwnerPhone())) {%>
			                <tr>
			                	<td>SDT (chủ nhà): <%= preHistory.getOwnerPhone() %></td>
			                	<td>SDT (chủ nhà): <%= currHistory.getOwnerPhone() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getOwnerEmail()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getOwnerEmail())) {%>
			                <tr>
			                	<td>Email (chủ nhà): <%= preHistory.getOwnerEmail() %></td>
			                	<td>Email (chủ nhà): <%= currHistory.getOwnerEmail() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getOwnerPrice()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getOwnerPrice())) {%>
			                <tr>
			                	<td>Giá (chủ nhà): <%= preHistory.getOwnerPrice() %></td>
			                	<td>Giá (chủ nhà): <%= currHistory.getOwnerPrice() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getOwnerContactPerson()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getOwnerContactPerson())) {%>
			                <tr>
			                	<td>Người liên hệ (chủ nhà): <%= preHistory.getOwnerContactPerson() %></td>
			                	<td>Người liên hệ (chủ nhà): <%= currHistory.getOwnerContactPerson() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getOwnerStartDate()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getOwnerStartDate())) {%>
			                <tr>
			                	<td>Ngày bắt đầu (chủ nhà): <%= preHistory.getOwnerStartDate() %></td>
			                	<td>Ngày bắt đầu (chủ nhà): <%= currHistory.getOwnerStartDate() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getOwnerEndDate()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getOwnerEndDate())) {%>
			                <tr>
			                	<td>Ngày kết thúc (chủ nhà): <%= preHistory.getOwnerEndDate() %></td>
			                	<td>Ngày kết thúc (chủ nhà): <%= currHistory.getOwnerEndDate() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getOwnerNote()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getOwnerNote())) {%>
			                <tr>
			                	<td>Ghi chú (chủ nhà): <%= preHistory.getOwnerNote() %></td>
			                	<td>Ghi chú (chủ nhà): <%= currHistory.getOwnerNote() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getAdvCompName()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getAdvCompName())) {%>
			                <tr>
			                	<td>Tên công ty: <%= preHistory.getAdvCompName() %></td>
			                	<td>Tên công ty: <%= currHistory.getAdvCompName() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getAdvCompPhone()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getAdvCompPhone())) {%>
			                <tr>
			                	<td>SDT (công ty): <%= preHistory.getAdvCompPhone() %></td>
			                	<td>SDT (công ty): <%= currHistory.getAdvCompPhone() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getAdvCompEmail()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getAdvCompEmail())) {%>
			                <tr>
			                	<td>Email (công ty): <%= preHistory.getAdvCompEmail() %></td>
			                	<td>Email (công ty): <%= currHistory.getAdvCompEmail() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getAdvCompPrice()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getAdvCompPrice())) {%>
			                <tr>
			                	<td>Giá (công ty): <%= preHistory.getAdvCompPrice() %></td>
			                	<td>Giá (công ty): <%= currHistory.getAdvCompPrice() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getAdvCompContactPerson()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getAdvCompContactPerson())) {%>
			                <tr>
			                	<td>Người liên hệ (công ty): <%= preHistory.getAdvCompContactPerson() %></td>
			                	<td>Người liên hệ (công ty): <%= currHistory.getAdvCompContactPerson() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getAdvCompStartDate()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getAdvCompStartDate())) {%>
			                <tr>
			                	<td>Ngày bắt đầu (công ty): <%= preHistory.getAdvCompStartDate() %></td>
			                	<td>Ngày bắt đầu (công ty): <%= currHistory.getAdvCompStartDate() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getAdvCompEndDate()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getAdvCompEndDate())) {%>
			                <tr>
			                	<td>Ngày kết thúc (công ty): <%= preHistory.getAdvCompEndDate() %></td>
			                	<td>Ngày kết thúc (công ty): <%= currHistory.getAdvCompEndDate() %></td>
			                </tr>
			                <%} %>
			                
			                <% if(!StringUtils.isEmptyOrNull(preHistory.getAdvCompNote()) 
			                		|| !StringUtils.isEmptyOrNull(currHistory.getAdvCompNote())) {%>
			                <tr>
			                	<td>Ghi chú (công ty): <%= preHistory.getAdvCompNote() %></td>
			                	<td>Ghi chú (công ty): <%= currHistory.getAdvCompNote() %></td>
			                </tr>
			                <%} %>
			                
			                <% } %>
		              	</tbody>
		              </table>
		            </div>
		            <!-- /.box-body -->
		          </div>
		          <!-- /.box -->
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>

<script>
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
		
		$('.datepicker').datepicker({
			format: 'dd/mm/yyyy'
		});
	});
	
	// Images to publish
	var imageIndex = 1;
	function addImageToPublish(element) {
		if($(element).is(":checked")) {
			if(imageIndex === 1) {
				$(element).parent().find('img').attr('data', 'images');
			} else {
				if(imageIndex === 3) {
					imageIndex++;
				}
				$(element).parent().find('img').attr('data', 'image' + imageIndex);
			}
			imageIndex++;
		} else {
			$(element).parent().find('img').removeAttr('data');
			if(imageIndex === 4) {
				imageIndex--;
			}
			imageIndex--;
		}
	}
	
	function publish() {
		// Init data
		var location = $('#coordinates').val().split(", ");
		var lat = location[0], lng = location[1];
		
		var province = document.getElementById('province').options[document.getElementById('province').selectedIndex].text;
		
		var detail = "<p>Vị trí: " + $('#houseNo').val() + ", " + $('#street').val() 
			+ ", " + $('#ward').val() + ", " + $('#district').val() 
			+ ", " + province + "</p>";
		detail += "<p>Loại hình: " + $('#type').val() + "</p>";
		detail += "<p>Tầm nhìn: " + $('#views').val() + "</p>";
		detail += "<p>Kích thước: " + $('#heightSize').val() + " x " + $('#widthSize').val() + "</p>";
		detail += "<p>Mật độ: " + $('#flow').val() + " người/ngày</p>";
		detail += "<p>Hình thức thực hiện: " + $('#implForm').val() + "</p>";
		detail += "<p>Hệ thống chiếu sáng: " + $('#lightSystem').val() + "</p>";
		detail += "<p>Tình trạng: Đang chào bán</p>";
		detail += "<p>Đơn giá: Liên hệ để biết giá</p>";
		detail += "<br/><br/>" + $('#describe').val();
		
		var published = "<p>Vị trí: " + $('#houseNo').val() + ", " + $('#street').val() 
		+ ", " + $('#ward').val() + ", " + $('#district').val() 
		+ ", " + province + "</p>";
		published += "<p>Loại hình: " + $('#type').val() + "</p>";
		
		var data = {
				"title": $('#title').val() + " - " + $('#code').val(),
				"price": $('#price').val(),
				"description": '',
				"published": 0,
				"ordering": 0,
				"lat": lat,
				"long": lng,
				"detail": detail
			}
		
		// Prepare images to publish
		// Map
		if($('#mapImage') !== undefined && $('#mapImage').attr('src') !== undefined) {
			data['image3'] = "http://namlongadv.ddns.net:7070" + $('#mapImage').attr('src');
		} else {
			data['image3'] = null;
		}
		
		var images = $('#images img');
		if(images.length > 0) {
			for(var i = 0; i < images.length; i++) {
				if($(images[i]).attr('data') !== undefined && $(images[i]).attr('data') !== '') {
					data[$(images[i]).attr('data')] = "http://namlongadv.ddns.net:7070" + $(images[i]).attr('src');
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
				url: "https://billboardquangcao.com/api.php/firerox_jv_article/" + publishedId,
			}).done(function(msg) {
				// Update if it existed
				$.ajax({
		 			method: "GET",
		 			url: "https://billboardquangcao.com/api.php/firerox_jv_article/" + publishedId,
		 			data: data
		 		}).done(function(msg) {
		 			if(msg === 1) {
			 			alert("Tái xuất bản thành công!!!");
			 			$('#publishedId').attr("value", publishedId);
			 			$('#formData').submit();
		 			} else {
		 				alert("An error occurred. Please contact IT department to get support.")
		 			}
		 		});
			}).error(function(e) {
				// Create if it does not existed
				console.log(data);
				if(e.status === 404) {
			 		$.ajax({
			 			method: "POST",
			 			url: "https://billboardquangcao.com/api.php/firerox_jv_article",
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
		 			method: "GET",
		 			url: "https://billboardquangcao.com/api.php/firerox_jv_article/" + publishedId,
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

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<style>
table tr th {
	text-align: center;
	background: #99ddfc;
}

#province {
	width: 150px;
}

.select2-selection {
	border-radius: 0px !important;
}

.select2-selection--single, .select2-selection__rendered {
	margin-top: -7px !important;
	border-color: #d2d6de !important;
}
.select2-selection__arrow {
	top: -7px !important;
}
</style>

<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.css" />

<section class="content">
	<div class="row">
		<div class="col-md-12" id="accordion" role="tablist">
			<div class="box box-danger">
				<div class="box-header with-border" role="tab" id="headingOne">
					<h4 class="panel-title ${isSearch ? '' : 'collapsed' }" data-toggle="collapse"
						style="cursor: pointer;" data-parent="#accordion"
						href="#collapseOne" aria-expanded="${isSearch }"
						aria-controls="collapseOne">
						<a role="button"> Bộ Lọc </a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse ${isSearch ? 'in' : '' }"
					role="tabpanel" aria-labelledby="headingOne" aria-expanded="${isSearch }">
					<form id="filter-form" action="${pageContext.request.contextPath }/adv/search" method="get">
						<input type="hidden" name="size" value="${sessionScope.pageSize }"/>
						<div class="box-body">
							<div class="row">
								<div class="col-md-2 ui-widget">
									<input type="text" name="code" class="form-control" placeholder="Mã" value="${code }"/>
								</div>
								<div class="col-md-2 ui-widget">
									<input type="text" name="contactPerson" class="form-control" placeholder="Tên công ty" value="${contactPerson }"/>
								</div>
								<div class="col-md-2">
								    <input type="text" name="createdBy" class="form-control" value="${createdBy }" placeholder="Tạo bởi"/>
								</div>
								<div class="col-md-3">
									<input id="address" type="text" name="address" value="${address }" class="form-control" placeholder="Địa chỉ"/>
								</div>
								<div class="col-md-3">
								    <input readonly type="text" class="form-control" value="${daterange }" placeholder="Ngày tạo" name="daterange"/>
								</div>
							</div>
							<div class="row">
								<div class="col-md-3" style="margin-top: 10px;">
								    <select id="province" class="form-control select2 col-md-3" name="province">
										<option value="">Chọn tỉnh</option>
										<c:forEach items="${provinces }" var="province">
										<option ${requestScope.province eq province.code ? 'selected' : '' } value="${province.code }">${province.name }</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</div>
						<!-- /.box-body -->
						<div class="footer">
							<div class="box-footer">
								<input type="submit" class="btn btn-info" value="Lọc">
								<input type="button" id="reset-filter" class="btn btn-danger" value="Đặt lại">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12" style="margin-bottom: 5px;">
			<security:authorize
				access="hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')">
				<button id="exportExcel" id="exportExcel" class="btn btn-info pull-right">Xuất Excel</button>
			</security:authorize>
			<security:authorize
				access="hasAnyRole('ROLE_ADMIN', 'ROLE_BUSINESS')">
				<button id="exportPowerpoint" id="exportPowerpoint" class="btn btn-info pull-right" style="margin-right: 5px;">Xuất Powerpoint</button>
			</security:authorize>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="box">
				<form:form id="exportForm" enctype="multipart/form-data"
					name="exportForm" action="${pageContext.request.contextPath }/export/excel" 
					method="post" modelAttribute="advertWrapper">
				<input type="hidden" name="csrf" value="${_csrf.token}"/>
				<div class="box-header">
					<h3 class="box-title">Data</h3>
					
					<div class="pull-right">
						Hiển thị
						<select id="page-size" onchange="pageSizeChanged()">
							<option value="10" ${sessionScope.pageSize == 10 ? 'selected' : ''}>10</option>
							<option value="25" ${sessionScope.pageSize == 25 ? 'selected' : ''}>25</option>
							<option value="50" ${sessionScope.pageSize == 50 ? 'selected' : ''}>50</option>
							<option value="100" ${sessionScope.pageSize == 100 ? 'selected' : ''}>100</option>
							<option value="500" ${sessionScope.pageSize == 500 ? 'selected' : ''}>500</option>
							<option value="1000" ${sessionScope.pageSize == 1000 ? 'selected' : ''}>1000</option>
							<option value="5000" ${sessionScope.pageSize == 5000 ? 'selected' : ''}>5000</option>
							<option value="10000" ${sessionScope.pageSize == 10000 ? 'selected' : ''}>10000</option>
						</select>
						mục
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body" style="overflow: auto;">
					<table class="table table-bordered table-responsive"
						style="max-width: none !important; margin-right: 10px;">
						<thead>
							<tr>
								<th style="width: 8px; text-align: center;">
									<input type="checkbox" checked="checked" id="checkAll"/>
								</th>
								<th style="width: 8px;">#</th>
								<th style="width: 5%;">Mã</th>
								<th style="width: 20%;">Tiêu đề</th>
								<th style="width: 20%;">Địa chỉ</th>
								<th style="width: 20px;"></th>
								<th style="width: 13%;">Liên hệ</th>
								<th style="width: 10%;">SĐT</th>
								<th style="width: 50%;">Hình ảnh</th>
								<th style="width: 5%;"></th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${empty page.content }">
								<tr>
									<td colspan="21" style="text-align: center;">Không tìm thấy dữ liệu</td>
								</tr>
							</c:if>
							<c:forEach items="${pageContent }" var="adv" varStatus="loop">
								<tr>
									<td rowspan="2">
										<input type="checkbox" class="check" name="advs[${loop.index }].id" checked="checked" value="${adv.id }" />
										<input type="hidden" name="advs[${loop.index }].code" 
											value="${adv.code }"/>
										<input type="hidden" name="advs[${loop.index }].title" 
											value="${adv.title }"/>
											<input type="hidden" name="advs[${loop.index }].houseNo" 
											value="${adv.houseNo }"/>
										<input type="hidden" name="advs[${loop.index }].street"
											value="${adv.street }"/>
										<input type="hidden" name="advs[${loop.index }].ward"
											value="${adv.ward }"/>
											<input type="hidden" name="advs[${loop.index }].district"
											value="${adv.district }"/>
											<input type="hidden" name="advs[${loop.index }].province"
											value="${adv.province }"/>
											<input type="hidden" name="advs[${loop.index }].map"
											value="${adv.map }"/>
											<input type="hidden" name="advs[${loop.index }].widthSize"
											value="${adv.widthSize }"/>
											<input type="hidden" name="advs[${loop.index }].heightSize"
											value="${adv.heightSize }"/>
											<input type="hidden" name="advs[${loop.index }].describe"
											value="${adv.describe }"/>
											<input type="hidden" name="advs[${loop.index }].ownerPhone"
											value="${adv.ownerPhone }"/>
											<input type="hidden" name="advs[${loop.index }].ownerEmail"
											value="${adv.ownerEmail }"/>
											<input type="hidden" name="advs[${loop.index }].ownerPrice"
											value="${adv.ownerPrice }"/>
											<input type="hidden" name="advs[${loop.index }].ownerStartDate"
											value="${adv.ownerStartDate }"/>
											<input type="hidden" name="advs[${loop.index }].ownerEndDate"
											value="${adv.ownerEndDate }"/>
											<input type="hidden" name="advs[${loop.index }].ownerContactPerson"
											value="${adv.ownerContactPerson }"/>
											<input type="hidden" name="advs[${loop.index }].ownerNote"
											value="${adv.ownerNote }"/>
											
											<input type="hidden" name="advs[${loop.index }].advCompPhone"
											value="${adv.advCompPhone }"/>
											<input type="hidden" name="advs[${loop.index }].advCompEmail"
											value="${adv.advCompEmail }"/>
											<input type="hidden" name="advs[${loop.index }].advCompPrice"
											value="${adv.advCompPrice }"/>
											<input type="hidden" name="advs[${loop.index }].advCompStartDate"
											value="${adv.advCompEndDate }"/>
											<input type="hidden" name="advs[${loop.index }].advCompContactPerson"
											value="${adv.advCompContactPerson }"/>
											<input type="hidden" name="advs[${loop.index }].advCompName"
											value="${adv.advCompName }"/>
											<input type="hidden" name="advs[${loop.index }].advCompNote"
											value="${adv.advCompNote }"/>
											
											<input type="hidden" name="advs[${loop.index }].views"
											value="${adv.views }"/>
											<input type="hidden" name="advs[${loop.index }].flow"
											value="${adv.flow }"/>
											<input type="hidden" name="advs[${loop.index }].implTime"
											value="${adv.implTime }"/>
											<input type="hidden" name="advs[${loop.index }].implForm"
											value="${adv.implForm }"/>
											<input type="hidden" name="advs[${loop.index }].lightSystem"
											value="${adv.lightSystem }"/>
											<input type="hidden" name="advs[${loop.index }].price"
											value="${adv.price }"/>
									</td>
									<td rowspan="2">${loop.index + 1 }</td>
									<td rowspan="2">${adv.code }</td>
									<td rowspan="2">
										<a href="${pageContext.request.contextPath }/adv/${adv.id }">
											${adv.title }
										</a>
									</td>
									<td rowspan="2">${adv.houseNo }, ${adv.street }, ${adv.ward }, ${adv.district }, ${adv.province }</td>
									<td>TTCN</td>
									<td>
										${adv.ownerContactPerson }
									</td>
									<td>
										${adv.ownerPhone }
									</td>
									<td rowspan="2" class="imageSelector">
										<ul class="image-group">
											<c:forEach items="${adv.advImages }" var="advImage" varStatus="i">
										  	<li class="image-item">
										  	<input type="checkbox" checked="checked" name="advs[${loop.index }].advImages[${i.index }].id" value="${advImage.id }" id="cb${loop.index }-${i.index }" />
										    <label for="cb${loop.index }-${i.index }">
										    	<img class="image lazy" data-original="${pageContext.request.contextPath }/resources/images?url=${advImage.url }" />
										    	<button type="button" class="btn-preview-image" data-toggle="modal" data-target="#modal-success">Preview</button>
										    </label>
										  	<input type="hidden" name="advs[${loop.index }].advImages[${i.index }].name" value="${advImage.name }"/>
										  	<input type="hidden" name="advs[${loop.index }].advImages[${i.index }].url" value="${advImage.url }"/>
										  	</li>
										  	</c:forEach>
										</ul>
									</td>
									<td rowspan="2" class="action" style="text-align: center;">
										<c:if test="${adv.allowEdit }">
										<a class="delete" title="Xoá"
											href="${pageContext.request.contextPath }/adv/delete/${adv.id }">
											<i class="fa fa-fw fa-trash"></i>
										</a>
										<br /> 
										<a title="Xem" href="${pageContext.request.contextPath }/adv/${adv.id }">
											<i class="fa fa-fw fa-edit"></i>
										</a>
										</c:if>
									</td>
								</tr>
								<tr>
									<td>TTCTQC</td>
									<td>${adv.advCompName }</td>
									<td>${adv.advCompPhone }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					

				</div>
				<!-- /.box-body -->

				<div class="box-footer clearfix">
					<ul class="pagination pagination-sm no-margin pull-right">
						<c:set var="currentPage" value="${page.number }"></c:set>
						<c:set var="firstPrev" value="${page.number - 5 }"></c:set>
						<c:set var="lastPost" value="${page.number + 5 }"></c:set>
						
						<c:if test="${page.number > 0 }">
							<li class="">
								<a title="Trang đầu" href="${pageContext.request.contextPath }/adv/${isSearch ? 'search' : 'view' }?page=0&size=${page.size }&code=${code }&contactPerson=${contactPerson }&createdBy=${createdBy }&address=${address }&daterange=${daterange }">««</a>
							</li>
						</c:if>
						
						<c:if test="${firstPrev > 0 }">
							<li class="">
								<a title="Trang trước" href="${pageContext.request.contextPath }/adv/${isSearch ? 'search' : 'view' }?page=${page.number - 1 }&size=${page.size }&code=${code }&contactPerson=${contactPerson }&createdBy=${createdBy }&address=${address }&daterange=${daterange }">«</a>
							</li>
						</c:if>

						<c:forEach begin="${firstPrev > 0 ? firstPrev : 0 }" end="${lastPost > 0 && lastPost < page.totalPages - 1 ? lastPost : page.totalPages - 1 }"
							varStatus="loop">
							<li class="${loop.index == page.number ? 'active' : ''}"><a
								href="${pageContext.request.contextPath }/adv/${isSearch ? 'search' : 'view' }?page=${loop.index }&size=${page.size }&code=${code }&contactPerson=${contactPerson }&createdBy=${createdBy }&address=${address }&daterange=${daterange }">${loop.index }</a></li>
						</c:forEach>

						<c:if test="${lastPost < page.totalPages }">
							<li class=""><a title="Trang tiếp theo"
								href="${pageContext.request.contextPath }/adv/${isSearch ? 'search' : 'view' }?page=${page.number + 1 }&size=${page.size }&code=${code }&contactPerson=${contactPerson }&createdBy=${createdBy }&address=${address }&daterange=${daterange }">»</a></li>
						</c:if>
						
						<c:if test="${page.number < page.totalPages - 1 }">
							<li class=""><a title="Trang cuối"
								href="${pageContext.request.contextPath }/adv/${isSearch ? 'search' : 'view' }?page=${page.totalPages - 1 }&size=${page.size }&code=${code }&contactPerson=${contactPerson }&createdBy=${createdBy }&address=${address }&daterange=${daterange }">»»</a></li>
						</c:if>
					</ul>
				</div>
				
				</form:form>
			</div>
			<!-- /.box -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</section>

<script type="text/javascript" src="//cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="//cdn.jsdelivr.net/bootstrap.daterangepicker/2/daterangepicker.js"></script>
<script>
$(document).ready(function() {
	$(".select2").select2();
	
	$('.delete').click(function(e) {
		e.preventDefault();
		var answer = confirm("Bạn có chắc muốn xoá?");
		if(answer === true) {
			window.location.href = $(this).attr('href');
		}
	});
	
	// Address
	$("#address").autocomplete({
		source: "${pageContext.request.contextPath}/location",
	    minLength: 2,
	    select: function( event, ui ) {
	    	console.log("Selected: " + ui.item.value);
	    }
	});
	
	// Init default date filter
	var daterange = '${daterange}'.split(" - ");
	if(daterange.length < 2) {
		var today = new Date();
		daterange[0] = "1/1/2017";
		daterange[1] = (today.getMonth()+1) + "/" + today.getDate() + "/" + today.getFullYear();
	}
	
	// Reset filter value
	$('#reset-filter').click(function(e) {
		$('input[name=code]').val('');
		$('input[name=address]').val('');
		$('input[name=createdBy]').val('');
		$('input[name=daterange]').val(new Date(daterange[0]).toLocaleDateString() + " - " + new Date(daterange[1]).toLocaleDateString());
		$('input[name=contactPerson]').val('');
		$('select[name=province]').val('').trigger('change');
	});
	
	// Init datepicker
	$('input[name="daterange"]').daterangepicker({
		 opens: "left",
		 startDate: daterange[0],
		 endDate: daterange[1]
	});
	
	// Get users for compobox
	$.ajax({
        url : "${pageContext.request.contextPath}/users",
        type : "get",
        success : function (result){
        	$('input[name=createdBy]').autocomplete({
	            source: result,
	            minLength: 0,
	            scroll: true
	        }).focus(function() {
	            $(this).autocomplete("search", "");
	        });
        },
        error: function(e) {
        	console.log("Error: " + e);
        }
    });
	
	// Export actions
	$('#exportExcel').click(function(e) {
		var token = $('input[name=csrf]').val();
		$('form[name=exportForm]').attr('action', '${pageContext.request.contextPath }/export/excel?_csrf=' + token);
		$('form[name=exportForm]').submit();
	});
	$('#exportPowerpoint').click(function(e) {
		var token = $('input[name=csrf]').val();
		$('form[name=exportForm]').attr('action', '${pageContext.request.contextPath }/export/powerpoint?_csrf=' + token);
		$('form[name=exportForm]').submit();
	});
	
	// Check all
	$('#checkAll').click(function(e) {
		var check = $(this)[0].checked;
		var checkboxes = $('.check');
		for(var i = 0; i < checkboxes.length; i++) {
			$('.check')[i].checked = check;
		}
	});
	
	$('.check').click(function() {
		var checkboxes = $('.check');
		for(var i = 0; i < checkboxes.length; i++) {
			if($('.check')[i].checked === false) {
				$('#checkAll')[0].checked = false;
				return;
			}
		}
		$('#checkAll')[0].checked = true;
	});
})

function pageSizeChanged() {
	$('input[name=size]').val($('#page-size').val());
	$('form[id=filter-form]').submit();
}
</script>
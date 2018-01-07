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

.imageSelector ul {
  list-style-type: none;
}

.imageSelector li {
  display: inline-block;
}

.imageSelector input[type="checkbox"][id^="cb"] {
  display: none;
}

.imageSelector label {
  border: 1px solid #fff;
  padding: 5px;
  display: block;
  position: relative;
  margin: 10px;
  cursor: pointer;
}

.imageSelector label:before {
  background-color: white;
  color: white;
  content: " ";
  display: block;
  border-radius: 50%;
  border: 1px solid grey;
  position: absolute;
  top: -5px;
  left: -5px;
  width: 25px;
  height: 25px;
  text-align: center;
  line-height: 28px;
  transition-duration: 0.4s;
  transform: scale(0);
}

.imageSelector label img {
  heigh: 50px;
  width: 50px;
  transition-duration: 0.2s;
  transform-origin: 50% 50%;
}

.imageSelector :checked + label {
  border-color: #ddd;
}

.imageSelector :checked + label:before {
  content: "✓";
  background-color: green;
  transform: scale(1);
  z-index: 9;
  opacity: 0.8;
}

.imageSelector :checked + label img {
  transform: scale(0.9);
  box-shadow: 0 0 5px #333;
  z-index: -1;
}

.ui-autocomplete { 
            cursor:pointer; 
            height:120px; 
            overflow-y:scroll;
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
					<form action="${pageContext.request.contextPath }/adv/search" method="get">
						<div class="box-body">
							<div class="row">
								<div class="col-md-2 ui-widget">
									<input type="text" name="code" class="form-control" placeholder="Mã" value="${code }"/>
								</div>
								<div class="col-md-2 ui-widget">
									<input type="text" name="contactPerson" class="form-control" placeholder="NLH" value="${contactPerson }"/>
								</div>
								<div class="col-md-2">
								    <input type="text" name="createdBy" class="form-control" value="${createdBy }" placeholder="Tạo bởi"/>
								</div>
								<div class="col-md-3">
									<input id="address" type="text" name="address" value="${address }" class="form-control" placeholder="Địa chỉ"/>
								</div>
								<div class="col-md-3">
								    <input type="text" class="form-control" value="${daterange }" placeholder="Ngày tạo" name="daterange"/>
								</div>
							</div>
						</div>
						<!-- /.box-body -->
						<div class="footer">
							<div class="box-footer">
								<input type="submit" class="btn btn-info" value="Lọc">
								<input type="reset" class="btn btn-danger" value="Đặt lại">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div class="box">
				<form:form id="exportForm" name="exportForm" action="${pageContext.request.contextPath }/export/excel" method="post" modelAttribute="advertWrapper">
				<div class="box-header">
					<h3 class="box-title">Data</h3>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
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
								<th style="width: 13%;">Người liên hệ</th>
								<th style="width: 10%;">SĐT</th>
								<th style="width: 50%;">Hình ảnh</th>
								<th style="width: 5%;"></th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${empty page.content }">
								<tr>
									<td colspan="21" style="text-align: center;">Không tìm
										thấy dữ liệu</td>
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
											<input type="hidden" name="advs[${loop.index }].size"
											value="${adv.size }"/>
											<input type="hidden" name="advs[${loop.index }].describe"
											value="${adv.describe }"/>
											<input type="hidden" name="advs[${loop.index }].note"
											value="${adv.note }"/>
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
										<ul>
											<c:forEach items="${adv.advImages }" var="advImage" varStatus="i">
										  <li>
										  	<input type="checkbox" checked="checked" name="advs[${loop.index }].advImages[${i.index }].id" value="${advImage.id }" id="cb${loop.index }-${i.index }" />
										    <label for="cb${loop.index }-${i.index }">
										    	<img class="image" src="${pageContext.request.contextPath }/resources/images?url=${advImage.url }&w=200&h=200" />
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
									<td>${adv.advCompContactPerson }</td>
									<td>${adv.advCompPhone }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					

				</div>
				<!-- /.box-body -->

				<div class="box-footer clearfix">
					<security:authorize
						access="hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')">
						<button id="exportExcel" id="exportExcel" class="btn btn-info">Xuất Excel</button>
					</security:authorize>
					<security:authorize
						access="hasAnyRole('ROLE_ADMIN', 'ROLE_BUSINESS')">
						<button id="exportPowerpoint" id="exportPowerpoint" class="btn btn-info">Xuất
							Powerpoint</button>
					</security:authorize>

					<ul class="pagination pagination-sm no-margin pull-right">
						<c:if test="${page.number > 0 }">
							<li class=""><a
								href="${pageContext.request.contextPath }/adv/view?page=${page.number - 1 }&size=${page.size }">«</a></li>
						</c:if>

						<c:forEach begin="0"
							end="${page.totalPages - 1 > 0 ? page.totalPages - 1 : 0 }"
							varStatus="loop">
							<li class="${loop.index == page.number ? 'active' : ''}"><a
								href="${pageContext.request.contextPath }/adv/view?page=${loop.index }&size=${page.size }">${loop.index }</a></li>
						</c:forEach>

						<c:if test="${page.number < page.totalPages - 1 }">
							<li class=""><a
								href="${pageContext.request.contextPath }/adv/view?page=${page.number + 1 }&size=${page.size }">»</a></li>
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
	$('.delete').click(function(e) {
		e.preventDefault();
		var answer = confirm("Bạn có chắc muốn xoá?");
		if(answer === true) {
			window.location.href = $(this).attr('href');
		}
	});
	
	$('input[type=reset]').click(function(e) {
		$('input[name=code]').val('');
		$('input[name=address]').val('');
		$('input[name=createdBy]').val('');
		$('input[name=daterange]').val('');
		$('input[name=contactPerson]').val('');
	});
	
	// Address
	$("#address").autocomplete({
		source: "${pageContext.request.contextPath}/location",
	    minLength: 2,
	    select: function( event, ui ) {
	    	console.log("Selected: " + ui.item.value);
	    }
	});
	
	var daterange = '${daterange}'.split(" - ");
	if(daterange.length < 2) {
		var today = new Date();
		daterange[0] = "01/01/2017";
		daterange[1] = (today.getMonth()+1) + "/" + today.getDate() + "/" + today.getFullYear();
	}
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
		$('form[name=exportForm]').attr('action', '${pageContext.request.contextPath }/export/excel');
		$('form[name=exportForm]').submit();
	});
	$('#exportPowerpoint').click(function(e) {
		$('form[name=exportForm]').attr('action', '${pageContext.request.contextPath }/export/powerpoint');
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
</script>
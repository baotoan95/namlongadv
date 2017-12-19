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
		<div class="col-xs-12" id="accordion" role="tablist">
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
					<form action="${pageContext.request.contextPath }/adv/search"
						method="get">
						<div class="box-body">
							<div class="row">
								<div class="col-xs-3 ui-widget">
									<input type="text" name="code" class="form-control"
										placeholder="Mã" value="${code }">
								</div>
								<div class="col-xs-3">
									<input id="address" type="text" name="address" value="${address }" class="form-control" placeholder="Địa chỉ">
								</div>
								<div class="col-xs-3">
								    <input type="text" class="form-control" value="${createdBy }" placeholder="Tạo bởi" name="createdBy" id="createByFilter" />
								</div>
								<div class="col-xs-3">
								    <input type="text" class="form-control" value="${daterange }" placeholder="Ngày tạo" name="daterange" id="createByFilter" />
								</div>
								<script>
									$(function() {
										$("#address").autocomplete({
											source: "${pageContext.request.contextPath}/location",
										    minLength: 2,
										    select: function( event, ui ) {
										    	console.log( "Selected: " + ui.item.value + " aka " + ui.item.id );
										    }
										});
										console.log($('input[name="daterange"]'));
										$('input[name="daterange"]').daterangepicker({
											 "opens": "left"
										});
									});
									
									$(document).ready(function() {
										$.ajax({
						                    url : "${pageContext.request.contextPath}/users",
						                    type : "get",
						                    success : function (result){
						                    	$('#createByFilter').autocomplete({
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
								    });
								</script>
							</div>
						</div>
						<!-- /.box-body -->
						<div class="footer">
							<div class="box-footer">
								<button type="submit" class="btn btn-info">Lọc</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<div class="box">
				<form:form name="exportForm" action="${pageContext.request.contextPath }/export/excel" method="post" modelAttribute="advertWrapper">
				<div class="box-header">
					<h3 class="box-title">Data</h3>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<table class="table table-bordered table-responsive"
						style="max-width: none !important; margin-right: 10px;">
						<thead>
							<tr>
								<th style="width: 8px; text-align: center;">C</th>
								<th style="width: 8px;">#</th>
								<th style="width: 20%;">Vị Trí</th>
<!-- 								<th>Tên Đường</th> -->
<!-- 								<th>Số Nhà</th> -->
<!-- 								<th>Xã</th> -->
<!-- 								<th>Quận</th> -->
<!-- 								<th>Tỉnh</th> -->
<!-- 								<th>Kích thước</th> -->
<!-- 								<th>Đơn giá</th> -->
								<th style="width: 20px;"></th>
								<th style="width: 13%;">Người liên hệ</th>
								<th style="width: 10%;">SĐT</th>
<!-- 								<th style="width: 10%;">Email</th> -->
<!-- 								<th>Giá thuê</th> -->
								<th style="width: 50%;">Hình ảnh</th>
<!-- 								<th>Ghi chú</th> -->
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
							<c:forEach items="${page.content }" var="adv" varStatus="loop">
								<tr>
									<td rowspan="2">
										<input type="checkbox" name="advs[${loop.index }].id" 
											checked="checked" value="${adv.id }" />
										<input type="hidden" 
											name="advs[${loop.index }].ownerPhone"
											value="${adv.ownerPhone }"/>
										<input type="hidden" 
											name="advs[${loop.index }].ownerContactPerson"
											value="${adv.ownerContactPerson }"/>
										<input type="hidden" 
											name="advs[${loop.index }].location" 
											value="${adv.location }"/>
									</td>
									<td rowspan="2">${loop.index + 1 }</td>
									<td rowspan="2">
										${adv.location }
									</td>
<%-- 									<td rowspan="2">${adv.street }</td> --%>
<%-- 									<td rowspan="2">${adv.houseNo }</td> --%>
<%-- 									<td rowspan="2">${adv.ward }</td> --%>
<%-- 									<td rowspan="2">${adv.district }</td> --%>
<%-- 									<td rowspan="2">${adv.province }</td> --%>
<%-- 									<td rowspan="2">${adv.size }</td> --%>
<%-- 									<td rowspan="2">${adv.price }</td> --%>
									<td>TTCN</td>
									<td>
										${adv.ownerContactPerson }
									</td>
									<td>
										${adv.ownerPhone }
									</td>
<%-- 									<td>${adv.ownerEmail }</td> --%>
<%-- 									<td>${adv.ownerPrice }</td> --%>
									<td rowspan="2" class="imageSelector">
										<ul>
											<c:forEach items="${adv.advImages }" var="advImage" varStatus="i">
										  <li>
										  	<input type="checkbox" name="advs[${loop.index }].advImages[${i.index }].id" value="${advImage.id }" id="cb${loop.index }-${i.index }" />
										    <label for="cb${loop.index }-${i.index }">
										    	<img src="${pageContext.request.contextPath }/resources/images?url=${advImage.url }&w=200&h=200" />
										    </label>
										  	<input type="hidden" name="advs[${loop.index }].advImages[${i.index }].name" value="${advImage.name }"/>
										  	<input type="hidden" name="advs[${loop.index }].advImages[${i.index }].url" value="${advImage.url }"/>
										  </li>
										  </c:forEach>
<%-- 										  <li><input type="checkbox" id="cbb${loop.index }" /> --%>
<%-- 										    <label for="cbb${loop.index }"><img src="http://lorempixel.com/101/101" /></label> --%>
<!-- 										  </li> -->
<%-- 										  <li><input type="checkbox" id="cbc${loop.index }" /> --%>
<%-- 										    <label for="cbc${loop.index }"><img src="http://lorempixel.com/102/102" /></label> --%>
<!-- 										  </li> -->
<%-- 										  <li><input type="checkbox" id="cbd${loop.index }" /> --%>
<%-- 										    <label for="cbd${loop.index }"><img src="http://lorempixel.com/103/103" /></label> --%>
<!-- 										  </li> -->
<%-- 										  <li><input type="checkbox" id="cbe${loop.index }" /> --%>
<%-- 										    <label for="cbe${loop.index }"><img src="http://lorempixel.com/103/103" /></label> --%>
<!-- 										  </li> -->
<%-- 										  <li><input type="checkbox" id="cbf${loop.index }" /> --%>
<%-- 										    <label for="cbf${loop.index }"><img src="http://lorempixel.com/103/103" /></label> --%>
<!-- 										  </li> -->
<%-- 										  <li><input type="checkbox" id="cbg${loop.index }" /> --%>
<%-- 										    <label for="cbg${loop.index }"><img src="http://lorempixel.com/103/103" /></label> --%>
<!-- 										  </li> -->
										</ul>
									</td>
<%-- 									<td rowspan="2">${adv.note }</td> --%>
									<td rowspan="2" class="action" style="text-align: center;"><a title="Xoá"
										href="${pageContext.request.contextPath }/adv/delete/${adv.id }"><i
											class="fa fa-fw fa-trash"></i></a> <br /> <a title="Xem"
										href="${pageContext.request.contextPath }/adv/${adv.id }"><i
											class="fa fa-fw fa-edit"></i></a></td>
								</tr>
								<tr>
									<td>TTCTQC</td>
									<td>${adv.advCompContactPerson }</td>
									<td>${adv.advCompPhone }</td>
<%-- 									<td>${adv.advCompEmail }</td> --%>
<%-- 									<td>${adv.advCompPrice }</td> --%>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					

				</div>
				<!-- /.box-body -->

				<div class="box-footer clearfix">
					<security:authorize
						access="hasAnyRole('ROLE_ADMIN', 'ROLE_ACCOUNTANT')">
						<button type="button" id="exportExcel" class="btn btn-info">Xuất Excel</button>
					</security:authorize>
					<security:authorize
						access="hasAnyRole('ROLE_ADMIN', 'ROLE_BUSINESS')">
						<input type="submit" id="exportPowerpoint" class="btn btn-info">Xuất
							Powerpoint</input>
					</security:authorize>
					
					<script type="text/javascript">
// 						$('#exportExcel').click(function(e) {
// 							$('form[name=exportForm]').attr('action', '${pageContext.request.contextPath}');
// 						});
					</script>

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
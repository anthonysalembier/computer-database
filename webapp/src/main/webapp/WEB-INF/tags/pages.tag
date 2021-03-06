<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="page" required="true" type="java.lang.Integer"%>
<%@ attribute name="pageCount" required="true" type="java.lang.Integer"%>
<%@ attribute name="pageSize" required="true" type="java.lang.Integer"%>
<%@ attribute name="url" required="true"%>
<%@ attribute name="totalPages" required="true" type="java.lang.Integer"%>

<div class="container text-center">
	<ul class="pagination">
		<c:if test="${ currentPage > 1 }">
			<li>
				<a href="${ url }?page=${ currentPage - 1 }&size=${ sizePage }&search=${ search }" aria-label="Previous">
					<span aria-hidden="true">&laquo;</span>
				</a>
			</li>
		</c:if>
		
		<c:choose>
			<c:when test="${ totalPages > 6}">
				<c:forEach var="i" begin="1" end="3">
					<li><a href="${ url }?page=${ i }&size=${ sizePage }&search=${ search }">${ i }</a></li>
				</c:forEach>
				<c:if test="${ currentPage == 3 }">
					<li><a href="${ url }?page=4&size=${ sizePage }&search=${ search }">4</a></li>
				</c:if>
				<c:choose>
					<c:when test="${ (currentPage > 3) && (currentPage < (totalPages - 2))}">
						<c:if test="${ currentPage > 5 }">
							<li><a href="#">...</a></li>
						</c:if>
						<c:if test="${ currentPage > 4 }">
							<li><a href="${ url }?page=${ currentPage - 1 }&size=${ sizePage }&search=${ search }">${ currentPage - 1 }</a></li>
						</c:if>
						<li><a href="${ url }?page=${ currentPage }&size=${ sizePage }&search=${ search }">${ currentPage }</a></li>
						<c:if test="${ currentPage < totalPages - 3 }">
							<li><a href="${ url }?page=${ currentPage + 1 }&size=${ sizePage }&search=${ search }">${ currentPage + 1 }</a></li>
						</c:if>
						<c:if test="${ currentPage < totalPages - 4 }">
							<li><a href="#">...</a></li>
						</c:if>
					</c:when>
					<c:otherwise>
						<li><a href="#">...</a></li>
					</c:otherwise>
				</c:choose>
				<c:if test="${ currentPage == totalPages - 2 }">
					<li><a href="${ url }?page=${ totalPages - 3 }&size=${ sizePage }&search=${ search }">${ totalPages - 3 }</a></li>
				</c:if>
				<c:forEach var="i" begin="${ totalPages - 2 }" end="${ totalPages }">
					<li><a href="${ url }?page=${ i }&size=${ sizePage }&search=${ search }">${ i }</a></li>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<c:forEach var="i" begin="1" end="${ maxPages }">
					<li><a href="${ url }?page=${ i }&size=${ sizePage }&search=${ search }">${ i }</a></li>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	
		<c:if test="${ currentPage < totalPages }">
			<li>
				<a href="${ url }?page=${ currentPage + 1 }&size=${ sizePage }&search=${ search }" aria-label="Next">
					<span aria-hidden="true">&raquo;</span>
				</a>
			</li>
		</c:if>
	</ul>

	<div class="btn-group btn-group-sm pull-right" role="group">
		<button type="button" class="btn btn-default" value="10"
				onclick="window.location.href='${ url }?page=1&size=10&search=${ search }'">
			10
		</button>
		<button type="button" class="btn btn-default"
				onclick="window.location.href='${ url }?page=1&size=50&search=${ search }'">
			50
		</button>
		<button type="button" class="btn btn-default"
				onclick="window.location.href='${ url }?page=1&size=100&search=${ search }'">
			100
		</button>
	</div>
</div>
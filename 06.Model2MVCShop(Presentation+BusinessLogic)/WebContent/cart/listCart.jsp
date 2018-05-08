<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>장바구니</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	// 검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
	function fncGetList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
</script>
</head>
<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">

			<form name="detailForm" action="/listCart.do" method="post">

				<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="15" height="37"><img
							src="/images/ct_ttl_img01.gif" width="15" height="37" /></td>
						<td background="/images/ct_ttl_img02.gif" width="100%"
							style="padding-left: 10px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="93%" class="ct_ttl01">장바구니</td>
								</tr>
							</table>
						</td>
						<td width="12" height="37"><img
							src="/images/ct_ttl_img03.gif" width="12" height="37" /></td>
					</tr>
				</table>

				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					style="margin-top: 10px;">
					<tr>
						<td colspan="11">전체 ${resultPage.totalCount}건수, 현재${resultPage.currentPage} 페이지</td>
					</tr>
					<tr>
						<td class="ct_list_b" width="20">No</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b" width="50">상품번호</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b" width="150">상품명</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b" width="150">상품이미지</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b" width="100">가격</td>
						<td class="ct_line02"></td>
						<td class="ct_list_b" width="50">수량</td>
						<td class="ct_line02"></td>
					</tr>
					<tr>
						<td colspan="20" bgcolor="808285" height="1"></td>
					</tr>
					<c:set var="i" value="0" />
					<c:forEach var="cart" items="${list}">
						<c:set var="i" value="${i+1 }" />
						<tr class="ct_list_pop" width="150">
							<td align="center">${ i }</td>
							<td></td>
							<td align="center">
								<a href="/getProduct.do?prodNo=${cart.cartProd.prodNo}&menu=search">${cart.cartProd.prodNo}</a>
							</td>
							<td></td>
							<td align="center">${cart.cartProd.prodName}</td>
							<td></td>
							<td align="center">${cart.cartProd.fileName}</td>
							<td></td>
							<td align="center">${cart.cartProd.prodNo}</td>
							<td></td>
							<td align="center">${cart.cartProd.price}</td>
							<td></td>
							<td align="center">${cart.cartProdQuantity}</td>
							<td></td>
						<tr>
							<td colspan="20" bgcolor="D6D7D6" height="1"></td>
						</tr>
					</c:forEach>
				</table>
				<!-- PageNavigation Start... -->
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					style="margin-top: 10px;">
					<tr>
						<td align="center">
							<input type="hidden" id="currentPage" name="currentPage" value="${resultPage.currentPage}" /> 
								<jsp:includepage="../common/pageNavigator.jsp" />
						</td>
					</tr>
				</table>
				<!-- PageNavigation End... -->
				<!--  페이지 Navigator 끝 -->
									
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			style="margin-top: 10px;">
			<tr>
				<td width="53%"></td>
				<td align="right">
					<table border="0" cellspacing="0" cellpadding="0">
			<tr>

				<td width="17" height="23"><img src="/images/ct_btnbg01.gif"
					width="17" height="23" /></td>
				<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;"><c:if test= "${product.prodQuantity!=0}">
						<a href="/addPurchaseView.do?prod_no=${product.prodNo}">구매</a>
					</c:if> 
				</td>
				<td width="14" height="23"><img src="/images/ct_btnbg03.gif" width="14" height="23"></td>
				<td width="30"></td>
				<td width="17" height="23"><img src="/images/ct_btnbg01.gif" width="17" height="23" /></td>
				<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">
					<a href="javascript:history.go(-1)">이전</a></td>
				<td width="14" height="23"><img src="/images/ct_btnbg03.gif" width="14" height="23"></td>
			</tr>
		</table>
			</form>
	</div>
</body>
</html>
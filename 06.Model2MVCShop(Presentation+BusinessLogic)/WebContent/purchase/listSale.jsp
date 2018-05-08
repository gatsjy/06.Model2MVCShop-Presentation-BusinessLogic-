<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>�Ǹ� ��� ��ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	// �˻� / page �ΰ��� ��� ��� Form ������ ���� JavaScrpt �̿�  
	function fncGetList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
</script>
</head>
<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">
<form name="detailForm" action="/listSale.do?tranNo=${tranNo}" method="post">

		<table width="100%" height="37" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"
					width="15" height="37" /></td>
				<td background="/images/ct_ttl_img02.gif" width="100%"
					style="padding-left: 10px;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="93%" class="ct_ttl01">�Ǹ� ���</td>
						</tr>
					</table>
				</td>
				<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"
					width="12" height="37" /></td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			style="margin-top: 10px;">
			<tr>
				<td align="right"><select name="searchCondition"
					class="ct_input_g" style="width: 80px">
						<option value="0"
							${! empty search.searchCondition&& search.searchCondition==0 ? "selected" : "" }>��ǰ��ȣ</option>
						<option value="1"
							${! empty search.searchCondition&& search.searchCondition==1 ? "selected" : "" }>��ǰ��</option>
						<option value="2"
							${! empty search.searchCondition&& search.searchCondition==2 ? "selected" : "" }>��ǰ����</option>
				</select> 
				<input type="text" name="searchKeyword" value="${search.searchKeyword }" 
					class="ct_input_g" style="width: 200px; height: 19px" /></td>
				<td align="right" width="70">
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="17" height="23"><img src="/images/ct_btnbg01.gif"
								width="17" height="23"></td>
							<td background="/images/ct_btnbg02.gif" class="ct_btn01"
								style="padding-top: 3px;"><a href="javascript:fncGetList(1);">�˻�</a></td>
							<td width="14" height="23"><img src="/images/ct_btnbg03.gif"
								width="14" height="23"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>


		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			style="margin-top: 10px;">
			<tr>
				<td colspan="11">��ü ${resultPage.totalCount}�Ǽ�,
					����${resultPage.currentPage} ������</td>
			</tr>
			<tr>
				<td class="ct_list_b" width="100">No</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b" width="150">��ǰ��ȣ</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b" width="150">�ŷ���ȣ</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b" width="150">��ǰ��</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b" width="150">���ž�</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b" width="150">���Ź���/��������</td>
				<td class="ct_line02"></td>
				<td class="ct_list_b" width="150">���</td>
			</tr>
			<tr>
				<td colspan="20" bgcolor="808285" height="1"></td>
			</tr>
			<c:set var="i" value="0" />
			<c:forEach var="purchase" items="${list}">
				<c:set var="i" value="${i+1 }" />
				<tr class="ct_list_pop">
					<td align="center">${ i }</td>
					<td></td>
					<td align="center">${purchase.purchaseProd.prodNo}</td>
					<td></td>
					<td align="center">${purchase.tranNo}</td>
					<td></td>
					<td align="center">${purchase.purchaseProd.prodName}</td>
					<td></td>
					<td align="center">${purchase.purchasePrice}</td>
					<td></td>
					<td align="center">${purchase.purchaseQuantity}/${purchase.purchaseProd.prodQuantity }</td>
					<td></td>
					<td align="center">
					<c:if test="${purchase.tranCode=='1' }">
					<a href="/updateTranCodeByTranNo.do?tranNo=${purchase.tranNo}&tranCode=2">����ϱ�</a>
					</c:if>
					<c:if test="${purchase.tranCode=='2' }">
					<a>�����</a>
					</c:if>
					<c:if test="${purchase.tranCode=='3' }">
					<a>��ۿϷ�!</a>
					</c:if>
					</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="D6D7D6" height="1"></td>
				</tr>
			</c:forEach>
		</table>

		<!-- PageNavigation Start... -->
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			style="margin-top: 10px;">
			<tr>
				<td align="center"><input type="hidden" id="currentPage"
					name="currentPage" value="${resultPage.currentPage}" /> 
					<jsp:include page="../common/pageNavigator.jsp" /></td>
			</tr>
		</table>
		<!-- PageNavigation End... -->

		<!--  ������ Navigator �� -->
	</form>
	</body>
	</html>

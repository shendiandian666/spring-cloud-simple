<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="http://www.thymeleaf.org"
	xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

<head>
<dif th:include="system/index/top::tophead"></dif>
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="page-header">
							<h1>
								<small>
									<i class="ace-icon fa fa-angle-double-right"></i>
									详情
								</small>
							</h1>
					</div><!-- /.page-header -->
					<div class="row">
						<div class="col-xs-8 col-xs-offset-2">
							<form name="Form" id="Form"
								method="post">
								<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report"
										class="table table-striped table-bordered table-hover">
										<#list columnMap?keys as key>  
										<#if columnMap[key][1] == 'true'>
										<tr>
											<td style="text-align: right; padding-top: 13px;">${columnMap['${key}'][0]}:</td>
											<td style="padding-top: 13px;">
												<span th:text="${r"${"}${class?lower_case}.${key}_DESC}"></span>
											</td>
										</tr>
										<#else>
										<tr>
											<td style="text-align: right; padding-top: 13px;">${columnMap['${key}'][0]}:</td>
											<td style="padding-top: 13px;">
												<span th:text="${r"${"}${class?lower_case} == null ? '' : ${class?lower_case}.${key}}"></span>
											</td>
										</tr>
										</#if>
										</#list>
									</table>
								</div>
							</form>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->

	<footer>
		<div style="width: 100%; padding-bottom: 2px;" class="center">
			<a class="btn btn-mini btn-danger" onclick="cancel();">返回</a>
		</div>
	</footer>

	<!-- 页面底部js¨ -->
	<div th:include="system/index/foot::footdiv" />
	<!--提示框-->
	<script type="text/javascript" th:src="@{/js/jquery.tips.js}"></script>
	<script type="text/javascript" th:inline="javascript">
		$(top.hangge());
		function cancel(){
			/*<![CDATA[*/
			top.jzts();
			if(true && document.forms[0]){
				var url = /*[[${r"${url}"}]]*/;
				document.forms[0].action = url;
				document.forms[0].submit();
			}
			/*]]>*/		
		}
	</script>
</body>
</html>
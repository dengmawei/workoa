<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		
<script type="text/javascript" src="${ctx }/js/jquery-validation-1.8.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx }/js/Jquery-Validate-For-Bootstrap-master/jquery.validate.bootstrap.js"></script>
<script type="text/javascript" src="${ctx }/js/twitter-bottstrap/js/bootstrap-modal.js"></script>
<title>模块中心</title>
<script type="text/javascript">
	function initModule(){
		var message = "${message}";
		if(message!=""){
			showTipInfo(message);
		}
	}
	
	$(document).ready(function(){
		initModule();
		
		$("#subDialog").modal({
			keyboard:true,
			show:false	
		});
		
		$.validator.addMethod("moduleName_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'模块名不能为空');
		
		$.validator.addMethod("moduleValue_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'模块值不能为空');
		
		$.validator.addMethod("linkUrl_required",function(value){
			var isParent = $("input[name='isParent']:checked").val();
			if(isParent==1){
				return true;
			}
			
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'访问地址不能为空');
		
		$("#moduleForm").validate({
			success: function(element) {
				element.text('OK!').addClass('valid').closest('.control-group').removeClass('error').removeClass('success');
			},
			highlight: function(element) {
				$(element).closest('.control-group').removeClass('success').addClass('error');
			},
			rules:{
				moduleName:{
					moduleName_required:true
				},
				moduleValue:{
					moduleValue_required:true
				},
				linkUrl:{
					linkUrl_required:true
				}
			}
		});
	});
	
	function showParentList(obj){
		if($(obj).val()==0){
			$("input[name='linkUrl']").prop("disabled",false);
			$("#parentList").removeClass("hidden").addClass("visible");
		}else{
			$("input[name='linkUrl']").prop("disabled",true);
			$("input[name='linkUrl']").val('');
			$("#parentList").removeClass("visible").addClass("hidden");
		}
	}
	
	function subModule(){
		if($("#moduleForm").validate().form()){
			$('#subDialog').modal('show');
		}
	}
	
	function editModule(){
		var isParent = $("input[name='isParent']:checked").val();
		if(isParent==1){
			$("#superId").val(0);
		}else{
			var superModule = $("#superModuleList").val();
			if(superModule!=null && superModule!=""){
				$("#superId").val(superModule);
			}else{
				showTipInfo("上级模块为空，请先添加上级模块");
				return;
			}
		}
		$("#moduleForm").submit();
	}
</script>
</head>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
		<a href="${ctx }/module/index.html?sid=${sid}" class="tip-bottom" title="模块列表">模块列表</a>
		<a href="javascript:void(0)" class="current">编辑模块</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-align-justify"></i>									
						</span>
						<h5>编辑模块</h5>
					</div>
					<div class="widget-content nopadding">
						<form id="moduleForm" action="${ctx }/module/editModule.html?sid=${sid}" method="post" class="form-horizontal">
							<input type="hidden" id="id" name="id" value="${module.id }">
							<div class="control-group">
								<label class="control-label">模块名</label>
								<div class="controls w50p">
									<input type="text" name="moduleName" maxlength="30" value="${module.moduleName }">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">模块值</label>
								<div class="controls w50p">
									<input type="text" name="moduleValue" maxlength="30" value="${module.moduleValue }">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">访问路径</label>
								<div class="controls w50p">
									<input type="text" name="linkUrl" maxlength="50" value="${module.linkUrl }" <c:if test="${module.isParent!=0 }">disabled="disabled"</c:if>>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">是否父节点</label>
								<div class="controls">
									<c:choose>
										<c:when test="${module.isParent==0 }">
											<label><input type="radio" name="isParent" value="0" checked="checked" onclick="showParentList(this)">否</label>
											<label><input type="radio" name="isParent" value="1" onclick="showParentList(this)">是</label>
										</c:when>
										<c:otherwise>
											<label><input type="radio" name="isParent" value="0" onclick="showParentList(this)">否</label>
											<label><input type="radio" name="isParent" value="1" checked="checked" onclick="showParentList(this)">是</label>
										</c:otherwise>
									</c:choose>
								</div>
							</div>
							<c:if test="${module.isParent==0 }">
								<div class="control-group" id="parentList">
									<label class="control-label">父节点</label>
									<div class="controls">
										<input type="hidden" id="superId" name="superId" value="${module.superId }">
										<select name="superModuleList" id="superModuleList" class="w100">
											<c:if test="${superModuleList!=null}">
												<c:forEach items="${superModuleList }" var="m">
													<c:if test="${module.superId==m.id }">
														<option value="${m.id }" selected="selected">${m.moduleName }</option>
													</c:if>
													<c:if test="${module.superId!=m.id }">
														<option value="${m.id }">${m.moduleName }</option>
													</c:if>
												</c:forEach>
											</c:if>
										</select>
									</div>
								</div>
							</c:if>
							<c:if test="${module.isParent!=0 }">
								<div class="control-group hidden" id="parentList">
									<label class="control-label">父节点</label>
									<div class="controls">
										<input type="hidden" id="superId" name="superId" value="${module.superId }">
										<select name="superModuleList" id="superModuleList" class="w100">
											<c:if test="${superModuleList!=null}">
												<c:forEach items="${superModuleList }" var="m">
													<c:if test="${module.superId==m.id }">
														<option value="${m.id }" selected="selected">${m.moduleName }</option>
													</c:if>
													<c:if test="${module.superId!=m.id }">
														<option value="${m.id }">${m.moduleName }</option>
													</c:if>
												</c:forEach>
											</c:if>
										</select>
									</div>
								</div>
							</c:if>
							<div class="control-group">
								<label class="control-label">模块描述</label>
								<div class="controls w50p">
									<textarea rows="5" cols="20" name="moduleDesc">${module.moduleDesc }</textarea>
								</div>
							</div>
							<div class="form-actions">
								<button type="button" class="btn btn-primary" onclick="subModule()">提交</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="subDialog" title="系统提示" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>编辑模块</h3>
		</div>
		<div class="modal-body">
			您确认提交？
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" value="取消" onclick="$('#subDialog').modal('hide');">
    		<input type="button" class="btn btn-primary" onclick="editModule()" value="确定">
		</div>
	</div>
</body>
</html>
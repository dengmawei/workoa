<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx }/js/bootstrap-pagination/bootstrap-paginator.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery-validation-1.8.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx }/js/Jquery-Validate-For-Bootstrap-master/jquery.validate.bootstrap.js"></script>
<script type="text/javascript" src="${ctx }/js/twitter-bottstrap/js/bootstrap-modal.js"></script>
<script type="text/javascript" src="${ctx }/js/jquery-form/jquery.form.js"></script>
<title>模块中心</title>
<script type="text/javascript">
	var pageOfNum = 10;
	var defaultPage = 1;
	
	$(document).ready(function(){
		selectModuleByPage(pageOfNum,defaultPage);
		
		$("#searchDialog").modal({
			keyboard:true,
			show:false
		});
		
		$("#setPermitDialog").modal({
			keyboard:true,
			show:false
		});
	});
	
	
	function selectModuleByPage(perPage,currentPage){
		$("#moduleList tbody").html("");
		var param = {};
		param.perPage=pageOfNum;
		param.currentPage=currentPage;
		param.moduleName = $("#moduleName").val();
		param.parentId = $("#parentNodeId").val();
		param.isParent = $("#searchBox input[name='isParent']:checked").val();
		$.post("${ctx}/module/selectModuleByPage.html",param,function(data){
			if(!data.success){
				return;
			}
			
			var result = data.data.list;
			var count = data.data.count;
			var currentPage = data.data.currentPage;
			var totalPage = data.data.totalPage;
			
			var modulehtml = "";
			if(result!=null && result.length>0){
				for(var i=0;i<result.length;i++){
					var module = result[i];
					var isTip = module.isParent==1?'是':'否';
					var superId = module.isParent==1?"":module.superId;
					var permitSetHtml = module.isParent==1?"":"<span class='icon'><button class='btn btn-mini' title='设置权限' onclick='toPermitPage("+module.id+")'><i class='icon-wrench'></i></button></span>";
					modulehtml += "<tr><td>"
					+(i+1)+"</td><td>"
					+module.id+"</td><td>"
					+module.moduleName+"</td><td>"
					+module.moduleValue+"</td><td>"
					+module.linkUrl+"</td><td>"
					+module.createTime+"</td><td>"
					+isTip+"</td><td>"
					+superId+"</td><td>"
					+"<span class='icon'><button class='btn btn-mini' title='编辑' onclick='editModule("+module.id+")'><i class='icon-edit'></i></button></span>&nbsp;&nbsp;"
					+"<span class='icon'><button class='btn btn-mini' title='删除' onclick='deleteModule("+module.id+")'><i class='icon-trash'></i></button></span>&nbsp;&nbsp;"
					+"<span class='icon'><button class='btn btn-mini' title='详情查看' onclick='searchModule("+module.id+")'><i class='icon-search'></i></button></span>&nbsp;&nbsp;"
					+permitSetHtml+"</td></tr>";
				}
			}
			
			$("#moduleList tbody").append(modulehtml);
			
			if(count>0){
				var options = {
					currentPage:currentPage,
					totalPages:totalPage,
					numberOfPages:pageOfNum,
					onPageClicked:function(event,originalEvent, type,page){
						if(currentPage==1 && (type=='first' || type=='prev')){
							return;
						}
						
						if(currentPage==totalPage && (type=='last' || type=='next')){
							return;
						}
						selectModuleByPage(pageOfNum,page);
					},
					itemTexts:function(type,page,current){
						switch (type) {  
							case "first":
								return "首页";
							case "prev":
								return "上一页";
							case "next":
								return "下一页";
							case "last":
								return "末页";
							case "page":
								return  page;
				            }                
						},
					shouldShowPage:true
				};
				$('#example').bootstrapPaginator(options);
			}else{
				$('#example').html('');
			}
		},"json");
	}
	
	function openSearch(){
		if($("#searchBox").is(":visible")==false){
			$("#searchBox").show();
		}else{
			$("#searchBox").hide();
		}
	}
	
	function deleteModule(id){
		if(window.confirm("您确定删除？")){
			$.post("${ctx}/module/deleteModule.html",{id:id},function(data){
				if(data.success){
					showTipInfo("删除成功");
					clearQueryInfo();
					selectModuleByPage(pageOfNum,defaultPage);
				}else{
					showTipInfo(data.message);
				}
			},'json');
		}
	}
	
	function clearQueryInfo(){
		$("#moduleName").val('');
		$("#searchBox input[value='1']").prop("checked",true);
	}
	
	function toAddModule(){
		window.location.href="${ctx}/module/toAddModule.html?sid=${sid}";
	}
	
	function searchModule(moduleId){
		$.post("${ctx}/module/searchModule.html",{id:moduleId},function(data){
			if(!data.success){
				return false;
			}
			
			var moduleView = data.data;
			var module = moduleView.module;
			var superModule = moduleView.superModule;
			$("#moduleNameS").val(module.moduleName);
			$("#moduleValueS").val(module.moduleValue);
			$("#linkUrlS").val(module.linkUrl);
			$.each($("input[name='isParentS']"),function(){
				if($(this).val()==module.isParent){
					$(this).prop("checked",true);
				}
			});
			$("#moduleDescS").val(module.moduleDesc);
			
			if(superModule!=null){
				$("#superModelNameS").val(superModule.moduleName);
			}else{
				$("#superModelNameS").parent().html("无");
			}
			
			$("#searchDialog").modal("show");
		},"json");
	}
	
	function editModule(moduleId){
		window.location.href="${ctx}/module/toEditModule/"+moduleId+".html?sid=${sid}";
	}
	
	function clearQueryInfo(){
		$("input[name='isParent']").eq(0).prop("checked",true);
		$("#moduleName").val('');
		$("#parentNodeId option").first().prop("selected",true);
	}
	
	function setPermit(moduleId){
		$("#setPermitDialog").modal('show');
		selectActionByPage(5,1);
	}
	
	function selectActionByPage(perPage,currentPage){
		$("#actionList tbody").html("");
		var param = {};
		param.perPage=pageOfNum;
		param.currentPage=currentPage;
		$.post("${ctx}/action/selectActionByPage.html",param,function(data){
			if(!data.success){
				return;
			}
			
			var result = data.data.list;
			var count = data.data.count;
			var currentPage = data.data.currentPage;
			var totalPage = data.data.totalPage;
			
			var actionHtml = "";
			if(result!=null && result.length>0){
				for(var i=0;i<result.length;i++){
					var action = result[i];
					actionHtml += "<tr onclick='checkAction("+action.id+",\""+action.actionName+"\")'><td>"
					+(i+1)+"</td><td>"
					+action.actionName+"</td></tr>";
				}
			}
			
			$("#actionList tbody").append(actionHtml);
			
			if(count>0){
				var options = {
					currentPage:currentPage,
					totalPages:totalPage,
					numberOfPages:pageOfNum,
					onPageClicked:function(event,originalEvent, type,page){
						if(currentPage==1 && (type=='first' || type=='prev')){
							return;
						}
						
						if(currentPage==totalPage && (type=='last' || type=='next')){
							return;
						}
						
						selectActionByPage(5,1);
					},
					itemTexts:function(type,page,current){
						switch (type) {  
							case "first":
								return "首页";
							case "prev":
								return "上一页";
							case "next":
								return "下一页";
							case "last":
								return "末页";
							case "page":
								return  page;
				            }                
						},
					shouldShowPage:true
				};
				$('#actionExample').bootstrapPaginator(options);
			}else{
				$('#actionExample').html('');
			}
		},"json");
	}
	
	function checkAction(actionId,actionName){
		var flag = true;
		$.each($("#actionIdList input[name='actionIdArr']"),function(){
			if($(this).val()==actionId){
				alert("当前Action已被选择");
				flag = false;
				return;
			}
		});
		
		if(flag){
			var content = '<li><span class="mr5">'+actionName+'<input type="hidden" name="actionIdArr" value="'+actionId+'"></span><button class="btn btn-mini" onclick="removeAction(this)"><i class="icon-remove"></i></button></li>';
			$("#actionIdList").append(content);
		}
	}
	
	function removeAction(obj){
		$(obj).parent().remove();
	}
	
	function savePermit(){
		var param = {};
		var actionIds = [];
		var moduleId = $("#moduleId").val();
		var actionArr = $("#actionIdList input[name='actionIdArr']");
		if(actionArr.length>0){
			$.each(actionArr,function(){
				actionIds.push($(this).val());
			});
			
			param.moduleId = moduleId;
			param.actionIds = actionIds;
		}else{
			alert("请选择要设置的Action");
		}
		
		$.post("${ctx}/module/savePermit.html",param,function(data){
			alert('设置成功');
			cancelSavePermit();
			$('#setPermitDialog').modal('hide');
		},'json');
	}
	
	function cancelSavePermit(){
		$("#moduleId").val('');
		$("#actionName").val('');
		$("#actionIdList").html('');
	}
	
	function toPermitPage(moduleId){
		window.location.href="${ctx}/permit/toPermit/"+moduleId+".html?sid=${sid}";
	}
</script>
</head>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
		<a href="javascript:void(0);" class="current">模块列表</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i>
						</span>
						<h5>模块列表</h5>
						<span class="icon">
							<button class="btn btn-mini" id="addBtn" onclick="toAddModule()"><i class="icon-plus"></i></button>
							<button class="btn btn-mini" id="openSearchBtn" onclick="openSearch()"><i class="icon-search" title="搜索"></i></button>
						</span>
					</div>
					<div class="widget-content nopadding" style="display: none" id="searchBox">
						<form class="form-horizontal">
							<div class="control-group">
								<label class="control-label">模块名</label>
								<div class="controls w50p">
									<input type="text" id="moduleName" maxlength="30">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">是否父节点</label>
								<div class="controls w50p">
									<input type="radio" name="isParent" value="-1" checked="checked">所有
									<input type="radio" name="isParent" value="0">否
									<input type="radio" name="isParent" value="1">是
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">父节点</label>
								<div class="controls w50p">
									<select id="parentNodeId">
										<option value="-1"></option>
										<c:forEach items="${parentList }" var="parentNode">
											<option value="${parentNode.id }">${parentNode.moduleName }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-actions">
								<button type="button" class="btn btn-primary" onclick="selectModuleByPage(pageOfNum,defaultPage);">查询</button>
								<button type="button" class="btn" onclick="clearQueryInfo();">清空</button>
							</div>
						</form>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered data-table" id="moduleList">
							<thead>
								<tr>
									<th>序号</th>
									<th>模块id</th>
									<th>模块名</th>
									<th>模块值</th>
									<th>访问路径</th>
									<th>创建时间</th>
									<th>是否父节点</th>
									<th>上级模块id</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					<div id="example"></div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="searchDialog" title="模块详情" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>模块详情</h3>
		</div>
		<div class="modal-body">
			<form class="form-horizontal">
			<div class="control-group">
				<label class="control-label">模块名</label>
				<div class="controls">
					<input type="text" id="moduleNameS" readonly="readonly">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">模块值</label>
				<div class="controls">
					<input type="text" id="moduleValueS" readonly="readonly">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">访问地址</label>
				<div class="controls">
					<input type="text" id="linkUrlS" readonly="readonly">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">是否父节点</label>
				<div class="controls">
					<input type="radio" name="isParentS" disabled="disabled" value=1>是
					<input type="radio" name="isParentS" disabled="disabled" value=0>否
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">上级模块</label>
				<div class="controls">
					<input type="text" id="superModelNameS" readonly="readonly">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">模块描述</label>
				<div class="controls">
					<textarea id="moduleDescS" readonly="readonly" disabled="disabled"></textarea>
				</div>
			</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" onclick="$('#searchDialog').modal('hide')" value="关闭">
		</div>
	</div>
	
	<div id="setPermitDialog" title="权限设置" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>权限设置</h3>
		</div>
		<div class="modal-body">
			<form method="post" class="form-horizontal">
				<input type="hidden" id="moduleId">
				<div class="control-group">
					<label class="control-label">Action名称</label>
					<div class="controls">
						<input type="text" name="actionName" id="actionName">
						<button type="button" class="btn btn-primary" id="searchRole" onclick="selectAction(10,1)">查询</button>
					</div>
				</div>
				<div class="control-group">
					<table class="table table-bordered table-hover data-table" id="actionList">
						<thead>
							<tr>
								<th>序号</th>
								<th>Action名称</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
					<div id="actionExample"></div>
				</div>
				<div class="control-group">
					<label class="control-label">已选Action</label>
					<div class="controls">
						<ul style="list-style-type: none" id="actionIdList">
						</ul>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" id="closePermitBtn" onclick="cancelSavePermit()" value="取消">
    		<input type="button" class="btn btn-primary" id="savePermitBtn" onclick="savePermit()" value="保存">
		</div>
	</div>
</body>
</html>
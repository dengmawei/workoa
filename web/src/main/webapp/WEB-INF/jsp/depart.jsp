<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="${ctx }/js/jquery_ztree/jquery.ztree.core-3.5.js"></script>
<link rel="stylesheet" href="${ctx }/js/jquery_ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${ctx }/js/jquery-validation-1.8.1/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx }/js/Jquery-Validate-For-Bootstrap-master/jquery.validate.bootstrap.js"></script>
<script type="text/javascript" src="${ctx }/js/twitter-bottstrap/js/bootstrap-modal.js"></script>
<script type="text/javascript">
	var setting = {
		treeId:"",
		treeObj:null,
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false
		},
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: ""
			}
		},
		callback:{
			onClick:loadDepart
		}
	};
	
	var settingAdd = {
			treeId:"",
			treeObj:null,
			view: {
				dblClickExpand: false,
				showLine: true,
				selectedMulti: false
			},
			data: {
				simpleData: {
					enable:true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: ""
				}
			},
			callback:{
				onDblClick:chooseDepart
			}
		};
	
	var settingEdit = {
			treeId:"",
			treeObj:null,
			view: {
				dblClickExpand: false,
				showLine: true,
				selectedMulti: false
			},
			data: {
				simpleData: {
					enable:true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: ""
				}
			},
			callback:{
				onDblClick:chooseEditDepart
			}
		};
	
	$(document).ready(function(){
		initCompany();
		
		$("#addCompany").click(function(){
			addCompany();
		});
		
		$("#openCompanyButton").click(function(){
			$("#addCompanyDiv").show();
		});
		
		$("#cancelCompany").click(function(){
			$("#addCompanyDiv").hide();
			$("#companyName").val("");
			$("#companyCode").val("");
		});
		
		$("#openDepartBtn").click(function(){
			initCompanyListOfAdd();
			$('#addDepartDialog').modal('show');
		});
		
		//初始化添加部门的表单
		$('#addDepartDialog').modal({
			keyboard:true,
			show:false	
		});
		
		//初始化编辑部门的表单
		$('#updateDepartDialog').modal({
			keyboard:true,
			show:false	
		});
		
		$.validator.addMethod("departName_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'部门名称不能为空');
		
		$.validator.addMethod("departCode_required",function(value){
			var reg = /^\d{3}$/;
			if(!reg.test(value)){
				return false;
			}
			return true;
		},'部门编码格式不正确');
		
		$.validator.addMethod("company_required",function(value){
			if(value!=-1){
				return true;
			}
			return false;
		},'所属集团不能为空');
		
		$.validator.addMethod("superDepart_required",function(value){
			if(value!=null && value!=""){
				return true;
			}
			return false;
		},'上级部门不能为空');
		
		$("#addDepartForm").validate({
			success: function(element) {
				element.text('OK!').addClass('valid').closest('.control-group').removeClass('error').removeClass('success');
			},
			highlight: function(element) {
				$(element).closest('.control-group').removeClass('success').addClass('error');
			},
			rules:{
				departmentName:{
					departName_required:true
				},
				departmentCode:{
					departCode_required:true
				},
				companyList:{
					company_required:true
				},
				departSel:{
					superDepart_required:true
				}
			}
		});
		
		$("#updateDepartForm").validate({
			success: function(element) {
				element.text('OK!').addClass('valid').closest('.control-group').removeClass('error').removeClass('success');
			},
			highlight: function(element) {
				$(element).closest('.control-group').removeClass('success').addClass('error');
			},
			rules:{
				departmentName:{
					departName_required:true
				},
				departmentCode:{
					departCode_required:true
				},
				companyList:{
					company_required:true
				},
				departSel:{
					superDepart_required:true
				}
			}
		});
	});
	
	//初始化公司列表
	function initCompany(){
		$.post("${ctx}/depart/selectAllCompany.html",function(data){
			var companyList = data.data;
			if(companyList!=null && companyList!=""){
				var companyListTable = $("#companylisttable tbody");
				$(companyListTable).html("");
				var html="";
				for(var i=0;i<companyList.length;i++){
					html=html+"<tr>"+
					"<td><span id='nameSpan"+companyList[i].id+"'>"+companyList[i].companyName+"</span><div align='center' style='display:none'><input type='text' id='nameUpdate"+companyList[i].id+"' maxlength='50' class='span12' placeholder='请输入集团名称'/></div></td>"+
					"<td></td>"+
					"<td>"+
					"<div align='center'>"+
					"<button type='button' id='saveBtn"+companyList[i].id+"' style='display:none' class='btn btn-info btn-mini' onclick='saveCompany("+companyList[i].id+")'><i class='icon-ok-sign icon-white'></i>保存</button>"+
					"&nbsp;"+
					"<button type='button' id='cancelBtn"+companyList[i].id+"' style='display:none' class='btn btn-warning btn-mini' onclick='cancelSaveCompany("+companyList[i].id+")'><i class='icon-remove-sign icon-white'></i>取消</button>"+
					"&nbsp;"+
					"<button type='button' id='loadBtn"+companyList[i].id+"' class='btn btn-success btn-mini' onclick='loadCompany("+companyList[i].id+")'><i class='icon-pencil icon-white'></i>修改</button>"+
					"&nbsp;"+
					"<button type='button' id='deleteBtn"+companyList[i].id+"' class='btn btn-danger btn-mini' onClick='deleteCompany("+companyList[i].id+")'><i class='icon-remove icon-white'></i>删除</button>"+
					"&nbsp;"+
					"<button type='button' id='openBtn"+companyList[i].id+"' class='btn btn-info btn-mini' onclick='showCompanyTree("+companyList[i].id+")'><i class='icon-folder-open icon-white'></i>展开</button>"+
					"</div>"+
					"</td>"+
					"</tr> ";
				}
				$(companyListTable).append(html);
			}
		},"json");
	}
	
	//删除公司
	function deleteCompany(companyid){
		var param = {};
		param.companyid=companyid;
		
		if(window.confirm("您确定删除吗？")){
			$.post("${ctx}/depart/deleteCompany.html",param,function(data){
				if(data.success){
					showTipInfo("集团删除成功");
					initCompany();
				}else{
					showTipInfo(data.message);					
				}
			},"json");
		}
	}
	
	//添加公司
	function addCompany(){
		var param = {};
		param.companyName=$("#companyName").val();
		if(param.companyName == ""||param.companyName == null){
			showTipInfo("请输入集团名称!");
			return;
		}
		$.post("${ctx}/depart/addCompany.html",param,function(data){
			showTipInfo("集团添加成功！");
			$("#companyName").val("");
			$("#addCompanyDiv").hide();
			initCompany();
		},"json");
	}
	
	//查询公司
	function loadCompany(companyId){
		var param = {};
		param.companyid = companyId;
		$.post("${ctx}/depart/loadCompany.html",param,function(data){
			var company = data.data;
			$("#nameUpdate"+companyId).val(company.companyName);
			
			$("#nameUpdate"+companyId).parent().show();
			
			$("#nameUpdate"+companyId).parent().prev().hide();
			
			$("#saveBtn"+companyId).show();
			$("#cancelBtn"+companyId).show();
			$("#loadBtn"+companyId).hide();
			$("#openBtn"+companyId).hide();
			$("#deleteBtn"+companyId).hide();
		},"json");
	}
	
	//保存公司信息
	function saveCompany(companyid){
		var param = {};
		param.companyid = companyid;
		param.companyName = $("#nameUpdate"+companyid).val();
		
		$.post("${ctx}/depart/updateCompany.html",param,function(data){
			$("#nameSpan"+companyid).html(param.companyName);
			$("#nameSpan"+companyid).show();
			
			$("#nameUpdate"+companyid).parent().hide();
			
			$("#saveBtn"+companyid).hide();
			$("#cancelBtn"+companyid).hide();
			$("#loadBtn"+companyid).show();
			$("#openBtn"+companyid).show();
			$("#deleteBtn"+companyid).show();
			
			showTipInfo("集团修改成功！");
		},"json");
	}
	
	//取消修改集团信息
	function cancelSaveCompany(companyid){
		var companyName = $("#nameUpdate"+companyid).val();
		
		$("#nameSpan"+companyid).html(companyName);
		$("#nameSpan"+companyid).show();
		
		$("#nameUpdate"+companyid).parent().hide();
		
		$("#saveBtn"+companyid).hide();
		$("#cancelBtn"+companyid).hide();
		$("#loadBtn"+companyid).show();
		$("#openBtn"+companyid).show();
		$("#deleteBtn"+companyid).show();
	}
	
	//展现公司树
	function showCompanyTree(companyid){
		var zTree = $("#companyTree");
		var zNodes = [];
		$.post("${ctx}/depart/selectDepartByCompanyId.html",{companyId:companyid},function(data){
			if(data.data){
				var map = data.data;
				zNodes.push({id:100000,pId:0,name:map.company.companyName,open:true});
				$.each(map.departList,function(k,v){
					var treeNode = {};
					treeNode.id = v.id;
					if(v.superId==null || v.superId==""){
						treeNode.pId = 100000;
					}else{
						treeNode.pId = v.superId;
					}
					treeNode.name = v.departmentName;
					//treeNode.code = v.departmentCode;
					//treeNode.desc = v.departmentDesc;
					
					zNodes.push(treeNode);
				});
			}
			/* var zNodes =[
			 	{id:1, pId:0, name:"[core] 基本功能 演示", open:true},
			 	{id:101, pId:1, name:"最简单的树 --  标准 JSON 数据", file:"core/standardData"},
			 	{id:102, pId:1, name:"最简单的树 --  简单 JSON 数据", file:"core/simpleData"},
			 	{id:103, pId:1, name:"不显示 连接线", file:"core/noline"}
			 ]; */
			zTree = $.fn.zTree.init(zTree, setting, zNodes);
		},"json");
	}
	
	//初始化添加或修改部门
	function initCompanyListOfAdd(){
		$("#companyListAdd").html("<option value='-1'>--请选择--</option>");
		var param = selectAllCompany();
		var arr = param.data;
		if(arr!=null){
			for(var i=0;i<arr.length;i++){
				$("#companyListAdd").append("<option value='"+arr[i].id+"'>"+arr[i].companyName+"</option>");
			}
		}
	}
	
	//查询所有的集团
	function selectAllCompany(){
		var param = [];
		$.ajax({
			url:"${ctx}/depart/selectAllCompany.html",
			async:false,
			dataType:"json",
			type:"post",
			success:function(data){
				param = data;
			}
		});
		return param;
	}
	
	//单击查询部门信息
	function loadDepart(e,treeId,treeNode){
		if(treeNode.id==100000){
			showTipInfo("当前节点为集团");
			return;
		}
		$.post("${ctx}/depart/selectDepartById.html",{id:treeNode.id},function(data){
			if(data.success){
				var depart = data.data;
				$("#departCode").html(depart.departmentCode);
				$("#departName").html(depart.departmentName);
				$("#departDesc").html(depart.departmentDesc);
				$("#superDepartName").html(depart.superDepartmentName);
				$("#superCompanyName").html(depart.companyName);
				$("#editDepartDiv").html('<button class="btn btn-primary btn-mini" onclick="loadEditDepartInfo('+depart.id+')" title="编辑"><i class="icon-edit"></i></button>&nbsp;'
						+'<button class="btn btn-primary btn-mini" onclick="deleteDepart('+depart.id+')" title="删除"><i class="icon-trash"></i></button>');
			}else{
				showTipInfo(data.message);
			}
		},"json");
	}
	
	function showSelectMenu() {
		var departTreeObj = $("#departSelAdd");
		var departOffset = $("#departSelAdd").offset();
		$("#menuContentAdd").css({left:departOffset.left + "px", top:departOffset.top + departTreeObj.outerHeight() + "px"}).slideDown("fast");
		
		var companyId = $("#companyListAdd").val();
		if(companyId==-1){
			showTipInfo("请先选择集团");
			return;
		}
		showSelTree(companyId,$("#treeDemo"));
		$("body").bind("mousedown", onBodyDown);
	}
	
	function showSelTree(companyid,zTree){
		var zNodes = [];
		$.post("${ctx}/depart/selectDepartByCompanyId.html",{companyId:companyid},function(data){
			if(data.data){
				var map = data.data;
				zNodes.push({id:100000,pId:0,name:map.company.companyName,open:true});
				$.each(map.departList,function(k,v){
					var treeNode = {};
					treeNode.id = v.id;
					if(v.superId==null || v.superId==""){
						treeNode.pId = 100000;
					}else{
						treeNode.pId = v.superId;
					}
					treeNode.name = v.departmentName;
					treeNode.title = v.departmentName;
					//treeNode.code = v.departmentCode;
					//treeNode.desc = v.departmentDesc;
					
					zNodes.push(treeNode);
				});
			}
			zTree = $.fn.zTree.init(zTree, settingAdd, zNodes);
		},"json");
	}
	
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContentAdd" || $(event.target).parents("#menuContentAdd").length>0)) {
			hideMenu();
		}
	}
	
	function hideMenu() {
		$("#menuContentAdd").fadeOut("fast");
		$("#menuContentUpdate").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	
	//双击选择上级部门
	function chooseDepart(e,treeId,treeNode){
		if(treeNode.id==100000){
			$("#superDepartIdAdd").val(0);
			$("#departSelAdd").val("无上级部门");
		}else{
			$("#superDepartIdAdd").val(treeNode.id);
			$("#departSelAdd").val(treeNode.name);
		}
	}
	
	//保存单位信息
	function saveDepartment(){
		if($("#addDepartForm").validate().form()){
			if(window.confirm("您确定提交？")){
				subAddDepartParam();
			}
		}
		
	}
	
	function subAddDepartParam(){
		var param = {};
		param.departmentName = $("#departNameAdd").val();
		param.departmentCode = $("#departCodeAdd").val();
		param.departmentDesc = $("#departDescAdd").val();
		param.companyId = $("#companyListAdd").val();
		param.superId = $("#superDepartIdAdd").val();
		$.post("${ctx}/depart/addDepart.html",param,function(data){
			if(data.success){
				showTipInfo(data.message);
				showCompanyTree(param.companyId);
				cancelSaveDepart();
				$('#addDepartDialog').modal('hide');
			}else{
				showTipInfo(data.message);
			}
		},"json");
	}
	
	
	function cancelSaveDepart(){
		$("#departNameAdd").val("");
		$("#departCodeAdd").val("");
		$("#superDepartIdAdd").val("");
		$("#departSelAdd").val("");
		$("#companyListAdd").html('<option value="-1">--请选择--</option>');
		$("#departDescAdd").val("");
		$('#addDepartDialog').modal('hide');
	}
	
	function loadEditDepartInfo(departId){
		$.post("${ctx}/depart/selectDepartById.html",{id:departId},function(data){
			if(data.success){
				var depart = data.data;
				$("#departCodeUpdate").val(depart.departmentCode);
				$("#departNameUpdate").val(depart.departmentName);
				$("#departDescUpdate").val(depart.departmentDesc);
				$("#companyListUpdate").val(depart.companyId);
				$("#departSelUpdate").val(depart.superDepartmentName);
				$("#superDepartIdUpdate").val(depart.superId);
				$("#departIdUpdate").val(depart.id);
				
				var param = selectAllCompany();
				var arr = param.data;
				if(arr!=null){
					for(var i=0;i<arr.length;i++){
						if(depart.companyId==arr[i].id){
							$("#companyListUpdate").append("<option value='"+arr[i].id+"' selected='selected'>"+arr[i].companyName+"</option>");
						}else{
							$("#companyListUpdate").append("<option value='"+arr[i].id+"'>"+arr[i].companyName+"</option>");
						}
					}
				}
				
				$('#updateDepartDialog').modal('show');
			}else{
				showTipInfo(data.message);
			}
		},"json");
	}
	
	function showEditDepartTree(){
		var departTreeObj = $("#departSelUpdate");
		var departOffset = $("#departSelUpdate").offset();
		$("#menuContentUpdate").css({left:departOffset.left + "px", top:departOffset.top + departTreeObj.outerHeight() + "px"}).slideDown("fast");
		
		var companyId = $("#companyListUpdate").val();
		if(companyId==-1){
			showTipInfo("请先选择集团");
			return;
		}
		showEditTree(companyId,$("#treeDemoUpdate"));
		$("body").bind("mousedown", onEditBodyDown);
	}
	
	function showEditTree(companyid,zTree){
		var zNodes = [];
		$.post("${ctx}/depart/selectDepartByCompanyId.html",{companyId:companyid},function(data){
			if(data.data){
				var map = data.data;
				zNodes.push({id:100000,pId:0,name:map.company.companyName,open:true});
				$.each(map.departList,function(k,v){
					var treeNode = {};
					treeNode.id = v.id;
					if(v.superId==null || v.superId==""){
						treeNode.pId = 100000;
					}else{
						treeNode.pId = v.superId;
					}
					treeNode.name = v.departmentName;
					treeNode.title = v.departmentName;
					//treeNode.code = v.departmentCode;
					//treeNode.desc = v.departmentDesc;
					
					zNodes.push(treeNode);
				});
			}
			zTree = $.fn.zTree.init(zTree, settingEdit, zNodes);
		},"json");
	}
	
	function onEditBodyDown(event) {
		if (!(event.target.id == "menuBtnEdit" || event.target.id == "menuContentUpdate" || $(event.target).parents("#menuContentUpdate").length>0)) {
			hideMenuEdit();
		}
	}
	
	function hideMenuEdit() {
		$("#menuContentUpdate").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	
	//双击选择上级部门
	function chooseEditDepart(e,treeId,treeNode){
		if(treeNode.id==100000){
			$("#superDepartIdUpdate").val(0);
			$("#departSelUpdate").val("无上级部门");
		}else{
			$("#superDepartIdUpdate").val(treeNode.id);
			$("#departSelUpdate").val(treeNode.name);
		}
	}
	
	function cancelEditDepart(){
		$("#departNameUpdate").val("");
		$("#departCodeUpdate").val("");
		$("#superDepartIdUpdate").val("");
		$("#departSelUpdate").val("");
		$("#companyListUpdate").html('<option value="-1">--请选择--</option>');
		$("#departDescUpdate").val("");
		$('#updateDepartDialog').modal('hide');
	}
	
	function editDepartment(){
		var param = {};
		param.id = $("#departIdUpdate").val();
		param.departmentCode = $("#departCodeUpdate").val();
		param.departmentDesc = $("#departDescUpdate").val();
		param.departmentName = $("#departNameUpdate").val();
		param.superId = $("#superDepartIdUpdate").val();
		param.companyId = $("#companyListUpdate").val();
		$.post("${ctx}/depart/updateDepart.html",param,function(data){
			if(data.success){
				showTipInfo("部门编辑成功");
				showCompanyTree($("#companyListUpdate").val());
				$('#updateDepartDialog').modal('hide');
			}else{
				showTipInfo(data.message);
			}
		},"json");
	}
	
	function deleteDepart(departId){
		if(window.confirm("您确定删除？")){
			$.post("${ctx}/depart/deleteDepart.html",{departId:departId},function(data){
				if(data.success){
					showTipInfo("删除成功");
					showCompanyTree($("#companyListUpdate").val());
				}else{
					showTipInfo(data.message);
				}
			},"json");
		}
	}
</script>
</head>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
		<a href="javascript:void(0)" class="current">部门管理</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"> <i class="icon-th"></i>
						</span>
						<h5>集团列表</h5>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered table-striped" id="companylisttable">
							<thead>
								<tr>
									<th width="40%">集团名称</th>
									<th width="40%"></th>
									<th width="20%">
										<button class="btn btn-primary btn-mini" id="openCompanyButton" title="添加"><i class="icon-plus"></i></button>
									</th>
								</tr>
								<tr style="display: none;" id="addCompanyDiv">
									<td>
										<div align="center">
											<input type="text" id="companyName" name="companyName" maxlength="50" class="span12" placeholder="请输入集团名称"/>
										</div>
									</td>
									<td>
									</td>
									<td>
										<div align="center">
											<button type="button" class="btn btn-success btn-mini" id="addCompany"><i class="icon-ok-sign icon-white"></i>保存</button>
											<button type="button" class="btn btn-danger btn-mini" id="cancelCompany"><i class="icon-remove-sign icon-white"></i>取消</button>
										</div>
									</td>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="row-fluid">
			<div class="span4">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"><i class="icon-th"></i></span>
						<h5>集团树</h5>
					</div>
					<div class="widget-content nopadding">
						<ul id="companyTree" class="ztree" style="width:260px; overflow:auto;"></ul>
					</div>
				</div>
			</div>
			<div class="span8">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon"><i class="icon-th"></i></span>
						<h5>部门</h5>
						<span class="icon"><button class="btn btn-primary btn-mini" id="openDepartBtn" title="添加新部门"><i class="icon-plus"></i></button></span>
					</div>
					<div class="widget-content nopadding">
						<table class="table table-bordered">
							<tr>
								<td class="span2">部门编号</td>
								<td><div id="departCode"></div></td>
							</tr>
							<tr>
								<td>部门名称</td>
								<td><div id="departName"></div></td>
							</tr>
							<tr>
								<td>上级部门</td>
								<td><div id="superDepartName"></div></td>
							</tr>
							<tr>
								<td>所属集团</td>
								<td><div id="superCompanyName"></div></td>
							</tr>
							<tr>
								<td>职能描述</td>
								<td><div id="departDesc"></div></td>
							</tr>
							<tr>
								<td>操作</td>
								<td>
									<div id="editDepartDiv">
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="addDepartDialog" title="添加部门" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>新增部门</h3>
		</div>
		<div class="modal-body">
			<form action="#" id="addDepartForm" method="post" class="form-horizontal">
				<div class="control-group">
					<label class="control-label">部门名称</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"></span>
							<input type="text" name="departmentName" id="departNameAdd" maxlength="50" placeholder="请输入部门名称"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">部门编码</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"></span>
							<input type="text" name="departmentCode" id="departCodeAdd" maxlength="3" placeholder="请输入部门编码"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">所属集团</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"></span>
							<select id="companyListAdd" name="companyList">
								<option value="-1">--请选择--</option>
							</select>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">上级部门</label>
					<div class="controls" id="superDepartAdd">
						<input type="hidden" id="superDepartIdAdd">
						<div class="input-prepend">
							<span class="add-on"></span>
							<input id="departSelAdd" name="departSel" type="text" value="" readonly="readonly" style="width:120px;"/>&nbsp;<input type="button" class="btn btn-primary" id="menuBtn" onclick="showSelectMenu()" value="选择">
						</div>
						<div id="menuContentAdd" style="display:none">
							<ul id="treeDemo" class="ztree" style="margin-top:0; width:100px;"></ul>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">部门描述</label>
					<div class="controls">
						<textarea id="departDescAdd" name="departmentDesc" style="resize:none"></textarea>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" id="cancelSaveBtn" onclick="cancelSaveDepart();" value="取消">
    		<input type="button" class="btn btn-primary" id="saveDepartBtn" onclick="saveDepartment()" value="保存">
		</div>
	</div>
	
	<div id="updateDepartDialog" title="编辑部门" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>编辑部门</h3>
		</div>
		<div class="modal-body">
			<form action="#" id="updateDepartForm" method="post" class="form-horizontal">
				<input type="hidden" id="departIdUpdate">
				<div class="control-group">
					<label class="control-label">部门名称</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"></span>
							<input type="text" name="departmentName" id="departNameUpdate" maxlength="50" placeholder="请输入部门名称"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">部门编码</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"></span>
							<input type="text" name="departmentCode" id="departCodeUpdate" maxlength="3" placeholder="请输入部门编码"/>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">所属集团</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"></span>
							<select id="companyListUpdate" name="companyList">
								<option value="-1">--请选择--</option>
							</select>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">上级部门</label>
					<div class="controls" id="superDepartUpdate">
						<input type="hidden" id="superDepartIdUpdate">
						<div class="input-prepend">
							<span class="add-on"></span>
							<input id="departSelUpdate" name="departSel" type="text" value="" readonly="readonly" style="width:120px;"/>&nbsp;<input type="button" class="btn btn-primary" id="menuBtnEdit" onclick="showEditDepartTree()" value="选择">
						</div>
						<div id="menuContentUpdate" style="display:none">
							<ul id="treeDemoUpdate" class="ztree" style="margin-top:0; width:100px;"></ul>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">部门描述</label>
					<div class="controls">
						<textarea id="departDescUpdate" name="departmentDesc" style="resize:none"></textarea>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" onclick="cancelEditDepart();" value="取消">
    		<input type="button" class="btn btn-primary" onclick="editDepartment()" value="保存">
		</div>
	</div>
</body>
</html>
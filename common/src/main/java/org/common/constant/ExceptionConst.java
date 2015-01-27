package org.common.constant;

public enum ExceptionConst {
	PARAM_EXCEPTION(1000,"参数异常"),
	
	
	
	USER_NOT_FOUND(20001,"用户不存在"),
	USER_NOT_PASS(20002,"用户认证失败"),
	USER_CODE_EXIST(20003,"用户编码已存在"),
	USER_EMAIL_EXIST(20004,"用户邮箱已存在"),
	USER_NAME_EXIST(20005,"用户名已存在"),
	USER_LOGIN_ERROR(20006,"用户登录失败"),
	USER_PASS_ERROR(20007,"用户密码错误"),
	
	COMPANY_NOT_FOUND(20051,"集团不存在"),
	DEPART_NOT_FOUND(20052,"部门不存在"),
	SUPER_DEPART_NOT_FOUND(20053,"上级部门不存在"),
	COMPANY_CAN_NOT_DELETE(20054,"集团不能删除"),
	DEPART_CODE_EXIST(20055,"部门编码已存在"),
	SUPERDEPART_CANNOT_BESELF(20056,"上级部门不能是自己"),
	SUPERDEPART_CANNOT_SUBDEPART(20057,"上级部门不能是自己的下级部门"),
	DEPART_USER_EXIST(20058,"部门下存在用户"),
	SUBDEPART_EXIST(20059,"部门下存在子部门"),
	
	ROLE_NOT_FOUND(20102,"角色不存在"),
	ROLE_USER_EXIST(20103,"角色下存在用户"),
	ROLE_PERMIT_EXIST(20104,"角色下存在权限"),
	ROLE_NAME_EXIST(20105,"角色名已存在"),
	
	MODULE_NOT_EXIST(20201,"模块不存在"),
	MODULE_BE_SUPERMODULE(20202,"当前模块为父级模块，不能设置为子模块"),
	MODULE_EXIST_SUBMODULE(20203,"当前模块存在子模块"),
	MODULE_VALUE_EXIST(20204,"模块值已存在"),
	MODULE_SUPERMODULE_CANNOT_BESELF(20205,"父模块不能是自己"),
	MODULE_PERMIT_EXIST(20205,"存在与该模块相关的权限"),
	MODULE_SUPER_NOT_EXIST(20206,"父模块不存在"),
	
	PERMIT_HAS_EXIST(20301,"权限已存在"),
	PERMIT_ROLE_EXIST(20302,"有角色与该权限关联"),
	
	ACTION_PERMIT_EXIST(20401,"存在与该Action相关的权限"),
	
	SCHEDULE_TIME_ZONE_CONFLICT(20501,"日程时间范围冲突"),
	SCHEDULE_PARTICIPANTS_NOT_NULL(20502,"参与工作的人员不能为空"),
	SCHEDULE_CANNOT_REMOVE(20503,"该日程不可以删除"),
	
	OVERTIME_TIMEZONE_OCNFLICT(20601,"加班日程时间范围冲突"),
	OVERTIME_NOT_EXIST(20602,"加班流程不存在"),
	
	PROCESS_INSTANCE_NOT_EXIST(30001,"流程实例不存在，请联系管理员"),
	EXECUTION_NOT_EXIST(30002,"执行实例不存在，请联系管理员"),
	EXECUTION_SINAL_FAILED(30003,"流程执行失败,请联系管理员"),
	TASK_NOT_EXIST(30004,"任务不存在"),
	PROCESS_NOT_EXIST(30005,"流程不存在"),
	
	UPLOAD_PICTURE_MAX_ERR(90001,"上传图片不能大于5M"),
	PICTURE_FORMAT_ERR(90002,"图片格式不正确"),
	
	
	
	SYSTEM_ERR(100000,"系统异常");
	public final int code;
	
	public final String message;
	
	private ExceptionConst(int code,String message){
		this.code = code;
		this.message = message;
	}
}

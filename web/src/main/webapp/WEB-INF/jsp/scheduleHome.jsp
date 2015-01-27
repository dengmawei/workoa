<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link href="${ctx}/css/fullcalendar.css" rel="stylesheet" />
	<script src="${ctx}/js/fullcalendar.js"></script>
	<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.min.css">
	<script src="${ctx}/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			var date = new Date();
			var d = date.getDate();
			var m = date.getMonth();
			var y = date.getFullYear();
			
			var calendar = $('#calendar').fullCalendar({
	            header: {
	                left: 'title',
	                center: '',
	                //right: 'prevYear,prev,today,month,basicWeek,basicDay,agendaWeek,agendaDay,next,nextYear'
	                right: 'prevYear,prev,today,month,basicWeek,basicDay,next,nextYear'
	            },
	            buttonText:{
	            	today:    '今天',
	            	month:    '月',
	            	week:     '周',
	            	day:      '日',
	            	prevYear:'上一年',
	            	nextYear:'下一年'
	            	
	            },
	            monthNames:['01','02', '03', '04', '05', '06', '07','08', '09', '10', '11', '12'],
	            monthNamesShort:['01','02', '03', '04', '05', '06', '07','08', '09', '10', '11', '12'],
	            dayNames:['星期日', '星期一', '星期二', '星期三','星期四', '星期五', '星期六'],
	            dayNamesShort:['星期日', '星期一', '星期二', '星期三','星期四', '星期五', '星期六'],
	            timeFormat:'HH:mm',
	            titleFormat:{
	            	month:'yyyy/MMMM',
	            	week:'yyyy/MMMM/dd{ - [yyyy/MMMM/]dd }',
	            	day:'yyyy/MMMM/dd,dddd'
	            },
	            allDayDefault:true,
	            events:function(start,end,callback){
	            	var startTime = getDateString(start);
	            	var endTime = getDateString(end);
	            	
	            	$.post("${ctx}/schedule/selectScheduleByCondition.html",{start:startTime,end:endTime},function(data){
	            		if(data.success){
	            			callback(formatScheduleEvent(data.data));
	            		}
	            	},"json");
	            },
	            dayClick:function(date, allDay, jsEvent, view) {
	            	var curDate = new Date();
	            	curDate.setHours(0);
	            	curDate.setMinutes(0);
	            	curDate.setSeconds(0);
	            	curDate.setMilliseconds(0);
	            	if(date<curDate){
	            		showTipInfo("您当前选择的日期无效！");
	            		return;
	            	}
	            	var year = date.getFullYear();
	            	var month = date.getMonth()+1;
	            	if(month<10){
	            		month = "0"+month;
	            	}
	            	var day = date.getDate();
	            	if(day<10){
	            		day = "0"+day;
	            	}
	            	var hour = new Date().getHours();
	            	if(hour<10){
	            		hour = "0"+hour;
	            	}
	            	var minutes = new Date().getMinutes();
	            	if(minutes<10){
	            		minutes = "0"+minutes;
	            	}
	            	
	            	var startTime = year+"-"+month+"-"+day+" "+hour+":"+minutes;
	            	clearScheduleDialog();
	            	$("#scheduleStartTime").val(startTime);
	            	$("#addScheduleDialog").modal("show");
	            },
	            eventClick:function(event, jsEvent, view){
	            	var scheduleId = event.id;
	            	selectScheduleById(scheduleId);
	            },
	            eventMouseover:function(event, jsEvent, view){
	            },
	           	/* droppable: true, // this allows things to be dropped onto the calendar !!!
	            drop: function(date, allDay) { // this function is called when something is dropped

	                    // retrieve the dropped element's stored Event Object
	                    var originalEventObject = $(this).data('eventObject');

	                    // we need to copy it, so that multiple events don't have a reference to the same object
	                    var copiedEventObject = $.extend({}, originalEventObject);

	                    // assign it the date that was reported
	                    copiedEventObject.start = date;
	                    copiedEventObject.allDay = allDay;

	                    // render the event on the calendar
	                    // the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
	                    $('#calendar').fullCalendar('renderEvent', copiedEventObject, true);

	                    // is the "remove after drop" checkbox checked?
	                    if ($('#drop-remove').is(':checked')) {
	                        // if so, remove the element from the "Draggable Events" list
	                    	$(this).remove();
	                    }

	            }, 
	           	selectable: true,
	            selectHelper: true,
	            select: function(start, end, allDay) {
	            	var title = prompt('请输入事件名称:');
	                if (title) {
	                	calendar.fullCalendar('renderEvent',
	                                    {
	                                            title: title,
	                                            start: start,
	                                            end: end,
	                                            allDay: allDay
	                                    },
	                                    true // make the event "stick"
	                            );
	                    }
	                    calendar.fullCalendar('unselect');
	            },  */
	           	editable: true
	        });
			
			$.validator.addMethod("scheduleTitle_required",function(value){
				if(value!=null && value!=""){
					return true;
				}
				return false;
			},'日程标题不能为空');
			
			$.validator.addMethod("scheduleTitle_format",function(value){
				if(value.length<50){
					return true;
				}
				return false;
			},'日程标题不能超过50');
			
			$.validator.addMethod("scheduleContent_required",function(value){
				if(value!=null && value!=""){
					return true;
				}
				return false;
			},'详细内容不能为空');
			
			$.validator.addMethod("scheduleContent_format",function(value){
				if(value.length<500){
					return true;
				}
				return false;
			},'详细内容长度不能超过500');
			
			$.validator.addMethod("scheduleStartTime_required",function(value){
				if(value!=null && value!=""){
					return true;
				}
				return false;
			},'开始时间不能为空');
			
			$.validator.addMethod("scheduleEndTime_required",function(value){
				if(value!=null && value!=""){
					return true;
				}
				return false;
			},'结束时间不能为空');
			
			$("#scheduleForm").validate({
				success: function(element) {
					element.text('OK!').addClass('valid').closest('.control-group').removeClass('error').removeClass('success');
				},
				highlight: function(element) {
					$(element).closest('.control-group').removeClass('success').addClass('error');
				},
				rules:{
					scheduleTitle:{
						scheduleTitle_required:true,
						scheduleTitle_format:true
					},
					scheduleContent:{
						scheduleContent_required:true,
						scheduleContent_format:true
					},
					scheduleStartTime:{
						scheduleStartTime_required:true
					},
					scheduleEndTime:{
						scheduleEndTime_required:true
					}
				}
			});
		
			$("#addScheduleDialog").modal({
				keyboard:true,
				show:false	
			});

			$("#showScheduleDialog").modal({
				keyboard:true,
				show:false	
			});
			
			$('#datetimepicker-start').datetimepicker({
				weekStart: 1,
				startDate:new Date(),
				//daysOfWeekDisabled:[0,6],
				autoclose:true,
				startView:'month',
				minView:'hour',
				maxView:'year',
				todayBtn:true,
				todayHighlight:true,
				keyboardNavigation:true,
				language:'en',
				forceParse:true,
				minuteStep:10,
				initialDate:new Date()
		    });
			
			$('#datetimepicker-end').datetimepicker({
				weekStart: 1,
				startDate:new Date(),
				//daysOfWeekDisabled:[0,6],
				autoclose:true,
				startView:'month',
				minView:'hour',
				maxView:'year',
				todayBtn:true,
				todayHighlight:true,
				keyboardNavigation:true,
				language:'en',
				forceParse:true,
				minuteStep:10,
				initialDate:new Date()
		    });
		});
		
		function formatScheduleEvent(data){
			var events = [];
			if(data!=null && data.length>0){
				for(var i=0;i<data.length;i++){
					var obj = {};
					obj.id=data[i].id;
					obj.title=data[i].title;
					obj.start=new Date(data[i].startYear, data[i].startMonth-1, data[i].startDay, data[i].startHour, data[i].startMinute);
					obj.end=new Date(data[i].endYear, data[i].endMonth-1, data[i].endDay, data[i].endHour, data[i].endMinute);
                    obj.allDay=false;
                    events.push(obj);
				}
			}
			return events;
		}
		
		function clearExtraContent(objId,limitLen){
			var contentObj = $("#"+objId);
			if(contentObj.val().length<=limitLen){
				return;
			}else{
				contentObj.val(contentObj.val().substr(0,limitLen));
			}
		}
		
		function subSchedule(){
			if($("#scheduleForm").validate().form() && window.confirm("您确认提交？")){
				var param = {};
				param.title = $("#scheduleTitle").val();
				param.content = $("#scheduleContent").val();
				param.scheduleStartTime = $("#scheduleStartTime").val();
				param.scheduleEndTime = $("#scheduleEndTime").val();
				param.personal = $("#isPrivate").is(":checked")?1:0;
				param.flag = $("#flag").is(":checked")?1:0;
				$.post("${ctx}/schedule/saveSchedule.html",param,function(data){
					if(data.success){
						$("#addScheduleDialog").modal("hide");
						showTipInfo("提交成功");
					}else{
						alert(data.message);
					}
				},"json");
			}
		}
		
		function clearScheduleDialog(){
			$("#scheduleTitle").val("");
			$("#scheduleContent").val("");
			$("#scheduleStartTime").val("");
			$("#scheduleEndTime").val("");
			$("#isPrivate").prop("checked",true);
			$("#flag").prop("checked",false);
		}
		
		function showScheduleDialog(){
			clearScheduleDialog();
			$("#addScheduleDialog").modal("show");
		}
		
		function getDateString(date){
			var year = date.getFullYear();
        	var month = date.getMonth()+1;
        	if(month<10){
        		month = "0"+month;
        	}
        	var day = date.getDate();
        	if(day<10){
        		day = "0"+day;
        	}
        	var hour = date.getHours();
        	if(hour<10){
        		hour = "0"+hour;
        	}
        	var minutes = date.getMinutes();
        	if(minutes<10){
        		minutes = "0"+minutes;
        	}
        	var seconds = date.getSeconds();
        	if(seconds<10){
        		seconds = "0"+seconds;
        	}
        	
        	return year+"-"+month+"-"+day+" "+hour+":"+minutes+":"+seconds;
		}
		
		function selectScheduleById(id){
			$.post("${ctx}/schedule/selectScheduleById/"+id+".html",{},function(data){
				if(data.success){
					clearScheduleInfoDialog();
					
					var shcedule = data.data;
					$("#scheduleTitleInfo").val(shcedule.title);
					$("#scheduleStartTimeInfo").val(shcedule.startTime);
					$("#scheduleEndTimeInfo").val(shcedule.endTime);
					$("#scheduleContentInfo").val(shcedule.content);
					if(shcedule.personal==1){
						$("#isPrivateInfo").prop("checked",true);
					}else{
						$("#isPrivateInfo").prop("checked",false);
					}
					if(shcedule.flag==1){
						$("#flagInfo").prop("checked",true);
					}else{
						$("#flagInfo").prop("checked",false);
					}
					
					$("#showScheduleDialog").modal("show");
				}
			},"json");
		}
		
		function clearScheduleInfoDialog(){
			$("#scheduleTitleInfo").val("");
			$("#scheduleStartTimeInfo").val("");
			$("#scheduleEndTimeInfo").val("");
			$("#scheduleContentInfo").val("");
			$("#isPrivateInfo").prop("checked",true);
			$("#flagInfo").prop("checked",false);
		}
		
		function addParticipant(){
			
		}
	</script>
</head>
<body>
	<div id="breadcrumb">
		<a href="${ctx }/index.html" title="返回首页" class="tip-bottom"><i class="icon-home"></i>首页</a>
		<a href="javascript:void(0);" class="current">工作日历</a>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="widget-box widget-calendar">
					<div class="widget-title">
						<span class="icon"><i class="icon-calendar"></i></span>
						<h5>Calendar</h5>
						<div class="buttons">
							<a id="add-event" onclick="showScheduleDialog()" class="btn btn-success btn-mini"><i class="icon-plus icon-white"></i>添加日程</a>
						</div>
					</div>
					<div class="widget-content nopadding">
						<div class="panel-left">
							<div id="calendar"></div>
						</div>
						<div id="external-events" class="panel-right">
							<div class="panel-title clearfix">
								<h5>我的个人日程</h5>
								<span><a href="${ctx }/schedule/more.html?sid=29">更多...</a></span>
							</div>
							<div class="panel-content" id="myPersonalEvents">
								<c:forEach items="${myPersonalEvents }" var="personalEvent" varStatus="step">
									<c:if test="${step.index%2==0 }">
										<div class="external-event ui-draggable label label-inverse w100p mt2 wordTooLong" title="${personalEvent.title }"><a href="javascript:void(0)" onclick="selectScheduleById(${personalEvent.id})">${personalEvent.title }</a></div>
									</c:if>
									<c:if test="${step.index%2==1 }">
										<div class="external-event ui-draggable label w100p mt2 wordTooLong" title="${personalEvent.title }"><a href="javascript:void(0)" onclick="selectScheduleById(${personalEvent.id})">${personalEvent.title }</a></div>
									</c:if>
								</c:forEach>
							</div>
							<div class="panel-title clearfix">
								<h5>我的工作日程</h5>
								<span><a href="${ctx }/schedule/more.html?sid=29">更多...</a></span>
							</div>
							<div class="panel-content" id="myWorkEvents">
								<c:forEach items="${myWorkEvents }" var="workEvent" varStatus="step">
									<c:if test="${step.index%2==0 }">
										<div class="external-event ui-draggable label label-inverse w100p mt2 wordTooLong" title="${workEvent.title }"><a href="javascript:void(0)" onclick="selectScheduleById(${workEvent.id})">${workEvent.title }</a></div>
									</c:if>
									<c:if test="${step.index%2==1 }">
										<div class="external-event ui-draggable label w100p mt2 wordTooLong" title="${workEvent.title }"><a href="javascript:void(0)" onclick="selectScheduleById(${workEvent.id})">${workEvent.title }</a></div>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="addScheduleDialog" title="添加日程" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>添加日程</h3>
		</div>
		<div class="modal-body">
			<form method="post" class="form-horizontal" id="scheduleForm">
				<div class="control-group">
					<label class="control-label">日程标题</label>
					<div class="controls">
						<div class="input-append">
							<input type="text" name="scheduleTitle" id="scheduleTitle" maxlength="50" placeholder="请输入日程标题"/>
							<span class="add-on"><i class="icon-pencil"></i></span>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">开始时间</label>
					<div class="controls">
						<div class="input-append date" id="datetimepicker-start" data-date-format="yyyy-mm-dd hh:ii">
						    <input class="span2" type="text" name="scheduleStartTime" id="scheduleStartTime">
						    <span class="add-on"><i class="icon-th"></i></span>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">结束时间</label>
					<div class="controls">
						<div class="input-append date" id="datetimepicker-end" data-date-format="yyyy-mm-dd hh:ii">
						    <input class="span2" type="text" name="scheduleEndTime" id="scheduleEndTime">
						    <span class="add-on"><i class="icon-th"></i></span>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">个人使用</label>
					<div class="controls">
						<div class="input-append">
						    <input type="checkbox" name="isPrivate" checked="checked" id="isPrivate" value="1">
						</div>
					</div>
				</div>
				<div class="control-group hidden">
					<label class="control-label">参与人员</label>
					<div class="controls">
						<button class="btn btn-primary" onclick="addParticipant()">添加</button>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">重要</label>
					<div class="controls">
						<div class="input-append">
						    <input type="checkbox" name="flag" id="flag">
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">详细内容</label>
					<div class="controls">
						<div class="input-prepend">
							<textarea rows="5" cols="100" name="scheduleContent" id="scheduleContent" placeholder="请输入内容" onkeyup="clearExtraContent('scheduleContent',500)"></textarea>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" id="closeBtn" onclick="$('#addScheduleDialog').modal('hide')" value="取消">
    		<input type="button" class="btn btn-primary" id="saveBtn" onclick="subSchedule()" value="保存">
		</div>
	</div>
	
	<div id="showScheduleDialog" title="日程详情" class="modal hide fade">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
			<h3>日程详情</h3>
		</div>
		<div class="modal-body">
			<form method="post" class="form-horizontal" id="scheduleForm">
				<div class="control-group">
					<label class="control-label">日程标题</label>
					<div class="controls">
						<div class="input-append">
							<input type="text" id="scheduleTitleInfo" maxlength="50" readonly="readonly"/>
							<span class="add-on"><i class="icon-pencil"></i></span>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">开始时间</label>
					<div class="controls">
						<div class="input-append date">
						    <input class="span2" type="text" id="scheduleStartTimeInfo" readonly="readonly">
						    <span class="add-on"><i class="icon-th"></i></span>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">结束时间</label>
					<div class="controls">
						<div class="input-append date">
						    <input class="span2" type="text" id="scheduleEndTimeInfo" readonly="readonly">
						    <span class="add-on"><i class="icon-th"></i></span>
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">个人使用</label>
					<div class="controls">
						<div class="input-append">
						    <input type="checkbox" id="isPrivateInfo" disabled="disabled">
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">重要</label>
					<div class="controls">
						<div class="input-append">
						    <input type="checkbox" id="flagInfo" disabled="disabled">
						</div>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label">详细内容</label>
					<div class="controls">
						<div class="input-prepend">
							<textarea rows="5" cols="100" id="scheduleContentInfo" readonly="readonly"></textarea>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<input type="button" class="btn" id="closeBtn" onclick="$('#showScheduleDialog').modal('hide')" value="关闭">
		</div>
	</div>
</body>
</html>
package org.common.workflow;

import org.jbpm.api.cmd.Command;
import org.jbpm.api.task.Task;

public interface JoinsignTaskCommand extends Command<Boolean> {
	public static final String VAR_SIGN = "sign";
	
	void setJoinsignTask(Task joinsignTask);
}

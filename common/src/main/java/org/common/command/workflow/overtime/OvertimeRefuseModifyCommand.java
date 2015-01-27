package org.common.command.workflow.overtime;

public class OvertimeRefuseModifyCommand extends OvertimeCommand{
	/**
	 * 放弃修改1-放弃0-继续修改
	 */
	private int abandon;

	public int getAbandon() {
		return abandon;
	}

	public void setAbandon(int abandon) {
		this.abandon = abandon;
	}
}

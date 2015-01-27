package org.common.entity.view.workflow;

import java.util.List;

import org.common.entity.workflow.OvertimeApprove;
import org.common.entity.workflow.OvertimeFlow;

public class OvertimeFlowView {
	private OvertimeFlow overtimeFlow;
	
	private List<OvertimeApprove> overtimeApproveList;

	public OvertimeFlow getOvertimeFlow() {
		return overtimeFlow;
	}

	public void setOvertimeFlow(OvertimeFlow overtimeFlow) {
		this.overtimeFlow = overtimeFlow;
	}

	public List<OvertimeApprove> getOvertimeApproveList() {
		return overtimeApproveList;
	}

	public void setOvertimeApproveList(List<OvertimeApprove> overtimeApproveList) {
		this.overtimeApproveList = overtimeApproveList;
	}
}

/**
 * Copyright(c)2013
 * DHC Software Co., Ltd.
 *
 * All Rights Reserved
 *
 * Revision History:
 *                       Modification        Tracking
 * Author (Email ID)     Date                Number              Description
 * xiongxm               2013-02-22          BugId				 Workflow Engine initialize servlet
 *
 */
package app.component.wkf.service.interfaces;


/**
 * for flex query branch
 * @author User
 *
 */
public interface IBranchService {
	public String list(String branchId, String branchName) throws Exception;
}

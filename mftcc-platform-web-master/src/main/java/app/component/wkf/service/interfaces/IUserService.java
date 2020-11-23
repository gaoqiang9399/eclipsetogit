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

import java.util.List;

/**
 * for flex query user
 * @author User
 *
 */
public interface IUserService {
	public List<String> list(String userId, String userName) throws Exception;
}

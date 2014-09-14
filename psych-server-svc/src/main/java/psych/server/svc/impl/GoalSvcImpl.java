package psych.server.svc.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import psych.server.data.svc.impl.BaseSvc;
import psych.server.svc.GoalSvc;
import psych.server.svc.model.UserAuth;

/**
 * Service for interacting with Goals
 * 
 * @author tobyboyd
 * 
 */
public class GoalSvcImpl extends BaseSvc implements GoalSvc {

	private static final Logger log = LoggerFactory.getLogger(GoalSvcImpl.class);
	
	public void getSingleGoal(UserAuth auth, String goalID) throws Exception{
		
	}
	
	public void getUsersGoal(UserAuth auth, String userID) throws Exception {
		
	}
	

}

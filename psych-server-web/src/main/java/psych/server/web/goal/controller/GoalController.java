package psych.server.web.goal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import psych.server.web.goal.model.Goal;
import psych.server.web.util.DateTimeUtil;


/**
 * Handles Goal requests
 * 
 * @version 2.0
 * 
 * @author Toby Boyd
 */
@Controller
@RequestMapping("/v1/goal")
public class GoalController {

	private static final Logger log = LoggerFactory.getLogger(GoalController.class);

	/**
	 * Handles manifest get request
	 * 
	 * @param request
	 *            Request object
	 * @param response
	 *            response object
	 * @return JSON object for the client to interpret
	 * @throws ServletException
	 *             Thrown if an internal exception that is not handled occurs
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public Goal getSingleGoal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.trace("getManifest");
		
		Goal goal = new Goal();
		goal.setId("Test ID");
		goal.setName("My Goal for the week");
		goal.setDescr("A long description for my goal so I know why I made it");
		goal.setStatus("A");
		goal.setScoreType("CNT");
		goal.setUpdatedDt(DateTimeUtil.getFormattedDate(new DateTime()));
		goal.setAddDt(DateTimeUtil.getFormattedDate(new DateTime()));
		
		return goal;
	}


	/**
	 * Handles manifest get request
	 * 
	 * @param request
	 *            Request object
	 * @param response
	 *            response object
	 * @return JSON object for the client to interpret
	 * @throws ServletException
	 *             Thrown if an internal exception that is not handled occurs
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	public List<Goal> getGoalList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.trace("getManifest");
		
		Goal goal = new Goal();
		goal.setId("Test ID");
		goal.setName("My Goal for the week");
		goal.setDescr("A long description for my goal so I know why I made it");
		goal.setStatus("A");
		goal.setScoreType("CNT");
		goal.setUpdatedDt(DateTimeUtil.getFormattedDate(new DateTime()));
		goal.setAddDt(DateTimeUtil.getFormattedDate(new DateTime()));
		
		List<Goal> goalList = new ArrayList<Goal>();
		goalList.add(goal);
		goalList.add(goal);
		goalList.add(goal);
		
		return goalList;
	}
	

}

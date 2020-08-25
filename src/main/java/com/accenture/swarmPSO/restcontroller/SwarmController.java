package com.accenture.swarmPSO.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.swarmPSO.bean.GlobalSolution;
import com.accenture.swarmPSO.bean.GlobalSolutionNew;
import com.accenture.swarmPSO.bean.Particle;
import com.accenture.swarmPSO.bean.Swarm;
import com.accenture.swarmPSO.bean.SwarmMagnitude;
import com.accenture.swarmPSO.bean.SwarmNew;
import com.accenture.swarmPSO.service.ISwarmService;

@RestController
@RequestMapping(value = "/swarmIntelligencePSO")
public class SwarmController {

	@Autowired
	ISwarmService swarmService;
	
	//1st API
	@RequestMapping(value="/loadSwarmData",method= {RequestMethod.GET, RequestMethod.POST},produces="application/json")
	public void loadSwarmData(@RequestBody Swarm swarm) {
		swarmService.loadSwarmData(swarm);
	}
	
	@RequestMapping(value="fetchLoadedSwarmData/{roomId}",method= {RequestMethod.GET},produces="application/json")
	public Swarm fetchLoadedSwarmData(@PathVariable int roomId) {
		return swarmService.fetchLoadedSwarmData(roomId);
	}
	
	
	//2nd API
	@RequestMapping(value="/calculateGlobalBestSolution",method= {RequestMethod.GET, RequestMethod.POST},produces="application/json")
	public GlobalSolution calculateGlobalBestSolution(@RequestBody Swarm swarm) {
		return swarmService.calculateGlobalBestSolution(swarm.getParticles(), swarm.getRoomId());
	}
	
	//new APIs
	
	//1st API
	@RequestMapping(value="/loadSwarmDataNew",method= {RequestMethod.GET, RequestMethod.POST},produces="application/json")
	public void loadSwarmNewData(@RequestBody SwarmNew swarm) {
		swarmService.loadSwarmNewData(swarm);
	}
	
	@RequestMapping(value="fetchLoadedSwarmNewData/{roomId}",method= {RequestMethod.GET},produces="application/json")
	public SwarmNew fetchLoadedSwarNewData(@PathVariable int roomId) {
		return swarmService.fetchLoadedSwarmNewData(roomId);
	}
	
	
	//2nd API
	@RequestMapping(value="/calculateGlobalBestSolutionNew",method= {RequestMethod.GET, RequestMethod.POST},produces="application/json")
	public GlobalSolutionNew calculateGlobalBestSolutionNew(@RequestBody SwarmNew swarm) {
		return swarmService.calculateGlobalBestSolutionNew(swarm.getParticles(), swarm.getRoomId());
	}
	
	//3rd Approach
	
	//1st API
	@RequestMapping(value="/loadSwarmDataMagnitude",method= {RequestMethod.GET, RequestMethod.POST},produces="application/json")
	public void loadSwarmDataMagnitude(@RequestBody SwarmMagnitude swarmMagnitude) {
		swarmService.loadSwarmDataMagnitude(swarmMagnitude);
	}
		
	@RequestMapping(value="fetchLoadedSwarmDataMagnitude/{roomId}",method= {RequestMethod.GET},produces="application/json")
	public SwarmMagnitude fetchLoadedSwarDataMagnitude(@PathVariable int roomId) {
		return swarmService.fetchLoadedSwarmDataMagnitude(roomId);
	}
		
		
	//2nd API
	@RequestMapping(value="/calculateGlobalBestSolutionMagnitude",method= {RequestMethod.GET, RequestMethod.POST},produces="application/json")
	public GlobalSolutionNew calculateGlobalBestSolutionMagnitude(@RequestBody SwarmNew swarm) {
		return swarmService.calculateGlobalBestSolutionMagnitude(swarm.getParticles(), swarm.getRoomId());
	}
	
}

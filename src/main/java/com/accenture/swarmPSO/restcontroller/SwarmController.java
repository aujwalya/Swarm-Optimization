package com.accenture.swarmPSO.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.swarmPSO.bean.GlobalSolution;
import com.accenture.swarmPSO.bean.Particle;
import com.accenture.swarmPSO.bean.Swarm;
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
	
	
}

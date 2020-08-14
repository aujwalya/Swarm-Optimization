package com.accenture.swarmPSO.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.swarmPSO.bean.GlobalSolution;
import com.accenture.swarmPSO.bean.Particle;
import com.accenture.swarmPSO.bean.ParticleResponse;
import com.accenture.swarmPSO.bean.Swarm;
import com.accenture.swarmPSO.bean.Vector;
import com.accenture.swarmPSO.repository.IParticleSwarmRepository;

@Service
public class SwarmService implements ISwarmService{

	Swarm swarmServices = new Swarm();
	
	@Autowired
	IParticleSwarmRepository particleSwarmDAO;
	
	GlobalSolution globalSolution = new GlobalSolution();
	
	private void updateGlobalBestOption(List<Vector> optionVertices, Vector globalBestPosition, GlobalSolution globalSolution) {
		double bestOptionDistance = 0.0;
		for(Vector option:optionVertices) {
			double currentOptionDistance = Math.sqrt(Math.pow(option.getY() - globalBestPosition.getY(), 2) 
					+ Math.pow(option.getY() - globalBestPosition.getY(), 2));
			if((currentOptionDistance < bestOptionDistance) || bestOptionDistance == 0.0) {
				bestOptionDistance = currentOptionDistance;
				globalSolution.setGlobalBestOption(option);
			}
				
		}
	}

	@Override
	public void loadSwarmData(Swarm swarm) {
		swarmServices.initialize(swarm.getParticles(), swarm);
		particleSwarmDAO.loadSwarmData(swarm);	
	}

	@Override
	public Swarm fetchLoadedSwarmData(int roomId) {
		return particleSwarmDAO.fetchLoadedSwarmData(roomId);
	}

	@Override
	public GlobalSolution calculateGlobalBestSolution(List<Particle> particles, int roomId) {
		Swarm swarm = particleSwarmDAO.fetchLoadedSwarmData(roomId);
		if(swarm == null)
			return new GlobalSolution();
		
		List<Particle> storedParticlesData = swarm.getParticles();
		
		//Update current Position of stored particle data with UI posted data
		particles.stream().forEach(uiParticleItem ->
			storedParticlesData.stream().filter(storedParticleItem -> storedParticleItem.getParticleId().equalsIgnoreCase(uiParticleItem.getParticleId()))
					.forEach(storedParticleItem -> storedParticleItem.setPosition(uiParticleItem.getPosition())));
		
		//Calculate global Best with PSO Algorithm
		swarmServices.onCalculate(storedParticlesData, swarm);
		
		//Set updated particles data for API end caching 
		//which will be needed for next time calculation
		swarm.setParticles(storedParticlesData);
		
		//Store the updated Swarm data at API end in Java cache
		particleSwarmDAO.updateSwarmDataAfterCalculation(swarm, roomId);
		
		//List of particles current position to return to UI
		List<ParticleResponse> calculatedParticleList = new ArrayList<>();
		storedParticlesData.stream().forEach(item -> {
			ParticleResponse calculatedParticle = new ParticleResponse(); 
			calculatedParticle.setParticleId(item.getParticleId());
			calculatedParticle.setPosition(item.getPosition());
			calculatedParticle.setBestPosition(item.getBestPosition());
			calculatedParticleList.add(calculatedParticle);
		});
		
		//Creating Global Solution to return it to UI
		globalSolution.setParticles(calculatedParticleList);
		globalSolution.setGlobalBestPosition(swarm.getBestPosition());
		updateGlobalBestOption(swarm.getOptionVertices(), swarm.getBestPosition(), globalSolution);
		
		return globalSolution;
	}

}

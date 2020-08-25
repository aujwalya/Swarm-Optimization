package com.accenture.swarmPSO.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.swarmPSO.bean.GlobalSolution;
import com.accenture.swarmPSO.bean.GlobalSolutionNew;
import com.accenture.swarmPSO.bean.Particle;
import com.accenture.swarmPSO.bean.ParticleResponse;
import com.accenture.swarmPSO.bean.ParticleResponseNew;
import com.accenture.swarmPSO.bean.Swarm;
import com.accenture.swarmPSO.bean.SwarmMagnitude;
import com.accenture.swarmPSO.bean.SwarmNew;
import com.accenture.swarmPSO.bean.Vector;
import com.accenture.swarmPSO.repository.IParticleSwarmRepository;

@Service
public class SwarmService implements ISwarmService{

	@Autowired
	Swarm swarmServices;
	
	@Autowired
	SwarmNew swarmNewServices;
	
	@Autowired
	SwarmMagnitude swarmMagnitudeServices;
	
	@Autowired
	IParticleSwarmRepository particleSwarmDAO;
	
	@Autowired
	GlobalSolution globalSolution;
	
	@Autowired
	GlobalSolutionNew globalSolutionNew;
	
	//This method returns the second best option to check the predictability of the first Best option compared with second best option
	private Vector updateGlobalBestOption(List<Vector> optionVertices, Vector globalBestPosition, GlobalSolution globalSolution) {
		double bestOptionDistance = 0.0;
		double secondBestDistance;
		Vector vector = new Vector();
		for(Vector option:optionVertices) {
			double currentOptionDistance = Math.sqrt(Math.pow(option.getY() - globalBestPosition.getY(), 2) 
					+ Math.pow(option.getY() - globalBestPosition.getY(), 2));
			if((currentOptionDistance < bestOptionDistance) || bestOptionDistance == 0.0) {
				secondBestDistance = bestOptionDistance;
				bestOptionDistance = currentOptionDistance;
				if(secondBestDistance != 0.0)
					vector = globalSolution.getGlobalBestOption();
				globalSolution.setGlobalBestOption(option);
			}
				
		}
		return vector;
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
			calculatedParticle.setPosition(roundOffVector(item.getPosition()));
			calculatedParticle.setBestPosition(roundOffVector(item.getBestPosition()));
			calculatedParticleList.add(calculatedParticle);
		});
		
		//Creating Global Solution to return it to UI
		globalSolution.setParticles(calculatedParticleList);
		globalSolution.setGlobalBestPosition(roundOffVector(swarm.getBestPosition()));
		Vector secondBestOption = updateGlobalBestOption(swarm.getOptionVertices(), swarm.getBestPosition(), globalSolution);
		
		double predictability = calculatePredictability(globalSolution.getGlobalBestOption(), secondBestOption, swarm.getBestPosition());
		globalSolution.setPredictability(predictability);
		
		return globalSolution;
	} 
	
	public Vector roundOffVector(Vector vector) {
		double x = BigDecimal.valueOf(vector.getX()).setScale(2, RoundingMode.HALF_UP).doubleValue();
		double y = BigDecimal.valueOf(vector.getY()).setScale(2, RoundingMode.HALF_UP).doubleValue();
		vector.setX(x);
		vector.setY(y);
		return vector;
	}
	
	@Override
	public void loadSwarmNewData(SwarmNew swarmNew) {
		swarmNewServices.initialize(swarmNew.getParticles(), swarmNew);
		particleSwarmDAO.loadSwarmNewData(swarmNew);	
	}

	public SwarmNew fetchLoadedSwarmNewData(int roomId) {
		return particleSwarmDAO.fetchLoadedSwarmNewData(roomId);
	}
	
	@Override
	public GlobalSolutionNew calculateGlobalBestSolutionNew(List<Particle> particles, int roomId) {
		SwarmNew swarmnew = particleSwarmDAO.fetchLoadedSwarmNewData(roomId);
		if(swarmnew == null)
			return new GlobalSolutionNew();
		
		List<Particle> storedParticlesData = swarmnew.getParticles();
		
		//Update current Position of stored particle data with UI posted data
		particles.stream().forEach(uiParticleItem ->
			storedParticlesData.stream().filter(storedParticleItem -> storedParticleItem.getParticleId().equalsIgnoreCase(uiParticleItem.getParticleId()))
					.forEach(storedParticleItem -> storedParticleItem.setPosition(uiParticleItem.getPosition())));			
			
		//Calculate global Best with PSO Algorithm
		swarmNewServices.onCalculate(storedParticlesData, swarmnew);
		
		//Set updated particles data for API end caching 
		//which will be needed for next time calculation
		swarmnew.setParticles(storedParticlesData);
		
		//Store the updated Swarm data at API end in Java cache
		particleSwarmDAO.updateSwarmDataAfterCalculationNew(swarmnew, roomId);
		
		//List of particles current position to return to UI
		List<ParticleResponseNew> calculatedParticleList = new ArrayList<>();
		storedParticlesData.stream().forEach(item -> {
			ParticleResponseNew calculatedParticle = new ParticleResponseNew(); 
			calculatedParticle.setParticleId(item.getParticleId());
			calculatedParticle.setPosition(roundOffVector(item.getPosition()));
			
			System.out.println("Particle Id" + item.getParticleId());
			System.out.println("Particle Id X" + item.getPosition().getX() + "--" +calculatedParticle.getPosition().getX()  );
			System.out.println("Particle Id Y" + item.getPosition().getY() + "--" +calculatedParticle.getPosition().getY()  );
			
			calculatedParticleList.add(calculatedParticle);
		});
		
		//Creating Global Solution to return it to UI
		globalSolutionNew.setParticles(calculatedParticleList);
		
		System.out.println("Best Position X" + swarmnew.getBestPosition().getX());
		System.out.println("Best Position Y" + swarmnew.getBestPosition().getY());
		
		globalSolutionNew.setGlobalBestPosition(roundOffVector(swarmnew.getBestPosition()));
		Vector secondBestOption = updateGlobalBestOptionNew(swarmnew.getOptionVertices(), swarmnew.getBestPosition(), globalSolutionNew);
		
		double predictability = calculatePredictability(globalSolutionNew.getGlobalBestOption(), secondBestOption, swarmnew.getBestPosition());
		globalSolutionNew.setPredictability(predictability);
		
		return globalSolutionNew;
	}
	
	//This method returns the second best option to check the predictability of the first Best option compared with second best option
	private Vector updateGlobalBestOptionNew(List<Vector> optionVertices, Vector globalBestPosition, GlobalSolutionNew globalSolutionNew) {
		double bestOptionDistance = 0.0;
		double secondBestDistance;
		Vector vector = new Vector();
		for(Vector option:optionVertices) {
			double currentOptionDistance = Math.sqrt(Math.pow(option.getY() - globalBestPosition.getY(), 2) 
					+ Math.pow(option.getY() - globalBestPosition.getY(), 2));
			if((currentOptionDistance < bestOptionDistance) || bestOptionDistance == 0.0) {
				secondBestDistance = bestOptionDistance;
				bestOptionDistance = currentOptionDistance;
				if(secondBestDistance != 0.0)
					vector = globalSolutionNew.getGlobalBestOption();
				globalSolutionNew.setGlobalBestOption(option);
			}
		}
		return vector;
	}
	
	private double calculatePredictability(Vector bestOption, Vector secondBestOption, Vector globalBestPosition) {
		double bestOptionDistance = Math.sqrt(Math.pow(bestOption.getY() - globalBestPosition.getY(), 2) 
				+ Math.pow(bestOption.getY() - globalBestPosition.getY(), 2));
		double secondBestOptionDistance = Math.sqrt(Math.pow(secondBestOption.getY() - globalBestPosition.getY(), 2) 
				+ Math.pow(secondBestOption.getY() - globalBestPosition.getY(), 2));
		
		double predictability = (secondBestOptionDistance/(bestOptionDistance+secondBestOptionDistance))*100;
		return BigDecimal.valueOf(predictability).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	
	@Override
	public SwarmMagnitude fetchLoadedSwarmDataMagnitude(int roomId) {
		return particleSwarmDAO.fetchLoadedSwarmDataMagnitude(roomId);
	}

	@Override
	public void loadSwarmDataMagnitude(SwarmMagnitude swarmMagnitude) {
		swarmMagnitude.initialize(swarmMagnitude.getParticles(), swarmMagnitude);
		particleSwarmDAO.loadSwarmDataMagnitude(swarmMagnitude);
		
	}

	@Override
	public GlobalSolutionNew calculateGlobalBestSolutionMagnitude(List<Particle> particles, int roomId) {
		SwarmMagnitude swarmMagnitude = particleSwarmDAO.fetchLoadedSwarmDataMagnitude(roomId);
		if(swarmMagnitude == null)
			return new GlobalSolutionNew();
		
		List<Particle> storedParticlesData = swarmMagnitude.getParticles();
		
		//Update current Position of stored particle data with UI posted data
		particles.stream().forEach(uiParticleItem ->
			storedParticlesData.stream().filter(storedParticleItem -> storedParticleItem.getParticleId().equalsIgnoreCase(uiParticleItem.getParticleId()))
					.forEach(storedParticleItem -> storedParticleItem.setPosition(uiParticleItem.getPosition())));			
			
		//Calculate global Best with PSO Algorithm
		swarmMagnitudeServices.onCalculate(storedParticlesData, swarmMagnitude);
		
		//Set updated particles data for API end caching 
		//which will be needed for next time calculation
		swarmMagnitude.setParticles(storedParticlesData);
		
		//Store the updated Swarm data at API end in Java cache
		particleSwarmDAO.updateSwarmDataAfterCalculationMagnitude(swarmMagnitude, roomId);
		
		//List of particles current position to return to UI
		List<ParticleResponseNew> calculatedParticleList = new ArrayList<>();
		storedParticlesData.stream().forEach(item -> {
			ParticleResponseNew calculatedParticle = new ParticleResponseNew(); 
			calculatedParticle.setParticleId(item.getParticleId());
			calculatedParticle.setPosition(roundOffVector(item.getPosition()));
			
			System.out.println("Particle Id" + item.getParticleId());
			System.out.println("Particle Id X" + item.getPosition().getX() + "--" +calculatedParticle.getPosition().getX()  );
			System.out.println("Particle Id Y" + item.getPosition().getY() + "--" +calculatedParticle.getPosition().getY()  );
			
			calculatedParticleList.add(calculatedParticle);
		});
		
		//Creating Global Solution to return it to UI
		globalSolutionNew.setParticles(calculatedParticleList);
		
		System.out.println("Best Position X" + swarmMagnitude.getBestPosition().getX());
		System.out.println("Best Position Y" + swarmMagnitude.getBestPosition().getY());
		
		globalSolutionNew.setGlobalBestPosition(roundOffVector(swarmMagnitude.getBestPosition()));
		Vector secondBestOption = updateGlobalBestOptionNew(swarmMagnitude.getOptionVertices(), swarmMagnitude.getBestPosition(), globalSolutionNew);
		
		double predictability = calculatePredictability(globalSolutionNew.getGlobalBestOption(), secondBestOption, swarmMagnitude.getBestPosition());
		globalSolutionNew.setPredictability(predictability);
		
		return globalSolutionNew;
	}

}

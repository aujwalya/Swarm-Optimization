package com.accenture.swarmPSO.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.accenture.swarmPSO.bean.Swarm;
import com.accenture.swarmPSO.bean.SwarmMagnitude;
import com.accenture.swarmPSO.bean.SwarmNew;

@Repository
public class ParticleSwarmRepository implements IParticleSwarmRepository{

	Map<Integer, Swarm> swarmData = new HashMap<>();
	Map<Integer, SwarmNew> swarmnewData = new HashMap<>();
	Map<Integer, SwarmMagnitude> swarmDataMagnitude = new HashMap<>();
	
	@Override
	public void loadSwarmData(Swarm swarm) {
		swarmData.put(swarm.getRoomId(), swarm);
	}

	@Override
	public Swarm fetchLoadedSwarmData(int roomId) {
		return swarmData.get(roomId);
	}

	@Override
	public void updateSwarmDataAfterCalculation(Swarm swarm, int roomId) {
		swarmData.put(roomId, swarm);
	}

	@Override
	public void loadSwarmNewData(SwarmNew swarm) {
		swarmnewData.put(swarm.getRoomId(), swarm);
	}
	
	@Override
	public SwarmNew fetchLoadedSwarmNewData(int roomId) {
		return swarmnewData.get(roomId);
	}

	@Override
	public void updateSwarmDataAfterCalculationNew(SwarmNew swarmNew, int roomId) {
		swarmnewData.put(roomId, swarmNew);
		
	}

	@Override
	public void loadSwarmDataMagnitude(SwarmMagnitude swarmMagnitude) {
		swarmDataMagnitude.put(swarmMagnitude.getRoomId(), swarmMagnitude);
		
	}

	@Override
	public SwarmMagnitude fetchLoadedSwarmDataMagnitude(int roomId) {
		return swarmDataMagnitude.get(roomId);
	}

	@Override
	public void updateSwarmDataAfterCalculationMagnitude(SwarmMagnitude swarmMagnitude, int roomId) {
		swarmDataMagnitude.put(roomId, swarmMagnitude);
		
	}

}

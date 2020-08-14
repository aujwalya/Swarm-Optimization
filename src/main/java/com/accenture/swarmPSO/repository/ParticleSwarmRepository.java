package com.accenture.swarmPSO.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.accenture.swarmPSO.bean.Swarm;

@Repository
public class ParticleSwarmRepository implements IParticleSwarmRepository{

	Map<Integer, Swarm> swarmData = new HashMap<>();
	
	@Override
	public void loadSwarmData(Swarm swarm) {
		swarmData.put(swarm.getRoomId(), swarm);
		//return swarm.getRoomId();
	}

	@Override
	public Swarm fetchLoadedSwarmData(int roomId) {
		return swarmData.get(roomId);
	}

	@Override
	public void updateSwarmDataAfterCalculation(Swarm swarm, int roomId) {
		swarmData.put(roomId, swarm);
	}


}

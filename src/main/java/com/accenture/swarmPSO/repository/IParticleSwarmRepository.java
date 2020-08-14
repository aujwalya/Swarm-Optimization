package com.accenture.swarmPSO.repository;

import java.util.List;

import com.accenture.swarmPSO.bean.Swarm;

public interface IParticleSwarmRepository {

	public void loadSwarmData(Swarm swarm);
	public Swarm fetchLoadedSwarmData(int roomId);
	public void updateSwarmDataAfterCalculation(Swarm swarm, int roomId);
}

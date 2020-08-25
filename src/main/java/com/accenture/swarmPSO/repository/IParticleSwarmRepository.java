package com.accenture.swarmPSO.repository;

import java.util.List;

import com.accenture.swarmPSO.bean.Swarm;
import com.accenture.swarmPSO.bean.SwarmMagnitude;
import com.accenture.swarmPSO.bean.SwarmNew;

public interface IParticleSwarmRepository {

	public void loadSwarmData(Swarm swarm);
	public void loadSwarmNewData(SwarmNew swarm);
	public Swarm fetchLoadedSwarmData(int roomId);
	public SwarmNew fetchLoadedSwarmNewData(int roomId);
	public void updateSwarmDataAfterCalculation(Swarm swarm, int roomId);
	public void updateSwarmDataAfterCalculationNew(SwarmNew swarmNew, int roomId);
	
	//3rd Approach
	public void loadSwarmDataMagnitude(SwarmMagnitude swarmMagnitude);
	public SwarmMagnitude fetchLoadedSwarmDataMagnitude(int roomId);
	public void updateSwarmDataAfterCalculationMagnitude(SwarmMagnitude swarmMagnitude, int roomId);
}

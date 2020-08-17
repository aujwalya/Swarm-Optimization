package com.accenture.swarmPSO.service;

import java.util.List;

import com.accenture.swarmPSO.bean.GlobalSolution;
import com.accenture.swarmPSO.bean.GlobalSolutionNew;
import com.accenture.swarmPSO.bean.Particle;
import com.accenture.swarmPSO.bean.Swarm;
import com.accenture.swarmPSO.bean.SwarmNew;

public interface ISwarmService {

	public void loadSwarmData(Swarm swarm);
	public Swarm fetchLoadedSwarmData(int roomId);
	public void loadSwarmNewData(SwarmNew swarm);
	public SwarmNew fetchLoadedSwarmNewData(int roomId);
	public GlobalSolution calculateGlobalBestSolution(List<Particle> particles, int roomId);
	public GlobalSolutionNew calculateGlobalBestSolutionNew(List<Particle> particles, int roomId);
}

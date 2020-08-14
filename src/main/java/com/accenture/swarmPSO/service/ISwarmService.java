package com.accenture.swarmPSO.service;

import java.util.List;

import com.accenture.swarmPSO.bean.GlobalSolution;
import com.accenture.swarmPSO.bean.Particle;
import com.accenture.swarmPSO.bean.Swarm;

public interface ISwarmService {

	public void loadSwarmData(Swarm swarm);
	public Swarm fetchLoadedSwarmData(int roomId);
	public GlobalSolution calculateGlobalBestSolution(List<Particle> particles, int roomId);
}

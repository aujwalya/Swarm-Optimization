package com.accenture.swarmPSO.bean;

import java.util.List;

public class GlobalSolution {	
	
	private Vector globalBestPosition;
	private Vector globalBestOption;
	List<ParticleResponse> particles;
	
	public Vector getGlobalBestPosition() {
		return globalBestPosition;
	}
	public void setGlobalBestPosition(Vector globalBestPosition) {
		this.globalBestPosition = globalBestPosition;
	}
	public Vector getGlobalBestOption() {
		return globalBestOption;
	}
	public void setGlobalBestOption(Vector globalBestOption) {
		this.globalBestOption = globalBestOption;
	}
	public List<ParticleResponse> getParticles() {
		return particles;
	}
	public void setParticles(List<ParticleResponse> particles) {
		this.particles = particles;
	}
	@Override
	public String toString() {
		return "GlobalSolution [globalBestPosition=" + globalBestPosition + ", globalBestOption=" + globalBestOption
				+ ", particles=" + particles + "]";
	}
	
}

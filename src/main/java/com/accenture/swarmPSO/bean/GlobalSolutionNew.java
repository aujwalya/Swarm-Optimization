package com.accenture.swarmPSO.bean;

import java.util.List;

public class GlobalSolutionNew {	
	
	private Vector globalBestPosition;
	private Vector globalBestOption;
	List<ParticleResponseNew> particles;
	
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
	public List<ParticleResponseNew> getParticles() {
		return particles;
	}
	public void setParticles(List<ParticleResponseNew> particles) {
		this.particles = particles;
	}
	@Override
	public String toString() {
		return "GlobalSolutionNew [globalBestPosition=" + globalBestPosition + ", globalBestOption=" + globalBestOption
				+ ", particles=" + particles + "]";
	}
	
	
}

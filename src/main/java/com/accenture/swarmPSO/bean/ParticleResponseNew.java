package com.accenture.swarmPSO.bean;

public class ParticleResponseNew {

	private String particleId;
    private Vector position;        // Current position.
    
	public String getParticleId() {
		return particleId;
	}
	public void setParticleId(String particleId) {
		this.particleId = particleId;
	}
	public Vector getPosition() {
		return position;
	}
	public void setPosition(Vector position) {
		this.position = position;
	}
	@Override
	public String toString() {
		return "ParticleResponseNew [particleId=" + particleId + ", position=" + position + "]";
	}
	
}

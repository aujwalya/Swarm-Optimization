package com.accenture.swarmPSO.bean;

public class ParticleResponse {

	private String particleId;
    private Vector position;        // Current position.
    private Vector bestPosition;
    
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
	public Vector getBestPosition() {
		return bestPosition;
	}
	public void setBestPosition(Vector bestPosition) {
		this.bestPosition = bestPosition;
	}
	@Override
	public String toString() {
		return "ParticleResponse [particleId=" + particleId + ", position=" + position + ", bestPosition="
				+ bestPosition + "]";
	}
}

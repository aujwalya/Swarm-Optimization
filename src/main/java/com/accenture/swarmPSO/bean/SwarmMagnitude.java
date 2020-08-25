package com.accenture.swarmPSO.bean;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class SwarmMagnitude {

	private int roomId;
    private Vector bestPosition;
    List<Vector> optionVertices;
    private List<Particle> particles;
    
    /** Raunak
     * When Particles are created they are given a random position.
     * The random position is selected from a specified range.
     * If the begin range is 0 and the end range is 10 then the
     * value will be between 0 (inclusive) and 10 (exclusive).
     */
    private int beginRange, endRange;
    private double bestEval;
    private double infinity = Double.POSITIVE_INFINITY;
    private static final int DEFAULT_BEGIN_RANGE = -100;
    private static final int DEFAULT_END_RANGE = 101;
    double oldEval;
    
    
	/**
     * Construct the Swarm with custom values.
     * @param particles     the number of particles to create
     * @param epochs        the number of generations
     * @param inertia       the particles resistance to change
     * @param cognitive     the cognitive component or introversion of the particle
     * @param social        the social component or extroversion of the particle
     */
    public SwarmMagnitude () {
       
        bestPosition = new Vector(infinity, infinity);
        bestEval = Double.POSITIVE_INFINITY;
        beginRange = DEFAULT_BEGIN_RANGE;
        endRange = DEFAULT_END_RANGE;
    }
    
    public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}   

	public Vector getBestPosition() {
		return bestPosition;
	}

	public void setBestPosition(Vector bestPosition) {
		this.bestPosition = bestPosition;
	}

	public double getBestEval() {
		return bestEval;
	}

	public void setBestEval(double bestEval) {
		this.bestEval = bestEval;
	}

	public List<Vector> getOptionVertices() {
		return optionVertices;
	}

	public void setOptionVertices(List<Vector> optionVertices) {
		this.optionVertices = optionVertices;
	}

	public List<Particle> getParticles() {
		return particles;
	}

	public void setParticles(List<Particle> particles) {
		this.particles = particles;
	}

	public int getBeginRange() {
		return beginRange;
	}

	public void setBeginRange(int beginRange) {
		this.beginRange = beginRange;
	}

	public int getEndRange() {
		return endRange;
	}

	public void setEndRange(int endRange) {
		this.endRange = endRange;
	}

	public double getOldEval() {
		return oldEval;
	}

	public void setOldEval(double oldEval) {
		this.oldEval = oldEval;
	}     

    public void onCalculate(List<Particle> particles, SwarmMagnitude swarmMagnitude) {
    	
    	System.out.println("Initial bestPosition X::" + bestPosition.getX());
    	System.out.println("Initial bestPosition Y::" + bestPosition.getY());
    	int numOfParticles = particles.size();
    	
        for (Particle p : particles) {
        	Vector tempGlobalBest = calculateGlobalBestPosition(p.getPosition(), swarmMagnitude.getBestPosition(), numOfParticles);
        	swarmMagnitude.setBestPosition(tempGlobalBest);
        }
        
        System.out.println("bestPosition X::" + bestPosition.getX());
    	System.out.println("bestPosition Y::" + bestPosition.getY());
        
        //update particle position
        
        
    	particles.stream().forEach(item -> {
        	Vector tempCurrentPosition = calculateParticleCurrentPosition(item.getPosition(), swarmMagnitude.getBestPosition(), numOfParticles);
        	item.setPosition(tempCurrentPosition);
        	
        	System.out.println("Particle Id:" + item.getParticleId());
        	System.out.println("Upated particle position X:" + item.getPosition().getX());
        	System.out.println("Upated particle position Y" + item.getPosition().getY());
        	});
        
        swarmMagnitude.setParticles(particles);
        
       }
    
    //Find Global Best Position
    private Vector calculateGlobalBestPosition(Vector particlePosition, Vector globalPosition, int numberOfParticles) {
    	Vector globalCoordinates = new Vector();
    	double d = Math.sqrt(Math.pow(particlePosition.getY() - globalPosition.getY(), 2) 
				+ Math.pow(particlePosition.getY() - globalPosition.getY(), 2));
    	double effectiveDistance = d/numberOfParticles;

    		double pointDistance = effectiveDistance/d;
    		double x = (1-pointDistance)*globalPosition.getX() + pointDistance * particlePosition.getX();
    		double y = (1-pointDistance)*globalPosition.getY() + pointDistance * particlePosition.getY();
    		globalCoordinates.setX(Math.abs(x));
    		globalCoordinates.setY(Math.abs(y));
    		
    	return globalCoordinates;
    }
    
    //Find the particles current Position
    private Vector calculateParticleCurrentPosition(Vector particlePosition, Vector globalPosition, int numOfParticles) {
    	double d = Math.sqrt(Math.pow(particlePosition.getY() - globalPosition.getY(), 2) 
				+ Math.pow(particlePosition.getY() - globalPosition.getY(), 2));
    	Vector particleCoordinates = new Vector();
    	if(d<=50.0) {
    		double effectiveDistance = (50.0 + d/numOfParticles);
    		double pointDistance = effectiveDistance/d;
    		double x = (1-pointDistance)*globalPosition.getX() + pointDistance * particlePosition.getX();
    		double y = (1-pointDistance)*globalPosition.getY() + pointDistance * particlePosition.getY();
    		particleCoordinates.setX(Math.abs(x));
    		particleCoordinates.setY(Math.abs(y));
    	}
    	else {
    		double effectiveDistance = (15.0 + d/numOfParticles);
    		double pointDistance = effectiveDistance/d;
    		double x = (1-pointDistance)*particlePosition.getX() + pointDistance * globalPosition.getX();
    		double y = (1-pointDistance)*particlePosition.getY() + pointDistance * globalPosition.getY();
    		particleCoordinates.setX(Math.abs(x));
    		particleCoordinates.setY(Math.abs(y));
    	}
    	return particleCoordinates;
    }

    /**
     * Create a set of particles, each with random starting positions.
     * @return  an array of particles
     */
    public void initialize(List<Particle> particles, SwarmMagnitude swarmMagnitude) {
    	int numberOfParticles = particles.size();
        for (int i = 0; i < numberOfParticles; i++) {
            Particle particle = new Particle(beginRange, endRange, particles.get(i).getParticleId());
            particles.set(i, particle);         
            
        }
        bestPosition.set(310, 325);
        
        swarmMagnitude.setOldEval(swarmMagnitude.getBestEval());    
    }

    /**
     * Update the velocity of a particle using the velocity update formula
     * @param particle  the particle to update
     */
 

	@Override
	public String toString() {
		return "Swarm [roomId=" + roomId + ", bestPosition=" + bestPosition + ", bestEval=" + bestEval
				+ ", optionVertices=" + optionVertices + ", particles=" + particles + ", beginRange=" + beginRange
				+ ", endRange=" + endRange + ", oldEval=" + oldEval + "]";
	}
    
    
}

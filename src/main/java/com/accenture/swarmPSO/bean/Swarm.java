package com.accenture.swarmPSO.bean;

import java.util.List;
import java.util.Random;

public class Swarm {

	private int roomId;
    private double inertia;
    private double cognitiveComponent;
    private double socialComponent;
    private Vector bestPosition;
    private double bestEval;
	List<Vector> optionVertices;
    private List<Particle> particles;
    public static final double DEFAULT_INERTIA = 0.729844;
    public static final double DEFAULT_COGNITIVE = 1.496180; // Cognitive component.
    public static final double DEFAULT_SOCIAL = 1.496180; // Social component..
    
    /**
     * When Particles are created they are given a random position.
     * The random position is selected from a specified range.
     * If the begin range is 0 and the end range is 10 then the
     * value will be between 0 (inclusive) and 10 (exclusive).
     */
    private int beginRange, endRange;
    private static final int DEFAULT_BEGIN_RANGE = -100;
    private static final int DEFAULT_END_RANGE = 101;
    double oldEval;
    
    public Swarm () {
        this(DEFAULT_INERTIA, DEFAULT_COGNITIVE, DEFAULT_SOCIAL);
    }
    
	/**
     * Construct the Swarm with custom values.
     * @param particles     the number of particles to create
     * @param epochs        the number of generations
     * @param inertia       the particles resistance to change
     * @param cognitive     the cognitive component or introversion of the particle
     * @param social        the social component or extroversion of the particle
     */
    public Swarm (double inertia, double cognitive, double social) {
        this.inertia = inertia;
        this.cognitiveComponent = cognitive;
        this.socialComponent = social;
        double infinity = Double.POSITIVE_INFINITY;
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

    public double getInertia() {
		return inertia;
	}

	public void setInertia(double inertia) {
		this.inertia = inertia;
	}

	public double getCognitiveComponent() {
		return cognitiveComponent;
	}

	public void setCognitiveComponent(double cognitiveComponent) {
		this.cognitiveComponent = cognitiveComponent;
	}

	public double getSocialComponent() {
		return socialComponent;
	}

	public void setSocialComponent(double socialComponent) {
		this.socialComponent = socialComponent;
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

    public void onCalculate(List<Particle> particles, Swarm swarm) {
    	
            if (swarm.getBestEval() < swarm.getOldEval()) {
                swarm.setOldEval(swarm.getBestEval());
            }

            for(int epoch = 1; epoch < 50; epoch++) {
            for (Particle p : particles) {
                p.updatePersonalBest();
                updateGlobalBest(p, swarm);
            }

            for (Particle p : particles) {
                updateVelocity(p);
                p.updatePosition();
            }
            System.out.println("Gbest value for the Epoch number" +epoch +"is :" +swarm.getBestPosition());
            }

    }

    /**
     * Create a set of particles, each with random starting positions.
     * @return  an array of particles
     */
    public void initialize(List<Particle> particles, Swarm swarm) {
    	int numberOfParticles = particles.size();
        for (int i = 0; i < numberOfParticles; i++) {
            Particle particle = new Particle(beginRange, endRange, particles.get(i).getParticleId());
            particles.set(i, particle);
            updateGlobalBest(particle);
        }
        swarm.setOldEval(swarm.getBestEval());    
    }

    /**
     * Update the global best solution if a the specified particle has
     * a better solution
     * @param particle  the particle to analyze
     */
    private void updateGlobalBest (Particle particle, Swarm swarm) {
        if (particle.getBestEval() < swarm.getBestEval()) {
        	swarm.setBestPosition(particle.getBestPosition()) ;
            swarm.setBestEval(particle.getBestEval());
        }
    }
    
    /**
     * Update the global best solution if a the specified particle has
     * a better solution
     * @param particle  the particle to analyze
     */
    private void updateGlobalBest (Particle particle) {
        if (particle.getBestEval() < bestEval) {
            bestPosition = particle.getBestPosition();
            bestEval = particle.getBestEval();
        }
    }

    /**
     * Update the velocity of a particle using the velocity update formula
     * @param particle  the particle to update
     */
    private void updateVelocity (Particle particle) {
        Vector oldVelocity = particle.getVelocity();
        Vector pBest = particle.getBestPosition();
        Vector gBest = bestPosition.clone();
        Vector pos = particle.getPosition();

        Random random = new Random();
        double r1 = random.nextDouble();
        double r2 = random.nextDouble();

        // The first product of the formula.
        Vector newVelocity = oldVelocity.clone();
        newVelocity.mul(inertia);

        // The second product of the formula.
        pBest.sub(pos);
        pBest.mul(cognitiveComponent);
        pBest.mul(r1);
        newVelocity.add(pBest);

        // The third product of the formula.
        gBest.sub(pos);
        gBest.mul(socialComponent);
        gBest.mul(r2);
        newVelocity.add(gBest);

        particle.setVelocity(newVelocity);
    }

	@Override
	public String toString() {
		return "Swarm [roomId=" + roomId + ", inertia=" + inertia + ", cognitiveComponent=" + cognitiveComponent
				+ ", socialComponent=" + socialComponent + ", bestPosition=" + bestPosition + ", bestEval=" + bestEval
				+ ", optionVertices=" + optionVertices + ", particles=" + particles + ", beginRange=" + beginRange
				+ ", endRange=" + endRange + ", oldEval=" + oldEval + "]";
	}
    
    
}

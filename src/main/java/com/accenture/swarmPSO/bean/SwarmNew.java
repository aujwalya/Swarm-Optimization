package com.accenture.swarmPSO.bean;

import java.util.List;
import java.util.Random;

public class SwarmNew {

	private int roomId;
    private Vector bestPosition;
    List<Vector> optionVertices;
    private List<Particle> particles;
    
    /**
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
    public SwarmNew () {
       
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

    public void onCalculate(List<Particle> particles, SwarmNew swarm) {
    	
    	//calculate global value
    	double standardvelocity = 5;
    	double magx;
    	double magy;
    	
    	
    	Vector GlobalResultant;
    	Vector v;
    	GlobalResultant = new Vector(0, 0);
    	
        for (Particle p : particles) {
        	magx = p.getPosition().getX() - bestPosition.getX();
        	magy = p.getPosition().getY() - bestPosition.getY();
        	v = new Vector(0,0);
        	
        	if (magx>0) {
        		if (magy>0)
        		{
        			v.set(standardvelocity,standardvelocity); 
        			GlobalResultant.add(v);        			       			        			
        		}
        		else if (magy<0) {
        			v.set(standardvelocity,-standardvelocity);
        			GlobalResultant.add(v);
        		}
        		else if (magy==0) {
        			v.set(standardvelocity,0);
        			GlobalResultant.add(v);
        		}
        		
        	}
        	else if (magx<0) {
        		if (magy>0)
        		{
        			v.set(-standardvelocity,standardvelocity);
        			GlobalResultant.add(v);
        		}
        		else if (magy<0) {
        			v.set(-standardvelocity,-standardvelocity);
        			GlobalResultant.add(v);
        		}
        		else if (magy==0) {
        			v.set(-standardvelocity,0);
        			GlobalResultant.add(v);
        		}
        		
        	}	
        	else if (magx==0) {
        		if (magy>0)
        		{
        			v.set(0,standardvelocity);
        			GlobalResultant.add(v);
        		}
        		else if (magy<0) {
        			v.set(0,-standardvelocity);
        			GlobalResultant.add(v);
        		}
        		else if (magy==0) {
        			v.set(0,0);
        			GlobalResultant.add(v);
        		}
        	}
        	
        	
        }
        
        bestPosition.add(GlobalResultant);
        
        //update particle position
        for (Particle p : particles) {
        	magx = p.getPosition().getX() - bestPosition.getX();
        	magy = p.getPosition().getY() - bestPosition.getY();
        	v = new Vector(0,0);
        	
        	if (magx>0) {
        		if (magy>0)
        		{
        			v.setX(bestPosition.getX()+((magx/100)*standardvelocity));
        			v.setY(bestPosition.getY()+((magy/100)*standardvelocity));
        			p.setPosition(v);         			        			       			        			
        		}
        		else if (magy<0) {
        			v.setX(bestPosition.getX()+((magx/100)*standardvelocity));
        			v.setY(bestPosition.getY()-((magy/100)*standardvelocity));
        			p.setPosition(v);        			       			
        		}
        		else if (magy==0) {
        			v.setX(bestPosition.getX()+((magx/100)*standardvelocity));
        			v.setY(bestPosition.getY());
        			p.setPosition(v);        			
        		}
        		
        	}
        	else if (magx<0) {
        		if (magy>0)
        		{
        			v.setX(bestPosition.getX()-((magx/100)*standardvelocity));
        			v.setY(bestPosition.getY()+((magy/100)*standardvelocity));
        			p.setPosition(v);
        		}
        		else if (magy<0) {
        			v.setX(bestPosition.getX()-((magx/100)*standardvelocity));
        			v.setY(bestPosition.getY()-((magy/100)*standardvelocity));
        			p.setPosition(v);
        		}
        		else if (magy==0) {
        			v.setX(bestPosition.getX()-((magx/100)*standardvelocity));
        			v.setY(bestPosition.getY());
        			p.setPosition(v);
        		}        			
        	}
        	else if (magx==0){
        		if (magy>0)
        		{
        			v.setX(bestPosition.getX());
        			v.setY(bestPosition.getY()+((magy/100)*standardvelocity));
        			p.setPosition(v);
        		}
        		else if (magy<0) {
        			v.setX(bestPosition.getX());
        			v.setY(bestPosition.getY()-((magy/100)*standardvelocity));
        			p.setPosition(v);
        		}
        		else if (magy==0) {
        			v.setX(bestPosition.getX());
        			v.setY(bestPosition.getY());
        			p.setPosition(v);
        		}        		
        		        		
        	}
        	
        }
        
        
        /*
            if (swarm.getBestEval() < swarm.getOldEval()) {
                swarm.setOldEval(swarm.getBestEval());
            }

            for(int epoch = 1; epoch < 50; epoch++) {
            for (Particle p : particles) {
                p.updatePersonalBest();
                updateGlobalBest(p, swarm);
            }

            for (Particle p : particles) {
                p.updatePosition();
            }
            System.out.println("Gbest value for the Epoch number" +epoch +"is :" +swarm.getBestPosition());
            }
		*/
    }

    /**
     * Create a set of particles, each with random starting positions.
     * @return  an array of particles
     */
    public void initialize(List<Particle> particles, SwarmNew swarm) {
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
    private void updateGlobalBest (Particle particle, SwarmNew swarm) {
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
 

	@Override
	public String toString() {
		return "Swarm [roomId=" + roomId + ", bestPosition=" + bestPosition + ", bestEval=" + bestEval
				+ ", optionVertices=" + optionVertices + ", particles=" + particles + ", beginRange=" + beginRange
				+ ", endRange=" + endRange + ", oldEval=" + oldEval + "]";
	}
    
    
}

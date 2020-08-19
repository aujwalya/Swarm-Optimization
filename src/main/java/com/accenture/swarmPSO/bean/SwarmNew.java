package com.accenture.swarmPSO.bean;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
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
    		
    	
    	GlobalResultant = new Vector(0, 0);
    	
    	System.out.println("Initial bestPosition X::" + bestPosition.getX());
    	System.out.println("Initial bestPosition Y::" + bestPosition.getY());
    	
        for (Particle p : particles) {
        	
        	Vector v = new Vector(0,0);
        	
        	magx = p.getPosition().getX() - bestPosition.getX();
        	magy = p.getPosition().getY() - bestPosition.getY();
        	
        	System.out.println("Particle:" + p.getParticleId());
        	System.out.println("magx:" + magx);
        	System.out.println("magy:" + magy);
        	
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
        	
        	System.out.println("GlobalResultant X::" + GlobalResultant.getX());
        	System.out.println("GlobalResultant Y::" + GlobalResultant.getY());
        }
        
        bestPosition.add(GlobalResultant);
        swarm.setBestPosition(bestPosition);
        
        System.out.println("bestPosition X::" + bestPosition.getX());
    	System.out.println("bestPosition Y::" + bestPosition.getY());
        
        //update particle position
        
        
    	particles.stream().forEach(item -> {
        	
    		Vector v = new Vector (0,0);		
        	double itemmagx = item.getPosition().getX() - bestPosition.getX();
        	double itemmagy = item.getPosition().getY() - bestPosition.getY();
        	
        	if (itemmagx>0) {
        		if (itemmagy>0)
        		{
        			v.setX(bestPosition.getX()+((itemmagx/5)*standardvelocity));
        			v.setY(bestPosition.getY()+((itemmagy/5)*standardvelocity));        			        			        			       			        			
        		}
        		else if (itemmagy<0) {
        			v.setX(bestPosition.getX()+((itemmagx/5)*standardvelocity));
        			v.setY(bestPosition.getY()-((itemmagy/5)*standardvelocity));        			        			       			
        		}
        		else if (itemmagy==0) {
        			v.setX(bestPosition.getX()+((itemmagx/5)*standardvelocity));
        			v.setY(bestPosition.getY());		
        		}
        		
        	}
        	else if (itemmagx<0) {
        		if (itemmagy>0)
        		{
        			v.setX(bestPosition.getX()-((itemmagx/5)*standardvelocity));
        			v.setY(bestPosition.getY()+((itemmagy/5)*standardvelocity));
        		
        		}
        		else if (itemmagy<0) {
        			v.setX(bestPosition.getX()-((itemmagx/5)*standardvelocity));
        			v.setY(bestPosition.getY()-((itemmagy/5)*standardvelocity));
        		
        		}
        		else if (itemmagy==0) {
        			v.setX(bestPosition.getX()-((itemmagx/5)*standardvelocity));
        			v.setY(bestPosition.getY());
        		
        		}        			
        	}
        	else if (itemmagx==0){
        		if (itemmagy>0)
        		{
        			v.setX(bestPosition.getX());
        			v.setY(bestPosition.getY()+((itemmagy/5)*standardvelocity));
        		
        		}
        		else if (itemmagy<0) {
        			v.setX(bestPosition.getX());
        			v.setY(bestPosition.getY()-((itemmagy/5)*standardvelocity));
        		
        		}
        		else if (itemmagy==0) {
        			v.setX(bestPosition.getX());
        			v.setY(bestPosition.getY());
        			
        		}        		
        		        		
        	}
        	
        	item.setPosition(v);
        	
        	System.out.println("Particle Id:" + item.getParticleId());
        	System.out.println("Upated particle position X:" + item.getPosition().getX());
        	System.out.println("Upated particle position Y" + item.getPosition().getY());
        	        	
        });
        
        swarm.setParticles(particles);
        
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
            //updateGlobalBest(particle);           
            
        }
        bestPosition.set(310, 325);
        
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

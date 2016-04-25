import java.util.ArrayList;
import java.util.Random;


public class SimulationField {
	private ArrayList<Blob> blobs;
	private ArrayList<Predator> predators;
	private ArrayList<Food> foods;
	private int[] arr;
	Random r;
	
	public SimulationField(int[] arr){
		//the array of paramaters inputted by the user at the start of the simulation is loaded, and passed on towards each blob that is created.
		this.arr = arr;
		int startingPopulation = arr[0];
		int[] arr2 = new int[8];
		System.arraycopy(arr, 1, arr2, 0, arr2.length);
		r = new Random();
		blobs = new ArrayList<Blob>();
		predators = new ArrayList<Predator>();
		foods = new ArrayList<Food>();
		for(int i = 0; i < startingPopulation; i++){
			Blob b = new Blob(r.nextInt(1000) + 1, r.nextInt(1000) + 1, arr2);
			blobs.add(b);
		}
	}
	
	public SimulationField(){
		int startingPopulation = arr[0];
		int[] arr2 = new int[8];
		System.arraycopy(arr, 1, arr2, 0, arr2.length);
		r = new Random();
		blobs = new ArrayList<Blob>();
		predators = new ArrayList<Predator>();
		foods = new ArrayList<Food>();
		for(int i = 0; i < startingPopulation; i++){
			Blob b = new Blob(r.nextInt(1000) + 1, r.nextInt(1000) + 1, arr2);
			blobs.add(b);
		}
	}
	
	//moves blobs
	//returns ArrayList with moved blobs
	public ArrayList<Blob> moveBlobs(int maxwidth, int maxheight){
		for(Blob blob:blobs){
			
			//the movement from each rule is calculated and multiplied by the weighting for each specific blob
			float sumx = (float) ((moveAverageX(blob) * blob.getMoveAveragePositionMultiplier()) + (avoidCollisionX(blob) * blob.getAvoidCollisionMultiplier())  + (matchVelocityX(blob) * blob.getSameDirectionMultiplier()) + (moveClosestPreyX(blob) * blob.getGetFoodMultiplier()) + (avoidPredatorsX(blob) * blob.getAvoidPredatorMultiplier()));
			float sumy = (float) ((moveAverageY(blob) * blob.getMoveAveragePositionMultiplier()) + (avoidCollisionY(blob) * blob.getAvoidCollisionMultiplier())  + (matchVelocityY(blob) * blob.getSameDirectionMultiplier()) + (moveClosestPreyY(blob) * blob.getGetFoodMultiplier()) + (avoidPredatorsY(blob) * blob.getAvoidPredatorMultiplier()));

			blob.move(maxwidth, maxheight, sumx, sumy);
			
			//eats food
			for(Food f:foods){
				if(Math.abs(blob.getXPosition() - f.getXPosition()) < 4 && Math.abs(blob.getYPosition() - f.getYPosition()) < 4){
					f.loseEnergy();
					if(!f.isAlive()){
						foods.remove(f);
					}
					blob.eat();
				}
			}
			
			
			//dies if energy is too low
			blob.checkEnergy();
			if(!blob.isAlive()){
				blobs.remove(blob);
			}
			checkBreed(blob);
		}
	
		return blobs;
	}
	
	//moves predators
	//returns ArrayList with moved Predators
	public ArrayList<Predator> movePredators(int maxwidth, int maxheight){
		for(Predator p:predators){
			float sumx = (float)(moveClosestPreyX(p) * p.getMoveAveragePositionMultiplier());
			float sumy = (float)(moveClosestPreyY(p) * p.getMoveAveragePositionMultiplier());
			if(p.isAlive()){
				p.move(maxwidth,maxheight, sumx, sumy);
			}
			else{
				predators.remove(p);
			}
		}
		
		return predators;
	}
	
	//moves food
	//returns ArrayList with moved Food
	public ArrayList<Food> moveFood(int maxwidth, int maxheight){
		for(Food f:foods){
			if(f.isAlive()){
				f.move(maxwidth, maxheight, 0, 0);
			}
			else{
				foods.remove(f);
			}
		}
		
		return foods;
	}
	
	public void checkBreed(Blob blob){
		//if within certain distance of another blob, 1 in 100 chance of breeding
		for(Blob b:blobs){
			if(b != blob){
				if(Math.abs(blob.getXPosition() - b.getXPosition()) < 4 && Math.abs(blob.getYPosition() - b.getYPosition()) < 4){
					if(blob.canBreed()){
						if(r.nextInt(100) == 72){
							breed(blob, b);
						}
					}
				}
			}
		}
	}
	
	public void breed(Blob blob1, Blob blob2){
		//chooses how many traits to crossover
		int traitsToCrossover = r.nextInt(11) + 1;
		
		//chooses start point of crossover (two point crossover)
		int crossoverStartPoint = r.nextInt(11) + 1;
		
		Blob newBlob1 = new Blob((blob1.getXPosition() + blob2.getXPosition())/2, (blob1.getYPosition() + blob2.getYPosition())/2, blob1);
		Blob newBlob2 = new Blob((blob1.getXPosition() + blob2.getXPosition())/2, (blob1.getYPosition() + blob2.getYPosition())/2, blob2);
		
		
		//random values added or subtracted for mutation
		for(int i = crossoverStartPoint; (i < (crossoverStartPoint + traitsToCrossover)) && (i <= 11); i++){
			if(i == 1){
				if(r.nextInt(2) == 1){
					newBlob1.setMaxEnergy(blob2.getMaxEnergy() + (1 + (int)(Math.random() * ((30 - 1) + 1))));
					newBlob2.setMaxEnergy(blob1.getMaxEnergy() + (1 + (int)(Math.random() * ((30 - 1) + 1))));
				}
				else{
					newBlob1.setMaxEnergy(blob2.getMaxEnergy() - (1 + (int)(Math.random() * ((30 - 1) + 1))));
					newBlob2.setMaxEnergy(blob1.getMaxEnergy() - (1 + (int)(Math.random() * ((30 - 1) + 1))));
				}
			}
			if(i == 2){
				if(r.nextInt(2) == 1){
					newBlob1.setFoodFromEating(blob2.getFoodFromEating() + (1 + (int)(Math.random() * ((30 - 1) + 1))));
					newBlob2.setFoodFromEating(blob1.getFoodFromEating() + (1 + (int)(Math.random() * ((30 - 1) + 1))));
				}
				else{
					newBlob1.setFoodFromEating(blob2.getFoodFromEating() - (1 + (int)(Math.random() * ((30 - 1) + 1))));
					newBlob2.setFoodFromEating(blob1.getFoodFromEating() - (1 + (int)(Math.random() * ((30 - 1) + 1))));
				}
			}
			if(i == 3){
				if(r.nextInt(2) == 1){
					newBlob1.setAvoidCollisionMultiplier(blob2.getAvoidCollisionMultiplier() + (0.01 + (0.1 - 0.01) * r.nextDouble()));
					newBlob2.setAvoidCollisionMultiplier(blob1.getAvoidCollisionMultiplier() + (0.01 + (0.1 - 0.01) * r.nextDouble()));
				}
				else{
					newBlob1.setAvoidCollisionMultiplier(blob2.getAvoidCollisionMultiplier() - (0.01 + (0.1 - 0.01) * r.nextDouble()));
					newBlob2.setAvoidCollisionMultiplier(blob1.getAvoidCollisionMultiplier() - (0.01 + (0.1 - 0.01) * r.nextDouble()));
				}
			}
			if(i == 4){
				if(r.nextInt(2) == 1){
					newBlob1.setAvoidPredatorMultiplier(blob2.getAvoidPredatorMultiplier() + (0.01 + (0.1 - 0.01) * r.nextDouble()));
					newBlob2.setAvoidPredatorMultiplier(blob1.getAvoidPredatorMultiplier() + (0.01 + (0.1 - 0.01) * r.nextDouble()));
				}
				else{
					newBlob1.setAvoidPredatorMultiplier(blob2.getAvoidPredatorMultiplier() - (0.01 + (0.1 - 0.01) * r.nextDouble()));
					newBlob2.setAvoidPredatorMultiplier(blob1.getAvoidPredatorMultiplier() - (0.01 + (0.1 - 0.01) * r.nextDouble()));
				}
			}
			if(i == 5){
				if(r.nextInt(2) == 1){
					newBlob1.setTimeBeforeRebreed(blob2.getTimeBeforeRebreed() + (1 + (int)(Math.random() * ((30 - 1) + 1))));
					newBlob2.setTimeBeforeRebreed(blob1.getTimeBeforeRebreed() + (1 + (int)(Math.random() * ((30 - 1) + 1))));
				}
				else{
					newBlob1.setTimeBeforeRebreed(blob2.getTimeBeforeRebreed() - (1 + (int)(Math.random() * ((30 - 1) + 1))));
					newBlob2.setTimeBeforeRebreed(blob1.getTimeBeforeRebreed() - (1 + (int)(Math.random() * ((30 - 1) + 1))));
				}
			}
			if(i == 6){
				if(r.nextInt(2) == 1){
					newBlob1.setMaxVelocity(blob2.getMaxVelocity() + (float)(0.1 + (int)(Math.random() * ((2 - 0.1) + 1))));
					newBlob2.setMaxVelocity(blob1.getMaxVelocity() + (float)(0.1 + (int)(Math.random() * ((2 - 0.1) + 1))));
				}
				else{
					newBlob1.setMaxVelocity(blob2.getMaxVelocity() - (float)(0.1 + (int)(Math.random() * ((2 - 0.1) + 1))));
					newBlob2.setMaxVelocity(blob1.getMaxVelocity() - (float)(0.1 + (int)(Math.random() * ((2 - 0.1) + 1))));
				}
			}
			if(i == 7){
				if(r.nextInt(2) == 1){
					newBlob1.setEnergyLostMultiplier(blob2.getEnergyLostMultiplier() + (0.01 + (0.03 - 0.01) * r.nextDouble()));
					newBlob2.setEnergyLostMultiplier(blob1.getEnergyLostMultiplier() + (0.01 + (0.03 - 0.01) * r.nextDouble()));
				}
				else{
					newBlob1.setEnergyLostMultiplier(blob2.getEnergyLostMultiplier() - (0.01 + (0.03 - 0.01) * r.nextDouble()));
					newBlob2.setEnergyLostMultiplier(blob1.getEnergyLostMultiplier() - (0.01 + (0.03 - 0.01) * r.nextDouble()));
				}
			}
			if(i == 8){
				if(r.nextInt(2) == 1){
					newBlob1.setGetFoodMultiplier(blob2.getGetFoodMultiplier() + (0.1 + (1 - 0.1) * r.nextDouble()));
					newBlob2.setGetFoodMultiplier(blob1.getGetFoodMultiplier() + (0.1 + (1 - 0.1) * r.nextDouble()));
				}
				else{
					newBlob1.setGetFoodMultiplier(blob2.getGetFoodMultiplier() - (0.1 + (1 - 0.1) * r.nextDouble()));
					newBlob2.setGetFoodMultiplier(blob1.getGetFoodMultiplier() - (0.1 + (1 - 0.1) * r.nextDouble()));
				}
			}
			if(i == 9){
				if(r.nextInt(2) == 1){
					newBlob1.setMoveAveragePositionMultiplier(blob2.getMoveAveragePositionMultiplier() + (0.01 + (0.1 - 0.01) * r.nextDouble()));
					newBlob2.setMoveAveragePositionMultiplier(blob1.getMoveAveragePositionMultiplier() + (0.01 + (0.1 - 0.01) * r.nextDouble()));
				}
				else{
					newBlob1.setMoveAveragePositionMultiplier(blob2.getMoveAveragePositionMultiplier() - (0.01 + (0.1 - 0.01) * r.nextDouble()));
					newBlob2.setMoveAveragePositionMultiplier(blob1.getMoveAveragePositionMultiplier() - (0.01 + (0.1 - 0.01) * r.nextDouble()));
				}
			}
			if(i == 10){
				if(r.nextInt(2) == 1){
					newBlob1.setSameDirectionMultiplier(blob2.getSameDirectionMultiplier() + (0.01 + (0.1 - 0.01) * r.nextDouble()));
					newBlob2.setSameDirectionMultiplier(blob1.getSameDirectionMultiplier() + (0.01 + (0.1 - 0.01) * r.nextDouble()));
				}
				else{
					newBlob1.setSameDirectionMultiplier(blob2.getSameDirectionMultiplier() - (0.01 + (0.1 - 0.01) * r.nextDouble()));
					newBlob2.setSameDirectionMultiplier(blob1.getSameDirectionMultiplier() - (0.01 + (0.1 - 0.01) * r.nextDouble()));
				}
			}
			if(i == 11){
				if(r.nextInt(2) == 1){
					newBlob1.setTimeBeforeNaturalDeath(blob2.getTimeBeforeNaturalDeath() + (1 + (int)(Math.random() * ((50 - 1) + 1))));
					newBlob2.setTimeBeforeNaturalDeath(blob1.getTimeBeforeNaturalDeath() + (1 + (int)(Math.random() * ((50 - 1) + 1))));
				}
				else{
					newBlob1.setTimeBeforeNaturalDeath(blob2.getTimeBeforeNaturalDeath() - (1 + (int)(Math.random() * ((50 - 1) + 1))));
					newBlob2.setTimeBeforeNaturalDeath(blob1.getTimeBeforeNaturalDeath() - (1 + (int)(Math.random() * ((50 - 1) + 1))));
				}
			}
		}
		
		blobs.add(newBlob1);
		blobs.add(newBlob2);
		blob1.setCurrentTimeBeforeRebreed(blob1.getTimeBeforeRebreed());
		blob2.setCurrentTimeBeforeRebreed(blob2.getTimeBeforeRebreed());
	}
	
	//moves organism towards average x position of blobs
	public float moveAverageX(Organism b){
		float c = 0;
		for(Blob blob:blobs){
			if(b != blob){
				c += blob.getXPosition();
			}
		}
		
		//if the organism is a blob then it isn't counted in the calculation
		if (b.getClass() == Blob.class){
			c = c/(blobs.size() - 1);
			return (c - b.getXPosition())/100;
		}
		else{
			c = c/blobs.size();
			return c/100;
		}
	}
	
	//moves organism towards average y position of blobs 
	public float moveAverageY(Organism b){
		float c = 0;
		for(Blob blob:blobs){
			if(b != blob){
				c += blob.getYPosition();
			}
		}
		
		if(b.getClass() == Blob.class){
			c = c/(blobs.size() - 1);
			return (c - b.getYPosition())/100;
		}
		else{
			c = c/blobs.size();return c/100;
		}
	}
	
	//avoid collisions with blobs on the x axis
	public float avoidCollisionX(Organism b){
		float c = 0;
		for(Blob blob:blobs){
			if( b != blob){
				if(Math.abs(b.getXPosition() - blob.getXPosition()) < 10){
					c += b.getXPosition() - blob.getXPosition();
				}
			}
		}
		
		return c;
	}
	
	//avoid collisions with blobs on the y axis
	public float avoidCollisionY(Organism b){
		float c = 0;
		for(Blob blob:blobs){
			if( b != blob){
				if(Math.abs(b.getYPosition() - blob.getYPosition()) < 10){
					c += b.getYPosition() - blob.getYPosition();
				}
			}
		}
		
		return c;
	}
	
	//match velocity on the x axis
	public float matchVelocityX(Organism b){
		float c = 0;
		for(Blob blob:blobs){
			if(b != blob){
				c = blob.getXVelocity();
			}
		}
		
		c = c/(blobs.size() - 1);
		return (c - b.getXVelocity())/8;
	}
	
	//match velocity on the y axis
	public float matchVelocityY(Organism b){
		float c = 0;
		for(Blob blob:blobs){
			if(b != blob){
				c = blob.getYVelocity();
			}
		}
		
		c = c/(blobs.size() - 1);
		return (c - b.getYVelocity())/8;
	}
	
	//avoid predators on the x axis
	public float avoidPredatorsX(Organism b){
		float c = 0;
		int predatorsInRange = 0;
		for(Predator predator:predators){
			if((Math.abs(b.getYPosition() - predator.getYPosition())) + (Math.abs(b.getXPosition() - predator.getXPosition())) < 250){
				c += predator.getXPosition();
				predatorsInRange++;
			}
		}
			
		c = c/predatorsInRange;
		if(predatorsInRange > 0){
			return -500 * ((c - b.getXPosition())/100);
		}
		else{
			return 0;
		}
		
	}
	
	//avoid predators on the y axis
	public float avoidPredatorsY(Organism b){
		float c = 0;
		int predatorsInRange = 0;
		for(Predator predator:predators){
			if((Math.abs(b.getYPosition() - predator.getYPosition())) + (Math.abs(b.getXPosition() - predator.getXPosition())) < 250){
				c += predator.getYPosition();
				predatorsInRange++;
			}
		}
			
		c = c/predatorsInRange;
		if(predatorsInRange > 0){
			return -500 * ((c - b.getYPosition())/100);
		}
		else{
			return 0;
		}
	}
	
	//moves towards the closest prey
	public float moveClosestPreyX(Organism o){
		
		float c = 0;
		ArrayList<? extends Organism> search = new ArrayList<Organism>();
		//the search space for the prey is determined by what type of Organism o is.
		if(o.getClass() == Blob.class){
			search = foods;
		}
		else if(o.getClass() == Predator.class){
			search = blobs;
		}
		
		//the closest target is found
		if(!search.isEmpty()){
			Organism target = search.get(0);
			for(Organism organism:search){
					if((Math.abs(o.getYPosition() - organism.getYPosition())) + (Math.abs(o.getXPosition() - organism.getXPosition())) < 500){
						if((Math.abs(o.getYPosition() - organism.getYPosition())) + (Math.abs(o.getXPosition() - organism.getXPosition())) < (Math.abs(o.getYPosition() - target.getYPosition())) + (Math.abs(o.getXPosition() - target.getXPosition()))){
							target = organism;
						}
					}
			}
			c = -(o.getXPosition() - target.getXPosition());
		}
		
		return c;
	}
	
	//moves towards the closest prey
	public float moveClosestPreyY(Organism o){
		
		float c = 0;
		ArrayList<? extends Organism> search = new ArrayList<Organism>();
		
		//the search space for the prey is determined by what type of Organism o is.
		if(o.getClass() == Blob.class){
			search = foods;
		}
		else if(o.getClass() == Predator.class){
			search = blobs;
		}
		
		//the closest target is found
		if(!search.isEmpty()){
			Organism target = search.get(0);
			for(Organism organism:search){
					if((Math.abs(o.getYPosition() - organism.getYPosition())) + (Math.abs(o.getXPosition() - organism.getXPosition())) < 500){
						if((Math.abs(o.getYPosition() - organism.getYPosition())) + (Math.abs(o.getXPosition() - organism.getXPosition())) < (Math.abs(o.getYPosition() - target.getYPosition())) + (Math.abs(o.getXPosition() - target.getXPosition()))){
							target = organism;
						}
					}
			}
			c = -(o.getYPosition() - target.getYPosition());
		}
		
		return c;
	}
	
	//add predator
	public void addPredator(int maxwidth, int maxheight){
		Predator p = new Predator(r.nextFloat() * (maxwidth + 1), r.nextFloat() * (maxheight + 1));
		predators.add(p);
	}
	
	public void addPredatorRandom(int maxwidth, int maxheight){
		if(r.nextInt(500) == 121){
			int rand = r.nextInt(4);
			if(rand == 0){
				addPredatorTopLeft();
			}
			else if(rand == 1){
				addPredatorTopRight(maxwidth);
			}
			else if(rand == 2){
				addPredatorBottomLeft(maxheight);
			}
			else if(rand == 3){
				addPredatorBottomRight(maxwidth, maxheight);
			}
		}
	}
	
	//add food
	public void addFoodRandom(int maxwidth, int maxheight){
		if(r.nextInt(180) == 121){
			int rand = r.nextInt(4);
			if(rand == 0){
				addFoodTopLeft();
			}
			else if(rand == 1){
				addFoodTopRight(maxwidth);
			}
			else if(rand == 2){
				addFoodBottomLeft(maxheight);
			}
			else if(rand == 3){
				addFoodBottomRight(maxwidth, maxheight);
			}
		}
	}
	
	//add food
	public void addFoodRandom2(int maxwidth, int maxheight){
		Food f = new Food(r.nextFloat() * (maxwidth + 1), r.nextFloat() * (maxheight + 1));
		foods.add(f);
	}
	
	public void addFoodTopLeft(){
		Food f = new Food(10, 10);
		foods.add(f);
	}
	
	public void addFoodTopRight(int maxwidth){
		Food f = new Food(maxwidth - 10, 10);
		foods.add(f);
	}
	
	public void addFoodBottomLeft(int maxheight){
		Food f = new Food(10, maxheight - 10);
		foods.add(f);
	}
	
	public void addFoodBottomRight(int maxwidth, int maxheight){
		Food f = new Food(maxwidth - 10, maxheight - 10);
		foods.add(f);
	}
	
	public void addPredatorTopLeft(){
		Predator p = new Predator(10, 10);
		predators.add(p);
	}
	
	public void addPredatorTopRight(int maxwidth){
		Predator p = new Predator(maxwidth - 10, 10);
		predators.add(p);
	}
	
	public void addPredatorBottomLeft(int maxheight){
		Predator p = new Predator(10, maxheight - 10);
		predators.add(p);
	}
	
	public void addPredatorBottomRight(int maxwidth, int maxheight){
		Predator p = new Predator(maxwidth - 10, maxheight - 10);
		predators.add(p);
	}
	
	public double getAverageMaxEnergy(){
		double total = 0;
		for (Blob b:blobs){
			total += b.getMaxEnergy();
		}
		
		return total/blobs.size();
	}
	
	public int getAverageFitness(){
		int total = 0;
		for (Blob b:blobs){
			total += b.fitness();
		}
		
		return total/blobs.size();
	}
	
	public int getPopulation(){
		return blobs.size();
	}
	
	public float getAverageMaxVelocity(){
		float total = 0;
		for (Blob b:blobs){
			total += b.getMaxVelocity();
		}
		
		return total/blobs.size();
	}
	
	public float getAverageXVelocity(){
		float total = 0;
		for (Blob b:blobs){
			total += b.getXVelocity();
		}
		
		return total/blobs.size();
	}
	
	public float getAverageYVelocity(){
		float total = 0;
		for (Blob b:blobs){
			total += b.getYVelocity();
		}
		
		return total/blobs.size();
	}
	
	public int[] getArr(){
		return arr;
	}
	
	//checks to see if there are still blobs remaining on the field
	public boolean checkBlobsPresent(){
		if(blobs.size() <= 1){
			return false;
		}
		else{
			return true;
		}
	}
}

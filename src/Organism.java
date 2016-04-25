import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public abstract class Organism {
		
		protected float x;
		protected float y;
		protected float xVelocity;
		protected float yVelocity;
		protected float maxVelocity;
		protected boolean isAlive;
		protected double maxEnergy;
		protected double currentEnergy;
		protected int timeBeforeNaturalDeath;
		protected int currentTimeBeforeNaturalDeath;
		protected double foodFromEating;
		
		protected double moveAveragePositionMultiplier;
		protected double energyLostMultiplier;
		
		protected Color colour;
		
		//default constructor, generates random values for variables between the 'default values'
		protected Organism(float x, float y){
			this.x = x;
			this.y = y;
			Random rand = new Random();
			xVelocity = rand.nextInt(10);
			yVelocity = rand.nextInt(10); 
			maxEnergy = 750 + (int)(Math.random() * ((1300 - 750) + 1));
			currentEnergy = maxEnergy;
			isAlive = true;
			foodFromEating = 200 + (int)(Math.random() * ((400 - 200) + 1));
			maxVelocity = 7 + (int)(Math.random() * ((13 - 7) + 1));
			moveAveragePositionMultiplier = 0.8 + (1.2 - 0.8) * rand.nextDouble();
			energyLostMultiplier = 0.1 + (0.3 - 0.1) * rand.nextDouble();
			timeBeforeNaturalDeath = 1100 + (int)(Math.random() * ((2000 - 1100) + 1));
			currentTimeBeforeNaturalDeath = timeBeforeNaturalDeath;
		}
		
		////constructor that generates random values for variables between the range specified by the user
		protected Organism(float x, float y, int energyStart, int energyEnd, int foodStart, int foodEnd, int deathStart, int deathEnd){
			this.x = x;
			this.y = y;
			Random rand = new Random();
			xVelocity = rand.nextInt(10);
			yVelocity = rand.nextInt(10); 
			maxEnergy = energyStart + (int)(Math.random() * ((energyEnd - energyStart) + 1));
			currentEnergy = maxEnergy;
			isAlive = true;
			foodFromEating = foodStart + (int)(Math.random() * ((foodEnd - foodStart) + 1));
			maxVelocity = 7 + (int)(Math.random() * ((13 - 7) + 1));
			moveAveragePositionMultiplier = 0.8 + (1.2 - 0.8) * rand.nextDouble();
			energyLostMultiplier = 0.1 + (0.3 - 0.1) * rand.nextDouble();
			timeBeforeNaturalDeath = deathStart + (int)(Math.random() * ((deathEnd - deathStart) + 1));;
			currentTimeBeforeNaturalDeath = timeBeforeNaturalDeath;
		}

		//the organism is drawn
		public void draw(Graphics g){
			g.setColor(colour);
			g.fillOval((int)x, (int)y, 4, 4);
		}
		
		//abstract method - each subclass of Organism should have its own implementation and style of movement
		abstract void move(int maxwidth, int maxheight, float sumx, float sumy);
		
		public float getXPosition(){
			return x;
		}
		
		public float getYPosition(){
			return y;
		}
		
		public float getXVelocity(){
			return xVelocity;
		}
		
		public float getYVelocity(){
			return yVelocity;
		}
		
		public double getCurrentEnergy(){
			return currentEnergy;
		}
		
		public double getMaxEnergy(){
			return maxEnergy;
		}
		
		public double getFoodFromEating(){
			return foodFromEating;
		}
		
		public float getMaxVelocity(){
			return maxVelocity;
		}
		
		public double getMoveAveragePositionMultiplier() {
			return moveAveragePositionMultiplier;
		}
		
		public double getEnergyLostMultiplier(){
			return energyLostMultiplier;
		}
		
		public int getTimeBeforeNaturalDeath() {
			return timeBeforeNaturalDeath;
		}
		
		public void setMaxEnergy(double maxenergy) {
			this.maxEnergy = maxenergy;
		}

		public void setFoodFromEating(double foodFromEating) {
			this.foodFromEating = foodFromEating;
		}
		
		public void setMaxVelocity(float maxvelocity) {
			this.maxVelocity = maxvelocity;
		}

		public void setMoveAveragePositionMultiplier(double moveAveragePositionMultiplier) {
			this.moveAveragePositionMultiplier = moveAveragePositionMultiplier;
		}
		
		public void setEnergyLostMultiplier(double energyLostMultiplier) {
			this.energyLostMultiplier = energyLostMultiplier;
		}

		public void setCurrentEnergy(double currentenergy) {
			this.currentEnergy = currentenergy;
		}
		
		public void setXVelocity(float xvelocity) {
			this.xVelocity = xvelocity;
		}

		public void setYVelocity(float yvelocity) {
			this.yVelocity = yvelocity;
		}

		public void setTimeBeforeNaturalDeath(int timeBeforeNaturalDeath) {
			this.timeBeforeNaturalDeath = timeBeforeNaturalDeath;
		}
		
		//eats and gains food from eating
		public void eat(){
			currentEnergy += foodFromEating;
			if(currentEnergy > maxEnergy){
				currentEnergy = maxEnergy;
			}
		}
		
		public boolean isAlive(){
			return isAlive;
		}
		
		//checks if the organism should die
		public void checkEnergy(){
			if(currentEnergy <= 0 || currentTimeBeforeNaturalDeath <= 0){
				die();
			}
		}

		public void die(){
			isAlive = false;
		}
	}

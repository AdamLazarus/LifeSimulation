import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Random;


public class Blob extends Organism{
	private int timeBeforeRebreed;
	private int currentTimeBeforeRebreed;
	private double avoidCollisionMultiplier;
	private double sameDirectionMultiplier;
	private double avoidPredatorMultiplier;
	private double getFoodMultiplier;
	
	public Blob(float x, float y, int[] arr) {
		super(x, y, arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);
		
		int rebreedStart = arr[6];
		int rebreedEnd = arr[7];
		
		Random rand = new Random();
		timeBeforeRebreed = rebreedStart + (int)(Math.random() * ((rebreedEnd - rebreedStart) + 1));
		currentTimeBeforeRebreed = timeBeforeRebreed;
		avoidCollisionMultiplier = 0.8 + (1.2 - 0.8) * rand.nextDouble();
		sameDirectionMultiplier = 0.8 + (1.2 - 0.8) * rand.nextDouble();
		avoidPredatorMultiplier = 0.8 + (1.2 - 0.8) * rand.nextDouble();
		getFoodMultiplier = 95 + (105 - 95) * rand.nextDouble();
		colour = Color.white;
	}

	
	//copy another Blob
	public Blob(float x, float y, Blob b){
		super(x, y);
		xVelocity = b.getXVelocity();
		yVelocity = b.getYVelocity(); 
		maxEnergy = b.getMaxEnergy();
		currentEnergy = maxEnergy;
		foodFromEating = b.getFoodFromEating();
		timeBeforeRebreed = b.getTimeBeforeRebreed();
		currentTimeBeforeRebreed = timeBeforeRebreed;
		maxVelocity = b.getMaxVelocity();
		moveAveragePositionMultiplier = b.getMoveAveragePositionMultiplier();
		avoidCollisionMultiplier = b.getAvoidCollisionMultiplier();
		sameDirectionMultiplier = b.getSameDirectionMultiplier();
		avoidPredatorMultiplier = b.getAvoidPredatorMultiplier();
		getFoodMultiplier = b.getGetFoodMultiplier();
		energyLostMultiplier = b.getEnergyLostMultiplier();
		colour = Color.white;
	}
	
	//move the Blob
	public void move(int maxwidth, int maxheight, float sumx, float sumy){
		//necessary operations for each cycle are performed
		currentEnergy -= Math.abs((xVelocity + yVelocity)*energyLostMultiplier);
		currentTimeBeforeRebreed--;
		currentTimeBeforeNaturalDeath--;
		
		//velocity is altered, and brought back within limits if it gets too far in one direction
		xVelocity += sumx;
		yVelocity += sumy;
		
		
		if(xVelocity > 10){
			xVelocity = 10;
		}
		else if (xVelocity < -10){
			xVelocity = -10;
		}
		
		if(yVelocity > 15){
			yVelocity = 10;
		}
		else if (yVelocity < -10){
			yVelocity = -10;
		}
		
		
		//the position is altered according to their velocity, and they are brought back within the bounds of the field if they go outside
		x += xVelocity;
		y += yVelocity;
		
		if(x >= maxwidth){
			x = (maxwidth - 5);
			xVelocity = -(xVelocity);
		}
		else if(x <= 0){
			x = 5;
			xVelocity = -(xVelocity);
		}
		
		if(y >= maxheight){
			y = (maxheight - 5);
			yVelocity = -(yVelocity);
		}
		else if (y <= 0){
			y = 5;
			yVelocity = -(yVelocity);
		}
	}

	//calculates the fitness of the organism
	public int fitness(){
		return (int) maxEnergy + ((int)foodFromEating * 2) - timeBeforeRebreed;
	}
	
	
	public int getTimeBeforeRebreed(){
		return timeBeforeRebreed;
	}


	public double getAvoidCollisionMultiplier() {
		return avoidCollisionMultiplier;
	}

	public double getSameDirectionMultiplier() {
		return sameDirectionMultiplier;
	}

	public double getAvoidPredatorMultiplier() {
		return avoidPredatorMultiplier;
	}

	public double getGetFoodMultiplier() {
		return getFoodMultiplier;
	}
	
	
	public boolean canBreed(){
		if(currentTimeBeforeRebreed <= 0){
			return true;
		}
		else{
			return false;
		}
	}
	

	public void setTimeBeforeRebreed(int timeBeforeRebreed) {
		this.timeBeforeRebreed = timeBeforeRebreed;
	}

	public void setAvoidCollisionMultiplier(double avoidCollisionMultiplier) {
		this.avoidCollisionMultiplier = avoidCollisionMultiplier;
	}

	public void setSameDirectionMultiplier(double sameDirectionMultiplier) {
		this.sameDirectionMultiplier = sameDirectionMultiplier;
	}

	public void setAvoidPredatorMultiplier(double avoidPredatorMultiplier) {
		this.avoidPredatorMultiplier = avoidPredatorMultiplier;
	}

	public void setGetFoodMultiplier(double getFoodMultiplier) {
		this.getFoodMultiplier = getFoodMultiplier;
	}

	public void setCurrentTimeBeforeRebreed(int currentTimeBeforeRebreed) {
		this.currentTimeBeforeRebreed = currentTimeBeforeRebreed;
	}
	
}

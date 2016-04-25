import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Random;


public class Food extends Organism {
	
	public Food(float x, float y){
		super(x, y);
		colour = Color.yellow;
	}

	//move the food
	public void move(int maxwidth, int maxheight, float sumx, float sumy){
		//necessary operations for each cycle are performed
		currentEnergy -= Math.abs((xVelocity + yVelocity)*energyLostMultiplier);
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
		
		checkEnergy();
	}
	
	//the food loses energy, for example if it's eaten by a blob
	public void loseEnergy(){
		if((currentEnergy -= 20) <= 0){
			isAlive = false;
		}
	}
	
}

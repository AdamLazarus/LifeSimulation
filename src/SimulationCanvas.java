import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class SimulationCanvas extends Canvas {
	
	SimulationField field;
	
	public SimulationCanvas(SimulationField field){
		this.field = field;
	}
	
	//all of the organism are moved and then drawn
	public void paint(Graphics g){
		ArrayList<Blob> blobs = field.moveBlobs(this.getWidth(), this.getHeight());
		ArrayList<Predator> predators = field.movePredators(this.getWidth(), this.getHeight());
		ArrayList<Food> foods = field.moveFood(this.getWidth(), this.getHeight());
		
		for (Blob b:blobs){
			b.draw(g);
		}
		
		for (Predator p:predators){
			p.draw(g);
		}
		
		for (Food f:foods){
			f.draw(g);
		}
		
	}
	
	
	public SimulationField getField(){
		return field;
	}
	

	
	
}

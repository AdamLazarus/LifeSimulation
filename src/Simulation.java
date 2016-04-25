import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.Timer;

import javax.swing.SwingUtilities;


public class Simulation {
	
	private SimulationWindow window;
	
	public Simulation(){
		initialise();
	}
	
	public final void initialise(){
		
		//the starting input window is created
		final JFrame startWindow = new JFrame();
		Container contentPane = startWindow.getContentPane();
		startWindow.setTitle("New Simulation");
		startWindow.setSize(450, 250);
		startWindow.setVisible(true);
		
		//the form is created and laid out using the SpringLayout class
		JLabel enterPopulation = new JLabel("Starting Population: ");
		final JTextField populationField = new JTextField(3);
		populationField.setText("25");
		JLabel enterEnergy = new JLabel("Starting Max Energy Between: ");
		final JTextField energyField1 = new JTextField(5);
		final JTextField energyField2 = new JTextField(5);
		energyField1.setText("750");
		energyField2.setText("1300");
		JLabel enterFood = new JLabel("Starting Food From Eating Between: ");
		final JTextField foodField1 = new JTextField(5);
		final JTextField foodField2 = new JTextField(5);
		foodField1.setText("200");
		foodField2.setText("400");
		JLabel enterDeath = new JLabel("Starting Time Before Natural Death Between: ");
		final JTextField deathField1 = new JTextField(5);
		final JTextField deathField2 = new JTextField(5);
		deathField1.setText("1100");
		deathField2.setText("2000");
		JLabel enterRebreed = new JLabel("Starting Time Before Rebreed Between: ");
		final JTextField rebreedField1 = new JTextField(5);
		final JTextField rebreedField2 = new JTextField(5);
		rebreedField1.setText("300");
		rebreedField2.setText("700");
		JButton ok = new JButton("OK");
		JLabel and = new JLabel("&");
		JLabel and2 = new JLabel("&");
		JLabel and3 = new JLabel("&");
		JLabel and4 = new JLabel("&");
		JLabel text = new JLabel("<html><body>Please enter the parameters that you would like to use for the simulation.<br/> The default values are recommended for the most realistic simulation.</body></html>");
		
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		contentPane.add(enterPopulation);
		contentPane.add(enterEnergy);
		contentPane.add(enterFood);
		contentPane.add(enterDeath);
		contentPane.add(enterRebreed);
		contentPane.add(populationField);
		contentPane.add(energyField1);
		contentPane.add(and);
		contentPane.add(and2);
		contentPane.add(and3);
		contentPane.add(and4);
		contentPane.add(energyField2);
		contentPane.add(foodField1);
		contentPane.add(foodField2);
		contentPane.add(deathField1);
		contentPane.add(deathField2);
		contentPane.add(rebreedField1);
		contentPane.add(rebreedField2);
		contentPane.add(ok);
		contentPane.add(text);
		//each object of the form is laid out relative to the other objects
		layout.putConstraint(SpringLayout.WEST, text, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, text, 5, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, enterPopulation, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, enterPopulation, 45, SpringLayout.NORTH, text);
		layout.putConstraint(SpringLayout.WEST, populationField, 5, SpringLayout.EAST, enterPopulation);
		layout.putConstraint(SpringLayout.NORTH, populationField, 45, SpringLayout.NORTH, text);
		
		layout.putConstraint(SpringLayout.WEST, enterEnergy, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, enterEnergy, 5, SpringLayout.SOUTH, enterPopulation);
		layout.putConstraint(SpringLayout.WEST, energyField1, 5, SpringLayout.EAST, enterEnergy);
		layout.putConstraint(SpringLayout.NORTH, energyField1, 5, SpringLayout.SOUTH, enterPopulation);
		layout.putConstraint(SpringLayout.WEST, and, 3, SpringLayout.EAST, energyField1);
		layout.putConstraint(SpringLayout.NORTH, and, 5, SpringLayout.SOUTH, enterPopulation);
		layout.putConstraint(SpringLayout.WEST, energyField2, 3, SpringLayout.EAST, and);
		layout.putConstraint(SpringLayout.NORTH, energyField2, 5, SpringLayout.SOUTH, enterPopulation);
		
		layout.putConstraint(SpringLayout.WEST, enterFood, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, enterFood, 5, SpringLayout.SOUTH, enterEnergy);
		layout.putConstraint(SpringLayout.WEST, foodField1, 5, SpringLayout.EAST, enterFood);
		layout.putConstraint(SpringLayout.NORTH, foodField1, 5, SpringLayout.SOUTH, enterEnergy);
		layout.putConstraint(SpringLayout.WEST, and2, 3, SpringLayout.EAST, foodField1);
		layout.putConstraint(SpringLayout.NORTH, and2, 5, SpringLayout.SOUTH, enterEnergy);
		layout.putConstraint(SpringLayout.WEST, foodField2, 3, SpringLayout.EAST, and2);
		layout.putConstraint(SpringLayout.NORTH, foodField2, 5, SpringLayout.SOUTH, enterEnergy);
		
		layout.putConstraint(SpringLayout.WEST, enterDeath, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, enterDeath, 5, SpringLayout.SOUTH, enterFood);
		layout.putConstraint(SpringLayout.WEST, deathField1, 5, SpringLayout.EAST, enterDeath);
		layout.putConstraint(SpringLayout.NORTH, deathField1, 5, SpringLayout.SOUTH, enterFood);
		layout.putConstraint(SpringLayout.WEST, and3, 3, SpringLayout.EAST, deathField1);
		layout.putConstraint(SpringLayout.NORTH, and3, 5, SpringLayout.SOUTH, enterFood);
		layout.putConstraint(SpringLayout.WEST, deathField2, 3, SpringLayout.EAST, and3);
		layout.putConstraint(SpringLayout.NORTH, deathField2, 5, SpringLayout.SOUTH, enterFood);
		
		layout.putConstraint(SpringLayout.WEST, enterRebreed, 5, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, enterRebreed, 5, SpringLayout.SOUTH, enterDeath);
		layout.putConstraint(SpringLayout.WEST, rebreedField1, 5, SpringLayout.EAST, enterDeath);
		layout.putConstraint(SpringLayout.NORTH, rebreedField1, 5, SpringLayout.SOUTH, enterDeath);
		layout.putConstraint(SpringLayout.WEST, and4, 3, SpringLayout.EAST, rebreedField1);
		layout.putConstraint(SpringLayout.NORTH, and4, 5, SpringLayout.SOUTH, enterDeath);
		layout.putConstraint(SpringLayout.WEST, rebreedField2, 3, SpringLayout.EAST, and4);
		layout.putConstraint(SpringLayout.NORTH, rebreedField2, 5, SpringLayout.SOUTH, enterDeath);
		
		layout.putConstraint(SpringLayout.WEST, ok, 10, SpringLayout.WEST, enterPopulation);
		layout.putConstraint(SpringLayout.NORTH, ok, 90, SpringLayout.SOUTH, enterPopulation);
		
		//user input is submitted and passed to the field as an array
		ok.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent event){
	    		int[] arr = new int[9];
	    		arr[0] = Integer.parseInt(populationField.getText());
	    		arr[1] = Integer.parseInt(energyField1.getText());
	    		arr[2] = Integer.parseInt(energyField2.getText());
	    		arr[3] = Integer.parseInt(foodField1.getText());
	    		arr[4] = Integer.parseInt(foodField2.getText());
	    		arr[5] = Integer.parseInt(deathField1.getText());
	    		arr[6] = Integer.parseInt(deathField2.getText());
	    		arr[7] = Integer.parseInt(rebreedField1.getText());
	    		arr[8] = Integer.parseInt(rebreedField2.getText());
	    		
	    		SimulationField field = new SimulationField(arr);
	    		SimulationCanvas canvas = new SimulationCanvas(field);
	    		window = new SimulationWindow(canvas);
	    		window.setVisible(true);
	    		startWindow.setVisible(false);
	    	}
	    });

	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args){
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Simulation simulate = new Simulation();
			}
		});
	}

}

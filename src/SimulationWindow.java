import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.TimerTask;

import javax.swing.*;



public class SimulationWindow extends JFrame {
	
	private SimulationCanvas canvas;
	private Timer timer;
	
	public SimulationWindow(SimulationCanvas theCanvas){
		canvas = theCanvas;
	    
		//initiates window
		setTitle("Life Simulation");
		setSize(1200, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getContentPane().setBackground(Color.BLACK);
		
		this.add(canvas);
		final SimulationPanel panel = new SimulationPanel();
		this.add(panel, BorderLayout.SOUTH);
		panel.setPreferredSize(new Dimension(this.getWidth(), 30));
		timer = new Timer(50, new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				//a dialogue box with a message is displayed if all of the blobs have died
				if(!canvas.getField().checkBlobsPresent()){
					timer.stop();
					JDialog box = new JDialog();
					box.setSize(250, 170);
					
					JLabel deadLabel = new JLabel("All of your blobs have died!");
					box.add(deadLabel);
					
					box.setVisible(true);
					
				}
				//with every cycle the organisms are moved and painted and there is a chance that a new food or predator object will be added 
				canvas.repaint();
				canvas.getField().addFoodRandom(canvas.getWidth(), canvas.getHeight());
				canvas.getField().addPredatorRandom(canvas.getWidth(), canvas.getHeight());
				panel.update(canvas.getField());
			}
		});
	
		timer.start();

		
		//creates menu bar
		JMenuBar menubar = new JMenuBar();
		
		JMenu file = new JMenu("File");
		
		//menu items with appropriate actions are created
		
		JMenuItem restart = new JMenuItem("Restart");
		restart.setToolTipText("Restart the application");
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event){
				close();
				restart();
			}
		});
		
		JMenuItem quit = new JMenuItem("Quit");
		quit.setToolTipText("Exit the application");
	    quit.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent event){
	    		System.exit(0);
	    	}
	    });
	    
	    JMenuItem addPred = new JMenuItem("Add Predator");
	    addPred.setToolTipText("Add a Predator");
	    addPred.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent event){
	    		SimulationField field = canvas.getField();
	    		field.addPredator(canvas.getWidth(), canvas.getHeight());
	    	}
	    });
	    
	    JMenuItem add10Pred = new JMenuItem("Add 10 Predators");
	    add10Pred.setToolTipText("Add a Predator");
	    add10Pred.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent event){
	    		SimulationField field = canvas.getField();
	    		for(int i = 0; i < 10; i++){
	    			field.addPredator(canvas.getWidth(), canvas.getHeight());
	    		}
	    	}
	    });
	    
	    JMenu food = new JMenu("Food");
	    JMenu oneFood = new JMenu("Add 1 Food");
	    JMenu tenFood = new JMenu("Add 10 Food");
	    
	    JMenuItem addOneFoodRandom = new JMenuItem("Add in Random Location");
	    addOneFoodRandom.setToolTipText("Add in Random Location");
	    addOneFoodRandom.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent event){
	    		SimulationField field = canvas.getField();
	    		field.addFoodRandom(canvas.getWidth(), canvas.getHeight());
	    	}
	    });
	    
	    JMenuItem addOneFoodTopLeft = new JMenuItem("Add to Top Left");
	    addOneFoodTopLeft.setToolTipText("Add to Top Left");
	    addOneFoodTopLeft.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent event){
	    		SimulationField field = canvas.getField();
	    		field.addFoodTopLeft();
	    	}
	    });
	    
	    JMenuItem addOneFoodTopRight = new JMenuItem("Add to Top Right");
	    addOneFoodTopRight.setToolTipText("Add to Top Right");
	    addOneFoodTopRight.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent event){
	    		SimulationField field = canvas.getField();
	    		field.addFoodTopRight(canvas.getWidth());
	    	}
	    });
	    
	    JMenuItem addOneFoodBottomLeft = new JMenuItem("Add to Bottom Left");
	    addOneFoodBottomLeft.setToolTipText("Add to Bottom Left");
	    addOneFoodBottomLeft.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent event){
	    		SimulationField field = canvas.getField();
	    		field.addFoodBottomLeft(canvas.getHeight());
	    	}
	    });
	    
	    JMenuItem addOneFoodBottomRight = new JMenuItem("Add to Bottom Right");
	    addOneFoodBottomRight.setToolTipText("Add to Bottom Right");
	    addOneFoodBottomRight.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent event){
	    		SimulationField field = canvas.getField();
	    		field.addFoodBottomRight(canvas.getWidth(), canvas.getHeight());
	    	}
	    });


	    
	    JMenuItem add10Food = new JMenuItem("Add 10 Food");
	    add10Food.setToolTipText("Add Food");
	    add10Food.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent event){
	    		SimulationField field = canvas.getField();
	    		for(int i = 0; i < 10; i++){
	    			field.addFoodRandom2(canvas.getWidth(), canvas.getHeight());
	    		}
	    	}
	    });
	    
	    oneFood.add(addOneFoodRandom);
	    oneFood.add(addOneFoodTopLeft);
	    oneFood.add(addOneFoodTopRight);
	    oneFood.add(addOneFoodBottomLeft);
	    oneFood.add(addOneFoodBottomRight);
	    
	    food.add(oneFood);
	    food.add(add10Food);
	    
	    file.add(restart);
	    file.add(addPred);
	    file.add(add10Pred);
	    file.add(food);
	    file.add(quit);
	    menubar.add(file);
	    
	    JMenu speed = new JMenu("Speed");
	    
	    final JMenuItem pause = new JMenuItem("Pause");
	    pause.setToolTipText("Pause");
	    pause.addActionListener(new ActionListener(){
	    	@Override
	    	public void actionPerformed(ActionEvent event){
	    		if(pause.getText() == "Pause"){
	    			timer.stop();
	    			pause.setText("Unpause");
	    		}
	    		else{
	    			timer.start();
	    			pause.setText("Pause");
	    		}
	    	}
	    });
	    
	    JMenuItem slow = new JMenuItem("Slow");
	    slow.setToolTipText("Slow");
	    slow.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent event){
	    		timer.setDelay(100);
	    	}
	    });
	    
	    JMenuItem medium = new JMenuItem("Medium");
	    medium.setToolTipText("Medium");
	    medium.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent event){
	    		timer.setDelay(75);
	    	}
	    });
	    
	    JMenuItem fast = new JMenuItem("Fast");
	    fast.setToolTipText("Fast");
	    fast.addActionListener(new ActionListener(){
	    	@Override
	    	public void actionPerformed(ActionEvent event){
	    		timer.setDelay(50);
	    	}
	    });
	    
	    JMenuItem superFast = new JMenuItem("Super Fast");
	    superFast.setToolTipText("SuperFast");
	    superFast.addActionListener(new ActionListener(){
	    	@Override
	    	public void actionPerformed(ActionEvent event){
	    		timer.setDelay(1);
	    	}
	    });
	    
	    speed.add(pause);
	    speed.add(slow);
	    speed.add(medium);
	    speed.add(fast);
	    speed.add(superFast);
	    menubar.add(speed);
	    
	    setJMenuBar(menubar);
	}
	
	public void restart(){
		//the simulation is restarted - a new SimulationCanvas and SimulationField object are generated
				SimulationCanvas newCanvas = new SimulationCanvas(new SimulationField(canvas.getField().getArr()));
				canvas = newCanvas;
				this.add(canvas);
				final SimulationPanel panel = new SimulationPanel();
				this.add(panel, BorderLayout.SOUTH);
				panel.setPreferredSize(new Dimension(this.getWidth(), 30));
				timer = new Timer(50, new ActionListener(){
					public void actionPerformed(ActionEvent evt) {
						canvas.repaint();
						canvas.getField().addFoodRandom(canvas.getWidth(), canvas.getHeight());
						canvas.getField().addPredatorRandom(canvas.getWidth(), canvas.getHeight());
						panel.update(canvas.getField());
					}
				});
			
				timer.start();
	}
	
	//closes the current simulation in preparation for a restart
	public void close(){
		this.remove(canvas);
		timer.stop();
	}
	
}

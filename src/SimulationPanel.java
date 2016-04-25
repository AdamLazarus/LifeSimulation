import javax.swing.*;


public class SimulationPanel extends JPanel{
	
	JLabel label;
	
	public SimulationPanel(){
		label = new JLabel();
		this.add(label);
	}
	
	//information about the simulation is displayed
	public void update(SimulationField field){
		label.setText("Average Fitness = " + field.getAverageFitness() + "       Population = " + field.getPopulation());	
	}
}

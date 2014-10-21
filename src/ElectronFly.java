import javax.swing.JPanel;


public class ElectronFly {

	public static double ELECTRON_POS_X = 250;
	public static double ELECTRON_POS_Y = 250;
	
	public static double ELECTRON_VEL_X = 2;
	public static double ELECTRON_VEL_Y = 2;
	
	final double ELECTRON_WEIGHT = 9.1 * Math.pow(10d, -31d);
	final double ELECTRON_CHARGE = 1.6 * Math.pow(10d, -19d);
	
	private Grid grid;
	
	
	public ElectronFly(Grid grid) {
		this.grid = grid;
	}
	
	public void startElectronFly() {
		int counter = 0;
		while (true) {
			electronFly();
			this.grid.setElectronPos((int) Math.floor(ElectronFly.ELECTRON_POS_X), (int) Math.floor(ElectronFly.ELECTRON_POS_Y));
			this.grid.repaint();
			System.out.println(counter);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void electronFly() {
		if (ElectronFly.ELECTRON_POS_X > this.grid.gridNew.length - 1 ||
				ElectronFly.ELECTRON_POS_Y > this.grid.gridNew[0].length - 1) {
			System.out.println("Finished1");
			return;
		}
		
		double posX = ELECTRON_POS_X;
		double posY = ELECTRON_POS_Y;
		
		// drift
		posX += (0.5 * ELECTRON_VEL_X);
		posY += (0.5 * ELECTRON_VEL_Y);
		
		if (posX > this.grid.gridNew.length - 1 ||
				posY > this.grid.gridNew[0].length - 1) {
			System.out.println("Finished2");
			return;
		}
		
		// kick
		// -> use old value
		ElectronFly.ELECTRON_POS_X = (this.ELECTRON_CHARGE / this.ELECTRON_WEIGHT) * (-1) * this.bilinearInterpolation((int) Math.floor(posX), (int) Math.floor(posY), posX); 
		ElectronFly.ELECTRON_POS_Y = (this.ELECTRON_CHARGE / this.ELECTRON_WEIGHT) * (-1) * this.bilinearInterpolation((int) Math.floor(posX), (int) Math.floor(posY), posY);
		
		//drift
		ElectronFly.ELECTRON_POS_X += (0.5 * ELECTRON_VEL_X);
		ElectronFly.ELECTRON_POS_Y += (0.5 * ELECTRON_VEL_Y);
		
		
	}
	
	
	private double bilinearInterpolation(int xPos, int yPos, double electronPos) {		
		double innerStep = electronPos % Grid.GRID_STEP_SIZE;
	
		
		return (1 / Grid.GRID_STEP_SIZE) * 
				(
					-1 * (1-innerStep) * this.grid.gridNew[xPos][yPos] + 
					 1 * (1-innerStep) * this.grid.gridNew[xPos+1][yPos] + 
					-1 * innerStep * this.grid.gridNew[xPos][yPos+1] + 
					 1 * innerStep * this.grid.gridNew[xPos+1][yPos+1]
				);
	}
}

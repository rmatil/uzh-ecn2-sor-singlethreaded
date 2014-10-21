import javax.swing.JFrame;

public class Sor {
	
	private static final int GRID_HEIGHT = 600;
	private static final int GRID_WIDTH = 600;
	
	/**
	 * Accuracy used to compute potentials
	 */
	private static final int NUMBER_OF_COMPUTATION_STEPS = 20000;
	
	public static void main(String[] args) {
		Grid grid = new Grid(Sor.GRID_WIDTH, Sor.GRID_HEIGHT);
		grid.setInitialBarrier(200, 275, 200, 50);
		
		Barrier barrier = new Barrier(grid, 4);
		
		JFrame top = new JFrame("SOR");
		top.setBounds(200, 50, Grid.PIXEL_SIZE * GRID_WIDTH, Grid.PIXEL_SIZE * GRID_HEIGHT);	
		top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		top.setResizable(true);
		top.getContentPane().add(grid);
		top.setVisible(true);
		
		ThreadGroup threadGroup = new ThreadGroup ("field computation");
		
		// top left corner
		SorThread thread1 = new SorThread(threadGroup, grid, barrier, Sor.NUMBER_OF_COMPUTATION_STEPS, 1, 599, 1, 599);
		thread1.start();
		
		ElectronFly fly = new ElectronFly(grid);
		
		while (true) {
			if (threadGroup.activeCount() == 0) {
				System.out.println("start electornFly");
				fly.startElectronFly();
			}
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}


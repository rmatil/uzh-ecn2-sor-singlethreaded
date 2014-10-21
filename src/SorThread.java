
public class SorThread extends Thread {
	
	private Grid grid;
	private Barrier barrier;
	
	private int nrOfComputationSteps;
	
	private int xStart;
	private int xEnd;
	private int yStart;
	private int yEnd;
	
	private int stepCounter;
	
	public SorThread(Grid grid, Barrier barrier, int nrOfComputationSteps, int xStart, int xEnd, int yStart, int yEnd) {
		this.grid = grid;
		this.barrier = barrier;
		this.nrOfComputationSteps = nrOfComputationSteps;
		this.xStart = xStart;
		this.xEnd = xEnd;
		this.yStart = yStart;
		this.yEnd = yEnd;
	}
	
	public void run() {
		while (this.stepCounter != this.nrOfComputationSteps) {

			for(int i = this.xStart; i < this.xEnd; i++){
				for(int j = this.yStart; j < this.yEnd; j++) {

					if (this.grid.gridOld[i][j] == Grid.POTENTIAL) {
						this.grid.gridNew[i][j] = Grid.POTENTIAL;
					} else {
						// calculate new potential
						this.grid.gridNew[i][j] = ( this.grid.gridOld[i-1][j] + this.grid.gridOld[i+1][j] + this.grid.gridOld[i][j-1] + this.grid.gridOld[i][j+1] ) / 4;
					}
				}
			}

			this.stepCounter++;

			// tell waiting barrier that we reached next computation step
			this.barrier.reached();
		}
		
		return;
	}
}

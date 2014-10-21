
/**
 * Barrier, which waits for each thread to end its computation step
 * so that the new grid state can be visualized
 */
public class Barrier {

	private int nrThreads;
	private int nrWaitingThreads;
	private Grid grid;
	
	public Barrier(Grid grid, int nrThreads) {
		this.nrThreads = nrThreads;
		this.nrWaitingThreads = 0;
		this.grid = grid;
	}
	
	/**
	 * Called once a thread completed one execution step.
	 * synchronized limits access to this method to one thread.
	 */
	public synchronized void reached() {
		this.nrWaitingThreads++;
				
		if (this.nrWaitingThreads == this.nrThreads) {
			// update grid state
			this.grid.gridOld = this.grid.gridNew;

			this.grid.repaint();
			
			this.nrWaitingThreads = 0;
		}
	}
}

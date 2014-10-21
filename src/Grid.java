import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

/**
 * Grid on which the calculation is done.
 */
public class Grid extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public static final int PIXEL_SIZE = 1;
	public static final int POTENTIAL = 1000000; // potential used on the beginning
	
	public int gridOld[][];
	public int gridNew[][];
	
	public Grid(int xSize, int ySize) {
		this.gridOld = new int[xSize][ySize];
		this.gridNew = new int[xSize][ySize];
		
		this.initGrid();
	}
	
	public void setInitialBarrier(int xPos, int yPos, int width, int height) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				this.gridOld[i + xPos][j + yPos] = Grid.POTENTIAL;
				this.gridNew[i + xPos][j + yPos] = Grid.POTENTIAL;
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		//clear drawing area
		Rectangle bounds = this.getBounds();
		g.clearRect(bounds.x, bounds.y, bounds.width, bounds.height);

		//draw the grid
		for (int i = 0; i < this.gridNew[0].length; i++) {
			for (int j = 0; j < this.gridNew[i].length; j++) {
				int cell = this.gridNew[i][j];	
				
				//green
				if(cell == 0){
					g.setColor(Color.WHITE);
				}
				else if(cell < 125000 && cell > 0){
					g.setColor(new Color(0, (cell/4000) + 14, 0));
				}else if(cell > 124999 && cell < 250000){
					g.setColor(new Color(0, (cell/4000) + 12, 0));
				}else if(cell > 249999 && cell < 375000){
					g.setColor(new Color(0, (cell/4000) + 10, 0));
				}else if(cell > 374999 && cell < 500000){
					g.setColor(new Color(0, (cell/4000) + 8, 0));
				}else if(cell > 499999 && cell < 625000){
					g.setColor(new Color(0, (cell/4000) + 6, 0));
				}else if(cell > 624999 && cell < 750000){
					g.setColor(new Color(0, (cell/4000) + 4, 0));
				}else if(cell > 749999 && cell < 875000){
					g.setColor(new Color(0, (cell/4000) + 2, 0));
				}else if(cell > 874999 && cell < 1000000){
					g.setColor(new Color(0, (cell/4000), 0));
				}else if(cell == Grid.POTENTIAL){
					g.setColor(Color.BLACK);
				}
				
				int x = PIXEL_SIZE * i;
				int y = PIXEL_SIZE * j;
				
				g.fillRect(x, y, PIXEL_SIZE, PIXEL_SIZE);
			}
		}
		
	}
	
	private void initGrid() {
		for (int i = 0; i < this.gridOld[0].length; i++) {
			for (int j = 0; j < this.gridOld[i].length; j++) {
				this.gridOld[i][j] = 0;
				this.gridNew[i][j] = 0;
			}
		}
	}
}

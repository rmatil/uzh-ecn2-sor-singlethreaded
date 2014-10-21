import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Grid extends JPanel {

	final int PIXEL_SIZE = 5;
	final double DELTA = 0.1;
	final double GCONST = 6.67*Math.pow(10, -11);

	double data[][] = {
			{ 1.00000000, -0.00714054, -0.00278783, 0.00020608, 0.00031248,	-0.00043002, -0.00000548 },
			{ 0.00000017, -0.13722916, -0.45008016, -0.02439273, 1.24238610, -0.37526184, -0.14462948 },
			{ 0.00000245, -0.72545689, -0.03549449, 0.04122171, 0.04675033,	-1.18021768, -0.01880933 },
			{ 0.00000304, -0.18431179, 0.96442666, 0.00020583, -0.99974673,	-0.18437586, -0.00000543 },
			{ 0.00000032, 1.38352721, -0.01617889, -0.03425517, 0.03941188,	0.88247492, 0.01752862 },
			{ 0.00095479, 3.99118040, 2.94292308, -0.10151173, -0.26534543,	0.37351952, 0.00439486 },
			{ 0.00028584, 6.40764395, 6.54287964, -0.36894069, -0.24855496,	0.22588506, 0.00594743 },
			{ 0.00004373, 14.41832534, -13.74043355, -0.23782704, 0.15620559, 0.15441055, -0.00145158 },
			{ 0.00005178, 16.79762227, -24.99549769, 0.12760929, 0.15048679, 0.10236959, -0.00558275 } };
	// data[planet][mass,x,y,z,vx,vy,vz]

	public static void main(String[] args) {
		JFrame top = new JFrame("Solar system");
		top.setBounds(100, 100, 800, 600);
		top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Grid sol1 = new Grid();
		top.getContentPane().add(sol1);

		SolarSystemTask task = sol1.new SolarSystemTask(sol1);
		task.start();

		top.setVisible(true);
	}

	private double dvpdt(int p, int x) {
		double res = 0;
		for (int i = 0; i < 9; ++i) {
			if (i == p)
				continue;
			double nom = ((data[p][x] - data[i][x]) * data[i][0]);     
			double den =  (Math.pow((Math.pow((data[p][1] - data[i][1]), 2)	+ Math.pow((data[p][2] - data[i][2]), 2) + Math.pow((data[p][3] - data[i][3]), 2)), 1.5));
			res += nom/den;
		}
		return -res;
	}

	@Override
	public void paint(Graphics g) {

		Rectangle bounds = getBounds();
		g.clearRect(bounds.x, bounds.y, bounds.width, bounds.height);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, bounds.width, bounds.height);

		for (int p = 0; p < 9; ++p) {
			g.setColor(Color.WHITE);
			g.fillRect(
					(int) Math.round(((bounds.width / 2) + (100 / PIXEL_SIZE)
							* data[p][1])
							- (PIXEL_SIZE / 2)),
					(int) Math.round(((bounds.height / 2) - (100 / PIXEL_SIZE)
							* data[p][2])
							- (PIXEL_SIZE / 2)), PIXEL_SIZE, PIXEL_SIZE);
		}
	}

	private class SolarSystemTask extends Thread {

		JPanel panel;

		public SolarSystemTask(JPanel panel) {
			this.panel = panel;
		}

		public void run() {

			for (int t = 0; t < DELTA * 200; t += DELTA) {

				for (int p = 0; p < 9; ++p) {
					data[p][1] += 0.5 * DELTA * data[p][4];
					data[p][2] += 0.5 * DELTA * data[p][5];
					data[p][3] += 0.5 * DELTA * data[p][6];
				}// Drift
				for (int p = 0; p < 9; ++p) {
					data[p][4] += DELTA * dvpdt(p, 1);
					data[p][5] += DELTA * dvpdt(p, 2);
					data[p][6] += DELTA * dvpdt(p, 3);
				}// Kick
				for (int p = 0; p < 9; ++p) {
					data[p][1] += 0.5 * DELTA * data[p][4];
					data[p][2] += 0.5 * DELTA * data[p][5];
					data[p][3] += 0.5 * DELTA * data[p][6];
				}// Drift

				panel.repaint();
			
			try {
				sleep(25);
			} catch (InterruptedException e) {
			}
		}
	}
}
}

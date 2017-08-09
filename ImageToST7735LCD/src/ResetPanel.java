import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ResetPanel extends JPanel{
	private JButton btnReset;
	private JButton btnCenterImage;
	
	public ResetPanel(){
		this.setLayout(null);
		this.setBackground(Color.LIGHT_GRAY);
		
		btnReset = new JButton("Reset Image");
		btnReset.setBounds(10, 11, 146, 23);
		this.add(btnReset);
		btnReset.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				ImageToST7735LCD.reset();
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		}); 
		
		btnCenterImage = new JButton("Center Image");
		btnCenterImage.setBounds(166, 11, 152, 23);
		this.add(btnCenterImage);
		btnCenterImage.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				ImageToST7735LCD.center();
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		}); 
	}
}

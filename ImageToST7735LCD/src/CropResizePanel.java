import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CropResizePanel extends JPanel{
	private JLabel lblCropXAnd;
	private JTextField textFieldCropX;
	private JTextField textFieldCropY;
	private JTextField textFieldCropWidth;
	private JTextField textFieldCropHeight;
	
	public CropResizePanel(){
		this.setLayout(null);
		this.setBackground(Color.LIGHT_GRAY);
		
		lblCropXAnd = new JLabel("Crop X and Y");
		lblCropXAnd.setBounds(10, 5, 194, 14);
		this.add(lblCropXAnd);
		
		textFieldCropX = new JTextField();
		textFieldCropX.setBounds(10, 25, 86, 20);
		this.add(textFieldCropX);
		textFieldCropX.setColumns(10);
		textFieldCropX.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyChar() == '\n'){
					try{
						ImageToST7735LCD.setStatus("Changing Crop Dimentions...", Color.BLACK);
						ImageToST7735LCD.setCropDimentions(Integer.parseInt(textFieldCropX.getText()), Integer.parseInt(textFieldCropY.getText()), Integer.parseInt(textFieldCropWidth.getText()), Integer.parseInt(textFieldCropHeight.getText()));
					}catch(Exception e){
						ImageToST7735LCD.setStatus("Crop Dimentions Error", Color.RED);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {	
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
		});
		textFieldCropX.setToolTipText("Crop X");
		
		
		textFieldCropY = new JTextField();
		textFieldCropY.setBounds(118, 25, 86, 20);
		this.add(textFieldCropY);
		textFieldCropY.setColumns(10);
		textFieldCropY.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyChar() == '\n'){
					try{
						ImageToST7735LCD.setStatus("Changing Crop Dimentions...", Color.BLACK);
						ImageToST7735LCD.setCropDimentions(Integer.parseInt(textFieldCropX.getText()), Integer.parseInt(textFieldCropY.getText()), Integer.parseInt(textFieldCropWidth.getText()), Integer.parseInt(textFieldCropHeight.getText()));
					}catch(Exception e){
						ImageToST7735LCD.setStatus("Crop Dimentions Error", Color.RED);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {	
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
		});
		textFieldCropY.setToolTipText("Crop Y");
		
		JLabel lblX = new JLabel("X");
		lblX.setBounds(106, 30, 13, 14);
		this.add(lblX);
		
		JLabel lblCropWidthAnd = new JLabel("Crop Width and Height");
		lblCropWidthAnd.setBounds(10, 55, 194, 14);
		this.add(lblCropWidthAnd);
		
		textFieldCropWidth = new JTextField();
		textFieldCropWidth.setBounds(10, 74, 86, 20);
		this.add(textFieldCropWidth);
		textFieldCropWidth.setColumns(10);
		textFieldCropWidth.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyChar() == '\n'){
					try{
						ImageToST7735LCD.setStatus("Changing Crop Dimentions...", Color.BLACK);
						ImageToST7735LCD.setCropDimentions(Integer.parseInt(textFieldCropX.getText()), Integer.parseInt(textFieldCropY.getText()), Integer.parseInt(textFieldCropWidth.getText()), Integer.parseInt(textFieldCropHeight.getText()));
					}catch(Exception e){
						ImageToST7735LCD.setStatus("Crop Dimentions Error", Color.RED);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {	
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
		});
		textFieldCropWidth.setToolTipText("Crop Width");
		
		textFieldCropHeight = new JTextField();
		textFieldCropHeight.setBounds(118, 74, 86, 20);
		this.add(textFieldCropHeight);
		textFieldCropHeight.setColumns(10);
		textFieldCropHeight.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyChar() == '\n'){
					try{
						ImageToST7735LCD.setStatus("Changing Crop Dimentions...", Color.BLACK);
						ImageToST7735LCD.setCropDimentions(Integer.parseInt(textFieldCropX.getText()), Integer.parseInt(textFieldCropY.getText()), Integer.parseInt(textFieldCropWidth.getText()), Integer.parseInt(textFieldCropHeight.getText()));
					}catch(Exception e){
						ImageToST7735LCD.setStatus("Crop Dimentions Error", Color.RED);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {	
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
			
		});
		textFieldCropHeight.setToolTipText("Crop Height");
		
		JLabel lblx = new JLabel("X");
		lblx.setBounds(106, 80, 13, 14);
		this.add(lblx);
	}
	public void setDimentions(int x, int y, int width, int height){
		textFieldCropX.setText(Integer.toString(x));
		textFieldCropY.setText(Integer.toString(y));
		textFieldCropWidth.setText(Integer.toString(width));
		textFieldCropHeight.setText(Integer.toString(height));
	}
}

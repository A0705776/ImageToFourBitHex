import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageResizePanel extends JPanel{
	private  JSlider rotationSlider;
	private  JTextField rotationTextField;
	private  JTextField imageWidthTextField;
	private  JTextField imageHeightTextField;
	private  JLabel lblX;
	
	private int x = 10;
	private int y = 5;
	
	public ImageResizePanel(){
		this.setVisible(true);
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(null);
		
		imageWidthTextField = new JTextField();
		imageWidthTextField.setBounds(x, y+25, 107, 20); 
		this.add(imageWidthTextField);
		imageWidthTextField.setColumns(10);
		imageWidthTextField.setText(Integer.toString(ImageToST7735LCD.getImageWidth()-ImageToST7735LCD.getImagePadding()));
		imageWidthTextField.setToolTipText("Image Width");
		imageWidthTextField.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println(arg0.getKeyChar());
				if(arg0.getKeyChar() == '\n'){
					try{
						ImageToST7735LCD.resizeDrawImage(Integer.parseInt(imageWidthTextField.getText()), Integer.parseInt(imageHeightTextField.getText()));
						ImageToST7735LCD.setStatus("Resizing Image...", Color.BLACK);
					}catch(Exception e){
						ImageToST7735LCD.setStatus("Resize Error", Color.RED);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		imageHeightTextField = new JTextField();
		imageHeightTextField.setBounds(x+139, y+25, 107, 20);
		this.add(imageHeightTextField);
		imageHeightTextField.setColumns(10);
		imageHeightTextField.setText(Integer.toString(ImageToST7735LCD.getImageHeight()-ImageToST7735LCD.getImagePadding()));
		imageHeightTextField.setToolTipText("Image Height");
		imageHeightTextField.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println(arg0.getKeyChar());
				if(arg0.getKeyChar() == '\n'){
					try{
						ImageToST7735LCD.resizeDrawImage(Integer.parseInt(imageWidthTextField.getText()), Integer.parseInt(imageHeightTextField.getText()));
						ImageToST7735LCD.setStatus("Resizing Image...", Color.BLACK);
					}catch(Exception e){
						ImageToST7735LCD.setStatus("Resize Error", Color.RED);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		lblX = new JLabel(" X");
		lblX.setBounds(x+117, y+25, 17, 14);
		this.add(lblX);
		
		JLabel lblImageSize = new JLabel("Image Size (Width X Height)");
		lblImageSize.setBounds(x, y, 201, 14); //153
		this.add(lblImageSize);
		
		JLabel lblImageRotation = new JLabel("Image Rotation");
		lblImageRotation.setBounds(x, y+50, 200, 14);
		this.add(lblImageRotation);
		
		rotationSlider = new JSlider();
		rotationSlider.setBounds(x, y+69, 200, 26);
		rotationSlider.setBackground(Color.LIGHT_GRAY);
		this.add(rotationSlider);
		
		rotationSlider.setMinimum(0);
		rotationSlider.setMaximum(360);
		rotationSlider.setValue(0);
		rotationSlider.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				ImageToST7735LCD.rotateImage(rotationSlider.getValue());
				rotationTextField.setText(Integer.toString(rotationSlider.getValue()));
			}
			
		});
		
		rotationTextField = new JTextField();
		rotationTextField.setBounds(x+202, y+69, 44, 20);
		this.add(rotationTextField);
		rotationTextField.setColumns(10);
		rotationTextField.setText(Integer.toString(rotationSlider.getValue()));
		rotationTextField.setToolTipText("Image Rotation");
		rotationTextField.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println(arg0.getKeyChar());
				if(arg0.getKeyChar() == '\n'){
					try{
						int temp = Integer.parseInt(rotationTextField.getText());
						while (temp < 0)
							temp += 360;
						while (temp > 360)
							temp -= 360;
						//System.out.println("tmep is " + temp);
						ImageToST7735LCD.rotateImage(temp);
						rotationTextField.setText(""+temp);
						rotationSlider.setValue(temp);
					}catch(Exception e){
						ImageToST7735LCD.setStatus("Rotation TextField Error",Color.RED);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	public void reset(){
		rotationSlider.setValue(0);
		rotationTextField.setText("0");
	}
	public int getRotation(){
		return rotationSlider.getValue();
	}
	public void setImageSize(int width, int height){
		imageWidthTextField.setText(Integer.toString(width));
		imageHeightTextField.setText(Integer.toString(height));
	}
}

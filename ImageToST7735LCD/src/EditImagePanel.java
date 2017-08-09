import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class EditImagePanel extends JPanel{

	private JSlider sliderG;
	private JSlider sliderB;
	private JLabel lblR;
	private JLabel lblG;
	private JLabel lblB;
	private JLabel lblA;
	private JSlider sliderA;
	private JTextField textFieldR;
	private JTextField textFieldG;
	private JTextField textFieldB;
	private JTextField textFieldA;
	private JSlider sliderR;
	private JToggleButton tglbtnGrayScale;
	private JToggleButton tglbtnInvert;
	private JToggleButton tglbtnB_W;
	
	//for the text field
	private double r(){
		try{
			sliderR.setValue(Integer.parseInt(textFieldR.getText())+255);
			return sliderR.getValue()/255.0;
		}catch(Exception e){
			ImageToST7735LCD.setStatus("Edit Image Error", Color.RED);
			return -999;
		}
	}
	private double g(){
		try{
			sliderG.setValue(Integer.parseInt(textFieldG.getText())+255);
			return sliderG.getValue()/255.0;
		}catch(Exception e){
			ImageToST7735LCD.setStatus("Edit Image Error", Color.RED);
			return -999;
		}
	}
	private double b(){
		try{
			sliderB.setValue(Integer.parseInt(textFieldB.getText())+255);
			return sliderB.getValue()/255.0;
		}catch(Exception e){
			ImageToST7735LCD.setStatus("Edit Image Error", Color.RED);
			return -999;
		}
	}
	private double a(){
		try{
			sliderA.setValue(Integer.parseInt(textFieldA.getText())+255);
			return sliderA.getValue()/255.0;
		}catch(Exception e){
			ImageToST7735LCD.setStatus("Edit Image Error", Color.RED);
			return -999;
		}
	}
	
	//for the sliders
	private double getR(){
		return sliderR.getValue()/255.0; 
	}
	private double getG(){
		return sliderG.getValue()/255.0; 
	}
	private double getB(){
		return sliderB.getValue()/255.0; 
	}
	private double getA(){
		return sliderA.getValue()/255.0; 
	}
	
	private void resetToggleBtn(){
		tglbtnGrayScale.setSelected(false);
		tglbtnInvert.setSelected(false);
		tglbtnB_W.setSelected(false);
	}
	
	public void reset(){
		sliderR.setValue(255);
		sliderG.setValue(255);
		sliderB.setValue(255);
		sliderA.setValue(255);
		textFieldR.setText("0");
		textFieldG.setText("0");
		textFieldB.setText("0");
		textFieldA.setText("0");
		tglbtnGrayScale.setSelected(false);
		tglbtnInvert.setSelected(false);
		tglbtnB_W.setSelected(false);
	}
	
	public EditImagePanel(){
		this.setLayout(null);
		this.setBackground(Color.LIGHT_GRAY);
		
		sliderR = new JSlider();
		sliderR.setBackground(Color.LIGHT_GRAY);
		sliderR.setBounds(20, 29, 152, 14);
		this.add(sliderR);
		sliderR.setMaximum(510);
		sliderR.setValue(255);
		sliderR.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				textFieldR.setText(Integer.toString(sliderR.getValue() - 255));
				ImageToST7735LCD.editRGBA(getR(),getG(),getB(),getA());
				resetToggleBtn();
			}
		});
		
		
		sliderG = new JSlider();
		sliderG.setBackground(Color.LIGHT_GRAY);
		sliderG.setBounds(20, 54, 152, 14);
		this.add(sliderG);
		sliderG.setMaximum(510);
		sliderG.setValue(255);
		sliderG.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				textFieldG.setText(Integer.toString(sliderG.getValue() - 255));
				ImageToST7735LCD.editRGBA(getR(),getG(),getB(),getA());
				resetToggleBtn();
			}
		});
		
		sliderB = new JSlider();
		sliderB.setBackground(Color.LIGHT_GRAY);
		sliderB.setBounds(20, 79, 152, 14);
		this.add(sliderB);
		sliderB.setMaximum(510);
		sliderB.setValue(255);
		sliderB.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				textFieldB.setText(Integer.toString(sliderB.getValue() - 255));
				ImageToST7735LCD.editRGBA(getR(),getG(),getB(),getA());
				resetToggleBtn();
			}
		});
		
		
		sliderA = new JSlider();
		sliderA.setBackground(Color.LIGHT_GRAY);
		sliderA.setBounds(20, 104, 152, 14);
		this.add(sliderA);
		sliderA.setMaximum(510);
		sliderA.setValue(255);
		sliderA.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				textFieldA.setText(Integer.toString(sliderA.getValue() - 255));
				ImageToST7735LCD.editRGBA(getR(),getG(),getB(),getA());
				resetToggleBtn();
			}
		});
		
		lblR = new JLabel("R");
		lblR.setBounds(10, 29, 12, 14);
		this.add(lblR);
		
		lblG = new JLabel("G");
		lblG.setBounds(10, 54, 12, 14);
		this.add(lblG);
		
		lblB = new JLabel("B");
		lblB.setBounds(10, 79, 12, 14);
		this.add(lblB);
		
		lblA = new JLabel("A");
		lblA.setBounds(10, 104, 12, 14);
		this.add(lblA);
		
				
		tglbtnB_W = new JToggleButton("B & W");
		tglbtnB_W.setBounds(217, 7, 101, 36);
		this.add(tglbtnB_W);
		tglbtnB_W.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// B_W   Gray   Invert ---> 0b000
				switch(btnState()){
				case 0b000: ImageToST7735LCD.resetImageFromEditImage();break;
				case 0b001: ImageToST7735LCD.resetImageFromEditImage();tglbtnInvert.setSelected(false);break;
				case 0b010: ImageToST7735LCD.resetImageFromEditImage();break;
				case 0b011: ImageToST7735LCD.resetImageFromEditImage();break;
				case 0b100: ImageToST7735LCD.bAndW();break;
				case 0b101: ImageToST7735LCD.bAndWAndInvert();break;
				case 0b110: ImageToST7735LCD.bAndW();tglbtnGrayScale.setSelected(false);break;
				case 0b111: ImageToST7735LCD.bAndWAndInvert();tglbtnGrayScale.setSelected(false);break;
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
		
		JLabel lblEditImage = new JLabel("Edit Image");
		lblEditImage.setBounds(82, 4, 78, 14);
		this.add(lblEditImage);
		
		tglbtnGrayScale = new JToggleButton("Gray");
		tglbtnGrayScale.setBounds(217, 47, 101, 36);
		this.add(tglbtnGrayScale);
		tglbtnGrayScale.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// B_W   Gray   Invert ---> 0b000
				switch(btnState()){
				case 0b000: ImageToST7735LCD.resetImageFromEditImage();break;
				case 0b001: ImageToST7735LCD.resetImageFromEditImage();tglbtnInvert.setSelected(false);break;
				case 0b010: ImageToST7735LCD.grayScale();break;
				case 0b011: ImageToST7735LCD.grayScaleAndInvert();break;
				case 0b100: ImageToST7735LCD.resetImageFromEditImage();break;
				case 0b101: ImageToST7735LCD.resetImageFromEditImage();break;
				case 0b110: ImageToST7735LCD.grayScale();tglbtnB_W.setSelected(false);break;
				case 0b111: ImageToST7735LCD.grayScaleAndInvert();tglbtnB_W.setSelected(false);break;
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
		
		tglbtnInvert = new JToggleButton("Invert");
		tglbtnInvert.setBounds(217, 87, 101, 36);
		this.add(tglbtnInvert);
		tglbtnInvert.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				// B_W   Gray   Invert ---> 0b000
				switch(btnState()){
				case 0b000: ImageToST7735LCD.resetImageFromEditImage();break;
				case 0b001: ImageToST7735LCD.invert();break;
				case 0b010: ImageToST7735LCD.resetImageFromEditImage();tglbtnGrayScale.setSelected(false);break;
				case 0b011: ImageToST7735LCD.grayScaleAndInvert();break;
				case 0b100: ImageToST7735LCD.resetImageFromEditImage();tglbtnB_W.setSelected(false);break;
				case 0b101: ImageToST7735LCD.bAndWAndInvert();break;
				case 0b110: ImageToST7735LCD.resetImageFromEditImage();break;
				case 0b111: ImageToST7735LCD.bAndWAndInvert();tglbtnGrayScale.setSelected(false);break;
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});
		
		textFieldR = new JTextField();
		textFieldR.setBounds(174, 25, 33, 20);
		this.add(textFieldR);
		textFieldR.setColumns(10);
		textFieldR.setText("0");
		textFieldR.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyChar() == '\n' && r() != -999 && g() != -999 && b() != -999 && a() != -999){
					ImageToST7735LCD.editRGBA(r(),g(),b(),a());
					resetToggleBtn();
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}
		});
		textFieldR.setToolTipText("Red");
		
		textFieldG = new JTextField();
		textFieldG.setColumns(10);
		textFieldG.setBounds(174, 50, 33, 20);
		this.add(textFieldG);
		textFieldG.setText("0");
		textFieldG.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyChar() == '\n' && r() != -999 && g() != -999 && b() != -999 && a() != -999){
					ImageToST7735LCD.editRGBA(r(),g(),b(),a());
					resetToggleBtn();
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}
		});
		textFieldG.setToolTipText("Green");
		
		textFieldB = new JTextField();
		textFieldB.setColumns(10);
		textFieldB.setBounds(174, 75, 33, 20);
		this.add(textFieldB);
		textFieldB.setText("0");
		textFieldB.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyChar() == '\n' && r() != -999 && g() != -999 && b() != -999 && a() != -999){
					ImageToST7735LCD.editRGBA(r(),g(),b(),a());
					resetToggleBtn();
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}
		});
		textFieldB.setToolTipText("Blue");
		
		textFieldA = new JTextField();
		textFieldA.setColumns(10);
		textFieldA.setBounds(174, 100, 33, 20);
		this.add(textFieldA);
		textFieldA.setText("0");
		textFieldA.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyChar() == '\n' && r() != -999 && g() != -999 && b() != -999 && a() != -999){
					ImageToST7735LCD.editRGBA(r(),g(),b(),a());
					resetToggleBtn();
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}
		});
		textFieldA.setToolTipText("Alpha");
	}
	
	private int btnState(){
		int temp = 0;
		
		if(tglbtnB_W.isSelected()) 			temp |= 0b100;
		if(tglbtnGrayScale.isSelected()) 	temp |= 0b010;
		if(tglbtnInvert.isSelected()) 		temp |= 0b001;
		
		return temp;
	}
	
}

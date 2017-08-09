import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class GetImageFile extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String invalidFormat = "Not a correct file format";
	private final String invalidDirectory = "Not a correct file directory";
	private final String goodSatus = "...";
	private final String title = "This Program will convert an image file to a 16Bit BMP array in a text document.";
	private final String inputTextFieldMessage = "BMP/PNG/JPG File Destination";
	private final String fileOperationCancled = "File open operation cancelled";
	private final String outputTextFieldMessage = "Output Destination";
	private final String sysNotInit = "Load an image 1st";
	private final String imageOpened = "Image Opened";
	private final String imageWritten = "Document Saved";
	
	private JTextField input_destination;
	private JTextField output_destination;

	private JButton input_dest_btn;
	private JButton output_dest_btn;
	private JButton getImage_btn;

	private JButton writeImage_btn;	
	
	public GetImageFile(){
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(null);
		JLabel titelLabel = new JLabel(title);
		titelLabel.setBounds(2, 5, 465, 20);
		titelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(titelLabel);
		
		input_destination = new JTextField();
		input_destination.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyChar() == '\n'){
					checkInput();
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
		input_destination.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				input_destination.setForeground(Color.BLACK);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		input_destination.setBounds(10, 36, 371, 20);
		this.add(input_destination);
		input_destination.setColumns(10);
		input_destination.setToolTipText(inputTextFieldMessage);
		input_destination.setText(inputTextFieldMessage);
		
		input_dest_btn = new JButton("Get");
		input_dest_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				int response = fc.showOpenDialog(fc);
				if (response == JFileChooser.APPROVE_OPTION){
					String temp = fc.getSelectedFile().toString();
					if(temp.endsWith(".bmp")||temp.endsWith(".png")||temp.endsWith(".jpg")||temp.endsWith(".jpeg")){
						input_destination.setForeground(Color.black);
						input_destination.setText(fc.getSelectedFile().toString());
					}else{
						input_destination.setForeground(Color.red);
						ImageToST7735LCD.setStatus(invalidFormat, Color.red);
					}
				}else{
					String temp =input_destination.getText();
					if(!temp.endsWith(".bmp")||!temp.endsWith(".png")||!temp.endsWith(".jpg")||!temp.endsWith(".jpeg")){
						input_destination.setForeground(Color.red);
						ImageToST7735LCD.setStatus(fileOperationCancled, Color.red);
					}
				}
			}
		});
		input_dest_btn.setBounds(391, 36, 85, 23);
		this.add(input_dest_btn);
		
		output_destination = new JTextField();
		output_destination.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getKeyChar() == '\n'){
					checkOutput();
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
		output_destination.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				output_destination.setForeground(Color.BLACK);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		output_destination.setBounds(10, 67, 371, 20);
		this.add(output_destination);
		output_destination.setColumns(10);
		output_destination.setToolTipText(outputTextFieldMessage);
		output_destination.setText(outputTextFieldMessage);
		
		output_dest_btn = new JButton("Get");
		output_dest_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				int response = fc.showSaveDialog(fc);
				if (response == JFileChooser.APPROVE_OPTION){
					if(fc.getSelectedFile().toString().endsWith(".txt")){
						output_destination.setForeground(Color.black);
						output_destination.setText(fc.getSelectedFile().toString());
					}else{
						output_destination.setForeground(Color.black);
						output_destination.setText(fc.getSelectedFile().toString()+".txt");
					}
				}else{
					output_destination.setForeground(Color.red);
					ImageToST7735LCD.setStatus(fileOperationCancled, Color.red);
					
				}
			}
		});
		output_dest_btn.setBounds(391, 66, 85, 23);
		this.add(output_dest_btn);
		
		getImage_btn = new JButton("Get Image");
		getImage_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkInput();
			}
		});
		getImage_btn.setBounds(10, 100, 225, 23);
		this.add(getImage_btn);
	
		writeImage_btn = new JButton("Write Image");
		writeImage_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkOutput();			
			}
		});
		writeImage_btn.setBounds(250, 100, 225, 23);
		this.add(writeImage_btn);
		
	}
	
	private void checkInput(){
		String itemp = input_destination.getText();
		File inputf = new File(itemp);			
		//checks all combination and output appropriate error message
		if(inputf.canRead()) { 
			if(itemp.endsWith(".bmp")||itemp.endsWith(".png")||itemp.endsWith(".jpg")||itemp.endsWith(".jpeg")){
				input_destination.setForeground(Color.black);
				ImageToST7735LCD.setStatus(imageOpened, Color.black);
				//TODO add the imageprocessing
				//Image2Hex(input_destination.getText(),output_destination.getText());
				ImageToST7735LCD.initVariables(input_destination.getText());
				
			}else{
				input_destination.setForeground(Color.red);
				ImageToST7735LCD.setStatus(invalidFormat, Color.red);
			}
		}else{
			input_destination.setForeground(Color.red);
			ImageToST7735LCD.setStatus(invalidDirectory, Color.red);
		}
	}
	
	private void checkOutput(){
		if(ImageToST7735LCD.isInitialized()){
			File outputf = new File(output_destination.getText());
			
			// create new file if file dose not exist 
			if(!outputf.canWrite() && output_destination.getText().endsWith(".txt")){
				try {
					outputf.createNewFile();
				} catch (IOException e1) {
					output_destination.setForeground(Color.red);
					ImageToST7735LCD.setStatus(invalidDirectory, Color.red);
				}
			}
				
			if(outputf.canWrite() && output_destination.getText().endsWith(".txt")) { 
					output_destination.setForeground(Color.black);
					ImageToST7735LCD.setStatus(imageWritten, Color.black);
					//TODO add the imageprocessing output
					ImageToST7735LCD.saveImage(output_destination.getText());
			}else{
				output_destination.setForeground(Color.red);
				ImageToST7735LCD.setStatus(invalidDirectory, Color.red);
			}
		}else{
			ImageToST7735LCD.setStatus(sysNotInit, Color.red);
		}
	}
}

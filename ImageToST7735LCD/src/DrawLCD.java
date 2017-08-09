import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

public class DrawLCD extends JPanel {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  final int MAX_RECT_X = 128;
    private  final int MAX_RECT_Y = 160;
    private  final int RECT_WIDTH = 1;
    private  final int RECT_HEIGHT = 1;
   
    private  int imageArrayWidth = 0;
    private  int imageArrayHeight = 0;
    private  int[][] imageArray;
 
	public DrawLCD(int[][] imageArray,int imageArrayWidth,int imageArrayHeight){
		this.setBackground(Color.WHITE);
		this.imageArray = imageArray;
		this.imageArrayWidth = imageArrayWidth;
		this.imageArrayHeight = imageArrayHeight;
    }
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	    
		for (int yPixel =  0; yPixel < imageArrayHeight; yPixel++){
			for (int xPixel =  0; xPixel < imageArrayWidth; xPixel++){
			    int color = imageArray[yPixel][xPixel];
			    
			    int colorRed = ((color << 3) & 0xFF);
	            //int colorAlfa = (color & 0xFF);
	            int colorGreen = ((color >> 3) & 0xFC);
	            int colorBlue = ((color >> 8) & 0xF8);
	            		            
	            g.setColor(new Color(colorRed,colorGreen,colorBlue));
	            g.fillRect((int)((MAX_RECT_X-imageArrayWidth)/2.0)+xPixel, (int)((MAX_RECT_Y-imageArrayHeight)/2.0)+yPixel, RECT_WIDTH, RECT_HEIGHT);
			}
		}
   }
	
	public void refresh(){
		imageArray = ImageToST7735LCD.getImageArray();
		this.imageArrayWidth = ImageToST7735LCD.getCropWidth();
		this.imageArrayHeight = ImageToST7735LCD.getCropHeight();
		repaint();
	}
	
	public int getImageWidth() {
		return imageArrayWidth;
	}

	public void setImageWidth(int imageWidth) {
		this.imageArrayWidth = imageWidth;
	}

	public int getImageHeight() {
		return imageArrayHeight;
	}

	public void setImageHeight(int imageHeight) {
		this.imageArrayHeight = imageHeight;
	}

	public int[][] getImageArray() {
		return imageArray;
	}

	public void setImageArray(int[][] imageArray) {
		this.imageArray = imageArray;
		this.repaint();
	}
	   

}
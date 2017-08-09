import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class CropFrame extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int maxCropWidth = 128;
	private final int maxCropHeight = 160;
	
	
	private  int cropX;
	private  int cropY;
	private  int cropFrameX;
	private  int cropFrameY;
	private  int cropWidth;
	private  int cropHeight;
	
	private  int mouseX = 0;
	private  int mouseY = 0;
	private  int maxWidth;
	private  int maxHeight;
	
	private ResizableCropFrame resizableFrame;

	@Override
    protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    resizableFrame.draw(g);
	}
	
	public CropFrame(int panelWidth,int panelHeight){
		maxWidth = panelWidth;
		maxHeight = panelHeight;
		
		center();
		
		this.setOpaque(false);
		this.setBorder(BorderFactory.createDashedBorder(new Color(50, 50, 50))); 
				
		resizableFrame = new ResizableCropFrame(maxCropWidth,maxCropHeight,cropX,cropY,cropWidth,cropHeight);
		
		this.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
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
				resizableFrame.setVariable(cropX, cropY, cropWidth, cropHeight);
				resizableFrame.mousePressed(e);
				mouseX = e.getXOnScreen()-ImageToST7735LCD.getX()-ImageToST7735LCD.getImagePanelX();
				mouseY = e.getYOnScreen()-ImageToST7735LCD.getY()-ImageToST7735LCD.getImagePanelY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		this.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(resizableFrame.mouseDragged(arg0)){
					cropX = resizableFrame.getCropX();
					cropY = resizableFrame.getCropY();
					cropWidth = resizableFrame.getCropWidth();
					cropHeight = resizableFrame.getCropHeight();
				}else{
					moveBodyWithMouse(arg0);
					ImageToST7735LCD.setStatus("Moving Crop Frame...", Color.BLACK);
				}
				setBounds(cropFrameX, cropFrameY, maxCropWidth, maxCropHeight);
				repaint();
				ImageToST7735LCD.refreshLCDWithCropFrame();
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				//setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				//crop.setBounds(10, 10, 100, 100);
				//repaint();
			}
			
		});
		
	}
	
	
	private void moveBodyWithMouse(MouseEvent arg0){
		int tempX = arg0.getXOnScreen()-ImageToST7735LCD.getX()-ImageToST7735LCD.getImagePanelX();
		int tempY = arg0.getYOnScreen()-ImageToST7735LCD.getY()-ImageToST7735LCD.getImagePanelY();
		cropFrameX += tempX-mouseX;
		if(cropFrameX < 0 || cropFrameX > maxWidth-maxCropWidth ){
			cropFrameX += (mouseX-tempX);
		}else{
			mouseX = tempX;
		}

		cropFrameY += tempY-mouseY;
		if(cropFrameY < 0 || cropFrameY > maxHeight-maxCropHeight ){
			cropFrameY += (mouseY-tempY);
		}else{
			mouseY = tempY;
		}
	}

	public void centerFrame(){
		cropFrameX = (int)((maxWidth-maxCropWidth)/2.0);
		cropFrameY = (int)((maxHeight-maxCropHeight)/2.0);
		this.setBounds(cropFrameX, cropFrameY, maxCropWidth, maxCropHeight);
	}
	
	private void center(){
		cropFrameX = (int)((maxWidth-maxCropWidth)/2.0);
		cropFrameY = (int)((maxHeight-maxCropHeight)/2.0);
		this.setBounds(cropFrameX, cropFrameY, maxCropWidth, maxCropHeight);
		cropX = 14;
		cropY = 30;
		cropWidth = 100;
		cropHeight = 100;
	}
	public void setDimentions(int x, int y, int width, int height){
		if(x >= 0 && x <= maxWidth-maxCropWidth && y >= 0 && y <= maxHeight-maxCropHeight && width > 1 && width <= maxCropWidth && height > 1 && height <= maxCropHeight){
			cropFrameX = x;
			cropFrameY = y;
			cropX = (int) ((maxCropWidth-width)/2.0);
			cropY = (int) ((maxCropHeight-height)/2.0);
			cropWidth = width;
			cropHeight = height;
			setBounds(cropFrameX, cropFrameY, maxCropWidth, maxCropHeight);
			resizableFrame.setVariable(cropX, cropY, cropWidth, cropHeight);
			repaint();
		}else{
			ImageToST7735LCD.setStatus("Crop Dimention Error", Color.RED);
		}
	}

	public int getCropX() {
		return cropX;
	}
	public void setCropX(int cropX) {
		this.cropX = cropX;
	}
	public int getCropY() {
		return cropY;
	}
	public void setCropY(int cropY) {
		this.cropY = cropY;
	}
	public  int getCropWidth() {
		return cropWidth;
	}
	public  void setCropWidth(int cropWidth) {
		this.cropWidth = cropWidth;
	}
	public  int getCropHeight() {
		return cropHeight;
	}
	public  void setCropHeight(int cropHeight) {
		this.cropHeight = cropHeight;
	}
 
}

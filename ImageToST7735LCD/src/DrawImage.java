import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;

public class DrawImage extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double zoom = 1.0;
	
	


	private final int panelWidth;
	private final int panelHeight;
	
	private  int mouseX = 0;
	private  int mouseY = 0;
	private  int imageX;
	private  int imageY;
	private  int imageWidth;
	private  int imageHeight;
	
	private  int tempImageWidth;
	private  int tempImageHeight;
	
	private  Image image;

	private CropFrame cropFrame;

	private ResizableImageFrame rif;
	
	
	public DrawImage(Image image, int panel_Width,int panel_Height){
		this.setVisible(true);
		this.image = image;
		panelWidth = panel_Width;
		panelHeight = panel_Height;
		imageWidth = image.getWidth(this);
		imageHeight = image.getHeight(this);
		
		this.setBackground(Color.LIGHT_GRAY);
		
		setCenter();
		
		cropFrame = new CropFrame(panelWidth,panelHeight);
		this.setLayout(null);
		this.add(cropFrame);
		
		rif = new ResizableImageFrame(imageX+ImageToST7735LCD.getImagePadding(),imageY+ImageToST7735LCD.getImagePadding(),imageWidth-ImageToST7735LCD.getImagePadding(),imageHeight-ImageToST7735LCD.getImagePadding());

		this.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
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
				mouseX = arg0.getX();
				mouseY = arg0.getY();
				rif.mousePressed(arg0);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(rif.mouseDragged(arg0)){
					if(ImageToST7735LCD.getRotationSliderValue() == 0||ImageToST7735LCD.getRotationSliderValue() == 360){
						ImageToST7735LCD.resizeImage(rif.getCropWidth(), rif.getCropHeight());
						imageX = rif.getCropX()-ImageToST7735LCD.getImagePadding();
						imageY = rif.getCropY()-ImageToST7735LCD.getImagePadding();
						zoom = 1;
						refresh();
					}else{
						mouseDrag(arg0);
						ImageToST7735LCD.setStatus("Moving Image...", Color.BLACK);
					}
				}else{
					mouseDrag(arg0);
					ImageToST7735LCD.setStatus("Moving Image...", Color.BLACK);
				}
				
				//refreshResizableFrame();
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			
		});
		
		this.addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getWheelRotation() > 0 && zoom >= 0.05){
					zoom -= 0.05;
					tempImageWidth = imageWidth;
					tempImageHeight = imageHeight;
					ImageToST7735LCD.resizeImageEvenly(zoom);
					mouseWheelRefresh();
					imageX += (tempImageWidth - imageWidth)/2.0;
					imageY += (tempImageHeight - imageHeight)/2.0;
				}else if(arg0.getWheelRotation() < 0 && zoom <= 2){
					zoom += 0.05;
					tempImageWidth = imageWidth;
					tempImageHeight = imageHeight;
					ImageToST7735LCD.resizeImageEvenly(zoom);
					mouseWheelRefresh();
					imageX -= (imageWidth - tempImageWidth)/2.0;
					imageY -= (imageHeight - tempImageHeight)/2.0;
				}
				refreshResizableFrame();
				ImageToST7735LCD.refreshLCDWithCropFrame();
			}
			
		});
		
	}
	public void setCropFrameDimentions(int x, int y, int width, int height){
		cropFrame.setDimentions(x, y, width, height);
		ImageToST7735LCD.refreshLCDWithCropFrame();
	}
	
	public void resizeImage(int width, int height){
		ImageToST7735LCD.resizeImage(width, height);
		imageX = rif.getCropX()-ImageToST7735LCD.getImagePadding();
		imageY = rif.getCropY()-ImageToST7735LCD.getImagePadding();
		zoom = 1;
		refresh();
	}
	private void mouseDrag(MouseEvent arg0){
		int tempX = arg0.getX();
		int tempY = arg0.getY();
		
		imageX += tempX-mouseX;
		mouseX = tempX;
		
		imageY += tempY-mouseY;
		mouseY = tempY;
		
		repaint();
		ImageToST7735LCD.refreshLCDWithCropFrame();
		refreshResizableFrame();
	}
	public void setZoom(double zoom) {
		this.zoom = zoom;
	}
	public double getZoom() {
		return zoom;
	}
	public void refreshResizableFrame(){
		rif.setVariable(imageX+ImageToST7735LCD.getImagePadding(), imageY+ImageToST7735LCD.getImagePadding(), imageWidth-ImageToST7735LCD.getImagePadding(), imageHeight-ImageToST7735LCD.getImagePadding());
	}
	private void mouseWheelRefresh(){
		this.image = ImageToST7735LCD.getImage();
		imageWidth = image.getWidth(this);
		imageHeight = image.getHeight(this);
		repaint();
	}
	public void refresh(){
		this.image = ImageToST7735LCD.getImage();
		imageWidth = image.getWidth(this);
		imageHeight = image.getHeight(this);
		//setCenter();
		//cropFrame.refresh();
		repaint();
		ImageToST7735LCD.refreshLCDWithCropFrame();
		refreshResizableFrame();
	}
	
	public void setCropFrammeCenter(){
		cropFrame.centerFrame();
	}
		
	public void setCenter(){
		imageX = (int) ((panelWidth-imageWidth-ImageToST7735LCD.getImagePadding())/2.0);
		imageY = (int) ((panelHeight-imageHeight-ImageToST7735LCD.getImagePadding())/2.0);
		//cropFrame.centerFrame();
	}
	
	public int getCropFrameX(){
		return cropFrame.getX();
	}
	public int getCropFrameY(){
		return cropFrame.getY();
	}
	
	public int getCropX() {
		return cropFrame.getCropX();
	}
	public int getCropY() {
		return cropFrame.getCropY();
	}
	public int getCropWidth() {
		return cropFrame.getCropWidth();
	}
	public int getCropHeight() {
		return cropFrame.getCropHeight();
	}
		
	public int getImageX() {
		return imageX;
	}
	public void setImageX(int imageX) {
		this.imageX = imageX;
	}
	public int getImageY() {
		return imageY;
	}
	public void setImageY(int imageY) {
		this.imageY = imageY;
	}
	public int getImageWidth() {
		return imageWidth;
	}
	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}
	public int getImageHeight() {
		return imageHeight;
	}
	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	
	@Override
    protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    g.drawImage(image,imageX,imageY,this);
	    if(ImageToST7735LCD.getRotationSliderValue() == 0||ImageToST7735LCD.getRotationSliderValue() == 360){
	    	rif.draw(g);
	    }
	}
	
}

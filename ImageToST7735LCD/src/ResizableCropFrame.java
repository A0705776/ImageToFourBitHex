import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;


public class ResizableCropFrame {
	private int cropX;
	private int cropY;
	private int cropWidth;
	private int cropHeight;
	
	private  int mouseX = 0;
	private  int mouseY = 0;
	private  int maxWidth;
	private  int maxHeight;

	private final int smallRect = 3;
	private enum cropState{UL,UR,LL,LR,DEFAULT}
	private cropState state = cropState.DEFAULT;
	private Rectangle crop;
	private Rectangle UL;
	private Rectangle UR;
	private Rectangle LL;
	private Rectangle LR;
	
	/*	UL---UM---UR
	 *	|		   |
	 *	ML  CROP  MR
	 *	|		   |
	 *	LL---LM---LR
	 */
    public void draw(Graphics g) {
	    Graphics2D g2D = (Graphics2D) g;
	    g2D.draw(crop);
	    g2D.fill(UL);
	    g2D.fill(UR);
	    g2D.fill(LL);
	    g2D.fill(LR);
	}
	
	public ResizableCropFrame(int panelWidth,int panelHeight,int crop_X, int crop_Y, int crop_Width, int crop_Height){
		maxWidth = panelWidth;
		maxHeight = panelHeight;
		cropX = crop_X;
		cropY = crop_Y;
		cropWidth = crop_Width;
		cropHeight = crop_Height;
		
		crop = new Rectangle();
		UL = new Rectangle();
		UR = new Rectangle();
		LL = new Rectangle();
		LR = new Rectangle();
		crop.setBounds(cropX, cropY, cropWidth, cropHeight);
		UL.setBounds(cropX-smallRect, cropY-smallRect, smallRect*2, smallRect*2);
		UR.setBounds(cropWidth-smallRect+cropX, cropY-smallRect, smallRect*2, smallRect*2);
		LL.setBounds(cropX-smallRect, cropHeight-smallRect+cropY, smallRect*2, smallRect*2);
		LR.setBounds(cropWidth-smallRect+cropX, cropHeight-smallRect+cropY, smallRect*2, smallRect*2);
				
	}
	public boolean mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseX = e.getX();
		mouseY = e.getY();
		if(e.getX()  >= UL.getX() && e.getX() <= UL.getX()+smallRect*2 && e.getY()  >= UL.getY() && e.getY() <= UL.getY()+smallRect*2){
			state = cropState.UL;
		}else if(e.getX()  >= UR.getX() && e.getX() <= UR.getX()+smallRect*2 && e.getY()  >= UR.getY() && e.getY() <= UR.getY()+smallRect*2){
			
			state = cropState.UR;
		}else if(e.getX()  >= LL.getX() && e.getX() <= LL.getX()+smallRect*2 && e.getY()  >= LL.getY() && e.getY() <= LL.getY()+smallRect*2){
			
			state = cropState.LL;
		}else if(e.getX()  >= LR.getX() && e.getX() <= LR.getX()+smallRect*2 && e.getY()  >= LR.getY() && e.getY() <= LR.getY()+smallRect*2){
			
			state = cropState.LR;
		}else{
			state = cropState.DEFAULT;
			return false;
		}
		return true;
		
	}
	
	
	
	
	public boolean mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		switch(state){
		case UL: moveULWithMouse(arg0);break;
		case UR: moveURWithMouse(arg0);break;
		case LL: moveLLWithMouse(arg0);break;
		case LR: moveLRWithMouse(arg0);break;
		default: return false;
		}
		
		crop.setBounds(cropX, cropY, cropWidth, cropHeight);
		UL.setBounds(cropX-smallRect, cropY-smallRect, smallRect*2, smallRect*2);
		UR.setBounds(cropWidth-smallRect+cropX, cropY-smallRect, smallRect*2, smallRect*2);
		LL.setBounds(cropX-smallRect, cropHeight-smallRect+cropY, smallRect*2, smallRect*2);
		LR.setBounds(cropWidth-smallRect+cropX, cropHeight-smallRect+cropY, smallRect*2, smallRect*2);
		ImageToST7735LCD.setStatus("Resizing Crop Frame...", Color.BLACK);
		return true;
	}
	
		
	private void moveLRWithMouse(MouseEvent arg0){
		int tempX = arg0.getX();
		int tempY = arg0.getY();
		//cropX += tempX-mouseX;
		cropWidth += tempX-mouseX;
		if(cropWidth+cropX > maxWidth || cropWidth < smallRect ){
			//cropX += (mouseX-tempX);
			cropWidth += (mouseX-tempX);
		}else{
			mouseX = tempX;
		}

		//cropY += tempY-mouseY;
		cropHeight += tempY-mouseY;
		if(cropHeight+cropY > maxHeight || cropHeight < smallRect ){
			//cropY += (mouseY-tempY);
			cropHeight += (mouseY-tempY);
		}else{
			mouseY = tempY;
		}
	}
	
	private void moveLLWithMouse(MouseEvent arg0){
		int tempX = arg0.getX();
		int tempY = arg0.getY();
		cropX += tempX-mouseX;
		cropWidth -= tempX-mouseX;
		if(cropX < 0 || cropX > cropWidth-smallRect+cropX ){
			cropX += (mouseX-tempX);
			cropWidth -= (mouseX-tempX);
		}else{
			mouseX = tempX;
		}

		//cropY += tempY-mouseY;
		cropHeight += tempY-mouseY;
		if(cropHeight+cropY > maxHeight || cropHeight < smallRect ){
			//cropY += (mouseY-tempY);
			cropHeight += (mouseY-tempY);
		}else{
			mouseY = tempY;
		}
	}
	
	private void moveURWithMouse(MouseEvent arg0){
		int tempX = arg0.getX();
		int tempY = arg0.getY();
		//cropX += tempX-mouseX;
		cropWidth += tempX-mouseX;
		if(cropWidth+cropX > maxWidth || cropWidth < smallRect ){
			//cropX += (mouseX-tempX);
			cropWidth += (mouseX-tempX);
		}else{
			mouseX = tempX;
		}

		cropY += tempY-mouseY;
		cropHeight -= tempY-mouseY;
		if(cropY < 0 || cropY > cropHeight-smallRect+cropY ){
			cropY += (mouseY-tempY);
			cropHeight -= (mouseY-tempY);
		}else{
			mouseY = tempY;
		}
	}
	
	private void moveULWithMouse(MouseEvent arg0){
		int tempX = arg0.getX();
		int tempY = arg0.getY();
		cropX += tempX-mouseX;
		cropWidth -= tempX-mouseX;
		if(cropX < 0 || cropX > cropWidth-smallRect+cropX ){
			cropX += (mouseX-tempX);
			cropWidth -= (mouseX-tempX);
		}else{
			mouseX = tempX;
		}

		cropY += tempY-mouseY;
		cropHeight -= tempY-mouseY;
		if(cropY < 0 || cropY > cropHeight-smallRect+cropY ){
			cropY += (mouseY-tempY);
			cropHeight -= (mouseY-tempY);
		}else{
			mouseY = tempY;
		}
	}
	
	public void setVariable(int x, int y, int width, int height){
		cropX = x;
		cropY = y;
		cropWidth = width;
		cropHeight = height;
		
		crop.setBounds(cropX, cropY, cropWidth, cropHeight);
		UL.setBounds(cropX-smallRect, cropY-smallRect, smallRect*2, smallRect*2);
		UR.setBounds(cropWidth-smallRect+cropX, cropY-smallRect, smallRect*2, smallRect*2);
		LL.setBounds(cropX-smallRect, cropHeight-smallRect+cropY, smallRect*2, smallRect*2);
		LR.setBounds(cropWidth-smallRect+cropX, cropHeight-smallRect+cropY, smallRect*2, smallRect*2);
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

	public int getCropWidth() {
		return cropWidth;
	}

	public void setCropWidth(int cropWidth) {
		this.cropWidth = cropWidth;
	}

	public int getCropHeight() {
		return cropHeight;
	}

	public void setCropHeight(int cropHeight) {
		this.cropHeight = cropHeight;
	}
}

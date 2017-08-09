import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

public class ImageProcessing {
	private final int imagePadding = 500;
	
	private BufferedImage outputImage;
	private BufferedImage scrollResigedImage;
	private BufferedImage manualResigedImage;
	private BufferedImage editImage;
	private BufferedImage manualEditImage;
	private BufferedImage originalImage;
	private int[][] imageArray;
	
	public ImageProcessing(String fileAdress){
		openImage(fileAdress);
	}
	
	public void openImage(String fileAdress){
		try {
			originalImage = ImageIO.read(new File(fileAdress));
			
			manualEditImage = originalImage;
            editImage = manualEditImage;
            
            //bAndWAndInvert();
            //invertImage();  
            //bAndW();
            
            
			manualResigedImage = editImage;
			scrollResigedImage = manualResigedImage;
			rotateImage(ImageToST7735LCD.getRotationSliderValue());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void editRGBA(double r, double g, double b, double a){
		BufferedImage sImage = new BufferedImage( originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		for (int x = 0; x < originalImage.getWidth();x++) {
            for(int y = 0; y < originalImage.getHeight();y++){
            	int color = originalImage.getRGB(x, y); 
	             
	            int colorB = (int) ((color & 0xFF)*b);
	            int colorG = (int) (((color>>8) & 0xFF)*g);
	            int colorR = (int) (((color>>16) & 0xFF)*r);
	            int alpha  = (int) (((color>>24) & 0xFF)*a);            
	            
	            if(alpha  > 255){
	            	int temp = alpha - 255;
	            	colorB *= 1.0 - (temp/255.0); 
	            	colorG *= 1.0 - (temp/255.0);
	            	colorR *= 1.0 - (temp/255.0);
	            	alpha  = 255;
	            }
	            
	            if(colorB > 255)colorB = 255;
	            if(colorG > 255)colorG = 255;
	            if(colorR > 255)colorR = 255;
	           
	            color = (colorB) + (colorG << 8) + (colorR << 16) + (alpha << 24); 
	            
	            sImage.setRGB(x, y, color);
        	}
        }
		manualEditImage = sImage;
		editImage = manualEditImage;
		resizeImage(manualResigedImage.getWidth(),manualResigedImage.getHeight());
	}
	
	public void resetImageFromEditImage(){
		editImage = manualEditImage;
		manualResigedImage = scaleImage(editImage,manualResigedImage.getWidth(),  manualResigedImage.getHeight());
		resizeImageEvenly(ImageToST7735LCD.getMouseZoom());
		rotateImage(ImageToST7735LCD.getRotationSliderValue());
	}
	
	public void resetImage(){
		manualEditImage = originalImage;
		editImage = manualEditImage;
		manualResigedImage = editImage;
		scrollResigedImage = manualResigedImage;
		rotateImage(0);
	}
	public void grayScaleAndInvert(){ 
		BufferedImage sImage = new BufferedImage( originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        for (int x = 0; x < manualEditImage.getWidth();x++) {
            for(int y = 0; y < manualEditImage.getHeight();y++){
            	int color = manualEditImage.getRGB(x, y); 
	             
	            int colorB = 255 - (color & 0xFF);
	            int colorG = 255 - ((color>>8) & 0xFF);
	            int colorR = 255 - ((color>>16) & 0xFF);
	            int alpha =    ((color>>24) & 0xFF);            
	            
	            color = (colorB) | (colorG << 8) | (colorR << 16) | (alpha << 24);
	            
	            sImage.setRGB(x, y, color);
        	}
        }
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(sImage,sImage);
        editImage = sImage;
        resizeImage(manualResigedImage.getWidth(),manualResigedImage.getHeight());
	}
	public void grayScale(){
		BufferedImage sImage = new BufferedImage( originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(manualEditImage,sImage);
        editImage = sImage;
        resizeImage(manualResigedImage.getWidth(),manualResigedImage.getHeight());
	}
	
	public void bAndWAndInvert(){ 
		BufferedImage sImage = new BufferedImage( originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        for (int x = 0; x < manualEditImage.getWidth();x++) {
            for(int y = 0; y < manualEditImage.getHeight();y++){
            	int color = manualEditImage.getRGB(x, y); 
	             
	            int colorB = 255 - (color & 0xFF);
	            int colorG = 255 - ((color>>8) & 0xFF);
	            int colorR = 255 - ((color>>16) & 0xFF);
	            int alpha =    ((color>>24) & 0xFF);            
	            
	            color = (colorB) | (colorG << 8) | (colorR << 16) | (alpha << 24);
	            
	            sImage.setRGB(x, y, color);
        	}
        }
        editImage = sImage;
        BufferedImage blackWhite = new BufferedImage(editImage.getWidth(), editImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = blackWhite.createGraphics();
        g2d.drawImage(editImage, 0, 0, null);
        g2d.dispose();
        editImage = blackWhite;
        resizeImage(manualResigedImage.getWidth(),manualResigedImage.getHeight());
	}
	
	public void bAndW(){
		BufferedImage blackWhite = new BufferedImage(manualEditImage.getWidth(), manualEditImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = blackWhite.createGraphics();
        g2d.drawImage(manualEditImage, 0, 0, null);
        g2d.dispose();
        editImage = blackWhite;
        resizeImage(manualResigedImage.getWidth(),manualResigedImage.getHeight());
	}
	
	public void invertImage(){
		BufferedImage sImage = new BufferedImage( originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        for (int x = 0; x < manualEditImage.getWidth();x++) {
            for(int y = 0; y < manualEditImage.getHeight();y++){
            	int color = manualEditImage.getRGB(x, y); 
	             
	            int colorB = 255 - (color & 0xFF);
	            int colorG = 255 - ((color>>8) & 0xFF);
	            int colorR = 255 - ((color>>16) & 0xFF);
	            int alpha =    ((color>>24) & 0xFF);            
	            
	            color = (colorB) | (colorG << 8) | (colorR << 16) | (alpha << 24);
	            
	            sImage.setRGB(x, y, color);
        	}
        }
        editImage = sImage;
		resizeImage(manualResigedImage.getWidth(),manualResigedImage.getHeight());
    }
	
	
	public void rotateImage(int rotation){
		 AffineTransform transform = AffineTransform.getTranslateInstance(imagePadding, imagePadding);
		 transform.rotate(Math.toRadians(rotation),scrollResigedImage.getWidth()/2, scrollResigedImage.getHeight()/2);
		 AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);
		 outputImage = op.filter(scrollResigedImage, null);
	}
	
	public void initImageArray(int x, int y, int width, int height){
		imageArray = new int[outputImage.getHeight()][outputImage.getWidth()];
		for (int yPixel =  y, arrayX = 0; yPixel < height && yPixel < outputImage.getHeight() && y > 0; yPixel++,arrayX++){
	        for (int xPixel =  x, arrayY = 0; xPixel < width && xPixel < outputImage.getWidth() && x > 0; xPixel++,arrayY++){
	            int color = outputImage.getRGB(xPixel, yPixel); //*
	             
	            int colorB = (color & 0xFF);
	            int colorG = ((color>>8) & 0xFF);
	            int colorR = ((color>>16) & 0xFF);
	            //int alpha = ((color>>24) & 0xFF);            
	            
	            color = ((colorB & 0xF8) << 8) | ((colorG & 0xFC) << 3) | (colorR >> 3);
	            imageArray[arrayX][arrayY] = (color); 
	        }
	    }
	}
	
	int bitReverse(int value){
       int result = 0;
       while(value > 0){
           /* make space by shifting left on the result */
           result <<= 1;
           /* add the low bit in to the space */
           result |= value & 1;
           /* get a new low bit. */
           value >>= 1;
       }
       return result;
    }
	
	
	public void saveImage(String fileAdress,int cropWidth, int cropHeight){
		String temp;
		PrintWriter writer;
		try {
			writer = new PrintWriter(fileAdress, "UTF-8");
			writer.println("const uint16_t " + fileAdress.substring(fileAdress.lastIndexOf("\\")+1,fileAdress.lastIndexOf(".")) +"[] = {");
			for(int yPixel = cropHeight - 1; yPixel >= 0 ; yPixel--){
				for(int xPixel =  0; xPixel < cropWidth ; xPixel++){
					writer.print("0x");
					temp = Integer.toHexString(imageArray[yPixel][xPixel]).toUpperCase();
					for(int i = 4 - temp.length(); i > 0;i--){writer.print("0");}
					writer.print(temp);
					writer.print(", ");
				}
				writer.println();
			}
			writer.print("}");
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public int getImagePadding() {
		return imagePadding;
	}

	public BufferedImage getImage(){
		return outputImage;
	}
	
	public int getImageHeight(){
		return outputImage.getHeight();
	}
	
	public int getImageWidth(){
		return outputImage.getWidth();
	}
	
	public int[][] getImageArray(){
		return imageArray;
	}
	
	public void resizeImageEvenly(double multiplier){
		try{
			scrollResigedImage = scaleImage(manualResigedImage,(int) (manualResigedImage.getWidth()*multiplier),(int) (manualResigedImage.getHeight()*multiplier));
			rotateImage(ImageToST7735LCD.getRotationSliderValue());
			ImageToST7735LCD.setStatus("Resizing Image...", Color.BLACK);
		}catch(Exception e){
			ImageToST7735LCD.setStatus("Image Size ERROR", Color.RED);
		}	
	}
	public void resizeImage(int width, int height){
		try{
			manualResigedImage = scaleImage(editImage,width,height);
			scrollResigedImage = manualResigedImage;
			rotateImage(ImageToST7735LCD.getRotationSliderValue());
			ImageToST7735LCD.setStatus("Resizing Image...", Color.BLACK);
		}catch(Exception e){
			ImageToST7735LCD.setStatus("Image Size ERROR", Color.RED);
		}
	}
	
	
	public BufferedImage scaleImage(BufferedImage originalImage,int width, int height){
		BufferedImage sImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2D = sImage.createGraphics(); //create a graphics object to paint to
		g2D.setBackground( Color.WHITE );
		g2D.setPaint( Color.WHITE );
		g2D.fillRect( 0, 0, width, height );
		g2D.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
		g2D.drawImage(originalImage, 0, 0, width, height, null ); //draw the image scaled
		return sImage;
	}
}

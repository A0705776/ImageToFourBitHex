import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

public class ImageToST7735LCD {
	private static boolean initialized = false;
	
	private static JFrame frame;
	
	private static JPanel panel_Image;
	private static JPanel panel_LCD;

	private static ImageProcessing imageProcessing;
	private static DrawImage drawImage;
	private static DrawLCD drawLCD;
	
	private static JLabel statusLabel;
	
	private JLabel lblStLcd;
	private JLabel lblNewLabel;
	private static JPanel imageResizePanel;

	public static ImageResizePanel image_Resize_Panel;
	private static JPanel cropResizePanel;
	private static JPanel imageProcessingPanel;
	private static JPanel resetPanel;
	
	
	
	private JLabel lblDragAndDrop;

	private static CropResizePanel crop_Resize_Panel;

	private static EditImagePanel editImagePanel;

	private static ResetPanel reset_Panel;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImageToST7735LCD window = new ImageToST7735LCD();
					window.frame.setVisible(true);
					window.frame.setTitle("Alamin's Image to 16Bit Hex Converter V3");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ImageToST7735LCD() {
		initialize();
					
	}
	
	public static void initVariables(String file){
		imageProcessing = new ImageProcessing(file);
		
		if(!initialized){
			panel_Image.setLayout(new BorderLayout(0, 0));
			drawImage =  new DrawImage(getImage(),getImagePanelWidth(),getImagePanelHeight());
			panel_Image.add(drawImage, BorderLayout.CENTER);		
			panel_Image.setVisible(false);
			panel_Image.setVisible(true);
			
			drawLCD = new DrawLCD(imageProcessing.getImageArray(),getCropWidth(),getCropHeight());
			drawLCD.setBounds(12, 15, 128, 160);
			panel_LCD.add(drawLCD);
			
			image_Resize_Panel = new ImageResizePanel();
			imageResizePanel.add(image_Resize_Panel, BorderLayout.CENTER);
			
			crop_Resize_Panel = new CropResizePanel();
			cropResizePanel.add(crop_Resize_Panel, BorderLayout.CENTER);
			setCropPanelDimentions(getCropFrameX(),getCropFrameY(),getCropWidth(),getCropHeight());
			
			editImagePanel = new EditImagePanel();
			imageProcessingPanel.add(editImagePanel, BorderLayout.CENTER);
			
			reset_Panel = new ResetPanel();
			resetPanel.add(reset_Panel, BorderLayout.CENTER);
			
			initialized = true;
		}else{
			//TODO rotationSlider.setValue(0);
			drawImage.refresh();
			drawImage.setCenter();
			drawImage.refreshResizableFrame();
			drawImage.setZoom(1);
			editImagePanel.reset();
		}
		
		refreshLCDWithCropFrame();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.menu);
		frame.setBounds(100, 100, 890, 520);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		panel_Image = new JPanel();
		panel_Image.setForeground(Color.YELLOW);
		panel_Image.setBackground(Color.LIGHT_GRAY);
		panel_Image.setBounds(10, 11, 364, 469);
		panel_Image.setTransferHandler(new DragAndDrop());
		frame.getContentPane().add(panel_Image);		
		panel_Image.setLayout(null);
		
		lblNewLabel = new JLabel("Image Window");
		lblNewLabel.setBounds(122, 5, 119, 22);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_Image.add(lblNewLabel);
		
		lblDragAndDrop = new JLabel("Drag and Drop Capable");
		lblDragAndDrop.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDragAndDrop.setBounds(115, 436, 138, 22);
		panel_Image.add(lblDragAndDrop);
		
		panel_LCD = new JPanel();
		panel_LCD.setForeground(Color.YELLOW);
		panel_LCD.setBackground(Color.LIGHT_GRAY);
		panel_LCD.setBounds(722, 269, 152, 185);
		frame.getContentPane().add(panel_LCD);
		panel_LCD.setLayout(null);
		
		lblStLcd = new JLabel("ST7735 LCD");
		lblStLcd.setBounds(42, 0, 76, 14);
		panel_LCD.add(lblStLcd);
		lblStLcd.setBackground(Color.WHITE);
		lblStLcd.setForeground(new Color(0, 0, 0));
		
		
		JLabel statusTitle = new JLabel("Status: ");
		statusTitle.setBounds(384, 466, 46, 14);
		frame.getContentPane().add(statusTitle);
		
		statusLabel = new JLabel("N/A");
		statusLabel.setBounds(434, 466, 440, 14);
		frame.getContentPane().add(statusLabel);
		
		JPanel panel_GetImageFile = new JPanel();
		panel_GetImageFile.setBackground(Color.LIGHT_GRAY);
		panel_GetImageFile.setBounds(384, 11, 490, 131);
		frame.getContentPane().add(panel_GetImageFile);
		panel_GetImageFile.setLayout(null);
				
		GetImageFile getImageFile = new GetImageFile();
		getImageFile.setBounds(0, 0, 490, 131);
		panel_GetImageFile.add(getImageFile);
		
		imageResizePanel = new JPanel();
		imageResizePanel.setBounds(384, 153, 266, 105);
		frame.getContentPane().add(imageResizePanel);
		imageResizePanel.setBackground(Color.LIGHT_GRAY);
		imageResizePanel.setLayout(new BorderLayout(0, 0));
		
		cropResizePanel = new JPanel();
		cropResizePanel.setBackground(Color.LIGHT_GRAY);
		cropResizePanel.setBounds(660, 153, 214, 105);
		frame.getContentPane().add(cropResizePanel);
		cropResizePanel.setLayout(new BorderLayout(0, 0));		
		
		imageProcessingPanel = new JPanel();
		imageProcessingPanel.setBackground(Color.LIGHT_GRAY);
		imageProcessingPanel.setBounds(384, 269, 328, 129);
		frame.getContentPane().add(imageProcessingPanel);
		imageProcessingPanel.setLayout(new BorderLayout(0, 0));
								
		resetPanel = new JPanel();
		resetPanel.setBackground(Color.LIGHT_GRAY);
		resetPanel.setBounds(384, 409, 328, 45);
		frame.getContentPane().add(resetPanel);
		resetPanel.setLayout(new BoxLayout(resetPanel, BoxLayout.X_AXIS));
		
	}
	
	
	public static void center(){
		drawImage.setCenter();
		drawImage.setCropFrammeCenter();
		drawImage.refresh();
		
	}
	
	public static void resetImageFromEditImage(){
		imageProcessing.resetImageFromEditImage();
		drawImage.refresh();
		setStatus("Editing Image...", Color.BLACK);
	}
	
	public static void grayScaleAndInvert(){
		imageProcessing.grayScaleAndInvert();
		imageProcessing.resizeImageEvenly(getMouseZoom());
		drawImage.refresh();
		setStatus("Editing Image...", Color.BLACK);
	}
	
	public static void bAndWAndInvert(){
		imageProcessing.bAndWAndInvert();
		imageProcessing.resizeImageEvenly(getMouseZoom());
		drawImage.refresh();
		setStatus("Editing Image...", Color.BLACK);
	}
	
	public static void grayScale(){
		imageProcessing.grayScale();
		imageProcessing.resizeImageEvenly(getMouseZoom());
		drawImage.refresh();
		setStatus("Editing Image...", Color.BLACK);
	}
	
	public static void bAndW(){
		imageProcessing.bAndW();
		imageProcessing.resizeImageEvenly(getMouseZoom());
		drawImage.refresh();
		setStatus("Editing Image...", Color.BLACK);
	}
	
	public static void invert(){
		imageProcessing.invertImage();
		imageProcessing.resizeImageEvenly(getMouseZoom());
		drawImage.refresh();
		setStatus("Editing Image...", Color.BLACK);
	}
	
	public static void reset(){
		imageProcessing.resetImage();
		drawImage.refresh();
		drawImage.setZoom(1);
		editImagePanel.reset();
		image_Resize_Panel.reset();
		setStatus("Reseted...",Color.BLACK);
	}
	
	public static void editRGBA(double r, double g, double b, double a){
		imageProcessing.editRGBA(r, g, b, a);
		imageProcessing.resizeImageEvenly(getMouseZoom());
		drawImage.refresh();
		setStatus("Editing Image...", Color.BLACK);
	}
	public static double getMouseZoom(){
		return drawImage.getZoom();
	}
	public static void setCropDimentions(int x, int y, int width, int height){
		drawImage.setCropFrameDimentions(x, y, width, height);
	}
	
	public static void setCropPanelDimentions(int x, int y, int width, int height){
		crop_Resize_Panel.setDimentions(x, y, width, height);
	}
	
	public static void resizeDrawImage(int width, int height){
		drawImage.resizeImage(width, height);
	}
	
	public static void rotateImage(int value){
		imageProcessing.rotateImage(value);
		drawImage.refresh();
		ImageToST7735LCD.setStatus("Rotating Image...", Color.BLACK);
	}
	
	public static JFrame getFrame() {
		return frame;
	}
	
	public static int getRotationSliderValue(){
		try{
			return image_Resize_Panel.getRotation();
		}catch(Exception e){
			return 0;
		}
	}
	
	public static void resizeImage(int width, int height){
		imageProcessing.resizeImage(width,height);
	}
	
	public static void saveImage(String file){
		imageProcessing.saveImage(file, getCropWidth(), getCropHeight());
	}
	
	public static boolean isInitialized() {
		return initialized;
	}
	
	public static void setStatus(String input,Color color){
		statusLabel.setForeground(color);
		statusLabel.setText(input);
	}
	
	public static void refreshLCDWithCropFrame(){
		ImageToST7735LCD.setupImageArray(getLCDX(),getLCDY(),getLCDX()+getCropWidth(),getLCDY()+getCropHeight());
		ImageToST7735LCD.refreshLCD();
		image_Resize_Panel.setImageSize(getImageWidth()-getImagePadding(), getImageHeight()-getImagePadding());
		setCropPanelDimentions(getCropFrameX(),getCropFrameY(),getCropWidth(),getCropHeight());
	}
	private static int getLCDX(){
		return ImageToST7735LCD.getCropFrameX()+Math.abs(ImageToST7735LCD.getImageX())+getCropX();
	}
	private static int getLCDY(){
		return ImageToST7735LCD.getCropFrameY()+Math.abs(ImageToST7735LCD.getImageY())+getCropY();
	}
	
	//getting image padding, used for the rotation
	public static int getImagePadding(){
		return imageProcessing.getImagePadding();
	}
	public static void setupImageArray(int x, int y, int width, int height){
		imageProcessing.initImageArray(x, y, width, height);
	}
	
	public static int getImageX(){
		return drawImage.getImageX();
	}
	public static int getImageY(){
		return drawImage.getImageY();
	}
	//getting crop dimension
	public static int getCropFrameX(){
		return drawImage.getCropFrameX();
	}
	public static int getCropFrameY(){
		return drawImage.getCropFrameY();
	}
	public static int getCropX() {
		return drawImage.getCropX();
	}
	public static int getCropY() {
		return drawImage.getCropY();
	}
	public static int getCropWidth() {
		return drawImage.getCropWidth();
	}
	public static int getCropHeight() {
		return drawImage.getCropHeight();
	}
	//getting the Image info
	public static BufferedImage getImage(){
		return imageProcessing.getImage();
	}
	public static int getImageHeight(){
		return imageProcessing.getImageHeight();
	}
	public static int getImageWidth(){
		return imageProcessing.getImageWidth();
	}
	public static int[][] getImageArray(){
		return imageProcessing.getImageArray();
	}
	public static void resizeImageEvenly(double multiplier){
		imageProcessing.resizeImageEvenly(multiplier);
	}
	//getting drawImage info
	public static void refreshImage(){
		drawImage.refresh();
	}
	public static void refreshLCD(){
		drawLCD.refresh();
	}
	//getting the frame info
	
//	public static int getLCDPanelX(){
//		return panel_LCD.getX();
//	}
//	public static int getLCDPanelY(){
//		return panel_LCD.getY();
//	}
//	public static int getLCDPanelWidth(){
//		return panel_LCD.getWidth();
//	}
//	public static int getLCDPanelHeight(){
//		return panel_LCD.getHeight();
//	}
	
	public static int getImagePanelX(){
		return panel_Image.getX();
	}
	public static int getImagePanelY(){
		return panel_Image.getY();
	}
	public static int getImagePanelWidth(){
		return panel_Image.getWidth();
	}
	public static int getImagePanelHeight(){
		return panel_Image.getHeight();
	}
	
	public static int getX(){
		return frame.getX();
	}
	public static int getY(){
		return frame.getY();
	}
	public static int getWidth(){
		return frame.getWidth();
	}
	public static int getHeight(){
		return frame.getHeight();
	}
}

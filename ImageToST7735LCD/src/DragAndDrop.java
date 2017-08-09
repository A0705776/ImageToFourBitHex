import java.awt.Color;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.TransferHandler;
import javax.swing.TransferHandler.TransferSupport;

public class DragAndDrop extends TransferHandler{
	
	public DragAndDrop(){
		
	}

	@Override
	public Image getDragImage() {
		// TODO Auto-generated method stub
		return super.getDragImage();
	}

	@Override
	public int getSourceActions(JComponent c) {
		// TODO Auto-generated method stub
		//System.out.println("a");
		return super.getSourceActions(c);
	}

	@Override
	public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
		// TODO Auto-generated method stub
		return super.canImport(comp, transferFlavors);
	}

	@Override
	public void exportAsDrag(JComponent comp, InputEvent e, int action) {
		// TODO Auto-generated method stub
		super.exportAsDrag(comp, e, action);
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
		// TODO Auto-generated method stub
		//System.out.println("a");
		super.exportDone(source, data, action);
	}

	@Override
	public boolean importData(TransferSupport support) {
		// TODO Auto-generated method stub
		
		return super.importData(support);
	}

	@Override
	public boolean canImport(TransferSupport support) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean importData(JComponent comp, Transferable t) {
		// TODO Auto-generated method stub
		try {
			String temp =  t.getTransferData(DataFlavor.javaFileListFlavor).toString();
			temp = temp.substring(1, temp.length()-1);
			if(temp.contains(", ")){
				ImageToST7735LCD.setStatus("Not a correct file format", Color.RED);
			}else if (temp.endsWith(".jpg")||temp.endsWith(".png")||temp.endsWith(".jpeg")||temp.endsWith(".bmp")){
				ImageToST7735LCD.initVariables(temp);
				//ImageToST7735LCD.setStatus("Image Opened", Color.BLACK);
			}else{
				ImageToST7735LCD.setStatus("Not a correct file format", Color.RED);
			}
			//System.out.println(temp.substring(1, temp.length()-1));
		} catch (UnsupportedFlavorException | IOException e) {
			// TODO Auto-generated catch block
			ImageToST7735LCD.setStatus("Error", Color.RED);
		}
		return super.importData(comp, t);
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		// TODO Auto-generated method stub
		return super.createTransferable(c);
	}
	
	
}

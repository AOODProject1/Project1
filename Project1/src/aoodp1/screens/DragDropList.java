package aoodp1.screens;
	import java.awt.datatransfer.DataFlavor;
	import java.awt.datatransfer.StringSelection;
	import java.awt.datatransfer.Transferable;
	import java.awt.dnd.DnDConstants;
	import java.awt.dnd.DragGestureEvent;
	import java.awt.dnd.DragGestureListener;
	import java.awt.dnd.DragGestureRecognizer;
	import java.awt.dnd.DragSource;
	import java.awt.dnd.DragSourceDragEvent;
	import java.awt.dnd.DragSourceDropEvent;
	import java.awt.dnd.DragSourceEvent;
	import java.awt.dnd.DragSourceListener;
	import javax.swing.DefaultListModel;
	import javax.swing.DropMode;
	import javax.swing.JFrame;
	import javax.swing.JList;
	import javax.swing.JScrollPane;
	import javax.swing.TransferHandler;
	
	@Deprecated
	public class DragDropList<E> extends JList<E> {
		private static final long serialVersionUID = -98360819352909216L;

	public DragDropList(E[] a) {
	    super(a);
	    setDragEnabled(true);
	    setDropMode(DropMode.INSERT);
	    setTransferHandler(new ListDrop(this));
	    new DragListener(this);
	  }
	private class DragListener implements DragSourceListener, DragGestureListener {
	  DragDropList<E> list;
	  DragSource ds = new DragSource();
	  public DragListener(DragDropList<E> list) {
	    this.list = list;
	    ds.createDefaultDragGestureRecognizer(list,DnDConstants.ACTION_MOVE, this);
	  }
	  public void dragGestureRecognized(DragGestureEvent dge) {
	    StringSelection transferable = new StringSelection(Integer.toString(list.getSelectedIndex()));
	    ds.startDrag(dge, DragSource.DefaultCopyDrop, transferable, this);
	  }
	  public void dragEnter(DragSourceDragEvent dsde) {}

	  public void dragExit(DragSourceEvent dse) {}

	  public void dragOver(DragSourceDragEvent dsde) {}
	  
	  public void dragDropEnd(DragSourceDropEvent dsde) {
	    if (dsde.getDropSuccess()) {
	      System.out.println("Succeeded");
	      //"repaint" items
	    }
	  }
	  public void dropActionChanged(DragSourceDragEvent dsde) {
	  }
	}

	private class ListDrop extends TransferHandler {
		private static final long serialVersionUID = 1142577497098275366L;
	public ListDrop(DragDropList<E> list) {}
	  public boolean canImport(TransferSupport support) {/*
	    if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
	      return false;
	    }
	    JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
	    if (dl.getIndex() == -1) {
	      return false;
	    } else {
	      return true;
	    }*/
		 return true;
	  }
	  public boolean importData(TransferSupport support) {
	    if (!canImport(support)) {
	      return false;
	    }
	    Transferable transferable = support.getTransferable();
	    String indexString;
	    try {
	      indexString = (String) transferable.getTransferData(DataFlavor.stringFlavor);
	    } catch (Exception e) {
	      return false;
	    }
	    int index = Integer.parseInt(indexString);
	    JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
	    int dropTargetIndex = dl.getIndex();
	    System.out.println(dropTargetIndex + " : ");
	    System.out.println("inserted");
	    System.out.println(index);
	    return true;
	  }
	}
}
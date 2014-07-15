package de.heblich;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.openni.Device;
import org.openni.DeviceInfo;
import org.openni.OpenNI;

import com.primesense.nite.NiTE;

import de.heblich.dummy.TestPanel;
import de.heblich.gui.Dummy2;
import de.heblich.gui.MyPanel;
import de.heblich.kinect.KinectReader;
import de.heblich.kinect.swing.GDepthImage;
import de.heblich.kinect.swing.GFrontImage;
import de.heblich.kinect.swing.GSideImage;
import de.heblich.kinect.swing.container.GKinectContainer;
import de.heblich.kinect.swing.container.GTree;
import de.heblich.logic.Element;
import de.heblich.logic.ReadElements;
import de.heblich.logic.controller.Speaker;

public class Test {

	public static void main(String[] args) {
		OpenNI.initialize();
        NiTE.initialize();


        List<DeviceInfo> devicesInfo = OpenNI.enumerateDevices();
        if (devicesInfo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No device is connected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Device device = Device.open(devicesInfo.get(0).getUri());
		final JFrame f = new JFrame();
		final KinectReader kr = new KinectReader();
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//f.setLayout(new GridLayout(2, 2));
		f.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				kr.endAll();
			}
		});
		
//		String path = new String("C://Dokumente und Einstellungen//mmg//workspace//ReadXml//res");
		File file = new File(Conf.PATH, "E-Bike_v4.xml");
		
		System.out.println(file.getAbsolutePath() + " "+file.exists());
		
		final Element root = ReadElements.readElements(file);
		Speaker.getInstance().SetRoot(root);
		JPanel rightSide = new JPanel(new GridLayout(3, 1));
		GDepthImage di = new GDepthImage();
		GFrontImage fi = new GFrontImage();
		GSideImage si = new GSideImage();
		
		//GTree tree = new GTree(root);
		//final GKinectScrollPane gksp = new GKinectScrollPane(f, tree);
		//final TestPanel ki = new TestPanel(f);
		f.setSize(1067, 600);
		kr.register(di);
		kr.register(fi);
		kr.register(si);
		//kr.register(ki);
		rightSide.add(di);
		rightSide.add(fi);
		rightSide.add(si);
		final MyPanel d2 = new MyPanel(f,root);
		kr.register(d2);
		//JPanel bla = new JPanel(new BorderLayout());
		f.add(rightSide, BorderLayout.EAST);
		f.add(d2, BorderLayout.CENTER);
		//f.add(new JScrollPane(tree), BorderLayout.WEST);
		//f.add(bla);
		//f.add(ki);
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				f.setVisible(true);
				f.validate();
				d2.finishAdd();
				//gksp.finishAdd();
			}
		});
	}
}

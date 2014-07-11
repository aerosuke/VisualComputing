package de.heblich.dummy;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.openni.Device;
import org.openni.DeviceInfo;
import org.openni.OpenNI;

import com.primesense.nite.NiTE;

public class First {

	static JFrame f = new JFrame();
	static DummyImage fi;
	
	public static void main(String[] args) {
		OpenNI.initialize();
        NiTE.initialize();


        List<DeviceInfo> devicesInfo = OpenNI.enumerateDevices();
        if (devicesInfo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No device is connected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Device device = Device.open(devicesInfo.get(0).getUri());
        fi = new DummyImage(device);
        
		f.add(fi);
		f.setSize(800, 600);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				f.setVisible(true);
			}
		});
		/*
		for (;;)
		{
			 fi.repaint();
		     try
		     {
		          Thread.sleep(20); // sleep for 20 milliseconds
		     }
		     catch (Exception e)
		     {
		          e.printStackTrace();
		     }
		}*/
	}
}

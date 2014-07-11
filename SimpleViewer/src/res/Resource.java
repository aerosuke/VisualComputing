package res;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public class Resource {

	public static Image getImage(String name){
		URL url = Resource.class.getResource(name);
		Toolkit tool = Toolkit.getDefaultToolkit();
		return tool.getImage(url);
	}
}

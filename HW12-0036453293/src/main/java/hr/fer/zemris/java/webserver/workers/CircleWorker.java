package hr.fer.zemris.java.webserver.workers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class representing a web worker drawing a circle.
 * 
 * @author MarkoB
 * @version 1.0
 */
public class CircleWorker implements IWebWorker {
	
	/**
	 * @see hr.fer.zemris.java.webserver.IWebWorker#processRequest(hr.fer.zemris.java.webserver.RequestContext)
	 */
	@Override
	public void processRequest(RequestContext context) {
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();
		g2d.drawOval(0, 0, 200, 200);
		g2d.dispose();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(bim, "png", bos);
			context.setMimeType("image/png");
			context.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

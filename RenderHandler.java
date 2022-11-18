import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;


public class RenderHandler {

	private final BufferedImage view;
	private final int[] pixels;

	public RenderHandler(int width, int height) 
	{
		this.view = new BufferedImage(width, height, 1);
		
		pixels = this.getImagePixels(this.view);
	}

	private int[] getImagePixels(BufferedImage image)
	{
		return ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}

	public void render(Graphics graphics) 
	{
		graphics.drawImage(this.view, 0, 0, this.view.getWidth(), this.view.getHeight(), null);
	}

	public void renderImage(BufferedImage image, int xpos, int ypos) 
	{
		int[] imagePixels = this.getImagePixels(image);

		for(int y = 0; y < image.getHeight(); y++)
		{
			for(int x = 0; x < image.getWidth(); x++)
			{
				this.pixels[(x + xpos) + (y + ypos) * this.view.getWidth()] = 
				imagePixels[x + y * image.getWidth()];
			}
		}
	}
}
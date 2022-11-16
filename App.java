import javax.swing.JFrame;

import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.Color;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.FileInputStream;

import java.lang.Runnable;
import java.lang.Thread;


public class App extends JFrame implements Runnable {

    private final Canvas canvas = new Canvas();
    
    private final RenderHandler renderer;
    private final BufferedImage testImage;
    
    private Graphics graphics;
    private BufferStrategy buffer;
    
    public App()
    {
        this.setSize(800, 600);
        this.setTitle("Pokemon Mediterranean Sea");

        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        
        this.add(this.canvas);
        
        this.setVisible(true);
        
        this.canvas.createBufferStrategy(3);

        this.renderer = new RenderHandler(this.getWidth(), this.getHeight());
        this.testImage = this.loadImage("tree.png");
    }
    
    private BufferedImage loadImage(String path)
    {
        try
        {
            BufferedImage loadedImage = ImageIO.read(new FileInputStream(path));
            BufferedImage formattedImage = new BufferedImage(
                    loadedImage.getWidth(), loadedImage.getHeight(), 1);
            formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
            return formattedImage;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    private void update() {}
    
    private void render()
    {
        this.buffer = this.canvas.getBufferStrategy();
        this.graphics = this.buffer.getDrawGraphics();
        super.paint(this.graphics);
        
        this.renderer.render(this.graphics);
        this.renderer.renderImage(this.testImage, 0, 0);
        
        this.graphics.dispose();
        this.buffer.show();
    }
    
    public void run()
    {
        final double TIMECONVERSION = 1e9 / 60;
        long now;
        long lastTime = System.nanoTime();
        double delta = 0;
        
        while(true)
        {
            now = System.nanoTime();
            delta += (now - lastTime) / TIMECONVERSION;
        
            while(delta >= 1)
            {
                this.update();
                delta--;
            }
    
            this.render();
            lastTime = now;
        }
    }
    
    public static void main(String[] args) 
    {
        System.out.println("Hello World!");
        new Thread(new App()).start();
    }
}
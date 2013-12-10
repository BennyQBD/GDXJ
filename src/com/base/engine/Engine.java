package com.base.engine;

public class Engine 
{
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "3D Engine";
	public static final double FRAME_CAP = 5000.0;
	
	private static boolean isRunning = false;
	private static Game game;
	private static RenderingEngine renderingEngine;
	private static Renderer renderer;
	
	public static void init(Game game, RenderingEngine renderingEngine, int width, int height, String title, boolean fullscreen)
	{
		renderer = new Renderer();
		
		Engine.game = game;
		Engine.renderingEngine = renderingEngine;
		Window.createWindow(width, height, title);
		renderingEngine.initGraphics();
		renderingEngine.init();
	}
	
	public static void start()
	{
		if(isRunning)
			return;
		
		run();
	}
	
	public static void stop()
	{
		if(!isRunning)
			return;
		
		isRunning = false;
	}
	
	private static void run()
	{
		isRunning = true;
		
		int frames = 0;
		double frameCounter = 0;
		
		final double frameTime = 1.0 / FRAME_CAP;
		
		double lastTime = Time.getTime();
		double unprocessedTime = 0;
		
		while(isRunning)
		{
			boolean render = false;
			
			double startTime = Time.getTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime;
			frameCounter += passedTime;
			
			while(unprocessedTime > frameTime)
			{
				render = true;
				
				unprocessedTime -= frameTime;
				
				if(Window.isCloseRequested())
					stop();
				
				Time.setDelta(frameTime);
				
				Window.update();
				game.input();
				Input.update();
				
				game.update();
				
				if(frameCounter >= 1.0)
				{
					System.out.println(frames + ": " + 1000.0f/frames + "ms");
					frames = 0;
					frameCounter = 0;
				}
			}
			if(render)
			{
				render();
				frames++;
			}
			else
			{
				try 
				{
					Thread.sleep(1);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		cleanUp();
	}
	
	private static void render()
	{
		renderingEngine.clearScreen();
		game.render();
		Window.render();
	}
	
	private static void cleanUp()
	{
		Window.dispose();
	}
	
	public static Game getGame(){return game;}
	public static RenderingEngine getRenderingEngine(){return renderingEngine;}
	public static Renderer getRenderer() {return renderer;}
	
	public static void setGame(Game game){Engine.game = game;}
	public static void setRenderer(RenderingEngine renderer){Engine.renderingEngine = renderer;}
}

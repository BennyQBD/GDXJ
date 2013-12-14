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
	
	public static Game getGame(){return game;}
	public static RenderingEngine getRenderingEngine(){return renderingEngine;}
	public static Renderer getRenderer() {return renderer;}
	
	public static void setGame(Game game){Engine.game = game;}
	public static void setRenderer(RenderingEngine renderer){Engine.renderingEngine = renderer;}
	
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
		
		Time.init();
		
		while(isRunning)
		{
			frameCounter += Time.getDelta();
			
			if(Window.isCloseRequested())
				stop();
				
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
			
			renderingEngine.clearScreen();
			game.render();
			Window.render();
			frames++;
			
			Time.tick();
		}
		
		cleanUp();
	}
	
	private static void cleanUp()
	{
		Window.dispose();
	}
}

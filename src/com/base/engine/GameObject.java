package com.base.engine;

import java.util.ArrayList;

public class GameObject
{
	private Transform transform;
	private ArrayList<Component> components;
	private boolean castShadows;
	private RenderableMesh mesh;
	
	public GameObject(Transform transform)
	{
		this(transform, null);
	}
	
	public GameObject(RenderableMesh mesh)
	{
		this(new Transform(), mesh);
	}
	
	public GameObject(Transform transform, RenderableMesh mesh)
	{
		this.transform = transform;
		this.mesh = mesh;
		components = new ArrayList<Component>();
		castShadows = true;
	}
	
	public void input()
	{
		for(Component component : components)
			component.input();
	}
	
	public void update()
	{
		for(Component component : components)
		{
			component.addUnprocessedTime(Time.getDelta());
			final double updateTime = component.getUpdateDelta();
			
			while(component.getUnprocessedTime() > updateTime)
			{
				component.update();
				component.addUnprocessedTime(-updateTime);
			}
		}
	}
	
	public void render()
	{
		for(Component component : components)
			component.render();
		
		if(mesh != null)
		{
			//mesh.draw(transform.getTransformation(), transform.getProjectedTransformation());
			mesh.draw(transform.calcModel(), transform.getMVP());
		}
	}
	
	public void addComponent(Component component)
	{
		component.setObject(this);
		component.init();
		components.add(component);
	}
	
	public void removeComponent(Component component)
	{
		components.remove(component);
	}
	
	public Transform getTransform()
	{
		return transform;
	}
	
	public RenderableMesh getMesh()
	{
		return mesh;
	}

	public boolean isCastShadows()
	{
		return castShadows;
	}

	public void setCastShadows(boolean castShadows)
	{
		this.castShadows = castShadows;
	}
}

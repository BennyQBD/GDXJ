package com.base.engine;

import java.util.HashMap;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;

public class NewShader 
{
	private static final HashMap<String, NewShader> shaderPrograms = new HashMap<>();
	
	private final int[] shaders;
	private final int program;
	
	public static NewShader get(String shaderName)
	{
		if(shaderPrograms.containsKey(shaderName))
			return shaderPrograms.get(shaderName);
		else
		{
			shaderPrograms.put(shaderName, new NewShader(shaderName));
			return shaderPrograms.get(shaderName);
		}
	}
	
	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		Engine.getRenderer().deleteShaderProgram(program, shaders);
	}
	
	private NewShader(String name)
	{
		shaders = new int[2];
		String shaderText = Shader.loadShader(name + ".glsl");
		
		shaders[0] = (Engine.getRenderer().createVertexShader(shaderText));
		shaders[1] = (Engine.getRenderer().createFragmentShader(shaderText));
		
		program = Engine.getRenderer().createShaderProgram(shaders);
		
		//TODO: Generic way of adding uniforms!
		uniforms = new HashMap<>();
		
		addUniform("MVP");
		//addUniform("color");
	}
	
	public void bind()
	{
		Engine.getRenderer().bindShaderProgram(program);
	}
	
	public void validate()
	{
		Engine.getRenderer().validateShaderProgram(program);
	}
	
	public void update(Transform transform, Material material)
	{
		//TODO: Actual generic update method
		
		if(material.getDiffuseTexture() != null)
			material.getDiffuseTexture().bind();
		else
			Texture.unbind();
		
		transform.calcModel();
		setUniform("MVP", transform.getMVP());
		//setUniform("color", material.getColor());
	}
	
	//TODO: This code won't be needed in final uniform system!
	private HashMap<String, Integer> uniforms;
	
	public void addUniform(String uniform)
	{
		int uniformLocation = glGetUniformLocation(program, uniform);
		
		if(uniformLocation == 0xFFFFFFFF)
		{
			System.err.println("Error: Could not find uniform: " + uniform);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		uniforms.put(uniform, uniformLocation);
	}
	
	public void setUniform(String uniformName, Vector3f value)
	{
		glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	public void setUniform(String uniformName, Matrix4f value)
	{
		glUniformMatrix4(uniforms.get(uniformName), true, Util.createFlippedBuffer(value));
	}
}

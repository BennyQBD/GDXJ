package com.base.engine;

import java.util.HashMap;

public class NewShader 
{
	private static final HashMap<String, NewShader> shaderPrograms = new HashMap<>();
	
	private final int[] shaders;
	private final UniformData[] uniforms;
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
		uniforms = Engine.getRenderer().createShaderUniforms(shaderText, program);
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
		for (UniformData uniform : uniforms) 
		{
			if(uniform.getName().substring(0, 4).equals("GDX_"))
			{
				String name = uniform.getName().substring(4);
				switch (name) 
				{
					case "MVP":
						Engine.getRenderer().setUniformMatrix4f(uniform.getLocation(), transform.calcMVP());
						break;
					case "Transform":
						Engine.getRenderer().setUniformMatrix4f(uniform.getLocation(), transform.calcModel());
						break;
				}
			}
			else if(!Engine.getRenderingEngine().updateUniform(uniform, transform, material)) 
			{
				switch (uniform.getType()) 
				{
					case "sampler2D":
						material.getTexture(uniform.getName()).bind();
						break;
					case "vec3":
						Engine.getRenderer().setUniformVector3f(uniform.getLocation(), material.getVector(uniform.getName()));
						break;
					case "float":
						Engine.getRenderer().setUniformFloat(uniform.getLocation(), material.getParameter(uniform.getName()));
						break;
				}
			}
		}
	}
}

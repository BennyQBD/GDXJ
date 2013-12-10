package com.base.engine;

public class RenderableMesh
{
	private Mesh mesh;
	private Material material;
	private Shader shader;
	
	public RenderableMesh(Mesh mesh, Material material)
	{
		this.mesh = mesh;
		this.material = material;
		
		Shader shader = null;
		
		if(material.getSpecularIntensity() != 0 || material.getSpecularPower() != 0)
			shader = PhongShader.getInstance();
		else
			shader = BasicShader.getInstance();
		
		this.shader = shader;
	}
	
	public RenderableMesh(Mesh mesh, Material material, Shader shader)
	{
		this.mesh = mesh;
		this.material = material;
		this.shader = shader;
	}
	
	public void draw(Matrix4f transformation, Matrix4f projection)
	{
		shader.bind();
		shader.updateUniforms(transformation, projection, material);
		mesh.draw();
	}

	public Mesh getMesh()
	{
		return mesh;
	}

	public Material getMaterial()
	{
		return material;
	}

	public Shader getShader()
	{
		return shader;
	}

	public void setMesh(Mesh mesh)
	{
		this.mesh = mesh;
	}

	public void setMaterial(Material material)
	{
		this.material = material;
	}

	public void setShader(Shader shader)
	{
		this.shader = shader;
	}
}

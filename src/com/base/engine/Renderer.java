package com.base.engine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

public class Renderer 
{
	public void init()
	{
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		
		glEnable(GL_DEPTH_CLAMP);
		glEnable(GL_TEXTURE_2D);
	}
	
	public void setClearColor(float r, float g, float b, float a)
	{
		glClearColor(r,g,b,a);
	}
	
    public void clearScreenAndDepth()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
    public void clearScreen()
	{
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
    public void clearDepth()
	{
		glClear(GL_DEPTH_BUFFER_BIT);
	}

    public void setDepthTest(boolean value)
	{
		if(value)
			glEnable(GL_DEPTH_TEST);
        else
			glDisable(GL_DEPTH_TEST);
	}
	
    public void setDepthWrite(boolean value)
	{
		if(value)
			glDepthMask(true);
        else
			glDepthMask(false);
	}

    public void setBlending(boolean value)
	{
		if(value)
        {
			glEnable(GL_BLEND);
			glBlendFunc(GL_ONE,GL_ONE);
        }
        else
        {
			glDisable(GL_BLEND);
        }
	}
	
    public void setDepthFunc(boolean value)
	{
		if(value)
			glDepthFunc(GL_EQUAL);
        else
			glDepthFunc(GL_LESS);
	}

    public int createVertexBuffer(FloatBuffer data, boolean isStatic)
	{
		int buffer = glGenBuffers();
		int hint = GL_STATIC_DRAW;

		if(!isStatic)
			hint = GL_DYNAMIC_DRAW;//TODO: Make sure this hint is appropriate!

		glBindBuffer(GL_ARRAY_BUFFER, buffer);
		glBufferData(GL_ARRAY_BUFFER, data, hint);
		
		return buffer;
	}
	
    public int createIndexBuffer(IntBuffer data, boolean isStatic)
	{
		int buffer = glGenBuffers();
		int hint = GL_STATIC_DRAW;

		if(!isStatic)
			hint = GL_DYNAMIC_DRAW;//TODO: Make sure this hint is appropriate!

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffer);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, hint);

		return buffer;
	}
	
    public void drawTriangles(int vertexBuffer, int indexBuffer, int nIndices)
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glEnableVertexAttribArray(3);
		
		glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);
		glVertexAttribPointer(3, 3, GL_FLOAT, false, Vertex.SIZE * 4, 32);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
		glDrawElements(GL_TRIANGLES, nIndices, GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
	}
	
    public void deleteBuffer(int buffer)
	{
		glDeleteBuffers(buffer);
	}
        
//    public int createVertexShader(String text)
//	{
//		
//	}
//	
//    public int CreateFragmentShader(const std::string& text);
//    public int CreateShaderProgram(unsigned int* shaders, int numShaders);
//    public std::vector<UniformData> CreateShaderUniforms(const std::string& shaderText, unsigned int shaderProgram);
//    public void ValidateShaderProgram(unsigned int program);
//    public void BindShaderProgram(unsigned int program);
//    public void DeleteShaderProgram(unsigned int program, unsigned int* shaders, int numShaders);
//    
//    public void SetUniformInt(unsigned int uniformLocation, int value);
//    public void SetUniformFloat(unsigned int uniformLocation, float value);
//    public void SetUniformVector3f(unsigned int uniformLocation, const Vector3f& value);
//    public void SetUniformMatrix4f(unsigned int uniformLocation, const Matrix4f& value);
//    
//    public int CreateTexture(int width, int height, unsigned char* data, bool linearFiltering = true, bool repeatTexture = true);
//    public void BindTexture(unsigned int texture, int unit);
//    public void DeleteTexture(unsigned int texture);
}

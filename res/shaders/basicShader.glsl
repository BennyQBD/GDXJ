#version 120

attribute vec3 position;
attribute vec2 texCoord;

varying vec2 FS_TexCoord;

uniform mat4 GDX_MVP;
uniform sampler2D diffuse;

void VSmain()
{
	gl_Position = GDX_MVP * vec4(position, 1.0);
	FS_TexCoord = texCoord;
}

void FSmain()
{
	gl_FragColor = texture2D(diffuse, FS_TexCoord.xy);
}

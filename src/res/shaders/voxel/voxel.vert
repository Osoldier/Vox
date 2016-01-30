#version 330 core

layout (location = 0) in vec3 vertex;
layout (location = 1) in vec3 normal;
layout (location = 2) in vec3 color;

out vData
{
  vec3 normal;
	vec3 color;
	vec3 vertToLight;
} vertexData;

uniform mat4 ml_mat, vw_mat, pr_mat;
uniform vec3 lightPos;

void main()
{
  vec4 position = vec4(vertex, 1.0) * ml_mat;
  gl_Position = position * vw_mat* pr_mat;
  vertexData.color = color;
  vertexData.vertToLight = lightPos - position.xyz;
  vertexData.normal = (vec4(normal, 1.0) * ml_mat).xyz;
}

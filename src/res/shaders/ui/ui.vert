#version 330 core

layout (location = 0) in vec2 vertex;
layout (location = 2) in vec2 inTexCoords;

out vec2 texCoords;

uniform mat4 ml_mat, pr_mat;

void main() {
	texCoords = inTexCoords;
	gl_Position = vec4(vertex.x, vertex.y, 0, 1) * ml_mat * pr_mat;
}
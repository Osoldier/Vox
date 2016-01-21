#version 330 core

out vec4 color;

in FragInfo {
  vec2 texCoord;
} fragInfo;

void main()
{
  color = vec4(1.0);
}

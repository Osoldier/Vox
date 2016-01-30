#version 330 core

layout (triangles) in;
layout (triangle_strip, max_vertices = 3) out;

in vData
{
	vec3 normal;
	vec3 color;
	vec3 vertToLight;
} vertices[];

out fData
{
	vec3 normal;
	vec3 color;
	vec3 vertToLight;
} frag;

void main() {
	vec3 V0 = vertices[0].normal - vertices[1].normal;
	vec3 V1 = vertices[2].normal - vertices[1].normal;
	vec3 faceNormal = cross(V1, V0);
	for(int i = 0;i < gl_in.length();i++)
  {
        frag.normal = faceNormal;
				frag.vertToLight = vertices[i].vertToLight;
        frag.color = vertices[i].color;
				gl_Position = gl_in[i].gl_Position;
				EmitVertex();
  }
  EndPrimitive();
}

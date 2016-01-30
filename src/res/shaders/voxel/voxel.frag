#version 330 core

out vec4 color;

in fData
{
  vec3 normal;
  vec3 color;
  vec3 vertToLight;
} fragInfo;

void main()
{
  float cosine = dot(normalize(fragInfo.normal), normalize(fragInfo.vertToLight));
  color = vec4(fragInfo.color,1.0);
  color *= max(cosine, 0.2);
}

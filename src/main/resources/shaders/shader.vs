#version 330

layout (location =0) in vec3 position;
layout (location =1) in vec3 inColour;

out vec3 exColour;

uniform mat4 projection;
uniform mat4 transformation;

void main() {
	gl_Position = projection * transformation * vec4(position, 1);
	exColour = inColour;
}
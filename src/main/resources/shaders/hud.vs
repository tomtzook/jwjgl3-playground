#version 330

layout (location =0) in vec3 position;
layout (location =1) in vec2 textCoord;

out vec2 outTextCoord;

uniform mat4 projection;

void main() {
	gl_Position = projection * vec4(position, 1);
	outTextCoord = textCoord;
}
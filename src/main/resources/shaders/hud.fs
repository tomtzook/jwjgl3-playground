#version 330

in vec2 outTextCoord;
out vec4 fragColor;

uniform sampler2D textureSampler;
uniform vec3 color;

void main() {
    fragColor = vec4(color, 1) * texture2D(textureSampler, outTextCoord);
}
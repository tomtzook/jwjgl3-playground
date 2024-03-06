#version 330

in vec2 outTextCoord;
out vec4 fragColor;

uniform sampler2D textureSampler;
uniform vec3 color;
uniform int useColor;

void main() {
    if(useColor == 1) {
        fragColor = vec4(color, 1);
    } else {
	    fragColor = texture2D(textureSampler, outTextCoord);
	}
}
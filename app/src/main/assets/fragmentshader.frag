#version 300 es
precision highp float;

in vec4 v_shaded_color;
in vec3 v_pos;
layout (location = 0) out vec4 final_color;

void main(void) {
   	if(v_pos.z > 49.0)
   		discard;

   final_color = v_shaded_color;
}

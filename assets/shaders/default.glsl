// Shaders decide how objects look. GLSL is the language used to write them.
/*
* Shaders are written in the C-like language GLSL.
* GLSL is tailored for use with graphics and contains useful features
* specifically targeted at vector and matrix manipulation.
*/

// Custom engine marker: indicates the start of the vertex shader section.
// Not a real GLSL directive — parsed by the engine before compilation.
#type Vertex

//Use OpenGL Shading Language version 3.30.
#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColor;

out vec4 fColor;

void main()
{
    fColor = aColor;
    gl_Position = vec4(aPos, 1.0);
}

#type fragment

#versio 330 core

in vec4 fColor;

out vec4 color;

void main()
{
    color = fColor;
}

package VoidCraft;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class LevelEditorScene extends Scene {

    private String vertexShaderSrc = "#version 330 core\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "layout (location = 1) in vec4 aColor;\n" +
            "\n" +
            "out vec4 fColor;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    fColor = aColor;\n" +
            "    gl_Position = vec4(aPos, 1.0);\n" +
            "}";
    private String fragmentShaderSrc = "#version 330 core\n" +
            "\n" +
            "in vec4 fColor;\n" +
            "\n" +
            "out vec4 color;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    color = fColor;\n" +
            "}\n";

    private int vertexId , fragmentId , shaderProgram;

    private float[] vertexAray = {
        //position            //color
        0.5f, -0.5f, 0.0f,    1.0f,0.0f,0.0f,1.0f, //Bottom right  0
        -0.5f, 0.5f, 0.0f,    0.0f, 1.0f,0.0f,1.0f, //Top left     1
        0.5f,0.5f,0.0f,       0.0f, 0.0f,1.0f,1.0f, //Top right    2
        -0.5f,-0.5f,0.0f,     1.0f, 1.0f,0.0f,1.0f //Bottom left   3
    };

    //Important: Must be in counter-clockwise order
    private int[] elementArray = {
        /*
                X           X


                X            X
         */

        2,1,0,  //Top right Triangle
        0,1,3  //bottom left triangle
    };

    private int vaoId , vboId , eboId;

    public LevelEditorScene()
    {


    }

    @Override
    public void init() {
        // ===========================================================
        // Compile and Link Shaders
        // ===========================================================

        //First, load and compile the vertex shader
        vertexId = glCreateShader(GL_VERTEX_SHADER);

        //Pass the shader source to GPU
        glShaderSource(vertexId, vertexShaderSrc);
        glCompileShader(vertexId);

        //Check for errors in compilation process
        int success = glGetShaderi(vertexId , GL_COMPILE_STATUS);

        if (success == GL_FALSE) {
            int len = glGetShaderi(vertexId , GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsi'\n\tVertex Shader compilation failed.");
            System.out.println(glGetShaderInfoLog(vertexId, len));
            assert false : " ";
        }


        //Second, load and compile the fragment shader
        fragmentId = glCreateShader(GL_FRAGMENT_SHADER);

        //Pass the shader source to GPU
        glShaderSource(fragmentId, fragmentShaderSrc);
        glCompileShader(fragmentId);

        //Check for errors in compilation process
        success = glGetShaderi(fragmentId , GL_COMPILE_STATUS);

        if (success == GL_FALSE) {
            int len = glGetShaderi(fragmentId , GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsi'\n\tFragment Shader compilation failed.");
            System.out.println(glGetShaderInfoLog(fragmentId, len));
            assert false : " ";
        }


        //Link shaders and check for errors
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexId);
        glAttachShader(shaderProgram, fragmentId);
        glLinkProgram(shaderProgram);

        success = glGetProgrami(shaderProgram, GL_LINK_STATUS);

        if (success == GL_FALSE) {
            int len = glGetShaderi(shaderProgram , GL_INFO_LOG_LENGTH);
            System.out.println("Error: 'defaultShader.glsi'\n\tLinking of shaders failed.");
            System.out.println(glGetShaderInfoLog(shaderProgram, len));
            assert false : " ";
        }


        // =================================================
        // Generate VAO, VBO and EBO object and send to GPU
        // ==================================================
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        //create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexAray.length);
        vertexBuffer.put(vertexAray).flip();

        // Create VBO upload the vertex buffer
        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER , vboId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();

        eboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        //Add the vertex attribute pointers
        int positionSize = 3;
        int colorSize = 4;
        int floatSizeBytes = 4;
        int vertexSiZeInBytes = (positionSize + colorSize) * floatSizeBytes;
        glVertexAttribPointer(0 , positionSize, GL_FLOAT, false, vertexSiZeInBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize , GL_FLOAT, false, vertexSiZeInBytes, positionSize);
        glEnableVertexAttribArray(1);
    }

    @Override
    public void update(float dt) {
        // Bind shader Program
        glUseProgram(shaderProgram);

        //Bind the VAO that we are using
        glBindVertexArray(vaoId);

        // Enable vertex attribute pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        //Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);
        glUseProgram(0);
    }


}

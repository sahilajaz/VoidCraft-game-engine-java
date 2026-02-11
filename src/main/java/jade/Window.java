package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * This is a singleton class.
 *
 * <ul>
 *     <li>Only ONE object can exist.</li>
 *     <li>Constructor is private.</li>
 * </ul>
 */

public class Window {
    private  int width;
    private  int height;
    private  String title;
    private  long  glfwWindow;

    // 1. Create a static variable to hold the single object
    private static Window window = null;

    // 2. Make constructor private so no one can create object using 'new'
    private Window() {
        this.width = 1000;
        this.height = 600;
        this.title = "Mario";
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();
    }

    public void init() {
        //Setup error callback
        GLFWErrorCallback.createPrint(System.err).set();

        //Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        //Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE , GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);


        //Create the window
        //Returns where the window is in our memory space.
        glfwWindow = glfwCreateWindow(this.width , this.height, this.title, NULL , NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create window");
        }

        //Make OPENGL context current
        //All OpenGL commands from now on should affect THIS window
        glfwMakeContextCurrent(glfwWindow);

        //Enable V-Sync = Vertical Synchronization
        //Synchronize your game’s frame rate with your monitor’s refresh rate.
        //Wait for 1 vertical refresh before swapping buffers.
        //If monitor = 60 Hz, Your game will run at max 60 FPS
        glfwSwapInterval(1);

        //Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
    }

    public void loop() {
        while(!glfwWindowShouldClose(glfwWindow)) {
            //Poll events
            /*
             *The window is connected to the operating system,
             * which sends events such as key presses, key releases,
             * mouse movement, mouse clicks, window resizing, and window closing.
             */
            glfwPollEvents();

            //Sets background color
            glClearColor(1.0f , 0.0f , 0.0f , 1.0f);

            //clears the screen using the color you just set
            glClear(GL_COLOR_BUFFER_BIT);

            // Swaps the back buffer with the front buffer, displaying the rendered frame on the screen
            glfwSwapBuffers(glfwWindow);

        }
    }
}

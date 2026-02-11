package jade;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private static MouseListener instance = null;
    private double scrollX;
    private double scrollY;
    private double XPos;
    private double YPos;
    private double lastX;
    private double lastY;
    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging;

    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.XPos = 0.0;
        this.YPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    public static MouseListener get() {
        if (MouseListener.instance == null) {
            MouseListener.instance = new MouseListener();
        }
        return MouseListener.instance;
    }

    public static void mousePosCallback(long window, double XPos , double YPos) {
        get().lastX = get().XPos;
        get().lastY = get().YPos;
        get().XPos = XPos;
        get().YPos = YPos;
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];
    }

    public static void mouseButtonCallback(long window, int button , int action, int mods) {
        if (action == GLFW_PRESS) {
            if (button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            if (button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = false;
                get().isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double XOffset , double YOffset) {
        get().scrollX = XOffset;
        get().scrollY = YOffset;
    }

    public static void endFrame() {
        get().scrollY = 0;
        get().scrollY = 0;
        get().lastX = get().XPos;
        get().lastY = get().YPos;
    }

    public static float getX() {
        return (float) get().XPos;
    }

    public static float getY() {
        return (float) get().YPos;
    }

    public static float getDX() {
        return (float) (get().lastX - get().XPos);
    }

    public static float getDY() {
        return (float) (get().lastY - get().YPos);
    }

    public static float getScrollX() {
        return (float) get().scrollX;
    }

    public static float getScrollY() {
        return (float) get().scrollY;
    }

    public static boolean isDragging() {
        return get().isDragging;
    }

    public static boolean mouseButtonDown(int button) {
        if (button < get().mouseButtonPressed.length) {
            return get().mouseButtonPressed[button];
        } else  {
            return false;
        }
    }
}

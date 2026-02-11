package util;

/**
 * A frame is a single rendered image in a sequence of images that make up an animation
 * or a game scene. Games and simulations display many frames per second (FPS) to create
 * the illusion of smooth motion.
 * <p>
 * The deltaTime variable represents the time elapsed between the current frame and the
 * previous frame, in seconds. This is commonly used to make game logic, movement, and
 * animations frame-rate independent. For example, multiplying movement speed by deltaTime
 * ensures consistent motion regardless of frame rate variations.
 * </p>
 */
public class Time {
    public static float timeStarted = System.nanoTime();

    public static float getTime() {
        return (float) ((System.nanoTime() - timeStarted) * 1E-9);
    }
}

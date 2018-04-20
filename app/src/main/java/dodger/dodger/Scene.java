package dodger.dodger;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * TODO: Add a class header comment!
 */
public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void receiveTouch(MotionEvent event);
}

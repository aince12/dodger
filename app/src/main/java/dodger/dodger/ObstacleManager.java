package dodger.dodger;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * TODO: Add a class header comment!
 */
public class ObstacleManager {
//heigher index = lower on screen - higher y value

    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long startTime;
    private long initTime;

    private int score;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime = System.currentTimeMillis();


        obstacles = new ArrayList<>();

        populateObstacles();
    }



    public boolean playerCollide(RectPlayer player){
        for(Obstacle o: obstacles)
            if(o.playerCollide(player))
                return true;

        return false;
    }

    private void populateObstacles() {
        int currY = -5 * Constants.SCREEN_HEIGHT/4;

        while(currY<0){
            int xStart =(int)( Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight,color,xStart,currY,playerGap));

            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update(){

        if(startTime<Constants.INIT_TIME)
            startTime = Constants.INIT_TIME;

        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime =  System.currentTimeMillis();
        float speed =(float) (Math.sqrt(1 + (startTime - initTime)/ 1000.0)) * Constants.SCREEN_HEIGHT / (10000.0f);

        for(Obstacle o: obstacles){
            o.incrementY(speed * elapsedTime);
        }

        if(obstacles.get(obstacles.size()-1).getRectangle().top > Constants.SCREEN_HEIGHT){

            int xStart =(int)( Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0,new Obstacle(obstacleHeight,color,xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap ,playerGap));

            obstacles.remove(obstacles.size()-1);
            score++;

        }

    }
    public void draw(Canvas canvas){

        for(Obstacle o: obstacles){
            o.draw(canvas);
        }

        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);

        canvas.drawText(""+score, 50, 50 + paint.descent()- paint.ascent(),paint);

    }
}

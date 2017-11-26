package com.rootonchair.phv;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;

public class CoordinateCanvas extends Canvas {
    private static final int SPACING=20
                            ,LINE_WIDTH=5
                            ,POINT_RADIUS=2;
    private final double canvasWidth;
    private final double canvasHeight;
    public CoordinateCanvas(double width, double height){
        super(width,height);
        this.canvasWidth=width;
        this.canvasHeight=height;
        drawBackground();
    }
    
    private void drawBackground(){
        GraphicsContext gc=getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
        gc.setStroke(Color.WHITE);
        double halfWidth=canvasWidth/2d;
        double halfHeight=canvasHeight/2d;
        gc.strokeLine(halfWidth,0,halfWidth,getHeight());
        gc.strokeLine(0, halfHeight, getWidth(),halfHeight );
        double offsetX=canvasWidth%(SPACING*20);
        double offsetY=canvasHeight%(SPACING*20);
        int number=-10;
        for(int i=0;i<=20;i++){
            if(i==10){
                number++;
                continue;
            }
            double posX=offsetX/2d+SPACING*i;
            double posY=offsetY/2d+SPACING*i;
            gc.strokeLine(posX,halfHeight-LINE_WIDTH/2d,posX,halfHeight+LINE_WIDTH/2d);
            gc.strokeLine(halfWidth-LINE_WIDTH/2d,posY,halfWidth+LINE_WIDTH/2d,posY);
            
            String strNumber=String.valueOf(number);
            
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.TOP);
            gc.strokeText(strNumber, posX, halfHeight);
            
            gc.setTextAlign(TextAlignment.LEFT);
            gc.setTextBaseline(VPos.CENTER);
            gc.strokeText(strNumber, halfWidth+LINE_WIDTH/2d, canvasHeight-posY);
            number++;
        }
        
       gc.setTextAlign(TextAlignment.LEFT);
       gc.setTextBaseline(VPos.TOP);
       gc.strokeText("0", halfWidth+LINE_WIDTH/2d, halfHeight);
    }
    
    public void drawDot(double x, double y){
        GraphicsContext gc= getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillOval(canvasWidth/2d+x*SPACING-POINT_RADIUS/2d, canvasHeight/2d+y*(-SPACING)-POINT_RADIUS/2d, POINT_RADIUS, POINT_RADIUS);
    }
    
    public void reset(){
        drawBackground();
    }
}

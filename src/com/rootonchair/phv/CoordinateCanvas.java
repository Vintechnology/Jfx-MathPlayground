package com.rootonchair.phv;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class CoordinateCanvas extends Canvas {//change to StackPane with black background
    private static final int SPACING=20
                            ,LINE_WIDTH=5;
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
        gc.setStroke(Color.BLACK);
        double halfWidth=canvasWidth/2f;
        double halfHeight=canvasHeight/2f;
        //gc.strokeLine(-halfWidth,0,halfWidth,0);
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
            double posX=offsetX/2f+SPACING*i;
            double posY=offsetY/2f+SPACING*i;
            gc.strokeLine(posX,halfHeight-LINE_WIDTH/2f,posX,halfHeight+LINE_WIDTH/2F);
            gc.strokeLine(halfWidth-LINE_WIDTH/2f,posY,halfWidth+LINE_WIDTH/2f,posY);
            
            String strNumber=String.valueOf(number);
            
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.TOP);
            gc.strokeText(strNumber, posX, halfHeight);
            
            gc.setTextAlign(TextAlignment.LEFT);
            gc.setTextBaseline(VPos.CENTER);
            gc.strokeText(strNumber, halfWidth+LINE_WIDTH/2f, canvasHeight-posY);
            number++;
        }
        
       gc.setTextAlign(TextAlignment.LEFT);
       gc.setTextBaseline(VPos.TOP);
       gc.strokeText("0", halfWidth+LINE_WIDTH/2f, halfHeight);
    }
}

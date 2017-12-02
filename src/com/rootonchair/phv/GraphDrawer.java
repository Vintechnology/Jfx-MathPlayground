/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rootonchair.phv;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineJoin;

/**
 * Class that handle all graph drawing action
 *
 */
public class GraphDrawer {
    private static final int POSITIVE_INFINITY=100
                                ,NEGATIVE_INFINITY=-100;
    private final EquationInterpret interpreter;
    private final CoordinateCanvas canvas;
    private boolean dirty,hasAsymtotic;
    
    public GraphDrawer(){
        canvas=new CoordinateCanvas(420,420);
        interpreter=new EquationInterpret("0","x",0);
        dirty=false;
        hasAsymtotic=false;
    }
    
    public CoordinateCanvas getCanvas(){
        return canvas;
    }
    
    public void setEquation(String newEquation){
        if(dirty){
            canvas.reset();
            dirty=false;
        }
        interpreter.setSource(newEquation);
    }
    
    public void drawGraph(){
        if(dirty){
            canvas.reset();
        }
        GraphicsContext gc=canvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.setLineJoin(StrokeLineJoin.ROUND);
        boolean first=true;
        for(int i=NEGATIVE_INFINITY;i<=POSITIVE_INFINITY;i++){
            double x=i/10d;
            double y=interpreter.applyVariable(x);
            if(Double.isInfinite(y)){
                y=(interpreter.isInPositive()?POSITIVE_INFINITY+20:NEGATIVE_INFINITY-20)/10d;
                hasAsymtotic=true;
            }else if(hasAsymtotic){
                double moveToPosition=(interpreter.isInPositive()?POSITIVE_INFINITY+20:NEGATIVE_INFINITY-20)/10d;
                gc.moveTo(canvas.getScaledX(x), canvas.getScaledY(moveToPosition));
                hasAsymtotic=false;
            }
            if(first){
                gc.beginPath();
                gc.moveTo(canvas.getScaledX(x), canvas.getScaledY(y));
                first=false;
            }
            else{
                gc.lineTo(canvas.getScaledX(x), canvas.getScaledY(y));
            }
            gc.stroke();
            
        }
        dirty=true;
    }
    
    public void erase(){
        canvas.reset();
        dirty=false;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rootonchair.phv;

/**
 * Class that handle all graph drawing action
 *
 */
public class GraphDrawer {
    private static final int POSITIVE_INFINITY=100
                                ,NEGATIVE_INFINITY=-100;
    private final EquationInterpret interpreter;
    private final CoordinateCanvas canvas;
    private boolean dirty;
    
    public GraphDrawer(){
        canvas=new CoordinateCanvas(420,420);
        interpreter=new EquationInterpret("0","x",0);
        dirty=false;
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
        for(int i=NEGATIVE_INFINITY;i<=POSITIVE_INFINITY;i++){
            double x=i/10d;
            double y=interpreter.applyVariable(x);
            //todo: hadle Asymtotic
            canvas.drawDot(x, y);
        }
        dirty=true;
    }
}
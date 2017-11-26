/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rootonchair.phv;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class MathPlayground extends Application {
    private EquationInterpret interpret;
    
    @Override
    public void start(Stage primaryStage) {
        interpret=new EquationInterpret("0","x",0);
        
        StackPane canvasRoot=new StackPane();
        final CoordinateCanvas canvas=new CoordinateCanvas(420,420);
        canvasRoot.getChildren().add(canvas);
        
        final TextField equationField=new TextField();
        Text topText=new Text("Welcome to Math Playground");
        topText.setFont(Font.font("monospace",FontWeight.MEDIUM,FontPosture.REGULAR,20));
        Text equationText=new Text("Equation:");
        
        HBox buttonPane=new HBox();
        buttonPane.setAlignment(Pos.CENTER_RIGHT);
        buttonPane.setSpacing(10);
        Button okButton=new Button("OK");
        okButton.setOnAction((ActionEvent event)->{
            String src=equationField.getText();
            interpret.setSource(src);
            for(int i=-100;i<=100;i++){
                double x= i/10d;
                double y=interpret.applyVariable(x);
                
                canvas.drawDot(x, y);
            }
        });
        Button resetButton=new Button("Reset");
        resetButton.setOnAction((ActionEvent event)->{
            canvas.reset();
        });
        buttonPane.getChildren().addAll(okButton,resetButton);
             
        GridPane controlPane=new GridPane();
        controlPane.setAlignment(Pos.CENTER);
        controlPane.setHgap(5);
        controlPane.setVgap(5);
        controlPane.add(equationText, 0, 0);
        controlPane.add(equationField,1,0);
        controlPane.add(buttonPane, 1, 2);
        
        VBox controlRoot=new VBox();
        controlRoot.setSpacing(10);
        controlRoot.setAlignment(Pos.CENTER);
        controlRoot.setPadding(new Insets(10,10,10,10));
        controlRoot.getChildren().addAll(topText,controlPane);
        
        HBox root=new HBox();
        root.getChildren().addAll(canvasRoot,controlRoot);
        
        
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Math Playground");
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

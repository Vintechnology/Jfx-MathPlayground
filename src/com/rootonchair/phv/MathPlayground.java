/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rootonchair.phv;

import com.rootonchair.phv.EquationInterpret.UnexpectedCharacterException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author user
 */
public class MathPlayground extends Application {
    private GraphDrawer graphDrawer;
    
    @Override
    public void start(Stage primaryStage) {
        graphDrawer=new GraphDrawer();
        
        HBox canvasRoot=new HBox();
        canvasRoot.getChildren().add(graphDrawer.getCanvas());
        canvasRoot.setId("canvas_pane");
        
        final TextField equationField=new TextField();
        CharacterListener listener=new CharacterListener(equationField);
        equationField.textProperty().addListener(listener);
        final ContextMenu contextMenu= new ContextMenu();
        MenuItem copy=new MenuItem("Copy");
        copy.setOnAction((ActionEvent event)->{
            equationField.copy();
        });
        MenuItem cut=new MenuItem("Cut");
        cut.setOnAction((ActionEvent event)->{
            equationField.cut();
        });
        MenuItem paste=new MenuItem("Paste");
        paste.setOnAction((ActionEvent event)->{
            equationField.paste();
        });
        
        contextMenu.getItems().addAll(cut,copy,paste);
        equationField.setContextMenu(contextMenu);
        
        Label topText=new Label("Welcome to Math Playground");
        topText.setId("top_text");
        Label equationText=new Label("Equation:");
        equationText.setId("label");
        
        Text errorText=new Text();
        errorText.setFill(Color.FIREBRICK);
        
        HBox buttonPane=new HBox();
        buttonPane.setAlignment(Pos.CENTER_RIGHT);
        buttonPane.setSpacing(10);
        Button okButton=new Button("OK");
        okButton.setOnAction((ActionEvent event)->{
            String src=equationField.getText();
            graphDrawer.setEquation(src);
            errorText.setText("");
            try{
                graphDrawer.drawGraph();
            } catch(UnexpectedCharacterException ex){
                errorText.setText("* "+ex.getMessage());
            }
        });
        Button resetButton=new Button("Reset");
        resetButton.setOnAction((ActionEvent event)->{
            equationField.clear();
            errorText.setText("");
            graphDrawer.erase();
        });
        buttonPane.getChildren().addAll(okButton,resetButton);
             
        GridPane controlPane=new GridPane();
        controlPane.setAlignment(Pos.CENTER);
        controlPane.setHgap(5);
        controlPane.setVgap(5);
        controlPane.add(equationText, 0, 0);
        controlPane.add(equationField,1,0);
        controlPane.add(buttonPane, 1, 2);
        controlPane.add(errorText, 1, 3);
        
        VBox controlRoot=new VBox();
        controlRoot.setSpacing(10);
        controlRoot.setAlignment(Pos.CENTER);
        controlRoot.setPadding(new Insets(10,10,10,10));
        controlRoot.getChildren().addAll(topText,controlPane);
        
        HBox root=new HBox();
        root.getChildren().addAll(canvasRoot,controlRoot);
        root.setId("root");
        
        Scene scene=new Scene(root);
        scene.getStylesheets().add(MathPlayground.class.getResource("MathPlayground.css").toExternalForm());
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

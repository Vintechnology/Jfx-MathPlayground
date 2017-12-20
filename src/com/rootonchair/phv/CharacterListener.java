/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rootonchair.phv;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author user
 */


public class CharacterListener implements ChangeListener<String> {
    private TextField target;
    private boolean isUser;
    
    public CharacterListener(TextField listenFrom){
        this.target=listenFrom;
        isUser=true;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if(newValue.length()>oldValue.length() && isUser){
            StringBuilder builder=new StringBuilder(newValue);
            isUser=!indentifyAndAppend(builder,newValue.charAt(newValue.length()-1));
            if(!isUser){
                target.setText(builder.toString());
                
                Platform.runLater(()->{
                    target.positionCaret(builder.length()-1);
                });
            }
        }else if(!isUser){
            isUser=true;
        }
    }

    private boolean indentifyAndAppend(StringBuilder builder,char character){
        if(character=='('){
            builder.append(')');
            return true;
        }
        return false;
    }
    
}
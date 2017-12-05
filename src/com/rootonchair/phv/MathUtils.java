/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rootonchair.phv;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author user
 */
public class MathUtils {
    
    public static enum MathSymbol{
        SQUARE_ROOT (0x221A),
        CUBE_ROOT (0x221B),
        INFINITY (0x221E),
        PI (0x1D70B),
        POWER_0 (0x2070),
        POWER_i (0x2071),
        POWER_4 (0x2074),
        POWER_5 (0x2075),
        POWER_6 (0x2076),
        POWER_7 (0x2077),
        POWER_8 (0x2078),
        POWER_9 (0x2079),
        POWER_PLUS (0x207A),
        POWER_MINUS (0x207B),
        POWER_OPEN_PARENTHESES (0x207D),
        POWER_CLOSE_PARENTHESES (0x207E);
        
        public final int codeNumber;
        
        MathSymbol(int number){
            codeNumber=number;
        }
    }
    
    public static double round(double value, int places)
    {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
    }
}

package com.rootonchair.phv;

import java.util.HashMap;
import java.util.Map;

public class EquationInterpret {
    private int pos=-1,ch;
    private String src,orgSrc;
    private final Map<String,Double> map;
    private final String keyBind;
    private double variable;
    private boolean isPositive;
    public EquationInterpret(String src,String keyBind,double defaultValue){
	this.orgSrc=src;
	this.keyBind=keyBind;
	this.map=new HashMap<>();
	map.put(keyBind, defaultValue);
        variable=defaultValue;
    }
    public void setSource(String newSrc){
	if(newSrc!=null && !newSrc.equals(orgSrc))
            this.orgSrc=newSrc;
    }
    public double applyVariable(double newValue){
	map.put(keyBind, newValue);
        return parse();
    }
    private void replaceVariable(){
        variable=map.get(keyBind);
	src=orgSrc.replace(keyBind, "("+String.valueOf(map.get(keyBind)+")"));
    }
    private void nextChar(){
	ch=(++pos<src.length()?src.charAt(pos):-1);
    }
    private boolean eat(int charToEat){
	while(ch==' ')
            nextChar();
	if(ch==charToEat){
            nextChar();
            return true;
	}
	return false;
    }
	
    public double parse(){
	replaceVariable();
	this.pos=-1;
	nextChar();
	if(ch==-1)
            return 0;
	double x= parseExpression();
	if(this.pos<src.length()) 
            throw new UnexpectedCharacterException("Unexpected character \""+(char)ch+"\"");
        if(!Double.isInfinite(x)){
            isPositive = Double.compare(x, 0d)>=0;
        }
        return x;
    }
    
    public boolean isInPositive(){
        return isPositive;
    }
	
    private double parseExpression(){
	double x=parseTerm();
	for(;;){
            if (eat('+'))
                x+=parseTerm();
            else if (eat('-'))
		x-=parseTerm();
            else
		return x;
	}
    }
	
    private double parseTerm(){
        double x= parseFactor();
	for(;;){
            if(eat('*'))
                x*=parseFactor();
            else if(eat('/')){
                double dividedFactor=parseFactor();
                if(dividedFactor==0){
                    //todo handle divided to zero
                }
                x/=dividedFactor;// may cause divided by zero
            }
            else
		return x;
	}
    }
	
    private double parseFactor(){
        if(eat('+'))
            return parseFactor();
	else if(eat('-'))
            return -parseFactor();
		
	double x;
	int startPos=this.pos;
	if(eat('(')){
            x=parseExpression();
            if(!eat(')'))
                throw new UnexpectedCharacterException("Cannot find \")\" ");
	}else if(eat('|')){
            x=parseExpression();
            if(!eat('|'))
                throw new UnexpectedCharacterException("Cannot find \"|\" ");
            x=Math.abs(x);
            
        }else if(ch>='0'&& ch<='9' || ch=='.'){
            while(ch>='0'&& ch<='9' || ch=='.')
		nextChar();
            try{
		x=Double.parseDouble(src.substring(startPos, this.pos));
            }catch(NumberFormatException ex){
                throw new UnexpectedCharacterException("Cannot resolve equation");
            }
	}else if (ch>='a' && ch<='z'){
            while(ch>='a' && ch<='z')
		nextChar();
            String functn=src.substring(startPos, this.pos);
            x=parseFactor();
            switch (functn) {
                case "sqrt":
                    x=Math.sqrt(x);
                    break;
                case "sin":
                    x=Math.sin(Math.toRadians(x));
                    break;
                case "cos":
                    x=Math.cos(Math.toRadians(x));
                    break;
                case "tan":
                    x=Math.tan(Math.toRadians(x));
                    break;
                case "cot":
                    x=1/Math.tan(Math.toRadians(x));
                    break;
                default:
                    throw new UnexpectedCharacterException("Unknown function \""+functn+"\"");
            }
	}else{
            if(ch!=-1)
                throw new UnexpectedCharacterException("Unexpected character \""+(char)ch+"\"");
            else
                throw new UnexpectedCharacterException("Cannot resolve equation");
                
        }
		
	if(eat('^')){
            double y=parseFactor();
            x=Math.pow(x, y);
	}
	return x;
    }
    
    public class UnexpectedCharacterException extends RuntimeException{

        public UnexpectedCharacterException(String message) {
            super(message);
        }
        
    }
}

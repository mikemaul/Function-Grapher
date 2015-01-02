
package grapher;


/**
    A class that can compute the value of an arithmetic expression.
*/
public class Evaluator {
   
    private ExpressionTokenizer tokenizer;
    String input;
    double x;

     /**
         Constructs an evaluator.
         @param anExpression  a string containing the expression
         to be evaluated
     */
     public Evaluator(String anExpression, double theX)
     {
        input = anExpression;
        x = theX;
        tokenizer = new ExpressionTokenizer(anExpression);
     }
     
     /*---------------------------------------------------------------------*/
     
     public double Evaluate() throws NumberFormatException
     {   
         //Check that the number of paranthesis match.
         int numOpenParenthesis=0;
         int numCloseParenthesis=0;
         for(int i=0; i<input.length();i++)
         {
             if(input.charAt(i)=='(') numOpenParenthesis++;
             if(input.charAt(i)==')') numCloseParenthesis++;
         }
         if(numOpenParenthesis != numCloseParenthesis)
         {
             throw new NumberFormatException();
         }
         
         //Get and return answer.
         return getExpressionValue();
     }
     
     /*---------------------------------------------------------------------*/

     /**
         Evaluates the expression.
         @return  the value of the expression
     */
     public double getExpressionValue() throws NumberFormatException
     {
         double value = getTermValue();
         boolean done = false;
         while (!done)
         {
             String next = tokenizer.peekToken();
             if ("+".equals(next) || "-".equals(next))
             {
                 tokenizer.nextToken(); //  Discard "+"  or "-"
                 double value2 = getTermValue();
                 if ("+".equals(next)) { value = value + value2; }
                 else { value = value - value2; }
             }
             else
             {
                 done = true;
             }
         }
         return value;
     }
     
     /*---------------------------------------------------------------------*/
     
     /**
         Evaluates the next term found in the expression.
         @return  the value of the term
     */
     public double getTermValue() throws NumberFormatException
     {
         double value = getFactorValue();
         boolean done = false;
         while (!done)
         {
             String next = tokenizer.peekToken();
             if ("*".equals(next) || "/".equals(next))
             {
                 tokenizer.nextToken();
                 double value2 = getFactorValue();
                 if ("*".equals(next)) { value = value * value2; }
                 else { value = value / value2; }
             }
             else
             {
                 done = true;
             }
         }
         return value;
     }
     
     /*---------------------------------------------------------------------*/
     
    /**
        Evaluates the next factor found in the expression.
        @return  the value of the factor
    */
    public double getFactorValue() throws NumberFormatException
    {
        double value;
        String next = tokenizer.peekToken();
        
        if ("(".equals(next))
        {
            tokenizer.nextToken(); //  Discard "("
            value = getExpressionValue();
            
            //if the closing ")" is not there, throw exception.
            if(!")".equals(tokenizer.peekToken()))
            {
                throw new NumberFormatException();
            }    
            else
            {    
                tokenizer.nextToken(); //  Discard ")"
            }
        }
        //If Sine
        else if("sin".equals(next))
        {
            tokenizer.nextToken(); //Discard "sin"
            tokenizer.nextToken(); //  Discard "("
            value = Math.sin(getExpressionValue());
            
            //if the closing ")" is not there, throw exception.
            if(!")".equals(tokenizer.peekToken()))
            {
                throw new NumberFormatException();
            }    
            else
            {    
                tokenizer.nextToken(); //  Discard ")"
            }
        }
        //If Cosine
        else if("cos".equals(next))
        {
            tokenizer.nextToken(); //Discard "cos"
            tokenizer.nextToken(); //  Discard "("
            value = Math.cos(getExpressionValue());
            
            //if the closing ")" is not there, throw exception.
            if(!")".equals(tokenizer.peekToken()))
            {
                throw new NumberFormatException();
            }    
            else
            {    
                tokenizer.nextToken(); //  Discard ")"
            }
        }
        //If Tangent
        else if("tan".equals(next))
        {
            tokenizer.nextToken(); //Discard "tan"
            tokenizer.nextToken(); //  Discard "("
            value = Math.tan(getExpressionValue());
            
            //if the closing ")" is not there, throw exception.
            if(!")".equals(tokenizer.peekToken()))
            {
                throw new NumberFormatException();
            }    
            else
            {    
                tokenizer.nextToken(); //  Discard ")"
            }
        }
        //If Cosecant
        else if("csc".equals(next))
        {
            tokenizer.nextToken(); //Discard "csc"
            tokenizer.nextToken(); //  Discard "("
            value = 1.0/Math.sin(getExpressionValue());
            
            //if the closing ")" is not there, throw exception.
            if(!")".equals(tokenizer.peekToken()))
            {
                throw new NumberFormatException();
            }    
            else
            {    
                tokenizer.nextToken(); //  Discard ")"
            }
        }
        //If Secant
        else if("sec".equals(next))
        {
            tokenizer.nextToken(); //Discard "sec"
            tokenizer.nextToken(); //  Discard "("
            value = 1.0/Math.cos(getExpressionValue());
            
            //if the closing ")" is not there, throw exception.
            if(!")".equals(tokenizer.peekToken()))
            {
                throw new NumberFormatException();
            }    
            else
            {    
                tokenizer.nextToken(); //  Discard ")"
            }
        }
        //If Cotangent
        else if("cot".equals(next))
        {
            tokenizer.nextToken(); //Discard "cot"
            tokenizer.nextToken(); //  Discard "("
            value = 1.0/Math.tan(getExpressionValue());
            
            //if the closing ")" is not there, throw exception.
            if(!")".equals(tokenizer.peekToken()))
            {
                throw new NumberFormatException();
            }    
            else
            {    
                tokenizer.nextToken(); //  Discard ")"
            }
        }
        //If Logarithm (base 10)
        else if("log".equals(next))
        {
            tokenizer.nextToken(); //Discard "log"
            tokenizer.nextToken(); //  Discard "("
            value = Math.log(getExpressionValue());
            
            //if the closing ")" is not there, throw exception.
            if(!")".equals(tokenizer.peekToken()))
            {
                throw new NumberFormatException();
            }    
            else
            {    
                tokenizer.nextToken(); //  Discard ")"
            }
        }
        //If Square root
        else if("root".equals(next))
        {
            tokenizer.nextToken(); //Discard "root"
            tokenizer.nextToken(); //  Discard "("
            value = Math.sqrt(getExpressionValue());
            
            //if the closing ")" is not there, throw exception.
            if(!")".equals(tokenizer.peekToken()))
            {
                throw new NumberFormatException();
            }    
            else
            {    
                tokenizer.nextToken(); //  Discard ")"
            }
        }
        //If Exponent
        else if("exp".equals(next))
        {
            tokenizer.nextToken(); //Discard "exp"
            tokenizer.nextToken(); //  Discard "("
            
            //Get Base 
            double base = getExpressionValue();
            
            tokenizer.nextToken(); //Discard ","
            
            //Get Power 
            double power = getExpressionValue();   
            
            value = Math.pow(base, power);
            
            //if the closing ")" is not there, throw exception.
            if(!")".equals(tokenizer.peekToken()))
            {
                throw new NumberFormatException();
            }    
            else
            {    
                tokenizer.nextToken(); //  Discard ")"
            }
        }
        //If X
        else if("x".equals(next) || "X".equals(next))
        {
            tokenizer.nextToken(); //Discard "x"
            value = x;
        }
        //If a number
        else
        {
            //This line may throw an exception if it cannot parse the token.
            value = Double.parseDouble(tokenizer.nextToken());
        }

        return value;
            
    }
 
     
    
}
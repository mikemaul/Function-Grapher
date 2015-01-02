
package grapher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.border.EtchedBorder;

public class Grapher {
    
    JFrame frame = new JFrame("Grapher");
    MyDrawPanel graphPanel;
    JButton zoomIN = new JButton("Zoom In");
    JButton zoomOUT = new JButton("Zoom Out");
    JButton btnGraph = new JButton("Graph");
    JLabel errorMessage = new JLabel("Input Error: Double Check Input");
    JTextField inputTxtField = new JTextField("2*sin(x)",20);
    int scale_index=1;
    double[] SCALE = {100.0, 50.0, 20.0, 10.0, 5.0, 0.5};

    //Create UI and run the grapher
    public void run()
    {
       
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(1000, 900);
        frame.setVisible(true);
        
        graphPanel = new MyDrawPanel();     
        
        JPanel bottomPanels = new JPanel();
        bottomPanels.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        
        //Removed some options because of issues
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        JLabel lblOne   = new JLabel("                         Legend:");
        JLabel lblTwo   = new JLabel("Sine = sin()                    Cosine = cos()");
        //JLabel lblThree = new JLabel("Tangent = tan()             Cotangent = cot()");
        //JLabel lblFour  = new JLabel("Cosecant = csc()         Secant = sec()");
        JLabel lblFive   = new JLabel("Square root = root()    Natural Log = log()");
        JLabel lblSix= new JLabel("Exponent = exp(BASE,POWER)");
        legendPanel.add(lblOne);
        legendPanel.add(lblTwo);
        //legendPanel.add(lblThree);
        //legendPanel.add(lblFour);
        legendPanel.add(lblFive);
        legendPanel.add(lblSix);
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        
        JPanel inFields = new JPanel();
        inFields.add(new JLabel("f(x) = "));
        inFields.add(inputTxtField);
        btnGraph.addActionListener(new GraphButtonListener());
        inFields.add(btnGraph);
        inputPanel.add(inFields);
        
        JPanel ErrorLabel = new JPanel();
        errorMessage.setForeground(Color.RED);
        errorMessage.setVisible(false);
        ErrorLabel.add(errorMessage);
        inputPanel.add(ErrorLabel);
        
        JPanel buttonsPanel = new JPanel();
        zoomIN.addActionListener(new ZoomInListener());
        zoomOUT.addActionListener(new ZoomOutListener());
        buttonsPanel.add(zoomIN);
        buttonsPanel.add(zoomOUT);
        inputPanel.add(buttonsPanel);
        
        bottomPanels.add(legendPanel);
        bottomPanels.add(inputPanel);
        
        frame.getContentPane().add(BorderLayout.CENTER, graphPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, bottomPanels);

        frame.validate();
    }
    
    //Graph button actionlistener
    class GraphButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            graphPanel.repaint();
            graphPanel.validate();
        }  
    }
    
    //Zoom in action listener
    class ZoomInListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            zoomOUT.setEnabled(true);
            if(scale_index>0){
                scale_index--;                
                if(scale_index==0)zoomIN.setEnabled(false);
                graphPanel.repaint();
                graphPanel.validate();
            }
        }
    }
    
    //Zoom out action listener
    class ZoomOutListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae) {
            zoomIN.setEnabled(true);
            if(scale_index<5)
            {
                scale_index++;
                if(scale_index==5)zoomOUT.setEnabled(false);
                graphPanel.repaint();
                graphPanel.validate();
            }
        }
    }
    
    class MyDrawPanel extends JPanel
    {

        @Override
        protected void paintComponent(Graphics g) throws NumberFormatException{
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            
            int xMid = getWidth()/2;
            int yMid = getHeight()/2;
            
            //Fill background white
            g2.setColor(Color.white);
            g2.fillRect(0, 0, getWidth(), getHeight());
            
            //Draw vertical grid lines and number on x-Axis
            for(int i=xMid; i<getWidth(); i=i+50){
                //Draw lines
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawLine(i, 0, i, getHeight());
                g2.drawLine((xMid-i)+xMid, 0, (xMid-i)+xMid, getHeight());
                
                //Draw numbers
                g2.setColor(Color.BLACK);
                FontMetrics metrics = g2.getFontMetrics();
                DecimalFormat df = new DecimalFormat("###.#");
                double number;
                //Positive numbers
                number = ((i-xMid)/SCALE[scale_index]);
                String posNum = df.format(number);
                int stringWidth = metrics.stringWidth(posNum);
                g2.drawString(posNum, i-stringWidth/2, yMid+metrics.getHeight());
                //Negative numbers
                number = ((xMid-i)/SCALE[scale_index]);
                String negNum = df.format(number);
                stringWidth = metrics.stringWidth(negNum);
                g2.drawString(negNum, ((xMid-i)+xMid)-stringWidth/2, yMid+metrics.getHeight());
            }
            
            //Draw horizontal grid lines and number on y-Axis
            for(int i=yMid+50; i<getHeight(); i=i+50){
                //Draw Lines
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawLine(0, i, getWidth(), i);
                g2.drawLine(0, (yMid-i)+yMid, getWidth(), (yMid-i)+yMid);
                
                //Draw numbers
                g2.setColor(Color.BLACK);
                FontMetrics metrics = g2.getFontMetrics();
                DecimalFormat df = new DecimalFormat("###.#");
                double number;
                //Positive numbers
                number = ((i-yMid)/SCALE[scale_index]);
                String posNum = df.format(number);
                int stringWidth = metrics.stringWidth(posNum);
                g2.drawString(posNum, xMid-stringWidth-5, (yMid-i)+yMid+(5));
                //Negative numbers
                number = ((yMid-i)/SCALE[scale_index]);
                String negNum = df.format(number);
                stringWidth = metrics.stringWidth(negNum);
                g2.drawString(negNum, xMid-stringWidth-5, (i+5));
            }
            
            //Draw X-Axis
            g2.setColor(Color.black);
            g2.drawLine(0, yMid, getWidth(), yMid);
            //Draw Y-Axis
            g2.drawLine(xMid, 0, xMid, getHeight());    
            
            
            
            //Draw actual graph
            g2.setColor(Color.BLUE);
            if(!inputTxtField.getText().equals(""))
                {    
                try{
                    errorMessage.setVisible(false);
                    for(int x=0; x<getWidth(); x++)
                    {
                        int X1 = x;

                        double y1 = new Evaluator(inputTxtField.getText(),((X1-(this.getWidth()/2))/SCALE[scale_index])).Evaluate();
                        if(Double.isNaN(y1) || Double.isInfinite(y1)) continue; //Check & skip undefined solutions
                        int Y1 = (int)((this.getHeight()/2) - y1*SCALE[scale_index]);

                        int X2 =  X1+1;

                        double y2 = new Evaluator(inputTxtField.getText(),((X2-(this.getWidth()/2))/SCALE[scale_index])).Evaluate();
                        if(Double.isNaN(y2) || Double.isInfinite(y2)) continue; //Check & skip undefined solutions
                        int Y2 = (int)((this.getHeight()/2) - y2*SCALE[scale_index]);

                        g.drawLine(X1, Y1, X2, Y2);
                    }
                }
                catch(NumberFormatException ex)
                {
                    errorMessage.setVisible(true);
                    inputTxtField.setText("");
                    btnGraph.doClick();
                    inputTxtField.requestFocus();
                }
            }
        }    
        
    }
}

package com.cc.tilingLizard.paneles.panel_resultado.stroke.strokes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class AnimatedStroke extends BasicStroke {

    public AnimatedStroke(int width, int height) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BasicStroke dashedStroke;
                //final int width = 100;
                //final int height = 30;
                final BufferedImage image = new BufferedImage(
                        width,height,BufferedImage.TYPE_INT_ARGB);
                final JLabel label = new JLabel(new ImageIcon(image));
                int pad = 5;
                final Shape rectangle = new Rectangle2D.Double(
                        (double)pad,(double)pad,
                        (double)(width-2*pad),
                        (double)(height-2*pad));

                ActionListener listener = new ActionListener() {

                    float dashPhase = 0f;
                    float dash[] = {5.0f,5.0f};
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        dashPhase += 9.0f;
                        BasicStroke dashedStroke = new BasicStroke(
                                1.5f,
                                BasicStroke.CAP_ROUND,
                                BasicStroke.JOIN_MITER,
                                1.5f, //miter limit
                                dash,
                                dashPhase
                        );
                        Graphics2D g = image.createGraphics();

                        g.setColor(Color.WHITE);
                        g.fillRect(0,0,width,height);

                        g.setColor(Color.BLACK);
                        g.setStroke(dashedStroke);
                        g.draw(rectangle);

                        g.dispose();
                        label.repaint();
                        /*
                        if (dashPhase<100f) {
                            try {
                                ImageIO.write(
                                        image,
                                        "PNG",
                                        new File("img" + dashPhase + ".png"));
                            } catch(IOException ioe) {
                                // we tried
                            }
                        }*/
                    }
                };
                Timer timer = new Timer(40, listener);
                timer.start();
                JOptionPane.showMessageDialog(null, label);
            }
        });
    }

    public static void main(String[] args) {
        new AnimatedStroke(100,100);
    }
}
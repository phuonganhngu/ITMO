
package clientudp;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

    
 class Circle implements Runnable {

        int x;
        int y;
        int radius;
        Color color;
        String action;
        ClientUDP7.ExpFrame frame;
        Ellipse2D ellip;
        float alpha_ellipse;

        public Circle(int x, int y, int radius, Color color, String action, ClientUDP7.ExpFrame frame) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.color = color;
            this.action = action;
            this.frame = frame;
            ellip = new Ellipse2D.Double(this.x-this.radius, this.y-this.radius, this.radius * 2, this.radius * 2);
            alpha_ellipse = 1f;
        }

        public int getX() {
            return this.x;
        }
        
        public void setAction(String action){
            this.action = action;
        }

        public int getY() {
            return this.y;
        }

        public int getRadius() {
            return this.radius;
        }

        public Color getColor() { 
            return this.color;
        }

        public void draw(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(color);
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    alpha_ellipse));
            g2d.fill(this.ellip);

        }

        public boolean contains(Point p) {
            if (((p.x - this.x) * (p.x - this.x) + (p.y - this.y) * (p.y - this.y)) < radius * radius) {
                return true;
            } else {
                return false;
            }
        }
        
        public void setAlpha(float alpha){
            this.alpha_ellipse = alpha;
        }

        @Override
        public void run() {
            draw(frame.panel.getGraphics());
        }
        
        @Override
        public boolean equals(Object obj){
             if (obj == null) {
                    return false;
            }

            if (!Circle.class.isAssignableFrom(obj.getClass())) {
                return false;
            }

            final Circle other = (Circle) obj;
            if ((this.x != other.x) || (this.y != other.y) || (this.radius != other.radius) || (this.color != other.color)) {
                return false;
            }

            return true;
        }
    }
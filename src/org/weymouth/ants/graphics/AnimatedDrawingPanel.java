package org.weymouth.ants.graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

public class AnimatedDrawingPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -1135538150464749505L;
	
	private Timer timer;
	private AnimatedDrawing source;
	private int milliseconds = 15;
	
	public AnimatedDrawingPanel(AnimatedDrawing source) {
		this.source = source;
        setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new BorderLayout());
        setBackground(Color.BLACK);
		setSize(getPreferredSize());
		source.setSize(getPreferredSize());
		timer = new Timer (milliseconds, this);
	}
	
	public void setTimerMilliseconds(int time){
		milliseconds = time;
		stop();
		start();
	}

	public void start(){
		timer = new Timer (milliseconds, this);
		timer.start ();
	}
	
	public void stop() {
        timer.stop ();
        timer = null;
	}
	
    public Dimension getPreferredSize() {
        return new Dimension(source.getSize());
    }
	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRect(0, 0, getSize().width,getSize().height);
        source.draw(g);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		source.update();
		this.repaint(); 
	}  

}


package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.w3c.dom.events.MouseEvent;

public class Input implements KeyListener {

	private Window window;
	
	private boolean[] keys = new boolean[256];
	public boolean[] processed = new boolean[keys.length];
	
	public Input(Window window) {
		this.window = window;
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		processed[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		
		if (e.getKeyCode() == KeyEvent.VK_F) {
			System.out.println("test");
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

	public void reset() {
		for (int i = 0; i < processed.length; i++) {
			processed[i] = false;
		}
	}

}

package com.cs304.lab9.Game;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

import javax.media.opengl.GLCanvas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FlappyBird extends JFrame implements KeyListener, MouseListener {

    private Animator animator;
    private GLCanvas glcanvas;
    private FlappyBirdGLEventListener listener = new FlappyBirdGLEventListener();

    public static void main(String[] args) {
//        new FlappyBird().animator.start();


        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FlappyBird.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        FlappyBird a = new FlappyBird();
    }



    public FlappyBird() {
        super("Flappy Bird Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        animator = new FPSAnimator(15);
        animator.add(glcanvas);
        glcanvas.addKeyListener(this);
        glcanvas.addMouseListener(this);
        animator.start();
        add(glcanvas, BorderLayout.CENTER);
        setSize(800, 600);
        setLocationRelativeTo(this);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            System.out.println("UP");
            listener.updateBirdPosition(KeyEvent.VK_UP);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            System.out.println("DOWN");
            listener.updateBirdPosition(KeyEvent.VK_DOWN);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        listener.handleMouseClick(mouseEvent.getX() , mouseEvent.getY());
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}

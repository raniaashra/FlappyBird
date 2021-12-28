package com.cs304.lab9.Game;
import com.cs304.lab9.AnimListener;
import com.cs304.lab9.Texture.TextureReader;
import com.sun.opengl.util.j2d.TextRenderer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.BitSet;

public class FlappyBirdGLEventListener extends AnimListener implements GLEventListener {
    private int xBird;
    private int yBird;
    int xPosition = 0;
    int yPosition = 0;
    boolean statefraze = false ;
    boolean HomePage = false;
    boolean isGame = false;
    boolean isGameOver = false;
    boolean isExit = false;
    int state = 0;
    boolean sound = false;
    int maxWidth = 800;
    int maxHeight = 500;
    int x = maxWidth / 2, y = maxHeight / 2;
    int animationIndex = 0;
    int timer = 0;
    int score = 0;
    int xPipe ;
    int yPipe;
    int xPipe2 ;
    int yPipe2 ;
    int xMargin = 100;
    int yMargin = 75;
    boolean startGame = true;
    boolean home =false;
    GL gl;
    TextRenderer tr = new TextRenderer(Font.decode("PLAIN"));

    String textureNames[] = {"b.png", "bird2.png", "flappy-bird-on-scratch-pipes.png", "back.png","over.png","icon.jpg"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture("Assets" + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }

    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();
        handleKeyPress();

        if(startGame){
            startGame = false;
            initGame();
        }
//        if(state == 0){
//            HomePage(gl);
//        }else if(state == 1){
//            increasePipe();
//            increasePipe2();
//            DrawBackground(gl);
//            DrawScore();
//            //draw pipe
//            DrawSprite2(gl, xPipe, yPipe, 2, 5);
//            DrawSprite2(gl, xPipe2, yPipe2, 2, 5);
//            //draw bird
//            DrawSprite(gl, xBird, yBird, 0, 1.5F);
//
//            handleCollision();
//            handleCollision2();
//        }
        increasePipe();
        increasePipe2();
        DrawBackground(gl);
        DrawScore();
        //draw pipe
        DrawSprite2(gl, xPipe, yPipe, 2, 5);
        DrawSprite2(gl, xPipe2, yPipe2, 2, 5);
        //draw bird
        DrawSprite(gl, xBird, yBird, 0, 1.5F);

        handleCollision();
        handleCollision2();
    }

    private void initGame() {
        xBird = 100;
        yBird = 300;
        xPipe = 850;
        yPipe = 300;
        yPipe2 = 300;
        xPipe2 = 1500 ;
        timer = 0 ;
        score = 0 ;
    }

    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        // فهنا اهو بحط رقم الصوره
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[3]);  // Turn Blending On

//        gl.glColor3f(0, 0.5f, 0.5f);
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }
    public void Drawgameover(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        // فهنا اهو بحط رقم الصوره
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[4]);  // Turn Blending On

//        gl.glColor3f(0, 0.5f, 0.5f);
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }
    public void HomePage(GL gl) {

        gl.glEnable(GL.GL_BLEND);
        // فهنا اهو بحط رقم الصوره
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[5]);  // Turn Blending On

//        gl.glColor3f(0, 0.5f, 0.5f);
        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }
    //bird
    public void DrawSprite(GL gl, int x, int y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.1 * scale, 0.1 * scale, 1);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);

    }
    //pipe
    public void DrawSprite2(GL gl, int x, int y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);    // Turn Blending On

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 1, y / (maxHeight / 2.0) - 1, 0);
        gl.glScaled(0.1 * scale, 0.3 * scale, 1);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    private void handleCollision() {
        if (collisionX() && collisionY()) {
//            Drawgameover(gl);
            JOptionPane.showMessageDialog(null, "Game Over");
            startGame = true;
        }
    }

    private boolean collisionX() {
        return xBird >= xPipe - xMargin && xBird <= xPipe + xMargin;
    }

    private boolean collisionY() {
        return yBird <= yPipe - yMargin || yBird >= yPipe + yMargin;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void handleCollision2() {
        if (collisionX2() && collisionY2()) {
//            DrawGameOver();
           JOptionPane.showMessageDialog(null, "Game Over");
            startGame = true;
        }
    }
    private boolean collisionX2() {
        return xBird >= xPipe2 - xMargin && xBird <= xPipe2 + xMargin;
    }
    private boolean collisionY2() {
        return yBird <= yPipe2 - yMargin || yBird >= yPipe2 + yMargin;
    }
  //////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void increasePipe() {
        xPipe -= 15;
        if (xPipe < 0) {
            xPipe = 850;
            yPipe = 300 + (Math.random() > 0.5 ? -1 : 1) * 80;
            if (Math.random() > 0.5 && yPipe < 300) {
                yPipe = 300;
            }
        }
    }
    private void increasePipe2() {
        xPipe2 -= 15;
        if (xPipe2 < 0) {
            xPipe2 = 1500;
            yPipe2 = 300 + (Math.random() > 0.5 ? -1 : 1) * 50;
            if (Math.random() > 0.5 && yPipe2 < 300) {
                yPipe2 = 300;
            }
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    public void updateBirdPosition(int code) {

        if (code == KeyEvent.VK_UP) {
            yBird+=5;
        } else if (code == KeyEvent.VK_DOWN) {
            yBird-=5;
        } else if (code == KeyEvent.VK_RIGHT) {
            xBird++;
        } else if (code == KeyEvent.VK_LEFT) {
            xBird--;
        }

        if (xBird < 0) {
            xBird++;
        }

        if (xBird == maxWidth) {
            xBird--;
        }

        if (yBird < 0) {
            yBird++;
        }

        if (yBird == maxHeight) {
            yBird--;
        }

    }
    public void DrawScore() {
        tr.beginRendering(300, 300);
        tr.setColor(Color.CYAN);
        timer += 1 ;
        score += 1 ;
        tr.draw("Score : " + score/50 , 8, 280);
        tr.draw("Timer : " + timer / 20 + " Sec ", 8, 260);
        tr.setColor(Color.WHITE);
        tr.endRendering();

    }
    public void DrawGameOver() {
        timer = 100 ;
        tr.beginRendering(500, 500);
        tr.setColor(Color.red);
        tr.draw("Game Over" , 200, 200);
        tr.setColor(Color.red);
        tr.endRendering();
        statefraze = true ;
        //System.out.println("hello");


    }
    public void handleKeyPress() {
//
//        if (isKeyPressed(KeyEvent.VK_LEFT)) {
//            if (x > 0) {
//                x--;
//            }
//            animationIndex++;
//        }
//        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
//            if (x < maxWidth - 10) {
//                x++;
//            }
//            animationIndex++;
//        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (y > 0) {
                y -= 15;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (y < maxHeight - 10) {
                y+=15;
            }
            animationIndex++;
        }
    }

    public BitSet keyBits = new BitSet(256);

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        keyBits.clear(keyCode);
    }

    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }
/*
    private void onORoffSound() {
        try {
            FileInputStream music = new FileInputStream(new File("sound.mp3"));
            AudioStream audios = new AudioStream(music);
            if (sound) {
                AudioPlayer.player.stop(audios);

            } else {
                AudioPlayer.player.start(audios);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }*/
    public void handleMouseClick(double x, double y){
        System.out.println(x + " " + y);
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        double x1 = mouseEvent.getX();
        double y1 = mouseEvent.getY();
        Component c = mouseEvent.getComponent();
        double width = c.getWidth();
        double height = c.getHeight();
        xPosition = (int) ((x1 / width) * 100);
        yPosition = ((int) ((y1 / height) * 100));
        yPosition = 100 - yPosition;
        System.out.println(xPosition + " " + yPosition);
        if(home){
            if(xPosition < 305 && xPosition >415 && yPosition<430 && yPosition> 500){
                home= false;
                state=1;

            }

        }

    }
}

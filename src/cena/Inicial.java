package cena;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT; //primitivas 3D
import java.util.Random;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author siabr
 */
public class Inicial implements GLEventListener{

    private GL2 gl;
    private GLU glu;
    private GLUT glut;
    public int tonalizacao = GL2.GL_SMOOTH;
    public boolean liga = true;
    private TextRenderer textRenderer;
    private static GLWindow window = null;

    public float mouseX = 0;
    public boolean clicked = false;
    public float mouseY = 0;
    public float tx = 0;
    public float ty = 0;
    public int larguraFrame;
    public int alturaFrame;
    public float position = 0.0f;
    Random r = new Random();

    @Override
    public void init(GLAutoDrawable drawable) {
        //dados iniciais da cena
        GL2 gl = drawable.getGL().getGL2();
        textRenderer = new TextRenderer(new Font("Comic Sans MS Negrito", Font.PLAIN, 38));

        //habilita o buffer de profundidade
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        //obtem o contexto Opengl
        gl = drawable.getGL().getGL2();
        glut = new GLUT(); //objeto da biblioteca glut

        //define a cor da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 1);
        //limpa a janela com a cor especificada
       
        //limpa o buffer de profundidade
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity(); //lê a matriz identidade
     //   desenha_estrelas(drawable,gl);
        desenhaTexto(gl,0,570, Color.YELLOW,  "Welcome to Galaxy Wars");
        desenhaTexto(gl,250,300, Color.YELLOW,  "Start");
        double i, cX, cY, rX, rY;
        cX = 0;
        cY = 0;
        //Valores diferentes geram elipses
        rX = 5f;
        rY = 5f;

        double limite = 2*Math.PI;
        if (clicked == true){
            gl = drawable.getGL().getGL2();
            glut = new GLUT(); //objeto da biblioteca glut

            //define a cor da janela (R, G, G, alpha)
            gl.glClearColor(0, 0, 0, 1);
            //limpa a janela com a cor especificada
            
            //limpa o buffer de profundidade
            gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
            gl.glLoadIdentity(); //lê a matriz identidade
            int randomPosX = r.nextInt((-90 + 180) + 1) - 90;
            int randomPosY = r.nextInt((95-70) + 1) + 70;
            int x = 0;
            gl.glPushMatrix();
            gl.glColor3f(1,1,0); // cor amarela
            gl.glTranslatef(position, -100, 0);
            gl.glBegin(GL2.GL_QUADS);
                    gl.glVertex2f(0.0f, 0.0f);
                    gl.glVertex2f(0f, 5f);
                    gl.glVertex2f(15f, 5f);
                    gl.glVertex2f(15f, 0f);
            gl.glPopMatrix();
            gl.glEnd();
            
            gl.glPushMatrix();
            gl.glScalef(0.2f, 0.3f, 0f);
            gl.glTranslatef(randomPosX, randomPosY, 1);
            gl.glBegin(GL2.GL_POLYGON);
            for(i=0 ; i < limite; i+= 0.01) {
                    gl.glVertex2d(cX + rX * Math.cos(i), cY + rY * Math.sin(i));		   
            }
            gl.glPopMatrix();
            gl.glEnd();
        }
        gl.glFlush();
//        desenhaTexto(gl,250,200, Color.YELLOW,  "Sair");
    }

    public void asteroide(GLAutoDrawable drawable){
            
            gl = drawable.getGL().getGL2();
            glut = new GLUT(); //objeto da biblioteca glut

            //define a cor da janela (R, G, G, alpha)
            gl.glClearColor(0, 0, 0, 1);
            //limpa a janela com a cor especificada
            
            //limpa o buffer de profundidade
            gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
            gl.glLoadIdentity(); //lê a matriz identidade
            
            int randomPosX = r.nextInt((-90 + 180) + 1) - 90;
            int randomPosY = r.nextInt((95-70) + 1) + 70;
        //    int randomPosZ = r.nextInt((60-30) + 1) + 30;
        
            gl.glPushMatrix();
            gl.glScalef(0.5f, 0.3f, 0.5f);
            gl.glTranslatef(randomPosX, randomPosY, 1);
            gl.glBegin(GL2.GL_POLYGON);
                gl.glVertex2f(2.0f, 4f);
                gl.glVertex2f(2.5f, 5f);
                gl.glVertex2f(2.4f, 6f);
                gl.glVertex2f(3f, 6.3f);
                gl.glVertex2f(4f, 5.5f);
                gl.glVertex2f(5f, 5.5f);
                gl.glVertex2f(5.3f, 2.3f);
                gl.glVertex2f(4.6f, -1f);
                gl.glVertex2f(4.4f, -2f);
                gl.glVertex2f(3.8f, -3f);
                gl.glVertex2f(2.6f, -2f);
                gl.glVertex2f(2.5f, -1.2f);
           gl.glPopMatrix();
           gl.glEnd();
    }
    
        public void asteroide1(GL2 gl){
            int randomPosX = r.nextInt((-90 + 180) + 1) - 90;
            int randomPosY = r.nextInt((95-70) + 1) + 70;
        //    int randomPosZ = r.nextInt((60-30) + 1) + 30;
            gl.glPushMatrix();
            gl.glScalef(0.5f, 0.3f, 0.5f);
            gl.glTranslatef(randomPosX, randomPosY, 1);
            gl.glBegin(GL2.GL_POLYGON);
                gl.glVertex2f(2.0f, 4f);
                gl.glVertex2f(2.5f, 5f);
                gl.glVertex2f(2.4f, 6f);
                gl.glVertex2f(3f, 6.3f);
                gl.glVertex2f(4f, 5.5f);
                gl.glVertex2f(5f, 5.5f);
                gl.glVertex2f(5.3f, 2.3f);
                gl.glVertex2f(4.6f, -1f);
                gl.glVertex2f(4.4f, -2f);
                gl.glVertex2f(3.8f, -3f);
                gl.glVertex2f(2.6f, -2f);
                gl.glVertex2f(2.5f, -1.2f);
           gl.glPopMatrix();
           gl.glEnd();
    }
    
    
    
    public void desenhaTexto(GL2 gl, int x, int y,Color cor, String frase) {
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);       
        textRenderer.setColor(cor);

        //glut = new GLUT(); //objeto da biblioteca glut
        gl.glRasterPos2f(x, y);
        textRenderer.draw(frase, x, y);
        textRenderer.endRendering();

        //glut.glutBitmapString(GLUT.BITMAP_8_BY_13, frase);
    }
    
    public void desenha_estrelas(GLAutoDrawable drawable, GL2 gl){
            gl.glPushMatrix();   
            gl.glTranslatef( 0.7f, 0.7f , 0 );  //canto superior direito
            gl.glScalef(100, 100,  100);//diminuiindo escala
            gl.glRotatef(45, 0, 0 , 1); // rotacionar em 45 graus no eixo Z
            gl.glColor3f(1,1,0); // cor amarela
            gl.glBegin(GL2.GL_QUADS);
             //cor amarela
            gl.glVertex2f(0.6f,0.3f); 
            gl.glVertex2f(0.6f,0.2f); 
            gl.glVertex2f(0.7f,0.2f);  
            gl.glVertex2f(0.7f,0.3f);
            gl.glPopMatrix();
            gl.glEnd();
    }
    
    


    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //obtem o contexto grafico Opengl
        gl = drawable.getGL().getGL2();
        //ativa a matriz de projeção
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity(); //lê a matriz identidade
        //projeção ortogonal (xMin, xMax, yMin, yMax, zMin, zMax)
        gl.glOrtho(-100, 100, -100, 100, -100, 100);
        //ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        System.out.println("Reshape: " + width + ", " + height);
        
        larguraFrame = width;
        alturaFrame = height;
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}
   
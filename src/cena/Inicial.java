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
    public float mouseY = 0;
    public float tx = 0;
    public float ty = 0;
    public int larguraFrame;
    public int alturaFrame;

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
        desenha_estrelas(drawable,gl);
        desenhaTexto(gl,0,570, Color.YELLOW,  "Welcome to Galaxy Wars");
        desenhaTexto(gl,250,300, Color.YELLOW,  "Start");
//        desenhaTexto(gl,250,200, Color.YELLOW,  "Sair");


        gl.glFlush();
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
            gl.glScalef(0.5f, 0.5f,  1 );//diminuiindo escala
            gl.glRotatef(45, 0, 0 , 1); // rotacionar em 45 graus no eixo Z
            gl.glBegin(GL2.GL_QUADS);
            gl.glColor3f(1.0f,1.0f,0); //cor amarela

            gl.glVertex2f(0.6f,0.3f); 
            gl.glVertex2f(0.6f,0.2f); 
            gl.glVertex2f(0.7f,0.2f);  
            gl.glVertex2f(0.7f,0.3f);

            gl.glEnd();
            gl.glPopMatrix();
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

    public void mouseClicked(MouseEvent e) {
        int botao = e.getButton();
        if(botao == MouseEvent.BUTTON1){
            System.out.println("Clique ESQ");

            //Pega as coordenados do clique do mouse
            mouseX = (float)e.getX();
            mouseY = (float)e.getY();	

            //Realiza da conversão das coordenadas da tela para coordenadas da janela
            tx = ( (2 * 100 * mouseX) / larguraFrame) - 100;
            ty = ( ( (2 * 100) * (mouseY-alturaFrame) ) / - alturaFrame) - 100;
            
            System.out.println("tx: " + tx + ", ty: " + ty);
        }
        System.out.println("mouse");
//adiciona a Cena a Janela  



        
    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {}
//
//    @Override
//    public void mouseExited(MouseEvent e) {}
//
//    @Override
//    public void mousePressed(MouseEvent e) {
//        /*int botao = e.getButton();
//
//        if(botao == MouseEvent.BUTTON1){
//            System.out.println("Clique ESQ");
//
//            //Pega as coordenados do clique do mouse
//            mouseX = (float)e.getX();
//            mouseY = (float)e.getY();	
//
//            //Realiza da conversão das coordenadas da tela para coordenadas da janela
//            tx = ( (2 * 100 * mouseX) / larguraFrame) - 100;
//            ty = ( ( (2 * 100) * (mouseY-alturaFrame) ) / - alturaFrame) - 100;
//            
//            System.out.println("tx: " + tx + ", ty: " + ty);
//        }
//        System.out.println("mouse");
//        */
//    }
//
//    @Override
//    public void mouseReleased(MouseEvent e) {}
//
//    @Override
//    public void mouseMoved(MouseEvent e) {}
//
//    @Override
//    public void mouseDragged(MouseEvent e) {}
//
//    @Override
//    public void mouseWheelMoved(MouseEvent e) {}
}

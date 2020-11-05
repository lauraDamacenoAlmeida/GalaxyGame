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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.Locale;

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
    private float livesAnimation = 0;
    private boolean ispoint = true;
    
    public int fase;
    public float velocidade = 0;
    public boolean lado_direito;
    public boolean lado_esquerdo = true;
    public boolean topo_superior;
    public boolean topo_inferior;
    private float xTranslateBall = 0;
    private float yTranslateBall = 100f;
    private float ballSpeed = 0f;
    private boolean colisao = false;
    private int score = 0;
    public int mode = GL2.GL_FILL;
    public int vidas = 5;
    public boolean subtrai_vida = false;
    public int nomeFase = 1;
    Random r = new Random();
    public Window frame;
    @Override
    public void init(GLAutoDrawable drawable) {
        textRenderer = new TextRenderer(new Font("Comic Sans MS Negrito", Font.BOLD, 15));
        //dados iniciais da cena
    }
    
    @Override
    public void display(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();
        glut = new GLUT();

        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);
        String m = mode == GL2.GL_LINE ? "LINE" : "FILL";
        
        desenhaTexto(gl,0,570, Color.GREEN,  "Bem vindo ao Pong!");
        desenhaTexto(gl,270,300, Color.GREEN,  "1 - Iniciar");
        desenhaTexto(gl,270,280, Color.GREEN,  "ESC - Sair");
        
        switch (fase) {
		case 0:
			break;
		case 1:
			fase1();
                        if (subtrai_vida == true){
                            vidas -= 1;
                        }
		}
        if (liga) {
            iluminacaoAmbiente();
            ligaLuz();
        }
        if (vidas == 0){
             glut = new GLUT();
            gl.glClearColor(0, 0, 0, 1);
            gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
            gl.glLoadIdentity();
            gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);
            desenhaTexto(gl,260,300, Color.GREEN,  "GAME OVER!");
        }
        gl.glFlush();
    }
    
    public void fase1(){
        glut = new GLUT();

        gl.glClearColor(0, 0, 0, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);
        String m = mode == GL2.GL_LINE ? "LINE" : "FILL";
        
        dadosObjeto(gl, 510, 570, Color.WHITE , "Score: " + score);
        dadosObjeto(gl, 510, 550, Color.WHITE , "Fase: " + nomeFase);
        fisicaDaBola();
        desenhaBarra();
        desenhaBola();
        for (int i = 1; i <= 5; i++) {
            if (vidas >= i)
                    drawLives(10f * i, true);
            else
                    drawLives(10f * i, false);
        }
    }
    
    public void desenhaBarra(){
        gl.glPushMatrix();
        gl.glColor3f(1,1,0); // cor amarela
        gl.glTranslatef(position, -100, 0);
        gl.glBegin(GL2.GL_QUADS);
                gl.glVertex2f(0.0f, 0.0f);
                gl.glVertex2f(0f, 5f);
                gl.glVertex2f(15f, 5f);
                gl.glVertex2f(15f, 0f);
        gl.glEnd();
        gl.glPopMatrix();        
    }
    
    public void desenhaBola(){
        double i, cX, cY, rX, rY;
        double limite = 2*Math.PI;
        cX = 0;
        cY = 0;
        //Valores diferentes geram elipses
        rX = 5f;
        rY = 5f;
        gl.glPushMatrix();
        gl.glTranslatef(xTranslateBall, yTranslateBall, 0);
        gl.glScalef(1.2f, 1.6f, 0f);
            gl.glBegin(GL2.GL_POLYGON);
            for(i=0 ; i < limite; i+= 0.01) {
                    gl.glVertex2d(cX + rX * Math.cos(i), cY + rY * Math.sin(i));		   
            }
        gl.glEnd();
        gl.glPopMatrix();
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
    
  public void iluminacaoAmbiente() {
        float luzAmbiente[] = {0.2f, 0.2f, 0.2f, 1.0f}; //cor
        float posicaoLuz[] = {-50.0f, 0.0f, 100.0f, 1.0f}; //pontual

        // define parametros de luz de número 0 (zero)
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);
    }

    public void ligaLuz() {
        // habilita a definição da cor do material a partir da cor corrente
        gl.glEnable(GL2.GL_COLOR_MATERIAL);

        // habilita o uso da iluminação na cena
        gl.glEnable(GL2.GL_LIGHTING);
        // habilita a luz de número 0
        gl.glEnable(GL2.GL_LIGHT0);
        //Especifica o Modelo de tonalizacao a ser utilizado 
        //GL_FLAT -> modelo de tonalizacao flat 
        //GL_SMOOTH -> modelo de tonalização GOURAUD (default)        
        gl.glShadeModel(tonalizacao);
    }

    public void desligaluz() {
        //desabilita o ponto de luz
        gl.glDisable(GL2.GL_LIGHT0);
        //desliga a iluminacao
        gl.glDisable(GL2.GL_LIGHTING);
    }
    
    public void fisicaDaBola(){
        Double v = Math.random() * ((velocidade + 0.8) - (velocidade + 0.5)) + (velocidade + 0.5);
        ballSpeed = v.floatValue();
        if (xTranslateBall >= 95){
            lado_direito = true;
            lado_esquerdo = false;
        }else if (xTranslateBall <= -95){
            lado_esquerdo = true;
            lado_direito = false;
            topo_inferior = false;
            topo_superior = false;
        }else if (yTranslateBall >= 100){
            topo_superior = true;
            topo_inferior = false;
            ispoint = true;
        }else if (yTranslateBall <= -100){
            topo_inferior = true;
            topo_superior = false;
            ispoint = true;
        }else if (xTranslateBall >= position-6.7 && xTranslateBall <= position+7.5 && yTranslateBall <=-88 && ispoint){
            colisao = true;
            topo_inferior = true;
            topo_superior = false;
            subtrai_vida = false;
            score += 10;
            if(score >= 200 && nomeFase == 1){
                velocidade = 0.8f;
                nomeFase += 1;
            }
            ispoint = false;
        }
         if (lado_direito == true && topo_inferior == false){
            xTranslateBall -= ballSpeed;
            yTranslateBall -= ballSpeed;
            subtrai_vida = false;
            colisao = false;
        }
        else if (lado_esquerdo == true && topo_superior == true){
            colisao = false;
            xTranslateBall += ballSpeed;
            yTranslateBall -= ballSpeed;
            subtrai_vida = false;
            ispoint = true;
        }else if (lado_esquerdo == true && topo_superior == false && colisao == true){
            xTranslateBall += ballSpeed;
            yTranslateBall += ballSpeed;
            subtrai_vida = false;
        }else if (lado_esquerdo == true && topo_superior == true && colisao == false){
            xTranslateBall += ballSpeed;
            yTranslateBall -= ballSpeed;
            subtrai_vida = false;
        }
        else if (lado_esquerdo == true && topo_superior == false && topo_inferior == false && colisao == true){
            colisao = false;
            xTranslateBall += ballSpeed;
            yTranslateBall -= ballSpeed;
            subtrai_vida = false;
        }else if (topo_inferior == true & colisao == true){
            topo_superior = false;
            xTranslateBall -= ballSpeed;
            yTranslateBall += ballSpeed;
        }
        else if (topo_inferior == true && colisao == false){
            topo_superior = false;
            xTranslateBall -= ballSpeed;
            yTranslateBall -= ballSpeed;
            subtrai_vida = true;
            resetBall();
        }else{
            xTranslateBall += ballSpeed;
            yTranslateBall -= ballSpeed;
            subtrai_vida = false;
        }
    }
    
    public void resetBall(){
        Double reset = Math.random() * (70.0 + 40.0) - 40.0;
        xTranslateBall = Double.valueOf(reset).floatValue();
        yTranslateBall = 100;
    }
	
    public void dadosObjeto(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase){         
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        //Retorna a largura e altura da janela
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);       
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, mode);
    }
    
    public void drawLives(float pos, boolean filled) {
            gl.glPushMatrix();
            if (filled)
                    gl.glColor3f(1, 0.2f, 0);
            else
                    gl.glColor3f(1, 1, 1);

            gl.glTranslatef(-95f + pos, 90f, 0);
            gl.glRotatef(livesAnimation, 1, 1, 0);
            livesAnimation += 0.8f;

            glut.glutSolidTeapot(3f);
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
}
   
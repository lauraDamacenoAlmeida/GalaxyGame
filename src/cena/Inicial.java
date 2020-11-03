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
    
    public int fase;
    public float velocidade;
    public boolean lado_direito;
    public boolean lado_esquerdo = true;
    public boolean topo_superior;
    public boolean topo_inferior;
    private float xTranslateBall = 0;
    private float yTranslateBall = 100f;
    private boolean colisao = false;
    private boolean colidiu = false;
    private int score = 0;
    public int mode = GL2.GL_FILL;
    Random r = new Random();

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
        dadosObjeto(gl, 510, 570, Color.WHITE , "Score: " + score);
       
        switch (fase) {
		case 0:
			break;
		case 1:
			fase1();
                       // desenhaTexto(gl, 70, 90, Color.WHITE, Integer.toString(score));
			break;
		case 2:
			break;
		case 3:
			break;
		}
         
     //   desenha_estrelas(drawable,gl);
//        desenhaTexto(gl,0,570, Color.YELLOW,  "Welcome to Galaxy Wars");
//        desenhaTexto(gl,250,300, Color.YELLOW,  "Start");
//        desenhaTexto(gl,250,200, Color.YELLOW,  "Sair");
//        if (clicked == true){
//            gl = drawable.getGL().getGL2();
//            glut = new GLUT(); //objeto da biblioteca glut
//
//            //define a cor da janela (R, G, G, alpha)
//            gl.glClearColor(0, 0, 0, 1);
//            //limpa a janela com a cor especificada
//            
//            //limpa o buffer de profundidade
//            gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
//            gl.glLoadIdentity(); //lê a matriz identidade
////            int randomPosX = r.nextInt((-90 + 180) + 1) - 90;
////            int randomPosY = r.nextInt((95-70) + 1) + 70;
//            
//            
//           
//        }
        gl.glFlush();
    }
    
    public void fase1(){
        fisicaDaBola();
        desenhaBarra();
        desenhaBola();
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
     //   gl.glScalef(0.2f, 0.3f, 0f);
        gl.glScalef(1.2f, 1.6f, 0f);
      //  gl.glTranslatef(randomPosX, randomPosY, 1);
            gl.glBegin(GL2.GL_POLYGON);
            for(i=0 ; i < limite; i+= 0.01) {
                    gl.glVertex2d(cX + rX * Math.cos(i), cY + rY * Math.sin(i));		   
            }
        gl.glEnd();
        gl.glPopMatrix();
        
      //  rolarBola();
    }
    
    public void fisicaDaBola(){
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
        }else if (yTranslateBall <= -100){
            topo_inferior = true;
            topo_superior = false;
        }else if (xTranslateBall >= position-7.5 && xTranslateBall <= position+7.5 && yTranslateBall <=-95){
//            System.out.println("COLISÃO!!!");
            colisao = true;
            topo_inferior = true;
            topo_superior = false;
            score += 10;
        }else if (!(xTranslateBall >= position-7.5 && xTranslateBall <= position+7.5 && yTranslateBall <=-95)){
//            System.out.println("SEM COLISÃO");
            //  colisao = false;
            //  colidiu = false;
        }
        if (lado_esquerdo == true && topo_inferior == true && colisao == false){
            System.out.println("ENTROU NO IF 2");
            xTranslateBall -= Math.random() * (0.8 - 0.5) + 0.5;
            yTranslateBall += Math.random() * (0.8 - 0.5) + 0.5;
        }
        else if (lado_direito == true && topo_inferior == false){
//            System.out.println("ENTROU NO IF 3");
            xTranslateBall -= Math.random() * (0.8 - 0.5) + 0.5;
            yTranslateBall -= Math.random() * (0.8 - 0.5) + 0.5;
        }
        else if (lado_esquerdo == true && topo_superior == true){
//            System.out.println("ENTROU NO IF 4");
            colidiu = false;
            colisao = false;
            xTranslateBall += Math.random() * (0.8 - 0.5) + 0.5;
            yTranslateBall -= Math.random() * (0.8 - 0.5) + 0.5;
        }else if (lado_esquerdo == true && topo_superior == false && colisao == true){
//            System.out.println("ENTROU NO IF 5");
            xTranslateBall += Math.random() * (0.8 - 0.5) + 0.5;
            yTranslateBall += Math.random() * (0.8 - 0.5) + 0.5;
        }else if (lado_esquerdo == true && topo_superior == false && topo_inferior == false && colisao == false){
//            System.out.println("ENTROU NO IF 6");
            xTranslateBall += Math.random() * (0.8 - 0.5) + 0.5;
            yTranslateBall += Math.random() * (0.8 - 0.5) + 0.5;
        }else if (topo_inferior == true & colisao == true){
//            System.out.println("ENTROU NO IF 9");
            xTranslateBall -= Math.random() * (0.8 - 0.5) + 0.5;
            yTranslateBall += Math.random() * (0.8 - 0.5) + 0.5;
            colidiu = true;
        }
        else if (topo_inferior == true && colidiu == false){
//            System.out.println("ENTROU NO IF 8");
            xTranslateBall -= Math.random() * (0.8 - 0.5) + 0.5;
            yTranslateBall -= Math.random() * (0.8 - 0.5) + 0.5;
        }
       // colidiu = false;
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
   
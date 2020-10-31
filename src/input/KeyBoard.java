package input;
import cena.Inicial;
import cena.Renderer;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.opengl.GL2;
/**
 *
 * @author Siabreu
 */
public class KeyBoard implements KeyListener, MouseListener{
    private Inicial cena_inicial;
    
    public KeyBoard(Inicial cena_inicial){
        this.cena_inicial = cena_inicial;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {        
        System.out.println("Key pressed: " + e.getKeyCode());
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);
        
        switch (e.getKeyCode()) {
            case 149:
                cena_inicial.position -= 1.0f;
                break;
            case 151:
                cena_inicial.position += 1.0f;
                break;
            
        }
    }

    @Override
    public void keyReleased(KeyEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) {   
        int botao = e.getButton();

        if(botao == MouseEvent.BUTTON1){
            System.out.println("Clique ESQ");
            cena_inicial.clicked = true;
            //Pega as coordenados do clique do mouse
            cena_inicial.mouseX = (float)e.getX();
            cena_inicial.mouseY = (float)e.getY();	

            //Realiza da conversão das coordenadas da tela para coordenadas da janela
            cena_inicial.tx = ( (2 * 100 * cena_inicial.mouseX) / cena_inicial.larguraFrame) - 100;
            cena_inicial.ty = ( ( (2 * 100) * (cena_inicial.mouseY-cena_inicial.alturaFrame) ) / - cena_inicial.alturaFrame) - 100;
            
            System.out.println("tx: " + cena_inicial.tx + ", ty: " + cena_inicial.ty);
        }
        System.out.println("mouse");
    }

    @Override
    public void mouseEntered(MouseEvent e) {  }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseEvent e) {}

}

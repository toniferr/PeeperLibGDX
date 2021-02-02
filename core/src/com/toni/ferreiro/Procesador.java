package com.toni.ferreiro;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

public class Procesador extends InputAdapter {  //con InputProcessor hay que implementar los metodos


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("Has tocado en la posicion "+ screenX + ", "+ screenY);
        System.out.println("Has usado el dedo "+ pointer + " y el boton "+button);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //si touchDown return false, no se tratara touchUP
        return super.touchUp(screenX, screenY, pointer, button);
    }
}

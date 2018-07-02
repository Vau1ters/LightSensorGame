package jp.ac.titech.itpro.sdl.game.entities;

import android.view.MotionEvent;

import jp.ac.titech.itpro.sdl.game.R;
import jp.ac.titech.itpro.sdl.game.Rect;
import jp.ac.titech.itpro.sdl.game.component.ColliderComponent;
import jp.ac.titech.itpro.sdl.game.component.SimpleRenderableComponent;
import jp.ac.titech.itpro.sdl.game.component.SpriteComponent;
import jp.ac.titech.itpro.sdl.game.component.TransformComponent;
import jp.ac.titech.itpro.sdl.game.component.parameters.SwitchParameterComponent;
import jp.ac.titech.itpro.sdl.game.math.Vector2;
import jp.ac.titech.itpro.sdl.game.messages.Message;
import jp.ac.titech.itpro.sdl.game.stage.RenderingLayers;
import jp.ac.titech.itpro.sdl.game.stage.Stage;

public class Switch extends Entity {
    public Switch(final Stage stage, Vector2 position, boolean canRelease, final int id) {
        super(stage);
        TransformComponent transform = new TransformComponent(position, this);
        SpriteComponent sprite = new SpriteComponent(R.drawable.button, new Rect(0, 0, 16, 16), this);
        final SwitchParameterComponent param = new SwitchParameterComponent(canRelease, id, this);
        addComponent(transform);
        addComponent(sprite);
        addComponent(new SimpleRenderableComponent(transform, sprite, RenderingLayers.LayerType.CHARACTER_UNDER, this));
        addComponent(param);
        addComponent(new ColliderComponent(new Vector2(16,16), this){

            @Override
            public void enterCollide(ColliderComponent other){
                // プレイヤーに踏まれたら
                Message msg = Message.SWITCH_PRESSED;
                msg.setArgs(new Object[]{param.id});
                stage.notifyAll(msg);
            }

            @Override
            public void exitCollide(ColliderComponent other){
                // プレイヤーが押していたのが離れたら
                if(param.canRelease){
                    Message msg = Message.SWITCH_RELEASED;
                    msg.setArgs(new Object[]{param.id});
                    stage.notifyAll(msg);
                }
            }
        });
    }
}

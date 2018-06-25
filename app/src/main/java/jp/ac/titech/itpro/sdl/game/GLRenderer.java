package jp.ac.titech.itpro.sdl.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.transition.Scene;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import jp.ac.titech.itpro.sdl.game.scenes.scenes.IScene;
import jp.ac.titech.itpro.sdl.game.scenes.scenes.SceneStage;

public class GLRenderer implements GLSurfaceView.Renderer {
    private Sprite sprite;
    private final Context context;
    private Texture texture;

    private IScene scene;

    public GLRenderer(final Context context){
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        sprite = new Sprite();
        Resources res = context.getResources();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.human, options);
        try {
            texture = new Texture(bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // ここから下に作らないといけない
        scene = new SceneStage();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 unused) {
        scene.update();
        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        sprite.draw(110,110, texture, new Rect(6,6, 5, 5), 1);
        scene.render(sprite);
    }
}
package dev.lyze.retro.game.actors;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import dev.lyze.retro.utils.OrthogonalTiledMapRendererBleeding;

public class Map extends Actor {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public Map() {
        map = new TmxMapLoader().load("maps/data/Test.tmx");
        renderer = new OrthogonalTiledMapRendererBleeding(map, 1);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        renderer.setView((OrthographicCamera) getStage().getCamera());

        batch.end();
        renderer.render();
        batch.begin();
    }
}

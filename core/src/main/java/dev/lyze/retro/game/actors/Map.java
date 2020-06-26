package dev.lyze.retro.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.github.czyzby.kiwi.log.Logger;
import com.github.czyzby.kiwi.log.LoggerService;
import dev.lyze.retro.game.Game;
import dev.lyze.retro.game.actors.enums.Direction;
import dev.lyze.retro.game.actors.enums.Type;
import dev.lyze.retro.utils.IntVector2;
import dev.lyze.retro.utils.OrthogonalTiledMapRendererBleeding;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;

public class Map extends Actor {
    private static final Logger logger = LoggerService.forClass(Map.class);

    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    @Getter
    private IntVector2 startPoint, finishPoint;

    @Getter
    private ArrayList<IntVector2> pathPoints = new ArrayList<>();

    @Getter
    private int mapTileWidth, mapTileHeight;
    @Getter
    private int tileWidth, tileHeight;

    public Map(Game game, int mapIndex) {
        map = game.getAss().getMaps().get(mapIndex);
        renderer = new OrthogonalTiledMapRendererBleeding(map, 1);

        parseMap();
    }

    private void parseMap() {
        parseMetadata();
        parseDirections();

        mapTileWidth = map.getProperties().get("width", Integer.class);
        tileWidth = map.getProperties().get("tilewidth", Integer.class);
        mapTileHeight = map.getProperties().get("height", Integer.class);
        tileHeight = map.getProperties().get("tileheight", Integer.class);

        logger.info("Parsing map {0}x{1} a {2}x{3}", mapTileWidth, mapTileHeight, tileWidth, tileHeight);

        setBounds(0, 0, mapTileWidth * tileWidth, mapTileHeight * tileHeight);
    }

    private void parseDirections() {
        var directions = (TiledMapTileLayer) map.getLayers().get("Directions");
        directions.setVisible(false);

        var currentPosition = new IntVector2(startPoint);
        var currentDirection = Direction.DOWN;
        pathPoints.add(new IntVector2(currentPosition));
        do {
            var cell = directions.getCell(currentPosition.getX(), currentPosition.getY());

            if (cell != null) {
                var type = cell.getTile().getProperties().get("type", String.class);
                if (Type.DIRECTION.getValue().equals(type)) {
                    var newDirection = cell.getTile().getProperties().get("direction", Integer.class);
                    currentDirection = Arrays.stream(Direction.values()).filter(direction -> direction.getValue() == newDirection).findFirst().orElseThrow(() -> new IllegalArgumentException("Unknown direction in " + currentPosition.toString()));
                } else {
                    logger.info("Unknown type on directions layer {0}", type);
                }
            }

            //logger.info("Going {0}", currentDirection);
            currentPosition.add(currentDirection.getAddToX(), currentDirection.getAddToY());
            pathPoints.add(new IntVector2(currentPosition));
        } while (!currentPosition.equals(finishPoint));
    }

    private void parseMetadata() {
        var metadata = (TiledMapTileLayer) map.getLayers().get("Metadata");
        metadata.setVisible(false);

        for (int y = 0; y < metadata.getHeight(); y++) {
            for (int x = 0; x < metadata.getWidth(); x++) {
                var cell = metadata.getCell(x, y);

                if (cell == null)
                    continue;

                var type = cell.getTile().getProperties().get("type", String.class);
                if (Type.START.getValue().equals(type)) {
                    startPoint = new IntVector2(x, y);
                }
                else if (Type.FINISH.getValue().equals(type)) {
                    finishPoint = new IntVector2(x, y);
                }
                else {
                    logger.info("Wrong tile type {0} on metadata layer", type);
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        renderer.setView((OrthographicCamera) getStage().getCamera());

        batch.end();
        renderer.render();
        batch.begin();
    }

    @Override
    public void drawDebug(ShapeRenderer shapes) {
        super.drawDebug(shapes);

        shapes.setColor(Color.WHITE);
        shapes.set(ShapeRenderer.ShapeType.Filled);

        for (IntVector2 point : pathPoints) {
            shapes.rect(point.getX() * tileWidth, point.getY() * tileHeight, tileWidth, tileHeight);
        }
    }

    public IntVector2 convertFromPixelCoords(IntVector2 pixelCoords) {
        return new IntVector2(pixelCoords.getX() / tileWidth, pixelCoords.getY() / tileHeight);
    }

    public IntVector2 convertToPixelCoords(IntVector2 mapCoords) {
        return new IntVector2(mapCoords.getX() * tileWidth, mapCoords.getY() * tileHeight);
    }

    public boolean mapCoordsEqualsPixelCoords(IntVector2 mapCoords, IntVector2 pixelCoords) {
        return mapCoordsEqualsPixelCoords(mapCoords.getX(), mapCoords.getY(), pixelCoords.getX(), pixelCoords.getY());
    }

    public boolean mapCoordsEqualsPixelCoords(int mapX, int mapY, int pixelX, int pixelY) {
        return mapX * tileWidth == pixelX && mapY * tileHeight == pixelY;
    }
}

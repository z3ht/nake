package me.zinno.nake.interfaces;

import me.zinno.nake.enums.GameStatus;

import java.util.List;
import java.awt.*;

public interface Collidable<T extends PredictableLocale> extends Drawable {

    List<Dimension> getLocations();

    GameStatus onCollision(T t);

    default boolean isCollided(T t) {
        for(Dimension location : this.getLocations())
            if(t.getFutureLocation().equals(location))
                return true;
        return false;
    }



}

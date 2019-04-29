package me.zinno.nake.interfaces;

import me.zinno.nake.enums.GameStatus;

import java.awt.*;

public interface Collidable<T extends Locatable> extends Drawable, Locatable {

    GameStatus onCollision(T t);

    default boolean isCollided(T t) {
        Dimension headLoc = t.getLocations().get(0);
        for(Dimension location : this.getLocations())
            if(headLoc.equals(location))
                return true;
        return false;
    }

}

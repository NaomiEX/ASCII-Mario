package game.npcs.enemies;

import engine.actors.Actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnemyManager {
    private final List<Enemy> allEnemies;

    private static EnemyManager instance;

    private EnemyManager() {
        this.allEnemies = new ArrayList<>();
    }

    public static EnemyManager getInstance() {
        if (instance == null) {
            instance = new EnemyManager();
        }
        return instance;
    }

    public List<Enemy> getAllEnemies() {
        return Collections.unmodifiableList(allEnemies);
    }

    public void addEnemy(Enemy enemy) {
        this.allEnemies.add(enemy);
    }

    public Enemy getEquivalentEnemy(Actor actor) {
        for (Enemy enemy: this.allEnemies) {
            if (enemy == actor) {
                return enemy;
            }
        }
        return null;
    }

    public boolean isEnemy(Actor actor) {
        Enemy equivalentEnemy = this.getEquivalentEnemy(actor);
        return equivalentEnemy != null;
    }

    public void removeEnemy(Enemy enemy) {
        this.allEnemies.remove(enemy);
    }

//
//    @Override
//    public void resetInstance(Location location) {
//        GameMap map = location.map();
//        for(Enemy enemy : this.allEnemies) {
//            map.removeActor(enemy);
//        }
//    }
}

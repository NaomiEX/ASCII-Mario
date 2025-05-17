package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.actors.Actor;
import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.*;
import game.ground.*;
import game.ground.jumpableground.MatureTree;
import game.ground.jumpableground.Sapling;
import game.ground.jumpableground.Sprout;
import game.ground.jumpableground.Wall;
import game.npcs.Toad;
import game.effects.powerups.PowerStar;
import game.effects.powerups.SuperMushroom;

/**
 * The main class for the Mario World game.
 *
 */
public class Application {

	public static void main(String[] args) {

			World world = new World(new Display());

			FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Wall(), new Floor(), new Sprout(),
					new Sapling(), new MatureTree());


			List<String> map = Arrays.asList(
				"..........................................##..........+.........................",
				"............+............+..................#...................................",
				"............................................#...................................",
				".............................................##......................+..........",
				"...............................................#................................",
				"................................................#...............................",
				".................+................................#.............................",
				".................................................##.............................",
				"................................................##..............................",
				".........+..............................+#____####.................+............",
				".......................................+#_____###++.............................",
				".......................................+#______###..............................",
				"........................................+#_____###..............................",
				"........................+........................##.............+...............",
				"...................................................#............................",
				"....................................................#...........................",
				"...................+.................................#..........................",
				"......................................................#.........................",
				".......................................................##.......................");

			GameMap gameMap = new GameMap(groundFactory, map);
			world.addGameMap(gameMap);

			Actor toad = new Toad();
			int midX = map.get(0).length() / 2 ;
			int midY = map.size() / 2;
			world.addPlayer(toad,gameMap.at(midX, midY));

			// add walls
			Location toadLocation = gameMap.at(midX, midY);
			for (Exit exit: toadLocation.getExits()) {
				Location destination = exit.getDestination();
				destination.setGround(new Wall());
			}
			// FIXME: the Goomba should be generated from the Tree
//			gameMap.at(35, 10).addActor(new Goomba());
//			gameMap.at(42, 15).addActor(new Goomba());
//			gameMap.at(42, 12).addActor(new Goomba());
//			gameMap.at(35, 12).addActor(new Goomba());
			Actor mario = new Player("Player", 'm', 100);
			gameMap.at(41, 10).addItem(new PowerStar());
			gameMap.at(43, 10).addItem(new SuperMushroom());
			world.addPlayer(mario, gameMap.at(42, 10));





		world.run();

	}
}

package war.game.board;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import war.game.board.builder.TerrainBuilder;
import war.game.board.builder.impl.BasicTerrainBuilder;
import war.game.board.exception.BoardSystemException;
import war.game.board.exception.InvalidArmyPlacementException;
import war.game.board.exception.InvalidMovementException;
import war.game.domain.Player;
import war.game.domain.impl.AbstractPlayer;
import war.game.domain.impl.HumanPlayer;
import war.game.domain.impl.TerrainImpl;

public class BoardControllerTest {

	BoardController bc = BoardController.getInstance();

	Player playerA = null;
	Player playerB = null;

	TerrainImpl t1 = null;
	TerrainImpl t2 = null;
	TerrainImpl t3 = null;
	TerrainImpl t4 = null;
	TerrainImpl t5 = null;
	TerrainImpl t6 = null;
	TerrainImpl t7 = null;
	TerrainImpl t8 = null;
	TerrainImpl t9 = null;

	@Before
	public void setUp() throws Exception {
		playerA = new HumanPlayer();
		((HumanPlayer) playerA).setName("Player A");
		playerB = new HumanPlayer();
		((HumanPlayer) playerA).setName("Player B");

		TerrainBuilder tb = new BasicTerrainBuilder(bc.getBoard());

		t1 = tb.createTerrain("Brasil");
		t2 = tb.createTerrain("Argentina");
		t3 = tb.createTerrain("Chile");

		tb.createBorder(t1, t2);
		tb.createBorder(t2, t3);

		t4 = tb.createTerrain("Espanha", playerA);
		t5 = tb.createTerrain("Portugal", playerA, 1);
		t6 = tb.createTerrain("Inglaterra", playerB);

		tb.createBorder(t4, t5);
		tb.createBorder(t5, t6);

		t7 = tb.createTerrain("Canadá", playerA, 2);
		t8 = tb.createTerrain("Estados Unidos", playerA, 1);
		t9 = tb.createTerrain("México", playerB, 2);

		tb.createBorder(t7, t8);
		tb.createBorder(t8, t9);
	}

	@Test(expected = InvalidArmyPlacementException.class)
	public void add_army_to_empty_terrain_without_owner()
			throws BoardSystemException {
		BoardController.getInstance().addArmy(playerA, t1, 1);
	}

	@Test(expected = InvalidArmyPlacementException.class)
	public void add_army_to_empty_terrain_with_wrong_owner()
			throws BoardSystemException {
		BoardController.getInstance().addArmy(playerA, t6, 1);
	}

	@Test
	public void add_army_to_empty_terrain_with_right_owner()
			throws BoardSystemException {
		((AbstractPlayer) playerA).addExtraArmies(1);
		BoardController.getInstance().addArmy(playerA, t4, 1);
		Assert.assertEquals(1, t4.getArmies());
	}

	@Test(expected = InvalidArmyPlacementException.class)
	public void add_army_to_terrain_with_wrong_owner()
			throws BoardSystemException {
		BoardController.getInstance().addArmy(playerB, t5, 1);
		Assert.assertEquals(1, t5.getArmies());
	}

	@Test
	public void add_army_to_terrain_with_right_owner()
			throws BoardSystemException {
		((AbstractPlayer) playerA).addExtraArmies(1);
		BoardController.getInstance().addArmy(playerA, t5, 1);
		Assert.assertEquals(2, t5.getArmies());
	}

	@Test(expected = InvalidMovementException.class)
	public void move_army_to_empty_terrain_with_wrong_owner()
			throws BoardSystemException {
		BoardController.getInstance().moveArmy(t7, t6, 1);
	}

	@Test(expected = InvalidMovementException.class)
	public void move_army_to_empty_terrain_with_right_owner()
			throws BoardSystemException {
		BoardController.getInstance().moveArmy(t7, t4, 1);
	}

	@Test(expected = InvalidMovementException.class)
	public void move_army_to_terrain_not_neighbor() throws BoardSystemException {
		BoardController.getInstance().moveArmy(t4, t5, 1);
	}

	@Test(expected = InvalidMovementException.class)
	public void move_army_to_terrain_neighbor_with_wrong_owner()
			throws BoardSystemException {
		BoardController.getInstance().moveArmy(t9, t8, 1);
	}

	@Test
	public void move_army_to_terrain_neighbor_with_right_owner()
			throws BoardSystemException {
		int sourceArmiesBeforeMove = t7.getArmies();
		int targetArmiesBeforeMove = t8.getArmies();
		int movedArmies = 1;

		BoardController.getInstance().moveArmy(t7, t8, movedArmies);
		Assert.assertEquals(sourceArmiesBeforeMove - movedArmies,
				t7.getArmies());
		Assert.assertEquals(targetArmiesBeforeMove + movedArmies,
				t8.getArmies());
	}
}

package war.game.battle;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import war.game.GameController;
import war.game.battle.dice.SixDice;
import war.game.battle.exception.UnreacheableTargetException;
import war.game.board.BoardController;
import war.game.domain.Player;
import war.game.domain.impl.HumanPlayer;
import war.game.domain.impl.SimpleArmy;
import war.game.domain.impl.TerrainImpl;
import war.game.util.GameSystemException;

public class BattleControllerTest {

	static GameController mockedGameController = GameController.getInstance();
	static BattleController battleController = null;
	static BoardController mockedBoardController = null;

	Player playerA = new HumanPlayer();
	Player playerB = new HumanPlayer();

	TerrainImpl source = new TerrainImpl();
	TerrainImpl target = new TerrainImpl();

	BattleResult result = null;

	@Before
	public void setUp() throws Exception {
		((HumanPlayer) playerA).setName("playerA");
		((HumanPlayer) playerB).setName("playerB");
		battleController = BattleController.getInstance();
		battleController.setDiceType(new SixDice());

		mockedGameController = mock(GameController.class);
		mockedBoardController = mock(BoardController.class);

		when(mockedGameController.getBoardOperations()).thenReturn(
				mockedBoardController);

		mockedBoardController.setGameController(mockedGameController);

		battleController.setGameController(mockedGameController);
	}

	@Test
	public void valid_2x1_battle() throws GameSystemException {
		source.setOwner(playerA);
		source.setArmies(new SimpleArmy(2));

		target.setOwner(playerB);
		target.setArmies(new SimpleArmy(1));

		// Força o controlador de tabuleiro responder que existe fronteira entre
		// os territórios
		when(mockedBoardController.isNeighbor(source, target)).thenReturn(true);

		result = battleController.attack(source, target);

		Assert.assertEquals(true, result.getBattleSummary()
				.containsKey(playerA));
		Assert.assertEquals(true, result.getBattleSummary()
				.containsKey(playerB));

		Assert.assertEquals(1, result.getAttackingDiceResults().length);
		Assert.assertEquals(1, result.getDefendingDiceResults().length);
	}

	@Test(expected = UnreacheableTargetException.class)
	public void no_border_2x1_battle() throws GameSystemException {
		source.setOwner(playerA);
		source.setArmies(new SimpleArmy(2));

		target.setOwner(playerB);
		target.setArmies(new SimpleArmy(1));

		// Força o controlador de tabuleiro responder que NÃO existe fronteira
		// entre os territórios
		mockedBoardController = mock(BoardController.class);
		when(mockedBoardController.isNeighbor(source, target))
				.thenReturn(false);

		result = battleController.attack(source, target);

		Assert.assertNull(result);
	}
}

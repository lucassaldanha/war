package war.game.board;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import war.game.board.builder.impl.BasicTerrainBuilder;
import war.game.board.graph.Board;

public class BoardBuilderTest {

	Board board = new Board();

	BasicTerrainBuilder builder = new BasicTerrainBuilder(board);

	@Before
	public void setUp() throws Exception {
		builder.buildBoard("./data/map/standart_map.xml");
	}

	@Test
	public void test_terrains() {
		Assert.assertEquals(42, board.getGraph().vertexSet().size());
	}

	@Test
	public void test_borders() {
		Assert.assertEquals(79, board.getGraph().edgeSet().size());
	}

}

package war.game.board.builder.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.newdawn.slick.geom.Vector2f;

import war.game.board.builder.TerrainBuilder;
import war.game.board.exception.BoardSystemException;
import war.game.board.graph.Board;
import war.game.board.util.xml.XMLReader;
import war.game.domain.Continent;
import war.game.domain.Player;
import war.game.domain.Terrain;
import war.game.domain.impl.ContinentImpl;
import war.game.domain.impl.NullPlayer;
import war.game.domain.impl.SimpleArmy;
import war.game.domain.impl.TerrainImpl;

public class BasicTerrainBuilder implements TerrainBuilder {

    private Board board;

    public BasicTerrainBuilder(Board board) {
	this.board = board;
    }

    public TerrainImpl createTerrain(String terrainName)
	    throws BoardSystemException {
	return this.createTerrain(terrainName, new NullPlayer());
    }

    public TerrainImpl createTerrain(String terrainName, Player owner)
	    throws BoardSystemException {
	return this.createTerrain(terrainName, owner, 0);
    }

    public TerrainImpl createTerrain(String terrainName, Player owner,
	    int initialArmy) throws BoardSystemException {

	if (board == null) {
	    throw new BoardSystemException("The board class was null.");
	}

	TerrainImpl terrain = new TerrainImpl(terrainName, owner);
	if (initialArmy > 0) {
	    terrain.setArmies(new SimpleArmy(initialArmy));
	}

	if (board.getGraph().containsVertex(terrain) == false) {
	    if (board.getGraph().addVertex(terrain) == false) {
		throw new BoardSystemException("Unable to create terrain.");
	    }
	}

	return terrain;
    }

    /**
     * Este método cria uma fronteira entre os dois territórios passados como
     * parâmetro.<br>
     * <br>
     * A criação de uma fronteira é feita através da criação de uma aresta
     * ligando os dois vértices que representam cada um dos territórios em
     * questão no grafo que representa o tabuleiro de jogo.
     */
    public void createBorder(TerrainImpl aTerrain, TerrainImpl neighborTerrain)
	    throws BoardSystemException {
	if (aTerrain == null || neighborTerrain == null) {
	    throw new BoardSystemException(
		    "Unable to create border (one or more terrains was null)");
	}

	board.getGraph().addEdge(aTerrain, neighborTerrain);

	if (board.getGraph().containsEdge(aTerrain, neighborTerrain) == false) {
	    throw new BoardSystemException("Unable to create border.");
	}
    }

    @Override
    public Continent createContinent(String name, int bonus)
	    throws BoardSystemException {
	Continent c = board.getContinentByName(name);
	if (c == null) {
	    c = new ContinentImpl();
	    ((ContinentImpl) c).setName(name);
	    ((ContinentImpl) c).setBonus(bonus);
	    ((ContinentImpl) c).setTerrains(new HashSet<Terrain>());
	    board.getContinents().put(name, c);
	}
	return c;
    }

    @Override
    public void addTerrainToContinent(Terrain t, Continent c)
	    throws BoardSystemException {
	c.getTerrains().add(t);
    }

    /**
     * Este método cria territórios e fronteiras a partir de um arquivo XML
     * passado como parâmetro.<br>
     * <br>
     */
    public void buildBoard(String XML_Location) throws JDOMException,
	    IOException, BoardSystemException {

	XMLReader.getTerrainsANDBorders(XML_Location);

	ArrayList<TerrainImpl> terrainList = new ArrayList<TerrainImpl>();

	while (XMLReader.terrains.hasNext()) {

	    Element elementT = (Element) XMLReader.terrains.next();

	    TerrainImpl t = createTerrain(elementT.getChildText("name"));

	    float xpos = Float.parseFloat(elementT.getChildText("xpos"));
	    float ypos = Float.parseFloat(elementT.getChildText("ypos"));

	    t.setPosition(new Vector2f(xpos, ypos));

	    terrainList.add(t);

	}

	while (XMLReader.continents.hasNext()) {
	    Element c = (Element) XMLReader.continents.next();
	    String continentName = c.getChildText("name");
	    int bonus = Integer.valueOf(c.getChildText("bonus"));
	    Continent newContinent = null;
	    if (continentName != null && bonus >= 0) {
		newContinent = createContinent(continentName, bonus);
	    }

	    Element tElement = (Element) c.getChild("terrains");
	    Iterator<Element> terrains = XMLReader
		    .getChildrenElements(tElement);
	    while (terrains.hasNext()) {
		Element ts = terrains.next();
		String terrainName = ts.getText();
		Terrain t = board.getTerrainByName(terrainName);
		if (t != null) {
		    addTerrainToContinent(t, newContinent);
		} else {
		    throw new BoardSystemException("Invalid terrain name ["
			    + terrainName + "]");
		}
	    }
	}

	while (XMLReader.borders.hasNext()) {

	    Element elementB = (Element) XMLReader.borders.next();

	    List<Element> list = elementB.getChildren();

	    createBorder(TerrainAuxiliaryOperations.getTerrain(list.get(0)
		    .getChildText("name"), terrainList),
		    TerrainAuxiliaryOperations.getTerrain(list.get(1)
			    .getChildText("name"), terrainList));
	}

    }
}

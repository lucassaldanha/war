package war.game.board.graph;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import war.game.domain.Continent;
import war.game.domain.Terrain;

public class Board {

    private final Graph<Terrain, DefaultEdge> graph = new SimpleGraph<Terrain, DefaultEdge>(
	    DefaultEdge.class);

    private Map<String, Continent> continents = new HashMap<String, Continent>();

    public Board() {
    }

    public Graph<Terrain, DefaultEdge> getGraph() {
	return graph;
    }

    public Map<String, Continent> getContinents() {
	return continents;
    }

    public Continent getContinentByName(String name) {
	return continents.get(name);
    }

    public Terrain getTerrainByName(String name) {
	for (Terrain t : graph.vertexSet()) {
	    if (t.getName().equals(name)) {
		return t;
	    }
	}
	return null;
    }
}

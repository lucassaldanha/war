package war.game.board.builder;

import war.game.board.exception.BoardSystemException;
import war.game.domain.Continent;
import war.game.domain.Player;
import war.game.domain.Terrain;
import war.game.domain.impl.TerrainImpl;

public interface TerrainBuilder {

    /**
     * Cria um terreno vazio no tabuleiro de jogo
     * 
     * @param terrainName
     *            o nome do novo território
     * @return um objeto que representa o território criado
     * @throws BoardSystemException
     *             caso ocorra algum erro na criação do território
     */
    public TerrainImpl createTerrain(String terrainName)
	    throws BoardSystemException;

    /**
     * Cria um terreno vazio no tabuleiro do jogo. O terreno terá como dono o
     * jogador passsado como parâmetro.
     * 
     * @param terrainName
     *            o nome do novo território
     * @param owner
     *            o jogador dono do novo território
     * @return um objeto que representa o território criado
     * @throws BoardSystemException
     *             caso ocorra algum erro na criação do território
     */
    public TerrainImpl createTerrain(String terrainName, Player owner)
	    throws BoardSystemException;

    /**
     * Cria um terreno com exércitos no tabuleiro do jogo. O terreno terá como
     * dono o jogador passsado como parâmetro.
     * 
     * @param terrainName
     *            o nome do novo território
     * @param owner
     *            o jogador dono do novo território
     * @param initialArmy
     *            a quantidade de exércitos que será colocada no território
     * @return um objeto que representa o território criado
     * @throws BoardSystemException
     *             caso ocorra algum erro na criação do território
     */
    public TerrainImpl createTerrain(String terrainName, Player owner,
	    int initialArmy) throws BoardSystemException;

    /**
     * Cria uma fronteira entre os territórios passados como parâmetro
     * 
     * @param aTerrain
     *            o território que irá terá a fronteira criada com outro
     *            território
     * @param neighborTerrains
     *            o outro território que fará fronteira com o primeiro
     * @throws BoardSystemException
     *             caso ocorra algum erro durante a criação da fronteira
     */
    public void createBorder(TerrainImpl aTerrain, TerrainImpl neighborTerrains)
	    throws BoardSystemException;

    public Continent createContinent(String name, int bonus)
	    throws BoardSystemException;

    public void addTerrainToContinent(Terrain t, Continent c)
	    throws BoardSystemException;

}

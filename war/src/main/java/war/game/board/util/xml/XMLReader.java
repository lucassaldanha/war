package war.game.board.util.xml;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class XMLReader {

    public static Iterator<Element> terrains = null;
    public static Iterator<Element> continents = null;
    public static Iterator<Element> borders = null;

    /**
     * Abre o arquivo XML, processa-o e gera um documento com toda a estrutura
     * do arquivo.
     * 
     * @param Location
     *            Local do arquivo XML
     * @return documento que conterá toda a estrutura do arquivo XML
     * @throws JDOMException
     *             , IOException caso ocorra algum erro na leiura do arquivo
     */
    private static Document readXML(String Location) throws JDOMException,
	    IOException {
	// Abre o arquivo XML.
	File f = new File(Location);

	// A classe SAXBuilder processará o XML4
	SAXBuilder sb = new SAXBuilder();

	// Este documento agora possui toda a estrutura do arquivo.
	Document d = sb.build(f);

	return d;

    }

    /**
     * Recupera os elementos filhos (children)
     * 
     * @param root
     *            Nó pai
     * @return Iterator sobre a lista de nós filhos
     * 
     */
    public static Iterator<Element> getChildrenElements(Element root) {

	List<Element> elements = root.getChildren();

	Iterator<Element> i = elements.iterator();

	return i;

    }

    /**
     * Recupera os terrenos e fronteiras
     * 
     * @param Location
     *            Local do arquivo XML
     * @throws JDOMException
     *             , IOException caso ocorra algum erro na leiura do arquivo
     */
    public static void getTerrainsANDBorders(String XML_Location)
	    throws JDOMException, IOException {

	Document d = readXML(XML_Location);

	Iterator<Element> i = getChildrenElements(d.getRootElement());

	// Recupera os Terrenos
	Element t = (Element) i.next();

	terrains = getChildrenElements(t);

	Element c = (Element) i.next();

	continents = getChildrenElements(c);

	// Recupera as fronteiras
	Element b = (Element) i.next();

	borders = getChildrenElements(b);

    }

}

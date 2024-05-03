package Main;

import Modelo.Poblacion;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.HashMap;

public class ParserSAX {
    private HashMap<String, Poblacion> poblaciones;

    public ParserSAX() {
        poblaciones = new HashMap<>();
    }

    public HashMap<String, Poblacion> parse(String xmlFile) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                private String comunidad, provincia, poblacion;
                private double lat, lon;
                private String lastElementName;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    lastElementName = qName;
                }

                @Override
                public void characters(char[] ch, int start, int length) {
                    String information = new String(ch, start, length);
                    information = information.replace("\n", "").trim();

                    if (!information.isEmpty()) {
                        switch (lastElementName) {
                            case "comunidad":
                                comunidad = information;
                                break;
                            case "provincia":
                                provincia = information;
                                break;
                            case "poblacion":
                                poblacion = information;
                                break;
                            case "lat":
                                lat = Double.parseDouble(information);
                                break;
                            case "lon":
                                lon = Double.parseDouble(information);
                                break;
                        }
                    }
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    if (qName.equals("unnodo")) {
                        poblaciones.put(poblacion, new Poblacion(comunidad, provincia, poblacion, lat, lon));
                    }
                }
            };

            saxParser.parse(xmlFile, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return poblaciones;
    }
}
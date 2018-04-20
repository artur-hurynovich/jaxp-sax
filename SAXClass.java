package jaxp.sax;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
class CarsHandler extends DefaultHandler {
    private ArrayList<String> cars;
    private String mark;
    private String model;
    private boolean inMark;
    private boolean inModel;
    private boolean inEngineCapacity;
    CarsHandler() {
        cars = new ArrayList<>();
        inMark = false;
    }
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("mark")) {
            inMark = true;
        }
        if (qName.equalsIgnoreCase("model")) {
            inModel = true;
        }
        if (qName.equalsIgnoreCase("engineCapacity")) {
            inEngineCapacity = true;
        }
    }
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("mark")) {
            inMark = false;
        }
        if (qName.equalsIgnoreCase("model")) {
            inModel = false;
        }
        if (qName.equalsIgnoreCase("engineCapacity")) {
            inEngineCapacity = false;
        }
    }
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inMark) {
            mark = String.valueOf(ch, start, length);
        }
        if (inModel) {
            model = String.valueOf(ch, start, length);
        }
        if (inEngineCapacity) {
            if (Double.valueOf(String.valueOf(ch, start, length)) > 3) {
                cars.add(mark + " " + model);
            }
        }
    }
    @Override
    public String toString() {
        return cars.toString();
    }
}
public class SAXClass {
    public static void main(String[] args) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            CarsHandler carsHandler = new CarsHandler();
            parser.parse("cars.xml", carsHandler);
            System.out.println(carsHandler);
        }
        catch (ParserConfigurationException|SAXException|IOException e) {
            e.printStackTrace();
        }
    }
}

package edu.iastate.cs228.hw5;

import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import edu.iastate.cs228.hw5.mapStructures.BorderSegment;
import edu.iastate.cs228.hw5.mapStructures.ConnType;
import edu.iastate.cs228.hw5.mapStructures.Coordinate;
import edu.iastate.cs228.hw5.mapStructures.ParseErrorException;
import edu.iastate.cs228.hw5.mapStructures.Path;
import edu.iastate.cs228.hw5.mapStructures.TerrainType;
import edu.iastate.cs228.hw5.rendering.TerrainGraph;
import edu.iastate.cs228.hw5.util.NoiseFilterReader;
import edu.iastate.cs228.hw5.util.TerrainScanner;

public class TerrainLoader {

    public interface Feature {
        public static final String kGeometry = "geometry";
        public static final String kFlags = "flags";
        public static final String kWater = "water";
        public static final String kBrush = "brush";
        public static final String kForest = "forest";
        public static final String kDesert = "desert";
        public static final String kSwamp = "swamp";
        public static final String kAlpine = "alpine";
        public static final String kHwy4 = "dividedhwy";
        public static final String kHwy2 = "hwy";
        public static final String kDirt = "unpaved";
        public static final String kRiver = "river";
        public static final String kWall = "barrierwall";
        public static final String kFence = "fence";
        public static final String kCanyon = "canyon";
        public static final String kPath = "testpath";
    }
    
    private static final String kBaseTerrainMsgFormat = "Exception encountered while parsing a \'%s\' terrain section: ";
    private static final String kBaseConnectMsgFormat = "Exception encountered while parsing a \'%s\' connector section: ";

    private static final String kWaterError = String.format(kBaseTerrainMsgFormat, Feature.kWater);
    private static final String kFlagError = String.format(kBaseTerrainMsgFormat, Feature.kFlags);
    private static final String kBrushError = String.format(kBaseTerrainMsgFormat, Feature.kBrush);
    private static final String kDesertError = String.format(kBaseTerrainMsgFormat, Feature.kDesert);
    private static final String kSwampError = String.format(kBaseTerrainMsgFormat, Feature.kSwamp);
    private static final String kAlpineError = String.format(kBaseTerrainMsgFormat, Feature.kAlpine);
    private static final String kForestError = String.format(kBaseTerrainMsgFormat, Feature.kForest);
    private static final String kDirtError = String.format(kBaseConnectMsgFormat, Feature.kDirt);
    private static final String kHwy2Error = String.format(kBaseConnectMsgFormat, Feature.kHwy2);
    private static final String kHwy4Error = String.format(kBaseConnectMsgFormat, Feature.kHwy4);
    private static final String kRiverError = String.format(kBaseConnectMsgFormat, Feature.kRiver);
    private static final String kWallError = String.format(kBaseConnectMsgFormat, Feature.kWall);
    private static final String kFenceError = String.format(kBaseConnectMsgFormat, Feature.kFence);
    private static final String kCanyonError = String.format(kBaseConnectMsgFormat, Feature.kCanyon);
    private static final String kDefaultParseError = "Exception encountered while parsing";
    private static final String kPathError = "Exception encountered while finding Path";

    TerrainGraph tGraph = null;
    TerrainScanner scan = null;
    InputStream inStream;
    Reader rdr = null;

    public TerrainLoader(String textData) {
        rdr = new NoiseFilterReader(new StringReader(textData));
    }
    
    public TerrainLoader(Reader reader) {
        if (!( reader instanceof NoiseFilterReader))
        {
            rdr = new NoiseFilterReader(reader);
        }
        else {
            rdr = reader;
        }
    }

    public TerrainGraph load() throws ParseErrorException {
        scan = new TerrainScanner(rdr);

        // throws exception if fails to construct "blank" graph.
        tGraph = processGeometry();
        
        while (scan.hasNext()) {
        	//System.out.println(scan.next());
        	processTerrainSpec();
        }
        return tGraph;
    }

    private String failGeometryMsg = "Terrain Specification file does not start"
            + " with valid geometry section. Cannot initialize" + " terrain map.";

    private TerrainGraph processGeometry() throws ParseErrorException {        
        scan.next("geometry"); //throws if not there.         
        if (!scan.hasNextInt())
            throw new NoSuchElementException("geometry must include board dimentions");
        int cols = scan.nextInt();
        if (!scan.hasNextInt())
            throw new NoSuchElementException("geometry must include board dimentions");           
        int rows = scan.nextInt();
        Coordinate.setGeometry(cols, rows);
        tGraph = new TerrainGraph(cols, rows);
        return tGraph;
    }

    private void processTerrainSpec( ) throws ParseErrorException {
        String keyword = scan.next().toLowerCase();
        System.out.println("Processing " + keyword);
        switch (keyword) {
        case Feature.kFlags:
            Coordinate[] flags = readTwo(kFlagError);
            tGraph.setFlags(flags[0], flags[1]);
            return;
        case Feature.kWater:
            readCells(TerrainType.kWater, kWaterError);
            return;
        case Feature.kBrush:
            readCells(TerrainType.kBrush, kBrushError);
            return;
        case Feature.kDesert:
        	readCells(TerrainType.kDesert, kDesertError);
        	return;
        case Feature.kSwamp:
        	readCells(TerrainType.kSwamp, kSwampError);
        	return;
        case Feature.kAlpine:
        	readCells(TerrainType.kAlpine, kAlpineError);
        	return;
        case Feature.kForest:
            readCells( TerrainType.kForest, kForestError);
            return;
        case Feature.kDirt:
            readPath( ConnType.dirt, kDirtError);
            return;
        case Feature.kHwy2:
            readPath( ConnType.hwy2, kHwy2Error);
            return;
        case Feature.kHwy4:
            readPath( ConnType.hwy4, kHwy4Error);
            return;
        case Feature.kRiver:
            readPath( ConnType.river, kRiverError);
            return;
        case Feature.kWall:
            readBorderPath(ConnType.wall, kWallError);
            return;
        case Feature.kFence:
        	readBorderPath(ConnType.fence, kFenceError);
        	return;
        case Feature.kCanyon:
        	readBorderPath(ConnType.canyon, kCanyonError);
        	return;
        case Feature.kPath:
            Path path = scanPath(ConnType.path, kPathError);
            tGraph.setSolution(path);
            return;
        default:
            System.out.println("Failure on keyword: " + keyword);
            throw new ParseErrorException(kDefaultParseError);
        }
    }

    /**
     * collects up one or more border segment (barrier) descriptors and passes 
     * them along with the Connector type (there might be different kinds of 
     * border elements in the future) to the tGraph. Reports parse elements. 
     * 
     * @param type the BorderSegment's connector type
     * @param errMsg Text to use in parse error messages
     * 
     * @throws ParseErrorException
     */
    private void readBorderPath(ConnType type, String errMsg) throws ParseErrorException  {
    	try{
	    	List<BorderSegment> borders = new ArrayList<BorderSegment>();
	    	while(scan.hasNextBorderSegment()){
	    		borders.add(scan.nextBorderSegment());
	    	}
	    	if(scan.hasNextInt()) throw new ParseErrorException(errMsg);
	    	tGraph.setBorderEdges(borders, type);
    	}
    	catch(Exception e){
    		System.out.println(e.getMessage());
            throw new ParseErrorException(errMsg);
    	}
    }

    /**
     * This reads the path information that accompanies a Connector description. 
     * Once it has read and validated the path, it calls the terrain graph to 
     * put the terrain information into the graph. 
     * 
     * Uses utility function scanPath to do the heavy lifting. 
     * @param type the type of terrain to be created
     * @param errMsg text to go in the parse error message if needed
     * @throws ParseErrorException if it enounters noSuchElement or the 
     * Coordinates that don't meet the requirements for a path. 
     */
    private void readPath( ConnType type, String errMsg) throws ParseErrorException {
        //capture any exceptions and add errMsg to them before rethrowing
        // forward any valid paths (along with connector type information to 
        // tGraph.setConnectedPath().
    	try{
    		Path p = scanPath(type, errMsg);
    		tGraph.setConnectedPath(p, type);
    	}
    	catch(Exception e){
    		System.out.println(e.getMessage());
            throw new ParseErrorException(errMsg);
    	}
    }
    
    private Path scanPath(ConnType type, String errMsg) throws ParseErrorException{
    if (! scan.hasNextCoord()){
        String msg = String.format("%s: Coordinate must follow %s specifier",
                errMsg, type.name());
        throw new NoSuchElementException(msg);
    }
    Coordinate c1 = scan.nextCoord();
    if (!scan.hasNextCoord()){
        String msg = String.format("%s: %s Path requires at least two coordinates",
                errMsg, type.name());
        throw new NoSuchElementException(msg);
    }
    try {
        Path path = new Path(c1, scan.nextCoord());
        while (scan.hasNextCoord()){
            path.add(scan.nextCoord());
        }
        return path;
    }
    catch (Exception e){
        System.out.println(e.getMessage());
        throw new ParseErrorException(errMsg + "Path must be adjacent elements");            
    }
}

    /**
     * This collects up the coordinate information for a terrain section, and then
     * passes that information (in object form) to the terrain graph. 
     * @param terrain The type of terrain to which these coordinates belong. 
     * @param errMessage text to go in the parse error if we have an int left over
     * after consuming all coordinate pairs (once hasNextCoord() returns false, ask 
     * hasNextInt(). Answer should be no. 
     * 
     * @throws ParseErrorException
     */
    private void readCells(TerrainType terrain, String errMessage) throws ParseErrorException {
    	List<Coordinate> coords = new ArrayList<Coordinate>();
    	while(scan.hasNextCoord()){
    		Coordinate temp = scan.nextCoord();
    		if(!checkCoord(temp)) throw new ParseErrorException(errMessage + ": Coordinate not valid");
    		coords.add(temp);
    	}
    	if(scan.hasNextInt()) throw new ParseErrorException(errMessage);
    	tGraph.setTerrain(coords, terrain);
    }
    
    /**
     * A utility function that scans as many coordinates as it can. It should 
     * never be called unless the syntax requires there be at least one
     * coordinate at this point. If it finds none, it returns an empty list. The
     * caller should consider an empty list a sign there was an error. 
     * @return
     */
    private List<Coordinate> scanCoordList(){
    	List<Coordinate> coords = new ArrayList<Coordinate>();
    	while(scan.hasNextCoord()){
    		coords.add(scan.nextCoord());
    	}
        return coords;
    }
    
    /**
     * Takes in a coordinate and checks to see if it is valid for the current board size
     * 
     * @param c
     * @return Boolean
     */
    private boolean checkCoord(Coordinate c)
    { 
    	return tGraph.isValidVertex(c.getHexX(), c.getHexY());
    }
    
    /**
     * This consumes two Board Coordinates (four integers from the stream
     * and returns two coordinate objects , 
     * @param errMessage text to go in the parse error if we don't find
     * two coordinates
     * @return the coordinates. 
     */
    private Coordinate[] readTwo(String errMessage) throws ParseErrorException{
    	Coordinate[] coordObjs = new Coordinate[2];
    	for(int i = 0; scan.hasNextCoord(); i++){
    		Coordinate temp = scan.nextCoord();
    		if(!checkCoord(temp)) throw new ParseErrorException(errMessage + ": Coordinate not valid");
    		coordObjs[i] = temp;
    	}
    	Coordinate.Pair p = coordObjs[0].getDelta(coordObjs[1]);
    	if(p.x == 0 && p.y == 0) throw new ParseErrorException(errMessage);
        return coordObjs;
    }

}

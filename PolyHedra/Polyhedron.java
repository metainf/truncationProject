import java.util.ArrayList;
public class Polyhedron
{
	private ArrayList<Vertex> points;
	private ArrayList<Edge> edges;
	private ArrayList<Face> sides;

	
	public Polyhedron(ArrayList<Vertex> inputPoints, ArrayList<Edge> inputEdges,ArrayList<Face> inputSides) {
		points=inputPoints;
		edges=inputedges;
		sides=inputsides;
}
	public static ArrayList<Face> makePolyhedronFaces(ArrayLst<Vertex> inputPoints, ArrayList<Edge> inputEdges){
		ArrayList<Vertex> copyPoints=inputPoints;
		ArrayList<Edge> copyEdge=inputEdges;
		ArrayList<Face> faces=new ArrayList();
		while(copyPoints.size()<0){
			Vertex currentPoint=copyPoints.get(0);
			ArrayList<Edge> edgesHasCurrentPoint= new ArrayList();
			for(Edge e:copyEdge){
				if(e.hasPoint(currentPoint)) edgesHasCurrentPoint.add(e);
			}
			ArrayList<ArrayList<Edge>> pairs=new ArrayList();
			for(int i=0;i<edgesHasCurrentPoint.size();i++){
				for(int j=i+1;j<edgesHasCurrentPoint.size();j++){
					ArrayList <Edge> pair=new ArrayList();
					pair.add(edgesHasCurrentPoint.get(i));
					pair.add(edgesHasCurrentPoint.get(j));
					pairs.add(pair);
				}
			}
			double shortestDistance=Double.MAX_VALUE
			for(int i=0;i<pairs.size();i++){
				if(pairs.get(i).get(0).otherVert(currentPoint).distance(pairs.get(i).get(1).otherVert(currentPoint))<shortestDistance){
					shortestDistance=pairs.get(i).get(0).otherVert(currentPoint).distance(pairs.get(i).get(1).otherVert(currentPoint));
				}
			}
			for(int i=0;i<pairs.size();i++){
				if(pairs.get(i).get(0).otherVert(currentPoint).distance(pairs.get(i).get(1).otherVert(currentPoint))>shortestDistance){
					pairs.remove(i);
					i=0;
				}
			}
			for(int i=0;i<pairs.size();i++){
				ArrayList <Edge> pair=pairs.get(i);
				ArrayList <Edge> face=
			}
		}
	}
	//This requires that the length that we are going to truncate things by is less than the length of the shortest
	//edge. There is full truncation;
	public void pointTrun(Vertex point, double chop){
		ArrayList<Edge> tempEdges=new ArrayList(); //used to store the edges before truncation
		ArrayList<Edge> newEdges=new ArrayList();
		ArrayList<Vertex> newPoints=new ArrayList();
		ArrayList<Face> newFaces=new ArrayList();
		ArrayList<Edge> newFace=new ArrayList();// used to make the new face created from truncating.
		//generates the list of edges that will be changed
		for(Edge e : edges){
			if(e.hasPoint(point)){
				tempEdges.add(e);
			}
		}
		//generates the list of faces that will be changed
		for(Edge e: tempEdges){
			for(Face f: sides){
				if(f.hasEdge(e)){
					newFaces.add(f);
				}
			}
		}
		//supermessy way of getting rid of duplicates faces
		for(int i=0;i<newFaces.size();i+=0){
			if(newFaces.indexOf(newFaces.get(i)).equals(newFaces.lastIndexOf(newFaces.get(i)))){
				i++;
			}
			else{
				newFaces.remove(newFaces.get(i));
				i=0;
			}
		}
		//removes the faces that are going to be changed from the actual list
		for(Face f: newFaces){
			if(sides.contains(f)){
				sides.remove(f);
			}
		}
		//removes  the edges that are going to be changed from the actual list
		for(Edge e: tempEdges){
			if(edges.contains(e)){
				edges.remove(e);
			}
		}
		/*This for loop does the truncation, aka, delete the old point, and creates the new point and edges
		*It will also add the new points and edges to the faces that get chopped.
		*At the end, the only left is to make the new face created by the truncation
		*/
		for(Edge e: tempEdges){
			//if the truncation goes to a point
			if(e.distance()==chop){
				if(e.getVertices()[0].equals(point)){
					Vertex notTrunPt=e.getVertices()[1];
				}
				else{
					Vertex notTrunPt=e.getVertices()[0];
				}
				Vertex newVertex= notTrunPt;
				for(int i=0;i<newFaces.size();i++){
					Face f=newFaces.get(i);
					if(f.hasEdge(e)){
						newFaces.remove(f);
						f.remove(e);
						newFaces.add(i,f);
					}
				}
				newPoints.add(newVertex);
			}
			else{
				//finds the point that does not change in the edge
				if(e.getVertices()[0].equals(point)){
					Vertex notTrunPt=e.getVertices()[1];
				}
				else{
					Vertex notTrunPt=e.getVertices()[0];
				}
				//creates the vector to get the new point
				double[] vector={notTrunPt.getX()-point.getX(),
						 notTrunPt.getY()-point.getY(),
						 notTrunPt.getZ()-point.getZ()};
				//creates the new point
				Vertex newVertex= new Vertex(point.getX()-(vector[0]/e.distance()*chop),
				                             point.getX()-(vector[1]/e.distance()*chop),
				                             point.getX()-(vector[2]/e.distance()*chop))
				//creates the new edge
				Edge newEdge= new Edge(notTrunpt,newVertex);
				//adds the new edge to the faces that had the old edge
				for(int i=0;i<newFaces.size();i++){
					Face f=newFaces.get(i);
					if(f.hasEdge(e)){
						newFaces.remove(f);
						f.remove(e);
						f.add(newEdge);
						newFaces.add(i,f);
					}
				}
				newEdges.add(e);
				newPoints.add(newVertex);
			}
		}
		/* This will create the face that is created by truncating the point
		*It does so by going through the list of new points, and creating the new face from that
		*/
		boolean isdone=false;
		Vertex firstPoint=newPoints.get(0);
		Vertex currentPoint=firstPoint;
		Vertex lastPoint=firstPoint;
		Vertex nextPoint;
		double smallestDistance=Double.MAX_VALUE;
		while(!isdone){
			//finds the next point
			for(Vertex comparePoint: newPoints){
				if(currentPoint.equals(comparePoint)){
				}
				else if(comparePoint.equals(lastPoint){
				}
				else{
					if(currentPoint.distance(comparePoint)<smallestDistance){
						smallestDistance=currentPoint.distance(comparePoint);
						nextPoint=comparePoint;
					}
				}
				
			}
			//creates and edds the edge made from the current point and the next point
			newFace.add(new Edge(currentPoint,nextPoint));
			newEdges.add(new Edge(currentPoint,nextPOint));
			//moves to the next point
			lastPoint=currentPoint;
			currentPoint=nextPoint;
			smallestDistance=Double.MAX_VALUE;
			//checks if the loop is done
			if(nextPoint.equals(firstPoint)){
				newFace.add(new Edge(lastPoint,nextPoint));
				newEdges.add(new Edge(lastPoint,nextPoint));
				isdone=true;
			}
		}
		//adds the edges of the new face created by truncation to the faces choped by truncation
		for(int i=0;i<newFaces.size();i++){
			Face currentFace=newFaces.get(i);
			ArrayList<Edge> edgesInCurrentFace=currentFace.returnEdges();
			ArrayList<Edge> edgesInNewFace=newFace.returnEdges();
			ArrayList<Vertex> pointsInCurrentFace= new ArrayList();
			for(Edge e:edgesInCurrentFace){
				if(pointsInCurrentFace.indexOf(e.getVertices()[0])==-1){
					pointsInCurrentFace.add(e.getVertices()[0]);
				}
				if(pointsInCurrentFace.indexOf(e.getVertices()[1])==-1){
					pointsInCurrentFace.add(e.getVertices()[1]);
				}
			}
			for(Edge e:edgesInNewFace){
				if(pointsInCurrentFace.indexOf(e.getVertices()[0])!=-1 && pointsInCurrentFace.indexOf(e.getVertices()[1])!=-1){
					currentFace.add(e);
				}
			}
			newFaces.remove(i);
			newFaces.add(i,currentFace);
		}
		newFaces.add( new Face(newFace));
		points.add(newPoints);
		edges.add(newEdges);
		sides.add(newFaces);
	}
	public boolean hasVertexAtXY(double x, double y)
	{
		for (Vertex v: points)
		{
			if (v.getX == x && v.getX == y)
			return true;
		}
		return false;
	}
	
	public boolean hasEdgeAtXY(double x3, double y3)
	{
		double x1,x2,y1,y2;
		Vertex[] verticies = new Vertex[2];
		for (Edge e: edges)
		{
			verticies = e.getVerticies();
			x1 = verticies[0].getX;
			x2 = verticies[1].getX;
			y1 = verticies[0].getY;
			y2 = verticies[1].getY;
			if (y3-y1 == (y2-y1)*(x3-x1)/(x2-x1))
			{ 
				return true;
			}
		}
		return false;
			
	}
	
	public Edge getEdgeAtXY(double x3, double y3)
	{
		double x1,x2,y1,y2;
		Vertex[] verticies = new Vertex[2];
		currentEdge
		for (Edge e: edges)
		{
			verticies = e.getVerticies();
			x1 = verticies[0].getX;
			x2 = verticies[1].getX;
			y1 = verticies[0].getY;
			y2 = verticies[1].getY;
			if (y3-y1 == (y2-y1)*(x3-x1)/(x2-x1))
			{ 
				currentEdge = e;
			}
		}
		return currentEdge;
			
	}
	
	public Vertex getVertexAtXY(double x, double y)
	{
		Vertex closest;	
		points.get(0).getZ = z		//gets a z coordinate to check against
		for (Vertex v: points)
		{
			if (v.getX == x && v.getX == y && v.getZ > z)	
			{
				closest = v;
				z = v.getZ;
			}
		}
		return closest;
	}
	public ArrayList<Face> getFaces()
	{
		return sides;
	}
	
	public ArrayList<Edge> initiateEdges(ArrayList<Vertex> points)
	{
		ArrayList tempPoints = new ArrayList<Vertex>();
		tempPoints = points
		ArrayList edges = new ArrayList<Edge>
	
		do
		{
			for (int i = 0; i < tempPoints.size; i ++)
			{
				if (tempPoints.get(0).distance() = 1)
				{
					edges.add(new Edge(tempPoints.get(0), tempPoints.get(i));
				}
			}
			tempPoints.remove(0);
		}
		while (tempPoints.size > 0);
	}
	public static ArrayList<Vertex> readVerticesTetrahedron(String fileName) {
		ArrayList<Vertex> output = new ArrayList<Vertex>();
 
		try {
			Scanner scan = new Scanner(new FileReader(fileName));
			int lineCounter = 0;
			while (scan.hasNextLine()) {
				String nextVertex = scan.nextLine();
				if(lineCounter >= 3 && lineCounter <=6) {
				StringTokenizer lineN = new StringTokenizer(nextVertex);
				double x = Double.parseDouble(lineN.nextToken());
				double y = Double.parseDouble(lineN.nextToken());
				double z = Double.parseDouble(lineN.nextToken());
				output.add(new Vertex(x,y,z));
				}
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return output;
	}
 
 
	public static ArrayList<Vertex> readVerticesCube(String fileName) {
		ArrayList<Vertex> output = new ArrayList<Vertex>();
 
		try {
			Scanner scan = new Scanner(new FileReader(fileName));
			int lineCounter = 0;
			while (scan.hasNextLine()) {
				String nextVertex = scan.nextLine();
				if(lineCounter>=9 && lineCounter <=16) {
				StringTokenizer lineN = new StringTokenizer(nextVertex);
				double x = Double.parseDouble(lineN.nextToken());
				double y = Double.parseDouble(lineN.nextToken());
				double z = Double.parseDouble(lineN.nextToken());
				output.add(new Vertex(x,y,z));
				}
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return output;
	}
 
 
	public static ArrayList<Vertex> readVerticesOctahedron(String fileName) {
		ArrayList<Vertex> output = new ArrayList<Vertex>();
 
		try {
			Scanner scan = new Scanner(new FileReader(fileName));
			int lineCounter = 0;
			while (scan.hasNextLine()) {
				String nextVertex = scan.nextLine();
				if(lineCounter>=19 && lineCounter <=24) {
				StringTokenizer lineN = new StringTokenizer(nextVertex);
				double x = Double.parseDouble(lineN.nextToken());
				double y = Double.parseDouble(lineN.nextToken());
				double z = Double.parseDouble(lineN.nextToken());
				output.add(new Vertex(x,y,z));
				}
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return output;
	}
 
 
	public static ArrayList<Vertex> readVerticesDodecahedron(String fileName) {
		ArrayList<Vertex> output = new ArrayList<Vertex>();
 
		try {
			Scanner scan = new Scanner(new FileReader(fileName));
			int lineCounter = 0;
			while (scan.hasNextLine()) {
				String nextVertex = scan.nextLine();
				if(lineCounter>=30 && lineCounter <=41) {
				StringTokenizer lineN = new StringTokenizer(nextVertex);
				double x = Double.parseDouble(lineN.nextToken());
				double y = Double.parseDouble(lineN.nextToken());
				double z = Double.parseDouble(lineN.nextToken());
				output.add(new Vertex(x,y,z));
				}
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return output;
	}
 
	public static ArrayList<Vertex> readVerticesIcosahedron(String fileName) {
		ArrayList<Vertex> output = new ArrayList<Vertex>();
 
		try {
			Scanner scan = new Scanner(new FileReader(fileName));
			int linelineCounter = 0;
			while (scan.hasNextLine()) {
				String nextVertex = scan.nextLine();
				if(lineCounter>=47 && lineCounter <= 66) {
				StringTokenizer lineN = new StringTokenizer(nextVertex);
				double x = Double.parseDouble(lineN.nextToken());
				double y = Double.parseDouble(lineN.nextToken());
				double z = Double.parseDouble(lineN.nextToken());
				output.add(new Vertex(x,y,z));
				}
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
 
		return output;
	}
}

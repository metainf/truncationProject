
package PolyHedra;



import java.util.ArrayList;
import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.awt.Point;
public class Polyhedron
{
    private ArrayList<Vertex> points;
    private ArrayList<Edge> edges;
    private ArrayList<Face> sides;

    public Polyhedron(ArrayList<Vertex> inputPoints, ArrayList<Edge> inputEdges,ArrayList<Face> inputSides) {
        points= new ArrayList<>(inputPoints);
        edges= new ArrayList<>(inputEdges);
        sides= new ArrayList<>(inputSides);
    }
    public Polyhedron(String fileName,String shape){
        switch(shape)
        {
            case "Tetrahedron": points=new ArrayList(readVerticesTetrahedron(fileName));
                                edges=new ArrayList(initiateEdges(points));
                                sides= new ArrayList(makePolyhedronFaces(points,edges));
                                
            break;
            case "Cube": points=new ArrayList(readVerticesCube(fileName));
                                edges=new ArrayList(initiateEdges(points));
                                sides=new ArrayList(makePolyhedronFaces(points,edges));
                                System.out.println(sides);
            break;
            case "Octahedron": points=new ArrayList(readVerticesOctahedron(fileName));
                                edges=new ArrayList(initiateEdges(points));
                                sides=new ArrayList(makePolyhedronFaces(points,edges));
            break;
            case "Dodecahedron": points=new ArrayList(readVerticesDodecahedron(fileName));
                                edges=new ArrayList(initiateEdges(points));
                                sides=new ArrayList(makePolyhedronFaces(points,edges));
            break;
            case "Icosahedron": points=new ArrayList(readVerticesIcosahedron(fileName));
                                edges=new ArrayList(initiateEdges(points));
                                sides=new ArrayList(makePolyhedronFaces(points,edges));
            break;
        }
    }

    public static ArrayList<Face> makePolyhedronFaces(ArrayList<Vertex> inputPoints, ArrayList<Edge> inputEdges){
        ArrayList<Vertex> copyPoints=new ArrayList<>(inputPoints);
        ArrayList<Edge> copyEdge=new ArrayList<>(inputEdges);
        ArrayList<Face> faces=new ArrayList<>();
        ArrayList<Face> facesOut=new ArrayList<>();
        while(copyPoints.size()>0){
            Vertex currentPoint=(Vertex)copyPoints.get(0);
            ArrayList<Edge> edgesHasCurrentPoint= new ArrayList<>();
            for(Edge e:copyEdge){
                if(e.hasPoint(currentPoint)) edgesHasCurrentPoint.add(e);
            }
            ArrayList<ArrayList<Edge>> pairs=new ArrayList<>();
            for(int i=0;i<edgesHasCurrentPoint.size();i++){
                for(int j=i+1;j<edgesHasCurrentPoint.size();j++){
                    ArrayList <Edge> pair=new ArrayList<>();
                    pair.add((Edge)edgesHasCurrentPoint.get(i));
                    pair.add((Edge)edgesHasCurrentPoint.get(j));
                    pairs.add(pair);
                }
            }
            double shortestDistance=Double.MAX_VALUE;
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
                ArrayList <Edge> pair=new ArrayList(pairs.get(i));
                ArrayList <Edge> face= new ArrayList();
                for(Edge e:copyEdge){
                    boolean inPlane=true;
                    for(Vertex v:e.getVertices()){
                        double[] v1={pair.get(0).otherVert(currentPoint).getX()-currentPoint.getX(),
                                pair.get(0).otherVert(currentPoint).getY()-currentPoint.getY(),
                                pair.get(0).otherVert(currentPoint).getZ()-currentPoint.getZ()};
                        double[] v2={pair.get(1).otherVert(currentPoint).getX()-currentPoint.getX(),
                                pair.get(1).otherVert(currentPoint).getY()-currentPoint.getY(),
                                pair.get(1).otherVert(currentPoint).getZ()-currentPoint.getZ()};
                        double[] v3={v.getX()-currentPoint.getX(),
                                v.getY()-currentPoint.getY(),
                                v.getZ()-currentPoint.getZ()};
                        double[] cross={v1[1]*v2[2]-v1[2]*v2[1],
                                v1[2]*v2[0]-v1[0]*v2[2],
                                v1[0]*v2[1]-v1[1]*v2[0],};
                        if(v3[0]*cross[0]+v3[1]*cross[1]+v3[2]*cross[2]!=0.0){
                            inPlane=false;
                        }
                    }
                    if(inPlane){
                        face.add(e);
                    }
                }
                faces.add(new Face(face));
                System.out.println(face+""+currentPoint);
            }
            System.out.println();
            copyPoints.remove(0);
        }
        for(int i=0;i<faces.size();i++){
            for(int j=i+1;j<faces.size();j+=0){
                if(faces.get(j).equals(faces.get(i))){
                    faces.remove(j);
                    j=i+1;
                }
                else{
                    j++;
                }
            }
        }
        return faces;
    }
    //This requires that the length that we are going to truncate things by is less than the length of the shortest
    //edge. There is full truncation;
    public void pointTrun(Vertex point, double chop){
        ArrayList<Edge> tempEdges=new ArrayList<Edge>(); //used to store the edges before truncation
        ArrayList<Edge> newEdges=new ArrayList<Edge>();
        ArrayList<Vertex> newPoints=new ArrayList<Vertex>();
        ArrayList<Face> newFaces=new ArrayList<Face>();
        ArrayList<Edge> newFace=new ArrayList<Edge>();// used to make the new face created from truncating.
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
            if(newFaces.indexOf(newFaces.get(i))==(newFaces.lastIndexOf(newFaces.get(i)))){
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
                Vertex notTrunPt;
                if(e.getVertices()[0].equals(point)){
                    notTrunPt=e.getVertices()[1];
                }
                else{
                    notTrunPt=e.getVertices()[0];
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
                Vertex notTrunPt;
                //finds the point that does not change in the edge
                if(e.getVertices()[0].equals(point)){
                    notTrunPt=e.getVertices()[1];
                }
                else{
                    notTrunPt=e.getVertices()[0];
                }
                //creates the vector to get the new point
                double[] vector={notTrunPt.getX()-point.getX(),
                        notTrunPt.getY()-point.getY(),
                        notTrunPt.getZ()-point.getZ()};
                //creates the new point
                Vertex newVertex= new Vertex(point.getX()-(vector[0]/e.distance()*chop),
                        point.getX()-(vector[1]/e.distance()*chop),
                        point.getX()-(vector[2]/e.distance()*chop));
                //creates the new edge
                Edge newEdge= new Edge(notTrunPt,newVertex);
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
        Vertex nextPoint=firstPoint;
        double smallestDistance=Double.MAX_VALUE;
        while(!isdone){
            //finds the next point
            for(Vertex comparePoint: newPoints){
                if(currentPoint.equals(comparePoint)){
                }
                else if(comparePoint.equals(lastPoint)){
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
            newEdges.add(new Edge(currentPoint,nextPoint));
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
            ArrayList<Edge> edgesInNewFace=newFace;
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
        for(Vertex p:newPoints){
            points.add(p);
        }
        for(Edge e:newEdges){
            edges.add(e);
        }
        for(Face f:newFaces){
            sides.add(f);
        }
        for(int i=0;i<points.size();i++){
            if(points.get(i).equals(point)){
                points.remove(i);
            }
        }
        for(int i=0;i<points.size();i++){
            for(int j=i+1;j<points.size();j+=0){
                if(points.get(j).equals(points.get(i))){
                    points.remove(j);
                    j=i+1;
                }
                else{
                    j++;
                }
            }
        }
    }

    public boolean hasVertexAtXY(double x, double y)
    {
        for (Vertex v: points)
        {
            if (v.getX() == x && v.getX() == y)
                return true;
        }
        return false;
    }

    public boolean hasEdgeAtXY(double x3, double y3)
    {
        double x1,x2,y1,y2;
        Vertex[] vertices = new Vertex[2];
        for (Edge e: edges)
        {
            vertices = e.getVertices();
            x1 = vertices[0].getX();
            x2 = vertices[1].getX();
            y1 = vertices[0].getY();
            y2 = vertices[1].getY();
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
        Vertex[] vertices = new Vertex[2];
        Edge currentEdge=new Edge();
        for (Edge e: edges)
        {
            vertices = e.getVertices();
            x1 = vertices[0].getX();
            x2 = vertices[1].getX();
            y1 = vertices[0].getY();
            y2 = vertices[1].getY();
            if (y3-y1 == (y2-y1)*(x3-x1)/(x2-x1))
            { 
                currentEdge = e;
            }
        }
        return currentEdge;

    }

    public Vertex getVertexAtXY(double x, double y)
    {
        Vertex closest= new Vertex();        
        double  z=points.get(0).getZ();                //gets a z coordinate to check against
        for (Vertex v: points)
        {
            if (v.getX() == x && v.getX() == y && v.getZ() > z)        
            {
                closest = v;
                z = v.getZ();
            }
        }
        return closest;
    }

    public ArrayList<Face> getFaces()
    {
        return sides;
    }

    public static ArrayList<Edge> initiateEdges(ArrayList<Vertex> points)
    {
        ArrayList<Vertex> tempPoints = new ArrayList<Vertex>(points);
        ArrayList<Edge> edges = new ArrayList<Edge>();

        do
        {
            for (int i = 0; i < tempPoints.size(); i ++)
            {
                if (tempPoints.get(0).distance(tempPoints.get(i)) == 1)
                {
                    edges.add(new Edge(tempPoints.get(0), tempPoints.get(i)));
                }
            }
            tempPoints.remove(0);
        }
        while (tempPoints.size() > 0);
        return edges;
    }

    public static ArrayList<Vertex> readVerticesTetrahedron(String fileName) {
        ArrayList<Vertex> output = new ArrayList<Vertex>();

        try {
            Scanner scan = new Scanner(new File(fileName));
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
                lineCounter++;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    public static ArrayList<Vertex> readVerticesCube(String fileName) {
        ArrayList<Vertex> output = new ArrayList<Vertex>();

        try {
            Scanner scan = new Scanner(new File(fileName));
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

                lineCounter++;

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
                lineCounter ++;
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
                lineCounter ++;
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
            int lineCounter = 0;
            while (scan.hasNextLine()) {
                String nextVertex = scan.nextLine();
                if(lineCounter>=47 && lineCounter <= 66) {
                    StringTokenizer lineN = new StringTokenizer(nextVertex);
                    double x = Double.parseDouble(lineN.nextToken());
                    double y = Double.parseDouble(lineN.nextToken());
                    double z = Double.parseDouble(lineN.nextToken());
                    output.add(new Vertex(x,y,z));
                }
                lineCounter ++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }
public void rotate (double p1X, double p1Y, double p2X, double p2Y)
    {
        this.rotate(Math.asin((p2Y - p1Y) / Math.sqrt(1 + Math.pow(p2Y, 2))),
        Math.asin((p2X - p1X) / Math.sqrt(1 + Math.pow(p2X, 2))),
        Math.asin((p2X - p1X) / Math.sqrt(Math.pow(p2X, 2) + Math.pow(p2Y, 2))));
    }
    
    public void rotate(double a, double b, double c)
    {
        double x, y,z;
        Vertex oldVertex;
        Vertex newVertex;
        double [] oldCoords = new double[3];
        int m,n;
        n = 0; 
        m = 0;

        for (int i = 0; i < sides.size(); i++)
        {
            for (int v = 0; v < sides.get(i).returnEdges().size(); v ++)
            {
                for (int k = 0; k < 2; k ++)
                {
                    oldCoords = sides.get(i).returnEdges().get(v).getVertexes().get(k).getCoords();
                    x = oldCoords[0];
                    y = oldCoords[1];
                    z = oldCoords[2];
                    newVertex = new Vertex(x * Math.cos(b) * Math.cos(c) + y * (Math.cos(a) * Math.sin(c) - Math.cos(c)  * Math.sin(a) *  Math.sin(b)) + z * (Math.cos(a) *  Math.cos(c) *  Math.sin(b) + Math.sin(a) * Math.sin(c)), 
                        -x * Math.cos(b) * Math.sin(c) + z  * (Math.cos(c)  * Math.sin(a) - Math.cos(a) * Math.sin(b) *  Math.sin(c)) + y * (Math.cos(a)  * Math.cos(c) + Math.sin(a) * Math.sin(b) * Math.sin(c)),
                        z * Math.cos(a) * Math.cos(b) - y * Math.sin(a) * Math.cos(b) - x * Math.sin(b));
                    sides.get(i).returnEdges().get(n).getVertexes().set(k, newVertex);
                    (edges.get(n)).getVertexes().set(k, newVertex);
                    points.set(m, newVertex);
                    m++;
                }
                n ++;
            }

        }
    }
    public ArrayList<Vertex> getVerts(){
        return points;
    }
}

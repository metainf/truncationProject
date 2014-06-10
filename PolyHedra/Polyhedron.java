package polyhedra;

import java.util.ArrayList;
import java.util.*;
import java.io.File;

public class Polyhedron
{
    private ArrayList<Vertex> vertexs;
    private ArrayList<Edge> edges;
    private ArrayList<Face> faces;
    
    public static final int TETRAHEDRON = 0;
    public static final int CUBE = 1;
    public static final int OCTAHEDRON = 2;
    public static final int DODECAHEDRON = 3;
    public static final int ICOSAHEDRON = 4;

    public Polyhedron(ArrayList<Vertex> inputPoints, ArrayList<Edge> inputEdges,ArrayList<Face> inputSides)
    {
        vertexs= new ArrayList<Vertex>(inputPoints);
        edges= new ArrayList<Edge>(inputEdges);
        faces= new ArrayList<Face>(inputSides);
    }
    
    public Polyhedron(int shape)
    {
        switch(shape)
        {
            case TETRAHEDRON: vertexs=new ArrayList<Vertex>(this.readVerticesTetrahedron("PolyhedraVertices.txt"));
                                edges=new ArrayList<Edge>(initiateEdges(vertexs));
                                faces= new ArrayList<Face>(makePolyhedronFaces(vertexs,edges));
                                
            break;
            case CUBE: vertexs=new ArrayList<Vertex>(this.readVerticesCube("PolyhedraVertices.txt"));
                                edges=new ArrayList<Edge>(initiateEdges(vertexs));
                                faces=new ArrayList<Face>(makePolyhedronFaces(vertexs,edges));
            break;
            case OCTAHEDRON: vertexs=new ArrayList<Vertex>(this.readVerticesOctahedron("PolyhedraVertices.txt"));
                                edges=new ArrayList<Edge>(initiateEdges(vertexs));
                                faces=new ArrayList<Face>(makePolyhedronFaces(vertexs,edges));
            break;
            case DODECAHEDRON: vertexs=new ArrayList<Vertex>(this.readVerticesDodecahedron("PolyhedraVertices.txt"));
                                edges=new ArrayList<Edge>(initiateEdges(vertexs));
                                faces=new ArrayList<Face>(makePolyhedronFaces(vertexs,edges));
            break;
            case ICOSAHEDRON: vertexs=new ArrayList<Vertex>(this.readVerticesIcosahedron("PolyhedraVertices.txt"));
                                edges=new ArrayList<Edge>(initiateEdges(vertexs));
                                faces=new ArrayList<Face>(makePolyhedronFaces(vertexs,edges));
            break;
        }
        //System.out.println(points.size()+"points");
        //System.out.println(edges.size()+"edges");
        //System.out.println(sides.size()+"faces");
    }

    public static ArrayList<Face> makePolyhedronFaces(ArrayList<Vertex> inputPoints, ArrayList<Edge> inputEdges)
    {
        ArrayList<Vertex> copyPoints=new ArrayList<Vertex>(inputPoints);
        ArrayList<Edge> copyEdge=new ArrayList<Edge>(inputEdges);
        ArrayList<Face> faces=new ArrayList<Face>();
        ArrayList<Face> facesOut=new ArrayList<Face>();
        while(copyPoints.size()>0)
        {
            Vertex currentPoint=(Vertex)copyPoints.get(0);
            ArrayList<Edge> edgesHasCurrentPoint= new ArrayList<Edge>();
            for(Edge e:copyEdge)
            {
                if(e.hasVertex(currentPoint)) edgesHasCurrentPoint.add(e);
            }
            ArrayList<ArrayList<Edge>> pairs=new ArrayList<ArrayList<Edge>>();
            for(int i=0;i<edgesHasCurrentPoint.size();i++)
            {
                for(int j=i+1;j<edgesHasCurrentPoint.size();j++)
                {
                    ArrayList <Edge> pair=new ArrayList<Edge>();
                    pair.add((Edge)edgesHasCurrentPoint.get(i));
                    pair.add((Edge)edgesHasCurrentPoint.get(j));
                    pairs.add(pair);
                }
            }
            double shortestDistance=Double.MAX_VALUE;
            for(int i=0;i<pairs.size();i++)
            {
                if(pairs.get(i).get(0).otherVert(currentPoint).distanceTo(pairs.get(i).get(1).otherVert(currentPoint))<shortestDistance)
                {
                    shortestDistance=pairs.get(i).get(0).otherVert(currentPoint).distanceTo(pairs.get(i).get(1).otherVert(currentPoint));
                }
            }
            for(int i=0;i<pairs.size();i++)
            {
                if(pairs.get(i).get(0).otherVert(currentPoint).distanceTo(pairs.get(i).get(1).otherVert(currentPoint))>shortestDistance+.01)
                {
                    pairs.remove(i);
                    i=0;
                }
            }

            for(int i=0;i<pairs.size();i++)
            {
                ArrayList <Edge> pair=new ArrayList<Edge>(pairs.get(i));
                ArrayList <Edge> face= new ArrayList<Edge>();
                for(Edge e:copyEdge)
                {
                    boolean inPlane=true;
                    for(Vertex v:e.getVertices())
                    {
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
                        if((v3[0]*cross[0]+v3[1]*cross[1]+v3[2]*cross[2])>0.01 || (v3[0]*cross[0]+v3[1]*cross[1]+v3[2]*cross[2])<-0.01)
                        {
                            inPlane=false;
                        }
                    }
                    if(inPlane)
                    {
                        face.add(e);
                    }
                }
                faces.add(new Face(face));
            }
            copyPoints.remove(0);
        }
        for(int i=0;i<faces.size();i++)
        {
            for(int j=i+1;j<faces.size();j+=0)
            {
                if(faces.get(j).equals(faces.get(i)))
                {
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
    
    public void truncPercent(Vertex vertexToTrunc, int percent)
    {
        double truePercent=percent/100.0;
        System.out.println(truePercent);
        ArrayList<Edge> edgesToTrunc=new ArrayList(); //should start with some number, and end with zero edges
        ArrayList<Edge> edgesAfterTrunc=new ArrayList();//should start with zero edges, and end with some number
        ArrayList<Face> facesToModify=new ArrayList();//should start with some number, and end with zero faces
        ArrayList<Face> facesAfterModify=new ArrayList();//should start with zero faces, and end with some number
        ArrayList<Vertex> vertsCreated=new ArrayList();
        ArrayList<Edge> edgesCreated=new ArrayList();
        Face faceCreated=new Face(edges);//remove later
        //This first part is to setup the truncation process.
        //puts the edges that are going to be truncated into the list
        for(Edge e: edges){
            if(e.hasVertex(vertexToTrunc)){
                edgesToTrunc.add(e);
            }
        }
        //removes the previous edges from the master list
        for(Edge e: edgesToTrunc){
            edges.remove(e);
        }
        //puts the edges that are going to be truncated into the list
        for(Face e: faces){
            if(e.hasVertex(vertexToTrunc)){
                facesToModify.add(e);
            }
        }
        //removes the previous faces from the master list.
        for(Face e:facesToModify){
            faces.remove(e);
        }
        vertexs.remove(vertexToTrunc);
        //setup complete
        /*This portion deals with if the truncation is 100%
         * It will simply delete the edges in the edgesToTrunc list, and remove them from the faces that had them.
         * Then it will take the verticies that make up the new face, and from that, create the edges in edgesCreated
         * then those edges will be used to make the new face, and to add to the faces in facesToModity
         */
        if(truePercent==1){
            System.out.println("I RAN");
            //removes the edge that will be truncated from the face that has it, and it also removes the point that will be truncated.
            for(Face e: facesToModify){
                for(Edge k: edgesToTrunc){
                    if(e.remove(k)) break;
                }
                e.remove(vertexToTrunc);
            }
            //creats the lists of points that make up the new face.
            for(Edge e: edgesToTrunc){
                if(!vertsCreated.contains(e.otherVert(vertexToTrunc))){
                    vertsCreated.add(e.otherVert(vertexToTrunc));
                }
            }
            //creates the new face created by the truncation
            faceCreated=Utility.createFace(vertsCreated);
            //clears the list of points that make up the new face, as they were not "created"
            vertsCreated=new ArrayList();
            //adds the edges of the new face to the edges created list
            edgesCreated=faceCreated.getEdges();
            facesAfterModify=facesToModify;
            
        }
        edges.addAll(edgesCreated);
        faces.addAll(facesAfterModify);
        faces.add(faceCreated);
        vertexs.addAll(vertsCreated);
        System.out.println(vertexs.size()+" "+edges.size()+" "+faces.size());
        
    }
    
    public void truncPercent(Edge edgeToTrunc, int percent)
    {
        //this.edgeTrun(edgeToTrunc, edgeToTrunc.distance() * percent / 100.0);
    }
    

    public boolean hasVertexAtXY(double x, double y)
    {
        for (Vertex v: vertexs)
        {
            if (Utility.inRange(v.getX(), x) && Utility.inRange(v.getY(), y))
            {
                return true;
            }
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
            if (Utility.inRange((y3 - y1) * (x2 - x1), (y2 - y1) * (x3 - x1))
            && x3 >= Math.min(x1, x2) - 0.02 && x3 <= Math.max(x1, x2) + 0.02
            && y3 >= Math.min(y1, y2) - 0.02 && y3 <= Math.max(y1, y2) + 0.02)
            {
                return true;
            }
        }
        return false;

    }

    public Edge getEdgeAtXY(double x3, double y3)
    {
        double x1,x2,y1,y2,z1,z2;
        Vertex[] vertices = new Vertex[2];
        Edge currentEdge=new Edge();
        double minZ = -Double.MAX_VALUE;
        for (int i = 0; i < edges.size(); i ++)
        {
            vertices = edges.get(i).getVertices();
            x1 = vertices[0].getX();
            x2 = vertices[1].getX();
            y1 = vertices[0].getY();
            y2 = vertices[1].getY();
            z1 = vertices[0].getZ();
            z2 = vertices[1].getZ();
            if (Utility.inRange((y3 - y1) * (x2 - x1), (y2 - y1) * (x3 - x1))
            && x3 >= Math.min(x1, x2) - 0.02 && x3 <= Math.max(x1, x2) + 0.02
            && y3 >= Math.min(y1, y2) - 0.02 && y3 <= Math.max(y1, y2) + 0.02)
            {
                if (x2 - x1 != 0.0)
                {
                    if (((z2 - z1) * (x3 - x1) / (x2 - x1)) + z1 > minZ)
                    {
                        currentEdge = edges.get(i);
                        minZ = ((z2 - z1) * (x3 - x1) / (x2 - x1)) + z1;
                    }
                }
                else if (y2 - y1 != 0)
                {
                    if (((z2 - z1) * (y3 - y1) / (y2 - y1)) + z1 > minZ)
                    {
                        currentEdge = edges.get(i);
                        minZ = ((z2 - z1) * (y3 - y1) / (y2 - y1)) + z1;
                    }
                }
                else if (x3 == x1 && y3 == y1)
                {
                    if (Math.max(z1, z2) > minZ)
                    {
                        currentEdge = edges.get(i);
                        minZ = Math.max(z1, z2);
                    }
                }
            }
        }
        return currentEdge;
    }

    public Vertex getVertexAtXY(double x, double y)
    {
        Vertex closest= new Vertex();
        double z=-Double.MAX_VALUE; //gets a z coordinate to check against
        for (int i = 0; i < vertexs.size(); i ++)
        {
            Vertex v = vertexs.get(i);
            if (Utility.inRange(v.getX(), x) && Utility.inRange(v.getY(), y) && v.getZ() > z)
            {
                closest = v;
                z = v.getZ();
            }
        }
        return closest;
    }

    public ArrayList<Face> getFaces()
    {
        return faces;
    }

    public static ArrayList<Edge> initiateEdges(ArrayList<Vertex> points)
    {
        ArrayList<Vertex> tempPoints = new ArrayList<Vertex>(points);
        ArrayList<Edge> edges = new ArrayList<Edge>();

        do
        {
            for (int i = 0; i < tempPoints.size(); i ++)
            {
                double length=tempPoints.get(0).distanceTo(tempPoints.get(i));
                if (length<1.01 && length>.99)
                {
                    edges.add(new Edge(tempPoints.get(0), tempPoints.get(i)));
                }
            }
            tempPoints.remove(0);
        }
        while (tempPoints.size() > 0);
        return edges;
    }

    public ArrayList<Vertex> readVerticesTetrahedron(String fileName)
    {
        ArrayList<Vertex> output = new ArrayList<Vertex>();

        try {
            Scanner scan = new Scanner(new File(this.getClass().getClassLoader().getResource(fileName).toURI()));
            int lineCounter = 0;
            while (scan.hasNextLine())
            {
                String nextVertex = scan.nextLine();
                if(lineCounter >= 3 && lineCounter <=6)
                {
                    StringTokenizer lineN = new StringTokenizer(nextVertex);
                    double x = Double.parseDouble(lineN.nextToken());
                    double y = Double.parseDouble(lineN.nextToken());
                    double z = Double.parseDouble(lineN.nextToken());
                    output.add(new Vertex(x,y,z));
                }
                lineCounter++;

            }
            scan.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    public ArrayList<Vertex> readVerticesCube(String fileName)
    {
        ArrayList<Vertex> output = new ArrayList<Vertex>();

        try {
            Scanner scan = new Scanner(new File(this.getClass().getClassLoader().getResource(fileName).toURI()));
            int lineCounter = 0;
            while (scan.hasNextLine())
            {
                String nextVertex = scan.nextLine();
                if(lineCounter>=9 && lineCounter <=16)
                {
                    StringTokenizer lineN = new StringTokenizer(nextVertex);
                    double x = Double.parseDouble(lineN.nextToken());
                    double y = Double.parseDouble(lineN.nextToken());
                    double z = Double.parseDouble(lineN.nextToken());
                    output.add(new Vertex(x,y,z));
                }

                lineCounter++;

            }
            scan.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    public ArrayList<Vertex> readVerticesOctahedron(String fileName)
    {
        ArrayList<Vertex> output = new ArrayList<Vertex>();

        try {
            Scanner scan = new Scanner(new File(this.getClass().getClassLoader().getResource(fileName).toURI()));
            int lineCounter = 0;
            while (scan.hasNextLine())
            {
                String nextVertex = scan.nextLine();
                if(lineCounter>=19 && lineCounter <=24)
                {
                    StringTokenizer lineN = new StringTokenizer(nextVertex);
                    double x = Double.parseDouble(lineN.nextToken());
                    double y = Double.parseDouble(lineN.nextToken());
                    double z = Double.parseDouble(lineN.nextToken());
                    output.add(new Vertex(x,y,z));
                }
                lineCounter ++;
            }
            scan.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    public ArrayList<Vertex> readVerticesDodecahedron(String fileName)
    {
        ArrayList<Vertex> output = new ArrayList<Vertex>();

        try {
            Scanner scan = new Scanner(new File(this.getClass().getClassLoader().getResource(fileName).toURI()));
            int lineCounter = 0;
            while (scan.hasNextLine())
            {
                String nextVertex = scan.nextLine();
                if(lineCounter>=47 && lineCounter <=66)
                {
                    StringTokenizer lineN = new StringTokenizer(nextVertex);
                    double x = Double.parseDouble(lineN.nextToken());
                    double y = Double.parseDouble(lineN.nextToken());
                    double z = Double.parseDouble(lineN.nextToken());
                    output.add(new Vertex(x,y,z));
                }
                lineCounter ++;
            }
            scan.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    public ArrayList<Vertex> readVerticesIcosahedron(String fileName)
    {
        ArrayList<Vertex> output = new ArrayList<Vertex>();

        try {
            Scanner scan = new Scanner(new File(this.getClass().getClassLoader().getResource(fileName).toURI()));
            int lineCounter = 0;
            while (scan.hasNextLine())
            {
                String nextVertex = scan.nextLine();
                if(lineCounter>=30 && lineCounter <= 41)
                {
                    StringTokenizer lineN = new StringTokenizer(nextVertex);
                    double x = Double.parseDouble(lineN.nextToken());
                    double y = Double.parseDouble(lineN.nextToken());
                    double z = Double.parseDouble(lineN.nextToken());
                    output.add(new Vertex(x,y,z));
                }
                lineCounter ++;
            }
            scan.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }
    
    public void rotate (double p1X, double p1Y, double p2X, double p2Y)
    {
        if (p1X == 0.0 && p1Y == 0.0)
        {
            return;
        }
        else if (p2X == 0.0 && p2Y == 0.0)
        {
            return;
        }
        else
        {
            this.rotate(Utility.angleOf(1.0, p2Y) - Utility.angleOf(1.0, p1Y),
            Utility.angleOf(p2X, -1.0) - Utility.angleOf(p1X, -1.0),
            Utility.angleOf(p2X, p2Y) - Utility.angleOf(p1X, p1Y));
        }
    }
    
    public void rotate(double a, double b, double c)
    {
        for (int i = 0; i < vertexs.size(); i ++)
        {
            double x = vertexs.get(i).getX();
            double y = vertexs.get(i).getY();
            double z = vertexs.get(i).getZ();
            vertexs.get(i).set(x * Math.cos(b) * Math.cos(c) + y * (Math.cos(a) * Math.sin(c) - Math.cos(c) * Math.sin(a) * Math.sin(b)) + z * (Math.cos(a) * Math.cos(c) * Math.sin(b) + Math.sin(a) * Math.sin(c)),
                -x * Math.cos(b) * Math.sin(c) + z * (Math.cos(c) * Math.sin(a) - Math.cos(a) * Math.sin(b) * Math.sin(c)) + y * (Math.cos(a) * Math.cos(c) + Math.sin(a) * Math.sin(b) * Math.sin(c)),
                z * Math.cos(a) * Math.cos(b) - y * Math.sin(a) * Math.cos(b) - x * Math.sin(b));
        }
    }
    
    public ArrayList<Vertex> getVertices()
    {
        return vertexs;
    }
}

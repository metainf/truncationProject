package polyhedra;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;

public class Polyhedron
{
    private ArrayList<Vertex> vertices;
    private ArrayList<Edge> edges;
    private ArrayList<Face> faces;
    
    public static final int TETRAHEDRON = 0;
    public static final int CUBE = 1;
    public static final int OCTAHEDRON = 2;
    public static final int DODECAHEDRON = 3;
    public static final int ICOSAHEDRON = 4;

    public Polyhedron(ArrayList<Vertex> inputPoints, ArrayList<Edge> inputEdges,ArrayList<Face> inputSides)
    {
        vertices= new ArrayList<Vertex>(inputPoints);
        edges= new ArrayList<Edge>(inputEdges);
        faces= new ArrayList<Face>(inputSides);
    }
    
    public Polyhedron(int shape)
    {
        switch(shape)
        {
            case TETRAHEDRON: vertices=new ArrayList<Vertex>(this.readVerticesTetrahedron("PolyhedraVertices.txt"));
                                edges=new ArrayList<Edge>(initiateEdges(vertices));
                                faces= new ArrayList<Face>(makePolyhedronFaces(vertices,edges));
                                
            break;
            case CUBE: vertices=new ArrayList<Vertex>(this.readVerticesCube("PolyhedraVertices.txt"));
                                edges=new ArrayList<Edge>(initiateEdges(vertices));
                                faces=new ArrayList<Face>(makePolyhedronFaces(vertices,edges));
            break;
            case OCTAHEDRON: vertices=new ArrayList<Vertex>(this.readVerticesOctahedron("PolyhedraVertices.txt"));
                                edges=new ArrayList<Edge>(initiateEdges(vertices));
                                faces=new ArrayList<Face>(makePolyhedronFaces(vertices,edges));
            break;
            case DODECAHEDRON: vertices=new ArrayList<Vertex>(this.readVerticesDodecahedron("PolyhedraVertices.txt"));
                                edges=new ArrayList<Edge>(initiateEdges(vertices));
                                faces=new ArrayList<Face>(makePolyhedronFaces(vertices,edges));
            break;
            case ICOSAHEDRON: vertices=new ArrayList<Vertex>(this.readVerticesIcosahedron("PolyhedraVertices.txt"));
                                edges=new ArrayList<Edge>(initiateEdges(vertices));
                                faces=new ArrayList<Face>(makePolyhedronFaces(vertices,edges));
            break;
        }
    }

    public static ArrayList<Face> makePolyhedronFaces(ArrayList<Vertex> inputPoints, ArrayList<Edge> inputEdges)
    {
        ArrayList<Vertex> copyPoints=new ArrayList<Vertex>(inputPoints);
        ArrayList<Edge> copyEdge=new ArrayList<Edge>(inputEdges);
        ArrayList<Face> faces=new ArrayList<Face>();
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
                if(faces.get(j).sameAs(faces.get(i)))
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
    	double percentLeft = (100 - percent) / 100.0;
        ArrayList<Edge> edgesToTrunc = new ArrayList<Edge>();
        ArrayList<Face> facesToModify = new ArrayList<Face>();
        ArrayList<Vertex> vertsCreated = new ArrayList<Vertex>();
        ArrayList<Edge> edgesCreated = new ArrayList<Edge>();
        for(Edge e: edges)
        {
            if(e.hasVertex(vertexToTrunc))
            {
                edgesToTrunc.add(e);
            }
        }
        for(Face f: faces)
        {
            if(f.hasVertex(vertexToTrunc))
            {
                facesToModify.add(f);
            }
        }
        for (int i = 0; i < edgesToTrunc.size(); i ++)
    	{
    		Edge currentEdge = edgesToTrunc.get(i);
    		Vertex baseVertex = currentEdge.otherVert(vertexToTrunc);
    		Vertex newVertex = new Vertex(
    				baseVertex.getX() + (percentLeft * (vertexToTrunc.getX() - baseVertex.getX())),
    				baseVertex.getY() + (percentLeft * (vertexToTrunc.getY() - baseVertex.getY())),
    				baseVertex.getZ() + (percentLeft * (vertexToTrunc.getZ() - baseVertex.getZ())));
    		currentEdge.replace(vertexToTrunc, newVertex);
    		vertsCreated.add(newVertex);
    	}
        for (int i = 0; i < facesToModify.size(); i ++)
        {
        	Face currentFace = facesToModify.get(i);
        	ArrayList<Vertex> newVertices = new ArrayList<Vertex>();
        	for (int j = 0; j < vertsCreated.size(); j ++)
    		{
    			Vertex currentVertex = vertsCreated.get(j);
    			if (currentFace.hasVertex(currentVertex))
        		{
        			newVertices.add(currentVertex);
        		}
    		}
        	Edge newEdge = new Edge(newVertices.get(0), newVertices.get(1));
        	currentFace.add(newEdge);
        	edgesCreated.add(newEdge);
        }
        //TODO //A TO //B causes an infinite loop in Face.getOrderedVertices()
        //A
        for (int i = 0; i < vertsCreated.size(); i ++)
        {
        	Vertex currentNewVertex = vertsCreated.get(i);
        	boolean redundant = false;
        	for (int j = 0; j < vertices.size(); j ++)
    		{
    			Vertex currentOldVertex = vertices.get(j);
    			if (currentNewVertex.roughEquals(currentOldVertex))
        		{
    				redundant = true;
    				for (int k = 0; k < edges.size(); k ++)
    				{
    					Edge currentEdge = edges.get(k);
    					if (currentEdge.hasVertex(currentNewVertex))
    					{
    						currentEdge.replace(currentNewVertex, currentOldVertex);
    					}
    				}
        		}
    		}
        	if (redundant)
        	{
        		vertsCreated.remove(currentNewVertex);
        		i --;
        	}
        	
        }
        faces.add(new Face(edgesCreated));
        edges.addAll(edgesCreated);
        vertices.remove(vertexToTrunc);
        vertices.addAll(vertsCreated);
        for (int i = 0; i < edges.size(); i ++)
    	{
    		Edge currentEdge = edges.get(i);
    		boolean redundant = false;
    		Vertex[] edgeVertices = currentEdge.getVertices();
    		if (edgeVertices[0].equals(edgeVertices[1]))
    		{
    			redundant = true;
    		}
    		else
    		{
    			for (int j = 0; j < edges.size(); j ++)
    	    	{
    	    		Edge currentOtherEdge = edges.get(j);
    	    		if (currentEdge.sameAs(currentOtherEdge)
    	    				&& !currentEdge.equals(currentOtherEdge))
    	    		{
    	    			redundant = true;
    	    			for (int k = 0; k < faces.size(); k ++)
    	        		{
    	    				Face currentFace = faces.get(k);
    	    				if (currentFace.hasEdge(currentEdge))
    	    				{
    	    					currentFace.add(currentOtherEdge);
    	    				}
    	        		}
    	    			break;
    	    		}
    	    	}
    		}
    		if (redundant)
    		{
    			edges.remove(currentEdge);
    			for (int j = 0; j < faces.size(); j ++)
        		{
    				Face currentFace = faces.get(j);
    				if (currentFace.hasEdge(currentEdge))
    				{
    					currentFace.remove(currentEdge);
    				}
        		}
    			i --;
    		}
    	}
        for (int i = 0; i < faces.size(); i ++)
		{
        	Face currentFace = faces.get(i);
        	if (currentFace.getNumVertices() < 3)
        	{
        		faces.remove(currentFace);
        	}
		}
        //B
        
    	
    	//obsolete code for performing full truncations
        /*double truePercent=percent/100.0;
        ArrayList<Edge> edgesToTrunc=new ArrayList<Edge>(); //should start with some number, and end with zero edges
        ArrayList<Edge> edgesAfterTrunc=new ArrayList<Edge>();//should start with zero edges, and end with some number
        ArrayList<Face> facesToModify=new ArrayList<Face>();//should start with some number, and end with zero faces
        ArrayList<Face> facesAfterModify=new ArrayList<Face>();//should start with zero faces, and end with some number
        ArrayList<Vertex> vertsCreated=new ArrayList<Vertex>();
        ArrayList<Edge> edgesCreated=new ArrayList<Edge>();
        Face faceCreated=new Face(edges);//remove later
        //This first part is to setup the truncation process.
        //puts the edges that are going to be truncated into the list
        for(Edge e: edges)
        {
            if(e.hasVertex(vertexToTrunc))
            {
                edgesToTrunc.add(e);
            }
        }
        //removes the previous edges from the master list
        for(Edge e: edgesToTrunc)
        {
            edges.remove(e);
        }
        //puts the faces that are going to be truncated into the list
        for(Face e: faces)
        {
            if(e.hasVertex(vertexToTrunc))
            {
                facesToModify.add(e);
            }
        }
        //removes the previous faces from the master list.
        for(Face e:facesToModify)
        {
            faces.remove(e);
        }
        vertices.remove(vertexToTrunc);
        //setup complete
        /*This portion deals with if the truncation is 100%
         * It will simply delete the edges in the edgesToTrunc list, and remove them from the faces that had them.
         * Then it will take the vertices that make up the new face, and from that, create the edges in edgesCreated
         * then those edges will be used to make the new face, and to add to the faces in facesToModity
         *//*
        if(truePercent==1)
        {
        	//removes the edge that will be truncated from the face that has it, and it also removes the point that will be truncated.
        	for(Face e: facesToModify)
        	{
        		for(Edge k: edgesToTrunc)
        		{
        			e.remove(k);
        		}
        		e.remove(vertexToTrunc);
        	}
        	//"heals" the face that had edges removed
        	for(int i=0;i<facesToModify.size();i++)
        	{
        		facesToModify.set(i, Utility.createFace(facesToModify.get(i).getVertices()));
        	}
        	//creates the lists of points that make up the new face.
        	for(Edge e: edgesToTrunc)
        	{
        		vertsCreated.add(e.otherVert(vertexToTrunc));
        	}
        	//creates the new face created by the truncation
        	faceCreated=Utility.createFace(vertsCreated);
        	//clears the list of points that make up the new face, as they were not "created"
        	vertsCreated=new ArrayList<Vertex>();
        	//adds the edges of the new face to the edges created list
        	edgesCreated=faceCreated.getEdges();
        	//if the full truncation removed an entire face, this removes those faces, and their edges.
        	for(Face e:facesToModify)
        	{
        		if(e.getEdges().size()>2)
        		{
        			facesAfterModify.add(e);
        		}
        		else
        		{
        			for(Edge k:e.getEdges())
        			{
        				edgesCreated.remove(k);
        			}
        		}
        	}
        }
        edges.addAll(edgesCreated);
        faces.addAll(facesAfterModify);
        faces.add(faceCreated);
        vertices.addAll(vertsCreated);*/
    }
    
    public void truncPercent(Edge edgeToTrunc, int percent)
    {
    	double truePercent = percent / 100.0;
    	Vertex[] edgeVertices = edgeToTrunc.getVertices();
    	Vertex midpoint = edgeToTrunc.getMidpoint();
    	edgeVertices[0].set(
    			edgeVertices[0].getX() + (truePercent * (midpoint.getX() - edgeVertices[0].getX())),
    			edgeVertices[0].getY() + (truePercent * (midpoint.getY() - edgeVertices[0].getY())),
    			edgeVertices[0].getZ() + (truePercent * (midpoint.getZ() - edgeVertices[0].getZ())));
    	edgeVertices[1].set(
    			edgeVertices[1].getX() + (truePercent * (midpoint.getX() - edgeVertices[1].getX())),
    			edgeVertices[1].getY() + (truePercent * (midpoint.getY() - edgeVertices[1].getY())),
    			edgeVertices[1].getZ() + (truePercent * (midpoint.getZ() - edgeVertices[1].getZ())));
    	//TODO //A TO //B causes an infinite loop in Face.getOrderedVertices()
    	//A
    	if (edgeVertices[0].roughEquals(edgeVertices[1]))
		{
			for (int i = 0; i < edges.size(); i ++)
			{
				Edge currentEdge = edges.get(i);
				if (currentEdge.hasVertex(edgeVertices[1]))
				{
					currentEdge.replace(edgeVertices[1], edgeVertices[0]);
				}
			}
			vertices.remove(edgeVertices[1]);
			edges.remove(edgeToTrunc);
			for (int j = 0; j < faces.size(); j ++)
    		{
				Face currentFace = faces.get(j);
				if (currentFace.hasEdge(edgeToTrunc))
				{
					currentFace.remove(edgeToTrunc);
				}
    		}
			for (int i = 0; i < faces.size(); i ++)
			{
	        	Face currentFace = faces.get(i);
	        	if (currentFace.getNumVertices() < 3)
	        	{
	        		faces.remove(currentFace);
	        	}
			}
		}
    	//B
    }

    public boolean hasVertexAtXY(double x, double y)
    {
        for (Vertex v: vertices)
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
        for (int i = 0; i < vertices.size(); i ++)
        {
            Vertex v = vertices.get(i);
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

        do {
            for (int i = 0; i < tempPoints.size(); i ++)
            {
                double length=tempPoints.get(0).distanceTo(tempPoints.get(i));
                if (length<1.01 && length>.99)
                {
                    edges.add(new Edge(tempPoints.get(0), tempPoints.get(i)));
                }
            }
            tempPoints.remove(0);
        } while (tempPoints.size() > 0);
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
        for (int i = 0; i < vertices.size(); i ++)
        {
            double x = vertices.get(i).getX();
            double y = vertices.get(i).getY();
            double z = vertices.get(i).getZ();
            vertices.get(i).set(x * Math.cos(b) * Math.cos(c) + y * (Math.cos(a) * Math.sin(c) - Math.cos(c) * Math.sin(a) * Math.sin(b)) + z * (Math.cos(a) * Math.cos(c) * Math.sin(b) + Math.sin(a) * Math.sin(c)),
                -x * Math.cos(b) * Math.sin(c) + z * (Math.cos(c) * Math.sin(a) - Math.cos(a) * Math.sin(b) * Math.sin(c)) + y * (Math.cos(a) * Math.cos(c) + Math.sin(a) * Math.sin(b) * Math.sin(c)),
                z * Math.cos(a) * Math.cos(b) - y * Math.sin(a) * Math.cos(b) - x * Math.sin(b));
        }
    }
    
    public ArrayList<Vertex> getVertices()
    {
        return vertices;
    }
}

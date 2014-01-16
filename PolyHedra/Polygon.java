import java.util.ArrayList;
public class Polygon
{
	private ArrayList<Vertex> points;
	private ArrayList<Edge> edges;
	private ArrayList<Face> sides;
	
	public Polygon(ArrayList<Vertex> inputPoints, ArrayList<Edge> inputEdges,ArrayList<Face> inputSides) {
		points=inputPoints;
		edges=inputedges;
		sides=inputsides;
}
	public void pointTrun(Vertex point, double distance){
		ArrayList<Edge> tempEdges=new ArrayList(); //used to store the edges before truncation
		ArrayList<Face> tempFaces=new ArrayList(); //used to store the faces before truncation
		ArrayList<Edge> newEdges=new ArrayList();
		ArrayList<Vertex> newPoints=new ArrayList();
		ArrayList<Face> newFaces=new ArrayList();
		for(Edge e : edges){ //generates the list of edges that will be changed
			if(e.hasPoint(point)){
				tempEdges.add(e);
			}
		}
		for(Edge e: tempEdges){ //generates the list of faces that will be changed
			for(Face f: sides){
				if(f.hasEdge(e)){
					tempFaces.add(f);
				}
			}
		}
		for(int i=0;i<tempFaces.size();i+=0){ //supermessy way of getting rid of duplicates
			if(tempFaces.indexOf(tempFaces.get(i)).equals(tempFaces.lastIndexOf(tempFaces.get(i)))){
				i++;
			}
			else{
				tempFaces.remove(tempFaces.get(i));
				i=0;
			}
		}
		for(Face f: tempFaces){ //removes the faces that are going to be changed from the actual list
			if(sides.contains(f)){
				sides.remove(f);
			}
		}
		for(Edge e: tempEdges){ //removes  the edges that are going to be changed from the actual list
			if(edges.contains(e)){
				edges.remove(e);
			}
		}
		for(Edge e: tempEdges){
			
		}
	}
}

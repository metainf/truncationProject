import java.util.ArrayList;
public class Polygon
{
	private ArrayList<Vertex> points;
	private ArrayList<Edge> edges;
	private ArrayList<Face> sides;
	
	public Polygod(ArrayList<Vertex> inputPoints, ArrayList<Edge> inputEdges,ArrayList<Face> inputSides) {
		points=inputPoints;
		edges=inputedges;
		sides=inputsides;
}
	public void pointTrun(Vertex point, double distance){
		ArrayList<Edge> tempEdges;
		for(Edge e : edges){
			if(e.hasPoint(point)){
				tempEdges.add(e):
			}
		}
	}
}
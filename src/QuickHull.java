//import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class QuickHull {
	
	

	public QuickHull() {
		// TODO Auto-generated constructor stub
	}
	    public static List<Point2D> quickHull(List<Point2D> points) {
	        ArrayList<Point2D> convexHull = new ArrayList<>();
	        if (points.size() < 3) return points;
	        // find leftmosts and rightmosts
	        int minPoint2D = -1, maxPoint2D = -1;
	        double minX = Integer.MAX_VALUE;
	        double maxX = Integer.MIN_VALUE;
	        for (int i = 0; i < points.size(); i++) {
	            if (points.get(i).x < minX) {
	                minX = points.get(i).x;
	                minPoint2D = i;
	            }
	            if (points.get(i).x > maxX) {
	                maxX = points.get(i).x;
	                maxPoint2D = i;
	            }
	        }
	        Point2D A = points.get(minPoint2D);
	        Point2D B = points.get(maxPoint2D);
	        
	        //Add points A and B to Convex_hull and delete them from point set
	        convexHull.add(A);
	        convexHull.add(B);
	        points.remove(A);
	        points.remove(B);

	        ArrayList<Point2D> leftSet = new ArrayList<Point2D>();
	        ArrayList<Point2D> rightSet = new ArrayList<Point2D>();

	        for (int i = 0; i < points.size(); i++) {
	            Point2D p = points.get(i);
	            if (pointSide(A, B, p) == -1)
	                leftSet.add(p);
	            else
	                rightSet.add(p);
	        }
	        findHull(convexHull,A, B, rightSet);
	        findHull(convexHull,B, A, leftSet);

	        return convexHull;
	    }


	    public static double distanceFromLine(Point2D A, Point2D B, Point2D C) {
	        double ABx = B.x - A.x;
	        double ABy = B.y - A.y;
	        double num = ABx * (A.y - C.y) - ABy * (A.x - C.x);
	        if (num < 0) num = -num;
	        return num;
	    }

	    public static void findHull(ArrayList<Point2D> hull,Point2D A, Point2D B, ArrayList<Point2D> set) {
	        int insertPosition = hull.indexOf(B);
	        if (set.size() == 0) return;
	        if (set.size() == 1) {
	            Point2D p = set.get(0);
	            set.remove(p);
	            hull.add(insertPosition, p);
	            return;
	        }
	        double dist = Integer.MIN_VALUE;
	        int furthestPoint2D = -1;
	        for (int i = 0; i < set.size(); i++) {
	            Point2D p = set.get(i);
	            double distance = distanceFromLine(A, B, p);
	            if (distance > dist) {
	                dist = distance;
	                furthestPoint2D = i;
	            }
	        }
	        Point2D P = set.get(furthestPoint2D);
	        set.remove(furthestPoint2D);
	        hull.add(insertPosition, P);

	        // points on the left of AP
	        ArrayList<Point2D> leftSetAP = new ArrayList<Point2D>();
	        for (int i = 0; i < set.size(); i++) {
	            Point2D M = set.get(i);
	            if (pointSide(A, P, M) == 1) {
	                leftSetAP.add(M);
	            }
	        }

	        // points on the left of PB
	        ArrayList<Point2D> leftSetPB = new ArrayList<Point2D>();
	        for (int i = 0; i < set.size(); i++) {
	            Point2D M = set.get(i);
	            if (pointSide(P, B, M) == 1) {
	                leftSetPB.add(M);
	            }
	        }
	        findHull(hull,A, P, leftSetAP);
	        findHull(hull,P, B, leftSetPB);

	    }

	    public static double pointSide(Point2D A, Point2D B, Point2D P) {
	    	//determining side of the point 
	        double side = (B.x - A.x) * (P.y - A.y) - (B.y - A.y) * (P.x - A.x);
	        return (side > 0) ? 1 : -1;
	    }
public static void writeToFile(List<Point2D> convexPoints) throws IOException
{
	try{
	 String filename = "outPut.txt";
	  String workingDirectory = System.getProperty("user.dir");
	  String absoluteFilePath = "";
		
		//absoluteFilePath = workingDirectory + System.getProperty("file.separator") + filename;
		absoluteFilePath = workingDirectory + File.separator + filename;

		System.out.println("\n"+"File has been saved to : " + absoluteFilePath);
			
		//****************//
			
	//	File file = new File(absoluteFilePath);
   FileWriter fileWriter = new FileWriter(absoluteFilePath);
   
   for(Point2D pp:convexPoints)
	 {
	   fileWriter.write(pp.x+" "+pp.y+"\n");
	 }
   fileWriter.close();
	}
	catch(Exception e)
	{
		e.getMessage();
		
	}
   
}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print("Please enter the full path with the file name : Example:"+"E:\\Input\\Points.txt");
		Scanner sc=new Scanner(System.in);
		String path=sc.next();
		  File file = new File(path); 
		  BufferedReader br;
		  String line;
		  ArrayList<Point2D> points=new ArrayList<Point2D>();
			try {
				br = new BufferedReader(new FileReader(file));
				 String[] token=new String[6];
				 while((line=br.readLine())!=null)
				 {
					 token=line.split(" ");
					 double x=Double.parseDouble(token[0]);
					 double y=Double.parseDouble(token[1]);
					 Point2D p=new Point2D();
					 p.setX(x);
					 p.setY(y);
					 points.add(p);
					// System.out.print("x: "+x+" y: "+y+"\n");
					 
				 }
				 
				 List<Point2D> Points= quickHull(points);
				writeToFile(Points);
			}
			catch(Exception e)
			{
				System.out.print("make sure you have entered a correct path");
			}

	}

}

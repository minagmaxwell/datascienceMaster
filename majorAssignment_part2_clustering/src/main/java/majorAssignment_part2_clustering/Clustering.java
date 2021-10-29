package majorAssignment_part2_clustering;

import java.util.*; 


public class Clustering {
	
	static float calcCentroid(ArrayList<Float> points) {
		float sum = 0f;
		for(int i=0;i<points.size();i++) {
			sum+= points.get(i);
		}
		
		return sum/points.size();
	}
	public static void main(String[] args) {
		
		float[] points = new float[] {29f,33f,35f,37f,41f,43f,47f,51f,53f,60f,64f,70f};
		
//		the keys are the centroids of clusters, values are a list of points of these clusters
		Map< Float, ArrayList<Float>> clusters = new HashMap< Float, ArrayList<Float>>();
		
		for(int i=0;i<(points.length);i++) {
			ArrayList<Float> clusterPoints = new ArrayList<Float>();
			clusterPoints.add(points[i]);
			clusters.put(points[i],  clusterPoints);
		}
		
		while(clusters.size()>3) {
	//		step1: find the closest two centroids
			float minDist = (float) Double.POSITIVE_INFINITY;  	// initial very high value
			float selectedCentroid1 = 0.0f;
			float selectedCentroid2 = 0.0f;
			Set<Float> myTreeSet = new TreeSet<>();
	        myTreeSet.addAll( clusters.keySet());
	        
	        for (Float cent1 : myTreeSet) {
				for (Float cent2 : myTreeSet) {
					if(cent1 == cent2) continue;
					float dist =  Math.abs(cent2-cent1);			
					if(dist<minDist) {
						minDist = dist;
						selectedCentroid1 = cent1;
						selectedCentroid2 = cent2;
					}
				}
	        }
	
	//		step2: Merge chosen clusters
			ArrayList<Float> mergedPoints = new ArrayList<Float>();
			mergedPoints.addAll(clusters.get(selectedCentroid1));
			mergedPoints.addAll(clusters.get(selectedCentroid2));
			float newCentroid = calcCentroid(mergedPoints);
			

			
	//		replace merged clusters with the new one
			clusters.remove(selectedCentroid1);
			clusters.remove(selectedCentroid2);
			clusters.put(newCentroid, mergedPoints);
			
	//		Display summary of the step
			System.out.println("----------------------------Step summary----------------------------\n");
			System.out.println("merging the two clusters with centroids: " + selectedCentroid1+ " and " + selectedCentroid2);
			System.out.println("newCentroid for merged cluster: "+ newCentroid);
			System.out.println("Points in the new merged centroid: " + mergedPoints + "\n");
			}
		System.out.println("*****************Target number of clusters reached*******************\n");
		System.out.println("Centroids for Resulting Clusters: "+ clusters.keySet());

	}
}

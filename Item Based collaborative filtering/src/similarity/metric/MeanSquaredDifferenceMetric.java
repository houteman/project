package similarity.metric;

import java.util.Set;

import profile.Profile;

public class MeanSquaredDifferenceMetric implements SimilarityMetric {
	
	public MeanSquaredDifferenceMetric(){}

	@Override
	public double getSimilarity(Profile p1, Profile p2) {
		
		double above=0.0;
		double below=0.0;
		double Rmax = 5.0;
		double Rmin =0.5;
		
		Set<Integer>common=p1.getCommonIds(p2);
		
		for(Integer id:common) {
			
			double r1 = p1.getValue(id).doubleValue();
			double r2 = p2.getValue(id).doubleValue();
			
			above += Math.pow((r1-r2), 2);
			
		}
		
		 below = common.size();
		 
		// compute the Mean Squared Difference
		 double MSD = above/below;
		 
		 double w = 1- MSD/Math.pow((Rmax-Rmin), 2);
		 
		 return w;
		
	}

}

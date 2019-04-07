package similarity.metric;

import java.util.Set;

import profile.Profile;

public class CosineMetric implements SimilarityMetric {
	
	//Constructor
	public CosineMetric() {}

	@Override
	public double getSimilarity(Profile p1, Profile p2) {
		
		double dotMultipul = 0.0;
		double below = 0.0;
		
		//find common id between p1 and p2
		Set<Integer>common=p1.getCommonIds(p2);
		
		//iterate each id from common ids
		//get the value of id
		for(Integer id:common) {
			
			double r1 = p1.getValue(id).doubleValue();
			double r2 = p2.getValue(id).doubleValue();
			
			dotMultipul += r1*r2;
			
		}
		below = p1.getNorm()*p2.getNorm();
		
		return ( below>0 )? dotMultipul/below : null;
	}

}

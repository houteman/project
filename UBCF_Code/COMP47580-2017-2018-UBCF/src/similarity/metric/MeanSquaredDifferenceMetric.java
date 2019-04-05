package similarity.metric;

import java.util.Set;

import profile.Profile;

public class MeanSquaredDifferenceMetric implements SimilarityMetric {

	@Override
	public double getSimilarity(Profile p1, Profile p2) {
		
		double above = 0.0;
		
		Set<Integer> common = p1.getCommonIds(p2);
		for(Integer id: common)
		{
			double r1 = p1.getValue(id).doubleValue();
			double r2 = p2.getValue(id).doubleValue();

			above += Math.pow((r1 - r2), 2);
		}
		
		double below = common.size();
		// compute the Mean Squared Difference
		double MSD = above / below;
		
		// convert MSD into a similarity metric
		return 1 - MSD / Math.pow((5.0 - 0.5), 2);
	}
}
	

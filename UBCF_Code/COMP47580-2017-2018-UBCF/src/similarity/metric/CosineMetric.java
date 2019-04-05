package similarity.metric;

import java.util.Set;

import profile.Profile;

public class CosineMetric implements SimilarityMetric {

	@Override
	public double getSimilarity(Profile p1, Profile p2) {
		
		double dotProduct = 0.0;
		Set<Integer> common = p1.getCommonIds(p2);
		for(Integer id: common)
		{
			double r1 = p1.getValue(id).doubleValue();
			double r2 = p2.getValue(id).doubleValue();

			dotProduct += r1 * r2;
		}
		
		double Cosine = dotProduct / (p1.getNorm() * p2.getNorm());

		return Cosine;
	}

}

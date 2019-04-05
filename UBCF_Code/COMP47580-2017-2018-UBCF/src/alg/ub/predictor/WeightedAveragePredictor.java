package alg.ub.predictor;

import java.util.Map;
import java.util.Set;

import alg.ub.neighbourhood.Neighbourhood;
import profile.Profile;
import similarity.SimilarityMap;

public class WeightedAveragePredictor implements Predictor {

	@Override
	public Double getPrediction(Integer userId, Integer itemId, Map<Integer, Profile> userProfileMap,
			Map<Integer, Profile> itemProfileMap, Neighbourhood neighbourhood, SimilarityMap simMap) 
	{
		double above = 0.0;
		double below = 0.0;
		double sim = 0.0;
		
		// get the neighbours for the user
		Set<Integer> neighbours = neighbourhood.getNeighbours(userId);
		
		// return null if the user has no neighbours
		if (neighbours == null)
			return null;
		
		for(Integer neighbour: neighbours) // iterate over each neighbour
		{
			Double rating = userProfileMap.get(neighbour).getValue(itemId); // get the neighbour's rating for the target item
			Profile profile = simMap.getSimilarities(userId); // get the user similarity profile
			
			if(rating != null) {
				sim = profile.getValue(neighbour);
				above += sim * rating.doubleValue();
			    below += Math.abs(sim);
			}
		}
		if (below > 0)
			return new Double(above / below);
		else
		    return null;
	}

}

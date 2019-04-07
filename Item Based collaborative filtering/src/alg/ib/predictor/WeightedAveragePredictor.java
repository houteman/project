package alg.ib.predictor;

import java.util.Map;
import java.util.Set;

import alg.ib.neighbourhood.Neighbourhood;
import profile.Profile;
import similarity.SimilarityMap;

public class WeightedAveragePredictor implements Predictor {
	
	public WeightedAveragePredictor() {
		
	}

	@Override
	public Double getPrediction(Integer userId, Integer itemId, Map<Integer, Profile> userProfileMap,
			Map<Integer, Profile> itemProfileMap, Neighbourhood neighbourhood, SimilarityMap simMap) {
		
		double above = 0.0;
		double below = 0.0;
		double sim = 0.0;
		
//		//get itemId's neighbours;
//		//Set<Integer>neighbours=neighbourhood.getNeighbours(userId);
//		
//		// if itemId doesn't have neighbours return null;
//		//if (neighbours == null)
//		//	return null;
		
		for(Integer id: userProfileMap.get(userId).getIds()) // iterate over the target user's item
		{
			//judgement neighbour
			if(neighbourhood.isNeighbour(itemId, id)) {
				
			Double rating = userProfileMap.get(userId).getValue(id);// get the rating of item value from user
			
			if(rating!= null) {
				
				//get the weight average=="sim"
				sim = simMap.getSimilarity(itemId, id);
				above += sim*rating.doubleValue();
				below += Math.abs(sim);//¾ø¶ÔÖµ
				
				}
			}
		}
		
		return (below > 0) ? (above / below) : null;
	}

}

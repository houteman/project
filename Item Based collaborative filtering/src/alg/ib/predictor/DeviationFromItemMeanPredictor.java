package alg.ib.predictor;

import java.util.Map;
import java.util.Set;

import alg.ib.neighbourhood.Neighbourhood;
import profile.Profile;
import similarity.SimilarityMap;

public class DeviationFromItemMeanPredictor implements Predictor {
	
	public DeviationFromItemMeanPredictor() {
		
	}

	@Override
	public Double getPrediction(Integer userId, Integer itemId, Map<Integer, Profile> userProfileMap,
			Map<Integer, Profile> itemProfileMap, Neighbourhood neighbourhood, SimilarityMap simMap) {
		
		double above = 0.0;
		double below = 0.0;
		double sim = 0.0;
		
		//get mean value of items
		double mean_item = itemProfileMap.get(itemId).getMeanValue();
		
//		//get item's neighbours
//		//Set<Integer>neighbours = neighbourhood.getNeighbours(itemId);
//		
//		//if (neighbours == null)
//		//return null;

		
		//iterate over target user's items
		//get mean value in item rating
		for(Integer id: userProfileMap.get(userId).getIds()) {
			
			if(neighbourhood.isNeighbour(itemId, id)) {
	
			Double rating = userProfileMap.get(userId).getValue(id);
			Double rating_mean_neighbour = itemProfileMap.get(id).getMeanValue();
			
			
			//calculate the deviation item mean value
			if(rating != null) {
				
				sim = simMap.getSimilarity(itemId, id);
				above += sim*(rating.doubleValue()-rating_mean_neighbour.doubleValue());
				below += sim;
				
				}
			}
			
		}
	
		return (below>0)? new Double(mean_item+above/below) : null;
	}

}

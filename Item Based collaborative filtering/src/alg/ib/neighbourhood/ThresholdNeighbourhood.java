package alg.ib.neighbourhood;

import profile.Profile;
import similarity.SimilarityMap;

public class ThresholdNeighbourhood extends Neighbourhood {
	
	//let L is Threshold to select neighbours
	private final double L;
	
	/**
	 * constructor - creates a new ThresholdNeighbourhood object
	 * @param L - threshold L to select neighbours
	 */
	public ThresholdNeighbourhood(final double L) {
		
		super();
		this.L=L;
		
	}

	public void computeNeighbourhoods(SimilarityMap simMap) {
		// TODO Auto-generated method stub
		for(Integer itemId1: simMap.getIds()) {
			
			// get the first item sim profile
			Profile firstItem=simMap.getSimilarities(itemId1);
			
			//iterate the second item sim in first Item profile
			//if found and sim over L, item2 is the item1's neighbour
			if (firstItem!=null) {
				
				for(Integer itemId2:firstItem.getIds()) {
					double sim=firstItem.getValue(itemId2);
					
					if(sim>L) {
						this.add(itemId1, itemId2);
					}
				}
			}
			
		}
	}

}

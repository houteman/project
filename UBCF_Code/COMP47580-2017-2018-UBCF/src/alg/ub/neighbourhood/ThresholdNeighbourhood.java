package alg.ub.neighbourhood;


import profile.Profile;
import similarity.SimilarityMap;

public class ThresholdNeighbourhood extends Neighbourhood{

	private final double L; // threshold L to select neighbours
	
	/**
	 * constructor - creates a new ThresholdNeighbourhood object
	 * @param L - threshold L to select neighbours
	 */
	public ThresholdNeighbourhood(final double L)
	{
		super();
		this.L = L;
	}
	
	/**
	 * stores the neighbourhoods for all users in member Neighbour.neighbourhoodMap
	 * @param simMap - a map containing user-user similarities
	 */
	@Override
	public void computeNeighbourhoods(SimilarityMap simMap) 
	{
		for (Integer userId1: simMap.getIds()) { // iterate over all users
			// Set<Integer> user1Set = new HashSet<Integer>();
			
			Profile profile = simMap.getSimilarities(userId1); // get the similarity profile of the first user;
			if (profile != null) {
				for (Integer userId2: profile.getIds()) { // iterate over each user2 in the profile
					double sim = profile.getValue(userId2);
					if(sim > L)
						this.add(userId1, userId2);
				}
		    }
	     }
    }
	
}

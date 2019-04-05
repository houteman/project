/**
 * Used to evaluate the user-based CF algorithm
 * 
 * Michael O'Mahony
 * 20/01/2011
 */

package alg.ub;

import java.io.File;

import alg.ub.neighbourhood.*;
import alg.ub.predictor.*;
import similarity.metric.*;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;

// Experiment 2 - Effect of neighbourhood threshold on predictions
public class ExecuteUB_Expt_2
{
	public static void main(String[] args)
	{
		for(double i = 0.05; i < 0.85; i = i + 0.05)
		{
		// configure the user-based CF algorithm - set the predictor, neighbourhood and similarity metric ...
	
		Predictor predictor = new DeviationFromUserMeanPredictor();
		Neighbourhood neighbourhood = new ThresholdNeighbourhood(i);
		SimilarityMetric metric = new CosineMetric();
		
		// set the paths and filenames of the item file, genome scores file, train file and test file ...
		String folder = "ml-20m-2017-2018";
		String itemFile = folder + File.separator + "movies-sample.txt";
		String itemGenomeScoresFile = folder + File.separator + "genome-scores-sample.txt";
		String trainFile = folder + File.separator + "train.txt";
		String testFile = folder + File.separator + "test.txt";	
		
		// set the path and filename of the output file ...
		String outputFile = "results" + File.separator + "predictions.txt";
		
		/////////////////////////////////////////////////////////////////////////////////
		// Evaluates the CF algorithm:
		// - the RMSE (if actual ratings are available) and coverage are output to screen
		// - the output file is created
		DatasetReader reader = new DatasetReader(itemFile, itemGenomeScoresFile, trainFile, testFile);
		UserBasedCF ubcf = new UserBasedCF(predictor, neighbourhood, metric, reader);
		Evaluator eval = new Evaluator(ubcf, reader.getTestData());
		eval.writeResults(outputFile);
		
		System.out.println("Threshold = " + i);
		
		// Display RMSE and coverage
		Double RMSE = eval.getRMSE();
		if(RMSE != null) System.out.printf("RMSE: %.6f\n", RMSE);
		
		double coverage = eval.getCoverage();			
		System.out.printf("coverage: %.2f%%\n", coverage);
		}
	}
}

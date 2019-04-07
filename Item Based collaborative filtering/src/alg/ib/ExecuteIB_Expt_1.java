package alg.ib;

import java.io.File;

import alg.ib.neighbourhood.NearestNeighbourhood;
import alg.ib.neighbourhood.Neighbourhood;
import alg.ib.predictor.DeviationFromItemMeanPredictor;
import alg.ib.predictor.NonPersonalisedPredictor;
import alg.ib.predictor.Predictor;
import alg.ib.predictor.SimpleAveragePredictor;
import alg.ib.predictor.WeightedAveragePredictor;
import similarity.metric.CosineMetric;
import similarity.metric.PearsonMetric;
import similarity.metric.SimilarityMetric;
import util.evaluator.Evaluator;
import util.reader.DatasetReader;

public class ExecuteIB_Expt_1 {
	public static void main(String[] args)
	{
		for(int i = 10; i <= 250; i = i + 10)
		{
		// configure the item-based CF algorithm - set the predictor, neighbourhood and similarity metric ..
		//Predictor predictor = new SimpleAveragePredictor();
		Predictor predictor = new WeightedAveragePredictor();
		//Predictor predictor = new NonPersonalisedPredictor();
		//Predictor predictor = new DeviationFromItemMeanPredictor();
		Neighbourhood neighbourhood = new NearestNeighbourhood(i);
		SimilarityMetric metric = new CosineMetric();
		
		// set the paths and filenames of the item file, genome scores file, train file and test file ...
		String folder = "ml-20m-2018-2019";
		String itemFile = folder + File.separator + "movies-sample.txt";
		String itemGenomeScoresFile = folder + File.separator + "genome-scores-sample.txt";
		String trainFile = folder + File.separator + "train.txt";
		String testFile = folder + File.separator + "test.txt";	
		
		// set the path and filename of the output file ...
		String outputFile = "results" + File.separator + "predictions.txt";
		
		////////////////////////////////////////////////
		// Evaluates the CF algorithm (do not change!!):
		// - the RMSE (if actual ratings are available) and coverage are output to screen
		// - output file is created
		DatasetReader reader = new DatasetReader(itemFile, itemGenomeScoresFile, trainFile, testFile);
		ItemBasedCF ibcf = new ItemBasedCF(predictor, neighbourhood, metric, reader);
		Evaluator eval = new Evaluator(ibcf, reader.getTestData());
		
		// Write to output file
		eval.writeResults(outputFile);
		
	
		System.out.println("Neighbourhood size = " + i);
		
		// Display RMSE and coverage
		Double RMSE = eval.getRMSE();
		if(RMSE != null) System.out.printf("RMSE: %.6f\n", RMSE);
		
		double coverage = eval.getCoverage();
		System.out.printf("coverage: %.2f%%\n", coverage);		
		}
	}
}

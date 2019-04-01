package kodea;

import java.util.Vector;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.core.SerializationHelper;

public class EraikiModeloa {
	
	public static void main(String[] args) {
		Evaluation eval;
		LinearRegression LR = new LinearRegression();
		LR.buildClassifier(train);
//		IBk	cls = new IBk(kOpt);
//      cls.buildClassifier(train);
//      etiketa = new SelectedTag(wOpt,etiketak);
//		linearnn = new LinearNNSearch();
//    	linearnn.setDistanceFunction(df[dOpt]);
//      cls.setNearestNeighbourSearchAlgorithm(linearnn);
//		cls.setDistanceWeighting(etiketa);
		
        eval = new Evaluation(train);
        eval.crossValidateModel(LR, test, 10, new Random(1));
		
		
		
		Vector v = new Vector();
        v.add(eval);
        v.add(LR);
        System.out.println(eval);
        System.out.println(cls);
        SerializationHelper.write("Modeloa.model", v);
	
	}

}

package kodea;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class LogisticModeloaEraiki {
	
	public static void main(String[] args) {
	
		FileReader fi;
		Evaluation eval;
		FileReader fi2 ;
		Instances train;
		Instances test;
		Logistic logistic;
		Evaluation bestEval = null;
		Logistic bestLogistic = null;
		double correct;
		double bestCorrect=0.0;
		int maxIts=1;
		try {
			fi = new FileReader(args[0]);
			train = new Instances(fi);
			train.setClass(train.attribute("@@class@@"));
			
			fi2 = new FileReader(args[1]);
			test = new Instances(fi2);
			test.setClass(test.attribute("@@class@@"));
			System.out.println(train.numInstances());
			System.out.println(test.numInstances());
			
			for (int i=1;i<100;i=i+5) {
				logistic = new Logistic();
				logistic.setMaxIts(i);
				logistic.buildClassifier(train);
				eval = new Evaluation(train);
			    eval.evaluateModel(logistic, test);
			    correct = eval.correct();
			    System.out.println(i + ":   " + correct);
			    if (correct>bestCorrect) {
			    	bestCorrect=correct;
			    	bestEval=eval;
			    	bestLogistic=logistic;
			    	maxIts=i;
			    }
			}
			
			System.out.println("");
			System.out.println("MaxIts Optimoa: " + maxIts);
			System.out.println("Iragarritako instantzia zuzenak: " + (int)bestCorrect);
			
			Vector v = new Vector();
			v.add(bestEval);
	        v.add(bestLogistic);
	        SerializationHelper.write(args[2], v);
	        
	        //Fitxategia sortu eta idatzi
			File f = new File(args[3]);
			FileWriter w = new FileWriter(f);
			w.write(bestEval.toSummaryString("\nResults\n======\n", false));
			w.write(bestEval.toClassDetailsString());
			w.write(bestEval.toMatrixString());
			w.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}

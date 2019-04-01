package kodea;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Vector;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class EraikiModeloa {
	
	public static void main(String[] args) {
	
		FileReader fi;
		Evaluation eval;
		FileReader fi2 ;
		Instances train;
		Instances test;
		Logistic logistic;
		try {
			fi = new FileReader(args[0]);
			 train = new Instances(fi);
			
			 fi2 = new FileReader(args[1]);
			 test = new Instances(fi2);
			
			
			logistic = new Logistic();
			logistic.buildClassifier(train);
			

			
	        eval = new Evaluation(train);
	        eval.crossValidateModel(logistic, test, 10, new Random(1));
			
			
			
			Vector v = new Vector();
	        v.add(eval);
	        v.add(logistic);
	        System.out.println(eval);
	        System.out.println(logistic);
	        SerializationHelper.write("Modeloa.model", v);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

}

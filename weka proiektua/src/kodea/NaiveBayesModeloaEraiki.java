package kodea;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;
import java.util.Vector;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class NaiveBayesModeloaEraiki {
	public static void main(String[] args) {
		
		FileReader fi;
		Evaluation eval;
		FileReader fi2 ;
		Instances train;
		Instances test;
		NaiveBayes naiveBayes;
		try {
			fi = new FileReader(args[0]); 
			train = new Instances(fi);
			train.setClass(train.attribute("@@class@@"));
			
			fi2 = new FileReader(args[1]);
			test = new Instances(fi2);
			test.setClass(test.attribute("@@class@@"));
			
			System.out.println(train.numInstances());
			System.out.println(test.numInstances());
			
			naiveBayes = new NaiveBayes();
			naiveBayes.buildClassifier(train);
			eval = new Evaluation(train);
		    //eval.evaluateModel(naiveBayes, test);
		    eval.crossValidateModel(naiveBayes, train, 10, new Random(1));
			
		    System.out.println(eval.toMatrixString());
		    System.out.println(eval.toSummaryString());
		    System.out.println(eval.toClassDetailsString());
			
			Vector v = new Vector();
			v.add(eval);
	        v.add(naiveBayes);
	        SerializationHelper.write(args[2], v);
	        
	     	//Fitxategia sortu eta idatzi
			File f = new File(args[3]);
			FileWriter w = new FileWriter(f);
			w.write(eval.toSummaryString("\nResults\n======\n", false) + '\n');
			w.write(eval.toClassDetailsString() + '\n');
			w.write(eval.toMatrixString() + '\n');
			w.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

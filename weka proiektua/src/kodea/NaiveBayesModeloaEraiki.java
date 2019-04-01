package kodea;

import java.io.FileReader;
import java.util.Vector;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

public class NaiveBayesModeloaEraiki {
	public static void main(String[] args) {
		
		FileReader fi;
		Evaluation eval;
		FileReader fi2 ;
		Instances train;
		Instances test;
		NaiveBayes naiveBayes;
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
			
			naiveBayes = new NaiveBayes();
			naiveBayes.buildClassifier(train);
			eval = new Evaluation(train);
		    eval.evaluateModel(naiveBayes, test);
			
			
			Vector v = new Vector();
			v.add(eval);
	        v.add(naiveBayes);
	        SerializationHelper.write(args[2], v);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

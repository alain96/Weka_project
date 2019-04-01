package kodea;

import java.util.Vector;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.SerializationHelper;

public class EraikiModeloa {
	
	public static void main(String[] args) {
		Evaluation eval = args[0]; 
		Classifier cls = args[1];
		Vector v = new Vector();
        v.add(eval);
        v.add(cls);
        System.out.println(eval);
        System.out.println(cls);
        SerializationHelper.write("Modeloa.model", v);
	
	}

}

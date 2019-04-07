package kodea;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class Iragarpenak {
	
	public static void main(String[] args) throws Exception {

		double predictedClassIndx = 0.0;
		String testua = " ";

		// Deserialize model (load model)
		Vector vModel = (Vector) SerializationHelper.read(args[0]);
        Evaluation evalModel = (Evaluation) vModel.get(0);
        Classifier clsModel = (Classifier) vModel.get(1);
        
        System.out.println(evalModel);
        System.out.println(clsModel);
		
		// load unlabeled data
		Instances unlabeled = new Instances(new BufferedReader(new FileReader(args[1])));
		// set class attribute
		unlabeled.setClassIndex(unlabeled.numAttributes() - 1);
		// create copy
		Instances labeled = new Instances(unlabeled);

		// label instances
		for (int i = 0; i < unlabeled.numInstances(); i++) {

			predictedClassIndx = clsModel.classifyInstance(unlabeled.instance(i));
			labeled.instance(i).setClassValue(predictedClassIndx);

			// System.out.println("Instantzia: " + i + " iragarritako klasea: " +
			// predictedClassIndx + " -> " + unlabeled.classAttribute().value((int)
			// predictedClassIndx));
			testua = testua + ("\r\n Instantzia: " + i + " iragarritako klasea: " + predictedClassIndx + " -> "
					+ unlabeled.classAttribute().value((int) predictedClassIndx));

		}
		// save labeled data
		BufferedWriter writer = new BufferedWriter(new FileWriter(args[2]));
		writer.write(labeled.toString());
		writer.newLine();
		writer.flush();
		writer.close();

		writer = new BufferedWriter(new FileWriter(args[3]));
		writer.write(testua);
		writer.newLine();
		writer.flush();
		writer.close();
	}

}

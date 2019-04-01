package kodea;

import java.util.Vector;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.SerializationHelper;

public class KargatuModeloa {
	
	public static void main(String[] args) throws Exception {

		Vector vModel = (Vector) SerializationHelper.read(args[0]);
        Evaluation evalModel = (Evaluation) vModel.get(0);
        Classifier clsModel = (Classifier) vModel.get(1);
        
        for (int i = 1; i < data.numInstances()-1;  i++) {
			System.out.println(i);
			System.out.println(data.get(i).classValue());
			double predictedClassIdx = evalModel.evaluateModelOnceAndRecordPrediction(clsModel, data.instance(i));
			//double predictedClassIdx = clsModel.classifyInstance(dataDev.get(i));
			
	
			String acierto;
			int numErrors = 0;
			if (predictedClassIdx == realClassIdx){
			    acierto=" ";
			}else{
			    acierto = "* ERROR";
			    numErrors++;
			    System.out.println("Erroreak: " + numErrors);
			}
			System.out.println("Instance"+ data.get(i)
			+ "Predicted class: " 
			+ data.classAttribute().value((int)predictedClassIdx)
			+ "Real Class: "
			+ data.classAttribute().value((int) realClassIdx)
			+ "     " + acierto
			);
		}
		
	}


}

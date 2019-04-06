package kodea;

import java.util.HashMap;
import java.util.Random;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

public class LogisticModeloaSortu {

	public static void main(String[] args) throws Exception {
		
		DataSource source = new DataSource(args[0]);
		Instances dataTrain = source.getDataSet();
		if(dataTrain.classIndex() == -1){
			dataTrain.setClassIndex(dataTrain.numAttributes() - 1);
		}
		
		DataSource source1 = new DataSource(args[1]);
		Instances dataTest = source1.getDataSet();
		if(dataTest.classIndex() == -1){
			dataTest.setClassIndex(dataTest.numAttributes() - 1);
		}
		
		HashMap<Integer, Integer> minimoak = new HashMap<Integer,Integer>();
		int klasearenBalioa = 0;
		for (int i = 0; i < dataTrain.numInstances()-1; i++) {
    		klasearenBalioa = (int) dataTrain.instance(i).classValue();
    		if (minimoak.containsKey(klasearenBalioa)) {
    			minimoak.put(klasearenBalioa, minimoak.get(klasearenBalioa)+1);
    			System.out.println(klasearenBalioa + " klasearen agerpen berria.");
    		}else {
    			minimoak.put(klasearenBalioa, 1);
    			System.out.println(klasearenBalioa + " klasea gehitu egin da.");
    		}
		}
		klasearenBalioa = 0;
		for (int i = 0; i < dataTest.numInstances()-1; i++) {
    		klasearenBalioa = (int) dataTest.instance(i).classValue();
    		if (minimoak.containsKey(klasearenBalioa)) {
    			minimoak.put(klasearenBalioa, minimoak.get(klasearenBalioa)+1);
    			System.out.println(klasearenBalioa + " klasearen agerpen berria.");
			}else {
				minimoak.put(klasearenBalioa, 1);
				System.out.println(klasearenBalioa + " klasea gehitu egin da.");
			}
		}
		
		int minBalio=9999999;
        double minKey=0D;
        int klaseKopurua = minimoak.size();
        System.out.println("KLASE KOPURUA: "+klaseKopurua);
        for (int key = 0; key < klaseKopurua; key++) {
        		if (minBalio>minimoak.get(key)) {	
        			minBalio		=	minimoak.get(key);
        			minKey 			=	key;
        		}
		}
        System.out.println("MINKEY : "+minKey);
		
        RemovePercentage rPf = new RemovePercentage();
        rPf.setInputFormat(dataTrain);
        rPf.setPercentage(20D);
        
        System.out.println("Instancias antes de filtrar Train: " + dataTrain.numInstances());
        dataTrain = Filter.useFilter(dataTrain, rPf);
        
		System.out.println("Instancias despues de filtrar Train: " + dataTrain.numInstances());
		System.out.println(dataTest.numInstances());
		
		int maxIts = 0;
		double 	correctOpt 	= -1D;
        double 	correct 	= -1D;
		Evaluation eval = new Evaluation(dataTrain);
//		double measure = 0.0;
//		double measureOpt = 0.0;
		double recall = 0.0;
		double recallOpt = -1.0;
        maxIts = 15;
//        for (int i=dataTrain.numInstances()/10; i<= dataTrain.numInstances(); i+=dataTrain.numInstances()/10) {
        for(int i=1;i<=100;i=i+10) {
        	System.out.println( i + " balioarekin modeloa sortzen...");
      	    Logistic logistic = new Logistic();
      	    logistic.buildClassifier(dataTrain);
      	    
      	    logistic.setBatchSize(""+i);
      	    logistic.setMaxIts(maxIts);
      	    
      	    //logistic.setMaxIts(i);
      	    
			eval = new Evaluation(dataTrain); //beti egin behar da ebaluazio berri bat
//			eval.evaluateModel(logistic, dataTest);
			eval.crossValidateModel(logistic, dataTest, 10, new Random(1));
//			correct = eval.correct();
//			measure = eval.fMeasure((int) minKey);
			recall = eval.recall((int) minKey);
			System.out.println(i);
		    System.out.println("Ondo iragarritako instantzia kopurua: " + correct);
		    
	        if (recall > recallOpt) {
//		        measureOpt=measure;
		        recallOpt = recall;
			    maxIts=i;
	        }
	        System.out.println("Recall optimoa: " + recallOpt);
        }
        
        System.out.println("");
		System.out.println("MaxIts Optimoa: " + maxIts);
		System.out.println("Recall hoberena: " + recallOpt);
    	
    	Logistic logistic = new Logistic();
        logistic.buildClassifier(dataTrain);
		logistic.setMaxIts(maxIts);	
		
        eval = new Evaluation(dataTrain);
        eval.crossValidateModel(logistic, dataTest, 10, new Random(1));
        
        System.out.println(eval.toSummaryString());
        System.out.println(eval.toClassDetailsString());
        System.out.println(eval.toMatrixString());
		
	}
}

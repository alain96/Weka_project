package kodea;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.core.SerializationHelper;
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
		try {
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
//		
        System.out.println("Instancias antes de filtrar Train: " + dataTrain.numInstances());

		
		int maxIts = 0;
		int maxItsOpt = 0;
		int batchSizeOpt = 0;
		Evaluation eval = new Evaluation(dataTrain);
//		double measure = 0.0;
//		double measureOpt = 0.0;
		double recall = 0.0;
		double recallOpt = -1.0;
//        maxIts = 10;
        for (maxIts=dataTrain.numInstances()/100; maxIts<= dataTrain.numInstances(); maxIts+=dataTrain.numInstances()/10) {
        	System.out.println("maxIts " + maxIts + " balioarekin modeloa sortzen...");
	        for(int i=1;i<20;i=i+2) {
	        	System.out.println("batchSize " + i + " balioarekin modeloa sortzen...");
	      	    Logistic logistic = new Logistic();
	      	    
	      	    logistic.setMaxIts(maxIts);
	      	    logistic.setBatchSize(""+i);
	      	    logistic.setNumDecimalPlaces(4);
	      	    logistic.setDoNotCheckCapabilities(false);
	      	    
	      	    logistic.buildClassifier(dataTrain);
	      	    
				eval = new Evaluation(dataTrain); //beti egin behar da ebaluazio berri bat
				eval.evaluateModel(logistic, dataTrain);
	//			eval.crossValidateModel(logistic, dataTest, 10, new Random(1));
	//			measure = eval.fMeasure((int) minKey);
				recall = eval.recall((int) minKey);
//				System.out.println(i);dre
			    
		        if (recall > recallOpt) {
	//		        measureOpt=measure;
			        recallOpt = recall;
				    maxItsOpt=maxIts;
				    batchSizeOpt = i;
		        }
		        System.out.println("Recall optimoa: " + recallOpt);
	        }
		}
        System.out.println("");
		System.out.println("MaxIts Optimoa: " + maxItsOpt);
		System.out.println("Recall hoberena: " + recallOpt);
//    	
    	Logistic logistic = new Logistic();
		logistic.setMaxIts(maxItsOpt);	
		logistic.setBatchSize(""+batchSizeOpt);
		logistic.setNumDecimalPlaces(4);
		logistic.setDoNotCheckCapabilities(false);
        logistic.buildClassifier(dataTrain);
		
        eval = new Evaluation(dataTrain);
        eval.crossValidateModel(logistic, dataTest, 10, new Random(1));
        
		Vector v = new Vector();
		v.add(eval);
        v.add(logistic);
        SerializationHelper.write(args[2], v);
        
        //Fitxategia sortu eta idatzi
		File f = new File(args[3]);
		FileWriter w = new FileWriter(f);
		w.write(eval.toSummaryString("\nResults\n======\n", false));
		w.write(eval.toClassDetailsString());
		w.write(eval.toMatrixString());
		w.close();
        
        System.out.println(eval.toSummaryString());
        System.out.println(eval.toClassDetailsString());
        System.out.println(eval.toMatrixString());
        
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
        

		
	}
}

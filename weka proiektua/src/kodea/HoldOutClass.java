package kodea;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.Randomize;
import weka.filters.unsupervised.instance.RemovePercentage;

public class HoldOutClass {
	
	public static void main(String[] args) throws Exception {
		DataSource source = new DataSource(args[0]);
		Instances data = source.getDataSet();
		if(data.classIndex() == -1){
			data.setClassIndex(data.numAttributes() - 1);
		}
		Randomize filter1 = new Randomize();
		filter1.setInputFormat(data);
		filter1.setSeed(42);
		Instances newData = Filter.useFilter(data,filter1);
		
		RemovePercentage filterRemove = new RemovePercentage();
		filterRemove.setPercentage(70);
		filterRemove.setInvertSelection(true);
		filterRemove.setInputFormat(newData);
		
		//TRAIN
		Instances train = Filter.useFilter(newData,filterRemove);
		
		//TEST
		filterRemove = new RemovePercentage();
	    filterRemove.setInputFormat(newData);
	    filterRemove.setPercentage(70);
		filterRemove.setInvertSelection(false);
		Instances test = Filter.useFilter(newData,filterRemove);
		
		System.out.println("Data: " + newData.numInstances());
        System.out.println("Train: " + train.numInstances());
        System.out.println("Test: " + test.numInstances());
        
       
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        writer.write(train.toString());
        writer.newLine();
        writer.flush();
        writer.close();
        System.out.println("Arff berria: " + args[1]);
		      
        BufferedWriter writer2 = new BufferedWriter(new FileWriter(args[2]));
        writer2.write(test.toString());
        writer2.newLine();
        writer2.flush();
        writer2.close();
        System.out.println("Arff berria: " + args[2]);
	}
	
}

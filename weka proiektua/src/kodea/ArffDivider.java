package kodea;

import java.io.BufferedWriter;
import java.io.FileWriter;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemovePercentage;

public class ArffDivider {

	public static void main(String[] args) throws Exception {
		DataSource source = new DataSource(args[0]);
		Instances data = source.getDataSet();
		if(data.classIndex() == -1){
			data.setClassIndex(data.numAttributes() - 1);
		}
		
		String module;
		RemovePercentage filterRemove = new RemovePercentage();
	    filterRemove.setInputFormat(data);
	    filterRemove.setPercentage(100);
		filterRemove.setInvertSelection(false);
		Instances dataNeonate = Filter.useFilter(data,filterRemove);
		
		filterRemove = new RemovePercentage();
		filterRemove.setInputFormat(data);
	    filterRemove.setPercentage(100);
		filterRemove.setInvertSelection(false);
		Instances dataChild = Filter.useFilter(data,filterRemove);
		
		filterRemove = new RemovePercentage();
		filterRemove.setInputFormat(data);
	    filterRemove.setPercentage(100);
		filterRemove.setInvertSelection(false);
		Instances dataAdult = Filter.useFilter(data,filterRemove);
		
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        writer.write(dataNeonate.toString());
		
		BufferedWriter writer2 = new BufferedWriter(new FileWriter(args[2]));
        writer2.write(dataChild.toString());
		
		BufferedWriter writer3 = new BufferedWriter(new FileWriter(args[3]));
		writer3.write(dataAdult.toString());
		
		for (int i=0; i<data.numInstances();i++){
			Instance instantzia = data.get(i);
			module = instantzia.stringValue(1);
		    
			if (module.equals("Neonate")) {
				writer.write(instantzia.toString());
				writer.newLine();
			}
			if (module.equals("Child")) {
				writer2.write(instantzia.toString());
				writer2.newLine();
			}
			if (module.equals("Adult")) {
				writer3.write(instantzia.toString());
				writer3.newLine();
			}
		}
        
       
        //BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        //writer.write(dataNeonate.toString());
        writer.newLine();
        writer.flush();
        writer.close();
        System.out.println("Arff berria: " + args[1]);
		      
        //BufferedWriter writer2 = new BufferedWriter(new FileWriter(args[2]));
        //writer2.write(dataChild.toString());
        writer2.newLine();
        writer2.flush();
        writer2.close();
        System.out.println("Arff berria: " + args[2]);
        
        //BufferedWriter writer3 = new BufferedWriter(new FileWriter(args[3]));
        //writer3.write(dataAdult.toString());
        writer3.newLine();
        writer3.flush();
        writer3.close();
        System.out.println("Arff berria: " + args[3]);
	}
	
}

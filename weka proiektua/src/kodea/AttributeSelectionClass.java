package kodea;


import java.io.BufferedWriter;
import java.io.FileWriter;

import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;


public class AttributeSelectionClass{

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		DataSource source = new DataSource(args[0]);
		Instances data = source.getDataSet();
		if(data.classIndex() == -1){
			data.setClassIndex(data.numAttributes() - 1);
		}


		//AttributeSelection filter = new AttributeSelection();
		AttributeSelection filter = new AttributeSelection();
        CfsSubsetEval eval = new CfsSubsetEval();
        BestFirst search = new BestFirst();
        filter.setEvaluator(eval);
        filter.setSearch(search);
        filter.setInputFormat(data);
        Instances newData = Filter.useFilter(data,filter);
        
        BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
        writer.write(newData.toString());
        writer.newLine();
        writer.flush();
        writer.close();
        System.out.println("Arff berria: " + args[1]);
	}

}

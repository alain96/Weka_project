package kodea;

import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;

public class AttributeSelection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataSource source = new DataSource(args[0]);
		Instances data = source.getDataSet();
		if(data.classIndex() == -1){
			data.setClassIndex(data.numAttributes() - 1);
		}


		AttributeSelection filter = new AttributeSelection();
        CfsSubsetEval eval = new CfsSubsetEval();
        BestFirst search = new BestFirst();
        filter.setEvaluator(eval);
        filter.setSearch(search);
        filter.setInputFormat(data);
        Instances newData = Filter.useFilter(data,filter);
	}

}

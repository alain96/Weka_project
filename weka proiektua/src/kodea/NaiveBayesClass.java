package kodea;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class NaiveBayesClass {
	public static void main(String[] args) throws Exception {
		DataSource source = new DataSource(args[0]);
		Instances data = source.getDataSet();
		if(data.classIndex() == -1){
			data.setClassIndex(data.numAttributes() - 1);
		}
		
		NaiveBayes cls = new NaiveBayes();
		cls.buildClassifier(data);
		
		weka.core.SerializationHelper.write(args[1], cls);
	}
}

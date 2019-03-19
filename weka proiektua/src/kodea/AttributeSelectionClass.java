package kodea;


import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.ClassifierAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class AttributeSelectionClass{

	public static int[] selectedAttributes;

	public static void main(String[] args) {
		try {
			FileReader fi = new FileReader(args[0]); // BOW o TF_IDF
			Instances data = new Instances(fi);
			data.setClass(data.attribute("class"));
			Ranker ranker = new Ranker();
			ClassifierAttributeEval evaluator = new ClassifierAttributeEval();
			AttributeSelection as = new AttributeSelection();
			ranker.setThreshold(-1.7976931348623157E308);
			ranker.setNumToSelect(-1);
			as.setSearch(ranker);
			as.setEvaluator(evaluator);
			Instances newData = null;
			
			as.SelectAttributes(data);
			selectedAttributes = as.selectedAttributes();
			Remove remove = new Remove();
			remove.setAttributeIndicesArray(selectedAttributes);
			remove.setInvertSelection(true);
			remove.setInputFormat(data);
			newData = Filter.useFilter(data, remove);

			BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
			writer.write(newData.toString());
			writer.newLine();
			writer.flush();
			writer.close();
			System.out.println("Arff berria: " + args[1]);
			String[] argumentuak = new String[2];
			argumentuak[0] = args[0]; // trainBOW
			argumentuak[1] = args[2]; // devBOW
			System.out.println("Bateraggarria egiten");
			new BateragarriakEgin();

			try {
				BateragarriakEgin.main(argumentuak, selectedAttributes);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

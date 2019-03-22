package kodea;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.SparseToNonSparse;


public class Raw2Bow {
	public static void main(String[] args) {
		try {
			FileReader fi = new FileReader(args[0]);
			Instances data = new Instances(fi);
			Instances databow = bagOfWords(data);
			BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
			writer.write(databow.toString());
			writer.newLine();
			writer.flush();
			writer.close();
			System.out.println("Arff berria: " + args[1]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static Instances bagOfWords(Instances data) {

		StringToWordVector filter = new StringToWordVector();

		filter.setAttributeIndices("6");
		filter.setDoNotOperateOnPerClassBasis(false);
		filter.setInvertSelection(false);
		filter.setLowerCaseTokens(true);
		filter.setMinTermFreq(1);
		filter.setOutputWordCounts(true);
		filter.setPeriodicPruning(-1.0);
		filter.setWordsToKeep(Integer.MAX_VALUE);
		try {
			filter.setInputFormat(data);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		filter.setTFTransform(true);
		filter.setIDFTransform(true);

		Instances databow = null;
		try {
			databow = Filter.useFilter(data, filter);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		SparseToNonSparse nonsparse = new SparseToNonSparse();
		Instances datanonsparse = null;
		try {
			nonsparse.setInputFormat(databow);
			datanonsparse = Filter.useFilter(databow, nonsparse);
			databow = datanonsparse;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return databow;
	}
}

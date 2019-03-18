package kodea;

import java.io.BufferedWriter;
import java.io.FileWriter;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;


public class BateragarriakEgin {

	public static void main(String[] args, int[] selectedAttributes) {
		DataSource train;
		try {
			System.out.println("Bateragarriak egiten");
			train = new DataSource(args[0]);
			DataSource dev = new DataSource(args[1]);
			Instances dataTrain = train.getDataSet();
			Instances dataDev = dev.getDataSet();
			dataTrain.setClass(dataTrain.attribute("@@class@@"));
			dataDev.setClass(dataDev.attribute("@@class@@"));
			Remove remove = new Remove();

			remove.setAttributeIndicesArray(selectedAttributes);
			remove.setInvertSelection(true);
			remove.setInputFormat(dataTrain);
			dataDev = Filter.useFilter(dataDev, remove);
			BufferedWriter writer = new BufferedWriter(new FileWriter(args[1] + "Bateragarria.arff"));
			writer.write(dataDev.toString());
			writer.newLine();
			writer.flush();
			writer.close();
			System.out.println("Bateragarria eginda");
		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());
			e.printStackTrace();
		}
	}
}

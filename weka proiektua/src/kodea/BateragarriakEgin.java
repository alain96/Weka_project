package kodea;

import java.io.BufferedWriter;
import java.io.FileWriter;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

/**
 * Bi arff bateragarriak izan daitezen lortzeko programa.
 * 
 * @author Alain B., Ander, Alain C., Andoni
 * @version 26.03.2019
 */
public class BateragarriakEgin {
	/**
	 * Metodo nagusia.
	 * 
	 * @param args
	 *            : Kontsolatik datozen komandoak.
	 * @param args[0]
	 *            : Arff-aren helbidea.
	 * @param args[1]
	 *            : Arff-a non gorde nahi den.
	 * @param selectedAttributes
	 *            : Gorde nahi diren atributuen indizea
	 */
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
			BufferedWriter writer = new BufferedWriter(new FileWriter(args[2]));
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

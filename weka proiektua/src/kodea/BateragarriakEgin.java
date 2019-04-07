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
	 *            : AS duen .arff fitxategia.
	 * @param args[1]
	 *            : args[0] .arff-rekin bateragarri egin behar den .arff-a.
	 * @param args[2]
	 *            : non idatziko dugun arff berria.
	 * @param selectedAttributes
	 *            : Gorde nahi diren atributuen indizea
	 */
	public static void main(String[] args, int[] selectedAttributes) {
		DataSource train;
		DataSource dev;
		try {
			System.out.println("Bateragarriak egiten");
			
			train = new DataSource(args[0]);
			dev = new DataSource(args[1]);
			
			Instances dataTrain 	= train.getDataSet();
			Instances dataDev 	= dev.getDataSet();
			
			System.out.println("dataTrain instances: "+dataTrain.numInstances());
			System.out.println("dataDev instances: "+dataDev.numInstances());
			
			dataTrain.setClass(dataTrain.attribute("@@class@@"));
			dataDev.setClass(dataDev.attribute("@@class@@"));
			
			System.out.println("train CLASS posicion: "+dataTrain.get(0).classIndex());
			System.out.println("dev CLASS posicion: "+dataDev.get(0).classIndex());

			System.out.println("train @@CLASS@@ posicion: "+dataTrain.attribute("@@class@@").index());
			System.out.println("dev @@CLASS@@ posicion: "+dataDev.attribute("@@class@@").index());
			
			Remove remove = new Remove();

			remove.setAttributeIndicesArray(selectedAttributes);
			remove.setInvertSelection(true);
			remove.setInputFormat(dataDev);
			
			Instances withAS  = null;
			withAS = Filter.useFilter(dataDev, remove);
			
			System.out.println("withAS num attributes : "+withAS.numAttributes());
			System.out.println("withAS num instances : "+withAS.numInstances());
			System.out.println("withAS @@CLASS@@ posicion: "+withAS.attribute("@@class@@").index());
			System.out.println("train num attributes : "+dataTrain.numAttributes());
			System.out.println("train num instances : "+dataTrain.numInstances());
			System.out.println("train @@CLASS@@ posicion: "+dataTrain.attribute("@@class@@").index());
			BufferedWriter writer = new BufferedWriter(new FileWriter(args[2]));
			writer.write(withAS.toString());
			writer.newLine();
			writer.flush();
			writer.close();
			System.out.println("Bateragarria eginda");
		} catch (Exception e) {
			//System.out.println("Error " + e.getMessage());
			//e.printStackTrace();
		}
	}
}

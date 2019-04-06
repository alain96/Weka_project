package kodea;


import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.CorrelationAttributeEval;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.FixedDictionaryStringToWordVector;
import weka.filters.unsupervised.attribute.Remove;

/**
 * Arff dokumentuak dituen erredundantzia gutxiko atributuak alde batera uzten
 * ditu.
 * 
 * @author Alain B., Ander, Alain C., Andoni
 * @version 26.03.2019
 */
public class AttributeSelectionClass{
	public static int[] selectedAttributes;

	/**
	 * Metodo nagusia.
	 * 
	 * @param args
	 *            : Kontsolatik datozen komandoak.
	 * @param args[0]
	 *            : Train Arff-aren helbidea.
	 * @param args[1]
	 *            : Arff berria gordeko den helbidea.
	 * @param args[2]
	 *            : Dev Arff-aren helbidea.
	 */
	public static void main(String[] args) {
		try {
			FileReader fi = new FileReader(args[0]); // BOW o TF_IDF
			Instances data = new Instances(fi);
			data.setClass(data.attribute("@@class@@"));
			AttributeSelection filter = new AttributeSelection();
//			CfsSubsetEval eval = new CfsSubsetEval();
//			BestFirst search = new BestFirst();
			InfoGainAttributeEval eval = new InfoGainAttributeEval();
			Ranker search = new Ranker();
			search.setNumToSelect(1500);
			filter.setSearch(search);
			filter.setEvaluator(eval);
			
			Instances newData = null;
			try {
				filter.SelectAttributes(data);
				selectedAttributes = filter.selectedAttributes();
				Remove remove = new Remove();
				remove.setAttributeIndicesArray(selectedAttributes);
				remove.setInvertSelection(true);
				remove.setInputFormat(data);
				newData = Filter.useFilter(data, remove);
			} catch (Exception e) {
				e.printStackTrace();
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]));
			writer.write(newData.toString());
			writer.newLine();
			writer.flush();
			writer.close();
			System.out.println("Arff berria: " + args[1]);
			
			String[] argumentuak = new String[3];
			argumentuak[0] = args[0]; // trainBOW
			argumentuak[1] = args[2]; // devBOW
			argumentuak[2] = args[3];
			//System.out.println("Bateragarria egiten");
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

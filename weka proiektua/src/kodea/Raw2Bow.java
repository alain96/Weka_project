package kodea;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.NonSparseToSparse;
import weka.filters.unsupervised.instance.SparseToNonSparse;

/**
 * Arff-motako dokumentuak RAW egoeratik BOW formatuan gordetzeko.
 * 
 * @author Alain B., Ander, Alain C., Andoni
 * @version 26.03.2019
 */
public class Raw2Bow {
	/**
	 * Metodo nagusia.
	 * 
	 * @param args
	 *            : Konsolatik datozen komandoak.
	 * @param args[0]
	 *            : RAW Arff-aren helbidea.
	 * @param args[1]
	 *            : Arff-a non gorde nahi den eta bere izena.
	 */
	public static void main(String[] args) {
		try {
			FileReader fi = new FileReader(args[0]);
			Instances data = new Instances(fi);
			if(data.classIndex() == -1) {
				data.setClassIndex(data.numAttributes()-1);
			}
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

	/**
	 * RAW motako dokumentuari, aukeratutako formatua ematen dio.
	 * 
	 * @param data
	 *           : RAW motako Arff-tik kargatu ditugun instantziak.
	 * @return databow
	 * 			 : Arff-tik kargatu ditun instantziak, formatuarekin.
	 */
	@SuppressWarnings("null")
	private static Instances bagOfWords(Instances data) {

		Instances dataBow = null;
		Instances dataFiltered = null;
		Instances newData = null;
		
		try {
			StringToWordVector filter = new StringToWordVector();

			filter.setAttributeIndices("6");
			filter.setDoNotOperateOnPerClassBasis(false);
			filter.setInvertSelection(false);
			filter.setLowerCaseTokens(true);
			filter.setMinTermFreq(1);
			filter.setOutputWordCounts(true);
			filter.setPeriodicPruning(-1.0);
			filter.setWordsToKeep(Integer.MAX_VALUE);
			filter.setTFTransform(false);
			filter.setIDFTransform(false);
			filter.setInputFormat(data);
			
			dataBow = Filter.useFilter(data, filter);
			
			dataBow.setClass(dataBow.attribute("@@class@@"));
			
//			SparseToNonSparse nonsparse = new SparseToNonSparse();
//			nonsparse.setInputFormat(dataBow);
//			newData = Filter.useFilter(dataBow, nonsparse);
//			
			NonSparseToSparse sparse = new NonSparseToSparse();
			sparse.setInputFormat(dataBow);
			newData = Filter.useFilter(dataBow, sparse);
//			
//			newData.setClass(newData.attribute("@@class@@"));
//			if(newData.classIndex() == -1) {
//				newData.setClassIndex(5);
//			}
			dataFiltered = newData;
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return dataFiltered;
//		try {
////			databow = Filter.useFilter(data, filter);
////			NonSparseToSparse sparse = new NonSparseToSparse();
////			sparse.setInputFormat(databow);
////			dataFiltered = Filter.useFilter(databow, filter);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
//		SparseToNonSparse nonsparse = new SparseToNonSparse();
//		Instances datanonsparse = null;
//		try {
//			nonsparse.setInputFormat(databow);
//			datanonsparse = Filter.useFilter(databow, nonsparse);
//			databow = datanonsparse;
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		

//		return databow;
		
	}
}
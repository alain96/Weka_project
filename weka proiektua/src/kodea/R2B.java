package kodea;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import weka.filters.unsupervised.instance.SparseToNonSparse;

/**
 * Arff-motako dokumentuak RAW egoeratik BOW formatuan gordetzeko.
 * 
 * @author Alain B., Ander, Alain C., Andoni
 * @version 26.03.2019
 */
public class R2B {
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
			Instances databow = null;
			String izena = "";
			for(int i=1; i <= 3; i++) {
				databow = bagOfWords(data,i);
				
				if(i==1) {
					izena = args[1]+"TFIDF.arff";
				}
				else if(i==2) {
					izena = args[1]+"Sparse.arff";
				}
				else {
					izena = args[1]+"NonSparse.arff";
				}
				BufferedWriter writer = new BufferedWriter(new FileWriter(izena));
				writer.write(databow.toString());
				writer.newLine();
				writer.flush();
				writer.close();
				System.out.println("Arff berria: " + izena);
			}
			
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
	private static Instances bagOfWords(Instances data, int i) {

		Instances emaitza = null;
		
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
			
			if(i == 1) {
				filter.setTFTransform(true);
				filter.setIDFTransform(true);
				
			}
			else  {
				filter.setTFTransform(false);
				filter.setIDFTransform(false);
			}
			
			filter.setInputFormat(data);
			
			emaitza = Filter.useFilter(data, filter);
			
			if(i == 2) {
				SparseToNonSparse sparse = new SparseToNonSparse();
				sparse.setInputFormat(emaitza);
				Instances egungoEmaitza = Filter.useFilter(emaitza, sparse);
				emaitza = egungoEmaitza;
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return emaitza;
		
	}
}
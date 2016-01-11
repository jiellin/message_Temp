

import java.io.IOException;
import java.util.Scanner;

import weka.core.pmml.FieldMetaInfo.Value;

public class wekaMain {

	public static void main(String[] args) throws Exception {
		// 生成K个文档
//		System.out.print("Please input the RandomForest num K:");
//		Scanner sc = new Scanner(System.in);
//		int k = sc.nextInt();
//		
//		System.out.print("Please input the SVM num S:");
//		int s = sc.nextInt();
//
//		System.out.print("Please input the num of feature:");
//		int numOfFeature = sc.nextInt();
		
//		int k = Integer.parseInt(args[0]);
//		int s = Integer.parseInt(args[1]);
//		int numOfFeature = Integer.parseInt(args[2]);

		oneToK oneTextToKText = new oneToK();
		oneTextToKText.oneTextToK(2,2,128);
		//(k, s, numOfFeature)
		Classfier classfier = new Classfier();
		classfier.Cla(2,2,128);
		System.out.println("main end!");
	}
}

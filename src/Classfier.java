

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

import javax.lang.model.element.Element;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class Classfier {

	public void Cla(int k, int s, int numOfFeature) throws Exception {

		// 构造K个分类器
		System.out.println("构造" + k + "个RandomForest分类器");
		Classifier[] classfiers = new Classifier[k+s];

		String path;
		File inpFile = null;
		ArffLoader arf = new ArffLoader();
		Instances[] instancesTrainRandom = new Instances[k];
		for (int i = 0; i < k; i++) {
			path = "test" + i + ".arff";
			inpFile = new File(path);
			arf.setFile(inpFile);
			instancesTrainRandom[i] = arf.getDataSet();
			instancesTrainRandom[i]
					.setClassIndex(instancesTrainRandom[i].numAttributes() - 1);
			
			classfiers[i] = (Classifier) Class.forName(
					"weka.classifiers.trees.RandomForest").newInstance();
			classfiers[i].buildClassifier(instancesTrainRandom[i]);
			System.out.println("RandomForest Classifier " + i + "is ok!");

			// 交叉验证
			 System.out.println("Classifier " + i + "正在 交叉验证！");
			 Evaluation eval = new Evaluation(instancesTrainRandom[i]);
			 eval.crossValidateModel(classfiers[i], instancesTrainRandom[i], 3,
			 new Random(1));
			 System.out.println(eval.errorRate());
			 System.out.println(eval.recall(1));
			 System.out.println(eval.recall(0));
			 System.out.println("RandomForest Classifier " + i + "交叉验证結束！！！");
		}
		System.out.println(k + "个RandomForest分类器器训练完成");

		// 构造一个S个SVM判别分类器
		System.out.println("构造S个判别分类器Lib linear SVM！");
		Instances[] instancesTrainSVM = new Instances[s];
		for (int i = k; i < k+s; i++) {
			
			path = "test" + i + ".arff";
			inpFile = new File(path);
			arf.setFile(inpFile);
			instancesTrainSVM[i-k] = arf.getDataSet();
			instancesTrainSVM[i-k]
					.setClassIndex(instancesTrainSVM[i-k].numAttributes() - 1);
			classfiers[i] = (Classifier) Class.forName(
					"weka.classifiers.functions.LibLINEAR").newInstance();
			classfiers[i].buildClassifier(instancesTrainSVM[i-k]);
			System.out.println("LibLinear SVM Classifier " + i + "is ok!");

			// 交叉验证
			 System.out.println("Classifier " + i + "正在 交叉验证！");
			 Evaluation eval = new Evaluation(instancesTrainSVM[i-k]);
			 eval.crossValidateModel(classfiers[i], instancesTrainSVM[i-k], 3,
			 new Random(1));
			 System.out.println(eval.errorRate());
			 System.out.println(eval.recall(1));
			 System.out.println(eval.recall(0));
			 System.out.println("LibLinear SVM Classifier " + i + "交叉验证結束！！！");
		}
		System.out.println(s + "个LibLinear SVM分类器器训练完成");

		//构造一个判别分类器
		Classifier classifierF;
		String trainFile = "train_data_nopre_80w_256_.arff";
		System.out.println("Loading the third data！");
		inpFile = new File(trainFile);
		arf = new ArffLoader();
		arf.setFile(inpFile);
		Instances instancesTrain = arf.getDataSet();
		System.out.println("Loading the third train data.");
		instancesTrain.setClassIndex(instancesTrain.numAttributes() - 1);

		System.out.println("start the third classifier.");
		classifierF = (Classifier) Class.forName(
				"weka.classifiers.functions.LibLINEAR").newInstance();
		classifierF.buildClassifier(instancesTrain);
		System.out.println("the third Model is ok!");
		
		// 打开测试集
		System.out.println("开始读取分类测试数据");
		int t = k+s;
		path = "test" + t + ".arff";
		inpFile = new File(path);
		arf.setFile(inpFile);
		Instances instancesTest = arf.getDataSet();
		instancesTest.setClassIndex(instancesTest.numAttributes() - 1);
		System.out.println("读取测试数据完成");

		//打开判别测试集
		System.out.println("开始读取判别分类测试数据");
		path = "test_data_nopre_20w_256_.arff";
		inpFile = new File(path);
		arf.setFile(inpFile);
		Instances instancesTestF = arf.getDataSet();
		instancesTestF.setClassIndex(instancesTestF.numAttributes() - 1);
		System.out.println("读取判别测试数据完成");
		
		// 写入结果
		System.out.println("Result is writing!");
		path = k + "and" + s + "and"+ numOfFeature + ".txt";
		BufferedWriter writerBays = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(new File(path))));
		int sum = instancesTest.numInstances();
		int[] type = new int[k+s];
		int num = 0;
		for (int i = 0; i < sum; i++) {
			for (int j = 0; j < k+s; j++) {
				type[j] = (int) classfiers[j].classifyInstance(instancesTest
						.instance(i));
			}
			int temp = (int) classifierF.classifyInstance(instancesTestF
					.instance(i));
			Vote vote = new Vote(type, i, temp);
			writerBays.write(vote.finalType() + "\n");
			
			if ((num++) % 40000 == 0 )
				System.out.println("done " + num * 100 / 200000 + "%");
		}
		writerBays.flush();
		writerBays.close();
		System.out.println("Writing is over");
	}
}

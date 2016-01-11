import java.awt.Window.Type;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.Format;
import java.util.Scanner;

import javax.lang.model.element.Element;

public class oneToK {
	public static void oneTextToK(int k, int s, int numOfFeature)
			throws IOException {

		// ����k��BufferedWriter writer[k]
		// writer feature
		WriteLine[] writer = new WriteLine[k + s + 1];
		System.out.println("��ʼ����feature��");
		for (int i = 0; i <= k + s; i++) {
			String filePath = "test" + i + ".arff";
			writer[i] = new WriteLine(filePath);
			writer[i].appendLine("@relation train");
			writer[i].appendLine("");

			for (int j = 0; j < numOfFeature; j++) {
				writer[i].appendLine("@attribute feature" + j + " real");
			}
			writer[i].appendLine("@attribute class {\"1\",\"0\"}" + "\n");
			writer[i].append("@data" + "\n");
		}
		System.out.println("����K��feature���");
		for (int i = 0; i <= k + s; i++) {
			writer[i].flush();
		}

		// ����v50g1
		ReadTxt readSourceK = new ReadTxt("file_output_linjie_train80");
		// ReadTxt readSourceK = new ReadTxt("80.utf8");
		BufferedReader readerSourceK = readSourceK.getRead();

		ReadTxt readTestK = new ReadTxt("file_output_linjie_test20");
		// ReadTxt readTestK = new ReadTxt("20.utf8");
		BufferedReader readerTestK = readTestK.getRead();

		// �����Ϊk��
		int num = 1;
		String message = null;
		// String type = null;
		if (k != 0) {
			System.out.println("��ʼ����" + k + "��ѵ���ļ�");
			while ((message = readerSourceK.readLine()) != null) {

				if (num <= 800000) {
					if (message.endsWith("0")) {
						int temp = (int) (Math.random() * k);
						writer[temp].append(message + "\n");
					} else {
						for (int i = 0; i < k; i++) {
							writer[i].append(message + "\n");
						}
					}
					num++;
				}
			}
			while ((message = readerTestK.readLine()) != null) {
				writer[k + s].appendLine(message);
			}
			System.out.println("����" + k + "��ѵ���ļ���ɣ�");
			for (int i = 0; i < k; i++) {
				writer[i].closeWrite();
			}
			writer[k + s].closeWrite();
			readerSourceK.close();
			readerTestK.close();
		}

		// ����v50g1
		ReadTxt readSourceS = new ReadTxt("file_output_linjie_train80");
		// ReadTxt readSourceS = new ReadTxt("80.utf8");
		BufferedReader readerSourceS = readSourceS.getRead();

		ReadTxt readTestS = new ReadTxt("file_output_linjie_test20");
		// ReadTxt readTestS = new ReadTxt("20.utf8");
		BufferedReader readerTestS = readTestS.getRead();
		// �����ΪS��
		num = 1;
		message = "";
		if (s != 0) {
			System.out.println("��ʼ����" + s + "��ѵ���ļ�");
			while ((message = readerSourceS.readLine()) != null) {
				if (num <= 800000) {
					if (message.endsWith("0")) {
						int temp = (int) (k + Math.random() * s);
						writer[temp].append(message + "\n");
					} else {
						for (int i = k; i < k + s; i++) {
							writer[i].append(message + "\n");
						}
					}
					num++;
				}
			}
			if (k == 0) {
				while ((message = readerTestS.readLine()) != null) {
					writer[k + s].appendLine(message);
				}
			}
			System.out.println("����" + s + "��ѵ���ļ���ɣ�");
		}

		// �ر�д����
		for (int i = k; i <= k + s; i++) {
			writer[i].closeWrite();
		}
		readerSourceS.close();
		readerTestS.close();
	}
}

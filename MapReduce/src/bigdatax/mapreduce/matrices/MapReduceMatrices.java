package bigdatax.mapreduce.matrices;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.InvalidJobConfException;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.JobContext;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.security.TokenCache;
import org.apache.hadoop.fs.FileSystem;

//import bigdatax.mapreduce.wordcount.EdxWordCount;
//import bigdatax.mapreduce.wordcount.EdxWordCount.EdxMap;
//import bigdatax.mapreduce.wordcount.EdxWordCount.EdxReduce;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner; // Import the Scanner class to read text files




public class MapReduceMatrices
{	

	@SuppressWarnings("rawtypes")
	public class OverwriteOutputDirOutputFile extends TextOutputFormat{

		@Override
		public void checkOutputSpecs(FileSystem ignored, JobConf  job) throws FileAlreadyExistsException, InvalidJobConfException, IOException {
		// Ensure that the output directory is set and not already there
		Path outDir = getOutputPath(job);
		if (outDir == null && job.getNumReduceTasks() != 0) {
		throw new InvalidJobConfException("Output directory not set in JobConf.");
		}
		if (outDir != null) {
		FileSystem fs = outDir.getFileSystem(job);
		// normalize the output directory
		outDir = fs.makeQualified(outDir);
		setOutputPath(job, outDir);

		// get delegation token for the outDir’s file system
		TokenCache.obtainTokensForNamenodes(job.getCredentials(),
		new Path[] {outDir}, job);

		// check its existence
		/* if (fs.exists(outDir)) {
		throw new FileAlreadyExistsException(”Output directory ” + outDir +
		” already exists”);
		}*/
		}
		} 
	}
	public static class FirstMap extends Mapper<LongWritable,Text,IntWritable, Text> {
		public void map(LongWritable key, Text value,Context context) throws IOException,InterruptedException{
			String line = value.toString();
			String[] parts = line.split("\t");
			if(parts[0].equals("M")) {
			Text valueOut = new Text(String.join(", ", parts[0], parts[1], parts[3]));
			context.write(new IntWritable(Integer.parseInt(parts[2])), valueOut);
			}
			else {
				Text valueOut = new Text(String.join(", ", parts[0], parts[2], parts[3]));
				context.write(new IntWritable(Integer.parseInt(parts[1])), valueOut );
			}
		}
	}

	public static class FirstReduce extends Reducer<IntWritable, Text, Text, FloatWritable> {
		public void reduce(IntWritable key, Iterable<Text> values,Context context) throws IOException,InterruptedException {

			ArrayList<String> data = new ArrayList<>();
			for(Text v: values) {
				data.add(v.toString());
			}
			for(String v1: data) {
				String letter1 = v1.split(", ")[0];
				if(!letter1.equals("M")) 	continue;
				
				String i = v1.split(", ")[1];
				float value1 =  Float.parseFloat(v1.toString().split(", ")[2]);
				for(String v2: data) {
					String letter2 = v2.split(", ")[0];
					if(!letter2.equals("N")) continue;
					String k = v2.split(", ")[1];
					float value2 =  Float.parseFloat(v2.split(", ")[2]);
					
					FloatWritable product = new FloatWritable(value1 * value2) ;
					Text keyOut = new Text(String.join(", ",  i ,  k));
					context.write(keyOut, product);
					
				}
			}
			
		}
	}
	
	public static class SecondMap extends Mapper<LongWritable,Text,Text,FloatWritable> {
		public void map(LongWritable key, Text value,Context context) throws IOException,InterruptedException{
			String[] parts = value.toString().split("\t");
			float product = Float.parseFloat(parts[1]);
			context.write( new Text(parts[0]) , new FloatWritable(product));
		}
	}

	public static class SecondReduce extends Reducer<Text,FloatWritable,Text,FloatWritable> {
		public void reduce(Text key, Iterable<FloatWritable> values,Context context) throws IOException,InterruptedException {
			float sum=0f;
			for(FloatWritable x: values)
			{
				sum+=x.get();
			}
    		String[] keys = key.toString().split(", ");  
    		
			context.write(new Text(String.join("\t", "N", keys[0], keys[1])), new FloatWritable(sum));
		}
	}
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		int iteration = 0;
		String outputFolder = "output/";
		String iterationFolder = outputFolder + iteration;
		System.out.println(iterationFolder);
		
		float[][] t = new float[20][20]; 
		
//		writing initial v0
		String v0 = iterationFolder + "/second/final.txt";
		System.out.println(v0);
		
		PrintWriter vw =new PrintWriter(v0);
		for(int i=0; i<t.length; i++){
			int j = 0;
			float v = 1f/t.length;
			vw.println("N" + "\t" + i + "\t" + j + "\t" +  v);
		}
		vw.flush();
		
//		System.exit(0);
		
		// read the transition matrix
		File myObj = new File("Pagerank_by_MapReduce-testcase.txt");
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
//	        System.out.println(data);
	        String[] edge = data.split("\\s+");
	        t[Integer.parseInt(edge[1])-1][Integer.parseInt(edge[0])-1] = 1;
	        t[Integer.parseInt(edge[0])-1][Integer.parseInt(edge[1])-1] = 1;
	      }
	      myReader.close();



		//add self loop
		for ( int row = 0; row < t.length; row ++ )
	        t [ row ] [ row ] = 1;

	      
	    // Taxation add vector
		float B = .5f;
		float taxAdd = (1- B)/ t.length; 
		
		float[] taxAddVector = new float[t.length];
		for (int j = 0; j < t.length; j++) {
			taxAddVector[j] = taxAdd;
		}
		
		
	    // normalize and tax
//		float B = 0.8f;
		for (int j = 0; j < t.length; j++)
		    {
				int sumCol = 0;
				for (int i = 0; i < t.length; i++) {
					sumCol += t[i][j];
				}
				for (int i = 0; i < t.length; i++)
					t[i][j] = B * t[i][j]/sumCol; 
		    }
		
        PrintWriter pw =new PrintWriter(
                "tmatrix.txt");
		
		for(int i=0; i<t.length; i++){
		  for(int j=0; j<t.length; j++){			  
			  pw.println("M" + "\t" + i + "\t" + j + "\t" +  t[i][j]);

		  	}
		  }
		pw.flush();
		
		
			
for(int iter=1;iter<50;iter++) {
	

		String vPath = outputFolder + (iter-1) + "/second" + "/final.txt";
//		System.exit(0);
		Job multJob = Job.getInstance();
		multJob.setJarByClass(MapReduceMatrices.class);
		multJob.setJobName("Multiply");
		
		multJob.setMapperClass(FirstMap.class);
		multJob.setReducerClass(FirstReduce.class);
		
		multJob.setMapOutputKeyClass(IntWritable.class);
		multJob.setMapOutputValueClass(Text.class);
		
		multJob.setOutputKeyClass(Text.class);
		multJob.setOutputValueClass(FloatWritable.class);
		
		multJob.setInputFormatClass(TextInputFormat.class);
		multJob.setOutputFormatClass(TextOutputFormat.class);
		
		FileInputFormat.addInputPath(multJob, new Path("tmatrix.txt"));
		FileInputFormat.addInputPath(multJob, new Path(vPath));

		FileOutputFormat.setOutputPath(multJob, new Path(outputFolder + iter+ "/first"));
		
		
		multJob.waitForCompletion(true);
//		System.exit(0);

		
		/////////////////////////////
		
		
		Job addJob = Job.getInstance();
		addJob.setJarByClass(MapReduceMatrices.class);
		addJob.setJobName("Add");
//		
		addJob.setMapperClass(SecondMap.class);
		addJob.setReducerClass(SecondReduce.class);
//		
//		addJob.setOutputKeyClass(Text.class);
		addJob.setOutputKeyClass(IntWritable.class);
		addJob.setOutputValueClass(IntWritable.class);
		addJob.setMapOutputKeyClass(Text.class);
		addJob.setMapOutputValueClass(FloatWritable.class);
		
		addJob.setInputFormatClass(TextInputFormat.class);
		addJob.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(addJob, new Path(outputFolder + iter + "/first/part-r-00000"));
		FileOutputFormat.setOutputPath(addJob, new Path(outputFolder + iter + "/second"));
		
		addJob.waitForCompletion(true);
		
//		Read resulting Matrix
		String secondOutput = outputFolder + iter + "/second" + "/part-r-00000";
		float[] v = new float[t.length];
		File obj = new File(secondOutput);
	      Scanner reader = new Scanner(obj);
	      while (reader.hasNextLine()) {
	        String[] parts = reader.nextLine().split("\t");
	        
    		int i = Integer.parseInt(parts[1].toString());
			float val = Float.parseFloat(parts[3]);
			v[i] = val;
	      }
	      reader.close();
	
//	      perform addition and write it back down
		String newVPath = outputFolder + iter + "/second" + "/final.txt";

		PrintWriter vr =new PrintWriter(newVPath);
		for(int i=0; i<v.length; i++){
			v[i] += taxAddVector[i];
			int j = 0;
			vr.println("N" + "\t" + i + "\t" + j + "\t" +  v[i]);
		}
		vr.flush();
 
	      
	      

  // 		read previous matrix	      
		float[] vPrev = new float[t.length];
		File objPrev = new File(outputFolder + (iter-1) + "/second" + "/final.txt");
		  Scanner readerPrev = new Scanner(objPrev);
		  while (readerPrev.hasNextLine()) {
		    String[] parts = readerPrev.nextLine().split("\t");
		    
	   		int i = Integer.parseInt(parts[1].toString());
			float val = Float.parseFloat(parts[3]);
			vPrev[i] = val;
		  }
		  readerPrev.close();

//		  check diff
		  boolean finished = false;
		  for  (int i = 0; i < v.length; i++) {
			  if(Math.abs(v[i] - vPrev[i])> 0.001) {
				finished = false;
				break;
			  }
			  else finished = true;
			  System.exit(0);
		  }
	  		
		  System.out.println(finished);

		  
}
		  
		  
		  
		  
//		for (int i=0; i<10, i++) {
//			float[][] old = t;
//			t = multiplyMatrices2(t,t);
//		}
		 
		
		
//		float[] v = {0.25f, 0.25f, 0.25f, 0.25f};
//		float[][] v = {{0.25f}, {0.25f}, {0.25f}, {0.25f}};

		
//		displayMatrix(t);
//		float[][] product = multiplyMatrices2(t, v);
//		product = addMatrices(product, taxAddVector);
//		displayMatrix(product);
//
//		System.out.println();
//		product = multiplyMatrices2(t, product);
//		displayMatrix(product);
//		System.out.println();
//		product = multiplyMatrices2(t, product);
//		displayMatrix(product);
//		float[][] firstMatrix = { {3, -2, 5}, {3, 0, 4} };
//        float[][] secondMatrix = { {2, 3}, {-9, 0}, {0, 4} };
//        
//        float[][] product = multiplyMatrices(firstMatrix, secondMatrix);
//        displayMatrix(product);


	}
	
	static float[] addMatrices(float A[], float B[][])
		{
		int i;
		float C[] = new float[A.length];
		
		for (i = 0; i < A.length; i++)
		      C[i] = A[i] + B[i][0];
		
		return C;
		}
	
    public static float[][] multiplyMatrices2(float[][] firstMatrix, float[][] secondMatrix) {
    	int r1 = firstMatrix.length;
    	int c2 = secondMatrix[0].length;
    	int c1 = firstMatrix[0].length;
        float[][] product = new float[r1][c2];
        for(int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                for (int k = 0; k < c1; k++) {
                    product[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }

        return product;
    }
	
    public static float[] multiplyMatrices(float[][] firstMatrix, float[] secondMatrix) {
    	int r1 = firstMatrix.length;
    	int c1 = firstMatrix[0].length;
        float[] product = new float[r1];
        for(int i = 0; i < r1; i++) {
                for (int k = 0; k < c1; k++) {
                    product[i] += firstMatrix[i][k] * secondMatrix[k];
                }
            
        }

        return product;
    }

    public static void displayMatrix(float[][] mat) {
        for(float[] row : mat) {
            for (float column : row) {
                System.out.print(column + "    ");
            }
            System.out.println();
        }
    }
    
    public static void displayMatrix(float[] mat) {
        for(float num : mat) {
            
            System.out.print(num);
        
            System.out.println();
        }
    }

}
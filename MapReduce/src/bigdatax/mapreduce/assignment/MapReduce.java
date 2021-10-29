package bigdatax.mapreduce.assignment;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import bigdatax.mapreduce.wordcount.EdxWordCount;
import bigdatax.mapreduce.wordcount.EdxWordCount.EdxMap;
import bigdatax.mapreduce.wordcount.EdxWordCount.EdxReduce;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.fs.Path;

public class MapReduce
{
	
	public static class CountMap extends Mapper<LongWritable,Text,Text,IntWritable> {
		public void map(LongWritable key, Text value,Context context) throws IOException,InterruptedException{
			String line = value.toString().toLowerCase();
			StringTokenizer tokenizer = new StringTokenizer(line);
			while (tokenizer.hasMoreTokens()) {
				value.set(tokenizer.nextToken());
				context.write(value, new IntWritable(1));
			}
		}
	}

	public static class CountReduce extends Reducer<Text,IntWritable,Text,IntWritable> {
		public void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException,InterruptedException {
			int sum=0;
			for(IntWritable x: values)
			{
				sum+=x.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}
	
	public static class EdxMap extends Mapper<LongWritable,Text,IntWritable,IntWritable> {
		public void map(LongWritable key, Text value,Context context) throws IOException,InterruptedException{
			String line = value.toString().toLowerCase();
			StringTokenizer tokenizer = new StringTokenizer(line);
			String word;
			word = tokenizer.nextToken();
//			value.set(tokenizer.nextToken());
			
			context.write( new IntWritable(word.length()), new IntWritable(1));

		}
	}

	public static class EdxReduce extends Reducer<IntWritable,IntWritable,IntWritable,IntWritable> {
		public void reduce(IntWritable key, Iterable<IntWritable> values,Context context) throws IOException,InterruptedException {
			int sum=0;
			for(IntWritable x: values)
			{
				sum+=x.get();
			}
			context.write(key, new IntWritable(sum));
		}
	}
	
	public static void main(String[] args) throws Exception {
		Job countJob = Job.getInstance();
		countJob.setJarByClass(MapReduce.class);
		countJob.setJobName("CountJob");
		
		countJob.setMapperClass(CountMap.class);
		countJob.setReducerClass(CountReduce.class);
		
		countJob.setOutputKeyClass(Text.class);
		countJob.setOutputValueClass(IntWritable.class);
		//job.setMapOutputKeyClass(theClass);
		//job.setMapOutputValueClass(theClass);
		
		countJob.setInputFormatClass(TextInputFormat.class);
		countJob.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(countJob, new Path(args[0]));
		FileOutputFormat.setOutputPath(countJob, new Path(args[1]));
		
		countJob.waitForCompletion(true);
//		System.exit(0);

		
		/////////////////////////////
		
		
		Job job = Job.getInstance();
		job.setJarByClass(MapReduce.class);
		job.setJobName("Edxmapreduce");
		
		job.setMapperClass(EdxMap.class);
		job.setReducerClass(EdxReduce.class);
		
//		job.setOutputKeyClass(Text.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(IntWritable.class);
		//job.setMapOutputKeyClass(theClass);
		//job.setMapOutputValueClass(theClass);
		
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		FileInputFormat.addInputPath(job, new Path("output_01/part-r-00000"));
//		FileInputFormat.addInputPath(job, new Path(args[1]));
		FileOutputFormat.setOutputPath(job, new Path("output_02"));
		
		job.waitForCompletion(true);
		System.exit(0);
		
		
		
	}

}
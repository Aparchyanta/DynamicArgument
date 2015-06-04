package FirstProject.DynamicArgument;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;





public class DynamicArgumentDriver extends Configured implements Tool{
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		int exitCode = ToolRunner.run(new DynamicArgumentDriver(), args);
	    System.exit(exitCode);

	}
	public int run(String[] args) throws Exception {

        if (args.length != 3) {
            System.out.printf(
                    "Usage: %s [generic options] <input dir> <output dir>\n", getClass()
                    .getSimpleName());
            ToolRunner.printGenericCommandUsage(System.out);
            return -1;
        }
        Configuration conf=new Configuration();
        conf.set("filter", args[2]);
        Job job = new Job(conf);
        job.setJarByClass(DynamicArgumentDriver.class);
        job.setJobName(this.getClass().getName());

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(MyMapper.class);
        //job.setReducerClass(CountR.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setNumReduceTasks(0);
        
        if (job.waitForCompletion(true)) {
            return 0;
        }
        return 1;
    }

}

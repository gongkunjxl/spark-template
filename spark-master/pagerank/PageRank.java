import java.io.IOException;  
import java.text.DecimalFormat;  
import java.text.NumberFormat;  
import java.util.StringTokenizer;  
import java.util.Iterator;  
  
import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.fs.Path;  
import org.apache.hadoop.io.IntWritable;  
import org.apache.hadoop.io.Text;  
import org.apache.hadoop.mapreduce.Job;  
import org.apache.hadoop.mapreduce.Mapper;  
import org.apache.hadoop.mapreduce.Reducer;  
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;  
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;  
import org.apache.hadoop.util.GenericOptionsParser;  
  
public class PageRank {  
  
  public static class MyMapper   extends Mapper<Object, Text, Text, Text>  
  {  
        private Text id = new Text();  
        public void map(Object key, Text value, Context context ) throws IOException, InterruptedException  
        {  
            String line = value.toString();  
            if(line.substring(0,1).matches("[0-9]{1}"))  
            {  
                  boolean flag = false;  
                  if(line.contains("_"))  
                  {  
                        line = line.replace("_","");  
                        flag = true;  
                  }  
                  String[] values = line.split("\t");  
                  Text t = new Text(values[0]);  
                  String[] vals = values[1].split(" ");  
                  String url="_";  
                    
                  double pr = 0;  
                  int i = 0;  
                  int num = 0;  
                    
                  if(flag)  
                  {  
                      i=2;  
                      pr=Double.valueOf(vals[1]);  
                      num=vals.length-2;  
                  }  
                  else  
                  {  
                      i=1;  
                      pr=Double.valueOf(vals[0]);  
                      num=vals.length-1;  
                  }  
                    
                  for(;i<vals.length;i++)  
                  {  
                      url=url+vals[i]+" ";  
                      id.set(vals[i]);  
                      Text prt = new Text(String.valueOf(pr/num));  
                      context.write(id,prt);  
                  }  
                  context.write(t,new Text(url));  
              }  
          }  
  }  
  
  public static class MyReducer  extends Reducer<Text,Text,Text,Text>  
  {  
              private Text result = new Text();  
              private Double pr = new Double(0);  
                
         public void reduce(Text key, Iterable<Text> values,  Context context  ) throws IOException, InterruptedException  
         {  
              double sum=0;  
              String url="";  
                
              for(Text val:values)  
              {  
                  if(!val.toString().contains("_"))  
                  {  
                      sum=sum+Double.valueOf(val.toString());  
                  }  
                  else  
                 {  
                      url=val.toString();  
                  }  
              }  
              pr=0.15+0.85*sum;  
              String str=String.format("%.3f",pr);  
              result.set(new Text(str+" "+url));  
              context.write(key,result);  
          }  
 }  
  
    public static void main(String[] args) throws Exception  
    {  
              String paths="/user/root/data/data";  
//            String paths="hdfs://localhost:9000/user/root/pageinput";  
              String path1=paths;  
              String path2="";  
  
              for(int i=1;i<=20;i++)  
              {  
                 path2=paths+i;  
                 System.out.println("This is the "+i+"th job!");  
                 System.out.println("path1:"+path1);  
                 System.out.println("path2:"+path2);  
                            
                 Configuration conf = new Configuration();  
                 Job job = new Job(conf, "PageRank");  
                            
                            
                 job.setJarByClass(PageRank.class);  
                 job.setMapperClass(MyMapper.class);  
                 job.setCombinerClass(MyReducer.class);  
                 job.setReducerClass(MyReducer.class);  
                 job.setOutputKeyClass(Text.class);  
                 job.setOutputValueClass(Text.class);  
                 FileInputFormat.addInputPath(job, new Path(path1));  
                 FileOutputFormat.setOutputPath(job, new Path(path2));  
                            
                 path1=path2;  
                            
                 job.waitForCompletion(true);  
                 System.out.println(i+"th end!");  
        }  
      }   
 }  

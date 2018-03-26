/*
    Java program which implements version Control System (i.e) copying source path directory files and folders to target folder path
    with two functionalities 
    1.Checkin
    2.Checkout
 */
package scm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *@authors
 * Vignesh Suresh               email: svignesh181993@gmail.com
 * Vandana Santosh              email: vandanasantosh26@gmail.com
 * Soujanya Kusampudi           email: soujanya.kusampudi@gmail.com
 * Sai Prasanna Saketh Poduri   email: saketh31@gmail.com
 * Saideep Karanam              email: saideepkaranam@gmail.com
 */
public class SCM {

   public static int count=0;
    public static int selectoption=0;
    public static void main(String[] args) throws IOException {
        
        //int selectoption;
        Scanner sop = new Scanner(System.in);
        while(true)
        {
            System.out.println("Select any one number from the below options:");
            System.out.println("1.Checkin");
            System.out.println("2.Checkout");
            System.out.println("3.Exit");
            selectoption=sop.nextInt();
            switch(selectoption)
            {
                case 1:
                {
                        String path="",despath="",updateFile="";
                        Scanner keyboard = new Scanner(System.in);
                        System.out.println("Enter the source path(Eg: C://my/ptree) to be copied:");
                        path = keyboard.nextLine();

                        //Source directory which you want to copy to new location
                        File sourceFolder = new File(path);

                        System.out.println("Enter the path(Eg: E://targetfolder) with target folder name:");
                        despath = keyboard.nextLine();
                        updateFile=despath;

                        String finalpath=despath+"\\"+sourceFolder.getName().toString();

                        //Target directory where files should be copied
                        File destinationFolder = new File(finalpath);

                        intialoperation(destinationFolder,sourceFolder,path,despath,updateFile,1);
                        break;
                }
                case 2:
                {
                    String path="",despath="",updateFile="";
                    Scanner keyboard = new Scanner(System.in);
                    System.out.println("Enter the source path(Eg: C://my/ptree) to be copied:");
                    path = keyboard.nextLine();

                    //Source directory which you want to copy to new location
                    File sourceFolder = new File(path);

                    System.out.println("Enter the path(Eg: E://targetfolder) with target folder name:");
                    despath = keyboard.nextLine();
                    updateFile=despath;

                    String finalpath=despath+"\\"+sourceFolder.getName().toString();

                    //Target directory where files should be copied
                    File destinationFolder = new File(finalpath);
                    intialoperation(destinationFolder,sourceFolder,path,despath,updateFile,2);
                    break;
                }
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select any one of the above options");
            }
        }
    }
    
    public static void intialoperation(File destinationFolder,File sourceFolder,String path,String despath,String updateFile,int test) throws IOException
    {
            String codename="SKS-1";
                    if (!destinationFolder.exists()) 
                        {
                            destinationFolder.mkdirs();
                            DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
                            Date dateobj = new Date();
                            //System.out.println(df.format(dateobj));
                                String activity;
                                activity=destinationFolder.getParent()+"\\"+"activity";
                                //System.out.println(activity);
                                File activityFolder=new File(activity);

                                if(!activityFolder.exists())
                                {
                                    activityFolder.mkdir();
                                }
                                //Calling the write manifesto function
                                writemanifesto(df.format(dateobj),sourceFolder,destinationFolder,activityFolder,codename,path,despath,updateFile,test);
                        }
                    //Call Copy function
                    copyFolder(sourceFolder, destinationFolder, updateFile);
                    
    }
    private static void copyFolder(File sourceFolder, File destinationFolder, String updateFile) throws IOException
    {
        
        if(selectoption==1)
        {
                //Check if sourceFolder is a directory or file
                if (sourceFolder.isDirectory()) 
                {
                    count=0;
                    //Verify if destinationFolder is already present; If not then create it
                    if (!destinationFolder.exists()) 
                    {
                        destinationFolder.mkdir();
                    }

                    //Get all files from source directory
                    String files[] = sourceFolder.list();

                    //Go over all files
                    for (String file : files) 
                    {
                        File srcFile = new File(sourceFolder, file);
                        File destFile = new File(destinationFolder, file);

                        //Recursive function call
                        copyFolder(srcFile, destFile, updateFile);
                    }
                }
                else
                {
                    int artifact[]={1,3,11,17};
                    String line="",copiedstring="";
                    int checksum=0;

                    try
                    {
                            FileReader f=new FileReader(sourceFolder);      //FileReader class for source path
                            BufferedReader reader= new BufferedReader(f);
                            long filelength=sourceFolder.length();          //Obtain the size of the size
                            while((line=reader.readLine())!=null)
                            {
                                copiedstring=copiedstring+line;             //copying file lines content to a string
                                char[] charArray = line.toCharArray();
                                     for(int i=0;i<charArray.length;i++)
                                     {  
                                         checksum=(checksum+((byte)charArray[i]*artifact[i%4]));   //Calculates the checksum 
                                     }
                            }
                            String path=destinationFolder.getParent();
                            String newfolder=destinationFolder.getName();       //Returns the file name alone

                            String newpath=path+"\\"+newfolder;  //Returns the complete directory of the file name and adding a new directory
                            File newFolder = new File(newpath);

                           //Creates the new folder with name as file name
                            if (!newFolder.exists()) 
                            {
                                newFolder.mkdir();

                                //Call writer function
                                writer(checksum,filelength,newFolder,sourceFolder,copiedstring,updateFile);
                                copiedstring="";
                            }
                            else
                            {
                                String newfilename=newFolder.getPath()+"\\"+checksum+"-"+filelength+getFileExtension(sourceFolder);
                                File forcheck=new File(newfilename);
                                if(forcheck.exists())
                                {
                                    String f_already=updateFile+"\\"+"activity"+"\\"+"manifest.txt";
                                    File file=new File(f_already);
                                    FileWriter fw=new FileWriter(file,true);
                                    fw.write(System.lineSeparator()+forcheck.getPath()+" is already exists."+System.lineSeparator());
                                    fw.close();    
                                    //System.out.println(forcheck.getPath()+"File already exists");
                                }
                                else  
                                {
                                    //System.out.println(forcheck.getPath()+"Does not Exists");
                                    writer(checksum,filelength,newFolder,sourceFolder,copiedstring,updateFile);
                                }
                            }

                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }
        }
        else
        {
            if (sourceFolder.isDirectory()) 
                {
                    count=0;
                    //Verify if destinationFolder is already present; If not then create it
                    if (!destinationFolder.exists()) 
                    {
                        destinationFolder.mkdir();
                    }

                    //Get all files from source directory
                    String files[] = sourceFolder.list();

                    //Go over all files
                    for (String file : files) 
                    {
                        File srcFile = new File(sourceFolder, file);
                        File destFile = new File(destinationFolder, file);

                        //Recursive function call
                        copyFolder(srcFile, destFile, updateFile);
                    }
                }
                else
                {
                    int i=0;
                    Scanner keyboard = new Scanner(System.in);
                    //To know whether there are multiple files
                    File temp=new File(sourceFolder.getParent());
                    File[] listOfFiles = temp.listFiles();
                    int n_file=listOfFiles.length;
                        
                        //If there are more than one file, it gets the particular file path from the user
                        if(n_file>1 && count==0)
                        {
                                String selectedFile="";
                                System.out.println("Enter the complete path of the file you want, listed below:");
                                while(i<n_file)
                                {
                                    int j=i;
                                    System.out.println((++j)+"."+listOfFiles[i]);
                                    i++;
                                }
                                selectedFile=keyboard.nextLine();
                                File filetocopy=new File(selectedFile);
                                checkoutwriter(filetocopy,destinationFolder,updateFile);
                                count++;
                        }
                        else
                        {
                            if(count==0)
                                checkoutwriter(sourceFolder,destinationFolder,updateFile);

                        }
                    }
        }
    }
    
    public static void writer(int checksum,long filelength,File file,File srcfile,String copiedstring,String updateFile)
            {
                //Writing the file content with checksum and byte size with same file type
                try
                {
                    String ex=getFileExtension(srcfile);
                    String duppath=file.getPath()+"\\"+checksum+"-"+filelength+ex;
                    FileWriter fw=new FileWriter(duppath);    
                    fw.write(copiedstring); 
                    fw.close();
                    
                    //Update the manifesto file after writing file with checksum and byte size values
                    updatemanifesto(checksum,filelength,file,srcfile,ex,updateFile);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
    
    //Returns file extension
    private static String getFileExtension(File file)
        {
            String ext=file.getName();
            if(ext.lastIndexOf(".") != -1 && ext.lastIndexOf(".") != 0)
            return "."+ext.substring(ext.lastIndexOf(".")+1);
            else return "";
        }
    
    //Writing manifesto file with initial details
    public static void writemanifesto(String date,File oldFolder,File folder,File activity,String codename,String path,String despath,String updateFile,int test)
            {
                if(test==1)
                {
                    try
                    {
                        String content="CODE NAME: "+codename+System.lineSeparator()+System.lineSeparator()+"FOLDER CREATION"+System.lineSeparator()+folder.getParent()+" folder is created at "+date+System.lineSeparator()+System.lineSeparator()+"COMMANDS GIVEN BY THE USER: "+System.lineSeparator()+path+System.lineSeparator()+despath+System.lineSeparator()+System.lineSeparator()+"FULL SOURCE PATH TO ORIGINAL PROJECT TREE"+System.lineSeparator()+oldFolder.getPath()+System.lineSeparator()+System.lineSeparator()+"TARGET REPO PATH:"+System.lineSeparator()+folder.getParent()+System.lineSeparator()+System.lineSeparator()+"COPIED FILES WITH ARTIFACT-ID, ORIGINAL FILE NAME AND ITS PATH"+System.lineSeparator();
                        FileWriter fw=new FileWriter(activity.getPath()+"\\"+"manifest.txt");    
                        fw.write(content); 
                        fw.close();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else if(test==2)
                {
                    try
                    {
                        String content="CODE NAME: "+codename+System.lineSeparator()+System.lineSeparator()+"FOLDER CREATION"+System.lineSeparator()+folder.getParent()+" folder is created at "+date+System.lineSeparator()+System.lineSeparator()+"COMMANDS GIVEN BY THE USER: "+System.lineSeparator()+path+System.lineSeparator()+despath+System.lineSeparator()+System.lineSeparator()+"FULL SOURCE PATH TO ORIGINAL PROJECT TREE"+System.lineSeparator()+oldFolder.getPath()+System.lineSeparator()+System.lineSeparator()+"TARGET REPO PATH:"+System.lineSeparator()+folder.getParent()+System.lineSeparator()+System.lineSeparator()+"COPIED FILES LIST, ORIGINAL FILE NAME AND ITS PATH"+System.lineSeparator();
                        FileWriter fw=new FileWriter(activity.getPath()+"\\"+"checkoutmanifest.txt");    
                        fw.write(content); 
                        fw.close();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
    
    //Append manifesto file which lists new file name with checksum and byte size, its original file name and its respective path
    public static void updatemanifesto(int checksum,long filelength,File newFolder,File oldFolder,String ex,String updateFile)
            {
                try
                {
                    String content = checksum+"-"+filelength+ex; 
                    String name=oldFolder.getName();
                    String path=oldFolder.getPath();
                    File fdate=new File(updateFile);
                    String f=updateFile+"\\"+"activity"+"\\"+"manifest.txt";
                    File file=new File(f);
                    FileWriter fw=new FileWriter(file,true);
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    fw.write(System.lineSeparator()+content+" is created."+" Original file name is:"+name +",and its original path is "+path+" ,created at "+sdf.format(fdate.lastModified())+System.lineSeparator());
                    fw.close();                        
                }
                catch(IOException e)
                {}
            }
    
    
    public static void checkoutwriter(File sourceFolder,File destinationFolder,String updateFile)
    {
        try
        {
            String line="",copiedstring="";
            //System.out.println(sourceFolder);
            FileReader f=new FileReader(sourceFolder);      //FileReader class for source path
                        BufferedReader reader= new BufferedReader(f);
                        while((line=reader.readLine())!=null)
                        {
                            copiedstring=copiedstring+line;             //copying file lines content to a string
                        }
                        String prevfol=destinationFolder.getParentFile().getParent();   //Gets the file's grand parent directory path
                        String path=destinationFolder.getParent();                      //Gets the file parent directory path
                        String newfolder=destinationFolder.getName();       //Returns the file name alone
                        File test=new File(path);
                        String parentname=test.getName();

                        //finalfol is the new file name with the extension
                        String finalfol=parentname+"."+newfolder.substring(newfolder.lastIndexOf(".")+1);

                        //Concatenate the finalfol name with the path
                        String newpath=prevfol+"\\"+finalfol;  //Returns the complete directory of the file name and adding a new directory
                        //System.out.println(finalfol);
                            //Call writer function
                            checkout_writer(sourceFolder,copiedstring,updateFile,newpath,finalfol);

                            copiedstring="";
                            newpath="";
                            //Deletes the folder
                            test.delete();
        }
        catch(IOException e)
        {
                e.printStackTrace();
        }
    }
  
    //File writer
    public static void checkout_writer(File srcfile,String copiedstring,String updateFile,String newpath,String finalfol)
            {
                //Writing the file content
                try
                {
                    FileWriter fw=new FileWriter(newpath);    
                    fw.write(copiedstring); 
                    fw.close();
                    
                    //Update the manifesto file after writing file with checksum and byte size values
                    updatecheckoutmanifesto(srcfile,finalfol,updateFile);
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
    
    //Append checkout manifesto file which lists  
    public static void updatecheckoutmanifesto(File Fullpath,String newname,String updateFile)
            {
                try
                {
                   // String content = checksum+"-"+filelength+ex; 
                    String originalname=Fullpath.getName();
                    String originalpath=Fullpath.getPath();
                    File fdate=new File(updateFile);
                    String f=updateFile+"\\"+"activity"+"\\"+"checkoutmanifest.txt";
                    File file=new File(f);
                    FileWriter fw=new FileWriter(file,true);
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    fw.write(System.lineSeparator()+newname+" is created."+" Original file name is:"+originalname +",and its original path is "+originalpath+" ,created at "+sdf.format(fdate.lastModified())+System.lineSeparator());
                    fw.close();                        
                }
                catch(IOException e)
                {}
            }
}

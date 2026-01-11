package com.ks.application.service.soa;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.ks.application.model.soa.SOARequest;

public class SoaUtil {
	
	 public static void deleteDirectory(String  dir) throws IOException { 
		    Path parentDir = Paths.get(dir); // change path

	        try (DirectoryStream<Path> stream = Files.newDirectoryStream(parentDir)) {
	            for (Path path : stream) {
	                if (Files.isDirectory(path)) {
	                    deleteDirectory(path);
	                }
	            }
	        }
	    }
		 
	    private static void deleteDirectory(Path dir) throws IOException {
	        Files.walk(dir)
	             .sorted((p1, p2) -> p2.compareTo(p1)) // children first
	             .forEach(p -> {
	                 try {
	                	 if (!p.endsWith(".ps"))
	                		 Files.delete(p);
	                 } catch (IOException e) {
	                     throw new RuntimeException("Failed to delete " + p, e);
	                 }
	             });
	    }
	    
		public static void createDirectory(String absolutePath) throws IOException 
		{
			Path parentDir = Paths.get(absolutePath); 
			
			  for (int i = 1; i <= 10; i++) 
			  {
		            Path subDir = parentDir.resolve("Page_" + i);
		            Files.createDirectories(subDir);
		      }
			  Path subDir = parentDir.resolve("Page_Multiple");
	          Files.createDirectories(subDir);		
		}

		public static String returnFilePages(List<BaseTransaction> transactions,SOARequest soaRequest) {
			
			int perPageRowCount = soaRequest.getNumberOfRowsPerPage().intValue();
	    	
	    	int accTitleAndAddressRowCount = 5;
	    	int barCodeRowCount = 2;
	    	int centralBankImageRowCount = 4;
	    	int extraBlankRowCount = soaRequest.getAdjustmentRowsPerPage().intValue();
	    	int pageFooterRowCount = 2;
	    	int grantTotalRowCount = 2;
	    	
	    	int firstPageHdrFooterRowCount = accTitleAndAddressRowCount + barCodeRowCount + centralBankImageRowCount + extraBlankRowCount + pageFooterRowCount + grantTotalRowCount;
	    	int firstPageRows = perPageRowCount - firstPageHdrFooterRowCount;
	    	System.out.println(" firstPageHdrFooterRowCount : " + firstPageHdrFooterRowCount + " - transactions.size() : " + transactions.size() + " - firstPageRows : " + firstPageRows);
	    	
	    	if ( transactions.size() <= firstPageRows )
	    		return "Page_1";
	    	
	    	for (int i=2; i<=10; i++)
	    	{
		    	int accAndTitleRowCount = 2;
		    	int nextPageRows = (perPageRowCount*i) - firstPageHdrFooterRowCount - (extraBlankRowCount*(i-1)) - (accAndTitleRowCount*(i-1)) - grantTotalRowCount;
//		    	System.out.println("Page_" + i + " - nextPageRows : " + nextPageRows + " - transactions.size() : " + transactions.size() );
		    	if ( transactions.size() <= nextPageRows )
		    		return "Page_"+i;
	    	}
	    	
			return "Page_Multiple";
		}

}

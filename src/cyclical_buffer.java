import java.util.Random; 
public class cyclical_buffer {

 public static void main(String[] args)  throws InterruptedException 
	 { 
		 
	 // how many times to run it? 22 !!!! 22 times 
	 final buffer bufvector = new buffer(22); 


	 Thread put = new Thread(new Runnable() 
	 { 
		 @Override
		 public void run() 
		 { 
			 try
			 { 
				 bufvector.put(); 
			 } 
			 catch(InterruptedException e) 
			 { 
				 return;
			} 
		 } }); 


	 Thread getl = new Thread(new Runnable() 
	 { 
		 	@Override
		 	public void run() 
		 	{ 
		 		try
		 		{ 
		 			bufvector.get(); 
		 		} 
		 		catch(InterruptedException e) 
		 		{ 
		 			return;
		 		} 
		 	} }); 

	 		put.start(); 
	 		getl.start(); 

	 		put.join(); 
	 		getl.join(); 
} 

    public static class buffer 
    { 
        
    	Random r = new Random();
    	char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        int put =0,get =0;
        char[] buf = new char[16];
        int times;
       public buffer(int n ) {
    	   times = n;
       }
        public void put() throws InterruptedException 
        { 
             
            while (true) 
             synchronized (this) 
                { 
                   while((put ==15 && get != 15)||put-get>2)wait();
                   if(put==get&&get==15)put=get=0;
                   else {
                	   buf[put] = alphabet[r.nextInt(alphabet.length)];
                	   System.out.println("put-"+ buf[put++]); 
                	   notify(); 
                	   
                	   if(times ==0 )return;
                       Thread.sleep(1000); 
                   		}
                   
                } 
             
        } 
  
        // Function called by consumer thread 
        public void get() throws InterruptedException 
        { 
            while (true) 
             synchronized (this) 
                { 
                    while(put - get<0)wait();
                    if(get < put) {
                    	System.out.println("get-"+ buf[get++]); 
                    	  
                        times--;if(times ==0 )return;
                        notify(); 
                        Thread.sleep(1000); 
                    }
                    
  
                    
                } 
         } 
        
    } 
}


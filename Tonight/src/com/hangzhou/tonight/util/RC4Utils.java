package com.hangzhou.tonight.util;

/**
 * 
 * @author WYH
 *
 */
public class RC4Utils {
	
 public	static String RC4(String keys, String encrypt) {
        char[] keyBytes = new char[256];
        char[] cypherBytes = new char[256];

        for (int i = 0; i < 256; ++i) {
            keyBytes[i] = keys.charAt(i % keys.length());
            cypherBytes[i] = (char) i;
        }

        int jump = 0;
        for (int i = 0; i < 256; ++i) {
            jump = (jump + cypherBytes[i] + keyBytes[i]) & 0xFF;
            char tmp = cypherBytes[i]; // Swap:
            cypherBytes[i] = cypherBytes[jump];
            cypherBytes[jump] = tmp;
        }

        int i = 0;
        jump = 0;
        String Result = "";
        for (int x = 0; x < encrypt.length(); ++x) {
            i = (i + 1) & 0xFF;
            char tmp = cypherBytes[i];
            jump = (jump + tmp) & 0xFF;
            char t = (char) ((tmp + cypherBytes[jump]) & 0xFF);
            cypherBytes[i] = cypherBytes[jump]; // Swap:
            cypherBytes[jump] = tmp;

            try {
                Result += new String(new char[] { (char) (encrypt.charAt(x) ^ cypherBytes[t]) });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Result;
    }
	
	public static String HloveyRC4(String aInput,String aKey)   
    {   
        int[] iS = new int[256];   
        byte[] iK = new byte[256];   
         
        for (int i=0;i<256;i++)   
           iS[i]=i;   
              
        int j = 1;   
          
        for (short i= 0;i<256;i++)   
        {   
            iK[i]=(byte)aKey.charAt((i % aKey.length()));   
        }   
          
        j=0;   
          
       for (int i=0;i<255;i++)   
       {   
            j=(j+iS[i]+iK[i]) % 256;   
            int temp = iS[i];   
            iS[i]=iS[j];   
            iS[j]=temp;   
        }   
      
      
    int i=0;   
        j=0;   
        char[] iInputChar = aInput.toCharArray();   
        char[] iOutputChar = new char[iInputChar.length];   
        for(short x = 0;x<iInputChar.length;x++)   
        {   
           i = (i+1) % 256;   
           j = (j+iS[i]) % 256;   
            int temp = iS[i];   
            iS[i]=iS[j];   
            iS[j]=temp;   
            int t = (iS[i]+(iS[j] % 256)) % 256;   
            int iY = iS[t];   
            char iCY = (char)iY;   
            iOutputChar[x] =(char)( iInputChar[x] ^ iCY) ;      
        }   
  
        return String.valueOf(iOutputChar);   
                  
   } 

	
	public static char[] HloveyRC42(String aInput,String aKey)   
    {   
        int[] iS = new int[256];   
        byte[] iK = new byte[256];   
         
        for (int i=0;i<256;i++)   
           iS[i]=i;   
              
        int j = 1;   
          
        for (short i= 0;i<256;i++)   
        {   
            iK[i]=(byte)aKey.charAt((i % aKey.length()));   
        }   
          
        j=0;   
          
       for (int i=0;i<255;i++)   
       {   
            j=(j+iS[i]+iK[i]) % 256;   
            int temp = iS[i];   
            iS[i]=iS[j];   
            iS[j]=temp;   
        }   
      
      
    int i=0;   
        j=0;   
        char[] iInputChar = aInput.toCharArray();   
        char[] iOutputChar = new char[iInputChar.length];   
        for(short x = 0;x<iInputChar.length;x++)   
        {   
           i = (i+1) % 256;   
           j = (j+iS[i]) % 256;   
            int temp = iS[i];   
            iS[i]=iS[j];   
            iS[j]=temp;   
            int t = (iS[i]+(iS[j] % 256)) % 256;   
            int iY = iS[t];   
            char iCY = (char)iY;   
            iOutputChar[x] =(char)( iInputChar[x] ^ iCY) ;      
        }   
  
        return iOutputChar;   
                  
   } 
	
	}


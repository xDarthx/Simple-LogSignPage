package com.example.indivproj2_v1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

import org.mindrot.jbcrypt.BCrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class mainFunctions {
    private static final File filePath = new File("C:/Users/lilso/IdeaProjects/IndivProj2_v1/src/main/resources/com/example/indivproj2_v1/storage.json");

    private static String SecretSecretKey = "HaHaFunnyKey";
    private static ArrayList<User> theList;

    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static boolean insertInfo(String name, String password, String email) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if(filePath.exists())
        {
            try
            {
                FileReader fileReader = new FileReader(filePath);
                Type type = new TypeToken<ArrayList<User>>(){}.getType();
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                theList = gson.fromJson(fileReader, type);
                fileReader.close();
            }
            catch (FileNotFoundException e)
            {
                System.err.println("Error in creating FileReader object");
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            theList = new ArrayList<>();
        }
        password = BCrypt.hashpw(password, BCrypt.gensalt());
        email = encryptEmail(email,SecretSecretKey);
        name = encryptName(name, SecretSecretKey);
        if(checkEmail(email))
        {
            return true;
        } else {
            theList.add(new User(name, password, email));
            try
            {
                FileWriter fileWriter = new FileWriter(filePath);
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                gson.toJson(theList,fileWriter);
                fileWriter.close();
                return false;
            }
            catch(IOException e)
            {
                System.err.println("Error In Writing File");
            }
        }
        return true;
    }

    public static boolean checkEmail(String email)
    {
        if(filePath.exists())
        {
            try
            {
                FileReader fileReader = new FileReader(filePath);
                Type type = new TypeToken<ArrayList<User>>(){}.getType();
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                theList = gson.fromJson(fileReader, type);
                fileReader.close();
                for (User user : theList) {
                    if(Objects.equals(decryptEmail(user.getEmail(),SecretSecretKey), decryptEmail(email, SecretSecretKey))){
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }
                return false;
            }
            catch (FileNotFoundException e)
            {
                System.err.println("Error in creating FileReader object");
                return false;
            }
            catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                   IllegalBlockSizeException | BadPaddingException e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            return false;
        }
    }
    public static String getName(String email){
        if(filePath.exists())
        {
            try
            {
                FileReader fileReader = new FileReader(filePath);
                Type type = new TypeToken<ArrayList<User>>(){}.getType();
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                theList = gson.fromJson(fileReader, type);
                fileReader.close();
                for (User user : theList) {
                    if (Objects.equals(decryptEmail(user.getEmail(), SecretSecretKey), email)) {
                        return decryptName(user.getName(), SecretSecretKey);
                    }
                }
                return "Name Not Found";
            }
            catch (FileNotFoundException e)
            {
                System.err.println("Error in creating FileReader object");
                return "Name Not Found";
            }
            catch (IOException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                   BadPaddingException | InvalidKeyException e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            return "Name Not Found";
        }
    }
    public static boolean checkInfo(String email, String password)
    {
        if(filePath.exists())
        {
            try
            {
                FileReader fileReader = new FileReader(filePath);
                Type type = new TypeToken<ArrayList<User>>(){}.getType();
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();
                theList = gson.fromJson(fileReader, type);
                fileReader.close();
                for (User user : theList) {
                    if (Objects.equals(decryptEmail(user.getEmail(), SecretSecretKey), email) && BCrypt.checkpw(password,user.getPassword())) {
                        return true;
                    }
                }
                return false;
            }
            catch (FileNotFoundException e)
            {
                System.err.println("Error in creating FileReader object");
                return false;
            }
            catch (IOException | NoSuchPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException |
                   BadPaddingException | InvalidKeyException e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            return false;
        }
    }

    public static String encryptName(String strToEncrypt, String secret) throws IOException, NoSuchPaddingException,
    NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        setKey(secret);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte [] strToEncryptBytes = strToEncrypt.getBytes(StandardCharsets.UTF_8);
        byte [] finalCipher = cipher.doFinal(strToEncryptBytes);
        return Base64.getEncoder().encodeToString(finalCipher);
    }
    public static String encryptEmail(String strToEncrypt, String secret) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        setKey(secret);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte [] strToEncryptBytes = strToEncrypt.getBytes(StandardCharsets.UTF_8);
        byte [] finalCipher = cipher.doFinal(strToEncryptBytes);
        return Base64.getMimeEncoder().encodeToString(finalCipher);
    }
    public static String decryptName(String strToDecrypt, String secret) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        setKey(secret);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte [] finalByteString = Base64.getDecoder().decode(strToDecrypt);
        return new String(cipher.doFinal(finalByteString));
    }
    public static String decryptEmail(String strToDecrypt, String secret) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        setKey(secret);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte [] finalByteString = Base64.getMimeDecoder().decode(strToDecrypt);
        return new String(cipher.doFinal(finalByteString));
    }

    public static void setKey(String myKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest sha = null;
        key = myKey.getBytes("UTF-8");
        sha = MessageDigest.getInstance("SHA-512");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16);
        secretKey = new SecretKeySpec(key, "AES");
    }

}

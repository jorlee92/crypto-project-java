package me.jorlee.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class CryptoCompareAPI {
	//https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
	public static float getPrice(String coinName) throws IOException {
		//Get the selected coins prices in USD
		String apiPath = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=" + coinName + "&tsyms=USD";
		
		URL apiUrl = new URL(apiPath);
		URLConnection connection = apiUrl.openConnection();
		connection.connect();
		
		JsonParser parser = new JsonParser();
	    JsonElement root = parser.parse(new InputStreamReader((InputStream) connection.getContent()));
	    JsonObject rootobj = root.getAsJsonObject(); 
	    JsonObject coin = rootobj.getAsJsonObject(coinName);
	    float coin_price = coin.get("USD").getAsFloat();
	    System.out.println("Price of " + coinName + " "+ coin_price);
	    return coin_price;
	}
	public static void main(String[] args) {
		try {
			getPrice("EOS");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

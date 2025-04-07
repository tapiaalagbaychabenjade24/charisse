package org.example;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.ArrayList;
import java.util.List;

public class API {

    public static class Coin {
        String name;
        String symbol;
        double price;
        double marketCap;
        double volume;
        double high;
        double low;
        double priceChange;
        float priceChangePercentage;
        String imageUrl;

        public Coin(String name, String symbol, double price, double marketCap, double volume, double high, double low,
                    double priceChange, float priceChangePercentage, String imageUrl) {
            this.name = name;
            this.symbol = symbol;
            this.price = price;
            this.marketCap = marketCap;
            this.volume = volume;
            this.high = high;
            this.low = low;
            this.priceChange = priceChange;
            this.priceChangePercentage = priceChangePercentage;
            this.imageUrl = imageUrl;
        }

        @Override
        public String toString() {
            return "Coin{" +
                    "name='" + name + '\'' +
                    ", symbol='" + symbol + '\'' +
                    ", price=" + price +
                    ", marketCap=" + marketCap +
                    ", volume=" + volume +
                    ", high=" + high +
                    ", low=" + low +
                    ", priceChange=" + priceChange +
                    ", priceChangePercentage=" + priceChangePercentage +
                    ", imageUrl='" + imageUrl + '\'' +
                    '}';
        }
    }

    public static List<Coin> getCoins() {
        OkHttpClient client = new OkHttpClient();
        List<Coin> coins = new ArrayList<>();

        Request request = new Request.Builder()
                .url("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=250&page=1")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("x-cg-pro-api-key", "CG-2RJAfDykmv8Grtp97Cvei7Tr")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                JsonArray coinsArray = JsonParser.parseString(response.body().string()).getAsJsonArray();

                for (JsonElement coinElement : coinsArray) {
                    var coin = coinElement.getAsJsonObject();
                    String name = coin.get("name").getAsString();
                    String symbol = coin.get("symbol").getAsString().toUpperCase();
                    double price = coin.get("current_price").getAsDouble();
                    double marketCap = coin.get("market_cap").getAsDouble();
                    double volume = coin.get("total_volume").getAsDouble();
                    double high = coin.get("high_24h").getAsDouble();
                    double low = coin.get("low_24h").getAsDouble();
                    double priceChange = coin.get("price_change_24h").getAsDouble();
                    float priceChangePercentage = coin.get("price_change_percentage_24h").getAsFloat();
                    String imageUrl = coin.get("image").getAsString();

                    coins.add(new Coin(name, symbol, price, marketCap, volume, high, low, priceChange, priceChangePercentage, imageUrl));
                }
            } else {
                System.out.println("Error: " + response.code() + " " + response.message());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return coins;

    }
}
